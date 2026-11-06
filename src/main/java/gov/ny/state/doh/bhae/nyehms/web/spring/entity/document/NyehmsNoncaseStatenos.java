package gov.ny.state.doh.bhae.nyehms.web.spring.entity.document;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.Getter;

/**
 * The persons (STATENOs) flagged as non-cases, and unflagged again (table NYEHMS_NONCASE_STATENOS), as the non-case
 * screens write and read them (NonCaseDocumentDAO).
 * <p>
 * Inserted by HQL inserts, read with projections and changed by bulk JPQL updates only, never loaded as an entity: the
 * key is only for the mapping (<b>(FLAGGED_STATENO, FLAGGED_DATE) as the key is assumed</b>; a person can be flagged
 * and unflagged several times). FLAGGED_BY and LAST_UPDATED_BY are worker ids, REASON_CODE a code of
 * CODED_NON_CASE_REASON (assumed text).
 */
@Entity
@Getter
@Table(name = "NYEHMS_NONCASE_STATENOS")
@IdClass(NyehmsNoncaseStatenosId.class)
public class NyehmsNoncaseStatenos {

	@Id
	@Column(name = "FLAGGED_STATENO")
	private String flaggedStateno;

	@Id
	@Column(name = "FLAGGED_DATE")
	private Timestamp flaggedDate;

	@Column(name = "CURRENT_STATENO")
	private String currentStateno;

	@Column(name = "REASON_CODE")
	private String reasonCode;

	@Column(name = "FLAGGED_BY")
	private Long flaggedBy;

	@Column(name = "NON_CASE_FLAG")
	private String nonCaseFlag;

	@Column(name = "REQUEST_COMMENTS")
	private String requestComments;

	@Column(name = "REVIEW_COMMENTS")
	private String reviewComments;

	@Column(name = "EHARS_STATENO")
	private String eharsStateno;

	@Column(name = "NYC_STATENO")
	private String nycStateno;

	@Column(name = "NYC_CITYNO")
	private String nycCityno;

	@Column(name = "LAST_UPDATED_BY")
	private Long lastUpdatedBy;

	@Column(name = "LAST_UPDATED_DATE")
	private Timestamp lastUpdatedDate;
}
