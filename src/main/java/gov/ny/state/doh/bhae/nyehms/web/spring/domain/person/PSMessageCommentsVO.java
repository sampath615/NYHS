package gov.ny.state.doh.bhae.nyehms.web.spring.domain.person;

public class PSMessageCommentsVO {

	private Integer DOCUMENT_ID;
	private String CREATE_ID;
	private String COMMENTS;
	private String CREATE_DATE;
	
	   
	
	public Integer getDOCUMENT_ID() {
		return DOCUMENT_ID;
	}
	public void setDOCUMENT_ID(Integer dOCUMENT_ID) {
		DOCUMENT_ID = dOCUMENT_ID;
	}
	
	public String getCREATE_ID() {
		return CREATE_ID;
	}
	public void setCREATE_ID(String cREATE_ID) {
		CREATE_ID = cREATE_ID;
	}
	public String getCOMMENTS() {
		return COMMENTS;
	}
	public void setCOMMENTS(String cOMMENTS) {
		COMMENTS = cOMMENTS;
	}
	public String getCREATE_DATE() {
		return CREATE_DATE;
	}
	public void setCREATE_DATE(String cREATE_DATE) {
		CREATE_DATE = cREATE_DATE;
	}
	
	
	
	
}
