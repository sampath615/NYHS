package gov.ny.state.doh.bhae.nyehms.web.spring.repository.document;

import org.springframework.data.jpa.repository.JpaRepository;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.document.RawDeathDoc;

/**
 * Spring Data JPA repository for the death records as received (RAW_DEATH_DOC; replaces
 * DeathDocumentDAO.insertRawDTHDocument, used when a document is copied).
 */
public interface RawDeathDocRepository extends JpaRepository<RawDeathDoc, Integer> {
}
