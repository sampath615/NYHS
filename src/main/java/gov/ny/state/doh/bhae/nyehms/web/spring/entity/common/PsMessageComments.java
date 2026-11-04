package gov.ny.state.doh.bhae.nyehms.web.spring.entity.common;

import java.io.Serializable;
import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * A comment on a partner services (PS) message (table PS_MESSAGE_COMMENTS), inserted by PSMessageReviewDAO.
 * <p>
 * <b>DOCUMENT_ID + CREATE_ID + CREATE_DATE as the key is assumed</b> (the table has no known key). The comments are
 * only inserted (persisted, never merged) and read through projections, so a key that repeats does not matter.
 */
@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@IdClass(PsMessageComments.Key.class)
@Table(name = "PS_MESSAGE_COMMENTS")
public class PsMessageComments {

	@Id
	@Column(name = "DOCUMENT_ID")
	private Long documentId;

	@Column(name = "COMMENTS")
	private String comments;

	@Id
	@Column(name = "CREATE_ID")
	private Long createId;

	@Id
	@Column(name = "CREATE_DATE")
	private Timestamp createDate;

	/** The assumed key. */
	@EqualsAndHashCode
	@NoArgsConstructor
	@AllArgsConstructor
	public static class Key implements Serializable {

		private static final long serialVersionUID = 1L;

		private Long documentId;
		private Long createId;
		private Timestamp createDate;
	}
}
