package gov.ny.state.doh.bhae.nyehms.web.spring.entity.common;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

/**
 * The regional offices (code table CODED_ADMIN_COUNTY_REGION).
 * <p>
 * Read-only and read through projections only. <b>ADMIN_COUNTY_REGION_CD as the key is assumed.</b>
 */
@Entity
@Immutable
@Getter
@Table(name = "CODED_ADMIN_COUNTY_REGION")
public class CodedAdminCountyRegion {

	@Id
	@Column(name = "ADMIN_COUNTY_REGION_CD")
	private String adminCountyRegionCd;

	@Column(name = "ADMIN_COUNTY_REGION_DESC")
	private String adminCountyRegionDesc;
}
