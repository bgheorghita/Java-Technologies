package org.acme.controllers;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.acme.models.Preference;
import org.acme.services.PreferenceService;

import java.util.List;
import java.util.stream.Collectors;

@Path("/preferences")
@ApplicationScoped
public class PreferenceController {
    @Inject
    PreferenceService preferenceService;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public List<String> getPreferences() {
        return preferenceService.findAll().stream().map(Preference::toString).collect(Collectors.toList());
    }
}