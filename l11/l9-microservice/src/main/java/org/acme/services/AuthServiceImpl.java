package org.acme.services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.acme.models.Role;
import org.acme.models.Token;
import org.acme.models.User;
import org.acme.repositories.UserRepository;
import org.acme.security.TokenService;

import java.util.List;

@ApplicationScoped
public class AuthServiceImpl implements AuthService {
    @Inject
    UserRepository userRepository;
    @Inject
    TokenService tokenService;

    @Override
    public Token register(String username, String password) {
        User user = userRepository.findByUsername(username).orElse(null);
        if(user != null) {
            throw new IllegalArgumentException("Username is not available");
        }

        user = new User(username, password, List.of(new Role("USER")));
        userRepository.persist(user);

        return tokenService.generateUserToken(username);
    }

    @Override
    public Token login(String username, String password) {
        User user = userRepository.findByUsername(username).orElse(null);
        if(user == null) {
            throw new IllegalArgumentException("User not available");
        }

        if(password.equals(user.getPassword())) {
            return tokenService.generateUserToken(username);
        }

        throw new IllegalArgumentException("Bad Credentials");
    }
}
