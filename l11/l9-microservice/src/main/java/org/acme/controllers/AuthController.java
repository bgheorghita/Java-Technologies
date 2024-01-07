package org.acme.controllers;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.dtos.RegisterUser;
import org.acme.models.Token;
import org.acme.services.AuthService;

import java.net.URI;

@Path("/auth")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class AuthController {
    @Inject
    AuthService authService;

    @POST
    @Path("/register")
    @Transactional
    public Response register(RegisterUser registerUser) {
        try{
            Token token = authService.register(registerUser.getUsername(), registerUser.getPassword());
            return Response.created(URI.create("/register")).entity(token).build();
        } catch (IllegalArgumentException e) {
            return Response.status(400).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/login")
    public Response login(@QueryParam("username") String username, @QueryParam("password") String password) {
        try{
            Token token = authService.login(username, password);
            return Response.ok().entity(token).build();
        } catch (IllegalArgumentException e) {
            return Response.status(400).entity(e.getMessage()).build();
        }
    }
}
