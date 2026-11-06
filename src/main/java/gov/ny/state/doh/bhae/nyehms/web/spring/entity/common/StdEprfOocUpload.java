package gov.ny.state.doh.bhae.nyehms.web.spring.entity.common;

import java.math.BigDecimal;
import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

/**
 * A standardized provider portal (ePRF out-of-care / DNLTC / SNP) upload record (table STD_EPRF_OOC_UPLOAD); the columns
 * the EPRF OOC document screen, the file review lists and the follow-up review read and write (the insert of the import
 * stays native SQL, see EPRFOOChDocumentDAO.writeStdDocData).
 * <p>
 * Read through JPQL projections and changed by JPQL bulk updates only, so a key that repeats does not matter. <b>DOCUMENT_ID
 * as the key is assumed.</b> Attribute types follow the former VOs (a text property was read with getString, an int
 * with getInt): text and codes are String, the counts and statuses Integer, the dates Timestamp.
 * LAST_CD4_RESULT_COUNT / LAST_CD4_RESULT_PERCENT are mapped twice, as numbers (the document screen's Integer
 * properties) and as text (the follow-up VO's String properties). <b>CARE_STATUS and INTERNAL_CONCLUSION are assumed to
 * be numbers</b> (they are joined with the NUMBER ids of CODED_CONCLUSION_SHARED_PROV / CODED_PP_INQURIES_INT_CONCL and
 * compared with 8); MONTHS_SINCE_LAST_HIV_LAB is text (read with getString, written as TRIM(UPPER(text))).
 */
@Entity
@Getter
@Table(name = "STD_EPRF_OOC_UPLOAD")
public class StdEprfOocUpload {

	@Id
	@Column(name = "DOCUMENT_ID")
	private Integer documentId;

	@Column(name = "PREF_NAME")
	private String prefName;

	@Column(name = "BIRTH_SEX")
	private String birthSex;

	@Column(name = "CURRENT_GENDER")
	private String currentGender;

	@Column(name = "CURRENT_PROCESS_STATUS")
	private Integer currentProcessStatus;

	@Column(name = "DIN")
	private String din;

	@Column(name = "DOB")
	private String dob;

	@Column(name = "EHARS_IMPORT_DATE")
	private Timestamp eharsImportDate;

	@Column(name = "ETHNICITY")
	private String ethnicity;

	@Column(name = "FACILITY_ADDRESS")
	private String facilityAddress;

	@Column(name = "FACILITY_CENSUS_TRACT")
	private String facilityCensusTract;

	@Column(name = "FACILITY_CITY")
	private String facilityCity;

	@Column(name = "FACILITY_COUNTY")
	private String facilityCounty;

	@Column(name = "FACILITY_LATITUDE")
	private String facilityLatitude;

	@Column(name = "FACILITY_LONGITUDE")
	private String facilityLongitude;

	@Column(name = "FACILITY_NAME")
	private String facilityName;

	@Column(name = "FACILITY_NPI")
	private String facilityNpi;

	@Column(name = "FACILITY_PHONE")
	private String facilityPhone;

	@Column(name = "FACILITY_STATE")
	private String facilityState;

	@Column(name = "FACILITY_ZIP")
	private String facilityZip;

	@Column(name = "FILE_TYPE_CD")
	private String fileTypeCd;

	@Column(name = "FNAME")
	private String fname;

	@Column(name = "LAST_CD4_DATE")
	private Timestamp lastCd4Date;

	@Column(name = "LAST_CD4_RESULT_COUNT")
	private Integer lastCd4ResultCount;

	/** LAST_CD4_RESULT_COUNT read as text (getString). */
	@Column(name = "LAST_CD4_RESULT_COUNT", insertable = false, updatable = false)
	private String lastCd4ResultCountText;

	@Column(name = "LAST_CD4_RESULT_PERCENT")
	private Integer lastCd4ResultPercent;

	/** LAST_CD4_RESULT_PERCENT read as text (getString). */
	@Column(name = "LAST_CD4_RESULT_PERCENT", insertable = false, updatable = false)
	private String lastCd4ResultPercentText;

	@Column(name = "LAST_VIRAL_LOAD_DATE")
	private Timestamp lastViralLoadDate;

	@Column(name = "LAST_VIRAL_LOAD_RESULT")
	private String lastViralLoadResult;

	@Column(name = "LNAME")
	private String lname;

	@Column(name = "MATCH_CD")
	private String matchCd;

	@Column(name = "MATCH_PASS")
	private Integer matchPass;

	@Column(name = "MATCH_WEIGHT")
	private Integer matchWeight;

	@Column(name = "MATCHED_PROCESS_DATE")
	private Timestamp matchedProcessDate;

	@Column(name = "MATCHED_STATENO")
	private String matchedStateno;

	@Column(name = "MED_RECORD_NUMBER")
	private String medRecordNumber;

	@Column(name = "MEDICAID_NUMBER")
	private String medicaidNumber;

	@Column(name = "MNAME")
	private String mname;

	@Column(name = "NYC_SHIP_DATE")
	private Timestamp nycShipDate;

	@Column(name = "NYSID")
	private String nysid;

	@Column(name = "PERSON_ADDRESS")
	private String personAddress;

	@Column(name = "PERSON_CENSUS_TRACT")
	private String personCensusTract;

	@Column(name = "PERSON_CITY")
	private String personCity;

	@Column(name = "PERSON_COUNTY")
	private String personCounty;

	@Column(name = "PERSON_LATITUDE")
	private String personLatitude;

	@Column(name = "PERSON_LONGITUDE")
	private String personLongitude;

	@Column(name = "PERSON_STATE")
	private String personState;

	@Column(name = "PERSON_PHONE")
	private String personPhone;

	@Column(name = "PERSON_ZIP")
	private String personZip;

	@Column(name = "PROV_ADDRESS")
	private String provAddress;

	@Column(name = "PROV_CENSUS_TRACT")
	private String provCensusTract;

	@Column(name = "PROV_CITY")
	private String provCity;

	@Column(name = "PROV_COUNTY")
	private String provCounty;

	@Column(name = "PROV_FNAME")
	private String provFname;

	@Column(name = "PROV_LAST_VISIT_DATE")
	private Timestamp provLastVisitDate;

	@Column(name = "PROV_LATITUDE")
	private String provLatitude;

	@Column(name = "PROV_LICENSE")
	private String provLicense;

	@Column(name = "PROV_LNAME")
	private String provLname;

	@Column(name = "PROV_LONGITUDE")
	private String provLongitude;

	@Column(name = "PROV_NPI")
	private String provNpi;

	@Column(name = "PROV_PHONE")
	private String provPhone;

	@Column(name = "PROV_STATE")
	private String provState;

	@Column(name = "PROV_ZIP")
	private String provZip;

	@Column(name = "QS_PARENT_DOC_ID")
	private Integer qsParentDocId;

	@Column(name = "RACE_ASIAN")
	private String raceAsian;

	@Column(name = "RACE_BLACK")
	private String raceBlack;

	@Column(name = "RACE_CD")
	private String raceCd;

	@Column(name = "RACE_INDIAN")
	private String raceIndian;

	@Column(name = "RACE_MULTIPLE")
	private String raceMultiple;

	@Column(name = "RACE_OTHER")
	private String raceOther;

	@Column(name = "RACE_PACIFIC")
	private String racePacific;

	@Column(name = "RACE_UNKNOWN")
	private String raceUnknown;

	@Column(name = "RACE_WHITE")
	private String raceWhite;

	@Column(name = "REVIEW_DT")
	private Timestamp reviewDt;

	@Column(name = "REVIEW_UID")
	private String reviewUid;

	@Column(name = "SSN")
	private String ssn;

	@Column(name = "SUFFIX")
	private String suffix;

	@Column(name = "UPLOAD_DT")
	private Timestamp uploadDt;

	@Column(name = "UPLOAD_FILENAME")
	private String uploadFilename;

	@Column(name = "UPLOAD_ID")
	private String uploadId;

	@Column(name = "FLUX_MATCH_CD")
	private String fluxMatchCd;

	@Column(name = "COMPLETE_DATE")
	private Timestamp completeDate;

	@Column(name = "CONFIRMATORY_RESULT_REPORTED")
	private String confirmatoryResultReported;

	@Column(name = "SUBMITTING_PROVIDER_PRESENT")
	private String submittingProviderPresent;

	@Column(name = "REVIEW_COMMENT")
	private String reviewComment;

	@Column(name = "INTERNAL_CONCLUSION")
	private BigDecimal internalConclusion;

	@Column(name = "CARE_STATUS")
	private BigDecimal careStatus;

	@Column(name = "NYS_EHARS_STATENO")
	private String nysEharsStateno;

	@Column(name = "NYC_EHARS_STATENO")
	private String nycEharsStateno;

	@Column(name = "NYC_CITY_NO")
	private String nycCityNo;

	@Column(name = "LAST_HIV_LAB_TYPE")
	private String lastHivLabType;

	@Column(name = "LAST_HIV_LAB_DATE")
	private Timestamp lastHivLabDate;

	@Column(name = "LAST_HIV_LAB_RESULT")
	private String lastHivLabResult;

	@Column(name = "MONTHS_SINCE_LAST_HIV_LAB")
	private String monthsSinceLastHivLab;

	@Column(name = "NYS_VITAL_STATUS")
	private String nysVitalStatus;

	@Column(name = "NYC_VITAL_STATUS")
	private String nycVitalStatus;

	@Column(name = "MEETS_CASE_DEFINITION_IN")
	private String meetsCaseDefinitionIn;

	@Column(name = "MEETS_CDC_ELIG_ID")
	private String meetsCdcEligId;
}
