package org.acme.es.events;

public class UserSetUsernameEvent extends Event {
    private final String username;
    private final String newUsername;

    public UserSetUsernameEvent(String username, String newUsername) {
        this.username = username;
        this.newUsername = newUsername;
    }

    public String getUsername() {
        return username;
    }

    public String getNewUsername() {
        return newUsername;
    }
}
