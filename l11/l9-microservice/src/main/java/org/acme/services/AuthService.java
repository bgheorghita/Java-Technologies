package org.acme.services;

import org.acme.models.Token;

public interface AuthService {
    Token register(String username, String password);
    Token login(String username, String password);
}
