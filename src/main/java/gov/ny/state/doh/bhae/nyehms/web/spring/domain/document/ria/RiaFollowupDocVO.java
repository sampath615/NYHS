package gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.ria;

import java.util.List;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.NYEHMSDocVO;

public class RiaFollowupDocVO extends NYEHMSDocVO {
	
	private Integer RIA_FOLLOW_UP_ID;
	private String PROGRAM_TYPE;
	private String FOLLOW_UP_CD;
	private String FOLLOW_UP_DESC;
	private String FOLLOWUP_OTHER_TEXT;
	private List<RiaInterventionDocVO> interventionDocList;
	
	
	public Integer getRIA_FOLLOW_UP_ID() {
		return RIA_FOLLOW_UP_ID;
	}
	public void setRIA_FOLLOW_UP_ID(Integer rIA_FOLLOW_UP_ID) {
		RIA_FOLLOW_UP_ID = rIA_FOLLOW_UP_ID;
	}
	public String getPROGRAM_TYPE() {
		return PROGRAM_TYPE;
	}
	public void setPROGRAM_TYPE(String pROGRAM_TYPE) {
		PROGRAM_TYPE = pROGRAM_TYPE;
	}
	public String getFOLLOW_UP_CD() {
		return FOLLOW_UP_CD;
	}
	public void setFOLLOW_UP_CD(String fOLLOW_UP_CD) {
		FOLLOW_UP_CD = fOLLOW_UP_CD;
	}
	public String getFOLLOW_UP_DESC() {
		return FOLLOW_UP_DESC;
	}
	public void setFOLLOW_UP_DESC(String fOLLOW_UP_DESC) {
		FOLLOW_UP_DESC = fOLLOW_UP_DESC;
	}
	public String getFOLLOWUP_OTHER_TEXT() {
		return FOLLOWUP_OTHER_TEXT;
	}
	public void setFOLLOWUP_OTHER_TEXT(String fOLLOWUP_OTHER_TEXT) {
		FOLLOWUP_OTHER_TEXT = fOLLOWUP_OTHER_TEXT;
	}
	public List<RiaInterventionDocVO> getInterventionDocList() {
		return interventionDocList;
	}
	public void setInterventionDocList(List<RiaInterventionDocVO> interventionDocList) {
		this.interventionDocList = interventionDocList;
	}
	
	

}
