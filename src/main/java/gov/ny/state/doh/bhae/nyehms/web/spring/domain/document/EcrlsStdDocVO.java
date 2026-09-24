package gov.ny.state.doh.bhae.nyehms.web.spring.domain.document;

import java.sql.Date;

public class EcrlsStdDocVO extends NYEHMSDocVO{

	private int  DOCUMENT_ID;
	private Long  LAB_SRC_ID;
	private String  FNAME;
	private String  MNAME;
	private String  LNAME;
	private String  SUFFIX;
	private String  DOB_TEXT;
	private Integer  AGE;
	private String  DIN;
	private String  NYSID;
	private String  SSN;
	private String  BIRTH_COUNTRY;
	private String  PERSON_ADDRESS;
	private String  PERSON_CITY;
	private String  PERSON_STATE;
	private String  PERSON_ZIP;
	private String  PERSON_PLUS4;
	private String  PERSON_COUNTY;
	private String  PERSON_PHONE;
	private String  PERSON_COUNTRY;
	private String  PERSON_CENSUS_TRACT;
	private String  PERSON_CENSUS_TRACT_SUFFIX;
	private String  PERSON_LATITUDE;
	private String  PERSON_LONGITUDE;
	private String  CURRENT_GENDER;
	private String  RACE_ASIAN;
	private String  RACE_BLACK;
	private String  RACE_INDIAN;
	private String  RACE_MULTIPLE;
	private String  RACE_OTHER;
	private String  RACE_PACIFIC;
	private String  RACE_UNKNOWN;
	private String  RACE_WHITE;
	private String  ETHNICITY;
	private String  MED_RECORD_NUMBER;
	private String  INSURANCE_CD;
	private String  INSURANCE_NUMBER;
	private String  PROV_FNAME;
	private String  PROV_MNAME;
	private String  PROV_LNAME;
	private String  PROV_SUFFIX;
	private String  PROV_ADDRESS;
	private String  PROV_CITY;
	private String  PROV_STATE;
	private String  PROV_ZIP;
	private String  PROV_PLUS4;
	private String  PROV_PHONE;
	private String  PROV_COUNTY;
	private String  PROV_CENSUS_TRACT;
	private String  PROV_CENSUS_TRACT_SUFFIX;
	private String  PROV_LATITUDE;
	private String  PROV_LONGITUDE;
	private String  FACILITY_NAME;
	private String  FACILITY_ADDRESS;
	private String  FACILITY_CITY;
	private String  FACILITY_STATE;
	private String  FACILITY_ZIP;
	private String  FACILITY_PLUS4;
	private String  FACILITY_COUNTY;
	private String  FACILITY_PHONE;
	private String  FACILITY_CENSUS_TRACT;
	private String  FACILITY_CENSUS_TRACT_SUFFIX;
	private String  FACILITY_LATITUDE;
	private String  FACILITY_LONGITUDE;
	private Date  DRAW_DATE;
	private Date  OBSERVATION_DATE;
	private Date  COLLECTION_DATE;
	private Date  RESULT_REPORT_DATE;
	private String  LOINC;
	private String  LOINC_DESC;
	private String  TEST_RESULT;
	private String  INTERPRET_CD;
	private String  UNIT_MEASURE_CODE;
	private String  PERSON_INTERNAL_ID;
	private String  PERSON_EXTERNAL_ID;
	private String  SENDING_FACILITY_NAME;
	private String  SPECIMEN_SOURCE_CD;
	private String  SPECIMEN_SOURCE_NAME;
	private String  REFERRING_FACILITY;
	private String  TESTING_FACILITY;
	private String  CASEKIND;
	private int  CRBATCHNUM;
	private String  PROCESSED;
	private String  PROVIDER_TYPE;
	private String  RECB;
	private String  TEST_RESULT_RAW;
	private String  HIV_VACCINE_RECIP;
	private String  OTHER_IMMUNE_INFECTION;
	private String  RESIDE_OUTSIDE_USA;
	private String  AREA_OF_BIRTH;
	private String  LAST_NEG_HIV_TEST_DATE;
	private String  LENGTH_RESIDENCY_IN_USA;
	private String  SENDING_FACILITY_CLIA;
	private Date  DATE_SENT;
	private String  ACCESSION_NUMBER;
	private String  MEDICAID_NUMBER;
	private String  LOCAL_TEST_CODE;
	private String  LOCAL_TEST_DESC;
	private String  REVIEW_UID;
	private Date  REVIEW_DATE;
	private Date  RECEIVED_DATE;
	private String  CREATE_UID;
	private Date  CREATE_DATE;
	private String  EHARS_STATUS_CD;
	private Date  EHARS_IMPORT_DATE;
	private Date  NYC_SHIP_DATE;
	private int  AGE_CATEGORY;
	private int  CURRENT_PROCESS_STATUS;
	private int  QS_PARENT_DOC_ID;
	private String  MATCH_CD;
	private int  MATCH_WEIGHT;
	private String  MATCH_PASS;
	private String  MATCHED_STATENO;
	private Date DATE_RECEIVED;
	private Date CALC_COLLECTION_DATE;
	private String PROV_NPI;
	private String FACILITY_NPI;
	private String PFI;
	
	private String FLUX_MATCH_CD;
	private String FLUX_POT_STATENO;
	private String FLUX_MATCHED_STATENO;
	private Integer FLUX_QS_PARENT_DOC_ID;
	
	
	
	public String getFLUX_MATCH_CD() {
		return FLUX_MATCH_CD;
	}

	public void setFLUX_MATCH_CD(String fLUX_MATCH_CD) {
		FLUX_MATCH_CD = fLUX_MATCH_CD;
	}

	public String getFLUX_POT_STATENO() {
		return FLUX_POT_STATENO;
	}

	public void setFLUX_POT_STATENO(String fLUX_POT_STATENO) {
		FLUX_POT_STATENO = fLUX_POT_STATENO;
	}

	public String getFLUX_MATCHED_STATENO() {
		return FLUX_MATCHED_STATENO;
	}

	public void setFLUX_MATCHED_STATENO(String fLUX_MATCHED_STATENO) {
		FLUX_MATCHED_STATENO = fLUX_MATCHED_STATENO;
	}

	public Integer getFLUX_QS_PARENT_DOC_ID() {
		return FLUX_QS_PARENT_DOC_ID;
	}

	public void setFLUX_QS_PARENT_DOC_ID(Integer fLUX_QS_PARENT_DOC_ID) {
		FLUX_QS_PARENT_DOC_ID = fLUX_QS_PARENT_DOC_ID;
	}
	
	
	public int getDOCUMENT_ID() {
		return DOCUMENT_ID;
	}
	public void setDOCUMENT_ID(int dOCUMENT_ID) {
		DOCUMENT_ID = dOCUMENT_ID;
	}
	public Long getLAB_SRC_ID() {
		return LAB_SRC_ID;
	}
	public void setLAB_SRC_ID(Long lAB_SRC_ID) {
		LAB_SRC_ID = lAB_SRC_ID;
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
	public String getDOB_TEXT() {
		return DOB_TEXT;
	}
	public void setDOB_TEXT(String dOB_TEXT) {
		DOB_TEXT = dOB_TEXT;
	}
	public Integer getAGE() {
		return AGE;
	}
	public void setAGE(Integer aGE) {
		AGE = aGE;
	}
	public String getDIN() {
		return DIN;
	}
	public void setDIN(String dIN) {
		DIN = dIN;
	}
	public String getNYSID() {
		return NYSID;
	}
	public void setNYSID(String nYSID) {
		NYSID = nYSID;
	}
	public String getSSN() {
		return SSN;
	}
	public void setSSN(String sSN) {
		SSN = sSN;
	}
	public String getBIRTH_COUNTRY() {
		return BIRTH_COUNTRY;
	}
	public void setBIRTH_COUNTRY(String bIRTH_COUNTRY) {
		BIRTH_COUNTRY = bIRTH_COUNTRY;
	}
	public String getPERSON_ADDRESS() {
		return PERSON_ADDRESS;
	}
	public void setPERSON_ADDRESS(String pERSON_ADDRESS) {
		PERSON_ADDRESS = pERSON_ADDRESS;
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
	public String getPERSON_COUNTRY() {
		return PERSON_COUNTRY;
	}
	public void setPERSON_COUNTRY(String pERSON_COUNTRY) {
		PERSON_COUNTRY = pERSON_COUNTRY;
	}
	public String getPERSON_CENSUS_TRACT() {
		return PERSON_CENSUS_TRACT;
	}
	public void setPERSON_CENSUS_TRACT(String pERSON_CENSUS_TRACT) {
		PERSON_CENSUS_TRACT = pERSON_CENSUS_TRACT;
	}
	public String getPERSON_CENSUS_TRACT_SUFFIX() {
		return PERSON_CENSUS_TRACT_SUFFIX;
	}
	public void setPERSON_CENSUS_TRACT_SUFFIX(String pERSON_CENSUS_TRACT_SUFFIX) {
		PERSON_CENSUS_TRACT_SUFFIX = pERSON_CENSUS_TRACT_SUFFIX;
	}
	public String getPERSON_LATITUDE() {
		return PERSON_LATITUDE;
	}
	public void setPERSON_LATITUDE(String pERSON_LATITUDE) {
		PERSON_LATITUDE = pERSON_LATITUDE;
	}
	public String getPERSON_LONGITUDE() {
		return PERSON_LONGITUDE;
	}
	public void setPERSON_LONGITUDE(String pERSON_LONGITUDE) {
		PERSON_LONGITUDE = pERSON_LONGITUDE;
	}
	public String getCURRENT_GENDER() {
		return CURRENT_GENDER;
	}
	public void setCURRENT_GENDER(String cURRENT_GENDER) {
		CURRENT_GENDER = cURRENT_GENDER;
	}
	public String getRACE_ASIAN() {
		return RACE_ASIAN;
	}
	public void setRACE_ASIAN(String rACE_ASIAN) {
		RACE_ASIAN = rACE_ASIAN;
	}
	public String getRACE_BLACK() {
		return RACE_BLACK;
	}
	public void setRACE_BLACK(String rACE_BLACK) {
		RACE_BLACK = rACE_BLACK;
	}
	public String getRACE_INDIAN() {
		return RACE_INDIAN;
	}
	public void setRACE_INDIAN(String rACE_INDIAN) {
		RACE_INDIAN = rACE_INDIAN;
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
	public String getRACE_WHITE() {
		return RACE_WHITE;
	}
	public void setRACE_WHITE(String rACE_WHITE) {
		RACE_WHITE = rACE_WHITE;
	}
	public String getETHNICITY() {
		return ETHNICITY;
	}
	public void setETHNICITY(String eTHNICITY) {
		ETHNICITY = eTHNICITY;
	}
	public String getMED_RECORD_NUMBER() {
		return MED_RECORD_NUMBER;
	}
	public void setMED_RECORD_NUMBER(String mED_RECORD_NUMBER) {
		MED_RECORD_NUMBER = mED_RECORD_NUMBER;
	}
	public String getINSURANCE_CD() {
		return INSURANCE_CD;
	}
	public void setINSURANCE_CD(String iNSURANCE_CD) {
		INSURANCE_CD = iNSURANCE_CD;
	}
	public String getINSURANCE_NUMBER() {
		return INSURANCE_NUMBER;
	}
	public void setINSURANCE_NUMBER(String iNSURANCE_NUMBER) {
		INSURANCE_NUMBER = iNSURANCE_NUMBER;
	}
	public String getPROV_FNAME() {
		return PROV_FNAME;
	}
	public void setPROV_FNAME(String pROV_FNAME) {
		PROV_FNAME = pROV_FNAME;
	}
	public String getPROV_MNAME() {
		return PROV_MNAME;
	}
	public void setPROV_MNAME(String pROV_MNAME) {
		PROV_MNAME = pROV_MNAME;
	}
	public String getPROV_LNAME() {
		return PROV_LNAME;
	}
	public void setPROV_LNAME(String pROV_LNAME) {
		PROV_LNAME = pROV_LNAME;
	}
	public String getPROV_SUFFIX() {
		return PROV_SUFFIX;
	}
	public void setPROV_SUFFIX(String pROV_SUFFIX) {
		PROV_SUFFIX = pROV_SUFFIX;
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
	public String getPROV_PLUS4() {
		return PROV_PLUS4;
	}
	public void setPROV_PLUS4(String pROV_PLUS4) {
		PROV_PLUS4 = pROV_PLUS4;
	}
	public String getPROV_PHONE() {
		return PROV_PHONE;
	}
	public void setPROV_PHONE(String pROV_PHONE) {
		PROV_PHONE = pROV_PHONE;
	}
	public String getPROV_COUNTY() {
		return PROV_COUNTY;
	}
	public void setPROV_COUNTY(String pROV_COUNTY) {
		PROV_COUNTY = pROV_COUNTY;
	}
	public String getPROV_CENSUS_TRACT() {
		return PROV_CENSUS_TRACT;
	}
	public void setPROV_CENSUS_TRACT(String pROV_CENSUS_TRACT) {
		PROV_CENSUS_TRACT = pROV_CENSUS_TRACT;
	}
	public String getPROV_CENSUS_TRACT_SUFFIX() {
		return PROV_CENSUS_TRACT_SUFFIX;
	}
	public void setPROV_CENSUS_TRACT_SUFFIX(String pROV_CENSUS_TRACT_SUFFIX) {
		PROV_CENSUS_TRACT_SUFFIX = pROV_CENSUS_TRACT_SUFFIX;
	}
	public String getPROV_LATITUDE() {
		return PROV_LATITUDE;
	}
	public void setPROV_LATITUDE(String pROV_LATITUDE) {
		PROV_LATITUDE = pROV_LATITUDE;
	}
	public String getPROV_LONGITUDE() {
		return PROV_LONGITUDE;
	}
	public void setPROV_LONGITUDE(String pROV_LONGITUDE) {
		PROV_LONGITUDE = pROV_LONGITUDE;
	}
	public String getFACILITY_NAME() {
		return FACILITY_NAME;
	}
	public void setFACILITY_NAME(String fACILITY_NAME) {
		FACILITY_NAME = fACILITY_NAME;
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
	public String getFACILITY_PHONE() {
		return FACILITY_PHONE;
	}
	public void setFACILITY_PHONE(String fACILITY_PHONE) {
		FACILITY_PHONE = fACILITY_PHONE;
	}
	public String getFACILITY_CENSUS_TRACT() {
		return FACILITY_CENSUS_TRACT;
	}
	public void setFACILITY_CENSUS_TRACT(String fACILITY_CENSUS_TRACT) {
		FACILITY_CENSUS_TRACT = fACILITY_CENSUS_TRACT;
	}
	public String getFACILITY_CENSUS_TRACT_SUFFIX() {
		return FACILITY_CENSUS_TRACT_SUFFIX;
	}
	public void setFACILITY_CENSUS_TRACT_SUFFIX(String fACILITY_CENSUS_TRACT_SUFFIX) {
		FACILITY_CENSUS_TRACT_SUFFIX = fACILITY_CENSUS_TRACT_SUFFIX;
	}
	public String getFACILITY_LATITUDE() {
		return FACILITY_LATITUDE;
	}
	public void setFACILITY_LATITUDE(String fACILITY_LATITUDE) {
		FACILITY_LATITUDE = fACILITY_LATITUDE;
	}
	public String getFACILITY_LONGITUDE() {
		return FACILITY_LONGITUDE;
	}
	public void setFACILITY_LONGITUDE(String fACILITY_LONGITUDE) {
		FACILITY_LONGITUDE = fACILITY_LONGITUDE;
	}
	public Date getDRAW_DATE() {
		return DRAW_DATE;
	}
	public void setDRAW_DATE(Date dRAW_DATE) {
		DRAW_DATE = dRAW_DATE;
	}
	public Date getOBSERVATION_DATE() {
		return OBSERVATION_DATE;
	}
	public void setOBSERVATION_DATE(Date oBSERVATION_DATE) {
		OBSERVATION_DATE = oBSERVATION_DATE;
	}
	public Date getCOLLECTION_DATE() {
		return COLLECTION_DATE;
	}
	public void setCOLLECTION_DATE(Date cOLLECTION_DATE) {
		COLLECTION_DATE = cOLLECTION_DATE;
	}
	public Date getRESULT_REPORT_DATE() {
		return RESULT_REPORT_DATE;
	}
	public void setRESULT_REPORT_DATE(Date rESULT_REPORT_DATE) {
		RESULT_REPORT_DATE = rESULT_REPORT_DATE;
	}
	public String getLOINC() {
		return LOINC;
	}
	public void setLOINC(String lOINC) {
		LOINC = lOINC;
	}
	public String getLOINC_DESC() {
		return LOINC_DESC;
	}
	public void setLOINC_DESC(String lOINC_DESC) {
		LOINC_DESC = lOINC_DESC;
	}
	public String getTEST_RESULT() {
		return TEST_RESULT;
	}
	public void setTEST_RESULT(String tEST_RESULT) {
		TEST_RESULT = tEST_RESULT;
	}
	public String getINTERPRET_CD() {
		return INTERPRET_CD;
	}
	public void setINTERPRET_CD(String iNTERPRET_CD) {
		INTERPRET_CD = iNTERPRET_CD;
	}
	public String getUNIT_MEASURE_CODE() {
		return UNIT_MEASURE_CODE;
	}
	public void setUNIT_MEASURE_CODE(String uNIT_MEASURE_CODE) {
		UNIT_MEASURE_CODE = uNIT_MEASURE_CODE;
	}
	public String getPERSON_INTERNAL_ID() {
		return PERSON_INTERNAL_ID;
	}
	public void setPERSON_INTERNAL_ID(String pERSON_INTERNAL_ID) {
		PERSON_INTERNAL_ID = pERSON_INTERNAL_ID;
	}
	public String getPERSON_EXTERNAL_ID() {
		return PERSON_EXTERNAL_ID;
	}
	public void setPERSON_EXTERNAL_ID(String pERSON_EXTERNAL_ID) {
		PERSON_EXTERNAL_ID = pERSON_EXTERNAL_ID;
	}
	public String getSENDING_FACILITY_NAME() {
		return SENDING_FACILITY_NAME;
	}
	public void setSENDING_FACILITY_NAME(String sENDING_FACILITY_NAME) {
		SENDING_FACILITY_NAME = sENDING_FACILITY_NAME;
	}
	public String getSPECIMEN_SOURCE_CD() {
		return SPECIMEN_SOURCE_CD;
	}
	public void setSPECIMEN_SOURCE_CD(String sPECIMEN_SOURCE_CD) {
		SPECIMEN_SOURCE_CD = sPECIMEN_SOURCE_CD;
	}
	public String getSPECIMEN_SOURCE_NAME() {
		return SPECIMEN_SOURCE_NAME;
	}
	public void setSPECIMEN_SOURCE_NAME(String sPECIMEN_SOURCE_NAME) {
		SPECIMEN_SOURCE_NAME = sPECIMEN_SOURCE_NAME;
	}
	public String getREFERRING_FACILITY() {
		return REFERRING_FACILITY;
	}
	public void setREFERRING_FACILITY(String rEFERRING_FACILITY) {
		REFERRING_FACILITY = rEFERRING_FACILITY;
	}
	public String getTESTING_FACILITY() {
		return TESTING_FACILITY;
	}
	public void setTESTING_FACILITY(String tESTING_FACILITY) {
		TESTING_FACILITY = tESTING_FACILITY;
	}
	public String getCASEKIND() {
		return CASEKIND;
	}
	public void setCASEKIND(String cASEKIND) {
		CASEKIND = cASEKIND;
	}
	public int getCRBATCHNUM() {
		return CRBATCHNUM;
	}
	public void setCRBATCHNUM(int cRBATCHNUM) {
		CRBATCHNUM = cRBATCHNUM;
	}
	public String getPROCESSED() {
		return PROCESSED;
	}
	public void setPROCESSED(String pROCESSED) {
		PROCESSED = pROCESSED;
	}
	public String getPROVIDER_TYPE() {
		return PROVIDER_TYPE;
	}
	public void setPROVIDER_TYPE(String pROVIDER_TYPE) {
		PROVIDER_TYPE = pROVIDER_TYPE;
	}
	public String getRECB() {
		return RECB;
	}
	public void setRECB(String rECB) {
		RECB = rECB;
	}
	public String getTEST_RESULT_RAW() {
		return TEST_RESULT_RAW;
	}
	public void setTEST_RESULT_RAW(String tEST_RESULT_RAW) {
		TEST_RESULT_RAW = tEST_RESULT_RAW;
	}
	public String getHIV_VACCINE_RECIP() {
		return HIV_VACCINE_RECIP;
	}
	public void setHIV_VACCINE_RECIP(String hIV_VACCINE_RECIP) {
		HIV_VACCINE_RECIP = hIV_VACCINE_RECIP;
	}
	public String getOTHER_IMMUNE_INFECTION() {
		return OTHER_IMMUNE_INFECTION;
	}
	public void setOTHER_IMMUNE_INFECTION(String oTHER_IMMUNE_INFECTION) {
		OTHER_IMMUNE_INFECTION = oTHER_IMMUNE_INFECTION;
	}
	public String getRESIDE_OUTSIDE_USA() {
		return RESIDE_OUTSIDE_USA;
	}
	public void setRESIDE_OUTSIDE_USA(String rESIDE_OUTSIDE_USA) {
		RESIDE_OUTSIDE_USA = rESIDE_OUTSIDE_USA;
	}
	public String getAREA_OF_BIRTH() {
		return AREA_OF_BIRTH;
	}
	public void setAREA_OF_BIRTH(String aREA_OF_BIRTH) {
		AREA_OF_BIRTH = aREA_OF_BIRTH;
	}
	public String getLAST_NEG_HIV_TEST_DATE() {
		return LAST_NEG_HIV_TEST_DATE;
	}
	public void setLAST_NEG_HIV_TEST_DATE(String lAST_NEG_HIV_TEST_DATE) {
		LAST_NEG_HIV_TEST_DATE = lAST_NEG_HIV_TEST_DATE;
	}
	public String getLENGTH_RESIDENCY_IN_USA() {
		return LENGTH_RESIDENCY_IN_USA;
	}
	public void setLENGTH_RESIDENCY_IN_USA(String lENGTH_RESIDENCY_IN_USA) {
		LENGTH_RESIDENCY_IN_USA = lENGTH_RESIDENCY_IN_USA;
	}
	public String getSENDING_FACILITY_CLIA() {
		return SENDING_FACILITY_CLIA;
	}
	public void setSENDING_FACILITY_CLIA(String sENDING_FACILITY_CLIA) {
		SENDING_FACILITY_CLIA = sENDING_FACILITY_CLIA;
	}
	public Date getDATE_SENT() {
		return DATE_SENT;
	}
	public void setDATE_SENT(Date dATE_SENT) {
		DATE_SENT = dATE_SENT;
	}
	public String getACCESSION_NUMBER() {
		return ACCESSION_NUMBER;
	}
	public void setACCESSION_NUMBER(String aCCESSION_NUMBER) {
		ACCESSION_NUMBER = aCCESSION_NUMBER;
	}
	public String getMEDICAID_NUMBER() {
		return MEDICAID_NUMBER;
	}
	public void setMEDICAID_NUMBER(String mEDICAID_NUMBER) {
		MEDICAID_NUMBER = mEDICAID_NUMBER;
	}
	public String getLOCAL_TEST_CODE() {
		return LOCAL_TEST_CODE;
	}
	public void setLOCAL_TEST_CODE(String lOCAL_TEST_CODE) {
		LOCAL_TEST_CODE = lOCAL_TEST_CODE;
	}
	public String getLOCAL_TEST_DESC() {
		return LOCAL_TEST_DESC;
	}
	public void setLOCAL_TEST_DESC(String lOCAL_TEST_DESC) {
		LOCAL_TEST_DESC = lOCAL_TEST_DESC;
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
	public Date getRECEIVED_DATE() {
		return RECEIVED_DATE;
	}
	public void setRECEIVED_DATE(Date rECEIVED_DATE) {
		RECEIVED_DATE = rECEIVED_DATE;
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
	public String getEHARS_STATUS_CD() {
		return EHARS_STATUS_CD;
	}
	public void setEHARS_STATUS_CD(String eHARS_STATUS_CD) {
		EHARS_STATUS_CD = eHARS_STATUS_CD;
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
	public int getAGE_CATEGORY() {
		return AGE_CATEGORY;
	}
	public void setAGE_CATEGORY(int aGE_CATEGORY) {
		AGE_CATEGORY = aGE_CATEGORY;
	}
	public int getCURRENT_PROCESS_STATUS() {
		return CURRENT_PROCESS_STATUS;
	}
	public void setCURRENT_PROCESS_STATUS(int cURRENT_PROCESS_STATUS) {
		CURRENT_PROCESS_STATUS = cURRENT_PROCESS_STATUS;
	}
	public int getQS_PARENT_DOC_ID() {
		return QS_PARENT_DOC_ID;
	}
	public void setQS_PARENT_DOC_ID(int qS_PARENT_DOC_ID) {
		QS_PARENT_DOC_ID = qS_PARENT_DOC_ID;
	}
	public String getMATCH_CD() {
		return MATCH_CD;
	}
	public void setMATCH_CD(String mATCH_CD) {
		MATCH_CD = mATCH_CD;
	}
	public int getMATCH_WEIGHT() {
		return MATCH_WEIGHT;
	}
	public void setMATCH_WEIGHT(int mATCH_WEIGHT) {
		MATCH_WEIGHT = mATCH_WEIGHT;
	}
	public String getMATCH_PASS() {
		return MATCH_PASS;
	}
	public void setMATCH_PASS(String mATCH_PASS) {
		MATCH_PASS = mATCH_PASS;
	}
	public String getMATCHED_STATENO() {
		return MATCHED_STATENO;
	}
	public void setMATCHED_STATENO(String mATCHED_STATENO) {
		MATCHED_STATENO = mATCHED_STATENO;
	}
	public Date getDATE_RECEIVED() {
		return DATE_RECEIVED;
	}
	public void setDATE_RECEIVED(Date dATE_RECEIVED) {
		DATE_RECEIVED = dATE_RECEIVED;
	}
	public Date getCALC_COLLECTION_DATE() {
		return CALC_COLLECTION_DATE;
	}
	public void setCALC_COLLECTION_DATE(Date cALC_COLLECTION_DATE) {
		CALC_COLLECTION_DATE = cALC_COLLECTION_DATE;
	}
	public String getPROV_NPI() {
		return PROV_NPI;
	}
	public void setPROV_NPI(String pROV_NPI) {
		PROV_NPI = pROV_NPI;
	}
	public String getFACILITY_NPI() {
		return FACILITY_NPI;
	}
	public void setFACILITY_NPI(String fACILITY_NPI) {
		FACILITY_NPI = fACILITY_NPI;
	}

	public String getPFI() {
		return PFI;
	}

	public void setPFI(String pFI) {
		PFI = pFI;
	}
	
		
	
	
}
