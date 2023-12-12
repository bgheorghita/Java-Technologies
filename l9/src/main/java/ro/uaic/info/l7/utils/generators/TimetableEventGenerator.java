package ro.uaic.info.l7.utils.generators;

import ro.uaic.info.l7.entities.TimetableEvent;

import java.util.List;

public interface TimetableEventGenerator {
    List<TimetableEvent> generateTimetableEvents(int size);
}
