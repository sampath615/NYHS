package gov.ny.state.doh.bhae.nyehms.web.spring.repository.common;

import org.springframework.data.jpa.repository.JpaRepository;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.common.RawFacility;

/** The facilities as received (table RAW_FACILITY). */
public interface RawFacilityRepository extends JpaRepository<RawFacility, Long> {

	/** Whether a raw facility is linked to the sub facility. */
	boolean existsBySubFacilityId(Long subFacilityId);
}
