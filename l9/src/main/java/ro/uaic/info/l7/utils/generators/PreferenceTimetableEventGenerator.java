package ro.uaic.info.l7.utils.generators;

import ro.uaic.info.l7.entities.PreferenceTimetableEvent;
import ro.uaic.info.l7.entities.TimetableEvent;

import java.util.List;

public interface PreferenceTimetableEventGenerator {
    List<PreferenceTimetableEvent> generatePreferenceTimetableEvents(List<TimetableEvent> timetableEvents, int noOfPreferencesForEachTimetableEvent);
}
