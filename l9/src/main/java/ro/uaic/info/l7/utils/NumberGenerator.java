package ro.uaic.info.l7.utils;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import java.util.UUID;

@ApplicationScoped
public class NumberGenerator {
    @Produces
    public UUID getRandomUUID() {
        return UUID.randomUUID();
    }
}
