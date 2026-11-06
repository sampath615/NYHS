package gov.ny.state.doh.bhae.nyehms.web.spring.entity.common;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

/**
 * The surveillance assignments (table SURVEILLANCE_ASSIGNMENT); the columns the AHI assignment report reads and the
 * case report form ids AcrfDocLabsDAO sets.
 * <p>
 * Read through JPQL projections and changed by JPQL bulk updates only (no entity is loaded or saved).
 * <b>DOCUMENT_ID as the key is assumed.</b> AHI_UPDATE_UID and LAST_UPDATED_BY are text (their column types are not
 * known; the worker id written to LAST_UPDATED_BY is bound as the number it is, as before).
 */
@Entity
@Getter
@Table(name = "SURVEILLANCE_ASSIGNMENT")
public class SurveillanceAssignment {

	@Id
	@Column(name = "DOCUMENT_ID")
	private Long documentId;

	@Column(name = "STATENO")
	private String stateno;

	/** The DOCUMENT_ID (DOCUMENT_LOCATOR) of the document that triggered the assignment. */
	@Column(name = "TRIGGERING_DOC_ID")
	private Integer triggeringDocId;

	@Column(name = "CURRENT_REGION_CD")
	private String currentRegionCd;

	@Column(name = "CURRENT_ASSIGNMENT_STATUS")
	private String currentAssignmentStatus;

	@Column(name = "CURRENT_ACTIVITY_TYPE_CODE")
	private String currentActivityTypeCode;

	@Column(name = "AHI_FLAG")
	private String ahiFlag;

	@Column(name = "CREATE_DATE")
	private Timestamp createDate;

	@Column(name = "AHI_UPDATE_DATE")
	private Timestamp ahiUpdateDate;

	@Column(name = "AHI_UPDATE_UID")
	private String ahiUpdateUid;

	/** The DOCUMENT_ID of the assignment's adult case report form. */
	@Column(name = "ACRF_DOCUMENT_ID")
	private Long acrfDocumentId;

	/** The DOCUMENT_ID of the assignment's pediatric case report form. */
	@Column(name = "PCRF_DOCUMENT_ID")
	private Long pcrfDocumentId;

	/** The DOCUMENT_ID of the assignment's reabstracted case report form. */
	@Column(name = "REAB_CRF_DOCUMENT_ID")
	private Long reabCrfDocumentId;

	@Column(name = "LAST_UPDATED_BY")
	private String lastUpdatedBy;

	@Column(name = "LAST_UPDATED_DATE")
	private Timestamp lastUpdatedDate;
}
