package ro.uaic.info.l7.repositories;

import ro.uaic.info.l7.entities.User;

import java.util.Optional;

public interface UserRepository {
    Optional<User> findByUsername(String username);
    User save(User user);
}
