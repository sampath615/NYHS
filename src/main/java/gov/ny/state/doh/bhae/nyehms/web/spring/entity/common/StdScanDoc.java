package gov.ny.state.doh.bhae.nyehms.web.spring.entity.common;

import java.sql.Timestamp;

import org.hibernate.annotations.ColumnTransformer;
import org.springframework.data.domain.Persistable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * The scanned documents (table STD_SCAN_DOC): the columns the scanning report reads (when and by whom a document was
 * scanned) and the columns the scanning writes (ScanDocumentDAO).
 * <p>
 * <b>DOCUMENT_ID as the key is assumed.</b> Inserted (always a new row; CREATE_DATE the database's SYSDATE, left null;
 * DELETED_BY, DELETED_DATE and COMMENTS not written, as before) and changed by a bulk update when the document is
 * deleted.
 */
@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "STD_SCAN_DOC")
public class StdScanDoc implements Persistable<Long> {

	@Id
	@Column(name = "DOCUMENT_ID")
	private Long documentId;

	@Column(name = "FILE_NAME")
	private String fileName;

	@Column(name = "STATENO")
	private String stateno;

	/** SYSDATE when inserted (left null). */
	@ColumnTransformer(write = "coalesce(?, SYSDATE)")
	@Column(name = "CREATE_DATE")
	private Timestamp createDate;

	/** The WORKER_ID of the worker who scanned the document. */
	@Column(name = "CREATED_BY")
	private Long createdBy;

	@Column(name = "DELETED_BY", insertable = false)
	private Long deletedBy;

	@Column(name = "DELETED_DATE", insertable = false)
	private Timestamp deletedDate;

	@Column(name = "COMMENTS", insertable = false)
	private String comments;

	@Override
	public Long getId() {
		return documentId;
	}

	/** Always a new row (the application only inserts it). */
	@Override
	public boolean isNew() {
		return true;
	}
}
