package org.acme.cqrs.commands;

public class DeleteUserCommand {
    private final Long userId;

    public DeleteUserCommand(Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }
}
