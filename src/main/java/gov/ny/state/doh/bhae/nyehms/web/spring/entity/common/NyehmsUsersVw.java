package gov.ny.state.doh.bhae.nyehms.web.spring.entity.common;

import java.sql.Timestamp;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

/**
 * The workers' view (NYEHMS_USERS_VW): short name, lock flag and end date.
 * <p>
 * Read-only and read through projections only (a key that repeats does not matter then; a worker can have several
 * rows). <b>WORKER_ID as the key is assumed.</b>
 */
@Entity
@Immutable
@Getter
@Table(name = "NYEHMS_USERS_VW")
public class NyehmsUsersVw {

	@Id
	@Column(name = "WORKER_ID")
	private Long workerId;

	@Column(name = "LEGACY_UID")
	private String legacyUid;

	@Column(name = "IS_LOCKED")
	private String isLocked;

	@Column(name = "END_DATE")
	private Timestamp endDate;

	/** The worker's full name, as the view builds it (AHIDocumentDAO.getAhiAlertDetails). */
	@Column(name = "FULL_NAME")
	private String fullName;
}
