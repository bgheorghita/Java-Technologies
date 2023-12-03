package ro.uaic.info.l7.filters;

import ro.uaic.info.l7.cache.PreferenceCache;
import ro.uaic.info.l7.controllers.PreferenceController;
import ro.uaic.info.l7.dtos.PreferenceDto;
import ro.uaic.info.l7.entities.Preference;
import ro.uaic.info.l7.mappers.DtoMapper;

import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

@Provider
public class PreferenceCacheFilter implements ContainerRequestFilter, ContainerResponseFilter {
    @Inject
    private PreferenceCache preferenceCache;
    @Inject
    private DtoMapper<Preference, PreferenceDto> preferenceDtoMapper;

    // cache lookup only for GET requests having the path /preferences/{id}
    // request filter
    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        String path = requestContext.getUriInfo().getPath(); // /preferences/1
        String[] parts = path.split("/"); // "", "preferences", "1"
        if(isEligibleForCacheLookup(requestContext, path, parts)) {
            try{
                Long preferenceId = Long.parseLong(parts[2]);
                Preference cachedPreference = preferenceCache.getById(preferenceId);
                if (cachedPreference != null) {
                    requestContext.abortWith(Response.ok(preferenceDtoMapper.toDto(cachedPreference)).build());
                }
            } catch (NumberFormatException ex) {
                ex.printStackTrace();
            }
        }
    }

    private static boolean isEligibleForCacheLookup(ContainerRequestContext requestContext, String path, String[] parts) {
        return path.startsWith(PreferenceController.PREFERENCE_PATH) && "GET".equalsIgnoreCase(requestContext.getMethod()) && parts.length == 3;
    }

    // cache update only for GET, POST, PUT, DELETE requests for paths starting with /preferences
    // response filter
    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
        responseContext.getHeaders().putSingle("Content-Type", MediaType.APPLICATION_JSON);
        String path = requestContext.getUriInfo().getPath();
        String method = requestContext.getMethod();
        if(path.startsWith(PreferenceController.PREFERENCE_PATH)) {
            updateCache(method, responseContext.getEntity());
        }
    }

    private void updateCache(String method, Object entity) {
        if ("GET".equalsIgnoreCase(method) || "POST".equalsIgnoreCase(method) || "PUT".equalsIgnoreCase(method)) {
            if (entity instanceof PreferenceDto) {
                PreferenceDto preferenceDto = (PreferenceDto) entity;
                Preference preference = preferenceDtoMapper.fromDto(preferenceDto);
                preferenceCache.add(preference);
            }
        } else if ("DELETE".equalsIgnoreCase(method)) {
            if (entity instanceof PreferenceDto) {
                PreferenceDto preferenceDto = (PreferenceDto) entity;
                preferenceCache.invalidateById(preferenceDto.getId());
            }
        }
    }
}