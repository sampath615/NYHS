package gov.ny.state.doh.bhae.nyehms.web.spring.record;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.document.StdNycijrDoc;

/**
 * An NYC IJR record as the NYC IJR document screen shows it (NycIJRDocument.jsp, model attribute
 * {@code stdNYCIJRDocVO}: {@code stdNYCIJRDocVO.stdNycijrDoc.<field>} etc.); replaces the former NycIJRDocVO.
 *
 * @param stdNycijrDoc the standardized record (STD_NYCIJR_DOC)
 * @param rawDxAddress the address at diagnosis as received (RAW_NYCIJR_DOC.ADDRESS)
 * @param rawAddress the current address as received (RAW_NYCIJR_DOC.CUR_STREET_ADDRESS1)
 * @param rawProvAddress the provider address as received (RAW_NYCIJR_DOC.PROV_ADDR)
 * @param stateNo the matched person's state number, if any
 * @param discardStatus DISCARD_STATUS of the person match, if any
 * @param numberOfPartners NUMBEROFPARTNERS, 0 when empty (as the former int property showed it)
 * @param parentDocumentId PARENT_DOCUMENT_ID of the document, 0 when empty (likewise)
 */
public record NycIjrDocData(StdNycijrDoc stdNycijrDoc,
		String rawDxAddress,
		String rawAddress,
		String rawProvAddress,
		String stateNo,
		String discardStatus,
		int numberOfPartners,
		int parentDocumentId) {
}
