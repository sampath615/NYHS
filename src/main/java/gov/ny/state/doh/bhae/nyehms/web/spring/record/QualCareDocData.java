package gov.ny.state.doh.bhae.nyehms.web.spring.record;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.document.StdQualityCareDoc;

/**
 * A quality of care record as the quality of care document screen shows it (QualCareDocument.jsp, model attribute
 * {@code qualCareDocVO}: {@code qualCareDocVO.stdQualityCareDoc.<field>} etc.); replaces the former QualCareDocVO.
 *
 * @param stdQualityCareDoc the standardized record (STD_QUALITY_CARE_DOC)
 * @param rawfacilityaddress the facility street as received (RAW_QUALITY_CARE_DOC.STREET)
 * @param housingStatus the name of the housing status code (CODED_QUAL_CARE_REF_DATA)
 * @param insurance the name of the insurance code
 * @param enrollment the name of the enrollment code
 * @param diagnosis the name of the diagnosis code
 * @param diagnosisDate DIAGNOSIS_DATE as MM/DD/YYYY
 * @param dischargeDate DISCHARGE_DATE as MM/DD/YYYY
 * @param stateno the matched person's state number, if any
 * @param asianSubGroup the names of the Asian sub-group codes, by code, separated by ", "; added by QualCareDocumentService
 * @param hispanicSubGroup the names of the Hispanic sub-group codes, likewise
 * @param facilityState never read by the former query: the screen shows it empty, as before
 */
public record QualCareDocData(StdQualityCareDoc stdQualityCareDoc,
		String rawfacilityaddress,
		String housingStatus,
		String insurance,
		String enrollment,
		String diagnosis,
		String diagnosisDate,
		String dischargeDate,
		String stateno,
		String asianSubGroup,
		String hispanicSubGroup,
		String facilityState) {

	/** The record as read (used by the JPQL constructor expression). */
	public QualCareDocData(StdQualityCareDoc stdQualityCareDoc, String rawfacilityaddress, String housingStatus, String insurance,
			String enrollment, String diagnosis, String diagnosisDate, String dischargeDate, String stateno) {
		this(stdQualityCareDoc, rawfacilityaddress, housingStatus, insurance, enrollment, diagnosis, diagnosisDate, dischargeDate, stateno,
				null, null, null);
	}

	/** The same record with the names of its sub-group codes. */
	public QualCareDocData withSubGroups(String asianSubGroup, String hispanicSubGroup) {
		return new QualCareDocData(stdQualityCareDoc, rawfacilityaddress, housingStatus, insurance, enrollment, diagnosis, diagnosisDate,
				dischargeDate, stateno, asianSubGroup, hispanicSubGroup, facilityState);
	}
}
