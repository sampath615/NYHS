package gov.ny.state.doh.bhae.nyehms.web.spring.record;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.document.RawCancerDoc;

/**
 * A cancer registry record with the values the database computes for the cancer screens (RawCancerDocRepository).
 *
 * @param doc the record (RAW_CANCER_DOC)
 * @param documentNumber TO_NUMBER(PATIENTIDNUMBER) read as text (getString), or null where the query does not read it
 * @param patientIdNumber TO_NUMBER(PATIENTIDNUMBER) read as a whole number (getInt), or null where the query does not
 *        read it
 * @param dateOfBirth TO_CHAR(FN_TO_DATE(DATEOFBIRTH, 'MM/DD/YYYY'), 'MM/DD/YYYY')
 * @param dateOfDeath TO_CHAR(FN_TO_DATE(DATEOFDEATHCANADA, 'MM/DD/YYYY'), 'MM/DD/YYYY')
 * @param countyName COUNTY_NAME of PERSON_COUNTY (CODED_COUNTY), or null where the query does not read it
 */
public record CancerDocRow(RawCancerDoc doc,
		String documentNumber,
		Integer patientIdNumber,
		String dateOfBirth,
		String dateOfDeath,
		String countyName) {
}
