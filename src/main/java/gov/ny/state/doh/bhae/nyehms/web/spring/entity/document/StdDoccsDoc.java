package gov.ny.state.doh.bhae.nyehms.web.spring.entity.document;

import java.sql.Date;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

/**
 * A DOCCS (Department of Corrections) report, standardized (table STD_DOCCS_DOC); the columns the DOCCS document screen reads.
 * <p>
 * Read-only. <b>DOCUMENT_ID as the key is assumed</b> (one row per document).
 */
@Entity
@Immutable
@Getter
@Table(name = "STD_DOCCS_DOC")
public class StdDoccsDoc {

	@Id
	@Column(name = "DOCUMENT_ID")
	private Integer documentId;

	@Column(name = "NYSID")
	private String nysid;

	@Column(name = "FILE_RECEIVED_DATE")
	private Date fileReceivedDate;

	@Column(name = "MATCHED_STATENO")
	private String matchedStateno;
}
