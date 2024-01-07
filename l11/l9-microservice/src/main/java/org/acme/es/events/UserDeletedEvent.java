package org.acme.es.events;

public class UserDeletedEvent extends Event {
    private final String username;

    public UserDeletedEvent(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
