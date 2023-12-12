package ro.uaic.info.l7.repositories.impl;

import ro.uaic.info.l7.entities.PreferenceTimetableEvent;
import ro.uaic.info.l7.repositories.PreferenceTimetableEventRepository;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Stateless
public class PreferenceTimetableEventRepositoryImpl implements PreferenceTimetableEventRepository {
    @PersistenceContext(unitName = "MyWebApplicationPU")
    private EntityManager entityManager;

    @Override
    public PreferenceTimetableEvent save(PreferenceTimetableEvent preferenceTimetableEvent) {
        entityManager.persist(preferenceTimetableEvent);
        entityManager.flush();
        entityManager.refresh(preferenceTimetableEvent);
        return preferenceTimetableEvent;
    }

    @Override
    public Optional<PreferenceTimetableEvent> findById(Long id) {
        return Optional.ofNullable(entityManager.find(PreferenceTimetableEvent.class, id));
    }

    @Override
    public List<PreferenceTimetableEvent> findByPreferenceId(Long preferenceId) {
        TypedQuery<PreferenceTimetableEvent> query = entityManager.createNamedQuery("PreferenceTimetableEvent.findByPreferenceId", PreferenceTimetableEvent.class);
        query.setParameter(1, preferenceId);
        return query.getResultList();
    }

    @Override
    public List<PreferenceTimetableEvent> findByUserId(Long userId) {
        TypedQuery<PreferenceTimetableEvent> query = entityManager.createNamedQuery("PreferenceTimetableEvent.findByUserId", PreferenceTimetableEvent.class);
        query.setParameter(1, userId);
        return query.getResultList();
    }

    @Override
    public List<PreferenceTimetableEvent> findAll() {
        TypedQuery<PreferenceTimetableEvent> query = entityManager.createNamedQuery("PreferenceTimetableEvent.findAll", PreferenceTimetableEvent.class);
        return query.getResultList();
    }
}
