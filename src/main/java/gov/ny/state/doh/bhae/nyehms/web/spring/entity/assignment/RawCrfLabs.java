package gov.ny.state.doh.bhae.nyehms.web.spring.entity.assignment;

import java.sql.Timestamp;

import org.hibernate.annotations.ColumnTransformer;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * A lab test entered on a case report form: the columns RAW_ACRF_LABS and RAW_PCRF_LABS share (the test and result code
 * columns have the form's prefix, see the two entities).
 * <p>
 * LAB_SRC_ID is filled by the database on insert (the former insert read it back as a generated key), so it is an
 * identity here. The attribute types follow the VO the rows were read into and written from (RawAcrfLabsVO).
 * UPDATED_DATE is the database's SYSDATE on insert.
 */
@MappedSuperclass
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class RawCrfLabs {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "LAB_SRC_ID")
	private Long labSrcId;

	@Column(name = "DOCUMENT_ID")
	private Long documentId;

	@Column(name = "PARENT_TEST_TYPE")
	private String parentTestType;

	/** ACRF_TEST_CODE / PCRF_TEST_CODE */
	@Column(name = "TEST_CODE")
	private String testCode;

	/** ACRF_RESULT_CODE / PCRF_RESULT_CODE */
	@Column(name = "RESULT_CODE")
	private String resultCode;

	@Column(name = "COLLECTION_DATE")
	private String collectionDate;

	@Column(name = "RAPID_TEST")
	private Boolean rapidTest;

	@Column(name = "COPIES_ML")
	private String copiesMl;

	@Column(name = "LOG")
	private String log;

	@Column(name = "OTHER_TEST")
	private String otherTest;

	@Column(name = "MANUFACTURER")
	private String manufacturer;

	@Column(name = "FACILITY_NAME")
	private String facilityName;

	@Column(name = "FACILITY_EHARS_UID")
	private String facilityEharsUid;

	@Column(name = "LAB_NAME")
	private String labName;

	@Column(name = "CLIA")
	private String clia;

	@Column(name = "PROV_NAME")
	private String provName;

	@Column(name = "PROV_FNAME")
	private String provFname;

	@Column(name = "PROV_LNAME")
	private String provLname;

	@Column(name = "PROV_MNAME")
	private String provMname;

	@Column(name = "PROV_SUFFIX")
	private String provSuffix;

	@Column(name = "INDEX_VALUE")
	private String indexValue;

	@Column(name = "HIV1_CML_ANALYTE_RESULT")
	private String hiv1CmlAnalyteResult;

	@Column(name = "HIV1_LOG_ANALYTE_RESULT")
	private String hiv1LogAnalyteResult;

	@Column(name = "HIV12_AB_ANALYTE_RESULT")
	private String hiv12AbAnalyteResult;

	@Column(name = "HIV1_AB_ANALYTE_RESULT")
	private String hiv1AbAnalyteResult;

	@Column(name = "HIV1_AB_ANALYTE_RESULT_INDEX")
	private String hiv1AbAnalyteResultIndex;

	@Column(name = "HIV2_AB_ANALYTE_RESULT")
	private String hiv2AbAnalyteResult;

	@Column(name = "HIV2_AB_ANALYTE_RESULT_INDEX")
	private String hiv2AbAnalyteResultIndex;

	@Column(name = "HIV1_AG_ANALYTE_RESULT")
	private String hiv1AgAnalyteResult;

	@Column(name = "HIV1_AG_ANALYTE_RESULT_INDEX")
	private String hiv1AgAnalyteResultIndex;

	@Column(name = "DIAGNOSTIC_ALGORITHM_STEP")
	private String diagnosticAlgorithmStep;

	@Column(name = "RAPID_TEST_TYPE")
	private String rapidTestType;

	@Column(name = "INTERPRETATION")
	private String interpretation;

	@Column(name = "ANALYTE_INTERPRETATION")
	private String analyteInterpretation;

	@Column(name = "UPDATED_BY")
	private String updatedBy;

	@ColumnTransformer(write = "coalesce(?, SYSDATE)")
	@Column(name = "UPDATED_DATE")
	private Timestamp updatedDate;
}
