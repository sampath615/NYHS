package gov.ny.state.doh.bhae.nyehms.web.spring.entity.document;

import java.math.BigDecimal;
import java.sql.Timestamp;

import org.hibernate.annotations.Immutable;
import org.springframework.data.domain.Persistable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * A quality of care record, standardized (table STD_QUALITY_CARE_DOC); the columns the quality of care document
 * screen reads and the import writes.
 * <p>
 * Never updated: a row is inserted once by the import ({@code QualCareDocRepository.saveAndFlush}, built by
 * {@code QualCareStdDocMapping}), so it is always new ({@link #isNew()}: save() inserts without a select first).
 * Columns the former SQL insert wrote as text are text here too (Oracle converts the numeric ones).
 * <b>DOCUMENT_ID as the key is assumed</b> (one row per document).
 */
@Entity
@Immutable
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "STD_QUALITY_CARE_DOC")
public class StdQualityCareDoc implements Persistable<Integer> {

	@Column(name = "LINKAGE_DAYS")
	private String linkageDays;

	@Column(name = "SUPPRESSION_DAYS")
	private String suppressionDays;

	@Column(name = "SUPPRESSION_DAYS_DISCHARGE")
	private String suppressionDaysDischarge;

	@Column(name = "SUPPRESSION_DAYS_HIV_CARE")
	private String suppressionDaysHivCare;

	@Column(name = "LINKAGE_30DAYS")
	private String linkage30days;

	@Column(name = "LINKAGE_3DAYS")
	private String linkage3days;

	@Column(name = "LINKAGE_7DAYS")
	private String linkage7days;

	@Column(name = "LINKAGE_90DAYS")
	private String linkage90days;

	@Column(name = "ON_ARV_ALL_PREV_DX_ACTIVE")
	private String onArvAllPrevDxActive;

	@Column(name = "ON_ARV_EST_ACTIVE")
	private String onArvEstActive;

	@Column(name = "ON_ARV_NEWLY_DX")
	private String onArvNewlyDx;

	@Column(name = "ON_ARV_OPEN_PTS")
	private String onArvOpenPts;

	@Column(name = "ON_ARV_OTHER_NEW_TO_CARE")
	private String onArvOtherNewToCare;

	@Column(name = "EVER_SUPPRESSED_NEWLY_DX")
	private String everSuppressedNewlyDx;

	@Column(name = "SUPPRESSION_91DAYS_FROM_CARE")
	private String suppression91daysFromCare;

	@Column(name = "SUPPRESSION_91DAYS_FROM_DIS")
	private String suppression91daysFromDis;

	@Column(name = "VL_SUPPRESSION_ALL_PREV_DX_ACT")
	private String vlSuppressionAllPrevDxAct;

	@Column(name = "VL_SUPPRESSION_EST_ACTIVE")
	private String vlSuppressionEstActive;

	@Column(name = "VL_SUPPRESSION_NEWLY_DX")
	private String vlSuppressionNewlyDx;

	@Column(name = "VL_SUPPRESSION_OPEN_PTS")
	private String vlSuppressionOpenPts;

	@Column(name = "VL_SUPPRESSION_OTHER_NEW_TO_CARE")
	private String vlSuppressionOtherNewToCare;

	@Column(name = "VL_TESTING_ALL_PREV_DX_ACTIVE")
	private String vlTestingAllPrevDxActive;

	@Column(name = "VL_TESTING_EST_ACTIVE")
	private String vlTestingEstActive;

	@Column(name = "VL_TESTING_NEWLY_DX")
	private String vlTestingNewlyDx;

	@Column(name = "VL_TESTING_OPEN_PTS")
	private String vlTestingOpenPts;

	@Column(name = "VL_TESTING_OTHER_NEW_TO_CARE")
	private String vlTestingOtherNewToCare;

	@Column(name = "FACILITY_CENSUS_TRACT")
	private String facilityCensusTract;

	@Column(name = "MED_RECORD_NUMBER")
	private String medRecordNumber;

	@Column(name = "ASIAN_SUB_GROUP")
	private String asianSubGroup;

	@Column(name = "FACILITY_ZIP")
	private String facilityZip;

	@Column(name = "PATIENT_STATUS")
	private String patientStatus;

	@Column(name = "INPATIENT")
	private String inpatient;

	@Column(name = "LINKED")
	private String linked;

	@Column(name = "HIV_CARE")
	private String hivCare;

	@Column(name = "ARV")
	private String arv;

	@Column(name = "ON_ARV")
	private String onArv;

	@Column(name = "SUPPRESSED_EVER_REVIEW_YEAR")
	private String suppressedEverReviewYear;

	@Column(name = "SUPPRESSED_FINAL_REVIEW_YEAR")
	private String suppressedFinalReviewYear;

	@Column(name = "SUPPRESSED_FINAL_VL")
	private String suppressedFinalVl;

	@Column(name = "VL_REVIEWED")
	private String vlReviewed;

	@Column(name = "VL_SUPPRESSED")
	private String vlSuppressed;

	@Column(name = "VL_TEST_REVIEW_YEAR")
	private String vlTestReviewYear;

	@Column(name = "VL_TESTED")
	private String vlTested;

	@Column(name = "FACILITY_COUNTY")
	private String facilityCounty;

	@Column(name = "FLUX_MATCH_CD")
	private String fluxMatchCd;

	@Column(name = "MATCH_CD")
	private String matchCd;

	@Column(name = "HISPANIC_SUB_GROUP")
	private String hispanicSubGroup;

	@Column(name = "SERVICE_LINE")
	private String serviceLine;

	@Column(name = "OTHER_SERVICE")
	private String otherService;

	@Column(name = "DIAGNOSIS")
	private String diagnosis;

	@Column(name = "ETHNICITY")
	private String ethnicity;

	@Column(name = "HOUSING_STATUS")
	private String housingStatus;

	@Column(name = "FACILITY_STATE")
	private String facilityState;

	@Column(name = "FACILITY_CITY")
	private String facilityCity;

	@Column(name = "QUAL_CARE_FILE")
	private String qualCareFile;

	@Column(name = "FACILITY_LONGITUDE")
	private String facilityLongitude;

	@Column(name = "FNAME")
	private String fname;

	@Column(name = "LNAME")
	private String lname;

	@Column(name = "FACILITY_ADDRESS")
	private String facilityAddress;

	@Column(name = "ORGANIZATION_NAME")
	private String organizationName;

	@Column(name = "FACILITY_NAME")
	private String facilityName;

	@Column(name = "FLUX_POT_STATENO")
	private String fluxPotStateno;

	@Column(name = "MATCHED_STATENO")
	private String matchedStateno;

	@Column(name = "DOB_TEXT")
	private String dobText;

	@Column(name = "INSURANCE")
	private String insurance;

	@Column(name = "ENROLLMENT")
	private String enrollment;

	@Column(name = "FACILITY_LATITUDE")
	private String facilityLatitude;

	@Column(name = "FLUX_MATCHED_STATENO")
	private String fluxMatchedStateno;

	@Column(name = "CREATE_UID")
	private String createUid;

	@Column(name = "MNAME")
	private String mname;

	@Column(name = "BIRTH_SEX")
	private String birthSex;

	@Column(name = "GENDER")
	private String gender;

	@Column(name = "RACE_WHITE")
	private String raceWhite;

	@Column(name = "RACE_UNKNOWN")
	private String raceUnknown;

	@Column(name = "RACE_PACIFIC")
	private String racePacific;

	@Column(name = "RACE_OTHER")
	private String raceOther;

	@Column(name = "RACE_INDIAN")
	private String raceIndian;

	@Column(name = "RACE_BLACK")
	private String raceBlack;

	@Column(name = "RACE_ASIAN")
	private String raceAsian;

	@Column(name = "RECV_BLOOD_PROD")
	private String recvBloodProd;

	@Column(name = "RECV_CLOTTING_FACTOR")
	private String recvClottingFactor;

	@Column(name = "SEX_PARTNER_HIV")
	private String sexPartnerHiv;

	@Column(name = "IDU")
	private String idu;

	@Column(name = "RISK_MSM")
	private String riskMsm;

	@Column(name = "PERINATAL")
	private String perinatal;

	@Column(name = "RISK_OTHER")
	private String riskOther;

	@Column(name = "RISK_UNKNOWN")
	private String riskUnknown;

	@Column(name = "MATCH_WEIGHT")
	private BigDecimal matchWeight;

	@Id
	@Column(name = "DOCUMENT_ID")
	private Integer documentId;

	/** Column ID (named so as not to clash with {@link #getId()}, the key). */
	@Column(name = "ID")
	private Integer recordId;

	@Column(name = "AGE")
	private Integer age;

	@Column(name = "CURRENT_PROCESS_STATUS")
	private Integer currentProcessStatus;

	@Column(name = "MATCH_PASS")
	private Integer matchPass;

	@Column(name = "DIAGNOSIS_DATE")
	private Timestamp diagnosisDate;

	@Column(name = "DISCHARGE_DATE")
	private Timestamp dischargeDate;

	/** The column as the document screen shows it (the database's text of the date). */
	@Column(name = "HIV_CARE_DATE", insertable = false, updatable = false)
	private String hivCareDate;

	/** The same column as the import writes it. */
	@Column(name = "HIV_CARE_DATE")
	private Timestamp hivCareDateValue;

	/** The column as the document screen shows it (the database's text of the date). */
	@Column(name = "FIRST_SUPPRESSED_DATE_NEWLY_DX", insertable = false, updatable = false)
	private String firstSuppressedDateNewlyDx;

	/** The same column as the import writes it. */
	@Column(name = "FIRST_SUPPRESSED_DATE_NEWLY_DX")
	private Timestamp firstSuppressedDateNewlyDxValue;

	/** The column as the document screen shows it (the database's text of the date). */
	@Column(name = "FIRST_VL_DATE_NEWLY_DX", insertable = false, updatable = false)
	private String firstVlDateNewlyDx;

	/** The same column as the import writes it. */
	@Column(name = "FIRST_VL_DATE_NEWLY_DX")
	private Timestamp firstVlDateNewlyDxValue;

	@Column(name = "FLUX_MATCHED_PROCESS_DATE")
	private Timestamp fluxMatchedProcessDate;

	@Column(name = "CREATE_DATE")
	private Timestamp createDate;

	@Override
	public Integer getId() {
		return documentId;
	}

	/** Rows are only ever inserted. */
	@Override
	public boolean isNew() {
		return true;
	}
}
