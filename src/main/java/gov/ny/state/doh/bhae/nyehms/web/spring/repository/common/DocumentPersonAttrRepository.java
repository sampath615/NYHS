package gov.ny.state.doh.bhae.nyehms.web.spring.repository.common;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.common.DocumentPersonAttr;

/** The person attributes of documents (table DOCUMENT_PERSON_ATTR). */
public interface DocumentPersonAttrRepository extends JpaRepository<DocumentPersonAttr, Integer> {

	/**
	 * Deletes the rows of a document.
	 *
	 * @return the number of rows deleted
	 */
	@Modifying
	@Query("delete from DocumentPersonAttr d where d.documentId = ?1")
	int deleteByDocument(Integer documentId);
}
