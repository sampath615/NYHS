package gov.ny.state.doh.bhae.nyehms.web.spring.repository.initialize;

import org.springframework.data.jpa.repository.JpaRepository;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.initialize.UserAccessAttempts;

/** The login attempts (USER_ACCESS_ATTEMPTS). */
public interface UserAccessAttemptsRepository extends JpaRepository<UserAccessAttempts, Long> {
}
