package gov.ny.state.doh.bhae.nyehms.web.spring.domain.dataentry;

public class ICCRTestVO {
	
	private Integer ICCR_DOCUMENT_ID;
	private String DATE_OF_TEST;
	private String TEST_TYPE;
	private String TEST_RESULTS;
	private String TEST_RESULT_COPIES;
	private String TEST_RESULT_LOGS;
	
	public Integer getICCR_DOCUMENT_ID() {
		return ICCR_DOCUMENT_ID;
	}
	public void setICCR_DOCUMENT_ID(Integer iCCR_DOCUMENT_ID) {
		ICCR_DOCUMENT_ID = iCCR_DOCUMENT_ID;
	}
	public String getDATE_OF_TEST() {
		return DATE_OF_TEST;
	}
	public void setDATE_OF_TEST(String dATE_OF_TEST) {
		DATE_OF_TEST = dATE_OF_TEST;
	}
	public String getTEST_TYPE() {
		return TEST_TYPE;
	}
	public void setTEST_TYPE(String tEST_TYPE) {
		TEST_TYPE = tEST_TYPE;
	}
	public String getTEST_RESULTS() {
		return TEST_RESULTS;
	}
	public void setTEST_RESULTS(String tEST_RESULTS) {
		TEST_RESULTS = tEST_RESULTS;
	}
	public String getTEST_RESULT_COPIES() {
		return TEST_RESULT_COPIES;
	}
	public void setTEST_RESULT_COPIES(String tEST_RESULT_COPIES) {
		TEST_RESULT_COPIES = tEST_RESULT_COPIES;
	}
	public String getTEST_RESULT_LOGS() {
		return TEST_RESULT_LOGS;
	}
	public void setTEST_RESULT_LOGS(String tEST_RESULT_LOGS) {
		TEST_RESULT_LOGS = tEST_RESULT_LOGS;
	}

}
