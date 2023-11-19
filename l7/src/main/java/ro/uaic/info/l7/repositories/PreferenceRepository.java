package ro.uaic.info.l7.repositories;

import ro.uaic.info.l7.entities.Preference;

import java.util.List;

public interface PreferenceRepository {
    Preference save(Preference preference);
    List<Preference> findByUserId(Long id);
    List<Preference> findByUsername(String username);
    List<Preference> findAll();
}
