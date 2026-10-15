package gov.ny.state.doh.bhae.nyehms.web.spring.initialize.repository;

import gov.ny.state.doh.bhae.nyehms.web.spring.initialize.entity.InitFailureReason;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InitFailureReasonRepository extends JpaRepository<InitFailureReason, Integer> {
    List<InitFailureReason> findAllByOrderByReasonCdAsc();
}
