package gov.ny.state.doh.bhae.nyehms.web.spring.entity.common;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

/**
 * The files uploaded through the provider portal (table EPRF_PROV_PORTAL_FILE_UPLOAD); the columns the provider portal
 * reports read (who uploaded a file and its file type) and the columns the EPRF OOC file review and follow-up read and
 * write (category, acceptance, inquiry county / facility, director e-mail, client feedback and follow-up dates).
 * <p>
 * Read through JPQL projections and subqueries and changed by JPQL bulk updates only. <b>FILENAME as the key is
 * assumed.</b> FILE_ACCEPTED_UID is written with the worker id (an int, as before).
 */
@Entity
@Getter
@Table(name = "EPRF_PROV_PORTAL_FILE_UPLOAD")
public class EprfProvPortalFileUpload {

	@Id
	@Column(name = "FILENAME")
	private String filename;

	@Column(name = "UPLOAD_UID")
	private String uploadUid;

	@Column(name = "FILE_TYPE")
	private String fileType;

	@Column(name = "FILE_CATEGORY")
	private String fileCategory;

	@Column(name = "FILE_ACCEPTED_UID")
	private Integer fileAcceptedUid;

	@Column(name = "FILE_ACCEPTED_DATE")
	private Timestamp fileAcceptedDate;

	@Column(name = "FILE_CLIENT_FEEDBACK_DATE")
	private Timestamp fileClientFeedbackDate;

	@Column(name = "FILE_EMAIL_DIRECTOR_DATE")
	private Timestamp fileEmailDirectorDate;

	@Column(name = "FILE_FOLLOW_UP_COMPLETE_DATE")
	private Timestamp fileFollowUpCompleteDate;

	@Column(name = "FILE_MATCH_REVIEW_COMPLTE_DATE")
	private Timestamp fileMatchReviewCompleteDate;

	@Column(name = "INQUIRY_COUNTY_FIPS")
	private String inquiryCountyFips;

	@Column(name = "INQUIRY_FACILITY_NAME")
	private String inquiryFacilityName;
}
