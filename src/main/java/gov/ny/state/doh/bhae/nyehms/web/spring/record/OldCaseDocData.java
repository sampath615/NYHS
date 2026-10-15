package gov.ny.state.doh.bhae.nyehms.web.spring.record;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.document.StdOldCasesDoc;

/**
 * An old case report as the Old Cases document screen shows it (OldCaseDocument.jsp, model
 * attribute {@code stdOldCaseDocVO}: {@code stdOldCaseDocVO.stdOldCasesDoc.fname} etc.).
 *
 * @param stdOldCasesDoc the standardized report (STD_OLD_CASES_DOC)
 * @param matchedStateno STATENO of the current person match (PERSON_DOC_ASSOCIATION), if any
 * @param discardStatus  DISCARD_STATUS of that match
 */
public record OldCaseDocData(StdOldCasesDoc stdOldCasesDoc, String matchedStateno, String discardStatus) {
}
