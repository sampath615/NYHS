package gov.ny.state.doh.bhae.nyehms.web.spring.record;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.document.StdCasaDoc;

/**
 * A CASA report as the CASA document screen shows it (CASADocument.jsp, model attribute
 * {@code stdCASADocVO}: {@code stdCASADocVO.stdCasaDoc.<field>} etc.); replaces the former CASADocVO.
 *
 * @param stdCasaDoc the standardized document (STD_CASA_DOC)
 * @param stateno STATENO of the current person match (PERSON_DOC_ASSOCIATION), if any
 * @param discardStatus DISCARD_STATUS of that match
 * @param parentDocumentId PARENT_DOCUMENT_ID of the document (DOCUMENT_LOCATOR); 0 if empty, as the former int property
 */
public record CasaDocData(StdCasaDoc stdCasaDoc,
		String stateno,
		String discardStatus,
		Integer parentDocumentId) {
}
