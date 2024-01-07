package org.acme.services;


import jakarta.inject.Inject;
import jakarta.inject.Singleton;
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
}