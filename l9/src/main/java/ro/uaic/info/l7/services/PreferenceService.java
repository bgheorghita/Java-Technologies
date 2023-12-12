package ro.uaic.info.l7.services;

import ro.uaic.info.l7.entities.Preference;

import java.util.List;

public interface PreferenceService {
    Preference saveOrUpdate(Preference preference);
    Preference findById(Long id);
    Preference deleteById(Long id);
    Preference findByRegistrationNumber(String registrationNumber);
    List<Preference> findByUserId(Long userId);
    List<Preference> findByUsername(String username);
    List<Preference> findAll();
}
