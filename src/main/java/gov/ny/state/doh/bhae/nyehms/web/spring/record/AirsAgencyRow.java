package gov.ny.state.doh.bhae.nyehms.web.spring.record;

import java.sql.Timestamp;

/**
 * An AIRS agency that is not retired (RawAirsDocRepository.findActiveAgency).
 *
 * @param agencyId AGENCY_ID
 * @param lastUpgradeDate LAST_UPGRADE_DATE
 */
public record AirsAgencyRow(String agencyId, Timestamp lastUpgradeDate) {
}
