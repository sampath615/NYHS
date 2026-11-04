package gov.ny.state.doh.bhae.nyehms.web.spring.domain.document;

import java.sql.Date;

/**
 * This class is used to create POJO for PRF doc.
 *
 * @author vxp06
 */
public class StdOldDocVO extends NYEHMSDocVO {

	private int DOCUMENT_ID;
	private long STD_SRC_ID;
	private String STATENO;
	private String MED_RECORD_NUMBER;
	private String FNAME;
	private String LNAME;
	private String MNAME;
	private String SUFFIX;
	private String DOB_TEXT;
	private String SSN;
	private String DOD_TEXT;
	private String INSURANCE_NUMBER;
	private String INSURANCE_CD;
	private String DIN;
	private String PERSON_ADDRESS;
	private String PERSON_CITY;
	private String PERSON_STATE;
	private String PERSON_ZIP;
	private String PERSON_PLUS4;
	private String PERSON_COUNTY;
	private String PERSON_PHONE;
	private String PROV_ADDRESS;
	private String PROV_CITY;
	private String PROV_STATE;
	private String PROV_ZIP;
	private String PROV_PLUS4;
	private String PROV_COUNTY;
	private String PROV_PHONE;
	private String ETHNICITY;
	private String CURRENT_GENDER;
	private String BIRTH_COUNTRY;
	private String PROV_NAME;
	private String FACILITY_NAME;
	private String SWM;
	private String SWF;
	private String IDU;
	private String SEX_PARTNER_IDU;
	private String SEX_PARTNER_MSM;
	private Date RECEIVED_DATE;
	private int AGE;
	private boolean RACE_INDIAN;
	private boolean RACE_BLACK;
	private boolean RACE_ASIAN;
	private boolean RACE_PACIFIC;
	private boolean RACE_WHITE;
	private boolean RACE_MULTIPLE;
	private boolean RACE_OTHER;
	private boolean RACE_UNKNOWN;
	private Date EHARS_IMPORT_DATE;
	private String VITAL_STATUS;
	private String SEXUAL_PREFERENCE;
	private String SWA;
	private String SWT;
	private String SEX_WHILE_INTOX;
	private String EXCHANGE_SEX;
	private String SEX_WOUT_CONDOM;
	private String INCARCERATED;
	private String SHARED_DRUG_EQUIP;
	private String DRUG_USE;
	private String DISEASE_CD;
	private String DISEASE_DATE;
	private String STD_FILE;
	
	private String rawPersonAddress;
	private String rawProviderAddress;
	
	public int getDOCUMENT_ID() {
		return DOCUMENT_ID;
	}
	public void setDOCUMENT_ID(int dOCUMENT_ID) {
		DOCUMENT_ID = dOCUMENT_ID;
	}
	public long getSTD_SRC_ID() {
		return STD_SRC_ID;
	}
	public void setSTD_SRC_ID(long sTD_SRC_ID) {
		STD_SRC_ID = sTD_SRC_ID;
	}
	public String getSTATENO() {
		return STATENO;
	}
	public void setSTATENO(String sTATENO) {
		STATENO = sTATENO;
	}
	public String getMED_RECORD_NUMBER() {
		return MED_RECORD_NUMBER;
	}
	public void setMED_RECORD_NUMBER(String mED_RECORD_NUMBER) {
		MED_RECORD_NUMBER = mED_RECORD_NUMBER;
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
	public String getSSN() {
		return SSN;
	}
	public void setSSN(String sSN) {
		SSN = sSN;
	}
	public String getDOD_TEXT() {
		return DOD_TEXT;
	}
	public void setDOD_TEXT(String dOD_TEXT) {
		DOD_TEXT = dOD_TEXT;
	}
	public String getINSURANCE_NUMBER() {
		return INSURANCE_NUMBER;
	}
	public void setINSURANCE_NUMBER(String iNSURANCE_NUMBER) {
		INSURANCE_NUMBER = iNSURANCE_NUMBER;
	}
	public String getINSURANCE_CD() {
		return INSURANCE_CD;
	}
	public void setINSURANCE_CD(String iNSURANCE_CD) {
		INSURANCE_CD = iNSURANCE_CD;
	}
	public String getDIN() {
		return DIN;
	}
	public void setDIN(String dIN) {
		DIN = dIN;
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
	public String getPROV_COUNTY() {
		return PROV_COUNTY;
	}
	public void setPROV_COUNTY(String pROV_COUNTY) {
		PROV_COUNTY = pROV_COUNTY;
	}
	public String getPROV_PHONE() {
		return PROV_PHONE;
	}
	public void setPROV_PHONE(String pROV_PHONE) {
		PROV_PHONE = pROV_PHONE;
	}
	public String getETHNICITY() {
		return ETHNICITY;
	}
	public void setETHNICITY(String eTHNICITY) {
		ETHNICITY = eTHNICITY;
	}
	public String getCURRENT_GENDER() {
		return CURRENT_GENDER;
	}
	public void setCURRENT_GENDER(String cURRENT_GENDER) {
		CURRENT_GENDER = cURRENT_GENDER;
	}
	public String getBIRTH_COUNTRY() {
		return BIRTH_COUNTRY;
	}
	public void setBIRTH_COUNTRY(String bIRTH_COUNTRY) {
		BIRTH_COUNTRY = bIRTH_COUNTRY;
	}
	public String getPROV_NAME() {
		return PROV_NAME;
	}
	public void setPROV_NAME(String pROV_NAME) {
		PROV_NAME = pROV_NAME;
	}
	public String getFACILITY_NAME() {
		return FACILITY_NAME;
	}
	public void setFACILITY_NAME(String fACILITY_NAME) {
		FACILITY_NAME = fACILITY_NAME;
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
	public String getSEX_PARTNER_MSM() {
		return SEX_PARTNER_MSM;
	}
	public void setSEX_PARTNER_MSM(String sEX_PARTNER_MSM) {
		SEX_PARTNER_MSM = sEX_PARTNER_MSM;
	}
	public Date getRECEIVED_DATE() {
		return RECEIVED_DATE;
	}
	public void setRECEIVED_DATE(Date rECEIVED_DATE) {
		RECEIVED_DATE = rECEIVED_DATE;
	}
	public int getAGE() {
		return AGE;
	}
	public void setAGE(int aGE) {
		AGE = aGE;
	}
	public boolean isRACE_INDIAN() {
		return RACE_INDIAN;
	}
	public void setRACE_INDIAN(boolean rACE_INDIAN) {
		RACE_INDIAN = rACE_INDIAN;
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
	public boolean isRACE_PACIFIC() {
		return RACE_PACIFIC;
	}
	public void setRACE_PACIFIC(boolean rACE_PACIFIC) {
		RACE_PACIFIC = rACE_PACIFIC;
	}
	public boolean isRACE_WHITE() {
		return RACE_WHITE;
	}
	public void setRACE_WHITE(boolean rACE_WHITE) {
		RACE_WHITE = rACE_WHITE;
	}
	public boolean isRACE_MULTIPLE() {
		return RACE_MULTIPLE;
	}
	public void setRACE_MULTIPLE(boolean rACE_MULTIPLE) {
		RACE_MULTIPLE = rACE_MULTIPLE;
	}
	public boolean isRACE_OTHER() {
		return RACE_OTHER;
	}
	public void setRACE_OTHER(boolean rACE_OTHER) {
		RACE_OTHER = rACE_OTHER;
	}
	public boolean isRACE_UNKNOWN() {
		return RACE_UNKNOWN;
	}
	public void setRACE_UNKNOWN(boolean rACE_UNKNOWN) {
		RACE_UNKNOWN = rACE_UNKNOWN;
	}
	public Date getEHARS_IMPORT_DATE() {
		return EHARS_IMPORT_DATE;
	}
	public void setEHARS_IMPORT_DATE(Date eHARS_IMPORT_DATE) {
		EHARS_IMPORT_DATE = eHARS_IMPORT_DATE;
	}
	public String getVITAL_STATUS() {
		return VITAL_STATUS;
	}
	public void setVITAL_STATUS(String vITAL_STATUS) {
		VITAL_STATUS = vITAL_STATUS;
	}
	public String getSEXUAL_PREFERENCE() {
		return SEXUAL_PREFERENCE;
	}
	public void setSEXUAL_PREFERENCE(String sEXUAL_PREFERENCE) {
		SEXUAL_PREFERENCE = sEXUAL_PREFERENCE;
	}
	public String getSWA() {
		return SWA;
	}
	public void setSWA(String sWA) {
		SWA = sWA;
	}
	public String getSWT() {
		return SWT;
	}
	public void setSWT(String sWT) {
		SWT = sWT;
	}
	public String getSEX_WHILE_INTOX() {
		return SEX_WHILE_INTOX;
	}
	public void setSEX_WHILE_INTOX(String sEX_WHILE_INTOX) {
		SEX_WHILE_INTOX = sEX_WHILE_INTOX;
	}
	public String getEXCHANGE_SEX() {
		return EXCHANGE_SEX;
	}
	public void setEXCHANGE_SEX(String eXCHANGE_SEX) {
		EXCHANGE_SEX = eXCHANGE_SEX;
	}
	public String getSEX_WOUT_CONDOM() {
		return SEX_WOUT_CONDOM;
	}
	public void setSEX_WOUT_CONDOM(String sEX_WOUT_CONDOM) {
		SEX_WOUT_CONDOM = sEX_WOUT_CONDOM;
	}
	public String getINCARCERATED() {
		return INCARCERATED;
	}
	public void setINCARCERATED(String iNCARCERATED) {
		INCARCERATED = iNCARCERATED;
	}
	public String getSHARED_DRUG_EQUIP() {
		return SHARED_DRUG_EQUIP;
	}
	public void setSHARED_DRUG_EQUIP(String sHARED_DRUG_EQUIP) {
		SHARED_DRUG_EQUIP = sHARED_DRUG_EQUIP;
	}
	public String getDRUG_USE() {
		return DRUG_USE;
	}
	public void setDRUG_USE(String dRUG_USE) {
		DRUG_USE = dRUG_USE;
	}
	public String getDISEASE_CD() {
		return DISEASE_CD;
	}
	public void setDISEASE_CD(String dISEASE_CD) {
		DISEASE_CD = dISEASE_CD;
	}
	public String getDISEASE_DATE() {
		return DISEASE_DATE;
	}
	public void setDISEASE_DATE(String dISEASE_DATE) {
		DISEASE_DATE = dISEASE_DATE;
	}
	public String getSTD_FILE() {
		return STD_FILE;
	}
	public void setSTD_FILE(String sTD_FILE) {
		STD_FILE = sTD_FILE;
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
	
	private String DISCARD_STATUS;
	
	public String getDISCARD_STATUS() {
		return DISCARD_STATUS;
	}

	public void setDISCARD_STATUS(String dISCARD_STATUS) {
		DISCARD_STATUS = dISCARD_STATUS;;
	}
	
	
	
}
