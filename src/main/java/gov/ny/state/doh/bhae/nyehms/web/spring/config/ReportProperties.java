package gov.ny.state.doh.bhae.nyehms.web.spring.config;

import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Report titles, output folders and layouts, bound from nyehms.report.* .
 */
@ConfigurationProperties(prefix = "nyehms.report")
public record ReportProperties(
        /* shared folder (SFTP) the generated reports are written to and read from */
        String outputPath,
        String riaReviewName,
        /* page title by report type, e.g. riaEligibility -> "RIA Eligibility Report" (RiaController, PSMessageController) */
        Map<String, String> names,
        LabCount labCount,
        LoincMonthly loincMonthly) {

    /** Weekly HIV lab reporting volume PDF (StatusReportsService). */
    public record LabCount(String dataSet1, String dataSet2, String dataSet3, String dataSet4, String notes,
                           String fileName) {}

    /** Monthly LOINC report workbook (StatusReportsService). */
    public record LoincMonthly(String pathName, String fileName, String fileNameByRegion, String columns1,
                               String byRegionColumns1, String columns2, String dateFormat) {}
}
