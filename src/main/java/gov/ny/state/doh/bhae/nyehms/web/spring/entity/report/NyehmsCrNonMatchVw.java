package gov.ny.state.doh.bhae.nyehms.web.spring.entity.report;

import java.sql.Timestamp;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

/**
 * The documents a clerical review marked as non-matches (view NYEHMS_CR_NON_MATCH_VW).
 * <p>
 * Read-only and read through projections only (a key that repeats does not matter then). <b>STATENO as the key is
 * assumed.</b>
 */
@Entity
@Immutable
@Getter
@Table(name = "NYEHMS_CR_NON_MATCH_VW")
public class NyehmsCrNonMatchVw {

	@Id
	@Column(name = "STATENO")
	private String stateno;

	@Column(name = "DOCUMENT_TYPE_CODE")
	private String documentTypeCode;

	@Column(name = "REVIEWED_BY")
	private String reviewedBy;

	@Column(name = "REVIEW_DATE")
	private Timestamp reviewDate;
}
