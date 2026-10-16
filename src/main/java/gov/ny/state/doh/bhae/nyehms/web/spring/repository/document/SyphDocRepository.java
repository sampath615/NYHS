package gov.ny.state.doh.bhae.nyehms.web.spring.repository.document;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.document.StdSyphilisDoc;
import gov.ny.state.doh.bhae.nyehms.web.spring.record.SyphDocData;

/**
 * Spring Data JPA repository for the syphilis document (replaces SYPHDocumentDAO.getSYPHDoc).
 */
public interface SyphDocRepository extends JpaRepository<StdSyphilisDoc, Integer> {

	/**
	 * A syphilis report for the syphilis document screen: the standardized document with its current
	 * person match (if any).
	 *
	 * @param documentId document id
	 * @return the document, or {@code null} if there is none
	 */
	@Query("""
			select new gov.ny.state.doh.bhae.nyehms.web.spring.record.SyphDocData(stdSyphilisDoc,
					personDocAssociation.stateno,
					format(stdSyphilisDoc.dob as 'MM/dd/yyyy'),
					format(stdSyphilisDoc.dod as 'MM/dd/yyyy'),
					rawSyphilisDoc.street,
					format(stdSyphilisDoc.syphilisDiagnosisDate as 'MM/dd/yyyy'),
					format(stdSyphilisDoc.receivedDate as 'MM/dd/yyyy'),
					rawSyphilisDoc.provideraddress,
					'STD',
					coalesce(documentLocator.parentDocumentId, 0))
			from RawSyphilisDoc rawSyphilisDoc
			join StdSyphilisDoc stdSyphilisDoc
			on stdSyphilisDoc.documentId = rawSyphilisDoc.documentId
			join DocumentLocator documentLocator
			on documentLocator.documentId = rawSyphilisDoc.documentId
			left join PersonDocAssociation personDocAssociation
			on personDocAssociation.documentId = rawSyphilisDoc.documentId
			and personDocAssociation.dissassociationDate is null
			where rawSyphilisDoc.documentId = :documentId
			""")
	SyphDocData findDocument(int documentId);
}
