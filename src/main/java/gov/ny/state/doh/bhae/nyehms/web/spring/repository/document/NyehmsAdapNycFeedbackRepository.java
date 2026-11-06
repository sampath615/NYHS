package gov.ny.state.doh.bhae.nyehms.web.spring.repository.document;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.document.NyehmsAdapNycFeedback;
import gov.ny.state.doh.bhae.nyehms.web.spring.record.AdapNycFileSummaryRow;

/** The NYC feedback on the ADAP records shipped to NYC (table NYEHMS_ADAP_NYC_FEEDBACK). */
public interface NyehmsAdapNycFeedbackRepository extends JpaRepository<NyehmsAdapNycFeedback, Long> {

	/** The feedback rows of an ADAP document (one expected; a repeated STDRPTS_ID gives the same entity twice). */
	List<NyehmsAdapNycFeedback> findByStdrptsId(Long stdrptsId);

	/** The ADAP documents whose feedback is not reviewed yet (null ids listed as null). */
	@Query("select f.stdrptsId from NyehmsAdapNycFeedback f where f.reviewedDate is null")
	List<Long> findUnreviewedDocumentIds();

	/** The files all of whose feedback is reviewed. */
	@Query("""
			select distinct f.adapFile from NyehmsAdapNycFeedback f
			where f.adapFile not in (select distinct f2.adapFile from NyehmsAdapNycFeedback f2 where f2.reviewedDate is null)
			""")
	List<String> findReviewedFileNames();

	/** The feedback of a file with the persons its documents are associated with (open associations), without repeats. */
	@Query("""
			select distinct new gov.ny.state.doh.bhae.nyehms.web.spring.record.AdapNycFileSummaryRow(p.stateno, f.nycnumber, f.reviewedCityno, f.reviewStatus, f.reviewComments)
			from NyehmsAdapNycFeedback f, PersonDocAssociation p
			where f.adapFile = ?1 and p.documentId = f.stdrptsId and p.dissassociationDate is null
			""")
	List<AdapNycFileSummaryRow> findFileSummary(String adapFile);

	/** Records the review of a document's feedback that is not reviewed yet, now (the database's SYSDATE). */
	@Modifying
	@Query("""
			update NyehmsAdapNycFeedback f set f.reviewedId = ?1, f.reviewedDate = sysdate, f.reviewStatus = ?2, f.reviewedCityno = ?3,
				f.reviewComments = ?4
			where f.stdrptsId = ?5 and f.reviewedDate is null
			""")
	int review(String workerId, String reviewStatus, String reviewedCityno, String reviewComments, Long stdrptsId);
}
