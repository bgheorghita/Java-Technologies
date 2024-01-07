package org.acme.es.events;

public class UserSetPasswordEvent extends Event {
    private final String username;
    private final String newPassword;

    public UserSetPasswordEvent(String username, String newPassword) {
        this.username = username;
        this.newPassword = newPassword;
    }

    public String getUsername() {
        return username;
    }

    public String getNewPassword() {
        return newPassword;
    }
}