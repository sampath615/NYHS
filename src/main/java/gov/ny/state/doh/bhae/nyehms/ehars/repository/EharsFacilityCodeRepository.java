package gov.ny.state.doh.bhae.nyehms.ehars.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import gov.ny.state.doh.bhae.nyehms.ehars.entity.EharsFacilityCode;
import gov.ny.state.doh.bhae.nyehms.ehars.record.EharsCode;
import gov.ny.state.doh.bhae.nyehms.ehars.record.EharsFacilityDetails;

/**
 * The eHARS facilities.
 */
public interface EharsFacilityCodeRepository extends JpaRepository<EharsFacilityCode, String> {

	/** The ids and names of the given facilities, by name. */
	@Query("""
			select new gov.ny.state.doh.bhae.nyehms.ehars.record.EharsCode(facility.facilityUid, facility.name1)
			from EharsFacilityCode facility
			where facility.facilityUid in :facilityUids
			order by facility.name1
			""")
	List<EharsCode> findNamesOfFacilities(Collection<String> facilityUids);

	/** A facility's address and type description (nothing if its type has no FACILITY_TYPE code). */
	@Query("""
			select new gov.ny.state.doh.bhae.nyehms.ehars.record.EharsFacilityDetails(facility.streetAddress1, facility.cityName,
					facility.countyFips, facility.stateCd, facility.zipCd, facility.phone, facility.countryCd, lookup.lookupDescr)
			from EharsFacilityCode facility, EharsLookupCode lookup
			where facility.facilityUid = :facilityUid
			and facility.facilityTypeCd = lookup.lookupCd
			and lookup.lookupType = 'FACILITY_TYPE'
			""")
	List<EharsFacilityDetails> findDetailsOfFacility(String facilityUid);
}
