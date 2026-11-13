package gov.ny.state.doh.bhae.nyehms.web.spring.entity.document;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * The risk factors of a document (table DOCUMENT_RISKS): only the document id, which the document deletion deletes by
 * (CommonDocLabsDAO, a bulk JPQL delete). Never loaded or saved as an entity. <b>DOCUMENT_ID as the key is assumed.</b>
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "DOCUMENT_RISKS")
public class DocumentRisks {

	@Id
	@Column(name = "DOCUMENT_ID")
	private Long documentId;
}
