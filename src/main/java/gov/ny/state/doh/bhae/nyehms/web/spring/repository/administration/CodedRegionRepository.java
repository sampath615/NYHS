package gov.ny.state.doh.bhae.nyehms.web.spring.repository.administration;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.administration.CodedRegion;

/** The regions (CODED_REGION). */
public interface CodedRegionRepository extends JpaRepository<CodedRegion, String> {

	/** A region. */
	record Region(String regionCode, String regionDescription) {
	}

	/** The regions of an assignment type not ended by {@code now}, by description. */
	List<Region> findByAssignmentTypeCodeAndEndDateIsNullOrAssignmentTypeCodeAndEndDateAfterOrderByRegionDescription(String assignmentTypeCode,
			String assignmentTypeCode2, Timestamp now);
}
