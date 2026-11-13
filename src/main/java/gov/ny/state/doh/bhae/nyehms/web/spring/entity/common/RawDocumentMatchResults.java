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
 * duplicate check reads (BirthDocumentDAO.isDuplicateDocumentHasAnyMatches) and the clerical review lists
 * (ClericalReviewDAO: the potential state numbers of a document).
 * <p>
 * Read-only, and only read through projections: a document has several match results, so <b>MATCH_RES_ID as the key
 * is assumed</b> (a repeating key would not matter, no entity is loaded). The rows are written elsewhere (the
 * quality-stage loaders).
 */
@Entity
@Immutable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "RAW_DOCUMENT_MATCH_RESULTS")
public class RawDocumentMatchResults {

	@Id
	@Column(name = "MATCH_RES_ID")
	private Long matchResId;

	@Column(name = "MATCH_SUMM_ID")
	private Long matchSummId;

	@Column(name = "DOCUMENT_ID")
	private Long documentId;

	@Column(name = "QS_PARENT_DOC_ID")
	private Long qsParentDocId;

	@Column(name = "MATCH_CD")
	private String matchCd;

	@Column(name = "POT_STATENO")
	private String potStateno;
}
