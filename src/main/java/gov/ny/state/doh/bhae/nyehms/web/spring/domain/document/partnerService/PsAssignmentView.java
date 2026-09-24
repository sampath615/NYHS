package gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.partnerService;

import java.sql.Date;
import java.util.List;

/**
 * This is POJO for legacy_ps_assignment_vw
 *
 * @author vxp06
 */
public class PsAssignmentView {

	private long DOCUMENT_ID;
	private String STATENO;
	private String LEGACY_REPORT_ID;
	private int LEGACY_ASSIGNMENT_ID;
	private String EVENT_DESC;
	private String FNAME;
	private String MNAME;
	private String LNAME;
	private String DOB_TEXT;
	private String SSN;
	private String DOD_TEXT;
	private String WORK_PHONE;
	private String CELL_PHONE;
	private String PAGER;
	private String WEB_ADDRESS_SCREEN;
	private String EMAIL;
	private String EMERGENCY_NAME;
	private String EMERGENCY_PHONE;
	private String EMERGENCY_RELATIONSHIP;
	private String LIVING_WITH;
	private String RESIDENCE_TYPE;
	private String TIME_IN_ADDRESS;
	private String ADDRESS_TIME_UNIT;
	private String TIME_IN_STATE;
	private String STATE_TIME_UNIT;
	private String TIME_IN_COUNTRY;
	private String COUNTRY_TIME_UNIT;
	private String CURRENTLY_INSTITUTE_ANS;
	private String INSTITUTE_NAME;
	private String INSTITUTE_CD;
	private Date DEMO_COLLECTION_DATE;
	private String MARITAL_STATUS;
	private String CTR_AIRS_ID;
	private String ENGLISH_SPEAKING;
	private String PRIMARY_LANGUAGE;
	private String EXPANDED_TEST_SITE;
	private String EXPANDED_TEST_SITE_AGENCY;
	private String PREGNANT_AT_EXAM;
	private String NUM_WKS_PREG_AT_EXAM;
	private String PREG_AT_INTERVIEW;
	private String NUM_WKS_PREG_AT_NUMBER;
	private String PREG_IN_LAST_YR;
	private String CURR_PRENATAL_CARE;
	private String PREGNANCY_OUTCOME;
	private String METHOD_CASE_DETECTION;
	private String METHOD_CASE_DETECTION_OTHER;
	private String WHY_NOT_INTERVIEWED;
	private String INTERVIEWED_OTHER;
	private String INTERVIEWED_PERIOD_MO;
	private String INTERVIEWED_LOCATION;
	private String INTERVIEWED_LOCATION_OTHER;
	private Date INTERVIEW_DATE;
	private String DIS_ASSIGNED;
	private Date INTERVIEWED_ASSIGNED_DATE;
	private String DIS_CONDUCTED;
	private Date INTERVIEWED_REASSIGN_DATE;
	private String DIS_REASSIGNED;
	private Date INTERVIEWED_DATE;
	private String DIS_CONDUCT_INTERVIEW;
	private String ENROLLMENT;
	private String SWM;
	private String SWF;
	private String SEX_W_TRANS;
	private String SEX_W_ANONYM;
	private String SEX_W_NUMBEROX;
	private String EXCHG_DRUGS_FOR_SEX;
	private String SEX_PARTNER_MSM;
	private String SEX_PARTNER_IDU;
	private String SEX_OTHER;
	private String INCARCERATED;
	private String SHARED_DRUG_EQUIP;
	private String IDU;
	private String CRACK;
	private String COCAINE;
	private String HEROIN;
	private String METH;
	private String NITRATES;
	private String ED_MEDS;
	private String NONE;
	private String DRUGS_OTHER;
	private String RISK_OTHER_TEXT;
	private Date RISK_COLLECTED_DATE;
	private String PLACES_MET_GIVEN;
	private String SEX_PLACES_GIVEN;
	private String PLACE_MET1;
	private String PLACE_MET_NAME1;
	private String PLACE_MET2;
	private String PLACE_MET_NAME2;
	private String PLACE_MET3;
	private String PLACE_MET_NAME3;
	private String PLACE_MET4;
	private String PLACE_MET_NAME4;
	private String PLACE_MET5;
	private String PLACE_MET_NAME5;
	private String PLACE_SEX1;
	private String PLACE_SEX_NAME1;
	private String PLACE_SEX2;
	private String PLACE_SEX_NAME2;
	private String PLACE_SEX3;
	private String PLACE_SEX_NAME3;
	private String PLACE_SEX4;
	private String PLACE_SEX_NAME4;
	private String PLACE_SEX5;
	private String PLACE_SEX_NAME5;
	private String CLAIMED_FEM_PART;
	private String CLAIMED_NO_FEM_PART;
	private String CLAIMED_MALE_PART;
	private String CLAIMED_NO_MALE_PART;
	private String CLAIMED_TRANS_PART;
	private String CLAIMED_NO_TRANS_PART;
	private String NAMED_FEM_PART;
	private String NAMED_NO_FEM_PART;
	private String NAMED_MALE_PART;
	private String NAMED_NO_MALE_PART;
	private String NAMED_TRANS_PART;
	private String NAMED_NO_TRANS_PART;
	private String SOCIAL_HIST_COMMENTS;
	private String NUMBER_SEX_PARTNERS;
	private String NUMBER_NS_PARTNERS;
	private String NUMBER_BOTH;
	private String PREVIOUS_TEST_RESULTS;
	private String CURRENT_TEST_DATE_TEXT;
	private String SIGNATURE;
	private String INFORMED_DIAG;
	private String INFORMED_DIAG_DATE_TEXT;
	private String PREVIOUS_TEST_DATE_TEXT;
	private String APPT_SCHEDULED;
	private Date APPT_DATE;
	private String APPT_DATE_UNK_REF;
	private String APPT_ATTENDED;
	private String INV_METHD_CD;
	private String SIGNATURE_DATE_TEXT;
	private Date SUPERVISOR_CLOSE_DATE;
	private Date WORKER_CLOSE_DATE;
	private Date SYSTEM_CLOSE_DATE;
	private Date INDEXCASE_CONTACT_DATE;
	private Date LETTER_SENT_DATE;
	private Date PROVIDER_CONTACT_DATE;
	private String PROVIDER_CONTACT;
	private String INVESTIGATION_STATUS_CD;
	private String PROVIDER_CONTACT_UID;
	private String INDEXCASE_CONTACT_UID;
	private String INFO_SOURCE;
	private String INFO_SOURCE_OTHER;
	private String AGE;
	private String PNAP_REQUESTED;
	private String WORKER_COMMENTS;
	private String SUPERVISOR_COMMENTS;
	private Date CREATE_DATE;
	private String INV_DUP_STATENO;
	private String PROV_COUNTY;
	private String PROV_ADDRESS;
	private String PROV_CITY;
	private String PROV_ZIP;
	private String PROV_NAME;
	private String PERSON_COUNTY;
	private String PERSON_ADDRESS;
	private String PERSON_CITY;
	private String PERSON_STATE;
	private String PERSON_ZIP;
	private Date REPORT_RECEIVED_DATE;
	private String TRIGGER_DOC_TYPE;
	private String LAST_WORKER_ASSIGNED_NAME;
	private Date LAST_WORKER_ASSIGNED_DATE;
	private String LAST_WORKER_ASSIGNED_REGION;
	private String LAST_SUPERV_ASSIGNED_NAME;
	private Date LAST_SUPERV_ASSIGNED_DATE;
	private String LAST_SUPERV_ASSIGNED_REGION;
	private String TRACING_DISPO;
	private String TRACING_DISPO_OTHER;
	private String MED_CHART_REVIEW;
	private String INTERVIEWED;
	private String CHLAMYDIA;
	private String GONORRHEA;
	private String SYPHILIS;
	private String OTHER_STD;
	private String SPECIFIED_STD;
	private String NUM_NON_MED_REFERRALS;
	private String REFERRAL_NAME;
	private String REFERRAL_ADDRESS;
	private String REFERRAL_CITY;
	private String REFERRAL_STATE;
	private String REFERRAL_ZIP;
	private String REFERRAL_PHONE;
	private String MED_APPT_SCHEDULED_DATE;
	private String MED_APPT_ATTENDED_DATE;
	private String MED_APPT_VERIFIED_DATE;
	private String REASON_MED_APPT_NOT_ATTENDED;
	private String SPECIFIED_REASON;
	private String LINKAGE_DISPO;
	private String FELT_HEALTHY;
	private String FELT_DEPRESSED;
	private String RESPONSIBILITIES;
	private String THINKS_NOT_HIV_POS;
	private String FAMILY_COMMITMENTS;
	private String SUBSTANCE_USE;
	private String NO_CHILD_CARE;
	private String LACK_STABLE_HOUSING;
	private String LANGUAGE_BARRIER;
	private String WORK_OBLIGATION;
	private String NO_MED_INSURANCE;
	private String DONT_TRUST_HEALTH_WRKR;
	private String ADVERSE_EFFECT_HIV_MEDS;
	private String INCONVENIENT_APPT_TIMES;
	private String FORGOT_APPT;
	private String DIDNT_FEEL_LIKE_GOING_APPT;
	private String TOO_ILL;
	private String NOT_WITH_PREFERRED_PROV;
	private String LACK_TRANSPORTATION;
	private String OTHER_REASON_OOC;
	private String SPECIFY_REASON_OOC;
	private String PREVENTION_COUNSELING;
	private String RISK_ASSESSMENT;
	private String MALE_CONDOMS;
	private String FEMALE_CONDOMS;
	private String NONMED_APPTS_MADE;
	private String CASE_MANAGEMENT;
	private String NUTRITION;
	private String TRANSPORTATION;
	private String DENTAL_CARE;
	private String HOUSING;
	private String MENTAL_HEALTH;
	private String ALCOHOL_SUBSTANCE_USE;
	private String LEGAL;
	private String EDUCATION_EMPLOYMENT;
	private String FINANCIAL_ENTITLEMENTS;
	private String FAMILY_PARENTING;
	private String SYRINGE_EXCHANGE_ESAP;
	private String POSITIVE_PATHWAYS;
	private String INTERVIEWED_IX;
	
	private List<Integer> partnerList;

	public long getDOCUMENT_ID() {
		return DOCUMENT_ID;
	}

	public void setDOCUMENT_ID(long dOCUMENT_ID) {
		DOCUMENT_ID = dOCUMENT_ID;
	}

	public String getSTATENO() {
		return STATENO;
	}

	public void setSTATENO(String sTATENO) {
		STATENO = sTATENO;
	}

	public String getLEGACY_REPORT_ID() {
		return LEGACY_REPORT_ID;
	}

	public void setLEGACY_REPORT_ID(String lEGACY_REPORT_ID) {
		LEGACY_REPORT_ID = lEGACY_REPORT_ID;
	}

	public int getLEGACY_ASSIGNMENT_ID() {
		return LEGACY_ASSIGNMENT_ID;
	}

	public void setLEGACY_ASSIGNMENT_ID(int lEGACY_ASSIGNMENT_ID) {
		LEGACY_ASSIGNMENT_ID = lEGACY_ASSIGNMENT_ID;
	}

	public String getEVENT_DESC() {
		return EVENT_DESC;
	}

	public void setEVENT_DESC(String eVENT_DESC) {
		EVENT_DESC = eVENT_DESC;
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

	public String getWORK_PHONE() {
		return WORK_PHONE;
	}

	public void setWORK_PHONE(String wORK_PHONE) {
		WORK_PHONE = wORK_PHONE;
	}

	public String getCELL_PHONE() {
		return CELL_PHONE;
	}

	public void setCELL_PHONE(String cELL_PHONE) {
		CELL_PHONE = cELL_PHONE;
	}

	public String getPAGER() {
		return PAGER;
	}

	public void setPAGER(String pAGER) {
		PAGER = pAGER;
	}

	public String getWEB_ADDRESS_SCREEN() {
		return WEB_ADDRESS_SCREEN;
	}

	public void setWEB_ADDRESS_SCREEN(String wEB_ADDRESS_SCREEN) {
		WEB_ADDRESS_SCREEN = wEB_ADDRESS_SCREEN;
	}

	public String getEMAIL() {
		return EMAIL;
	}

	public void setEMAIL(String eMAIL) {
		EMAIL = eMAIL;
	}

	public String getEMERGENCY_NAME() {
		return EMERGENCY_NAME;
	}

	public void setEMERGENCY_NAME(String eMERGENCY_NAME) {
		EMERGENCY_NAME = eMERGENCY_NAME;
	}

	public String getEMERGENCY_PHONE() {
		return EMERGENCY_PHONE;
	}

	public void setEMERGENCY_PHONE(String eMERGENCY_PHONE) {
		EMERGENCY_PHONE = eMERGENCY_PHONE;
	}

	public String getEMERGENCY_RELATIONSHIP() {
		return EMERGENCY_RELATIONSHIP;
	}

	public void setEMERGENCY_RELATIONSHIP(String eMERGENCY_RELATIONSHIP) {
		EMERGENCY_RELATIONSHIP = eMERGENCY_RELATIONSHIP;
	}

	public String getLIVING_WITH() {
		return LIVING_WITH;
	}

	public void setLIVING_WITH(String lIVING_WITH) {
		LIVING_WITH = lIVING_WITH;
	}

	public String getRESIDENCE_TYPE() {
		return RESIDENCE_TYPE;
	}

	public void setRESIDENCE_TYPE(String rESIDENCE_TYPE) {
		RESIDENCE_TYPE = rESIDENCE_TYPE;
	}

	public String getTIME_IN_ADDRESS() {
		return TIME_IN_ADDRESS;
	}

	public void setTIME_IN_ADDRESS(String tIME_IN_ADDRESS) {
		TIME_IN_ADDRESS = tIME_IN_ADDRESS;
	}

	public String getADDRESS_TIME_UNIT() {
		return ADDRESS_TIME_UNIT;
	}

	public void setADDRESS_TIME_UNIT(String aDDRESS_TIME_UNIT) {
		ADDRESS_TIME_UNIT = aDDRESS_TIME_UNIT;
	}

	public String getTIME_IN_STATE() {
		return TIME_IN_STATE;
	}

	public void setTIME_IN_STATE(String tIME_IN_STATE) {
		TIME_IN_STATE = tIME_IN_STATE;
	}

	public String getSTATE_TIME_UNIT() {
		return STATE_TIME_UNIT;
	}

	public void setSTATE_TIME_UNIT(String sTATE_TIME_UNIT) {
		STATE_TIME_UNIT = sTATE_TIME_UNIT;
	}

	public String getTIME_IN_COUNTRY() {
		return TIME_IN_COUNTRY;
	}

	public void setTIME_IN_COUNTRY(String tIME_IN_COUNTRY) {
		TIME_IN_COUNTRY = tIME_IN_COUNTRY;
	}

	public String getCOUNTRY_TIME_UNIT() {
		return COUNTRY_TIME_UNIT;
	}

	public void setCOUNTRY_TIME_UNIT(String cOUNTRY_TIME_UNIT) {
		COUNTRY_TIME_UNIT = cOUNTRY_TIME_UNIT;
	}

	public String getCURRENTLY_INSTITUTE_ANS() {
		return CURRENTLY_INSTITUTE_ANS;
	}

	public void setCURRENTLY_INSTITUTE_ANS(String cURRENTLY_INSTITUTE_ANS) {
		CURRENTLY_INSTITUTE_ANS = cURRENTLY_INSTITUTE_ANS;
	}

	public String getINSTITUTE_NAME() {
		return INSTITUTE_NAME;
	}

	public void setINSTITUTE_NAME(String iNSTITUTE_NAME) {
		INSTITUTE_NAME = iNSTITUTE_NAME;
	}

	public String getINSTITUTE_CD() {
		return INSTITUTE_CD;
	}

	public void setINSTITUTE_CD(String iNSTITUTE_CD) {
		INSTITUTE_CD = iNSTITUTE_CD;
	}

	public Date getDEMO_COLLECTION_DATE() {
		return DEMO_COLLECTION_DATE;
	}

	public void setDEMO_COLLECTION_DATE(Date dEMO_COLLECTION_DATE) {
		DEMO_COLLECTION_DATE = dEMO_COLLECTION_DATE;
	}

	public String getMARITAL_STATUS() {
		return MARITAL_STATUS;
	}

	public void setMARITAL_STATUS(String mARITAL_STATUS) {
		MARITAL_STATUS = mARITAL_STATUS;
	}

	public String getCTR_AIRS_ID() {
		return CTR_AIRS_ID;
	}

	public void setCTR_AIRS_ID(String cTR_AIRS_ID) {
		CTR_AIRS_ID = cTR_AIRS_ID;
	}

	public String getENGLISH_SPEAKING() {
		return ENGLISH_SPEAKING;
	}

	public void setENGLISH_SPEAKING(String eNGLISH_SPEAKING) {
		ENGLISH_SPEAKING = eNGLISH_SPEAKING;
	}

	public String getPRIMARY_LANGUAGE() {
		return PRIMARY_LANGUAGE;
	}

	public void setPRIMARY_LANGUAGE(String pRIMARY_LANGUAGE) {
		PRIMARY_LANGUAGE = pRIMARY_LANGUAGE;
	}

	public String getEXPANDED_TEST_SITE() {
		return EXPANDED_TEST_SITE;
	}

	public void setEXPANDED_TEST_SITE(String eXPANDED_TEST_SITE) {
		EXPANDED_TEST_SITE = eXPANDED_TEST_SITE;
	}

	public String getEXPANDED_TEST_SITE_AGENCY() {
		return EXPANDED_TEST_SITE_AGENCY;
	}

	public void setEXPANDED_TEST_SITE_AGENCY(String eXPANDED_TEST_SITE_AGENCY) {
		EXPANDED_TEST_SITE_AGENCY = eXPANDED_TEST_SITE_AGENCY;
	}

	public String getPREGNANT_AT_EXAM() {
		return PREGNANT_AT_EXAM;
	}

	public void setPREGNANT_AT_EXAM(String pREGNANT_AT_EXAM) {
		PREGNANT_AT_EXAM = pREGNANT_AT_EXAM;
	}

	public String getNUM_WKS_PREG_AT_EXAM() {
		return NUM_WKS_PREG_AT_EXAM;
	}

	public void setNUM_WKS_PREG_AT_EXAM(String nUM_WKS_PREG_AT_EXAM) {
		NUM_WKS_PREG_AT_EXAM = nUM_WKS_PREG_AT_EXAM;
	}

	public String getPREG_AT_INTERVIEW() {
		return PREG_AT_INTERVIEW;
	}

	public void setPREG_AT_INTERVIEW(String pREG_AT_INTERVIEW) {
		PREG_AT_INTERVIEW = pREG_AT_INTERVIEW;
	}

	public String getNUM_WKS_PREG_AT_NUMBER() {
		return NUM_WKS_PREG_AT_NUMBER;
	}

	public void setNUM_WKS_PREG_AT_NUMBER(String nUM_WKS_PREG_AT_NUMBER) {
		NUM_WKS_PREG_AT_NUMBER = nUM_WKS_PREG_AT_NUMBER;
	}

	public String getPREG_IN_LAST_YR() {
		return PREG_IN_LAST_YR;
	}

	public void setPREG_IN_LAST_YR(String pREG_IN_LAST_YR) {
		PREG_IN_LAST_YR = pREG_IN_LAST_YR;
	}

	public String getCURR_PRENATAL_CARE() {
		return CURR_PRENATAL_CARE;
	}

	public void setCURR_PRENATAL_CARE(String cURR_PRENATAL_CARE) {
		CURR_PRENATAL_CARE = cURR_PRENATAL_CARE;
	}

	public String getPREGNANCY_OUTCOME() {
		return PREGNANCY_OUTCOME;
	}

	public void setPREGNANCY_OUTCOME(String pREGNANCY_OUTCOME) {
		PREGNANCY_OUTCOME = pREGNANCY_OUTCOME;
	}

	public String getMETHOD_CASE_DETECTION() {
		return METHOD_CASE_DETECTION;
	}

	public void setMETHOD_CASE_DETECTION(String mETHOD_CASE_DETECTION) {
		METHOD_CASE_DETECTION = mETHOD_CASE_DETECTION;
	}

	public String getMETHOD_CASE_DETECTION_OTHER() {
		return METHOD_CASE_DETECTION_OTHER;
	}

	public void setMETHOD_CASE_DETECTION_OTHER(String mETHOD_CASE_DETECTION_OTHER) {
		METHOD_CASE_DETECTION_OTHER = mETHOD_CASE_DETECTION_OTHER;
	}

	public String getWHY_NOT_INTERVIEWED() {
		return WHY_NOT_INTERVIEWED;
	}

	public void setWHY_NOT_INTERVIEWED(String wHY_NOT_INTERVIEWED) {
		WHY_NOT_INTERVIEWED = wHY_NOT_INTERVIEWED;
	}

	public String getINTERVIEWED_OTHER() {
		return INTERVIEWED_OTHER;
	}

	public void setINTERVIEWED_OTHER(String iNTERVIEWED_OTHER) {
		INTERVIEWED_OTHER = iNTERVIEWED_OTHER;
	}

	public String getINTERVIEWED_PERIOD_MO() {
		return INTERVIEWED_PERIOD_MO;
	}

	public void setINTERVIEWED_PERIOD_MO(String iNTERVIEWED_PERIOD_MO) {
		INTERVIEWED_PERIOD_MO = iNTERVIEWED_PERIOD_MO;
	}

	public String getINTERVIEWED_LOCATION() {
		return INTERVIEWED_LOCATION;
	}

	public void setINTERVIEWED_LOCATION(String iNTERVIEWED_LOCATION) {
		INTERVIEWED_LOCATION = iNTERVIEWED_LOCATION;
	}

	public String getINTERVIEWED_LOCATION_OTHER() {
		return INTERVIEWED_LOCATION_OTHER;
	}

	public void setINTERVIEWED_LOCATION_OTHER(String iNTERVIEWED_LOCATION_OTHER) {
		INTERVIEWED_LOCATION_OTHER = iNTERVIEWED_LOCATION_OTHER;
	}

	public Date getINTERVIEW_DATE() {
		return INTERVIEW_DATE;
	}

	public void setINTERVIEW_DATE(Date iNTERVIEW_DATE) {
		INTERVIEW_DATE = iNTERVIEW_DATE;
	}

	public String getDIS_ASSIGNED() {
		return DIS_ASSIGNED;
	}

	public void setDIS_ASSIGNED(String dIS_ASSIGNED) {
		DIS_ASSIGNED = dIS_ASSIGNED;
	}

	public Date getINTERVIEWED_ASSIGNED_DATE() {
		return INTERVIEWED_ASSIGNED_DATE;
	}

	public void setINTERVIEWED_ASSIGNED_DATE(Date iNTERVIEWED_ASSIGNED_DATE) {
		INTERVIEWED_ASSIGNED_DATE = iNTERVIEWED_ASSIGNED_DATE;
	}

	public String getDIS_CONDUCTED() {
		return DIS_CONDUCTED;
	}

	public void setDIS_CONDUCTED(String dIS_CONDUCTED) {
		DIS_CONDUCTED = dIS_CONDUCTED;
	}

	public Date getINTERVIEWED_REASSIGN_DATE() {
		return INTERVIEWED_REASSIGN_DATE;
	}

	public void setINTERVIEWED_REASSIGN_DATE(Date iNTERVIEWED_REASSIGN_DATE) {
		INTERVIEWED_REASSIGN_DATE = iNTERVIEWED_REASSIGN_DATE;
	}

	public String getDIS_REASSIGNED() {
		return DIS_REASSIGNED;
	}

	public void setDIS_REASSIGNED(String dIS_REASSIGNED) {
		DIS_REASSIGNED = dIS_REASSIGNED;
	}

	public Date getINTERVIEWED_DATE() {
		return INTERVIEWED_DATE;
	}

	public void setINTERVIEWED_DATE(Date iNTERVIEWED_DATE) {
		INTERVIEWED_DATE = iNTERVIEWED_DATE;
	}

	public String getDIS_CONDUCT_INTERVIEW() {
		return DIS_CONDUCT_INTERVIEW;
	}

	public void setDIS_CONDUCT_INTERVIEW(String dIS_CONDUCT_INTERVIEW) {
		DIS_CONDUCT_INTERVIEW = dIS_CONDUCT_INTERVIEW;
	}

	public String getENROLLMENT() {
		return ENROLLMENT;
	}

	public void setENROLLMENT(String eNROLLMENT) {
		ENROLLMENT = eNROLLMENT;
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

	public String getSEX_W_TRANS() {
		return SEX_W_TRANS;
	}

	public void setSEX_W_TRANS(String sEX_W_TRANS) {
		SEX_W_TRANS = sEX_W_TRANS;
	}

	public String getSEX_W_ANONYM() {
		return SEX_W_ANONYM;
	}

	public void setSEX_W_ANONYM(String sEX_W_ANONYM) {
		SEX_W_ANONYM = sEX_W_ANONYM;
	}

	public String getSEX_W_NUMBEROX() {
		return SEX_W_NUMBEROX;
	}

	public void setSEX_W_NUMBEROX(String sEX_W_NUMBEROX) {
		SEX_W_NUMBEROX = sEX_W_NUMBEROX;
	}

	public String getEXCHG_DRUGS_FOR_SEX() {
		return EXCHG_DRUGS_FOR_SEX;
	}

	public void setEXCHG_DRUGS_FOR_SEX(String eXCHG_DRUGS_FOR_SEX) {
		EXCHG_DRUGS_FOR_SEX = eXCHG_DRUGS_FOR_SEX;
	}

	public String getSEX_PARTNER_MSM() {
		return SEX_PARTNER_MSM;
	}

	public void setSEX_PARTNER_MSM(String sEX_PARTNER_MSM) {
		SEX_PARTNER_MSM = sEX_PARTNER_MSM;
	}

	public String getSEX_PARTNER_IDU() {
		return SEX_PARTNER_IDU;
	}

	public void setSEX_PARTNER_IDU(String sEX_PARTNER_IDU) {
		SEX_PARTNER_IDU = sEX_PARTNER_IDU;
	}

	public String getSEX_OTHER() {
		return SEX_OTHER;
	}

	public void setSEX_OTHER(String sEX_OTHER) {
		SEX_OTHER = sEX_OTHER;
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

	public String getIDU() {
		return IDU;
	}

	public void setIDU(String iDU) {
		IDU = iDU;
	}

	public String getCRACK() {
		return CRACK;
	}

	public void setCRACK(String cRACK) {
		CRACK = cRACK;
	}

	public String getCOCAINE() {
		return COCAINE;
	}

	public void setCOCAINE(String cOCAINE) {
		COCAINE = cOCAINE;
	}

	public String getHEROIN() {
		return HEROIN;
	}

	public void setHEROIN(String hEROIN) {
		HEROIN = hEROIN;
	}

	public String getMETH() {
		return METH;
	}

	public void setMETH(String mETH) {
		METH = mETH;
	}

	public String getNITRATES() {
		return NITRATES;
	}

	public void setNITRATES(String nITRATES) {
		NITRATES = nITRATES;
	}

	public String getED_MEDS() {
		return ED_MEDS;
	}

	public void setED_MEDS(String eD_MEDS) {
		ED_MEDS = eD_MEDS;
	}

	public String getNONE() {
		return NONE;
	}

	public void setNONE(String nONE) {
		NONE = nONE;
	}

	public String getDRUGS_OTHER() {
		return DRUGS_OTHER;
	}

	public void setDRUGS_OTHER(String dRUGS_OTHER) {
		DRUGS_OTHER = dRUGS_OTHER;
	}

	public String getRISK_OTHER_TEXT() {
		return RISK_OTHER_TEXT;
	}

	public void setRISK_OTHER_TEXT(String rISK_OTHER_TEXT) {
		RISK_OTHER_TEXT = rISK_OTHER_TEXT;
	}

	public Date getRISK_COLLECTED_DATE() {
		return RISK_COLLECTED_DATE;
	}

	public void setRISK_COLLECTED_DATE(Date rISK_COLLECTED_DATE) {
		RISK_COLLECTED_DATE = rISK_COLLECTED_DATE;
	}

	public String getPLACES_MET_GIVEN() {
		return PLACES_MET_GIVEN;
	}

	public void setPLACES_MET_GIVEN(String pLACES_MET_GIVEN) {
		PLACES_MET_GIVEN = pLACES_MET_GIVEN;
	}

	public String getSEX_PLACES_GIVEN() {
		return SEX_PLACES_GIVEN;
	}

	public void setSEX_PLACES_GIVEN(String sEX_PLACES_GIVEN) {
		SEX_PLACES_GIVEN = sEX_PLACES_GIVEN;
	}

	public String getPLACE_MET1() {
		return PLACE_MET1;
	}

	public void setPLACE_MET1(String pLACE_MET1) {
		PLACE_MET1 = pLACE_MET1;
	}

	public String getPLACE_MET_NAME1() {
		return PLACE_MET_NAME1;
	}

	public void setPLACE_MET_NAME1(String pLACE_MET_NAME1) {
		PLACE_MET_NAME1 = pLACE_MET_NAME1;
	}

	public String getPLACE_MET2() {
		return PLACE_MET2;
	}

	public void setPLACE_MET2(String pLACE_MET2) {
		PLACE_MET2 = pLACE_MET2;
	}

	public String getPLACE_MET_NAME2() {
		return PLACE_MET_NAME2;
	}

	public void setPLACE_MET_NAME2(String pLACE_MET_NAME2) {
		PLACE_MET_NAME2 = pLACE_MET_NAME2;
	}

	public String getPLACE_MET3() {
		return PLACE_MET3;
	}

	public void setPLACE_MET3(String pLACE_MET3) {
		PLACE_MET3 = pLACE_MET3;
	}

	public String getPLACE_MET_NAME3() {
		return PLACE_MET_NAME3;
	}

	public void setPLACE_MET_NAME3(String pLACE_MET_NAME3) {
		PLACE_MET_NAME3 = pLACE_MET_NAME3;
	}

	public String getPLACE_MET4() {
		return PLACE_MET4;
	}

	public void setPLACE_MET4(String pLACE_MET4) {
		PLACE_MET4 = pLACE_MET4;
	}

	public String getPLACE_MET_NAME4() {
		return PLACE_MET_NAME4;
	}

	public void setPLACE_MET_NAME4(String pLACE_MET_NAME4) {
		PLACE_MET_NAME4 = pLACE_MET_NAME4;
	}

	public String getPLACE_MET5() {
		return PLACE_MET5;
	}

	public void setPLACE_MET5(String pLACE_MET5) {
		PLACE_MET5 = pLACE_MET5;
	}

	public String getPLACE_MET_NAME5() {
		return PLACE_MET_NAME5;
	}

	public void setPLACE_MET_NAME5(String pLACE_MET_NAME5) {
		PLACE_MET_NAME5 = pLACE_MET_NAME5;
	}

	public String getPLACE_SEX1() {
		return PLACE_SEX1;
	}

	public void setPLACE_SEX1(String pLACE_SEX1) {
		PLACE_SEX1 = pLACE_SEX1;
	}

	public String getPLACE_SEX_NAME1() {
		return PLACE_SEX_NAME1;
	}

	public void setPLACE_SEX_NAME1(String pLACE_SEX_NAME1) {
		PLACE_SEX_NAME1 = pLACE_SEX_NAME1;
	}

	public String getPLACE_SEX2() {
		return PLACE_SEX2;
	}

	public void setPLACE_SEX2(String pLACE_SEX2) {
		PLACE_SEX2 = pLACE_SEX2;
	}

	public String getPLACE_SEX_NAME2() {
		return PLACE_SEX_NAME2;
	}

	public void setPLACE_SEX_NAME2(String pLACE_SEX_NAME2) {
		PLACE_SEX_NAME2 = pLACE_SEX_NAME2;
	}

	public String getPLACE_SEX3() {
		return PLACE_SEX3;
	}

	public void setPLACE_SEX3(String pLACE_SEX3) {
		PLACE_SEX3 = pLACE_SEX3;
	}

	public String getPLACE_SEX_NAME3() {
		return PLACE_SEX_NAME3;
	}

	public void setPLACE_SEX_NAME3(String pLACE_SEX_NAME3) {
		PLACE_SEX_NAME3 = pLACE_SEX_NAME3;
	}

	public String getPLACE_SEX4() {
		return PLACE_SEX4;
	}

	public void setPLACE_SEX4(String pLACE_SEX4) {
		PLACE_SEX4 = pLACE_SEX4;
	}

	public String getPLACE_SEX_NAME4() {
		return PLACE_SEX_NAME4;
	}

	public void setPLACE_SEX_NAME4(String pLACE_SEX_NAME4) {
		PLACE_SEX_NAME4 = pLACE_SEX_NAME4;
	}

	public String getPLACE_SEX5() {
		return PLACE_SEX5;
	}

	public void setPLACE_SEX5(String pLACE_SEX5) {
		PLACE_SEX5 = pLACE_SEX5;
	}

	public String getPLACE_SEX_NAME5() {
		return PLACE_SEX_NAME5;
	}

	public void setPLACE_SEX_NAME5(String pLACE_SEX_NAME5) {
		PLACE_SEX_NAME5 = pLACE_SEX_NAME5;
	}

	public String getCLAIMED_FEM_PART() {
		return CLAIMED_FEM_PART;
	}

	public void setCLAIMED_FEM_PART(String cLAIMED_FEM_PART) {
		CLAIMED_FEM_PART = cLAIMED_FEM_PART;
	}

	public String getCLAIMED_NO_FEM_PART() {
		return CLAIMED_NO_FEM_PART;
	}

	public void setCLAIMED_NO_FEM_PART(String cLAIMED_NO_FEM_PART) {
		CLAIMED_NO_FEM_PART = cLAIMED_NO_FEM_PART;
	}

	public String getCLAIMED_MALE_PART() {
		return CLAIMED_MALE_PART;
	}

	public void setCLAIMED_MALE_PART(String cLAIMED_MALE_PART) {
		CLAIMED_MALE_PART = cLAIMED_MALE_PART;
	}

	public String getCLAIMED_NO_MALE_PART() {
		return CLAIMED_NO_MALE_PART;
	}

	public void setCLAIMED_NO_MALE_PART(String cLAIMED_NO_MALE_PART) {
		CLAIMED_NO_MALE_PART = cLAIMED_NO_MALE_PART;
	}

	public String getCLAIMED_TRANS_PART() {
		return CLAIMED_TRANS_PART;
	}

	public void setCLAIMED_TRANS_PART(String cLAIMED_TRANS_PART) {
		CLAIMED_TRANS_PART = cLAIMED_TRANS_PART;
	}

	public String getCLAIMED_NO_TRANS_PART() {
		return CLAIMED_NO_TRANS_PART;
	}

	public void setCLAIMED_NO_TRANS_PART(String cLAIMED_NO_TRANS_PART) {
		CLAIMED_NO_TRANS_PART = cLAIMED_NO_TRANS_PART;
	}

	public String getNAMED_FEM_PART() {
		return NAMED_FEM_PART;
	}

	public void setNAMED_FEM_PART(String nAMED_FEM_PART) {
		NAMED_FEM_PART = nAMED_FEM_PART;
	}

	public String getNAMED_NO_FEM_PART() {
		return NAMED_NO_FEM_PART;
	}

	public void setNAMED_NO_FEM_PART(String nAMED_NO_FEM_PART) {
		NAMED_NO_FEM_PART = nAMED_NO_FEM_PART;
	}

	public String getNAMED_MALE_PART() {
		return NAMED_MALE_PART;
	}

	public void setNAMED_MALE_PART(String nAMED_MALE_PART) {
		NAMED_MALE_PART = nAMED_MALE_PART;
	}

	public String getNAMED_NO_MALE_PART() {
		return NAMED_NO_MALE_PART;
	}

	public void setNAMED_NO_MALE_PART(String nAMED_NO_MALE_PART) {
		NAMED_NO_MALE_PART = nAMED_NO_MALE_PART;
	}

	public String getNAMED_TRANS_PART() {
		return NAMED_TRANS_PART;
	}

	public void setNAMED_TRANS_PART(String nAMED_TRANS_PART) {
		NAMED_TRANS_PART = nAMED_TRANS_PART;
	}

	public String getNAMED_NO_TRANS_PART() {
		return NAMED_NO_TRANS_PART;
	}

	public void setNAMED_NO_TRANS_PART(String nAMED_NO_TRANS_PART) {
		NAMED_NO_TRANS_PART = nAMED_NO_TRANS_PART;
	}

	public String getSOCIAL_HIST_COMMENTS() {
		return SOCIAL_HIST_COMMENTS;
	}

	public void setSOCIAL_HIST_COMMENTS(String sOCIAL_HIST_COMMENTS) {
		SOCIAL_HIST_COMMENTS = sOCIAL_HIST_COMMENTS;
	}

	public String getNUMBER_SEX_PARTNERS() {
		return NUMBER_SEX_PARTNERS;
	}

	public void setNUMBER_SEX_PARTNERS(String nUMBER_SEX_PARTNERS) {
		NUMBER_SEX_PARTNERS = nUMBER_SEX_PARTNERS;
	}

	public String getNUMBER_NS_PARTNERS() {
		return NUMBER_NS_PARTNERS;
	}

	public void setNUMBER_NS_PARTNERS(String nUMBER_NS_PARTNERS) {
		NUMBER_NS_PARTNERS = nUMBER_NS_PARTNERS;
	}

	public String getNUMBER_BOTH() {
		return NUMBER_BOTH;
	}

	public void setNUMBER_BOTH(String nUMBER_BOTH) {
		NUMBER_BOTH = nUMBER_BOTH;
	}

	public String getPREVIOUS_TEST_RESULTS() {
		return PREVIOUS_TEST_RESULTS;
	}

	public void setPREVIOUS_TEST_RESULTS(String pREVIOUS_TEST_RESULTS) {
		PREVIOUS_TEST_RESULTS = pREVIOUS_TEST_RESULTS;
	}

	public String getCURRENT_TEST_DATE_TEXT() {
		return CURRENT_TEST_DATE_TEXT;
	}

	public void setCURRENT_TEST_DATE_TEXT(String cURRENT_TEST_DATE_TEXT) {
		CURRENT_TEST_DATE_TEXT = cURRENT_TEST_DATE_TEXT;
	}

	public String getSIGNATURE() {
		return SIGNATURE;
	}

	public void setSIGNATURE(String sIGNATURE) {
		SIGNATURE = sIGNATURE;
	}

	public String getINFORMED_DIAG() {
		return INFORMED_DIAG;
	}

	public void setINFORMED_DIAG(String iNFORMED_DIAG) {
		INFORMED_DIAG = iNFORMED_DIAG;
	}

	public String getINFORMED_DIAG_DATE_TEXT() {
		return INFORMED_DIAG_DATE_TEXT;
	}

	public void setINFORMED_DIAG_DATE_TEXT(String iNFORMED_DIAG_DATE_TEXT) {
		INFORMED_DIAG_DATE_TEXT = iNFORMED_DIAG_DATE_TEXT;
	}

	public String getPREVIOUS_TEST_DATE_TEXT() {
		return PREVIOUS_TEST_DATE_TEXT;
	}

	public void setPREVIOUS_TEST_DATE_TEXT(String pREVIOUS_TEST_DATE_TEXT) {
		PREVIOUS_TEST_DATE_TEXT = pREVIOUS_TEST_DATE_TEXT;
	}

	public String getAPPT_SCHEDULED() {
		return APPT_SCHEDULED;
	}

	public void setAPPT_SCHEDULED(String aPPT_SCHEDULED) {
		APPT_SCHEDULED = aPPT_SCHEDULED;
	}

	public Date getAPPT_DATE() {
		return APPT_DATE;
	}

	public void setAPPT_DATE(Date aPPT_DATE) {
		APPT_DATE = aPPT_DATE;
	}

	public String getAPPT_DATE_UNK_REF() {
		return APPT_DATE_UNK_REF;
	}

	public void setAPPT_DATE_UNK_REF(String aPPT_DATE_UNK_REF) {
		APPT_DATE_UNK_REF = aPPT_DATE_UNK_REF;
	}

	public String getAPPT_ATTENDED() {
		return APPT_ATTENDED;
	}

	public void setAPPT_ATTENDED(String aPPT_ATTENDED) {
		APPT_ATTENDED = aPPT_ATTENDED;
	}

	public String getINV_METHD_CD() {
		return INV_METHD_CD;
	}

	public void setINV_METHD_CD(String iNV_METHD_CD) {
		INV_METHD_CD = iNV_METHD_CD;
	}

	public String getSIGNATURE_DATE_TEXT() {
		return SIGNATURE_DATE_TEXT;
	}

	public void setSIGNATURE_DATE_TEXT(String sIGNATURE_DATE_TEXT) {
		SIGNATURE_DATE_TEXT = sIGNATURE_DATE_TEXT;
	}

	public Date getSUPERVISOR_CLOSE_DATE() {
		return SUPERVISOR_CLOSE_DATE;
	}

	public void setSUPERVISOR_CLOSE_DATE(Date sUPERVISOR_CLOSE_DATE) {
		SUPERVISOR_CLOSE_DATE = sUPERVISOR_CLOSE_DATE;
	}

	public Date getWORKER_CLOSE_DATE() {
		return WORKER_CLOSE_DATE;
	}

	public void setWORKER_CLOSE_DATE(Date wORKER_CLOSE_DATE) {
		WORKER_CLOSE_DATE = wORKER_CLOSE_DATE;
	}

	public Date getSYSTEM_CLOSE_DATE() {
		return SYSTEM_CLOSE_DATE;
	}

	public void setSYSTEM_CLOSE_DATE(Date sYSTEM_CLOSE_DATE) {
		SYSTEM_CLOSE_DATE = sYSTEM_CLOSE_DATE;
	}

	public Date getINDEXCASE_CONTACT_DATE() {
		return INDEXCASE_CONTACT_DATE;
	}

	public void setINDEXCASE_CONTACT_DATE(Date iNDEXCASE_CONTACT_DATE) {
		INDEXCASE_CONTACT_DATE = iNDEXCASE_CONTACT_DATE;
	}

	public Date getLETTER_SENT_DATE() {
		return LETTER_SENT_DATE;
	}

	public void setLETTER_SENT_DATE(Date lETTER_SENT_DATE) {
		LETTER_SENT_DATE = lETTER_SENT_DATE;
	}

	public Date getPROVIDER_CONTACT_DATE() {
		return PROVIDER_CONTACT_DATE;
	}

	public void setPROVIDER_CONTACT_DATE(Date pROVIDER_CONTACT_DATE) {
		PROVIDER_CONTACT_DATE = pROVIDER_CONTACT_DATE;
	}

	public String getPROVIDER_CONTACT() {
		return PROVIDER_CONTACT;
	}

	public void setPROVIDER_CONTACT(String pROVIDER_CONTACT) {
		PROVIDER_CONTACT = pROVIDER_CONTACT;
	}

	public String getINVESTIGATION_STATUS_CD() {
		return INVESTIGATION_STATUS_CD;
	}

	public void setINVESTIGATION_STATUS_CD(String iNVESTIGATION_STATUS_CD) {
		INVESTIGATION_STATUS_CD = iNVESTIGATION_STATUS_CD;
	}

	public String getPROVIDER_CONTACT_UID() {
		return PROVIDER_CONTACT_UID;
	}

	public void setPROVIDER_CONTACT_UID(String pROVIDER_CONTACT_UID) {
		PROVIDER_CONTACT_UID = pROVIDER_CONTACT_UID;
	}

	public String getINDEXCASE_CONTACT_UID() {
		return INDEXCASE_CONTACT_UID;
	}

	public void setINDEXCASE_CONTACT_UID(String iNDEXCASE_CONTACT_UID) {
		INDEXCASE_CONTACT_UID = iNDEXCASE_CONTACT_UID;
	}

	public String getINFO_SOURCE() {
		return INFO_SOURCE;
	}

	public void setINFO_SOURCE(String iNFO_SOURCE) {
		INFO_SOURCE = iNFO_SOURCE;
	}

	public String getINFO_SOURCE_OTHER() {
		return INFO_SOURCE_OTHER;
	}

	public void setINFO_SOURCE_OTHER(String iNFO_SOURCE_OTHER) {
		INFO_SOURCE_OTHER = iNFO_SOURCE_OTHER;
	}

	public String getAGE() {
		return AGE;
	}

	public void setAGE(String aGE) {
		AGE = aGE;
	}

	public String getPNAP_REQUESTED() {
		return PNAP_REQUESTED;
	}

	public void setPNAP_REQUESTED(String pNAP_REQUESTED) {
		PNAP_REQUESTED = pNAP_REQUESTED;
	}

	public String getWORKER_COMMENTS() {
		return WORKER_COMMENTS;
	}

	public void setWORKER_COMMENTS(String wORKER_COMMENTS) {
		WORKER_COMMENTS = wORKER_COMMENTS;
	}

	public String getSUPERVISOR_COMMENTS() {
		return SUPERVISOR_COMMENTS;
	}

	public void setSUPERVISOR_COMMENTS(String sUPERVISOR_COMMENTS) {
		SUPERVISOR_COMMENTS = sUPERVISOR_COMMENTS;
	}

	public Date getCREATE_DATE() {
		return CREATE_DATE;
	}

	public void setCREATE_DATE(Date cREATE_DATE) {
		CREATE_DATE = cREATE_DATE;
	}

	public String getINV_DUP_STATENO() {
		return INV_DUP_STATENO;
	}

	public void setINV_DUP_STATENO(String iNV_DUP_STATENO) {
		INV_DUP_STATENO = iNV_DUP_STATENO;
	}

	public String getPROV_COUNTY() {
		return PROV_COUNTY;
	}

	public void setPROV_COUNTY(String pROV_COUNTY) {
		PROV_COUNTY = pROV_COUNTY;
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

	public String getPROV_ZIP() {
		return PROV_ZIP;
	}

	public void setPROV_ZIP(String pROV_ZIP) {
		PROV_ZIP = pROV_ZIP;
	}

	public String getPROV_NAME() {
		return PROV_NAME;
	}

	public void setPROV_NAME(String pROV_NAME) {
		PROV_NAME = pROV_NAME;
	}

	public String getPERSON_COUNTY() {
		return PERSON_COUNTY;
	}

	public void setPERSON_COUNTY(String pERSON_COUNTY) {
		PERSON_COUNTY = pERSON_COUNTY;
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

	public Date getREPORT_RECEIVED_DATE() {
		return REPORT_RECEIVED_DATE;
	}

	public void setREPORT_RECEIVED_DATE(Date rEPORT_RECEIVED_DATE) {
		REPORT_RECEIVED_DATE = rEPORT_RECEIVED_DATE;
	}

	public String getTRIGGER_DOC_TYPE() {
		return TRIGGER_DOC_TYPE;
	}

	public void setTRIGGER_DOC_TYPE(String tRIGGER_DOC_TYPE) {
		TRIGGER_DOC_TYPE = tRIGGER_DOC_TYPE;
	}

	public String getLAST_WORKER_ASSIGNED_NAME() {
		return LAST_WORKER_ASSIGNED_NAME;
	}

	public void setLAST_WORKER_ASSIGNED_NAME(String lAST_WORKER_ASSIGNED_NAME) {
		LAST_WORKER_ASSIGNED_NAME = lAST_WORKER_ASSIGNED_NAME;
	}

	public Date getLAST_WORKER_ASSIGNED_DATE() {
		return LAST_WORKER_ASSIGNED_DATE;
	}

	public void setLAST_WORKER_ASSIGNED_DATE(Date lAST_WORKER_ASSIGNED_DATE) {
		LAST_WORKER_ASSIGNED_DATE = lAST_WORKER_ASSIGNED_DATE;
	}

	public String getLAST_WORKER_ASSIGNED_REGION() {
		return LAST_WORKER_ASSIGNED_REGION;
	}

	public void setLAST_WORKER_ASSIGNED_REGION(String lAST_WORKER_ASSIGNED_REGION) {
		LAST_WORKER_ASSIGNED_REGION = lAST_WORKER_ASSIGNED_REGION;
	}

	public String getLAST_SUPERV_ASSIGNED_NAME() {
		return LAST_SUPERV_ASSIGNED_NAME;
	}

	public void setLAST_SUPERV_ASSIGNED_NAME(String lAST_SUPERV_ASSIGNED_NAME) {
		LAST_SUPERV_ASSIGNED_NAME = lAST_SUPERV_ASSIGNED_NAME;
	}

	public Date getLAST_SUPERV_ASSIGNED_DATE() {
		return LAST_SUPERV_ASSIGNED_DATE;
	}

	public void setLAST_SUPERV_ASSIGNED_DATE(Date lAST_SUPERV_ASSIGNED_DATE) {
		LAST_SUPERV_ASSIGNED_DATE = lAST_SUPERV_ASSIGNED_DATE;
	}

	public String getLAST_SUPERV_ASSIGNED_REGION() {
		return LAST_SUPERV_ASSIGNED_REGION;
	}

	public void setLAST_SUPERV_ASSIGNED_REGION(String lAST_SUPERV_ASSIGNED_REGION) {
		LAST_SUPERV_ASSIGNED_REGION = lAST_SUPERV_ASSIGNED_REGION;
	}

	public String getTRACING_DISPO() {
		return TRACING_DISPO;
	}

	public void setTRACING_DISPO(String tRACING_DISPO) {
		TRACING_DISPO = tRACING_DISPO;
	}

	public String getTRACING_DISPO_OTHER() {
		return TRACING_DISPO_OTHER;
	}

	public void setTRACING_DISPO_OTHER(String tRACING_DISPO_OTHER) {
		TRACING_DISPO_OTHER = tRACING_DISPO_OTHER;
	}

	public String getMED_CHART_REVIEW() {
		return MED_CHART_REVIEW;
	}

	public void setMED_CHART_REVIEW(String mED_CHART_REVIEW) {
		MED_CHART_REVIEW = mED_CHART_REVIEW;
	}

	public String getINTERVIEWED() {
		return INTERVIEWED;
	}

	public void setINTERVIEWED(String iNTERVIEWED) {
		INTERVIEWED = iNTERVIEWED;
	}

	public String getCHLAMYDIA() {
		return CHLAMYDIA;
	}

	public void setCHLAMYDIA(String cHLAMYDIA) {
		CHLAMYDIA = cHLAMYDIA;
	}

	public String getGONORRHEA() {
		return GONORRHEA;
	}

	public void setGONORRHEA(String gONORRHEA) {
		GONORRHEA = gONORRHEA;
	}

	public String getSYPHILIS() {
		return SYPHILIS;
	}

	public void setSYPHILIS(String sYPHILIS) {
		SYPHILIS = sYPHILIS;
	}

	public String getOTHER_STD() {
		return OTHER_STD;
	}

	public void setOTHER_STD(String oTHER_STD) {
		OTHER_STD = oTHER_STD;
	}

	public String getSPECIFIED_STD() {
		return SPECIFIED_STD;
	}

	public void setSPECIFIED_STD(String sPECIFIED_STD) {
		SPECIFIED_STD = sPECIFIED_STD;
	}

	public String getNUM_NON_MED_REFERRALS() {
		return NUM_NON_MED_REFERRALS;
	}

	public void setNUM_NON_MED_REFERRALS(String nUM_NON_MED_REFERRALS) {
		NUM_NON_MED_REFERRALS = nUM_NON_MED_REFERRALS;
	}

	public String getREFERRAL_NAME() {
		return REFERRAL_NAME;
	}

	public void setREFERRAL_NAME(String rEFERRAL_NAME) {
		REFERRAL_NAME = rEFERRAL_NAME;
	}

	public String getREFERRAL_ADDRESS() {
		return REFERRAL_ADDRESS;
	}

	public void setREFERRAL_ADDRESS(String rEFERRAL_ADDRESS) {
		REFERRAL_ADDRESS = rEFERRAL_ADDRESS;
	}

	public String getREFERRAL_CITY() {
		return REFERRAL_CITY;
	}

	public void setREFERRAL_CITY(String rEFERRAL_CITY) {
		REFERRAL_CITY = rEFERRAL_CITY;
	}

	public String getREFERRAL_STATE() {
		return REFERRAL_STATE;
	}

	public void setREFERRAL_STATE(String rEFERRAL_STATE) {
		REFERRAL_STATE = rEFERRAL_STATE;
	}

	public String getREFERRAL_ZIP() {
		return REFERRAL_ZIP;
	}

	public void setREFERRAL_ZIP(String rEFERRAL_ZIP) {
		REFERRAL_ZIP = rEFERRAL_ZIP;
	}

	public String getREFERRAL_PHONE() {
		return REFERRAL_PHONE;
	}

	public void setREFERRAL_PHONE(String rEFERRAL_PHONE) {
		REFERRAL_PHONE = rEFERRAL_PHONE;
	}

	public String getMED_APPT_SCHEDULED_DATE() {
		return MED_APPT_SCHEDULED_DATE;
	}

	public void setMED_APPT_SCHEDULED_DATE(String mED_APPT_SCHEDULED_DATE) {
		MED_APPT_SCHEDULED_DATE = mED_APPT_SCHEDULED_DATE;
	}

	public String getMED_APPT_ATTENDED_DATE() {
		return MED_APPT_ATTENDED_DATE;
	}

	public void setMED_APPT_ATTENDED_DATE(String mED_APPT_ATTENDED_DATE) {
		MED_APPT_ATTENDED_DATE = mED_APPT_ATTENDED_DATE;
	}

	public String getMED_APPT_VERIFIED_DATE() {
		return MED_APPT_VERIFIED_DATE;
	}

	public void setMED_APPT_VERIFIED_DATE(String mED_APPT_VERIFIED_DATE) {
		MED_APPT_VERIFIED_DATE = mED_APPT_VERIFIED_DATE;
	}

	public String getREASON_MED_APPT_NOT_ATTENDED() {
		return REASON_MED_APPT_NOT_ATTENDED;
	}

	public void setREASON_MED_APPT_NOT_ATTENDED(String rEASON_MED_APPT_NOT_ATTENDED) {
		REASON_MED_APPT_NOT_ATTENDED = rEASON_MED_APPT_NOT_ATTENDED;
	}

	public String getSPECIFIED_REASON() {
		return SPECIFIED_REASON;
	}

	public void setSPECIFIED_REASON(String sPECIFIED_REASON) {
		SPECIFIED_REASON = sPECIFIED_REASON;
	}

	public String getLINKAGE_DISPO() {
		return LINKAGE_DISPO;
	}

	public void setLINKAGE_DISPO(String lINKAGE_DISPO) {
		LINKAGE_DISPO = lINKAGE_DISPO;
	}

	public String getFELT_HEALTHY() {
		return FELT_HEALTHY;
	}

	public void setFELT_HEALTHY(String fELT_HEALTHY) {
		FELT_HEALTHY = fELT_HEALTHY;
	}

	public String getFELT_DEPRESSED() {
		return FELT_DEPRESSED;
	}

	public void setFELT_DEPRESSED(String fELT_DEPRESSED) {
		FELT_DEPRESSED = fELT_DEPRESSED;
	}

	public String getRESPONSIBILITIES() {
		return RESPONSIBILITIES;
	}

	public void setRESPONSIBILITIES(String rESPONSIBILITIES) {
		RESPONSIBILITIES = rESPONSIBILITIES;
	}

	public String getTHINKS_NOT_HIV_POS() {
		return THINKS_NOT_HIV_POS;
	}

	public void setTHINKS_NOT_HIV_POS(String tHINKS_NOT_HIV_POS) {
		THINKS_NOT_HIV_POS = tHINKS_NOT_HIV_POS;
	}

	public String getFAMILY_COMMITMENTS() {
		return FAMILY_COMMITMENTS;
	}

	public void setFAMILY_COMMITMENTS(String fAMILY_COMMITMENTS) {
		FAMILY_COMMITMENTS = fAMILY_COMMITMENTS;
	}

	public String getSUBSTANCE_USE() {
		return SUBSTANCE_USE;
	}

	public void setSUBSTANCE_USE(String sUBSTANCE_USE) {
		SUBSTANCE_USE = sUBSTANCE_USE;
	}

	public String getNO_CHILD_CARE() {
		return NO_CHILD_CARE;
	}

	public void setNO_CHILD_CARE(String nO_CHILD_CARE) {
		NO_CHILD_CARE = nO_CHILD_CARE;
	}

	public String getLACK_STABLE_HOUSING() {
		return LACK_STABLE_HOUSING;
	}

	public void setLACK_STABLE_HOUSING(String lACK_STABLE_HOUSING) {
		LACK_STABLE_HOUSING = lACK_STABLE_HOUSING;
	}

	public String getLANGUAGE_BARRIER() {
		return LANGUAGE_BARRIER;
	}

	public void setLANGUAGE_BARRIER(String lANGUAGE_BARRIER) {
		LANGUAGE_BARRIER = lANGUAGE_BARRIER;
	}

	public String getWORK_OBLIGATION() {
		return WORK_OBLIGATION;
	}

	public void setWORK_OBLIGATION(String wORK_OBLIGATION) {
		WORK_OBLIGATION = wORK_OBLIGATION;
	}

	public String getNO_MED_INSURANCE() {
		return NO_MED_INSURANCE;
	}

	public void setNO_MED_INSURANCE(String nO_MED_INSURANCE) {
		NO_MED_INSURANCE = nO_MED_INSURANCE;
	}

	public String getDONT_TRUST_HEALTH_WRKR() {
		return DONT_TRUST_HEALTH_WRKR;
	}

	public void setDONT_TRUST_HEALTH_WRKR(String dONT_TRUST_HEALTH_WRKR) {
		DONT_TRUST_HEALTH_WRKR = dONT_TRUST_HEALTH_WRKR;
	}

	public String getADVERSE_EFFECT_HIV_MEDS() {
		return ADVERSE_EFFECT_HIV_MEDS;
	}

	public void setADVERSE_EFFECT_HIV_MEDS(String aDVERSE_EFFECT_HIV_MEDS) {
		ADVERSE_EFFECT_HIV_MEDS = aDVERSE_EFFECT_HIV_MEDS;
	}

	public String getINCONVENIENT_APPT_TIMES() {
		return INCONVENIENT_APPT_TIMES;
	}

	public void setINCONVENIENT_APPT_TIMES(String iNCONVENIENT_APPT_TIMES) {
		INCONVENIENT_APPT_TIMES = iNCONVENIENT_APPT_TIMES;
	}

	public String getFORGOT_APPT() {
		return FORGOT_APPT;
	}

	public void setFORGOT_APPT(String fORGOT_APPT) {
		FORGOT_APPT = fORGOT_APPT;
	}

	public String getDIDNT_FEEL_LIKE_GOING_APPT() {
		return DIDNT_FEEL_LIKE_GOING_APPT;
	}

	public void setDIDNT_FEEL_LIKE_GOING_APPT(String dIDNT_FEEL_LIKE_GOING_APPT) {
		DIDNT_FEEL_LIKE_GOING_APPT = dIDNT_FEEL_LIKE_GOING_APPT;
	}

	public String getTOO_ILL() {
		return TOO_ILL;
	}

	public void setTOO_ILL(String tOO_ILL) {
		TOO_ILL = tOO_ILL;
	}

	public String getNOT_WITH_PREFERRED_PROV() {
		return NOT_WITH_PREFERRED_PROV;
	}

	public void setNOT_WITH_PREFERRED_PROV(String nOT_WITH_PREFERRED_PROV) {
		NOT_WITH_PREFERRED_PROV = nOT_WITH_PREFERRED_PROV;
	}

	public String getLACK_TRANSPORTATION() {
		return LACK_TRANSPORTATION;
	}

	public void setLACK_TRANSPORTATION(String lACK_TRANSPORTATION) {
		LACK_TRANSPORTATION = lACK_TRANSPORTATION;
	}

	public String getOTHER_REASON_OOC() {
		return OTHER_REASON_OOC;
	}

	public void setOTHER_REASON_OOC(String oTHER_REASON_OOC) {
		OTHER_REASON_OOC = oTHER_REASON_OOC;
	}

	public String getSPECIFY_REASON_OOC() {
		return SPECIFY_REASON_OOC;
	}

	public void setSPECIFY_REASON_OOC(String sPECIFY_REASON_OOC) {
		SPECIFY_REASON_OOC = sPECIFY_REASON_OOC;
	}

	public String getPREVENTION_COUNSELING() {
		return PREVENTION_COUNSELING;
	}

	public void setPREVENTION_COUNSELING(String pREVENTION_COUNSELING) {
		PREVENTION_COUNSELING = pREVENTION_COUNSELING;
	}

	public String getRISK_ASSESSMENT() {
		return RISK_ASSESSMENT;
	}

	public void setRISK_ASSESSMENT(String rISK_ASSESSMENT) {
		RISK_ASSESSMENT = rISK_ASSESSMENT;
	}

	public String getMALE_CONDOMS() {
		return MALE_CONDOMS;
	}

	public void setMALE_CONDOMS(String mALE_CONDOMS) {
		MALE_CONDOMS = mALE_CONDOMS;
	}

	public String getFEMALE_CONDOMS() {
		return FEMALE_CONDOMS;
	}

	public void setFEMALE_CONDOMS(String fEMALE_CONDOMS) {
		FEMALE_CONDOMS = fEMALE_CONDOMS;
	}

	public String getNONMED_APPTS_MADE() {
		return NONMED_APPTS_MADE;
	}

	public void setNONMED_APPTS_MADE(String nONMED_APPTS_MADE) {
		NONMED_APPTS_MADE = nONMED_APPTS_MADE;
	}

	public String getCASE_MANAGEMENT() {
		return CASE_MANAGEMENT;
	}

	public void setCASE_MANAGEMENT(String cASE_MANAGEMENT) {
		CASE_MANAGEMENT = cASE_MANAGEMENT;
	}

	public String getNUTRITION() {
		return NUTRITION;
	}

	public void setNUTRITION(String nUTRITION) {
		NUTRITION = nUTRITION;
	}

	public String getTRANSPORTATION() {
		return TRANSPORTATION;
	}

	public void setTRANSPORTATION(String tRANSPORTATION) {
		TRANSPORTATION = tRANSPORTATION;
	}

	public String getDENTAL_CARE() {
		return DENTAL_CARE;
	}

	public void setDENTAL_CARE(String dENTAL_CARE) {
		DENTAL_CARE = dENTAL_CARE;
	}

	public String getHOUSING() {
		return HOUSING;
	}

	public void setHOUSING(String hOUSING) {
		HOUSING = hOUSING;
	}

	public String getMENTAL_HEALTH() {
		return MENTAL_HEALTH;
	}

	public void setMENTAL_HEALTH(String mENTAL_HEALTH) {
		MENTAL_HEALTH = mENTAL_HEALTH;
	}

	public String getALCOHOL_SUBSTANCE_USE() {
		return ALCOHOL_SUBSTANCE_USE;
	}

	public void setALCOHOL_SUBSTANCE_USE(String aLCOHOL_SUBSTANCE_USE) {
		ALCOHOL_SUBSTANCE_USE = aLCOHOL_SUBSTANCE_USE;
	}

	public String getLEGAL() {
		return LEGAL;
	}

	public void setLEGAL(String lEGAL) {
		LEGAL = lEGAL;
	}

	public String getEDUCATION_EMPLOYMENT() {
		return EDUCATION_EMPLOYMENT;
	}

	public void setEDUCATION_EMPLOYMENT(String eDUCATION_EMPLOYMENT) {
		EDUCATION_EMPLOYMENT = eDUCATION_EMPLOYMENT;
	}

	public String getFINANCIAL_ENTITLEMENTS() {
		return FINANCIAL_ENTITLEMENTS;
	}

	public void setFINANCIAL_ENTITLEMENTS(String fINANCIAL_ENTITLEMENTS) {
		FINANCIAL_ENTITLEMENTS = fINANCIAL_ENTITLEMENTS;
	}

	public String getFAMILY_PARENTING() {
		return FAMILY_PARENTING;
	}

	public void setFAMILY_PARENTING(String fAMILY_PARENTING) {
		FAMILY_PARENTING = fAMILY_PARENTING;
	}

	public String getSYRINGE_EXCHANGE_ESAP() {
		return SYRINGE_EXCHANGE_ESAP;
	}

	public void setSYRINGE_EXCHANGE_ESAP(String sYRINGE_EXCHANGE_ESAP) {
		SYRINGE_EXCHANGE_ESAP = sYRINGE_EXCHANGE_ESAP;
	}

	public String getPOSITIVE_PATHWAYS() {
		return POSITIVE_PATHWAYS;
	}

	public void setPOSITIVE_PATHWAYS(String pOSITIVE_PATHWAYS) {
		POSITIVE_PATHWAYS = pOSITIVE_PATHWAYS;
	}

	public String getINTERVIEWED_IX() {
		return INTERVIEWED_IX;
	}

	public void setINTERVIEWED_IX(String iNTERVIEWED_IX) {
		INTERVIEWED_IX = iNTERVIEWED_IX;
	}

	public List<Integer> getPartnerList() {
		return partnerList;
	}

	public void setPartnerList(List<Integer> partnerList) {
		this.partnerList = partnerList;
	}
		
	
	
}