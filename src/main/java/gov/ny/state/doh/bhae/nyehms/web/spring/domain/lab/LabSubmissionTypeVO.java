package gov.ny.state.doh.bhae.nyehms.web.spring.domain.lab;



public class LabSubmissionTypeVO {
	
	private String PFI;
	private int LAB_SUBMIT_ID;	
	private String SUBMISSION_TYPE_CD;	
	private String PRIMARY_SUBMISSION;
	private String DESCRIPTION ;	
	private String START_DATE ;	
	private String END_DATE ;
	private int userModified;
	private String LIS_VENDOR;
	private String LIS_VENDOR_DESC;
	
	public String getPFI() {
		return PFI;
	}
	public void setPFI(String pFI) {
		PFI = pFI;
	}
	public int getLAB_SUBMIT_ID() {
		return LAB_SUBMIT_ID;
	}
	public void setLAB_SUBMIT_ID(int lAB_SUBMIT_ID) {
		LAB_SUBMIT_ID = lAB_SUBMIT_ID;
	}
	public String getSUBMISSION_TYPE_CD() {
		return SUBMISSION_TYPE_CD;
	}
	public void setSUBMISSION_TYPE_CD(String sUBMISSION_TYPE_CD) {
		SUBMISSION_TYPE_CD = sUBMISSION_TYPE_CD;
	}
	public String getDESCRIPTION() {
		return DESCRIPTION;
	}
	public void setDESCRIPTION(String dESCRIPTION) {
		DESCRIPTION = dESCRIPTION;
	}
	public String getSTART_DATE() {
		return START_DATE;
	}
	public void setSTART_DATE(String sTART_DATE) {
		START_DATE = sTART_DATE;
	}
	public String getEND_DATE() {
		return END_DATE;
	}
	public void setEND_DATE(String eND_DATE) {
		END_DATE = eND_DATE;
	}
	public int getUserModified() {
		return userModified;
	}
	public void setUserModified(int userModified) {
		this.userModified = userModified;
	}
	public String getPRIMARY_SUBMISSION() {
		return PRIMARY_SUBMISSION;
	}
	public void setPRIMARY_SUBMISSION(String pRIMARY_SUBMISSION) {
		PRIMARY_SUBMISSION = pRIMARY_SUBMISSION;
	}
	public String getLIS_VENDOR() {
		return LIS_VENDOR;
	}
	public void setLIS_VENDOR(String lIS_VENDOR) {
		LIS_VENDOR = lIS_VENDOR;
	}
	public String getLIS_VENDOR_DESC() {
		return LIS_VENDOR_DESC;
	}
	public void setLIS_VENDOR_DESC(String lIS_VENDOR_DESC) {
		LIS_VENDOR_DESC = lIS_VENDOR_DESC;
	}
	
}
