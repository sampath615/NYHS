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
 * A TB record, standardized (table STD_TB_DOC); the columns the TB document screen reads and the import and the
 * document copy write.
 * <p>
 * Never updated: a row is inserted once ({@code TbDocRepository.saveAndFlush}, built by {@code TbDocMapping}), so it
 * is always new ({@link #isNew()}: save() inserts without a select first).
 * <b>DOCUMENT_ID as the key is assumed</b> (one row per document).
 */
@Entity
@Immutable
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "STD_TB_DOC")
public class StdTbDoc implements Persistable<Integer> {

	@Column(name = "VITAL_STATUS_AT_DIAGNOSIS")
	private String vitalStatusAtDiagnosis;

	@Column(name = "HOSP_PFI")
	private String hospPfi;

	@Column(name = "DIN")
	private String din;

	@Column(name = "PERSON_ADDRESS")
	private String personAddress;

	@Column(name = "FACILITY_ADDRESS")
	private String facilityAddress;

	@Column(name = "PROV_CITY")
	private String provCity;

	@Column(name = "PERSON_CENSUS_TRACT")
	private String personCensusTract;

	@Column(name = "FACILITY_CENSUS_TRACT")
	private String facilityCensusTract;

	@Column(name = "PROV_CENSUS_TRACT")
	private String provCensusTract;

	@Column(name = "ATYPICAL_MYCO")
	private String atypicalMyco;

	@Column(name = "DISEASE_TYPE")
	private String diseaseType;

	@Column(name = "PERSON_ZIP")
	private String personZip;

	@Column(name = "FACILITY_ZIP")
	private String facilityZip;

	@Column(name = "PROV_ZIP")
	private String provZip;

	@Column(name = "PROV_FNAME")
	private String provFname;

	@Column(name = "PROV_LNAME")
	private String provLname;

	@Column(name = "MATCH_CD")
	private String matchCd;

	@Column(name = "REVIEW_UID")
	private String reviewUid;

	@Column(name = "EHARS_STATUS_CD")
	private String eharsStatusCd;

	@Column(name = "PERSON_PLUS4")
	private String personPlus4;

	@Column(name = "FACILITY_PLUS4")
	private String facilityPlus4;

	@Column(name = "PROV_PLUS4")
	private String provPlus4;

	@Column(name = "MATCH_PASS")
	private String matchPass;

	@Column(name = "PERSON_CITY")
	private String personCity;

	@Column(name = "FACILITY_CITY")
	private String facilityCity;

	@Column(name = "HOSP_CHART")
	private String hospChart;

	@Column(name = "PERSON_COUNTY")
	private String personCounty;

	@Column(name = "FACILITY_COUNTY")
	private String facilityCounty;

	@Column(name = "PROV_COUNTY")
	private String provCounty;

	@Column(name = "LNAME")
	private String lname;

	@Column(name = "MI")
	private String mi;

	@Column(name = "PROV_ADDRESS")
	private String provAddress;

	@Column(name = "RAW_PROVIDER_ADDRESS")
	private String rawProviderAddress;

	@Column(name = "FNAME")
	private String fname;

	@Column(name = "PERSON_PHONE")
	private String personPhone;

	@Column(name = "FACILITY_PHONE")
	private String facilityPhone;

	@Column(name = "CASE_STATUS")
	private String caseStatus;

	@Column(name = "ETHNICITY")
	private String ethnicity;

	@Column(name = "PERSON_STATE")
	private String personState;

	@Column(name = "FACILITY_STATE")
	private String facilityState;

	@Column(name = "PROV_STATE")
	private String provState;

	@Column(name = "RACE_CD")
	private String raceCd;

	@Column(name = "RACE_ASIAN_CD")
	private String raceAsianCd;

	@Column(name = "RACE_POLY_CD")
	private String racePolyCd;

	@Column(name = "PROV_PHONE")
	private String provPhone;

	@Column(name = "FACILITY_NAME")
	private String facilityName;

	@Column(name = "PERSON_LONGITUDE")
	private String personLongitude;

	@Column(name = "FACILITY_LONGITUDE")
	private String facilityLongitude;

	@Column(name = "PROV_LONGITUDE")
	private String provLongitude;

	@Column(name = "DOB_TEXT")
	private String dobText;

	@Column(name = "PERSON_LATITUDE")
	private String personLatitude;

	@Column(name = "FACILITY_LATITUDE")
	private String facilityLatitude;

	@Column(name = "MATCHED_STATENO")
	private String matchedStateno;

	@Column(name = "CREATE_UID")
	private String createUid;

	@Column(name = "DOD_TEXT")
	private String dodText;

	@Column(name = "TB_FILE")
	private String tbFile;

	@Column(name = "PROV_LATITUDE")
	private String provLatitude;

	@Column(name = "BIRTH_SEX")
	private String birthSex;

	@Column(name = "RACE_ASIAN")
	private String raceAsian;

	@Column(name = "RACE_BLACK")
	private String raceBlack;

	@Column(name = "RACE_INDIAN")
	private String raceIndian;

	@Column(name = "RACE_OTHER")
	private String raceOther;

	@Column(name = "RACE_PACIFIC")
	private String racePacific;

	@Column(name = "RACE_UNKNOWN")
	private String raceUnknown;

	@Column(name = "RACE_WHITE")
	private String raceWhite;

	@Column(name = "HIV_IND")
	private String hivInd;

	@Column(name = "MATCH_WEIGHT")
	private BigDecimal matchWeight;

	@Id
	@Column(name = "DOCUMENT_ID")
	private Integer documentId;

	@Column(name = "SERIALNO")
	private Long serialno;

	@Column(name = "CURRENT_PROCESS_STATUS")
	private Integer currentProcessStatus;

	@Column(name = "QS_PARENT_DOC_ID")
	private Long qsParentDocId;

	@Column(name = "RECEIVED_DATE")
	private Timestamp receivedDate;

	@Column(name = "REVIEW_DATE")
	private Timestamp reviewDate;

	@Column(name = "EHARS_IMPORT_DATE")
	private Timestamp eharsImportDate;

	@Column(name = "DX_DATE")
	private Timestamp dxDate;

	@Column(name = "CREATE_DATE")
	private Timestamp createDate;

	@Column(name = "NYC_SHIP_DATE")
	private Timestamp nycShipDate;

	@Column(name = "MATCHED_PROCESS_DATE")
	private Timestamp matchedProcessDate;

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

	@Column(name = "MNAME")
	private String mname;

	@Column(name = "SUFFIX")
	private String suffix;

	@Column(name = "TB_SRC_ID")
	private String tbSrcId;

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
