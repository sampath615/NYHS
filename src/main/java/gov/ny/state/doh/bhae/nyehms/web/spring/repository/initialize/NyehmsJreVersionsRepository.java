package gov.ny.state.doh.bhae.nyehms.web.spring.repository.initialize;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.initialize.NyehmsJreVersions;

/** The accepted Java versions (NYEHMS_JRE_VERSIONS). */
public interface NyehmsJreVersionsRepository extends JpaRepository<NyehmsJreVersions, String> {

	/** A Java version and its reject flag. */
	record JreVersion(String jreVersion, String rejectFlag) {
	}

	/** The versions in effect at {@code now} (started, not ended). */
	List<JreVersion> findByEffectiveStartDateLessThanEqualAndEffectiveEndDateIsNullOrEffectiveStartDateLessThanEqualAndEffectiveEndDateAfter(
			Timestamp now, Timestamp now2, Timestamp now3);

	/** The versions with the latest start date (a subquery, hence JPQL). */
	@Query("select j.jreVersion from NyehmsJreVersions j where j.effectiveStartDate = (select max(k.effectiveStartDate) from NyehmsJreVersions k)")
	List<String> findLatestVersions();
}
