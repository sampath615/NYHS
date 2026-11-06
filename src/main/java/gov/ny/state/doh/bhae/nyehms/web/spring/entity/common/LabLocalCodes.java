package gov.ny.state.doh.bhae.nyehms.web.spring.entity.common;

import java.sql.Timestamp;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

/**
 * A local test code of a lab (PFI) with its validity (table LAB_LOCAL_CODES); the columns the eCLRS loaders read (the
 * table is also used by LabReportsDAO, which may add its columns here).
 * <p>
 * Read-only, through JPQL projections only. <b>LAB_LOCAL_CD_MAP_ID as the key is assumed.</b> LAB_LOCAL_CD_MAP_ID and
 * PFI are text attributes: the former statement read the map id with getString and compared PFI with the lab report's
 * PFI text as it was.
 */
@Entity
@Immutable
@Getter
@Table(name = "LAB_LOCAL_CODES")
public class LabLocalCodes {

	@Id
	@Column(name = "LAB_LOCAL_CD_MAP_ID")
	private String labLocalCdMapId;

	@Column(name = "PFI")
	private String pfi;

	@Column(name = "LOCAL_CD")
	private String localCd;

	@Column(name = "START_DATE")
	private Timestamp startDate;

	@Column(name = "END_DATE")
	private Timestamp endDate;
}
