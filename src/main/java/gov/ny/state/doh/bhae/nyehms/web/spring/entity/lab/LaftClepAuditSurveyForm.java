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
 * The CLEP audit survey forms of the labs (table LAFT_CLEP_AUDIT_SURVEY_FORM): the survey, its review and the lab
 * recruitment assessment.
 * <p>
 * The date columns the screens enter and show as MM/DD/YYYY text (SURVEY_DATE, RECEIVE_DATE and the recruitment
 * assessment dates) are mapped as that text: read as {@code TO_CHAR(x, 'MM/DD/YYYY')} and written as
 * {@code FN_TO_DATE(?, 'MM/DD/YYYY')}, as the former statements did; the upper-cased columns are written as
 * {@code upper(?)} (SURVEYOR as {@code trim(upper(?))}). Inserted as an entity (the review, recruitment and last-update
 * columns are left to the database, as the former insert did not set them), changed by JPQL bulk updates, which spell
 * out the same conversions (HQL updates do not apply the write expressions). <b>SURVEY_NUMBER as the key, filled by the database on insert
 * (identity or trigger; the former insert read it back as a generated key), is assumed.</b>
 */
@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "LAFT_CLEP_AUDIT_SURVEY_FORM")
public class LaftClepAuditSurveyForm {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "SURVEY_NUMBER")
	private Long surveyNumber;

	@ColumnTransformer(write = "upper(?)")
	@Column(name = "PFI")
	private String pfi;

	@ColumnTransformer(read = "to_char(SURVEY_DATE, 'MM/DD/YYYY')", write = "FN_TO_DATE(?, 'MM/DD/YYYY')")
	@Column(name = "SURVEY_DATE")
	private String surveyDate;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "SURVEYOR")
	private String surveyor;

	@ColumnTransformer(read = "to_char(RECEIVE_DATE, 'MM/DD/YYYY')", write = "FN_TO_DATE(?, 'MM/DD/YYYY')")
	@Column(name = "RECEIVE_DATE")
	private String receiveDate;

	@Column(name = "INST_BASED_REPORTABLE_HIV")
	private String instBasedReportableHiv;

	@Column(name = "LAB_USES_ECLRS")
	private String labUsesEclrs;

	@Column(name = "LAB_USES_REF_LAB")
	private String labUsesRefLab;

	@ColumnTransformer(write = "upper(?)")
	@Column(name = "REFERENCE_PFI")
	private String referencePfi;

	@ColumnTransformer(write = "upper(?)")
	@Column(name = "CREATE_UID")
	private String createUid;

	@Column(name = "CREATE_DATE")
	private Timestamp createDate;

	@Column(name = "REVIEW_COMPLETED", insertable = false)
	private String reviewCompleted;

	@Column(name = "REVIEW_COMPLETED_BY", insertable = false)
	private String reviewCompletedBy;

	@Column(name = "REVIEW_COMPLETED_DATE", insertable = false)
	private Timestamp reviewCompletedDate;

	@Column(name = "RECRUIT_ASSESS_STAFF_ASSIGN", insertable = false)
	private String recruitAssessStaffAssign;

	@ColumnTransformer(read = "to_char(RECRUIT_ASSESS_DATE_ASSIGN, 'MM/DD/YYYY')", write = "FN_TO_DATE(?, 'MM/DD/YYYY')")
	@Column(name = "RECRUIT_ASSESS_DATE_ASSIGN", insertable = false)
	private String recruitAssessDateAssign;

	@Column(name = "RECRUIT_ASSESS_STAFF_ASSIGN_BY", insertable = false)
	private String recruitAssessStaffAssignBy;

	@ColumnTransformer(read = "to_char(RECRUIT_ASSESS_DATE_COMPLETE, 'MM/DD/YYYY')", write = "FN_TO_DATE(?, 'MM/DD/YYYY')")
	@Column(name = "RECRUIT_ASSESS_DATE_COMPLETE", insertable = false)
	private String recruitAssessDateComplete;

	@Column(name = "RECRUIT_ASSESS_STAFF_COMPLETE", insertable = false)
	private String recruitAssessStaffComplete;

	@Column(name = "RECRUIT_ASSESS_OUTCOME", insertable = false)
	private String recruitAssessOutcome;

	@Column(name = "RECRUIT_ASSESS_COMMENTS", insertable = false)
	private String recruitAssessComments;

	@ColumnTransformer(write = "upper(?)")
	@Column(name = "LAST_UPDATE_BY", insertable = false)
	private String lastUpdateBy;

	@Column(name = "LAST_UPDATE_DATE", insertable = false)
	private Timestamp lastUpdateDate;
}
