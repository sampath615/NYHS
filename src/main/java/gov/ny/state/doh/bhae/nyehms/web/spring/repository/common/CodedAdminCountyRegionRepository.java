package gov.ny.state.doh.bhae.nyehms.web.spring.repository.common;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.common.CodedAdminCountyRegion;

/** The regional offices (CODED_ADMIN_COUNTY_REGION). */
public interface CodedAdminCountyRegionRepository extends JpaRepository<CodedAdminCountyRegion, String> {

	/** A regional office. */
	record RegOffice(String adminCountyRegionCd, String adminCountyRegionDesc) {
	}

	/** All regional offices, in this order. */
	List<RegOffice> findAllBy(Sort sort);
}
