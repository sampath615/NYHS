package gov.ny.state.doh.bhae.nyehms.web.spring;

import gov.ny.state.doh.bhae.nyehms.web.spring.config.LegacyEnvironmentBridge;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.EnvironmentProperties;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationListener;

@SpringBootApplication
public class NyehmsApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {

        // Profile comes from the server: -Dspring.profiles.active=local|dev|eval|prod
        return application.sources(NyehmsApplication.class)
                .listeners(new LegacyEnvironmentBridge(), new RequireActiveProfile());

    }

    /**
     * After Spring has started: values every JSP can read.
     */
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        super.onStartup(servletContext);
        servletContext.setAttribute("contextPath", servletContext.getContextPath());
        // cache-busting version on static JS/CSS URLs (?v=${buildDate})
        servletContext.setAttribute("buildDate",
                EnvironmentProperties.envProperties.getProperty("installationDate"));
    }

    /**
     * Stops startup if the server didn't set a profile.
     */
    static class RequireActiveProfile
            implements ApplicationListener<ApplicationEnvironmentPreparedEvent> {

        @Override
        public void onApplicationEvent(ApplicationEnvironmentPreparedEvent event) {
            if (event.getEnvironment().getActiveProfiles().length == 0) {
                throw new IllegalStateException("No Spring profile set. Add "
                        + "-Dspring.profiles.active=local|dev|eval|prod to this Tomcat's JVM options.");
            }
        }
    }
}
