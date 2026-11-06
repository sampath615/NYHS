package gov.ny.state.doh.bhae.nyehms.web.spring.entity.document;

import java.math.BigDecimal;
import java.sql.Date;
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
 * An AIRS (AIDS Institute Reporting System) report, standardized (table STD_AIRS_DOC): the columns the AIRS document
 * screen reads and the columns the AIRS import writes (AIRSDocumentDAO.writeRawAIRSRecordDataToStdTable).
 * <p>
 * Inserted, never changed. <b>DOCUMENT_ID as the key is assumed</b> (one row per document). An insert keeps the
 * conversions of the former insert in the database (the write transformers: UPPER/TRIM, FN_TO_DATE and
 * TO_CHAR(FN_TO_DATE(...)) of the received texts). DOB, PT_INTERVIEW_DATE and FIRST_POS_HIV_DATE are read as dates and
 * written from the received texts (FN_TO_DATE); AIRS_CALC_FILE_DATE is read as a date only and written with its time;
 * the read attributes of those columns are not written. The columns the import does not write are not inserted.
 */
@Entity
@Immutable
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "STD_AIRS_DOC")
public class StdAirsDoc implements Persistable<Integer> {

	@Id
	@Column(name = "DOCUMENT_ID")
	private Integer documentId;

	@Column(name = "EHARS_IMPORT_DATE", insertable = false)
	private Date eharsImportDate;

	@Column(name = "NYC_SHIP_DATE", insertable = false)
	private Date nycShipDate;

	@ColumnTransformer(write = "upper(trim(?))")
	@Column(name = "FNAME")
	private String fname;

	@ColumnTransformer(write = "upper(trim(?))")
	@Column(name = "MNAME")
	private String mname;

	@ColumnTransformer(write = "upper(trim(?))")
	@Column(name = "LNAME")
	private String lname;

	@ColumnTransformer(write = "upper(trim(?))")
	@Column(name = "ALT_NAME")
	private String altName;

	@Column(name = "DOB", insertable = false, updatable = false)
	private Date dob;

	@ColumnTransformer(write = "trim(?)")
	@Column(name = "SSN")
	private String ssn;

	@Column(name = "AGE")
	private String age;

	@ColumnTransformer(write = "upper(trim(?))")
	@Column(name = "BIRTH_COUNTRY")
	private String birthCountry;

	@ColumnTransformer(write = "upper(trim(?))")
	@Column(name = "BIRTH_COUNTRY_OTHER")
	private String birthCountryOther;

	@ColumnTransformer(write = "TO_CHAR(FN_TO_DATE(trim(upper(?)),'MM/DD/YYYY'),'MM/DD/YYYY')")
	@Column(name = "DOD_TEXT")
	private String dodText;

	@ColumnTransformer(write = "upper(trim(?))")
	@Column(name = "PERSON_CITY")
	private String personCity;

	@ColumnTransformer(write = "upper(trim(?))")
	@Column(name = "PERSON_STATE")
	private String personState;

	@ColumnTransformer(write = "trim(?)")
	@Column(name = "PERSON_ZIP")
	private String personZip;

	@Column(name = "PERSON_COUNTY", insertable = false)
	private String personCounty;

	@ColumnTransformer(write = "upper(trim(?))")
	@Column(name = "PERSON_ADDRESS")
	private String personAddress;

	@ColumnTransformer(write = "trim(?)")
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

	@ColumnTransformer(write = "upper(trim(?))")
	@Column(name = "BIRTH_SEX")
	private String birthSex;

	@ColumnTransformer(write = "upper(trim(?))")
	@Column(name = "CURRENT_GENDER")
	private String currentGender;

	@Column(name = "SWM")
	private String swm;

	@Column(name = "SWF")
	private String swf;

	@Column(name = "IDU")
	private String idu;

	@Column(name = "SEX_PARTNER_IDU")
	private String sexPartnerIdu;

	@Column(name = "RECV_BLOOD_PROD")
	private String recvBloodProd;

	@Column(name = "SEX_PARTNER_HIV")
	private String sexPartnerHiv;

	@Column(name = "PERINATAL")
	private String perinatal;

	@Column(name = "RISK_OTHER")
	private String riskOther;

	@Column(name = "RISK_UNKNOWN")
	private String riskUnknown;

	@ColumnTransformer(write = "upper(trim(?))")
	@Column(name = "TC_ID")
	private String tcId;

	@ColumnTransformer(write = "upper(trim(?))")
	@Column(name = "URN")
	private String urn;

	@ColumnTransformer(write = "upper(trim(?))")
	@Column(name = "MEDICAID_NUMBER")
	private String medicaidNumber;

	@ColumnTransformer(write = "upper(trim(?))")
	@Column(name = "CASE_NO")
	private String caseNo;

	@ColumnTransformer(write = "upper(trim(?))")
	@Column(name = "MED_RECORD_NUMBER")
	private String medRecordNumber;

	@ColumnTransformer(write = "upper(trim(?))")
	@Column(name = "FED_ID")
	private String fedId;

	@ColumnTransformer(write = "upper(trim(?))")
	@Column(name = "DUNS_ID")
	private String dunsId;

	@ColumnTransformer(write = "TO_CHAR(FN_TO_DATE(trim(upper(?)),'MM/DD/YYYY'),'MM/DD/YYYY')")
	@Column(name = "ADMISSION_DATE_TEXT")
	private String admissionDateText;

	@ColumnTransformer(write = "TO_CHAR(FN_TO_DATE(trim(upper(?)),'MM/DD/YYYY'),'MM/DD/YYYY')")
	@Column(name = "RECV_BLOOD_PROD_DATE_TEXT")
	private String recvBloodProdDateText;

	@Column(name = "INIT_HIV_DIAG")
	private String initHivDiag;

	@ColumnTransformer(write = "TO_CHAR(FN_TO_DATE(trim(upper(?)),'MM/DD/YYYY'),'MM/DD/YYYY')")
	@Column(name = "INIT_HIV_DIAG_DATE_TEXT")
	private String initHivDiagDateText;

	@Column(name = "INFORMED_HIV_POS")
	private String informedHivPos;

	@ColumnTransformer(write = "TO_CHAR(FN_TO_DATE(trim(upper(?)),'MM/DD/YYYY'),'MM/DD/YYYY')")
	@Column(name = "INFORMED_HIV_DATE_TEXT")
	private String informedHivDateText;

	@Column(name = "PREV_HIV_DIAG")
	private String prevHivDiag;

	@ColumnTransformer(write = "TO_CHAR(FN_TO_DATE(trim(upper(?)),'MM/DD/YYYY'),'MM/DD/YYYY')")
	@Column(name = "PREV_HIV_DIAG_DATE_TEXT")
	private String prevHivDiagDateText;

	@Column(name = "INIT_AIDS_DIAG")
	private String initAidsDiag;

	@ColumnTransformer(write = "TO_CHAR(FN_TO_DATE(trim(upper(?)),'MM/DD/YYYY'),'MM/DD/YYYY')")
	@Column(name = "INIT_AIDS_DIAG_DATE_TEXT")
	private String initAidsDiagDateText;

	@Column(name = "PREV_AIDS_DIAG")
	private String prevAidsDiag;

	@ColumnTransformer(write = "TO_CHAR(FN_TO_DATE(trim(upper(?)),'MM/DD/YYYY'),'MM/DD/YYYY')")
	@Column(name = "PREV_AIDS_DIAG_DATE_TEXT")
	private String prevAidsDiagDateText;

	@Column(name = "PT_INTERVIEW_DATE", insertable = false, updatable = false)
	private Date ptInterviewDate;

	@Column(name = "FIRST_POS_HIV_DATE", insertable = false, updatable = false)
	private Date firstPosHivDate;

	@ColumnTransformer(write = "upper(trim(?))")
	@Column(name = "ARV_6_MO")
	private String arv6Mo;

	@ColumnTransformer(write = "upper(trim(?))")
	@Column(name = "ARV_6_MO_NAME")
	private String arv6MoName;

	@ColumnTransformer(write = "TO_CHAR(FN_TO_DATE(trim(upper(?)),'MM/DD/YYYY'),'MM/DD/YYYY')")
	@Column(name = "ARV_6_MO_START_DATE_TEXT")
	private String arv6MoStartDateText;

	@ColumnTransformer(write = "TO_CHAR(FN_TO_DATE(trim(upper(?)),'MM/DD/YYYY'),'MM/DD/YYYY')")
	@Column(name = "ARV_6_MO_END_DATE_TEXT")
	private String arv6MoEndDateText;

	@ColumnTransformer(write = "upper(trim(?))")
	@Column(name = "ARV_CURRENT_TEST")
	private String arvCurrentTest;

	@ColumnTransformer(write = "TO_CHAR(FN_TO_DATE(trim(upper(?)),'MM/DD/YYYY'),'MM/DD/YYYY')")
	@Column(name = "FIRST_HIV_TEST_EVER_DATE_TEXT")
	private String firstHivTestEverDateText;

	@ColumnTransformer(write = "trim(?)")
	@Column(name = "NUMBER_TEST_2_YRS")
	private String numberTest2Yrs;

	@ColumnTransformer(write = "upper(trim(?))")
	@Column(name = "EVER_TEST_NEG")
	private String everTestNeg;

	@ColumnTransformer(write = "TO_CHAR(FN_TO_DATE(trim(upper(?)),'MM/DD/YYYY'),'MM/DD/YYYY')")
	@Column(name = "LAST_NEG_DATE_TEXT")
	private String lastNegDateText;

	@ColumnTransformer(write = "upper(trim(?))")
	@Column(name = "FACILITY_NAME")
	private String facilityName;

	@ColumnTransformer(write = "upper(trim(?))")
	@Column(name = "PROVIDER_DESCRIPTION")
	private String providerDescription;

	@ColumnTransformer(write = "upper(trim(?))")
	@Column(name = "AGENCY_ID")
	private String agencyId;

	@ColumnTransformer(write = "upper(trim(?))")
	@Column(name = "FACILITY_CITY")
	private String facilityCity;

	@ColumnTransformer(write = "upper(trim(?))")
	@Column(name = "FACILITY_STATE")
	private String facilityState;

	@ColumnTransformer(write = "trim(?)")
	@Column(name = "FACILITY_ZIP")
	private String facilityZip;

	@Column(name = "FACILITY_COUNTY", insertable = false)
	private String facilityCounty;

	@ColumnTransformer(write = "upper(trim(?))")
	@Column(name = "FACILITY_ADDRESS")
	private String facilityAddress;

	@ColumnTransformer(write = "trim(?)")
	@Column(name = "FACILITY_PHONE")
	private String facilityPhone;

	@Column(name = "FACILITY_PLUS4", insertable = false)
	private String facilityPlus4;

	@Column(name = "NUM_PARTNERS")
	private String numPartners;

	@ColumnTransformer(write = "TO_CHAR(FN_TO_DATE(trim(upper(?)),'MM/DD/YYYY'),'MM/DD/YYYY')")
	@Column(name = "SIG_DATE_TEXT")
	private String sigDateText;

	@ColumnTransformer(write = "upper(trim(?))")
	@Column(name = "ARV_6_MO_NAME2")
	private String arv6MoName2;

	@ColumnTransformer(write = "upper(trim(?))")
	@Column(name = "ARV_6_MO_NAME3")
	private String arv6MoName3;

	@ColumnTransformer(write = "upper(trim(?))")
	@Column(name = "ARV_6_MO_NAME4")
	private String arv6MoName4;

	@ColumnTransformer(write = "upper(trim(?))")
	@Column(name = "IS_MEDICAID")
	private String isMedicaid;

	@Column(name = "AIRS_CALC_FILE_DATE", insertable = false, updatable = false)
	private Date airsCalcFileDate;

	@ColumnTransformer(write = "upper(trim(?))")
	@Column(name = "RECORD_TYPE")
	private String recordType;

	@ColumnTransformer(write = "upper(trim(?))")
	@Column(name = "CTR_FLAG")
	private String ctrFlag;

	@ColumnTransformer(write = "trim(?)")
	@Column(name = "EXTRALOGID")
	private String extralogid;

	@ColumnTransformer(write = "trim(?)")
	@Column(name = "AIRS_VERSION")
	private String airsVersion;

	@Column(name = "AIRS_SRC_ID")
	private BigDecimal airsSrcId;

	@Column(name = "AIRS_ID")
	private BigDecimal airsId;

	/** DOB as the import writes it (the read attribute above is not written). */
	@ColumnTransformer(write = "FN_TO_DATE(?,'MM/DD/YYYY')")
	@Column(name = "DOB", updatable = false)
	private String dobWritten;

	/** PT_INTERVIEW_DATE as the import writes it (the read attribute above is not written). */
	@ColumnTransformer(write = "FN_TO_DATE(?,'MM/DD/YYYY')")
	@Column(name = "PT_INTERVIEW_DATE", updatable = false)
	private String ptInterviewDateWritten;

	/** FIRST_POS_HIV_DATE as the import writes it (the read attribute above is not written). */
	@ColumnTransformer(write = "FN_TO_DATE(?,'MM/DD/YYYY')")
	@Column(name = "FIRST_POS_HIV_DATE", updatable = false)
	private String firstPosHivDateWritten;

	@ColumnTransformer(write = "upper(trim(?))")
	@Column(name = "ARV1_CODE")
	private String arv1Code;

	@ColumnTransformer(write = "upper(trim(?))")
	@Column(name = "ARV2_CODE")
	private String arv2Code;

	@ColumnTransformer(write = "upper(trim(?))")
	@Column(name = "ARV3_CODE")
	private String arv3Code;

	@ColumnTransformer(write = "FN_TO_DATE(?,'MM/DD/YYYY')")
	@Column(name = "FILE_CREATED_DATE")
	private String fileCreatedDate;

	@ColumnTransformer(write = "FN_TO_DATE(?,'MM/DD/YYYY')")
	@Column(name = "PAT_REC_MOD_DATE")
	private String patRecModDate;

	@ColumnTransformer(write = "upper(trim(?))")
	@Column(name = "EUCI")
	private String euci;

	@ColumnTransformer(write = "upper(trim(?))")
	@Column(name = "PROVIDER_CD")
	private String providerCd;

	@Column(name = "CURRENT_PROCESS_STATUS")
	private BigDecimal currentProcessStatus;

	@ColumnTransformer(write = "upper(trim(?))")
	@Column(name = "CREATE_UID")
	private String createUid;

	@Column(name = "CREATE_DATE")
	private Timestamp createDate;

	/** AIRS_CALC_FILE_DATE as the import writes it (the read attribute above is not written). */
	@Column(name = "AIRS_CALC_FILE_DATE", updatable = false)
	private Timestamp airsCalcFileDateTime;

	@ColumnTransformer(write = "upper(trim(?))")
	@Column(name = "CTR_VERSION")
	private String ctrVersion;

	@ColumnTransformer(write = "upper(trim(?))")
	@Column(name = "CTR_FORMID")
	private String ctrFormid;

	@Column(name = "CTR_PRELIMINARY_POSITIVE")
	private String ctrPreliminaryPositive;

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
