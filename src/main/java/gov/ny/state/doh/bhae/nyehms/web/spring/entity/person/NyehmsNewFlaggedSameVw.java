package gov.ny.state.doh.bhae.nyehms.web.spring.entity.person;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

/**
 * A pair of state numbers flagged as the same person and waiting for review (view NYEHMS_NEW_FLAGGED_SAME_VW). The
 * date columns are read as text, as the former mapper read them into the text properties of the VO. Read-only, read
 * in a JPQL projection by {@code MergeUnMergePersonDAO.getFlaggedStatenosList}. <b>DUPLICATION_ID as the key is
 * assumed</b>.
 */
@Entity
@Immutable
@Getter
@Table(name = "NYEHMS_NEW_FLAGGED_SAME_VW")
public class NyehmsNewFlaggedSameVw {

	@Id
	@Column(name = "DUPLICATION_ID")
	private Long duplicationId;

	@Column(name = "FLAGGED_BY")
	private String flaggedBy;

	@Column(name = "FLAGGED_DATE")
	private String flaggedDate;

	@Column(name = "REQUEST_COMMENTS")
	private String requestComments;

	@Column(name = "STATENO_1")
	private String stateno1;

	@Column(name = "STATENO_2")
	private String stateno2;

	@Column(name = "FLAGGED_EARLIER")
	private String flaggedEarlier;

	@Column(name = "STATENO_1_BAD_FLAG")
	private String stateno1BadFlag;

	@Column(name = "STATENO_2_BAD_FLAG")
	private String stateno2BadFlag;

	@Column(name = "ORIGIN")
	private String origin;

	@Column(name = "FOLLOWUP")
	private String followup;
}
