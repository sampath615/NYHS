package gov.ny.state.doh.bhae.nyehms.web.spring;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import gov.ny.state.doh.bhae.nyehms.web.spring.config.LegacyEnvironmentBridge;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.EnvironmentProperties;

@SpringBootApplication
public class NyehmsApplication extends SpringBootServletInitializer {

    /** Old way of choosing the environment: a one-word file on each server (LOCAL/DEV/EVAL/PROD). */
    private static final Path STAGE_FILE = Path.of("/usr/local/bin/stage");

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {

         application.sources(NyehmsApplication.class).listeners(new LegacyEnvironmentBridge());

        // Standard Spring Boot: the server sets SPRING_PROFILES_ACTIVE (or -Dspring.profiles.active).
        // Until every server does, fall back to the stage file, so no server change is needed yet.
        if (System.getenv("SPRING_PROFILES_ACTIVE") == null && System.getProperty("spring.profiles.active") == null) {
            application.profiles(readStageFile());
        }
        return application;
    }

    /** After Spring has started: values every JSP can read, the same for all users. */
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        super.onStartup(servletContext);
        servletContext.setAttribute("contextPath", servletContext.getContextPath());
        // cache-busting version on static JS/CSS URLs (?v=${buildDate})
        servletContext.setAttribute("buildDate", EnvironmentProperties.envProperties.getProperty("installationDate"));
    }


    private static String readStageFile() {
        try {
            List<String> lines = Files.readAllLines(STAGE_FILE);
            if (!lines.isEmpty() && !lines.get(0).isBlank()) {
                return lines.get(0).trim().toLowerCase();   // "DEV" -> profile "dev"
            }
        } catch (IOException e) {
            // file missing or unreadable: same default as before
        }
        return "prod";
    }
}
