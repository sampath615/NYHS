package gov.ny.state.doh.bhae.nyehms.web.spring.repository.document;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.document.PersonDocAssociation;

/** The links between documents and persons (table PERSON_DOC_ASSOCIATION). */
public interface PersonDocAssociationRepository extends JpaRepository<PersonDocAssociation, Long> {

	/**
	 * Deletes every association of a document (current or not).
	 *
	 * @return the number of rows deleted
	 */
	@Modifying
	@Query("delete from PersonDocAssociation p where p.documentId = ?1")
	int deleteByDocument(Integer documentId);
}
