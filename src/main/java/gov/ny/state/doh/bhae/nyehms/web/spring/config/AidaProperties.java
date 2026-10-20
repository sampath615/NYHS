package gov.ny.state.doh.bhae.nyehms.web.spring.config;

import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * AIDA (AIRS) data tabs on the document page, bound from nyehms.aida.views.* .
 * The key is the parameterName the page sends (PREP, ARV, MED_VISITS, ...).
 */
@ConfigurationProperties(prefix = "nyehms.aida")
public record AidaProperties(Map<String, View> views) {

    /** table/view to read, SELECT column list, and the matching comma-separated tab headers. */
    public record View(String table, String columns, String headers) {}

    public View view(String parameterName) {
        View view = views == null ? null : views.get(parameterName);
        if (view == null) {
            throw new IllegalArgumentException("No nyehms.aida.views entry for '" + parameterName + "'");
        }
        return view;
    }
}
