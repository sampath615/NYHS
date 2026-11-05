package gov.ny.state.doh.bhae.nyehms.web.spring.repository.common;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.common.SubFacility;

/** The standardized (sub) facilities (table SUB_FACILITY). */
public interface SubFacilityRepository extends JpaRepository<SubFacility, Long> {

	/** A sub facility with this eHARS facility UID (the first one found, in no particular order). */
	Optional<SubFacility> findFirstByEharsFacUid(String eharsFacUid);
}
