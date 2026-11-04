package gov.ny.state.doh.bhae.nyehms.web.spring.domain.person;

import java.io.Serializable;
import java.util.List;

/**
 * Author 	: Srikar Kummamuri
 * Date 	: 2/12/12
 * 
 * This object will hold the Person view details
 */

public class PersonViewVO implements Serializable {

	private static final long serialVersionUID = 1L;
	private String bIndicatedDate = "";
	private String stateNo = "";
	private String FNAME = "";
	private String MNAME = "";
	private String LNAME = "";
	private String SUFFIX = "";
	private String GENDER = "";
	private String birthSex = "";
	private String stateOfDeath = "";
	private String dateOfDeath = "";
	private String SSN = "";
	private String NYSID = "";
	private String DIN = "";
	private String MED_RECORD_NUMBER = "";
	private String MEDICAID_NUMBER = "";
	private String INSURANCE_NUMBER = "";
	private String DOB = "";
	private String BIRTH_COUNTRY = "";
	private String ETHNICITY = "";
	private PersonRaceVO personRaceVO;
	
	private String firstNameExists= "";
	private String lastNameExists= "";
	private String middleNameExists= "";
	private String suffixExists= "";
	private String currentGenderExists= "";
	private String birthSexExists= "";
	private String sodExists= "";
	private String dodExists= "";
	private String ssnExists= "";
	private String nysidExists= "";
	private String dinExists= "";
	private String medRecordExists= "";
	private String insuranceExists= "";
	private String birthDateExists= "";
	private String birthCountryExists= "";
	private String ethnicityExists= "";
	private String MEDICAID_NUMBER_EXISTS = "";
	
	private String homePhoneExists= "";
	private String addressExists= "";
	
	// Person Address
	private String PERSON_ADDRESS = "";
	private String PERSON_STATE = "";
	private String PERSON_CITY = "";
	private String country = "";
	private String PERSON_COUNTY = "";
	private String PERSON_ZIP = "";
	private String PERSON_PLUS4 = "";

	// Person Communication
	private String homePhone = "";
	private String cellPhone = "";
	private String workPhone = "";
	private String emerPhone = "";
	private String pager = "";
	private String email = "";
	private String web = "";
	private String badMergeFlag;

	
	private String NYC_CITYNO;
	private List<String> NYC_CITYNO_LIST;
	private String NYC_EHARS_STATENO;
	private List<String> NYC_EHARS_STATENO_LIST;
	private String EHARS_STATENO;
	private List<String> EHARS_STATENO_LIST;
	private String NYS_STAGE_ZERO_DX;
	private String NYS_STAGE_CD4_OI_DX;
	private String NYC_STAGE_ZERO_DX;
	private String NYC_STAGE_CD4_OI_DX;
	private String recordSearchFlag;
	private String recordSearchComments;
	
	private String sentToCDC;
	
	private String clusterIds;
	private String rasDocActive;
	
	private String statenoFlaggedEarlier;
	private String nonCaseDocStatus;
	private String nonCaseHistory;
    private String DUPLICATE_STATENO;
	
	private List<String> DUPLICATE_STATENO_LIST;
	
	
	public String getDUPLICATE_STATENO() {
		return DUPLICATE_STATENO;
	}
	public void setDUPLICATE_STATENO(String dUPLICATE_STATENO) {
		DUPLICATE_STATENO = dUPLICATE_STATENO;
	}
	public List<String> getDUPLICATE_STATENO_LIST() {
		return DUPLICATE_STATENO_LIST;
	}
	public void setDUPLICATE_STATENO_LIST(List<String> dUPLICATE_STATENO_LIST) {
		DUPLICATE_STATENO_LIST = dUPLICATE_STATENO_LIST;
	}
	
	public String getNYC_CITYNO() {
		return NYC_CITYNO;
	}
	public void setNYC_CITYNO(String nYC_CITYNO) {
		NYC_CITYNO = nYC_CITYNO;
	}
	public List<String> getNYC_CITYNO_LIST() {
		return NYC_CITYNO_LIST;
	}
	public void setNYC_CITYNO_LIST(List<String> nYC_CITYNO_LIST) {
		NYC_CITYNO_LIST = nYC_CITYNO_LIST;
	}
	public String getNYC_EHARS_STATENO() {
		return NYC_EHARS_STATENO;
	}
	public void setNYC_EHARS_STATENO(String nYC_EHARS_STATENO) {
		NYC_EHARS_STATENO = nYC_EHARS_STATENO;
	}
	public List<String> getNYC_EHARS_STATENO_LIST() {
		return NYC_EHARS_STATENO_LIST;
	}
	public void setNYC_EHARS_STATENO_LIST(List<String> nYC_EHARS_STATENO_LIST) {
		NYC_EHARS_STATENO_LIST = nYC_EHARS_STATENO_LIST;
	}
	public String getEHARS_STATENO() {
		return EHARS_STATENO;
	}
	public void setEHARS_STATENO(String eHARS_STATENO) {
		EHARS_STATENO = eHARS_STATENO;
	}
	public List<String> getEHARS_STATENO_LIST() {
		return EHARS_STATENO_LIST;
	}
	public void setEHARS_STATENO_LIST(List<String> eHARS_STATENO_LIST) {
		EHARS_STATENO_LIST = eHARS_STATENO_LIST;
	}
	public String getbIndicatedDate() {
		return bIndicatedDate;
	}
	public void setbIndicatedDate(String bIndicatedDate) {
		this.bIndicatedDate = bIndicatedDate;
	}
	public String getStateNo() {
		return stateNo;
	}
	public void setStateNo(String stateNo) {
		this.stateNo = stateNo;
	}
	
	public String getBirthSex() {
		return birthSex;
	}
	public void setBirthSex(String birthSex) {
		this.birthSex = birthSex;
	}
	public String getStateOfDeath() {
		return stateOfDeath;
	}
	public void setStateOfDeath(String stateOfDeath) {
		this.stateOfDeath = stateOfDeath;
	}
	public String getDateOfDeath() {
		return dateOfDeath;
	}
	public void setDateOfDeath(String dateOfDeath) {
		this.dateOfDeath = dateOfDeath;
	}
	
	public String getFirstNameExists() {
		return firstNameExists;
	}
	public void setFirstNameExists(String firstNameExists) {
		this.firstNameExists = firstNameExists;
	}
	public String getLastNameExists() {
		return lastNameExists;
	}
	public void setLastNameExists(String lastNameExists) {
		this.lastNameExists = lastNameExists;
	}
	public String getMiddleNameExists() {
		return middleNameExists;
	}
	public void setMiddleNameExists(String middleNameExists) {
		this.middleNameExists = middleNameExists;
	}
	public String getSuffixExists() {
		return suffixExists;
	}
	public void setSuffixExists(String suffixExists) {
		this.suffixExists = suffixExists;
	}
	public String getCurrentGenderExists() {
		return currentGenderExists;
	}
	public void setCurrentGenderExists(String currentGenderExists) {
		this.currentGenderExists = currentGenderExists;
	}
	public String getBirthSexExists() {
		return birthSexExists;
	}
	public void setBirthSexExists(String birthSexExists) {
		this.birthSexExists = birthSexExists;
	}
	public String getSodExists() {
		return sodExists;
	}
	public void setSodExists(String sodExists) {
		this.sodExists = sodExists;
	}
	public String getDodExists() {
		return dodExists;
	}
	public void setDodExists(String dodExists) {
		this.dodExists = dodExists;
	}
	public String getSsnExists() {
		return ssnExists;
	}
	public void setSsnExists(String ssnExists) {
		this.ssnExists = ssnExists;
	}
	public String getNysidExists() {
		return nysidExists;
	}
	public void setNysidExists(String nysidExists) {
		this.nysidExists = nysidExists;
	}
	public String getDinExists() {
		return dinExists;
	}
	public void setDinExists(String dinExists) {
		this.dinExists = dinExists;
	}
	public String getMedRecordExists() {
		return medRecordExists;
	}
	public void setMedRecordExists(String medRecordExists) {
		this.medRecordExists = medRecordExists;
	}
	public String getInsuranceExists() {
		return insuranceExists;
	}
	public void setInsuranceExists(String insuranceExists) {
		this.insuranceExists = insuranceExists;
	}
	public String getBirthDateExists() {
		return birthDateExists;
	}
	public void setBirthDateExists(String birthDateExists) {
		this.birthDateExists = birthDateExists;
	}
	public String getBirthCountryExists() {
		return birthCountryExists;
	}
	public void setBirthCountryExists(String birthCountryExists) {
		this.birthCountryExists = birthCountryExists;
	}
	public String getEthnicityExists() {
		return ethnicityExists;
	}
	public void setEthnicityExists(String ethnicityExists) {
		this.ethnicityExists = ethnicityExists;
	}
	public String getHomePhoneExists() {
		return homePhoneExists;
	}
	public void setHomePhoneExists(String homePhoneExists) {
		this.homePhoneExists = homePhoneExists;
	}
	public String getAddressExists() {
		return addressExists;
	}
	public void setAddressExists(String addressExists) {
		this.addressExists = addressExists;
	}
	
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	
	public String getHomePhone() {
		return homePhone;
	}
	public void setHomePhone(String homePhone) {
		this.homePhone = homePhone;
	}
	public String getCellPhone() {
		return cellPhone;
	}
	public void setCellPhone(String cellPhone) {
		this.cellPhone = cellPhone;
	}
	public String getWorkPhone() {
		return workPhone;
	}
	public void setWorkPhone(String workPhone) {
		this.workPhone = workPhone;
	}
	public String getEmerPhone() {
		return emerPhone;
	}
	public void setEmerPhone(String emerPhone) {
		this.emerPhone = emerPhone;
	}
	public String getPager() {
		return pager;
	}
	public void setPager(String pager) {
		this.pager = pager;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getWeb() {
		return web;
	}
	public void setWeb(String web) {
		this.web = web;
	}
	public String getBadMergeFlag() {
		return badMergeFlag;
	}
	public void setBadMergeFlag(String badMergeFlag) {
		this.badMergeFlag = badMergeFlag;
	}
	
	
	public String getFNAME() {
		return FNAME;
	}
	public void setFNAME(String fNAME) {
		FNAME = fNAME;
	}
	public String getMNAME() {
		return MNAME;
	}
	public void setMNAME(String mNAME) {
		MNAME = mNAME;
	}
	public String getLNAME() {
		return LNAME;
	}
	public void setLNAME(String lNAME) {
		LNAME = lNAME;
	}
	public String getSUFFIX() {
		return SUFFIX;
	}
	public void setSUFFIX(String sUFFIX) {
		SUFFIX = sUFFIX;
	}
	public String getGENDER() {
		return GENDER;
	}
	public void setGENDER(String gENDER) {
		GENDER = gENDER;
	}
	public String getSSN() {
		return SSN;
	}
	public void setSSN(String sSN) {
		SSN = sSN;
	}
	public String getNYSID() {
		return NYSID;
	}
	public void setNYSID(String nYSID) {
		NYSID = nYSID;
	}
	public String getDIN() {
		return DIN;
	}
	public void setDIN(String dIN) {
		DIN = dIN;
	}
	public String getMED_RECORD_NUMBER() {
		return MED_RECORD_NUMBER;
	}
	public void setMED_RECORD_NUMBER(String mED_RECORD_NUMBER) {
		MED_RECORD_NUMBER = mED_RECORD_NUMBER;
	}
	public String getINSURANCE_NUMBER() {
		return INSURANCE_NUMBER;
	}
	public void setINSURANCE_NUMBER(String iNSURANCE_NUMBER) {
		INSURANCE_NUMBER = iNSURANCE_NUMBER;
	}
	public String getDOB() {
		return DOB;
	}
	public void setDOB(String dOB) {
		DOB = dOB;
	}
	public String getBIRTH_COUNTRY() {
		return BIRTH_COUNTRY;
	}
	public void setBIRTH_COUNTRY(String bIRTH_COUNTRY) {
		BIRTH_COUNTRY = bIRTH_COUNTRY;
	}
	public String getETHNICITY() {
		return ETHNICITY;
	}
	public void setETHNICITY(String eTHNICITY) {
		ETHNICITY = eTHNICITY;
	}
	public String getPERSON_ADDRESS() {
		return PERSON_ADDRESS;
	}
	public void setPERSON_ADDRESS(String pERSON_ADDRESS) {
		PERSON_ADDRESS = pERSON_ADDRESS;
	}
	public String getPERSON_STATE() {
		return PERSON_STATE;
	}
	public void setPERSON_STATE(String pERSON_STATE) {
		PERSON_STATE = pERSON_STATE;
	}
	public String getPERSON_CITY() {
		return PERSON_CITY;
	}
	public void setPERSON_CITY(String pERSON_CITY) {
		PERSON_CITY = pERSON_CITY;
	}
	public String getPERSON_COUNTY() {
		return PERSON_COUNTY;
	}
	public void setPERSON_COUNTY(String pERSON_COUNTY) {
		PERSON_COUNTY = pERSON_COUNTY;
	}
	public String getPERSON_ZIP() {
		return PERSON_ZIP;
	}
	public void setPERSON_ZIP(String pERSON_ZIP) {
		PERSON_ZIP = pERSON_ZIP;
	}
	public String getPERSON_PLUS4() {
		return PERSON_PLUS4;
	}
	public void setPERSON_PLUS4(String pERSON_PLUS4) {
		PERSON_PLUS4 = pERSON_PLUS4;
	}
	public PersonRaceVO getPersonRaceVO() {
		return personRaceVO;
	}
	public void setPersonRaceVO(PersonRaceVO personRaceVO) {
		this.personRaceVO = personRaceVO;
	}
	public String getMEDICAID_NUMBER() {
		return MEDICAID_NUMBER;
	}
	public void setMEDICAID_NUMBER(String mEDICAID_NUMBER) {
		MEDICAID_NUMBER = mEDICAID_NUMBER;
	}
	public String getMEDICAID_NUMBER_EXISTS() {
		return MEDICAID_NUMBER_EXISTS;
	}
	public void setMEDICAID_NUMBER_EXISTS(String mEDICAID_NUMBER_EXISTS) {
		MEDICAID_NUMBER_EXISTS = mEDICAID_NUMBER_EXISTS;
	}
	public String getRecordSearchFlag() {
		return recordSearchFlag;
	}
	public void setRecordSearchFlag(String recordSearchFlag) {
		this.recordSearchFlag = recordSearchFlag;
	}
	public String getRecordSearchComments() {
		return recordSearchComments;
	}
	public void setRecordSearchComments(String recordSearchComments) {
		this.recordSearchComments = recordSearchComments;
	}
	public String getSentToCDC() {
		return sentToCDC;
	}
	public void setSentToCDC(String sentToCDC) {
		this.sentToCDC = sentToCDC;
	}
	public String getClusterIds() {
		return clusterIds;
	}
	public void setClusterIds(String clusterIds) {
		this.clusterIds = clusterIds;
	}
	public String getRasDocActive() {
		return rasDocActive;
	}
	public void setRasDocActive(String rasDocActive) {
		this.rasDocActive = rasDocActive;
	}
	public String getStatenoFlaggedEarlier() {
		return statenoFlaggedEarlier;
	}
	public void setStatenoFlaggedEarlier(String statenoFlaggedEarlier) {
		this.statenoFlaggedEarlier = statenoFlaggedEarlier;
	}
	public String getNonCaseDocStatus() {
		return nonCaseDocStatus;
	}
	public void setNonCaseDocStatus(String nonCaseDocStatus) {
		this.nonCaseDocStatus = nonCaseDocStatus;
	}
	public String getNonCaseHistory() {
		return nonCaseHistory;
	}
	public void setNonCaseHistory(String nonCaseHistory) {
		this.nonCaseHistory = nonCaseHistory;
	}
	public String getNYS_STAGE_ZERO_DX() {
		return NYS_STAGE_ZERO_DX;
	}
	public void setNYS_STAGE_ZERO_DX(String nYS_STAGE_ZERO_DX) {
		NYS_STAGE_ZERO_DX = nYS_STAGE_ZERO_DX;
	}
	public String getNYS_STAGE_CD4_OI_DX() {
		return NYS_STAGE_CD4_OI_DX;
	}
	public void setNYS_STAGE_CD4_OI_DX(String nYS_STAGE_CD4_OI_DX) {
		NYS_STAGE_CD4_OI_DX = nYS_STAGE_CD4_OI_DX;
	}
	public String getNYC_STAGE_ZERO_DX() {
		return NYC_STAGE_ZERO_DX;
	}
	public void setNYC_STAGE_ZERO_DX(String nYC_STAGE_ZERO_DX) {
		NYC_STAGE_ZERO_DX = nYC_STAGE_ZERO_DX;
	}
	public String getNYC_STAGE_CD4_OI_DX() {
		return NYC_STAGE_CD4_OI_DX;
	}
	public void setNYC_STAGE_CD4_OI_DX(String nYC_STAGE_CD4_OI_DX) {
		NYC_STAGE_CD4_OI_DX = nYC_STAGE_CD4_OI_DX;
	}
	
	
}
