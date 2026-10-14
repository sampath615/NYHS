package gov.ny.state.doh.bhae.nyehms.web.spring.entity.document;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

/**
 * An NYC Partner Referral Form as received (table RAW_NYCPRF_DOC); only the columns the
 * application reads. Read-only; <b>DOCUMENT_ID as the key is assumed</b>.
 */
@Entity
@Immutable
@Getter
@Table(name = "RAW_NYCPRF_DOC")
public class RawNycprfDoc {

	@Id
	@Column(name = "DOCUMENT_ID")
	private Integer documentId;

	@Column(name = "ADDRESS")
	private String address;
}
