package ro.uaic.info.l7.beans;


import ro.uaic.info.l7.services.AuthService;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named("loginBean")
@SessionScoped
public class LoginBean implements Serializable {
    @Inject
    private AuthService authService;
    @Inject
    private UserSessionBean userSessionBean;
    private String username;
    private String password;

    public String login() {
        if(authService.successfullyLogin(username, password)) {
            userSessionBean.setLoggedInUsername(username);
            if(userSessionBean.isAdmin()) {
                return "admin-home";
            } else if (userSessionBean.isTeacher()) {
                return "teacher-home";
            }
            return "welcome";
        }
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Incorrect Username or Password", null));
        return "failure";
    }

    public String register() {
        return "register";
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}