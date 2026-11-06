package gov.ny.state.doh.bhae.nyehms.web.spring.entity.document;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

/**
 * The DOCCS facilities (table CODED_DOCCS_FACILITY); only the columns the DOCCS import copies into the standardized DIN
 * records.
 * <p>
 * Read-only and read through JPQL projections only. <b>FACILITY_NAME as the key is assumed</b>; the columns are read
 * as text (they are written into text columns).
 */
@Entity
@Immutable
@Getter
@Table(name = "CODED_DOCCS_FACILITY")
public class CodedDoccsFacility {

	@Id
	@Column(name = "FACILITY_NAME")
	private String facilityName;

	@Column(name = "ADDRESS1")
	private String address1;

	@Column(name = "ADDRESS2")
	private String address2;

	@Column(name = "CITY")
	private String city;

	@Column(name = "STATE")
	private String state;

	@Column(name = "ZIPCODE")
	private String zipcode;

	@Column(name = "PLUS4")
	private String plus4;

	@Column(name = "COUNTY_FIPS")
	private String countyFips;

	@Column(name = "PHONE")
	private String phone;
}
