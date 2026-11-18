package gov.ny.state.doh.bhae.nyehms.web.spring.administration.systemmessage.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SystemMessageRepository extends JpaRepository<SystemMessage, Long> {
}
