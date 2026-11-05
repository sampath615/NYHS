package gov.ny.state.doh.bhae.nyehms.web.spring.repository.review;

import org.springframework.data.jpa.repository.JpaRepository;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.review.RawNbsDoc;

/** The newborn screening (NBS) reports as received (table RAW_NBS_DOC). */
public interface RawNbsDocRepository extends JpaRepository<RawNbsDoc, Long> {
}
