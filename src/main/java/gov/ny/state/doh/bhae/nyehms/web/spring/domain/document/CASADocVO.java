package gov.ny.state.doh.bhae.nyehms.web.spring.domain.document;

import java.util.Date;

public class CASADocVO extends NYEHMSDocVO {
	
	private int DOCUMENT_ID;
    private String STATENO = "";
	private String MEDICAID_NUMBER;
	private String INPAT;
	private String CLAIMCOUNT;
	private String OUTPAT;
	private String DIAGALLCLAIMS;
	private String HIVHOSPICE;
	private String HIVSNP;
	private String ARV;
	private String OUTP_ADIOI;
	private String OUTPSERV_DIAG;
	private String SPEC_POSS;
	private String SPEC_ADI;
	private String ARV_POSS;
	private String ARV_ADI;
	private String ARV_OUTPAT;
	private String PROCEDURES;
	private String OUTPAT_SELECT;
	private String FINCRG;
	private String GENDER;
	private String ETHNICITY;
	private String DOB;
	private String DOB_TEXT;
	private String SSN;
	private String FNAME;
	private String LNAME;
	private String MNAME;
	private String PERSON_ZIP;
	private String PERSON_COUNTY; //COUNTY_FIPS
	private String ELIG_DATE;
	private String NARROW;
	private String RATE;
	private String RATE1;
	private String RACE_INDIAN;
	private String RACE_BLACK;
	private String RACE_ASIAN;
	private String RACE_PACIFIC;
	private String RACE_WHITE;
	private String RACE_MULTIPLE;
	private String RACE_OTHER;
	private String RACE_UNKNOWN;
	private String CASA_FILE;
	private Date EHARS_IMPORT_DATE;
	
	private int PARENT_DOCUMENT_ID;

	public int getPARENT_DOCUMENT_ID() {
		return PARENT_DOCUMENT_ID;
	}

	public void setPARENT_DOCUMENT_ID(int pARENT_DOCUMENT_ID) {
		PARENT_DOCUMENT_ID = pARENT_DOCUMENT_ID;
	}
	
	public int getDOCUMENT_ID() {
		return DOCUMENT_ID;
	}
	public void setDOCUMENT_ID(int dOCUMENT_ID) {
		DOCUMENT_ID = dOCUMENT_ID;
	}
	public String getSTATENO() {
		return STATENO;
	}
	public void setSTATENO(String sTATENO) {
		STATENO = sTATENO;
	}
	public String getMEDICAID_NUMBER() {
		return MEDICAID_NUMBER;
	}
	public void setMEDICAID_NUMBER(String mEDICAID_NUMBER) {
		MEDICAID_NUMBER = mEDICAID_NUMBER;
	}
	public String getINPAT() {
		return INPAT;
	}
	public void setINPAT(String iNPAT) {
		INPAT = iNPAT;
	}
	public String getCLAIMCOUNT() {
		return CLAIMCOUNT;
	}
	public void setCLAIMCOUNT(String cLAIMCOUNT) {
		CLAIMCOUNT = cLAIMCOUNT;
	}
	public String getOUTPAT() {
		return OUTPAT;
	}
	public void setOUTPAT(String oUTPAT) {
		OUTPAT = oUTPAT;
	}
	public String getDIAGALLCLAIMS() {
		return DIAGALLCLAIMS;
	}
	public void setDIAGALLCLAIMS(String dIAGALLCLAIMS) {
		DIAGALLCLAIMS = dIAGALLCLAIMS;
	}
	public String getHIVHOSPICE() {
		return HIVHOSPICE;
	}
	public void setHIVHOSPICE(String hIVHOSPICE) {
		HIVHOSPICE = hIVHOSPICE;
	}
	public String getHIVSNP() {
		return HIVSNP;
	}
	public void setHIVSNP(String hIVSNP) {
		HIVSNP = hIVSNP;
	}
	public String getARV() {
		return ARV;
	}
	public void setARV(String aRV) {
		ARV = aRV;
	}
	public String getOUTP_ADIOI() {
		return OUTP_ADIOI;
	}
	public void setOUTP_ADIOI(String oUTP_ADIOI) {
		OUTP_ADIOI = oUTP_ADIOI;
	}
	public String getOUTPSERV_DIAG() {
		return OUTPSERV_DIAG;
	}
	public void setOUTPSERV_DIAG(String oUTPSERV_DIAG) {
		OUTPSERV_DIAG = oUTPSERV_DIAG;
	}
	public String getSPEC_POSS() {
		return SPEC_POSS;
	}
	public void setSPEC_POSS(String sPEC_POSS) {
		SPEC_POSS = sPEC_POSS;
	}
	public String getSPEC_ADI() {
		return SPEC_ADI;
	}
	public void setSPEC_ADI(String sPEC_ADI) {
		SPEC_ADI = sPEC_ADI;
	}
	public String getARV_POSS() {
		return ARV_POSS;
	}
	public void setARV_POSS(String aRV_POSS) {
		ARV_POSS = aRV_POSS;
	}
	public String getARV_ADI() {
		return ARV_ADI;
	}
	public void setARV_ADI(String aRV_ADI) {
		ARV_ADI = aRV_ADI;
	}
	public String getARV_OUTPAT() {
		return ARV_OUTPAT;
	}
	public void setARV_OUTPAT(String aRV_OUTPAT) {
		ARV_OUTPAT = aRV_OUTPAT;
	}
	public String getPROCEDURES() {
		return PROCEDURES;
	}
	public void setPROCEDURES(String pROCEDURES) {
		PROCEDURES = pROCEDURES;
	}
	public String getOUTPAT_SELECT() {
		return OUTPAT_SELECT;
	}
	public void setOUTPAT_SELECT(String oUTPAT_SELECT) {
		OUTPAT_SELECT = oUTPAT_SELECT;
	}
	public String getFINCRG() {
		return FINCRG;
	}
	public void setFINCRG(String fINCRG) {
		FINCRG = fINCRG;
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
	public String getDOB() {
		return DOB;
	}
	public void setDOB(String dOB) {
		DOB = dOB;
	}
	public String getSSN() {
		return SSN;
	}
	public void setSSN(String sSN) {
		SSN = sSN;
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
	public String getELIG_DATE() {
		return ELIG_DATE;
	}
	public void setELIG_DATE(String eLIG_DATE) {
		ELIG_DATE = eLIG_DATE;
	}
	public String getNARROW() {
		return NARROW;
	}
	public void setNARROW(String nARROW) {
		NARROW = nARROW;
	}
	public String getRATE() {
		return RATE;
	}
	public void setRATE(String rATE) {
		RATE = rATE;
	}
	public String getRATE1() {
		return RATE1;
	}
	public void setRATE1(String rATE1) {
		RATE1 = rATE1;
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
	public String getCASA_FILE() {
		return CASA_FILE;
	}
	public void setCASA_FILE(String cASA_FILE) {
		CASA_FILE = cASA_FILE;
	}
	public Date getEHARS_IMPORT_DATE() {
		return EHARS_IMPORT_DATE;
	}
	public void setEHARS_IMPORT_DATE(Date eHARS_IMPORT_DATE) {
		EHARS_IMPORT_DATE = eHARS_IMPORT_DATE;
	}
	public String getDOB_TEXT() {
		return DOB_TEXT;
	}
	public void setDOB_TEXT(String dOB_TEXT) {
		DOB_TEXT = dOB_TEXT;
	}
	
	private String DISCARD_STATUS;
	
	public String getDISCARD_STATUS() {
		return DISCARD_STATUS;
	}

	public void setDISCARD_STATUS(String dISCARD_STATUS) {
		DISCARD_STATUS = dISCARD_STATUS;;
	}
	

}
