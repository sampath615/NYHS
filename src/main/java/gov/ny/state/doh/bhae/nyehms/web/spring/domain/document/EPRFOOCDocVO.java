package gov.ny.state.doh.bhae.nyehms.web.spring.domain.document;

import java.sql.Date;

/**
 * This is pojo class for EPRF OOC document.
 * This is used by mybatis for creating
 * object map for EPRF OOC document .
 *
 * @author ska03
 */
public class EPRFOOCDocVO extends NYEHMSDocVO {

	private String STATENO;
	private String SSN;
	private String NYSID;
	private String FACILITY_NAME;
	private String DIN;
	private String SUFFIX;
	private String PERSON_ZIP;
	private String PROV_ZIP;
	private String FACILITY_ZIP;
	private String PROV_FNAME;
	private String PROV_LNAME;
	private String PERSON_CITY;
	private String PROV_CITY;
	private String FACILITY_CITY;
	private String GENDER;
	private String CURRENT_GENDER;
	private String MATCH_CD;
	private String MEDICAID_NUMBER;
	private String RACE_CD;
	private String DOB;
	private String PERSON_PHONE;
	private String PROV_LICENSE;
	private String PROV_PHONE;
	private String FACILITY_PHONE;
	private String PERSON_LATITUDE;
	private String PERSON_LONGITUDE;
	private String PERSON_CENSUS_TRACT;
	private String PROV_LATITUDE;
	private String PROV_LONGITUDE;
	private String PROV_CENSUS_TRACT;
	private String FACILITY_LATITUDE;
	private String FACILITY_LONGITUDE;
	private String FACILITY_CENSUS_TRACT;
	private String ETHNICITY;
	private String PERSON_STATE;
	private String PROV_STATE;
	private String FACILITY_STATE;
	private String MED_RECORD_NUMBER;
	private String PROV_NPI;
	private String FACILITY_NPI;
	private String FNAME;
	private String MNAME;
	private String LNAME;
	private String PERSON_ADDRESS;
	private String PROV_ADDRESS;
	private String FACILITY_ADDRESS;
	private String UPLOAD_FILENAME;
	private String LAST_VIRAL_LOAD_RESULT;
	private String MATCHED_STATENO;
	private String UPLOAD_ID;
	private String REVIEW_UID;
	private String birthSex;
	private String FILE_TYPE_CD;
	private String RACE_INDIAN;
	private String RACE_BLACK;
	private String RACE_ASIAN;
	private String RACE_PACIFIC;
	private String RACE_WHITE;
	private String RACE_MULTIPLE;
	private String RACE_OTHER;
	private String RACE_UNKNOWN;
	private int MATCH_WEIGHT;
	private int DOCUMENT_ID;
	private Integer LAST_CD4_RESULT_COUNT;
	private Integer LAST_CD4_RESULT_PERCENT;
	private int CURRENT_PROCESS_STATUS;
	private int MATCH_PASS;
	private int QS_PARENT_DOC_ID;
	private Date PROV_LAST_VISIT_DATE;
	private Date LAST_VIRAL_LOAD_DATE;
	private Date LAST_CD4_DATE;
	private Date UPLOAD_DT;
	private Date REVIEW_DT;
	private Date MATCHED_PROCESS_DATE;
	private Date FLUX_MATCHED_PROCESS_DATE;
	private Date EHARS_IMPORT_DATE;
	private Date NYC_SHIP_DATE;
	private String BIRTH_SEX;
	 
	private String PERSON_COUNTY;
	private String PROV_COUNTY;
	private String FACILITY_COUNTY;

	 private String rawPersonAddress;
	 private String rawProviderAddress;
	 private String rawFacilityAddress;
	 private String PREF_NAME;
	 
	 
	private int PARENT_DOCUMENT_ID;
	
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
	

	public int getPARENT_DOCUMENT_ID() {
		return PARENT_DOCUMENT_ID;
	}

	public void setPARENT_DOCUMENT_ID(int pARENT_DOCUMENT_ID) {
		PARENT_DOCUMENT_ID = pARENT_DOCUMENT_ID;
	}
	public String getSTATENO() {
		return STATENO;
	}
	public void setSTATENO(String sTATENO) {
		STATENO = sTATENO;
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
	public String getFACILITY_NAME() {
		return FACILITY_NAME;
	}
	public void setFACILITY_NAME(String fACILITY_NAME) {
		FACILITY_NAME = fACILITY_NAME;
	}
	public String getDIN() {
		return DIN;
	}
	public void setDIN(String dIN) {
		DIN = dIN;
	}
	public String getSUFFIX() {
		return SUFFIX;
	}
	public void setSUFFIX(String sUFFIX) {
		SUFFIX = sUFFIX;
	}

	public String getPERSON_ZIP() {
		return PERSON_ZIP;
	}
	public void setPERSON_ZIP(String pERSON_ZIP) {
		PERSON_ZIP = pERSON_ZIP;
	}
	public String getPROV_ZIP() {
		return PROV_ZIP;
	}
	public void setPROV_ZIP(String pROV_ZIP) {
		PROV_ZIP = pROV_ZIP;
	}
	public String getFACILITY_ZIP() {
		return FACILITY_ZIP;
	}
	public void setFACILITY_ZIP(String fACILITY_ZIP) {
		FACILITY_ZIP = fACILITY_ZIP;
	}
	public String getPROV_FNAME() {
		return PROV_FNAME;
	}
	public void setPROV_FNAME(String pROV_FNAME) {
		PROV_FNAME = pROV_FNAME;
	}
	public String getPROV_LNAME() {
		return PROV_LNAME;
	}
	public void setPROV_LNAME(String pROV_LNAME) {
		PROV_LNAME = pROV_LNAME;
	}
	public String getPERSON_CITY() {
		return PERSON_CITY;
	}
	public void setPERSON_CITY(String pERSON_CITY) {
		PERSON_CITY = pERSON_CITY;
	}
	public String getPROV_CITY() {
		return PROV_CITY;
	}
	public void setPROV_CITY(String pROV_CITY) {
		PROV_CITY = pROV_CITY;
	}
	public String getFACILITY_CITY() {
		return FACILITY_CITY;
	}
	public void setFACILITY_CITY(String fACILITY_CITY) {
		FACILITY_CITY = fACILITY_CITY;
	}

	public String getMATCH_CD() {
		return MATCH_CD;
	}
	public void setMATCH_CD(String mATCH_CD) {
		MATCH_CD = mATCH_CD;
	}
	public String getMEDICAID_NUMBER() {
		return MEDICAID_NUMBER;
	}
	public void setMEDICAID_NUMBER(String mEDICAID_NUMBER) {
		MEDICAID_NUMBER = mEDICAID_NUMBER;
	}
	public String getRACE_CD() {
		return RACE_CD;
	}
	public void setRACE_CD(String rACE_CD) {
		RACE_CD = rACE_CD;
	}
	public String getDOB() {
		return DOB;
	}
	public void setDOB(String dOB) {
		DOB = dOB;
	}
	
	public String getPERSON_PHONE() {
		return PERSON_PHONE;
	}

	public void setPERSON_PHONE(String pERSON_PHONE) {
		PERSON_PHONE = pERSON_PHONE;
	}

	public String getPROV_LICENSE() {
		return PROV_LICENSE;
	}
	public void setPROV_LICENSE(String pROV_LICENSE) {
		PROV_LICENSE = pROV_LICENSE;
	}
	public String getPROV_PHONE() {
		return PROV_PHONE;
	}
	public void setPROV_PHONE(String pROV_PHONE) {
		PROV_PHONE = pROV_PHONE;
	}
	public String getFACILITY_PHONE() {
		return FACILITY_PHONE;
	}
	public void setFACILITY_PHONE(String fACILITY_PHONE) {
		FACILITY_PHONE = fACILITY_PHONE;
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
	public String getPERSON_CENSUS_TRACT() {
		return PERSON_CENSUS_TRACT;
	}
	public void setPERSON_CENSUS_TRACT(String pERSON_CENSUS_TRACT) {
		PERSON_CENSUS_TRACT = pERSON_CENSUS_TRACT;
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
	public String getPROV_CENSUS_TRACT() {
		return PROV_CENSUS_TRACT;
	}
	public void setPROV_CENSUS_TRACT(String pROV_CENSUS_TRACT) {
		PROV_CENSUS_TRACT = pROV_CENSUS_TRACT;
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
	public String getFACILITY_CENSUS_TRACT() {
		return FACILITY_CENSUS_TRACT;
	}
	public void setFACILITY_CENSUS_TRACT(String fACILITY_CENSUS_TRACT) {
		FACILITY_CENSUS_TRACT = fACILITY_CENSUS_TRACT;
	}
	public String getETHNICITY() {
		return ETHNICITY;
	}
	public void setETHNICITY(String eTHNICITY) {
		ETHNICITY = eTHNICITY;
	}
	public String getPERSON_STATE() {
		return PERSON_STATE;
	}
	public void setPERSON_STATE(String pERSON_STATE) {
		PERSON_STATE = pERSON_STATE;
	}
	public String getPROV_STATE() {
		return PROV_STATE;
	}
	public void setPROV_STATE(String pROV_STATE) {
		PROV_STATE = pROV_STATE;
	}
	public String getFACILITY_STATE() {
		return FACILITY_STATE;
	}
	public void setFACILITY_STATE(String fACILITY_STATE) {
		FACILITY_STATE = fACILITY_STATE;
	}
	public String getMED_RECORD_NUMBER() {
		return MED_RECORD_NUMBER;
	}
	public void setMED_RECORD_NUMBER(String mED_RECORD_NUMBER) {
		MED_RECORD_NUMBER = mED_RECORD_NUMBER;
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
	public String getPERSON_ADDRESS() {
		return PERSON_ADDRESS;
	}
	public void setPERSON_ADDRESS(String pERSON_ADDRESS) {
		PERSON_ADDRESS = pERSON_ADDRESS;
	}
	public String getPROV_ADDRESS() {
		return PROV_ADDRESS;
	}
	public void setPROV_ADDRESS(String pROV_ADDRESS) {
		PROV_ADDRESS = pROV_ADDRESS;
	}
	public String getFACILITY_ADDRESS() {
		return FACILITY_ADDRESS;
	}
	public void setFACILITY_ADDRESS(String fACILITY_ADDRESS) {
		FACILITY_ADDRESS = fACILITY_ADDRESS;
	}
	public String getUPLOAD_FILENAME() {
		return UPLOAD_FILENAME;
	}
	public void setUPLOAD_FILENAME(String uPLOAD_FILENAME) {
		UPLOAD_FILENAME = uPLOAD_FILENAME;
	}
	public String getLAST_VIRAL_LOAD_RESULT() {
		return LAST_VIRAL_LOAD_RESULT;
	}
	public void setLAST_VIRAL_LOAD_RESULT(String lAST_VIRAL_LOAD_RESULT) {
		LAST_VIRAL_LOAD_RESULT = lAST_VIRAL_LOAD_RESULT;
	}
	public String getMATCHED_STATENO() {
		return MATCHED_STATENO;
	}
	public void setMATCHED_STATENO(String mATCHED_STATENO) {
		MATCHED_STATENO = mATCHED_STATENO;
	}
	public String getUPLOAD_ID() {
		return UPLOAD_ID;
	}
	public void setUPLOAD_ID(String uPLOAD_ID) {
		UPLOAD_ID = uPLOAD_ID;
	}
	public String getREVIEW_UID() {
		return REVIEW_UID;
	}
	public void setREVIEW_UID(String rEVIEW_UID) {
		REVIEW_UID = rEVIEW_UID;
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
	public String getFILE_TYPE_CD() {
		return FILE_TYPE_CD;
	}
	public void setFILE_TYPE_CD(String fILE_TYPE_CD) {
		FILE_TYPE_CD = fILE_TYPE_CD;
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
	public int getMATCH_WEIGHT() {
		return MATCH_WEIGHT;
	}
	public void setMATCH_WEIGHT(int mATCH_WEIGHT) {
		MATCH_WEIGHT = mATCH_WEIGHT;
	}
	public int getDOCUMENT_ID() {
		return DOCUMENT_ID;
	}
	public void setDOCUMENT_ID(int dOCUMENT_ID) {
		DOCUMENT_ID = dOCUMENT_ID;
	}
	public Integer getLAST_CD4_RESULT_COUNT() {
		return LAST_CD4_RESULT_COUNT;
	}
	public void setLAST_CD4_RESULT_COUNT(Integer lAST_CD4_RESULT_COUNT) {
		LAST_CD4_RESULT_COUNT = lAST_CD4_RESULT_COUNT;
	}
	public Integer getLAST_CD4_RESULT_PERCENT() {
		return LAST_CD4_RESULT_PERCENT;
	}
	public void setLAST_CD4_RESULT_PERCENT(Integer lAST_CD4_RESULT_PERCENT) {
		LAST_CD4_RESULT_PERCENT = lAST_CD4_RESULT_PERCENT;
	}
	public int getCURRENT_PROCESS_STATUS() {
		return CURRENT_PROCESS_STATUS;
	}
	public void setCURRENT_PROCESS_STATUS(int cURRENT_PROCESS_STATUS) {
		CURRENT_PROCESS_STATUS = cURRENT_PROCESS_STATUS;
	}
	public int getMATCH_PASS() {
		return MATCH_PASS;
	}
	public void setMATCH_PASS(int mATCH_PASS) {
		MATCH_PASS = mATCH_PASS;
	}
	public int getQS_PARENT_DOC_ID() {
		return QS_PARENT_DOC_ID;
	}
	public void setQS_PARENT_DOC_ID(int qS_PARENT_DOC_ID) {
		QS_PARENT_DOC_ID = qS_PARENT_DOC_ID;
	}
	public Date getPROV_LAST_VISIT_DATE() {
		return PROV_LAST_VISIT_DATE;
	}
	public void setPROV_LAST_VISIT_DATE(Date pROV_LAST_VISIT_DATE) {
		PROV_LAST_VISIT_DATE = pROV_LAST_VISIT_DATE;
	}
	public Date getLAST_VIRAL_LOAD_DATE() {
		return LAST_VIRAL_LOAD_DATE;
	}
	public void setLAST_VIRAL_LOAD_DATE(Date lAST_VIRAL_LOAD_DATE) {
		LAST_VIRAL_LOAD_DATE = lAST_VIRAL_LOAD_DATE;
	}
	public Date getLAST_CD4_DATE() {
		return LAST_CD4_DATE;
	}
	public void setLAST_CD4_DATE(Date lAST_CD4_DATE) {
		LAST_CD4_DATE = lAST_CD4_DATE;
	}
	public Date getUPLOAD_DT() {
		return UPLOAD_DT;
	}
	public void setUPLOAD_DT(Date uPLOAD_DT) {
		UPLOAD_DT = uPLOAD_DT;
	}
	public Date getREVIEW_DT() {
		return REVIEW_DT;
	}
	public void setREVIEW_DT(Date rEVIEW_DT) {
		REVIEW_DT = rEVIEW_DT;
	}
	public Date getMATCHED_PROCESS_DATE() {
		return MATCHED_PROCESS_DATE;
	}
	public void setMATCHED_PROCESS_DATE(Date mATCHED_PROCESS_DATE) {
		MATCHED_PROCESS_DATE = mATCHED_PROCESS_DATE;
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
	public String getRawPersonAddress() {
		return rawPersonAddress;
	}
	public void setRawPersonAddress(String rawPersonAddress) {
		this.rawPersonAddress = rawPersonAddress;
	}
	public String getRawProviderAddress() {
		return rawProviderAddress;
	}
	public void setRawProviderAddress(String rawProviderAddress) {
		this.rawProviderAddress = rawProviderAddress;
	}
	public String getRawFacilityAddress() {
		return rawFacilityAddress;
	}
	public void setRawFacilityAddress(String rawFacilityAddress) {
		this.rawFacilityAddress = rawFacilityAddress;
	}
	public String getPERSON_COUNTY() {
		return PERSON_COUNTY;
	}
	public void setPERSON_COUNTY(String pERSON_COUNTY) {
		PERSON_COUNTY = pERSON_COUNTY;
	}
	public String getPROV_COUNTY() {
		return PROV_COUNTY;
	}
	public void setPROV_COUNTY(String pROV_COUNTY) {
		PROV_COUNTY = pROV_COUNTY;
	}
	public String getFACILITY_COUNTY() {
		return FACILITY_COUNTY;
	}
	public void setFACILITY_COUNTY(String fACILITY_COUNTY) {
		FACILITY_COUNTY = fACILITY_COUNTY;
	}
	public String getPREF_NAME() {
		return PREF_NAME;
	}
	public void setPREF_NAME(String pREF_NAME) {
		PREF_NAME = pREF_NAME;
	}
	public String getCURRENT_GENDER() {
		return CURRENT_GENDER;
	}
	public void setCURRENT_GENDER(String cURRENT_GENDER) {
		CURRENT_GENDER = cURRENT_GENDER;
	}
	public String getBIRTH_SEX() {
		return BIRTH_SEX;
	}
	public void setBIRTH_SEX(String bIRTH_SEX) {
		BIRTH_SEX = bIRTH_SEX;
	}
	
	private String DISCARD_STATUS;
	
	public String getDISCARD_STATUS() {
		return DISCARD_STATUS;
	}

	public void setDISCARD_STATUS(String dISCARD_STATUS) {
		DISCARD_STATUS = dISCARD_STATUS;;
	}

	public Date getFLUX_MATCHED_PROCESS_DATE() {
		return FLUX_MATCHED_PROCESS_DATE;
	}

	public void setFLUX_MATCHED_PROCESS_DATE(Date fLUX_MATCHED_PROCESS_DATE) {
		FLUX_MATCHED_PROCESS_DATE = fLUX_MATCHED_PROCESS_DATE;
	}
	
	
	
	
}
