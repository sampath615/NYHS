package gov.ny.state.doh.bhae.nyehms.web.spring.repository.document;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.document.StdMedOldDoc;
import gov.ny.state.doh.bhae.nyehms.web.spring.record.MedicaidOldDocData;

/**
 * Spring Data JPA repository for Medicaid Old documents.
 */
public interface MedicaidOldDocRepository extends JpaRepository<StdMedOldDoc, Integer> {

	/**
	 * an old Medicaid report for the Medicaid Old document screen: the standardized document with its current
	 * person match (if any).
	 *
	 * @param documentId document id
	 * @return the document, or {@code null} if there is none
	 */
	@Query("""
			select new gov.ny.state.doh.bhae.nyehms.web.spring.record.MedicaidOldDocData(stdMedOldDoc,
					personDocAssociation.stateno,
					personDocAssociation.discardStatus,
					rawMedOldDoc.address,
					rawMedOldDoc.provAddr)
			from RawMedOldDoc rawMedOldDoc
			join StdMedOldDoc stdMedOldDoc
			on stdMedOldDoc.documentId = rawMedOldDoc.documentId
			left join PersonDocAssociation personDocAssociation
			on personDocAssociation.documentId = rawMedOldDoc.documentId
			and personDocAssociation.dissassociationDate is null
			where rawMedOldDoc.documentId = :documentId
			""")
	MedicaidOldDocData findDocument(int documentId);
}
