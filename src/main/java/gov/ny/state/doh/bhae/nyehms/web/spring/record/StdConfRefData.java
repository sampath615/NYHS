package gov.ny.state.doh.bhae.nyehms.web.spring.record;

/**
 * One code of a coded column of the confirmed STD reports (STDConfDocument.jsp, model attribute
 * {@code documentRefData}; the PS confirmed STD upload checks); replaces the former map with REF_COLUMN,
 * LOOKUP_NAME and LOOKUP_CD.
 *
 * @param refColumn  the coded column (e.g. X900TEST)
 * @param lookupName the name shown for the code
 * @param lookupCd   the code
 */
public record StdConfRefData(String refColumn, String lookupName, String lookupCd) {
}
