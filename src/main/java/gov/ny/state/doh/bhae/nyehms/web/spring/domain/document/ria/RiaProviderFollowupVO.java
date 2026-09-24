package gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.ria;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.NYEHMSDocVO;

public class RiaProviderFollowupVO extends NYEHMSDocVO {
	
	private Integer RIA_PROVIDER_FOLLOW_UP_ID;
	private String DATE_OF_CONTACT;
	private String STATENO;
	private String CONTACT_REASON;
	private String PROVIDER;
	private String PROVIDER_ROLE;
	private String FACILITY;
	private String CONTACT_PERSON;
	private String RIA_MATERIALS_PROVIDED;
	private String CREATE_DATE;
	
	

	
	public Integer getRIA_PROVIDER_FOLLOW_UP_ID() {
		return RIA_PROVIDER_FOLLOW_UP_ID;
	}
	public void setRIA_PROVIDER_FOLLOW_UP_ID(Integer rIA_PROVIDER_FOLLOW_UP_ID) {
		RIA_PROVIDER_FOLLOW_UP_ID = rIA_PROVIDER_FOLLOW_UP_ID;
	}
	public String getDATE_OF_CONTACT() {
		return DATE_OF_CONTACT;
	}
	public void setDATE_OF_CONTACT(String dATE_OF_CONTACT) {
		DATE_OF_CONTACT = dATE_OF_CONTACT;
	}
	public String getSTATENO() {
		return STATENO;
	}
	public void setSTATENO(String sTATENO) {
		STATENO = sTATENO;
	}
	public String getCONTACT_REASON() {
		return CONTACT_REASON;
	}
	public void setCONTACT_REASON(String cONTACT_REASON) {
		CONTACT_REASON = cONTACT_REASON;
	}
	public String getPROVIDER() {
		return PROVIDER;
	}
	public void setPROVIDER(String pROVIDER) {
		PROVIDER = pROVIDER;
	}
	public String getPROVIDER_ROLE() {
		return PROVIDER_ROLE;
	}
	public void setPROVIDER_ROLE(String pROVIDER_ROLE) {
		PROVIDER_ROLE = pROVIDER_ROLE;
	}
	public String getFACILITY() {
		return FACILITY;
	}
	public void setFACILITY(String fACILITY) {
		FACILITY = fACILITY;
	}
	public String getCONTACT_PERSON() {
		return CONTACT_PERSON;
	}
	public void setCONTACT_PERSON(String cONTACT_PERSON) {
		CONTACT_PERSON = cONTACT_PERSON;
	}
	public String getRIA_MATERIALS_PROVIDED() {
		return RIA_MATERIALS_PROVIDED;
	}
	public void setRIA_MATERIALS_PROVIDED(String rIA_MATERIALS_PROVIDED) {
		RIA_MATERIALS_PROVIDED = rIA_MATERIALS_PROVIDED;
	}
	public String getCREATE_DATE() {
		return CREATE_DATE;
	}
	public void setCREATE_DATE(String cREATE_DATE) {
		CREATE_DATE = cREATE_DATE;
	}
	
	
	
	
	
	
	
}
