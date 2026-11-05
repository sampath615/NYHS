package gov.ny.state.doh.bhae.nyehms.web.spring.record;

import java.math.BigDecimal;

/**
 * The cancer registry record picked for the clerical review (RawCancerDocRepository.findOneToReview).
 *
 * @param documentId TO_NUMBER(PATIENTIDNUMBER)
 * @param matchCd FLUX_MATCH_CD
 * @param potStateno FLUX_POT_STATENO
 */
public record CancerReviewRow(BigDecimal documentId, String matchCd, String potStateno) {
}
