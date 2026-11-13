package gov.ny.state.doh.bhae.nyehms.web.spring.entity.common;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * The cluster person staging records of the SAS database owner (table NYEHMS_SASDB_OWNER.ER_PERSON_CLUSTERS_STAGE):
 * a document's match (code, potential and matched state numbers, clusters), its file and review; the columns the
 * dataflux clerical review reads and changes (ClericalReviewDAO) and the (unused) discard of the cluster partner
 * review changes.
 * <p>
 * <b>DOCUMENT_ID as the key is assumed.</b> Read through projections and changed by JPQL bulk updates only.
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "ER_PERSON_CLUSTERS_STAGE", schema = "NYEHMS_SASDB_OWNER")
public class ErPersonClustersStage {

	@Id
	@Column(name = "DOCUMENT_ID")
	private Long documentId;

	@Column(name = "CURRENT_PROCESS_STATUS")
	private Integer currentProcessStatus;

	@Column(name = "REVIEW_UID")
	private Long reviewUid;

	@Column(name = "REVIEW_DATE")
	private Timestamp reviewDate;

	@Column(name = "MATCH_CD")
	private String matchCd;

	@Column(name = "POT_STATENO")
	private String potStateno;

	@Column(name = "MATCHED_STATENO")
	private String matchedStateno;

	@Column(name = "DOC_TYPE")
	private String docType;

	@Column(name = "FILE_NAME")
	private String fileName;

	@Column(name = "FILE_TYPE_CD")
	private String fileTypeCd;

	@Column(name = "FUZZY_CLUSTER_ID")
	private Long fuzzyClusterId;

	@Column(name = "CLUSTER_ID")
	private Long clusterId;

	@Column(name = "VALID_PARENT")
	private String validParent;

	// the person columns the clerical review shows as a person (CommonDocLabsDAO.getClericalDocumentAsPerson)

	@Column(name = "PERSON_FIRST_NAME")
	private String personFirstName;

	@Column(name = "PERSON_MIDDLE_NAME")
	private String personMiddleName;

	@Column(name = "PERSON_LAST_NAME")
	private String personLastName;

	@Column(name = "PERSON_SUFFIX")
	private String personSuffix;

	@Column(name = "GENDER_CODE")
	private String genderCode;

	@Column(name = "BIRTH_SEX")
	private String birthSex;

	@Column(name = "DOD_TEXT")
	private String dodText;

	@Column(name = "SSN")
	private String ssn;

	@Column(name = "NYSID")
	private String nysid;

	@Column(name = "DIN")
	private String din;

	@Column(name = "MED_RECORD_NUMBER")
	private String medRecordNumber;

	@Column(name = "MEDICAID_NUMBER")
	private String medicaidNumber;

	@Column(name = "INSURANCE_NUMBER")
	private String insuranceNumber;

	@Column(name = "PERSON_DOB_TEXT")
	private String personDobText;

	@Column(name = "BIRTH_COUNTRY")
	private String birthCountry;

	@Column(name = "ETHNICITY_CODE")
	private String ethnicityCode;

	@Column(name = "PERSON_ADDRESS")
	private String personAddress;

	@Column(name = "PERSON_STATE")
	private String personState;

	@Column(name = "PERSON_CITY")
	private String personCity;

	@Column(name = "PERSON_COUNTY")
	private String personCounty;

	@Column(name = "PERSON_ZIP")
	private String personZip;

	@Column(name = "PLUS4")
	private String plus4;

	@Column(name = "PERSON_PHONE")
	private String personPhone;

	@Column(name = "PERSON_WORK_PHONE")
	private String personWorkPhone;
}
