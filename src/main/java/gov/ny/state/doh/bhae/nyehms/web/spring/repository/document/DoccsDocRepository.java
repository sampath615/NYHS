package gov.ny.state.doh.bhae.nyehms.web.spring.repository.document;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.document.StdDoccsDoc;
import gov.ny.state.doh.bhae.nyehms.web.spring.record.DoccsDocData;

/**
 * Spring Data JPA repository for the DOCCS document (replaces DoccsDocumentDAO.getDoccsDoc).
 */
public interface DoccsDocRepository extends JpaRepository<StdDoccsDoc, Integer> {

	/**
	 * A DOCCS (Department of Corrections) report for the DOCCS document screen: the standardized document with its current
	 * person match (if any).
	 *
	 * @param documentId document id
	 * @return the document, or {@code null} if there is none
	 */
	@Query("""
			select new gov.ny.state.doh.bhae.nyehms.web.spring.record.DoccsDocData(stdDoccsDoc,
					personDocAssociation.discardStatus,
					personDocAssociation.stateno)
			from StdDoccsDoc stdDoccsDoc
			left join PersonDocAssociation personDocAssociation
			on personDocAssociation.documentId = stdDoccsDoc.documentId
			and personDocAssociation.dissassociationDate is null
			where stdDoccsDoc.documentId = :documentId
			""")
	DoccsDocData findDocument(int documentId);
}
