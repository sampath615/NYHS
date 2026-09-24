package gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.ccd;

import java.util.*;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.hl7.RawHL7ObxVO;

public class RawCCDEncounterVO {

	private int uphnId;
	private int ccdDocID;
	private int encounterSeqNo;
	private int encounterDocId;
	private String encounterId;
	private String encounterType;
	private RawCCDNameVO providerName;
	private String facilityName;
	private String facilityDept;
	private String startDateText;
	private String endDateText;
	private String admissionType;
    // ADDED This 5 columns date 6/19/2017
	private String participantFacilityName;
	private String facilityTypeCode;
	private String facilityTypeCodeSystem;
	private String facilityTypeCodeSystemName;
	private String facilityType;

	private List<RawCCDDiagnosisVO> theDiagnoses = new ArrayList<RawCCDDiagnosisVO>();
		
	/*FOR CCD_DOC_VIEWER ONLY*/
	private String providerFullName;
	private String facilitydetails;
	

	public int getUphnId() {
		return uphnId;
	}

	public void setUphnId(int uphnId) {
		this.uphnId = uphnId;
	}

	public int getCcdDocID() {
		return ccdDocID;
	}

	public void setCcdDocID(int ccdDocID) {
		this.ccdDocID = ccdDocID;
	}

	public int getEncounterSeqNo() {
		return encounterSeqNo;
	}

	public void setEncounterSeqNo(int encounterSeqNo) {
		this.encounterSeqNo = encounterSeqNo;
	}

	public int getEncounterDocId() {
		return encounterDocId;
	}

	public void setEncounterDocId(int encounterDocId) {
		this.encounterDocId = encounterDocId;
	}

	public String getEncounterId() {
		return encounterId;
	}

	public void setEncounterId(String encounterId) {
		this.encounterId = encounterId;
	}

	public String getEncounterType() {
		return encounterType;
	}

	public void setEncounterType(String encounterType) {
		this.encounterType = encounterType;
	}

	public RawCCDNameVO getProviderName() {
		return providerName;
	}

	public void setProviderName(RawCCDNameVO providerName) {
		this.providerName = providerName;
	}

	public String getFacilityName() {
		return facilityName;
	}

	public void setFacilityName(String facilityName) {
		this.facilityName = facilityName;
	}

	public String getFacilityDept() {
		return facilityDept;
	}

	public void setFacilityDept(String facilityDept) {
		this.facilityDept = facilityDept;
	}

	public String getStartDateText() {
		return startDateText;
	}

	public void setStartDateText(String startDateText) {
		this.startDateText = startDateText;
	}

	public String getEndDateText() {
		return endDateText;
	}

	public void setEndDateText(String endDateText) {
		this.endDateText = endDateText;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Encounter ID: " + encounterDocId + " of type " + encounterType + " at facility \"" + facilityName
				+ "\"\n");
		sb.append("Admission Type: " + admissionType + "\n");
		for (RawCCDDiagnosisVO d : theDiagnoses) {
			sb.append(d.toString() + "\n");
		}
		return sb.toString();
	}

	public String getAdmissionType() {
		return admissionType;
	}

	public void setAdmissionType(String admissionType) {
		this.admissionType = admissionType;
	}

	public String getParticipantFacilityName() {
		return participantFacilityName;
	}

	public void setParticipantFacilityName(String participantFacilityName) {
		this.participantFacilityName = participantFacilityName;
	}

	public String getFacilityTypeCode() {
		return facilityTypeCode;
	}

	public void setFacilityTypeCode(String facilityTypeCode) {
		this.facilityTypeCode = facilityTypeCode;
	}

	public String getFacilityTypeCodeSystem() {
		return facilityTypeCodeSystem;
	}

	public void setFacilityTypeCodeSystem(String facilityTypeCodeSystem) {
		this.facilityTypeCodeSystem = facilityTypeCodeSystem;
	}

	public String getFacilityTypeCodeSystemName() {
		return facilityTypeCodeSystemName;
	}

	public void setFacilityTypeCodeSystemName(String facilityTypeCodeSystemName) {
		this.facilityTypeCodeSystemName = facilityTypeCodeSystemName;
	}

	public String getFacilityType() {
		return facilityType;
	}

	public void setFacilityType(String facilityType) {
		this.facilityType = facilityType;
	}

	public List<RawCCDDiagnosisVO> getTheDiagnoses() {
		return theDiagnoses;
	}

	public String getProviderFullName() {
		return providerFullName;
	}

	public void setProviderFullName(String providerFullName) {
		this.providerFullName = providerFullName;
	}

	public String getFacilitydetails() {
		return facilitydetails;
	}

	public void setFacilitydetails(String facilitydetails) {
		this.facilitydetails = facilitydetails;
	}

	public void setTheDiagnoses(List<RawCCDDiagnosisVO> theDiagnosis) {
		this.theDiagnoses = theDiagnosis;
	}

}
