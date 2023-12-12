package ro.uaic.info.l7.services.impl;

import ro.uaic.info.l7.entities.PreferenceTimetableEvent;
import ro.uaic.info.l7.repositories.PreferenceTimetableEventRepository;
import ro.uaic.info.l7.services.PreferenceTimetableEventService;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.*;
import java.util.stream.Collectors;

@Stateless
public class PreferenceTimetableEventServiceImpl implements PreferenceTimetableEventService {
    @Inject
    private PreferenceTimetableEventRepository preferenceTimetableEventRepository;


    @Override
    public PreferenceTimetableEvent save(PreferenceTimetableEvent preferenceTimetableEvent) {
        return preferenceTimetableEventRepository.save(preferenceTimetableEvent);
    }

    @Override
    public PreferenceTimetableEvent findById(Long id) {
        return preferenceTimetableEventRepository.findById(id).orElseThrow(() -> new RuntimeException("Resource not found"));
    }

    @Override
    public List<PreferenceTimetableEvent> findByPreferenceId(Long preferenceId) {
        return preferenceTimetableEventRepository.findByPreferenceId(preferenceId);
    }

    @Override
    public List<PreferenceTimetableEvent> findByUserId(Long userId) {
        return preferenceTimetableEventRepository.findByUserId(userId);
    }

    @Override
    public List<PreferenceTimetableEvent> findAll() {
        return preferenceTimetableEventRepository.findAll();
    }

    @Override
    public List<PreferenceTimetableEvent> getCircularDependencies(List<PreferenceTimetableEvent> preferenceTimetableEvents) {
        // a circular dependency is when there are two events with the same priority
        Map<Integer, List<PreferenceTimetableEvent>> priorityEventMap = new HashMap<>();

        for (PreferenceTimetableEvent event : preferenceTimetableEvents) {
            Integer priority = event.getPriority();
            priorityEventMap.computeIfAbsent(priority, k -> new ArrayList<>()).add(event);
        }

        return priorityEventMap.entrySet().stream()
                .filter(entry -> entry.getValue().size() > 1)
                .flatMap(entry -> entry.getValue().stream())
                .collect(Collectors.toList());
    }
}