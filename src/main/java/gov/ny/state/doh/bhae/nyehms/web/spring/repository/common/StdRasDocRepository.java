package gov.ny.state.doh.bhae.nyehms.web.spring.repository.common;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.common.StdRasDoc;
import gov.ny.state.doh.bhae.nyehms.web.spring.record.RasDocRow;

/** The standardized resistance (RAS) documents (table STD_RAS_DOC). */
public interface StdRasDocRepository extends JpaRepository<StdRasDoc, Integer> {

	/**
	 * A resistance document that has a DOCUMENT_LOCATOR row, with the STATENO of its current person match (one row per
	 * current match; none if there is no such document).
	 */
	@Query("""
			select new gov.ny.state.doh.bhae.nyehms.web.spring.record.RasDocRow(doc, pdoc.stateno)
			from StdRasDoc doc
			join DocumentLocator locator on doc.documentId = locator.documentId and doc.documentId = ?1
			left join PersonDocAssociation pdoc on doc.documentId = pdoc.documentId and pdoc.dissassociationDate is null
			""")
	List<RasDocRow> findWithMatchedStateno(int documentId);

	/**
	 * Sets CURRENT_PROCESS_STATUS 9 on the linked documents (status 5) that name a resistance document in one of their
	 * three RESISTANCE_DOC_ID columns.
	 *
	 * @return the number of rows changed
	 */
	@Modifying
	@Query("""
			update StdRasDoc doc set doc.currentProcessStatus = 9
			where doc.currentProcessStatus = 5
			and (doc.resistanceDocId1 = ?1 or doc.resistanceDocId2 = ?1 or doc.resistanceDocId3 = ?1)
			""")
	int deactivateLinkedDocuments(int resistanceDocId);
}
