package gov.ny.state.doh.bhae.nyehms.web.spring.repository.document;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.document.StdInsDeathDoc;
import gov.ny.state.doh.bhae.nyehms.web.spring.record.InsDeathDocData;

/**
 * Spring Data JPA repository for the insurance death document (replaces MedicaidDocumentDAO.getInsDthDoc).
 */
public interface InsDeathDocRepository extends JpaRepository<StdInsDeathDoc, Integer> {

	/**
	 * An insurance death record for the insurance death document screen: the standardized document with its current
	 * person match (if any).
	 *
	 * @param documentId document id
	 * @return the document, or {@code null} if there is none
	 */
	@Query("""
			select new gov.ny.state.doh.bhae.nyehms.web.spring.record.InsDeathDocData(stdInsDeathDoc,
					coalesce(stdInsDeathDoc.currentProcessStatus, 0),
					personDocAssociation.stateno,
					personDocAssociation.discardStatus,
					coalesce(documentLocator.parentDocumentId, 0))
			from StdInsDeathDoc stdInsDeathDoc
			left join PersonDocAssociation personDocAssociation
			on personDocAssociation.documentId = stdInsDeathDoc.documentId
			and personDocAssociation.dissassociationDate is null
			join DocumentLocator documentLocator
			on documentLocator.documentId = stdInsDeathDoc.documentId
			where stdInsDeathDoc.documentId = :documentId
			""")
	InsDeathDocData findDocument(int documentId);
}
