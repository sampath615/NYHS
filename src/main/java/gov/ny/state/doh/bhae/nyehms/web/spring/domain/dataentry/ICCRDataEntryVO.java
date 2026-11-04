package gov.ny.state.doh.bhae.nyehms.web.spring.domain.dataentry;

import java.util.List;

public class ICCRDataEntryVO {
	
	private Integer DOCUMENT_ID;
	private String FIRST_NAME;
	private String MIDDLE_NAME;
	private String LAST_NAME;
	private String DOB;
	private String PERSON_ADDRESS;
	private String PERSON_CITY;
	private String PERSON_STATE;
	private String PERSON_ZIP;
	private String PERSON_COUNTY;
	private String PERSON_PHONE;
	private String JURISDICTION_PROVIDING_INFO;
	private String PROV_NAME;
	private String PROV_ADDRESS;
	private String PROV_CITY;
	private String PROV_STATE;
	private String PROV_ZIP;
	private String PROV_COUNTY;
	private String PROV_PHONE;
	private String RESULTS_RECEIVED;
	private String COMMENTS;
	private int CREATE_UID;
	
	private List<ICCRTestVO> iccrTestList; 
	
	public Integer getDOCUMENT_ID() {
		return DOCUMENT_ID;
	}
	public void setDOCUMENT_ID(Integer dOCUMENT_ID) {
		DOCUMENT_ID = dOCUMENT_ID;
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
	public String getDOB() {
		return DOB;
	}
	public void setDOB(String dOB) {
		DOB = dOB;
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
	public String getJURISDICTION_PROVIDING_INFO() {
		return JURISDICTION_PROVIDING_INFO;
	}
	public void setJURISDICTION_PROVIDING_INFO(String jURISDICTION_PROVIDING_INFO) {
		JURISDICTION_PROVIDING_INFO = jURISDICTION_PROVIDING_INFO;
	}
	public String getPROV_NAME() {
		return PROV_NAME;
	}
	public void setPROV_NAME(String pROV_NAME) {
		PROV_NAME = pROV_NAME;
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
	public String getPROV_PHONE() {
		return PROV_PHONE;
	}
	public void setPROV_PHONE(String pROV_PHONE) {
		PROV_PHONE = pROV_PHONE;
	}
	public String getRESULTS_RECEIVED() {
		return RESULTS_RECEIVED;
	}
	public void setRESULTS_RECEIVED(String rESULTS_RECEIVED) {
		RESULTS_RECEIVED = rESULTS_RECEIVED;
	}
	public String getCOMMENTS() {
		return COMMENTS;
	}
	public void setCOMMENTS(String cOMMENTS) {
		COMMENTS = cOMMENTS;
	}
	public List<ICCRTestVO> getIccrTestList() {
		return iccrTestList;
	}
	public void setIccrTestList(List<ICCRTestVO> iccrTestList) {
		this.iccrTestList = iccrTestList;
	}
	public int getCREATE_UID() {
		return CREATE_UID;
	}
	public void setCREATE_UID(int cREATE_UID) {
		CREATE_UID = cREATE_UID;
	}

}
