package gov.ny.state.doh.bhae.nyehms.web.spring.entity.sierra;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * A lab document the Sierra job could not parse, or that is to be parsed again (table RAS_SIERRA_PROCESS_ERRORS):
 * the columns the Sierra job and the RAS error screen read and write (SierraResponseDAO).
 * <p>
 * Inserted by HQL inserts, read with projections and changed by bulk JPQL updates only, never loaded as an entity: the
 * key is only for the mapping (<b>assumed</b>; a document may have several rows over the reprise cycles).
 * UPDATE_ID is written with the worker id as text (the database converts it, as before).
 */
@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "RAS_SIERRA_PROCESS_ERRORS")
@IdClass(RasSierraProcessErrorsId.class)
public class RasSierraProcessErrors {

	@Id
	@Column(name = "DOCUMENT_ID")
	private Long documentId;

	@Id
	@Column(name = "ACTIVE_FLAG")
	private String activeFlag;

	@Id
	@Column(name = "CREATE_DATE")
	private Timestamp createDate;

	@Column(name = "ERROR_DESC")
	private String errorDesc;

	@Column(name = "CREATE_ID")
	private String createId;

	@Column(name = "UPDATE_ID")
	private String updateId;

	@Column(name = "UPDATE_DATE")
	private Timestamp updateDate;

	@Column(name = "COMMENTS")
	private String comments;

	@Column(name = "REPRISE_FLAG")
	private String repriseFlag;
}
