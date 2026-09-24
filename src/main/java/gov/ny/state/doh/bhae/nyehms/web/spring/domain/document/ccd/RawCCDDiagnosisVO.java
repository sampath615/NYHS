package gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.ccd;

public class RawCCDDiagnosisVO {

	private int uphnId;
	private int ccdDocID;
	private int encounterSeqNo;
	private int diagnosisSeqNo;
	private int diagnosisId;
	private int encounterDocId;
	private String diagnosisText;
	private String diagnosisCode;
	private String diagnosisCodeSystemCode;
	private String diagnosisCodeSystemOid;
	
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
	public int getDiagnosisSeqNo() {
		return diagnosisSeqNo;
	}
	public void setDiagnosisSeqNo(int diagnosisSeqNo) {
		this.diagnosisSeqNo = diagnosisSeqNo;
	}
	public int getDiagnosisId()	{
		return diagnosisId;
	}
	public void setDiagnosisId(int diagnosisId) {
		this.diagnosisId = diagnosisId;
	}
	public int getEncounterDocId() {
		return encounterDocId;
	}
	public void setEncounterDocId(int encounterDocId) {
		this.encounterDocId = encounterDocId;
	}
	public String getDiagnosisText() {
		return diagnosisText;
	}
	public void setDiagnosisText(String diagnosisText) {
		this.diagnosisText = diagnosisText;
	}
	public String getDiagnosisCode() {
		return diagnosisCode;
	}
	public void setDiagnosisCode(String diagnosisCode) {
		this.diagnosisCode = diagnosisCode;
	}
	public String getDiagnosisCodeSystemCode() {
		return diagnosisCodeSystemCode;
	}
	public void setDiagnosisCodeSystemCode(String diagnosisCodeSystemCode) {
		this.diagnosisCodeSystemCode = diagnosisCodeSystemCode;
	}
	public String getDiagnosisCodeSystemOid() {
		return diagnosisCodeSystemOid;
	}
	public void setDiagnosisCodeSystemOid(String diagnosisCodeSystemOid) {
		this.diagnosisCodeSystemOid = diagnosisCodeSystemOid;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Diagnosis: " + diagnosisText + ":"
				+ diagnosisCode + ":" + diagnosisCodeSystemOid
				+ ":" + diagnosisCodeSystemCode + ".\n");
		return sb.toString();
	}
}
