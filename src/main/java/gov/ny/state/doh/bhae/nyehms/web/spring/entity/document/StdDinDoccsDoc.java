package gov.ny.state.doh.bhae.nyehms.web.spring.entity.document;

import java.math.BigDecimal;
import java.sql.Timestamp;

import org.hibernate.annotations.ColumnTransformer;
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
 * A DIN (inmate) record of a DOCCS report, standardized (table STD_DIN_DOCCS_DOC): the columns the DOCCS import writes
 * and the DIN DOCCS screen reads (DoccsDocumentDAO).
 * <p>
 * Inserted, never changed. <b>DOCUMENT_ID as the key is assumed</b> (one row per DIN document); the import writes one
 * row per DIN record and facility match, as the former insert did, and does not keep the rows in the persistence
 * context. An insert keeps the conversions of the former insert in the database (the write transformers: TRIM(UPPER),
 * TO_CHAR(FN_TO_DATE(...)) of the received date texts); the race flags are written as text, as the former insert bound
 * them.
 */
@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "STD_DIN_DOCCS_DOC")
public class StdDinDoccsDoc implements Persistable<Long> {

	@Id
	@Column(name = "DOCUMENT_ID")
	private Long documentId;

	@Column(name = "DOCCS_DOC_ID")
	private Long doccsDocId;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "NYSID")
	private String nysid;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "SSN")
	private String ssn;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "PROVNAME")
	private String provname;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "MEDICAID_NUMBER")
	private String medicaidNumber;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "DIN")
	private String din;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "PERSON_ADDRESS")
	private String personAddress;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "LEGAL_PERSON_ADDRESS")
	private String legalPersonAddress;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "PREV_PERSON_ADDRESS")
	private String prevPersonAddress;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "PROV_ADDR")
	private String provAddr;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "PERSON_CENSUS_TRACT")
	private String personCensusTract;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "OWNING_FACILITY_CENSUS_TRACT")
	private String owningFacilityCensusTract;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "CURRENT_FACILITY_CENSUS_TRACT")
	private String currentFacilityCensusTract;

	@Column(name = "DOCCS_FILE")
	private String doccsFile;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "SUFFIX")
	private String suffix;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "PERSON_ZIP")
	private String personZip;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "LEGAL_PERSON_ZIP")
	private String legalPersonZip;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "PREV_PERSON_ZIP")
	private String prevPersonZip;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "PROV_ZIP")
	private String provZip;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "PERSON_PLUS4")
	private String personPlus4;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "LEGAL_PERSON_PLUS4")
	private String legalPersonPlus4;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "PREV_PERSON_PLUS4")
	private String prevPersonPlus4;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "PROV_PLUS4")
	private String provPlus4;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "PROV_CENSUS_TRACT")
	private String provCensusTract;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "MATCH_PASS")
	private String matchPass;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "PERSON_CITY")
	private String personCity;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "LEGAL_PERSON_CITY")
	private String legalPersonCity;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "PREV_PERSON_CITY")
	private String prevPersonCity;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "PROV_CITY")
	private String provCity;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "PERSON_COUNTY")
	private String personCounty;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "COUNTY_PROV")
	private String countyProv;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "ALT_PROVIDER")
	private String altProvider;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "LNAME")
	private String lname;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "MNAME")
	private String mname;

	@Column(name = "BIRTH_COUNTRY")
	private String birthCountry;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "DRUG1")
	private String drug1;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "DRUG2")
	private String drug2;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "DRUG3")
	private String drug3;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "PAROLE_RELEASE_COUNTY")
	private String paroleReleaseCounty;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "FNAME")
	private String fname;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "PROV_PHONE")
	private String provPhone;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "ETHNICITY")
	private String ethnicity;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "PERSON_STATE")
	private String personState;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "LEGAL_PERSON_STATE")
	private String legalPersonState;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "PREV_PERSON_STATE")
	private String prevPersonState;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "PROV_ST")
	private String provSt;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "PROV_CENSUS_TRACT_SUFFIX")
	private String provCensusTractSuffix;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "RACE_CD")
	private String raceCd;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "ALIEN_STATUS")
	private String alienStatus;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "LEGAL_PERSON_COUNTY_NAME")
	private String legalPersonCountyName;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "RESCO")
	private String resco;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "PERSON_LONGITUDE")
	private String personLongitude;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "OWNING_FACILITY_LONGITUDE")
	private String owningFacilityLongitude;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "CURRENT_FACILITY_LONGITUDE")
	private String currentFacilityLongitude;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "PROV_LONG")
	private String provLong;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "STATENO")
	private String stateno;

	@ColumnTransformer(write = "TO_CHAR(FN_TO_DATE(trim(upper(?)),'MM/DD/YYYY'),'MM/DD/YYYY')")
	@Column(name = "DOB_TEXT")
	private String dobText;

	@ColumnTransformer(write = "TO_CHAR(FN_TO_DATE(trim(upper(?)),'MM/DD/YYYY'),'MM/DD/YYYY')")
	@Column(name = "ENTERED_US_DATE_TEXT")
	private String enteredUsDateText;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "PERSON_LATITUDE")
	private String personLatitude;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "OWNING_FACILITY_LATITUDE")
	private String owningFacilityLatitude;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "CURRENT_FACILITY_LATITUDE")
	private String currentFacilityLatitude;

	@ColumnTransformer(write = "TO_CHAR(FN_TO_DATE(trim(upper(?)),'MM/DD/YYYY'),'MM/DD/YYYY')")
	@Column(name = "ADMIN_DATE_TEXT")
	private String adminDateText;

	@ColumnTransformer(write = "TO_CHAR(FN_TO_DATE(trim(upper(?)),'MM/DD/YYYY'),'MM/DD/YYYY')")
	@Column(name = "LATEST_ADMIN_DATE_TEXT")
	private String latestAdminDateText;

	@ColumnTransformer(write = "TO_CHAR(FN_TO_DATE(trim(upper(?)),'MM/DD/YYYY'),'MM/DD/YYYY')")
	@Column(name = "EARLIEST_RELEASE_DATE_TEXT")
	private String earliestReleaseDateText;

	@ColumnTransformer(write = "TO_CHAR(FN_TO_DATE(trim(upper(?)),'MM/DD/YYYY'),'MM/DD/YYYY')")
	@Column(name = "LATEST_RELEASE_DATE_TEXT")
	private String latestReleaseDateText;

	@ColumnTransformer(write = "TO_CHAR(FN_TO_DATE(trim(upper(?)),'MM/DD/YYYY'),'MM/DD/YYYY')")
	@Column(name = "LATEST_DISCHARGE_DATE_TEXT")
	private String latestDischargeDateText;

	@ColumnTransformer(write = "TO_CHAR(FN_TO_DATE(trim(upper(?)),'MM/DD/YYYY'),'MM/DD/YYYY')")
	@Column(name = "DOD_TEXT")
	private String dodText;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "PROV_LAT")
	private String provLat;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "PROVIDER_TYPE")
	private String providerType;

	@ColumnTransformer(write = "TO_CHAR(FN_TO_DATE(trim(upper(?)),'MM/DD/YYYY'),'MM/DD/YYYY')")
	@Column(name = "LRELDA")
	private String lrelda;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "MATCHED_STATENO")
	private String matchedStateno;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "CURRENT_GENDER")
	private String currentGender;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "UNDER_CUSTODY")
	private String underCustody;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "UNDER_SUPERVISION")
	private String underSupervision;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "PROCESSED")
	private String processed;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "ASSESS_ASSIGN")
	private String assessAssign;

	@Column(name = "MATCH_WEIGHT")
	private BigDecimal matchWeight;

	@Column(name = "CURRENT_PROCESS_STATUS")
	private BigDecimal currentProcessStatus;

	@Column(name = "QS_PARENT_DOC_ID")
	private BigDecimal qsParentDocId;

	@Column(name = "REVIEW_DATE")
	private Timestamp reviewDate;

	@Column(name = "CREATE_DATE")
	private Timestamp createDate;

	@Column(name = "EHARS_IMPORT_DATE")
	private Timestamp eharsImportDate;

	@Column(name = "NYC_SHIP_DATE")
	private Timestamp nycShipDate;

	@Column(name = "MATCHED_PROCESS_DATE")
	private Timestamp matchedProcessDate;

	@Column(name = "REVIEW_DT")
	private Timestamp reviewDt;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "REVIEW_UID")
	private String reviewUid;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "MATCH_CD")
	private String matchCd;

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "CREATE_UID")
	private String createUid;

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

	@ColumnTransformer(write = "trim(upper(?))")
	@Column(name = "EHARS_STATUS_CD")
	private String eharsStatusCd;

	@Column(name = "OWNING_FACILITY_NAME")
	private String owningFacilityName;

	@Column(name = "OWNING_FACILITY_ADDRESS")
	private String owningFacilityAddress;

	@Column(name = "OWNING_FACILITY_CITY")
	private String owningFacilityCity;

	@Column(name = "OWNING_FACILITY_STATE")
	private String owningFacilityState;

	@Column(name = "OWNING_FACILITY_ZIP")
	private String owningFacilityZip;

	@Column(name = "OWNING_FACILITY_PLUS4")
	private String owningFacilityPlus4;

	@Column(name = "OWNING_FACILITY_COUNTY")
	private String owningFacilityCounty;

	@Column(name = "OWNING_FACILITY_PHONE")
	private String owningFacilityPhone;

	@Column(name = "CURRENT_FACILITY_NAME")
	private String currentFacilityName;

	@Column(name = "CURRENT_FACILITY_ADDRESS")
	private String currentFacilityAddress;

	@Column(name = "CURRENT_FACILITY_CITY")
	private String currentFacilityCity;

	@Column(name = "CURRENT_FACILITY_STATE")
	private String currentFacilityState;

	@Column(name = "CURRENT_FACILITY_ZIP")
	private String currentFacilityZip;

	@Column(name = "CURRENT_FACILITY_PLUS4")
	private String currentFacilityPlus4;

	@Column(name = "CURRENT_FACILITY_COUNTY")
	private String currentFacilityCounty;

	@Column(name = "CURRENT_FACILITY_PHONE")
	private String currentFacilityPhone;


	@Override
	public Long getId() {
		return documentId;
	}

	/** Always a new row (the application only inserts it). */
	@Override
	public boolean isNew() {
		return true;
	}
}
