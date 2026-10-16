package gov.ny.state.doh.bhae.nyehms.web.spring.record;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.document.StdInsDeathDoc;

/**
 * An insurance death record as the insurance death document screen shows it (InsDthDocument.jsp, model attribute
 * {@code StdInsDeathDocVO}: {@code StdInsDeathDocVO.stdInsDeathDoc.<field>} etc.); replaces the former InsDeathDocVO.
 *
 * @param stdInsDeathDoc the standardized document (STD_INS_DEATH_DOC)
 * @param currentProcessStatus CURRENT_PROCESS_STATUS; its default if empty, as the former primitive property
 * @param stateno STATENO of the current person match (PERSON_DOC_ASSOCIATION), if any
 * @param discardStatus DISCARD_STATUS of that match
 * @param parentDocumentId PARENT_DOCUMENT_ID of the document (DOCUMENT_LOCATOR); 0 if empty, as the former int property
 */
public record InsDeathDocData(StdInsDeathDoc stdInsDeathDoc,
		Integer currentProcessStatus,
		String stateno,
		String discardStatus,
		Integer parentDocumentId) {
}
