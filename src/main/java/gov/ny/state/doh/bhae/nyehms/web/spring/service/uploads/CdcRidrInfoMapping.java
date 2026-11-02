package gov.ny.state.doh.bhae.nyehms.web.spring.service.uploads;

import java.util.Map;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.upload.CdcRidrInfo;

/**
 * Builds a CDC_RIDR_INFO row from a parsed line of a CDC RIDR file.
 */
final class CdcRidrInfoMapping {

	private CdcRidrInfoMapping() {
	}

	/**
	 * @param row the parsed line, value per field name
	 * @param fileName the uploaded file
	 * @param workerId the worker who uploaded it (CREATED_UID)
	 */
	static CdcRidrInfo toRow(Map<String, Object> row, String fileName, int workerId) {
		return CdcRidrInfo.builder()
				.recordNo((String) row.get("record_no"))
				.nysNewlyReportedCdc((String) row.get("NYS_newly_reported_cdc"))
				.nysReportingJurisdiction((String) row.get("NYS_Reporting_Jurisdiction"))
				.nysStateno((String) row.get("NYS_stateno"))
				.nysLastNameSoundex((String) row.get("NYS_Last_Name_Soundex"))
				.nysDateOfBirth((String) row.get("NYS_DATE_OF_BIRTH"))
				.nysSexAtBirth((String) row.get("NYS_SEX_AT_BIRTH"))
				.nysDateOfDeath((String) row.get("NYS_DATE_OF_DEATH"))
				.nysTransmissionCategory((String) row.get("NYS_Transmission_Category"))
				.nysHivCaseDefCategory((String) row.get("NYS_HIV_Case_Def_Category"))
				.nysDateHivInfectionDiag((String) row.get("NYS_Date_HIV_Infection_Diag"))
				.nysResiHivInfectionDiag((String) row.get("NYS_Resi_HIV_Infection_Diag"))
				.nysAidsCaseDefCategory((String) row.get("NYS_AIDS_Case_Def_Category"))
				.nysDateAidsDiag((String) row.get("NYS_Date_AIDS_Diag"))
				.nysResiAtAidsDiag((String) row.get("NYS_Resi_at_AIDS_Diag"))
				.nysDuplicateStatus((String) row.get("NYS_duplicate_status"))
				.newlyReportedCdc((String) row.get("newly_reported_cdc"))
				.reportingJurisdiction((String) row.get("Reporting_Jurisdiction"))
				.stateno((String) row.get("stateno"))
				.dateOfDeath((String) row.get("DATE_OF_DEATH"))
				.transmissionCategory((String) row.get("Transmission_Category"))
				.hivCaseDefCategory((String) row.get("HIV_Case_Def_Category"))
				.dateHivInfectionDiag((String) row.get("Date_HIV_Infection_Diag"))
				.resiHivInfectionDiag((String) row.get("Resi_HIV_Infection_Diag"))
				.aidsCaseDefCategory((String) row.get("AIDS_Case_Def_Category"))
				.dateAidsDiag((String) row.get("Date_AIDS_Diag"))
				.resiAtAidsDiag((String) row.get("Resi_at_AIDS_Diag"))
				.duplicateStatus((String) row.get("duplicate_status"))
				.mergedCdc((String) row.get("merged_cdc"))
				.createdUid(String.valueOf(workerId))
				.fileName(fileName)
				.build();
	}
}
