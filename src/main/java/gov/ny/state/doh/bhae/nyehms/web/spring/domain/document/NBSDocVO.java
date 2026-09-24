package gov.ny.state.doh.bhae.nyehms.web.spring.domain.document;

/**
 * This is pojo class for the NBS doc. This is used by mybatis to create map.
 *
 * @author ska03
 */
public class NBSDocVO extends NYEHMSDocVO{

	private int DOCUMENT_ID;
	private int NYS_ID;
	private String LABID;
	private String LABNO;
	private String LINK;
	private String MEDRECNO;
	private String INFANT_FNAME;
	private String INFANT_LNAME;
	private String AKA;
	private String BIRTH_DATE;
	private String BIRTH_TIME;
	private Integer BIRTH_WEIGHT;
	private String RACE_WHITE;
	private String RACE_BLACK;
	private String RACE_ASIAN;
	private String RACE_OTHER;
	private String RACE_INDIAN;
	private String ETHNICITY;
	private String SEX;
	private String MOTHER_FNAME;
	private String MOTHER_LNAME;
	private String MOTHER_AGE;
	private String MOTHER_DOB;
	private String MOTHER_ADDRESS;
	private String MOTHER_CITY;
	private String MOTHER_STATE;
	private String MOTHER_ZIP;
	private String MOTHER_COUNTY;
	private String MOTHER_REGION;
	private String MOTHER_PHONE;
	private String MOTHER_LATITUDE;
	private String MOTHER_LONGITUDE;
	private String MOTHER_CENSUS_TRACT;
	private String COLLECTION_DATE;
	private String DATE_RECEIVED;
	private String DATE_REPORTED;
	private String INIT_RPT;
	private String BIRTH_HOSPITAL_CODE;
	private String PFI;
	private String FACILITY_NAME;
	private String FACILITY_ADDRESS;
	private String FACILITY_CITY;
	private String FACILITY_STATE;
	private String FACILITY_ZIP;
	private String FACILITY_COUNTY;
	private String FACILITY_REGION;
	private String FACILITY_LATITUDE;
	private String FACILITY_LONGITUDE;
	private String FACILITY_CENSUS_TRACT;
	private String TWIN_STATUS;
	private String HIV_FINAL_STATUS;
	private String TRANS_CALC_KEEP;
	private int CLIMS_RECID;
	private String PATIENTID;
	private String MEMO;
	private String FACILITY_PHONE;
	private String FACILITY_EHARS_UID;
	public int getDOCUMENT_ID() {
		return DOCUMENT_ID;
	}
	public void setDOCUMENT_ID(int dOCUMENT_ID) {
		DOCUMENT_ID = dOCUMENT_ID;
	}
	public int getNYS_ID() {
		return NYS_ID;
	}
	public void setNYS_ID(int nYS_ID) {
		NYS_ID = nYS_ID;
	}
	public String getLABID() {
		return LABID;
	}
	public void setLABID(String lABID) {
		LABID = lABID;
	}
	public String getLABNO() {
		return LABNO;
	}
	public void setLABNO(String lABNO) {
		LABNO = lABNO;
	}
	public String getLINK() {
		return LINK;
	}
	public void setLINK(String lINK) {
		LINK = lINK;
	}
	public String getMEDRECNO() {
		return MEDRECNO;
	}
	public void setMEDRECNO(String mEDRECNO) {
		MEDRECNO = mEDRECNO;
	}
	public String getINFANT_FNAME() {
		return INFANT_FNAME;
	}
	public void setINFANT_FNAME(String iNFANT_FNAME) {
		INFANT_FNAME = iNFANT_FNAME;
	}
	public String getINFANT_LNAME() {
		return INFANT_LNAME;
	}
	public void setINFANT_LNAME(String iNFANT_LNAME) {
		INFANT_LNAME = iNFANT_LNAME;
	}
	public String getAKA() {
		return AKA;
	}
	public void setAKA(String aKA) {
		AKA = aKA;
	}
	public String getBIRTH_DATE() {
		return BIRTH_DATE;
	}
	public void setBIRTH_DATE(String bIRTH_DATE) {
		BIRTH_DATE = bIRTH_DATE;
	}
	public String getBIRTH_TIME() {
		return BIRTH_TIME;
	}
	public void setBIRTH_TIME(String bIRTH_TIME) {
		BIRTH_TIME = bIRTH_TIME;
	}
	public Integer getBIRTH_WEIGHT() {
		return BIRTH_WEIGHT;
	}
	public void setBIRTH_WEIGHT(Integer bIRTH_WEIGHT) {
		BIRTH_WEIGHT = bIRTH_WEIGHT;
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
	public String getRACE_OTHER() {
		return RACE_OTHER;
	}
	public void setRACE_OTHER(String rACE_OTHER) {
		RACE_OTHER = rACE_OTHER;
	}
	public String getRACE_INDIAN() {
		return RACE_INDIAN;
	}
	public void setRACE_INDIAN(String rACE_INDIAN) {
		RACE_INDIAN = rACE_INDIAN;
	}
	public String getETHNICITY() {
		return ETHNICITY;
	}
	public void setETHNICITY(String eTHNICITY) {
		ETHNICITY = eTHNICITY;
	}
	public String getSEX() {
		return SEX;
	}
	public void setSEX(String sEX) {
		SEX = sEX;
	}
	public String getMOTHER_FNAME() {
		return MOTHER_FNAME;
	}
	public void setMOTHER_FNAME(String mOTHER_FNAME) {
		MOTHER_FNAME = mOTHER_FNAME;
	}
	public String getMOTHER_LNAME() {
		return MOTHER_LNAME;
	}
	public void setMOTHER_LNAME(String mOTHER_LNAME) {
		MOTHER_LNAME = mOTHER_LNAME;
	}
	public String getMOTHER_AGE() {
		return MOTHER_AGE;
	}
	public void setMOTHER_AGE(String mOTHER_AGE) {
		MOTHER_AGE = mOTHER_AGE;
	}
	public String getMOTHER_DOB() {
		return MOTHER_DOB;
	}
	public void setMOTHER_DOB(String mOTHER_DOB) {
		MOTHER_DOB = mOTHER_DOB;
	}
	public String getMOTHER_ADDRESS() {
		return MOTHER_ADDRESS;
	}
	public void setMOTHER_ADDRESS(String mOTHER_ADDRESS) {
		MOTHER_ADDRESS = mOTHER_ADDRESS;
	}
	public String getMOTHER_CITY() {
		return MOTHER_CITY;
	}
	public void setMOTHER_CITY(String mOTHER_CITY) {
		MOTHER_CITY = mOTHER_CITY;
	}
	public String getMOTHER_STATE() {
		return MOTHER_STATE;
	}
	public void setMOTHER_STATE(String mOTHER_STATE) {
		MOTHER_STATE = mOTHER_STATE;
	}
	public String getMOTHER_ZIP() {
		return MOTHER_ZIP;
	}
	public void setMOTHER_ZIP(String mOTHER_ZIP) {
		MOTHER_ZIP = mOTHER_ZIP;
	}
	public String getMOTHER_COUNTY() {
		return MOTHER_COUNTY;
	}
	public void setMOTHER_COUNTY(String mOTHER_COUNTY) {
		MOTHER_COUNTY = mOTHER_COUNTY;
	}
	public String getMOTHER_REGION() {
		return MOTHER_REGION;
	}
	public void setMOTHER_REGION(String mOTHER_REGION) {
		MOTHER_REGION = mOTHER_REGION;
	}
	public String getMOTHER_PHONE() {
		return MOTHER_PHONE;
	}
	public void setMOTHER_PHONE(String mOTHER_PHONE) {
		MOTHER_PHONE = mOTHER_PHONE;
	}
	public String getMOTHER_LATITUDE() {
		return MOTHER_LATITUDE;
	}
	public void setMOTHER_LATITUDE(String mOTHER_LATITUDE) {
		MOTHER_LATITUDE = mOTHER_LATITUDE;
	}
	public String getMOTHER_LONGITUDE() {
		return MOTHER_LONGITUDE;
	}
	public void setMOTHER_LONGITUDE(String mOTHER_LONGITUDE) {
		MOTHER_LONGITUDE = mOTHER_LONGITUDE;
	}
	public String getMOTHER_CENSUS_TRACT() {
		return MOTHER_CENSUS_TRACT;
	}
	public void setMOTHER_CENSUS_TRACT(String mOTHER_CENSUS_TRACT) {
		MOTHER_CENSUS_TRACT = mOTHER_CENSUS_TRACT;
	}
	public String getCOLLECTION_DATE() {
		return COLLECTION_DATE;
	}
	public void setCOLLECTION_DATE(String cOLLECTION_DATE) {
		COLLECTION_DATE = cOLLECTION_DATE;
	}
	public String getDATE_RECEIVED() {
		return DATE_RECEIVED;
	}
	public void setDATE_RECEIVED(String dATE_RECEIVED) {
		DATE_RECEIVED = dATE_RECEIVED;
	}
	public String getDATE_REPORTED() {
		return DATE_REPORTED;
	}
	public void setDATE_REPORTED(String dATE_REPORTED) {
		DATE_REPORTED = dATE_REPORTED;
	}
	public String getINIT_RPT() {
		return INIT_RPT;
	}
	public void setINIT_RPT(String iNIT_RPT) {
		INIT_RPT = iNIT_RPT;
	}
	public String getBIRTH_HOSPITAL_CODE() {
		return BIRTH_HOSPITAL_CODE;
	}
	public void setBIRTH_HOSPITAL_CODE(String bIRTH_HOSPITAL_CODE) {
		BIRTH_HOSPITAL_CODE = bIRTH_HOSPITAL_CODE;
	}
	public String getPFI() {
		return PFI;
	}
	public void setPFI(String pFI) {
		PFI = pFI;
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
	public String getFACILITY_COUNTY() {
		return FACILITY_COUNTY;
	}
	public void setFACILITY_COUNTY(String fACILITY_COUNTY) {
		FACILITY_COUNTY = fACILITY_COUNTY;
	}
	public String getFACILITY_REGION() {
		return FACILITY_REGION;
	}
	public void setFACILITY_REGION(String fACILITY_REGION) {
		FACILITY_REGION = fACILITY_REGION;
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
	public String getTWIN_STATUS() {
		return TWIN_STATUS;
	}
	public void setTWIN_STATUS(String tWIN_STATUS) {
		TWIN_STATUS = tWIN_STATUS;
	}
	public String getHIV_FINAL_STATUS() {
		return HIV_FINAL_STATUS;
	}
	public void setHIV_FINAL_STATUS(String hIV_FINAL_STATUS) {
		HIV_FINAL_STATUS = hIV_FINAL_STATUS;
	}
	public String getTRANS_CALC_KEEP() {
		return TRANS_CALC_KEEP;
	}
	public void setTRANS_CALC_KEEP(String tRANS_CALC_KEEP) {
		TRANS_CALC_KEEP = tRANS_CALC_KEEP;
	}
	public int getCLIMS_RECID() {
		return CLIMS_RECID;
	}
	public void setCLIMS_RECID(int cLIMS_RECID) {
		CLIMS_RECID = cLIMS_RECID;
	}
	public String getPATIENTID() {
		return PATIENTID;
	}
	public void setPATIENTID(String pATIENTID) {
		PATIENTID = pATIENTID;
	}
	public String getMEMO() {
		return MEMO;
	}
	public void setMEMO(String mEMO) {
		MEMO = mEMO;
	}
	public String getFACILITY_PHONE() {
		return FACILITY_PHONE;
	}
	public void setFACILITY_PHONE(String fACILITY_PHONE) {
		FACILITY_PHONE = fACILITY_PHONE;
	}
	public String getFACILITY_EHARS_UID() {
		return FACILITY_EHARS_UID;
	}
	public void setFACILITY_EHARS_UID(String fACILITY_EHARS_UID) {
		FACILITY_EHARS_UID = fACILITY_EHARS_UID;
	}
	
	
	
}
