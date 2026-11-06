package gov.ny.state.doh.bhae.nyehms.web.spring.repository.sierra;

import org.springframework.data.jpa.repository.JpaRepository;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.sierra.RasSiMutation;

/** The mutations of the Sierra resistance responses (table RAS_SI_MUTATION); saveAndFlush inserts a new one. */
public interface RasSiMutationRepository extends JpaRepository<RasSiMutation, Long> {
}
