package gov.ny.state.doh.bhae.nyehms.web.spring.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Expected column layouts of uploaded files, bound from nyehms.upload.* .
 * Values are comma-separated, in file order; callers split them.
 * cdc-return-column-names differs per environment (application-{profile}.yml), the rest are shared.
 */
@ConfigurationProperties(prefix = "nyehms.upload")
public record UploadProperties(
        String ridrColumnNames,
        String ridrDbColumnNames,
        String clusterBhaeColumnNames,
        String clusterBhaeDbColumnNames,
        String recordSearchColumnNames,
        String statenoHistoryColumnName,
        String psConfirmedStdColumnNames,
        String nycSurvRejectColumnNames,
        String laftSurveyResponseColumnNames,
        String clepHivSurveyColumnNames,
        String adapReturnFileColumnNames,
        String cdcReturnColumnNames,
        Dataflux dataflux) {

    /** Dataflux match upload: columns of the file and the SQL used to insert them. */
    public record Dataflux(String matchColumns, String matchSqlColumns, String matchSqlColumnsValues,
                           String matchSqlMapping) {}
}
