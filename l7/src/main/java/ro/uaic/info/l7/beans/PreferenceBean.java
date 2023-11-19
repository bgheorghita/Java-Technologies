package ro.uaic.info.l7.beans;

import ro.uaic.info.l7.entities.Preference;
import ro.uaic.info.l7.entities.User;
import ro.uaic.info.l7.interfaces.Logged;
import ro.uaic.info.l7.services.PreferenceService;
import ro.uaic.info.l7.services.UserService;
import ro.uaic.info.l7.utils.NumberGenerator;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named("preferenceBean")
@SessionScoped
public class PreferenceBean implements Serializable {
    @Inject
    private UserSessionBean userSessionBean;

    private String content;
    @Inject
    private PreferenceService preferenceService;
    @Inject
    private UserService userService;
    @Inject
    private NumberGenerator numberGenerator;

    @Logged
    public void submitPreference() {
        Preference preference = new Preference();
        User user = userService.findByUsername(userSessionBean.readUsername());
        preference.setUser(user);
        preference.setContent(content);
        preference.setRegistrationNumber(numberGenerator.getRandomUUID().toString());
        preferenceService.save(preference);
    }

    public List<Preference> getPreferences() {
        return preferenceService.findAll();
    }

    public List<Preference> getPreferencesOfLoggedUser() {
        return preferenceService.findByUsername(userSessionBean.readUsername());
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
