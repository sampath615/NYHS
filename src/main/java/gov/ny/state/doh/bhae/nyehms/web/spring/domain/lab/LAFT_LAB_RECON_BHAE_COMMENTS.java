package gov.ny.state.doh.bhae.nyehms.web.spring.domain.lab;

public class LAFT_LAB_RECON_BHAE_COMMENTS {
	
	private String LAFT_RECON_ID;
	private String PFI;
	private String BHAE_COMMENTS;
	private String CREATE_DATE;
	private String WORKER_ID;
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
	public String getBHAE_COMMENTS() {
		return BHAE_COMMENTS;
	}
	public void setBHAE_COMMENTS(String bHAE_COMMENTS) {
		BHAE_COMMENTS = bHAE_COMMENTS;
	}
	public String getCREATE_DATE() {
		return CREATE_DATE;
	}
	public void setCREATE_DATE(String cREATE_DATE) {
		CREATE_DATE = cREATE_DATE;
	}
	public String getWORKER_ID() {
		return WORKER_ID;
	}
	public void setWORKER_ID(String wORKER_ID) {
		WORKER_ID = wORKER_ID;
	}

}
