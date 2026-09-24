package gov.ny.state.doh.bhae.nyehms.web.spring.domain.test;

import javax.validation.Valid;

//import org.hibernate.validator.constraints.NotEmpty;

public class Person {
		
	@Valid
	private PersonInfo personInfo;
	
	//@NotEmpty 
	private String LAST_NAME="";
	

	public PersonInfo getPersonInfo() {
		return personInfo;
	}

	public void setPersonInfo(PersonInfo personInfo) {
		this.personInfo = personInfo;
	}

	public String getLAST_NAME() {
		return LAST_NAME;
	}

	public void setLAST_NAME(String lAST_NAME) {
		LAST_NAME = lAST_NAME;
	}	
	
}
