package org.acme.cqrs.aggregates;

import org.acme.cqrs.commands.DeleteUserCommand;
import org.acme.cqrs.commands.UpdateUserPasswordCommand;
import org.acme.cqrs.commands.UpdateUserUsernameCommand;

import org.acme.models.User;
import org.acme.cqrs.commands.CreateUserCommand;
import org.acme.repositories.UserRepository;

import java.util.Optional;

public class UserAggregate {
    private final UserRepository userRepository;

    public UserAggregate(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User handleCreateUserCommand(CreateUserCommand command) {
        Optional<User> userOptional = userRepository.findByUsername(command.getUsername());
        if(userOptional.isPresent()) {
            throw new IllegalArgumentException("Username is not available");
        }

        User user = new User(command.getUsername(), command.getPassword());
        userRepository.persist(user);
        return user;
    }

    public User handleDeleteUserCommand(DeleteUserCommand command) {
        User user = userRepository.findByIdOptional(command.getUserId()).orElse(null);
        if(user == null) {
            throw new IllegalArgumentException("User not found");
        }
        userRepository.delete(user);
        return user;
    }

    public User handleUpdateUserPasswordCommand(UpdateUserPasswordCommand command) {
        User user = userRepository.findByIdOptional(command.getUserId()).orElse(null);
        if(user == null) {
            throw new IllegalArgumentException("User not found");
        }
        user.setPassword(command.getNewPassword());
        return user;
    }

    public User handleUpdateUserUsernameCommand(UpdateUserUsernameCommand command) {
        Optional<User> userOptional = userRepository.findByUsername(command.getNewUsername());
        if(userOptional.isPresent()) {
            throw new IllegalArgumentException("Username is not available");
        }

        User user = userRepository.findById(command.getUserId());
        if(user == null) {
            throw new IllegalArgumentException("User not found");
        }
        user.setUsername(command.getNewUsername());
        return user;
    }

    /*

        private final UserRepository userRepository;
    private final UserEventRepository userEventRepository;

    public UserAggregate(UserRepository userRepository, UserEventRepository userEventRepository) {
        this.userRepository = userRepository;
        this.userEventRepository = userEventRepository;
    }

    public Event handleCreateUserCommand(CreateUserCommand command) {
        Optional<User> userOptional = userRepository.findByUsername(command.getUsername());
        if(userOptional.isPresent()) {
            throw new IllegalArgumentException("Username is not available");
        }

        User user = new User(command.getUsername(), command.getUsername());
        userRepository.persist(user);

        UserCreatedEvent event = new UserCreatedEvent(user.getId(), command.getUsername(), command.getPassword());
        userEventRepository.save(user.getId(), event);
        return event;
    }

    public Event handleDeleteUserCommand(DeleteUserCommand command) {
        User user = userRepository.findByIdOptional(command.getUserId()).orElse(null);
        if(user == null) {
            throw new IllegalArgumentException("User not found");
        }

        userRepository.delete(user);
        UserDeletedEvent userDeletedEvent = new UserDeletedEvent(user.getUsername());
        userEventRepository.save(command.getUserId(), userDeletedEvent);
        return userDeletedEvent;
    }

    public Event handleUpdateUserPasswordCommand(UpdateUserPasswordCommand command) {
        User user = userRepository.findByIdOptional(command.getUserId()).orElse(null);
        if(user == null) {
            throw new IllegalArgumentException("User not found");
        }
        user.setPassword(command.getNewPassword());

        UserSetPasswordEvent userSetPasswordEvent = new UserSetPasswordEvent(user.getUsername(), command.getNewPassword());
        userEventRepository.save(command.getUserId(), userSetPasswordEvent);
        return userSetPasswordEvent;
    }

    public Event handleUpdateUserUsernameCommand(UpdateUserUsernameCommand command) {
        Optional<User> userOptional = userRepository.findByUsername(command.getNewUsername());
        if(userOptional.isPresent()) {
            throw new IllegalArgumentException("Username is not available");
        }

        User user = userRepository.findById(command.getUserId());
        if(user == null) {
            throw new IllegalArgumentException("User not found");
        }

        UserSetUsernameEvent userSetUsernameEvent = new UserSetUsernameEvent(user.getUsername(), command.getNewUsername());
        userEventRepository.save(command.getUserId(), userSetUsernameEvent);
        user.setUsername(command.getNewUsername());
        return userSetUsernameEvent;
    }


     */
}
