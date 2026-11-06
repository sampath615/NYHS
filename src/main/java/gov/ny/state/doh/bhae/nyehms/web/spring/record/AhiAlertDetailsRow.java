package gov.ny.state.doh.bhae.nyehms.web.spring.record;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.document.AhiAlertTracking;

/**
 * An AHI alert with the values of its tracking form (read by AHIDocumentDAO.getAhiAlertDetails).
 *
 * @param alert the alert (AHI_ALERT_TRACKING)
 * @param flagSetBy the FULL_NAME (NYEHMS_USERS_VW) of the worker who created the alert, or null
 * @param alertSourceDesc the description of the alert's source, or null
 * @param prfCollectionDesc the description of the PRF collection answer, or null
 * @param lastUpdatedBy the FULL_NAME of the worker who last updated the alert, or null
 * @param assignmentId the open surveillance assignment of the person flagged AHI, or null
 * @param triggeringDocType the document (or test) type of the document that triggered an open assignment of the
 *        hard-coded person 0010930066 (a bug kept as it was)
 */
public record AhiAlertDetailsRow(AhiAlertTracking alert, String flagSetBy, String alertSourceDesc, String prfCollectionDesc,
		String lastUpdatedBy, Long assignmentId, String triggeringDocType) {
}
