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
 * The submission (report file) types of a lab (table LAB_SUBMISSION_TYPE_MAPPING): type, primary flag, validity and LIS vendor; the columns the former statements named (two of them read the table with select *, which stays SQL).
 * <p>
 * The new submission type of the submission screen is saved as an entity (its key is read back, as the former insert's generated key was); PRIMARY_SUBMISSION, which that insert did not set, is not insertable. The other insert is a JPQL insert of its own columns; changes are bulk JPQL updates. <b>LAB_SUBMIT_ID as the key, filled by the database on insert, is assumed.</b>
 */
@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "LAB_SUBMISSION_TYPE_MAPPING")
public class LabSubmissionTypeMapping {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "LAB_SUBMIT_ID")
	private Long labSubmitId;

	@Column(name = "PFI")
	private String pfi;

	@Column(name = "SUBMISSION_TYPE_CD")
	private String submissionTypeCd;

	@Column(name = "PRIMARY_SUBMISSION", insertable = false)
	private String primarySubmission;

	@Column(name = "START_DATE")
	private Timestamp startDate;

	@Column(name = "END_DATE")
	private Timestamp endDate;

	@Column(name = "LIS_VENDOR")
	private String lisVendor;

	@Column(name = "LAST_UPDATED_BY")
	private Long lastUpdatedBy;

	@Column(name = "LAST_UPDATED_DATE")
	private Timestamp lastUpdatedDate;
}
