package gov.ny.state.doh.bhae.nyehms.web.spring.record;

/**
 * One line of the NYC feedback file summary (read by ADAPDocumentDAO.generateNYCFileSummary).
 *
 * @param stateno the person the ADAP document is associated with (an open association)
 * @param cityno the NYC number NYC returned (NYCNUMBER)
 * @param reviewedCityno the city number the reviewer entered
 * @param reviewStatus the review status
 * @param reviewComments the reviewer's comments
 */
public record AdapNycFileSummaryRow(String stateno, String cityno, String reviewedCityno, String reviewStatus, String reviewComments) {
}
