package gov.ny.state.doh.bhae.nyehms.web.spring.entity.common;

import java.math.BigDecimal;
import java.sql.Timestamp;

import org.hibernate.annotations.ColumnTransformer;
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
 * A Medicaid (or insurance death) record, standardized (table STD_MEDICAID_DOC): the columns the import and the
 * document copy write and the Medicaid document screen reads (MedicaidDocumentDAO).
 * <p>
 * Inserted, never changed. <b>DOCUMENT_ID as the key is assumed</b> (one row per document). An insert keeps the
 * conversions of the former insert in the database (the write transformers: UPPER, UPPER(TRIM(?))). The race flags,
 * the coordinates and the census tract are written as text, as the former insert bound them.
 */
@Entity
@Immutable
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "STD_MEDICAID_DOC")
public class StdMedicaidDoc implements Persistable<Integer> {

	@Column(name = "RACE_INDIAN")
	private String raceIndian;

	@Column(name = "RACE_BLACK")
	private String raceBlack;

	@Column(name = "RACE_ASIAN")
	private String raceAsian;

	@Column(name = "RACE_PACIFIC")
	private String racePacific;

	@Column(name = "RACE_WHITE")
	private String raceWhite;

	@Column(name = "RACE_MULTIPLE")
	private String raceMultiple;

	@Column(name = "RACE_OTHER")
	private String raceOther;

	@Column(name = "RACE_UNKNOWN")
	private String raceUnknown;

	@Column(name = "MATCH_CD")
	private String matchCd;

	@Column(name = "CREATE_DATE")
	private Timestamp createDate;

	@Column(name = "REVIEW_DATE")
	private Timestamp reviewDate;

	@Column(name = "EHARS_IMPORT_DATE")
	private Timestamp eharsImportDate;

	@Column(name = "NYC_SHIP_DATE")
	private Timestamp nycShipDate;

	@Id
	@Column(name = "DOCUMENT_ID")
	private Integer documentId;

	@Column(name = "CURRENT_PROCESS_STATUS")
	private BigDecimal currentProcessStatus;

	@Column(name = "QS_PARENT_DOC_ID")
	private BigDecimal qsParentDocId;

	@Column(name = "MATCH_WEIGHT")
	private BigDecimal matchWeight;

	@ColumnTransformer(write = "upper(trim(?))")
	@Column(name = "MNAME")
	private String mname;

	@ColumnTransformer(write = "upper(?)")
	@Column(name = "CRITERIA_1")
	private String criteria1;

	@ColumnTransformer(write = "upper(?)")
	@Column(name = "CRITERIA_2")
	private String criteria2;

	@ColumnTransformer(write = "upper(?)")
	@Column(name = "CRITERIA_3")
	private String criteria3;

	@ColumnTransformer(write = "upper(?)")
	@Column(name = "CRITERIA_4")
	private String criteria4;

	@ColumnTransformer(write = "upper(?)")
	@Column(name = "CRITERIA_5")
	private String criteria5;

	@ColumnTransformer(write = "upper(?)")
	@Column(name = "CRITERIA_6")
	private String criteria6;

	@ColumnTransformer(write = "upper(?)")
	@Column(name = "CRITERIA_7")
	private String criteria7;

	@ColumnTransformer(write = "upper(?)")
	@Column(name = "CRITERIA_8")
	private String criteria8;

	@ColumnTransformer(write = "upper(?)")
	@Column(name = "CRITERIA_9")
	private String criteria9;

	@ColumnTransformer(write = "upper(?)")
	@Column(name = "CRITERIA_10")
	private String criteria10;

	@ColumnTransformer(write = "upper(trim(?))")
	@Column(name = "EHARS_STATUS_CD")
	private String eharsStatusCd;

	@ColumnTransformer(write = "upper(?)")
	@Column(name = "DOB_TEXT")
	private String dobText;

	@ColumnTransformer(write = "upper(trim(?))")
	@Column(name = "CURRENT_GENDER")
	private String currentGender;

	@Column(name = "ETHNICITY")
	private String ethnicity;

	@Column(name = "PERSON_LATITUDE")
	private String personLatitude;

	@ColumnTransformer(write = "upper(?)")
	@Column(name = "DOD_TEXT")
	private String dodText;

	@ColumnTransformer(write = "upper(trim(?))")
	@Column(name = "CREATE_UID")
	private String createUid;

	@Column(name = "STATENO")
	private String stateno;

	@ColumnTransformer(write = "upper(trim(?))")
	@Column(name = "MEDICAID_NUMBER")
	private String medicaidNumber;

	@Column(name = "PERSON_LONGITUDE")
	private String personLongitude;

	@ColumnTransformer(write = "upper(trim(?))")
	@Column(name = "PERSON_STATE")
	private String personState;

	@ColumnTransformer(write = "upper(trim(?))")
	@Column(name = "PERSON_COUNTY")
	private String personCounty;

	@ColumnTransformer(write = "upper(trim(?))")
	@Column(name = "PERSON_CITY")
	private String personCity;

	@Column(name = "MATCH_PASS")
	private String matchPass;

	@ColumnTransformer(write = "upper(trim(?))")
	@Column(name = "FNAME")
	private String fname;

	@ColumnTransformer(write = "upper(trim(?))")
	@Column(name = "LNAME")
	private String lname;

	@ColumnTransformer(write = "upper(trim(?))")
	@Column(name = "PERSON_ZIP")
	private String personZip;

	@Column(name = "PERSON_CENSUS_TRACT")
	private String personCensusTract;

	@ColumnTransformer(write = "upper(trim(?))")
	@Column(name = "PERSON_ADDRESS")
	private String personAddress;

	@Column(name = "VITAL_STATUS")
	private String vitalStatus;

	@ColumnTransformer(write = "upper(trim(?))")
	@Column(name = "REVIEW_UID")
	private String reviewUid;

	@ColumnTransformer(write = "upper(?)")
	@Column(name = "SSN")
	private String ssn;

	@Column(name = "MATCHED_PROCESS_DATE")
	private Timestamp matchedProcessDate;

	@Column(name = "MEDICAID_FILE")
	private String medicaidFile;

	@Column(name = "FLUX_MATCH_CD")
	private String fluxMatchCd;

	@Column(name = "FLUX_POT_STATENO")
	private String fluxPotStateno;

	@Column(name = "FLUX_MATCHED_STATENO")
	private String fluxMatchedStateno;

	@Column(name = "FLUX_QS_PARENT_DOC_ID")
	private BigDecimal fluxQsParentDocId;

	@Column(name = "FLUX_MATCHED_PROCESS_DATE")
	private Timestamp fluxMatchedProcessDate;

	@Override
	public Integer getId() {
		return documentId;
	}

	/** Always a new row (the application only inserts it). */
	@Override
	public boolean isNew() {
		return true;
	}
}
