package gov.ny.state.doh.bhae.nyehms.web.spring.entity.document;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

/**
 * The documents awaiting clerical review (view NYEHMS_DOCUMENT_CLERIC_RVW_VW): only the document type the review
 * screen lists. Read-only, read through a distinct projection, so a key that repeats does not matter.
 * <b>DOCUMENT_TYPE_CODE as the key is assumed.</b>
 */
@Entity
@Immutable
@Getter
@Table(name = "NYEHMS_DOCUMENT_CLERIC_RVW_VW")
public class NyehmsDocumentClericRvwVw {

	@Id
	@Column(name = "DOCUMENT_TYPE_CODE")
	private String documentTypeCode;
}
