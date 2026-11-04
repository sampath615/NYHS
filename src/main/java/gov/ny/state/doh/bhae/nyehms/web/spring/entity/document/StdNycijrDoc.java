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
 * An NYC IJR record, standardized (table STD_NYCIJR_DOC); the columns the NYC IJR document screen reads and the
 * import writes.
 * <p>
 * Never updated: a row is inserted once by the import ({@code NycIjrDocRepository.saveAndFlush}, built by
 * {@code NycIjrStdDocMapping}), so it is always new ({@link #isNew()}: save() inserts without a select first).
 * <b>DOCUMENT_ID as the key is assumed</b> (one row per document).
 */
@Entity
@Immutable
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "STD_NYCIJR_DOC")
public class StdNycijrDoc implements Persistable<Integer> {

	@Column(name = "REVIEW_UID")
	private String reviewUid;

	@Column(name = "DIN")
	private String din;

	@Column(name = "ALT_LNAME")
	private String altLname;

	@Column(name = "DX_PERSON_ADDRESS")
	private String dxPersonAddress;

	@Column(name = "CUR_PERSON_ADDRESS")
	private String curPersonAddress;

	@Column(name = "PROV_ADDRESS")
	private String provAddress;

	@Column(name = "PROV_NAME")
	private String provName;

	@Column(name = "PROV_DEPARTMENT")
	private String provDepartment;

	@Column(name = "PROV_SIGNED")
	private String provSigned;

	@Column(name = "COMPLETER_FNAME")
	private String completerFname;

	@Column(name = "COMPLETER_MI")
	private String completerMi;

	@Column(name = "COMPLETER_LNAME")
	private String completerLname;

	@Column(name = "COMPLETER_TITLE")
	private String completerTitle;

	@Column(name = "COMPLETER_EMAIL")
	private String completerEmail;

	@Column(name = "CONTACT_LNAME")
	private String contactLname;

	@Column(name = "CONTACT_FNAME")
	private String contactFname;

	@Column(name = "CONTACT_MNAME")
	private String contactMname;

	@Column(name = "CONTACT_TITLE")
	private String contactTitle;

	@Column(name = "DX_PERSON_CENSUS_TRACT")
	private String dxPersonCensusTract;

	@Column(name = "CUR_PERSON_CENSUS_TRACT")
	private String curPersonCensusTract;

	@Column(name = "PROV_CENSUS_TRACT")
	private String provCensusTract;

	@Column(name = "DX_PERSON_ZIP")
	private String dxPersonZip;

	@Column(name = "CUR_PERSON_ZIP")
	private String curPersonZip;

	@Column(name = "PROV_ZIP")
	private String provZip;

	@Column(name = "DX_PERSON_CITY")
	private String dxPersonCity;

	@Column(name = "CUR_PERSON_CITY")
	private String curPersonCity;

	@Column(name = "PROV_CITY")
	private String provCity;

	@Column(name = "HIV_FACILITY_NAME")
	private String hivFacilityName;

	@Column(name = "DX_PERSON_COUNTY")
	private String dxPersonCounty;

	@Column(name = "CUR_PERSON_COUNTY")
	private String curPersonCounty;

	@Column(name = "PROV_COUNTY")
	private String provCounty;

	@Column(name = "FILE_NAME")
	private String fileName;

	@Column(name = "LNAME")
	private String lname;

	@Column(name = "FNAME")
	private String fname;

	@Column(name = "MNAME")
	private String mname;

	@Column(name = "MED_RECORD_NUMBER")
	private String medRecordNumber;

	@Column(name = "PROV_PHONE")
	private String provPhone;

	@Column(name = "RISK_OTHER_TEXT")
	private String riskOtherText;

	@Column(name = "MATCH_CD")
	private String matchCd;

	@Column(name = "ETHNICITY")
	private String ethnicity;

	@Column(name = "DX_PERSON_STATE")
	private String dxPersonState;

	@Column(name = "CUR_PERSON_STATE")
	private String curPersonState;

	@Column(name = "PROV_STATE")
	private String provState;

	@Column(name = "BIRTH_COUNTRY")
	private String birthCountry;

	@Column(name = "DX_PERSON_TELEPHONE")
	private String dxPersonTelephone;

	@Column(name = "PERSON_CELLPHONE")
	private String personCellphone;

	@Column(name = "PERSON_WORKPHONE")
	private String personWorkphone;

	@Column(name = "CUR_PERSON_COUNTY_NAME")
	private String curPersonCountyName;

	@Column(name = "CUR_PERSON_PHONE")
	private String curPersonPhone;

	@Column(name = "RECV_BLOOD_PROD_COUNTRY")
	private String recvBloodProdCountry;

	@Column(name = "CONTACT_PHONE")
	private String contactPhone;

	@Column(name = "RSH_PHONE")
	private String rshPhone;

	@Column(name = "SSN")
	private String ssn;

	@Column(name = "DX_PERSON_LONGITUDE")
	private String dxPersonLongitude;

	@Column(name = "CUR_PERSON_LONGITUDE")
	private String curPersonLongitude;

	@Column(name = "PROV_LONGITUDE")
	private String provLongitude;

	@Column(name = "CITYNO")
	private String cityno;

	@Column(name = "NYC_SENT_STATENO")
	private String nycSentStateno;

	@Column(name = "DOB_TEXT")
	private String dobText;

	@Column(name = "RACE_CD")
	private String raceCd;

	@Column(name = "NYSID")
	private String nysid;

	@Column(name = "ADMISSION_DATE_TEXT")
	private String admissionDateText;

	@Column(name = "INIT_HIV_DIAG_DATE_TEXT")
	private String initHivDiagDateText;

	@Column(name = "INIT_AIDS_DIAG_DATE_TEXT")
	private String initAidsDiagDateText;

	@Column(name = "INFORMED_HIV_DATE_TEXT")
	private String informedHivDateText;

	@Column(name = "INDX_INTERVIEWED_DATE_TEXT")
	private String indxInterviewedDateText;

	@Column(name = "RECV_BLOOD_PROD_DATE_TEXT")
	private String recvBloodProdDateText;

	@Column(name = "MATCHED_STATENO")
	private String matchedStateno;

	@Column(name = "HIV_DX_DATE_TEXT")
	private String hivDxDateText;

	@Column(name = "AIDS_DX_DATE_TEXT")
	private String aidsDxDateText;

	@Column(name = "DX_PERSON_LATITUDE")
	private String dxPersonLatitude;

	@Column(name = "CUR_PERSON_LATITUDE")
	private String curPersonLatitude;

	@Column(name = "PROV_LATITUDE")
	private String provLatitude;

	@Column(name = "CURRENT_GENDER")
	private String currentGender;

	@Column(name = "INIT_HIV_DIAG")
	private String initHivDiag;

	@Column(name = "INIT_AIDS_DIAG")
	private String initAidsDiag;

	@Column(name = "INFORMED_HIV_POS")
	private String informedHivPos;

	@Column(name = "INDX_INTERVIEWED")
	private String indxInterviewed;

	@Column(name = "SWM")
	private String swm;

	@Column(name = "SWF")
	private String swf;

	@Column(name = "IDU")
	private String idu;

	@Column(name = "SEX_PARTNER_IDU")
	private String sexPartnerIdu;

	@Column(name = "SEX_PARTNER_HIV")
	private String sexPartnerHiv;

	@Column(name = "RECV_BLOOD_PROD")
	private String recvBloodProd;

	@Column(name = "PERINATAL")
	private String perinatal;

	@Column(name = "RISK_OTHER")
	private String riskOther;

	@Column(name = "RISK_UNKNOWN")
	private String riskUnknown;

	@Column(name = "SEX_PARTNER_MSM")
	private String sexPartnerMsm;

	@Column(name = "PS_REQUESTED")
	private String psRequested;

	@Column(name = "PROCESSED")
	private String processed;

	@Column(name = "NUMBEROFPARTNERS")
	private Integer numberofpartners;

	@Column(name = "NYC_IJR_SRC_ID")
	private Long nycIjrSrcId;

	@Id
	@Column(name = "DOCUMENT_ID")
	private Integer documentId;

	@Column(name = "CURRENT_PROCESS_STATUS")
	private Integer currentProcessStatus;

	@Column(name = "AGE")
	private Integer age;

	@Column(name = "MATCH_PASS")
	private Integer matchPass;

	@Column(name = "MATCH_WEIGHT")
	private BigDecimal matchWeight;

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

	@Column(name = "PROV_SIGNED_DATE")
	private Timestamp provSignedDate;

	@Column(name = "COMPLETED_DATE")
	private Timestamp completedDate;

	@Column(name = "FILE_DATE")
	private Timestamp fileDate;

	@Column(name = "CREATE_DATE")
	private Timestamp createDate;

	@Column(name = "REVIEW_DT")
	private Timestamp reviewDt;

	@Column(name = "MATCHED_PROCESS_DATE")
	private Timestamp matchedProcessDate;

	@Column(name = "EHARS_IMPORT_DATE")
	private Timestamp eharsImportDate;

	@Column(name = "NYC_SHIP_DATE")
	private Timestamp nycShipDate;

	@Column(name = "CREATE_UID")
	private String createUid;

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
