package gov.ny.state.doh.bhae.nyehms.web.spring.entity.common;

import java.sql.Timestamp;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

/**
 * A raw eCLRS HIV lab document (table RAW_HIV_ECLRS_DOC); only the columns LoincResultDomainMappingDAO, LaftDetailsDAO and LabReportsDAO read so far,
 * and the multi-step document details of SchedulerJobsDAO (the table is also used by other mappers, which may add their columns here).
 * <p>
 * Read-only, used in counts and JPQL projections only. <b>DOCUMENT_ID as the key is assumed.</b>
 */
@Entity
@Immutable
@Getter
@Table(name = "RAW_HIV_ECLRS_DOC")
public class RawHivEclrsDoc {

	@Id
	@Column(name = "DOCUMENT_ID")
	private Long documentId;

	@Column(name = "MSHLABPFI")
	private String mshlabpfi;

	@Column(name = "OBXLOINCCODE")
	private String obxloinccode;

	@Column(name = "MSHSENDINGFACILITYCLIA")
	private String mshsendingfacilityclia;

	@Column(name = "OBXOBSERVATIONRESULTKEY")
	private Long obxobservationresultkey;

	@Column(name = "CURRENT_PROCESS_STATUS")
	private Long currentProcessStatus;

	@Column(name = "PIDADDRESSLINE1")
	private String pidaddressline1;

	@Column(name = "PIDADDRESSLINE2")
	private String pidaddressline2;

	@Column(name = "PIDCITY")
	private String pidcity;

	@Column(name = "PIDSTATE")
	private String pidstate;

	@Column(name = "PIDZIPCODE")
	private String pidzipcode;

	@Column(name = "MSHCREATEDATE")
	private Timestamp mshcreatedate;
}
