package gov.ny.state.doh.bhae.nyehms.web.spring.entity.common;

import java.sql.Timestamp;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

/**
 * The workers' regions and roles view (NYEHMS_WORKER_REGIONS_VW): only the columns read so far.
 * <p>
 * Read-only and read through projections only. <b>WORKERREGIONID as the key is assumed.</b>
 */
@Entity
@Immutable
@Getter
@Table(name = "NYEHMS_WORKER_REGIONS_VW")
public class NyehmsWorkerRegionsVw {

	@Id
	@Column(name = "WORKERREGIONID")
	private Long workerRegionId;

	@Column(name = "WORKERID")
	private Long workerId;

	@Column(name = "ROLE_DESC")
	private String roleDesc;

	@Column(name = "REGION_DESCRIPTION")
	private String regionDescription;

	@Column(name = "WR_START_DATE")
	private Timestamp wrStartDate;

	@Column(name = "WR_END_DATE")
	private Timestamp wrEndDate;

	// the columns the assignment screens read (AssignmentDAO)

	@Column(name = "ROLE_CODE")
	private Long roleCode;

	@Column(name = "REGION_CODE")
	private String regionCode;

	@Column(name = "FULL_NAME")
	private String fullName;

	/** The end date of the worker (the region role's end date is WR_END_DATE). */
	@Column(name = "END_DATE")
	private Timestamp endDate;
}
