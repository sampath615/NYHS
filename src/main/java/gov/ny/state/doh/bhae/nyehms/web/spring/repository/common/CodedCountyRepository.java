package gov.ny.state.doh.bhae.nyehms.web.spring.repository.common;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.common.CodedCounty;

/**
 * Spring Data JPA repository for the counties (CODED_COUNTY; replaces DeathDocumentDAO.getNysFipsCounty).
 */
public interface CodedCountyRepository extends JpaRepository<CodedCounty, String> {

	/**
	 * The FIPS code of a NYS county code; the first one found if there are several (as the former rownum &lt; 2).
	 *
	 * @return the FIPS code, or null if the county code is not known
	 */
	@Query("""
			select county.countyFipsCode
			from CodedCounty county
			where county.countyCd = :countyCd
			fetch first 1 rows only
			""")
	String findFipsCodeOfCounty(String countyCd);
}
