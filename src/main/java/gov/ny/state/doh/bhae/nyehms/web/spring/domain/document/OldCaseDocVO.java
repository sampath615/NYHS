package gov.ny.state.doh.bhae.nyehms.web.spring.domain.document;


/**
 * This is pojo class for the Old case doc. This is used by mybatis to create map.
 *
 * @author ska03
 */
public class OldCaseDocVO {
	private String DOCUMENT_ID;
	private String OLDCASE_SRC_ID;
	private String MATCHED_STATENO;
	private String FNAME;
	private String MNAME;
	private String LNAME;
	private String SUFFIX;
	private String DOB_TEXT;
	private String DATE_OF_BIRTH;
	private String DOD_TEXT;
	private String DATE_OF_DEATH;
	private String SSN;
	private String MED_RECORD_NUMBER;
	private String DIN;
	private String ADDRESS;
	private String CITY;
	private String STATE;
	private String ZIP;
	private String RACE;
	private String ETHNICITY;
	private String CURRENT_GENDER;
	private String CREATE_DATE;
	private String CREATE_UID;
	private String RACE_INDIAN;
	private String RACE_BLACK;
	private String RACE_ASIAN;
	private String RACE_PACIFIC;
	private String RACE_WHITE;
	private String RACE_MULTIPLE;
	private String RACE_OTHER;
	private String RACE_UNKNOWN;
	private String COMMENTS;
	
	public String getDOCUMENT_ID() {
		return DOCUMENT_ID;
	}
	public void setDOCUMENT_ID(String dOCUMENT_ID) {
		DOCUMENT_ID = dOCUMENT_ID;
	}
	public String getOLDCASE_SRC_ID() {
		return OLDCASE_SRC_ID;
	}
	public void setOLDCASE_SRC_ID(String oLDCASE_SRC_ID) {
		OLDCASE_SRC_ID = oLDCASE_SRC_ID;
	}
	public String getMATCHED_STATENO() {
		return MATCHED_STATENO;
	}
	public void setMATCHED_STATENO(String mATCHED_STATENO) {
		MATCHED_STATENO = mATCHED_STATENO;
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
	public String getSUFFIX() {
		return SUFFIX;
	}
	public void setSUFFIX(String sUFFIX) {
		SUFFIX = sUFFIX;
	}
	public String getDOB_TEXT() {
		return DOB_TEXT;
	}
	public void setDOB_TEXT(String dOB_TEXT) {
		DOB_TEXT = dOB_TEXT;
	}
	public String getDATE_OF_BIRTH() {
		return DATE_OF_BIRTH;
	}
	public void setDATE_OF_BIRTH(String dATE_OF_BIRTH) {
		DATE_OF_BIRTH = dATE_OF_BIRTH;
	}
	public String getDOD_TEXT() {
		return DOD_TEXT;
	}
	public void setDOD_TEXT(String dOD_TEXT) {
		DOD_TEXT = dOD_TEXT;
	}
	public String getDATE_OF_DEATH() {
		return DATE_OF_DEATH;
	}
	public void setDATE_OF_DEATH(String dATE_OF_DEATH) {
		DATE_OF_DEATH = dATE_OF_DEATH;
	}
	public String getSSN() {
		return SSN;
	}
	public void setSSN(String sSN) {
		SSN = sSN;
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
	public String getZIP() {
		return ZIP;
	}
	public void setZIP(String zIP) {
		ZIP = zIP;
	}
	public String getRACE() {
		return RACE;
	}
	public void setRACE(String rACE) {
		RACE = rACE;
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
	public String getCREATE_DATE() {
		return CREATE_DATE;
	}
	public void setCREATE_DATE(String cREATE_DATE) {
		CREATE_DATE = cREATE_DATE;
	}
	public String getCREATE_UID() {
		return CREATE_UID;
	}
	public void setCREATE_UID(String cREATE_UID) {
		CREATE_UID = cREATE_UID;
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
	
	
	private String DISCARD_STATUS;
	
	public String getDISCARD_STATUS() {
		return DISCARD_STATUS;
	}

	public void setDISCARD_STATUS(String dISCARD_STATUS) {
		DISCARD_STATUS = dISCARD_STATUS;;
	}
	public String getCOMMENTS() {
		return COMMENTS;
	}
	public void setCOMMENTS(String cOMMENTS) {
		COMMENTS = cOMMENTS;
	}
	
	

}
