package gov.ny.state.doh.bhae.nyehms.web.spring.entity.document;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

/**
 * The reasons for deleting / discarding a document or flagging a person as a non-case (code table
 * CODED_NON_CASE_REASON), as the non-case screens read them (NonCaseDocumentDAO).
 * <p>
 * Read-only and read with projections only. <b>REASON_CODE as the key is assumed</b> (and assumed text).
 */
@Entity
@Immutable
@Getter
@Table(name = "CODED_NON_CASE_REASON")
public class CodedNonCaseReason {

	@Id
	@Column(name = "REASON_CODE")
	private String reasonCode;

	@Column(name = "REASON_DESC")
	private String reasonDesc;

	/** 'Y' for a delete reason, otherwise a discard reason. */
	@Column(name = "DELETE_FLAG")
	private String deleteFlag;

	/** 'NON_CASE' for the non-case reasons. */
	@Column(name = "DOC_FLAG")
	private String docFlag;
}
