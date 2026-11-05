package gov.ny.state.doh.bhae.nyehms.web.spring.repository.common;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.common.NyehmsWorkerRegionsVw;
import gov.ny.state.doh.bhae.nyehms.web.spring.record.RegionRoleRow;

/** The workers' regions and roles view (NYEHMS_WORKER_REGIONS_VW). */
public interface NyehmsWorkerRegionsVwRepository extends JpaRepository<NyehmsWorkerRegionsVw, Long> {

	/**
	 * A worker's regions and roles with the dates as MM/DD/YYYY text, sorted by that end date text, empty first (the
	 * former ORDER BY END_DATE named the TO_CHAR column, so it sorted the text); JPQL for the sort by a formatted value.
	 */
	@Query("""
			select new gov.ny.state.doh.bhae.nyehms.web.spring.record.RegionRoleRow(w.workerRegionId, w.roleDesc, w.regionDescription,
			  format(w.wrStartDate as 'MM/dd/yyyy'), format(w.wrEndDate as 'MM/dd/yyyy'))
			from NyehmsWorkerRegionsVw w where w.workerId = ?1 order by format(w.wrEndDate as 'MM/dd/yyyy') nulls first""")
	List<RegionRoleRow> findRegionRoles(Long workerId);
}
