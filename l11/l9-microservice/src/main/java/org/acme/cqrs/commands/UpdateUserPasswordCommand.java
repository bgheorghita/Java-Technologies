package org.acme.cqrs.commands;

public class UpdateUserPasswordCommand {
    private final Long userId;
    private final String newPassword;

    public UpdateUserPasswordCommand(Long userId, String newPassword) {
        this.userId = userId;
        this.newPassword = newPassword;
    }

    public Long getUserId() {
        return userId;
    }

    public String getNewPassword() {
        return newPassword;
    }
}
