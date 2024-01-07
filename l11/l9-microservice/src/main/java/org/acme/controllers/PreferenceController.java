package org.acme.controllers;

import io.quarkus.security.Authenticated;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import org.acme.dtos.PreferenceDto;
import org.acme.mappers.PreferenceMapper;
import org.acme.models.Preference;
import org.acme.services.PreferenceService;
import org.eclipse.microprofile.faulttolerance.*;
import org.eclipse.microprofile.lra.annotation.Compensate;
import org.eclipse.microprofile.lra.annotation.Complete;
import org.eclipse.microprofile.lra.annotation.ws.rs.LRA;
import org.eclipse.microprofile.metrics.MetricUnits;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Timed;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import static org.eclipse.microprofile.lra.annotation.ws.rs.LRA.LRA_HTTP_CONTEXT_HEADER;

@Path("/preferences")
@RequestScoped
@Authenticated
public class PreferenceController {
    @Context
    SecurityContext ctx;
    @Inject
    PreferenceService preferenceService;
    @Inject
    PreferenceMapper preferenceMapper;
    private final AtomicLong counter = new AtomicLong(0);
//    private long performanceEndpointCalls = 0;
    private final Map<URI, Long> lraIdAndPreference = new ConcurrentHashMap<>();

    @Timeout(500)
    @Fallback(fallbackMethod = "getMockPreference")
    @Retry(maxRetries = 1, delay = 1000)
    @Path("/timeout/{timeInMs}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Preference> getPreferencesFallbackTimeoutRetry(@PathParam("timeInMs") int timeInMs) {
        try {
            System.out.println(timeInMs);
            Thread.sleep(timeInMs);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        return preferenceService.findAll();
    }

    private List<Preference> getMockPreference(int timeInMs) {
        return List.of(new Preference(null, "mock preference", "mock registration number"));
    }

    // the circuit is open once 2 (2 x 1) failures occur among the rolling window of 2 consecutive invocations
    // The circuit will stay open for 3000ms and then back to half open.
    // After 2 consecutive successful invocations, the circuit will be back to close again. When a
    // circuit is open, a CircuitBreakerOpenException will be thrown.
    @CircuitBreaker(
            successThreshold = 2,
            requestVolumeThreshold = 2,
            failureRatio = 1,
            delay = 3000)
    @Path("/circuit-breaker")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Preference> getPreferencesCircuitBreaker() {
        maybeFail();
        return preferenceService.findAll();
    }

    // Semaphore isolation: execution happens on the calling thread
    // and the concurrent requests are constrained by the semaphore
    // count.
    @Bulkhead(5) // maximum 5 concurrent requests allowed
    @Path("/semaphore")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Preference> getPreferencesSemaphore() {
        try {
            Thread.sleep(10000);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        return preferenceService.findAll();
    }

    // Thread pool isolation: execution happens on a separate thread
    // and the concurrent requests are confined in a fixed number of a
    // thread pool
    @Asynchronous
    @Bulkhead(value = 5, waitingTaskQueue = 8)
    // maximum 5 concurrent requests allowed,
    // maximum 8 requests allowed in the waiting queue
    @Path("/thread-pool")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Future<List<Preference>> getPreferencesThreadPool() {
        return CompletableFuture.supplyAsync(() -> preferenceService.findAll());
    }

    private void maybeFail() {
        final long invocationNumber = counter.getAndIncrement();
        if (invocationNumber % 4 > 1) { // alternate 2 successful and 2 failing invocations
            throw new RuntimeException("Service failed.");
        }
    }

    // http://localhost:8088/q/metrics/application
    @Path("/performance")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Counted(name = "performedChecks", description = "How many checks have been performed.")
    @Timed(name = "checksTimer", description = "A measure of how long it takes to perform the fetch of the preferences.", unit = MetricUnits.MILLISECONDS)
    public List<Preference> getPreferencesPerformance() {
//        performanceEndpointCalls++;
        return preferenceService.findAll();
    }

//    @Gauge(name = "performanceEndpointCallsSoFar", unit = MetricUnits.NONE, description = "Performance Endpoint Calls So Far.")
//    public Long performanceEndpointCalls() {
//        return performanceEndpointCalls;
//    }

    @Path("/hello")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String helloFromDocker() {
        return "Hello from Docker";
    }

    // Lab 11
    @LRA(
            cancelOn = {
                Response.Status.INTERNAL_SERVER_ERROR
            },
            value = LRA.Type.REQUIRED
    )
    @POST
    @RolesAllowed({"TEACHER"})
    @Produces(MediaType.APPLICATION_JSON)
    public Response save(@HeaderParam(LRA_HTTP_CONTEXT_HEADER) URI lraId, PreferenceDto preferenceDto) {
        if(!ctx.getUserPrincipal().getName().equals(preferenceDto.getUsername())) {
            return Response.status(403).entity("Forbidden: You are not allowed to use a username other than yours.").build();
        }

        Preference preference = preferenceMapper.fromDto(preferenceDto);
        Preference savedPreference =  preferenceService.save(preference);
        PreferenceDto returnPreference = preferenceMapper.toDto(savedPreference);
        lraIdAndPreference.put(lraId, preference.getId());

        Client client = ClientBuilder.newClient();
        client
                .target("http://localhost:8089/submissions/" + preference.getUser().getUsername())
                .request(MediaType.APPLICATION_JSON)
                .post(null);
        client.close();

        return Response.created(URI.create("/preferences/" + savedPreference.getId())).entity(returnPreference).build();
    }

    @Path("/user")
    @GET
    @RolesAllowed({"TEACHER"})
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllPreferencesByLoggedUser() {
        List<Preference> preferences = preferenceService.findByUsername(ctx.getUserPrincipal().getName());
        List<PreferenceDto> preferenceDtos = preferences.stream().map(preferenceMapper::toDto).collect(Collectors.toList());
        return Response.ok(preferenceDtos).build();
    }

    @Path("/{id}")
    @DELETE
    @RolesAllowed({"ADMIN"})
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(@PathParam("id") Long id) {
        Preference deletedPreference = preferenceService.deleteById(id);
        return Response.ok().entity(preferenceMapper.toDto(deletedPreference)).build();
    }

    @GET
    @RolesAllowed({"ADMIN"})
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllPreferences() {
        List<Preference> preferences = preferenceService.findAll();
        List<PreferenceDto> preferenceDtos = preferences.stream().map(preferenceMapper::toDto).collect(Collectors.toList());
        return Response.ok(preferenceDtos).build();
    }

    @PUT
    @Path("/compensate")
    @Compensate
    @Transactional
    public Response compensateWork(@HeaderParam(LRA_HTTP_CONTEXT_HEADER) URI lraId) {
        Long preferenceId = lraIdAndPreference.get(lraId);
        preferenceService.deleteById(preferenceId);
        System.out.printf("[PreferenceController] compensating %s (deleted preference id: %s%n)", lraId, preferenceId);
        return Response.ok(lraId.toASCIIString()).build();
    }

    @PUT
    @Path("/complete")
    @Complete
    public Response completeWork(@HeaderParam(LRA_HTTP_CONTEXT_HEADER) URI lraId) {
        System.out.printf("[PreferenceController] completing %s (preference saved)%n", lraId);
        return Response.ok(lraId.toASCIIString()).build();
    }
}