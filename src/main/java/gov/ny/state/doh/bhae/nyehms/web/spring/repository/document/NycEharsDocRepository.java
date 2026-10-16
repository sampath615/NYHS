package gov.ny.state.doh.bhae.nyehms.web.spring.repository.document;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.document.StdNycEharsDoc;
import gov.ny.state.doh.bhae.nyehms.web.spring.record.NycEharsDocData;

/**
 * Spring Data JPA repository for the NYC eHARS document (replaces CommonDocLabsDAO.getNycEHARSDoc).
 */
public interface NycEharsDocRepository extends JpaRepository<StdNycEharsDoc, Integer> {

	/**
	 * An NYC eHARS case report for the NYC eHARS document screen: the standardized document with its current
	 * person match (if any).
	 *
	 * @param documentId document id
	 * @return the document, or {@code null} if there is none
	 */
	@Query("""
			select new gov.ny.state.doh.bhae.nyehms.web.spring.record.NycEharsDocData(stdNycEharsDoc,
					rawNycEharsDoc.currentAddress,
					personDocAssociation.stateno,
					personDocAssociation.discardStatus,
					coalesce(documentLocator.parentDocumentId, 0))
			from RawNycEharsDoc rawNycEharsDoc
			join StdNycEharsDoc stdNycEharsDoc
			on stdNycEharsDoc.documentId = rawNycEharsDoc.documentId
			join DocumentLocator documentLocator
			on documentLocator.documentId = rawNycEharsDoc.documentId
			left join PersonDocAssociation personDocAssociation
			on personDocAssociation.documentId = rawNycEharsDoc.documentId
			and personDocAssociation.dissassociationDate is null
			where rawNycEharsDoc.documentId = :documentId
			""")
	NycEharsDocData findDocument(int documentId);
}
