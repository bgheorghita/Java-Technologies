package ro.uaic.info.l7.services;

import ro.uaic.info.l7.entities.PreferenceTimetableEvent;

import java.io.Serializable;
import java.util.List;

public interface PreferenceTimetableEventService extends Serializable {
    PreferenceTimetableEvent save(PreferenceTimetableEvent preferenceTimetableEvent);
    PreferenceTimetableEvent findById(Long id);
    List<PreferenceTimetableEvent> findByPreferenceId(Long preferenceId);
    List<PreferenceTimetableEvent> findByUserId(Long userId);
    List<PreferenceTimetableEvent> findAll();
    List<PreferenceTimetableEvent> getCircularDependencies(List<PreferenceTimetableEvent> preferenceTimetableEvents);
}
