package gov.ny.state.doh.bhae.nyehms.web.spring.entity.person;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

/**
 * A pair of state numbers flagged as the same person (table NYEHMS_DUPLICATE_STATENOS); only the columns the follow-up
 * update of {@code MergeUnMergePersonDAO} changes (the merge reports read the table natively). Changed by one bulk
 * JPQL update only; never loaded or saved as an entity. <b>DUPLICATION_ID as the key is assumed</b>.
 */
@Entity
@Getter
@Table(name = "NYEHMS_DUPLICATE_STATENOS")
public class NyehmsDuplicateStatenos {

	@Id
	@Column(name = "DUPLICATION_ID")
	private Long duplicationId;

	@Column(name = "FOLLOWUP")
	private String followup;

	@Column(name = "STATENO_1")
	private String stateno1;

	@Column(name = "STATENO_2")
	private String stateno2;

	@Column(name = "STATUS_CD")
	private String statusCd;

	@Column(name = "REVIEW_COMMENTS")
	private String reviewComments;

	@Column(name = "LAST_UPDATED_BY")
	private Long lastUpdatedBy;

	@Column(name = "LAST_UPDATED_DATE")
	private Timestamp lastUpdatedDate;
}
