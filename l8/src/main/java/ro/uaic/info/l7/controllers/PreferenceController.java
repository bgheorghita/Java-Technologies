package ro.uaic.info.l7.controllers;

import com.wordnik.swagger.annotations.*;
import ro.uaic.info.l7.dtos.PreferenceDto;
import ro.uaic.info.l7.entities.Preference;
import ro.uaic.info.l7.mappers.DtoMapper;
import ro.uaic.info.l7.services.PreferenceService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.stream.Collectors;

@Path(PreferenceController.PREFERENCE_PATH)
@ApplicationScoped
@Api(value = PreferenceController.PREFERENCE_PATH, description = "Operations related to preferences")
public class PreferenceController {
    public final static String PREFERENCE_PATH = "/preferences";
    @Inject
    private PreferenceService preferenceService;
    @Inject
    private DtoMapper<Preference, PreferenceDto> preferenceDtoMapper;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Get all preferences", notes = "Returns a list of all preferences")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = PreferenceDto.class),
            @ApiResponse(code = 500, message = "Something wrong in Server")})
    public List<PreferenceDto> getPreferences() {
        return preferenceService
                .findAll()
                .stream()
                .map(preferenceDtoMapper::toDto)
                .collect(Collectors.toList());
    }

    @GET
    @Path("/user/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Get preferences by user ID", notes = "Returns a list of preferences for a specific user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = PreferenceDto.class),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public List<PreferenceDto> getPreferencesByUserId(@ApiParam(value = "User ID of the desired preferences", required = true) @PathParam("userId") Long userId) {
        return preferenceService
                .findByUserId(userId)
                .stream()
                .map(preferenceDtoMapper::toDto)
                .collect(Collectors.toList());
    }

    @GET
    @Path("/{id}")
    @ApiOperation(value = "Get preference by ID", notes = "Returns a single preference by its ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = PreferenceDto.class),
            @ApiResponse(code = 404, message = "Resource not found."),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @Produces(MediaType.APPLICATION_JSON) public PreferenceDto getPreferenceById(@PathParam("id") Long id) {
        return preferenceDtoMapper.toDto(preferenceService.findById(id));
    }

    @DELETE
    @Path("/{id}")
    @ApiOperation(value = "Delete preference by ID", notes = "Deletes a preference by its ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = PreferenceDto.class),
            @ApiResponse(code = 404, message = "Resource not found."),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @Produces(MediaType.APPLICATION_JSON) public PreferenceDto deleteById(@PathParam("id") Long id) {
        return preferenceDtoMapper.toDto(preferenceService.deleteById(id));
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Add a new preference", notes = "Creates and returns a new preference")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Created", response = PreferenceDto.class),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public PreferenceDto add(PreferenceDto preferenceDto) {
        Preference preference = preferenceDtoMapper.fromDto(preferenceDto);
        return preferenceDtoMapper.toDto(preferenceService.saveOrUpdate(preference));
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Update an existing preference", notes = "Updates and returns an existing preference")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = PreferenceDto.class),
            @ApiResponse(code = 404, message = "Resource not found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public PreferenceDto update(PreferenceDto preferenceDto) {
        Preference preference = preferenceDtoMapper.fromDto(preferenceDto);
        return preferenceDtoMapper.toDto(preferenceService.saveOrUpdate(preference));
    }
}