package gov.ny.state.doh.bhae.nyehms.web.spring.domain.lab;

public class LAFT_LAB_RECON_SUMM_REC {
	
	private long LAFT_RECON_SUMM_PK;
	private String LAFT_RECON_ID;
	
	private String PFI;
	private String CLIA;
	private String UPDATE_WORKER_ID;
	private String LAFT_TEST_CD;
//	private String LAFT_TEST_DESC;
	
	private int INITIAL_LAB_COUNT = 0;
	private int CURRENT_LAB_COUNT = 0;
	private int SURVEY_LAB_COUNT = 0;
	private int ADJUSTED_SURVEY_LAB_COUNT = 0;
	private int COUNT_DIFFERENCE = 0;
	private String DATE_RESOLVED;	
	
	private String RECONCILED;
	private String INITIAL_CONTACT_DATE;
	
	public long getLAFT_RECON_SUMM_PK() {
		return LAFT_RECON_SUMM_PK;
	}
	public void setLAFT_RECON_SUMM_PK(long lAFT_RECON_SUMM_PK) {
		LAFT_RECON_SUMM_PK = lAFT_RECON_SUMM_PK;
	}
	public String getLAFT_RECON_ID() {
		return LAFT_RECON_ID;
	}
	public void setLAFT_RECON_ID(String lAFT_RECON_ID) {
		LAFT_RECON_ID = lAFT_RECON_ID;
	}
	public String getPFI() {
		return PFI;
	}
	public void setPFI(String pFI) {
		PFI = pFI;
	}
	public String getCLIA() {
		return CLIA;
	}
	public void setCLIA(String cLIA) {
		CLIA = cLIA;
	}
	public String getLAFT_TEST_CD() {
		return LAFT_TEST_CD;
	}
	public void setLAFT_TEST_CD(String lAFT_TEST_CD) {
		LAFT_TEST_CD = lAFT_TEST_CD;
	}
	public int getINITIAL_LAB_COUNT() {
		return INITIAL_LAB_COUNT;
	}
	public void setINITIAL_LAB_COUNT(int iNITIAL_LAB_COUNT) {
		INITIAL_LAB_COUNT = iNITIAL_LAB_COUNT;
	}
	public int getCURRENT_LAB_COUNT() {
		return CURRENT_LAB_COUNT;
	}
	public void setCURRENT_LAB_COUNT(int cURRENT_LAB_COUNT) {
		CURRENT_LAB_COUNT = cURRENT_LAB_COUNT;
	}
	public int getSURVEY_LAB_COUNT() {
		return SURVEY_LAB_COUNT;
	}
	public void setSURVEY_LAB_COUNT(int sURVEY_LAB_COUNT) {
		SURVEY_LAB_COUNT = sURVEY_LAB_COUNT;
	}
	public int getADJUSTED_SURVEY_LAB_COUNT() {
		return ADJUSTED_SURVEY_LAB_COUNT;
	}
	public void setADJUSTED_SURVEY_LAB_COUNT(int aDJUSTED_SURVEY_LAB_COUNT) {
		ADJUSTED_SURVEY_LAB_COUNT = aDJUSTED_SURVEY_LAB_COUNT;
	}
	public int getCOUNT_DIFFERENCE() {
		return COUNT_DIFFERENCE;
	}
	public void setCOUNT_DIFFERENCE(int cOUNT_DIFFERENCE) {
		COUNT_DIFFERENCE = cOUNT_DIFFERENCE;
	}
	public String getDATE_RESOLVED() {
		return DATE_RESOLVED;
	}
	public void setDATE_RESOLVED(String dATE_RESOLVED) {
		DATE_RESOLVED = dATE_RESOLVED;
	}
	public String getRECONCILED() {
		return RECONCILED;
	}
	public void setRECONCILED(String rECONCILED) {
		RECONCILED = rECONCILED;
	}
	public String getINITIAL_CONTACT_DATE() {
		return INITIAL_CONTACT_DATE;
	}
	public void setINITIAL_CONTACT_DATE(String iNITIAL_CONTACT_DATE) {
		INITIAL_CONTACT_DATE = iNITIAL_CONTACT_DATE;
	}
	public String getUPDATE_WORKER_ID() {
		return UPDATE_WORKER_ID;
	}
	public void setUPDATE_WORKER_ID(String uPDATE_WORKER_ID) {
		UPDATE_WORKER_ID = uPDATE_WORKER_ID;
	}

}
