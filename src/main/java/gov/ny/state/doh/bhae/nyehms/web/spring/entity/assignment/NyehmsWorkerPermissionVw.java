package gov.ny.state.doh.bhae.nyehms.web.spring.entity.assignment;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

/**
 * The workers' permissions (view NYEHMS_WORKER_PERMISSION_VW): the columns the reabstraction worker list joins on
 * and the login's permission checks read (CommonDAO).
 * <p>
 * Read-only and read through projections only, so a key that repeats does not matter. <b>WORKER_ID as the key is
 * assumed</b> (it is not one: a worker has several permissions).
 */
@Entity
@Immutable
@Getter
@Table(name = "NYEHMS_WORKER_PERMISSION_VW")
public class NyehmsWorkerPermissionVw {

	@Id
	@Column(name = "WORKER_ID")
	private Long workerId;

	@Column(name = "PERMISSION_ID")
	private Long permissionId;

	@Column(name = "PERMISSION")
	private String permission;

	@Column(name = "MODULE_NAME")
	private String moduleName;

	@Column(name = "WORKER_REGION_ID")
	private Long workerRegionId;
}
