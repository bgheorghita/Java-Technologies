package ro.uaic.info.l7.services.impl;

import ro.uaic.info.l7.entities.Role;
import ro.uaic.info.l7.entities.User;
import ro.uaic.info.l7.repositories.UserRepository;
import ro.uaic.info.l7.services.UserService;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.Optional;

@Stateless
public class UserServiceImpl implements UserService, Serializable {
    @Inject
    private UserRepository userRepository;

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
   // @Transactional
    public boolean hasRoleName(String username, String roleName) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        if(userOptional.isEmpty()) {
            return false;
        }

        User user = userOptional.get();
        //user.getRoles().size(); // lazily initialization
        return user.getRoles().stream().map(Role::getName).anyMatch(roleName::equals);
    }

    @Override
    public boolean hasAnyRole(String username) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        if(userOptional.isEmpty()) {
            return false;
        }
        User user = userOptional.get();
        return user.getRoles().size() > 0;
    }
}
