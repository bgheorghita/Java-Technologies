package ro.uaic.info.l7.cache;

import ro.uaic.info.l7.entities.Preference;

import javax.enterprise.context.ApplicationScoped;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class PreferenceCache {
    private final List<Preference> preferencesCache = new ArrayList<>();

    public synchronized Preference getById(Long id) {
        Optional<Preference> preference = findById(id);
        return preference.orElse(null);
    }

    private synchronized Optional<Preference> findById(Long id) {
        return preferencesCache.stream().filter(p -> p.getId().equals(id)).findAny();
    }

    public synchronized void invalidateById(Long id) {
        Optional<Preference> preferenceOptional = findById(id);
        if (preferenceOptional.isEmpty()) {
            return;
        }

        Preference preference = preferenceOptional.get();
        preferencesCache.remove(preference);
    }

    public synchronized void invalidate(Preference preference) {
        preferencesCache.remove(preference);
    }

    public synchronized void add(Preference preference) {
        Optional<Preference> cachedPreference = findById(preference.getId());
        if (cachedPreference.isPresent()) {
            invalidate(preference);
        }
        preferencesCache.add(preference);
    }
}