package ro.uaic.info.l7.beans;

import ro.uaic.info.l7.services.AuthService;
import ro.uaic.info.l7.utils.MessageExtractor;

import javax.ejb.EJBTransactionRolledbackException;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named("registerBean")
@SessionScoped
public class RegisterBean implements Serializable {
    @Inject
    private AuthService authService;
    @Inject
    private UserSessionBean userSessionBean;
    private String username;
    private String password;

    public String register() {
        try {
            authService.register(username, password);
        } catch (EJBTransactionRolledbackException e) {
            String msg = MessageExtractor.handleValidationException(e);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
        } catch (RuntimeException e) {
            String msg = MessageExtractor.getMessageFromException(e);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
            return "failure";
        }

        userSessionBean.setLoggedInUsername(username);
        return "welcome";
    }

    public String login() {
        return "login";
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
