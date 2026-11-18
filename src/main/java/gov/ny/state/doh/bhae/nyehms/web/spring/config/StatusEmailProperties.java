package gov.ny.state.doh.bhae.nyehms.web.spring.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Layout of the daily status summary emails (CommonService.sendStatusSummary), bound from nyehms.status-email.* .
 */
@ConfigurationProperties(prefix = "nyehms.status-email")
public record StatusEmailProperties(
        Summary assignment,
        Summary ahi,
        Summary psMessage,
        Summary psMessageNyc,
        Summary airsAgency) {

    /**
     * headers = table header row, columns = result-map keys in the same order (both comma-separated),
     * noData = text sent instead of the table when there are no rows.
     */
    public record Summary(String headers, String columns, String noData) {}
}
