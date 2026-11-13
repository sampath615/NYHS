package gov.ny.state.doh.bhae.nyehms.web.spring.entity.common;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

/**
 * The ZIP code reference (table NYEHMS_COUNTY_REFERENCE): a ZIP code's cities, counties and states; NUM_CITY = 1
 * marks a ZIP code's main row. Read-only, read through projections only by the address drop-downs and lookups
 * (CommonDAO), so a key that repeats does not matter. <b>ZIP_CD as the key is assumed</b> (it is not one: a ZIP
 * code has a row per city).
 */
@Entity
@Immutable
@Getter
@Table(name = "NYEHMS_COUNTY_REFERENCE")
public class NyehmsCountyReference {

	@Id
	@Column(name = "ZIP_CD")
	private String zipCd;

	@Column(name = "CITY")
	private String city;

	@Column(name = "COUNTY")
	private String county;

	@Column(name = "COUNTY_FIPS_CD")
	private String countyFipsCd;

	@Column(name = "STATE_CD")
	private String stateCd;

	@Column(name = "NUM_CITY")
	private Integer numCity;
}
