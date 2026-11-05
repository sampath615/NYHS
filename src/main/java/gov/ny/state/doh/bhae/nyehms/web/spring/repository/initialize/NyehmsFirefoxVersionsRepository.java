package gov.ny.state.doh.bhae.nyehms.web.spring.repository.initialize;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.initialize.NyehmsFirefoxVersions;

/** The accepted Firefox versions (NYEHMS_FIREFOX_VERSIONS). */
public interface NyehmsFirefoxVersionsRepository extends JpaRepository<NyehmsFirefoxVersions, String> {

	/** A Firefox version and its reject flag. */
	record FirefoxVersion(String version, String rejectFlag) {
	}

	/** The versions in effect at {@code now} (started, not ended). */
	List<FirefoxVersion> findByEffectiveStartDateLessThanEqualAndEffectiveEndDateIsNullOrEffectiveStartDateLessThanEqualAndEffectiveEndDateAfter(
			Timestamp now, Timestamp now2, Timestamp now3);

	/** The versions with the latest start date (a subquery, hence JPQL). */
	@Query("select f.version from NyehmsFirefoxVersions f where f.effectiveStartDate = (select max(g.effectiveStartDate) from NyehmsFirefoxVersions g)")
	List<String> findLatestVersions();
}
