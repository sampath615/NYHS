package gov.ny.state.doh.bhae.nyehms.web.spring.repository.document;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.document.StdOldCasesDoc;
import gov.ny.state.doh.bhae.nyehms.web.spring.record.OldCaseDocData;

/**
 * Spring Data JPA repository for old case reports (replaces the MyBatis OldCasesDocumentDAO).
 */
public interface OldCasesDocRepository extends JpaRepository<StdOldCasesDoc, Integer> {

	/**
	 * An old case report for the document screen: the standardized report and the STATENO it is
	 * currently matched to (if any).
	 *
	 * @param documentId document id of the report
	 * @return the report, or {@code null} if there is none
	 */
	@Query("""
			select new gov.ny.state.doh.bhae.nyehms.web.spring.record.OldCaseDocData(stdOldCasesDoc,
					personDocAssociation.stateno, personDocAssociation.discardStatus)
			from StdOldCasesDoc stdOldCasesDoc
			left join PersonDocAssociation personDocAssociation
			on personDocAssociation.documentId = stdOldCasesDoc.documentId
			and personDocAssociation.dissassociationDate is null
			where stdOldCasesDoc.documentId = :documentId
			""")
	OldCaseDocData findDocument(int documentId);
}
