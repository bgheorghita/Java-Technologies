package ro.uaic.info.l7.observes;

import ro.uaic.info.l7.entities.Preference;

import javax.enterprise.event.Observes;
import java.time.LocalDateTime;

public class PreferenceEventObserver {
    public void onPreferenceEvent(@Observes Preference preferenceEvent) {
        System.out.println("New preference saved at " + LocalDateTime.now() + ": " + preferenceEvent.toString());
    }
}
