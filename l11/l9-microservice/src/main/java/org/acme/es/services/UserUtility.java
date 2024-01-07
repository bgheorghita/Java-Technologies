package org.acme.es.services;

import org.acme.es.events.Event;
import org.acme.es.events.UserCreatedEvent;
import org.acme.es.events.UserSetPasswordEvent;
import org.acme.es.events.UserSetUsernameEvent;
import org.acme.models.User;
import org.acme.es.repositories.UserEventRepository;

import java.util.List;

public class UserUtility {

    public static User recreateUserState(Long userId, UserEventRepository userEventRepository) {
        User user = null;
        List<Event> events = userEventRepository.getEventsByUserId(userId);

        for (Event event : events) {
            if (event instanceof UserCreatedEvent userCreatedEvent) {
                user = new User(userCreatedEvent.getUserId(), userCreatedEvent.getUsername(), userCreatedEvent.getPassword());
            }

            if (event instanceof UserSetPasswordEvent userSetPasswordEvent) {
                if (user != null) {
                    user.setPassword(userSetPasswordEvent.getNewPassword());
                }
            }

            if (event instanceof UserSetUsernameEvent userSetUsernameEvent) {
                if (user != null) {
                    user.setUsername(userSetUsernameEvent.getNewUsername());
                }
            }
        }

        return user;
    }

}
