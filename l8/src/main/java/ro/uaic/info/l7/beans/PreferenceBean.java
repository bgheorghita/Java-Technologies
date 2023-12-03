package ro.uaic.info.l7.beans;

import ro.uaic.info.l7.entities.Preference;
import ro.uaic.info.l7.entities.PreferenceTimetableEvent;
import ro.uaic.info.l7.entities.TimetableEvent;
import ro.uaic.info.l7.entities.User;
import ro.uaic.info.l7.interfaces.Logged;
import ro.uaic.info.l7.services.PreferenceService;
import ro.uaic.info.l7.services.PreferenceTimetableEventService;
import ro.uaic.info.l7.services.TimetableEventService;
import ro.uaic.info.l7.services.UserService;
import ro.uaic.info.l7.utils.NumberGenerator;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Named("preferenceBean")
@SessionScoped
public class PreferenceBean implements Serializable {
    @Inject
    private UserSessionBean userSessionBean;

    private String content;

    @Inject
    private PreferenceService preferenceService;

    @Inject
    private UserService userService;

    @Inject
    private NumberGenerator numberGenerator;

    @Inject
    private TimetableEventService timetableEventService;

    @Inject
    private PreferenceTimetableEventService preferenceTimetableEventService;

    private List<TimetableEvent> availableEvents;
    private Map<Long, Integer> eventPriorities = new HashMap<>();
    List<Integer> availablePriorities = new ArrayList<>();

    @PostConstruct
    public void loadEvents() {
        availableEvents = timetableEventService.findAll();
        availablePriorities = IntStream.rangeClosed(1, availableEvents.size()).boxed().collect(Collectors.toList());
    }

    @Logged
    public void submitPreference() {
        List<PreferenceTimetableEvent> preferenceTimetableEvents = new ArrayList<>();
        availableEvents.forEach(event -> {
            Integer priority = eventPriorities.get(event.getId());
            PreferenceTimetableEvent preferenceTimetableEvent = new PreferenceTimetableEvent();
            preferenceTimetableEvent.setTimetableEvent(event);
            preferenceTimetableEvent.setPriority(priority);
            preferenceTimetableEvents.add(preferenceTimetableEvent);
        });

        List<PreferenceTimetableEvent> circularDependencies = preferenceTimetableEventService.getCircularDependencies(preferenceTimetableEvents);
        circularDependencies.forEach(preferenceTimetableEvent -> FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, preferenceTimetableEvent.getTimetableEvent().getName() + " has a circular dependency", null)));
        if(!circularDependencies.isEmpty()) {
            return;
        }

        Preference preference = new Preference();
        User user = userService.findByUsername(userSessionBean.getLoggedInUsername());
        preference.setUser(user);
        preference.setContent(content);
        preference.setRegistrationNumber(numberGenerator.getRandomUUID().toString());
        Preference savedPreference = preferenceService.saveOrUpdate(preference);
        if(savedPreference == null || savedPreference.isNew()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "The preference could not be saved.", null));
            return;
        }

        preferenceTimetableEvents.forEach(preferenceTimetableEvent -> {
            preferenceTimetableEvent.setPreference(savedPreference);
            preferenceTimetableEventService.save(preferenceTimetableEvent);
        });
        eventPriorities.clear();
        content = null;
    }

    public List<Preference> getPreferences() {
        return preferenceService.findAll();
    }

    public List<Preference> getPreferencesOfLoggedUser() {
        return preferenceService.findByUsername(userSessionBean.getLoggedInUsername());
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<TimetableEvent> getAvailableEvents() {
        return availableEvents;
    }

    public void setAvailableEvents(List<TimetableEvent> availableEvents) {
        this.availableEvents = availableEvents;
    }

    public Map<Long, Integer> getEventPriorities() {
        return eventPriorities;
    }

    public void setEventPriorities(Map<Long, Integer> eventPriorities) {
        this.eventPriorities = eventPriorities;
    }

    public List<Integer> getAvailablePriorities() {
        return availablePriorities;
    }

    public void setAvailablePriorities(List<Integer> availablePriorities) {
        this.availablePriorities = availablePriorities;
    }
}
