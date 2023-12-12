package ro.uaic.info.l7.utils.generators;

import ro.uaic.info.l7.entities.TimetableEvent;

import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class TimetableEventGeneratorImpl implements TimetableEventGenerator {

    @Override
    public List<TimetableEvent> generateTimetableEvents(int size) {
        List<TimetableEvent> events = new ArrayList<>();
        for(int i = 1; i <= size; i++) {
            events.add(new TimetableEvent("Event " + i));
        }

        return events;
    }
}
