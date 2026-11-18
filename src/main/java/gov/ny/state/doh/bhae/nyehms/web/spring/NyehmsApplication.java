package gov.ny.state.doh.bhae.nyehms.web.spring;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Set;

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
    /** Server stage file: one word LOCAL / DEV / EVAL / PROD.
     *  On Windows "/usr/..." resolves to the current drive, e.g. C:/usr/local/bin/stage. */
    private static final Path STAGE_FILE = Path.of("/usr/local/bin/stage");

    private static final Set<String> KNOWN_PROFILES = Set.of("local", "dev", "eval", "prod");

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {

        // Must run before Spring reads application-{profile}.yml
        String stageProfile = readStageProfile();
        if (stageProfile != null) {
            application.profiles(stageProfile);
        }
        return application.sources(NyehmsApplication.class)
                .listeners(new RequireActiveProfile());

    }
    /**
     * Reads the stage file and returns the matching Spring profile, or null.
     * Uses System.out because logging is not configured yet at this point.
     */
    static String readStageProfile() {
        if(!Files.isReadable(STAGE_FILE)){
            System.out.println("NYEHMS: stage file not found at " + STAGE_FILE.toAbsolutePath());
            return null;
        }
        try{
            String stage = Files.readString(STAGE_FILE).trim().toLowerCase(Locale.ROOT);
            System.out.println("NYEHMS: stage file says [" + stage + "]");

            if (!KNOWN_PROFILES.contains(stage)) {
                System.out.println("NYEHMS: unknown stage [" + stage + "], expected one of " + KNOWN_PROFILES);
                return null;

            }
            return stage;
        } catch (IOException e) {
            System.out.println("NYEHMS: could not read stage file: " + e.getMessage());
            return null;
        }
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
     * Stops startup if no profile could be determined.
     */
    static class RequireActiveProfile
            implements ApplicationListener<ApplicationEnvironmentPreparedEvent> {

        @Override
        public void onApplicationEvent(ApplicationEnvironmentPreparedEvent event) {
            if (event.getEnvironment().getActiveProfiles().length == 0) {
                throw new IllegalStateException("No Spring profile set. Expected " + STAGE_FILE.toAbsolutePath() + " containing LOCAL, DEV, EVAL or PROD.");
            }
        }
    }
}
