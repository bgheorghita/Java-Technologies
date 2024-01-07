package org.acme.services;


import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.transaction.Transactional;
import org.acme.models.Preference;
import org.acme.repositories.PreferenceRepository;

import java.io.Serializable;
import java.util.List;

@Singleton
public class PreferenceServiceImpl implements PreferenceService, Serializable {
    @Inject
    PreferenceRepository preferenceRepository;

    @Override
    public List<Preference> findAll() {
        return preferenceRepository.findAllPreferences();
    }

    @Override
    public List<Preference> findByUsername(String username) {
        return preferenceRepository.findByUsername(username);
    }

    @Override
    public Preference save(Preference preference) {
        return preferenceRepository.save(preference);
    }

    @Override
    @Transactional
    public Preference deleteById(Long id) {
        Preference preference = preferenceRepository.findById(id);
        if(preference == null) {
            throw new IllegalArgumentException("Preference id does not exist");
        } else {
            preferenceRepository.deleteById(id);
        }
        return preference;
    }
}