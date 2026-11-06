package gov.ny.state.doh.bhae.nyehms.web.spring.entity.lab;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.Getter;

/**
 * The comments of a LAFT follow-up (table NYEHMS_LAFT_FOLLOWUP_COMMENTS); INITIAL_COMMENT is 'Y' for the initial
 * comments, null for the review comments.
 * <p>
 * Read through JPQL projections and written by an HQL insert only, never loaded or saved as an entity. <b>The key
 * (FOLLOWUP_ID_PK, COMMENT_DATE) is assumed</b> (the table has no known key; it does not matter for projections and
 * HQL inserts).
 */
@Entity
@Getter
@Table(name = "NYEHMS_LAFT_FOLLOWUP_COMMENTS")
@IdClass(NyehmsLaftFollowupCommentsId.class)
public class NyehmsLaftFollowupComments {

	@Id
	@Column(name = "FOLLOWUP_ID_PK")
	private Long followupIdPk;

	@Id
	@Column(name = "COMMENT_DATE")
	private Timestamp commentDate;

	@Column(name = "COMMENTS")
	private String comments;

	@Column(name = "LAFT_USER")
	private Long laftUser;

	@Column(name = "INITIAL_COMMENT")
	private String initialComment;
}
