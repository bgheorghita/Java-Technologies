package org.acme.services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.acme.models.UserSubmission;
import org.acme.repositories.UserSubmissionRepository;

@ApplicationScoped
public class UserSubmissionServiceImpl implements UserSubmissionService {
    @Inject
    UserSubmissionRepository userSubmissionRepository;

    @Override
    @Transactional
    public void incrementCounter(String username) {
        UserSubmission userSubmission = userSubmissionRepository.findByUsername(username).orElse(null);

        if(userSubmission == null) {
            userSubmissionRepository.persist(new UserSubmission(username, 1));
        } else {
            userSubmissionRepository.update("counter = ?1 where id = ?2", userSubmission.getCounter() + 1, userSubmission.getId());
        }
    }
}
