package gov.ny.state.doh.bhae.nyehms.web.spring.repository.document;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.document.StdNycijrDoc;
import gov.ny.state.doh.bhae.nyehms.web.spring.record.NycIjrDocData;

/**
 * Spring Data JPA repository for the NYC IJR records (replaces NycIJRDocumentDAO; the import saves the standardized
 * record with saveAndFlush, which replaces NycIJRDocumentDAO.writeStdDocData).
 */
public interface NycijrDocRepository extends JpaRepository<StdNycijrDoc, Integer> {

	/**
	 * An NYC IJR record for the document screen: the standardized record, the addresses as received, its current
	 * person match (if any) and its parent document.
	 *
	 * @param documentId document id of the record
	 * @return the record, or null if there is none
	 */
	@Query("""
			select new gov.ny.state.doh.bhae.nyehms.web.spring.record.NycIjrDocData(stdNycijrDoc,
					rawNycijrDoc.address,
					rawNycijrDoc.curStreetAddress1,
					rawNycijrDoc.provAddr,
					personDocAssociation.stateno,
					personDocAssociation.discardStatus,
					coalesce(stdNycijrDoc.numberofpartners, 0),
					coalesce(documentLocator.parentDocumentId, 0))
			from RawNycijrDoc rawNycijrDoc
			join StdNycijrDoc stdNycijrDoc
			on stdNycijrDoc.documentId = rawNycijrDoc.documentId
			join DocumentLocator documentLocator
			on documentLocator.documentId = rawNycijrDoc.documentId
			left join PersonDocAssociation personDocAssociation
			on personDocAssociation.documentId = rawNycijrDoc.documentId
			and personDocAssociation.dissassociationDate is null
			where rawNycijrDoc.documentId = :documentId
			""")
	NycIjrDocData findDocument(int documentId);

	/** The files that still have records waiting for clerical review (CURRENT_PROCESS_STATUS 17, not reviewed). */
	@Query("""
			select distinct stdNycijrDoc.fileName from StdNycijrDoc stdNycijrDoc
			where stdNycijrDoc.reviewDt is null and stdNycijrDoc.currentProcessStatus = 17
			""")
	List<String> findFileNamesToReview();
}
