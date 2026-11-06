package gov.ny.state.doh.bhae.nyehms.web.spring.entity.common;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.Getter;

/**
 * The local codes of a lab (table LAB_LOCAL_CODES) and their validity; the columns LabReportsDAO reads and writes (the table is also used by SchedulerJobsDAO).
 * <p>
 * Inserted by a JPQL insert and changed by bulk JPQL updates. <b>LAB_LOCAL_CD_MAP_ID as the key, filled by the database on insert (the former insert did not set it), is assumed.</b>
 */
@Entity
@Getter
@Table(name = "LAB_LOCAL_CODES")
public class LabLocalCodes {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "LAB_LOCAL_CD_MAP_ID")
	private Long labLocalCdMapId;

	@Column(name = "PFI")
	private String pfi;

	@Column(name = "LOCAL_CD")
	private String localCd;

	@Column(name = "LAST_UPDATED_BY")
	private Long lastUpdatedBy;

	@Column(name = "LAST_UPDATED_DATE")
	private Timestamp lastUpdatedDate;

	@Column(name = "START_DATE")
	private Timestamp startDate;

	@Column(name = "END_DATE")
	private Timestamp endDate;
}
