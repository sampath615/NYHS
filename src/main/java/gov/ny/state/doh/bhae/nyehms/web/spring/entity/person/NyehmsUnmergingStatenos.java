package gov.ny.state.doh.bhae.nyehms.web.spring.entity.person;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * A state number flagged as badly merged (table NYEHMS_UNMERGING_STATENOS): the columns the review of an inactive state
 * number changes (CommonDocLabsDAO, a bulk JPQL update). Never loaded or saved as an entity. <b>UNMERGE_ID as the key is
 * assumed.</b>
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "NYEHMS_UNMERGING_STATENOS")
public class NyehmsUnmergingStatenos {

	@Id
	@Column(name = "UNMERGE_ID")
	private Long unmergeId;

	@Column(name = "STATENO")
	private String stateno;

	@Column(name = "STATUS_CD")
	private String statusCd;

	@Column(name = "REVIEW_COMMENTS")
	private String reviewComments;

	@Column(name = "LAST_UPDATED_BY")
	private Long lastUpdatedBy;

	@Column(name = "LAST_UPDATED_DATE")
	private Timestamp lastUpdatedDate;
}
