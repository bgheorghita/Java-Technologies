package org.acme.services;


import org.acme.models.Preference;

import java.util.List;

public interface PreferenceService {
    List<Preference> findAll();
    List<Preference> findByUsername(String username);
    Preference save(Preference preference);
    Preference deleteById(Long id);
}
