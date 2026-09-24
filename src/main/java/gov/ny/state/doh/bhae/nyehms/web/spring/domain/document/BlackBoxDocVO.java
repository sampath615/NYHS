package gov.ny.state.doh.bhae.nyehms.web.spring.domain.document;

import java.sql.Date;


/**
 * This is POJO class for Black box document.
 *
 * @author ska03
 */
public class BlackBoxDocVO {
	
	private Date RECEIVED_DATE;
	private Date EHARS_IMPORT_DATE;
	private int DOCUMENT_ID;
	private int CURRENT_PROCESS_STATUS;
	private String BIRTH_SEX;
	private String VITAL_STATUS;
	private String XLAB;
	private String PHYS_HIV;
	private String RACE_ASIAN;
	private String RACE_BLACK;
	private String RACE_INDIAN;
	private String RACE_OTHER;
	private String RACE_PACIFIC;
	private String RACE_UNKNOWN;
	private String RACE_WHITE;
	private String EHARS_STATUS_CD;
	private String DOB_TEXT;
	private String DOD_TEXT;
	private String HIV_DX_DATE_TEXT;
	private String AIDS_DX_DATE_TEXT;
	private String HIV_AIDS_DX_DATE_TEXT;
	private String CUR_ADDRESS_DATE_TEXT;
	private String SAMPLE_DATE_HIV1_TEXT;
	private String SAMPLE_DATE_HIV2_TEXT;
	private String SAMPLE_DATE_HIV3_TEXT;
	private String XLAB_DATE_TEXT;
	private String PHYS_HIV_DATE_TEXT;
	private String SAMPLE_DATE_AIDS_TEXT;
	private String OI_DX_DATE_TEXT;
	private String VL_RECENT_DATE_TEXT;
	private String CD4_RECENT_CNT_DATE_TEXT;
	private String CD4_RECENT_PCT_DATE_TEXT;
	private String SAMPLE_DT_NEG_TEXT;
	private String TTH_LAST_NEG_DATE_TEXT;
	private String RSD_STREET_ADDRESS;
	private String CUR_STREET_ADDRESS;
	private String SSN;
	private String STATENO;
	private String RIDR_STATENO;
	private String CITYNO;
	private String TRANS_CATEG;
	private String RSH_STATE_CD;
	private String RSA_STATE_CD;
	private String RSD_STATE_CD;
	private String RAD_STATE_CD;
	private String CUR_STATE_CD;
	private String STATE_CODE;
	private String SCORE;
	private String LAB_TEST_CD_HIV1;
	private String LAB_TEST_TYPE_HIV1;
	private String RESULT_HIV1;
	private String RESULT_INTERPRETATION_HIV1;
	private String RESULT_UNITS_HIV1;
	private String LAB_TEST_CD_HIV2;
	private String LAB_TEST_TYPE_HIV2;
	private String RESULT_HIV2;
	private String RESULT_INTERPRETATION_HIV2;
	private String RESULT_UNITS_HIV2;
	private String LAB_TEST_CD_HIV3;
	private String LAB_TEST_TYPE_HIV3;
	private String RESULT_HIV3;
	private String RESULT_INTERPRETATION_HIV3;
	private String RESULT_UNITS_HIV3;
	private String LAB_TEST_CD_AIDS;
	private String LAB_TEST_TYPE_AIDS;
	private String RESULT_AIDS;
	private String RESULT_UNITS_AIDS;
	private String OI_CD;
	private String VL_RECENT_VALUE;
	private String CD4_RECENT_CNT_VALUE;
	private String CD4_RECENT_PCT_VALUE;
	private String LAB_TEST_CD_NEG;
	private String LAB_TEST_TYPE_NEG;
	private String RESULT_NEG;
	private String RESULT_INTERPRETATION_NEG;
	private String RESULT_UNITS_NEG;
	private String COMMENTS;
	private String FIRST_NAME;
	private String MIDDLE_NAME;
	private String LAST_NAME;
	private String CURRENT_GENDER;
	private String HIV_CATEG;
	private String AIDS_CATEG;
	private String RSD_COUNTRY_CD;
	private String ETHNICITY;
	private String RSD_CITY_NAME;
	private String CUR_CITY_NAME;
	private String RSH_CITY_NAME;
	private String RSA_CITY_NAME;
	private String RSD_ZIP_CD;
	private String CUR_ZIP_CD;
	private String RSH_ZIP_CD;
	private String RSA_ZIP_CD;
	private String MATCH_SUMMARY;
	private String RSD_COUNTY_NAME;
	private String CUR_COUNTY_NAME;
	private String RSH_COUNTY_NAME;
	private String RSA_COUNTY_NAME;
	private String CUR_COUNTRY_CD;
	
	public Date getRECEIVED_DATE() {
		return RECEIVED_DATE;
	}
	public void setRECEIVED_DATE(Date rECEIVED_DATE) {
		RECEIVED_DATE = rECEIVED_DATE;
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
	public int getCURRENT_PROCESS_STATUS() {
		return CURRENT_PROCESS_STATUS;
	}
	public void setCURRENT_PROCESS_STATUS(int cURRENT_PROCESS_STATUS) {
		CURRENT_PROCESS_STATUS = cURRENT_PROCESS_STATUS;
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
	public String getXLAB() {
		return XLAB;
	}
	public void setXLAB(String xLAB) {
		XLAB = xLAB;
	}
	public String getPHYS_HIV() {
		return PHYS_HIV;
	}
	public void setPHYS_HIV(String pHYS_HIV) {
		PHYS_HIV = pHYS_HIV;
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
	public String getHIV_DX_DATE_TEXT() {
		return HIV_DX_DATE_TEXT;
	}
	public void setHIV_DX_DATE_TEXT(String hIV_DX_DATE_TEXT) {
		HIV_DX_DATE_TEXT = hIV_DX_DATE_TEXT;
	}
	public String getAIDS_DX_DATE_TEXT() {
		return AIDS_DX_DATE_TEXT;
	}
	public void setAIDS_DX_DATE_TEXT(String aIDS_DX_DATE_TEXT) {
		AIDS_DX_DATE_TEXT = aIDS_DX_DATE_TEXT;
	}
	public String getHIV_AIDS_DX_DATE_TEXT() {
		return HIV_AIDS_DX_DATE_TEXT;
	}
	public void setHIV_AIDS_DX_DATE_TEXT(String hIV_AIDS_DX_DATE_TEXT) {
		HIV_AIDS_DX_DATE_TEXT = hIV_AIDS_DX_DATE_TEXT;
	}
	public String getCUR_ADDRESS_DATE_TEXT() {
		return CUR_ADDRESS_DATE_TEXT;
	}
	public void setCUR_ADDRESS_DATE_TEXT(String cUR_ADDRESS_DATE_TEXT) {
		CUR_ADDRESS_DATE_TEXT = cUR_ADDRESS_DATE_TEXT;
	}
	public String getSAMPLE_DATE_HIV1_TEXT() {
		return SAMPLE_DATE_HIV1_TEXT;
	}
	public void setSAMPLE_DATE_HIV1_TEXT(String sAMPLE_DATE_HIV1_TEXT) {
		SAMPLE_DATE_HIV1_TEXT = sAMPLE_DATE_HIV1_TEXT;
	}
	public String getSAMPLE_DATE_HIV2_TEXT() {
		return SAMPLE_DATE_HIV2_TEXT;
	}
	public void setSAMPLE_DATE_HIV2_TEXT(String sAMPLE_DATE_HIV2_TEXT) {
		SAMPLE_DATE_HIV2_TEXT = sAMPLE_DATE_HIV2_TEXT;
	}
	public String getSAMPLE_DATE_HIV3_TEXT() {
		return SAMPLE_DATE_HIV3_TEXT;
	}
	public void setSAMPLE_DATE_HIV3_TEXT(String sAMPLE_DATE_HIV3_TEXT) {
		SAMPLE_DATE_HIV3_TEXT = sAMPLE_DATE_HIV3_TEXT;
	}
	public String getXLAB_DATE_TEXT() {
		return XLAB_DATE_TEXT;
	}
	public void setXLAB_DATE_TEXT(String xLAB_DATE_TEXT) {
		XLAB_DATE_TEXT = xLAB_DATE_TEXT;
	}
	public String getPHYS_HIV_DATE_TEXT() {
		return PHYS_HIV_DATE_TEXT;
	}
	public void setPHYS_HIV_DATE_TEXT(String pHYS_HIV_DATE_TEXT) {
		PHYS_HIV_DATE_TEXT = pHYS_HIV_DATE_TEXT;
	}
	public String getSAMPLE_DATE_AIDS_TEXT() {
		return SAMPLE_DATE_AIDS_TEXT;
	}
	public void setSAMPLE_DATE_AIDS_TEXT(String sAMPLE_DATE_AIDS_TEXT) {
		SAMPLE_DATE_AIDS_TEXT = sAMPLE_DATE_AIDS_TEXT;
	}
	public String getOI_DX_DATE_TEXT() {
		return OI_DX_DATE_TEXT;
	}
	public void setOI_DX_DATE_TEXT(String oI_DX_DATE_TEXT) {
		OI_DX_DATE_TEXT = oI_DX_DATE_TEXT;
	}
	public String getVL_RECENT_DATE_TEXT() {
		return VL_RECENT_DATE_TEXT;
	}
	public void setVL_RECENT_DATE_TEXT(String vL_RECENT_DATE_TEXT) {
		VL_RECENT_DATE_TEXT = vL_RECENT_DATE_TEXT;
	}
	public String getCD4_RECENT_CNT_DATE_TEXT() {
		return CD4_RECENT_CNT_DATE_TEXT;
	}
	public void setCD4_RECENT_CNT_DATE_TEXT(String cD4_RECENT_CNT_DATE_TEXT) {
		CD4_RECENT_CNT_DATE_TEXT = cD4_RECENT_CNT_DATE_TEXT;
	}
	public String getCD4_RECENT_PCT_DATE_TEXT() {
		return CD4_RECENT_PCT_DATE_TEXT;
	}
	public void setCD4_RECENT_PCT_DATE_TEXT(String cD4_RECENT_PCT_DATE_TEXT) {
		CD4_RECENT_PCT_DATE_TEXT = cD4_RECENT_PCT_DATE_TEXT;
	}
	public String getSAMPLE_DT_NEG_TEXT() {
		return SAMPLE_DT_NEG_TEXT;
	}
	public void setSAMPLE_DT_NEG_TEXT(String sAMPLE_DT_NEG_TEXT) {
		SAMPLE_DT_NEG_TEXT = sAMPLE_DT_NEG_TEXT;
	}
	public String getTTH_LAST_NEG_DATE_TEXT() {
		return TTH_LAST_NEG_DATE_TEXT;
	}
	public void setTTH_LAST_NEG_DATE_TEXT(String tTH_LAST_NEG_DATE_TEXT) {
		TTH_LAST_NEG_DATE_TEXT = tTH_LAST_NEG_DATE_TEXT;
	}
	public String getRSD_STREET_ADDRESS() {
		return RSD_STREET_ADDRESS;
	}
	public void setRSD_STREET_ADDRESS(String rSD_STREET_ADDRESS) {
		RSD_STREET_ADDRESS = rSD_STREET_ADDRESS;
	}
	public String getCUR_STREET_ADDRESS() {
		return CUR_STREET_ADDRESS;
	}
	public void setCUR_STREET_ADDRESS(String cUR_STREET_ADDRESS) {
		CUR_STREET_ADDRESS = cUR_STREET_ADDRESS;
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
	public String getRIDR_STATENO() {
		return RIDR_STATENO;
	}
	public void setRIDR_STATENO(String rIDR_STATENO) {
		RIDR_STATENO = rIDR_STATENO;
	}
	public String getCITYNO() {
		return CITYNO;
	}
	public void setCITYNO(String cITYNO) {
		CITYNO = cITYNO;
	}
	public String getTRANS_CATEG() {
		return TRANS_CATEG;
	}
	public void setTRANS_CATEG(String tRANS_CATEG) {
		TRANS_CATEG = tRANS_CATEG;
	}
	public String getRSH_STATE_CD() {
		return RSH_STATE_CD;
	}
	public void setRSH_STATE_CD(String rSH_STATE_CD) {
		RSH_STATE_CD = rSH_STATE_CD;
	}
	public String getRSA_STATE_CD() {
		return RSA_STATE_CD;
	}
	public void setRSA_STATE_CD(String rSA_STATE_CD) {
		RSA_STATE_CD = rSA_STATE_CD;
	}
	public String getRSD_STATE_CD() {
		return RSD_STATE_CD;
	}
	public void setRSD_STATE_CD(String rSD_STATE_CD) {
		RSD_STATE_CD = rSD_STATE_CD;
	}
	public String getRAD_STATE_CD() {
		return RAD_STATE_CD;
	}
	public void setRAD_STATE_CD(String rAD_STATE_CD) {
		RAD_STATE_CD = rAD_STATE_CD;
	}
	public String getCUR_STATE_CD() {
		return CUR_STATE_CD;
	}
	public void setCUR_STATE_CD(String cUR_STATE_CD) {
		CUR_STATE_CD = cUR_STATE_CD;
	}
	public String getSTATE_CODE() {
		return STATE_CODE;
	}
	public void setSTATE_CODE(String sTATE_CODE) {
		STATE_CODE = sTATE_CODE;
	}
	public String getSCORE() {
		return SCORE;
	}
	public void setSCORE(String sCORE) {
		SCORE = sCORE;
	}
	public String getLAB_TEST_CD_HIV1() {
		return LAB_TEST_CD_HIV1;
	}
	public void setLAB_TEST_CD_HIV1(String lAB_TEST_CD_HIV1) {
		LAB_TEST_CD_HIV1 = lAB_TEST_CD_HIV1;
	}
	public String getLAB_TEST_TYPE_HIV1() {
		return LAB_TEST_TYPE_HIV1;
	}
	public void setLAB_TEST_TYPE_HIV1(String lAB_TEST_TYPE_HIV1) {
		LAB_TEST_TYPE_HIV1 = lAB_TEST_TYPE_HIV1;
	}
	public String getRESULT_HIV1() {
		return RESULT_HIV1;
	}
	public void setRESULT_HIV1(String rESULT_HIV1) {
		RESULT_HIV1 = rESULT_HIV1;
	}
	public String getRESULT_INTERPRETATION_HIV1() {
		return RESULT_INTERPRETATION_HIV1;
	}
	public void setRESULT_INTERPRETATION_HIV1(String rESULT_INTERPRETATION_HIV1) {
		RESULT_INTERPRETATION_HIV1 = rESULT_INTERPRETATION_HIV1;
	}
	public String getRESULT_UNITS_HIV1() {
		return RESULT_UNITS_HIV1;
	}
	public void setRESULT_UNITS_HIV1(String rESULT_UNITS_HIV1) {
		RESULT_UNITS_HIV1 = rESULT_UNITS_HIV1;
	}
	public String getLAB_TEST_CD_HIV2() {
		return LAB_TEST_CD_HIV2;
	}
	public void setLAB_TEST_CD_HIV2(String lAB_TEST_CD_HIV2) {
		LAB_TEST_CD_HIV2 = lAB_TEST_CD_HIV2;
	}
	public String getLAB_TEST_TYPE_HIV2() {
		return LAB_TEST_TYPE_HIV2;
	}
	public void setLAB_TEST_TYPE_HIV2(String lAB_TEST_TYPE_HIV2) {
		LAB_TEST_TYPE_HIV2 = lAB_TEST_TYPE_HIV2;
	}
	public String getRESULT_HIV2() {
		return RESULT_HIV2;
	}
	public void setRESULT_HIV2(String rESULT_HIV2) {
		RESULT_HIV2 = rESULT_HIV2;
	}
	public String getRESULT_INTERPRETATION_HIV2() {
		return RESULT_INTERPRETATION_HIV2;
	}
	public void setRESULT_INTERPRETATION_HIV2(String rESULT_INTERPRETATION_HIV2) {
		RESULT_INTERPRETATION_HIV2 = rESULT_INTERPRETATION_HIV2;
	}
	public String getRESULT_UNITS_HIV2() {
		return RESULT_UNITS_HIV2;
	}
	public void setRESULT_UNITS_HIV2(String rESULT_UNITS_HIV2) {
		RESULT_UNITS_HIV2 = rESULT_UNITS_HIV2;
	}
	public String getLAB_TEST_CD_HIV3() {
		return LAB_TEST_CD_HIV3;
	}
	public void setLAB_TEST_CD_HIV3(String lAB_TEST_CD_HIV3) {
		LAB_TEST_CD_HIV3 = lAB_TEST_CD_HIV3;
	}
	public String getLAB_TEST_TYPE_HIV3() {
		return LAB_TEST_TYPE_HIV3;
	}
	public void setLAB_TEST_TYPE_HIV3(String lAB_TEST_TYPE_HIV3) {
		LAB_TEST_TYPE_HIV3 = lAB_TEST_TYPE_HIV3;
	}
	public String getRESULT_HIV3() {
		return RESULT_HIV3;
	}
	public void setRESULT_HIV3(String rESULT_HIV3) {
		RESULT_HIV3 = rESULT_HIV3;
	}
	public String getRESULT_INTERPRETATION_HIV3() {
		return RESULT_INTERPRETATION_HIV3;
	}
	public void setRESULT_INTERPRETATION_HIV3(String rESULT_INTERPRETATION_HIV3) {
		RESULT_INTERPRETATION_HIV3 = rESULT_INTERPRETATION_HIV3;
	}
	public String getRESULT_UNITS_HIV3() {
		return RESULT_UNITS_HIV3;
	}
	public void setRESULT_UNITS_HIV3(String rESULT_UNITS_HIV3) {
		RESULT_UNITS_HIV3 = rESULT_UNITS_HIV3;
	}
	public String getLAB_TEST_CD_AIDS() {
		return LAB_TEST_CD_AIDS;
	}
	public void setLAB_TEST_CD_AIDS(String lAB_TEST_CD_AIDS) {
		LAB_TEST_CD_AIDS = lAB_TEST_CD_AIDS;
	}
	public String getLAB_TEST_TYPE_AIDS() {
		return LAB_TEST_TYPE_AIDS;
	}
	public void setLAB_TEST_TYPE_AIDS(String lAB_TEST_TYPE_AIDS) {
		LAB_TEST_TYPE_AIDS = lAB_TEST_TYPE_AIDS;
	}
	public String getRESULT_AIDS() {
		return RESULT_AIDS;
	}
	public void setRESULT_AIDS(String rESULT_AIDS) {
		RESULT_AIDS = rESULT_AIDS;
	}
	public String getRESULT_UNITS_AIDS() {
		return RESULT_UNITS_AIDS;
	}
	public void setRESULT_UNITS_AIDS(String rESULT_UNITS_AIDS) {
		RESULT_UNITS_AIDS = rESULT_UNITS_AIDS;
	}
	public String getOI_CD() {
		return OI_CD;
	}
	public void setOI_CD(String oI_CD) {
		OI_CD = oI_CD;
	}
	public String getVL_RECENT_VALUE() {
		return VL_RECENT_VALUE;
	}
	public void setVL_RECENT_VALUE(String vL_RECENT_VALUE) {
		VL_RECENT_VALUE = vL_RECENT_VALUE;
	}
	public String getCD4_RECENT_CNT_VALUE() {
		return CD4_RECENT_CNT_VALUE;
	}
	public void setCD4_RECENT_CNT_VALUE(String cD4_RECENT_CNT_VALUE) {
		CD4_RECENT_CNT_VALUE = cD4_RECENT_CNT_VALUE;
	}
	public String getCD4_RECENT_PCT_VALUE() {
		return CD4_RECENT_PCT_VALUE;
	}
	public void setCD4_RECENT_PCT_VALUE(String cD4_RECENT_PCT_VALUE) {
		CD4_RECENT_PCT_VALUE = cD4_RECENT_PCT_VALUE;
	}
	public String getLAB_TEST_CD_NEG() {
		return LAB_TEST_CD_NEG;
	}
	public void setLAB_TEST_CD_NEG(String lAB_TEST_CD_NEG) {
		LAB_TEST_CD_NEG = lAB_TEST_CD_NEG;
	}
	public String getLAB_TEST_TYPE_NEG() {
		return LAB_TEST_TYPE_NEG;
	}
	public void setLAB_TEST_TYPE_NEG(String lAB_TEST_TYPE_NEG) {
		LAB_TEST_TYPE_NEG = lAB_TEST_TYPE_NEG;
	}
	public String getRESULT_NEG() {
		return RESULT_NEG;
	}
	public void setRESULT_NEG(String rESULT_NEG) {
		RESULT_NEG = rESULT_NEG;
	}
	public String getRESULT_INTERPRETATION_NEG() {
		return RESULT_INTERPRETATION_NEG;
	}
	public void setRESULT_INTERPRETATION_NEG(String rESULT_INTERPRETATION_NEG) {
		RESULT_INTERPRETATION_NEG = rESULT_INTERPRETATION_NEG;
	}
	public String getRESULT_UNITS_NEG() {
		return RESULT_UNITS_NEG;
	}
	public void setRESULT_UNITS_NEG(String rESULT_UNITS_NEG) {
		RESULT_UNITS_NEG = rESULT_UNITS_NEG;
	}
	public String getCOMMENTS() {
		return COMMENTS;
	}
	public void setCOMMENTS(String cOMMENTS) {
		COMMENTS = cOMMENTS;
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
	public String getCURRENT_GENDER() {
		return CURRENT_GENDER;
	}
	public void setCURRENT_GENDER(String cURRENT_GENDER) {
		CURRENT_GENDER = cURRENT_GENDER;
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
	public String getRSD_COUNTRY_CD() {
		return RSD_COUNTRY_CD;
	}
	public void setRSD_COUNTRY_CD(String rSD_COUNTRY_CD) {
		RSD_COUNTRY_CD = rSD_COUNTRY_CD;
	}
	public String getETHNICITY() {
		return ETHNICITY;
	}
	public void setETHNICITY(String eTHNICITY) {
		ETHNICITY = eTHNICITY;
	}
	public String getRSD_CITY_NAME() {
		return RSD_CITY_NAME;
	}
	public void setRSD_CITY_NAME(String rSD_CITY_NAME) {
		RSD_CITY_NAME = rSD_CITY_NAME;
	}
	public String getCUR_CITY_NAME() {
		return CUR_CITY_NAME;
	}
	public void setCUR_CITY_NAME(String cUR_CITY_NAME) {
		CUR_CITY_NAME = cUR_CITY_NAME;
	}
	public String getRSH_CITY_NAME() {
		return RSH_CITY_NAME;
	}
	public void setRSH_CITY_NAME(String rSH_CITY_NAME) {
		RSH_CITY_NAME = rSH_CITY_NAME;
	}
	public String getRSA_CITY_NAME() {
		return RSA_CITY_NAME;
	}
	public void setRSA_CITY_NAME(String rSA_CITY_NAME) {
		RSA_CITY_NAME = rSA_CITY_NAME;
	}
	public String getRSD_ZIP_CD() {
		return RSD_ZIP_CD;
	}
	public void setRSD_ZIP_CD(String rSD_ZIP_CD) {
		RSD_ZIP_CD = rSD_ZIP_CD;
	}
	public String getCUR_ZIP_CD() {
		return CUR_ZIP_CD;
	}
	public void setCUR_ZIP_CD(String cUR_ZIP_CD) {
		CUR_ZIP_CD = cUR_ZIP_CD;
	}
	public String getRSH_ZIP_CD() {
		return RSH_ZIP_CD;
	}
	public void setRSH_ZIP_CD(String rSH_ZIP_CD) {
		RSH_ZIP_CD = rSH_ZIP_CD;
	}
	public String getRSA_ZIP_CD() {
		return RSA_ZIP_CD;
	}
	public void setRSA_ZIP_CD(String rSA_ZIP_CD) {
		RSA_ZIP_CD = rSA_ZIP_CD;
	}
	public String getMATCH_SUMMARY() {
		return MATCH_SUMMARY;
	}
	public void setMATCH_SUMMARY(String mATCH_SUMMARY) {
		MATCH_SUMMARY = mATCH_SUMMARY;
	}
	public String getRSD_COUNTY_NAME() {
		return RSD_COUNTY_NAME;
	}
	public void setRSD_COUNTY_NAME(String rSD_COUNTY_NAME) {
		RSD_COUNTY_NAME = rSD_COUNTY_NAME;
	}
	public String getCUR_COUNTY_NAME() {
		return CUR_COUNTY_NAME;
	}
	public void setCUR_COUNTY_NAME(String cUR_COUNTY_NAME) {
		CUR_COUNTY_NAME = cUR_COUNTY_NAME;
	}
	public String getRSH_COUNTY_NAME() {
		return RSH_COUNTY_NAME;
	}
	public void setRSH_COUNTY_NAME(String rSH_COUNTY_NAME) {
		RSH_COUNTY_NAME = rSH_COUNTY_NAME;
	}
	public String getRSA_COUNTY_NAME() {
		return RSA_COUNTY_NAME;
	}
	public void setRSA_COUNTY_NAME(String rSA_COUNTY_NAME) {
		RSA_COUNTY_NAME = rSA_COUNTY_NAME;
	}
	public String getCUR_COUNTRY_CD() {
		return CUR_COUNTRY_CD;
	}
	public void setCUR_COUNTRY_CD(String cUR_COUNTRY_CD) {
		CUR_COUNTRY_CD = cUR_COUNTRY_CD;
	}
	
	
	
}	
