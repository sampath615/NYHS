package gov.ny.state.doh.bhae.nyehms.web.spring.repository.document;

import org.springframework.data.jpa.repository.JpaRepository;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.document.RawTbDoc;

/**
 * Spring Data JPA repository for the received TB records: the document copy saves the raw row with saveAndFlush
 * (replaces TBDocumentDAO.insertRawEditedTBDocument).
 */
public interface RawTbDocRepository extends JpaRepository<RawTbDoc, Integer> {
}
