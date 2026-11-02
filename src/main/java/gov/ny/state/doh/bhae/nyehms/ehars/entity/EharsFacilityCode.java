package gov.ny.state.doh.bhae.nyehms.ehars.entity;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

/**
 * An eHARS facility (eHARS database, table facility_code); the columns NYEHMS reads. Read-only. <b>The key is
 * assumed.</b>
 */
@Entity
@Immutable
@Getter
@Table(name = "facility_code")
public class EharsFacilityCode {

	@Id
	@Column(name = "facility_uid")
	private String facilityUid;

	@Column(name = "name1")
	private String name1;

	@Column(name = "street_address1")
	private String streetAddress1;

	@Column(name = "city_name")
	private String cityName;

	@Column(name = "county_fips")
	private String countyFips;

	@Column(name = "state_cd")
	private String stateCd;

	@Column(name = "zip_cd")
	private String zipCd;

	@Column(name = "phone")
	private String phone;

	@Column(name = "country_cd")
	private String countryCd;

	@Column(name = "facility_type_cd")
	private String facilityTypeCd;
}
