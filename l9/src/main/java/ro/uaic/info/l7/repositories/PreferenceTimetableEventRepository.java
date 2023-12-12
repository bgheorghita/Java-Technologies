package ro.uaic.info.l7.repositories;

import ro.uaic.info.l7.entities.PreferenceTimetableEvent;

import java.util.List;
import java.util.Optional;

public interface PreferenceTimetableEventRepository {
    PreferenceTimetableEvent save(PreferenceTimetableEvent preferenceTimetableEvent);
    Optional<PreferenceTimetableEvent> findById(Long id);
    List<PreferenceTimetableEvent> findByPreferenceId(Long preferenceId);
    List<PreferenceTimetableEvent> findByUserId(Long userId);

    List<PreferenceTimetableEvent> findAll();
}
