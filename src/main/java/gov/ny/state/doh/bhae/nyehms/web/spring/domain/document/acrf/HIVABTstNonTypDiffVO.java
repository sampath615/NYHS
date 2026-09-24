package gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.acrf;

public class HIVABTstNonTypDiffVO {

	private String test="";
	private String testDesc="";
	private String result="";
	private boolean rapidTst;
	private String date="";
	private String parentTest="";

	public String getTest() {
		return test;
	}
	public void setTest(String test) {
		this.test = test;
	}
	public String getTestDesc() {
		return testDesc;
	}
	public void setTestDesc(String testDesc) {
		this.testDesc = testDesc;
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
	public boolean isRapidTst() {
		return rapidTst;
	}
	public void setRapidTst(boolean rapidTst) {
		this.rapidTst = rapidTst;
	}
	
	

}
