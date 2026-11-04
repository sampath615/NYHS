package gov.ny.state.doh.bhae.nyehms.web.spring.config;

import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Lab reference data, bound from nyehms.lab.* .
 */
@ConfigurationProperties(prefix = "nyehms.lab")
public record LabProperties(
        /* viral-load results from these labs get DETECTED / NOT DETECTED appended from the OBX note */
        Vendor quest,
        Vendor labcorp,
        /* standard test-result code -> text shown, e.g. RCT -> REACTIVE (LabDocumentService) */
        Map<String, String> testResultLabels,
        /* GraphQL query sent to the Stanford Sierra (HIVdb) service */
        String sierraQuery) {

    /** Comma-separated lists; LabDocumentService matches with indexOf. */
    public record Vendor(String clias, String loincCodes, String testResults) {}
}
