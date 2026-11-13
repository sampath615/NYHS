package gov.ny.state.doh.bhae.nyehms.web.spring.entity.person;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

/**
 * A state number flagged as a bad merge and waiting for review (view NYEHMS_NEW_FLAGGED_BADMERGE_VW). The date column
 * is read as text, as the former mapper read it into the text property of the VO. Read-only, read in a JPQL
 * projection by {@code MergeUnMergePersonDAO.getBadlyMergedStatenosList}. <b>UNMERGE_ID as the key is assumed</b>.
 */
@Entity
@Immutable
@Getter
@Table(name = "NYEHMS_NEW_FLAGGED_BADMERGE_VW")
public class NyehmsNewFlaggedBadmergeVw {

	@Id
	@Column(name = "UNMERGE_ID")
	private Long unmergeId;

	@Column(name = "STATENO")
	private String stateno;

	@Column(name = "FLAGGED_BY")
	private String flaggedBy;

	@Column(name = "FLAGGED_DATE")
	private String flaggedDate;

	@Column(name = "STATUS_CD")
	private String statusCd;

	@Column(name = "REQUEST_COMMENTS")
	private String requestComments;

	@Column(name = "FLAGGED_EARLIER")
	private String flaggedEarlier;
}
