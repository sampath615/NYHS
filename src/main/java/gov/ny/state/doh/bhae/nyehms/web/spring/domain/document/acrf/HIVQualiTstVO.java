package gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.acrf;

//import org.hibernate.validator.constraints.NotEmpty;

public class HIVQualiTstVO {
		
	private String test="";
	private String result="";
	private String date="";
	private String parentTest="";
	
	public String getTest() {
		return test;
	}
	public void setTest(String test) {
		this.test = test;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getParentTest() {
		return parentTest;
	}
	public void setParentTest(String parentTest) {
		this.parentTest = parentTest;
	}

}
