package org.acme.cqrs.projections;

import org.acme.cqrs.queries.UserByUsernameQuery;
import org.acme.models.User;
import org.acme.repositories.UserRepository;

import java.util.Optional;

public class UserProjection {
    private final UserRepository userRepository;

    public UserProjection(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> handle(UserByUsernameQuery query) {
        return userRepository.findByUsername(query.getUsername());
    }

//    private final UserRepository userRepository;
//
//    public UserProjector(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }
//
//    public void project(Long userId, List<Event> events) {
//        for (Event event : events) {
//            if (event instanceof UserCreatedEvent)
//                apply(userId, (UserCreatedEvent) event);
//            if (event instanceof UserDeletedEvent)
//                apply(userId, (UserDeletedEvent) event);
//            if (event instanceof UserSetPasswordEvent)
//                apply(userId, (UserSetPasswordEvent) event);
//            if (event instanceof UserSetUsernameEvent)
//                apply(userId, (UserSetUsernameEvent) event);
//        }
//    }
//
//    public void apply(Long userId, UserCreatedEvent event) {
//
//    }
//
//    public void apply(Long userId, UserDeletedEvent event) {
//
//    }
//
//    public void apply(Long userId, UserSetPasswordEvent event) {
//
//    }
//
//    public void apply(Long userId, UserSetUsernameEvent event) {
//
//    }
}
