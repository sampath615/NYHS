package gov.ny.state.doh.bhae.nyehms.web.spring.domain.document;

import java.sql.Date;


/**
 * This is pojo class for the NYC eHARS doc. This is used by mybatis to create map.
 *
 * @author ska03
 */
public class NYCEHARSDocVO extends NYEHMSDocVO{
	
	private Date CREATE_DT;
	private Date EHARS_IMPORT_DATE;
	private int DOCUMENT_ID;
	private String BIRTH_SEX;
	private String VITAL_STATUS;
	private String RACE_INDIAN;
	private String RACE_ASIAN;
	private String RACE_BLACK;
	private String RACE_PACIFIC;
	private String RACE_WHITE;
	private String SWM;
	private String SWF;
	private String IDU;
	private String SEX_PARTNER_IDU;
	private String RECV_BLOOD_PROD;
	private String SEX_PARTNER_MSM;
	private String RECV_CLOTTING_FACTOR;
	private String SEX_PARTNER_TRANSFUSION;
	private String SEX_PARTNER_TRANSPLANT;
	private String RECV_TRANSPLANT;
	private String HEALTHCARE_WORKER;
	private String EHARS_STATUS_CD;
	private String NYEHMS_STATENO;
	private String CITYNO;
	private String DOB_TEXT;
	private String PERSON_ADDRESS_DATE;
	private String DOD_TEXT;
	private String PERSON_STATE;
	private String ETHNICITY;
	private String NYC_EHARS_UID;
	private String FNAME;
	private String MNAME;
	private String PERSON_COUNTY;
	private String BIRTH_COUNTRY;
	private String CURRENT_GENDER;
	private String LNAME;
	private String PERSON_CITY;
	private String PERSON_ZIP;
	private String PERSON_ADDRESS;
	private String SSN;
	private String STATENO;
	private String CREATEID;
	private String rawAddress;
	private String HIV_CATEG; 
	private String AIDS_CATEG; 
	private String HIV_DX_DT; 
	private String AIDS_DX_DT;
	
	private int PARENT_DOCUMENT_ID;

	public int getPARENT_DOCUMENT_ID() {
		return PARENT_DOCUMENT_ID;
	}

	public void setPARENT_DOCUMENT_ID(int pARENT_DOCUMENT_ID) {
		PARENT_DOCUMENT_ID = pARENT_DOCUMENT_ID;
	}
	
	
	public Date getCREATE_DT() {
		return CREATE_DT;
	}
	public void setCREATE_DT(Date cREATE_DT) {
		CREATE_DT = cREATE_DT;
	}
	public Date getEHARS_IMPORT_DATE() {
		return EHARS_IMPORT_DATE;
	}
	public void setEHARS_IMPORT_DATE(Date eHARS_IMPORT_DATE) {
		EHARS_IMPORT_DATE = eHARS_IMPORT_DATE;
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
	public String getVITAL_STATUS() {
		return VITAL_STATUS;
	}
	public void setVITAL_STATUS(String vITAL_STATUS) {
		VITAL_STATUS = vITAL_STATUS;
	}
	public String getRACE_INDIAN() {
		return RACE_INDIAN;
	}
	public void setRACE_INDIAN(String rACE_INDIAN) {
		RACE_INDIAN = rACE_INDIAN;
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
	public String getRECV_BLOOD_PROD() {
		return RECV_BLOOD_PROD;
	}
	public void setRECV_BLOOD_PROD(String rECV_BLOOD_PROD) {
		RECV_BLOOD_PROD = rECV_BLOOD_PROD;
	}
	public String getSEX_PARTNER_MSM() {
		return SEX_PARTNER_MSM;
	}
	public void setSEX_PARTNER_MSM(String sEX_PARTNER_MSM) {
		SEX_PARTNER_MSM = sEX_PARTNER_MSM;
	}
	public String getRECV_CLOTTING_FACTOR() {
		return RECV_CLOTTING_FACTOR;
	}
	public void setRECV_CLOTTING_FACTOR(String rECV_CLOTTING_FACTOR) {
		RECV_CLOTTING_FACTOR = rECV_CLOTTING_FACTOR;
	}
	public String getSEX_PARTNER_TRANSFUSION() {
		return SEX_PARTNER_TRANSFUSION;
	}
	public void setSEX_PARTNER_TRANSFUSION(String sEX_PARTNER_TRANSFUSION) {
		SEX_PARTNER_TRANSFUSION = sEX_PARTNER_TRANSFUSION;
	}
	public String getSEX_PARTNER_TRANSPLANT() {
		return SEX_PARTNER_TRANSPLANT;
	}
	public void setSEX_PARTNER_TRANSPLANT(String sEX_PARTNER_TRANSPLANT) {
		SEX_PARTNER_TRANSPLANT = sEX_PARTNER_TRANSPLANT;
	}
	public String getRECV_TRANSPLANT() {
		return RECV_TRANSPLANT;
	}
	public void setRECV_TRANSPLANT(String rECV_TRANSPLANT) {
		RECV_TRANSPLANT = rECV_TRANSPLANT;
	}
	public String getHEALTHCARE_WORKER() {
		return HEALTHCARE_WORKER;
	}
	public void setHEALTHCARE_WORKER(String hEALTHCARE_WORKER) {
		HEALTHCARE_WORKER = hEALTHCARE_WORKER;
	}
	public String getEHARS_STATUS_CD() {
		return EHARS_STATUS_CD;
	}
	public void setEHARS_STATUS_CD(String eHARS_STATUS_CD) {
		EHARS_STATUS_CD = eHARS_STATUS_CD;
	}
	public String getNYEHMS_STATENO() {
		return NYEHMS_STATENO;
	}
	public void setNYEHMS_STATENO(String nYEHMS_STATENO) {
		NYEHMS_STATENO = nYEHMS_STATENO;
	}
	public String getCITYNO() {
		return CITYNO;
	}
	public void setCITYNO(String cITYNO) {
		CITYNO = cITYNO;
	}
	public String getDOB_TEXT() {
		return DOB_TEXT;
	}
	public void setDOB_TEXT(String dOB_TEXT) {
		DOB_TEXT = dOB_TEXT;
	}
	public String getPERSON_ADDRESS_DATE() {
		return PERSON_ADDRESS_DATE;
	}
	public void setPERSON_ADDRESS_DATE(String pERSON_ADDRESS_DATE) {
		PERSON_ADDRESS_DATE = pERSON_ADDRESS_DATE;
	}
	public String getDOD_TEXT() {
		return DOD_TEXT;
	}
	public void setDOD_TEXT(String dOD_TEXT) {
		DOD_TEXT = dOD_TEXT;
	}
	public String getPERSON_STATE() {
		return PERSON_STATE;
	}
	public void setPERSON_STATE(String pERSON_STATE) {
		PERSON_STATE = pERSON_STATE;
	}
	public String getETHNICITY() {
		return ETHNICITY;
	}
	public void setETHNICITY(String eTHNICITY) {
		ETHNICITY = eTHNICITY;
	}
	public String getNYC_EHARS_UID() {
		return NYC_EHARS_UID;
	}
	public void setNYC_EHARS_UID(String nYC_EHARS_UID) {
		NYC_EHARS_UID = nYC_EHARS_UID;
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
	public String getPERSON_COUNTY() {
		return PERSON_COUNTY;
	}
	public void setPERSON_COUNTY(String pERSON_COUNTY) {
		PERSON_COUNTY = pERSON_COUNTY;
	}
	public String getBIRTH_COUNTRY() {
		return BIRTH_COUNTRY;
	}
	public void setBIRTH_COUNTRY(String bIRTH_COUNTRY) {
		BIRTH_COUNTRY = bIRTH_COUNTRY;
	}
	public String getCURRENT_GENDER() {
		return CURRENT_GENDER;
	}
	public void setCURRENT_GENDER(String cURRENT_GENDER) {
		CURRENT_GENDER = cURRENT_GENDER;
	}
	public String getLNAME() {
		return LNAME;
	}
	public void setLNAME(String lNAME) {
		LNAME = lNAME;
	}
	public String getPERSON_CITY() {
		return PERSON_CITY;
	}
	public void setPERSON_CITY(String pERSON_CITY) {
		PERSON_CITY = pERSON_CITY;
	}
	public String getPERSON_ZIP() {
		return PERSON_ZIP;
	}
	public void setPERSON_ZIP(String pERSON_ZIP) {
		PERSON_ZIP = pERSON_ZIP;
	}
	public String getPERSON_ADDRESS() {
		return PERSON_ADDRESS;
	}
	public void setPERSON_ADDRESS(String pERSON_ADDRESS) {
		PERSON_ADDRESS = pERSON_ADDRESS;
	}
	public String getSSN() {
		return SSN;
	}
	public void setSSN(String sSN) {
		SSN = sSN;
	}
	public String getSTATENO() {
		return STATENO;
	}
	public void setSTATENO(String sTATENO) {
		STATENO = sTATENO;
	}
	public String getCREATEID() {
		return CREATEID;
	}
	public void setCREATEID(String cREATEID) {
		CREATEID = cREATEID;
	}
	public String getRawAddress() {
		return rawAddress;
	}
	public void setRawAddress(String rawAddress) {
		this.rawAddress = rawAddress;
	}
	
	private String DISCARD_STATUS;
	
	public String getDISCARD_STATUS() {
		return DISCARD_STATUS;
	}

	public void setDISCARD_STATUS(String dISCARD_STATUS) {
		DISCARD_STATUS = dISCARD_STATUS;;
	}

	public String getHIV_CATEG() {
		return HIV_CATEG;
	}

	public void setHIV_CATEG(String hIV_CATEG) {
		HIV_CATEG = hIV_CATEG;
	}

	public String getAIDS_CATEG() {
		return AIDS_CATEG;
	}

	public void setAIDS_CATEG(String aIDS_CATEG) {
		AIDS_CATEG = aIDS_CATEG;
	}

	public String getHIV_DX_DT() {
		return HIV_DX_DT;
	}

	public void setHIV_DX_DT(String hIV_DX_DT) {
		HIV_DX_DT = hIV_DX_DT;
	}

	public String getAIDS_DX_DT() {
		return AIDS_DX_DT;
	}

	public void setAIDS_DX_DT(String aIDS_DX_DT) {
		AIDS_DX_DT = aIDS_DX_DT;
	}
	
	
}
