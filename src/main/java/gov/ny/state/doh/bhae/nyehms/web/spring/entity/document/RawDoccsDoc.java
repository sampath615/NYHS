package gov.ny.state.doh.bhae.nyehms.web.spring.entity.document;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

/**
 * A DOCCS (Department of Corrections) report, as received (table RAW_DOCCS_DOC); only the columns the DOCCS import
 * changes (the process status, by a bulk update of DoccsDocumentDAO).
 * <p>
 * <b>DOCUMENT_ID as the key is assumed</b> (one row per document).
 */
@Entity
@Getter
@Table(name = "RAW_DOCCS_DOC")
public class RawDoccsDoc {

	@Id
	@Column(name = "DOCUMENT_ID")
	private BigDecimal documentId;

	@Column(name = "CURRENT_PROCESS_STATUS")
	private BigDecimal currentProcessStatus;
}
