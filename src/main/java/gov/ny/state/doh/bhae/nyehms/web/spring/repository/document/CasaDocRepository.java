package gov.ny.state.doh.bhae.nyehms.web.spring.repository.document;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.document.StdCasaDoc;
import gov.ny.state.doh.bhae.nyehms.web.spring.record.CasaDocData;

/**
 * Spring Data JPA repository for the CASA document (replaces CASADocumentDAO.getCASADoc).
 */
public interface CasaDocRepository extends JpaRepository<StdCasaDoc, Integer> {

	/**
	 * A CASA report for the CASA document screen: the standardized document with its current
	 * person match (if any).
	 *
	 * @param documentId document id
	 * @return the document, or {@code null} if there is none
	 */
	@Query("""
			select new gov.ny.state.doh.bhae.nyehms.web.spring.record.CasaDocData(stdCasaDoc,
					personDocAssociation.stateno,
					personDocAssociation.discardStatus,
					coalesce(documentLocator.parentDocumentId, 0))
			from RawCasaDoc rawCasaDoc
			join StdCasaDoc stdCasaDoc
			on stdCasaDoc.documentId = rawCasaDoc.documentId
			join DocumentLocator documentLocator
			on documentLocator.documentId = rawCasaDoc.documentId
			left join PersonDocAssociation personDocAssociation
			on personDocAssociation.documentId = rawCasaDoc.documentId
			and personDocAssociation.dissassociationDate is null
			where rawCasaDoc.documentId = :documentId
			""")
	CasaDocData findDocument(int documentId);
}
