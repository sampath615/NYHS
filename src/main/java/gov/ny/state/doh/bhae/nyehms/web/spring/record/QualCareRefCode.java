package gov.ny.state.doh.bhae.nyehms.web.spring.record;

/**
 * One code of a coded column of the quality of care records (CODED_QUAL_CARE_REF_DATA).
 *
 * @param refColumn the coded column (e.g. ASIAN_SUB_GROUP)
 * @param lookupCd the code
 * @param lookupName the name of the code
 */
public record QualCareRefCode(String refColumn, String lookupCd, String lookupName) {
}
