package gov.ny.state.doh.bhae.nyehms.web.spring.domain.document;

import java.sql.Date;
import java.util.List;


public class MedicaidDocVO extends NYEHMSDocVO{
	private String DOCUMENT_ID;
	private String FNAME;
    private String LNAME;
    private String MNAME;
    private String CURRENT_GENDER;
    private String RACE_INDIAN;
    private String RACE_BLACK;
    private String RACE_ASIAN;
    private String RACE_PACIFIC;
    private String RACE_WHITE;
    private String RACE_MULTIPLE;
    private String RACE_OTHER;
    private String RACE_UNKNOWN;
    private String ETHNICITY;
    private String PERSON_ADDRESS;
    private String PERSON_CITY;
    private String PERSON_STATE;
    private String PERSON_ZIP;
    private String PERSON_COUNTY ;
    private String CRITERIA_1;
    private String CRITERIA_2;
    private String CRITERIA_3;
    private String CRITERIA_4;
    private String CRITERIA_5;
    private String CRITERIA_6;
    private String CRITERIA_7;
    private String CRITERIA_8;
    private String CRITERIA_9;
    private String CRITERIA_10;
    private String MEDICAID_NUMBER;
    private String VITAL_STATUS;
    private Date NYC_SHIP_DATE;
    private String STATENO;
    private String DOD_TEXT;
    private String  raw_address;
    private String DOB_TEXT ;
    private String DOB;
    private String SSN;
    private Date EHARS_IMPORT_DATE;
    private Date CREATE_DATE; 
    private Date MATCHED_PROCESS_DATE;
    private Date FLUX_MATCHED_PROCESS_DATE;
    private String CIN;
    
    
    private List<MedicaidDocArvsVO> medicaidDocArvsVO;
    
    private List<MedicaidDocProvidersVO> medicaidDocProvidersVO;
    
    
	public Date getEHARS_IMPORT_DATE() {
		return EHARS_IMPORT_DATE;
	}
	public void setEHARS_IMPORT_DATE(Date eHARS_IMPORT_DATE) {
		EHARS_IMPORT_DATE = eHARS_IMPORT_DATE;
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
	public String getCURRENT_GENDER() {
		return CURRENT_GENDER;
	}
	public void setCURRENT_GENDER(String cURRENT_GENDER) {
		CURRENT_GENDER = cURRENT_GENDER;
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
	public String getETHNICITY() {
		return ETHNICITY;
	}
	public void setETHNICITY(String eTHNICITY) {
		ETHNICITY = eTHNICITY;
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
	public String getPERSON_COUNTY() {
		return PERSON_COUNTY;
	}
	public void setPERSON_COUNTY(String pERSON_COUNTY) {
		PERSON_COUNTY = pERSON_COUNTY;
	}
	public String getCRITERIA_1() {
		return CRITERIA_1;
	}
	public void setCRITERIA_1(String cRITERIA_1) {
		CRITERIA_1 = cRITERIA_1;
	}
	public String getCRITERIA_2() {
		return CRITERIA_2;
	}
	public void setCRITERIA_2(String cRITERIA_2) {
		CRITERIA_2 = cRITERIA_2;
	}
	public String getCRITERIA_3() {
		return CRITERIA_3;
	}
	public void setCRITERIA_3(String cRITERIA_3) {
		CRITERIA_3 = cRITERIA_3;
	}
	public String getCRITERIA_4() {
		return CRITERIA_4;
	}
	public void setCRITERIA_4(String cRITERIA_4) {
		CRITERIA_4 = cRITERIA_4;
	}
	public String getCRITERIA_5() {
		return CRITERIA_5;
	}
	public void setCRITERIA_5(String cRITERIA_5) {
		CRITERIA_5 = cRITERIA_5;
	}
	public String getCRITERIA_6() {
		return CRITERIA_6;
	}
	public void setCRITERIA_6(String cRITERIA_6) {
		CRITERIA_6 = cRITERIA_6;
	}
	public String getCRITERIA_7() {
		return CRITERIA_7;
	}
	public void setCRITERIA_7(String cRITERIA_7) {
		CRITERIA_7 = cRITERIA_7;
	}
	public String getCRITERIA_8() {
		return CRITERIA_8;
	}
	public void setCRITERIA_8(String cRITERIA_8) {
		CRITERIA_8 = cRITERIA_8;
	}
	public String getCRITERIA_9() {
		return CRITERIA_9;
	}
	public void setCRITERIA_9(String cRITERIA_9) {
		CRITERIA_9 = cRITERIA_9;
	}
	public String getCRITERIA_10() {
		return CRITERIA_10;
	}
	public void setCRITERIA_10(String cRITERIA_10) {
		CRITERIA_10 = cRITERIA_10;
	}
	public String getMEDICAID_NUMBER() {
		return MEDICAID_NUMBER;
	}
	public void setMEDICAID_NUMBER(String MEDICAID_NUMBER) {
		this.MEDICAID_NUMBER = MEDICAID_NUMBER;
	}
	public String getVITAL_STATUS() {
		return VITAL_STATUS;
	}
	public void setVITAL_STATUS(String vITAL_STATUS) {
		VITAL_STATUS = vITAL_STATUS;
	}
	public Date getNYC_SHIP_DATE() {
		return NYC_SHIP_DATE;
	}
	public void setNYC_SHIP_DATE(Date nYC_SHIP_DATE) {
		NYC_SHIP_DATE = nYC_SHIP_DATE;
	}
	public String getSTATENO() {
		return STATENO;
	}
	public void setSTATENO(String sTATENO) {
		STATENO = sTATENO;
	}
	public String getDOD_TEXT() {
		return DOD_TEXT;
	}
	public void setDOD_TEXT(String dOD_TEXT) {
		DOD_TEXT = dOD_TEXT;
	}
	public String getRaw_address() {
		return raw_address;
	}
	public void setRaw_address(String raw_address) {
		this.raw_address = raw_address;
	}
	
	public List<MedicaidDocArvsVO> getMedicaidDocArvsVO() {
		return medicaidDocArvsVO;
	}
	public void setMedicaidDocArvsVO(List<MedicaidDocArvsVO> medicaidDocArvsVO) {
		this.medicaidDocArvsVO = medicaidDocArvsVO;
	}
	public List<MedicaidDocProvidersVO> getMedicaidDocProvidersVO() {
		return medicaidDocProvidersVO;
	}
	public void setMedicaidDocProvidersVO(
			List<MedicaidDocProvidersVO> medicaidDocProvidersVO) {
		this.medicaidDocProvidersVO = medicaidDocProvidersVO;
	}
	public String getDOCUMENT_ID() {
		return DOCUMENT_ID;
	}
	public void setDOCUMENT_ID(String dOCUMENT_ID) {
		DOCUMENT_ID = dOCUMENT_ID;
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
	public String getDOB() {
		return DOB;
	}
	public void setDOB(String dOB) {
		DOB = dOB;
	}
	public Date getCREATE_DATE() {
		return CREATE_DATE;
	}
	public void setCREATE_DATE(Date cREATE_DATE) {
		CREATE_DATE = cREATE_DATE;
	}
	public Date getMATCHED_PROCESS_DATE() {
		return MATCHED_PROCESS_DATE;
	}
	public void setMATCHED_PROCESS_DATE(Date mATCHED_PROCESS_DATE) {
		MATCHED_PROCESS_DATE = mATCHED_PROCESS_DATE;
	}
	public String getCIN() {
		return CIN;
	}
	public void setCIN(String cIN) {
		CIN = cIN;
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
