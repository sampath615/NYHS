package gov.ny.state.doh.bhae.nyehms.web.spring.repository.document;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.document.StdDeathDoc;
import gov.ny.state.doh.bhae.nyehms.web.spring.record.DeathDocData;

/**
 * Spring Data JPA repository for the death document (replaces DeathDocumentDAO.getDeathDoc).
 */
public interface DeathDocRepository extends JpaRepository<StdDeathDoc, Integer> {

	/**
	 * A death certificate for the death document screen: the standardized document with its current
	 * person match (if any).
	 *
	 * @param documentId document id
	 * @return the document, or {@code null} if there is none
	 */
	@Query("""
			select new gov.ny.state.doh.bhae.nyehms.web.spring.record.DeathDocData(stdDeathDoc,
					personDocAssociation.stateno,
					personDocAssociation.discardStatus,
					rawDeathDoc.resStreetType,
					rawDeathDoc.resAptNo,
					rawDeathDoc.resStreetNo,
					rawDeathDoc.resStreet,
					rawDeathDoc.resStreetDirect)
			from RawDeathDoc rawDeathDoc
			join StdDeathDoc stdDeathDoc
			on stdDeathDoc.documentId = rawDeathDoc.documentId
			left join PersonDocAssociation personDocAssociation
			on personDocAssociation.documentId = rawDeathDoc.documentId
			and personDocAssociation.dissassociationDate is null
			where rawDeathDoc.documentId = :documentId
			""")
	DeathDocData findDocument(int documentId);
}
