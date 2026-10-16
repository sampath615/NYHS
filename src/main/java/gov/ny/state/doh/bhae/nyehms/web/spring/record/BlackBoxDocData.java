package gov.ny.state.doh.bhae.nyehms.web.spring.record;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.document.StdBlackBoxDoc;

/**
 * A Black Box record as the Black Box document screen shows it (BlackBoxDocument.jsp, model attribute
 * {@code stdBlackBoxDocVO}: {@code stdBlackBoxDocVO.stdBlackBoxDoc.<field>} etc.); replaces the former BlackBoxDocVO.
 *
 * @param stdBlackBoxDoc the standardized document (STD_BLACK_BOX_DOC)
 * @param stateno STATENO of the current person match (PERSON_DOC_ASSOCIATION), if any
 * @param currentProcessStatus CURRENT_PROCESS_STATUS; 0 if empty, as the former int property
 * @param rsdStreetAddress residence street address: line 1, a space, line 2
 * @param curStreetAddress current street address: line 1, a space, line 2
 */
public record BlackBoxDocData(StdBlackBoxDoc stdBlackBoxDoc,
		String stateno,
		Integer currentProcessStatus,
		String rsdStreetAddress,
		String curStreetAddress) {
}
