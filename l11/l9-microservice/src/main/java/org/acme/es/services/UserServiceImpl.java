package org.acme.es.services;

import org.acme.es.events.UserCreatedEvent;
import org.acme.es.events.UserDeletedEvent;
import org.acme.es.events.UserSetPasswordEvent;
import org.acme.es.events.UserSetUsernameEvent;
import org.acme.models.User;
import org.acme.es.repositories.UserEventRepository;

public class UserServiceImpl implements UserService {
    private final UserEventRepository userEventRepository;

    public UserServiceImpl(UserEventRepository userEventRepository) {
        this.userEventRepository = userEventRepository;
    }

    public void createUser(Long userId, String username, String password) {
        userEventRepository.save(userId, new UserCreatedEvent(userId, username, password));
    }

    public void updateUsername(Long userId, String newUsername) {
        User recreatedUser = getRecreatedUserById(userId);
        UserSetUsernameEvent userSetUsernameEvent = new UserSetUsernameEvent(recreatedUser.getUsername(), newUsername);
        userEventRepository.save(userId, userSetUsernameEvent);
    }

    public void updateUserPassword(Long userId, String newPassword) {
        User recreatedUser = getRecreatedUserById(userId);
        UserSetPasswordEvent userSetPasswordEvent = new UserSetPasswordEvent(recreatedUser.getUsername(), newPassword);
        userEventRepository.save(userId, userSetPasswordEvent);
    }

    public void deleteUser(Long userId) {
        User recreatedUser = getRecreatedUserById(userId);
        UserDeletedEvent userDeletedEvent = new UserDeletedEvent(recreatedUser.getUsername());
        userEventRepository.save(userId, userDeletedEvent);
    }

    private User getRecreatedUserById(Long userId) {
        return UserUtility.recreateUserState(userId, userEventRepository);
    }
}