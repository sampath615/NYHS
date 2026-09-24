package gov.ny.state.doh.bhae.nyehms.web.spring.domain.person;

import java.io.Serializable;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.NYEHMSDocVO;

/**
 * Author 	: Srikar Kummamuri
 * Date 	: 2/12/12
 * 
 * This object will hold the Person view details
 */

public class ClericalPersonViewVO extends NYEHMSDocVO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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
	private String cityNo = "";
	private String eharsNysStateno = "";
	private String eharsNycStateno = "";
	
	
	private String RACE_WHITE = "";
	private String RACE_BLACK = "";
	private String RACE_ASIAN = "";
	private String RACE_INDIAN = "";
	private String RACE_PACIFIC = "";
	private String RACE_MULTIPLE = "";
	private String RACE_UNKNOWN = "";
	private String RACE_OTHER = "";
	private String RACE_OTHER_TEXT = "";
	
	public String getStateNo() {
		return stateNo;
	}
	public void setStateNo(String stateNo) {
		this.stateNo = stateNo;
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
	public String getMEDICAID_NUMBER() {
		return MEDICAID_NUMBER;
	}
	public void setMEDICAID_NUMBER(String mEDICAID_NUMBER) {
		MEDICAID_NUMBER = mEDICAID_NUMBER;
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
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
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
	public String getCityNo() {
		return cityNo;
	}
	public void setCityNo(String cityNo) {
		this.cityNo = cityNo;
	}
	public String getEharsNysStateno() {
		return eharsNysStateno;
	}
	public void setEharsNysStateno(String eharsNysStateno) {
		this.eharsNysStateno = eharsNysStateno;
	}
	public String getEharsNycStateno() {
		return eharsNycStateno;
	}
	public void setEharsNycStateno(String eharsNycStateno) {
		this.eharsNycStateno = eharsNycStateno;
	}
	public String getRACE_WHITE() {
		return RACE_WHITE;
	}
	public void setRACE_WHITE(String rACE_WHITE) {
		RACE_WHITE = rACE_WHITE;
	}
	public String getRACE_BLACK() {
		return RACE_BLACK;
	}
	public void setRACE_BLACK(String rACE_BLACK) {
		RACE_BLACK = rACE_BLACK;
	}
	public String getRACE_ASIAN() {
		return RACE_ASIAN;
	}
	public void setRACE_ASIAN(String rACE_ASIAN) {
		RACE_ASIAN = rACE_ASIAN;
	}
	public String getRACE_INDIAN() {
		return RACE_INDIAN;
	}
	public void setRACE_INDIAN(String rACE_INDIAN) {
		RACE_INDIAN = rACE_INDIAN;
	}
	public String getRACE_PACIFIC() {
		return RACE_PACIFIC;
	}
	public void setRACE_PACIFIC(String rACE_PACIFIC) {
		RACE_PACIFIC = rACE_PACIFIC;
	}
	public String getRACE_MULTIPLE() {
		return RACE_MULTIPLE;
	}
	public void setRACE_MULTIPLE(String rACE_MULTIPLE) {
		RACE_MULTIPLE = rACE_MULTIPLE;
	}
	public String getRACE_UNKNOWN() {
		return RACE_UNKNOWN;
	}
	public void setRACE_UNKNOWN(String rACE_UNKNOWN) {
		RACE_UNKNOWN = rACE_UNKNOWN;
	}
	public String getRACE_OTHER() {
		return RACE_OTHER;
	}
	public void setRACE_OTHER(String rACE_OTHER) {
		RACE_OTHER = rACE_OTHER;
	}
	public String getRACE_OTHER_TEXT() {
		return RACE_OTHER_TEXT;
	}
	public void setRACE_OTHER_TEXT(String rACE_OTHER_TEXT) {
		RACE_OTHER_TEXT = rACE_OTHER_TEXT;
	}
	
		
	
}
