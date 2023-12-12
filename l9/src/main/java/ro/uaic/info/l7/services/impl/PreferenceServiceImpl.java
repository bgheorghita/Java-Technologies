package ro.uaic.info.l7.services.impl;

import ro.uaic.info.l7.entities.Preference;
import ro.uaic.info.l7.repositories.PreferenceRepository;
import ro.uaic.info.l7.services.PreferenceService;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.List;

@Stateless
public class PreferenceServiceImpl implements PreferenceService, Serializable {
    @Inject
    private PreferenceRepository preferenceRepository;

    @Override
    public Preference saveOrUpdate(Preference preference) {
        return preferenceRepository.saveOrUpdate(preference);
    }

    @Override
    public Preference findById(Long id) {
        return preferenceRepository.findById(id).orElseThrow(() -> new RuntimeException("Resource not found."));
    }

    @Override
    public Preference deleteById(Long id) {
        return preferenceRepository.deleteById(id).orElseThrow(() -> new RuntimeException("Resource not found."));
    }

    @Override
    public Preference findByRegistrationNumber(String registrationNumber) {
        return preferenceRepository.findByRegistrationNumber(registrationNumber).orElseThrow(() -> new RuntimeException("Resource not found."));
    }

    @Override
    public List<Preference> findByUserId(Long userId) {
        return preferenceRepository.findByUserId(userId);
    }

    @Override
    public List<Preference> findByUsername(String username) {
        return preferenceRepository.findByUsername(username);
    }

    @Override
    public List<Preference> findAll() {
        return preferenceRepository.findAll();
    }
}
