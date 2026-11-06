package gov.ny.state.doh.bhae.nyehms.web.spring.entity.common;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

/**
 * A raw eCLRS STD lab document (table RAW_STD_ECLRS_DOC); only the columns the JPQL statements of SchedulerJobsDAO
 * change (the table is wide and also used by other mappers, which may add their columns here; its inserts and
 * {@code select *} reads stay native).
 * <p>
 * Changed by bulk JPQL updates only. <b>DOCUMENT_ID as the key is assumed.</b>
 */
@Entity
@Getter
@Table(name = "RAW_STD_ECLRS_DOC")
public class RawStdEclrsDoc {

	@Id
	@Column(name = "DOCUMENT_ID")
	private Long documentId;

	@Column(name = "CURRENT_PROCESS_STATUS")
	private Long currentProcessStatus;

	@Column(name = "PROCESS_DATE")
	private Timestamp processDate;
}
