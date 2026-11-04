package gov.ny.state.doh.bhae.nyehms.web.spring.domain.document;

import java.sql.Date;


/**
 * This is pojo class for the TRK Alias doc. This is used by mybatis to create map.
 *
 * @author ska03
 */
public class TRKAliasDocVO {

	private int DOCUMENT_ID;
	private int ALIAS_ID;
	private String STATENO;
	private String REPSRCCD;
	private int ALIAS_NO;
	private String FNAME;
	private String MNAME;
	private String LNAME;
	private String SSN;
	private String DOB_TEXT;
	private Date DOB;
	private String SUFFIX;
	private String CREATEID;
	private Date CREATEDT;
	private String UPDATE_UID;
	private Date UPDATE_DT;
	private String COMMENTS;
	
	public int getDOCUMENT_ID() {
		return DOCUMENT_ID;
	}
	public void setDOCUMENT_ID(int dOCUMENT_ID) {
		DOCUMENT_ID = dOCUMENT_ID;
	}
	public int getALIAS_ID() {
		return ALIAS_ID;
	}
	public void setALIAS_ID(int aLIAS_ID) {
		ALIAS_ID = aLIAS_ID;
	}
	public String getSTATENO() {
		return STATENO;
	}
	public void setSTATENO(String sTATENO) {
		STATENO = sTATENO;
	}
	public String getREPSRCCD() {
		return REPSRCCD;
	}
	public void setREPSRCCD(String rEPSRCCD) {
		REPSRCCD = rEPSRCCD;
	}
	public int getALIAS_NO() {
		return ALIAS_NO;
	}
	public void setALIAS_NO(int aLIAS_NO) {
		ALIAS_NO = aLIAS_NO;
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
	public String getSSN() {
		return SSN;
	}
	public void setSSN(String sSN) {
		SSN = sSN;
	}
	public String getDOB_TEXT() {
		return DOB_TEXT;
	}
	public void setDOB_TEXT(String dOB_TEXT) {
		DOB_TEXT = dOB_TEXT;
	}
	public Date getDOB() {
		return DOB;
	}
	public void setDOB(Date dOB) {
		DOB = dOB;
	}
	public String getSUFFIX() {
		return SUFFIX;
	}
	public void setSUFFIX(String sUFFIX) {
		SUFFIX = sUFFIX;
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
	public String getCOMMENTS() {
		return COMMENTS;
	}
	public void setCOMMENTS(String cOMMENTS) {
		COMMENTS = cOMMENTS;
	}

}
