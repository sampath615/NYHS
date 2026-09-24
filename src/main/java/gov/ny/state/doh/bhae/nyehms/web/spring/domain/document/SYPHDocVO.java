package gov.ny.state.doh.bhae.nyehms.web.spring.domain.document;

import java.sql.Date;

/**
 * This class is POJO for SYPH document.
 *
 * @author vxp06
 */
public class SYPHDocVO extends NYEHMSDocVO {
	
	private String DOCUMENT_ID;
	private String STATENO;
	private Date EHARS_IMPORT_DATE;
	private String REVIEW_DATE;	
	private String RECEIVED_DATE;
	private String REVIEW_UID;
	private String MATCH_PASS;
	private String MATCH_CD;
	private String FNAME;
	private String MNAME;
	private String LNAME;
	private String DOB;
	private String DOD;
	private String PERSON_CITY;
	private String PERSON_STATE;
	private String PERSON_ZIP;
	private String PERSON_COUNTY;
	private String PERSON_ADDRESS;
	private String STREET;
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
	private String CASEKEY;
	private String SYPHILIS_DIAGNOSIS_DATE;
	private String DISEASE_CODE;
	private String PROV_FNAME;
	private String PROV_LNAME;
	private String PROV_ADDRESS;
	private String PROV_CITY;
	private String PROV_STATE;
	private String PROV_ZIP;
	private String PROV_PHONE;	
	private String PROV_COUNTY;
	private String DOCUMENT_SOURCE_TABLE;
	private String PROVIDERADDRESS;
	
	private String dateOfDeath;
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
			
	public String getREVIEW_DATE() {
		return REVIEW_DATE;
	}
	public void setREVIEW_DATE(String rEVIEW_DATE) {
		REVIEW_DATE = rEVIEW_DATE;
	}
	
	public String getREVIEW_UID() {
		return REVIEW_UID;
	}
	public void setREVIEW_UID(String rEVIEW_UID) {
		REVIEW_UID = rEVIEW_UID;
	}
	
	public String getRECEIVED_DATE() {
		return RECEIVED_DATE;
	}
	public void setRECEIVED_DATE(String rECEIVED_DATE) {
		RECEIVED_DATE = rECEIVED_DATE;
	}
				
	public String getMATCH_PASS() {
		return MATCH_PASS;
	}
	public void setMATCH_PASS(String mATCH_PASS) {
		MATCH_PASS = mATCH_PASS;
	}
	
	public String getMATCH_CD() {
		return MATCH_CD;
	}
	public void setMATCH_CD(String mATCH_CD) {
		MATCH_CD = mATCH_CD;
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
		
	public String getDOD() {
		return DOD;
	}
	public void setDOD(String dOD) {
		DOD = dOD;
	}
	
	public String getSYPHILIS_DIAGNOSIS_DATE() {
		return SYPHILIS_DIAGNOSIS_DATE;
	}
	public void setSYPHILIS_DIAGNOSIS_DATE(String sYPHILIS_DIAGNOSIS_DATE) {
		SYPHILIS_DIAGNOSIS_DATE = sYPHILIS_DIAGNOSIS_DATE;
	}
	
	public String getDISEASE_CODE() {
		return DISEASE_CODE;
	}
	public void setDISEASE_CODE(String dISEASE_CODE) {
		DISEASE_CODE = dISEASE_CODE;
	}
	
	public String getPERSON_ADDRESS() {
		return PERSON_ADDRESS;
	}
	public void setPERSON_ADDRESS(String pERSON_ADDRESS) {
		PERSON_ADDRESS = pERSON_ADDRESS;
	}
	
	public String getSTREET() {
		return STREET;
	}
	public void setSTREET(String sTREET) {
		STREET = sTREET;
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

	public String getCASEKEY() {
		return CASEKEY;
	}
	public void setCASEKEY(String cASEKEY) {
		CASEKEY = cASEKEY;
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

	public String getPROV_COUNTY() {
		return PROV_COUNTY;
	}
	public void setPROV_COUNTY(String pROV_COUNTY) {
		PROV_COUNTY = pROV_COUNTY;
	}
		
	public String getDOCUMENT_SOURCE_TABLE() {
		return DOCUMENT_SOURCE_TABLE;
	}
	public void setDOCUMENT_SOURCE_TABLE(String dOCUMENT_SOURCE_TABLE) {
		DOCUMENT_SOURCE_TABLE = dOCUMENT_SOURCE_TABLE;
	}
	
	public String getPROVIDERADDRESS() {
		return PROVIDERADDRESS;
	}		
	public void setPROVIDERADDRESS(String pROVIDERADDRESS) {
		PROVIDERADDRESS = pROVIDERADDRESS;
	}
	
    public String getPROV_PHONE() {
		return PROV_PHONE;
	}
	public void setPROV_PHONE(String pROV_PHONE) {
		PROV_PHONE = pROV_PHONE;
	}
			
	public String getDateOfDeath() {
		return dateOfDeath;
	}
	public void setDateOfDeath(String dateOfDeath) {
		this.dateOfDeath = dateOfDeath;
	}

}
