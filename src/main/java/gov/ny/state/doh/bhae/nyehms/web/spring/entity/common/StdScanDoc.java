package gov.ny.state.doh.bhae.nyehms.web.spring.entity.common;

import java.sql.Timestamp;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

/**
 * The scanned documents (table STD_SCAN_DOC); the columns the scanning report reads: when and by whom a document was
 * scanned.
 * <p>
 * Read-only and read through JPQL projections only. <b>DOCUMENT_ID as the key is assumed.</b>
 */
@Entity
@Immutable
@Getter
@Table(name = "STD_SCAN_DOC")
public class StdScanDoc {

	@Id
	@Column(name = "DOCUMENT_ID")
	private Long documentId;

	@Column(name = "CREATE_DATE")
	private Timestamp createDate;

	/** The WORKER_ID of the worker who scanned the document. */
	@Column(name = "CREATED_BY")
	private Long createdBy;
}
