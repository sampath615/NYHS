package gov.ny.state.doh.bhae.nyehms.web.spring.entity.common;

import java.io.Serializable;
import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * A comment on a worker's profile (table NYEHMS_USERS_ACTIVITY), inserted by SearchManageProfileDAO.
 * <p>
 * <b>WORKER_ID + ACTIVITY_DATE as the key is assumed</b> (the table has no id column); rows are inserted with persist
 * (no lookup) and read through projections only.
 */
@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@IdClass(NyehmsUsersActivity.Key.class)
@Table(name = "NYEHMS_USERS_ACTIVITY")
public class NyehmsUsersActivity {

	/** The assumed key. */
	@Getter
	@EqualsAndHashCode
	@NoArgsConstructor
	@AllArgsConstructor
	public static class Key implements Serializable {
		private static final long serialVersionUID = 1L;
		private Long workerId;
		private Timestamp activityDate;
	}

	@Id
	@Column(name = "WORKER_ID")
	private Long workerId;

	@Id
	@Column(name = "ACTIVITY_DATE")
	private Timestamp activityDate;

	@Column(name = "ACTIVITY_BY")
	private String activityBy;

	@Column(name = "COMMENTS")
	private String comments;
}
