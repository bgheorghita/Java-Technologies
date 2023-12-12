package ro.uaic.info.l7.beans;

import ro.uaic.info.l7.entities.TimetableEvent;
import ro.uaic.info.l7.services.TimetableEventService;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Map;

@Named("timetableEventBean")
@SessionScoped
public class TimetableEventBean implements Serializable {
    @Inject
    private TimetableEventService timetableEventService;
    private Map<TimetableEvent, Integer> chronologicalOrder;

    @PostConstruct
    public void init() {
        chronologicalOrder = timetableEventService.computeChronologicalOrderBasedOnAllSubmittedPreferenceTimetableEvents();
    }

    public Map<TimetableEvent, Integer> getChronologicalOrder() {
        return chronologicalOrder;
    }
}
