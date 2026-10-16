package gov.ny.state.doh.bhae.nyehms.web.spring.entity.document;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

/**
 * An ICCR (Infection Control) report, as received (table RAW_ICCR_DOC); only the columns the application reads.
 * <p>
 * Read-only. <b>DOCUMENT_ID as the key is assumed</b> (one row per document).
 */
@Entity
@Immutable
@Getter
@Table(name = "RAW_ICCR_DOC")
public class RawIccrDoc {

	@Id
	@Column(name = "DOCUMENT_ID")
	private Integer documentId;

	@Column(name = "PERSON_ADDRESS")
	private String personAddress;

	@Column(name = "PROV_ADDRESS")
	private String provAddress;
}
