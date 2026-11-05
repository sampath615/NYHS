package gov.ny.state.doh.bhae.nyehms.web.spring.repository.common;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.common.CodedEharsStdResults;

/** The eHARS standard test results (table CODED_EHARS_STD_RESULTS). */
public interface CodedEharsStdResultsRepository extends JpaRepository<CodedEharsStdResults, String> {

	/** All results, ordered by result and interpretation together (sorted by the database, with its collation). */
	@Query("select e from CodedEharsStdResults e order by e.resultsStd || e.resultInterpretation")
	List<CodedEharsStdResults> findAllByDescription();
}
