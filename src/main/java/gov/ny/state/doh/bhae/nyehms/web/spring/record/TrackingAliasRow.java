package gov.ny.state.doh.bhae.nyehms.web.spring.record;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.common.TrackingUserEnteredAlias;

/**
 * A tracking alias with the name of the worker who entered it (read by TrackingDocumentDAO.getTRKAliasDoc).
 *
 * @param alias the alias (TRACKING_USER_ENTERED_ALIAS)
 * @param createdByName PKG_TRK_COMMON.fn_get_fullname of the worker whose LEGACY_UID is the alias's CREATEID; null if
 *        there is no such worker
 */
public record TrackingAliasRow(TrackingUserEnteredAlias alias, String createdByName) {
}
