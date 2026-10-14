package gov.ny.state.doh.bhae.nyehms.web.spring.record;

import java.util.List;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.document.StdNycprfDoc;

/**
 * An NYC Partner Referral Form as the NYC PRF document screen shows it (NycPrfDocument.jsp, model
 * attribute {@code nycPRFDocVO}: {@code nycPRFDocVO.stdNycprfDoc.fname} etc.).
 *
 * @param stdNycprfDoc     the standardized form (STD_NYCPRF_DOC)
 * @param rawPersonAddress the person address as received (RAW_NYCPRF_DOC.ADDRESS)
 * @param stateno          STATENO of the current person match (PERSON_DOC_ASSOCIATION), if any
 * @param discardStatus    DISCARD_STATUS of that match
 * @param dob              date of birth as MM/dd/yyyy text (the screen's date picker needs text)
 * @param fieldProcDate    field processing date as MM/dd/yyyy text
 * @param partnerList      the partners on the form, see {@link #withPartnerList}
 */
public record NycPrfDocData(StdNycprfDoc stdNycprfDoc, String rawPersonAddress, String stateno, String discardStatus,
		String dob, String fieldProcDate, List<NycPrfPartnerData> partnerList) {

	/** Document without partners (used by the JPQL constructor expression). */
	public NycPrfDocData(StdNycprfDoc stdNycprfDoc, String rawPersonAddress, String stateno, String discardStatus,
			String dob, String fieldProcDate) {
		this(stdNycprfDoc, rawPersonAddress, stateno, discardStatus, dob, fieldProcDate, List.of());
	}

	/** The same document with its partners. */
	public NycPrfDocData withPartnerList(List<NycPrfPartnerData> partners) {
		return new NycPrfDocData(stdNycprfDoc, rawPersonAddress, stateno, discardStatus, dob, fieldProcDate, partners);
	}
}
