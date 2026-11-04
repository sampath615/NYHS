package gov.ny.state.doh.bhae.nyehms.web.spring.domain.document;

import java.sql.Date;

/**
 * This is POJO for NYC-IJR document
 *
 * @author vxp06
 */
public class NycIJRDocVO extends NYEHMSDocVO{

	private String stateNo;
	private int DOCUMENT_ID;
	private String CITYNO;
	private String NYC_SENT_STATENO;
	private String LNAME;
	private String FNAME;
	private String MNAME;
	private String ALT_LNAME;
	private String SSN;
	private String DOB;
	private String DOB_TEXT;
	private String GENDER;
	private String CURRENT_GENDER;
	private String ETHNICITY;
	private String RACE_CD;
	private String RACE_INDIAN;
	private String RACE_BLACK;
	private String RACE_ASIAN;
	private String RACE_PACIFIC;
	private String RACE_WHITE;
	private String RACE_MULTIPLE;
	private String RACE_OTHER;
	private String RACE_UNKNOWN;
	private String MED_RECORD_NUMBER;
	private String DIN;
	private String NYSID;
	private String BIRTH_COUNTRY;
	private String rawDxAddress;
	private String DX_PERSON_ADDRESS;
	private String DX_PERSON_CITY;
	private String DX_PERSON_STATE;
	private String DX_PERSON_ZIP;
	private String DX_PERSON_TELEPHONE;
	private String PERSON_CELLPHONE;
	private String PERSON_WORKPHONE;
	private String rawAddress;
	
	private String CUR_PERSON_ADDRESS; 
	private String CUR_PERSON_CITY; 
	private String CUR_PERSON_STATE; 
	private String CUR_PERSON_ZIP; 
	private String CUR_PERSON_COUNTY; 
	
	
	private String PERSON_ADDRESS;
	private String PERSON_CITY;
	private String CUR_PERSON_COUNTY_NAME;
	private String PERSON_STATE;
	private String PERSON_ZIP;
	private String PERSON_COUNTY;
	private String CUR_PERSON_PHONE;
	private String rawProvAddress;
	private String PROV_ADDRESS;
	private String PROV_CITY;
	private String PROV_STATE;
	private String PROV_ZIP;
	private String PROV_COUNTY;
	private String PROV_NAME;
	private String PROV_DEPARTMENT;
	private String PROV_PHONE;
	private String ADMISSION_DATE_TEXT;
	private String INIT_HIV_DIAG;
	private String INIT_HIV_DIAG_DATE_TEXT;
	private String INIT_AIDS_DIAG;
	private String INIT_AIDS_DIAG_DATE_TEXT;
	private String INFORMED_HIV_POS;
	private String INFORMED_HIV_DATE_TEXT;
	private String INDX_INTERVIEWED_DATE_TEXT;
	private String INDX_INTERVIEWED;
	private String SWM;
	private String SWF;
	private String IDU;
	private String SEX_PARTNER_IDU;
	private String SEX_PARTNER_HIV;
	private String RECV_BLOOD_PROD;
	private String PERINATAL;
	private String RISK_OTHER;
	private String RISK_OTHER_TEXT;
	private String RISK_UNKNOWN;
	private String SEX_PARTNER_MSM;
	private String RECV_BLOOD_PROD_DATE_TEXT;
	private String RECV_BLOOD_PROD_COUNTRY;
	private Date PROV_SIGNED_DATE;
	private String PROV_SIGNED;
	private String COMPLETER_FNAME;
	private String COMPLETER_MI;
	private String COMPLETER_LNAME;
	private String COMPLETER_TITLE;
	private String COMPLETER_EMAIL;
	private Date COMPLETED_DATE;
	private String CONTACT_LNAME;
	private String CONTACT_FNAME;
	private String CONTACT_MNAME;
	private String CONTACT_TITLE;
	private String CONTACT_PHONE;
	private int NUMBEROFPARTNERS;
	private String PS_REQUESTED;
	private String FILE_NAME;
	private Date FILE_DATE;
	private String RSH_PHONE;
	private String HIV_DX_DATE_TEXT;
	private String AIDS_DX_DATE_TEXT;
	private String HIV_FACILITY_NAME;
	private Long NYC_IJR_SRC_ID;
	private String DX_PERSON_COUNTY;
	private String PROV_CENSUS_TRACT;
	private String PROCESSED;
	private String CREATE_UID;
	private Date CREATE_DATE;
	private String AGE;
	private Date EHARS_IMPORT_DATE;
	private Date NYC_SHIP_DATE;
	
	
	private String cityNo;
	
	private int PARENT_DOCUMENT_ID;

	public int getPARENT_DOCUMENT_ID() {
		return PARENT_DOCUMENT_ID;
	}

	public void setPARENT_DOCUMENT_ID(int pARENT_DOCUMENT_ID) {
		PARENT_DOCUMENT_ID = pARENT_DOCUMENT_ID;
	}

	public String getStateNo() {
		return stateNo;
	}

	public void setStateNo(String stateNo) {
		this.stateNo = stateNo;
	}

	public int getDOCUMENT_ID() {
		return DOCUMENT_ID;
	}

	public void setDOCUMENT_ID(int dOCUMENT_ID) {
		DOCUMENT_ID = dOCUMENT_ID;
	}

	public String getCITYNO() {
		return CITYNO;
	}

	public void setCITYNO(String cITYNO) {
		CITYNO = cITYNO;
	}

	public String getNYC_SENT_STATENO() {
		return NYC_SENT_STATENO;
	}

	public void setNYC_SENT_STATENO(String nYC_SENT_STATENO) {
		NYC_SENT_STATENO = nYC_SENT_STATENO;
	}

	public String getLNAME() {
		return LNAME;
	}

	public void setLNAME(String lNAME) {
		LNAME = lNAME;
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

	public String getALT_LNAME() {
		return ALT_LNAME;
	}

	public void setALT_LNAME(String aLT_LNAME) {
		ALT_LNAME = aLT_LNAME;
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
	
	public String getDOB_TEXT() {
		return DOB_TEXT;
	}

	public void setDOB_TEXT(String dOB_TEXT) {
		DOB_TEXT = dOB_TEXT;
	}

	public String getGENDER() {
		return GENDER;
	}

	public void setGENDER(String gENDER) {
		GENDER = gENDER;
	}

	public String getETHNICITY() {
		return ETHNICITY;
	}

	public void setETHNICITY(String eTHNICITY) {
		ETHNICITY = eTHNICITY;
	}

	public String getRACE_CD() {
		return RACE_CD;
	}

	public void setRACE_CD(String rACE_CD) {
		RACE_CD = rACE_CD;
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

	public String getMED_RECORD_NUMBER() {
		return MED_RECORD_NUMBER;
	}

	public void setMED_RECORD_NUMBER(String mED_RECORD_NUMBER) {
		MED_RECORD_NUMBER = mED_RECORD_NUMBER;
	}

	public String getDIN() {
		return DIN;
	}

	public void setDIN(String dIN) {
		DIN = dIN;
	}

	public String getNYSID() {
		return NYSID;
	}

	public void setNYSID(String nYSID) {
		NYSID = nYSID;
	}

	public String getBIRTH_COUNTRY() {
		return BIRTH_COUNTRY;
	}

	public void setBIRTH_COUNTRY(String bIRTH_COUNTRY) {
		BIRTH_COUNTRY = bIRTH_COUNTRY;
	}

	public String getRawDxAddress() {
		return rawDxAddress;
	}

	public void setRawDxAddress(String rawDxAddress) {
		this.rawDxAddress = rawDxAddress;
	}

	public String getDX_PERSON_ADDRESS() {
		return DX_PERSON_ADDRESS;
	}

	public void setDX_PERSON_ADDRESS(String dX_PERSON_ADDRESS) {
		DX_PERSON_ADDRESS = dX_PERSON_ADDRESS;
	}

	public String getDX_PERSON_CITY() {
		return DX_PERSON_CITY;
	}

	public void setDX_PERSON_CITY(String dX_PERSON_CITY) {
		DX_PERSON_CITY = dX_PERSON_CITY;
	}

	public String getDX_PERSON_STATE() {
		return DX_PERSON_STATE;
	}

	public void setDX_PERSON_STATE(String dX_PERSON_STATE) {
		DX_PERSON_STATE = dX_PERSON_STATE;
	}

	public String getDX_PERSON_ZIP() {
		return DX_PERSON_ZIP;
	}

	public void setDX_PERSON_ZIP(String dX_PERSON_ZIP) {
		DX_PERSON_ZIP = dX_PERSON_ZIP;
	}

	public String getDX_PERSON_TELEPHONE() {
		return DX_PERSON_TELEPHONE;
	}

	public void setDX_PERSON_TELEPHONE(String dX_PERSON_TELEPHONE) {
		DX_PERSON_TELEPHONE = dX_PERSON_TELEPHONE;
	}

	public String getPERSON_CELLPHONE() {
		return PERSON_CELLPHONE;
	}

	public void setPERSON_CELLPHONE(String pERSON_CELLPHONE) {
		PERSON_CELLPHONE = pERSON_CELLPHONE;
	}

	public String getPERSON_WORKPHONE() {
		return PERSON_WORKPHONE;
	}

	public void setPERSON_WORKPHONE(String pERSON_WORKPHONE) {
		PERSON_WORKPHONE = pERSON_WORKPHONE;
	}

	public String getRawAddress() {
		return rawAddress;
	}

	public void setRawAddress(String rawAddress) {
		this.rawAddress = rawAddress;
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

	public String getCUR_PERSON_COUNTY_NAME() {
		return CUR_PERSON_COUNTY_NAME;
	}

	public void setCUR_PERSON_COUNTY_NAME(String cUR_PERSON_COUNTY_NAME) {
		CUR_PERSON_COUNTY_NAME = cUR_PERSON_COUNTY_NAME;
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

	public String getCUR_PERSON_PHONE() {
		return CUR_PERSON_PHONE;
	}

	public void setCUR_PERSON_PHONE(String cUR_PERSON_PHONE) {
		CUR_PERSON_PHONE = cUR_PERSON_PHONE;
	}

	public String getRawProvAddress() {
		return rawProvAddress;
	}

	public void setRawProvAddress(String rawProvAddress) {
		this.rawProvAddress = rawProvAddress;
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

	public String getPROV_NAME() {
		return PROV_NAME;
	}

	public void setPROV_NAME(String pROV_NAME) {
		PROV_NAME = pROV_NAME;
	}

	public String getPROV_DEPARTMENT() {
		return PROV_DEPARTMENT;
	}

	public void setPROV_DEPARTMENT(String pROV_DEPARTMENT) {
		PROV_DEPARTMENT = pROV_DEPARTMENT;
	}

	public String getPROV_PHONE() {
		return PROV_PHONE;
	}

	public void setPROV_PHONE(String pROV_PHONE) {
		PROV_PHONE = pROV_PHONE;
	}

	public String getADMISSION_DATE_TEXT() {
		return ADMISSION_DATE_TEXT;
	}

	public void setADMISSION_DATE_TEXT(String aDMISSION_DATE_TEXT) {
		ADMISSION_DATE_TEXT = aDMISSION_DATE_TEXT;
	}

	public String getINIT_HIV_DIAG() {
		return INIT_HIV_DIAG;
	}

	public void setINIT_HIV_DIAG(String iNIT_HIV_DIAG) {
		INIT_HIV_DIAG = iNIT_HIV_DIAG;
	}

	public String getINIT_HIV_DIAG_DATE_TEXT() {
		return INIT_HIV_DIAG_DATE_TEXT;
	}

	public void setINIT_HIV_DIAG_DATE_TEXT(String iNIT_HIV_DIAG_DATE_TEXT) {
		INIT_HIV_DIAG_DATE_TEXT = iNIT_HIV_DIAG_DATE_TEXT;
	}

	public String getINIT_AIDS_DIAG() {
		return INIT_AIDS_DIAG;
	}

	public void setINIT_AIDS_DIAG(String iNIT_AIDS_DIAG) {
		INIT_AIDS_DIAG = iNIT_AIDS_DIAG;
	}

	public String getINIT_AIDS_DIAG_DATE_TEXT() {
		return INIT_AIDS_DIAG_DATE_TEXT;
	}

	public void setINIT_AIDS_DIAG_DATE_TEXT(String iNIT_AIDS_DIAG_DATE_TEXT) {
		INIT_AIDS_DIAG_DATE_TEXT = iNIT_AIDS_DIAG_DATE_TEXT;
	}

	public String getINFORMED_HIV_POS() {
		return INFORMED_HIV_POS;
	}

	public void setINFORMED_HIV_POS(String iNFORMED_HIV_POS) {
		INFORMED_HIV_POS = iNFORMED_HIV_POS;
	}

	public String getINFORMED_HIV_DATE_TEXT() {
		return INFORMED_HIV_DATE_TEXT;
	}

	public void setINFORMED_HIV_DATE_TEXT(String iNFORMED_HIV_DATE_TEXT) {
		INFORMED_HIV_DATE_TEXT = iNFORMED_HIV_DATE_TEXT;
	}

	public String getINDX_INTERVIEWED_DATE_TEXT() {
		return INDX_INTERVIEWED_DATE_TEXT;
	}

	public void setINDX_INTERVIEWED_DATE_TEXT(String iNDX_INTERVIEWED_DATE_TEXT) {
		INDX_INTERVIEWED_DATE_TEXT = iNDX_INTERVIEWED_DATE_TEXT;
	}

	public String getINDX_INTERVIEWED() {
		return INDX_INTERVIEWED;
	}

	public void setINDX_INTERVIEWED(String iNDX_INTERVIEWED) {
		INDX_INTERVIEWED = iNDX_INTERVIEWED;
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

	public String getRISK_OTHER_TEXT() {
		return RISK_OTHER_TEXT;
	}

	public void setRISK_OTHER_TEXT(String rISK_OTHER_TEXT) {
		RISK_OTHER_TEXT = rISK_OTHER_TEXT;
	}

	public String getRISK_UNKNOWN() {
		return RISK_UNKNOWN;
	}

	public void setRISK_UNKNOWN(String rISK_UNKNOWN) {
		RISK_UNKNOWN = rISK_UNKNOWN;
	}

	

	public String getSEX_PARTNER_MSM() {
		return SEX_PARTNER_MSM;
	}

	public void setSEX_PARTNER_MSM(String sEX_PARTNER_MSM) {
		SEX_PARTNER_MSM = sEX_PARTNER_MSM;
	}

	public String getRECV_BLOOD_PROD_DATE_TEXT() {
		return RECV_BLOOD_PROD_DATE_TEXT;
	}

	public void setRECV_BLOOD_PROD_DATE_TEXT(String rECV_BLOOD_PROD_DATE_TEXT) {
		RECV_BLOOD_PROD_DATE_TEXT = rECV_BLOOD_PROD_DATE_TEXT;
	}

	public String getRECV_BLOOD_PROD_COUNTRY() {
		return RECV_BLOOD_PROD_COUNTRY;
	}

	public void setRECV_BLOOD_PROD_COUNTRY(String rECV_BLOOD_PROD_COUNTRY) {
		RECV_BLOOD_PROD_COUNTRY = rECV_BLOOD_PROD_COUNTRY;
	}

	public Date getPROV_SIGNED_DATE() {
		return PROV_SIGNED_DATE;
	}

	public void setPROV_SIGNED_DATE(Date pROV_SIGNED_DATE) {
		PROV_SIGNED_DATE = pROV_SIGNED_DATE;
	}

	public String getPROV_SIGNED() {
		return PROV_SIGNED;
	}

	public void setPROV_SIGNED(String pROV_SIGNED) {
		PROV_SIGNED = pROV_SIGNED;
	}

	public String getCOMPLETER_FNAME() {
		return COMPLETER_FNAME;
	}

	public void setCOMPLETER_FNAME(String cOMPLETER_FNAME) {
		COMPLETER_FNAME = cOMPLETER_FNAME;
	}

	public String getCOMPLETER_MI() {
		return COMPLETER_MI;
	}

	public void setCOMPLETER_MI(String cOMPLETER_MI) {
		COMPLETER_MI = cOMPLETER_MI;
	}

	public String getCOMPLETER_LNAME() {
		return COMPLETER_LNAME;
	}

	public void setCOMPLETER_LNAME(String cOMPLETER_LNAME) {
		COMPLETER_LNAME = cOMPLETER_LNAME;
	}

	public String getCOMPLETER_TITLE() {
		return COMPLETER_TITLE;
	}

	public void setCOMPLETER_TITLE(String cOMPLETER_TITLE) {
		COMPLETER_TITLE = cOMPLETER_TITLE;
	}

	public String getCOMPLETER_EMAIL() {
		return COMPLETER_EMAIL;
	}

	public void setCOMPLETER_EMAIL(String cOMPLETER_EMAIL) {
		COMPLETER_EMAIL = cOMPLETER_EMAIL;
	}

	public Date getCOMPLETED_DATE() {
		return COMPLETED_DATE;
	}

	public void setCOMPLETED_DATE(Date cOMPLETED_DATE) {
		COMPLETED_DATE = cOMPLETED_DATE;
	}

	public String getCONTACT_LNAME() {
		return CONTACT_LNAME;
	}

	public void setCONTACT_LNAME(String cONTACT_LNAME) {
		CONTACT_LNAME = cONTACT_LNAME;
	}

	public String getCONTACT_FNAME() {
		return CONTACT_FNAME;
	}

	public void setCONTACT_FNAME(String cONTACT_FNAME) {
		CONTACT_FNAME = cONTACT_FNAME;
	}

	public String getCONTACT_MNAME() {
		return CONTACT_MNAME;
	}

	public void setCONTACT_MNAME(String cONTACT_MNAME) {
		CONTACT_MNAME = cONTACT_MNAME;
	}

	public String getCONTACT_TITLE() {
		return CONTACT_TITLE;
	}

	public void setCONTACT_TITLE(String cONTACT_TITLE) {
		CONTACT_TITLE = cONTACT_TITLE;
	}

	public String getCONTACT_PHONE() {
		return CONTACT_PHONE;
	}

	public void setCONTACT_PHONE(String cONTACT_PHONE) {
		CONTACT_PHONE = cONTACT_PHONE;
	}

	public int getNUMBEROFPARTNERS() {
		return NUMBEROFPARTNERS;
	}

	public void setNUMBEROFPARTNERS(int nUMBEROFPARTNERS) {
		NUMBEROFPARTNERS = nUMBEROFPARTNERS;
	}

	public String getPS_REQUESTED() {
		return PS_REQUESTED;
	}

	public void setPS_REQUESTED(String pS_REQUESTED) {
		PS_REQUESTED = pS_REQUESTED;
	}

	public String getFILE_NAME() {
		return FILE_NAME;
	}

	public void setFILE_NAME(String fILE_NAME) {
		FILE_NAME = fILE_NAME;
	}

	public Date getFILE_DATE() {
		return FILE_DATE;
	}

	public void setFILE_DATE(Date fILE_DATE) {
		FILE_DATE = fILE_DATE;
	}

	public String getRSH_PHONE() {
		return RSH_PHONE;
	}

	public void setRSH_PHONE(String rSH_PHONE) {
		RSH_PHONE = rSH_PHONE;
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

	public String getHIV_FACILITY_NAME() {
		return HIV_FACILITY_NAME;
	}

	public void setHIV_FACILITY_NAME(String hIV_FACILITY_NAME) {
		HIV_FACILITY_NAME = hIV_FACILITY_NAME;
	}

	public Long getNYC_IJR_SRC_ID() {
		return NYC_IJR_SRC_ID;
	}

	public void setNYC_IJR_SRC_ID(Long nYC_IJR_SRC_ID) {
		NYC_IJR_SRC_ID = nYC_IJR_SRC_ID;
	}

	public String getDX_PERSON_COUNTY() {
		return DX_PERSON_COUNTY;
	}

	public void setDX_PERSON_COUNTY(String dX_PERSON_COUNTY) {
		DX_PERSON_COUNTY = dX_PERSON_COUNTY;
	}

	public String getPROV_CENSUS_TRACT() {
		return PROV_CENSUS_TRACT;
	}

	public void setPROV_CENSUS_TRACT(String pROV_CENSUS_TRACT) {
		PROV_CENSUS_TRACT = pROV_CENSUS_TRACT;
	}

	public String getPROCESSED() {
		return PROCESSED;
	}

	public void setPROCESSED(String pROCESSED) {
		PROCESSED = pROCESSED;
	}

	public String getCREATE_UID() {
		return CREATE_UID;
	}

	public void setCREATE_UID(String cREATE_UID) {
		CREATE_UID = cREATE_UID;
	}

	public Date getCREATE_DATE() {
		return CREATE_DATE;
	}

	public void setCREATE_DATE(Date cREATE_DATE) {
		CREATE_DATE = cREATE_DATE;
	}

	public String getAGE() {
		return AGE;
	}

	public void setAGE(String aGE) {
		AGE = aGE;
	}

	public Date getEHARS_IMPORT_DATE() {
		return EHARS_IMPORT_DATE;
	}

	public void setEHARS_IMPORT_DATE(Date eHARS_IMPORT_DATE) {
		EHARS_IMPORT_DATE = eHARS_IMPORT_DATE;
	}

	public String getCityNo() {
		return cityNo;
	}

	public void setCityNo(String cityNo) {
		this.cityNo = cityNo;
	}

	public String getCUR_PERSON_ADDRESS() {
		return CUR_PERSON_ADDRESS;
	}

	public void setCUR_PERSON_ADDRESS(String cUR_PERSON_ADDRESS) {
		CUR_PERSON_ADDRESS = cUR_PERSON_ADDRESS;
	}

	public String getCUR_PERSON_CITY() {
		return CUR_PERSON_CITY;
	}

	public void setCUR_PERSON_CITY(String cUR_PERSON_CITY) {
		CUR_PERSON_CITY = cUR_PERSON_CITY;
	}

	public String getCUR_PERSON_STATE() {
		return CUR_PERSON_STATE;
	}

	public void setCUR_PERSON_STATE(String cUR_PERSON_STATE) {
		CUR_PERSON_STATE = cUR_PERSON_STATE;
	}

	public String getCUR_PERSON_ZIP() {
		return CUR_PERSON_ZIP;
	}

	public void setCUR_PERSON_ZIP(String cUR_PERSON_ZIP) {
		CUR_PERSON_ZIP = cUR_PERSON_ZIP;
	}

	public String getCUR_PERSON_COUNTY() {
		return CUR_PERSON_COUNTY;
	}

	public void setCUR_PERSON_COUNTY(String cUR_PERSON_COUNTY) {
		CUR_PERSON_COUNTY = cUR_PERSON_COUNTY;
	}

	public Date getNYC_SHIP_DATE() {
		return NYC_SHIP_DATE;
	}

	public void setNYC_SHIP_DATE(Date nYC_SHIP_DATE) {
		NYC_SHIP_DATE = nYC_SHIP_DATE;
	}

	public String getCURRENT_GENDER() {
		return CURRENT_GENDER;
	}

	public void setCURRENT_GENDER(String cURRENT_GENDER) {
		CURRENT_GENDER = cURRENT_GENDER;
	}
	
	private String DISCARD_STATUS;
	
	public String getDISCARD_STATUS() {
		return DISCARD_STATUS;
	}

	public void setDISCARD_STATUS(String dISCARD_STATUS) {
		DISCARD_STATUS = dISCARD_STATUS;;
	}
		
	
    
}
