package gov.ny.state.doh.bhae.nyehms.web.spring.repository.common;

import org.springframework.data.jpa.repository.JpaRepository;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.common.LabLoinc;

/** The LOINC lookup (table LAB_LOINC). */
public interface LabLoincRepository extends JpaRepository<LabLoinc, String>, LabLoincQueries {

	/** The number of rows of a LOINC code. */
	long countByLoincCd(String loincCd);
}
