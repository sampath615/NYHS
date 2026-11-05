package gov.ny.state.doh.bhae.nyehms.web.spring.entity.standardization;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

/**
 * A raw provider address waiting for the manual (clerical) review (view NYEHMS_RAW_PROVS_CLERIC_RVW_VW); read-only.
 * The columns the manual review grid reads. <b>RAW_PROVIDER_ADDRESS_ID as the key is assumed</b> (one row per raw
 * address).
 */
@Entity
@Immutable
@Getter
@Table(name = "NYEHMS_RAW_PROVS_CLERIC_RVW_VW")
public class RawProvsClericRvwView {

	@Id
	@Column(name = "RAW_PROVIDER_ADDRESS_ID")
	private Long rawProviderAddressId;

	@Column(name = "COUNTY_FIPS")
	private String countyFips;

	@Column(name = "ADDRESS")
	private String address;

	@Column(name = "CITY")
	private String city;

	@Column(name = "STATE")
	private String state;

	@Column(name = "ZIPCODE")
	private String zipcode;

	@Column(name = "PLUS4")
	private String plus4;

	@Column(name = "PHONE")
	private String phone;

	@Column(name = "PROVIDER_FULLNAME")
	private String providerFullname;

	@Column(name = "PROVIDER_FNAME")
	private String providerFname;

	@Column(name = "PROVIDER_MNAME")
	private String providerMname;

	@Column(name = "PROVIDER_LNAME")
	private String providerLname;

	@Column(name = "PROVIDER_SUFFIX")
	private String providerSuffix;
}
