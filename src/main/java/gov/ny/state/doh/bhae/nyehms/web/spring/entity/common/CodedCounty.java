package gov.ny.state.doh.bhae.nyehms.web.spring.entity.common;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

/**
 * The counties (table CODED_COUNTY): NYS county code and FIPS code; the columns the application reads.
 * <p>
 * Read-only and used in JPQL projections only. <b>COUNTY_FIPS_CODE as the key is assumed.</b>
 */
@Entity
@Immutable
@Getter
@Table(name = "CODED_COUNTY")
public class CodedCounty {

	@Id
	@Column(name = "COUNTY_FIPS_CODE")
	private String countyFipsCode;

	@Column(name = "COUNTY_CD")
	private String countyCd;

	@Column(name = "COUNTY_NAME")
	private String countyName;

	/** The order of the counties in the partner services (PS) reports. */
	@Column(name = "PS_SORT_ORDER")
	private Integer psSortOrder;
}
