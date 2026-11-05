package gov.ny.state.doh.bhae.nyehms.web.spring.record;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.document.TrackingUserEnteredAddress;

/**
 * A tracking address with the name of the worker who entered it (read by TrackingDocumentDAO.getTRKAddressDoc).
 *
 * @param address the address (TRACKING_USER_ENTERED_ADDRESS)
 * @param createdByName PKG_TRK_COMMON.fn_get_fullname of the worker whose LEGACY_UID is the address's CREATEID; null if
 *        there is no such worker
 */
public record TrackingAddressRow(TrackingUserEnteredAddress address, String createdByName) {
}
