package ro.uaic.info.l7.repositories;

import ro.uaic.info.l7.entities.TimetableEvent;

import java.util.List;

public interface TimetableEventRepository {
    List<TimetableEvent> findAll();
}
