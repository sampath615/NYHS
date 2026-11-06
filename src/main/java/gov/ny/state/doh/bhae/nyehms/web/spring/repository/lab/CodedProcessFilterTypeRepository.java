package gov.ny.state.doh.bhae.nyehms.web.spring.repository.lab;

import org.springframework.data.jpa.repository.JpaRepository;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.lab.CodedProcessFilterType;

/** The lab proficiency filter types (CODED_PROCESS_FILTER_TYPE). */
public interface CodedProcessFilterTypeRepository extends JpaRepository<CodedProcessFilterType, Long> {
}
