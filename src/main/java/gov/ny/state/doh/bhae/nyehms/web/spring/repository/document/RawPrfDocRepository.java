package gov.ny.state.doh.bhae.nyehms.web.spring.repository.document;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.document.RawPrfDoc;

/** The provider report forms as entered or received (table RAW_PRF_DOC); saveAndFlush inserts a new one. */
public interface RawPrfDocRepository extends JpaRepository<RawPrfDoc, Integer> {

	/** The forms waiting for review (status 16), by document id: rows of [FNAME, LNAME, DOCUMENT_ID]. */
	@Query("select doc.fname, doc.lname, doc.documentId from RawPrfDoc doc where doc.currentProcessStatus = 16 order by doc.documentId asc")
	List<Object[]> findForReview();

	/** The number of forms waiting for review (status 16). */
	@Query("select count(*) from RawPrfDoc doc where doc.currentProcessStatus = 16")
	long countForReview();

	/**
	 * Sets the status of a form and who reviewed it; PROCESS_DATE and ENTRY_REVIEW_DATE become the database's SYSDATE.
	 *
	 * @return the number of rows changed
	 */
	@Modifying
	@Query("""
			update RawPrfDoc doc set doc.currentProcessStatus = ?1, doc.processDate = sysdate, doc.entryReviewDate = sysdate,
					doc.entryReviewUid = ?2
			where doc.documentId = ?3
			""")
	int changeStatus(Integer currentProcessStatus, String entryReviewUid, Integer documentId);
}
