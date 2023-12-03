package ro.uaic.info.l7.services;

import ro.uaic.info.l7.entities.User;

public interface AuthService {
    boolean successfullyLogin(String username, String password);
    User register(String username, String password);
}
