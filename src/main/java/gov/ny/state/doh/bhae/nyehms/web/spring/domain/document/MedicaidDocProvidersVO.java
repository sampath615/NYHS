package gov.ny.state.doh.bhae.nyehms.web.spring.domain.document;

import java.sql.Date;


public class MedicaidDocProvidersVO {
	private String MEDICAID_NUMBER;
	private String PROV_NAME;
    private String NPI;
    private String PROV_ADDRESS;
    private String PROV_CITY;
    private String PROV_STATE;
    private String PROV_ZIP;
    private String PROV_COUNTY;
    private Date SERVICE_DATE;
    private String CATEGORY_OF_SERVICE_CD;
    private String PRIMARY_IND;
    private String  raw_prov_address;
    private String PROV_COS_CODE_DESC;
    
	public String getMEDICAID_NUMBER() {
		return MEDICAID_NUMBER;
	}
	public void setMEDICAID_NUMBER(String MEDICAID_NUMBER) {
		this.MEDICAID_NUMBER = MEDICAID_NUMBER;
	}
	public String getPROV_NAME() {
		return PROV_NAME;
	}
	public void setPROV_NAME(String pROV_NAME) {
		PROV_NAME = pROV_NAME;
	}
	public String getNPI() {
		return NPI;
	}
	public void setNPI(String nPI) {
		NPI = nPI;
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
	public Date getSERVICE_DATE() {
		return SERVICE_DATE;
	}
	public void setSERVICE_DATE(Date sERVICE_DATE) {
		SERVICE_DATE = sERVICE_DATE;
	}
	public String getCATEGORY_OF_SERVICE_CD() {
		return CATEGORY_OF_SERVICE_CD;
	}
	public void setCATEGORY_OF_SERVICE_CD(String cATEGORY_OF_SERVICE_CD) {
		CATEGORY_OF_SERVICE_CD = cATEGORY_OF_SERVICE_CD;
	}
	public String getPRIMARY_IND() {
		return PRIMARY_IND;
	}
	public void setPRIMARY_IND(String pRIMARY_IND) {
		PRIMARY_IND = pRIMARY_IND;
	}
	public String getRaw_prov_address() {
		return raw_prov_address;
	}
	public void setRaw_prov_address(String raw_prov_address) {
		this.raw_prov_address = raw_prov_address;
	}
	public String getPROV_COS_CODE_DESC() {
		return PROV_COS_CODE_DESC;
	}
	public void setPROV_COS_CODE_DESC(String pROV_COS_CODE_DESC) {
		PROV_COS_CODE_DESC = pROV_COS_CODE_DESC;
	}
    
	

	 
		 
    
}
