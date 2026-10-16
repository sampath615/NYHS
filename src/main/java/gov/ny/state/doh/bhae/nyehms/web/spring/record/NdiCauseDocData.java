package gov.ny.state.doh.bhae.nyehms.web.spring.record;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.document.StdNdiCauseDoc;

/**
 * An NDI cause of death record as the NDI cause of death document screen shows it (NDICauseDocument.jsp, model attribute
 * {@code stdNDICauseDocVO}: {@code stdNDICauseDocVO.stdNdiCauseDoc.<field>} etc.); replaces the former NDICauseDocVO.
 *
 * @param stdNdiCauseDoc the standardized document (STD_NDI_CAUSE_DOC)
 * @param stateno STATENO of the current person match (PERSON_DOC_ASSOCIATION), if any
 * @param discardStatus DISCARD_STATUS of that match
 */
public record NdiCauseDocData(StdNdiCauseDoc stdNdiCauseDoc,
		String stateno,
		String discardStatus) {
}
