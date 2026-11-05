package gov.ny.state.doh.bhae.nyehms.web.spring.repository.review;

import org.springframework.data.jpa.repository.JpaRepository;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.review.CodedHivFinalStatus;

/** The HIV final statuses of newborn screening reports (table CODED_HIV_FINAL_STATUS). */
public interface CodedHivFinalStatusRepository extends JpaRepository<CodedHivFinalStatus, String> {
}
