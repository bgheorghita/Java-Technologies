package ro.uaic.info.l7.repositories.impl;

import ro.uaic.info.l7.entities.Preference;
import ro.uaic.info.l7.repositories.PreferenceRepository;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Stateless
public class PreferenceRepositoryImpl implements PreferenceRepository {
    @PersistenceContext(unitName = "MyWebApplicationPU")
    private EntityManager entityManager;

    @Override
    public Preference saveOrUpdate(Preference preference) {
        if (preference.isNew()) {
            // save
            entityManager.persist(preference);
        } else {
            // update
            preference = entityManager.merge(preference);
        }
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

    @Override
    public Optional<Preference> findById(Long id) {
        TypedQuery<Preference> query = entityManager.createNamedQuery("Preference.findById", Preference.class);
        query.setParameter(1, id);
        try {
            Preference result = query.getSingleResult();
            return Optional.of(result);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Preference> deleteById(Long id) {
        Optional<Preference> preferenceOptional = findById(id);
        if(preferenceOptional.isPresent()) {
            entityManager.remove(preferenceOptional.get());
            return preferenceOptional;
        }
        return Optional.empty();
    }

    @Override
    public Optional<Preference> findByRegistrationNumber(String registrationNumber) {
        TypedQuery<Preference> query = entityManager.createNamedQuery("Preference.findByRegistrationNumber", Preference.class);
        query.setParameter(1, registrationNumber);
        try {
            Preference result = query.getSingleResult();
            return Optional.of(result);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }
}
