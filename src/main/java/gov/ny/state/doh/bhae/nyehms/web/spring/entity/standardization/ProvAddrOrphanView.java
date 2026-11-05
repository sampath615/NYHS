package gov.ny.state.doh.bhae.nyehms.web.spring.entity.standardization;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

/**
 * A standardized provider address without a provider (view NYEHMS_PROV_ADDR_ORPHAN_VW); read-only. The view has one
 * row per address and phone, so it is read through a projection only (ProvAddrOrphanRow): the key declared here
 * (SUB_PROVIDER_ADDRESS_ID) repeats, which does not matter for projections.
 */
@Entity
@Immutable
@Getter
@Table(name = "NYEHMS_PROV_ADDR_ORPHAN_VW")
public class ProvAddrOrphanView {

	@Id
	@Column(name = "SUB_PROVIDER_ADDRESS_ID")
	private Long subProviderAddressId;

	@Column(name = "PROVIDER_ID")
	private Long providerId;

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
}
