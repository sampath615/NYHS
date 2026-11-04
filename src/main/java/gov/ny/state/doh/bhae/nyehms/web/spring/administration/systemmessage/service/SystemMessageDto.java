package gov.ny.state.doh.bhae.nyehms.web.spring.administration.systemmessage.service;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import gov.ny.state.doh.bhae.nyehms.web.spring.platform.web.PickerDateTime;

/**
 * One row of the System Message Manage grids (active, inactive and history).
 * Names are the ones systemMessageManage.js reads (colModel names, rowObject.*, getRowData).
 * Dates go out as the text the grid's date formatter expects ('m/d/Y h:i:s A'),
 * same as the old SQL TO_CHAR(..., 'MM/DD/YYYY HH:MI:SS AM').
 */
public record SystemMessageDto(
        Long messageId,
        String messageBody,
        String createdByName,
        String createdByShortName,
        @JsonFormat(pattern = PickerDateTime.PATTERN, locale = "en_US") LocalDateTime effectiveStartDate,
        @JsonFormat(pattern = PickerDateTime.PATTERN, locale = "en_US") LocalDateTime effectiveEndDate,
        @JsonFormat(pattern = PickerDateTime.PATTERN, locale = "en_US") LocalDateTime lastUpdatedDate,
        Long lastUpdatedBy,
        String lastUpdatedByName,
        String lastUpdatedByShortName,
        /** 'Y' = has been changed since it was created (shows the history icon). Not set on history rows. */
        String historyFlag,
        /** 'Y' = deleted. Only set for inactive and history rows. */
        String deleted) {
}
