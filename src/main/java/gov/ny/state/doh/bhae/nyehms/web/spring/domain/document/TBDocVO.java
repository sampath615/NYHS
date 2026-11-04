package gov.ny.state.doh.bhae.nyehms.web.spring.domain.document;

import java.sql.Date;


/**
 * This is pojo class for the TB doc. This is used by mybatis to create map.
 *
 * @author vxp06
 */
public class TBDocVO extends NYEHMSDocVO{

	private Date CREATE_DATE;
	private Date DX_DATE;
	private Date EHARS_IMPORT_DATE;
	private Date MATCHED_PROCESS_DATE;
	private Date FLUX_MATCHED_PROCESS_DATE;
	private Date NYC_SHIP_DATE;
	private Date RECEIVED_DATE;
	private Date REVIEW_DATE;
	private int DOCUMENT_ID;
	private long SERIALNO;
	private String BIRTH_SEX;
	private String RACE_ASIAN;
	private String RACE_BLACK;
	private String RACE_INDIAN;
	private String RACE_MULTIPLE;
	private String RACE_OTHER;
	private String RACE_PACIFIC;
	private String RACE_UNKNOWN;
	private String RACE_WHITE;
	private String CREATE_UID;
	private String DOB_TEXT;
	private String DOD_TEXT;
	private String FACILITY_LATITUDE;
	private String STATENO;
	private String PERSON_LATITUDE;
	private String TB_FILE;
	private String FACILITY_LONGITUDE;
	private String PERSON_LONGITUDE;
	private String FACILITY_NAME;
	private String PROV_PHONE;
	private String RACE_ASIAN_CD;
	private String RACE_POLY_CD;
	private String ETHNICITY;
	private String FACILITY_STATE;
	private String PERSON_STATE;
	private String PROV_STATE;
	private String RACE_CD;
	private String FACILITY_PHONE;
	private String FNAME;
	private String PERSON_PHONE;
	private String PROV_ADDRESS;
	private String LNAME;
	private String MNAME;
	private String FACILITY_COUNTY;
	private String PERSON_AREA_CD;
	private String PERSON_COUNTY;
	private String PROV_COUNTY;
	private String TB_FLAG;
	private String FACILITY_CITY;
	private String HOSP_CHART;
	private String PERSON_CITY;
	private String FACILITY_PLUS4;
	private String MATCH_PASS;
	private String PERSON_PLUS4;
	private String PROV_PLUS4;
	private String EHARS_STATUS_CD;
	private String MATCH_CD;
	private String REVIEW_UID;
	private String PROV_FNAME;
	private String PROV_LNAME;
	private String FACILITY_ZIP;
	private String PERSON_ZIP;
	private String PROV_ZIP;
	private String SUFFIX;
	private String ATYPICAL_MYCO;
	private String DISEASE_TYPE;
	private String OI;
	private String FACILITY_CENSUS_TRACT;
	private String PERSON_CENSUS_TRACT;
	private String PROV_CITY;
	private String FACILITY_ADDRESS;
	private String PERSON_ADDRESS;
	private String DIN;
	private String HOSP_PFI;
	private String VITAL_STATUS_AT_DIAGNOSIS;
	private String rawProviderAddress;
	private String rawFacilityAddress;
	private String rawPersonAddress;
	
	
	private String GENDER;
	private String birthSex;
	private String dateOfDeath;
	private String DOB;
	
	
	private String MI;
	
	
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
	
	
	public Date getCREATE_DATE() {
		return CREATE_DATE;
	}
	public void setCREATE_DATE(Date cREATE_DATE) {
		CREATE_DATE = cREATE_DATE;
	}
	public Date getDX_DATE() {
		return DX_DATE;
	}
	public void setDX_DATE(Date dX_DATE) {
		DX_DATE = dX_DATE;
	}
	public Date getEHARS_IMPORT_DATE() {
		return EHARS_IMPORT_DATE;
	}
	public void setEHARS_IMPORT_DATE(Date eHARS_IMPORT_DATE) {
		EHARS_IMPORT_DATE = eHARS_IMPORT_DATE;
	}
	public Date getMATCHED_PROCESS_DATE() {
		return MATCHED_PROCESS_DATE;
	}
	public void setMATCHED_PROCESS_DATE(Date mATCHED_PROCESS_DATE) {
		MATCHED_PROCESS_DATE = mATCHED_PROCESS_DATE;
	}
	public Date getNYC_SHIP_DATE() {
		return NYC_SHIP_DATE;
	}
	public void setNYC_SHIP_DATE(Date nYC_SHIP_DATE) {
		NYC_SHIP_DATE = nYC_SHIP_DATE;
	}
	public Date getRECEIVED_DATE() {
		return RECEIVED_DATE;
	}
	public void setRECEIVED_DATE(Date rECEIVED_DATE) {
		RECEIVED_DATE = rECEIVED_DATE;
	}
	public Date getREVIEW_DATE() {
		return REVIEW_DATE;
	}
	public void setREVIEW_DATE(Date rEVIEW_DATE) {
		REVIEW_DATE = rEVIEW_DATE;
	}
	public int getDOCUMENT_ID() {
		return DOCUMENT_ID;
	}
	public void setDOCUMENT_ID(int dOCUMENT_ID) {
		DOCUMENT_ID = dOCUMENT_ID;
	}

	public String getBIRTH_SEX() {
		return BIRTH_SEX;
	}
	public void setBIRTH_SEX(String bIRTH_SEX) {
		BIRTH_SEX = bIRTH_SEX;
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
	public String getCREATE_UID() {
		return CREATE_UID;
	}
	public void setCREATE_UID(String cREATE_UID) {
		CREATE_UID = cREATE_UID;
	}
	public String getDOB_TEXT() {
		return DOB_TEXT;
	}
	public void setDOB_TEXT(String dOB_TEXT) {
		DOB_TEXT = dOB_TEXT;
	}
	public String getDOD_TEXT() {
		return DOD_TEXT;
	}
	public void setDOD_TEXT(String dOD_TEXT) {
		DOD_TEXT = dOD_TEXT;
	}
	public String getFACILITY_LATITUDE() {
		return FACILITY_LATITUDE;
	}
	public void setFACILITY_LATITUDE(String fACILITY_LATITUDE) {
		FACILITY_LATITUDE = fACILITY_LATITUDE;
	}
	public String getSTATENO() {
		return STATENO;
	}
	public void setSTATENO(String sTATENO) {
		STATENO = sTATENO;
	}
	public String getPERSON_LATITUDE() {
		return PERSON_LATITUDE;
	}
	public void setPERSON_LATITUDE(String pERSON_LATITUDE) {
		PERSON_LATITUDE = pERSON_LATITUDE;
	}
	public String getTB_FILE() {
		return TB_FILE;
	}
	public void setTB_FILE(String tB_FILE) {
		TB_FILE = tB_FILE;
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
	public String getFACILITY_NAME() {
		return FACILITY_NAME;
	}
	public void setFACILITY_NAME(String fACILITY_NAME) {
		FACILITY_NAME = fACILITY_NAME;
	}
	public String getPROV_PHONE() {
		return PROV_PHONE;
	}
	public void setPROV_PHONE(String pROV_PHONE) {
		PROV_PHONE = pROV_PHONE;
	}
	public String getRACE_ASIAN_CD() {
		return RACE_ASIAN_CD;
	}
	public void setRACE_ASIAN_CD(String rACE_ASIAN_CD) {
		RACE_ASIAN_CD = rACE_ASIAN_CD;
	}
	public String getRACE_POLY_CD() {
		return RACE_POLY_CD;
	}
	public void setRACE_POLY_CD(String rACE_POLY_CD) {
		RACE_POLY_CD = rACE_POLY_CD;
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
	public String getPROV_STATE() {
		return PROV_STATE;
	}
	public void setPROV_STATE(String pROV_STATE) {
		PROV_STATE = pROV_STATE;
	}
	public String getRACE_CD() {
		return RACE_CD;
	}
	public void setRACE_CD(String rACE_CD) {
		RACE_CD = rACE_CD;
	}
	public String getFACILITY_PHONE() {
		return FACILITY_PHONE;
	}
	public void setFACILITY_PHONE(String fACILITY_PHONE) {
		FACILITY_PHONE = fACILITY_PHONE;
	}
	public String getFNAME() {
		return FNAME;
	}
	public void setFNAME(String fNAME) {
		FNAME = fNAME;
	}
	public String getPERSON_PHONE() {
		return PERSON_PHONE;
	}
	public void setPERSON_PHONE(String pERSON_PHONE) {
		PERSON_PHONE = pERSON_PHONE;
	}
	public String getPROV_ADDRESS() {
		return PROV_ADDRESS;
	}
	public void setPROV_ADDRESS(String pROV_ADDRESS) {
		PROV_ADDRESS = pROV_ADDRESS;
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
	public String getFACILITY_COUNTY() {
		return FACILITY_COUNTY;
	}
	public void setFACILITY_COUNTY(String fACILITY_COUNTY) {
		FACILITY_COUNTY = fACILITY_COUNTY;
	}
	public String getPERSON_AREA_CD() {
		return PERSON_AREA_CD;
	}
	public void setPERSON_AREA_CD(String pERSON_AREA_CD) {
		PERSON_AREA_CD = pERSON_AREA_CD;
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
	public String getTB_FLAG() {
		return TB_FLAG;
	}
	public void setTB_FLAG(String tB_FLAG) {
		TB_FLAG = tB_FLAG;
	}
	public String getFACILITY_CITY() {
		return FACILITY_CITY;
	}
	public void setFACILITY_CITY(String fACILITY_CITY) {
		FACILITY_CITY = fACILITY_CITY;
	}
	public String getHOSP_CHART() {
		return HOSP_CHART;
	}
	public void setHOSP_CHART(String hOSP_CHART) {
		HOSP_CHART = hOSP_CHART;
	}
	public String getPERSON_CITY() {
		return PERSON_CITY;
	}
	public void setPERSON_CITY(String pERSON_CITY) {
		PERSON_CITY = pERSON_CITY;
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
	public String getPERSON_PLUS4() {
		return PERSON_PLUS4;
	}
	public void setPERSON_PLUS4(String pERSON_PLUS4) {
		PERSON_PLUS4 = pERSON_PLUS4;
	}
	public String getPROV_PLUS4() {
		return PROV_PLUS4;
	}
	public void setPROV_PLUS4(String pROV_PLUS4) {
		PROV_PLUS4 = pROV_PLUS4;
	}
	public String getEHARS_STATUS_CD() {
		return EHARS_STATUS_CD;
	}
	public void setEHARS_STATUS_CD(String eHARS_STATUS_CD) {
		EHARS_STATUS_CD = eHARS_STATUS_CD;
	}
	public String getMATCH_CD() {
		return MATCH_CD;
	}
	public void setMATCH_CD(String mATCH_CD) {
		MATCH_CD = mATCH_CD;
	}
	public String getREVIEW_UID() {
		return REVIEW_UID;
	}
	public void setREVIEW_UID(String rEVIEW_UID) {
		REVIEW_UID = rEVIEW_UID;
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
	public String getFACILITY_ZIP() {
		return FACILITY_ZIP;
	}
	public void setFACILITY_ZIP(String fACILITY_ZIP) {
		FACILITY_ZIP = fACILITY_ZIP;
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
	public String getSUFFIX() {
		return SUFFIX;
	}
	public void setSUFFIX(String sUFFIX) {
		SUFFIX = sUFFIX;
	}

	public long getSERIALNO() {
		return SERIALNO;
	}

	public void setSERIALNO(long sERIALNO) {
		SERIALNO = sERIALNO;
	}

	public String getATYPICAL_MYCO() {
		return ATYPICAL_MYCO;
	}

	public void setATYPICAL_MYCO(String aTYPICAL_MYCO) {
		ATYPICAL_MYCO = aTYPICAL_MYCO;
	}

	public String getDISEASE_TYPE() {
		return DISEASE_TYPE;
	}

	public void setDISEASE_TYPE(String dISEASE_TYPE) {
		DISEASE_TYPE = dISEASE_TYPE;
	}

	public String getMI() {
		return MI;
	}

	public void setMI(String mI) {
		MI = mI;
	}

	public String getOI() {
		return OI;
	}
	public void setOI(String oI) {
		OI = oI;
	}
	public String getFACILITY_CENSUS_TRACT() {
		return FACILITY_CENSUS_TRACT;
	}
	public void setFACILITY_CENSUS_TRACT(String fACILITY_CENSUS_TRACT) {
		FACILITY_CENSUS_TRACT = fACILITY_CENSUS_TRACT;
	}
	public String getPERSON_CENSUS_TRACT() {
		return PERSON_CENSUS_TRACT;
	}
	public void setPERSON_CENSUS_TRACT(String pERSON_CENSUS_TRACT) {
		PERSON_CENSUS_TRACT = pERSON_CENSUS_TRACT;
	}
	public String getPROV_CITY() {
		return PROV_CITY;
	}
	public void setPROV_CITY(String pROV_CITY) {
		PROV_CITY = pROV_CITY;
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
	public String getDIN() {
		return DIN;
	}
	public void setDIN(String dIN) {
		DIN = dIN;
	}
	public String getHOSP_PFI() {
		return HOSP_PFI;
	}
	public void setHOSP_PFI(String hOSP_PFI) {
		HOSP_PFI = hOSP_PFI;
	}
	public String getVITAL_STATUS_AT_DIAGNOSIS() {
		return VITAL_STATUS_AT_DIAGNOSIS;
	}
	public void setVITAL_STATUS_AT_DIAGNOSIS(String vITAL_STATUS_AT_DIAGNOSIS) {
		VITAL_STATUS_AT_DIAGNOSIS = vITAL_STATUS_AT_DIAGNOSIS;
	}
	public String getRawFacilityAddress() {
		return rawFacilityAddress;
	}
	public void setRawFacilityAddress(String rawFacilityAddress) {
		this.rawFacilityAddress = rawFacilityAddress;
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
	public String getDateOfDeath() {
		return dateOfDeath;
	}
	public void setDateOfDeath(String dateOfDeath) {
		this.dateOfDeath = dateOfDeath;
	}
	public String getDOB() {
		return DOB;
	}
	public void setDOB(String dOB) {
		DOB = dOB;
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
