package gov.ny.state.doh.bhae.nyehms.web.spring.repository.document;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.document.StdNycDeathDoc;
import gov.ny.state.doh.bhae.nyehms.web.spring.record.NycDeathDocData;

/**
 * Spring Data JPA repository for the NYC death document (replaces NycDeathDocumentDAO.getNycDeathDoc).
 */
public interface NycDeathDocRepository extends JpaRepository<StdNycDeathDoc, Integer> {

	/**
	 * An NYC death certificate for the NYC death document screen: the standardized document with its current
	 * person match (if any).
	 *
	 * @param documentId document id
	 * @return the document, or {@code null} if there is none
	 */
	@Query("""
			select new gov.ny.state.doh.bhae.nyehms.web.spring.record.NycDeathDocData(stdNycDeathDoc,
					personDocAssociation.stateno,
					personDocAssociation.discardStatus,
					rawNycDeathDoc.resStreetNo,
					rawNycDeathDoc.resStreetDirect,
					rawNycDeathDoc.resStreet,
					rawNycDeathDoc.resStreetType,
					rawNycDeathDoc.resAptNo)
			from RawNycDeathDoc rawNycDeathDoc
			join StdNycDeathDoc stdNycDeathDoc
			on stdNycDeathDoc.documentId = rawNycDeathDoc.documentId
			left join PersonDocAssociation personDocAssociation
			on personDocAssociation.documentId = rawNycDeathDoc.documentId
			and personDocAssociation.dissassociationDate is null
			where rawNycDeathDoc.documentId = :documentId
			""")
	NycDeathDocData findDocument(int documentId);
}
