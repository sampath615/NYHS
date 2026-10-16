package gov.ny.state.doh.bhae.nyehms.web.spring.repository.document;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.document.StdBlackBoxDoc;
import gov.ny.state.doh.bhae.nyehms.web.spring.record.BlackBoxDocData;
import gov.ny.state.doh.bhae.nyehms.web.spring.record.BlackBoxRefData;

/**
 * Spring Data JPA repository for Black Box documents.
 */
public interface BlackBoxDocRepository extends JpaRepository<StdBlackBoxDoc, Integer> {

	/**
	 * a Black Box record for the Black Box document screen: the standardized document with its current
	 * person match (if any).
	 *
	 * @param documentId document id
	 * @return the document, or {@code null} if there is none
	 */
	@Query("""
			select new gov.ny.state.doh.bhae.nyehms.web.spring.record.BlackBoxDocData(stdBlackBoxDoc,
					personDocAssociation.stateno,
					coalesce(stdBlackBoxDoc.currentProcessStatus, 0),
					concat(stdBlackBoxDoc.rsdStreetAddress1, ' ', stdBlackBoxDoc.rsdStreetAddress2),
					concat(stdBlackBoxDoc.curStreetAddress1, ' ', stdBlackBoxDoc.curStreetAddress2))
			from StdBlackBoxDoc stdBlackBoxDoc
			left join PersonDocAssociation personDocAssociation
			on personDocAssociation.documentId = stdBlackBoxDoc.documentId
			and personDocAssociation.dissassociationDate is null
			where stdBlackBoxDoc.documentId = :documentId
			""")
	BlackBoxDocData findDocument(int documentId);

	/**
	 * The code lists of the Black Box document screen, by coded column and code.
	 */
	@Query("""
			select new gov.ny.state.doh.bhae.nyehms.web.spring.record.BlackBoxRefData(refData.refColumn,
					refData.lookupName, refData.lookupCd)
			from CodedStdBlackBoxRefData refData
			order by refData.refColumn, refData.lookupCd
			""")
	List<BlackBoxRefData> findRefData();
}
