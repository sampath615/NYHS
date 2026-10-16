package gov.ny.state.doh.bhae.nyehms.web.spring.entity.document;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

/**
 * An AIRS (AIDS Institute Reporting System) report, as received (table RAW_AIRS_DOC); only the columns the application reads.
 * <p>
 * Read-only. <b>DOCUMENT_ID as the key is assumed</b> (one row per document).
 */
@Entity
@Immutable
@Getter
@Table(name = "RAW_AIRS_DOC")
public class RawAirsDoc {

	@Id
	@Column(name = "DOCUMENT_ID")
	private Integer documentId;

	@Column(name = "ADDRESS")
	private String address;

	@Column(name = "PROV_ADDR")
	private String provAddr;
}
