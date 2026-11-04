package gov.ny.state.doh.bhae.nyehms.web.spring.entity.administration;

import java.sql.Timestamp;

import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * A system message (table NYEHMS_SYSTEM_MESSAGES), inserted and changed by SystemMessageDAO.
 * <p>
 * <b>Assumed:</b> MSG_ID is filled by the database on insert (identity column or trigger; the former insert did not
 * set it). FLAG_DELETED is not written on insert, so its default applies. A change writes only the changed columns.
 */
@Entity
@DynamicUpdate
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "NYEHMS_SYSTEM_MESSAGES")
public class NyehmsSystemMessage {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "MSG_ID")
	private Long msgId;

	@Column(name = "EFFECTIVE_START_DATE")
	private Timestamp effectiveStartDate;

	@Column(name = "EFFECTIVE_END_DATE")
	private Timestamp effectiveEndDate;

	@Column(name = "MSG_BODY")
	private String msgBody;

	@Column(name = "CREATED_BY")
	private Long createdBy;

	@Column(name = "LAST_UPDATED_BY")
	private Long lastUpdatedBy;

	@Column(name = "LAST_UPDATED_DATE")
	private Timestamp lastUpdatedDate;

	@Column(name = "FLAG_DELETED", insertable = false)
	private String flagDeleted;

	/** Changes the message, as the system message screen saves it. */
	public void change(Timestamp effectiveStartDate, Timestamp effectiveEndDate, String msgBody, String flagDeleted, Long lastUpdatedBy,
			Timestamp lastUpdatedDate) {
		this.effectiveStartDate = effectiveStartDate;
		this.effectiveEndDate = effectiveEndDate;
		this.msgBody = msgBody;
		this.flagDeleted = flagDeleted;
		this.lastUpdatedBy = lastUpdatedBy;
		this.lastUpdatedDate = lastUpdatedDate;
	}
}
