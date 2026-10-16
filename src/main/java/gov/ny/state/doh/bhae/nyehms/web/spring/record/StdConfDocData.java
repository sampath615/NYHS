package gov.ny.state.doh.bhae.nyehms.web.spring.record;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.document.StdStdConfirmedDoc;

/**
 * A confirmed STD (sexually transmitted disease) report as the confirmed STD document screen shows it (STDConfDocument.jsp, model attribute
 * {@code stdConfDocVO}: {@code stdConfDocVO.stdStdConfirmedDoc.<field>} etc.); replaces the former STDConfDocVO.
 *
 * @param stdStdConfirmedDoc the standardized document (STD_STD_CONFIRMED_DOC)
 * @param stateno STATENO of the current person match (PERSON_DOC_ASSOCIATION), if any
 * @param dob DOB; (TO_CHAR(std.DOB, 'MM/DD/YYYY') DOB) its default if empty, as the former primitive property
 * @param diagDate DIAG_DATE; (TO_CHAR(std.DIAG_DATE, 'MM/DD/YYYY') DIAG_DATE) its default if empty, as the former primitive property
 * @param dateOfDeath DATE_OF_DEATH; (TO_CHAR(std.DATE_OF_DEATH, 'MM/DD/YYYY') DATE_OF_DEATH) its default if empty, as the former primitive property
 * @param x900dxdate X900DXDATE; (TO_CHAR(std.X900DXDATE, 'MM/DD/YYYY') X900DXDATE) its default if empty, as the former primitive property
 * @param personAddressRaw PERSON_ADDRESS_RAW; (raw1.PERSON_ADDRESS PERSON_ADDRESS_RAW) its default if empty, as the former primitive property
 * @param provAddressRaw PROV_ADDRESS_RAW; (raw1.PROV_ADDRESS PROV_ADDRESS_RAW) its default if empty, as the former primitive property
 */
public record StdConfDocData(StdStdConfirmedDoc stdStdConfirmedDoc,
		String stateno,
		String dob,
		String diagDate,
		String dateOfDeath,
		String x900dxdate,
		String personAddressRaw,
		String provAddressRaw) {
}
