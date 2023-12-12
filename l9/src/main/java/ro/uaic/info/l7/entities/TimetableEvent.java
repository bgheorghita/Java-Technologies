package ro.uaic.info.l7.entities;

import ro.uaic.info.l7.entities.base.NamedEntity;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "events")
@NamedQueries(
        @NamedQuery(name = "TimetableEvent.findAll", query = "SELECT e FROM TimetableEvent e")
)
public class TimetableEvent extends NamedEntity {

    public TimetableEvent(String name) {
        super(name);
    }

    public TimetableEvent() {
    }

    @Override
    public String toString() {
        return name;
    }
}
