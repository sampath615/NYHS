package gov.ny.state.doh.bhae.nyehms.web.spring.repository.document;

import org.springframework.data.jpa.repository.JpaRepository;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.document.RawIccrDoc;

/**
 * Spring Data JPA repository for the ICCR reports as entered (RAW_ICCR_DOC; replaces
 * ICCRDocumentDAO.saveICCRDocumentData).
 */
public interface RawIccrDocRepository extends JpaRepository<RawIccrDoc, Integer> {
}
