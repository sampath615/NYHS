package gov.ny.state.doh.bhae.nyehms.web.spring.entity.administration;

import java.sql.Timestamp;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

/**
 * An inactive (ended or deleted) system message (view NYEHMS_INACTIVE_SYSTEM_MSG_VW); read-only. The columns the
 * system message screens read. <b>MSG_ID as the key is assumed</b> (one row per message).
 */
@Entity
@Immutable
@Getter
@Table(name = "NYEHMS_INACTIVE_SYSTEM_MSG_VW")
public class InactiveSystemMessageView {

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

	@Column(name = "FLAG_HISTORY_EXIST")
	private String flagHistoryExist;

	@Column(name = "LAST_UPDATED_DATE")
	private Timestamp lastUpdatedDate;

	@Column(name = "LAST_UPDATED_BY_NAME")
	private String lastUpdatedByName;

	@Column(name = "LAST_UPDATED_SHORT_NAME")
	private String lastUpdatedShortName;

	@Column(name = "CREATED_BY_SHORT_NAME")
	private String createdByShortName;
}
