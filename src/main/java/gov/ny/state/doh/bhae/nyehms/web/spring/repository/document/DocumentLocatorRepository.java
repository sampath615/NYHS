package gov.ny.state.doh.bhae.nyehms.web.spring.repository.document;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.document.DocumentLocator;

/** Where the documents are filed (table DOCUMENT_LOCATOR). */
public interface DocumentLocatorRepository extends JpaRepository<DocumentLocator, Integer> {

	/**
	 * Sets the test type of a document.
	 *
	 * @return the number of rows changed
	 */
	@Modifying
	@Query("update DocumentLocator d set d.testTypeCd = ?1 where d.documentId = ?2")
	int updateTestType(String testType, Integer documentId);

	/**
	 * Deletes a document if it is active (IS_ACTIVE = 'Y').
	 *
	 * @return the number of rows deleted
	 */
	@Modifying
	@Query("delete from DocumentLocator d where d.documentId = ?1 and d.isActive = 'Y'")
	int deleteActive(Integer documentId);

	/** The documents derived from a document (its children with a higher id), in database order. */
	@Query("select d.documentId from DocumentLocator d where d.parentDocumentId = ?1 and d.documentId > ?1")
	List<Integer> findDerivedDocumentIds(Integer documentId);

	/** The parent document id of a document (one element per row; null if not set). */
	@Query("select d.parentDocumentId from DocumentLocator d where d.documentId = ?1")
	List<Integer> findParentDocumentIds(Integer documentId);
}
