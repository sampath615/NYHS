package gov.ny.state.doh.bhae.nyehms.web.spring.administration.systemmessage.web;

import java.time.LocalDateTime;

import gov.ny.state.doh.bhae.nyehms.web.spring.platform.web.PickerDateTime;

/**
 * Form fields posted by systemMessageManage.js to /saveSystemMessage.json and /updateSystemMessage.json.
 * Dates come from the date-time picker as text, e.g. "10/22/2026 01:05:00 PM".
 *
 * @param messageId  only on update
 * @param deleted    "Y" when the page deletes the message (only on update)
 */
public record SystemMessageForm(
        Long messageId,
        String messageBody,
        String effectiveStartDate,
        String effectiveEndDate,
        String deleted) {

    public LocalDateTime start() {
        return PickerDateTime.parse(effectiveStartDate);
    }

    public LocalDateTime end() {
        return PickerDateTime.parse(effectiveEndDate);
    }

    public boolean isDelete() {
        return "Y".equals(deleted);
    }
}
