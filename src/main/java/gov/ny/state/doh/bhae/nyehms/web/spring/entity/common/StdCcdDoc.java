package gov.ny.state.doh.bhae.nyehms.web.spring.entity.common;

import java.sql.Timestamp;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

/**
 * The standardized CCD documents (table STD_CCD_DOC): the person's names, date of birth, demographics, address and
 * phones, the MPI, the dataflux match code and the loader's status (the columns the HIE death checks, the HL7 loader
 * and the CCD document viewer read; the loader's insert into this table stays native, see CCDDocumentDAO).
 * <p>
 * Read-only (read in JPQL). <b>DOCUMENT_ID as the key is assumed</b> (one row per document). MPI, the race flags and
 * the address parts are read as text (the loader writes them as text).
 */
@Entity
@Immutable
@Getter
@Table(name = "STD_CCD_DOC")
public class StdCcdDoc {

	@Id
	@Column(name = "DOCUMENT_ID")
	private Integer documentId;

	@Column(name = "MPI")
	private String mpi;

	@Column(name = "FNAME")
	private String fname;

	@Column(name = "MNAME")
	private String mname;

	@Column(name = "LNAME")
	private String lname;

	@Column(name = "DOB_TEXT")
	private String dobText;

	@Column(name = "GENDER")
	private String gender;

	@Column(name = "ETHNICITY")
	private String ethnicity;

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

	@Column(name = "RACE_UNKNOWN")
	private String raceUnknown;

	@Column(name = "RACE_OTHER")
	private String raceOther;

	@Column(name = "RACE_OTHER_TEXT")
	private String raceOtherText;

	@Column(name = "LANGUAGE")
	private String language;

	@Column(name = "PERSON_ADDRESS")
	private String personAddress;

	@Column(name = "PERSON_CITY")
	private String personCity;

	@Column(name = "PERSON_STATE")
	private String personState;

	@Column(name = "PERSON_ZIP")
	private String personZip;

	@Column(name = "PERSON_PLUS4")
	private String personPlus4;

	@Column(name = "PERSON_COUNTY")
	private String personCounty;

	@Column(name = "PERSON_PHONE")
	private String personPhone;

	@Column(name = "PERSON_ADDRESS_ID")
	private Long personAddressId;

	@Column(name = "PERSON_COUNTRY")
	private String personCountry;

	@Column(name = "PERSON_PHONE_HOME")
	private String personPhoneHome;

	@Column(name = "PERSON_PHONE_CELL")
	private String personPhoneCell;

	@Column(name = "PERSON_PHONE_WORK")
	private String personPhoneWork;

	@Column(name = "ADDRESS_USE")
	private String addressUse;

	@Column(name = "USEABLE_PERIOD_LOW")
	private String useablePeriodLow;

	@Column(name = "USEABLE_PERIOD_HIGH")
	private String useablePeriodHigh;

	@Column(name = "CREATE_DATE")
	private Timestamp createDate;

	@Column(name = "EHARS_IMPORT_DATE")
	private Timestamp eharsImportDate;

	@Column(name = "EHARS_STATUS_CD")
	private String eharsStatusCd;

	@Column(name = "UPHN_TRANX_IN_ID")
	private Long uphnTranxInId;

	@Column(name = "CURRENT_PROCESS_STATUS")
	private Integer currentProcessStatus;

	@Column(name = "FLUX_MATCH_CD")
	private String fluxMatchCd;
}
