package ro.uaic.info.l7.utils.generators;

import ro.uaic.info.l7.entities.PreferenceTimetableEvent;
import ro.uaic.info.l7.entities.TimetableEvent;

import javax.ejb.Stateless;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Stateless
public class PreferenceTimetableEventGeneratorImpl implements PreferenceTimetableEventGenerator {

    @Override
    public List<PreferenceTimetableEvent> generatePreferenceTimetableEvents(List<TimetableEvent> timetableEvents, int noOfPreferencesForEachTimetableEvent) {
        List<PreferenceTimetableEvent> preferenceTimetableEvents = new ArrayList<>();
        AtomicLong preferenceTimetableEventId = new AtomicLong(0);
        AtomicLong timetableEventId = new AtomicLong(0);
        
        timetableEvents.forEach(timetableEvent -> {
            timetableEvent.setId(timetableEventId.getAndIncrement());
            for(int i = 1; i <= noOfPreferencesForEachTimetableEvent; i++) {
                PreferenceTimetableEvent preferenceTimetableEvent = new PreferenceTimetableEvent(null, timetableEvent, getRandomPriority(timetableEvents.size()));
                preferenceTimetableEvent.setId(preferenceTimetableEventId.getAndIncrement());
                preferenceTimetableEvents.add(preferenceTimetableEvent);
            }
        });

        return preferenceTimetableEvents;
    }

    private Integer getRandomPriority(int maxInclusive) {
        Random r = new Random();
        return r.nextInt(maxInclusive) + 1;
    }
}