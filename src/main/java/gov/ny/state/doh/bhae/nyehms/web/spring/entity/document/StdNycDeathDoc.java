package gov.ny.state.doh.bhae.nyehms.web.spring.entity.document;

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
 * An NYC death certificate, standardized (table STD_NYC_DEATH_DOC); the columns the NYC death document screen reads.
 * <p>
 * Read-only. <b>DOCUMENT_ID as the key is assumed</b> (one row per document).
 */
@Entity
@Immutable
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "STD_NYC_DEATH_DOC")
public class StdNycDeathDoc implements Persistable<Integer> {

	@Id
	@Column(name = "DOCUMENT_ID")
	private Integer documentId;

	@Column(name = "EHARS_IMPORT_DATE")
	private Timestamp eharsImportDate;

	@Column(name = "DEATH_CERT")
	private String deathCert;

	@Column(name = "DEATH_CERT_LICENSE")
	private String deathCertLicense;

	@Column(name = "DEATH_COUNTY")
	private String deathCounty;

	@Column(name = "NYC_SHIP_DATE")
	private Timestamp nycShipDate;

	@Column(name = "DOD_TEXT")
	private String dodText;

	@Column(name = "AGE_DEATH")
	private String ageDeath;

	@Column(name = "ICD10")
	private String icd10;

	@Column(name = "SPEC_INT_CAUSE")
	private String specIntCause;

	@Column(name = "UNITS_AGE")
	private String unitsAge;

	@Column(name = "FNAME")
	private String fname;

	@Column(name = "LNAME")
	private String lname;

	@Column(name = "MNAME")
	private String mname;

	@Column(name = "SOUNDEX")
	private String soundex;

	@Column(name = "FATHER_LNAME")
	private String fatherLname;

	@Column(name = "MOTHER_MAIDEN")
	private String motherMaiden;

	@Column(name = "SSN")
	private String ssn;

	@Column(name = "DOB_TEXT")
	private String dobText;

	@Column(name = "CURRENT_GENDER")
	private String currentGender;

	@Column(name = "MARITAL_STATUS")
	private String maritalStatus;

	@Column(name = "PREGNANT")
	private String pregnant;

	@Column(name = "DELIVERY_DATE_TEXT")
	private String deliveryDateText;

	@Column(name = "MED_RECORD_NUMBER")
	private String medRecordNumber;

	@Column(name = "BIRTH_STATE")
	private String birthState;

	@Column(name = "PERSON_ADDRESS")
	private String personAddress;

	@Column(name = "PERSON_ZIP")
	private String personZip;

	@Column(name = "PERSON_STATE")
	private String personState;

	@Column(name = "PERSON_COUNTY")
	private String personCounty;

	@Column(name = "PERSON_CITY")
	private String personCity;

	@Column(name = "LOCAL_REG_NO")
	private String localRegNo;

	@Column(name = "FIPS_PLACE")
	private String fipsPlace;

	@Column(name = "PFI")
	private String pfi;

	@Column(name = "FACILITY_TYPE")
	private String facilityType;

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

	@Column(name = "FACILITY_PLUS4")
	private String facilityPlus4;

	@Column(name = "FACILITY_PHONE")
	private String facilityPhone;

	@Column(name = "FACILITY_COUNTY")
	private String facilityCounty;

	@Column(name = "INST_CODE")
	private String instCode;

	@Column(name = "TRANS_FAC_CD")
	private String transFacCd;

	@Column(name = "ETHNICITY")
	private String ethnicity;

	@Column(name = "RACE_WHITE")
	private String raceWhite;

	@Column(name = "RACE_BLACK")
	private String raceBlack;

	@Column(name = "RACE_ASIAN")
	private String raceAsian;

	@Column(name = "RACE_PACIFIC")
	private String racePacific;

	@Column(name = "RACE_INDIAN")
	private String raceIndian;

	@Column(name = "RACE_OTHER")
	private String raceOther;

	@Column(name = "RACE_UNKNOWN")
	private String raceUnknown;

	@Column(name = "DEATH_UNIQUE_ID")
	private String deathUniqueId;

	@Column(name = "DEATH_ID")
	private String deathId;

	@Column(name = "UNDERLYING")
	private String underlying;

	@Column(name = "CAUSE1")
	private String cause1;

	@Column(name = "CAUSE2")
	private String cause2;

	@Column(name = "CAUSE3")
	private String cause3;

	@Column(name = "CAUSE4")
	private String cause4;

	@Column(name = "CAUSE5")
	private String cause5;

	@Column(name = "CAUSE6")
	private String cause6;

	@Column(name = "CAUSE7")
	private String cause7;

	@Column(name = "CAUSE8")
	private String cause8;

	@Column(name = "CAUSE9")
	private String cause9;

	@Column(name = "CAUSE10")
	private String cause10;

	@Column(name = "CAUSE11")
	private String cause11;

	@Column(name = "CAUSE12")
	private String cause12;

	@Column(name = "CAUSE13")
	private String cause13;

	@Column(name = "CAUSE14")
	private String cause14;

	@Column(name = "CAUSE15")
	private String cause15;

	@Column(name = "CAUSE16")
	private String cause16;

	@Column(name = "CAUSE17")
	private String cause17;

	@Column(name = "CAUSE18")
	private String cause18;

	@Column(name = "CAUSE19")
	private String cause19;

	@Column(name = "CAUSE20")
	private String cause20;

	@Column(name = "H_CAUSE")
	private String hcause;

	@Column(name = "OI_CAUSE")
	private String oiCause;

	@Column(name = "CREATE_DATE")
	private Timestamp createDate;

	@Column(name = "REVIEW_DATE")
	private Timestamp reviewDate;

	@Column(name = "MATCH_WEIGHT")
	private BigDecimal matchWeight;

	@Column(name = "CURRENT_PROCESS_STATUS")
	private Integer currentProcessStatus;

	@Column(name = "QS_PARENT_DOC_ID")
	private Long qsParentDocId;

	@Column(name = "RACE_MULTIPLE")
	private String raceMultiple;

	@Column(name = "EHARS_STATUS_CD")
	private String eharsStatusCd;

	@Column(name = "PERSON_LATITUDE")
	private String personLatitude;

	@Column(name = "FACILITY_LATITUDE")
	private String facilityLatitude;

	@Column(name = "CREATE_UID")
	private String createUid;

	@Column(name = "MATCHED_STATENO")
	private String matchedStateno;

	@Column(name = "PERSON_LONGITUDE")
	private String personLongitude;

	@Column(name = "FACILITY_LONGITUDE")
	private String facilityLongitude;

	@Column(name = "MATCH_CD")
	private String matchCd;

	@Column(name = "NYS_COUNTY")
	private String nysCounty;

	@Column(name = "INST")
	private String inst;

	@Column(name = "PERSON_PLUS4")
	private String personPlus4;

	@Column(name = "MATCH_PASS")
	private String matchPass;

	@Column(name = "NYC_FAC_CODE")
	private String nycFacCode;

	@Column(name = "SUFFIX")
	private String suffix;

	@Column(name = "DEATH_FILE")
	private String deathFile;

	@Column(name = "PERSON_CENSUS_TRACT")
	private String personCensusTract;

	@Column(name = "FACILITY_CENSUS_TRACT")
	private String facilityCensusTract;

	@Column(name = "ORIG_FACILITY_ADDRESS")
	private String origFacilityAddress;

	@Column(name = "REVIEW_UID")
	private String reviewUid;

	@Column(name = "MATCHED_PROCESS_DATE")
	private Timestamp matchedProcessDate;

	/**
	 * Written as ORA_HASH of the text built by the mapping (the former insert hashed the same text in
	 * Oracle); read as the stored number.
	 */
	@Column(name = "HASH_NUMBER")
	@ColumnTransformer(write = "ORA_HASH(?)")
	private String hashNumber;

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
