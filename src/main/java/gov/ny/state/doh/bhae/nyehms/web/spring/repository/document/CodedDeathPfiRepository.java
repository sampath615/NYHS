package gov.ny.state.doh.bhae.nyehms.web.spring.repository.document;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.document.CodedDeathPfi;
import gov.ny.state.doh.bhae.nyehms.web.spring.record.DeathFacility;

/**
 * Spring Data JPA repository for the facilities of the death records (CODED_DEATH_PFI; replaces
 * DeathDocumentDAO.getFacilityNameByPFI, NycDeathDocumentDAO.getFacilityInfoFromPFI and getFacilityInfoFromNycFacCode).
 */
public interface CodedDeathPfiRepository extends JpaRepository<CodedDeathPfi, String> {

	/**
	 * The facility of a PFI.
	 *
	 * @return the facility, or null if there is none
	 */
	@Query("""
			select new gov.ny.state.doh.bhae.nyehms.web.spring.record.DeathFacility(pfi.facilityType, pfi.facilityName,
					pfi.facilityAddress, pfi.facilityCity, pfi.facilityState, pfi.facilityZip, pfi.facilityPlus4,
					pfi.facilityPhone, pfi.facilityCounty)
			from CodedDeathPfi pfi
			where pfi.pfi = :pfi
			""")
	DeathFacility findFacilityByPfi(String pfi);

	/**
	 * The facility of an NYC facility code (NYC Master Institution list).
	 *
	 * @return the facility, or null if there is none
	 */
	@Query("""
			select new gov.ny.state.doh.bhae.nyehms.web.spring.record.DeathFacility(pfi.facilityType, pfi.facilityName,
					pfi.facilityAddress, pfi.facilityCity, pfi.facilityState, pfi.facilityZip, pfi.facilityPlus4,
					pfi.facilityPhone, pfi.facilityCounty)
			from CodedDeathPfi pfi
			where pfi.nycFacCd = :nycFacCode
			""")
	DeathFacility findFacilityByNycFacCode(String nycFacCode);
}
