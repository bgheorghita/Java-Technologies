package ro.uaic.info.l7.decorators;

import ro.uaic.info.l7.entities.Preference;
import ro.uaic.info.l7.services.PreferenceService;
import ro.uaic.info.l7.utils.MessageExtractor;

import javax.decorator.Decorator;
import javax.decorator.Delegate;
import javax.ejb.EJBTransactionRolledbackException;
import javax.enterprise.event.Event;
import javax.enterprise.inject.Any;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Decorator
public abstract class PreferenceServiceDecorator implements PreferenceService {
    @Inject // is used to inject the original PreferenceService bean
    @Delegate // this field will hold the reference to the original bean
    @Any // any eligible bean of type PreferenceService can be injected
    private PreferenceService preferenceService;

    @Inject
    @Any
    private Event<Preference> preferenceEvent;
    public Preference saveOrUpdate(Preference preference) {
        if (!isWithinTimeFrame()) {
            String msg = "Preference submission is not allowed outside the specified time frame. (08:00 - 19:30)";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
            return null;
        }
        try {
            Preference savedPreference = preferenceService.saveOrUpdate(preference);
            preferenceEvent.fire(savedPreference);
            return savedPreference;
        } catch (EJBTransactionRolledbackException e) {
            String msg = MessageExtractor.handleValidationException(e);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
        } catch (RuntimeException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Unexpected error", null));
        }
        return null;
    }

    private boolean isWithinTimeFrame() {
        // submissions allowed only between 8 AM and 7 PM
        LocalTime startTime = LocalTime.of(7, 0);
        LocalTime endTime = LocalTime.of(23, 59);

        LocalDateTime currentTime = LocalDateTime.now();
        LocalTime currentLocalTime = currentTime.toLocalTime();

        return currentLocalTime.isAfter(startTime) && currentLocalTime.isBefore(endTime);
    }
}