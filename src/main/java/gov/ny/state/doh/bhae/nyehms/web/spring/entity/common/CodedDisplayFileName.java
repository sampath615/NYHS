package gov.ny.state.doh.bhae.nyehms.web.spring.entity.common;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

/**
 * The names shown for the files of a document type (table CODED_DISPLAY_FILE_NAME); the columns the display name
 * change (AssignmentDAO.updateDocumentFileDisplayName) writes.
 * <p>
 * Changed by a JPQL bulk update only (no entity is loaded or saved). <b>FILE_NAME as the key is assumed</b> (a file
 * name may well repeat over document types: the update selects by FILE_NAME and DOCUMENT_TYPE_CODE, as before).
 */
@Entity
@Getter
@Table(name = "CODED_DISPLAY_FILE_NAME")
public class CodedDisplayFileName {

	@Id
	@Column(name = "FILE_NAME")
	private String fileName;

	@Column(name = "DOCUMENT_TYPE_CODE")
	private String documentTypeCode;

	@Column(name = "DISPLAY_NAME")
	private String displayName;

	/** The worker id. */
	@Column(name = "LAST_UPDATED_BY")
	private Long lastUpdatedBy;

	@Column(name = "LAST_UPDATED_DATE")
	private Timestamp lastUpdatedDate;
}
