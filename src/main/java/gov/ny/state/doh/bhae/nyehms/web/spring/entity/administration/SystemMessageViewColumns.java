package gov.ny.state.doh.bhae.nyehms.web.spring.entity.administration;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;

/**
 * The columns of the three system message views (active, inactive, history), read by SystemMessageDAO.
 * <p>
 * The views are only read through projections (select items, never whole entities), so a key that repeats (the
 * history view has several rows per MSG_ID) does not matter.
 */
@MappedSuperclass
@Getter
public abstract class SystemMessageViewColumns {

	@Id
	@Column(name = "MSG_ID")
	private Long msgId;

	@Column(name = "MSG_BODY")
	private String msgBody;

	@Column(name = "CREATED_BY")
	private Long createdBy;

	@Column(name = "CREATED_BY_NAME")
	private String createdByName;

	@Column(name = "EFFECTIVE_START_DATE")
	private Timestamp effectiveStartDate;

	@Column(name = "EFFECTIVE_END_DATE")
	private Timestamp effectiveEndDate;

	@Column(name = "FLAG_DELETED")
	private String flagDeleted;

	@Column(name = "FLAG_HISTORY_EXIST")
	private String flagHistoryExist;

	@Column(name = "LAST_UPDATED_DATE")
	private Timestamp lastUpdatedDate;

	@Column(name = "LAST_UPDATED_BY")
	private Long lastUpdatedBy;

	@Column(name = "LAST_UPDATED_BY_NAME")
	private String lastUpdatedByName;

	@Column(name = "LAST_UPDATED_SHORT_NAME")
	private String lastUpdatedShortName;

	@Column(name = "CREATED_BY_SHORT_NAME")
	private String createdByShortName;
}
