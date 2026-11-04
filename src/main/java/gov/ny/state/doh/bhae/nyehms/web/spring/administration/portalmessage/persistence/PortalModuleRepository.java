package gov.ny.state.doh.bhae.nyehms.web.spring.administration.portalmessage.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PortalModuleRepository extends JpaRepository<PortalModule, Long> {

    List<PortalModule> findAllByOrderByCodeAsc();
}
