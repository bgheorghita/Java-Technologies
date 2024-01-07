package org.acme.controllers;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.services.UserSubmissionService;
import org.eclipse.microprofile.lra.annotation.Compensate;
import org.eclipse.microprofile.lra.annotation.Complete;
import org.eclipse.microprofile.lra.annotation.ws.rs.LRA;

import java.net.URI;

import static org.eclipse.microprofile.lra.annotation.ws.rs.LRA.LRA_HTTP_CONTEXT_HEADER;

@Path(UserSubmissionController.BASE_URI)
public class UserSubmissionController {
    public static final String BASE_URI = "/submissions";
    @Inject
    UserSubmissionService userSubmissionService;

    @LRA(value = LRA.Type.REQUIRED, end = false)
    @POST
    @Path("/{username}")
    @Produces(MediaType.APPLICATION_JSON)
        public Response increment(@PathParam("username") String username) {
//        return Response.serverError().build();
        if(username == null || username.isBlank()) {
            return Response.serverError().entity("username cannot be blank").build();
        }
        userSubmissionService.incrementCounter(username);
        return Response.ok(URI.create(UserSubmissionController.BASE_URI + "/" + username)).build();
    }

    @PUT
    @Path("/compensate")
    @Compensate
    public Response compensateWork(@HeaderParam(LRA_HTTP_CONTEXT_HEADER) URI lraId) {
        System.out.printf("[UserSubmissionController] compensating %s - preference deleted %n", lraId);
        return Response.ok(lraId.toASCIIString()).build();
    }

    @PUT
    @Path("/complete")
    @Complete
    public Response completeWork(@HeaderParam(LRA_HTTP_CONTEXT_HEADER) URI lraId) {
        System.out.printf("[UserSubmissionController] completing %s (user counter incremented)%n", lraId);
        return Response.ok(lraId.toASCIIString()).build();
    }
}
