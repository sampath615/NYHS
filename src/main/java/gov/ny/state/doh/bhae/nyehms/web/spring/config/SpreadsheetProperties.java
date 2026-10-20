package gov.ny.state.doh.bhae.nyehms.web.spring.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * File names and column headers of the Excel downloads (view.report / view.review), bound from nyehms.spreadsheet.* .
 * Column values are comma-separated; the views split them.
 */
@ConfigurationProperties(prefix = "nyehms.spreadsheet")
public record SpreadsheetProperties(
        Sheet ndvlReport,
        Sheet riaActivityReport,
        Sheet psOocExpsReport,
        AdapNycData adapNycData,
        String statenoHistoryColumns,
        String currentStatenoColumns,
        AdapNycSummary adapNycSummary,
        EprfSummary eprfSummary) {

    public record Sheet(String fileName, String columns) {}

    /** columns = sheet headers, dbColumns = result-map keys in the same order. */
    public record AdapNycData(String columns, String dbColumns) {}

    public record AdapNycSummary(String agreeColumns, String disagreeColumns, String shippedMatchSummary) {}

    public record EprfSummary(String docColumns, String eharsColumns, String eharsConclusions) {}
}
