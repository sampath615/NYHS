package gov.ny.state.doh.bhae.nyehms.web.spring.record;

import java.sql.Date;
import java.util.List;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.dataentry.ICCRTestVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.entity.document.StdIccrDoc;

/**
 * An ICCR (Infection Control) report as the ICCR document screen shows it (ICCRDocument.jsp, model attribute
 * {@code stdICCRDocVO}: {@code stdICCRDocVO.stdIccrDoc.<field>} etc.); replaces the former ICCRDocVO.
 *
 * @param stdIccrDoc the standardized document (STD_ICCR_DOC)
 * @param stateno stateNo; (PDOC.STATENO stateNo) its default if empty, as the former primitive property
 * @param dob DOB; (to_char(std.DOB,'MM/DD/YYYY') DOB) its default if empty, as the former primitive property
 * @param rawpersonaddress rawPersonAddress; (raw1.PERSON_ADDRESS rawPersonAddress) its default if empty, as the former primitive property
 * @param rawprovaddress rawProvAddress; (raw1.PROV_ADDRESS rawProvAddress) its default if empty, as the former primitive property
 * @param resultsReceived RESULTS_RECEIVED; (c1.ANS_DESC RESULTS_RECEIVED) its default if empty, as the former primitive property
 * @param parentDocumentId PARENT_DOCUMENT_ID of the document (DOCUMENT_LOCATOR); 0 if empty, as the former int property
 * @param iccrTestList the tests of the report, added by ICCRDocumentService
 * @param eharsImportDate never read by the query: the screen shows it empty, as before
 */
public record IccrDocData(StdIccrDoc stdIccrDoc,
		String stateno,
		String dob,
		String rawpersonaddress,
		String rawprovaddress,
		String resultsReceived,
		Integer parentDocumentId,
		List<ICCRTestVO> iccrTestList,
		Date eharsImportDate) {

	/** The document as read (used by the JPQL constructor expression). */
	public IccrDocData(StdIccrDoc stdIccrDoc, String stateno, String dob, String rawpersonaddress, String rawprovaddress, String resultsReceived, Integer parentDocumentId) {
		this(stdIccrDoc, stateno, dob, rawpersonaddress, rawprovaddress, resultsReceived, parentDocumentId, null, null);
	}

	/** The same document with {@code iccrTestList}. */
	public IccrDocData withIccrTestList(List<ICCRTestVO> iccrTestList) {
		return new IccrDocData(stdIccrDoc, stateno, dob, rawpersonaddress, rawprovaddress, resultsReceived, parentDocumentId, iccrTestList, eharsImportDate);
	}
}
