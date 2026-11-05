package gov.ny.state.doh.bhae.nyehms.web.spring.repository.review;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.review.NbsFacilityMonitoring;

/**
 * The perinatal facility monitoring figures (NBS_FACILITY_MONITORING). The queries are JPQL rather than derived
 * methods: a null value must compare as SQL "= NULL" (no rows), as before, not as "IS NULL".
 */
public interface NbsFacilityMonitoringRepository extends JpaRepository<NbsFacilityMonitoring, String> {

	/** A facility of the monitoring figures. */
	record Facility(String pfi, String facilityName) {
	}

	/** The named facilities with figures for either of two birth quarters, by name. */
	@Query("""
			select distinct new gov.ny.state.doh.bhae.nyehms.web.spring.repository.review.NbsFacilityMonitoringRepository$Facility(f.pfi, f.facilityName)
			from NbsFacilityMonitoring f where f.facilityName is not null
			and ((f.birthqtr = ?1 and f.birthyr = ?2) or (f.birthqtr = ?3 and f.birthyr = ?4)) order by f.facilityName asc""")
	List<Facility> findFacilities(String firstQuarter, String firstYear, String secondQuarter, String secondYear);

	/** {@link #findFacilities} of one facility. */
	@Query("""
			select distinct new gov.ny.state.doh.bhae.nyehms.web.spring.repository.review.NbsFacilityMonitoringRepository$Facility(f.pfi, f.facilityName)
			from NbsFacilityMonitoring f where f.facilityName is not null and f.pfi = ?5
			and ((f.birthqtr = ?1 and f.birthyr = ?2) or (f.birthqtr = ?3 and f.birthyr = ?4)) order by f.facilityName asc""")
	List<Facility> findFacility(String firstQuarter, String firstYear, String secondQuarter, String secondYear, String pfi);
}
