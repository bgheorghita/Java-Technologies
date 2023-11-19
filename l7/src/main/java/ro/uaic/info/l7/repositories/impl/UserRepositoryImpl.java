package ro.uaic.info.l7.repositories.impl;

import ro.uaic.info.l7.entities.Role;
import ro.uaic.info.l7.entities.User;
import ro.uaic.info.l7.repositories.UserRepository;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@Stateless
public class UserRepositoryImpl implements UserRepository, Serializable {
    @PersistenceContext(unitName = "MyWebApplicationPU")
    private EntityManager entityManager;

    @Override
    public Optional<User> findByUsername(String username) {
        TypedQuery<User> query = entityManager.createNamedQuery("User.findByUsername", User.class);
        query.setParameter(1, username);

        try {
            return Optional.of(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public User save(User user) {
        entityManager.persist(user);
        entityManager.flush();
        entityManager.refresh(user);
        return user;
    }
}
