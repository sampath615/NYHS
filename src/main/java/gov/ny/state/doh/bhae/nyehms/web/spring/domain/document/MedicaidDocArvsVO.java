package gov.ny.state.doh.bhae.nyehms.web.spring.domain.document;


public class MedicaidDocArvsVO {
	private String CIN;
	private String PRESCRIBED_ARVS;
    private String ARV_MOS;
    private String ARV_YOS;
    private String PLAN_TYPE;
    private String MEDICAID_FILE;
	public String getCIN() {
		return CIN;
	}
	public void setCIN(String cIN) {
		CIN = cIN;
	}
	public String getPRESCRIBED_ARVS() {
		return PRESCRIBED_ARVS;
	}
	public void setPRESCRIBED_ARVS(String pRESCRIBED_ARVS) {
		PRESCRIBED_ARVS = pRESCRIBED_ARVS;
	}
	public String getARV_MOS() {
		return ARV_MOS;
	}
	public void setARV_MOS(String aRV_MOS) {
		ARV_MOS = aRV_MOS;
	}
	public String getARV_YOS() {
		return ARV_YOS;
	}
	public void setARV_YOS(String aRV_YOS) {
		ARV_YOS = aRV_YOS;
	}
	public String getPLAN_TYPE() {
		return PLAN_TYPE;
	}
	public void setPLAN_TYPE(String pLAN_TYPE) {
		PLAN_TYPE = pLAN_TYPE;
	}
	public String getMEDICAID_FILE() {
		return MEDICAID_FILE;
	}
	public void setMEDICAID_FILE(String mEDICAID_FILE) {
		MEDICAID_FILE = mEDICAID_FILE;
	}
	 
		 
    
}
