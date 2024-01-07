package org.acme.cqrs.queries;

public class UserByUsernameQuery {
    private final String username;

    public UserByUsernameQuery(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
