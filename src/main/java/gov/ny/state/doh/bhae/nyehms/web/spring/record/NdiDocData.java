package gov.ny.state.doh.bhae.nyehms.web.spring.record;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.document.StdNdiDoc;

/**
 * An NDI (National Death Index) record as the NDI document screen shows it (NDIDocument.jsp, model attribute
 * {@code stdNDIDocVO}: {@code stdNDIDocVO.stdNdiDoc.<field>} etc.); replaces the former NDIDocumentVO.
 *
 * @param stdNdiDoc the standardized document (STD_NDI_DOC)
 * @param stateno STATENO of the current person match (PERSON_DOC_ASSOCIATION), if any
 * @param discardStatus DISCARD_STATUS of that match
 * @param ndifileCat NDI file category as received (RAW_NDI_DOC.NDIFILE_CAT)
 * @param parentDocumentId PARENT_DOCUMENT_ID of the document (DOCUMENT_LOCATOR); 0 if empty, as the former int property
 * @param dodText date of death as day/month/year text, built by NDIDocumentService (see {@link #withDodText})
 */
public record NdiDocData(StdNdiDoc stdNdiDoc,
		String stateno,
		String discardStatus,
		String ndifileCat,
		Integer parentDocumentId,
		String dodText) {

	/** The document as read (used by the JPQL constructor expression). */
	public NdiDocData(StdNdiDoc stdNdiDoc, String stateno, String discardStatus, String ndifileCat, Integer parentDocumentId) {
		this(stdNdiDoc, stateno, discardStatus, ndifileCat, parentDocumentId, null);
	}

	/** The same document with {@code dodText}. */
	public NdiDocData withDodText(String dodText) {
		return new NdiDocData(stdNdiDoc, stateno, discardStatus, ndifileCat, parentDocumentId, dodText);
	}
}
