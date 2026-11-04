package gov.ny.state.doh.bhae.nyehms.web.spring.record;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.document.StdTbDoc;

/**
 * A TB record as the TB document screen shows it (TBDocument.jsp, model attribute {@code stdTBDocVO}:
 * {@code stdTBDocVO.stdTbDoc.<field>} etc.); replaces the former TBDocVO.
 *
 * @param stdTbDoc the standardized record (STD_TB_DOC)
 * @param rawFacilityAddress the facility address as received (RAW_TB_DOC.FACILITY_ADDRESS)
 * @param rawProviderAddress the provider address as received (RAW_TB_DOC.PROVIDERADDRESS)
 * @param rawPersonAddress the person's street as received (RAW_TB_DOC.STREET)
 * @param stateno the matched person's state number, if any
 * @param discardStatus DISCARD_STATUS of the person match, if any
 * @param parentDocumentId PARENT_DOCUMENT_ID of the document, 0 when empty (as the former int property showed it)
 */
public record TbDocData(StdTbDoc stdTbDoc,
		String rawFacilityAddress,
		String rawProviderAddress,
		String rawPersonAddress,
		String stateno,
		String discardStatus,
		int parentDocumentId) {

	/** The empty form the screen shows when there is no such document (document id 0, as before). */
	public static TbDocData empty() {
		return new TbDocData(StdTbDoc.builder().documentId(0).build(), null, null, null, null, null, 0);
	}
}
