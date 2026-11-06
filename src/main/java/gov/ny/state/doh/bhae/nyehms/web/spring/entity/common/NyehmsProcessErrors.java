package gov.ny.state.doh.bhae.nyehms.web.spring.entity.common;

import java.math.BigDecimal;
import java.sql.Timestamp;

import org.hibernate.annotations.ColumnTransformer;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * The validation errors of the documents (table NYEHMS_PROCESS_ERRORS): one row per error record of a document, with
 * its process status, active flag and the LAFT notes; the columns the LAFT statements and the scheduler
 * (SchedulerJobsDAO) read and write (the table is also written by PL/SQL, and used by other mappers, which may add
 * their columns here).
 * <p>
 * Read through JPQL projections and changed by bulk JPQL updates; the scheduler inserts a new error record as an entity
 * (SchedulerJobsDAO.addValidationError): ERROR_ID from NYEHMS_PROCESS_ERRORS_SEQ and CREATE_DATE the database's SYSDATE,
 * as the former insert had them; NOTES, UPDATEID, UPDATE_DATE and COMMENTS are not written on insert (the former insert
 * did not list them). <b>ERROR_ID as the key is assumed.</b>
 */
@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "NYEHMS_PROCESS_ERRORS")
public class NyehmsProcessErrors {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "nyehmsProcessErrorsSeq")
	@SequenceGenerator(name = "nyehmsProcessErrorsSeq", sequenceName = "NYEHMS_PROCESS_ERRORS_SEQ", allocationSize = 1)
	@Column(name = "ERROR_ID")
	private Long errorId;

	@Column(name = "DOCUMENT_ID")
	private Long documentId;

	@Column(name = "DOCUMENT_TYPE")
	private String documentType;

	@Column(name = "ACTIVE_FLAG")
	private String activeFlag;

	@Column(name = "PROCESS_STATUS")
	private Long processStatus;

	@Column(name = "ERROR_DESC")
	private String errorDesc;

	@Column(name = "CREATEID")
	private String createid;

	/** Left null on insert: the database's SYSDATE, as the former insert wrote. */
	@ColumnTransformer(write = "coalesce(?, SYSDATE)")
	@Column(name = "CREATE_DATE")
	private Timestamp createDate;

	@Column(name = "REPRISE_FLAG")
	private String repriseFlag;

	@Column(name = "PREVIOUS_STATUS")
	private BigDecimal previousStatus;

	@Column(name = "NOTES", insertable = false)
	private String notes;

	@Column(name = "UPDATEID", insertable = false)
	private Long updateid;

	@Column(name = "UPDATE_DATE", insertable = false)
	private Timestamp updateDate;

	@Column(name = "COMMENTS", insertable = false)
	private String comments;
}
