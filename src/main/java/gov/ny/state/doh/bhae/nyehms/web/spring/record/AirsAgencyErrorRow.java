package gov.ny.state.doh.bhae.nyehms.web.spring.record;

/**
 * The AIRS reports stopped for an unknown agency, per agency and creation day (RawAirsDocRepository.countAgencyErrors).
 *
 * @param agencyId AGENCY_ID
 * @param createDate the creation day, MM/DD/YYYY
 * @param totalCount the number of reports
 */
public record AirsAgencyErrorRow(String agencyId, String createDate, Long totalCount) {
}
