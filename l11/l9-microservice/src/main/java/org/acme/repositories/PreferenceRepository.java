package org.acme.repositories;


import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import org.acme.models.Preference;

import java.util.List;

@ApplicationScoped
public class PreferenceRepository implements PanacheRepository<Preference> {
    public List<Preference> findAllPreferences() {
        return listAll();
    }

    @Transactional
    public Preference save(Preference preference) {
        persistAndFlush(preference);
        return preference;
    }

    public List<Preference> findByUsername(String username) {
        return find("user.username", username).stream().toList();
    }
}