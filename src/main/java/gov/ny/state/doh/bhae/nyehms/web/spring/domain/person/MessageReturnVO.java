package gov.ny.state.doh.bhae.nyehms.web.spring.domain.person;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.uploads.UpdatedDispositionsVO;

import java.sql.Date;
import java.util.List;

public class MessageReturnVO {

	private Date MESSAGE_ENTERED_DATE;
	private Date MESSAGE_RECEIVED_DATE;
	private Integer CDESSCASEKEY;
	private String PREGNANT;
	private boolean RACE_WHITE;
	private boolean RACE_BLACK;
	private boolean RACE_ASIAN;
	private boolean RACE_PACIFIC;
	private boolean RACE_INDIAN;
	private boolean RACE_MULTIPLE;
	private boolean RACE_OTHER;
	private String SWM;
	private String SWF;
	private String SWT;
	private String SWA;
	private String SEX_WOUT_CONDOM;
	private String SEX_WHILE_INTOX;
	private String EXCHANGE_SEX;
	private String FEMALE_SEX_MSM;
	private String SEX_IDU;
	private String INCARCERATED;
	private String SHARED_DRUG_EQUIP;
	private String IDU;
	private String CRACK;
	private String COCAINE;
	private String HEROIN;
	private String METH;
	private String NITRATES;
	private String ED_MEDS;
	private String STATENO;
	private String CITY_NO;
	private String DOB;
	private String DOD;
	private String DX_DATE;
	private String FIRST_NAME;
	private String MIDDLE_NAME;
	private String LAST_NAME;
	private String OTHER_RISK;
	private String SEX_AT_BIRTH;
	private String GENDER;
	private String PERSON_STATE;
	private String DX_STATE;
	private String BIRTH_COUNTRY;
	private String PERSON_CITY;
	private String DX_CITY;
	private String DISPOSITION_CODE;
	private String PERSON_ZIP;
	private String DX_ZIP;
	private String OTHER_DRUGS;
	private String PERSON_ADDRESS;
	private String DX_ADDRESS;
	private String DIN;
	private String NYSID;
	
	
	
	private Integer X900POSITIVE;
	private String X900DXDATE;
	private Integer DIAGNOSISLOCATION;
	private String DIAGNOSISLOCOUTSIDEUS;
	private Integer INITMEDAPPT;
	private String DATEMEDAPPT;
	private Integer ATTENDEDMEDAPPT;
	private Integer TESTTECHNOLOGY;
	private Integer NINEHUNDREDTEST;
	private String CURRENT900TESTDATE;
	private Integer CURRENT900TESTRESULT;
	private String PREPREFERRAL;
	private Integer EVERONPREP;
	private Integer CURRENTONPREP;
	private Integer REFERRALPREPINFO;
	private String FIELDNOTE; 
	
	private List<UpdatedDispositionsVO> updatedDispositionsList;  
	
	
	
	public Date getMESSAGE_ENTERED_DATE() {
		return MESSAGE_ENTERED_DATE;
	}
	public void setMESSAGE_ENTERED_DATE(Date mESSAGE_ENTERED_DATE) {
		MESSAGE_ENTERED_DATE = mESSAGE_ENTERED_DATE;
	}
	public Date getMESSAGE_RECEIVED_DATE() {
		return MESSAGE_RECEIVED_DATE;
	}
	public void setMESSAGE_RECEIVED_DATE(Date mESSAGE_RECEIVED_DATE) {
		MESSAGE_RECEIVED_DATE = mESSAGE_RECEIVED_DATE;
	}
	public Integer getCDESSCASEKEY() {
		return CDESSCASEKEY;
	}
	public void setCDESSCASEKEY(Integer cDESSCASEKEY) {
		CDESSCASEKEY = cDESSCASEKEY;
	}
	public String getPREGNANT() {
		return PREGNANT;
	}
	public void setPREGNANT(String pREGNANT) {
		PREGNANT = pREGNANT;
	}
	public boolean isRACE_WHITE() {
		return RACE_WHITE;
	}
	public void setRACE_WHITE(boolean rACE_WHITE) {
		RACE_WHITE = rACE_WHITE;
	}
	public boolean isRACE_BLACK() {
		return RACE_BLACK;
	}
	public void setRACE_BLACK(boolean rACE_BLACK) {
		RACE_BLACK = rACE_BLACK;
	}
	public boolean isRACE_ASIAN() {
		return RACE_ASIAN;
	}
	public void setRACE_ASIAN(boolean rACE_ASIAN) {
		RACE_ASIAN = rACE_ASIAN;
	}
	public boolean isRACE_PACIFIC() {
		return RACE_PACIFIC;
	}
	public void setRACE_PACIFIC(boolean rACE_PACIFIC) {
		RACE_PACIFIC = rACE_PACIFIC;
	}
	public boolean isRACE_INDIAN() {
		return RACE_INDIAN;
	}
	public void setRACE_INDIAN(boolean rACE_INDIAN) {
		RACE_INDIAN = rACE_INDIAN;
	}
	public boolean isRACE_MULTIPLE() {
		return RACE_MULTIPLE;
	}
	public void setRACE_MULTIPLE(boolean rACE_MULTIPLE) {
		RACE_MULTIPLE = rACE_MULTIPLE;
	}
	public boolean isRACE_OTHER() {
		return RACE_OTHER;
	}
	public void setRACE_OTHER(boolean rACE_OTHER) {
		RACE_OTHER = rACE_OTHER;
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
	public String getSWT() {
		return SWT;
	}
	public void setSWT(String sWT) {
		SWT = sWT;
	}
	public String getSWA() {
		return SWA;
	}
	public void setSWA(String sWA) {
		SWA = sWA;
	}
	public String getSEX_WOUT_CONDOM() {
		return SEX_WOUT_CONDOM;
	}
	public void setSEX_WOUT_CONDOM(String sEX_WOUT_CONDOM) {
		SEX_WOUT_CONDOM = sEX_WOUT_CONDOM;
	}
	public String getSEX_WHILE_INTOX() {
		return SEX_WHILE_INTOX;
	}
	public void setSEX_WHILE_INTOX(String sEX_WHILE_INTOX) {
		SEX_WHILE_INTOX = sEX_WHILE_INTOX;
	}
	public String getEXCHANGE_SEX() {
		return EXCHANGE_SEX;
	}
	public void setEXCHANGE_SEX(String eXCHANGE_SEX) {
		EXCHANGE_SEX = eXCHANGE_SEX;
	}
	public String getFEMALE_SEX_MSM() {
		return FEMALE_SEX_MSM;
	}
	public void setFEMALE_SEX_MSM(String fEMALE_SEX_MSM) {
		FEMALE_SEX_MSM = fEMALE_SEX_MSM;
	}
	public String getSEX_IDU() {
		return SEX_IDU;
	}
	public void setSEX_IDU(String sEX_IDU) {
		SEX_IDU = sEX_IDU;
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
	public String getSTATENO() {
		return STATENO;
	}
	public void setSTATENO(String sTATENO) {
		STATENO = sTATENO;
	}
	public String getCITY_NO() {
		return CITY_NO;
	}
	public void setCITY_NO(String cITY_NO) {
		CITY_NO = cITY_NO;
	}
	public String getDOB() {
		return DOB;
	}
	public void setDOB(String dOB) {
		DOB = dOB;
	}
	public String getDOD() {
		return DOD;
	}
	public void setDOD(String dOD) {
		DOD = dOD;
	}
	public String getDX_DATE() {
		return DX_DATE;
	}
	public void setDX_DATE(String dX_DATE) {
		DX_DATE = dX_DATE;
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
	public String getOTHER_RISK() {
		return OTHER_RISK;
	}
	public void setOTHER_RISK(String oTHER_RISK) {
		OTHER_RISK = oTHER_RISK;
	}
	public String getSEX_AT_BIRTH() {
		return SEX_AT_BIRTH;
	}
	public void setSEX_AT_BIRTH(String sEX_AT_BIRTH) {
		SEX_AT_BIRTH = sEX_AT_BIRTH;
	}
	public String getGENDER() {
		return GENDER;
	}
	public void setGENDER(String gENDER) {
		GENDER = gENDER;
	}
	public String getPERSON_STATE() {
		return PERSON_STATE;
	}
	public void setPERSON_STATE(String pERSON_STATE) {
		PERSON_STATE = pERSON_STATE;
	}
	public String getDX_STATE() {
		return DX_STATE;
	}
	public void setDX_STATE(String dX_STATE) {
		DX_STATE = dX_STATE;
	}
	public String getBIRTH_COUNTRY() {
		return BIRTH_COUNTRY;
	}
	public void setBIRTH_COUNTRY(String bIRTH_COUNTRY) {
		BIRTH_COUNTRY = bIRTH_COUNTRY;
	}
	public String getPERSON_CITY() {
		return PERSON_CITY;
	}
	public void setPERSON_CITY(String pERSON_CITY) {
		PERSON_CITY = pERSON_CITY;
	}
	public String getDX_CITY() {
		return DX_CITY;
	}
	public void setDX_CITY(String dX_CITY) {
		DX_CITY = dX_CITY;
	}
	public String getDISPOSITION_CODE() {
		return DISPOSITION_CODE;
	}
	public void setDISPOSITION_CODE(String dISPOSITION_CODE) {
		DISPOSITION_CODE = dISPOSITION_CODE;
	}
	public String getPERSON_ZIP() {
		return PERSON_ZIP;
	}
	public void setPERSON_ZIP(String pERSON_ZIP) {
		PERSON_ZIP = pERSON_ZIP;
	}
	public String getDX_ZIP() {
		return DX_ZIP;
	}
	public void setDX_ZIP(String dX_ZIP) {
		DX_ZIP = dX_ZIP;
	}
	public String getOTHER_DRUGS() {
		return OTHER_DRUGS;
	}
	public void setOTHER_DRUGS(String oTHER_DRUGS) {
		OTHER_DRUGS = oTHER_DRUGS;
	}
	public String getPERSON_ADDRESS() {
		return PERSON_ADDRESS;
	}
	public void setPERSON_ADDRESS(String pERSON_ADDRESS) {
		PERSON_ADDRESS = pERSON_ADDRESS;
	}
	public String getDX_ADDRESS() {
		return DX_ADDRESS;
	}
	public void setDX_ADDRESS(String dX_ADDRESS) {
		DX_ADDRESS = dX_ADDRESS;
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
	public List<UpdatedDispositionsVO> getUpdatedDispositionsList() {
		return updatedDispositionsList;
	}
	public void setUpdatedDispositionsList(
			List<UpdatedDispositionsVO> updatedDispositionsList) {
		this.updatedDispositionsList = updatedDispositionsList;
	}
	public Integer getX900POSITIVE() {
		return X900POSITIVE;
	}
	public void setX900POSITIVE(Integer x900positive) {
		X900POSITIVE = x900positive;
	}
	public String getX900DXDATE() {
		return X900DXDATE;
	}
	public void setX900DXDATE(String x900dxdate) {
		X900DXDATE = x900dxdate;
	}
	public Integer getDIAGNOSISLOCATION() {
		return DIAGNOSISLOCATION;
	}
	public void setDIAGNOSISLOCATION(Integer dIAGNOSISLOCATION) {
		DIAGNOSISLOCATION = dIAGNOSISLOCATION;
	}
	public String getDIAGNOSISLOCOUTSIDEUS() {
		return DIAGNOSISLOCOUTSIDEUS;
	}
	public void setDIAGNOSISLOCOUTSIDEUS(String dIAGNOSISLOCOUTSIDEUS) {
		DIAGNOSISLOCOUTSIDEUS = dIAGNOSISLOCOUTSIDEUS;
	}
	public Integer getINITMEDAPPT() {
		return INITMEDAPPT;
	}
	public void setINITMEDAPPT(Integer iNITMEDAPPT) {
		INITMEDAPPT = iNITMEDAPPT;
	}
	public String getDATEMEDAPPT() {
		return DATEMEDAPPT;
	}
	public void setDATEMEDAPPT(String dATEMEDAPPT) {
		DATEMEDAPPT = dATEMEDAPPT;
	}
	public Integer getATTENDEDMEDAPPT() {
		return ATTENDEDMEDAPPT;
	}
	public void setATTENDEDMEDAPPT(Integer aTTENDEDMEDAPPT) {
		ATTENDEDMEDAPPT = aTTENDEDMEDAPPT;
	}
	public Integer getTESTTECHNOLOGY() {
		return TESTTECHNOLOGY;
	}
	public void setTESTTECHNOLOGY(Integer tESTTECHNOLOGY) {
		TESTTECHNOLOGY = tESTTECHNOLOGY;
	}
	public Integer getNINEHUNDREDTEST() {
		return NINEHUNDREDTEST;
	}
	public void setNINEHUNDREDTEST(Integer nINEHUNDREDTEST) {
		NINEHUNDREDTEST = nINEHUNDREDTEST;
	}
	public String getCURRENT900TESTDATE() {
		return CURRENT900TESTDATE;
	}
	public void setCURRENT900TESTDATE(String cURRENT900TESTDATE) {
		CURRENT900TESTDATE = cURRENT900TESTDATE;
	}
	public Integer getCURRENT900TESTRESULT() {
		return CURRENT900TESTRESULT;
	}
	public void setCURRENT900TESTRESULT(Integer cURRENT900TESTRESULT) {
		CURRENT900TESTRESULT = cURRENT900TESTRESULT;
	}
	public String getPREPREFERRAL() {
		return PREPREFERRAL;
	}
	public void setPREPREFERRAL(String pREPREFERRAL) {
		PREPREFERRAL = pREPREFERRAL;
	}
	public Integer getEVERONPREP() {
		return EVERONPREP;
	}
	public void setEVERONPREP(Integer eVERONPREP) {
		EVERONPREP = eVERONPREP;
	}
	public Integer getCURRENTONPREP() {
		return CURRENTONPREP;
	}
	public void setCURRENTONPREP(Integer cURRENTONPREP) {
		CURRENTONPREP = cURRENTONPREP;
	}
	public Integer getREFERRALPREPINFO() {
		return REFERRALPREPINFO;
	}
	public void setREFERRALPREPINFO(Integer rEFERRALPREPINFO) {
		REFERRALPREPINFO = rEFERRALPREPINFO;
	}
	public String getFIELDNOTE() {
		return FIELDNOTE;
	}
	public void setFIELDNOTE(String fIELDNOTE) {
		FIELDNOTE = fIELDNOTE;
	}

	
	
	
}
