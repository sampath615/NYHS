package gov.ny.state.doh.bhae.nyehms.web.spring.repository.document;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.document.StdAirsDoc;
import gov.ny.state.doh.bhae.nyehms.web.spring.record.AirsDocData;

/**
 * Spring Data JPA repository for the AIRS document (replaces AIRSDocumentDAO.getAIRSDoc).
 */
public interface AirsDocRepository extends JpaRepository<StdAirsDoc, Integer> {

	/**
	 * An AIRS (AIDS Institute Reporting System) report for the AIRS document screen: the standardized document with its current
	 * person match (if any).
	 *
	 * @param documentId document id
	 * @return the document, or {@code null} if there is none
	 */
	@Query("""
			select new gov.ny.state.doh.bhae.nyehms.web.spring.record.AirsDocData(stdAirsDoc,
					personDocAssociation.stateno,
					personDocAssociation.discardStatus,
					format(stdAirsDoc.dob as 'MM/dd/yyyy'),
					rawAirsDoc.address,
					rawAirsDoc.provAddr,
					'STD',
					coalesce(documentLocator.parentDocumentId, 0))
			from RawAirsDoc rawAirsDoc
			join StdAirsDoc stdAirsDoc
			on stdAirsDoc.documentId = rawAirsDoc.documentId
			join DocumentLocator documentLocator
			on documentLocator.documentId = rawAirsDoc.documentId
			left join PersonDocAssociation personDocAssociation
			on personDocAssociation.documentId = rawAirsDoc.documentId
			and personDocAssociation.dissassociationDate is null
			where rawAirsDoc.documentId = :documentId
			""")
	AirsDocData findDocument(int documentId);
}
