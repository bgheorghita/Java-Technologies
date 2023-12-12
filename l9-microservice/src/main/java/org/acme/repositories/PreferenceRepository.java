package org.acme.repositories;


import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.acme.models.Preference;

import java.util.List;

@ApplicationScoped
public class PreferenceRepository implements PanacheRepository<Preference> {
    public List<Preference> findAllPreferences() {
        return listAll();
    }
}