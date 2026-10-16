package gov.ny.state.doh.bhae.nyehms.web.spring.record;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.document.StdMedOldDoc;

/**
 * An old Medicaid report as the Medicaid Old document screen shows it (MedOldDocument.jsp, model attribute
 * {@code stdMedicaidDocVO}: {@code stdMedicaidDocVO.stdMedOldDoc.<field>} etc.); replaces the former MedicaidOldDocVO.
 *
 * @param stdMedOldDoc the standardized document (STD_MED_OLD_DOC)
 * @param stateno STATENO of the current person match (PERSON_DOC_ASSOCIATION), if any
 * @param discardStatus DISCARD_STATUS of that match
 * @param rawAddress the person address as received (RAW_MED_OLD_DOC.ADDRESS)
 * @param rawProvAddress the provider address as received (RAW_MED_OLD_DOC.PROV_ADDR)
 */
public record MedicaidOldDocData(StdMedOldDoc stdMedOldDoc,
		String stateno,
		String discardStatus,
		String rawAddress,
		String rawProvAddress) {

	/** An empty document, shown when there is none (the screen's form needs an object to bind to). */
	public static MedicaidOldDocData empty() {
		return new MedicaidOldDocData(new StdMedOldDoc(), null, null, null, null);
	}
}
