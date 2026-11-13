package gov.ny.state.doh.bhae.nyehms.web.spring.entity.common;

import java.sql.Timestamp;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * The quality-stage match summary of a document (table RAW_DOCUMENT_MATCH_SUMMARY): the document, its parent, its
 * type, the flagged match code and when it was reviewed. Read-only, read through projections by the clerical review
 * (ClericalReviewDAO). <b>MATCH_SUMM_ID as the key is assumed.</b>
 */
@Entity
@Immutable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "RAW_DOCUMENT_MATCH_SUMMARY")
public class RawDocumentMatchSummary {

	@Id
	@Column(name = "MATCH_SUMM_ID")
	private Long matchSummId;

	@Column(name = "DOCUMENT_ID")
	private Long documentId;

	@Column(name = "QS_PARENT_DOC_ID")
	private Long qsParentDocId;

	@Column(name = "DOCUMENT_TYPE_CODE")
	private String documentTypeCode;

	@Column(name = "FLAG_MATCH_CD")
	private String flagMatchCd;

	@Column(name = "REVIEWED_DATE")
	private Timestamp reviewedDate;
}
