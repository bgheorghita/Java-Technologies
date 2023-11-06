package ro.uaic.info.l3.services;

import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.LazyScheduleModel;
import org.primefaces.model.ScheduleModel;
import ro.uaic.info.l3.models.ScheduledMeeting;
import ro.uaic.info.l3.utils.MeetingScheduler;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@ManagedBean
@RequestScoped
public class ScheduleController implements Serializable {

    private final ScheduleModel lazyEventModel;

    public ScheduleController() {
        MeetingScheduler meetingScheduler = new MeetingScheduler();
        List<ScheduledMeeting> meetings = meetingScheduler.getDemoData();
        meetings.forEach(s -> System.out.println(s.getFriendlyPresentation() + ", " + s.getMeeting()));

        lazyEventModel = new LazyScheduleModel() {
            @Override
            public void loadEvents(LocalDateTime localDateTime, LocalDateTime localDateTime1) {
                clear();

                AtomicInteger atomicInteger = new AtomicInteger(1);
                meetings.forEach(meeting -> {
                    DefaultScheduleEvent event = new DefaultScheduleEvent();
                    event.setId(atomicInteger.getAndIncrement() + "");
                    event.setStyleClass("event-title");
                    event.setTitle(meeting.getFriendlyPresentation());
                    event.setStartDate(meeting.getMeeting().getMeetingDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
                    event.setEndDate(meeting.getMeeting().getMeetingDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
                    addEvent(event);
                });
            }
        };
    }

    public ScheduleModel getLazyEventModel() {
        return lazyEventModel;
    }
}