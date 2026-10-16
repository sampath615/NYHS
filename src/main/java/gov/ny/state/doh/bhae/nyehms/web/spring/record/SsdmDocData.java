package gov.ny.state.doh.bhae.nyehms.web.spring.record;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.document.StdSsdmDoc;

/**
 * An SSDM (Social Security Death Master) record as the SSDM document screen shows it (SSDMDocument.jsp, model attribute
 * {@code stdSSDMDocVO}: {@code stdSSDMDocVO.stdSsdmDoc.<field>} etc.); replaces the former SSDMDocVO.
 *
 * @param stdSsdmDoc the standardized document (STD_SSDM_DOC)
 * @param stateno STATENO of the current person match (PERSON_DOC_ASSOCIATION), if any
 * @param discardStatus DISCARD_STATUS of that match
 * @param parentDocumentId PARENT_DOCUMENT_ID of the document (DOCUMENT_LOCATOR); 0 if empty, as the former int property
 */
public record SsdmDocData(StdSsdmDoc stdSsdmDoc,
		String stateno,
		String discardStatus,
		Integer parentDocumentId) {
}
