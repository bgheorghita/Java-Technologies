package ro.uaic.info.l7.configs;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import ro.uaic.info.l7.controllers.PreferenceController;
import ro.uaic.info.l7.controllers.TimetableEventController;
import ro.uaic.info.l7.filters.PreferenceCacheFilter;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.Set;

@ApplicationPath("/resources")
public class ApplicationConfig extends Application {
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(com.wordnik.swagger.jaxrs.listing.ApiListingResource.class);
        resources.add(com.wordnik.swagger.jaxrs.listing.ApiDeclarationProvider.class);
        resources.add(com.wordnik.swagger.jaxrs.listing.ApiListingResourceJSON.class);
        resources.add(com.wordnik.swagger.jaxrs.listing.ResourceListingProvider.class);
        resources.add(JacksonJsonProvider.class);

        resources.add(PreferenceController.class);
        resources.add(PreferenceCacheFilter.class);
        resources.add(TimetableEventController.class);
    }
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }
}