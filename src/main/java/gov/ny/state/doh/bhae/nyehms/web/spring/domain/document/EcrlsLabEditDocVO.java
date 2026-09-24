package gov.ny.state.doh.bhae.nyehms.web.spring.domain.document;

import java.sql.Date;

public class EcrlsLabEditDocVO {

	private int DOCUMENT_ID;
	private String DOB;
	private String GENDER;
	private String FACILITY_NAME;
	private String FACILITY_ADDRESS;
	private String FACILITY_CITY;
	private String FACILITY_STATE;
	private String FACILITY_ZIP;
	private String FACILITY_PLUS4;
	private String FACILITY_COUNTY;
	private String FACILITY_PHONE;
	private Date OBSERVATION_DATE;
	private Date COLLECTION_DATE;
	private Date RESULT_REPORT_DATE;
	private String LOINC;
	private String LOINC_DESC;
	private String UNIT_MEASURE_CODE="";
	
	
	public int getDOCUMENT_ID() {
		return DOCUMENT_ID;
	}
	public void setDOCUMENT_ID(int dOCUMENT_ID) {
		DOCUMENT_ID = dOCUMENT_ID;
	}
	public String getDOB() {
		return DOB;
	}
	public void setDOB(String dOB) {
		DOB = dOB;
	}
	public String getGENDER() {
		return GENDER;
	}
	public void setGENDER(String gENDER) {
		GENDER = gENDER;
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
	public String getFACILITY_PLUS4() {
		return FACILITY_PLUS4;
	}
	public void setFACILITY_PLUS4(String fACILITY_PLUS4) {
		FACILITY_PLUS4 = fACILITY_PLUS4;
	}
	public String getFACILITY_COUNTY() {
		return FACILITY_COUNTY;
	}
	public void setFACILITY_COUNTY(String fACILITY_COUNTY) {
		FACILITY_COUNTY = fACILITY_COUNTY;
	}
	public String getFACILITY_PHONE() {
		return FACILITY_PHONE;
	}
	public void setFACILITY_PHONE(String fACILITY_PHONE) {
		FACILITY_PHONE = fACILITY_PHONE;
	}
	public Date getOBSERVATION_DATE() {
		return OBSERVATION_DATE;
	}
	public void setOBSERVATION_DATE(Date oBSERVATION_DATE) {
		OBSERVATION_DATE = oBSERVATION_DATE;
	}
	public Date getCOLLECTION_DATE() {
		return COLLECTION_DATE;
	}
	public void setCOLLECTION_DATE(Date cOLLECTION_DATE) {
		COLLECTION_DATE = cOLLECTION_DATE;
	}
	public Date getRESULT_REPORT_DATE() {
		return RESULT_REPORT_DATE;
	}
	public void setRESULT_REPORT_DATE(Date rESULT_REPORT_DATE) {
		RESULT_REPORT_DATE = rESULT_REPORT_DATE;
	}
	public String getLOINC() {
		return LOINC;
	}
	public void setLOINC(String lOINC) {
		LOINC = lOINC;
	}
	public String getLOINC_DESC() {
		return LOINC_DESC;
	}
	public void setLOINC_DESC(String lOINC_DESC) {
		LOINC_DESC = lOINC_DESC;
	}
	public String getUNIT_MEASURE_CODE() {
		return UNIT_MEASURE_CODE;
	}
	public void setUNIT_MEASURE_CODE(String uNIT_MEASURE_CODE) {
		UNIT_MEASURE_CODE = uNIT_MEASURE_CODE;
	}
	
	
}
