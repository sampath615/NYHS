package gov.ny.state.doh.bhae.nyehms.web.spring.repository.document;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.document.LegacyPsPersonPartner;
import gov.ny.state.doh.bhae.nyehms.web.spring.entity.document.LegacyPsPersonPartnerId;

/**
 * The partners of the legacy partner services assignments.
 */
public interface LegacyPsPersonPartnerRepository extends JpaRepository<LegacyPsPersonPartner, LegacyPsPersonPartnerId> {

	/** The partner ids of an assignment (0 for a missing id, as the screen always showed it). */
	@Query("""
			select coalesce(partner.partnrid, 0)
			from LegacyPsPersonPartner partner
			where partner.assignmentId = :assignmentId
			""")
	List<Integer> findPartnerIdsOfAssignment(int assignmentId);
}
