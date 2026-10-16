package gov.ny.state.doh.bhae.nyehms.web.spring.repository.document;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.document.StdSsdmDoc;
import gov.ny.state.doh.bhae.nyehms.web.spring.record.SsdmDocData;

/**
 * Spring Data JPA repository for the SSDM document (replaces SSDMDocumentDAO.getSSDMDoc).
 */
public interface SsdmDocRepository extends JpaRepository<StdSsdmDoc, Integer> {

	/**
	 * An SSDM (Social Security Death Master) record for the SSDM document screen: the standardized document with its current
	 * person match (if any).
	 *
	 * @param documentId document id
	 * @return the document, or {@code null} if there is none
	 */
	@Query("""
			select new gov.ny.state.doh.bhae.nyehms.web.spring.record.SsdmDocData(stdSsdmDoc,
					personDocAssociation.stateno,
					personDocAssociation.discardStatus,
					coalesce(documentLocator.parentDocumentId, 0))
			from StdSsdmDoc stdSsdmDoc
			left join PersonDocAssociation personDocAssociation
			on personDocAssociation.documentId = stdSsdmDoc.documentId
			and personDocAssociation.dissassociationDate is null
			join DocumentLocator documentLocator
			on documentLocator.documentId = stdSsdmDoc.documentId
			where stdSsdmDoc.documentId = :documentId
			""")
	SsdmDocData findDocument(int documentId);
}
