package org.acme.repositories;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.acme.models.UserSubmission;

import java.util.Optional;

@ApplicationScoped
public class UserSubmissionRepository implements PanacheRepository<UserSubmission> {
    public Optional<UserSubmission> findByUsername(String username) {
        return find("username", username).singleResultOptional();
    }
}
