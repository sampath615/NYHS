package gov.ny.state.doh.bhae.nyehms.web.spring.entity.administration;

import java.sql.Timestamp;

import org.hibernate.annotations.ColumnTransformer;

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
 * A system message (table NYEHMS_SYSTEM_MESSAGES): inserted by SystemMessageDAO.saveSystemMessageDetails, changed by
 * JPQL updates.
 * <p>
 * <b>Assumed:</b> MSG_ID is filled by the database on insert (identity column or trigger; the former insert did not
 * set it), so Hibernate reads it back after the insert. FLAG_DELETED is not written on insert (its default applies,
 * as before). LAST_UPDATED_DATE is the database clock (SYSDATE) when not given, as before.
 */
@Entity
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

	@ColumnTransformer(write = "coalesce(?, SYSDATE)")
	@Column(name = "LAST_UPDATED_DATE")
	private Timestamp lastUpdatedDate;

	@Column(name = "FLAG_DELETED", insertable = false)
	private String flagDeleted;
}
