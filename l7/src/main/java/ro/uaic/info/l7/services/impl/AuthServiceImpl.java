package ro.uaic.info.l7.services.impl;

import ro.uaic.info.l7.services.PasswordEncoder;
import ro.uaic.info.l7.entities.User;
import ro.uaic.info.l7.repositories.UserRepository;
import ro.uaic.info.l7.services.AuthService;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@Stateless
public class AuthServiceImpl implements AuthService, Serializable {
    @Inject
    private UserRepository userRepository;
    @Inject
    private PasswordEncoder passwordEncoder;

    @Override
    public boolean successfullyLogin(String username, String password) {
        Optional<User> user = userRepository.findByUsername(username);
        if(user.isEmpty()) {
            return false;
        }

        User retrievedUser = user.get();
        return passwordEncoder.verify(password, retrievedUser.getPassword());
    }

    @Override
    public User register(String username, String password) {
        if(!validatePassword(password)) {
            throw new RuntimeException("Password must contain one digit, one letter, and be minimum 5 characters long!");
        }

        Optional<User> user = userRepository.findByUsername(username);
        if(user.isPresent()) {
            throw new RuntimeException("Username not available!");
        }

        String encodedPassword = passwordEncoder.encode(password, BCryptPasswordEncoder.COST_12);
        User newUser = new User(username, encodedPassword, List.of());
        return userRepository.save(newUser);
    }

    private boolean validatePassword(String password) {
        // Password must be at least 5 characters long
        // Password must contain at least one letter and one digit.
        return password.length() >= 5 && password.matches("(?=.*[A-Za-z])(?=.*\\d).+");
    }
}
