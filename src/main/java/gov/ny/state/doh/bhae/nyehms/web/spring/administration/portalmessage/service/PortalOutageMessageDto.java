package gov.ny.state.doh.bhae.nyehms.web.spring.administration.portalmessage.service;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import gov.ny.state.doh.bhae.nyehms.web.spring.platform.web.PickerDateTime;

/**
 * One row of the Portal System Message grid. Names (snake_case) are the ones portalsystemMessage.js
 * reads, kept from the old PortalSystemMessage VO.
 */
public record PortalOutageMessageDto(
        Long msgId,
        /** Column name of the targeted module (ADMIN, PRF_DE, ...) or " ". */
        String module_name,
        String admin,
        String prf_de,
        String ooc_inq,
        String dnltc_inq,
        String snp_id,
        String airs_epi,
        @JsonFormat(pattern = PickerDateTime.PATTERN, locale = "en_US") LocalDateTime start_date,
        @JsonFormat(pattern = PickerDateTime.PATTERN, locale = "en_US") LocalDateTime end_date,
        String display_msg,
        String flag_deleted,
        String create_by,
        @JsonFormat(pattern = "MM/dd/yyyy") LocalDateTime create_date,
        /** " " when nobody updated it (old SQL: COALESCE(UPDATE_BY, ' ')). */
        String update_by,
        @JsonFormat(pattern = "MM/dd/yyyy") LocalDateTime update_date) {
}
