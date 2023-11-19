package ro.uaic.info.l7.beans;

import ro.uaic.info.l7.services.UserService;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named("userSessionBean")
@SessionScoped
public class UserSessionBean implements Serializable {
    private final static String SESSION_NOT_DEFINED_EXC = "User session is not defined.";
    @Inject
    private UserService userService;
    private String username;

    public String readUsername() {
        checkSessionIsDefined();
        return username;
    }

    private void checkSessionIsDefined() {
        if(username == null) {
            throw new RuntimeException(SESSION_NOT_DEFINED_EXC);
        }
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isTeacher() {
        checkSessionIsDefined();
        return userService.hasRoleName(username, "ROLE_TEACHER");
    }

    public boolean isAdmin() {
        checkSessionIsDefined();
        return userService.hasRoleName(username, "ROLE_ADMIN");
    }

    public boolean hasAnyRole() {
        checkSessionIsDefined();
        return userService.hasAnyRole(username);
    }
}
