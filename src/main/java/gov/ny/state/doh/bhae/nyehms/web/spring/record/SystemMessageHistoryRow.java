package gov.ny.state.doh.bhae.nyehms.web.spring.record;

import java.sql.Timestamp;

/**
 * One row of the system message history view (NYEHMS_SYSTEM_MSG_HISTORY_VW), read as a projection so that several
 * rows of one message stay separate rows.
 */
public record SystemMessageHistoryRow(Long msgId,
		String msgBody,
		String createdByName,
		Timestamp effectiveStartDate,
		Timestamp effectiveEndDate,
		String flagDeleted,
		Timestamp lastUpdatedDate,
		String lastUpdatedByName,
		String lastUpdatedShortName,
		String createdByShortName) {
}
