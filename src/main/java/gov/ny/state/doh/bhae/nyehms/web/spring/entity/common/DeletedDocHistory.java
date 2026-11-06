package gov.ny.state.doh.bhae.nyehms.web.spring.entity.common;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.Getter;

/**
 * The history of deleted, discarded and non-case documents (table DELETED_DOC_HISTORY): the columns the non-case
 * screens read and write (NonCaseDocumentDAO).
 * <p>
 * Inserted by HQL inserts, read with projections and changed by bulk JPQL updates only, never loaded as an entity: the
 * key is only for the mapping (<b>(TRIGGERING_DOC_ID, CREATE_DATE) as the key is assumed</b>; TRIGGERING_DOC_ID alone
 * repeats, a document can have a discard row and a non-case row). CREATE_ID and LAST_UPDATE_ID are worker ids,
 * REASON_CODE a code of CODED_NON_CASE_REASON (<b>assumed text</b>).
 */
@Entity
@Getter
@Table(name = "DELETED_DOC_HISTORY")
@IdClass(DeletedDocHistoryId.class)
public class DeletedDocHistory {

	@Id
	@Column(name = "TRIGGERING_DOC_ID")
	private Integer triggeringDocId;

	@Id
	@Column(name = "CREATE_DATE")
	private Timestamp createDate;

	@Column(name = "CREATE_ID")
	private Long createId;

	@Column(name = "FLAG_ACTIVE")
	private String flagActive;

	@Column(name = "REASON_CODE")
	private String reasonCode;

	@Column(name = "COMMENTS")
	private String comments;

	@Column(name = "LAST_UPDATE_DATE")
	private Timestamp lastUpdateDate;

	@Column(name = "LAST_UPDATE_ID")
	private Long lastUpdateId;

	@Column(name = "NYEHMS_STATENO")
	private String nyehmsStateno;

	@Column(name = "EHARS_STATENO")
	private String eharsStateno;

	@Column(name = "NYC_STATENO")
	private String nycStateno;

	@Column(name = "NYC_CITYNO")
	private String nycCityno;
}
