package org.acme.es.repositories;

import org.acme.es.events.Event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserEventRepository {
    private final Map<Long, List<Event>> store = new HashMap<>();

    public void save(Long userId, Event event) {
        List<Event> events = store.get(userId);
        if(events == null) {
            events = new ArrayList<>();
        }
        events.add(event);
        store.put(userId, events);
    }

    public List<Event> getEventsByUserId(Long userId) {
        List<Event> events = store.get(userId);
        return events == null ? new ArrayList<>() : events;
    }
}