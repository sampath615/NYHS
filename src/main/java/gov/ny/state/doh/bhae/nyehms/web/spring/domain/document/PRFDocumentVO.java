package gov.ny.state.doh.bhae.nyehms.web.spring.domain.document;

import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonUtil;

import java.sql.Date;
import java.util.List;

/**
 * This class is used to create POJO for PRF doc.
 *
 * @author vxp06
 */
public class PRFDocumentVO extends NYEHMSDocVO{

	private	int  DOCUMENT_ID;
	private Long  PRF_SRC_ID;
	private String PRFDoc="PrfTTH"; // this variable will give us edit value (PRF+TTH or TTH only)
	private String  FNAME;
	private String  MNAME;
	private String  LNAME;
	private String  SUFFIX;
	private String  ALT_FNAME;
	private String  ALT_MNAME;
	private String  ALT_LNAME;
	private String  DOB;
	private String  DOD_TEXT;
	private String  MED_RECORD_NUMBER;
	private String  DIN;
	private String  rawPersonAddress;
	private String  PERSON_CITY;
	private String  PERSON_STATE;
	private String  PERSON_ZIP;
	private String  PERSON_PLUS4;
	private String  PERSON_COUNTY;
	private String  PERSON_TELEPHONE;
	private String  PERSON_PHONE;
	private String  GENDER;
	private String  ETHNICITY;
	private boolean  RACE_WHITE;
	private boolean  RACE_BLACK;
	private boolean  RACE_ASIAN;
	private boolean  RACE_INDIAN;
	private boolean  RACE_PACIFIC;
	private boolean  RACE_UNKNOWN;
	private boolean  RACE_OTHER;
	private String  RACE_OTHER_TEXT;
	private String  BIRTH_COUNTRY;
	private String  swm;
	private String  swf;
	private String  idu;
	private String  sexIDU;
	private String  sexAIDSHIV;
	private String  rcvdTransfusion;
	private String  perinatalExposure;
	private String  riskOther;
	private String  riskUnknown;
	private String  RISK_OTHER_TEXT;
	private String  RECV_BLOOD_PROD_DATE;
	private String  RECV_BLOOD_PROD_COUNTRY;
	private String  ADMISSION_DATE;
	private boolean  INIT_HIV_DIAG;
	private String  INIT_HIV_DIAG_DATE_TEXT;
	private String  INFORMED_HIV_POS;
	private String  INFORMED_HIV_DATE_TEXT;
	private boolean  PREV_HIV_DIAG;
	private String  PREV_HIV_DIAG_DATE_TEXT;
	private boolean  INIT_AIDS_DIAG;
	private String  INIT_AIDS_DIAG_DATE_TEXT;
	private boolean  PREV_AIDS_DIAG;
	private String  PREV_AIDS_DIAG_DATE_TEXT;
	private boolean  PS_REQUESTED;
	private String  COMPLETER_LNAME;
	private String  COMPLETER_MNAME;
	private String  COMPLETER_FNAME;
	private String COMPLETER_NAME;
	private String  COMPLETER_TITLE;
	private String  COMPLETER_PHONE;
	private String  COMPLETER_PHONE_EXT;
	private String  COMPLETER_EMAIL;
	private String  FACILITY_NAME;
	private String  FACILITY_DEPT;
	private String  FACILITY_ADDRESS;
	private String  FACILITY_CITY;
	private String  FACILITY_STATE;
	private String  FACILITY_ZIP;
	private String  FACILITY_PLUS4;
	private String  FACILITY_COUNTY;
	private Date  COMPLETED_DATE;
	private String COMPLETED_DATE_TEXT;
	private String  CONTACT_LNAME;
	private String  CONTACT_FNAME;
	private String  CONTACT_MNAME;
	private String CONTACT_NAME;
	private String  CONTACT_TITLE;
	private String  CONTACT_PHONE;
	private String  CONTACT_PHONE_EXT;
	private String  PROV_LNAME;
	private String  PROV_FNAME;
	private String  PROV_MI;
	private boolean  PROV_SIGNED;
	private Date  PROV_SIGNED_DATE;
	private String  PROV_SIGNED_DATE_TEXT;
	private String  CREATE_UID;
	private Date  CREATE_DATE;
	private String CREATE_DATE_TEXT;
	private String  ENTRY_REVIEW_UID;
	private Date  ENTRY_REVIEW_DATE;
	private String  COMMENTS;
	private String  sexBI;
	private String  STATENO;
	private String  PERSON_ADDRESS;
	private Date  EHARS_IMPORT_DATE;
	private String  EHARS_IMPORT_DATE_TEXT;
	    
	private String TTHMAINTTHSOURCE;
	private String PT_INTERVIEW_DATE;
	private String FIRST_POS_HIV_DATE;
	private String FIRST_POS_TEST_ANON;
	private String FIRST_POS_FAC_TYPE;
	private String FIRST_POS_FAC_NAME;
	private String FIRST_POS_FAC_STATE;
	private String RSON_FIRST_POS_EXP;
	private String RSON_FIRST_POS_ROUTN;
	private String RSON_FIRST_POS_CHKNEG;
	private String RSON_FIRST_POS_REQD;
	private String RSON_FIRST_POS_PRTNR;
	private String RSON_FIRST_POS_OTHER;
	private String RSON_FIRST_POS_OTHER_TEXT;
	private String PT_ARV_6_MO;
	private String PT_ARV_NAME;
	private String PT_ARV_START_DATE;
	private String PT_ARV_END_DATE;
	private String PT_ARV_TIME_OF_TEST;
	private String FIRST_HIV_TEST_EVER_DATE;
	private String COUNT_HIV_TEST_2YRS ;
	private String RESP_HIV_TEST_2YRS;
	private String EVER_HAD_NEG;
	private String LAST_NEG_DATE;
	private String LAST_NEG_FAC_TYPE;
	private String LAST_NEG_FAC_NAME;
	private String LAST_NEG_FAC_STATE;
	private Date NYC_SHIP_DATE;
	private String NYC_SHIP_DATE_TEXT;
	private String ADMISSION_DATE_TEXT;
	private int partnersCount;
	private Date FILE_RECEVIED_DATE;
	private Date FILE_PROCESSED_DATE;
	
	
	private String PROV_FAC_TYPE_CODE;
	private String EHARS_DOCUMENT_SOURCE_CD;
	
	
	private String PERSON_ADDRESS_TYPE;
	private String SEX_AT_BIRTH;
	private String PREGNANT;
	private String EDC;
	private String PERSON_OTHER_IDENTIFIER;
	private String PRIMARY_LANGUAGE;
	private String PRIMARY_LANGUAGE_OTHER;
	private String ACUTE_INFECTION;
	private String REFERRED_HIV_PROVIDER;
	private String REFERRED_PROVIDER_NAME;
	private String REFERRED_PROVIDER_TELEPHONE;
	private String EVER_HAD_PREV_POS_TEST;
	private String PT_ARV_EVER_TAKEN;
	private String ARV_PREVEN_PREP;
	private String ARV_PREVEN_PREP_START_DT;
	private String ARV_PREVEN_PREP_END_DT;
	private String ARV_PREVENPREP_MED_NAMES;
	private String POST_EXP_ARV_PEP;
	private String POST_EXP_ARV_PEP_START_DT;
	private String POST_EXP_ARV_PEP_END_DT;
	private String POST_EXP_ARV_PEP_MED_NAMES;
	private String ARV_TREATMENT;
	private String RSON_CLINICIAN_OFFERED_REC;
	private String FACILITY_TYPE;
	private String FACILITY_TYPE_OTHER_TEXT;
	private String PROVIDER_NYS_LIC_NO;
	private String PROVIDER_NPI_NO;
	private String ADDITIONAL_GENDER_IDENTITY;
	private String NYSID;
	private int CURRENT_PROCESS_STATUS;
	private String PROV_LICENSE_PROF_CD;
	private Integer ADDRESS_MAPPING_ID;
	private String SEXUAL_ORIENTATION;
	private String ADD_SEXUAL_ORIENATION_IDENTITY;
	
	
	
	private final String DEFAULT_DATE = "../../....";
	
	
	List<PRFPartnerVO> partnerList;

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

	public String getSUFFIX() {
		return SUFFIX;
	}

	public void setSUFFIX(String sUFFIX) {
		SUFFIX = sUFFIX;
	}

	public String getALT_FNAME() {
		return ALT_FNAME;
	}

	public void setALT_FNAME(String aLT_FNAME) {
		ALT_FNAME = aLT_FNAME;
	}

	public String getALT_MNAME() {
		return ALT_MNAME;
	}

	public void setALT_MNAME(String aLT_MNAME) {
		ALT_MNAME = aLT_MNAME;
	}

	public String getALT_LNAME() {
		return ALT_LNAME;
	}

	public void setALT_LNAME(String aLT_LNAME) {
		ALT_LNAME = aLT_LNAME;
	}

	public String getDOB() {
		return DOB;
	}

	public void setDOB(String dOB) {
		
		if(DEFAULT_DATE.equals(dOB)){
			DOB = null;
		}else{
			DOB = dOB;
		}
	}

	public String getDOD_TEXT() {
		return DOD_TEXT;
	}

	public void setDOD_TEXT(String dOD_TEXT) {
		
		if(DEFAULT_DATE.equals(dOD_TEXT)){
			DOD_TEXT = null;
		}else{
			DOD_TEXT = dOD_TEXT;
		}
		
		
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
		
		if(dIN != null){
			DIN = dIN.toUpperCase();
		}else{
			DIN = null;
		}
		
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

	public String getPERSON_PHONE() {
		return PERSON_PHONE;
	}

	public void setPERSON_PHONE(String pERSON_PHONE) {
		PERSON_PHONE = pERSON_PHONE;
	}

	public String getPERSON_TELEPHONE() {
		return PERSON_TELEPHONE;
	}

	public void setPERSON_TELEPHONE(String pERSON_TELEPHONE) {
		PERSON_TELEPHONE = pERSON_TELEPHONE;
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

	public boolean isRACE_WHITE() {
		return RACE_WHITE;
	}

	public void setRACE_WHITE(boolean rACE_WHITE) {
		RACE_WHITE = rACE_WHITE;
	}

	public boolean isRACE_BLACK() {
		return RACE_BLACK;
	}

	public void setRACE_BLACK(boolean rACE_BLACK) {
		RACE_BLACK = rACE_BLACK;
	}

	public boolean isRACE_ASIAN() {
		return RACE_ASIAN;
	}

	public void setRACE_ASIAN(boolean rACE_ASIAN) {
		RACE_ASIAN = rACE_ASIAN;
	}

	public boolean isRACE_INDIAN() {
		return RACE_INDIAN;
	}

	public void setRACE_INDIAN(boolean rACE_INDIAN) {
		RACE_INDIAN = rACE_INDIAN;
	}

	public boolean isRACE_PACIFIC() {
		return RACE_PACIFIC;
	}

	public void setRACE_PACIFIC(boolean rACE_PACIFIC) {
		RACE_PACIFIC = rACE_PACIFIC;
	}

	public boolean isRACE_UNKNOWN() {
		return RACE_UNKNOWN;
	}

	public void setRACE_UNKNOWN(boolean rACE_UNKNOWN) {
		RACE_UNKNOWN = rACE_UNKNOWN;
	}

	public boolean isRACE_OTHER() {
		return RACE_OTHER;
	}

	public void setRACE_OTHER(boolean rACE_OTHER) {
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

	public String getSwm() {
		return swm;
	}

	public void setSwm(String swm) {
		this.swm = swm;
	}

	public String getSwf() {
		return swf;
	}

	public void setSwf(String swf) {
		this.swf = swf;
	}

	public String getIdu() {
		return idu;
	}

	public void setIdu(String idu) {
		this.idu = idu;
	}

	public String getSexIDU() {
		return sexIDU;
	}

	public void setSexIDU(String sexIDU) {
		this.sexIDU = sexIDU;
	}

	public String getSexAIDSHIV() {
		return sexAIDSHIV;
	}

	public void setSexAIDSHIV(String sexAIDSHIV) {
		this.sexAIDSHIV = sexAIDSHIV;
	}

	public String getRcvdTransfusion() {
		return rcvdTransfusion;
	}

	public void setRcvdTransfusion(String rcvdTransfusion) {
		this.rcvdTransfusion = rcvdTransfusion;
	}

	public String getPerinatalExposure() {
		return perinatalExposure;
	}

	public void setPerinatalExposure(String perinatalExposure) {
		this.perinatalExposure = perinatalExposure;
	}

	public String getRiskOther() {
		return riskOther;
	}

	public void setRiskOther(String riskOther) {
		this.riskOther = riskOther;
	}

	public String getRiskUnknown() {
		return riskUnknown;
	}

	public void setRiskUnknown(String riskUnknown) {
		this.riskUnknown = riskUnknown;
	}

	public String getRISK_OTHER_TEXT() {
		return RISK_OTHER_TEXT;
	}

	public void setRISK_OTHER_TEXT(String rISK_OTHER_TEXT) {
		RISK_OTHER_TEXT = rISK_OTHER_TEXT;
	}

	public String getRECV_BLOOD_PROD_DATE() {
		return RECV_BLOOD_PROD_DATE;
	}

	public void setRECV_BLOOD_PROD_DATE(String rECV_BLOOD_PROD_DATE) {
		
		if(DEFAULT_DATE.equals(rECV_BLOOD_PROD_DATE)){
			RECV_BLOOD_PROD_DATE = null;
		}else{
			RECV_BLOOD_PROD_DATE = rECV_BLOOD_PROD_DATE;
		}
		
	}

	public String getRECV_BLOOD_PROD_COUNTRY() {
		return RECV_BLOOD_PROD_COUNTRY;
	}

	public void setRECV_BLOOD_PROD_COUNTRY(String rECV_BLOOD_PROD_COUNTRY) {
		RECV_BLOOD_PROD_COUNTRY = rECV_BLOOD_PROD_COUNTRY;
	}

	public String getADMISSION_DATE() {
		return ADMISSION_DATE;
	}

	public void setADMISSION_DATE(String aDMISSION_DATE) {
		if(DEFAULT_DATE.equals(aDMISSION_DATE)){
			ADMISSION_DATE = null;
		}else{
			ADMISSION_DATE = aDMISSION_DATE;
		}
	}

	public boolean isINIT_HIV_DIAG() {
		return INIT_HIV_DIAG;
	}

	public void setINIT_HIV_DIAG(boolean iNIT_HIV_DIAG) {
		INIT_HIV_DIAG = iNIT_HIV_DIAG;
	}

	public String getINIT_HIV_DIAG_DATE_TEXT() {
		return INIT_HIV_DIAG_DATE_TEXT;
	}

	public void setINIT_HIV_DIAG_DATE_TEXT(String iNIT_HIV_DIAG_DATE_TEXT) {
		
		if(DEFAULT_DATE.equals(iNIT_HIV_DIAG_DATE_TEXT)){
			INIT_HIV_DIAG_DATE_TEXT = null;
		}else{
			INIT_HIV_DIAG_DATE_TEXT = iNIT_HIV_DIAG_DATE_TEXT;
		}
		
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
		
		if(DEFAULT_DATE.equals(iNFORMED_HIV_DATE_TEXT)){
			INFORMED_HIV_DATE_TEXT = null;
		}else{
			INFORMED_HIV_DATE_TEXT = iNFORMED_HIV_DATE_TEXT;
		}
		
	}

	public boolean isPREV_HIV_DIAG() {
		return PREV_HIV_DIAG;
	}

	public void setPREV_HIV_DIAG(boolean pREV_HIV_DIAG) {
		PREV_HIV_DIAG = pREV_HIV_DIAG;
	}

	public String getPREV_HIV_DIAG_DATE_TEXT() {
		return PREV_HIV_DIAG_DATE_TEXT;
	}

	public void setPREV_HIV_DIAG_DATE_TEXT(String pREV_HIV_DIAG_DATE_TEXT) {
		
		if(DEFAULT_DATE.equals(pREV_HIV_DIAG_DATE_TEXT)){
			PREV_HIV_DIAG_DATE_TEXT = null;
		}else{
			PREV_HIV_DIAG_DATE_TEXT = pREV_HIV_DIAG_DATE_TEXT;
		}
		
	}

	public boolean isINIT_AIDS_DIAG() {
		return INIT_AIDS_DIAG;
	}

	public void setINIT_AIDS_DIAG(boolean iNIT_AIDS_DIAG) {
		INIT_AIDS_DIAG = iNIT_AIDS_DIAG;
	}

	public String getINIT_AIDS_DIAG_DATE_TEXT() {
		return INIT_AIDS_DIAG_DATE_TEXT;
	}

	public void setINIT_AIDS_DIAG_DATE_TEXT(String iNIT_AIDS_DIAG_DATE_TEXT) {
		
		if(DEFAULT_DATE.equals(iNIT_AIDS_DIAG_DATE_TEXT)){
			INIT_AIDS_DIAG_DATE_TEXT = null;
		}else{
			INIT_AIDS_DIAG_DATE_TEXT = iNIT_AIDS_DIAG_DATE_TEXT;
		}
		
	}

	public boolean isPREV_AIDS_DIAG() {
		return PREV_AIDS_DIAG;
	}

	public void setPREV_AIDS_DIAG(boolean pREV_AIDS_DIAG) {
		PREV_AIDS_DIAG = pREV_AIDS_DIAG;
	}

	public String getPREV_AIDS_DIAG_DATE_TEXT() {
		return PREV_AIDS_DIAG_DATE_TEXT;
	}

	public void setPREV_AIDS_DIAG_DATE_TEXT(String pREV_AIDS_DIAG_DATE_TEXT) {
		
		if(DEFAULT_DATE.equals(pREV_AIDS_DIAG_DATE_TEXT)){
			PREV_AIDS_DIAG_DATE_TEXT = null;
		}else{
			PREV_AIDS_DIAG_DATE_TEXT = pREV_AIDS_DIAG_DATE_TEXT;
		}
	}

	

	public String getCOMPLETER_LNAME() {
		return COMPLETER_LNAME;
	}

	public void setCOMPLETER_LNAME(String cOMPLETER_LNAME) {
		COMPLETER_LNAME = cOMPLETER_LNAME;
	}

	public String getCOMPLETER_MNAME() {
		return COMPLETER_MNAME;
	}

	public void setCOMPLETER_MNAME(String cOMPLETER_MNAME) {
		COMPLETER_MNAME = cOMPLETER_MNAME;
	}

	public String getCOMPLETER_FNAME() {
		return COMPLETER_FNAME;
	}

	public void setCOMPLETER_FNAME(String cOMPLETER_FNAME) {
		COMPLETER_FNAME = cOMPLETER_FNAME;
	}

	public String getCOMPLETER_TITLE() {
		return COMPLETER_TITLE;
	}

	public void setCOMPLETER_TITLE(String cOMPLETER_TITLE) {
		COMPLETER_TITLE = cOMPLETER_TITLE;
	}

	public String getCOMPLETER_PHONE() {
		return COMPLETER_PHONE;
	}

	public void setCOMPLETER_PHONE(String cOMPLETER_PHONE) {
		COMPLETER_PHONE = cOMPLETER_PHONE;
	}

	public String getCOMPLETER_PHONE_EXT() {
		return COMPLETER_PHONE_EXT;
	}

	public void setCOMPLETER_PHONE_EXT(String cOMPLETER_PHONE_EXT) {
		COMPLETER_PHONE_EXT = cOMPLETER_PHONE_EXT;
	}

	public String getCOMPLETER_EMAIL() {
		return COMPLETER_EMAIL;
	}

	public void setCOMPLETER_EMAIL(String cOMPLETER_EMAIL) {
		COMPLETER_EMAIL = cOMPLETER_EMAIL;
	}

	public String getFACILITY_NAME() {
		return FACILITY_NAME;
	}

	public void setFACILITY_NAME(String fACILITY_NAME) {
		FACILITY_NAME = fACILITY_NAME;
	}

	public String getFACILITY_DEPT() {
		return FACILITY_DEPT;
	}

	public void setFACILITY_DEPT(String fACILITY_DEPT) {
		FACILITY_DEPT = fACILITY_DEPT;
	}

	public String getFACILITY_ADDRESS() {
		return FACILITY_ADDRESS;
	}

	public void setFACILITY_ADDRESS(String fACILITY_ADDRESS) {
		FACILITY_ADDRESS = fACILITY_ADDRESS;
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

	public String getFACILITY_PLUS4() {
		return FACILITY_PLUS4;
	}

	public void setFACILITY_PLUS4(String fACILITY_PLUS4) {
		FACILITY_PLUS4 = fACILITY_PLUS4;
	}

	public String getFACILITY_COUNTY() {
		return FACILITY_COUNTY;
	}

	public void setFACILITY_COUNTY(String fACILITY_COUNTY) {
		FACILITY_COUNTY = fACILITY_COUNTY;
	}

	public Date getCOMPLETED_DATE() {
		return COMPLETED_DATE;
	}

	public void setCOMPLETED_DATE(Date cOMPLETED_DATE) {
		COMPLETED_DATE = cOMPLETED_DATE;
	}

	public String getCOMPLETED_DATE_TEXT() {
		return COMPLETED_DATE_TEXT;
	}

	public void setCOMPLETED_DATE_TEXT(String cOMPLETED_DATE_TEXT) {
		if(DEFAULT_DATE.equals(cOMPLETED_DATE_TEXT)){
			COMPLETED_DATE_TEXT = null;
		}else{
			COMPLETED_DATE_TEXT = cOMPLETED_DATE_TEXT;
		}
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

	public String getCONTACT_PHONE_EXT() {
		return CONTACT_PHONE_EXT;
	}

	public void setCONTACT_PHONE_EXT(String cONTACT_PHONE_EXT) {
		CONTACT_PHONE_EXT = cONTACT_PHONE_EXT;
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

	public boolean isPROV_SIGNED() {
		return PROV_SIGNED;
	}

	public void setPROV_SIGNED(boolean pROV_SIGNED) {
		PROV_SIGNED = pROV_SIGNED;
	}

	public Date getPROV_SIGNED_DATE() {
		return PROV_SIGNED_DATE;
	}

	public void setPROV_SIGNED_DATE(Date pROV_SIGNED_DATE) {
		PROV_SIGNED_DATE = pROV_SIGNED_DATE;
	}

	public String getPROV_SIGNED_DATE_TEXT() {
		return PROV_SIGNED_DATE_TEXT;
	}

	public void setPROV_SIGNED_DATE_TEXT(String pROV_SIGNED_DATE_TEXT) {
		if(DEFAULT_DATE.equals(pROV_SIGNED_DATE_TEXT)){
			PROV_SIGNED_DATE_TEXT = null;
		}else{
			PROV_SIGNED_DATE_TEXT = pROV_SIGNED_DATE_TEXT;
		}
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

	public String getENTRY_REVIEW_UID() {
		return ENTRY_REVIEW_UID;
	}

	public void setENTRY_REVIEW_UID(String eNTRY_REVIEW_UID) {
		ENTRY_REVIEW_UID = eNTRY_REVIEW_UID;
	}

	public Date getENTRY_REVIEW_DATE() {
		return ENTRY_REVIEW_DATE;
	}

	public void setENTRY_REVIEW_DATE(Date eNTRY_REVIEW_DATE) {
		ENTRY_REVIEW_DATE = eNTRY_REVIEW_DATE;
	}

	public String getCOMMENTS() {
		return COMMENTS;
	}

	public void setCOMMENTS(String cOMMENTS) {
		COMMENTS = cOMMENTS;
	}

	public String getSexBI() {
		return sexBI;
	}

	public void setSexBI(String sexBI) {
		this.sexBI = sexBI;
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

	public String getEHARS_IMPORT_DATE_TEXT() {
		return EHARS_IMPORT_DATE_TEXT;
	}

	public void setEHARS_IMPORT_DATE_TEXT(String eHARS_IMPORT_DATE_TEXT) {
		EHARS_IMPORT_DATE_TEXT = eHARS_IMPORT_DATE_TEXT;
	}

	public String getTTHMAINTTHSOURCE() {
		return TTHMAINTTHSOURCE;
	}

	public void setTTHMAINTTHSOURCE(String tTHMAINTTHSOURCE) {
		TTHMAINTTHSOURCE = tTHMAINTTHSOURCE;
	}

	public String getPT_INTERVIEW_DATE() {
		return PT_INTERVIEW_DATE;
	}

	public void setPT_INTERVIEW_DATE(String pT_INTERVIEW_DATE) {
		
		if(DEFAULT_DATE.equals(pT_INTERVIEW_DATE)){
			PT_INTERVIEW_DATE = null;
		}else{
			PT_INTERVIEW_DATE = pT_INTERVIEW_DATE;
		}
		
	}

	public String getFIRST_POS_HIV_DATE() {
		return FIRST_POS_HIV_DATE;
	}

	public void setFIRST_POS_HIV_DATE(String fIRST_POS_HIV_DATE) {
		
		if(DEFAULT_DATE.equals(fIRST_POS_HIV_DATE)){
			FIRST_POS_HIV_DATE = null;
		}else{
			FIRST_POS_HIV_DATE = fIRST_POS_HIV_DATE;
		}
		
	}

	public String getFIRST_POS_TEST_ANON() {
		return FIRST_POS_TEST_ANON;
	}

	public void setFIRST_POS_TEST_ANON(String fIRST_POS_TEST_ANON) {
		FIRST_POS_TEST_ANON = fIRST_POS_TEST_ANON;
	}

	public String getFIRST_POS_FAC_TYPE() {
		return FIRST_POS_FAC_TYPE;
	}

	public void setFIRST_POS_FAC_TYPE(String fIRST_POS_FAC_TYPE) {
		FIRST_POS_FAC_TYPE = fIRST_POS_FAC_TYPE;
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

	public String getPT_ARV_6_MO() {
		return PT_ARV_6_MO;
	}

	public void setPT_ARV_6_MO(String pT_ARV_6_MO) {
		PT_ARV_6_MO = pT_ARV_6_MO;
	}

	public String getPT_ARV_NAME() {
		return PT_ARV_NAME;
	}

	public void setPT_ARV_NAME(String pT_ARV_NAME) {
		
		if(CommonUtil.isNotNullAndEmpty(pT_ARV_NAME)){
			
			if(pT_ARV_NAME.indexOf(",") == 0 ){
				pT_ARV_NAME = pT_ARV_NAME.replaceFirst(",","");
			}
			
			PT_ARV_NAME = pT_ARV_NAME.replaceAll("," ,"|");
		}else{
			PT_ARV_NAME = pT_ARV_NAME;
		}
	}

	public String getPT_ARV_START_DATE() {
		return PT_ARV_START_DATE;
	}

	public void setPT_ARV_START_DATE(String pT_ARV_START_DATE) {
		
		if(DEFAULT_DATE.equals(pT_ARV_START_DATE)){
			PT_ARV_START_DATE = null;
		}else{
			PT_ARV_START_DATE = pT_ARV_START_DATE;
		}
		
	}

	public String getPT_ARV_END_DATE() {
		return PT_ARV_END_DATE;
	}

	public void setPT_ARV_END_DATE(String pT_ARV_END_DATE) {
		
		if(DEFAULT_DATE.equals(pT_ARV_END_DATE)){
			PT_ARV_END_DATE = null;
		}else{
			PT_ARV_END_DATE = pT_ARV_END_DATE;
		}
		
	}

	public String getPT_ARV_TIME_OF_TEST() {
		return PT_ARV_TIME_OF_TEST;
	}

	public void setPT_ARV_TIME_OF_TEST(String pT_ARV_TIME_OF_TEST) {
		PT_ARV_TIME_OF_TEST = pT_ARV_TIME_OF_TEST;
	}

	public String getFIRST_HIV_TEST_EVER_DATE() {
		return FIRST_HIV_TEST_EVER_DATE;
	}

	public void setFIRST_HIV_TEST_EVER_DATE(String fIRST_HIV_TEST_EVER_DATE) {
		
		if(DEFAULT_DATE.equals(fIRST_HIV_TEST_EVER_DATE)){
			FIRST_HIV_TEST_EVER_DATE = null;
		}else{
			FIRST_HIV_TEST_EVER_DATE = fIRST_HIV_TEST_EVER_DATE;
		}
		
	}

	public String getCOUNT_HIV_TEST_2YRS() {
		return COUNT_HIV_TEST_2YRS;
	}

	public void setCOUNT_HIV_TEST_2YRS(String cOUNT_HIV_TEST_2YRS) {
		COUNT_HIV_TEST_2YRS = cOUNT_HIV_TEST_2YRS;
	}

	public String getRESP_HIV_TEST_2YRS() {
		return RESP_HIV_TEST_2YRS;
	}

	public void setRESP_HIV_TEST_2YRS(String rESP_HIV_TEST_2YRS) {
		RESP_HIV_TEST_2YRS = rESP_HIV_TEST_2YRS;
	}

	public String getEVER_HAD_NEG() {
		return EVER_HAD_NEG;
	}

	public void setEVER_HAD_NEG(String eVER_HAD_NEG) {
		EVER_HAD_NEG = eVER_HAD_NEG;
	}

	public String getLAST_NEG_DATE() {
		return LAST_NEG_DATE;
	}

	public void setLAST_NEG_DATE(String lAST_NEG_DATE) {
		
		if(DEFAULT_DATE.equals(lAST_NEG_DATE)){
			LAST_NEG_DATE = null;
		}else{
			LAST_NEG_DATE = lAST_NEG_DATE;
		}
		
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

	public Date getNYC_SHIP_DATE() {
		return NYC_SHIP_DATE;
	}

	public void setNYC_SHIP_DATE(Date nYC_SHIP_DATE) {
		NYC_SHIP_DATE = nYC_SHIP_DATE;
	}

	public String getNYC_SHIP_DATE_TEXT() {
		return NYC_SHIP_DATE_TEXT;
	}

	public void setNYC_SHIP_DATE_TEXT(String nYC_SHIP_DATE_TEXT) {
		NYC_SHIP_DATE_TEXT = nYC_SHIP_DATE_TEXT;
	}

	public String getADMISSION_DATE_TEXT() {
		return ADMISSION_DATE_TEXT;
	}

	public void setADMISSION_DATE_TEXT(String aDMISSION_DATE_TEXT) {
	
		if(DEFAULT_DATE.equals(aDMISSION_DATE_TEXT)){
			ADMISSION_DATE_TEXT = null;
		}else{
			ADMISSION_DATE_TEXT = aDMISSION_DATE_TEXT;
		}

	}

	public List<PRFPartnerVO> getPartnerList() {
		return partnerList;
	}

	public void setPartnerList(List<PRFPartnerVO> partnerList) {
		this.partnerList = partnerList;
	}

	public boolean isPS_REQUESTED() {
		return PS_REQUESTED;
	}

	public void setPS_REQUESTED(boolean pS_REQUESTED) {
		PS_REQUESTED = pS_REQUESTED;
	}

	public String getPROV_FAC_TYPE_CODE() {
		return PROV_FAC_TYPE_CODE;
	}

	public void setPROV_FAC_TYPE_CODE(String pROV_FAC_TYPE_CODE) {
		PROV_FAC_TYPE_CODE = pROV_FAC_TYPE_CODE;
	}

	public String getEHARS_DOCUMENT_SOURCE_CD() {
		return EHARS_DOCUMENT_SOURCE_CD;
	}

	public void setEHARS_DOCUMENT_SOURCE_CD(String eHARS_DOCUMENT_SOURCE_CD) {
		EHARS_DOCUMENT_SOURCE_CD = eHARS_DOCUMENT_SOURCE_CD;
	}

	public String getCREATE_DATE_TEXT() {
		return CREATE_DATE_TEXT;
	}

	public void setCREATE_DATE_TEXT(String cREATE_DATE_TEXT) {
		CREATE_DATE_TEXT = cREATE_DATE_TEXT;
	}

	public int getPartnersCount() {
		return partnersCount;
	}

	public void setPartnersCount(int partnersCount) {
		this.partnersCount = partnersCount;
	}

	public String getCOMPLETER_NAME() {
		return COMPLETER_NAME;
	}

	public void setCOMPLETER_NAME(String cOMPLETER_NAME) {
		COMPLETER_NAME = cOMPLETER_NAME;
	}

	public String getCONTACT_NAME() {
		return CONTACT_NAME;
	}

	public void setCONTACT_NAME(String cONTACT_NAME) {
		CONTACT_NAME = cONTACT_NAME;
	}

	public Date getFILE_RECEVIED_DATE() {
		return FILE_RECEVIED_DATE;
	}

	public void setFILE_RECEVIED_DATE(Date fILE_RECEVIED_DATE) {
		FILE_RECEVIED_DATE = fILE_RECEVIED_DATE;
	}

	public Date getFILE_PROCESSED_DATE() {
		return FILE_PROCESSED_DATE;
	}

	public void setFILE_PROCESSED_DATE(Date fILE_PROCESSED_DATE) {
		FILE_PROCESSED_DATE = fILE_PROCESSED_DATE;
	}

	public String getPERSON_ADDRESS_TYPE() {
		return PERSON_ADDRESS_TYPE;
	}

	public void setPERSON_ADDRESS_TYPE(String pERSON_ADDRESS_TYPE) {
		PERSON_ADDRESS_TYPE = pERSON_ADDRESS_TYPE;
	}

	public String getSEX_AT_BIRTH() {
		return SEX_AT_BIRTH;
	}

	public void setSEX_AT_BIRTH(String sEX_AT_BIRTH) {
		SEX_AT_BIRTH = sEX_AT_BIRTH;
	}

	public String getPREGNANT() {
		return PREGNANT;
	}

	public void setPREGNANT(String pREGNANT) {
		PREGNANT = pREGNANT;
	}

	public String getEDC() {
		return EDC;
	}

	public void setEDC(String eDC) {
		EDC = eDC;
	}

	public String getPERSON_OTHER_IDENTIFIER() {
		return PERSON_OTHER_IDENTIFIER;
	}

	public void setPERSON_OTHER_IDENTIFIER(String pERSON_OTHER_IDENTIFIER) {
		PERSON_OTHER_IDENTIFIER = pERSON_OTHER_IDENTIFIER;
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

	public String getACUTE_INFECTION() {
		return ACUTE_INFECTION;
	}

	public void setACUTE_INFECTION(String aCUTE_INFECTION) {
		ACUTE_INFECTION = aCUTE_INFECTION;
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

	public String getREFERRED_PROVIDER_TELEPHONE() {
		return REFERRED_PROVIDER_TELEPHONE;
	}

	public void setREFERRED_PROVIDER_TELEPHONE(String rEFERRED_PROVIDER_TELEPHONE) {
		REFERRED_PROVIDER_TELEPHONE = rEFERRED_PROVIDER_TELEPHONE;
	}

	public String getEVER_HAD_PREV_POS_TEST() {
		return EVER_HAD_PREV_POS_TEST;
	}

	public void setEVER_HAD_PREV_POS_TEST(String eVER_HAD_PREV_POS_TEST) {
		EVER_HAD_PREV_POS_TEST = eVER_HAD_PREV_POS_TEST;
	}

	public String getPT_ARV_EVER_TAKEN() {
		return PT_ARV_EVER_TAKEN;
	}

	public void setPT_ARV_EVER_TAKEN(String pT_ARV_EVER_TAKEN) {
		PT_ARV_EVER_TAKEN = pT_ARV_EVER_TAKEN;
	}

	public String getARV_PREVEN_PREP() {
		return ARV_PREVEN_PREP;
	}

	public void setARV_PREVEN_PREP(String aRV_PREVEN_PREP) {
		ARV_PREVEN_PREP = aRV_PREVEN_PREP;
	}

	public String getARV_PREVEN_PREP_START_DT() {
		return ARV_PREVEN_PREP_START_DT;
	}

	public void setARV_PREVEN_PREP_START_DT(String aRV_PREVEN_PREP_START_DT) {
		ARV_PREVEN_PREP_START_DT = aRV_PREVEN_PREP_START_DT;
	}

	public String getARV_PREVEN_PREP_END_DT() {
		return ARV_PREVEN_PREP_END_DT;
	}

	public void setARV_PREVEN_PREP_END_DT(String aRV_PREVEN_PREP_END_DT) {
		ARV_PREVEN_PREP_END_DT = aRV_PREVEN_PREP_END_DT;
	}

	public String getARV_PREVENPREP_MED_NAMES() {
		return ARV_PREVENPREP_MED_NAMES;
	}

	public void setARV_PREVENPREP_MED_NAMES(String aRV_PREVENPREP_MED_NAMES) {
		
		if(CommonUtil.isNotNullAndEmpty(aRV_PREVENPREP_MED_NAMES)){
			
			if(aRV_PREVENPREP_MED_NAMES.indexOf(",") == 0 ){
				aRV_PREVENPREP_MED_NAMES = aRV_PREVENPREP_MED_NAMES.replaceFirst(",","");
			}
			
			ARV_PREVENPREP_MED_NAMES = aRV_PREVENPREP_MED_NAMES.replaceAll("," ,"|");
		}else{
			ARV_PREVENPREP_MED_NAMES = aRV_PREVENPREP_MED_NAMES;
		}
	}

	public String getPOST_EXP_ARV_PEP() {
		return POST_EXP_ARV_PEP;
	}

	public void setPOST_EXP_ARV_PEP(String pOST_EXP_ARV_PEP) {
		POST_EXP_ARV_PEP = pOST_EXP_ARV_PEP;
	}

	public String getPOST_EXP_ARV_PEP_START_DT() {
		return POST_EXP_ARV_PEP_START_DT;
	}

	public void setPOST_EXP_ARV_PEP_START_DT(String pOST_EXP_ARV_PEP_START_DT) {
		POST_EXP_ARV_PEP_START_DT = pOST_EXP_ARV_PEP_START_DT;
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
			
			if(CommonUtil.isNotNullAndEmpty(pOST_EXP_ARV_PEP_MED_NAMES)){
				
				if(pOST_EXP_ARV_PEP_MED_NAMES.indexOf(",") == 0 ){
					pOST_EXP_ARV_PEP_MED_NAMES = pOST_EXP_ARV_PEP_MED_NAMES.replaceFirst(",","");
				}
				
				POST_EXP_ARV_PEP_MED_NAMES = pOST_EXP_ARV_PEP_MED_NAMES.replaceAll("," ,"|");
			}else{
				POST_EXP_ARV_PEP_MED_NAMES = pOST_EXP_ARV_PEP_MED_NAMES;
			}
	}

	public String getARV_TREATMENT() {
		return ARV_TREATMENT;
	}

	public void setARV_TREATMENT(String aRV_TREATMENT) {
		ARV_TREATMENT = aRV_TREATMENT;
	}

	public String getRSON_CLINICIAN_OFFERED_REC() {
		return RSON_CLINICIAN_OFFERED_REC;
	}

	public void setRSON_CLINICIAN_OFFERED_REC(String rSON_CLINICIAN_OFFERED_REC) {
		RSON_CLINICIAN_OFFERED_REC = rSON_CLINICIAN_OFFERED_REC;
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

	public String getPROVIDER_NYS_LIC_NO() {
		return PROVIDER_NYS_LIC_NO;
	}

	public void setPROVIDER_NYS_LIC_NO(String pROVIDER_NYS_LIC_NO) {
		PROVIDER_NYS_LIC_NO = pROVIDER_NYS_LIC_NO;
	}

	public String getPROVIDER_NPI_NO() {
		return PROVIDER_NPI_NO;
	}

	public void setPROVIDER_NPI_NO(String pROVIDER_NPI_NO) {
		PROVIDER_NPI_NO = pROVIDER_NPI_NO;
	}

	public String getADDITIONAL_GENDER_IDENTITY() {
		return ADDITIONAL_GENDER_IDENTITY;
	}

	public void setADDITIONAL_GENDER_IDENTITY(String aDDITIONAL_GENDER_IDENTITY) {
		ADDITIONAL_GENDER_IDENTITY = aDDITIONAL_GENDER_IDENTITY;
	}

	public String getNYSID() {
		return NYSID;
	}

	public void setNYSID(String nYSID) {
		NYSID = nYSID;
	}

	public String getDEFAULT_DATE() {
		return DEFAULT_DATE;
	}


	
	private String DISCARD_STATUS;
	
	public String getDISCARD_STATUS() {
		return DISCARD_STATUS;
	}

	public void setDISCARD_STATUS(String dISCARD_STATUS) {
		DISCARD_STATUS = dISCARD_STATUS;;
	}

	public int getCURRENT_PROCESS_STATUS() {
		return CURRENT_PROCESS_STATUS;
	}

	public void setCURRENT_PROCESS_STATUS(int cURRENT_PROCESS_STATUS) {
		CURRENT_PROCESS_STATUS = cURRENT_PROCESS_STATUS;
	}

	public String getPROV_LICENSE_PROF_CD() {
		return PROV_LICENSE_PROF_CD;
	}

	public void setPROV_LICENSE_PROF_CD(String pROV_LICENSE_PROF_CD) {
		PROV_LICENSE_PROF_CD = pROV_LICENSE_PROF_CD;
	}

	public Integer getADDRESS_MAPPING_ID() {
		return ADDRESS_MAPPING_ID;
	}

	public void setADDRESS_MAPPING_ID(Integer aDDRESS_MAPPING_ID) {
		ADDRESS_MAPPING_ID = aDDRESS_MAPPING_ID;
	}

	public String getSEXUAL_ORIENTATION() {
		return SEXUAL_ORIENTATION;
	}

	public void setSEXUAL_ORIENTATION(String sEXUAL_ORIENTATION) {
		SEXUAL_ORIENTATION = sEXUAL_ORIENTATION;
	}

	public String getADD_SEXUAL_ORIENATION_IDENTITY() {
		return ADD_SEXUAL_ORIENATION_IDENTITY;
	}

	public void setADD_SEXUAL_ORIENATION_IDENTITY(String aDD_SEXUAL_ORIENATION_IDENTITY) {
		ADD_SEXUAL_ORIENATION_IDENTITY = aDD_SEXUAL_ORIENATION_IDENTITY;
	}
	
	

    	
}
