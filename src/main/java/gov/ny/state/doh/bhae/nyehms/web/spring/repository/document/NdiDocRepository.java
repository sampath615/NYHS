package gov.ny.state.doh.bhae.nyehms.web.spring.repository.document;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.document.StdNdiDoc;
import gov.ny.state.doh.bhae.nyehms.web.spring.record.NdiCauseDocData;
import gov.ny.state.doh.bhae.nyehms.web.spring.record.NdiDocData;

/**
 * Spring Data JPA repository for NDI document and NDI cause of death documents.
 */
public interface NdiDocRepository extends JpaRepository<StdNdiDoc, Integer> {

	/**
	 * an NDI (National Death Index) record for the NDI document screen: the standardized document with its current
	 * person match (if any).
	 *
	 * @param documentId document id
	 * @return the document, or {@code null} if there is none
	 */
	@Query("""
			select new gov.ny.state.doh.bhae.nyehms.web.spring.record.NdiDocData(stdNdiDoc,
					personDocAssociation.stateno,
					personDocAssociation.discardStatus,
					rawNdiDoc.ndifileCat,
					coalesce(documentLocator.parentDocumentId, 0))
			from RawNdiDoc rawNdiDoc
			join StdNdiDoc stdNdiDoc
			on stdNdiDoc.documentId = rawNdiDoc.documentId
			join DocumentLocator documentLocator
			on documentLocator.documentId = rawNdiDoc.documentId
			left join PersonDocAssociation personDocAssociation
			on personDocAssociation.documentId = rawNdiDoc.documentId
			and personDocAssociation.dissassociationDate is null
			where rawNdiDoc.documentId = :documentId
			""")
	NdiDocData findDocument(int documentId);

	/**
	 * an NDI cause of death record for the NDI cause of death document screen: the standardized document with its current
	 * person match (if any).
	 *
	 * @param documentId document id
	 * @return the document, or {@code null} if there is none
	 */
	@Query("""
			select new gov.ny.state.doh.bhae.nyehms.web.spring.record.NdiCauseDocData(stdNdiCauseDoc,
					personDocAssociation.stateno,
					personDocAssociation.discardStatus)
			from StdNdiCauseDoc stdNdiCauseDoc
			left join PersonDocAssociation personDocAssociation
			on personDocAssociation.documentId = stdNdiCauseDoc.documentId
			and personDocAssociation.dissassociationDate is null
			where stdNdiCauseDoc.documentId = :documentId
			""")
	NdiCauseDocData findCauseDocument(int documentId);
}
