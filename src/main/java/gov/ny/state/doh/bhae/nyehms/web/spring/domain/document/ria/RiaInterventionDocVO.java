package gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.ria;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.NYEHMSDocVO;

public class RiaInterventionDocVO extends NYEHMSDocVO {
	
	private String INTERVENTION_CD;
	private String INTERVENTION_OTHER_TEXT;
	private String FOLLOW_UP_CD;;
	private String INTERVENTION_DATE;
	
	public String getINTERVENTION_CD() {
		return INTERVENTION_CD;
	}
	public void setINTERVENTION_CD(String iNTERVENTION_CD) {
		INTERVENTION_CD = iNTERVENTION_CD;
	}
	public String getINTERVENTION_OTHER_TEXT() {
		return INTERVENTION_OTHER_TEXT;
	}
	public void setINTERVENTION_OTHER_TEXT(String iNTERVENTION_OTHER_TEXT) {
		INTERVENTION_OTHER_TEXT = iNTERVENTION_OTHER_TEXT;
	}
	public String getFOLLOW_UP_CD() {
		return FOLLOW_UP_CD;
	}
	public void setFOLLOW_UP_CD(String fOLLOW_UP_CD) {
		FOLLOW_UP_CD = fOLLOW_UP_CD;
	}
	public String getINTERVENTION_DATE() {
		return INTERVENTION_DATE;
	}
	public void setINTERVENTION_DATE(String iNTERVENTION_DATE) {
		INTERVENTION_DATE = iNTERVENTION_DATE;
	}
	
	
	

}
