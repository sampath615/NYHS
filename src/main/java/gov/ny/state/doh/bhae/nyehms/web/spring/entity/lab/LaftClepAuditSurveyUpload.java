package gov.ny.state.doh.bhae.nyehms.web.spring.entity.lab;

import java.sql.Timestamp;

import org.hibernate.annotations.ColumnTransformer;

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
 * The uploaded CLEP HIV survey rows (table LAFT_CLEP_AUDIT_SURVEY_UPLOAD), one per lab of an uploaded file, and their
 * follow-up.
 * <p>
 * The survey, assignment and close dates are mapped as MM/DD/YYYY text (read as {@code TO_CHAR(x, 'MM/DD/YYYY')},
 * written as {@code FN_TO_DATE(?, 'MM/DD/YYYY')}, as the former statements did). Inserted as entities (the follow-up
 * columns and SURVEY_NUMBER are left to the database, as the former insert did not set them) and changed by
 * JPQL bulk updates, which spell out the same conversions (HQL updates do not apply the write expressions). <b>PFI_UPLOAD_PK as the key, filled by the database on
 * insert (identity or trigger, as the former insert did not set it), is assumed.</b>
 */
@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "LAFT_CLEP_AUDIT_SURVEY_UPLOAD")
public class LaftClepAuditSurveyUpload {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PFI_UPLOAD_PK")
	private Long pfiUploadPk;

	@Column(name = "SURVEY_NUMBER", insertable = false, updatable = false)
	private Long surveyNumber;

	@Column(name = "PFI")
	private String pfi;

	@Column(name = "CLIA")
	private String clia;

	@Column(name = "LAB_NAME")
	private String labName;

	@ColumnTransformer(read = "to_char(SURVEY_BEGIN_DATE, 'MM/DD/YYYY')", write = "FN_TO_DATE(?, 'MM/DD/YYYY')")
	@Column(name = "SURVEY_BEGIN_DATE")
	private String surveyBeginDate;

	@ColumnTransformer(read = "to_char(SURVEY_END_DATE, 'MM/DD/YYYY')", write = "FN_TO_DATE(?, 'MM/DD/YYYY')")
	@Column(name = "SURVEY_END_DATE")
	private String surveyEndDate;

	@Column(name = "CLEP_FILE_NAME")
	private String clepFileName;

	@Column(name = "FILE_UPLOAD_BY")
	private String fileUploadBy;

	@Column(name = "FILE_UPLOAD_DATE")
	private Timestamp fileUploadDate;

	@Column(name = "COMMENTS", insertable = false)
	private String comments;

	@Column(name = "ASSIGNED_STAFF_ID", insertable = false)
	private String assignedStaffId;

	@ColumnTransformer(read = "to_char(ASSIGNED_DATE, 'MM/DD/YYYY')", write = "FN_TO_DATE(?, 'MM/DD/YYYY')")
	@Column(name = "ASSIGNED_DATE", insertable = false)
	private String assignedDate;

	@Column(name = "ASSIGNED_BY", insertable = false)
	private String assignedBy;

	@ColumnTransformer(read = "to_char(SURVEY_CLOSE_DATE, 'MM/DD/YYYY')", write = "FN_TO_DATE(?, 'MM/DD/YYYY')")
	@Column(name = "SURVEY_CLOSE_DATE", insertable = false)
	private String surveyCloseDate;

	@Column(name = "SURVEY_CLOSE_BY", insertable = false)
	private String surveyCloseBy;
}
