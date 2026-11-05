package gov.ny.state.doh.bhae.nyehms.web.spring.entity.common;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * A quality-stage match result of a document (table RAW_DOCUMENT_MATCH_RESULTS): the columns the birth import's
 * duplicate check reads (BirthDocumentDAO.isDuplicateDocumentHasAnyMatches).
 * <p>
 * Read-only, and only read through count queries: the table has no known key and a document has several match
 * results, so <b>DOCUMENT_ID as the key is assumed</b> only to satisfy JPA (a repeating key does not matter, no entity
 * is loaded). The rows are written elsewhere (the quality-stage loaders).
 */
@Entity
@Immutable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "RAW_DOCUMENT_MATCH_RESULTS")
public class RawDocumentMatchResults {

	@Id
	@Column(name = "DOCUMENT_ID")
	private Long documentId;

	@Column(name = "QS_PARENT_DOC_ID")
	private Long qsParentDocId;

	@Column(name = "MATCH_CD")
	private String matchCd;
}
