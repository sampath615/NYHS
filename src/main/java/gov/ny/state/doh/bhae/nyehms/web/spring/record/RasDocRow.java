package gov.ny.state.doh.bhae.nyehms.web.spring.record;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.common.StdRasDoc;

/**
 * A resistance document with its current person match, as RASDocumentDAO.getRASDoc reads it.
 *
 * @param doc the standardized document (STD_RAS_DOC)
 * @param matchedStateno STATENO of the current person match (PERSON_DOC_ASSOCIATION), null if there is none
 */
public record RasDocRow(StdRasDoc doc, String matchedStateno) {
}
