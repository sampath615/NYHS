package gov.ny.state.doh.bhae.nyehms.web.spring.domain.document;

import java.util.Date;

/**
 * This is pojo class for ADAP document. This is used for Mybatis object to
 * create java map for select, insert, update
 *
 * @author vxp06
 */

public class ADAPDocVO extends NYEHMSDocVO{
	
	
	private String CURRENT_GENDER;
	private String BIRTH_SEX;
	private String EHARS_STATUS_CD;
	private String ASSESS_ASSIGN;
	private String DOB_TEXT;
	private String PERSON_LATITUDE;
	private String CERT_PROV_LATITUDE;
	private String PCP_PROV_LATITUDE;
	private String RX_PROVIDER_TYPE;
	private String RX_PROV_LATITUDE;
	private String FIRST_HIV_TEST_DATE_TEXT;
	private String REVIEW_UID;
	private String CITYNO;
	private String PERSON_LONGITUDE;
	private String CERT_PROV_LONGITUDE;
	private String PCP_PROV_LONGITUDE;
	private String RX_PROV_LONGITUDE;
	private String CERT_PROV_NPI;
	private String PCP_PROV_NPI;
	private String RX_PROV_NPI;
	private String PERSON_STATE;
	private String CERT_PROV_STATE;
	private String PCP_PROV_STATE;
	private String PROV_CENSUS_TRACT_SUFFIX_PCP;
	private String RX_PROV_STATE;
	private String MATCH_CD;
	private String PERSON_COUNTY;
	private String PERSON_CITY;
	private String CERT_PROV_CITY;
	private String PCP_PROV_CITY;
	private String RX_PROV_CITY;
	private String PERSON_PLUS4;
	private String PERSON_CENSUS_TRACT;
	private String CERT_PROV_PLUS4;
	private String CERT_PROV_CENSUS_TRACT;
	private String PCP_PROV_PLUS4;
	private String PCP_PROV_CENSUS_TRACT;
	private String RX_PROV_PLUS4;
	private String RX_PROV_CENSUS_TRACT;
	private String CARE_OF;
	private String ADAP_FILE;
	private String PERSON_ZIP;
	private String CERT_PROV_ZIP;
	private String PCP_PROV_ZIP;
	private String RX_PROV_ZIP;
	private String SSN;
	private Date REVIEW_DT;
	private Date NYC_SHIP_DT;
	private Date EHARS_IMPORT_DATE;
	private Date RECEIVED_DATE;
	private Date CREATE_DATE;
	private int DOCUMENT_ID;
	private int RAW_PROVIDER_ADDRESS_ID;
	private int CURRENT_PROCESS_STATUS;
	private int QS_PARENT_DOC_ID;
	private float MATCH_WEIGHT;
	private String RACE_INDIAN;
	private String RACE_BLACK;
	private String RACE_ASIAN;
	private String RACE_PACIFIC;
	private String RACE_WHITE;
	private String RACE_MULTIPLE;
	private String RACE_OTHER;
	private String RACE_UNKNOWN;
	private String CREATE_UID;
	private String MATCHED_STATENO;
	private String MEDICAID_NUMBER;
	private String ETHNICCD;
	private String RACE_CD;
	private String SWM;
	private String SWF;
	private String IDU;
	private String SEX_PARTNER_IDU;
	private String SEX_PARTNER_HIV;
	private String RECV_BLOOD_PROD;
	private String PERINATAL;
	private String RISK_OTHER;
	private String HEALTHCARE_WORKER;
	private String FNAME;
	private String PERSON_PHONE;
	private String CERT_PROV_FNAME;
	private String CERT_PROV_MNAME;
	private String CERT_PROV_COUNTY;
	private String CERT_PROV_PHONE;
	private String PCP_PROV_FNAME;
	private String PCP_PROV_MNAME;
	private String PCP_PROV_COUNTY;
	private String PCP_PROV_PHONE;
	private String RX_PROV_FNAME;
	private String RX_PROV_MNAME;
	private String RX_PROV_COUNTY;
	private String RX_PROV_PHONE;
	private String CERT_PROV_ADDRESS;
	private String PCP_PROV_ADDRESS;
	private String RX_PROV_ADDRESS;
	private String LNAME;
	private String MNAME;
	private String ADAP_ID;
	private String CERT_PROV_LNAME;
	private String PCP_PROV_LNAME;
	private String RX_PROV_LNAME;
	private String ADAP_SRC_ID;
	private String FLAG_CHOSEN_PROVIDER;
	private String MATCH_PASS;
	private String SUFFIX;
	private String PERSON_ADDRESS;
	private String DOB;
	  
	  
	private String street_raw; 
	private String rxprov_addr_raw;
	private String pcprov_addr_raw;
	private String certprov_addr_raw;
	
	private String DISCARD_STATUS;
	
	
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
	
	
	public String getCURRENT_GENDER() {
		return CURRENT_GENDER;
	}
	public void setCURRENT_GENDER(String cURRENT_GENDER) {
		CURRENT_GENDER = cURRENT_GENDER;
	}
	public String getEHARS_STATUS_CD() {
		return EHARS_STATUS_CD;
	}
	public void setEHARS_STATUS_CD(String eHARS_STATUS_CD) {
		EHARS_STATUS_CD = eHARS_STATUS_CD;
	}
	public String getASSESS_ASSIGN() {
		return ASSESS_ASSIGN;
	}
	public void setASSESS_ASSIGN(String aSSESS_ASSIGN) {
		ASSESS_ASSIGN = aSSESS_ASSIGN;
	}
	public String getDOB_TEXT() {
		return DOB_TEXT;
	}
	public void setDOB_TEXT(String dOB_TEXT) {
		DOB_TEXT = dOB_TEXT;
	}
	public String getPERSON_LATITUDE() {
		return PERSON_LATITUDE;
	}
	public void setPERSON_LATITUDE(String pERSON_LATITUDE) {
		PERSON_LATITUDE = pERSON_LATITUDE;
	}
	public String getCERT_PROV_LATITUDE() {
		return CERT_PROV_LATITUDE;
	}
	public void setCERT_PROV_LATITUDE(String cERT_PROV_LATITUDE) {
		CERT_PROV_LATITUDE = cERT_PROV_LATITUDE;
	}
	public String getPCP_PROV_LATITUDE() {
		return PCP_PROV_LATITUDE;
	}
	public void setPCP_PROV_LATITUDE(String pCP_PROV_LATITUDE) {
		PCP_PROV_LATITUDE = pCP_PROV_LATITUDE;
	}
	public String getRX_PROVIDER_TYPE() {
		return RX_PROVIDER_TYPE;
	}
	public void setRX_PROVIDER_TYPE(String rX_PROVIDER_TYPE) {
		RX_PROVIDER_TYPE = rX_PROVIDER_TYPE;
	}
	public String getRX_PROV_LATITUDE() {
		return RX_PROV_LATITUDE;
	}
	public void setRX_PROV_LATITUDE(String rX_PROV_LATITUDE) {
		RX_PROV_LATITUDE = rX_PROV_LATITUDE;
	}
	public String getFIRST_HIV_TEST_DATE_TEXT() {
		return FIRST_HIV_TEST_DATE_TEXT;
	}
	public void setFIRST_HIV_TEST_DATE_TEXT(String fIRST_HIV_TEST_DATE_TEXT) {
		FIRST_HIV_TEST_DATE_TEXT = fIRST_HIV_TEST_DATE_TEXT;
	}
	public String getREVIEW_UID() {
		return REVIEW_UID;
	}
	public void setREVIEW_UID(String rEVIEW_UID) {
		REVIEW_UID = rEVIEW_UID;
	}
	public String getCITYNO() {
		return CITYNO;
	}
	public void setCITYNO(String cITYNO) {
		CITYNO = cITYNO;
	}
	public String getPERSON_LONGITUDE() {
		return PERSON_LONGITUDE;
	}
	public void setPERSON_LONGITUDE(String pERSON_LONGITUDE) {
		PERSON_LONGITUDE = pERSON_LONGITUDE;
	}
	public String getCERT_PROV_LONGITUDE() {
		return CERT_PROV_LONGITUDE;
	}
	public void setCERT_PROV_LONGITUDE(String cERT_PROV_LONGITUDE) {
		CERT_PROV_LONGITUDE = cERT_PROV_LONGITUDE;
	}
	public String getPCP_PROV_LONGITUDE() {
		return PCP_PROV_LONGITUDE;
	}
	public void setPCP_PROV_LONGITUDE(String pCP_PROV_LONGITUDE) {
		PCP_PROV_LONGITUDE = pCP_PROV_LONGITUDE;
	}
	public String getRX_PROV_LONGITUDE() {
		return RX_PROV_LONGITUDE;
	}
	public void setRX_PROV_LONGITUDE(String rX_PROV_LONGITUDE) {
		RX_PROV_LONGITUDE = rX_PROV_LONGITUDE;
	}
	public String getCERT_PROV_NPI() {
		return CERT_PROV_NPI;
	}
	public void setCERT_PROV_NPI(String cERT_PROV_NPI) {
		CERT_PROV_NPI = cERT_PROV_NPI;
	}
	public String getPCP_PROV_NPI() {
		return PCP_PROV_NPI;
	}
	public void setPCP_PROV_NPI(String pCP_PROV_NPI) {
		PCP_PROV_NPI = pCP_PROV_NPI;
	}
	public String getRX_PROV_NPI() {
		return RX_PROV_NPI;
	}
	public void setRX_PROV_NPI(String rX_PROV_NPI) {
		RX_PROV_NPI = rX_PROV_NPI;
	}
	public String getPERSON_STATE() {
		return PERSON_STATE;
	}
	public void setPERSON_STATE(String pERSON_STATE) {
		PERSON_STATE = pERSON_STATE;
	}
	public String getCERT_PROV_STATE() {
		return CERT_PROV_STATE;
	}
	public void setCERT_PROV_STATE(String cERT_PROV_STATE) {
		CERT_PROV_STATE = cERT_PROV_STATE;
	}
	public String getPCP_PROV_STATE() {
		return PCP_PROV_STATE;
	}
	public void setPCP_PROV_STATE(String pCP_PROV_STATE) {
		PCP_PROV_STATE = pCP_PROV_STATE;
	}
	public String getPROV_CENSUS_TRACT_SUFFIX_PCP() {
		return PROV_CENSUS_TRACT_SUFFIX_PCP;
	}
	public void setPROV_CENSUS_TRACT_SUFFIX_PCP(String pROV_CENSUS_TRACT_SUFFIX_PCP) {
		PROV_CENSUS_TRACT_SUFFIX_PCP = pROV_CENSUS_TRACT_SUFFIX_PCP;
	}
	public String getRX_PROV_STATE() {
		return RX_PROV_STATE;
	}
	public void setRX_PROV_STATE(String rX_PROV_STATE) {
		RX_PROV_STATE = rX_PROV_STATE;
	}
	public String getMATCH_CD() {
		return MATCH_CD;
	}
	public void setMATCH_CD(String mATCH_CD) {
		MATCH_CD = mATCH_CD;
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
	public String getCERT_PROV_CITY() {
		return CERT_PROV_CITY;
	}
	public void setCERT_PROV_CITY(String cERT_PROV_CITY) {
		CERT_PROV_CITY = cERT_PROV_CITY;
	}
	public String getPCP_PROV_CITY() {
		return PCP_PROV_CITY;
	}
	public void setPCP_PROV_CITY(String pCP_PROV_CITY) {
		PCP_PROV_CITY = pCP_PROV_CITY;
	}
	public String getRX_PROV_CITY() {
		return RX_PROV_CITY;
	}
	public void setRX_PROV_CITY(String rX_PROV_CITY) {
		RX_PROV_CITY = rX_PROV_CITY;
	}
	public String getPERSON_PLUS4() {
		return PERSON_PLUS4;
	}
	public void setPERSON_PLUS4(String pERSON_PLUS4) {
		PERSON_PLUS4 = pERSON_PLUS4;
	}
	public String getPERSON_CENSUS_TRACT() {
		return PERSON_CENSUS_TRACT;
	}
	public void setPERSON_CENSUS_TRACT(String pERSON_CENSUS_TRACT) {
		PERSON_CENSUS_TRACT = pERSON_CENSUS_TRACT;
	}
	public String getCERT_PROV_PLUS4() {
		return CERT_PROV_PLUS4;
	}
	public void setCERT_PROV_PLUS4(String cERT_PROV_PLUS4) {
		CERT_PROV_PLUS4 = cERT_PROV_PLUS4;
	}
	public String getCERT_PROV_CENSUS_TRACT() {
		return CERT_PROV_CENSUS_TRACT;
	}
	public void setCERT_PROV_CENSUS_TRACT(String cERT_PROV_CENSUS_TRACT) {
		CERT_PROV_CENSUS_TRACT = cERT_PROV_CENSUS_TRACT;
	}
	public String getPCP_PROV_PLUS4() {
		return PCP_PROV_PLUS4;
	}
	public void setPCP_PROV_PLUS4(String pCP_PROV_PLUS4) {
		PCP_PROV_PLUS4 = pCP_PROV_PLUS4;
	}
	
	public String getPCP_PROV_CENSUS_TRACT() {
		return PCP_PROV_CENSUS_TRACT;
	}

	public void setPCP_PROV_CENSUS_TRACT(String pCP_PROV_CENSUS_TRACT) {
		PCP_PROV_CENSUS_TRACT = pCP_PROV_CENSUS_TRACT;
	}

	public String getRX_PROV_PLUS4() {
		return RX_PROV_PLUS4;
	}
	public void setRX_PROV_PLUS4(String rX_PROV_PLUS4) {
		RX_PROV_PLUS4 = rX_PROV_PLUS4;
	}
	public String getRX_PROV_CENSUS_TRACT() {
		return RX_PROV_CENSUS_TRACT;
	}
	public void setRX_PROV_CENSUS_TRACT(String rX_PROV_CENSUS_TRACT) {
		RX_PROV_CENSUS_TRACT = rX_PROV_CENSUS_TRACT;
	}
	public String getCARE_OF() {
		return CARE_OF;
	}
	public void setCARE_OF(String cARE_OF) {
		CARE_OF = cARE_OF;
	}
	public String getADAP_FILE() {
		return ADAP_FILE;
	}
	public void setADAP_FILE(String aDAP_FILE) {
		ADAP_FILE = aDAP_FILE;
	}
	public String getPERSON_ZIP() {
		return PERSON_ZIP;
	}
	public void setPERSON_ZIP(String pERSON_ZIP) {
		PERSON_ZIP = pERSON_ZIP;
	}
	public String getCERT_PROV_ZIP() {
		return CERT_PROV_ZIP;
	}
	public void setCERT_PROV_ZIP(String cERT_PROV_ZIP) {
		CERT_PROV_ZIP = cERT_PROV_ZIP;
	}
	public String getPCP_PROV_ZIP() {
		return PCP_PROV_ZIP;
	}
	public void setPCP_PROV_ZIP(String pCP_PROV_ZIP) {
		PCP_PROV_ZIP = pCP_PROV_ZIP;
	}
	public String getRX_PROV_ZIP() {
		return RX_PROV_ZIP;
	}
	public void setRX_PROV_ZIP(String rX_PROV_ZIP) {
		RX_PROV_ZIP = rX_PROV_ZIP;
	}
	public String getSSN() {
		return SSN;
	}
	public void setSSN(String sSN) {
		SSN = sSN;
	}
	public Date getREVIEW_DT() {
		return REVIEW_DT;
	}
	public void setREVIEW_DT(Date rEVIEW_DT) {
		REVIEW_DT = rEVIEW_DT;
	}
	public Date getNYC_SHIP_DT() {
		return NYC_SHIP_DT;
	}
	public void setNYC_SHIP_DT(Date nYC_SHIP_DT) {
		NYC_SHIP_DT = nYC_SHIP_DT;
	}
	public Date getEHARS_IMPORT_DATE() {
		return EHARS_IMPORT_DATE;
	}
	public void setEHARS_IMPORT_DATE(Date eHARS_IMPORT_DATE) {
		EHARS_IMPORT_DATE = eHARS_IMPORT_DATE;
	}
	public Date getRECEIVED_DATE() {
		return RECEIVED_DATE;
	}
	public void setRECEIVED_DATE(Date rECEIVED_DATE) {
		RECEIVED_DATE = rECEIVED_DATE;
	}
	public Date getCREATE_DATE() {
		return CREATE_DATE;
	}
	public void setCREATE_DATE(Date cREATE_DATE) {
		CREATE_DATE = cREATE_DATE;
	}
	public int getDOCUMENT_ID() {
		return DOCUMENT_ID;
	}
	public void setDOCUMENT_ID(int dOCUMENT_ID) {
		DOCUMENT_ID = dOCUMENT_ID;
	}
	public int getRAW_PROVIDER_ADDRESS_ID() {
		return RAW_PROVIDER_ADDRESS_ID;
	}
	public void setRAW_PROVIDER_ADDRESS_ID(int rAW_PROVIDER_ADDRESS_ID) {
		RAW_PROVIDER_ADDRESS_ID = rAW_PROVIDER_ADDRESS_ID;
	}
	public int getCURRENT_PROCESS_STATUS() {
		return CURRENT_PROCESS_STATUS;
	}
	public void setCURRENT_PROCESS_STATUS(int cURRENT_PROCESS_STATUS) {
		CURRENT_PROCESS_STATUS = cURRENT_PROCESS_STATUS;
	}
	public int getQS_PARENT_DOC_ID() {
		return QS_PARENT_DOC_ID;
	}
	public void setQS_PARENT_DOC_ID(int qS_PARENT_DOC_ID) {
		QS_PARENT_DOC_ID = qS_PARENT_DOC_ID;
	}
	public float getMATCH_WEIGHT() {
		return MATCH_WEIGHT;
	}
	public void setMATCH_WEIGHT(float mATCH_WEIGHT) {
		MATCH_WEIGHT = mATCH_WEIGHT;
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
	public String getCREATE_UID() {
		return CREATE_UID;
	}
	public void setCREATE_UID(String cREATE_UID) {
		CREATE_UID = cREATE_UID;
	}
	public String getMATCHED_STATENO() {
		return MATCHED_STATENO;
	}
	public void setMATCHED_STATENO(String mATCHED_STATENO) {
		MATCHED_STATENO = mATCHED_STATENO;
	}
	public String getMEDICAID_NUMBER() {
		return MEDICAID_NUMBER;
	}
	public void setMEDICAID_NUMBER(String mEDICAID_NUMBER) {
		MEDICAID_NUMBER = mEDICAID_NUMBER;
	}
	public String getETHNICCD() {
		return ETHNICCD;
	}
	public void setETHNICCD(String eTHNICCD) {
		ETHNICCD = eTHNICCD;
	}
	public String getRACE_CD() {
		return RACE_CD;
	}
	public void setRACE_CD(String rACE_CD) {
		RACE_CD = rACE_CD;
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
	public String getHEALTHCARE_WORKER() {
		return HEALTHCARE_WORKER;
	}
	public void setHEALTHCARE_WORKER(String hEALTHCARE_WORKER) {
		HEALTHCARE_WORKER = hEALTHCARE_WORKER;
	}
	public String getFNAME() {
		return FNAME;
	}
	public void setFNAME(String fNAME) {
		FNAME = fNAME;
	}
	public String getPERSON_PHONE() {
		return PERSON_PHONE;
	}
	public void setPERSON_PHONE(String pERSON_PHONE) {
		PERSON_PHONE = pERSON_PHONE;
	}
	public String getCERT_PROV_FNAME() {
		return CERT_PROV_FNAME;
	}
	public void setCERT_PROV_FNAME(String cERT_PROV_FNAME) {
		CERT_PROV_FNAME = cERT_PROV_FNAME;
	}
	public String getCERT_PROV_MNAME() {
		return CERT_PROV_MNAME;
	}
	public void setCERT_PROV_MNAME(String cERT_PROV_MNAME) {
		CERT_PROV_MNAME = cERT_PROV_MNAME;
	}
	public String getCERT_PROV_COUNTY() {
		return CERT_PROV_COUNTY;
	}
	public void setCERT_PROV_COUNTY(String cERT_PROV_COUNTY) {
		CERT_PROV_COUNTY = cERT_PROV_COUNTY;
	}
	public String getCERT_PROV_PHONE() {
		return CERT_PROV_PHONE;
	}
	public void setCERT_PROV_PHONE(String cERT_PROV_PHONE) {
		CERT_PROV_PHONE = cERT_PROV_PHONE;
	}
	public String getPCP_PROV_FNAME() {
		return PCP_PROV_FNAME;
	}
	public void setPCP_PROV_FNAME(String pCP_PROV_FNAME) {
		PCP_PROV_FNAME = pCP_PROV_FNAME;
	}
	public String getPCP_PROV_MNAME() {
		return PCP_PROV_MNAME;
	}
	public void setPCP_PROV_MNAME(String pCP_PROV_MNAME) {
		PCP_PROV_MNAME = pCP_PROV_MNAME;
	}
	
	public String getPCP_PROV_COUNTY() {
		return PCP_PROV_COUNTY;
	}

	public void setPCP_PROV_COUNTY(String pCP_PROV_COUNTY) {
		PCP_PROV_COUNTY = pCP_PROV_COUNTY;
	}

	public String getPCP_PROV_PHONE() {
		return PCP_PROV_PHONE;
	}
	public void setPCP_PROV_PHONE(String pCP_PROV_PHONE) {
		PCP_PROV_PHONE = pCP_PROV_PHONE;
	}
	public String getRX_PROV_FNAME() {
		return RX_PROV_FNAME;
	}
	public void setRX_PROV_FNAME(String rX_PROV_FNAME) {
		RX_PROV_FNAME = rX_PROV_FNAME;
	}
	public String getRX_PROV_MNAME() {
		return RX_PROV_MNAME;
	}
	public void setRX_PROV_MNAME(String rX_PROV_MNAME) {
		RX_PROV_MNAME = rX_PROV_MNAME;
	}
	public String getRX_PROV_COUNTY() {
		return RX_PROV_COUNTY;
	}
	public void setRX_PROV_COUNTY(String rX_PROV_COUNTY) {
		RX_PROV_COUNTY = rX_PROV_COUNTY;
	}
	public String getRX_PROV_PHONE() {
		return RX_PROV_PHONE;
	}
	public void setRX_PROV_PHONE(String rX_PROV_PHONE) {
		RX_PROV_PHONE = rX_PROV_PHONE;
	}
	public String getCERT_PROV_ADDRESS() {
		return CERT_PROV_ADDRESS;
	}
	public void setCERT_PROV_ADDRESS(String cERT_PROV_ADDRESS) {
		CERT_PROV_ADDRESS = cERT_PROV_ADDRESS;
	}
	public String getPCP_PROV_ADDRESS() {
		return PCP_PROV_ADDRESS;
	}
	public void setPCP_PROV_ADDRESS(String pCP_PROV_ADDRESS) {
		PCP_PROV_ADDRESS = pCP_PROV_ADDRESS;
	}
	public String getRX_PROV_ADDRESS() {
		return RX_PROV_ADDRESS;
	}
	public void setRX_PROV_ADDRESS(String rX_PROV_ADDRESS) {
		RX_PROV_ADDRESS = rX_PROV_ADDRESS;
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
	public String getADAP_ID() {
		return ADAP_ID;
	}
	public void setADAP_ID(String aDAP_ID) {
		ADAP_ID = aDAP_ID;
	}
	public String getCERT_PROV_LNAME() {
		return CERT_PROV_LNAME;
	}
	public void setCERT_PROV_LNAME(String cERT_PROV_LNAME) {
		CERT_PROV_LNAME = cERT_PROV_LNAME;
	}
	public String getPCP_PROV_LNAME() {
		return PCP_PROV_LNAME;
	}
	public void setPCP_PROV_LNAME(String pCP_PROV_LNAME) {
		PCP_PROV_LNAME = pCP_PROV_LNAME;
	}
	public String getRX_PROV_LNAME() {
		return RX_PROV_LNAME;
	}
	public void setRX_PROV_LNAME(String rX_PROV_LNAME) {
		RX_PROV_LNAME = rX_PROV_LNAME;
	}
	public String getADAP_SRC_ID() {
		return ADAP_SRC_ID;
	}
	public void setADAP_SRC_ID(String aDAP_SRC_ID) {
		ADAP_SRC_ID = aDAP_SRC_ID;
	}
	public String getFLAG_CHOSEN_PROVIDER() {
		return FLAG_CHOSEN_PROVIDER;
	}
	public void setFLAG_CHOSEN_PROVIDER(String fLAG_CHOSEN_PROVIDER) {
		FLAG_CHOSEN_PROVIDER = fLAG_CHOSEN_PROVIDER;
	}
	public String getMATCH_PASS() {
		return MATCH_PASS;
	}
	public void setMATCH_PASS(String mATCH_PASS) {
		MATCH_PASS = mATCH_PASS;
	}
	public String getSUFFIX() {
		return SUFFIX;
	}
	public void setSUFFIX(String sUFFIX) {
		SUFFIX = sUFFIX;
	}
	public String getPERSON_ADDRESS() {
		return PERSON_ADDRESS;
	}
	public void setPERSON_ADDRESS(String pERSON_ADDRESS) {
		PERSON_ADDRESS = pERSON_ADDRESS;
	}
	public String getDOB() {
		return DOB;
	}
	public void setDOB(String dOB) {
		DOB = dOB;
	}
	public String getStreet_raw() {
		return street_raw;
	}
	public void setStreet_raw(String street_raw) {
		this.street_raw = street_raw;
	}
	public String getRxprov_addr_raw() {
		return rxprov_addr_raw;
	}
	public void setRxprov_addr_raw(String rxprov_addr_raw) {
		this.rxprov_addr_raw = rxprov_addr_raw;
	}
	public String getPcprov_addr_raw() {
		return pcprov_addr_raw;
	}
	public void setPcprov_addr_raw(String pcprov_addr_raw) {
		this.pcprov_addr_raw = pcprov_addr_raw;
	}
	public String getCertprov_addr_raw() {
		return certprov_addr_raw;
	}
	public void setCertprov_addr_raw(String certprov_addr_raw) {
		this.certprov_addr_raw = certprov_addr_raw;
	}

	public String getBIRTH_SEX() {
		return BIRTH_SEX;
	}

	public void setBIRTH_SEX(String bIRTH_SEX) {
		BIRTH_SEX = bIRTH_SEX;
	}

	public String getDISCARD_STATUS() {
		return DISCARD_STATUS;
	}

	public void setDISCARD_STATUS(String dISCARD_STATUS) {
		DISCARD_STATUS = dISCARD_STATUS;;
	}
	
	
	
	
	
    
}
