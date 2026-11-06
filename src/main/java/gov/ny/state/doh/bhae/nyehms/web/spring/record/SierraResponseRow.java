package gov.ny.state.doh.bhae.nyehms.web.spring.record;

import java.sql.Timestamp;

/**
 * A Sierra resistance response with the person of its HIV lab report, as RASDocumentDAO.getSierraResponseData reads it.
 *
 * @param fname FNAME of the lab report (STD_HIV_ECLRS_DOC)
 * @param lname LNAME of the lab report
 * @param mname MNAME of the lab report
 * @param dobText DOB_TEXT of the lab report
 * @param seqResultId SEQ_RESULT_ID of the response (RAS_SI_RESPONSE)
 * @param documentId DOCUMENT_ID of the response
 * @param collectionDate CALC_COLLECTION_DATE of the lab report
 * @param subType SUB_TYPE of the response
 */
public record SierraResponseRow(String fname, String lname, String mname, String dobText, Long seqResultId,
		Integer documentId, Timestamp collectionDate, String subType) {
}
