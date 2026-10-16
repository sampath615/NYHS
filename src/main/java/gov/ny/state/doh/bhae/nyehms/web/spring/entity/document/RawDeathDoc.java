package gov.ny.state.doh.bhae.nyehms.web.spring.entity.document;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

/**
 * A death certificate, as received (table RAW_DEATH_DOC); only the columns the application reads.
 * <p>
 * Read-only. <b>DOCUMENT_ID as the key is assumed</b> (one row per document).
 */
@Entity
@Immutable
@Getter
@Table(name = "RAW_DEATH_DOC")
public class RawDeathDoc {

	@Id
	@Column(name = "DOCUMENT_ID")
	private Integer documentId;

	@Column(name = "RES_STREET_TYPE")
	private String resStreetType;

	@Column(name = "RES_APT_NO")
	private String resAptNo;

	@Column(name = "RES_STREET_NO")
	private String resStreetNo;

	@Column(name = "RES_STREET")
	private String resStreet;

	@Column(name = "RES_STREET_DIRECT")
	private String resStreetDirect;
}
