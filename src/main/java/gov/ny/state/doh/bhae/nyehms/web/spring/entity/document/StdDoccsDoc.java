package gov.ny.state.doh.bhae.nyehms.web.spring.entity.document;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

import org.hibernate.annotations.Immutable;
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
 * A DOCCS (Department of Corrections) report, standardized (table STD_DOCCS_DOC): the columns the DOCCS document screen
 * reads and the DOCCS import writes (DoccsDocumentDAO.insertRawDoccsDataToStdTable).
 * <p>
 * Inserted, never changed. <b>DOCUMENT_ID as the key is assumed</b> (one row per document). FILE_RECEIVED_DATE is read
 * as a date only and written with its time (as the former insert bound it); the read attribute is not written.
 */
@Entity
@Immutable
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "STD_DOCCS_DOC")
public class StdDoccsDoc implements Persistable<Integer> {

	@Id
	@Column(name = "DOCUMENT_ID")
	private Integer documentId;

	@Column(name = "NYSID")
	private String nysid;

	@Column(name = "FILE_RECEIVED_DATE", insertable = false, updatable = false)
	private Date fileReceivedDate;

	/** FILE_RECEIVED_DATE as the import writes it. */
	@Column(name = "FILE_RECEIVED_DATE", updatable = false)
	private Timestamp fileReceivedDateWritten;

	@Column(name = "MATCHED_STATENO")
	private String matchedStateno;

	@Column(name = "DOCCS_FILE")
	private String doccsFile;

	@Column(name = "CURRENT_PROCESS_STATUS")
	private BigDecimal currentProcessStatus;

	@Override
	public Integer getId() {
		return documentId;
	}

	/** Always a new row (the application only inserts it). */
	@Override
	public boolean isNew() {
		return true;
	}
}
