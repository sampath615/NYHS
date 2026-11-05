package gov.ny.state.doh.bhae.nyehms.web.spring.repository.common;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.common.NycSurveillanceReject;
import gov.ny.state.doh.bhae.nyehms.web.spring.record.NycRejectRow;

/** The rejected NYC surveillance records (table NYC_SURVEILLANCE_REJECT). */
public interface NycSurveillanceRejectRepository extends JpaRepository<NycSurveillanceReject, Integer> {

	/**
	 * The records of a reject document (by REJECT_DOCUMENT_ID) whose document is filed (DOCUMENT_LOCATOR), with the
	 * current state number (the PL/SQL function PKG_CONSOLIDATION.FN_GIVE_CURRENT_STATENO, passed through) and the
	 * disposition's descriptions; in database order.
	 */
	@Query("""
			select new gov.ny.state.doh.bhae.nyehms.web.spring.record.NycRejectRow(r.documentId,
					function('PKG_CONSOLIDATION.FN_GIVE_CURRENT_STATENO' as String, r.stateno),
					r.nycnumber, r.rejectDocumentId, r.dispositionCode, c.dispositionDesc, c.dispositionDescShort,
					r.statusnotes, r.statusnoncase, r.statusUpdateDate, r.stateno2, r.uploadFilename, r.uploadUid,
					r.uploadDate, r.comments, r.updateUid, r.updateDateText)
			from NycSurveillanceReject r
			join DocumentLocator d on r.documentId = d.documentId and r.rejectDocumentId = ?1
			left join CodedNycRejectDisposition c on r.dispositionCode = c.dispositionCode
			""")
	List<NycRejectRow> findDetails(Integer rejectDocumentId);

	/**
	 * Sets the comments of the records of a document (by DOCUMENT_ID), UPDATE_DATE (the database's SYSDATE) and
	 * UPDATE_UID.
	 *
	 * @return the number of rows changed
	 */
	@Modifying
	@Query("update NycSurveillanceReject r set r.comments = ?1, r.updateDate = sysdate, r.updateUid = ?2 where r.documentId = ?3")
	int updateComments(String comments, String updateUid, Integer documentId);
}
