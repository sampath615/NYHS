package gov.ny.state.doh.bhae.nyehms.web.spring.record;

/**
 * A test of an ICCR (Infection Control) report as the ICCR document screen shows it (ICCRDocument.jsp,
 * {@code stdICCRDocVO.iccrTestList[i].<component>}); replaces the former ICCRTestVO of that screen.
 *
 * @param dateOfTest DATE_OF_TEST as MM/DD/YYYY
 * @param testType the name of the test type (CODED_ACRF_TEST.ACRF_TEST_DESC)
 * @param testResults the name of the result (CODED_ACRF_TEST_RESULT.ACRF_RESULT_DESC)
 * @param testResultCopies TEST_RESULT_COPIES (copies/mL)
 * @param testResultLogs TEST_RESULT_LOGS (log)
 */
public record IccrTestData(String dateOfTest, String testType, String testResults, String testResultCopies, String testResultLogs) {
}
