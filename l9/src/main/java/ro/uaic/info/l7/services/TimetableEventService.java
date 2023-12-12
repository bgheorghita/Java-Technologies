package ro.uaic.info.l7.services;

import ro.uaic.info.l7.entities.PreferenceTimetableEvent;
import ro.uaic.info.l7.entities.TimetableEvent;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface TimetableEventService extends Serializable {
    List<TimetableEvent> findAll();
    Map<TimetableEvent, Integer> computeChronologicalOrderOfTimetableEvents(List<PreferenceTimetableEvent> preferenceTimetableEvents);
    Map<TimetableEvent, Integer> computeChronologicalOrderBasedOnAllSubmittedPreferenceTimetableEvents();
}
