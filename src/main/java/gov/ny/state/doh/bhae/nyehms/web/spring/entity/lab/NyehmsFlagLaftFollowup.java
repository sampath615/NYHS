package gov.ny.state.doh.bhae.nyehms.web.spring.entity.lab;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * The documents flagged for LAFT follow-up (table NYEHMS_FLAG_LAFT_FOLLOWUP) and their resolution.
 * <p>
 * Inserted as an entity (the resolution columns are left to the database, as the former insert did not set them) and
 * changed by bulk JPQL updates. <b>FOLLOWUP_ID_PK as the key, filled by the database on insert (identity or trigger;
 * the former insert read it back as a generated key), is assumed.</b>
 */
@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "NYEHMS_FLAG_LAFT_FOLLOWUP")
public class NyehmsFlagLaftFollowup {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "FOLLOWUP_ID_PK")
	private Long followupIdPk;

	@Column(name = "FLAGGED_DOC_ID")
	private Long flaggedDocId;

	@Column(name = "DOCUMENT_ID")
	private Long documentId;

	@Column(name = "STATENO")
	private String stateno;

	@Column(name = "REASON_ID")
	private Long reasonId;

	@Column(name = "CREATE_UID")
	private Long createUid;

	@Column(name = "CREATE_DATE")
	private Timestamp createDate;

	@Column(name = "RESOLUTION_ID", insertable = false)
	private Long resolutionId;

	@Column(name = "RESOLUTION_UID", insertable = false)
	private Long resolutionUid;

	@Column(name = "RESOLUTION_DATE", insertable = false)
	private Timestamp resolutionDate;
}
