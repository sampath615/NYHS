package gov.ny.state.doh.bhae.nyehms.web.spring.record;

/**
 * One code of a coded column of the Black Box document screen (BlackBoxDocument.jsp, model
 * attribute {@code documentRefData}); replaces the former map with REF_COLUMN, LOOKUP_NAME and
 * LOOKUP_CD.
 *
 * @param refColumn  the coded column (e.g. TRANS_CATEG)
 * @param lookupName the name shown for the code
 * @param lookupCd   the code
 */
public record BlackBoxRefData(String refColumn, String lookupName, String lookupCd) {
}
