package gov.ny.state.doh.bhae.nyehms.web.spring.record;

import java.sql.Timestamp;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.document.AhiAlertTracking;

/**
 * An AHI alert joined with one of its follow-up steps (read by AHIDocumentDAO.getAhiFlaggedStatenoData, which keeps the
 * latest step of each alert).
 *
 * @param alert the alert (AHI_ALERT_TRACKING)
 * @param alertSourceDesc the description of the alert's source, or null
 * @param prfCollectionDesc the description of the PRF collection answer, or null
 * @param flagSetBy the full name (PKG_TRK_COMMON.fn_get_fullname) of the worker who created the alert
 * @param lastUpdatedBy the full name of the worker who last updated it
 * @param completedBy the full name of the worker who completed it
 * @param reviewedBy the full name of the worker who reviewed it
 * @param followUpCode the follow-up step's FOLLOW_UP_CODE (AHI_ALERT_FOLLOWUP), or null if the alert has none
 * @param followUpDate the follow-up step's FOLLOW_UP_DATE
 * @param followupStepTakenCode the step's code as found in CODED_AHI_PRF_FOLLOWUP, or null
 * @param followupStepTaken the step's description, or null
 * @param psMsgExist 'Y' if a partner services message was sent (or is in process) for the person, else 'N'
 */
public record AhiAlertListRow(AhiAlertTracking alert, String alertSourceDesc, String prfCollectionDesc, String flagSetBy,
		String lastUpdatedBy, String completedBy, String reviewedBy, String followUpCode, Timestamp followUpDate,
		String followupStepTakenCode, String followupStepTaken, String psMsgExist) {
}
