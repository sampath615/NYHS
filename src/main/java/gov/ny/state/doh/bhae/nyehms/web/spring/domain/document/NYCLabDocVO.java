package gov.ny.state.doh.bhae.nyehms.web.spring.domain.document;

import java.sql.Date;


/**
 * This is pojo class for the NYC eHARS doc. This is used by mybatis to create map.
 *
 * @author ska03
 */
public class NYCLabDocVO extends NYEHMSDocVO{
	
	private Date CREATE_DATE;
	private Date EHARS_IMPORT_DATE;
	private int DOCUMENT_ID;
	private String BIRTH_SEX;
	private String VITAL_STATUS;
	private String SWM;
	private String SWF;
	private String IDU;
	private String SEX_PARTNER_IDU;
	private String SEX_PARTNER_MSM;
	private String SEX_PARTNER_HEMOPHILIAC;
	private String SEX_PARTNER_TRANSFUSION;
	private String SEX_PARTNER_TRANSPLANT;
	private String SEX_PARTNER_HIV;
	private String RECV_BLOOD_PROD;
	private String FIRST_TRANSFUSION_DATE_TEXT;
	private String LAST_TRANSFUSION_DATE_TEXT;
	private String RECV_TRANSPLANT;
	private String HEALTHCARE_WORKER;
	private String OCCUPATION;
	private String RISK_OTHER;
	private String INTERPRETATION;
	private String DOCTOR_DIAGNOSIS;
	private String EHARS_STATUS_CD;
	private String NYEHMS_STATENO;
	private String EHARS_STATENO;
	private String CITYNO;
	private String RECEIVED_DATE;
	private String NYSID;
	private String DOB_TEXT;
	private String DOD_TEXT;
	private String ADDRESS_DATE_TEXT;
	private String PERSON_ZIP;
	private String XLAB_HIV_DATE_TEXT;
	private String COLLECTION_DATE_TEXT;
	private String DOCTOR_DIAGNOSIS_DATE_TEXT;
	private String CANDLUNG_DATE_TEXT;
	private String CANDE_DATE_TEXT;
	private String CERVCANC_DATE_TEXT;
	private String COCCI_DATE_TEXT;
	private String CRYPTOCO_DATE_TEXT;
	private String CRYPTOSP_DATE_TEXT;
	private String CMV_DATE_TEXT;
	private String CMVRET_DATE_TEXT;
	private String HIVNCEPH_DATE_TEXT;
	private String HERPES_DATE_TEXT;
	private String HISTO_DATE_TEXT;
	private String ISO_DATE_TEXT;
	private String KS_DATE_TEXT;
	private String BURKL_DATE_TEXT;
	private String LYMPHIM_DATE_TEXT;
	private String LYMPHB_DATE_TEXT;
	private String MYCOAV_DATE_TEXT;
	private String MYCOPULM_DATE_TEXT;
	private String MYCODISM_DATE_TEXT;
	private String MYCOTH_DATE_TEXT;
	private String PCP_DATE_TEXT;
	private String PNEUM_DATE_TEXT;
	private String PML_DATE_TEXT;
	private String SALMNELA_DATE_TEXT;
	private String TOXO_DATE_TEXT;
	private String WASTING_DATE_TEXT;
	private String LAB_RESULT;
	private String SSN;
	private String DEATH_STATE;
	private String CURRENT_GENDER;
	private String PERSON_STATE;
	private String EVENT_CODE;
	private String CURRENT_PROCESS_STATUS;
	private String EHARS_UID;
	private String FACILITY_UID;
	private String BIRTH_COUNTRY;
	private String ETHNICITY;
	private String RACE_WHITE;
	private String RACE_BLACK;
	private String RACE_ASIAN;
	private String RACE_INDIAN;
	private String RACE_PACIFIC;
	private String RACE_UNKNOWN;
	private String ADDRESS_TYPE_CODE;
	private String PERSON_COUNTRY;
	private String MATCH_CODE;
	private String FIRST_NAME;
	private String MIDDLE_NAME;
	private String LAST_NAME;
	private String LAB_UNITS;
	private String CREATE_UID;
	private String PERSON_ADDRESS1;
	private String LAB_TEST_NAME;
	private String PERSON_COUNTY;
	private String PERSON_CITY;
	private String rawAddress;
	private String STATENO;
	
	private String FACILITY_STATE;

	private int PARENT_DOCUMENT_ID;

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

	public String getSEX_PARTNER_HEMOPHILIAC() {
		return SEX_PARTNER_HEMOPHILIAC;
	}

	public void setSEX_PARTNER_HEMOPHILIAC(String sEX_PARTNER_HEMOPHILIAC) {
		SEX_PARTNER_HEMOPHILIAC = sEX_PARTNER_HEMOPHILIAC;
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

	public String getSEX_PARTNER_HIV() {
		return SEX_PARTNER_HIV;
	}

	public void setSEX_PARTNER_HIV(String sEX_PARTNER_HIV) {
		SEX_PARTNER_HIV = sEX_PARTNER_HIV;
	}

	public String getRECV_BLOOD_PROD() {
		return RECV_BLOOD_PROD;
	}

	public void setRECV_BLOOD_PROD(String rECV_BLOOD_PROD) {
		RECV_BLOOD_PROD = rECV_BLOOD_PROD;
	}

	public String getFIRST_TRANSFUSION_DATE_TEXT() {
		return FIRST_TRANSFUSION_DATE_TEXT;
	}

	public void setFIRST_TRANSFUSION_DATE_TEXT(String fIRST_TRANSFUSION_DATE_TEXT) {
		FIRST_TRANSFUSION_DATE_TEXT = fIRST_TRANSFUSION_DATE_TEXT;
	}

	public String getLAST_TRANSFUSION_DATE_TEXT() {
		return LAST_TRANSFUSION_DATE_TEXT;
	}

	public void setLAST_TRANSFUSION_DATE_TEXT(String lAST_TRANSFUSION_DATE_TEXT) {
		LAST_TRANSFUSION_DATE_TEXT = lAST_TRANSFUSION_DATE_TEXT;
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

	

	public String getOCCUPATION() {
		return OCCUPATION;
	}

	public void setOCCUPATION(String oCCUPATION) {
		OCCUPATION = oCCUPATION;
	}

	public String getRISK_OTHER() {
		return RISK_OTHER;
	}

	public void setRISK_OTHER(String rISK_OTHER) {
		RISK_OTHER = rISK_OTHER;
	}

	public String getINTERPRETATION() {
		return INTERPRETATION;
	}

	public void setINTERPRETATION(String iNTERPRETATION) {
		INTERPRETATION = iNTERPRETATION;
	}

	public String getDOCTOR_DIAGNOSIS() {
		return DOCTOR_DIAGNOSIS;
	}

	public void setDOCTOR_DIAGNOSIS(String dOCTOR_DIAGNOSIS) {
		DOCTOR_DIAGNOSIS = dOCTOR_DIAGNOSIS;
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

	public String getEHARS_STATENO() {
		return EHARS_STATENO;
	}

	public void setEHARS_STATENO(String eHARS_STATENO) {
		EHARS_STATENO = eHARS_STATENO;
	}

	public String getCITYNO() {
		return CITYNO;
	}

	public void setCITYNO(String cITYNO) {
		CITYNO = cITYNO;
	}

	public String getRECEIVED_DATE() {
		return RECEIVED_DATE;
	}

	public void setRECEIVED_DATE(String rECEIVED_DATE) {
		RECEIVED_DATE = rECEIVED_DATE;
	}

	public String getNYSID() {
		return NYSID;
	}

	public void setNYSID(String nYSID) {
		NYSID = nYSID;
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

	public String getADDRESS_DATE_TEXT() {
		return ADDRESS_DATE_TEXT;
	}

	public void setADDRESS_DATE_TEXT(String aDDRESS_DATE_TEXT) {
		ADDRESS_DATE_TEXT = aDDRESS_DATE_TEXT;
	}

	public String getPERSON_ZIP() {
		return PERSON_ZIP;
	}

	public void setPERSON_ZIP(String pERSON_ZIP) {
		PERSON_ZIP = pERSON_ZIP;
	}

	public String getXLAB_HIV_DATE_TEXT() {
		return XLAB_HIV_DATE_TEXT;
	}

	public void setXLAB_HIV_DATE_TEXT(String xLAB_HIV_DATE_TEXT) {
		XLAB_HIV_DATE_TEXT = xLAB_HIV_DATE_TEXT;
	}

	public String getCOLLECTION_DATE_TEXT() {
		return COLLECTION_DATE_TEXT;
	}

	public void setCOLLECTION_DATE_TEXT(String cOLLECTION_DATE_TEXT) {
		COLLECTION_DATE_TEXT = cOLLECTION_DATE_TEXT;
	}

	public String getDOCTOR_DIAGNOSIS_DATE_TEXT() {
		return DOCTOR_DIAGNOSIS_DATE_TEXT;
	}

	public void setDOCTOR_DIAGNOSIS_DATE_TEXT(String dOCTOR_DIAGNOSIS_DATE_TEXT) {
		DOCTOR_DIAGNOSIS_DATE_TEXT = dOCTOR_DIAGNOSIS_DATE_TEXT;
	}

	public String getCANDLUNG_DATE_TEXT() {
		return CANDLUNG_DATE_TEXT;
	}

	public void setCANDLUNG_DATE_TEXT(String cANDLUNG_DATE_TEXT) {
		CANDLUNG_DATE_TEXT = cANDLUNG_DATE_TEXT;
	}

	public String getCANDE_DATE_TEXT() {
		return CANDE_DATE_TEXT;
	}

	public void setCANDE_DATE_TEXT(String cANDE_DATE_TEXT) {
		CANDE_DATE_TEXT = cANDE_DATE_TEXT;
	}

	public String getCERVCANC_DATE_TEXT() {
		return CERVCANC_DATE_TEXT;
	}

	public void setCERVCANC_DATE_TEXT(String cERVCANC_DATE_TEXT) {
		CERVCANC_DATE_TEXT = cERVCANC_DATE_TEXT;
	}

	public String getCOCCI_DATE_TEXT() {
		return COCCI_DATE_TEXT;
	}

	public void setCOCCI_DATE_TEXT(String cOCCI_DATE_TEXT) {
		COCCI_DATE_TEXT = cOCCI_DATE_TEXT;
	}

	public String getCRYPTOCO_DATE_TEXT() {
		return CRYPTOCO_DATE_TEXT;
	}

	public void setCRYPTOCO_DATE_TEXT(String cRYPTOCO_DATE_TEXT) {
		CRYPTOCO_DATE_TEXT = cRYPTOCO_DATE_TEXT;
	}

	public String getCRYPTOSP_DATE_TEXT() {
		return CRYPTOSP_DATE_TEXT;
	}

	public void setCRYPTOSP_DATE_TEXT(String cRYPTOSP_DATE_TEXT) {
		CRYPTOSP_DATE_TEXT = cRYPTOSP_DATE_TEXT;
	}

	public String getCMV_DATE_TEXT() {
		return CMV_DATE_TEXT;
	}

	public void setCMV_DATE_TEXT(String cMV_DATE_TEXT) {
		CMV_DATE_TEXT = cMV_DATE_TEXT;
	}

	public String getCMVRET_DATE_TEXT() {
		return CMVRET_DATE_TEXT;
	}

	public void setCMVRET_DATE_TEXT(String cMVRET_DATE_TEXT) {
		CMVRET_DATE_TEXT = cMVRET_DATE_TEXT;
	}

	public String getHIVNCEPH_DATE_TEXT() {
		return HIVNCEPH_DATE_TEXT;
	}

	public void setHIVNCEPH_DATE_TEXT(String hIVNCEPH_DATE_TEXT) {
		HIVNCEPH_DATE_TEXT = hIVNCEPH_DATE_TEXT;
	}

	public String getHERPES_DATE_TEXT() {
		return HERPES_DATE_TEXT;
	}

	public void setHERPES_DATE_TEXT(String hERPES_DATE_TEXT) {
		HERPES_DATE_TEXT = hERPES_DATE_TEXT;
	}

	public String getHISTO_DATE_TEXT() {
		return HISTO_DATE_TEXT;
	}

	public void setHISTO_DATE_TEXT(String hISTO_DATE_TEXT) {
		HISTO_DATE_TEXT = hISTO_DATE_TEXT;
	}

	public String getISO_DATE_TEXT() {
		return ISO_DATE_TEXT;
	}

	public void setISO_DATE_TEXT(String iSO_DATE_TEXT) {
		ISO_DATE_TEXT = iSO_DATE_TEXT;
	}

	public String getKS_DATE_TEXT() {
		return KS_DATE_TEXT;
	}

	public void setKS_DATE_TEXT(String kS_DATE_TEXT) {
		KS_DATE_TEXT = kS_DATE_TEXT;
	}

	public String getBURKL_DATE_TEXT() {
		return BURKL_DATE_TEXT;
	}

	public void setBURKL_DATE_TEXT(String bURKL_DATE_TEXT) {
		BURKL_DATE_TEXT = bURKL_DATE_TEXT;
	}

	public String getLYMPHIM_DATE_TEXT() {
		return LYMPHIM_DATE_TEXT;
	}

	public void setLYMPHIM_DATE_TEXT(String lYMPHIM_DATE_TEXT) {
		LYMPHIM_DATE_TEXT = lYMPHIM_DATE_TEXT;
	}

	public String getLYMPHB_DATE_TEXT() {
		return LYMPHB_DATE_TEXT;
	}

	public void setLYMPHB_DATE_TEXT(String lYMPHB_DATE_TEXT) {
		LYMPHB_DATE_TEXT = lYMPHB_DATE_TEXT;
	}

	public String getMYCOAV_DATE_TEXT() {
		return MYCOAV_DATE_TEXT;
	}

	public void setMYCOAV_DATE_TEXT(String mYCOAV_DATE_TEXT) {
		MYCOAV_DATE_TEXT = mYCOAV_DATE_TEXT;
	}

	public String getMYCOPULM_DATE_TEXT() {
		return MYCOPULM_DATE_TEXT;
	}

	public void setMYCOPULM_DATE_TEXT(String mYCOPULM_DATE_TEXT) {
		MYCOPULM_DATE_TEXT = mYCOPULM_DATE_TEXT;
	}

	public String getMYCODISM_DATE_TEXT() {
		return MYCODISM_DATE_TEXT;
	}

	public void setMYCODISM_DATE_TEXT(String mYCODISM_DATE_TEXT) {
		MYCODISM_DATE_TEXT = mYCODISM_DATE_TEXT;
	}

	public String getMYCOTH_DATE_TEXT() {
		return MYCOTH_DATE_TEXT;
	}

	public void setMYCOTH_DATE_TEXT(String mYCOTH_DATE_TEXT) {
		MYCOTH_DATE_TEXT = mYCOTH_DATE_TEXT;
	}

	public String getPCP_DATE_TEXT() {
		return PCP_DATE_TEXT;
	}

	public void setPCP_DATE_TEXT(String pCP_DATE_TEXT) {
		PCP_DATE_TEXT = pCP_DATE_TEXT;
	}

	public String getPNEUM_DATE_TEXT() {
		return PNEUM_DATE_TEXT;
	}

	public void setPNEUM_DATE_TEXT(String pNEUM_DATE_TEXT) {
		PNEUM_DATE_TEXT = pNEUM_DATE_TEXT;
	}

	public String getPML_DATE_TEXT() {
		return PML_DATE_TEXT;
	}

	public void setPML_DATE_TEXT(String pML_DATE_TEXT) {
		PML_DATE_TEXT = pML_DATE_TEXT;
	}

	public String getSALMNELA_DATE_TEXT() {
		return SALMNELA_DATE_TEXT;
	}

	public void setSALMNELA_DATE_TEXT(String sALMNELA_DATE_TEXT) {
		SALMNELA_DATE_TEXT = sALMNELA_DATE_TEXT;
	}

	public String getTOXO_DATE_TEXT() {
		return TOXO_DATE_TEXT;
	}

	public void setTOXO_DATE_TEXT(String tOXO_DATE_TEXT) {
		TOXO_DATE_TEXT = tOXO_DATE_TEXT;
	}

	public String getWASTING_DATE_TEXT() {
		return WASTING_DATE_TEXT;
	}

	public void setWASTING_DATE_TEXT(String wASTING_DATE_TEXT) {
		WASTING_DATE_TEXT = wASTING_DATE_TEXT;
	}

	public String getLAB_RESULT() {
		return LAB_RESULT;
	}

	public void setLAB_RESULT(String lAB_RESULT) {
		LAB_RESULT = lAB_RESULT;
	}

	public String getSSN() {
		return SSN;
	}

	public void setSSN(String sSN) {
		SSN = sSN;
	}

	public String getDEATH_STATE() {
		return DEATH_STATE;
	}

	public void setDEATH_STATE(String dEATH_STATE) {
		DEATH_STATE = dEATH_STATE;
	}

	public String getCURRENT_GENDER() {
		return CURRENT_GENDER;
	}

	public void setCURRENT_GENDER(String cURRENT_GENDER) {
		CURRENT_GENDER = cURRENT_GENDER;
	}

	public String getPERSON_STATE() {
		return PERSON_STATE;
	}

	public void setPERSON_STATE(String pERSON_STATE) {
		PERSON_STATE = pERSON_STATE;
	}

	public String getEVENT_CODE() {
		return EVENT_CODE;
	}

	public void setEVENT_CODE(String eVENT_CODE) {
		EVENT_CODE = eVENT_CODE;
	}

	public String getCURRENT_PROCESS_STATUS() {
		return CURRENT_PROCESS_STATUS;
	}

	public void setCURRENT_PROCESS_STATUS(String cURRENT_PROCESS_STATUS) {
		CURRENT_PROCESS_STATUS = cURRENT_PROCESS_STATUS;
	}

	public String getEHARS_UID() {
		return EHARS_UID;
	}

	public void setEHARS_UID(String eHARS_UID) {
		EHARS_UID = eHARS_UID;
	}

	public String getFACILITY_UID() {
		return FACILITY_UID;
	}

	public void setFACILITY_UID(String fACILITY_UID) {
		FACILITY_UID = fACILITY_UID;
	}

	public String getBIRTH_COUNTRY() {
		return BIRTH_COUNTRY;
	}

	public void setBIRTH_COUNTRY(String bIRTH_COUNTRY) {
		BIRTH_COUNTRY = bIRTH_COUNTRY;
	}

	public String getETHNICITY() {
		return ETHNICITY;
	}

	public void setETHNICITY(String eTHNICITY) {
		ETHNICITY = eTHNICITY;
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

	public String getRACE_UNKNOWN() {
		return RACE_UNKNOWN;
	}

	public void setRACE_UNKNOWN(String rACE_UNKNOWN) {
		RACE_UNKNOWN = rACE_UNKNOWN;
	}

	public String getADDRESS_TYPE_CODE() {
		return ADDRESS_TYPE_CODE;
	}

	public void setADDRESS_TYPE_CODE(String aDDRESS_TYPE_CODE) {
		ADDRESS_TYPE_CODE = aDDRESS_TYPE_CODE;
	}

	public String getPERSON_COUNTRY() {
		return PERSON_COUNTRY;
	}

	public void setPERSON_COUNTRY(String pERSON_COUNTRY) {
		PERSON_COUNTRY = pERSON_COUNTRY;
	}

	public String getMATCH_CODE() {
		return MATCH_CODE;
	}

	public void setMATCH_CODE(String mATCH_CODE) {
		MATCH_CODE = mATCH_CODE;
	}

	public String getFIRST_NAME() {
		return FIRST_NAME;
	}

	public void setFIRST_NAME(String fIRST_NAME) {
		FIRST_NAME = fIRST_NAME;
	}

	public String getMIDDLE_NAME() {
		return MIDDLE_NAME;
	}

	public void setMIDDLE_NAME(String mIDDLE_NAME) {
		MIDDLE_NAME = mIDDLE_NAME;
	}

	public String getLAST_NAME() {
		return LAST_NAME;
	}

	public void setLAST_NAME(String lAST_NAME) {
		LAST_NAME = lAST_NAME;
	}

	public String getLAB_UNITS() {
		return LAB_UNITS;
	}

	public void setLAB_UNITS(String lAB_UNITS) {
		LAB_UNITS = lAB_UNITS;
	}

	public String getCREATE_UID() {
		return CREATE_UID;
	}

	public void setCREATE_UID(String cREATE_UID) {
		CREATE_UID = cREATE_UID;
	}

	public String getPERSON_ADDRESS1() {
		return PERSON_ADDRESS1;
	}

	public void setPERSON_ADDRESS1(String pERSON_ADDRESS1) {
		PERSON_ADDRESS1 = pERSON_ADDRESS1;
	}

	public String getLAB_TEST_NAME() {
		return LAB_TEST_NAME;
	}

	public void setLAB_TEST_NAME(String lAB_TEST_NAME) {
		LAB_TEST_NAME = lAB_TEST_NAME;
	}

	public String getPERSON_COUNTY() {
		return PERSON_COUNTY;
	}

	public void setPERSON_COUNTY(String pERSON_COUNTY) {
		PERSON_COUNTY = pERSON_COUNTY;
	}

	public String getPERSON_CITY() {
		return PERSON_CITY;
	}

	public void setPERSON_CITY(String pERSON_CITY) {
		PERSON_CITY = pERSON_CITY;
	}

	public String getRawAddress() {
		return rawAddress;
	}

	public void setRawAddress(String rawAddress) {
		this.rawAddress = rawAddress;
	}

	public String getSTATENO() {
		return STATENO;
	}

	public void setSTATENO(String sTATENO) {
		STATENO = sTATENO;
	}

	public String getFACILITY_STATE() {
		return FACILITY_STATE;
	}

	public void setFACILITY_STATE(String fACILITY_STATE) {
		FACILITY_STATE = fACILITY_STATE;
	}
	
	private String DISCARD_STATUS;
	
	public String getDISCARD_STATUS() {
		return DISCARD_STATUS;
	}

	public void setDISCARD_STATUS(String dISCARD_STATUS) {
		DISCARD_STATUS = dISCARD_STATUS;;
	}
	
	
}
