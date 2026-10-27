package gov.ny.state.doh.bhae.nyehms.web.spring.entity.document;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

/**
 * A quality of care record as received (table RAW_QUALITY_CARE_DOC); the columns the quality of care document
 * screen reads.
 * <p>
 * Read-only. <b>DOCUMENT_ID as the key is assumed</b> (one row per document).
 */
@Entity
@Immutable
@Getter
@Table(name = "RAW_QUALITY_CARE_DOC")
public class RawQualityCareDoc {

	@Id
	@Column(name = "DOCUMENT_ID")
	private Integer documentId;

	@Column(name = "STREET")
	private String street;
}
