package gov.ny.state.doh.bhae.nyehms.web.spring.entity.assignment;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

/**
 * The document sources of the case report forms (table CODED_DOCUMENT_SOURCE); the codes are text.
 * <p>
 * Read-only, used in JPQL projections only. <b>DOCUMENT_SOURCE_CODE as the key is assumed.</b>
 */
@Entity
@Immutable
@Getter
@Table(name = "CODED_DOCUMENT_SOURCE")
public class CodedDocumentSource {

	@Id
	@Column(name = "DOCUMENT_SOURCE_CODE")
	private String documentSourceCode;

	@Column(name = "DOCUMENT_SOURCE_DESCRIPTION")
	private String documentSourceDescription;
}
