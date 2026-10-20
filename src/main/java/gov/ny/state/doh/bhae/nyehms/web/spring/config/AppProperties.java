package gov.ny.state.doh.bhae.nyehms.web.spring.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * General application settings, bound from nyehms.app.* in application.yml + application-{profile}.yml.
 * Replaces the loose top-level keys (loginPage, loggout_url, byPass, loadSchedulers, ...)
 * that used to be read through EnvironmentProperties.envProperties.
 */
@ConfigurationProperties(prefix = "nyehms.app")
public record AppProperties(
        /* view name shown when the user is not authenticated */
        String loginPage,
        /* where /logout redirects (HCS portal outside local) */
        String logoutUrl,
        /* local only: skip the HCS/crypto login and trust the ?user= parameter */
        boolean byPass,
        /* false on local so Quartz jobs don't run against shared databases */
        boolean loadSchedulers,
        /* local only: read scheduler metadata from properties instead of LAB_SCHEDULER_PARAMETERS */
        boolean loadSchedulersDataFromProperty,
        /* shown in scheduler status email subjects, e.g. "Devcommerce" */
        String environmentName,
        /* days used by the dashboard priority-assignment graph */
        int priorityAssignmentPeriod,
        /* max rows fetched per Sierra (RAS) batch */
        int rasFetchRowLimit,
        /* HTML shown on the crypto-card acknowledgement page */
        String acknowledgementMessage,
        /* note under the assignment list (published to JSPs by JspGlobals) */
        String assignment30DayNote) {
}
