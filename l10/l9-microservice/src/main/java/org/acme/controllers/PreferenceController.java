package org.acme.controllers;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.acme.models.Preference;
import org.acme.services.PreferenceService;
import org.eclipse.microprofile.faulttolerance.*;
import org.eclipse.microprofile.metrics.MetricUnits;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Gauge;
import org.eclipse.microprofile.metrics.annotation.Timed;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicLong;

@Path("/preferences")
@ApplicationScoped
public class PreferenceController {
    @Inject
    PreferenceService preferenceService;
    private final AtomicLong counter = new AtomicLong(0);
    private long performanceEndpointCalls = 0;

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
        performanceEndpointCalls++;
        return preferenceService.findAll();
    }

    @Gauge(name = "performanceEndpointCallsSoFar", unit = MetricUnits.NONE, description = "Performance Endpoint Calls So Far.")
    public Long performanceEndpointCalls() {
        return performanceEndpointCalls;
    }

    @Path("/hello")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String helloFromDocker() {
        return "Hello from Docker";
    }
}