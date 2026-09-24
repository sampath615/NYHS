package gov.ny.state.doh.bhae.nyehms.web.spring.domain.document;

public class QualCareDocVO extends NYEHMSDocVO {
	
	private Integer DOCUMENT_ID;
	private Integer ID;
	private String FNAME;
	private String LNAME;
	private String MNAME;
	private String  DOB_TEXT;
	private String BIRTH_SEX;
	private String GENDER;
	private String RACE_WHITE;
	private String RACE_UNKNOWN;
	private String RACE_PACIFIC;
	private String RACE_OTHER;
	private String RACE_INDIAN;
	private String RACE_BLACK;
	private String RACE_ASIAN;
	private String ETHNICITY;
	private Integer AGE;
	private String MED_RECORD_NUMBER;
	private String FACILITY_ADDRESS;
	private String FACILITY_CITY;
	private String FACILITY_ZIP;
	private String HOUSING_STATUS;
	private String INSURANCE;
	private String ENROLLMENT;
	private String INPATIENT;
	private String PATIENT_STATUS;
	private String DIAGNOSIS;
	private String DIAGNOSIS_DATE;
	private String DISCHARGE_DATE;
	private String ORGANIZATION_NAME;
	private String FACILITY_NAME;
	private String RECV_BLOOD_PROD;
	private String RECV_CLOTTING_FACTOR;
	private String SEX_PARTNER_HIV;
	private String IDU;
	private String RISK_MSM;
	private String PERINATAL;
	private String RISK_OTHER;
	private String RISK_UNKNOWN;
	private String SERVICE_LINE;
	private String OTHER_SERVICE;
	private String ASIAN_SUB_GROUP;
	private String HISPANIC_SUB_GROUP;
	private String LINKED;
	private String LINKAGE_30DAYS;
	private String LINKAGE_3DAYS;
	private String LINKAGE_7DAYS;
	private String LINKAGE_90DAYS;
	private String LINKAGE_DAYS;
	private String HIV_CARE;
	private String HIV_CARE_DATE;
	private String ARV;
	private String ON_ARV;
	private String ON_ARV_ALL_PREV_DX_ACTIVE;
	private String ON_ARV_EST_ACTIVE;
	private String ON_ARV_NEWLY_DX;
	private String ON_ARV_OPEN_PTS;
	private String ON_ARV_OTHER_NEW_TO_CARE;
	private String FIRST_SUPPRESSED_DATE_NEWLY_DX;
	private String EVER_SUPPRESSED_NEWLY_DX;
	private String SUPPRESSED_EVER_REVIEW_YEAR;
	private String SUPPRESSED_FINAL_REVIEW_YEAR;
	private String SUPPRESSION_91DAYS_FROM_CARE;
	private String SUPPRESSION_91DAYS_FROM_DIS;
	private String SUPPRESSION_DAYS;
	private String SUPPRESSION_DAYS_DISCHARGE;
	private String SUPPRESSION_DAYS_HIV_CARE;
	private String FIRST_VL_DATE_NEWLY_DX;
	private String SUPPRESSED_FINAL_VL;
	private String VL_REVIEWED;
	private String VL_SUPPRESSED;
	private String VL_TEST_REVIEW_YEAR;
	private String VL_SUPPRESSION_ALL_PREV_DX_ACT;
	private String VL_SUPPRESSION_EST_ACTIVE;
	private String VL_SUPPRESSION_NEWLY_DX;
	private String VL_SUPPRESSION_OPEN_PTS;
	private String VL_SUPPRESSION_OTHER_NEW_TO_CARE;
	private String VL_TESTED;
	private String VL_TESTING_ALL_PREV_DX_ACTIVE;
	private String VL_TESTING_EST_ACTIVE;
	private String VL_TESTING_NEWLY_DX;
	private String VL_TESTING_OPEN_PTS;
	private String VL_TESTING_OTHER_NEW_TO_CARE;
	private String FACILITY_STATE;
	private String FACILITY_COUNTY;
	private String STATENO;
	private String RAWFACILITYADDRESS;
	
	public Integer getDOCUMENT_ID() {
		return DOCUMENT_ID;
	}
	public void setDOCUMENT_ID(Integer dOCUMENT_ID) {
		DOCUMENT_ID = dOCUMENT_ID;
	}
	public Integer getID() {
		return ID;
	}
	public void setID(Integer iD) {
		ID = iD;
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
	public String getDOB_TEXT() {
		return DOB_TEXT;
	}
	public void setDOB_TEXT(String dOB_TEXT) {
		DOB_TEXT = dOB_TEXT;
	}
	public String getBIRTH_SEX() {
		return BIRTH_SEX;
	}
	public void setBIRTH_SEX(String bIRTH_SEX) {
		BIRTH_SEX = bIRTH_SEX;
	}
	public String getGENDER() {
		return GENDER;
	}
	public void setGENDER(String gENDER) {
		GENDER = gENDER;
	}
	public String getRACE_WHITE() {
		return RACE_WHITE;
	}
	public void setRACE_WHITE(String rACE_WHITE) {
		RACE_WHITE = rACE_WHITE;
	}
	public String getRACE_UNKNOWN() {
		return RACE_UNKNOWN;
	}
	public void setRACE_UNKNOWN(String rACE_UNKNOWN) {
		RACE_UNKNOWN = rACE_UNKNOWN;
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
	public String getETHNICITY() {
		return ETHNICITY;
	}
	public void setETHNICITY(String eTHNICITY) {
		ETHNICITY = eTHNICITY;
	}
	public Integer getAGE() {
		return AGE;
	}
	public void setAGE(Integer aGE) {
		AGE = aGE;
	}
	public String getMED_RECORD_NUMBER() {
		return MED_RECORD_NUMBER;
	}
	public void setMED_RECORD_NUMBER(String mED_RECORD_NUMBER) {
		MED_RECORD_NUMBER = mED_RECORD_NUMBER;
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
	public String getFACILITY_ZIP() {
		return FACILITY_ZIP;
	}
	public void setFACILITY_ZIP(String fACILITY_ZIP) {
		FACILITY_ZIP = fACILITY_ZIP;
	}
	public String getHOUSING_STATUS() {
		return HOUSING_STATUS;
	}
	public void setHOUSING_STATUS(String hOUSING_STATUS) {
		HOUSING_STATUS = hOUSING_STATUS;
	}
	public String getINSURANCE() {
		return INSURANCE;
	}
	public void setINSURANCE(String iNSURANCE) {
		INSURANCE = iNSURANCE;
	}
	public String getENROLLMENT() {
		return ENROLLMENT;
	}
	public void setENROLLMENT(String eNROLLMENT) {
		ENROLLMENT = eNROLLMENT;
	}
	public String getINPATIENT() {
		return INPATIENT;
	}
	public void setINPATIENT(String iNPATIENT) {
		INPATIENT = iNPATIENT;
	}
	public String getPATIENT_STATUS() {
		return PATIENT_STATUS;
	}
	public void setPATIENT_STATUS(String pATIENT_STATUS) {
		PATIENT_STATUS = pATIENT_STATUS;
	}
	public String getDIAGNOSIS() {
		return DIAGNOSIS;
	}
	public void setDIAGNOSIS(String dIAGNOSIS) {
		DIAGNOSIS = dIAGNOSIS;
	}
	public String getDIAGNOSIS_DATE() {
		return DIAGNOSIS_DATE;
	}
	public void setDIAGNOSIS_DATE(String dIAGNOSIS_DATE) {
		DIAGNOSIS_DATE = dIAGNOSIS_DATE;
	}
	public String getDISCHARGE_DATE() {
		return DISCHARGE_DATE;
	}
	public void setDISCHARGE_DATE(String dISCHARGE_DATE) {
		DISCHARGE_DATE = dISCHARGE_DATE;
	}
	public String getORGANIZATION_NAME() {
		return ORGANIZATION_NAME;
	}
	public void setORGANIZATION_NAME(String oRGANIZATION_NAME) {
		ORGANIZATION_NAME = oRGANIZATION_NAME;
	}
	public String getFACILITY_NAME() {
		return FACILITY_NAME;
	}
	public void setFACILITY_NAME(String fACILITY_NAME) {
		FACILITY_NAME = fACILITY_NAME;
	}
	public String getRECV_BLOOD_PROD() {
		return RECV_BLOOD_PROD;
	}
	public void setRECV_BLOOD_PROD(String rECV_BLOOD_PROD) {
		RECV_BLOOD_PROD = rECV_BLOOD_PROD;
	}
	public String getRECV_CLOTTING_FACTOR() {
		return RECV_CLOTTING_FACTOR;
	}
	public void setRECV_CLOTTING_FACTOR(String rECV_CLOTTING_FACTOR) {
		RECV_CLOTTING_FACTOR = rECV_CLOTTING_FACTOR;
	}
	public String getRISK_MSM() {
		return RISK_MSM;
	}
	public void setRISK_MSM(String rISK_MSM) {
		RISK_MSM = rISK_MSM;
	}
	public String getSEX_PARTNER_HIV() {
		return SEX_PARTNER_HIV;
	}
	public void setSEX_PARTNER_HIV(String sEX_PARTNER_HIV) {
		SEX_PARTNER_HIV = sEX_PARTNER_HIV;
	}
	public String getIDU() {
		return IDU;
	}
	public void setIDU(String iDU) {
		IDU = iDU;
	}
	public String getPERINATAL() {
		return PERINATAL;
	}
	public void setPERINATAL(String pERINATAL) {
		PERINATAL = pERINATAL;
	}
	public String getRISK_OTHER() {
		return RISK_OTHER;
	}
	public void setRISK_OTHER(String rISK_OTHER) {
		RISK_OTHER = rISK_OTHER;
	}
	public String getRISK_UNKNOWN() {
		return RISK_UNKNOWN;
	}
	public void setRISK_UNKNOWN(String rISK_UNKNOWN) {
		RISK_UNKNOWN = rISK_UNKNOWN;
	}
	public String getSERVICE_LINE() {
		return SERVICE_LINE;
	}
	public void setSERVICE_LINE(String sERVICE_LINE) {
		SERVICE_LINE = sERVICE_LINE;
	}
	public String getOTHER_SERVICE() {
		return OTHER_SERVICE;
	}
	public void setOTHER_SERVICE(String oTHER_SERVICE) {
		OTHER_SERVICE = oTHER_SERVICE;
	}
	public String getASIAN_SUB_GROUP() {
		return ASIAN_SUB_GROUP;
	}
	public void setASIAN_SUB_GROUP(String aSIAN_SUB_GROUP) {
		ASIAN_SUB_GROUP = aSIAN_SUB_GROUP;
	}
	public String getHISPANIC_SUB_GROUP() {
		return HISPANIC_SUB_GROUP;
	}
	public void setHISPANIC_SUB_GROUP(String hISPANIC_SUB_GROUP) {
		HISPANIC_SUB_GROUP = hISPANIC_SUB_GROUP;
	}
	public String getLINKED() {
		return LINKED;
	}
	public void setLINKED(String lINKED) {
		LINKED = lINKED;
	}
	public String getLINKAGE_30DAYS() {
		return LINKAGE_30DAYS;
	}
	public void setLINKAGE_30DAYS(String lINKAGE_30DAYS) {
		LINKAGE_30DAYS = lINKAGE_30DAYS;
	}
	public String getLINKAGE_3DAYS() {
		return LINKAGE_3DAYS;
	}
	public void setLINKAGE_3DAYS(String lINKAGE_3DAYS) {
		LINKAGE_3DAYS = lINKAGE_3DAYS;
	}
	public String getLINKAGE_7DAYS() {
		return LINKAGE_7DAYS;
	}
	public void setLINKAGE_7DAYS(String lINKAGE_7DAYS) {
		LINKAGE_7DAYS = lINKAGE_7DAYS;
	}
	public String getLINKAGE_90DAYS() {
		return LINKAGE_90DAYS;
	}
	public void setLINKAGE_90DAYS(String lINKAGE_90DAYS) {
		LINKAGE_90DAYS = lINKAGE_90DAYS;
	}
	public String getLINKAGE_DAYS() {
		return LINKAGE_DAYS;
	}
	public void setLINKAGE_DAYS(String lINKAGE_DAYS) {
		LINKAGE_DAYS = lINKAGE_DAYS;
	}
	public String getHIV_CARE() {
		return HIV_CARE;
	}
	public void setHIV_CARE(String hIV_CARE) {
		HIV_CARE = hIV_CARE;
	}
	public String getHIV_CARE_DATE() {
		return HIV_CARE_DATE;
	}
	public void setHIV_CARE_DATE(String hIV_CARE_DATE) {
		HIV_CARE_DATE = hIV_CARE_DATE;
	}
	public String getARV() {
		return ARV;
	}
	public void setARV(String aRV) {
		ARV = aRV;
	}
	public String getON_ARV() {
		return ON_ARV;
	}
	public void setON_ARV(String oN_ARV) {
		ON_ARV = oN_ARV;
	}
	public String getON_ARV_ALL_PREV_DX_ACTIVE() {
		return ON_ARV_ALL_PREV_DX_ACTIVE;
	}
	public void setON_ARV_ALL_PREV_DX_ACTIVE(String oN_ARV_ALL_PREV_DX_ACTIVE) {
		ON_ARV_ALL_PREV_DX_ACTIVE = oN_ARV_ALL_PREV_DX_ACTIVE;
	}
	public String getON_ARV_EST_ACTIVE() {
		return ON_ARV_EST_ACTIVE;
	}
	public void setON_ARV_EST_ACTIVE(String oN_ARV_EST_ACTIVE) {
		ON_ARV_EST_ACTIVE = oN_ARV_EST_ACTIVE;
	}
	public String getON_ARV_NEWLY_DX() {
		return ON_ARV_NEWLY_DX;
	}
	public void setON_ARV_NEWLY_DX(String oN_ARV_NEWLY_DX) {
		ON_ARV_NEWLY_DX = oN_ARV_NEWLY_DX;
	}
	public String getON_ARV_OPEN_PTS() {
		return ON_ARV_OPEN_PTS;
	}
	public void setON_ARV_OPEN_PTS(String oN_ARV_OPEN_PTS) {
		ON_ARV_OPEN_PTS = oN_ARV_OPEN_PTS;
	}
	public String getON_ARV_OTHER_NEW_TO_CARE() {
		return ON_ARV_OTHER_NEW_TO_CARE;
	}
	public void setON_ARV_OTHER_NEW_TO_CARE(String oN_ARV_OTHER_NEW_TO_CARE) {
		ON_ARV_OTHER_NEW_TO_CARE = oN_ARV_OTHER_NEW_TO_CARE;
	}
	public String getFIRST_SUPPRESSED_DATE_NEWLY_DX() {
		return FIRST_SUPPRESSED_DATE_NEWLY_DX;
	}
	public void setFIRST_SUPPRESSED_DATE_NEWLY_DX(String fIRST_SUPPRESSED_DATE_NEWLY_DX) {
		FIRST_SUPPRESSED_DATE_NEWLY_DX = fIRST_SUPPRESSED_DATE_NEWLY_DX;
	}
	public String getEVER_SUPPRESSED_NEWLY_DX() {
		return EVER_SUPPRESSED_NEWLY_DX;
	}
	public void setEVER_SUPPRESSED_NEWLY_DX(String eVER_SUPPRESSED_NEWLY_DX) {
		EVER_SUPPRESSED_NEWLY_DX = eVER_SUPPRESSED_NEWLY_DX;
	}
	public String getSUPPRESSED_EVER_REVIEW_YEAR() {
		return SUPPRESSED_EVER_REVIEW_YEAR;
	}
	public void setSUPPRESSED_EVER_REVIEW_YEAR(String sUPPRESSED_EVER_REVIEW_YEAR) {
		SUPPRESSED_EVER_REVIEW_YEAR = sUPPRESSED_EVER_REVIEW_YEAR;
	}
	public String getSUPPRESSED_FINAL_REVIEW_YEAR() {
		return SUPPRESSED_FINAL_REVIEW_YEAR;
	}
	public void setSUPPRESSED_FINAL_REVIEW_YEAR(String sUPPRESSED_FINAL_REVIEW_YEAR) {
		SUPPRESSED_FINAL_REVIEW_YEAR = sUPPRESSED_FINAL_REVIEW_YEAR;
	}
	public String getSUPPRESSION_91DAYS_FROM_CARE() {
		return SUPPRESSION_91DAYS_FROM_CARE;
	}
	public void setSUPPRESSION_91DAYS_FROM_CARE(String sUPPRESSION_91DAYS_FROM_CARE) {
		SUPPRESSION_91DAYS_FROM_CARE = sUPPRESSION_91DAYS_FROM_CARE;
	}
	public String getSUPPRESSION_91DAYS_FROM_DIS() {
		return SUPPRESSION_91DAYS_FROM_DIS;
	}
	public void setSUPPRESSION_91DAYS_FROM_DIS(String sUPPRESSION_91DAYS_FROM_DIS) {
		SUPPRESSION_91DAYS_FROM_DIS = sUPPRESSION_91DAYS_FROM_DIS;
	}
	public String getSUPPRESSION_DAYS() {
		return SUPPRESSION_DAYS;
	}
	public void setSUPPRESSION_DAYS(String sUPPRESSION_DAYS) {
		SUPPRESSION_DAYS = sUPPRESSION_DAYS;
	}
	public String getSUPPRESSION_DAYS_DISCHARGE() {
		return SUPPRESSION_DAYS_DISCHARGE;
	}
	public void setSUPPRESSION_DAYS_DISCHARGE(String sUPPRESSION_DAYS_DISCHARGE) {
		SUPPRESSION_DAYS_DISCHARGE = sUPPRESSION_DAYS_DISCHARGE;
	}
	public String getSUPPRESSION_DAYS_HIV_CARE() {
		return SUPPRESSION_DAYS_HIV_CARE;
	}
	public void setSUPPRESSION_DAYS_HIV_CARE(String sUPPRESSION_DAYS_HIV_CARE) {
		SUPPRESSION_DAYS_HIV_CARE = sUPPRESSION_DAYS_HIV_CARE;
	}
	public String getFIRST_VL_DATE_NEWLY_DX() {
		return FIRST_VL_DATE_NEWLY_DX;
	}
	public void setFIRST_VL_DATE_NEWLY_DX(String fIRST_VL_DATE_NEWLY_DX) {
		FIRST_VL_DATE_NEWLY_DX = fIRST_VL_DATE_NEWLY_DX;
	}
	public String getSUPPRESSED_FINAL_VL() {
		return SUPPRESSED_FINAL_VL;
	}
	public void setSUPPRESSED_FINAL_VL(String sUPPRESSED_FINAL_VL) {
		SUPPRESSED_FINAL_VL = sUPPRESSED_FINAL_VL;
	}
	public String getVL_REVIEWED() {
		return VL_REVIEWED;
	}
	public void setVL_REVIEWED(String vL_REVIEWED) {
		VL_REVIEWED = vL_REVIEWED;
	}
	public String getVL_SUPPRESSED() {
		return VL_SUPPRESSED;
	}
	public void setVL_SUPPRESSED(String vL_SUPPRESSED) {
		VL_SUPPRESSED = vL_SUPPRESSED;
	}
	public String getVL_TEST_REVIEW_YEAR() {
		return VL_TEST_REVIEW_YEAR;
	}
	public void setVL_TEST_REVIEW_YEAR(String vL_TEST_REVIEW_YEAR) {
		VL_TEST_REVIEW_YEAR = vL_TEST_REVIEW_YEAR;
	}
	public String getVL_SUPPRESSION_ALL_PREV_DX_ACT() {
		return VL_SUPPRESSION_ALL_PREV_DX_ACT;
	}
	public void setVL_SUPPRESSION_ALL_PREV_DX_ACT(String vL_SUPPRESSION_ALL_PREV_DX_ACT) {
		VL_SUPPRESSION_ALL_PREV_DX_ACT = vL_SUPPRESSION_ALL_PREV_DX_ACT;
	}
	public String getVL_SUPPRESSION_EST_ACTIVE() {
		return VL_SUPPRESSION_EST_ACTIVE;
	}
	public void setVL_SUPPRESSION_EST_ACTIVE(String vL_SUPPRESSION_EST_ACTIVE) {
		VL_SUPPRESSION_EST_ACTIVE = vL_SUPPRESSION_EST_ACTIVE;
	}
	public String getVL_SUPPRESSION_NEWLY_DX() {
		return VL_SUPPRESSION_NEWLY_DX;
	}
	public void setVL_SUPPRESSION_NEWLY_DX(String vL_SUPPRESSION_NEWLY_DX) {
		VL_SUPPRESSION_NEWLY_DX = vL_SUPPRESSION_NEWLY_DX;
	}
	public String getVL_SUPPRESSION_OPEN_PTS() {
		return VL_SUPPRESSION_OPEN_PTS;
	}
	public void setVL_SUPPRESSION_OPEN_PTS(String vL_SUPPRESSION_OPEN_PTS) {
		VL_SUPPRESSION_OPEN_PTS = vL_SUPPRESSION_OPEN_PTS;
	}
	public String getVL_SUPPRESSION_OTHER_NEW_TO_CARE() {
		return VL_SUPPRESSION_OTHER_NEW_TO_CARE;
	}
	public void setVL_SUPPRESSION_OTHER_NEW_TO_CARE(String vL_SUPPRESSION_OTHER_NEW_TO_CARE) {
		VL_SUPPRESSION_OTHER_NEW_TO_CARE = vL_SUPPRESSION_OTHER_NEW_TO_CARE;
	}
	public String getVL_TESTED() {
		return VL_TESTED;
	}
	public void setVL_TESTED(String vL_TESTED) {
		VL_TESTED = vL_TESTED;
	}
	public String getVL_TESTING_ALL_PREV_DX_ACTIVE() {
		return VL_TESTING_ALL_PREV_DX_ACTIVE;
	}
	public void setVL_TESTING_ALL_PREV_DX_ACTIVE(String vL_TESTING_ALL_PREV_DX_ACTIVE) {
		VL_TESTING_ALL_PREV_DX_ACTIVE = vL_TESTING_ALL_PREV_DX_ACTIVE;
	}
	public String getVL_TESTING_EST_ACTIVE() {
		return VL_TESTING_EST_ACTIVE;
	}
	public void setVL_TESTING_EST_ACTIVE(String vL_TESTING_EST_ACTIVE) {
		VL_TESTING_EST_ACTIVE = vL_TESTING_EST_ACTIVE;
	}
	public String getVL_TESTING_NEWLY_DX() {
		return VL_TESTING_NEWLY_DX;
	}
	public void setVL_TESTING_NEWLY_DX(String vL_TESTING_NEWLY_DX) {
		VL_TESTING_NEWLY_DX = vL_TESTING_NEWLY_DX;
	}
	public String getVL_TESTING_OPEN_PTS() {
		return VL_TESTING_OPEN_PTS;
	}
	public void setVL_TESTING_OPEN_PTS(String vL_TESTING_OPEN_PTS) {
		VL_TESTING_OPEN_PTS = vL_TESTING_OPEN_PTS;
	}
	public String getVL_TESTING_OTHER_NEW_TO_CARE() {
		return VL_TESTING_OTHER_NEW_TO_CARE;
	}
	public void setVL_TESTING_OTHER_NEW_TO_CARE(String vL_TESTING_OTHER_NEW_TO_CARE) {
		VL_TESTING_OTHER_NEW_TO_CARE = vL_TESTING_OTHER_NEW_TO_CARE;
	}
	public String getFACILITY_STATE() {
		return FACILITY_STATE;
	}
	public void setFACILITY_STATE(String fACILITY_STATE) {
		FACILITY_STATE = fACILITY_STATE;
	}
	public String getFACILITY_COUNTY() {
		return FACILITY_COUNTY;
	}
	public void setFACILITY_COUNTY(String fACILITY_COUNTY) {
		FACILITY_COUNTY = fACILITY_COUNTY;
	}
	public String getSTATENO() {
		return STATENO;
	}
	public void setSTATENO(String sTATENO) {
		STATENO = sTATENO;
	}
	public String getRAWFACILITYADDRESS() {
		return RAWFACILITYADDRESS;
	}
	public void setRAWFACILITYADDRESS(String rAWFACILITYADDRESS) {
		RAWFACILITYADDRESS = rAWFACILITYADDRESS;
	}
}
