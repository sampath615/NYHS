package gov.ny.state.doh.bhae.nyehms.web.spring.repository.common;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.common.AccptdTstResultDomain;

/** The acceptable test results (table ACCPTD_TST_RESULT_DOMAIN). */
public interface AccptdTstResultDomainRepository extends JpaRepository<AccptdTstResultDomain, Long> {

	/**
	 * The keys of the results whose VALUE_TYPE|STRING_VALUE|RANGE_LOWER_BOUND|RANGE_UPPER_BOUND is the given text. In
	 * the query, not in Java: the database turns the NUMBER ranges into text (e.g. 0.5 as ".5" on Oracle) and
	 * concatenates NULL as nothing.
	 */
	@Query("""
			select a.atrDomainCd from AccptdTstResultDomain a
			where (a.valueType || '|' || a.stringValue || '|' || a.rangeLowerBound || '|' || a.rangeUpperBound) = :combinedValues
			""")
	List<Long> findKeysByCombinedValues(String combinedValues);
}
