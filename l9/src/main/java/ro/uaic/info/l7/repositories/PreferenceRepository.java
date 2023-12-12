package ro.uaic.info.l7.repositories;

import ro.uaic.info.l7.entities.Preference;

import java.util.List;
import java.util.Optional;

public interface PreferenceRepository {
    Preference saveOrUpdate(Preference preference);
    List<Preference> findByUserId(Long id);
    List<Preference> findByUsername(String username);
    List<Preference> findAll();
    Optional<Preference> findById(Long id);
    Optional<Preference> deleteById(Long id);
    Optional<Preference> findByRegistrationNumber(String registrationNumber);
}
