package gov.ny.state.doh.bhae.nyehms.web.spring.repository.document;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.document.StdTbDoc;
import gov.ny.state.doh.bhae.nyehms.web.spring.record.TbDocData;

/**
 * Spring Data JPA repository for the standardized TB records (replaces TBDocumentDAO.getTBDoc; the import and the
 * document copy save the record with saveAndFlush, which replaces writeRawTBDocDataToStdTable and
 * insertStdEditedTBDocument).
 */
public interface TbDocRepository extends JpaRepository<StdTbDoc, Integer> {

	/**
	 * A TB record for the document screen: the standardized record, the addresses as received, its current person
	 * match (if any) and its parent document.
	 *
	 * @param documentId document id of the record
	 * @return the record, or null if there is none
	 */
	@Query("""
			select new gov.ny.state.doh.bhae.nyehms.web.spring.record.TbDocData(stdTbDoc,
					rawTbDoc.facilityAddress,
					rawTbDoc.provideraddress,
					rawTbDoc.street,
					personDocAssociation.stateno,
					personDocAssociation.discardStatus,
					coalesce(documentLocator.parentDocumentId, 0))
			from StdTbDoc stdTbDoc
			join RawTbDoc rawTbDoc
			on rawTbDoc.documentId = stdTbDoc.documentId
			join DocumentLocator documentLocator
			on documentLocator.documentId = rawTbDoc.documentId
			left join PersonDocAssociation personDocAssociation
			on personDocAssociation.documentId = stdTbDoc.documentId
			and personDocAssociation.dissassociationDate is null
			where stdTbDoc.documentId = :documentId
			""")
	TbDocData findDocument(int documentId);
}
