package gov.ny.state.doh.bhae.nyehms.web.spring.entity.document;

import java.sql.Date;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

/**
 * An address entered in the tracking system (table TRACKING_USER_ENTERED_ADDRESS); the columns the tracking address
 * document screen reads.
 * <p>
 * Read-only. <b>DOCUMENT_ID as the key is assumed</b> (the screen reads one row per document).
 */
@Entity
@Immutable
@Getter
@Table(name = "TRACKING_USER_ENTERED_ADDRESS")
public class TrackingUserEnteredAddress {

	@Id
	@Column(name = "DOCUMENT_ID")
	private Integer documentId;

	@Column(name = "STATENO")
	private String stateno;

	@Column(name = "FNAME")
	private String fname;

	@Column(name = "MNAME")
	private String mname;

	@Column(name = "LNAME")
	private String lname;

	@Column(name = "SSN")
	private String ssn;

	@Column(name = "DOB_TEXT")
	private String dobText;

	@Column(name = "SUFFIX")
	private String suffix;

	@Column(name = "ADDRESS")
	private String address;

	@Column(name = "ADDRESS_CITY")
	private String addressCity;

	@Column(name = "ADDRESS_STATE")
	private String addressState;

	@Column(name = "ADDRESS_ZIPCODE")
	private String addressZipcode;

	@Column(name = "ADDRESS_PLUS4")
	private String addressPlus4;

	@Column(name = "COUNTY_FIPS")
	private String countyFips;

	@Column(name = "HOME_PHONE")
	private String homePhone;

	@Column(name = "ADDRESS_TRACT_CENSUS")
	private String addressTractCensus;

	@Column(name = "TRACT_SUFFIX_CENSUS")
	private String tractSuffixCensus;

	/** Read as text, as the screen's VO read it. */
	@Column(name = "ADDRESS_LATITUDE")
	private String addressLatitude;

	/** Read as text, as the screen's VO read it. */
	@Column(name = "ADDRESS_LONGITUDE")
	private String addressLongitude;

	/** The legacy id (NYEHMS_USERS.LEGACY_UID) of the worker who entered it. */
	@Column(name = "CREATEID")
	private String createid;

	@Column(name = "CREATEDT")
	private Date createdt;
}
