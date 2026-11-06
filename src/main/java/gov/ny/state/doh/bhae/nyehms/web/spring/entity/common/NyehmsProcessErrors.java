package gov.ny.state.doh.bhae.nyehms.web.spring.entity.common;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

/**
 * The validation errors of the documents (table NYEHMS_PROCESS_ERRORS): one row per error record of a document, with
 * its process status, active flag and the LAFT notes; only the columns the LAFT statements read and write (the table
 * is also written by the scheduler and PL/SQL, and used by other mappers, which may add their columns here).
 * <p>
 * Read through JPQL projections and changed by bulk JPQL updates only, never loaded and saved as an entity.
 * <b>ERROR_ID as the key is assumed.</b>
 */
@Entity
@Getter
@Table(name = "NYEHMS_PROCESS_ERRORS")
public class NyehmsProcessErrors {

	@Id
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

	@Column(name = "NOTES")
	private String notes;

	@Column(name = "UPDATEID")
	private Long updateid;

	@Column(name = "UPDATE_DATE")
	private Timestamp updateDate;
}
