package gov.ny.state.doh.bhae.nyehms.web.spring.domain.document;

public class ClusterDocPSMessageVO {
	
	private int DOCUMENT_ID;
	private int TRIGGER_DOC_ID;
	private String DOCUMENT_TYPE;
	private String SEND_DATE;
	private String RETURN_DATE;
	private String RECENT_RETURN_DISPO;
	
	public int getDOCUMENT_ID() {
		return DOCUMENT_ID;
	}
	public void setDOCUMENT_ID(int dOCUMENT_ID) {
		DOCUMENT_ID = dOCUMENT_ID;
	}
	public int getTRIGGER_DOC_ID() {
		return TRIGGER_DOC_ID;
	}
	public void setTRIGGER_DOC_ID(int tRIGGER_DOC_ID) {
		TRIGGER_DOC_ID = tRIGGER_DOC_ID;
	}
	public String getDOCUMENT_TYPE() {
		return DOCUMENT_TYPE;
	}
	public void setDOCUMENT_TYPE(String dOCUMENT_TYPE) {
		DOCUMENT_TYPE = dOCUMENT_TYPE;
	}
	public String getSEND_DATE() {
		return SEND_DATE;
	}
	public void setSEND_DATE(String sEND_DATE) {
		SEND_DATE = sEND_DATE;
	}
	public String getRETURN_DATE() {
		return RETURN_DATE;
	}
	public void setRETURN_DATE(String rETURN_DATE) {
		RETURN_DATE = rETURN_DATE;
	}
	public String getRECENT_RETURN_DISPO() {
		return RECENT_RETURN_DISPO;
	}
	public void setRECENT_RETURN_DISPO(String rECENT_RETURN_DISPO) {
		RECENT_RETURN_DISPO = rECENT_RETURN_DISPO;
	}
	

	
	

}
