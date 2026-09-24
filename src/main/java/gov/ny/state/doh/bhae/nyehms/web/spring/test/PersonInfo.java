package gov.ny.state.doh.bhae.nyehms.web.spring.domain.test;

//import org.hibernate.validator.constraints.NotEmpty;

public class PersonInfo {
	
	//@NotEmpty 
	private String FIRST_NAME="";

	public String getFIRST_NAME() {
		return FIRST_NAME;
	}

	public void setFIRST_NAME(String fIRST_NAME) {
		FIRST_NAME = fIRST_NAME;
	}

}
