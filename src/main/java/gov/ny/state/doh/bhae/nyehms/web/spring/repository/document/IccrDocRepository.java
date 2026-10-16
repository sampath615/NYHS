package gov.ny.state.doh.bhae.nyehms.web.spring.repository.document;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.document.StdIccrDoc;
import gov.ny.state.doh.bhae.nyehms.web.spring.record.IccrDocData;

/**
 * Spring Data JPA repository for the ICCR document (replaces ICCRDocumentDAO.getICCRDocument).
 */
public interface IccrDocRepository extends JpaRepository<StdIccrDoc, Integer> {

	/**
	 * An ICCR (Infection Control) report for the ICCR document screen: the standardized document with its current
	 * person match (if any).
	 *
	 * @param documentId document id
	 * @return the document, or {@code null} if there is none
	 */
	@Query("""
			select new gov.ny.state.doh.bhae.nyehms.web.spring.record.IccrDocData(stdIccrDoc,
					personDocAssociation.stateno,
					format(stdIccrDoc.dob as 'MM/dd/yyyy'),
					rawIccrDoc.personAddress,
					rawIccrDoc.provAddress,
					codedYesNoUnk.ansDesc,
					coalesce(documentLocator.parentDocumentId, 0))
			from RawIccrDoc rawIccrDoc
			join StdIccrDoc stdIccrDoc
			on stdIccrDoc.documentId = rawIccrDoc.documentId
			left join CodedYesNoUnk codedYesNoUnk
			on codedYesNoUnk.ansCode = stdIccrDoc.resultsReceived
			join DocumentLocator documentLocator
			on documentLocator.documentId = rawIccrDoc.documentId
			left join PersonDocAssociation personDocAssociation
			on personDocAssociation.documentId = rawIccrDoc.documentId
			and personDocAssociation.dissassociationDate is null
			where rawIccrDoc.documentId = :documentId
			""")
	IccrDocData findDocument(int documentId);
}
