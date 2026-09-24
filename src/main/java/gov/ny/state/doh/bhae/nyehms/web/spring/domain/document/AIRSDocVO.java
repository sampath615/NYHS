package gov.ny.state.doh.bhae.nyehms.web.spring.domain.document;

import java.sql.Date;

/**
 * This class is POJO for AIRS document.
 *
 * @author vxp06
 */
public class AIRSDocVO extends NYEHMSDocVO {
	
	
	
	private String DOCUMENT_ID;
	private String STATENO;
	private Date EHARS_IMPORT_DATE;
	private Date NYC_SHIP_DATE;
	private String FNAME;
	private String MNAME;
	private String LNAME;
	private String ALT_NAME;
	private String DOB;
	private String SSN;
	private String AGE;
	private String BIRTH_COUNTRY;
	private String BIRTH_COUNTRY_OTHER;
	private String DOD_TEXT;
	private String ADDRESS;
	private String PERSON_CITY;
	private String PERSON_STATE;
	private String PERSON_ZIP;
	private String PERSON_COUNTY;
	private String PERSON_ADDRESS;
	private String PERSON_PHONE_TEXT;
	private String PERSON_PHONE;
	private String RACE_WHITE;
	private String RACE_BLACK;
	private String RACE_ASIAN;
	private String RACE_INDIAN;
	private String RACE_PACIFIC;
	private String RACE_OTHER;
	private String RACE_UNKNOWN;
	private String ETHNICITY;
	private String BIRTH_SEX;
	private String CURRENT_GENDER;
	private String GENDER;
	private String SWM;
	private String SWF;
	private String IDU;
	private String SEX_PARTNER_IDU;
	private String RECV_BLOOD_PROD;
	private String SEX_PARTNER_HIV;
	private String PERINATAL;
	private String RISK_OTHER;
	private String RISK_UNKNOWN;
	private String TC_ID;
	private String URN;
	private String MEDICAID_NUMBER;
	private String CASE_NO;
	private String MED_RECORD_NUMBER;
	private String FED_ID;
	private String DUNS_ID;
	private String ADMISSION_DATE_TEXT;
	private String RECV_BLOOD_PROD_DATE_TEXT;
	private String INIT_HIV_DIAG_DATE_TEXT;
	private String INFORMED_HIV_POS;
	private String INFORMED_HIV_DATE_TEXT;
	private String PREV_HIV_DIAG_DATE_TEXT;
	private String INIT_AIDS_DIAG;
	private String INIT_AIDS_DIAG_DATE_TEXT;
	private String PREV_AIDS_DIAG;
	private String PREV_AIDS_DIAG_DATE_TEXT;
	private Date PT_INTERVIEW_DATE;
	private Date FIRST_POS_HIV_DATE;
	private String ARV_6_MO;
	private String ARV_6_MO_NAME;
	private String ARV_6_MO_START_DATE_TEXT;
	private String ARV_6_MO_END_DATE_TEXT;
	private String ARV_CURRENT_TEST;
	private String FIRST_HIV_TEST_EVER_DATE_TEXT;
	private String NUMBER_TEST_2_YRS;
	private String EVER_TEST_NEG;
	private String LAST_NEG_DATE_TEXT;
	private String FACILITY_NAME;
	private String PROVIDER_DESCRIPTION;
	private String AGENCY_ID;
	private String PROV_ADDR;
	private String FACILITY_CITY;
	private String FACILITY_STATE;
	private String FACILITY_ZIP;
	private String FACILITY_COUNTY;
	private String FACILITY_ADDRESS;
	private String FACILITY_PHONE;
	private String FACILITY_PLUS4;
	private String DOCUMENT_SOURCE_TABLE;
	private String INIT_HIV_DIAG;
	private String PREV_HIV_DIAG;
	private String SIG_DATE_TEXT;
	private String NUM_PARTNERS;
	private String ARV_6_MO_NAME2;
	private String ARV_6_MO_NAME3;
	private String ARV_6_MO_NAME4;
	private Date AIRS_CALC_FILE_DATE;
	private String RECORD_TYPE;
	private String CTR_FLAG;
	private String IS_MEDICAID;
	private String PROVIDER_CD;
	private String EXTRALOGID;
	private String AIRS_VERSION;
	private String AIRS_VER_TYPE;
	private String dateOfDeath;
	
	
	private String birthSex;

	
	private int PARENT_DOCUMENT_ID;

	public int getPARENT_DOCUMENT_ID() {
		return PARENT_DOCUMENT_ID;
	}

	public void setPARENT_DOCUMENT_ID(int pARENT_DOCUMENT_ID) {
		PARENT_DOCUMENT_ID = pARENT_DOCUMENT_ID;
	}

	public String getDOCUMENT_ID() {
		return DOCUMENT_ID;
	}


	public void setDOCUMENT_ID(String dOCUMENT_ID) {
		DOCUMENT_ID = dOCUMENT_ID;
	}


	public String getSTATENO() {
		return STATENO;
	}


	public void setSTATENO(String sTATENO) {
		STATENO = sTATENO;
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


	public String getALT_NAME() {
		return ALT_NAME;
	}


	public void setALT_NAME(String aLT_NAME) {
		ALT_NAME = aLT_NAME;
	}


	public String getDOB() {
		return DOB;
	}


	public void setDOB(String dOB) {
		DOB = dOB;
	}


	public String getSSN() {
		return SSN;
	}


	public void setSSN(String sSN) {
		SSN = sSN;
	}


	public String getAGE() {
		return AGE;
	}


	public void setAGE(String aGE) {
		AGE = aGE;
	}


	public String getBIRTH_COUNTRY() {
		return BIRTH_COUNTRY;
	}


	public void setBIRTH_COUNTRY(String bIRTH_COUNTRY) {
		BIRTH_COUNTRY = bIRTH_COUNTRY;
	}
	
	


	public String getBIRTH_COUNTRY_OTHER() {
		return BIRTH_COUNTRY_OTHER;
	}

	public void setBIRTH_COUNTRY_OTHER(String bIRTH_COUNTRY_OTHER) {
		BIRTH_COUNTRY_OTHER = bIRTH_COUNTRY_OTHER;
	}

	public String getDOD_TEXT() {
		return DOD_TEXT;
	}


	public void setDOD_TEXT(String dOD_TEXT) {
		DOD_TEXT = dOD_TEXT;
	}


	public String getADDRESS() {
		return ADDRESS;
	}


	public void setADDRESS(String aDDRESS) {
		ADDRESS = aDDRESS;
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


	public String getPERSON_COUNTY() {
		return PERSON_COUNTY;
	}


	public void setPERSON_COUNTY(String pERSON_COUNTY) {
		PERSON_COUNTY = pERSON_COUNTY;
	}


	public String getPERSON_ADDRESS() {
		return PERSON_ADDRESS;
	}


	public void setPERSON_ADDRESS(String pERSON_ADDRESS) {
		PERSON_ADDRESS = pERSON_ADDRESS;
	}


	public String getPERSON_PHONE_TEXT() {
		return PERSON_PHONE_TEXT;
	}


	public String getPERSON_PHONE() {
		return PERSON_PHONE;
	}

	public void setPERSON_PHONE(String pERSON_PHONE) {
		PERSON_PHONE = pERSON_PHONE;
	}

	public void setPERSON_PHONE_TEXT(String pERSON_PHONE_TEXT) {
		PERSON_PHONE_TEXT = pERSON_PHONE_TEXT;
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


	public String getETHNICITY() {
		return ETHNICITY;
	}


	public void setETHNICITY(String eTHNICITY) {
		ETHNICITY = eTHNICITY;
	}


	public String getBIRTH_SEX() {
		return BIRTH_SEX;
	}


	public void setBIRTH_SEX(String bIRTH_SEX) {
		BIRTH_SEX = bIRTH_SEX;
	}


	public String getCURRENT_GENDER() {
		return CURRENT_GENDER;
	}


	public void setCURRENT_GENDER(String cURRENT_GENDER) {
		CURRENT_GENDER = cURRENT_GENDER;
	}


	public String getGENDER() {
		return GENDER;
	}


	public void setGENDER(String gENDER) {
		GENDER = gENDER;
	}


	public String getSWM() {
		return SWM;
	}


	public void setSWM(String sWM) {
		SWM = sWM;
	}


	public String getSWF() {
		return SWF;
	}


	public void setSWF(String sWF) {
		SWF = sWF;
	}


	public String getIDU() {
		return IDU;
	}


	public void setIDU(String iDU) {
		IDU = iDU;
	}


	public String getSEX_PARTNER_IDU() {
		return SEX_PARTNER_IDU;
	}


	public void setSEX_PARTNER_IDU(String sEX_PARTNER_IDU) {
		SEX_PARTNER_IDU = sEX_PARTNER_IDU;
	}


	public String getRECV_BLOOD_PROD() {
		return RECV_BLOOD_PROD;
	}


	public void setRECV_BLOOD_PROD(String rECV_BLOOD_PROD) {
		RECV_BLOOD_PROD = rECV_BLOOD_PROD;
	}


	public String getSEX_PARTNER_HIV() {
		return SEX_PARTNER_HIV;
	}


	public void setSEX_PARTNER_HIV(String sEX_PARTNER_HIV) {
		SEX_PARTNER_HIV = sEX_PARTNER_HIV;
	}


	public String getPERINATAL() {
		return PERINATAL;
	}


	public void setPERINATAL(String pERINATAL) {
		PERINATAL = pERINATAL;
	}


	public String getRISK_OTHER() {
		return RISK_OTHER;
	}


	public void setRISK_OTHER(String rISK_OTHER) {
		RISK_OTHER = rISK_OTHER;
	}


	public String getRISK_UNKNOWN() {
		return RISK_UNKNOWN;
	}


	public void setRISK_UNKNOWN(String rISK_UNKNOWN) {
		RISK_UNKNOWN = rISK_UNKNOWN;
	}


	public String getTC_ID() {
		return TC_ID;
	}


	public void setTC_ID(String tC_ID) {
		TC_ID = tC_ID;
	}


	public String getURN() {
		return URN;
	}


	public void setURN(String uRN) {
		URN = uRN;
	}


	public String getMEDICAID_NUMBER() {
		return MEDICAID_NUMBER;
	}


	public void setMEDICAID_NUMBER(String mEDICAID_NUMBER) {
		MEDICAID_NUMBER = mEDICAID_NUMBER;
	}


	public String getCASE_NO() {
		return CASE_NO;
	}


	public void setCASE_NO(String cASE_NO) {
		CASE_NO = cASE_NO;
	}


	public String getMED_RECORD_NUMBER() {
		return MED_RECORD_NUMBER;
	}


	public void setMED_RECORD_NUMBER(String mED_RECORD_NUMBER) {
		MED_RECORD_NUMBER = mED_RECORD_NUMBER;
	}


	public String getFED_ID() {
		return FED_ID;
	}


	public void setFED_ID(String fED_ID) {
		FED_ID = fED_ID;
	}


	public String getDUNS_ID() {
		return DUNS_ID;
	}


	public void setDUNS_ID(String dUNS_ID) {
		DUNS_ID = dUNS_ID;
	}


	public String getADMISSION_DATE_TEXT() {
		return ADMISSION_DATE_TEXT;
	}


	public void setADMISSION_DATE_TEXT(String aDMISSION_DATE_TEXT) {
		ADMISSION_DATE_TEXT = aDMISSION_DATE_TEXT;
	}


	public String getRECV_BLOOD_PROD_DATE_TEXT() {
		return RECV_BLOOD_PROD_DATE_TEXT;
	}


	public void setRECV_BLOOD_PROD_DATE_TEXT(String rECV_BLOOD_PROD_DATE_TEXT) {
		RECV_BLOOD_PROD_DATE_TEXT = rECV_BLOOD_PROD_DATE_TEXT;
	}


	public String getINIT_HIV_DIAG_DATE_TEXT() {
		return INIT_HIV_DIAG_DATE_TEXT;
	}


	public void setINIT_HIV_DIAG_DATE_TEXT(String iNIT_HIV_DIAG_DATE_TEXT) {
		INIT_HIV_DIAG_DATE_TEXT = iNIT_HIV_DIAG_DATE_TEXT;
	}


	public String getINFORMED_HIV_POS() {
		return INFORMED_HIV_POS;
	}


	public void setINFORMED_HIV_POS(String iNFORMED_HIV_POS) {
		INFORMED_HIV_POS = iNFORMED_HIV_POS;
	}


	public String getINFORMED_HIV_DATE_TEXT() {
		return INFORMED_HIV_DATE_TEXT;
	}


	public void setINFORMED_HIV_DATE_TEXT(String iNFORMED_HIV_DATE_TEXT) {
		INFORMED_HIV_DATE_TEXT = iNFORMED_HIV_DATE_TEXT;
	}


	public String getPREV_HIV_DIAG_DATE_TEXT() {
		return PREV_HIV_DIAG_DATE_TEXT;
	}


	public void setPREV_HIV_DIAG_DATE_TEXT(String pREV_HIV_DIAG_DATE_TEXT) {
		PREV_HIV_DIAG_DATE_TEXT = pREV_HIV_DIAG_DATE_TEXT;
	}


	public String getINIT_AIDS_DIAG() {
		return INIT_AIDS_DIAG;
	}


	public void setINIT_AIDS_DIAG(String iNIT_AIDS_DIAG) {
		INIT_AIDS_DIAG = iNIT_AIDS_DIAG;
	}


	public String getINIT_AIDS_DIAG_DATE_TEXT() {
		return INIT_AIDS_DIAG_DATE_TEXT;
	}


	public void setINIT_AIDS_DIAG_DATE_TEXT(String iNIT_AIDS_DIAG_DATE_TEXT) {
		INIT_AIDS_DIAG_DATE_TEXT = iNIT_AIDS_DIAG_DATE_TEXT;
	}


	public String getPREV_AIDS_DIAG() {
		return PREV_AIDS_DIAG;
	}


	public void setPREV_AIDS_DIAG(String pREV_AIDS_DIAG) {
		PREV_AIDS_DIAG = pREV_AIDS_DIAG;
	}


	public String getPREV_AIDS_DIAG_DATE_TEXT() {
		return PREV_AIDS_DIAG_DATE_TEXT;
	}


	public void setPREV_AIDS_DIAG_DATE_TEXT(String pREV_AIDS_DIAG_DATE_TEXT) {
		PREV_AIDS_DIAG_DATE_TEXT = pREV_AIDS_DIAG_DATE_TEXT;
	}


	public Date getPT_INTERVIEW_DATE() {
		return PT_INTERVIEW_DATE;
	}


	public void setPT_INTERVIEW_DATE(Date pT_INTERVIEW_DATE) {
		PT_INTERVIEW_DATE = pT_INTERVIEW_DATE;
	}


	public Date getFIRST_POS_HIV_DATE() {
		return FIRST_POS_HIV_DATE;
	}


	public void setFIRST_POS_HIV_DATE(Date fIRST_POS_HIV_DATE) {
		FIRST_POS_HIV_DATE = fIRST_POS_HIV_DATE;
	}


	public String getARV_6_MO() {
		return ARV_6_MO;
	}


	public void setARV_6_MO(String aRV_6_MO) {
		ARV_6_MO = aRV_6_MO;
	}


	public String getARV_6_MO_NAME() {
		return ARV_6_MO_NAME;
	}


	public void setARV_6_MO_NAME(String aRV_6_MO_NAME) {
		ARV_6_MO_NAME = aRV_6_MO_NAME;
	}


	public String getARV_6_MO_START_DATE_TEXT() {
		return ARV_6_MO_START_DATE_TEXT;
	}


	public void setARV_6_MO_START_DATE_TEXT(String aRV_6_MO_START_DATE_TEXT) {
		ARV_6_MO_START_DATE_TEXT = aRV_6_MO_START_DATE_TEXT;
	}


	public String getARV_6_MO_END_DATE_TEXT() {
		return ARV_6_MO_END_DATE_TEXT;
	}


	public void setARV_6_MO_END_DATE_TEXT(String aRV_6_MO_END_DATE_TEXT) {
		ARV_6_MO_END_DATE_TEXT = aRV_6_MO_END_DATE_TEXT;
	}


	public String getARV_CURRENT_TEST() {
		return ARV_CURRENT_TEST;
	}


	public void setARV_CURRENT_TEST(String aRV_CURRENT_TEST) {
		ARV_CURRENT_TEST = aRV_CURRENT_TEST;
	}


	public String getFIRST_HIV_TEST_EVER_DATE_TEXT() {
		return FIRST_HIV_TEST_EVER_DATE_TEXT;
	}


	public void setFIRST_HIV_TEST_EVER_DATE_TEXT(
			String fIRST_HIV_TEST_EVER_DATE_TEXT) {
		FIRST_HIV_TEST_EVER_DATE_TEXT = fIRST_HIV_TEST_EVER_DATE_TEXT;
	}


	public String getNUMBER_TEST_2_YRS() {
		return NUMBER_TEST_2_YRS;
	}


	public void setNUMBER_TEST_2_YRS(String nUMBER_TEST_2_YRS) {
		NUMBER_TEST_2_YRS = nUMBER_TEST_2_YRS;
	}


	public String getEVER_TEST_NEG() {
		return EVER_TEST_NEG;
	}


	public void setEVER_TEST_NEG(String eVER_TEST_NEG) {
		EVER_TEST_NEG = eVER_TEST_NEG;
	}


	public String getLAST_NEG_DATE_TEXT() {
		return LAST_NEG_DATE_TEXT;
	}


	public void setLAST_NEG_DATE_TEXT(String lAST_NEG_DATE_TEXT) {
		LAST_NEG_DATE_TEXT = lAST_NEG_DATE_TEXT;
	}


	public String getFACILITY_NAME() {
		return FACILITY_NAME;
	}


	public void setFACILITY_NAME(String fACILITY_NAME) {
		FACILITY_NAME = fACILITY_NAME;
	}


	public String getPROVIDER_DESCRIPTION() {
		return PROVIDER_DESCRIPTION;
	}


	public void setPROVIDER_DESCRIPTION(String pROVIDER_DESCRIPTION) {
		PROVIDER_DESCRIPTION = pROVIDER_DESCRIPTION;
	}


	public String getAGENCY_ID() {
		return AGENCY_ID;
	}


	public void setAGENCY_ID(String aGENCY_ID) {
		AGENCY_ID = aGENCY_ID;
	}


	public String getPROV_ADDR() {
		return PROV_ADDR;
	}


	public void setPROV_ADDR(String pROV_ADDR) {
		PROV_ADDR = pROV_ADDR;
	}


	public String getFACILITY_CITY() {
		return FACILITY_CITY;
	}


	public void setFACILITY_CITY(String fACILITY_CITY) {
		FACILITY_CITY = fACILITY_CITY;
	}


	public String getFACILITY_STATE() {
		return FACILITY_STATE;
	}


	public void setFACILITY_STATE(String fACILITY_STATE) {
		FACILITY_STATE = fACILITY_STATE;
	}


	public String getFACILITY_ZIP() {
		return FACILITY_ZIP;
	}


	public void setFACILITY_ZIP(String fACILITY_ZIP) {
		FACILITY_ZIP = fACILITY_ZIP;
	}


	public String getFACILITY_COUNTY() {
		return FACILITY_COUNTY;
	}


	public void setFACILITY_COUNTY(String fACILITY_COUNTY) {
		FACILITY_COUNTY = fACILITY_COUNTY;
	}


	public String getFACILITY_ADDRESS() {
		return FACILITY_ADDRESS;
	}


	public void setFACILITY_ADDRESS(String fACILITY_ADDRESS) {
		FACILITY_ADDRESS = fACILITY_ADDRESS;
	}


	public String getFACILITY_PHONE() {
		return FACILITY_PHONE;
	}


	public void setFACILITY_PHONE(String fACILITY_PHONE) {
		FACILITY_PHONE = fACILITY_PHONE;
	}


	public String getFACILITY_PLUS4() {
		return FACILITY_PLUS4;
	}


	public void setFACILITY_PLUS4(String fACILITY_PLUS4) {
		FACILITY_PLUS4 = fACILITY_PLUS4;
	}


	public String getDOCUMENT_SOURCE_TABLE() {
		return DOCUMENT_SOURCE_TABLE;
	}


	public void setDOCUMENT_SOURCE_TABLE(String dOCUMENT_SOURCE_TABLE) {
		DOCUMENT_SOURCE_TABLE = dOCUMENT_SOURCE_TABLE;
	}


	public String getINIT_HIV_DIAG() {
		return INIT_HIV_DIAG;
	}


	public void setINIT_HIV_DIAG(String iNIT_HIV_DIAG) {
		INIT_HIV_DIAG = iNIT_HIV_DIAG;
	}


	public String getPREV_HIV_DIAG() {
		return PREV_HIV_DIAG;
	}


	public void setPREV_HIV_DIAG(String pREV_HIV_DIAG) {
		PREV_HIV_DIAG = pREV_HIV_DIAG;
	}


	public String getSIG_DATE_TEXT() {
		return SIG_DATE_TEXT;
	}


	public void setSIG_DATE_TEXT(String sIG_DATE_TEXT) {
		SIG_DATE_TEXT = sIG_DATE_TEXT;
	}


	public String getNUM_PARTNERS() {
		return NUM_PARTNERS;
	}


	public void setNUM_PARTNERS(String nUM_PARTNERS) {
		NUM_PARTNERS = nUM_PARTNERS;
	}


	public String getARV_6_MO_NAME2() {
		return ARV_6_MO_NAME2;
	}


	public void setARV_6_MO_NAME2(String aRV_6_MO_NAME2) {
		ARV_6_MO_NAME2 = aRV_6_MO_NAME2;
	}


	public String getARV_6_MO_NAME3() {
		return ARV_6_MO_NAME3;
	}


	public void setARV_6_MO_NAME3(String aRV_6_MO_NAME3) {
		ARV_6_MO_NAME3 = aRV_6_MO_NAME3;
	}


	public String getARV_6_MO_NAME4() {
		return ARV_6_MO_NAME4;
	}


	public void setARV_6_MO_NAME4(String aRV_6_MO_NAME4) {
		ARV_6_MO_NAME4 = aRV_6_MO_NAME4;
	}


	public Date getAIRS_CALC_FILE_DATE() {
		return AIRS_CALC_FILE_DATE;
	}


	public void setAIRS_CALC_FILE_DATE(Date aIRS_CALC_FILE_DATE) {
		AIRS_CALC_FILE_DATE = aIRS_CALC_FILE_DATE;
	}


	public String getRECORD_TYPE() {
		return RECORD_TYPE;
	}


	public void setRECORD_TYPE(String rECORD_TYPE) {
		RECORD_TYPE = rECORD_TYPE;
	}


	public String getCTR_FLAG() {
		return CTR_FLAG;
	}


	public void setCTR_FLAG(String cTR_FLAG) {
		CTR_FLAG = cTR_FLAG;
	}


	public String getIS_MEDICAID() {
		return IS_MEDICAID;
	}


	public void setIS_MEDICAID(String iS_MEDICAID) {
		IS_MEDICAID = iS_MEDICAID;
	}


	public String getPROVIDER_CD() {
		return PROVIDER_CD;
	}


	public void setPROVIDER_CD(String pROVIDER_CD) {
		PROVIDER_CD = pROVIDER_CD;
	}


	public String getEXTRALOGID() {
		return EXTRALOGID;
	}


	public void setEXTRALOGID(String eXTRALOGID) {
		EXTRALOGID = eXTRALOGID;
	}


	public String getAIRS_VERSION() {
		return AIRS_VERSION;
	}


	public void setAIRS_VERSION(String aIRS_VERSION) {
		AIRS_VERSION = aIRS_VERSION;
	}


	public String getDateOfDeath() {
		return dateOfDeath;
	}


	public void setDateOfDeath(String dateOfDeath) {
		this.dateOfDeath = dateOfDeath;
	}


	public String getBirthSex() {
		return birthSex;
	}


	public void setBirthSex(String birthSex) {
		this.birthSex = birthSex;
	}
	
	private String DISCARD_STATUS;
	
	public String getDISCARD_STATUS() {
		return DISCARD_STATUS;
	}

	public void setDISCARD_STATUS(String dISCARD_STATUS) {
		DISCARD_STATUS = dISCARD_STATUS;;
	}

	public String getAIRS_VER_TYPE() {
		return AIRS_VER_TYPE;
	}

	public void setAIRS_VER_TYPE(String aIRS_VER_TYPE) {
		AIRS_VER_TYPE = aIRS_VER_TYPE;
	}
	
		
	
}
