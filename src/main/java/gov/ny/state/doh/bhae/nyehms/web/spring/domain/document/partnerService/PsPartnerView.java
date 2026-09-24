package gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.partnerService;

import java.util.Date;

/**
 * This is POJO for LEGACY_PS_PERSON_PARTNER_VW
 * @author vxp06
 */
public class PsPartnerView {

	private String STATENO;
	private int PARTNRID;
	private String WORK_TYPE;
	private int ASSIGNMENT_ID;
	private String GENRL_CD;
	private String RRT_CD;
	private String TESTLOC_CD;
	private String P_RISK_CD;
	private  String RACE_CD;
	private String ETHNICCD;
	private String FNAME;
	private String MNAME;
	private String LNAME;
	private String ADDRESS;
	private String CITY;
	private String STATE;
	private String ZIPCODE;
	private String PLUS4;
	private String TRACT_CENSUS;
	private String TRACT_SUFFIX_CENSUS;
	private String TELEPHONE;
	private String SPOUSE;
	private String GENDER;
	private String DVSCREEN;
	private String RISK_VIOLENCE;
	private int AGE;
	private Date NOTIF_DT;
	private Date HDISP_DT;
	private String F_EXP_DT;
	private String L_EXP_DT;
	private String EXP_FREQ;
	private String HDISP_UID;
	private String REFERRED_BY;
	private String PREGNANT;
	private int NUMBER_OF_WEEKS_PREGNANT;
	private String DISCL_CD;
	private Date DISCL_DT;
	private String CASEWORKER_UID_DISCLOSURE_INTE;
	private String STATUS_PROVIDER;
	private String STATUSDT_PROVIDER;
	private String EXAM_DT_PARTNER;
	private String TEST_TYPE_PARTNER;
	private String RESULT_PARTNER;
	private String EXAM_PROVIDER_PARTNER;
	private int HEIGHT_PARTNER;
	private String BUILD_PARTNER;
	private String HAIR_PARTNER;
	private String COMPLEXION_PARTNER;
	private String PLACE_OF_EMPLOYMENT_PARTNER;
	private String HDISP_CD;
	private String PARTNER_CO_FIPS;
	private String POSTESTC;
	private String REFPCD;
	private String REFLABCD;
	private String STATUS_CD_PARTNER_ASSIGNMENT;
	private String MARITALCD;
	private String INVEST_AGEN;
	private String REFPART;
	private String REFPOSTLAB;
	private String OOJ_NO;
	private String OOJ_AR;
	private String REFOOJ;
	private String REFOOJCD;
	private String DUE_DATE;
	private String PART_LAT;
	private String PART_LONG;
	private String UPDATE_UID;
	private Date UPDATE_DT;
	private String CREATEID;
	private Date CREATEDT;
	private int HIGH_PRIORITY;
	private int FLAG_CD;
	private String HEIGHT;
	private int RACE_INDIAN;
	private int RACE_BLACK;
	private int RACE_ASIAN;
	private int RACE_PACIFIC;
	private int RACE_WHITE;
	private int RACE_MULTIPLE;
	private int RACE_OTHER;
	private int RACE_UNKNOWN;
	private String ALIAS_LNAME;
	private String ALIAS_FNAME;
	private String ALIAS_MNAME;
	private String EMAIL_ADDRESS;
	private String CELL_PHONE;
	private String PAGER;
	private String DOB_TEXT;
	private String HIV_STATUS_PARTNER_CD;
	private String TEST_DT_PARTNER;
	private String WEIGHT;
	private String WORK_PHONE;
	private String WORK_HOURS;
	private int PARTNER_ELICITED_BY_PNAP;
	private int PARTNER_ELICITED_BY_ACT;
	private String RACE_OTHER_TEXT;
	private String SUFFIX;
	private String AKA;
	private String MAIDEN;
	private String COUNTRY_CD;
	private String BIRTH_SEX;
	private String ADD_GENDER;
	private String TYPE_REFERRAL;
	private String PARTNER_TYPE;
	private String NOTIFY;
	private String NOTIFY_PLAN;
	private String NOTIFY_METHOD;
	private String PREV_HIV_TEST;
	private String SELF_RPT_RES;
	private Date DATE_LAST_HIV_TEST;
	private String REF_TO_TESTING;
	private Date REF_DATE;
	private String TESTED;
	private String POSTESTC_NEW;
	private String OTHER_IDENTIFIERS;
	private String REF2;
	private String ATTEND_FIRST_APPT;
	private String WEB_ADDRESS_SCREEN;
	private String RACE_ASIAN_SUB;
	private String RACE_PACIFIC_SUB;
	private String FIELD_TESTING;
	private String INTERNET_OUTCOME;
	private String TEST_RESULTS;

	private Date DATE_COLLECTED;
	private String SWM;
	private String SWF;
	private String SEX_W_TRANS;
	private String SHARED_DRUG_EQUIP;
	private String IDU;
	private String COMMENTS;
	private String RISK_OTHER;

    public PsPartnerView(String STATENO, int PARTNRID, String WORK_TYPE, int ASSIGNMENT_ID, String GENRL_CD, String RRT_CD, String TESTLOC_CD, String P_RISK_CD, String RACE_CD, String ETHNICCD, String FNAME, String MNAME, String LNAME, String ADDRESS, String CITY, String STATE, String ZIPCODE, String PLUS4, String TRACT_CENSUS, String TRACT_SUFFIX_CENSUS, String TELEPHONE, String SPOUSE, String GENDER, String DVSCREEN, String RISK_VIOLENCE, int AGE, Date NOTIF_DT, Date HDISP_DT, String F_EXP_DT, String L_EXP_DT, String EXP_FREQ, String HDISP_UID, String REFERRED_BY, String PREGNANT, int NUMBER_OF_WEEKS_PREGNANT, String DISCL_CD, Date DISCL_DT, String CASEWORKER_UID_DISCLOSURE_INTE, String STATUS_PROVIDER, String STATUSDT_PROVIDER, String EXAM_DT_PARTNER, String TEST_TYPE_PARTNER, String RESULT_PARTNER, String EXAM_PROVIDER_PARTNER, int HEIGHT_PARTNER, String BUILD_PARTNER, String HAIR_PARTNER, String COMPLEXION_PARTNER, String PLACE_OF_EMPLOYMENT_PARTNER, String HDISP_CD, String PARTNER_CO_FIPS, String POSTESTC, String REFPCD, String REFLABCD, String STATUS_CD_PARTNER_ASSIGNMENT, String MARITALCD, String INVEST_AGEN, String REFPART, String REFPOSTLAB, String OOJ_NO, String OOJ_AR, String REFOOJ, String REFOOJCD, String DUE_DATE, String PART_LAT, String PART_LONG, String UPDATE_UID, Date UPDATE_DT, String CREATEID, Date CREATEDT, int HIGH_PRIORITY, int FLAG_CD, String HEIGHT, int RACE_INDIAN, int RACE_BLACK, int RACE_ASIAN, int RACE_PACIFIC, int RACE_WHITE, int RACE_MULTIPLE, int RACE_OTHER, int RACE_UNKNOWN, String ALIAS_LNAME, String ALIAS_FNAME, String ALIAS_MNAME, String EMAIL_ADDRESS, String CELL_PHONE, String PAGER, String DOB_TEXT, String HIV_STATUS_PARTNER_CD, String TEST_DT_PARTNER, String WEIGHT, String WORK_PHONE, String WORK_HOURS, int PARTNER_ELICITED_BY_PNAP, int PARTNER_ELICITED_BY_ACT, String RACE_OTHER_TEXT, String SUFFIX, String AKA, String MAIDEN, String COUNTRY_CD, String BIRTH_SEX, String ADD_GENDER, String TYPE_REFERRAL, String PARTNER_TYPE, String NOTIFY, String NOTIFY_PLAN, String NOTIFY_METHOD, String PREV_HIV_TEST, String SELF_RPT_RES, Date DATE_LAST_HIV_TEST, String REF_TO_TESTING, Date REF_DATE, String TESTED, String POSTESTC_NEW, String OTHER_IDENTIFIERS, String REF2, String ATTEND_FIRST_APPT, String WEB_ADDRESS_SCREEN, String RACE_ASIAN_SUB, String RACE_PACIFIC_SUB, String FIELD_TESTING, String INTERNET_OUTCOME, String TEST_RESULTS, Date DATE_COLLECTED, String SWM, String SWF, String SEX_W_TRANS, String SHARED_DRUG_EQUIP, String IDU, String COMMENTS, String RISK_OTHER) {
        this.STATENO = STATENO;
        this.PARTNRID = PARTNRID;
        this.WORK_TYPE = WORK_TYPE;
        this.ASSIGNMENT_ID = ASSIGNMENT_ID;
        this.GENRL_CD = GENRL_CD;
        this.RRT_CD = RRT_CD;
        this.TESTLOC_CD = TESTLOC_CD;
        this.P_RISK_CD = P_RISK_CD;
        this.RACE_CD = RACE_CD;
        this.ETHNICCD = ETHNICCD;
        this.FNAME = FNAME;
        this.MNAME = MNAME;
        this.LNAME = LNAME;
        this.ADDRESS = ADDRESS;
        this.CITY = CITY;
        this.STATE = STATE;
        this.ZIPCODE = ZIPCODE;
        this.PLUS4 = PLUS4;
        this.TRACT_CENSUS = TRACT_CENSUS;
        this.TRACT_SUFFIX_CENSUS = TRACT_SUFFIX_CENSUS;
        this.TELEPHONE = TELEPHONE;
        this.SPOUSE = SPOUSE;
        this.GENDER = GENDER;
        this.DVSCREEN = DVSCREEN;
        this.RISK_VIOLENCE = RISK_VIOLENCE;
        this.AGE = AGE;
        this.NOTIF_DT = NOTIF_DT;
        this.HDISP_DT = HDISP_DT;
        this.F_EXP_DT = F_EXP_DT;
        this.L_EXP_DT = L_EXP_DT;
        this.EXP_FREQ = EXP_FREQ;
        this.HDISP_UID = HDISP_UID;
        this.REFERRED_BY = REFERRED_BY;
        this.PREGNANT = PREGNANT;
        this.NUMBER_OF_WEEKS_PREGNANT = NUMBER_OF_WEEKS_PREGNANT;
        this.DISCL_CD = DISCL_CD;
        this.DISCL_DT = DISCL_DT;
        this.CASEWORKER_UID_DISCLOSURE_INTE = CASEWORKER_UID_DISCLOSURE_INTE;
        this.STATUS_PROVIDER = STATUS_PROVIDER;
        this.STATUSDT_PROVIDER = STATUSDT_PROVIDER;
        this.EXAM_DT_PARTNER = EXAM_DT_PARTNER;
        this.TEST_TYPE_PARTNER = TEST_TYPE_PARTNER;
        this.RESULT_PARTNER = RESULT_PARTNER;
        this.EXAM_PROVIDER_PARTNER = EXAM_PROVIDER_PARTNER;
        this.HEIGHT_PARTNER = HEIGHT_PARTNER;
        this.BUILD_PARTNER = BUILD_PARTNER;
        this.HAIR_PARTNER = HAIR_PARTNER;
        this.COMPLEXION_PARTNER = COMPLEXION_PARTNER;
        this.PLACE_OF_EMPLOYMENT_PARTNER = PLACE_OF_EMPLOYMENT_PARTNER;
        this.HDISP_CD = HDISP_CD;
        this.PARTNER_CO_FIPS = PARTNER_CO_FIPS;
        this.POSTESTC = POSTESTC;
        this.REFPCD = REFPCD;
        this.REFLABCD = REFLABCD;
        this.STATUS_CD_PARTNER_ASSIGNMENT = STATUS_CD_PARTNER_ASSIGNMENT;
        this.MARITALCD = MARITALCD;
        this.INVEST_AGEN = INVEST_AGEN;
        this.REFPART = REFPART;
        this.REFPOSTLAB = REFPOSTLAB;
        this.OOJ_NO = OOJ_NO;
        this.OOJ_AR = OOJ_AR;
        this.REFOOJ = REFOOJ;
        this.REFOOJCD = REFOOJCD;
        this.DUE_DATE = DUE_DATE;
        this.PART_LAT = PART_LAT;
        this.PART_LONG = PART_LONG;
        this.UPDATE_UID = UPDATE_UID;
        this.UPDATE_DT = UPDATE_DT;
        this.CREATEID = CREATEID;
        this.CREATEDT = CREATEDT;
        this.HIGH_PRIORITY = HIGH_PRIORITY;
        this.FLAG_CD = FLAG_CD;
        this.HEIGHT = HEIGHT;
        this.RACE_INDIAN = RACE_INDIAN;
        this.RACE_BLACK = RACE_BLACK;
        this.RACE_ASIAN = RACE_ASIAN;
        this.RACE_PACIFIC = RACE_PACIFIC;
        this.RACE_WHITE = RACE_WHITE;
        this.RACE_MULTIPLE = RACE_MULTIPLE;
        this.RACE_OTHER = RACE_OTHER;
        this.RACE_UNKNOWN = RACE_UNKNOWN;
        this.ALIAS_LNAME = ALIAS_LNAME;
        this.ALIAS_FNAME = ALIAS_FNAME;
        this.ALIAS_MNAME = ALIAS_MNAME;
        this.EMAIL_ADDRESS = EMAIL_ADDRESS;
        this.CELL_PHONE = CELL_PHONE;
        this.PAGER = PAGER;
        this.DOB_TEXT = DOB_TEXT;
        this.HIV_STATUS_PARTNER_CD = HIV_STATUS_PARTNER_CD;
        this.TEST_DT_PARTNER = TEST_DT_PARTNER;
        this.WEIGHT = WEIGHT;
        this.WORK_PHONE = WORK_PHONE;
        this.WORK_HOURS = WORK_HOURS;
        this.PARTNER_ELICITED_BY_PNAP = PARTNER_ELICITED_BY_PNAP;
        this.PARTNER_ELICITED_BY_ACT = PARTNER_ELICITED_BY_ACT;
        this.RACE_OTHER_TEXT = RACE_OTHER_TEXT;
        this.SUFFIX = SUFFIX;
        this.AKA = AKA;
        this.MAIDEN = MAIDEN;
        this.COUNTRY_CD = COUNTRY_CD;
        this.BIRTH_SEX = BIRTH_SEX;
        this.ADD_GENDER = ADD_GENDER;
        this.TYPE_REFERRAL = TYPE_REFERRAL;
        this.PARTNER_TYPE = PARTNER_TYPE;
        this.NOTIFY = NOTIFY;
        this.NOTIFY_PLAN = NOTIFY_PLAN;
        this.NOTIFY_METHOD = NOTIFY_METHOD;
        this.PREV_HIV_TEST = PREV_HIV_TEST;
        this.SELF_RPT_RES = SELF_RPT_RES;
        this.DATE_LAST_HIV_TEST = DATE_LAST_HIV_TEST;
        this.REF_TO_TESTING = REF_TO_TESTING;
        this.REF_DATE = REF_DATE;
        this.TESTED = TESTED;
        this.POSTESTC_NEW = POSTESTC_NEW;
        this.OTHER_IDENTIFIERS = OTHER_IDENTIFIERS;
        this.REF2 = REF2;
        this.ATTEND_FIRST_APPT = ATTEND_FIRST_APPT;
        this.WEB_ADDRESS_SCREEN = WEB_ADDRESS_SCREEN;
        this.RACE_ASIAN_SUB = RACE_ASIAN_SUB;
        this.RACE_PACIFIC_SUB = RACE_PACIFIC_SUB;
        this.FIELD_TESTING = FIELD_TESTING;
        this.INTERNET_OUTCOME = INTERNET_OUTCOME;
        this.TEST_RESULTS = TEST_RESULTS;
        this.DATE_COLLECTED = DATE_COLLECTED;
        this.SWM = SWM;
        this.SWF = SWF;
        this.SEX_W_TRANS = SEX_W_TRANS;
        this.SHARED_DRUG_EQUIP = SHARED_DRUG_EQUIP;
        this.IDU = IDU;
        this.COMMENTS = COMMENTS;
        this.RISK_OTHER = RISK_OTHER;
    }

	public String getSTATENO() {
		return STATENO;
	}

	public void setSTATENO(String sTATENO) {
		STATENO = sTATENO;
	}

	public int getPARTNRID() {
		return PARTNRID;
	}

	public void setPARTNRID(int pARTNRID) {
		PARTNRID = pARTNRID;
	}

	public String getWORK_TYPE() {
		return WORK_TYPE;
	}

	public void setWORK_TYPE(String wORK_TYPE) {
		WORK_TYPE = wORK_TYPE;
	}

	public int getASSIGNMENT_ID() {
		return ASSIGNMENT_ID;
	}

	public void setASSIGNMENT_ID(int aSSIGNMENT_ID) {
		ASSIGNMENT_ID = aSSIGNMENT_ID;
	}

	public String getGENRL_CD() {
		return GENRL_CD;
	}

	public void setGENRL_CD(String gENRL_CD) {
		GENRL_CD = gENRL_CD;
	}

	public String getRRT_CD() {
		return RRT_CD;
	}

	public void setRRT_CD(String rRT_CD) {
		RRT_CD = rRT_CD;
	}

	public String getTESTLOC_CD() {
		return TESTLOC_CD;
	}

	public void setTESTLOC_CD(String tESTLOC_CD) {
		TESTLOC_CD = tESTLOC_CD;
	}

	public String getP_RISK_CD() {
		return P_RISK_CD;
	}

	public void setP_RISK_CD(String p_RISK_CD) {
		P_RISK_CD = p_RISK_CD;
	}

	public String getRACE_CD() {
		return RACE_CD;
	}

	public void setRACE_CD(String rACE_CD) {
		RACE_CD = rACE_CD;
	}

	public String getETHNICCD() {
		return ETHNICCD;
	}

	public void setETHNICCD(String eTHNICCD) {
		ETHNICCD = eTHNICCD;
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

	public String getADDRESS() {
		return ADDRESS;
	}

	public void setADDRESS(String aDDRESS) {
		ADDRESS = aDDRESS;
	}

	public String getCITY() {
		return CITY;
	}

	public void setCITY(String cITY) {
		CITY = cITY;
	}

	public String getSTATE() {
		return STATE;
	}

	public void setSTATE(String sTATE) {
		STATE = sTATE;
	}

	public String getZIPCODE() {
		return ZIPCODE;
	}

	public void setZIPCODE(String zIPCODE) {
		ZIPCODE = zIPCODE;
	}

	public String getPLUS4() {
		return PLUS4;
	}

	public void setPLUS4(String pLUS4) {
		PLUS4 = pLUS4;
	}

	public String getTRACT_CENSUS() {
		return TRACT_CENSUS;
	}

	public void setTRACT_CENSUS(String tRACT_CENSUS) {
		TRACT_CENSUS = tRACT_CENSUS;
	}

	public String getTRACT_SUFFIX_CENSUS() {
		return TRACT_SUFFIX_CENSUS;
	}

	public void setTRACT_SUFFIX_CENSUS(String tRACT_SUFFIX_CENSUS) {
		TRACT_SUFFIX_CENSUS = tRACT_SUFFIX_CENSUS;
	}

	public String getTELEPHONE() {
		return TELEPHONE;
	}

	public void setTELEPHONE(String tELEPHONE) {
		TELEPHONE = tELEPHONE;
	}

	public String getSPOUSE() {
		return SPOUSE;
	}

	public void setSPOUSE(String sPOUSE) {
		SPOUSE = sPOUSE;
	}

	public String getGENDER() {
		return GENDER;
	}

	public void setGENDER(String gENDER) {
		GENDER = gENDER;
	}

	public String getDVSCREEN() {
		return DVSCREEN;
	}

	public void setDVSCREEN(String dVSCREEN) {
		DVSCREEN = dVSCREEN;
	}

	public String getRISK_VIOLENCE() {
		return RISK_VIOLENCE;
	}

	public void setRISK_VIOLENCE(String rISK_VIOLENCE) {
		RISK_VIOLENCE = rISK_VIOLENCE;
	}

	public int getAGE() {
		return AGE;
	}

	public void setAGE(int aGE) {
		AGE = aGE;
	}

	public Date getNOTIF_DT() {
		return NOTIF_DT;
	}

	public void setNOTIF_DT(Date nOTIF_DT) {
		NOTIF_DT = nOTIF_DT;
	}

	public Date getHDISP_DT() {
		return HDISP_DT;
	}

	public void setHDISP_DT(Date hDISP_DT) {
		HDISP_DT = hDISP_DT;
	}

	public String getF_EXP_DT() {
		return F_EXP_DT;
	}

	public void setF_EXP_DT(String f_EXP_DT) {
		F_EXP_DT = f_EXP_DT;
	}

	public String getL_EXP_DT() {
		return L_EXP_DT;
	}

	public void setL_EXP_DT(String l_EXP_DT) {
		L_EXP_DT = l_EXP_DT;
	}

	public String getEXP_FREQ() {
		return EXP_FREQ;
	}

	public void setEXP_FREQ(String eXP_FREQ) {
		EXP_FREQ = eXP_FREQ;
	}

	public String getHDISP_UID() {
		return HDISP_UID;
	}

	public void setHDISP_UID(String hDISP_UID) {
		HDISP_UID = hDISP_UID;
	}

	public String getREFERRED_BY() {
		return REFERRED_BY;
	}

	public void setREFERRED_BY(String rEFERRED_BY) {
		REFERRED_BY = rEFERRED_BY;
	}

	public String getPREGNANT() {
		return PREGNANT;
	}

	public void setPREGNANT(String pREGNANT) {
		PREGNANT = pREGNANT;
	}

	public int getNUMBER_OF_WEEKS_PREGNANT() {
		return NUMBER_OF_WEEKS_PREGNANT;
	}

	public void setNUMBER_OF_WEEKS_PREGNANT(int nUMBER_OF_WEEKS_PREGNANT) {
		NUMBER_OF_WEEKS_PREGNANT = nUMBER_OF_WEEKS_PREGNANT;
	}

	public String getDISCL_CD() {
		return DISCL_CD;
	}

	public void setDISCL_CD(String dISCL_CD) {
		DISCL_CD = dISCL_CD;
	}

	public Date getDISCL_DT() {
		return DISCL_DT;
	}

	public void setDISCL_DT(Date dISCL_DT) {
		DISCL_DT = dISCL_DT;
	}

	public String getCASEWORKER_UID_DISCLOSURE_INTE() {
		return CASEWORKER_UID_DISCLOSURE_INTE;
	}

	public void setCASEWORKER_UID_DISCLOSURE_INTE(
			String cASEWORKER_UID_DISCLOSURE_INTE) {
		CASEWORKER_UID_DISCLOSURE_INTE = cASEWORKER_UID_DISCLOSURE_INTE;
	}

	public String getSTATUS_PROVIDER() {
		return STATUS_PROVIDER;
	}

	public void setSTATUS_PROVIDER(String sTATUS_PROVIDER) {
		STATUS_PROVIDER = sTATUS_PROVIDER;
	}

	public String getSTATUSDT_PROVIDER() {
		return STATUSDT_PROVIDER;
	}

	public void setSTATUSDT_PROVIDER(String sTATUSDT_PROVIDER) {
		STATUSDT_PROVIDER = sTATUSDT_PROVIDER;
	}

	public String getEXAM_DT_PARTNER() {
		return EXAM_DT_PARTNER;
	}

	public void setEXAM_DT_PARTNER(String eXAM_DT_PARTNER) {
		EXAM_DT_PARTNER = eXAM_DT_PARTNER;
	}

	public String getTEST_TYPE_PARTNER() {
		return TEST_TYPE_PARTNER;
	}

	public void setTEST_TYPE_PARTNER(String tEST_TYPE_PARTNER) {
		TEST_TYPE_PARTNER = tEST_TYPE_PARTNER;
	}

	public String getRESULT_PARTNER() {
		return RESULT_PARTNER;
	}

	public void setRESULT_PARTNER(String rESULT_PARTNER) {
		RESULT_PARTNER = rESULT_PARTNER;
	}

	public String getEXAM_PROVIDER_PARTNER() {
		return EXAM_PROVIDER_PARTNER;
	}

	public void setEXAM_PROVIDER_PARTNER(String eXAM_PROVIDER_PARTNER) {
		EXAM_PROVIDER_PARTNER = eXAM_PROVIDER_PARTNER;
	}

	public int getHEIGHT_PARTNER() {
		return HEIGHT_PARTNER;
	}

	public void setHEIGHT_PARTNER(int hEIGHT_PARTNER) {
		HEIGHT_PARTNER = hEIGHT_PARTNER;
	}

	public String getBUILD_PARTNER() {
		return BUILD_PARTNER;
	}

	public void setBUILD_PARTNER(String bUILD_PARTNER) {
		BUILD_PARTNER = bUILD_PARTNER;
	}

	public String getHAIR_PARTNER() {
		return HAIR_PARTNER;
	}

	public void setHAIR_PARTNER(String hAIR_PARTNER) {
		HAIR_PARTNER = hAIR_PARTNER;
	}

	public String getCOMPLEXION_PARTNER() {
		return COMPLEXION_PARTNER;
	}

	public void setCOMPLEXION_PARTNER(String cOMPLEXION_PARTNER) {
		COMPLEXION_PARTNER = cOMPLEXION_PARTNER;
	}

	public String getPLACE_OF_EMPLOYMENT_PARTNER() {
		return PLACE_OF_EMPLOYMENT_PARTNER;
	}

	public void setPLACE_OF_EMPLOYMENT_PARTNER(String pLACE_OF_EMPLOYMENT_PARTNER) {
		PLACE_OF_EMPLOYMENT_PARTNER = pLACE_OF_EMPLOYMENT_PARTNER;
	}

	public String getHDISP_CD() {
		return HDISP_CD;
	}

	public void setHDISP_CD(String hDISP_CD) {
		HDISP_CD = hDISP_CD;
	}

	public String getPARTNER_CO_FIPS() {
		return PARTNER_CO_FIPS;
	}

	public void setPARTNER_CO_FIPS(String pARTNER_CO_FIPS) {
		PARTNER_CO_FIPS = pARTNER_CO_FIPS;
	}

	public String getPOSTESTC() {
		return POSTESTC;
	}

	public void setPOSTESTC(String pOSTESTC) {
		POSTESTC = pOSTESTC;
	}

	public String getREFPCD() {
		return REFPCD;
	}

	public void setREFPCD(String rEFPCD) {
		REFPCD = rEFPCD;
	}

	public String getREFLABCD() {
		return REFLABCD;
	}

	public void setREFLABCD(String rEFLABCD) {
		REFLABCD = rEFLABCD;
	}

	public String getSTATUS_CD_PARTNER_ASSIGNMENT() {
		return STATUS_CD_PARTNER_ASSIGNMENT;
	}

	public void setSTATUS_CD_PARTNER_ASSIGNMENT(String sTATUS_CD_PARTNER_ASSIGNMENT) {
		STATUS_CD_PARTNER_ASSIGNMENT = sTATUS_CD_PARTNER_ASSIGNMENT;
	}

	public String getMARITALCD() {
		return MARITALCD;
	}

	public void setMARITALCD(String mARITALCD) {
		MARITALCD = mARITALCD;
	}

	public String getINVEST_AGEN() {
		return INVEST_AGEN;
	}

	public void setINVEST_AGEN(String iNVEST_AGEN) {
		INVEST_AGEN = iNVEST_AGEN;
	}

	public String getREFPART() {
		return REFPART;
	}

	public void setREFPART(String rEFPART) {
		REFPART = rEFPART;
	}

	public String getREFPOSTLAB() {
		return REFPOSTLAB;
	}

	public void setREFPOSTLAB(String rEFPOSTLAB) {
		REFPOSTLAB = rEFPOSTLAB;
	}

	public String getOOJ_NO() {
		return OOJ_NO;
	}

	public void setOOJ_NO(String oOJ_NO) {
		OOJ_NO = oOJ_NO;
	}

	public String getOOJ_AR() {
		return OOJ_AR;
	}

	public void setOOJ_AR(String oOJ_AR) {
		OOJ_AR = oOJ_AR;
	}

	public String getREFOOJ() {
		return REFOOJ;
	}

	public void setREFOOJ(String rEFOOJ) {
		REFOOJ = rEFOOJ;
	}

	public String getREFOOJCD() {
		return REFOOJCD;
	}

	public void setREFOOJCD(String rEFOOJCD) {
		REFOOJCD = rEFOOJCD;
	}

	public String getDUE_DATE() {
		return DUE_DATE;
	}

	public void setDUE_DATE(String dUE_DATE) {
		DUE_DATE = dUE_DATE;
	}

	public String getPART_LAT() {
		return PART_LAT;
	}

	public void setPART_LAT(String pART_LAT) {
		PART_LAT = pART_LAT;
	}

	public String getPART_LONG() {
		return PART_LONG;
	}

	public void setPART_LONG(String pART_LONG) {
		PART_LONG = pART_LONG;
	}

	public String getUPDATE_UID() {
		return UPDATE_UID;
	}

	public void setUPDATE_UID(String uPDATE_UID) {
		UPDATE_UID = uPDATE_UID;
	}

	public Date getUPDATE_DT() {
		return UPDATE_DT;
	}

	public void setUPDATE_DT(Date uPDATE_DT) {
		UPDATE_DT = uPDATE_DT;
	}

	public String getCREATEID() {
		return CREATEID;
	}

	public void setCREATEID(String cREATEID) {
		CREATEID = cREATEID;
	}

	public Date getCREATEDT() {
		return CREATEDT;
	}

	public void setCREATEDT(Date cREATEDT) {
		CREATEDT = cREATEDT;
	}

	public int getHIGH_PRIORITY() {
		return HIGH_PRIORITY;
	}

	public void setHIGH_PRIORITY(int hIGH_PRIORITY) {
		HIGH_PRIORITY = hIGH_PRIORITY;
	}

	public int getFLAG_CD() {
		return FLAG_CD;
	}

	public void setFLAG_CD(int fLAG_CD) {
		FLAG_CD = fLAG_CD;
	}

	public String getHEIGHT() {
		return HEIGHT;
	}

	public void setHEIGHT(String hEIGHT) {
		HEIGHT = hEIGHT;
	}

	public int getRACE_INDIAN() {
		return RACE_INDIAN;
	}

	public void setRACE_INDIAN(int rACE_INDIAN) {
		RACE_INDIAN = rACE_INDIAN;
	}

	public int getRACE_BLACK() {
		return RACE_BLACK;
	}

	public void setRACE_BLACK(int rACE_BLACK) {
		RACE_BLACK = rACE_BLACK;
	}

	public int getRACE_ASIAN() {
		return RACE_ASIAN;
	}

	public void setRACE_ASIAN(int rACE_ASIAN) {
		RACE_ASIAN = rACE_ASIAN;
	}

	public int getRACE_PACIFIC() {
		return RACE_PACIFIC;
	}

	public void setRACE_PACIFIC(int rACE_PACIFIC) {
		RACE_PACIFIC = rACE_PACIFIC;
	}

	public int getRACE_WHITE() {
		return RACE_WHITE;
	}

	public void setRACE_WHITE(int rACE_WHITE) {
		RACE_WHITE = rACE_WHITE;
	}

	public int getRACE_MULTIPLE() {
		return RACE_MULTIPLE;
	}

	public void setRACE_MULTIPLE(int rACE_MULTIPLE) {
		RACE_MULTIPLE = rACE_MULTIPLE;
	}

	public int getRACE_OTHER() {
		return RACE_OTHER;
	}

	public void setRACE_OTHER(int rACE_OTHER) {
		RACE_OTHER = rACE_OTHER;
	}

	public int getRACE_UNKNOWN() {
		return RACE_UNKNOWN;
	}

	public void setRACE_UNKNOWN(int rACE_UNKNOWN) {
		RACE_UNKNOWN = rACE_UNKNOWN;
	}

	public String getALIAS_LNAME() {
		return ALIAS_LNAME;
	}

	public void setALIAS_LNAME(String aLIAS_LNAME) {
		ALIAS_LNAME = aLIAS_LNAME;
	}

	public String getALIAS_FNAME() {
		return ALIAS_FNAME;
	}

	public void setALIAS_FNAME(String aLIAS_FNAME) {
		ALIAS_FNAME = aLIAS_FNAME;
	}

	public String getALIAS_MNAME() {
		return ALIAS_MNAME;
	}

	public void setALIAS_MNAME(String aLIAS_MNAME) {
		ALIAS_MNAME = aLIAS_MNAME;
	}

	public String getEMAIL_ADDRESS() {
		return EMAIL_ADDRESS;
	}

	public void setEMAIL_ADDRESS(String eMAIL_ADDRESS) {
		EMAIL_ADDRESS = eMAIL_ADDRESS;
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

	public String getDOB_TEXT() {
		return DOB_TEXT;
	}

	public void setDOB_TEXT(String dOB_TEXT) {
		DOB_TEXT = dOB_TEXT;
	}

	public String getHIV_STATUS_PARTNER_CD() {
		return HIV_STATUS_PARTNER_CD;
	}

	public void setHIV_STATUS_PARTNER_CD(String hIV_STATUS_PARTNER_CD) {
		HIV_STATUS_PARTNER_CD = hIV_STATUS_PARTNER_CD;
	}

	public String getTEST_DT_PARTNER() {
		return TEST_DT_PARTNER;
	}

	public void setTEST_DT_PARTNER(String tEST_DT_PARTNER) {
		TEST_DT_PARTNER = tEST_DT_PARTNER;
	}

	public String getWEIGHT() {
		return WEIGHT;
	}

	public void setWEIGHT(String wEIGHT) {
		WEIGHT = wEIGHT;
	}

	public String getWORK_PHONE() {
		return WORK_PHONE;
	}

	public void setWORK_PHONE(String wORK_PHONE) {
		WORK_PHONE = wORK_PHONE;
	}

	public String getWORK_HOURS() {
		return WORK_HOURS;
	}

	public void setWORK_HOURS(String wORK_HOURS) {
		WORK_HOURS = wORK_HOURS;
	}

	public int getPARTNER_ELICITED_BY_PNAP() {
		return PARTNER_ELICITED_BY_PNAP;
	}

	public void setPARTNER_ELICITED_BY_PNAP(int pARTNER_ELICITED_BY_PNAP) {
		PARTNER_ELICITED_BY_PNAP = pARTNER_ELICITED_BY_PNAP;
	}

	public int getPARTNER_ELICITED_BY_ACT() {
		return PARTNER_ELICITED_BY_ACT;
	}

	public void setPARTNER_ELICITED_BY_ACT(int pARTNER_ELICITED_BY_ACT) {
		PARTNER_ELICITED_BY_ACT = pARTNER_ELICITED_BY_ACT;
	}

	public String getRACE_OTHER_TEXT() {
		return RACE_OTHER_TEXT;
	}

	public void setRACE_OTHER_TEXT(String rACE_OTHER_TEXT) {
		RACE_OTHER_TEXT = rACE_OTHER_TEXT;
	}

	public String getSUFFIX() {
		return SUFFIX;
	}

	public void setSUFFIX(String sUFFIX) {
		SUFFIX = sUFFIX;
	}

	public String getAKA() {
		return AKA;
	}

	public void setAKA(String aKA) {
		AKA = aKA;
	}

	public String getMAIDEN() {
		return MAIDEN;
	}

	public void setMAIDEN(String mAIDEN) {
		MAIDEN = mAIDEN;
	}

	public String getCOUNTRY_CD() {
		return COUNTRY_CD;
	}

	public void setCOUNTRY_CD(String cOUNTRY_CD) {
		COUNTRY_CD = cOUNTRY_CD;
	}

	public String getBIRTH_SEX() {
		return BIRTH_SEX;
	}

	public void setBIRTH_SEX(String bIRTH_SEX) {
		BIRTH_SEX = bIRTH_SEX;
	}

	public String getADD_GENDER() {
		return ADD_GENDER;
	}

	public void setADD_GENDER(String aDD_GENDER) {
		ADD_GENDER = aDD_GENDER;
	}

	public String getTYPE_REFERRAL() {
		return TYPE_REFERRAL;
	}

	public void setTYPE_REFERRAL(String tYPE_REFERRAL) {
		TYPE_REFERRAL = tYPE_REFERRAL;
	}

	public String getPARTNER_TYPE() {
		return PARTNER_TYPE;
	}

	public void setPARTNER_TYPE(String pARTNER_TYPE) {
		PARTNER_TYPE = pARTNER_TYPE;
	}

	public String getNOTIFY() {
		return NOTIFY;
	}

	public void setNOTIFY(String nOTIFY) {
		NOTIFY = nOTIFY;
	}

	public String getNOTIFY_PLAN() {
		return NOTIFY_PLAN;
	}

	public void setNOTIFY_PLAN(String nOTIFY_PLAN) {
		NOTIFY_PLAN = nOTIFY_PLAN;
	}

	public String getNOTIFY_METHOD() {
		return NOTIFY_METHOD;
	}

	public void setNOTIFY_METHOD(String nOTIFY_METHOD) {
		NOTIFY_METHOD = nOTIFY_METHOD;
	}

	public String getPREV_HIV_TEST() {
		return PREV_HIV_TEST;
	}

	public void setPREV_HIV_TEST(String pREV_HIV_TEST) {
		PREV_HIV_TEST = pREV_HIV_TEST;
	}

	public String getSELF_RPT_RES() {
		return SELF_RPT_RES;
	}

	public void setSELF_RPT_RES(String sELF_RPT_RES) {
		SELF_RPT_RES = sELF_RPT_RES;
	}

	public Date getDATE_LAST_HIV_TEST() {
		return DATE_LAST_HIV_TEST;
	}

	public void setDATE_LAST_HIV_TEST(Date dATE_LAST_HIV_TEST) {
		DATE_LAST_HIV_TEST = dATE_LAST_HIV_TEST;
	}

	public String getREF_TO_TESTING() {
		return REF_TO_TESTING;
	}

	public void setREF_TO_TESTING(String rEF_TO_TESTING) {
		REF_TO_TESTING = rEF_TO_TESTING;
	}

	public Date getREF_DATE() {
		return REF_DATE;
	}

	public void setREF_DATE(Date rEF_DATE) {
		REF_DATE = rEF_DATE;
	}

	public String getTESTED() {
		return TESTED;
	}

	public void setTESTED(String tESTED) {
		TESTED = tESTED;
	}

	public String getPOSTESTC_NEW() {
		return POSTESTC_NEW;
	}

	public void setPOSTESTC_NEW(String pOSTESTC_NEW) {
		POSTESTC_NEW = pOSTESTC_NEW;
	}

	public String getOTHER_IDENTIFIERS() {
		return OTHER_IDENTIFIERS;
	}

	public void setOTHER_IDENTIFIERS(String oTHER_IDENTIFIERS) {
		OTHER_IDENTIFIERS = oTHER_IDENTIFIERS;
	}

	public String getREF2() {
		return REF2;
	}

	public void setREF2(String rEF2) {
		REF2 = rEF2;
	}

	public String getATTEND_FIRST_APPT() {
		return ATTEND_FIRST_APPT;
	}

	public void setATTEND_FIRST_APPT(String aTTEND_FIRST_APPT) {
		ATTEND_FIRST_APPT = aTTEND_FIRST_APPT;
	}

	public String getWEB_ADDRESS_SCREEN() {
		return WEB_ADDRESS_SCREEN;
	}

	public void setWEB_ADDRESS_SCREEN(String wEB_ADDRESS_SCREEN) {
		WEB_ADDRESS_SCREEN = wEB_ADDRESS_SCREEN;
	}

	public String getRACE_ASIAN_SUB() {
		return RACE_ASIAN_SUB;
	}

	public void setRACE_ASIAN_SUB(String rACE_ASIAN_SUB) {
		RACE_ASIAN_SUB = rACE_ASIAN_SUB;
	}

	public String getRACE_PACIFIC_SUB() {
		return RACE_PACIFIC_SUB;
	}

	public void setRACE_PACIFIC_SUB(String rACE_PACIFIC_SUB) {
		RACE_PACIFIC_SUB = rACE_PACIFIC_SUB;
	}

	public String getFIELD_TESTING() {
		return FIELD_TESTING;
	}

	public void setFIELD_TESTING(String fIELD_TESTING) {
		FIELD_TESTING = fIELD_TESTING;
	}

	public String getINTERNET_OUTCOME() {
		return INTERNET_OUTCOME;
	}

	public void setINTERNET_OUTCOME(String iNTERNET_OUTCOME) {
		INTERNET_OUTCOME = iNTERNET_OUTCOME;
	}

	public String getTEST_RESULTS() {
		return TEST_RESULTS;
	}

	public void setTEST_RESULTS(String tEST_RESULTS) {
		TEST_RESULTS = tEST_RESULTS;
	}

	public Date getDATE_COLLECTED() {
		return DATE_COLLECTED;
	}

	public void setDATE_COLLECTED(Date dATE_COLLECTED) {
		DATE_COLLECTED = dATE_COLLECTED;
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

	public String getCOMMENTS() {
		return COMMENTS;
	}

	public void setCOMMENTS(String cOMMENTS) {
		COMMENTS = cOMMENTS;
	}

	public String getRISK_OTHER() {
		return RISK_OTHER;
	}

	public void setRISK_OTHER(String rISK_OTHER) {
		RISK_OTHER = rISK_OTHER;
	}

	

	

}
