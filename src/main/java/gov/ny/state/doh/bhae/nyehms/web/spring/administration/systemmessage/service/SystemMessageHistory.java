package gov.ny.state.doh.bhae.nyehms.web.spring.administration.systemmessage.service;

import java.util.List;

/**
 * History of one message. Kept in the old shape because the page reads
 * {@code response.rows[0].systemMessageArchives}.
 */
public record SystemMessageHistory(Long messageId, List<SystemMessageDto> systemMessageArchives) {
}
