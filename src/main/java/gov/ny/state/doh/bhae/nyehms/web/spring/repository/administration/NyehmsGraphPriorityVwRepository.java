package gov.ny.state.doh.bhae.nyehms.web.spring.repository.administration;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.administration.NyehmsGraphPriorityVw;

/** The landing-page graphs by role (NYEHMS_GRAPH_PRIORITY_VW). */
public interface NyehmsGraphPriorityVwRepository extends JpaRepository<NyehmsGraphPriorityVw, String> {

	/**
	 * The graphs of a worker's lowest current role code, in rank order (a subquery over another entity, hence JPQL).
	 */
	@Query("""
			select g.graphDescription from NyehmsGraphPriorityVw g
			where g.roleCode in (select min(u.roleCode) from UserRegion u where u.endDate is null and u.workerId = ?1)
			order by g.graphRoleRank""")
	List<String> findGraphsOfWorker(Long workerId);
}
