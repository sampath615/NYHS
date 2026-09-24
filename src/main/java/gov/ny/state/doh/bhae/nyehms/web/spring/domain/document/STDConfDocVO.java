package gov.ny.state.doh.bhae.nyehms.web.spring.domain.document;

import java.sql.Date;

/**
 * This class is used to create POJO for STD_CONF doc.
 *
 * @author SKA03
 */
public class STDConfDocVO extends NYEHMSDocVO {
	
	 private int DOCUMENT_ID;
	 private Integer CASEKEY;
	 private Long HINSERIALNO;
	 private String FNAME;
	 private String MNAME;
	 private String LNAME;
	 private String PERSON_ADDRESS;
	 private String PERSON_CITY;
	 private String PERSON_STATE;
	 private String PERSON_ZIP;
	 private String PERSON_COUNTY;
	 private String PERSON_PHONE;
	 private String DOB;
	 private String ETHNICITY;
	 private String BIRTH_SEX;
	 private String CURRENT_GENDER;
	 private String DATE_OF_DEATH;
	 
	 private String PROV_FNAME;
	 private String PROV_LNAME;
	 private String PROV_PHONE;
	 private String PROV_ADDRESS;
	 private String PROV_CITY;
	 private String PROV_STATE;
	 private String PROV_ZIP;

	 private String PROV_COUNTY;
	 private String RACE_WHITE;
	 private String RACE_BLACK;
	 private String RACE_INDIAN;
	 private String RACE_ASIAN;
	 private String RACE_PACIFIC;
	 private String RACE_UNKNOWN;
	 private String RACE_OTHER;
	 
	 private String DIAG_DATE;;
	 private Integer DISEASE;
	 private String RISK_SEX_MALE;
	 private String RISK_SEX_FEMALE;
	 private String RISK_SEX_TRANSGENDER;
	 private String RISK_SEX_IDU;
	 private String RISK_SEX_DRUGS;
	 private String RISK_SEX_EXCHANGED;
	 private String RISK_SEX_ANONYMOUS;
	 private String RISK_SEX_MSM;
	 private String RISK_SEX_WITHOUT_CONDOM;
	 private String RISK_DRUGS_USED;
	 private String RISK_DRUG_USED_CRACK;
	 private String RISK_DRUG_USED_COCAINE;
	 private String RISK_DRUG_USED_HEROIN;
	 private String RISK_DRUG_USED_METHAMPHETAMINES;
	 private String RISK_DRUG_USED_NITRATES_POPPERS;
	 private String RISK_DRUG_USED_ED_MEDICATIONS;
	 private String RISK_DRUG_USED_OTHER;
	 private String RISK_DRUG_USED_MARIJUANA;
	 private String RISK_DRUG_USED_PRESCRIPTION_PAINKILLERS;
	 private String RISK_DRUG_USED_ALCOHOL;
	 private String RISK_DRUG_OTHER_TEXT;
	 private String RISK_DRUG_INCARCERATED;
	 private String RISK_DRUG_PARAPHENALIA;
	 private String RISK_DRUG_USED_BENZODIAZEPINES;
	 private String RISK_DRUG_USED_PRESCRIPTION_OPIODS;
	 
	 private String RISK_IDU;
	 private String RISK_OTHER;
	 private String RISK_OTHER_TEXT;
	 private String FIELDNOTE;
	 private String NINEHUNDREDTEST;
	 private String TESTTECHNOLOGY;
	 private String X900POSITIVE;
	 private String X900DXDATE;
	 private String CURRENT900TESTRESULT;
	 private String UPLOAD_ID;
	 private String UPLOAD_DT;
	 private String CURRENT_PROCESS_STATUS;
	 private String FILENAME;
	 private String MATCH_CD;
	 private String MATCH_PASS;
	 private String EHARS_STATUS_CD;
	 private Date EHARS_IMPORT_DATE;
	 private String STATENO;
	 private String PERSON_ADDRESS_RAW;	 
	 private String PROV_ADDRESS_RAW;	 
	 
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
		
	public int getDOCUMENT_ID() {
		return DOCUMENT_ID;
	}
	public void setDOCUMENT_ID(int dOCUMENT_ID) {
		DOCUMENT_ID = dOCUMENT_ID;
	}
	public Integer getCASEKEY() {
		return CASEKEY;
	}
	public void setCASEKEY(Integer cASEKEY) {
		CASEKEY = cASEKEY;
	}
	public Long getHINSERIALNO() {
		return HINSERIALNO;
	}
	public void setHINSERIALNO(Long hINSERIALNO) {
		HINSERIALNO = hINSERIALNO;
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
	public String getPERSON_PHONE() {
		return PERSON_PHONE;
	}
	public void setPERSON_PHONE(String pERSON_PHONE) {
		PERSON_PHONE = pERSON_PHONE;
	}
	public String getDOB() {
		return DOB;
	}
	public void setDOB(String dOB) {
		DOB = dOB;
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
	public String getCURRENT_GENDER() {
		return CURRENT_GENDER;
	}
	public void setCURRENT_GENDER(String cURRENT_GENDER) {
		CURRENT_GENDER = cURRENT_GENDER;
	}
	public String getDATE_OF_DEATH() {
		return DATE_OF_DEATH;
	}
	public void setDATE_OF_DEATH(String dATE_OF_DEATH) {
		DATE_OF_DEATH = dATE_OF_DEATH;
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
	public String getPROV_PHONE() {
		return PROV_PHONE;
	}
	public void setPROV_PHONE(String pROV_PHONE) {
		PROV_PHONE = pROV_PHONE;
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
	public void setPROV_ST(String pROV_STATE) {
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
	public String getRACE_OTHER() {
		return RACE_OTHER;
	}
	public void setRACE_OTHER(String rACE_OTHER) {
		RACE_OTHER = rACE_OTHER;
	}
	public String getDIAG_DATE() {
		return DIAG_DATE;
	}
	public void setDIAG_DATE(String dIAG_DATE) {
		DIAG_DATE = dIAG_DATE;
	}
	public Integer getDISEASE() {
		return DISEASE;
	}
	public void setDISEASE(Integer dISEASE) {
		DISEASE = dISEASE;
	}
	public String getRISK_SEX_MALE() {
		return RISK_SEX_MALE;
	}
	public void setRISK_SEX_MALE(String rISK_SEX_MALE) {
		RISK_SEX_MALE = rISK_SEX_MALE;
	}
	public String getRISK_SEX_FEMALE() {
		return RISK_SEX_FEMALE;
	}
	public void setRISK_SEX_FEMALE(String rISK_SEX_FEMALE) {
		RISK_SEX_FEMALE = rISK_SEX_FEMALE;
	}
	public String getRISK_SEX_TRANSGENDER() {
		return RISK_SEX_TRANSGENDER;
	}
	public void setRISK_SEX_TRANSGENDER(String rISK_SEX_TRANSGENDER) {
		RISK_SEX_TRANSGENDER = rISK_SEX_TRANSGENDER;
	}
	public String getRISK_SEX_IDU() {
		return RISK_SEX_IDU;
	}
	public void setRISK_SEX_IDU(String rISK_SEX_IDU) {
		RISK_SEX_IDU = rISK_SEX_IDU;
	}
	public String getRISK_SEX_DRUGS() {
		return RISK_SEX_DRUGS;
	}
	public void setRISK_SEX_DRUGS(String rISK_SEX_DRUGS) {
		RISK_SEX_DRUGS = rISK_SEX_DRUGS;
	}
	public String getRISK_SEX_EXCHANGED() {
		return RISK_SEX_EXCHANGED;
	}
	public void setRISK_SEX_EXCHANGED(String rISK_SEX_EXCHANGED) {
		RISK_SEX_EXCHANGED = rISK_SEX_EXCHANGED;
	}
	public String getRISK_SEX_ANONYMOUS() {
		return RISK_SEX_ANONYMOUS;
	}
	public void setRISK_SEX_ANONYMOUS(String rISK_SEX_ANONYMOUS) {
		RISK_SEX_ANONYMOUS = rISK_SEX_ANONYMOUS;
	}
	public String getRISK_SEX_MSM() {
		return RISK_SEX_MSM;
	}
	public void setRISK_SEX_MSM(String rISK_SEX_MSM) {
		RISK_SEX_MSM = rISK_SEX_MSM;
	}
	public String getRISK_SEX_WITHOUT_CONDOM() {
		return RISK_SEX_WITHOUT_CONDOM;
	}
	public void setRISK_SEX_WITHOUT_CONDOM(String rISK_SEX_WITHOUT_CONDOM) {
		RISK_SEX_WITHOUT_CONDOM = rISK_SEX_WITHOUT_CONDOM;
	}
	public String getRISK_DRUGS_USED() {
		return RISK_DRUGS_USED;
	}
	public void setRISK_DRUGS_USED(String rISK_DRUGS_USED) {
		RISK_DRUGS_USED = rISK_DRUGS_USED;
	}
	public String getRISK_DRUG_USED_CRACK() {
		return RISK_DRUG_USED_CRACK;
	}
	public void setRISK_DRUG_USED_CRACK(String rISK_DRUG_USED_CRACK) {
		RISK_DRUG_USED_CRACK = rISK_DRUG_USED_CRACK;
	}
	public String getRISK_DRUG_USED_COCAINE() {
		return RISK_DRUG_USED_COCAINE;
	}
	public void setRISK_DRUG_USED_COCAINE(String rISK_DRUG_USED_COCAINE) {
		RISK_DRUG_USED_COCAINE = rISK_DRUG_USED_COCAINE;
	}
	public String getRISK_DRUG_USED_HEROIN() {
		return RISK_DRUG_USED_HEROIN;
	}
	public void setRISK_DRUG_USED_HEROIN(String rISK_DRUG_USED_HEROIN) {
		RISK_DRUG_USED_HEROIN = rISK_DRUG_USED_HEROIN;
	}
	public String getRISK_DRUG_USED_METHAMPHETAMINES() {
		return RISK_DRUG_USED_METHAMPHETAMINES;
	}
	public void setRISK_DRUG_USED_METHAMPHETAMINES(String rISK_DRUG_USED_METHAMPHETAMINES) {
		RISK_DRUG_USED_METHAMPHETAMINES = rISK_DRUG_USED_METHAMPHETAMINES;
	}
	public String getRISK_DRUG_USED_NITRATES_POPPERS() {
		return RISK_DRUG_USED_NITRATES_POPPERS;
	}
	public void setRISK_DRUG_USED_NITRATES_POPPERS(String rISK_DRUG_USED_NITRATES_POPPERS) {
		RISK_DRUG_USED_NITRATES_POPPERS = rISK_DRUG_USED_NITRATES_POPPERS;
	}
	public String getRISK_DRUG_USED_ED_MEDICATIONS() {
		return RISK_DRUG_USED_ED_MEDICATIONS;
	}
	public void setRISK_DRUG_USED_ED_MEDICATIONS(String rISK_DRUG_USED_ED_MEDICATIONS) {
		RISK_DRUG_USED_ED_MEDICATIONS = rISK_DRUG_USED_ED_MEDICATIONS;
	}
	public String getRISK_DRUG_USED_OTHER() {
		return RISK_DRUG_USED_OTHER;
	}
	public void setRISK_DRUG_USED_OTHER(String rISK_DRUG_USED_OTHER) {
		RISK_DRUG_USED_OTHER = rISK_DRUG_USED_OTHER;
	}
	public String getRISK_DRUG_USED_MARIJUANA() {
		return RISK_DRUG_USED_MARIJUANA;
	}
	public void setRISK_DRUG_USED_MARIJUANA(String rISK_DRUG_USED_MARIJUANA) {
		RISK_DRUG_USED_MARIJUANA = rISK_DRUG_USED_MARIJUANA;
	}
	public String getRISK_DRUG_USED_PRESCRIPTION_PAINKILLERS() {
		return RISK_DRUG_USED_PRESCRIPTION_PAINKILLERS;
	}
	public void setRISK_DRUG_USED_PRESCRIPTION_PAINKILLERS(String rISK_DRUG_USED_PRESCRIPTION_PAINKILLERS) {
		RISK_DRUG_USED_PRESCRIPTION_PAINKILLERS = rISK_DRUG_USED_PRESCRIPTION_PAINKILLERS;
	}
	public String getRISK_DRUG_USED_ALCOHOL() {
		return RISK_DRUG_USED_ALCOHOL;
	}
	public void setRISK_DRUG_USED_ALCOHOL(String rISK_DRUG_USED_ALCOHOL) {
		RISK_DRUG_USED_ALCOHOL = rISK_DRUG_USED_ALCOHOL;
	}
	public String getRISK_DRUG_OTHER_TEXT() {
		return RISK_DRUG_OTHER_TEXT;
	}
	public void setRISK_DRUG_OTHER_TEXT(String rISK_DRUG_OTHER_TEXT) {
		RISK_DRUG_OTHER_TEXT = rISK_DRUG_OTHER_TEXT;
	}
	public String getRISK_DRUG_INCARCERATED() {
		return RISK_DRUG_INCARCERATED;
	}
	public void setRISK_DRUG_INCARCERATED(String rISK_DRUG_INCARCERATED) {
		RISK_DRUG_INCARCERATED = rISK_DRUG_INCARCERATED;
	}
	public String getRISK_DRUG_PARAPHENALIA() {
		return RISK_DRUG_PARAPHENALIA;
	}
	public void setRISK_DRUG_PARAPHENALIA(String rISK_DRUG_PARAPHENALIA) {
		RISK_DRUG_PARAPHENALIA = rISK_DRUG_PARAPHENALIA;
	}
	public String getRISK_IDU() {
		return RISK_IDU;
	}
	public void setRISK_IDU(String rISK_IDU) {
		RISK_IDU = rISK_IDU;
	}
	public String getRISK_OTHER() {
		return RISK_OTHER;
	}
	public void setRISK_OTHER(String rISK_OTHER) {
		RISK_OTHER = rISK_OTHER;
	}
	public String getRISK_OTHER_TEXT() {
		return RISK_OTHER_TEXT;
	}
	public void setRISK_OTHER_TEXT(String rISK_OTHER_TEXT) {
		RISK_OTHER_TEXT = rISK_OTHER_TEXT;
	}
	public String getFIELDNOTE() {
		return FIELDNOTE;
	}
	public void setFIELDNOTE(String fIELDNOTE) {
		FIELDNOTE = fIELDNOTE;
	}
	public String getNINEHUNDREDTEST() {
		return NINEHUNDREDTEST;
	}
	public void setNINEHUNDREDTEST(String nINEHUNDREDTEST) {
		NINEHUNDREDTEST = nINEHUNDREDTEST;
	}
	public String getTESTTECHNOLOGY() {
		return TESTTECHNOLOGY;
	}
	public void setTESTTECHNOLOGY(String tESTTECHNOLOGY) {
		TESTTECHNOLOGY = tESTTECHNOLOGY;
	}
	public String getX900POSITIVE() {
		return X900POSITIVE;
	}
	public void setX900POSITIVE(String x900positive) {
		X900POSITIVE = x900positive;
	}
	public String getX900DXDATE() {
		return X900DXDATE;
	}
	public void setX900DXDATE(String x900dxdate) {
		X900DXDATE = x900dxdate;
	}
	public String getCURRENT900TESTRESULT() {
		return CURRENT900TESTRESULT;
	}
	public void setCURRENT900TESTRESULT(String cURRENT900TESTRESULT) {
		CURRENT900TESTRESULT = cURRENT900TESTRESULT;
	}
	public String getUPLOAD_ID() {
		return UPLOAD_ID;
	}
	public void setUPLOAD_ID(String uPLOAD_ID) {
		UPLOAD_ID = uPLOAD_ID;
	}
	public String getUPLOAD_DT() {
		return UPLOAD_DT;
	}
	public void setUPLOAD_DT(String uPLOAD_DT) {
		UPLOAD_DT = uPLOAD_DT;
	}
	public String getCURRENT_PROCESS_STATUS() {
		return CURRENT_PROCESS_STATUS;
	}
	public void setCURRENT_PROCESS_STATUS(String cURRENT_PROCESS_STATUS) {
		CURRENT_PROCESS_STATUS = cURRENT_PROCESS_STATUS;
	}
	public String getFILENAME() {
		return FILENAME;
	}
	public void setFILENAME(String fILENAME) {
		FILENAME = fILENAME;
	}
	public String getMATCH_CD() {
		return MATCH_CD;
	}
	public void setMATCH_CD(String mATCH_CD) {
		MATCH_CD = mATCH_CD;
	}
	public String getMATCH_PASS() {
		return MATCH_PASS;
	}
	public void setMATCH_PASS(String mATCH_PASS) {
		MATCH_PASS = mATCH_PASS;
	}
	public String getEHARS_STATUS_CD() {
		return EHARS_STATUS_CD;
	}
	public void setEHARS_STATUS_CD(String eHARS_STATUS_CD) {
		EHARS_STATUS_CD = eHARS_STATUS_CD;
	}
	public Date getEHARS_IMPORT_DATE() {
		return EHARS_IMPORT_DATE;
	}
	public void setEHARS_IMPORT_DATE(Date eHARS_IMPORT_DATE) {
		EHARS_IMPORT_DATE = eHARS_IMPORT_DATE;
	}
	public String getSTATENO() {
		return STATENO;
	}
	public void setSTATENO(String sTATENO) {
		STATENO = sTATENO;
	}
	public String getPERSON_ADDRESS_RAW() {
		return PERSON_ADDRESS_RAW;
	}
	public void setPERSON_ADDRESS_RAW(String pERSON_ADDRESS_RAW) {
		PERSON_ADDRESS_RAW = pERSON_ADDRESS_RAW;
	}
	public String getPROV_ADDRESS_RAW() {
		return PROV_ADDRESS_RAW;
	}
	public void setPROV_ADDRESS_RAW(String pROV_ADDRESS_RAW) {
		PROV_ADDRESS_RAW = pROV_ADDRESS_RAW;
	}
	public String getRISK_DRUG_USED_BENZODIAZEPINES() {
		return RISK_DRUG_USED_BENZODIAZEPINES;
	}
	public void setRISK_DRUG_USED_BENZODIAZEPINES(String rISK_DRUG_USED_BENZODIAZEPINES) {
		RISK_DRUG_USED_BENZODIAZEPINES = rISK_DRUG_USED_BENZODIAZEPINES;
	}
	public String getRISK_DRUG_USED_PRESCRIPTION_OPIODS() {
		return RISK_DRUG_USED_PRESCRIPTION_OPIODS;
	}
	public void setRISK_DRUG_USED_PRESCRIPTION_OPIODS(String rISK_DRUG_USED_PRESCRIPTION_OPIODS) {
		RISK_DRUG_USED_PRESCRIPTION_OPIODS = rISK_DRUG_USED_PRESCRIPTION_OPIODS;
	}
}
