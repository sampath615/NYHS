package gov.ny.state.doh.bhae.nyehms.web.spring.repository.standardization;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.standardization.RawProvsClericRvwView;
import gov.ny.state.doh.bhae.nyehms.web.spring.record.RawProvsClericRvwRow;

/** The raw provider addresses waiting for the manual review (view NYEHMS_RAW_PROVS_CLERIC_RVW_VW), read as projections. */
public interface RawProvsClericRvwViewRepository extends JpaRepository<RawProvsClericRvwView, Long> {

	/** Every row as it is, in the view's order. */
	List<RawProvsClericRvwRow> findAllBy();
}
