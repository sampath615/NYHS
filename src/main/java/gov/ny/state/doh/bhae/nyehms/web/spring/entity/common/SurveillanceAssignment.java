package gov.ny.state.doh.bhae.nyehms.web.spring.entity.common;

import java.sql.Timestamp;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

/**
 * The surveillance assignments (table SURVEILLANCE_ASSIGNMENT); the columns the AHI assignment report reads.
 * <p>
 * Read-only and read through JPQL projections only. <b>DOCUMENT_ID as the key is assumed.</b> AHI_UPDATE_UID is read as
 * a text (its column type is not known; the report only prints it).
 */
@Entity
@Immutable
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
}
