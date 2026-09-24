package gov.ny.state.doh.bhae.nyehms.web.spring.domain.document;

import java.sql.Date;
import java.util.List;

public class DinDoccsDocVO extends NYEHMSDocVO{
	private long DOCUMENT_ID; 
	private String STATENO;
    private String FNAME; 
   private String LNAME;
   private String MNAME;
   private String DOB; 
   private String LSTREET;
   private String GENDER; 
   private String ETHNICITY; 
   private String DIN; 
   private String LATEST_RELEASE_DATE_TEXT ; 
   private String DOD_TEXT; 
   private String UNDER_CUSTODY ;
   private String PERSON_CITY; 
   private String PERSON_STATE; 
   private String PERSON_ZIP; 
   private String PERSON_PLUS4; 
   private String PERSON_COUNTY; 
   private String CURRENT_FACILITY_NAME ; 
   private String CURRENT_FACILITY_ADDRESS ; 
   private String CURRENT_FACILITY_CITY ; 
   private String CURRENT_FACILITY_STATE ; 
   private String CURRENT_FACILITY_ZIP ; 
   private String CURRENT_FACILITY_PLUS4 ; 
   private String CURRENT_FACILITY_COUNTY ; 
   private String CURRENT_FACILITY_PHONE ;
   private String OWNING_FACILITY_NAME;
   private String OWNING_FACILITY_ADDRESS;
   private String OWNING_FACILITY_CITY;
   private String OWNING_FACILITY_STATE;
   private String OWNING_FACILITY_ZIP;
   private String OWNING_FACILITY_PLUS4;
   private String OWNING_FACILITY_COUNTY;
   private String OWNING_FACILITY_PHONE;
   
   
   private String RACE_INDIAN; 
   private String RACE_BLACK; 
   private String RACE_ASIAN; 
   private String RACE_PACIFIC; 
   private String RACE_WHITE; 
   private String RACE_MULTIPLE; 
   private String RACE_OTHER; 
   private String RACE_UNKNOWN; 
   private String SSN; 
   private String SUFFIX; 
   private String PERSON_ADDRESS ; 
   private Date EHARS_IMPORT_DATE;
   private Date NYC_SHIP_DATE ;
   
   private String LEGAL_PERSON_ADDRESS;
   private String LEGAL_PERSON_CITY; 
   private String LEGAL_PERSON_STATE;
   private String LEGAL_PERSON_ZIP;
   private String LEGAL_PERSON_COUNTY_NAME; 
   private String  STREET;
   private String MEDICAID_NUMBER;
   
	private String NYSID;
   
	
	private String UNDER_SUPERVISION;
	private String BIRTH_COUNTRY;
	private String ENTERED_US_DATE_TEXT;
	private String ADMIN_DATE_TEXT;
	private String LATEST_ADMIN_DATE_TEXT;
	private String EARLIEST_RELEASE_DATE_TEXT;
	private String LATEST_DISCHARGE_DATE_TEXT;
	
	private String PREV_STREET;
	private String PREV_PERSON_ADDRESS;
	private String PREV_PERSON_CITY;
	private String PREV_PERSON_STATE;
	private String PREV_PERSON_ZIP;
	
	private String DRUG1;
	private String DRUG2;
	private String DRUG3;
	private String DOCCS_FILE;
	private String ALIEN_STATUS;
	private String RESCO;
	private String LRELDA;
	private String PAROLE_RELEASE_COUNTY;
	
	
	//Clerical review field
	private String dateOfDeath;
	
   private List<DoccsProbCodesVO> doccsProbCodes; 
   
   
	public long getDOCUMENT_ID() {
		return DOCUMENT_ID;
	}
	public void setDOCUMENT_ID(long dOCUMENT_ID) {
		DOCUMENT_ID = dOCUMENT_ID;
	}
	public String getFNAME() {
		return FNAME;
	}
	public void setFNAME(String fNAME) {
		FNAME = fNAME;
	}
	public String getLNAME() {
		return LNAME;
	}
	public void setLNAME(String lNAME) {
		LNAME = lNAME;
	}
	public String getMNAME() {
		return MNAME;
	}
	public void setMNAME(String mNAME) {
		MNAME = mNAME;
	}
	
	public String getDOB() {
		return DOB;
	}
	public void setDOB(String dOB) {
		DOB = dOB;
	}
	
	public String getGENDER() {
		return GENDER;
	}
	public void setGENDER(String gENDER) {
		GENDER = gENDER;
	}
	public String getETHNICITY() {
		return ETHNICITY;
	}
	public void setETHNICITY(String eTHNICITY) {
		ETHNICITY = eTHNICITY;
	}
	public String getDIN() {
		return DIN;
	}
	public void setDIN(String dIN) {
		DIN = dIN;
	}
	public String getLATEST_RELEASE_DATE_TEXT() {
		return LATEST_RELEASE_DATE_TEXT;
	}
	public void setLATEST_RELEASE_DATE_TEXT(String lATEST_RELEASE_DATE_TEXT) {
		LATEST_RELEASE_DATE_TEXT = lATEST_RELEASE_DATE_TEXT;
	}
	public String getDOD_TEXT() {
		return DOD_TEXT;
	}
	public void setDOD_TEXT(String dOD_TEXT) {
		DOD_TEXT = dOD_TEXT;
	}
	public String getUNDER_CUSTODY() {
		return UNDER_CUSTODY;
	}
	public void setUNDER_CUSTODY(String uNDER_CUSTODY) {
		UNDER_CUSTODY = uNDER_CUSTODY;
	}
	public String getPERSON_CITY() {
		return PERSON_CITY;
	}
	public void setPERSON_CITY(String pERSON_CITY) {
		PERSON_CITY = pERSON_CITY;
	}
	public String getPERSON_STATE() {
		return PERSON_STATE;
	}
	public void setPERSON_STATE(String pERSON_STATE) {
		PERSON_STATE = pERSON_STATE;
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
	public String getPERSON_COUNTY() {
		return PERSON_COUNTY;
	}
	public void setPERSON_COUNTY(String pERSON_COUNTY) {
		PERSON_COUNTY = pERSON_COUNTY;
	}
	public String getCURRENT_FACILITY_NAME() {
		return CURRENT_FACILITY_NAME;
	}
	public void setCURRENT_FACILITY_NAME(String cURRENT_FACILITY_NAME) {
		CURRENT_FACILITY_NAME = cURRENT_FACILITY_NAME;
	}
	public String getCURRENT_FACILITY_ADDRESS() {
		return CURRENT_FACILITY_ADDRESS;
	}
	public void setCURRENT_FACILITY_ADDRESS(String cURRENT_FACILITY_ADDRESS) {
		CURRENT_FACILITY_ADDRESS = cURRENT_FACILITY_ADDRESS;
	}
	public String getCURRENT_FACILITY_CITY() {
		return CURRENT_FACILITY_CITY;
	}
	public void setCURRENT_FACILITY_CITY(String cURRENT_FACILITY_CITY) {
		CURRENT_FACILITY_CITY = cURRENT_FACILITY_CITY;
	}
	public String getCURRENT_FACILITY_STATE() {
		return CURRENT_FACILITY_STATE;
	}
	public void setCURRENT_FACILITY_STATE(String cURRENT_FACILITY_STATE) {
		CURRENT_FACILITY_STATE = cURRENT_FACILITY_STATE;
	}
	public String getCURRENT_FACILITY_ZIP() {
		return CURRENT_FACILITY_ZIP;
	}
	public void setCURRENT_FACILITY_ZIP(String cURRENT_FACILITY_ZIP) {
		CURRENT_FACILITY_ZIP = cURRENT_FACILITY_ZIP;
	}
	public String getCURRENT_FACILITY_PLUS4() {
		return CURRENT_FACILITY_PLUS4;
	}
	public void setCURRENT_FACILITY_PLUS4(String cURRENT_FACILITY_PLUS4) {
		CURRENT_FACILITY_PLUS4 = cURRENT_FACILITY_PLUS4;
	}
	public String getCURRENT_FACILITY_COUNTY() {
		return CURRENT_FACILITY_COUNTY;
	}
	public void setCURRENT_FACILITY_COUNTY(String cURRENT_FACILITY_COUNTY) {
		CURRENT_FACILITY_COUNTY = cURRENT_FACILITY_COUNTY;
	}
	public String getCURRENT_FACILITY_PHONE() {
		return CURRENT_FACILITY_PHONE;
	}
	public void setCURRENT_FACILITY_PHONE(String cURRENT_FACILITY_PHONE) {
		CURRENT_FACILITY_PHONE = cURRENT_FACILITY_PHONE;
	}
	public String getRACE_INDIAN() {
		return RACE_INDIAN;
	}
	public void setRACE_INDIAN(String rACE_INDIAN) {
		RACE_INDIAN = rACE_INDIAN;
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
	public String getRACE_PACIFIC() {
		return RACE_PACIFIC;
	}
	public void setRACE_PACIFIC(String rACE_PACIFIC) {
		RACE_PACIFIC = rACE_PACIFIC;
	}
	public String getRACE_WHITE() {
		return RACE_WHITE;
	}
	public void setRACE_WHITE(String rACE_WHITE) {
		RACE_WHITE = rACE_WHITE;
	}
	public String getRACE_MULTIPLE() {
		return RACE_MULTIPLE;
	}
	public void setRACE_MULTIPLE(String rACE_MULTIPLE) {
		RACE_MULTIPLE = rACE_MULTIPLE;
	}
	public String getRACE_OTHER() {
		return RACE_OTHER;
	}
	public void setRACE_OTHER(String rACE_OTHER) {
		RACE_OTHER = rACE_OTHER;
	}
	public String getRACE_UNKNOWN() {
		return RACE_UNKNOWN;
	}
	public void setRACE_UNKNOWN(String rACE_UNKNOWN) {
		RACE_UNKNOWN = rACE_UNKNOWN;
	}
	public String getSSN() {
		return SSN;
	}
	public void setSSN(String sSN) {
		SSN = sSN;
	}
	public String getOWNING_FACILITY_NAME() {
		return OWNING_FACILITY_NAME;
	}
	public void setOWNING_FACILITY_NAME(String oWNING_FACILITY_NAME) {
		OWNING_FACILITY_NAME = oWNING_FACILITY_NAME;
	}
	public String getSUFFIX() {
		return SUFFIX;
	}
	public void setSUFFIX(String sUFFIX) {
		SUFFIX = sUFFIX;
	}
	public String getPERSON_ADDRESS() {
		return PERSON_ADDRESS;
	}
	public void setPERSON_ADDRESS(String pERSON_ADDRESS) {
		PERSON_ADDRESS = pERSON_ADDRESS;
	}

	public Date getEHARS_IMPORT_DATE() {
		return EHARS_IMPORT_DATE;
	}
	public void setEHARS_IMPORT_DATE(Date eHARS_IMPORT_DATE) {
		EHARS_IMPORT_DATE = eHARS_IMPORT_DATE;
	}
	public Date getNYC_SHIP_DATE() {
		return NYC_SHIP_DATE;
	}
	public void setNYC_SHIP_DATE(Date nYC_SHIP_DATE) {
		NYC_SHIP_DATE = nYC_SHIP_DATE;
	}
	public List<DoccsProbCodesVO> getDoccsProbCodes() {
		return doccsProbCodes;
	}
	public void setDoccsProbCodes(List<DoccsProbCodesVO> doccsProbCodes) {
		this.doccsProbCodes = doccsProbCodes;
	}
	public String getLSTREET() {
		return LSTREET;
	}
	public void setLSTREET(String lSTREET) {
		LSTREET = lSTREET;
	}
	public String getLEGAL_PERSON_ADDRESS() {
		return LEGAL_PERSON_ADDRESS;
	}
	public void setLEGAL_PERSON_ADDRESS(String lEGAL_PERSON_ADDRESS) {
		LEGAL_PERSON_ADDRESS = lEGAL_PERSON_ADDRESS;
	}
	public String getLEGAL_PERSON_CITY() {
		return LEGAL_PERSON_CITY;
	}
	public void setLEGAL_PERSON_CITY(String lEGAL_PERSON_CITY) {
		LEGAL_PERSON_CITY = lEGAL_PERSON_CITY;
	}
	public String getLEGAL_PERSON_STATE() {
		return LEGAL_PERSON_STATE;
	}
	public void setLEGAL_PERSON_STATE(String lEGAL_PERSON_STATE) {
		LEGAL_PERSON_STATE = lEGAL_PERSON_STATE;
	}
	public String getLEGAL_PERSON_ZIP() {
		return LEGAL_PERSON_ZIP;
	}
	public void setLEGAL_PERSON_ZIP(String lEGAL_PERSON_ZIP) {
		LEGAL_PERSON_ZIP = lEGAL_PERSON_ZIP;
	}
	public String getLEGAL_PERSON_COUNTY_NAME() {
		return LEGAL_PERSON_COUNTY_NAME;
	}
	public void setLEGAL_PERSON_COUNTY_NAME(String lEGAL_PERSON_COUNTY_NAME) {
		LEGAL_PERSON_COUNTY_NAME = lEGAL_PERSON_COUNTY_NAME;
	}
	public String getSTREET() {
		return STREET;
	}
	public void setSTREET(String sTREET) {
		STREET = sTREET;
	}
	public String getMEDICAID_NUMBER() {
		return MEDICAID_NUMBER;
	}
	public void setMEDICAID_NUMBER(String mEDICAID_NUMBER) {
		MEDICAID_NUMBER = mEDICAID_NUMBER;
	}
	public String getOWNING_FACILITY_ADDRESS() {
		return OWNING_FACILITY_ADDRESS;
	}
	public void setOWNING_FACILITY_ADDRESS(String oWNING_FACILITY_ADDRESS) {
		OWNING_FACILITY_ADDRESS = oWNING_FACILITY_ADDRESS;
	}
	public String getOWNING_FACILITY_CITY() {
		return OWNING_FACILITY_CITY;
	}
	public void setOWNING_FACILITY_CITY(String oWNING_FACILITY_CITY) {
		OWNING_FACILITY_CITY = oWNING_FACILITY_CITY;
	}
	public String getOWNING_FACILITY_STATE() {
		return OWNING_FACILITY_STATE;
	}
	public void setOWNING_FACILITY_STATE(String oWNING_FACILITY_STATE) {
		OWNING_FACILITY_STATE = oWNING_FACILITY_STATE;
	}
	public String getOWNING_FACILITY_ZIP() {
		return OWNING_FACILITY_ZIP;
	}
	public void setOWNING_FACILITY_ZIP(String oWNING_FACILITY_ZIP) {
		OWNING_FACILITY_ZIP = oWNING_FACILITY_ZIP;
	}
	public String getOWNING_FACILITY_PLUS4() {
		return OWNING_FACILITY_PLUS4;
	}
	public void setOWNING_FACILITY_PLUS4(String oWNING_FACILITY_PLUS4) {
		OWNING_FACILITY_PLUS4 = oWNING_FACILITY_PLUS4;
	}
	public String getOWNING_FACILITY_COUNTY() {
		return OWNING_FACILITY_COUNTY;
	}
	public void setOWNING_FACILITY_COUNTY(String oWNING_FACILITY_COUNTY) {
		OWNING_FACILITY_COUNTY = oWNING_FACILITY_COUNTY;
	}
	public String getOWNING_FACILITY_PHONE() {
		return OWNING_FACILITY_PHONE;
	}
	public void setOWNING_FACILITY_PHONE(String oWNING_FACILITY_PHONE) {
		OWNING_FACILITY_PHONE = oWNING_FACILITY_PHONE;
	}
	public String getSTATENO() {
		return STATENO;
	}
	public void setSTATENO(String sTATENO) {
		STATENO = sTATENO;
	}
	public String getNYSID() {
		return NYSID;
	}
	public void setNYSID(String nYSID) {
		NYSID = nYSID;
	}
	public String getUNDER_SUPERVISION() {
		return UNDER_SUPERVISION;
	}
	public void setUNDER_SUPERVISION(String uNDER_SUPERVISION) {
		UNDER_SUPERVISION = uNDER_SUPERVISION;
	}
	public String getBIRTH_COUNTRY() {
		return BIRTH_COUNTRY;
	}
	public void setBIRTH_COUNTRY(String bIRTH_COUNTRY) {
		BIRTH_COUNTRY = bIRTH_COUNTRY;
	}
	public String getENTERED_US_DATE_TEXT() {
		return ENTERED_US_DATE_TEXT;
	}
	public void setENTERED_US_DATE_TEXT(String eNTERED_US_DATE_TEXT) {
		ENTERED_US_DATE_TEXT = eNTERED_US_DATE_TEXT;
	}
	public String getADMIN_DATE_TEXT() {
		return ADMIN_DATE_TEXT;
	}
	public void setADMIN_DATE_TEXT(String aDMIN_DATE_TEXT) {
		ADMIN_DATE_TEXT = aDMIN_DATE_TEXT;
	}
	public String getLATEST_ADMIN_DATE_TEXT() {
		return LATEST_ADMIN_DATE_TEXT;
	}
	public void setLATEST_ADMIN_DATE_TEXT(String lATEST_ADMIN_DATE_TEXT) {
		LATEST_ADMIN_DATE_TEXT = lATEST_ADMIN_DATE_TEXT;
	}
	public String getEARLIEST_RELEASE_DATE_TEXT() {
		return EARLIEST_RELEASE_DATE_TEXT;
	}
	public void setEARLIEST_RELEASE_DATE_TEXT(String eARLIEST_RELEASE_DATE_TEXT) {
		EARLIEST_RELEASE_DATE_TEXT = eARLIEST_RELEASE_DATE_TEXT;
	}
	public String getLATEST_DISCHARGE_DATE_TEXT() {
		return LATEST_DISCHARGE_DATE_TEXT;
	}
	public void setLATEST_DISCHARGE_DATE_TEXT(String lATEST_DISCHARGE_DATE_TEXT) {
		LATEST_DISCHARGE_DATE_TEXT = lATEST_DISCHARGE_DATE_TEXT;
	}
	public String getPREV_STREET() {
		return PREV_STREET;
	}
	public void setPREV_STREET(String pREV_STREET) {
		PREV_STREET = pREV_STREET;
	}
	public String getPREV_PERSON_ADDRESS() {
		return PREV_PERSON_ADDRESS;
	}
	public void setPREV_PERSON_ADDRESS(String pREV_PERSON_ADDRESS) {
		PREV_PERSON_ADDRESS = pREV_PERSON_ADDRESS;
	}
	public String getPREV_PERSON_CITY() {
		return PREV_PERSON_CITY;
	}
	public void setPREV_PERSON_CITY(String pREV_PERSON_CITY) {
		PREV_PERSON_CITY = pREV_PERSON_CITY;
	}
	public String getPREV_PERSON_STATE() {
		return PREV_PERSON_STATE;
	}
	public void setPREV_PERSON_STATE(String pREV_PERSON_STATE) {
		PREV_PERSON_STATE = pREV_PERSON_STATE;
	}
	public String getPREV_PERSON_ZIP() {
		return PREV_PERSON_ZIP;
	}
	public void setPREV_PERSON_ZIP(String pREV_PERSON_ZIP) {
		PREV_PERSON_ZIP = pREV_PERSON_ZIP;
	}
	public String getDRUG1() {
		return DRUG1;
	}
	public void setDRUG1(String dRUG1) {
		DRUG1 = dRUG1;
	}
	public String getDRUG2() {
		return DRUG2;
	}
	public void setDRUG2(String dRUG2) {
		DRUG2 = dRUG2;
	}
	public String getDRUG3() {
		return DRUG3;
	}
	public void setDRUG3(String dRUG3) {
		DRUG3 = dRUG3;
	}
	public String getALIEN_STATUS() {
		return ALIEN_STATUS;
	}
	public void setALIEN_STATUS(String aLIEN_STATUS) {
		ALIEN_STATUS = aLIEN_STATUS;
	}
	public String getDOCCS_FILE() {
		return DOCCS_FILE;
	}
	public void setDOCCS_FILE(String dOCCS_FILE) {
		DOCCS_FILE = dOCCS_FILE;
	}
	public String getRESCO() {
		return RESCO;
	}
	public void setRESCO(String rESCO) {
		RESCO = rESCO;
	}
	public String getLRELDA() {
		return LRELDA;
	}
	public void setLRELDA(String lRELDA) {
		LRELDA = lRELDA;
	}
	public String getDateOfDeath() {
		return dateOfDeath;
	}
	public void setDateOfDeath(String dateOfDeath) {
		this.dateOfDeath = dateOfDeath;
	}
	public String getPAROLE_RELEASE_COUNTY() {
		return PAROLE_RELEASE_COUNTY;
	}
	public void setPAROLE_RELEASE_COUNTY(String pAROLE_RELEASE_COUNTY) {
		PAROLE_RELEASE_COUNTY = pAROLE_RELEASE_COUNTY;
	}
	
	
	
	
		
		
	
			
		
}
