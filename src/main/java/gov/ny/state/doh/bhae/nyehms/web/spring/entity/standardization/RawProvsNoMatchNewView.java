package gov.ny.state.doh.bhae.nyehms.web.spring.entity.standardization;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

/**
 * A raw provider address not yet standardized, new screen (view NYEHMS_RAW_PROVS_NO_MATCH_NEW_VW); read-only. The
 * columns the new standardization grid reads, and SUB_PROVIDER_ADDRESS_ID (-9999 for the selectable rows).
 * <b>RAW_PROVIDER_ADDRESS_ID as the key is assumed</b> (one row per raw address).
 */
@Entity
@Immutable
@Getter
@Table(name = "NYEHMS_RAW_PROVS_NO_MATCH_NEW_VW")
public class RawProvsNoMatchNewView {

	@Id
	@Column(name = "RAW_PROVIDER_ADDRESS_ID")
	private Long rawProviderAddressId;

	@Column(name = "SUB_PROVIDER_ADDRESS_ID")
	private Long subProviderAddressId;

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

	@Column(name = "NPI")
	private String npi;

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
}
