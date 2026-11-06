package gov.ny.state.doh.bhae.nyehms.web.spring.record;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.document.StdAhiDoc;

/**
 * An AHI form with the values the AHI document screen adds (read by AHIDocumentDAO.getAHIDoc).
 *
 * @param doc the form (STD_AHI_DOC)
 * @param parentDocumentId the document's PARENT_DOCUMENT_ID (DOCUMENT_LOCATOR)
 * @param ahiFlag the AHI_FLAG of the surveillance assignment whose AHI_DOC_ID is the form, or null
 */
public record AhiDocRow(StdAhiDoc doc, Integer parentDocumentId, String ahiFlag) {
}
