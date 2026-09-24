package gov.ny.state.doh.bhae.nyehms.web.spring.domain.lab;



public class LabNewContactVO {
	
	private String PFI;
	private int CONTACTID;
	private String CONTACTTYPE ;
	private String NAME;
	private String EMAIL;
	private String PHONE;
	private String REPORTCARD="N";
	private String COUNTS="N";
	private String QA="N";
	private String RETIRE="N";
	private int userModified;
	private String NOTES;
	private String MAINCONTACT;
	private String PHONEEXT;
	
	
	
	public String getPFI() {
		return PFI;
	}
	public void setPFI(String pFI) {
		PFI = pFI;
	}
	public int getCONTACTID() {
		return CONTACTID;
	}
	public void setCONTACTID(int cONTACTID) {
		CONTACTID = cONTACTID;
	}
	public String getCONTACTTYPE() {
		return CONTACTTYPE;
	}
	public void setCONTACTTYPE(String cONTACTTYPE) {
		CONTACTTYPE = cONTACTTYPE;
	}
	public String getNAME() {
		return NAME;
	}
	public void setNAME(String nAME) {
		NAME = nAME;
	}
	public String getEMAIL() {
		return EMAIL;
	}
	public void setEMAIL(String eMAIL) {
		EMAIL = eMAIL;
	}
	public String getPHONE() {
		return PHONE;
	}
	public void setPHONE(String pHONE) {
		PHONE = pHONE;
	}
	public String getREPORTCARD() {
		return REPORTCARD;
	}
	public void setREPORTCARD(String rEPORTCARD) {
		REPORTCARD = rEPORTCARD;
	}
	public String getCOUNTS() {
		return COUNTS;
	}
	public void setCOUNTS(String cOUNTS) {
		COUNTS = cOUNTS;
	}
	public String getQA() {
		return QA;
	}
	public void setQA(String qA) {
		QA = qA;
	}
	public String getRETIRE() {
		return RETIRE;
	}
	public void setRETIRE(String rETIRE) {
		RETIRE = rETIRE;
	}
	public int getUserModified() {
		return userModified;
	}
	public void setUserModified(int userModified) {
		this.userModified = userModified;
	}
	public String getNOTES() {
		return NOTES;
	}
	public void setNOTES(String nOTES) {
		NOTES = nOTES;
	}
	public String getMAINCONTACT() {
		return MAINCONTACT;
	}
	public void setMAINCONTACT(String mAINCONTACT) {
		MAINCONTACT = mAINCONTACT;
	}
	public String getPHONEEXT() {
		return PHONEEXT;
	}
	public void setPHONEEXT(String pHONEEXT) {
		PHONEEXT = pHONEEXT;
	}
		
}
