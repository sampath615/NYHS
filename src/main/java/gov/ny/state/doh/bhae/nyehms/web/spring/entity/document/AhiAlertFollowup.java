package gov.ny.state.doh.bhae.nyehms.web.spring.entity.document;

import java.sql.Timestamp;

import org.hibernate.annotations.ColumnTransformer;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * A follow-up step taken on an AHI alert (table AHI_ALERT_FOLLOWUP), inserted from the AHI alert tracking form.
 * <p>
 * <b>The table has no known key</b>: AHI_ALERT_SEQ_ID is declared as the id, but it repeats (one row per step), so a
 * row is inserted and detached, and the rows are read through projections only. FOLLOW_UP_DATE is the database's
 * SYSDATE (left null on insert); FOLLOW_UP_UID is the worker id.
 */
@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "AHI_ALERT_FOLLOWUP")
public class AhiAlertFollowup {

	@Id
	@Column(name = "AHI_ALERT_SEQ_ID")
	private Long ahiAlertSeqId;

	@Column(name = "FOLLOW_UP_CODE")
	private String followUpCode;

	@Column(name = "USER_NOTES")
	private String userNotes;

	@Column(name = "FOLLOW_UP_UID")
	private Long followUpUid;

	@Column(name = "FOLLOW_UP_DATE")
	@ColumnTransformer(write = "coalesce(?, SYSDATE)")
	private Timestamp followUpDate;
}
