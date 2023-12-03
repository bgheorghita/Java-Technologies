package ro.uaic.info.l7.services.impl;

import ro.uaic.info.l7.entities.PreferenceTimetableEvent;
import ro.uaic.info.l7.entities.TimetableEvent;
import ro.uaic.info.l7.repositories.TimetableEventRepository;
import ro.uaic.info.l7.services.PreferenceTimetableEventService;
import ro.uaic.info.l7.services.TimetableEventService;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.*;

@Stateless
public class TimetableEventServiceImpl implements TimetableEventService {
    @Inject
    private TimetableEventRepository timetableEventRepository;
    @Inject
    private PreferenceTimetableEventService preferenceTimetableEventService;

    @Override
    public List<TimetableEvent> findAll() {
        return timetableEventRepository.findAll();
    }

    @Override
    public Map<TimetableEvent, Integer> computeChronologicalOrderOfTimetableEvents(List<PreferenceTimetableEvent> preferenceTimetableEvents) {
        // each event contains a list of given priorities (named scores)
        Map<Long, List<Integer>> eventScores = new HashMap<>();

        preferenceTimetableEvents.forEach(preferenceTimetableEvent -> {
            TimetableEvent timetableEvent = preferenceTimetableEvent.getTimetableEvent();
            Long key = timetableEvent.getId();
            eventScores.computeIfAbsent(key, k -> new ArrayList<>()).add(preferenceTimetableEvent.getPriority());
        });

        // compute the sum of scores for each event
        Map<Long, Integer> eventSumScores = new HashMap<>();
        eventScores.forEach((eventId, scores) -> {
            int sum = scores.stream().mapToInt(Integer::intValue).sum();
            eventSumScores.put(eventId, sum);
        });

        // Sort events from smallest to largest score (smallest = highest priority) & compute the chronological order
        List<Map.Entry<Long, Integer>> sortedEvents = new ArrayList<>(eventSumScores.entrySet());
        sortedEvents.sort(Map.Entry.comparingByValue());

        Map<TimetableEvent, Integer> chronologicalOrder = new LinkedHashMap<>();
        for (int i = 0; i < sortedEvents.size(); i++) {
            Long eventId = sortedEvents.get(i).getKey();
            int order = i + 1;
            preferenceTimetableEvents.stream()
                    .filter(event -> event.getTimetableEvent().getId().equals(eventId))
                    .findFirst()
                    .ifPresent(correspondingEvent -> chronologicalOrder.put(correspondingEvent.getTimetableEvent(), order));
        }

        return chronologicalOrder;
    }

    @Override
    public Map<TimetableEvent, Integer> computeChronologicalOrderBasedOnAllSubmittedPreferenceTimetableEvents() {
        List<PreferenceTimetableEvent> preferenceTimetableEvents = preferenceTimetableEventService.findAll();
        return computeChronologicalOrderOfTimetableEvents(preferenceTimetableEvents);
    }
}