package gov.ny.state.doh.bhae.nyehms.web.spring.repository.common;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.common.StdNbsDoc;

/** The standardized newborn screening (NBS) reports (table STD_NBS_DOC). */
public interface StdNbsDocRepository extends JpaRepository<StdNbsDoc, Long> {

	/**
	 * The birth years (YYYY, latest first) of the reports from 2021 on without a CLIMS match, or whose HIV final status
	 * is not CN / CP, that are not in status 9 or 20.
	 */
	@Query("""
			select distinct format(d.birthDate as 'yyyy') from StdNbsDoc d
			where ((d.climsRecid is null and d.infantStateno is null) or (d.hivFinalStatus not in ('CN', 'CP')))
			and d.currentProcessStatus not in (9, 20) and extract(year from d.birthDate) >= 2021
			order by format(d.birthDate as 'yyyy') desc""")
	List<String> findClimsNonMatchBirthYears();
}
