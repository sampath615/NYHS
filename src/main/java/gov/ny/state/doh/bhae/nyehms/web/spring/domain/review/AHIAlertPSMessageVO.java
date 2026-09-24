package gov.ny.state.doh.bhae.nyehms.web.spring.domain.review;

public class AHIAlertPSMessageVO {
	
	private String TRIGGER_MSG_TYPE;
	private String SEND_DATE;
	private String RETURN_DATE;
	private String LATEST_DISPOSITION_DESC;
	
	public String getTRIGGER_MSG_TYPE() {
		return TRIGGER_MSG_TYPE;
	}
	public void setTRIGGER_MSG_TYPE(String tRIGGER_MSG_TYPE) {
		TRIGGER_MSG_TYPE = tRIGGER_MSG_TYPE;
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
	public String getLATEST_DISPOSITION_DESC() {
		return LATEST_DISPOSITION_DESC;
	}
	public void setLATEST_DISPOSITION_DESC(String lATEST_DISPOSITION_DESC) {
		LATEST_DISPOSITION_DESC = lATEST_DISPOSITION_DESC;
	}

}
