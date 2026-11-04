package gov.ny.state.doh.bhae.nyehms.web.spring.domain.document;

import java.sql.Date;
/**
 * This class is POJO for CLIMS document.
 *
 * @author skg02
 */

public class CLIMSDocVO extends NYEHMSDocVO {

	private String DOCUMENT_ID;
	private String STATENO;
	private String ACCID;
	private Date FINALAPPROVALDT;
	private String INITIALPCR;
	private String FOLLOWUPPCR;
	private String LASTNAME;
	private String FIRSTNAME;
	private String RECID;
	private String PATIENTID;
	private String NBSCODE;
	private String NBSLABID;
	private String PATCODE;
	private String COUNTYCODE;
	private String PATZIP;
	private Date DOB;
	private Double AGE;
	private Date COLLDATE;
	private String RACE_INDIAN;
	private String RACE_ASIAN;
	private String RACE_BLACK;
	private String RACE_PACIFIC;
	private String RACE_WHITE;
	private String RACE_UNKNOWN;
	private String RACE_OTHER;
	private String ETHNICITY;
	private String CURRENT_GENDER;
	private String GESTATION;
	private String WEIGHT;
	private String BIRTH_FACILITY;
	private String BIRTH_COUNTYCODE;
	private String FOSTERCARE;
	private String MAV;
	private String MAVWHEN;
	private String IPAV;
	private String ITAV;
	private String SUBMFNAME;
	private String SUBMLNAME;
	private String SUBMITTERID;
	private String FACILITY_NAME;
	private String FACILITY_ADDRESS;
	private String FACILITY_COUNTY;
	private String FACILITY_CITY;
	private String FACILITY_STATE;
	private String FACILITY_ZIP;
	private String MULTISPOT;
	private String HIV2QUAL;
	private String TMA;
	private String MEMO;
	private String GEENIUS;
	private Date EHARS_IMPORT_DATE;
	private Date NYC_SHIP_DATE;
	private String MATCHED_STATENO;
	private String FACADDR1;
	private String FACADDR2;
	
	public String getDOCUMENT_ID() {
		return DOCUMENT_ID;
	}
	public void setDOCUMENT_ID(String dOCUMENT_ID) {
		DOCUMENT_ID = dOCUMENT_ID;
	}
	public String getACCID() {
		return ACCID;
	}
	public void setACCID(String aCCID) {
		ACCID = aCCID;
	}
	public Date getFINALAPPROVALDT() {
		return FINALAPPROVALDT;
	}
	public void setFINALAPPROVALDT(Date fINALAPPROVALDT) {
		FINALAPPROVALDT = fINALAPPROVALDT;
	}
	public String getINITIALPCR() {
		return INITIALPCR;
	}
	public void setINITIALPCR(String iNITIALPCR) {
		INITIALPCR = iNITIALPCR;
	}
	public String getFOLLOWUPPCR() {
		return FOLLOWUPPCR;
	}
	public void setFOLLOWUPPCR(String fOLLOWUPPCR) {
		FOLLOWUPPCR = fOLLOWUPPCR;
	}
	public String getLASTNAME() {
		return LASTNAME;
	}
	public void setLASTNAME(String lASTNAME) {
		LASTNAME = lASTNAME;
	}
	public String getFIRSTNAME() {
		return FIRSTNAME;
	}
	public void setFIRSTNAME(String fIRSTNAME) {
		FIRSTNAME = fIRSTNAME;
	}
	public String getRECID() {
		return RECID;
	}
	public void setRECID(String rECID) {
		RECID = rECID;
	}
	public String getPATIENTID() {
		return PATIENTID;
	}
	public void setPATIENTID(String pATIENTID) {
		PATIENTID = pATIENTID;
	}
	public String getNBSCODE() {
		return NBSCODE;
	}
	public void setNBSCODE(String nBSCODE) {
		NBSCODE = nBSCODE;
	}
	public String getNBSLABID() {
		return NBSLABID;
	}
	public void setNBSLABID(String nBSLABID) {
		NBSLABID = nBSLABID;
	}
	public String getPATCODE() {
		return PATCODE;
	}
	public void setPATCODE(String pATCODE) {
		PATCODE = pATCODE;
	}
	public String getCOUNTYCODE() {
		return COUNTYCODE;
	}
	public void setCOUNTYCODE(String cOUNTYCODE) {
		COUNTYCODE = cOUNTYCODE;
	}
	public String getPATZIP() {
		return PATZIP;
	}
	public void setPATZIP(String pATZIP) {
		PATZIP = pATZIP;
	}
	public Date getDOB() {
		return DOB;
	}
	public void setDOB(Date dOB) {
		DOB = dOB;
	}
	public Double getAGE() {
		return AGE;
	}
	public void setAGE(Double aGE) {
		AGE = aGE;
	}
	public Date getCOLLDATE() {
		return COLLDATE;
	}
	public void setCOLLDATE(Date cOLLDATE) {
		COLLDATE = cOLLDATE;
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
	public String getRACE_BLACK() {
		return RACE_BLACK;
	}
	public void setRACE_BLACK(String rACE_BLACK) {
		RACE_BLACK = rACE_BLACK;
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
	public String getETHNICITY() {
		return ETHNICITY;
	}
	public void setETHNICITY(String eTHNICITY) {
		ETHNICITY = eTHNICITY;
	}
	public String getCURRENT_GENDER() {
		return CURRENT_GENDER;
	}
	public void setCURRENT_GENDER(String cURRENT_GENDER) {
		CURRENT_GENDER = cURRENT_GENDER;
	}
	public String getGESTATION() {
		return GESTATION;
	}
	public void setGESTATION(String gESTATION) {
		GESTATION = gESTATION;
	}
	public String getWEIGHT() {
		return WEIGHT;
	}
	public void setWEIGHT(String wEIGHT) {
		WEIGHT = wEIGHT;
	}
	public String getBIRTH_FACILITY() {
		return BIRTH_FACILITY;
	}
	public void setBIRTH_FACILITY(String bIRTH_FACILITY) {
		BIRTH_FACILITY = bIRTH_FACILITY;
	}
	public String getBIRTH_COUNTYCODE() {
		return BIRTH_COUNTYCODE;
	}
	public void setBIRTH_COUNTYCODE(String bIRTH_COUNTYCODE) {
		BIRTH_COUNTYCODE = bIRTH_COUNTYCODE;
	}
	public String getFOSTERCARE() {
		return FOSTERCARE;
	}
	public void setFOSTERCARE(String fOSTERCARE) {
		FOSTERCARE = fOSTERCARE;
	}
	public String getMAV() {
		return MAV;
	}
	public void setMAV(String mAV) {
		MAV = mAV;
	}
	public String getMAVWHEN() {
		return MAVWHEN;
	}
	public void setMAVWHEN(String mAVWHEN) {
		MAVWHEN = mAVWHEN;
	}
	public String getIPAV() {
		return IPAV;
	}
	public void setIPAV(String iPAV) {
		IPAV = iPAV;
	}
	public String getITAV() {
		return ITAV;
	}
	public void setITAV(String iTAV) {
		ITAV = iTAV;
	}
	public String getSUBMFNAME() {
		return SUBMFNAME;
	}
	public void setSUBMFNAME(String sUBMFNAME) {
		SUBMFNAME = sUBMFNAME;
	}
	public String getSUBMLNAME() {
		return SUBMLNAME;
	}
	public void setSUBMLNAME(String sUBMLNAME) {
		SUBMLNAME = sUBMLNAME;
	}
	public String getSUBMITTERID() {
		return SUBMITTERID;
	}
	public void setSUBMITTERID(String sUBMITTERID) {
		SUBMITTERID = sUBMITTERID;
	}
	public String getFACILITY_NAME() {
		return FACILITY_NAME;
	}
	public void setFACILITY_NAME(String fACILITY_NAME) {
		FACILITY_NAME = fACILITY_NAME;
	}
	public String getFACILITY_ADDRESS() {
		return FACILITY_ADDRESS;
	}
	public void setFACILITY_ADDRESS(String fACILITY_ADDRESS) {
		FACILITY_ADDRESS = fACILITY_ADDRESS;
	}
	public String getFACILITY_COUNTY() {
		return FACILITY_COUNTY;
	}
	public void setFACILITY_COUNTY(String fACILITY_COUNTY) {
		FACILITY_COUNTY = fACILITY_COUNTY;
	}
	public String getFACILITY_CITY() {
		return FACILITY_CITY;
	}
	public void setFACILITY_CITY(String fACILITY_CITY) {
		FACILITY_CITY = fACILITY_CITY;
	}
	public String getFACILITY_STATE() {
		return FACILITY_STATE;
	}
	public void setFACILITY_STATE(String fACILITY_STATE) {
		FACILITY_STATE = fACILITY_STATE;
	}
	public String getFACILITY_ZIP() {
		return FACILITY_ZIP;
	}
	public void setFACILITY_ZIP(String fACILITY_ZIP) {
		FACILITY_ZIP = fACILITY_ZIP;
	}
	public String getMULTISPOT() {
		return MULTISPOT;
	}
	public void setMULTISPOT(String mULTISPOT) {
		MULTISPOT = mULTISPOT;
	}
	public String getHIV2QUAL() {
		return HIV2QUAL;
	}
	public void setHIV2QUAL(String hIV2QUAL) {
		HIV2QUAL = hIV2QUAL;
	}
	public String getTMA() {
		return TMA;
	}
	public void setTMA(String tMA) {
		TMA = tMA;
	}
	public String getMEMO() {
		return MEMO;
	}
	public void setMEMO(String mEMO) {
		MEMO = mEMO;
	}
	public String getGEENIUS() {
		return GEENIUS;
	}
	public void setGEENIUS(String gEENIUS) {
		GEENIUS = gEENIUS;
	}
	public Date getEHARS_IMPORT_DATE() {
		return EHARS_IMPORT_DATE;
	}
	public void setEHARS_IMPORT_DATE(Date eHARS_IMPORT_DATE) {
		EHARS_IMPORT_DATE = eHARS_IMPORT_DATE;
	}
	public Date getNYC_SHIP_DATE() {
		return NYC_SHIP_DATE;
	}
	public void setNYC_SHIP_DATE(Date nYC_SHIP_DATE) {
		NYC_SHIP_DATE = nYC_SHIP_DATE;
	}
	public String getMATCHED_STATENO() {
		return MATCHED_STATENO;
	}
	public void setMATCHED_STATENO(String mATCHED_STATENO) {
		MATCHED_STATENO = mATCHED_STATENO;
	}
	public String getSTATENO() {
		return STATENO;
	}
	public void setSTATENO(String sTATENO) {
		STATENO = sTATENO;
	}
	public String getFACADDR1() {
		return FACADDR1;
	}
	public void setFACADDR1(String fACADDR1) {
		FACADDR1 = fACADDR1;
	}
	public String getFACADDR2() {
		return FACADDR2;
	}
	public void setFACADDR2(String fACADDR2) {
		FACADDR2 = fACADDR2;
	}
	
	private String DISCARD_STATUS;
	
	public String getDISCARD_STATUS() {
		return DISCARD_STATUS;
	}

	public void setDISCARD_STATUS(String dISCARD_STATUS) {
		DISCARD_STATUS = dISCARD_STATUS;;
	}
	
}
