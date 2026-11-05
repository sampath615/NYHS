package gov.ny.state.doh.bhae.nyehms.web.spring.repository.common;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.common.LoincAccptdTstResultDomain;
import gov.ny.state.doh.bhae.nyehms.web.spring.record.LoincAtrDomainRow;

/** The associations of acceptable test results with LOINC codes (table LOINC_ACCPTD_TST_RESULT_DOMAIN). */
public interface LoincAccptdTstResultDomainRepository extends JpaRepository<LoincAccptdTstResultDomain, Long> {

	/** The number of associations with a flag, a LOINC code and any of the results (the parameters must not be null or empty). */
	long countByFlagActiveAndLoincCdAndAtrDomainCdIn(String flagActive, String loincCd, Collection<Long> atrDomainCds);

	/** The active associations of a LOINC code whose result is active, with their result and eHARS standard result. */
	@Query("""
			select new gov.ny.state.doh.bhae.nyehms.web.spring.record.LoincAtrDomainRow(l, a, e)
			from AccptdTstResultDomain a, LoincAccptdTstResultDomain l, CodedEharsStdResults e
			where a.atrDomainCd = l.atrDomainCd and l.resultsStdCd = e.resultsStdCd and l.loincCd = :loincCode
			and a.flagActive = 'Y' and l.flagActive = 'Y'
			""")
	List<LoincAtrDomainRow> findActiveOfLoinc(String loincCode);
}
