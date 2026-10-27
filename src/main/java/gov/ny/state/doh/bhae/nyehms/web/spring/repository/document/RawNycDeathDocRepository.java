package gov.ny.state.doh.bhae.nyehms.web.spring.repository.document;

import org.springframework.data.jpa.repository.JpaRepository;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.document.RawNycDeathDoc;

/**
 * Spring Data JPA repository for the NYC death records as received (RAW_NYC_DEATH_DOC; replaces
 * NycDeathDocumentDAO.insertNycRawDTHDocument, used when a document is copied).
 */
public interface RawNycDeathDocRepository extends JpaRepository<RawNycDeathDoc, Integer> {
}
