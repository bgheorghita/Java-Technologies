package ro.uaic.info.l7.repositories.impl;

import ro.uaic.info.l7.entities.Preference;
import ro.uaic.info.l7.repositories.PreferenceRepository;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Stateless
public class PreferenceRepositoryImpl implements PreferenceRepository {
    @PersistenceContext(unitName = "MyWebApplicationPU")
    private EntityManager entityManager;

    @Override
    public Preference save(Preference preference) {
        entityManager.persist(preference);
        entityManager.flush();
        entityManager.refresh(preference);
        return preference;
    }

    @Override
    public List<Preference> findByUserId(Long id) {
        TypedQuery<Preference> query = entityManager.createNamedQuery("Preference.findByUserId", Preference.class);
        query.setParameter(1, id);
        return query.getResultList();
    }

    @Override
    public List<Preference> findByUsername(String username) {
        TypedQuery<Preference> query = entityManager.createNamedQuery("Preference.findByUsername", Preference.class);
        query.setParameter(1, username);
        return query.getResultList();
    }

    @Override
    public List<Preference> findAll() {
        TypedQuery<Preference> query = entityManager.createNamedQuery("Preference.findAll", Preference.class);
        return query.getResultList();
    }
}
