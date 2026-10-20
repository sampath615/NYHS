package gov.ny.state.doh.bhae.nyehms.web.spring.config;

import java.util.Properties;

import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.EnumerablePropertySource;
import org.springframework.core.env.PropertySource;

import gov.ny.state.doh.bhae.nyehms.web.spring.util.EnvironmentProperties;

/**
 * Temporary bridge while the code still reads settings from the static
 * {@link EnvironmentProperties#envProperties} map (152 places).
 *
 * Spring Boot now loads application.yml + application-{profile}.yml. Right after that,
 * before any bean is created, this copies every setting from those files into the old map,
 * so existing code keeps working unchanged.
 *
 * Delete this class (and EnvironmentProperties) once all callers use @Value / @ConfigurationProperties.
 */
public class LegacyEnvironmentBridge implements ApplicationListener<ApplicationEnvironmentPreparedEvent> {

    @Override
    public void onApplicationEvent(ApplicationEnvironmentPreparedEvent event) {
        ConfigurableEnvironment env = event.getEnvironment();
        Properties legacy = EnvironmentProperties.envProperties;

        for (PropertySource<?> source : env.getPropertySources()) {
            // only our application*.yml files ("Config resource 'class path resource [application-dev.yml]' ...")
            if (source instanceof EnumerablePropertySource<?> files && source.getName().startsWith("Config resource")) {
                for (String name : files.getPropertyNames()) {
                    String value = env.getProperty(name);   // effective value: profile file wins over application.yml
                    if (value != null) {
                        legacy.setProperty(name, value);
                    }
                }
            }
        }

        // Same extra the old listener set
        String[] profiles = env.getActiveProfiles();
        System.setProperty("stage", profiles.length > 0 ? profiles[0].toUpperCase() : "PROD");
    }
}
