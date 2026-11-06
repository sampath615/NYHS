package gov.ny.state.doh.bhae.nyehms.web.spring.repository.document;

import org.springframework.data.jpa.repository.JpaRepository;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.document.StdHieDeathDoc;

/** The standardized HIE death records (table STD_HIE_DEATH_DOC). */
public interface StdHieDeathDocRepository extends JpaRepository<StdHieDeathDoc, Integer> {
}
