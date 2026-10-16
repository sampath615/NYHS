package gov.ny.state.doh.bhae.nyehms.web.spring.entity.document;

import java.sql.Date;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

/**
 * A CLIMS lab report, standardized (table STD_CLIMS_DOC); the columns the CLIMS document screen reads.
 * <p>
 * Read-only. <b>DOCUMENT_ID as the key is assumed</b> (one row per document).
 */
@Entity
@Immutable
@Getter
@Table(name = "STD_CLIMS_DOC")
public class StdClimsDoc {

	@Id
	@Column(name = "DOCUMENT_ID")
	private Integer documentId;

	@Column(name = "ACCID")
	private String accid;

	@Column(name = "FINALAPPROVALDT")
	private Date finalapprovaldt;

	@Column(name = "INITIALPCR")
	private String initialpcr;

	@Column(name = "FOLLOWUPPCR")
	private String followuppcr;

	@Column(name = "LASTNAME")
	private String lastname;

	@Column(name = "FIRSTNAME")
	private String firstname;

	@Column(name = "RECID")
	private String recid;

	@Column(name = "PATIENTID")
	private String patientid;

	@Column(name = "NBSCODE")
	private String nbscode;

	@Column(name = "NBSLABID")
	private String nbslabid;

	@Column(name = "PATCODE")
	private String patcode;

	@Column(name = "COUNTYCODE")
	private String countycode;

	@Column(name = "PATZIP")
	private String patzip;

	@Column(name = "DOB")
	private Date dob;

	@Column(name = "AGE")
	private Double age;

	@Column(name = "COLLDATE")
	private Date colldate;

	@Column(name = "RACE_INDIAN")
	private String raceIndian;

	@Column(name = "RACE_ASIAN")
	private String raceAsian;

	@Column(name = "RACE_BLACK")
	private String raceBlack;

	@Column(name = "RACE_PACIFIC")
	private String racePacific;

	@Column(name = "RACE_WHITE")
	private String raceWhite;

	@Column(name = "RACE_UNKNOWN")
	private String raceUnknown;

	@Column(name = "RACE_OTHER")
	private String raceOther;

	@Column(name = "ETHNICITY")
	private String ethnicity;

	@Column(name = "CURRENT_GENDER")
	private String currentGender;

	@Column(name = "GESTATION")
	private String gestation;

	@Column(name = "WEIGHT")
	private String weight;

	@Column(name = "BIRTH_FACILITY")
	private String birthFacility;

	@Column(name = "BIRTH_COUNTYCODE")
	private String birthCountycode;

	@Column(name = "FOSTERCARE")
	private String fostercare;

	@Column(name = "MAV")
	private String mav;

	@Column(name = "MAVWHEN")
	private String mavwhen;

	@Column(name = "IPAV")
	private String ipav;

	@Column(name = "ITAV")
	private String itav;

	@Column(name = "SUBMFNAME")
	private String submfname;

	@Column(name = "SUBMLNAME")
	private String submlname;

	@Column(name = "SUBMITTERID")
	private String submitterid;

	@Column(name = "FACILITY_NAME")
	private String facilityName;

	@Column(name = "FACILITY_ADDRESS")
	private String facilityAddress;

	@Column(name = "FACILITY_COUNTY")
	private String facilityCounty;

	@Column(name = "FACILITY_CITY")
	private String facilityCity;

	@Column(name = "FACILITY_STATE")
	private String facilityState;

	@Column(name = "FACILITY_ZIP")
	private String facilityZip;

	@Column(name = "MULTISPOT")
	private String multispot;

	@Column(name = "HIV2QUAL")
	private String hiv2qual;

	@Column(name = "TMA")
	private String tma;

	@Column(name = "HIV1PANTHER")
	private String hiv1panther;

	@Column(name = "MEMO")
	private String memo;

	@Column(name = "GEENIUS")
	private String geenius;

	@Column(name = "EHARS_IMPORT_DATE")
	private Date eharsImportDate;

	@Column(name = "NYC_SHIP_DATE")
	private Date nycShipDate;

	@Column(name = "MATCHED_STATENO")
	private String matchedStateno;
}
