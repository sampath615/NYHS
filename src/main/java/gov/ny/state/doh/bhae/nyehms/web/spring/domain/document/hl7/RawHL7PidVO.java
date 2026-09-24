package gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.hl7;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class RawHL7PidVO {

	private String patientIdentifierJson;
	private String pidMpi;
	private RawHL7XpnVO patientName;
	private String pidDateOfBirthRaw;
	private String pidSexCode;
	private String pidRaceCodeRaw;
	private String pidRaceCodeSystem;
	private String pidRaceCodeDesc;
	private RawHL7XadVO patientAddress;
	private String pidHomePhone;
	private String pidWorkPhone;
	private String pidMedicalRecordnumber;
	private String pidSsn;
	private String pidDin;
	private String pidEthnicGroupCode;
	private String pidDeathDate;
	private String pidDeathInd;
	private String pidVeteranStatus;
	private String pidFacilityIdExternal;
	private String pidFacilityIdInternal;
	private String pidLengyhOfResidency;
	private String pidMedicaidNumber;
	private String pidNysid;
	private String pidPatientAccountNumber;
	private String pidPatientIdExternal;
	private String pidPatientIdInternal;
	private String pidTypeOfInsurance;
	private String pidVaccinceTrailInd;
	private String pidPatientIDList;
	private String pidNote;

	///////////////////////////
	public RawHL7PidVO() {
		// TODO Auto-generated constructor stub
	}

	public String getPatientIdentifierJson() {
		return patientIdentifierJson;
	}

	public void setPatientIdentifierJson(String patientIdentifierJson) {
		this.patientIdentifierJson = patientIdentifierJson;
	}

	public String getPidMpi() {
		return pidMpi;
	}

	public void setPidMpi(String pidMpi) {
		this.pidMpi = pidMpi;
	}

	public RawHL7XpnVO getPatientName() {
		return patientName;
	}

	public void setPatientName(RawHL7XpnVO patientName) {
		this.patientName = patientName;
	}

	public String getPidDateOfBirthRaw() {
		return pidDateOfBirthRaw;
	}

	public void setPidDateOfBirthRaw(String pidDateOfBirthRaw) {
		this.pidDateOfBirthRaw = pidDateOfBirthRaw;
	}

	public String getPidSexCode() {
		return pidSexCode;
	}

	public void setPidSexCode(String pidSexCode) {
		this.pidSexCode = pidSexCode;
	}

	public String getPidRaceCodeRaw() {
		return pidRaceCodeRaw;
	}

	public void setPidRaceCodeRaw(String pidRaceCodeRaw) {
		this.pidRaceCodeRaw = pidRaceCodeRaw;
	}

	public String getPidRaceCodeSystem() {
		return pidRaceCodeSystem;
	}

	public void setPidRaceCodeSystem(String pidRaceCodeSystem) {
		this.pidRaceCodeSystem = pidRaceCodeSystem;
	}

	public String getPidRaceCodeDesc() {
		return pidRaceCodeDesc;
	}

	public void setPidRaceCodeDesc(String pidRaceCodeDesc) {
		this.pidRaceCodeDesc = pidRaceCodeDesc;
	}

	public RawHL7XadVO getPatientAddress() {
		return patientAddress;
	}

	public void setPatientAddress(RawHL7XadVO patientAddress) {
		this.patientAddress = patientAddress;
	}

	public String getPidHomePhone() {
		return pidHomePhone;
	}

	public void setPidHomePhone(String pidHomePhone) {
		this.pidHomePhone = pidHomePhone;
	}

	public String getPidWorkPhone() {
		return pidWorkPhone;
	}

	public void setPidWorkPhone(String pidWorkPhone) {
		this.pidWorkPhone = pidWorkPhone;
	}

	public String getPidMedicalRecordnumber() {
		return pidMedicalRecordnumber;
	}

	public void setPidMedicalRecordnumber(String pidMedicalRecordnumber) {
		this.pidMedicalRecordnumber = pidMedicalRecordnumber;
	}

	public String getPidSsn() {
		return pidSsn;
	}

	public void setPidSsn(String pidSsn) {
		this.pidSsn = pidSsn;
	}

	public String getPidDin() {
		return pidDin;
	}

	public void setPidDin(String pidDin) {
		this.pidDin = pidDin;
	}

	public String getPidEthnicGroupCode() {
		return pidEthnicGroupCode;
	}

	public void setPidEthnicGroupCode(String pidEthnicGroupCode) {
		this.pidEthnicGroupCode = pidEthnicGroupCode;
	}

	public String getPidDeathDate() {
		return pidDeathDate;
	}

	public void setPidDeathDate(String pidDeathDate) {
		this.pidDeathDate = pidDeathDate;
	}

	public String getPidDeathInd() {
		return pidDeathInd;
	}

	public void setPidDeathInd(String pidDeathInd) {
		this.pidDeathInd = pidDeathInd;
	}

	public String getPidVeteranStatus() {
		return pidVeteranStatus;
	}

	public void setPidVeteranStatus(String pidVeteranStatus) {
		this.pidVeteranStatus = pidVeteranStatus;
	}

	public String getPidFacilityIdExternal() {
		return pidFacilityIdExternal;
	}

	public void setPidFacilityIdExternal(String pidFacilityIdExternal) {
		this.pidFacilityIdExternal = pidFacilityIdExternal;
	}

	public String getPidFacilityIdInternal() {
		return pidFacilityIdInternal;
	}

	public void setPidFacilityIdInternal(String pidFacilityIdInternal) {
		this.pidFacilityIdInternal = pidFacilityIdInternal;
	}

	public String getPidLengyhOfResidency() {
		return pidLengyhOfResidency;
	}

	public void setPidLengyhOfResidency(String pidLengyhOfResidency) {
		this.pidLengyhOfResidency = pidLengyhOfResidency;
	}

	public String getPidMedicaidNumber() {
		return pidMedicaidNumber;
	}

	public void setPidMedicaidNumber(String pidMedicaidNumber) {
		this.pidMedicaidNumber = pidMedicaidNumber;
	}

	public String getPidNysid() {
		return pidNysid;
	}

	public void setPidNysid(String pidNysid) {
		this.pidNysid = pidNysid;
	}

	public String getPidPatientAccountNumber() {
		return pidPatientAccountNumber;
	}

	public void setPidPatientAccountNumber(String pidPatientAccountNumber) {
		this.pidPatientAccountNumber = pidPatientAccountNumber;
	}

	public String getPidPatientIdExternal() {
		return pidPatientIdExternal;
	}

	public void setPidPatientIdExternal(String pidPatientIdExternal) {
		this.pidPatientIdExternal = pidPatientIdExternal;
	}

	public String getPidPatientIdInternal() {
		return pidPatientIdInternal;
	}

	public void setPidPatientIdInternal(String pidPatientIdInternal) {
		this.pidPatientIdInternal = pidPatientIdInternal;
	}

	public String getPidTypeOfInsurance() {
		return pidTypeOfInsurance;
	}

	public void setPidTypeOfInsurance(String pidTypeOfInsurance) {
		this.pidTypeOfInsurance = pidTypeOfInsurance;
	}

	public String getPidVaccinceTrailInd() {
		return pidVaccinceTrailInd;
	}

	public void setPidVaccinceTrailInd(String pidVaccinceTrailInd) {
		this.pidVaccinceTrailInd = pidVaccinceTrailInd;
	}

	public String getPidPatientIDList() {
		return pidPatientIDList;
	}

	public void setPidPatientIDList(String pidPatientList) {
		this.pidPatientIDList = pidPatientList;
	}

	public String getPidNote() {
		return pidNote;
	}

	public void setPidNote(String pidNote) {
		this.pidNote = pidNote;
	}

	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

}
