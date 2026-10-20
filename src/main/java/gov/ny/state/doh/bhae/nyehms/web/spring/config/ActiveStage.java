package gov.ny.state.doh.bhae.nyehms.web.spring.config;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * The environment this server runs as: LOCAL, DEV, EVAL or PROD, from spring.profiles.active.
 * Replaces System.getProperty("stage"), which LegacyEnvironmentBridge used to set.
 * Some values are matched against the database (e.g. userStage for the HCS web-service settings),
 * so the name stays upper case.
 */
@Component
public class ActiveStage {

    private final String name;

    public ActiveStage(Environment environment) {
        String[] profiles = environment.getActiveProfiles();
        this.name = profiles.length > 0 ? profiles[0].toUpperCase() : "PROD";
    }

    public String name() {
        return name;
    }

    public boolean isLocal() {
        return "LOCAL".equals(name);
    }

    @Override
    public String toString() {
        return name;
    }
}
