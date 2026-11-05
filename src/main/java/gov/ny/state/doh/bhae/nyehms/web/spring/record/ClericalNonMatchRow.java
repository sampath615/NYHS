package gov.ny.state.doh.bhae.nyehms.web.spring.record;

import java.sql.Timestamp;

/** A row of the clerical non-match report (view NYEHMS_CR_NON_MATCH_VW). */
public record ClericalNonMatchRow(String stateno, String documentTypeCode, String reviewedBy, Timestamp reviewDate) {
}
