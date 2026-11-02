package gov.ny.state.doh.bhae.nyehms.ehars.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import gov.ny.state.doh.bhae.nyehms.ehars.entity.EharsCountryCode;
import gov.ny.state.doh.bhae.nyehms.ehars.record.EharsCode;

/**
 * The eHARS country codes.
 */
public interface EharsCountryCodeRepository extends JpaRepository<EharsCountryCode, String> {

	/** The country codes and descriptions, by description. */
	@Query("""
			select new gov.ny.state.doh.bhae.nyehms.ehars.record.EharsCode(country.countryCd, country.descr)
			from EharsCountryCode country
			order by country.descr
			""")
	List<EharsCode> findCodesByDescription();
}
