package gov.ny.state.doh.bhae.nyehms.web.spring.entity.administration;

import java.sql.Timestamp;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

/**
 * The earlier versions of the system messages (view NYEHMS_SYSTEM_MSG_HISTORY_VW); read-only.
 * <p>
 * The view has several rows per MSG_ID, so it is only read through the projection
 * {@code SystemMessageHistoryRow} (each row as it is), never as entities: MSG_ID is the key only for the mapping.
 */
@Entity
@Immutable
@Getter
@Table(name = "NYEHMS_SYSTEM_MSG_HISTORY_VW")
public class SystemMessageHistoryView {

	@Id
	@Column(name = "MSG_ID")
	private Long msgId;

	@Column(name = "MSG_BODY")
	private String msgBody;

	@Column(name = "CREATED_BY_NAME")
	private String createdByName;

	@Column(name = "EFFECTIVE_START_DATE")
	private Timestamp effectiveStartDate;

	@Column(name = "EFFECTIVE_END_DATE")
	private Timestamp effectiveEndDate;

	@Column(name = "FLAG_DELETED")
	private String flagDeleted;

	@Column(name = "LAST_UPDATED_DATE")
	private Timestamp lastUpdatedDate;

	@Column(name = "LAST_UPDATED_BY_NAME")
	private String lastUpdatedByName;

	@Column(name = "LAST_UPDATED_SHORT_NAME")
	private String lastUpdatedShortName;

	@Column(name = "CREATED_BY_SHORT_NAME")
	private String createdByShortName;
}
