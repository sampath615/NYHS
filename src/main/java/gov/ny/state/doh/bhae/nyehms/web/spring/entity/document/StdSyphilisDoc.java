package gov.ny.state.doh.bhae.nyehms.web.spring.entity.document;

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
 * A syphilis report, standardized (); the columns the syphilis document screen reads and the import writes.
 * <p>
 * Never updated: a row is inserted once by the import ({@code SyphDocRepository.saveAndFlush}, built by
 * {@code SyphStdDocMapping}), so it is always new ({@link #isNew()}: save() inserts without a select first).
 * Columns the former SQL insert wrote as text are text here too (Oracle converts the numeric ones).
 * <b>DOCUMENT_ID as the key is assumed</b> (one row per document).
 */
@Entity
@Immutable
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "STD_SYPHILIS_DOC")
public class StdSyphilisDoc implements Persistable<Integer> {

	@Id
	@Column(name = "DOCUMENT_ID")
	private Integer documentId;

	@Column(name = "EHARS_IMPORT_DATE")
	private Timestamp eharsImportDate;

	@Column(name = "FNAME")
	private String fname;

	@Column(name = "MNAME")
	private String mname;

	@Column(name = "LNAME")
	private String lname;

	@Column(name = "DOB")
	private Timestamp dob;

	@Column(name = "DOD")
	private Timestamp dod;

	@Column(name = "PERSON_ADDRESS")
	private String personAddress;

	@Column(name = "PERSON_CITY")
	private String personCity;

	@Column(name = "PERSON_STATE")
	private String personState;

	@Column(name = "PERSON_ZIP")
	private String personZip;

	@Column(name = "PERSON_COUNTY")
	private String personCounty;

	@Column(name = "PERSON_PHONE")
	private String personPhone;

	@Column(name = "RACE_WHITE")
	private String raceWhite;

	@Column(name = "RACE_BLACK")
	private String raceBlack;

	@Column(name = "RACE_ASIAN")
	private String raceAsian;

	@Column(name = "RACE_INDIAN")
	private String raceIndian;

	@Column(name = "RACE_PACIFIC")
	private String racePacific;

	@Column(name = "RACE_OTHER")
	private String raceOther;

	@Column(name = "RACE_UNKNOWN")
	private String raceUnknown;

	@Column(name = "ETHNICITY")
	private String ethnicity;

	@Column(name = "BIRTH_SEX")
	private String birthSex;

	@Column(name = "CASEKEY")
	private String casekey;

	@Column(name = "DISEASE_CODE")
	private String diseaseCode;

	@Column(name = "SYPHILIS_DIAGNOSIS_DATE")
	private Timestamp syphilisDiagnosisDate;

	@Column(name = "RECEIVED_DATE")
	private Timestamp receivedDate;

	@Column(name = "PROV_FNAME")
	private String provFname;

	@Column(name = "PROV_LNAME")
	private String provLname;

	@Column(name = "PROV_ADDRESS")
	private String provAddress;

	@Column(name = "PROV_CITY")
	private String provCity;

	@Column(name = "PROV_STATE")
	private String provState;

	@Column(name = "PROV_ZIP")
	private String provZip;

	@Column(name = "PROV_PHONE")
	private String provPhone;

	@Column(name = "PROV_COUNTY")
	private String provCounty;

	@Column(name = "CURRENT_PROCESS_STATUS")
	private Integer currentProcessStatus;

	@Column(name = "MATCHED_PROCESS_DATE")
	private Timestamp matchedProcessDate;

	@Column(name = "EHARS_STATUS_CD")
	private String eharsStatusCd;

	@Column(name = "MATCHED_STATENO")
	private String matchedStateno;

	@Column(name = "QS_PARENT_DOC_ID")
	private Long qsParentDocId;

	@Column(name = "REVIEW_UID")
	private String reviewUid;

	@Column(name = "REVIEW_DATE")
	private Timestamp reviewDate;

	@Column(name = "FLUX_MATCH_CD")
	private String fluxMatchCd;

	@Column(name = "FLUX_POT_STATENO")
	private String fluxPotStateno;

	@Column(name = "FLUX_MATCHED_STATENO")
	private String fluxMatchedStateno;

	@Column(name = "FLUX_QS_PARENT_DOC_ID")
	private Long fluxQsParentDocId;

	@Column(name = "FLUX_MATCHED_PROCESS_DATE")
	private Timestamp fluxMatchedProcessDate;

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
