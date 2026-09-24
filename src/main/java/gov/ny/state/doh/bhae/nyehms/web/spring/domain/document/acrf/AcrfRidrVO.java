package gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.acrf;

public class AcrfRidrVO {
	private String DUPLICATE_STATUS;
	private String RIDR_ROWID;
	private String STATE_CD;
	private String STATENO;
	private String COMMENTS;
	private int documentId;
	
	
	
	
	public String getRIDR_ROWID() {
		return RIDR_ROWID;
	}
	public void setRIDR_ROWID(String rIDR_ROWID) {
		RIDR_ROWID = rIDR_ROWID;
	}
	public String getDUPLICATE_STATUS() {
		return DUPLICATE_STATUS;
	}
	public void setDUPLICATE_STATUS(String dUPLICATE_STATUS) {
		DUPLICATE_STATUS = dUPLICATE_STATUS;
	}
	public String getSTATE_CD() {
		return STATE_CD;
	}
	public void setSTATE_CD(String sTATE_CD) {
		STATE_CD = sTATE_CD;
	}
	public String getSTATENO() {
		return STATENO;
	}
	public void setSTATENO(String sTATENO) {
		STATENO = sTATENO;
	}
	public String getCOMMENTS() {
		return COMMENTS;
	}
	public void setCOMMENTS(String cOMMENTS) {
		COMMENTS = cOMMENTS;
	}
	public int getDocumentId() {
		return documentId;
	}
	public void setDocumentId(int documentId) {
		this.documentId = documentId;
	}
	
	
		
	
}
