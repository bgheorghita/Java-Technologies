package ro.uaic.info.l7.beans;

import ro.uaic.info.l7.services.UserService;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named("userSessionBean")
@SessionScoped
public class UserSessionBean implements Serializable {
    @Inject
    private UserService userService;
    private String loggedInUsername;

    public String getLoggedInUsername() {
        if(loggedInUsername == null) {
            throw new RuntimeException("NO SESSION");
        }
        return loggedInUsername;
    }

    public void setLoggedInUsername(String loggedInUsername) {
        this.loggedInUsername = loggedInUsername;
    }

    public boolean isTeacher() {
        return userService.hasRoleName(getLoggedInUsername(), "ROLE_TEACHER");
    }

    public boolean isAdmin() {
        return userService.hasRoleName(getLoggedInUsername(), "ROLE_ADMIN");
    }

    public boolean hasAnyRole() {
        return userService.hasAnyRole(getLoggedInUsername());
    }

    public String logout() {
        loggedInUsername = null;
        return "login";
    }
}
