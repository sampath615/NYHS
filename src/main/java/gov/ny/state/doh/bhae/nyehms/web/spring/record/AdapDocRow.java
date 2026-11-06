package gov.ny.state.doh.bhae.nyehms.web.spring.record;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.common.StdAdapDoc;

/**
 * A standardized ADAP record with the values the ADAP document screen adds (read by ADAPDocumentDAO.getADAPDoc).
 *
 * @param doc the standardized record (STD_ADAP_DOC)
 * @param street the received record's STREET (RAW_ADAP_DOC)
 * @param apt the received record's APT (RAW_ADAP_DOC)
 * @param parentDocumentId the document's PARENT_DOCUMENT_ID (DOCUMENT_LOCATOR)
 * @param stateno the person the document is associated with (PERSON_DOC_ASSOCIATION, an open association), or null
 * @param discardStatus that association's DISCARD_STATUS, or null
 */
public record AdapDocRow(StdAdapDoc doc, String street, String apt, Integer parentDocumentId, String stateno, String discardStatus) {
}
