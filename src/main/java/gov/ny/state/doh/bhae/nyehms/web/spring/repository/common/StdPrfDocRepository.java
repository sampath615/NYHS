package gov.ny.state.doh.bhae.nyehms.web.spring.repository.common;

import org.springframework.data.jpa.repository.JpaRepository;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.common.StdPrfDoc;

/** The standardized provider report forms (table STD_PRF_DOC); saveAndFlush inserts a new one. */
public interface StdPrfDocRepository extends JpaRepository<StdPrfDoc, Integer> {
}
