package gov.ny.state.doh.bhae.nyehms.web.spring.domain.document;



/**
 * This is POJO class for NDI Cause document.
 *
 * @author ska03
 */
public class NonCaseDocVO {
	private int TRIGGERING_DOC_ID;
	private String CREATE_ID;
	private String CREATE_DATE;
	private String FLAG_ACTIVE;
	private String REASON_DESC;
	private String COMMENTS;
	private String STATENO;
	
	
	public int getTRIGGERING_DOC_ID() {
		return TRIGGERING_DOC_ID;
	}
	public void setTRIGGERING_DOC_ID(int tRIGGERING_DOC_ID) {
		TRIGGERING_DOC_ID = tRIGGERING_DOC_ID;
	}
	
	public String getCREATE_ID() {
		return CREATE_ID;
	}
	public void setCREATE_ID(String cREATE_ID) {
		CREATE_ID = cREATE_ID;
	}
	public String getCREATE_DATE() {
		return CREATE_DATE;
	}
	public void setCREATE_DATE(String cREATE_DATE) {
		CREATE_DATE = cREATE_DATE;
	}
	public String getFLAG_ACTIVE() {
		return FLAG_ACTIVE;
	}
	public void setFLAG_ACTIVE(String fLAG_ACTIVE) {
		FLAG_ACTIVE = fLAG_ACTIVE;
	}
	public String getREASON_DESC() {
		return REASON_DESC;
	}
	public void setREASON_DESC(String rEASON_DESC) {
		REASON_DESC = rEASON_DESC;
	}
	public String getCOMMENTS() {
		return COMMENTS;
	}
	public void setCOMMENTS(String cOMMENTS) {
		COMMENTS = cOMMENTS;
	}
	public String getSTATENO() {
		return STATENO;
	}
	public void setSTATENO(String sTATENO) {
		STATENO = sTATENO;
	}
	
	
	
	
}	
