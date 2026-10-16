package gov.ny.state.doh.bhae.nyehms.web.spring.record;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.document.StdNycEharsDoc;

/**
 * An NYC eHARS case report as the NYC eHARS document screen shows it (NycEharsDocument.jsp, model attribute
 * {@code StdNYCEHARSDocVO}: {@code StdNYCEHARSDocVO.stdNycEharsDoc.<field>} etc.); replaces the former NYCEHARSDocVO.
 *
 * @param stdNycEharsDoc the standardized document (STD_NYC_EHARS_DOC)
 * @param rawaddress rawAddress; (r1.CURRENT_ADDRESS rawAddress) its default if empty, as the former primitive property
 * @param stateno STATENO of the current person match (PERSON_DOC_ASSOCIATION), if any
 * @param discardStatus DISCARD_STATUS of that match
 * @param parentDocumentId PARENT_DOCUMENT_ID of the document (DOCUMENT_LOCATOR); 0 if empty, as the former int property
 */
public record NycEharsDocData(StdNycEharsDoc stdNycEharsDoc,
		String rawaddress,
		String stateno,
		String discardStatus,
		Integer parentDocumentId) {
}
