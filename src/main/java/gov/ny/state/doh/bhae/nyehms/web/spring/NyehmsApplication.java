package gov.ny.state.doh.bhae.nyehms.web.spring;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationListener;

@SpringBootApplication
@ConfigurationPropertiesScan
public class NyehmsApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {

        // Profile comes from the server: -Dspring.profiles.active=local|dev|eval|prod
        return application.sources(NyehmsApplication.class)
                .listeners(new RequireActiveProfile());

    }

    /**
     * After Spring has started: values every JSP can read.
     */
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        super.onStartup(servletContext);
        servletContext.setAttribute("contextPath", servletContext.getContextPath());
        // cache-busting version on static JS/CSS URLs (?v=${buildDate})
        // (startup date, so every redeploy gets fresh URLs)
        servletContext.setAttribute("buildDate",
                LocalDate.now().format(DateTimeFormatter.ofPattern("MM/dd/yyyy")));
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
