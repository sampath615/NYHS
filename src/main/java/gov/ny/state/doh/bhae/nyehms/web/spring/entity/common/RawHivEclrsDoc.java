package gov.ny.state.doh.bhae.nyehms.web.spring.entity.common;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

/**
 * A raw eCLRS HIV lab document (table RAW_HIV_ECLRS_DOC); only the columns LoincResultDomainMappingDAO, LaftDetailsDAO and LabReportsDAO read so far,
 * the multi-step document details of SchedulerJobsDAO and the process status LabDocumentDAO sets (the table is also used by other mappers, which may
 * add their columns here).
 * <p>
 * Used in counts and JPQL projections, and changed by one bulk JPQL update (the process status and date of the documents
 * grouped into a multi-spot document); never loaded or saved as an entity. <b>DOCUMENT_ID as the key is assumed.</b>
 */
@Entity
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

	@Column(name = "PROCESS_DATE")
	private Timestamp processDate;
}
