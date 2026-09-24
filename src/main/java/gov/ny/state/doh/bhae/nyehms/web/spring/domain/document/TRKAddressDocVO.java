package gov.ny.state.doh.bhae.nyehms.web.spring.domain.document;

import java.sql.Date;


/**
 * This is pojo class for the TRK Address doc. This is used by mybatis to create map.
 *
 * @author ska03
 */
public class TRKAddressDocVO {

	private int DOCUMENT_ID;
	private String STATENO;
	private String FNAME;
	private String MNAME;
	private String LNAME;
	private String SSN;
	private String DOB_TEXT;
	private String SUFFIX;
	private String ADDRESS;
	private String ADDRESS_CITY;
	private String ADDRESS_STATE;
	private String ADDRESS_ZIPCODE;
	private String ADDRESS_PLUS4;
	private String COUNTY_FIPS;
	private String HOME_PHONE;
	private String ADDRESS_TRACT_CENSUS;
	private String TRACT_SUFFIX_CENSUS;
	private String ADDRESS_LATITUDE;
	private String ADDRESS_LONGITUDE;
	private String CREATEID;
	private Date CREATEDT;
	
	
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
	public String getSUFFIX() {
		return SUFFIX;
	}
	public void setSUFFIX(String sUFFIX) {
		SUFFIX = sUFFIX;
	}
	public String getADDRESS() {
		return ADDRESS;
	}
	public void setADDRESS(String aDDRESS) {
		ADDRESS = aDDRESS;
	}
	public String getADDRESS_CITY() {
		return ADDRESS_CITY;
	}
	public void setADDRESS_CITY(String aDDRESS_CITY) {
		ADDRESS_CITY = aDDRESS_CITY;
	}
	public String getADDRESS_STATE() {
		return ADDRESS_STATE;
	}
	public void setADDRESS_STATE(String aDDRESS_STATE) {
		ADDRESS_STATE = aDDRESS_STATE;
	}
	public String getADDRESS_ZIPCODE() {
		return ADDRESS_ZIPCODE;
	}
	public void setADDRESS_ZIPCODE(String aDDRESS_ZIPCODE) {
		ADDRESS_ZIPCODE = aDDRESS_ZIPCODE;
	}
	public String getADDRESS_PLUS4() {
		return ADDRESS_PLUS4;
	}
	public void setADDRESS_PLUS4(String aDDRESS_PLUS4) {
		ADDRESS_PLUS4 = aDDRESS_PLUS4;
	}
	public String getCOUNTY_FIPS() {
		return COUNTY_FIPS;
	}
	public void setCOUNTY_FIPS(String cOUNTY_FIPS) {
		COUNTY_FIPS = cOUNTY_FIPS;
	}
	public String getHOME_PHONE() {
		return HOME_PHONE;
	}
	public void setHOME_PHONE(String hOME_PHONE) {
		HOME_PHONE = hOME_PHONE;
	}
	public String getADDRESS_TRACT_CENSUS() {
		return ADDRESS_TRACT_CENSUS;
	}
	public void setADDRESS_TRACT_CENSUS(String aDDRESS_TRACT_CENSUS) {
		ADDRESS_TRACT_CENSUS = aDDRESS_TRACT_CENSUS;
	}
	public String getTRACT_SUFFIX_CENSUS() {
		return TRACT_SUFFIX_CENSUS;
	}
	public void setTRACT_SUFFIX_CENSUS(String tRACT_SUFFIX_CENSUS) {
		TRACT_SUFFIX_CENSUS = tRACT_SUFFIX_CENSUS;
	}
	public String getADDRESS_LATITUDE() {
		return ADDRESS_LATITUDE;
	}
	public void setADDRESS_LATITUDE(String aDDRESS_LATITUDE) {
		ADDRESS_LATITUDE = aDDRESS_LATITUDE;
	}
	public String getADDRESS_LONGITUDE() {
		return ADDRESS_LONGITUDE;
	}
	public void setADDRESS_LONGITUDE(String aDDRESS_LONGITUDE) {
		ADDRESS_LONGITUDE = aDDRESS_LONGITUDE;
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
	
	

}
