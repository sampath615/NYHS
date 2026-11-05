package gov.ny.state.doh.bhae.nyehms.web.spring.repository.common;

import org.springframework.data.jpa.repository.JpaRepository;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.common.CodedRole;

/** The worker roles (CODED_ROLE). */
public interface CodedRoleRepository extends JpaRepository<CodedRole, Long> {
}
