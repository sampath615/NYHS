package gov.ny.state.doh.bhae.nyehms.web.spring.initialize.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InitFailureReasonRepository extends JpaRepository<InitFailureReason, Integer> {
    List<InitFailureReason> findAllByOrderByReasonCdAsc();
}
