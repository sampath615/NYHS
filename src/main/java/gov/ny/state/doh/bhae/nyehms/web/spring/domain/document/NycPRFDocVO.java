package gov.ny.state.doh.bhae.nyehms.web.spring.domain.document;

import java.sql.Date;
import java.util.List;

/**
 * This class is used to create POJO for PRF doc.
 *
 * @author vxp06
 */
public class NycPRFDocVO extends NYEHMSDocVO {

	private	int  DOCUMENT_ID;
	private Long  PRF_SRC_ID;
	
	private String ALT_NAME;
	private String SSN;
	private String  SEX_PARTNER_HEMOPHILIAC;
	private String  HEALTHCARE_WORKER;
	
	private String PRFDoc="PrfTTH"; // this variable will give us edit value (PRF+TTH or TTH only)
	private String  FNAME;
	private String  MNAME;
	private String  LNAME;
	private String  DOB;
	private String  DOD_TEXT;
	private String  MED_RECORD_NUMBER;
	private String  DIN;
	private String  rawPersonAddress;
	private String  PERSON_CITY;
	private String  PERSON_STATE;
	private String  PERSON_ZIP;
	private String  PERSON_COUNTY;
	private String  PERSON_PHONE;
	private String  CURRENT_GENDER;
	private String  ETHNICITY;
	private String  RACE_WHITE;
	private String  RACE_BLACK;
	private String  RACE_ASIAN;
	private String  RACE_INDIAN;
	private String  RACE_PACIFIC;
	private String  RACE_UNKNOWN;
	private String  RACE_OTHER;
	private String  RACE_OTHER_TEXT;
	private String  BIRTH_COUNTRY;
	private String  SWM;
	private String  SWF;
	private String  IDU;
	private String  SEX_PARTNER_IDU;
	private String  SEX_PARTNER_HIV;
	private String  RECV_BLOOD_PROD;
	private String  PERINATAL;
	private String  RISK_OTHER;
	private String  RISK_UNKNOWN;
	private String  SEX_PARTNER_MSM;
	private String  RISK_OTHER_TEXT;
	private String  RECV_BLOOD_PROD_DATE_TEXT;
	private String  RECV_BLOOD_PROD_COUNTRY;
	private Date  ADMISSION_DATE;
	private String  INITIAL_HIV_DIAG;
	private String  INITIAL_HIV_DIAG_DATE_TEXT;
	private String  INFORMED_HIV_POS;
	private String  INFORMED_HIV_DATE_TEXT;
	private String  PREVIOUS_HIV_DIAG;
	private String  PREV_HIV_DIAG_DATE_TEXT;
	private String  INITIAL_AIDS_DIAG;
	private String  INIT_AIDS_DIAG_DATE_TEXT;
	private String  PREVIOUS_AIDS_DIAG;
	private String  PREV_AIDS_DIAG_DATE_TEXT;
	private String  PS_REQUESTED;
	private Date  COMPLETED_DATE;
	private String  CONTACT_LNAME;
	private String  CONTACT_FNAME;
	private String  CONTACT_MNAME;
	private String  CONTACT_TITLE;
	private String  CONTACT_PHONE;
	private String  PROV_LNAME;
	private String  PROV_FNAME;
	private String  PROV_MI;
	private String  CREATE_UID;
	private Date  CREATE_DATE;
	private String  REVIEW_UID;
	private Date  REVIEW_DATE;
	private String  STATENO;
	private String  PERSON_ADDRESS;
	private Date  EHARS_IMPORT_DATE;
	    
	private String tthMainTTHSource;
	private String PT_INTERVIEW_DATE;
	private String FIRST_POS_HIV_DATE;
	private String FIRST_POS_ANON;
	private String FIRST_POS_FAC_NAME;
	private String FIRST_POS_FAC_STATE;
	private String RSON_FIRST_POS_EXP;
	private String RSON_FIRST_POS_ROUTN;
	private String RSON_FIRST_POS_CHKNEG;
	private String RSON_FIRST_POS_REQD;
	private String RSON_FIRST_POS_PRTNR;
	private String RSON_FIRST_POS_OTHER;
	private String RSON_FIRST_POS_OTHER_TEXT;
	private String ARV_6_MO;
	private String ARV_6_MO_NAME;
	private String ARV_6_MO_START_DATE_TEXT;
	private String ARV_6_MO_END_DATE_TEXT;
	private String ARV_CURRENT_TEST;
	private String FIRST_HIV_TEST_EVER_DATE_TEXT;
	private String NUMBER_TEST_2_YRS;
	private String EVER_TEST_NEG;
	private String LAST_NEG_DATE_TEXT;
	private String LAST_NEG_FAC_TYPE;
	private String LAST_NEG_FAC_NAME;
	private String LAST_NEG_FAC_STATE;
	private String tthEharsImportDate;
	private String PROV_DEPARTMENT;
	private String PROV_ADDRESS;
	private String PROV_CITY;
	private String PROV_STATE;
	private String PROV_COUNTY;	
	private String PROV_ZIP;
	private String PROV_PHONE;
	private String PROV_EXT;
	private String PRO_EMAIL;
	
	private String FIELD_PROC_DATE;
	private String APARTMENT;
	private String SEX_ORN;
	private String SEX_ORN_ADD_ID;
	private String NUM_NEG_HIV_TEST;

    //List<TTHDocumentVO> tTHDocumentVOList;
    //List<TTHDocumentVO> tTHDocumentVOListOrigional; // we are saving TTH doc list 2 times
    // since we will have to compare if the TTH document was changed or not         
    List<NycprfPartner> partnerList;



	public int getDOCUMENT_ID() {
		return DOCUMENT_ID;
	}



	public void setDOCUMENT_ID(int dOCUMENT_ID) {
		DOCUMENT_ID = dOCUMENT_ID;
	}



	public Long getPRF_SRC_ID() {
		return PRF_SRC_ID;
	}



	public void setPRF_SRC_ID(Long pRF_SRC_ID) {
		PRF_SRC_ID = pRF_SRC_ID;
	}



	public String getALT_NAME() {
		return ALT_NAME;
	}



	public void setALT_NAME(String aLT_NAME) {
		ALT_NAME = aLT_NAME;
	}



	public String getSSN() {
		return SSN;
	}



	public void setSSN(String sSN) {
		SSN = sSN;
	}



	public String getSEX_PARTNER_HEMOPHILIAC() {
		return SEX_PARTNER_HEMOPHILIAC;
	}



	public void setSEX_PARTNER_HEMOPHILIAC(String sEX_PARTNER_HEMOPHILIAC) {
		SEX_PARTNER_HEMOPHILIAC = sEX_PARTNER_HEMOPHILIAC;
	}



	public String getHEALTHCARE_WORKER() {
		return HEALTHCARE_WORKER;
	}



	public void setHEALTHCARE_WORKER(String hEALTHCARE_WORKER) {
		HEALTHCARE_WORKER = hEALTHCARE_WORKER;
	}



	public String getPRFDoc() {
		return PRFDoc;
	}



	public void setPRFDoc(String pRFDoc) {
		PRFDoc = pRFDoc;
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



	public String getDOB() {
		return DOB;
	}



	public void setDOB(String dOB) {
		DOB = dOB;
	}



	public String getDOD_TEXT() {
		return DOD_TEXT;
	}



	public void setDOD_TEXT(String dOD_TEXT) {
		DOD_TEXT = dOD_TEXT;
	}



	public String getMED_RECORD_NUMBER() {
		return MED_RECORD_NUMBER;
	}



	public void setMED_RECORD_NUMBER(String mED_RECORD_NUMBER) {
		MED_RECORD_NUMBER = mED_RECORD_NUMBER;
	}



	public String getDIN() {
		return DIN;
	}



	public void setDIN(String dIN) {
		DIN = dIN;
	}



	public String getRawPersonAddress() {
		return rawPersonAddress;
	}



	public void setRawPersonAddress(String rawPersonAddress) {
		this.rawPersonAddress = rawPersonAddress;
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



	public String getPERSON_PHONE() {
		return PERSON_PHONE;
	}



	public void setPERSON_PHONE(String pERSON_PHONE) {
		PERSON_PHONE = pERSON_PHONE;
	}



	public String getCURRENT_GENDER() {
		return CURRENT_GENDER;
	}



	public void setCURRENT_GENDER(String cURRENT_GENDER) {
		CURRENT_GENDER = cURRENT_GENDER;
	}



	public String getETHNICITY() {
		return ETHNICITY;
	}



	public void setETHNICITY(String eTHNICITY) {
		ETHNICITY = eTHNICITY;
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



	public String getBIRTH_COUNTRY() {
		return BIRTH_COUNTRY;
	}



	public void setBIRTH_COUNTRY(String bIRTH_COUNTRY) {
		BIRTH_COUNTRY = bIRTH_COUNTRY;
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



	public String getSEX_PARTNER_HIV() {
		return SEX_PARTNER_HIV;
	}



	public void setSEX_PARTNER_HIV(String sEX_PARTNER_HIV) {
		SEX_PARTNER_HIV = sEX_PARTNER_HIV;
	}



	public String getRECV_BLOOD_PROD() {
		return RECV_BLOOD_PROD;
	}



	public void setRECV_BLOOD_PROD(String rECV_BLOOD_PROD) {
		RECV_BLOOD_PROD = rECV_BLOOD_PROD;
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



	public String getSEX_PARTNER_MSM() {
		return SEX_PARTNER_MSM;
	}



	public void setSEX_PARTNER_MSM(String sEX_PARTNER_MSM) {
		SEX_PARTNER_MSM = sEX_PARTNER_MSM;
	}



	public String getRISK_OTHER_TEXT() {
		return RISK_OTHER_TEXT;
	}



	public void setRISK_OTHER_TEXT(String rISK_OTHER_TEXT) {
		RISK_OTHER_TEXT = rISK_OTHER_TEXT;
	}



	public String getRECV_BLOOD_PROD_DATE_TEXT() {
		return RECV_BLOOD_PROD_DATE_TEXT;
	}



	public void setRECV_BLOOD_PROD_DATE_TEXT(String rECV_BLOOD_PROD_DATE_TEXT) {
		RECV_BLOOD_PROD_DATE_TEXT = rECV_BLOOD_PROD_DATE_TEXT;
	}



	public String getRECV_BLOOD_PROD_COUNTRY() {
		return RECV_BLOOD_PROD_COUNTRY;
	}



	public void setRECV_BLOOD_PROD_COUNTRY(String rECV_BLOOD_PROD_COUNTRY) {
		RECV_BLOOD_PROD_COUNTRY = rECV_BLOOD_PROD_COUNTRY;
	}



	public Date getADMISSION_DATE() {
		return ADMISSION_DATE;
	}



	public void setADMISSION_DATE(Date aDMISSION_DATE) {
		ADMISSION_DATE = aDMISSION_DATE;
	}



	public String getINITIAL_HIV_DIAG() {
		return INITIAL_HIV_DIAG;
	}



	public void setINITIAL_HIV_DIAG(String iNITIAL_HIV_DIAG) {
		INITIAL_HIV_DIAG = iNITIAL_HIV_DIAG;
	}



	public String getINITIAL_HIV_DIAG_DATE_TEXT() {
		return INITIAL_HIV_DIAG_DATE_TEXT;
	}



	public void setINITIAL_HIV_DIAG_DATE_TEXT(String iNITIAL_HIV_DIAG_DATE_TEXT) {
		INITIAL_HIV_DIAG_DATE_TEXT = iNITIAL_HIV_DIAG_DATE_TEXT;
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



	public String getPREVIOUS_HIV_DIAG() {
		return PREVIOUS_HIV_DIAG;
	}



	public void setPREVIOUS_HIV_DIAG(String pREVIOUS_HIV_DIAG) {
		PREVIOUS_HIV_DIAG = pREVIOUS_HIV_DIAG;
	}



	public String getPREV_HIV_DIAG_DATE_TEXT() {
		return PREV_HIV_DIAG_DATE_TEXT;
	}



	public void setPREV_HIV_DIAG_DATE_TEXT(String pREV_HIV_DIAG_DATE_TEXT) {
		PREV_HIV_DIAG_DATE_TEXT = pREV_HIV_DIAG_DATE_TEXT;
	}



	public String getINITIAL_AIDS_DIAG() {
		return INITIAL_AIDS_DIAG;
	}



	public void setINITIAL_AIDS_DIAG(String iNITIAL_AIDS_DIAG) {
		INITIAL_AIDS_DIAG = iNITIAL_AIDS_DIAG;
	}



	public String getINIT_AIDS_DIAG_DATE_TEXT() {
		return INIT_AIDS_DIAG_DATE_TEXT;
	}



	public void setINIT_AIDS_DIAG_DATE_TEXT(String iNIT_AIDS_DIAG_DATE_TEXT) {
		INIT_AIDS_DIAG_DATE_TEXT = iNIT_AIDS_DIAG_DATE_TEXT;
	}



	public String getPREVIOUS_AIDS_DIAG() {
		return PREVIOUS_AIDS_DIAG;
	}



	public void setPREVIOUS_AIDS_DIAG(String pREVIOUS_AIDS_DIAG) {
		PREVIOUS_AIDS_DIAG = pREVIOUS_AIDS_DIAG;
	}



	public String getPREV_AIDS_DIAG_DATE_TEXT() {
		return PREV_AIDS_DIAG_DATE_TEXT;
	}



	public void setPREV_AIDS_DIAG_DATE_TEXT(String pREV_AIDS_DIAG_DATE_TEXT) {
		PREV_AIDS_DIAG_DATE_TEXT = pREV_AIDS_DIAG_DATE_TEXT;
	}



	public String getPS_REQUESTED() {
		return PS_REQUESTED;
	}



	public void setPS_REQUESTED(String pS_REQUESTED) {
		PS_REQUESTED = pS_REQUESTED;
	}



	public Date getCOMPLETED_DATE() {
		return COMPLETED_DATE;
	}



	public void setCOMPLETED_DATE(Date cOMPLETED_DATE) {
		COMPLETED_DATE = cOMPLETED_DATE;
	}



	public String getCONTACT_LNAME() {
		return CONTACT_LNAME;
	}



	public void setCONTACT_LNAME(String cONTACT_LNAME) {
		CONTACT_LNAME = cONTACT_LNAME;
	}



	public String getCONTACT_FNAME() {
		return CONTACT_FNAME;
	}



	public void setCONTACT_FNAME(String cONTACT_FNAME) {
		CONTACT_FNAME = cONTACT_FNAME;
	}



	public String getCONTACT_MNAME() {
		return CONTACT_MNAME;
	}



	public void setCONTACT_MNAME(String cONTACT_MNAME) {
		CONTACT_MNAME = cONTACT_MNAME;
	}



	public String getCONTACT_TITLE() {
		return CONTACT_TITLE;
	}



	public void setCONTACT_TITLE(String cONTACT_TITLE) {
		CONTACT_TITLE = cONTACT_TITLE;
	}



	public String getCONTACT_PHONE() {
		return CONTACT_PHONE;
	}



	public void setCONTACT_PHONE(String cONTACT_PHONE) {
		CONTACT_PHONE = cONTACT_PHONE;
	}



	public String getPROV_LNAME() {
		return PROV_LNAME;
	}



	public void setPROV_LNAME(String pROV_LNAME) {
		PROV_LNAME = pROV_LNAME;
	}



	public String getPROV_FNAME() {
		return PROV_FNAME;
	}



	public void setPROV_FNAME(String pROV_FNAME) {
		PROV_FNAME = pROV_FNAME;
	}



	public String getPROV_MI() {
		return PROV_MI;
	}



	public void setPROV_MI(String pROV_MI) {
		PROV_MI = pROV_MI;
	}



	public String getCREATE_UID() {
		return CREATE_UID;
	}



	public void setCREATE_UID(String cREATE_UID) {
		CREATE_UID = cREATE_UID;
	}



	public Date getCREATE_DATE() {
		return CREATE_DATE;
	}



	public void setCREATE_DATE(Date cREATE_DATE) {
		CREATE_DATE = cREATE_DATE;
	}



	public String getREVIEW_UID() {
		return REVIEW_UID;
	}



	public void setREVIEW_UID(String rEVIEW_UID) {
		REVIEW_UID = rEVIEW_UID;
	}



	public Date getREVIEW_DATE() {
		return REVIEW_DATE;
	}



	public void setREVIEW_DATE(Date rEVIEW_DATE) {
		REVIEW_DATE = rEVIEW_DATE;
	}



	public String getSTATENO() {
		return STATENO;
	}



	public void setSTATENO(String sTATENO) {
		STATENO = sTATENO;
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



	public String getTthMainTTHSource() {
		return tthMainTTHSource;
	}



	public void setTthMainTTHSource(String tthMainTTHSource) {
		this.tthMainTTHSource = tthMainTTHSource;
	}



	public String getPT_INTERVIEW_DATE() {
		return PT_INTERVIEW_DATE;
	}



	public void setPT_INTERVIEW_DATE(String pT_INTERVIEW_DATE) {
		PT_INTERVIEW_DATE = pT_INTERVIEW_DATE;
	}



	public String getFIRST_POS_HIV_DATE() {
		return FIRST_POS_HIV_DATE;
	}



	public void setFIRST_POS_HIV_DATE(String fIRST_POS_HIV_DATE) {
		FIRST_POS_HIV_DATE = fIRST_POS_HIV_DATE;
	}



	public String getFIRST_POS_ANON() {
		return FIRST_POS_ANON;
	}



	public void setFIRST_POS_ANON(String fIRST_POS_ANON) {
		FIRST_POS_ANON = fIRST_POS_ANON;
	}



	public String getFIRST_POS_FAC_NAME() {
		return FIRST_POS_FAC_NAME;
	}



	public void setFIRST_POS_FAC_NAME(String fIRST_POS_FAC_NAME) {
		FIRST_POS_FAC_NAME = fIRST_POS_FAC_NAME;
	}



	public String getFIRST_POS_FAC_STATE() {
		return FIRST_POS_FAC_STATE;
	}



	public void setFIRST_POS_FAC_STATE(String fIRST_POS_FAC_STATE) {
		FIRST_POS_FAC_STATE = fIRST_POS_FAC_STATE;
	}



	public String getRSON_FIRST_POS_EXP() {
		return RSON_FIRST_POS_EXP;
	}



	public void setRSON_FIRST_POS_EXP(String rSON_FIRST_POS_EXP) {
		RSON_FIRST_POS_EXP = rSON_FIRST_POS_EXP;
	}



	public String getRSON_FIRST_POS_ROUTN() {
		return RSON_FIRST_POS_ROUTN;
	}



	public void setRSON_FIRST_POS_ROUTN(String rSON_FIRST_POS_ROUTN) {
		RSON_FIRST_POS_ROUTN = rSON_FIRST_POS_ROUTN;
	}



	public String getRSON_FIRST_POS_CHKNEG() {
		return RSON_FIRST_POS_CHKNEG;
	}



	public void setRSON_FIRST_POS_CHKNEG(String rSON_FIRST_POS_CHKNEG) {
		RSON_FIRST_POS_CHKNEG = rSON_FIRST_POS_CHKNEG;
	}



	public String getRSON_FIRST_POS_REQD() {
		return RSON_FIRST_POS_REQD;
	}



	public void setRSON_FIRST_POS_REQD(String rSON_FIRST_POS_REQD) {
		RSON_FIRST_POS_REQD = rSON_FIRST_POS_REQD;
	}



	public String getRSON_FIRST_POS_PRTNR() {
		return RSON_FIRST_POS_PRTNR;
	}



	public void setRSON_FIRST_POS_PRTNR(String rSON_FIRST_POS_PRTNR) {
		RSON_FIRST_POS_PRTNR = rSON_FIRST_POS_PRTNR;
	}



	public String getRSON_FIRST_POS_OTHER() {
		return RSON_FIRST_POS_OTHER;
	}



	public void setRSON_FIRST_POS_OTHER(String rSON_FIRST_POS_OTHER) {
		RSON_FIRST_POS_OTHER = rSON_FIRST_POS_OTHER;
	}



	public String getRSON_FIRST_POS_OTHER_TEXT() {
		return RSON_FIRST_POS_OTHER_TEXT;
	}



	public void setRSON_FIRST_POS_OTHER_TEXT(String rSON_FIRST_POS_OTHER_TEXT) {
		RSON_FIRST_POS_OTHER_TEXT = rSON_FIRST_POS_OTHER_TEXT;
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



	public String getLAST_NEG_FAC_TYPE() {
		return LAST_NEG_FAC_TYPE;
	}



	public void setLAST_NEG_FAC_TYPE(String lAST_NEG_FAC_TYPE) {
		LAST_NEG_FAC_TYPE = lAST_NEG_FAC_TYPE;
	}



	public String getLAST_NEG_FAC_NAME() {
		return LAST_NEG_FAC_NAME;
	}



	public void setLAST_NEG_FAC_NAME(String lAST_NEG_FAC_NAME) {
		LAST_NEG_FAC_NAME = lAST_NEG_FAC_NAME;
	}



	public String getLAST_NEG_FAC_STATE() {
		return LAST_NEG_FAC_STATE;
	}



	public void setLAST_NEG_FAC_STATE(String lAST_NEG_FAC_STATE) {
		LAST_NEG_FAC_STATE = lAST_NEG_FAC_STATE;
	}



	public String getTthEharsImportDate() {
		return tthEharsImportDate;
	}



	public void setTthEharsImportDate(String tthEharsImportDate) {
		this.tthEharsImportDate = tthEharsImportDate;
	}



	public List<NycprfPartner> getPartnerList() {
		return partnerList;
	}



	public void setPartnerList(List<NycprfPartner> partnerList) {
		this.partnerList = partnerList;
	}


	private String DISCARD_STATUS;
	
	public String getDISCARD_STATUS() {
		return DISCARD_STATUS;
	}

	public void setDISCARD_STATUS(String dISCARD_STATUS) {
		DISCARD_STATUS = dISCARD_STATUS;;
	}



	public String getPROV_DEPARTMENT() {
		return PROV_DEPARTMENT;
	}



	public void setPROV_DEPARTMENT(String pROV_DEPARTMENT) {
		PROV_DEPARTMENT = pROV_DEPARTMENT;
	}



	public String getPROV_ADDRESS() {
		return PROV_ADDRESS;
	}



	public void setPROV_ADDRESS(String pROV_ADDRESS) {
		PROV_ADDRESS = pROV_ADDRESS;
	}



	public String getPROV_CITY() {
		return PROV_CITY;
	}



	public void setPROV_CITY(String pROV_CITY) {
		PROV_CITY = pROV_CITY;
	}



	public String getPROV_STATE() {
		return PROV_STATE;
	}



	public void setPROV_STATE(String pROV_STATE) {
		PROV_STATE = pROV_STATE;
	}



	public String getPROV_ZIP() {
		return PROV_ZIP;
	}



	public void setPROV_ZIP(String pROV_ZIP) {
		PROV_ZIP = pROV_ZIP;
	}



	public String getPROV_PHONE() {
		return PROV_PHONE;
	}



	public void setPROV_PHONE(String pROV_PHONE) {
		PROV_PHONE = pROV_PHONE;
	}



	public String getPROV_EXT() {
		return PROV_EXT;
	}



	public void setPROV_EXT(String pROV_EXT) {
		PROV_EXT = pROV_EXT;
	}



	public String getPRO_EMAIL() {
		return PRO_EMAIL;
	}



	public void setPRO_EMAIL(String pRO_EMAIL) {
		PRO_EMAIL = pRO_EMAIL;
	}



	public String getPROV_COUNTY() {
		return PROV_COUNTY;
	}



	public void setPROV_COUNTY(String pROV_COUNTY) {
		PROV_COUNTY = pROV_COUNTY;
	}



	public String getFIELD_PROC_DATE() {
		return FIELD_PROC_DATE;
	}



	public void setFIELD_PROC_DATE(String fIELD_PROC_DATE) {
		FIELD_PROC_DATE = fIELD_PROC_DATE;
	}



	public String getAPARTMENT() {
		return APARTMENT;
	}



	public void setAPARTMENT(String aPARTMENT) {
		APARTMENT = aPARTMENT;
	}



	public String getSEX_ORN() {
		return SEX_ORN;
	}



	public void setSEX_ORN(String sEX_ORN) {
		SEX_ORN = sEX_ORN;
	}



	public String getSEX_ORN_ADD_ID() {
		return SEX_ORN_ADD_ID;
	}



	public void setSEX_ORN_ADD_ID(String sEX_ORN_ADD_ID) {
		SEX_ORN_ADD_ID = sEX_ORN_ADD_ID;
	}



	public String getNUM_NEG_HIV_TEST() {
		return NUM_NEG_HIV_TEST;
	}



	public void setNUM_NEG_HIV_TEST(String nUM_NEG_HIV_TEST) {
		NUM_NEG_HIV_TEST = nUM_NEG_HIV_TEST;
	}
	
	
	private String ACUTE_INFECTION;
	private String ADDITIONAL_GENDER_IDENTITY;
	private String ALT_LNAME;
	private String ALT_MNAME;
	private String ARV_PREVENPREP_MED_NAMES;
	private String ARV_PREVEN_PREP;
	private String ARV_PREVEN_PREP_END_DT;
	private String ARV_PREVEN_PREP_START_DT;
	private String ARV_TREATMENT;
	private String BIRTH_SEX;
	private String EDC;
	private String EVER_HAD_PREV_POS_TEST;
	private String FACILITY_TYPE;
	private String FACILITY_TYPE_OTHER_TEXT;
	private String MDPRINTNAME;
	private String PERSON_OTHER_IDENTIFIER;
	private String POST_EXP_ARV_PEP;
	private String POST_EXP_ARV_PEP_END_DT;
	private String POST_EXP_ARV_PEP_MED_NAMES;
	private String POST_EXP_ARV_PEP_START_DT;
	private String PREGNANT;
	private String PRIMARY_LANGUAGE;
	private String PRIMARY_LANGUAGE_OTHER;
	private String PRINTED_DATE;
	private String PROVIDER_NPI_NO;
	private String PROVIDER_NYS_LIC_NO;
	private String PROV_FACILITY;
	private String PROV_TITLE;
	private String PT_ARV_END_DATE_TEXT;
	private String PT_ARV_EVER_TAKEN;
	private String PT_ARV_NAME;
	private String PT_ARV_START_DATE_TEXT;
	private String REFERRED_HIV_PROVIDER;
	private String REFERRED_PROVIDER_NAME;
	private String REFFERED_PROVIDER_TELEPHONE;

	



	public String getACUTE_INFECTION() {
		return ACUTE_INFECTION;
	}
	public void setACUTE_INFECTION(String aCUTE_INFECTION) {
		ACUTE_INFECTION = aCUTE_INFECTION;
	}



	public String getADDITIONAL_GENDER_IDENTITY() {
		return ADDITIONAL_GENDER_IDENTITY;
	}



	public void setADDITIONAL_GENDER_IDENTITY(String aDDITIONAL_GENDER_IDENTITY) {
		ADDITIONAL_GENDER_IDENTITY = aDDITIONAL_GENDER_IDENTITY;
	}



	public String getALT_LNAME() {
		return ALT_LNAME;
	}



	public void setALT_LNAME(String aLT_LNAME) {
		ALT_LNAME = aLT_LNAME;
	}



	public String getALT_MNAME() {
		return ALT_MNAME;
	}



	public void setALT_MNAME(String aLT_MNAME) {
		ALT_MNAME = aLT_MNAME;
	}



	public String getARV_PREVENPREP_MED_NAMES() {
		return ARV_PREVENPREP_MED_NAMES;
	}



	public void setARV_PREVENPREP_MED_NAMES(String aRV_PREVENPREP_MED_NAMES) {
		ARV_PREVENPREP_MED_NAMES = aRV_PREVENPREP_MED_NAMES;
	}



	public String getARV_PREVEN_PREP() {
		return ARV_PREVEN_PREP;
	}



	public void setARV_PREVEN_PREP(String aRV_PREVEN_PREP) {
		ARV_PREVEN_PREP = aRV_PREVEN_PREP;
	}



	public String getARV_PREVEN_PREP_END_DT() {
		return ARV_PREVEN_PREP_END_DT;
	}



	public void setARV_PREVEN_PREP_END_DT(String aRV_PREVEN_PREP_END_DT) {
		ARV_PREVEN_PREP_END_DT = aRV_PREVEN_PREP_END_DT;
	}



	public String getARV_PREVEN_PREP_START_DT() {
		return ARV_PREVEN_PREP_START_DT;
	}



	public void setARV_PREVEN_PREP_START_DT(String aRV_PREVEN_PREP_START_DT) {
		ARV_PREVEN_PREP_START_DT = aRV_PREVEN_PREP_START_DT;
	}



	public String getARV_TREATMENT() {
		return ARV_TREATMENT;
	}



	public void setARV_TREATMENT(String aRV_TREATMENT) {
		ARV_TREATMENT = aRV_TREATMENT;
	}



	public String getBIRTH_SEX() {
		return BIRTH_SEX;
	}



	public void setBIRTH_SEX(String bIRTH_SEX) {
		BIRTH_SEX = bIRTH_SEX;
	}



	public String getEDC() {
		return EDC;
	}



	public void setEDC(String eDC) {
		EDC = eDC;
	}



	public String getEVER_HAD_PREV_POS_TEST() {
		return EVER_HAD_PREV_POS_TEST;
	}



	public void setEVER_HAD_PREV_POS_TEST(String eVER_HAD_PREV_POS_TEST) {
		EVER_HAD_PREV_POS_TEST = eVER_HAD_PREV_POS_TEST;
	}



	public String getFACILITY_TYPE() {
		return FACILITY_TYPE;
	}



	public void setFACILITY_TYPE(String fACILITY_TYPE) {
		FACILITY_TYPE = fACILITY_TYPE;
	}



	public String getFACILITY_TYPE_OTHER_TEXT() {
		return FACILITY_TYPE_OTHER_TEXT;
	}



	public void setFACILITY_TYPE_OTHER_TEXT(String fACILITY_TYPE_OTHER_TEXT) {
		FACILITY_TYPE_OTHER_TEXT = fACILITY_TYPE_OTHER_TEXT;
	}



	public String getMDPRINTNAME() {
		return MDPRINTNAME;
	}



	public void setMDPRINTNAME(String mDPRINTNAME) {
		MDPRINTNAME = mDPRINTNAME;
	}



	public String getPERSON_OTHER_IDENTIFIER() {
		return PERSON_OTHER_IDENTIFIER;
	}



	public void setPERSON_OTHER_IDENTIFIER(String pERSON_OTHER_IDENTIFIER) {
		PERSON_OTHER_IDENTIFIER = pERSON_OTHER_IDENTIFIER;
	}



	public String getPOST_EXP_ARV_PEP() {
		return POST_EXP_ARV_PEP;
	}



	public void setPOST_EXP_ARV_PEP(String pOST_EXP_ARV_PEP) {
		POST_EXP_ARV_PEP = pOST_EXP_ARV_PEP;
	}



	public String getPOST_EXP_ARV_PEP_END_DT() {
		return POST_EXP_ARV_PEP_END_DT;
	}



	public void setPOST_EXP_ARV_PEP_END_DT(String pOST_EXP_ARV_PEP_END_DT) {
		POST_EXP_ARV_PEP_END_DT = pOST_EXP_ARV_PEP_END_DT;
	}



	public String getPOST_EXP_ARV_PEP_MED_NAMES() {
		return POST_EXP_ARV_PEP_MED_NAMES;
	}



	public void setPOST_EXP_ARV_PEP_MED_NAMES(String pOST_EXP_ARV_PEP_MED_NAMES) {
		POST_EXP_ARV_PEP_MED_NAMES = pOST_EXP_ARV_PEP_MED_NAMES;
	}



	public String getPOST_EXP_ARV_PEP_START_DT() {
		return POST_EXP_ARV_PEP_START_DT;
	}



	public void setPOST_EXP_ARV_PEP_START_DT(String pOST_EXP_ARV_PEP_START_DT) {
		POST_EXP_ARV_PEP_START_DT = pOST_EXP_ARV_PEP_START_DT;
	}



	public String getPREGNANT() {
		return PREGNANT;
	}



	public void setPREGNANT(String pREGNANT) {
		PREGNANT = pREGNANT;
	}



	public String getPRIMARY_LANGUAGE() {
		return PRIMARY_LANGUAGE;
	}



	public void setPRIMARY_LANGUAGE(String pRIMARY_LANGUAGE) {
		PRIMARY_LANGUAGE = pRIMARY_LANGUAGE;
	}



	public String getPRIMARY_LANGUAGE_OTHER() {
		return PRIMARY_LANGUAGE_OTHER;
	}



	public void setPRIMARY_LANGUAGE_OTHER(String pRIMARY_LANGUAGE_OTHER) {
		PRIMARY_LANGUAGE_OTHER = pRIMARY_LANGUAGE_OTHER;
	}



	public String getPRINTED_DATE() {
		return PRINTED_DATE;
	}



	public void setPRINTED_DATE(String pRINTED_DATE) {
		PRINTED_DATE = pRINTED_DATE;
	}



	public String getPROVIDER_NPI_NO() {
		return PROVIDER_NPI_NO;
	}



	public void setPROVIDER_NPI_NO(String pROVIDER_NPI_NO) {
		PROVIDER_NPI_NO = pROVIDER_NPI_NO;
	}



	public String getPROVIDER_NYS_LIC_NO() {
		return PROVIDER_NYS_LIC_NO;
	}



	public void setPROVIDER_NYS_LIC_NO(String pROVIDER_NYS_LIC_NO) {
		PROVIDER_NYS_LIC_NO = pROVIDER_NYS_LIC_NO;
	}



	public String getPROV_FACILITY() {
		return PROV_FACILITY;
	}



	public void setPROV_FACILITY(String pROV_FACILITY) {
		PROV_FACILITY = pROV_FACILITY;
	}



	public String getPROV_TITLE() {
		return PROV_TITLE;
	}



	public void setPROV_TITLE(String pROV_TITLE) {
		PROV_TITLE = pROV_TITLE;
	}



	public String getPT_ARV_END_DATE_TEXT() {
		return PT_ARV_END_DATE_TEXT;
	}



	public void setPT_ARV_END_DATE_TEXT(String pT_ARV_END_DATE_TEXT) {
		PT_ARV_END_DATE_TEXT = pT_ARV_END_DATE_TEXT;
	}



	public String getPT_ARV_EVER_TAKEN() {
		return PT_ARV_EVER_TAKEN;
	}



	public void setPT_ARV_EVER_TAKEN(String pT_ARV_EVER_TAKEN) {
		PT_ARV_EVER_TAKEN = pT_ARV_EVER_TAKEN;
	}



	public String getPT_ARV_NAME() {
		return PT_ARV_NAME;
	}



	public void setPT_ARV_NAME(String pT_ARV_NAME) {
		PT_ARV_NAME = pT_ARV_NAME;
	}



	public String getPT_ARV_START_DATE_TEXT() {
		return PT_ARV_START_DATE_TEXT;
	}



	public void setPT_ARV_START_DATE_TEXT(String pT_ARV_START_DATE_TEXT) {
		PT_ARV_START_DATE_TEXT = pT_ARV_START_DATE_TEXT;
	}



	public String getREFERRED_HIV_PROVIDER() {
		return REFERRED_HIV_PROVIDER;
	}



	public void setREFERRED_HIV_PROVIDER(String rEFERRED_HIV_PROVIDER) {
		REFERRED_HIV_PROVIDER = rEFERRED_HIV_PROVIDER;
	}



	public String getREFERRED_PROVIDER_NAME() {
		return REFERRED_PROVIDER_NAME;
	}



	public void setREFERRED_PROVIDER_NAME(String rEFERRED_PROVIDER_NAME) {
		REFERRED_PROVIDER_NAME = rEFERRED_PROVIDER_NAME;
	}



	public String getREFFERED_PROVIDER_TELEPHONE() {
		return REFFERED_PROVIDER_TELEPHONE;
	}



	public void setREFFERED_PROVIDER_TELEPHONE(String rEFFERED_PROVIDER_TELEPHONE) {
		REFFERED_PROVIDER_TELEPHONE = rEFFERED_PROVIDER_TELEPHONE;
	}
	
	
}
