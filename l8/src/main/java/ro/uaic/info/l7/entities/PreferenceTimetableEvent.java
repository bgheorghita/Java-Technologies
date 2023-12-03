package ro.uaic.info.l7.entities;

import ro.uaic.info.l7.entities.base.IdentifiableEntity;

import javax.persistence.*;

@Entity
@Table(
    name = "preferences_events",
    uniqueConstraints = @UniqueConstraint(columnNames = {"preference_id", "event_id", "priority"})
)
@NamedQueries({
        @NamedQuery(name = "PreferenceTimetableEvent.findByPreferenceId", query = "SELECT p FROM PreferenceTimetableEvent p WHERE p.preference.id = ?1"),
        @NamedQuery(name = "PreferenceTimetableEvent.findByUserId", query = "SELECT p FROM PreferenceTimetableEvent p WHERE p.preference.user.id = ?1"),
        @NamedQuery(name = "PreferenceTimetableEvent.findAll", query = "SELECT p FROM PreferenceTimetableEvent p")
})
public class PreferenceTimetableEvent extends IdentifiableEntity {
    @ManyToOne
    @JoinColumn(name = "preference_id", nullable = false)
    private Preference preference;

    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
    private TimetableEvent timetableEvent;

    @Column(name = "priority", nullable = false)
    private Integer priority;

    public PreferenceTimetableEvent(Preference preference, TimetableEvent timetableEvent, Integer priority) {
        this.preference = preference;
        this.timetableEvent = timetableEvent;
        this.priority = priority;
    }

    public PreferenceTimetableEvent() {
    }

    public Preference getPreference() {
        return preference;
    }

    public void setPreference(Preference preference) {
        this.preference = preference;
    }

    public TimetableEvent getTimetableEvent() {
        return timetableEvent;
    }

    public void setTimetableEvent(TimetableEvent timetableEvent) {
        this.timetableEvent = timetableEvent;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }
}