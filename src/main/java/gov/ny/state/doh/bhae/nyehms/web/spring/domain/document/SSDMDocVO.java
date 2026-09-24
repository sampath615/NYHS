package gov.ny.state.doh.bhae.nyehms.web.spring.domain.document;

import java.sql.Date;

/**
 * This is POJO for SSDM document
 *
 * @author vxp06
 */
public class SSDMDocVO extends NYEHMSDocVO{

	private Date EHARS_IMPORT_DATE;
	private Integer AGE_AT_DEATH;
	private int DOCUMENT_ID;
	private Long SSDM_SRC_ID;
	private String EHARS_STATUS_CD;
	private String DOB_TEXT;
	private String DOD_TEXT;
	private String STATENO;
	private String PERSON_STATE_AT_DEATH;
	private String FNAME;
	private String MNAME;
	private String PERSON_COUNTY_AT_DEATH;
	private String SUFFIX;
	private String LNAME;
	private String ORIG_LNAME;
	private String BIRTH_YEAR;
	private String SSDM_FILE;
	private String PERSON_ZIP_AT_DEATH;
	private String SSN;
	
	private String DOB;
	private String stateOfDeath;
	private String dateOfDeath;
	
	private int PARENT_DOCUMENT_ID;

	public int getPARENT_DOCUMENT_ID() {
		return PARENT_DOCUMENT_ID;
	}

	public void setPARENT_DOCUMENT_ID(int pARENT_DOCUMENT_ID) {
		PARENT_DOCUMENT_ID = pARENT_DOCUMENT_ID;
	}
	
	public Date getEHARS_IMPORT_DATE() {
		return EHARS_IMPORT_DATE;
	}
	public void setEHARS_IMPORT_DATE(Date eHARS_IMPORT_DATE) {
		EHARS_IMPORT_DATE = eHARS_IMPORT_DATE;
	}
	public Integer getAGE_AT_DEATH() {
		return AGE_AT_DEATH;
	}
	public void setAGE_AT_DEATH(Integer aGE_AT_DEATH) {
		AGE_AT_DEATH = aGE_AT_DEATH;
	}
	public int getDOCUMENT_ID() {
		return DOCUMENT_ID;
	}
	public void setDOCUMENT_ID(int dOCUMENT_ID) {
		DOCUMENT_ID = dOCUMENT_ID;
	}
	public Long getSSDM_SRC_ID() {
		return SSDM_SRC_ID;
	}
	public void setSSDM_SRC_ID(Long sSDM_SRC_ID) {
		SSDM_SRC_ID = sSDM_SRC_ID;
	}
	public String getEHARS_STATUS_CD() {
		return EHARS_STATUS_CD;
	}
	public void setEHARS_STATUS_CD(String eHARS_STATUS_CD) {
		EHARS_STATUS_CD = eHARS_STATUS_CD;
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
	public String getSTATENO() {
		return STATENO;
	}
	public void setSTATENO(String sTATENO) {
		STATENO = sTATENO;
	}
	public String getPERSON_STATE_AT_DEATH() {
		return PERSON_STATE_AT_DEATH;
	}
	public void setPERSON_STATE_AT_DEATH(String pERSON_STATE_AT_DEATH) {
		PERSON_STATE_AT_DEATH = pERSON_STATE_AT_DEATH;
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
	public String getPERSON_COUNTY_AT_DEATH() {
		return PERSON_COUNTY_AT_DEATH;
	}
	public void setPERSON_COUNTY_AT_DEATH(String pERSON_COUNTY_AT_DEATH) {
		PERSON_COUNTY_AT_DEATH = pERSON_COUNTY_AT_DEATH;
	}
	public String getSUFFIX() {
		return SUFFIX;
	}
	public void setSUFFIX(String sUFFIX) {
		SUFFIX = sUFFIX;
	}
	public String getLNAME() {
		return LNAME;
	}
	public void setLNAME(String lNAME) {
		LNAME = lNAME;
	}
	public String getORIG_LNAME() {
		return ORIG_LNAME;
	}
	public void setORIG_LNAME(String oRIG_LNAME) {
		ORIG_LNAME = oRIG_LNAME;
	}
	public String getBIRTH_YEAR() {
		return BIRTH_YEAR;
	}
	public void setBIRTH_YEAR(String bIRTH_YEAR) {
		BIRTH_YEAR = bIRTH_YEAR;
	}
	public String getSSDM_FILE() {
		return SSDM_FILE;
	}
	public void setSSDM_FILE(String sSDM_FILE) {
		SSDM_FILE = sSDM_FILE;
	}
	public String getPERSON_ZIP_AT_DEATH() {
		return PERSON_ZIP_AT_DEATH;
	}
	public void setPERSON_ZIP_AT_DEATH(String pERSON_ZIP_AT_DEATH) {
		PERSON_ZIP_AT_DEATH = pERSON_ZIP_AT_DEATH;
	}
	public String getSSN() {
		return SSN;
	}
	public void setSSN(String sSN) {
		SSN = sSN;
	}
	public String getDOB() {
		return DOB;
	}
	public void setDOB(String dOB) {
		DOB = dOB;
	}
	public String getStateOfDeath() {
		return stateOfDeath;
	}
	public void setStateOfDeath(String stateOfDeath) {
		this.stateOfDeath = stateOfDeath;
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
	
	
}
