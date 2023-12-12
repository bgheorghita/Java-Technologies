package ro.uaic.info.l7.repositories.impl;

import ro.uaic.info.l7.entities.TimetableEvent;
import ro.uaic.info.l7.repositories.TimetableEventRepository;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Stateless
public class TimetableEventRepositoryImpl implements TimetableEventRepository {
    @PersistenceContext(unitName = "MyWebApplicationPU")
    private EntityManager entityManager;

    @Override
    public List<TimetableEvent> findAll() {
        TypedQuery<TimetableEvent> query = entityManager.createNamedQuery("TimetableEvent.findAll", TimetableEvent.class);
        return query.getResultList();
    }
}
