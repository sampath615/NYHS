package gov.ny.state.doh.bhae.nyehms.web.spring.entity.common;

import java.sql.Timestamp;

import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * A newborn screening (NBS) report, standardized (table STD_NBS_DOC): read by the NBS document screen and changed by
 * the NBS review (NBSReviewDAO; a change writes only the changed columns). The columns the former NBSReviewDAO
 * statements used.
 * <p>
 * <b>DOCUMENT_ID as the key is assumed</b> (one row per document). <b>Assumed column types</b> (the table definition
 * is not in this repository), following the former {@code NBSDocVO}: text where the VO had text (read with getString,
 * as MyBatis read it), whole numbers where it had numbers (NYS_ID, BIRTH_WEIGHT, CLIMS_RECID); the date columns are
 * dates (the former SQL formatted them with TO_CHAR); CURRENT_PROCESS_STATUS and the worker ids are numbers.
 */
@Entity
@DynamicUpdate
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "STD_NBS_DOC")
public class StdNbsDoc {

	@Id
	@Column(name = "DOCUMENT_ID")
	private Long documentId;

	@Column(name = "NYS_ID")
	private Integer nysId;

	@Column(name = "LABID")
	private String labid;

	@Column(name = "LABNO")
	private String labno;

	@Column(name = "LINK")
	private String link;

	@Column(name = "MEDRECNO")
	private String medrecno;

	@Column(name = "INFANT_FNAME")
	private String infantFname;

	@Column(name = "INFANT_LNAME")
	private String infantLname;

	@Column(name = "AKA")
	private String aka;

	@Column(name = "BIRTH_DATE")
	private Timestamp birthDate;

	@Column(name = "BIRTH_TIME")
	private String birthTime;

	@Column(name = "BIRTH_WEIGHT")
	private Integer birthWeight;

	@Column(name = "RACE_WHITE")
	private String raceWhite;

	@Column(name = "RACE_BLACK")
	private String raceBlack;

	@Column(name = "RACE_ASIAN")
	private String raceAsian;

	@Column(name = "RACE_OTHER")
	private String raceOther;

	@Column(name = "RACE_INDIAN")
	private String raceIndian;

	@Column(name = "ETHNICITY")
	private String ethnicity;

	@Column(name = "SEX")
	private String sex;

	@Column(name = "MOTHER_FNAME")
	private String motherFname;

	@Column(name = "MOTHER_LNAME")
	private String motherLname;

	@Column(name = "MOTHER_AGE")
	private String motherAge;

	@Column(name = "MOTHER_DOB")
	private Timestamp motherDob;

	@Column(name = "MOTHER_ADDRESS")
	private String motherAddress;

	@Column(name = "MOTHER_CITY")
	private String motherCity;

	@Column(name = "MOTHER_STATE")
	private String motherState;

	@Column(name = "MOTHER_ZIP")
	private String motherZip;

	@Column(name = "MOTHER_COUNTY")
	private String motherCounty;

	@Column(name = "MOTHER_REGION")
	private String motherRegion;

	@Column(name = "MOTHER_PHONE")
	private String motherPhone;

	@Column(name = "MOTHER_LATITUDE")
	private String motherLatitude;

	@Column(name = "MOTHER_LONGITUDE")
	private String motherLongitude;

	@Column(name = "MOTHER_CENSUS_TRACT")
	private String motherCensusTract;

	@Column(name = "COLLECTION_DATE")
	private Timestamp collectionDate;

	@Column(name = "DATE_RECEIVED")
	private Timestamp dateReceived;

	@Column(name = "DATE_REPORTED")
	private Timestamp dateReported;

	@Column(name = "INIT_RPT")
	private String initRpt;

	@Column(name = "BIRTH_HOSPITAL_CODE")
	private String birthHospitalCode;

	@Column(name = "PFI")
	private String pfi;

	@Column(name = "FACILITY_NAME")
	private String facilityName;

	@Column(name = "FACILITY_ADDRESS")
	private String facilityAddress;

	@Column(name = "FACILITY_CITY")
	private String facilityCity;

	@Column(name = "FACILITY_STATE")
	private String facilityState;

	@Column(name = "FACILITY_ZIP")
	private String facilityZip;

	@Column(name = "FACILITY_COUNTY")
	private String facilityCounty;

	@Column(name = "FACILITY_REGION")
	private String facilityRegion;

	@Column(name = "FACILITY_LATITUDE")
	private String facilityLatitude;

	@Column(name = "FACILITY_LONGITUDE")
	private String facilityLongitude;

	@Column(name = "FACILITY_CENSUS_TRACT")
	private String facilityCensusTract;

	@Column(name = "FACILITY_PHONE")
	private String facilityPhone;

	@Column(name = "FACILITY_EHARS_UID")
	private String facilityEharsUid;

	@Column(name = "TWIN_STATUS")
	private String twinStatus;

	@Column(name = "HIV_FINAL_STATUS")
	private String hivFinalStatus;

	@Column(name = "HIV_FINAL_STATUS_USER")
	private String hivFinalStatusUser;

	@Column(name = "TRANS_CALC_KEEP")
	private String transCalcKeep;

	@Column(name = "CLIMS_RECID")
	private Integer climsRecid;

	@Column(name = "PATIENTID")
	private String patientid;

	@Column(name = "MEMO")
	private String memo;

	@Column(name = "CLIMS_MATCH_UID")
	private Long climsMatchUid;

	@Column(name = "CLIMS_MATCH_DT")
	private Timestamp climsMatchDt;

	@Column(name = "CURRENT_PROCESS_STATUS")
	private Integer currentProcessStatus;

	@Column(name = "INFANT_STATENO")
	private String infantStateno;

	@Column(name = "MOTHER_STATENO")
	private String motherStateno;

	@Column(name = "POT_MOTHER_STATENO")
	private String potMotherStateno;

	@Column(name = "REVIEW_COMMENTS")
	private String reviewComments;

	@Column(name = "REVIEW_ID")
	private Long reviewId;

	@Column(name = "REVIEW_DATE")
	private Timestamp reviewDate;

	/** Matches the infant to a STATENO (and the CLIMS patient id) by manual review (MEMO 'MANUAL'). */
	public void matchStateno(String infantStateno, String patientid, String reviewComments, Long climsMatchUid, Timestamp climsMatchDt) {
		this.infantStateno = infantStateno;
		this.patientid = patientid;
		this.reviewComments = reviewComments;
		this.climsMatchUid = climsMatchUid;
		this.climsMatchDt = climsMatchDt;
		this.memo = "MANUAL";
	}

	/** Sets the mother's STATENO, reviewed by the worker at the time. */
	public void motherStateno(String motherStateno, Long reviewId, Timestamp reviewDate, String reviewComments) {
		this.motherStateno = motherStateno;
		this.reviewId = reviewId;
		this.reviewDate = reviewDate;
		this.reviewComments = reviewComments;
	}

	/** Saves review comments (and, when given, the reviewer's HIV final status). */
	public void comment(Long reviewId, Timestamp reviewDate, String reviewComments) {
		this.reviewId = reviewId;
		this.reviewDate = reviewDate;
		this.reviewComments = reviewComments;
	}

	/** Sets the reviewer's HIV final status. */
	public void hivFinalStatusUser(String hivFinalStatusUser) {
		this.hivFinalStatusUser = hivFinalStatusUser;
	}

	/** Matches the report to a CLIMS patient by manual review (MEMO 'MANUAL_REVIEW'). */
	public void matchClims(String patientid, Long climsMatchUid, Timestamp climsMatchDt, String reviewComments) {
		this.patientid = patientid;
		this.memo = "MANUAL_REVIEW";
		this.climsMatchUid = climsMatchUid;
		this.climsMatchDt = climsMatchDt;
		this.reviewComments = reviewComments;
	}
}
