package gov.ny.state.doh.bhae.nyehms.web.spring.domain.document;

import java.sql.Date;

/**
 * This is pojo class for NYC Death document.
 * This is used by mybatis for creating
 * object map for death document .
 *
 * @author cdf02
 */
public class NycDeathDocVO extends NYEHMSDocVO {

	private int CURRENT_PROCESS_STATUS;
	private int DOCUMENT_ID;
	private int MATCH_WEIGHT;
	private int QS_PARENT_DOC_ID;
	private String CURRENT_GENDER;
	private String EHARS_STATUS_CD;
	private String H_CAUSE;
	private String MARITAL_STATUS;
	private String PREGNANT;
	private String RACE_ASIAN;
	private String RACE_BLACK;
	private String RACE_INDIAN;
	private String RACE_MULTIPLE;
	private String RACE_OTHER;
	private String RACE_PACIFIC;
	private String RACE_UNKNOWN;
	private String RACE_WHITE;
	private String SPEC_INT_CAUSE;
	private String UNITS_AGE;
	private String CREATE_UID;
	private String DELIVERY_DATE_TEXT;
	private String DOB;
	private String FACILITY_LATITUDE;
	private String FACILITY_TYPE;
	private String MATCHED_STATENO;
	private String MED_RECORD_NUMBER;
	private String PERSON_LATITUDE;
	private String FACILITY_LONGITUDE;
	private String PERSON_LONGITUDE;
	private String DEATH_ID;
	private String DEATH_UNIQUE_ID;
	private String DOD;
	private String BIRTH_STATE;
	private String ETHNICITY;
	private String FACILITY_STATE;
	private String PERSON_STATE;
	private String FACILITY_PHONE;
	private String FATHER_LNAME;
	private String FNAME;
	private String MOTHER_MAIDEN;
	private String LNAME;
	private String MNAME;
	private String AGE_DEATH;
	private String DEATH_COUNTY;
	private String FACILITY_COUNTY;
	private String INST;
	private String MATCH_CD;
	private String NYS_COUNTY;
	private String PERSON_COUNTY;
	private String SOUNDEX;
	private String FACILITY_CITY;
	private String PERSON_CITY;
	private String CAUSE1;
	private String CAUSE10;
	private String CAUSE11;
	private String CAUSE12;
	private String CAUSE13;
	private String CAUSE14;
	private String CAUSE15;
	private String CAUSE16;
	private String CAUSE17;
	private String CAUSE18;
	private String CAUSE19;
	private String CAUSE2;
	private String CAUSE20;
	private String CAUSE3;
	private String CAUSE4;
	private String CAUSE5;
	private String CAUSE6;
	private String CAUSE7;
	private String CAUSE8;
	private String CAUSE9;
	private String FACILITY_PLUS4;
	private String MATCH_PASS;
	private String NYC_FAC_CODE;
	private String PERSON_PLUS4;
	private String UNDERLYING;
	private String ICD10;
	private String FACILITY_ZIP;
	private String FIPS_PLACE;
	private String LOCAL_REG_NO;
	private String PERSON_ZIP;
	private String SUFFIX;
	private String TRANS_FAC_CD;
	private String DEATH_FILE;
	private String DEATH_CERT;
	private String DEATH_CERT_LICENSE;
	private String FACILITY_CENSUS_TRACT;
	private String INST_CODE;
	private String PERSON_CENSUS_TRACT;
	private String PFI;
	private String FACILITY_ADDRESS;
	private String PERSON_ADDRESS;
	private String REVIEW_UID;
	private String FACILITY_NAME;
	private String SSN;
	
	private String STATENO;
	
	private Date CREATE_DATE;
	private Date EHARS_IMPORT_DATE;
	private Date NYC_SHIP_DATE;
	private Date REVIEW_DATE;
	
	//Raw layer fields
	private String RES_STREET_NO;
	private String RES_STREET_DIRECT;
	private String RES_STREET;
	private String RES_STREET_TYPE;
	private String RES_APT_NO;
	private String causeOfDeath;
	private String OI_CAUSE;
   
    
	private String dateOfDeath;
	
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
    
	public int getCURRENT_PROCESS_STATUS() {
		return CURRENT_PROCESS_STATUS;
	}
	public void setCURRENT_PROCESS_STATUS(int cURRENT_PROCESS_STATUS) {
		CURRENT_PROCESS_STATUS = cURRENT_PROCESS_STATUS;
	}
	public int getDOCUMENT_ID() {
		return DOCUMENT_ID;
	}
	public void setDOCUMENT_ID(int dOCUMENT_ID) {
		DOCUMENT_ID = dOCUMENT_ID;
	}
	public int getMATCH_WEIGHT() {
		return MATCH_WEIGHT;
	}
	public void setMATCH_WEIGHT(int mATCH_WEIGHT) {
		MATCH_WEIGHT = mATCH_WEIGHT;
	}
	public int getQS_PARENT_DOC_ID() {
		return QS_PARENT_DOC_ID;
	}
	public void setQS_PARENT_DOC_ID(int qS_PARENT_DOC_ID) {
		QS_PARENT_DOC_ID = qS_PARENT_DOC_ID;
	}
	public String getCURRENT_GENDER() {
		return CURRENT_GENDER;
	}
	public void setCURRENT_GENDER(String cURRENT_GENDER) {
		CURRENT_GENDER = cURRENT_GENDER;
	}
	public String getEHARS_STATUS_CD() {
		return EHARS_STATUS_CD;
	}
	public void setEHARS_STATUS_CD(String eHARS_STATUS_CD) {
		EHARS_STATUS_CD = eHARS_STATUS_CD;
	}
	public String getH_CAUSE() {
		return H_CAUSE;
	}
	public void setH_CAUSE(String h_CAUSE) {
		H_CAUSE = h_CAUSE;
	}
	public String getMARITAL_STATUS() {
		return MARITAL_STATUS;
	}
	public void setMARITAL_STATUS(String mARITAL_STATUS) {
		MARITAL_STATUS = mARITAL_STATUS;
	}
	public String getPREGNANT() {
		return PREGNANT;
	}
	public void setPREGNANT(String pREGNANT) {
		PREGNANT = pREGNANT;
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
	public String getSPEC_INT_CAUSE() {
		return SPEC_INT_CAUSE;
	}
	public void setSPEC_INT_CAUSE(String sPEC_INT_CAUSE) {
		SPEC_INT_CAUSE = sPEC_INT_CAUSE;
	}
	public String getUNITS_AGE() {
		return UNITS_AGE;
	}
	public void setUNITS_AGE(String uNITS_AGE) {
		UNITS_AGE = uNITS_AGE;
	}
	public String getCREATE_UID() {
		return CREATE_UID;
	}
	public void setCREATE_UID(String cREATE_UID) {
		CREATE_UID = cREATE_UID;
	}
	public String getDELIVERY_DATE_TEXT() {
		return DELIVERY_DATE_TEXT;
	}
	public void setDELIVERY_DATE_TEXT(String dELIVERY_DATE_TEXT) {
		DELIVERY_DATE_TEXT = dELIVERY_DATE_TEXT;
	}
	
	public String getDOB() {
		return DOB;
	}
	public void setDOB(String dOB) {
		DOB = dOB;
	}
	public String getFACILITY_LATITUDE() {
		return FACILITY_LATITUDE;
	}
	public void setFACILITY_LATITUDE(String fACILITY_LATITUDE) {
		FACILITY_LATITUDE = fACILITY_LATITUDE;
	}
	public String getFACILITY_TYPE() {
		return FACILITY_TYPE;
	}
	public void setFACILITY_TYPE(String fACILITY_TYPE) {
		FACILITY_TYPE = fACILITY_TYPE;
	}
	public String getMATCHED_STATENO() {
		return MATCHED_STATENO;
	}
	public void setMATCHED_STATENO(String mATCHED_STATENO) {
		MATCHED_STATENO = mATCHED_STATENO;
	}
	public String getMED_RECORD_NUMBER() {
		return MED_RECORD_NUMBER;
	}
	public void setMED_RECORD_NUMBER(String mED_RECORD_NUMBER) {
		MED_RECORD_NUMBER = mED_RECORD_NUMBER;
	}
	public String getPERSON_LATITUDE() {
		return PERSON_LATITUDE;
	}
	public void setPERSON_LATITUDE(String pERSON_LATITUDE) {
		PERSON_LATITUDE = pERSON_LATITUDE;
	}
	public String getFACILITY_LONGITUDE() {
		return FACILITY_LONGITUDE;
	}
	public void setFACILITY_LONGITUDE(String fACILITY_LONGITUDE) {
		FACILITY_LONGITUDE = fACILITY_LONGITUDE;
	}
	public String getPERSON_LONGITUDE() {
		return PERSON_LONGITUDE;
	}
	public void setPERSON_LONGITUDE(String pERSON_LONGITUDE) {
		PERSON_LONGITUDE = pERSON_LONGITUDE;
	}
	public String getDEATH_ID() {
		return DEATH_ID;
	}
	public void setDEATH_ID(String dEATH_ID) {
		DEATH_ID = dEATH_ID;
	}
	public String getDEATH_UNIQUE_ID() {
		return DEATH_UNIQUE_ID;
	}
	public void setDEATH_UNIQUE_ID(String dEATH_UNIQUE_ID) {
		DEATH_UNIQUE_ID = dEATH_UNIQUE_ID;
	}
	
	public String getDOD() {
		return DOD;
	}
	public void setDOD(String dOD) {
		DOD = dOD;
	}
	public String getBIRTH_STATE() {
		return BIRTH_STATE;
	}
	public void setBIRTH_STATE(String bIRTH_STATE) {
		BIRTH_STATE = bIRTH_STATE;
	}
	public String getETHNICITY() {
		return ETHNICITY;
	}
	public void setETHNICITY(String eTHNICITY) {
		ETHNICITY = eTHNICITY;
	}
	public String getFACILITY_STATE() {
		return FACILITY_STATE;
	}
	public void setFACILITY_STATE(String fACILITY_STATE) {
		FACILITY_STATE = fACILITY_STATE;
	}
	public String getPERSON_STATE() {
		return PERSON_STATE;
	}
	public void setPERSON_STATE(String pERSON_STATE) {
		PERSON_STATE = pERSON_STATE;
	}
	public String getFACILITY_PHONE() {
		return FACILITY_PHONE;
	}
	public void setFACILITY_PHONE(String fACILITY_PHONE) {
		FACILITY_PHONE = fACILITY_PHONE;
	}
	public String getFATHER_LNAME() {
		return FATHER_LNAME;
	}
	public void setFATHER_LNAME(String fATHER_LNAME) {
		FATHER_LNAME = fATHER_LNAME;
	}
	public String getFNAME() {
		return FNAME;
	}
	public void setFNAME(String fNAME) {
		FNAME = fNAME;
	}
	public String getMOTHER_MAIDEN() {
		return MOTHER_MAIDEN;
	}
	public void setMOTHER_MAIDEN(String mOTHER_MAIDEN) {
		MOTHER_MAIDEN = mOTHER_MAIDEN;
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
	public String getAGE_DEATH() {
		return AGE_DEATH;
	}
	public void setAGE_DEATH(String aGE_DEATH) {
		AGE_DEATH = aGE_DEATH;
	}
	public String getDEATH_COUNTY() {
		return DEATH_COUNTY;
	}
	public void setDEATH_COUNTY(String dEATH_COUNTY) {
		DEATH_COUNTY = dEATH_COUNTY;
	}
	public String getFACILITY_COUNTY() {
		return FACILITY_COUNTY;
	}
	public void setFACILITY_COUNTY(String fACILITY_COUNTY) {
		FACILITY_COUNTY = fACILITY_COUNTY;
	}
	public String getINST() {
		return INST;
	}
	public void setINST(String iNST) {
		INST = iNST;
	}
	public String getMATCH_CD() {
		return MATCH_CD;
	}
	public void setMATCH_CD(String mATCH_CD) {
		MATCH_CD = mATCH_CD;
	}
	public String getNYS_COUNTY() {
		return NYS_COUNTY;
	}
	public void setNYS_COUNTY(String nYS_COUNTY) {
		NYS_COUNTY = nYS_COUNTY;
	}
	public String getPERSON_COUNTY() {
		return PERSON_COUNTY;
	}
	public void setPERSON_COUNTY(String pERSON_COUNTY) {
		PERSON_COUNTY = pERSON_COUNTY;
	}
	public String getSOUNDEX() {
		return SOUNDEX;
	}
	public void setSOUNDEX(String sOUNDEX) {
		SOUNDEX = sOUNDEX;
	}
	public String getFACILITY_CITY() {
		return FACILITY_CITY;
	}
	public void setFACILITY_CITY(String fACILITY_CITY) {
		FACILITY_CITY = fACILITY_CITY;
	}
	public String getPERSON_CITY() {
		return PERSON_CITY;
	}
	public void setPERSON_CITY(String pERSON_CITY) {
		PERSON_CITY = pERSON_CITY;
	}
	public String getCAUSE1() {
		return CAUSE1;
	}
	public void setCAUSE1(String cAUSE1) {
		CAUSE1 = cAUSE1;
	}
	public String getCAUSE10() {
		return CAUSE10;
	}
	public void setCAUSE10(String cAUSE10) {
		CAUSE10 = cAUSE10;
	}
	public String getCAUSE11() {
		return CAUSE11;
	}
	public void setCAUSE11(String cAUSE11) {
		CAUSE11 = cAUSE11;
	}
	public String getCAUSE12() {
		return CAUSE12;
	}
	public void setCAUSE12(String cAUSE12) {
		CAUSE12 = cAUSE12;
	}
	public String getCAUSE13() {
		return CAUSE13;
	}
	public void setCAUSE13(String cAUSE13) {
		CAUSE13 = cAUSE13;
	}
	public String getCAUSE14() {
		return CAUSE14;
	}
	public void setCAUSE14(String cAUSE14) {
		CAUSE14 = cAUSE14;
	}
	public String getCAUSE15() {
		return CAUSE15;
	}
	public void setCAUSE15(String cAUSE15) {
		CAUSE15 = cAUSE15;
	}
	public String getCAUSE16() {
		return CAUSE16;
	}
	public void setCAUSE16(String cAUSE16) {
		CAUSE16 = cAUSE16;
	}
	public String getCAUSE17() {
		return CAUSE17;
	}
	public void setCAUSE17(String cAUSE17) {
		CAUSE17 = cAUSE17;
	}
	public String getCAUSE18() {
		return CAUSE18;
	}
	public void setCAUSE18(String cAUSE18) {
		CAUSE18 = cAUSE18;
	}
	public String getCAUSE19() {
		return CAUSE19;
	}
	public void setCAUSE19(String cAUSE19) {
		CAUSE19 = cAUSE19;
	}
	public String getCAUSE2() {
		return CAUSE2;
	}
	public void setCAUSE2(String cAUSE2) {
		CAUSE2 = cAUSE2;
	}
	public String getCAUSE20() {
		return CAUSE20;
	}
	public void setCAUSE20(String cAUSE20) {
		CAUSE20 = cAUSE20;
	}
	public String getCAUSE3() {
		return CAUSE3;
	}
	public void setCAUSE3(String cAUSE3) {
		CAUSE3 = cAUSE3;
	}
	public String getCAUSE4() {
		return CAUSE4;
	}
	public void setCAUSE4(String cAUSE4) {
		CAUSE4 = cAUSE4;
	}
	public String getCAUSE5() {
		return CAUSE5;
	}
	public void setCAUSE5(String cAUSE5) {
		CAUSE5 = cAUSE5;
	}
	public String getCAUSE6() {
		return CAUSE6;
	}
	public void setCAUSE6(String cAUSE6) {
		CAUSE6 = cAUSE6;
	}
	public String getCAUSE7() {
		return CAUSE7;
	}
	public void setCAUSE7(String cAUSE7) {
		CAUSE7 = cAUSE7;
	}
	public String getCAUSE8() {
		return CAUSE8;
	}
	public void setCAUSE8(String cAUSE8) {
		CAUSE8 = cAUSE8;
	}
	public String getCAUSE9() {
		return CAUSE9;
	}
	public void setCAUSE9(String cAUSE9) {
		CAUSE9 = cAUSE9;
	}
	public String getFACILITY_PLUS4() {
		return FACILITY_PLUS4;
	}
	public void setFACILITY_PLUS4(String fACILITY_PLUS4) {
		FACILITY_PLUS4 = fACILITY_PLUS4;
	}
	public String getMATCH_PASS() {
		return MATCH_PASS;
	}
	public void setMATCH_PASS(String mATCH_PASS) {
		MATCH_PASS = mATCH_PASS;
	}
	public String getNYC_FAC_CODE() {
		return NYC_FAC_CODE;
	}
	public void setNYC_FAC_CODE(String nYC_FAC_CODE) {
		NYC_FAC_CODE = nYC_FAC_CODE;
	}
	public String getPERSON_PLUS4() {
		return PERSON_PLUS4;
	}
	public void setPERSON_PLUS4(String pERSON_PLUS4) {
		PERSON_PLUS4 = pERSON_PLUS4;
	}
	public String getUNDERLYING() {
		return UNDERLYING;
	}
	public void setUNDERLYING(String uNDERLYING) {
		UNDERLYING = uNDERLYING;
	}
	public String getICD10() {
		return ICD10;
	}
	public void setICD10(String iCD10) {
		ICD10 = iCD10;
	}
	public String getFACILITY_ZIP() {
		return FACILITY_ZIP;
	}
	public void setFACILITY_ZIP(String fACILITY_ZIP) {
		FACILITY_ZIP = fACILITY_ZIP;
	}
	public String getFIPS_PLACE() {
		return FIPS_PLACE;
	}
	public void setFIPS_PLACE(String fIPS_PLACE) {
		FIPS_PLACE = fIPS_PLACE;
	}
	public String getLOCAL_REG_NO() {
		return LOCAL_REG_NO;
	}
	public void setLOCAL_REG_NO(String lOCAL_REG_NO) {
		LOCAL_REG_NO = lOCAL_REG_NO;
	}
	public String getPERSON_ZIP() {
		return PERSON_ZIP;
	}
	public void setPERSON_ZIP(String pERSON_ZIP) {
		PERSON_ZIP = pERSON_ZIP;
	}
	public String getSUFFIX() {
		return SUFFIX;
	}
	public void setSUFFIX(String sUFFIX) {
		SUFFIX = sUFFIX;
	}
	public String getTRANS_FAC_CD() {
		return TRANS_FAC_CD;
	}
	public void setTRANS_FAC_CD(String tRANS_FAC_CD) {
		TRANS_FAC_CD = tRANS_FAC_CD;
	}
	public String getDEATH_FILE() {
		return DEATH_FILE;
	}
	public void setDEATH_FILE(String dEATH_FILE) {
		DEATH_FILE = dEATH_FILE;
	}
	public String getDEATH_CERT() {
		return DEATH_CERT;
	}
	public void setDEATH_CERT(String dEATH_CERT) {
		DEATH_CERT = dEATH_CERT;
	}
	public String getDEATH_CERT_LICENSE() {
		return DEATH_CERT_LICENSE;
	}
	public void setDEATH_CERT_LICENSE(String dEATH_CERT_LICENSE) {
		DEATH_CERT_LICENSE = dEATH_CERT_LICENSE;
	}
	public String getFACILITY_CENSUS_TRACT() {
		return FACILITY_CENSUS_TRACT;
	}
	public void setFACILITY_CENSUS_TRACT(String fACILITY_CENSUS_TRACT) {
		FACILITY_CENSUS_TRACT = fACILITY_CENSUS_TRACT;
	}
	public String getINST_CODE() {
		return INST_CODE;
	}
	public void setINST_CODE(String iNST_CODE) {
		INST_CODE = iNST_CODE;
	}
	public String getPERSON_CENSUS_TRACT() {
		return PERSON_CENSUS_TRACT;
	}
	public void setPERSON_CENSUS_TRACT(String pERSON_CENSUS_TRACT) {
		PERSON_CENSUS_TRACT = pERSON_CENSUS_TRACT;
	}
	public String getPFI() {
		return PFI;
	}
	public void setPFI(String pFI) {
		PFI = pFI;
	}
	public String getFACILITY_ADDRESS() {
		return FACILITY_ADDRESS;
	}
	public void setFACILITY_ADDRESS(String fACILITY_ADDRESS) {
		FACILITY_ADDRESS = fACILITY_ADDRESS;
	}
	public String getPERSON_ADDRESS() {
		return PERSON_ADDRESS;
	}
	public void setPERSON_ADDRESS(String pERSON_ADDRESS) {
		PERSON_ADDRESS = pERSON_ADDRESS;
	}
	public String getREVIEW_UID() {
		return REVIEW_UID;
	}
	public void setREVIEW_UID(String rEVIEW_UID) {
		REVIEW_UID = rEVIEW_UID;
	}
	public String getFACILITY_NAME() {
		return FACILITY_NAME;
	}
	public void setFACILITY_NAME(String fACILITY_NAME) {
		FACILITY_NAME = fACILITY_NAME;
	}
	public String getSSN() {
		return SSN;
	}
	public void setSSN(String sSN) {
		SSN = sSN;
	}
	public Date getCREATE_DATE() {
		return CREATE_DATE;
	}
	public void setCREATE_DATE(Date cREATE_DATE) {
		CREATE_DATE = cREATE_DATE;
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
	public String getRES_STREET_NO() {
		return RES_STREET_NO;
	}
	public void setRES_STREET_NO(String rES_STREET_NO) {
		RES_STREET_NO = rES_STREET_NO;
	}
	public String getRES_STREET_DIRECT() {
		return RES_STREET_DIRECT;
	}
	public void setRES_STREET_DIRECT(String rES_STREET_DIRECT) {
		RES_STREET_DIRECT = rES_STREET_DIRECT;
	}
	public String getRES_STREET() {
		return RES_STREET;
	}
	public void setRES_STREET(String rES_STREET) {
		RES_STREET = rES_STREET;
	}
	public String getRES_STREET_TYPE() {
		return RES_STREET_TYPE;
	}
	public void setRES_STREET_TYPE(String rES_STREET_TYPE) {
		RES_STREET_TYPE = rES_STREET_TYPE;
	}
	public String getRES_APT_NO() {
		return RES_APT_NO;
	}
	public void setRES_APT_NO(String rES_APT_NO) {
		RES_APT_NO = rES_APT_NO;
	}
	public String getDateOfDeath() {
		return dateOfDeath;
	}
	public void setDateOfDeath(String dateOfDeath) {
		this.dateOfDeath = dateOfDeath;
	}
	
	private String DISCARD_STATUS;
	
	public String getDISCARD_STATUS() {
		return DISCARD_STATUS;
	}

	public void setDISCARD_STATUS(String dISCARD_STATUS) {
		DISCARD_STATUS = dISCARD_STATUS;;
	}
	public String getCauseOfDeath() {
		return causeOfDeath;
	}
	public void setCauseOfDeath(String causeOfDeath) {
		this.causeOfDeath = causeOfDeath;
	}
	public String getOI_CAUSE() {
		return OI_CAUSE;
	}
	public void setOI_CAUSE(String oI_CAUSE) {
		OI_CAUSE = oI_CAUSE;
	}
	
	
	
	
}
