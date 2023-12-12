package ro.uaic.info.l7.beans;

import ro.uaic.info.l7.services.UserService;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.Serializable;
import java.security.Principal;

@Named("userSessionBean")
@SessionScoped
public class UserSessionBean implements Serializable {
    @Inject
    private UserService userService;
    private String loggedInUsername;

    public String getLoggedInUsername() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext().getRequest();
        Principal principal = request.getUserPrincipal();
        if(principal == null) {
            throw new RuntimeException("NO SESSION");
        }
        return principal.getName();
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

    public void logout() throws IOException {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext().getRequest();
        HttpSession session = request.getSession(false);

        if (session != null) {
            session.invalidate();
        }

        loggedInUsername = null;
        facesContext.getExternalContext().redirect(request.getContextPath() + "/faces/welcome.xhtml");
    }
}
