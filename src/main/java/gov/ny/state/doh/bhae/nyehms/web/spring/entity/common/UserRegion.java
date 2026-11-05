package gov.ny.state.doh.bhae.nyehms.web.spring.entity.common;

import java.sql.Timestamp;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

/**
 * A worker's region and role (table USER_REGION), written by the PKG_ADMINISTRATION procedures.
 * <p>
 * Read-only here and read through queries only. <b>WORKER_REGION_ID as the key is assumed.</b>
 */
@Entity
@Immutable
@Getter
@Table(name = "USER_REGION")
public class UserRegion {

	@Id
	@Column(name = "WORKER_REGION_ID")
	private Long workerRegionId;

	@Column(name = "WORKER_ID")
	private Long workerId;

	@Column(name = "ROLE_CODE")
	private Long roleCode;

	@Column(name = "END_DATE")
	private Timestamp endDate;
}
