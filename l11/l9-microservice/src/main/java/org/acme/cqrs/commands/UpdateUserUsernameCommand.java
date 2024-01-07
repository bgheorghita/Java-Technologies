package org.acme.cqrs.commands;

public class UpdateUserUsernameCommand {
    private final Long userId;
    private final String newUsername;

    public UpdateUserUsernameCommand(Long userId, String newUsername) {
        this.userId = userId;
        this.newUsername = newUsername;
    }

    public Long getUserId() {
        return userId;
    }

    public String getNewUsername() {
        return newUsername;
    }
}
