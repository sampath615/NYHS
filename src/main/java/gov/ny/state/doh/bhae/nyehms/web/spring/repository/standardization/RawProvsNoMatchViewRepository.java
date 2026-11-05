package gov.ny.state.doh.bhae.nyehms.web.spring.repository.standardization;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.standardization.RawProvsNoMatchView;
import gov.ny.state.doh.bhae.nyehms.web.spring.record.RawProvsNoMatchRow;

/**
 * The raw provider addresses not yet standardized (view NYEHMS_RAW_PROVS_NO_MATCH_VW), read as projections; the
 * grid search is a specification.
 */
public interface RawProvsNoMatchViewRepository extends JpaRepository<RawProvsNoMatchView, Long>, JpaSpecificationExecutor<RawProvsNoMatchView> {

	/** The first 100 rows, in the view's order (the former ROWNUM &lt;= 100). */
	List<RawProvsNoMatchRow> findTop100By();
}
