package gov.ny.state.doh.bhae.nyehms.web.spring.repository.document;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.document.StdStdConfirmedDoc;
import gov.ny.state.doh.bhae.nyehms.web.spring.record.StdConfDocData;

/**
 * Spring Data JPA repository for the confirmed STD document (replaces STDConfDocumentDAO.getSTDConfDoc).
 */
public interface StdConfDocRepository extends JpaRepository<StdStdConfirmedDoc, Integer> {

	/**
	 * A confirmed STD (sexually transmitted disease) report for the confirmed STD document screen: the standardized document with its current
	 * person match (if any).
	 *
	 * @param documentId document id
	 * @return the document, or {@code null} if there is none
	 */
	@Query("""
			select new gov.ny.state.doh.bhae.nyehms.web.spring.record.StdConfDocData(stdStdConfirmedDoc,
					personDocAssociation.stateno,
					format(stdStdConfirmedDoc.dob as 'MM/dd/yyyy'),
					format(stdStdConfirmedDoc.diagDate as 'MM/dd/yyyy'),
					format(stdStdConfirmedDoc.dateOfDeath as 'MM/dd/yyyy'),
					format(stdStdConfirmedDoc.x900dxdate as 'MM/dd/yyyy'),
					rawStdConfirmedDoc.personAddress,
					rawStdConfirmedDoc.provAddress)
			from RawStdConfirmedDoc rawStdConfirmedDoc
			join StdStdConfirmedDoc stdStdConfirmedDoc
			on stdStdConfirmedDoc.documentId = rawStdConfirmedDoc.documentId
			left join PersonDocAssociation personDocAssociation
			on personDocAssociation.documentId = rawStdConfirmedDoc.documentId
			and personDocAssociation.dissassociationDate is null
			where rawStdConfirmedDoc.documentId = :documentId
			""")
	StdConfDocData findDocument(int documentId);
}
