package gov.ny.state.doh.bhae.nyehms.web.spring.entity.document;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

/**
 * An NYC IJR record as received (table RAW_NYCIJR_DOC); the columns the NYC IJR document screen reads.
 * <p>
 * Read-only. <b>DOCUMENT_ID as the key is assumed</b> (one row per document).
 */
@Entity
@Immutable
@Getter
@Table(name = "RAW_NYCIJR_DOC")
public class RawNycijrDoc {

	@Id
	@Column(name = "DOCUMENT_ID")
	private Integer documentId;

	@Column(name = "ADDRESS")
	private String address;

	@Column(name = "CUR_STREET_ADDRESS1")
	private String curStreetAddress1;

	@Column(name = "PROV_ADDR")
	private String provAddr;
}
