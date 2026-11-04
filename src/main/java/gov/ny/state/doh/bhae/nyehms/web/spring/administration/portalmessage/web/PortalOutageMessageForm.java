package gov.ny.state.doh.bhae.nyehms.web.spring.administration.portalmessage.web;

import java.time.LocalDateTime;

import gov.ny.state.doh.bhae.nyehms.web.spring.platform.web.PickerDateTime;

/**
 * Fields posted by portalsystemMessage.js to /savePortalSystemMessage.json and /updatePortalSystemMessage.json.
 *
 * @param msgId        only on update
 * @param module_name  page code from the drop-down (ADMIN_PAGE, EPRF_DE, OOC_INQ, ...)
 * @param end_date     optional; the grid sends "" or " " for no end date
 * @param flag_deleted on update: "Y" = delete, "N" = edit
 */
public record PortalOutageMessageForm(
        Long msgId,
        String module_name,
        String start_date,
        String end_date,
        String display_msg,
        String flag_deleted) {

    public LocalDateTime start() {
        return PickerDateTime.parse(start_date);
    }

    public LocalDateTime end() {
        return PickerDateTime.parseOptional(end_date);
    }

    /** Old code: anything but 'N' took the "delete" branch. */
    public boolean isDelete() {
        return !"N".equals(flag_deleted);
    }
}
