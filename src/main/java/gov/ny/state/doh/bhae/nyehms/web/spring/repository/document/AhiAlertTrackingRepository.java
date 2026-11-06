package gov.ny.state.doh.bhae.nyehms.web.spring.repository.document;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.document.AhiAlertTracking;
import gov.ny.state.doh.bhae.nyehms.web.spring.record.AhiAlertDetailsRow;
import gov.ny.state.doh.bhae.nyehms.web.spring.record.AhiAlertListRow;

/** The AHI alerts (table AHI_ALERT_TRACKING). */
public interface AhiAlertTrackingRepository extends JpaRepository<AhiAlertTracking, Long> {

	/**
	 * Every alert with each of its follow-up steps (one row per step, one row without step if there is none), the
	 * descriptions of its codes, the workers' full names (the PL/SQL function PKG_TRK_COMMON.fn_get_fullname, passed
	 * through) and whether a partner services message was sent for the person.
	 */
	@Query("""
			select new gov.ny.state.doh.bhae.nyehms.web.spring.record.AhiAlertListRow(t, src.ahiAlertDesc, ap.ansDesc,
					function('pkg_trk_common.fn_get_fullname' as String, usr1.lastName, usr1.firstName, usr1.middleName, null),
					function('pkg_trk_common.fn_get_fullname' as String, usr2.lastName, usr2.firstName, usr2.middleName, null),
					function('pkg_trk_common.fn_get_fullname' as String, usr3.lastName, usr3.firstName, usr3.middleName, null),
					function('pkg_trk_common.fn_get_fullname' as String, usr4.lastName, usr4.firstName, usr4.middleName, null),
					f.followUpCode, f.followUpDate, cf.followUpCode, cf.followUpDesc,
					case when exists (select 1 from NyehmsStdMessageSent a where a.stateno = t.stateno and a.processInd in ('Y', 'T')) then 'Y' else 'N' end)
			from AhiAlertTracking t
			left join CodedAhiAlertSrc src on t.ahiAlertCode = src.ahiAlertCode
			left join CodedActivePassive ap on t.prfCollection = ap.ansCode
			left join NyehmsUsers usr1 on t.ahiCreateUid = usr1.workerId
			left join NyehmsUsers usr2 on t.lastUpdatedBy = usr2.workerId
			left join NyehmsUsers usr3 on t.alertCompletedBy = usr3.workerId
			left join AhiAlertFollowup f on t.ahiAlertSeqId = f.ahiAlertSeqId
			left join CodedAhiPrfFollowup cf on f.followUpCode = cf.followUpCode
			left join NyehmsUsers usr4 on t.ahiAlertReviewedBy = usr4.workerId
			""")
	List<AhiAlertListRow> findAllWithFollowUps();

	/**
	 * An alert for its tracking form: the workers' full names (NYEHMS_USERS_VW; the view is also joined, unused, on the
	 * creator and the completing worker, as before), the descriptions of its codes, the person's open assignment flagged
	 * AHI and the triggering document type of the open assignments of the hard-coded person '0010930066' (a bug kept as
	 * it was). The scalar subqueries fail when they find more than one row, as before.
	 */
	@Query("""
			select new gov.ny.state.doh.bhae.nyehms.web.spring.record.AhiAlertDetailsRow(t, usr1.fullName, src.ahiAlertDesc, ap.ansDesc, usr2.fullName,
					(select sa.documentId from SurveillanceAssignment sa where sa.stateno = t.stateno and sa.closedDate is null and sa.ahiFlag = 'Y'),
					(select case when d.documentTypeCode in ('HIV_LAB', 'EPRF_OOC', 'ACRF', 'CLUSTER', 'PCRF', 'SCAN') then d.testTypeCd
								else d.documentTypeCode end
						from DocumentLocator d
						where d.documentId in (select sa2.triggeringDocId from SurveillanceAssignment sa2 where sa2.stateno = '0010930066' and sa2.closedDate is null)))
			from AhiAlertTracking t
			left join NyehmsUsersVw usr on t.ahiCreateUid = usr.workerId
			left join CodedAhiAlertSrc src on t.ahiAlertCode = src.ahiAlertCode
			left join CodedActivePassive ap on t.prfCollection = ap.ansCode
			left join NyehmsUsersVw usr1 on t.ahiCreateUid = usr1.workerId
			left join NyehmsUsersVw usr2 on t.lastUpdatedBy = usr2.workerId
			left join NyehmsUsersVw usr3 on t.alertCompletedBy = usr3.workerId
			where t.ahiAlertSeqId = ?1
			""")
	List<AhiAlertDetailsRow> findDetails(Long ahiAlertSeqId);

	/** Records the PRF collection answer and the worker who changed it, now (the database's SYSDATE). */
	@Modifying
	@Query("update AhiAlertTracking t set t.prfCollection = ?1, t.lastUpdatedBy = ?2, t.lastUpdatedDate = sysdate where t.ahiAlertSeqId = ?3")
	int updatePrfCollection(String prfCollection, Long lastUpdatedBy, Long ahiAlertSeqId);

	/** As {@link #updatePrfCollection}, and completes the alert by a worker now. */
	@Modifying
	@Query("""
			update AhiAlertTracking t set t.prfCollection = ?1, t.lastUpdatedBy = ?2, t.lastUpdatedDate = sysdate,
				t.alertCompletedBy = ?3, t.alertCompletedDate = sysdate
			where t.ahiAlertSeqId = ?4
			""")
	int updatePrfCollectionAndComplete(String prfCollection, Long lastUpdatedBy, Long alertCompletedBy, Long ahiAlertSeqId);

	/** Marks an alert reviewed by a worker now (the database's SYSDATE). */
	@Modifying
	@Query("update AhiAlertTracking t set t.ahiAlertStatus = 'REVIEWED', t.ahiAlertReviewedBy = ?1, t.ahiAlertReviewedDate = sysdate where t.ahiAlertSeqId = ?2")
	int markReviewed(Long ahiAlertReviewedBy, Long ahiAlertSeqId);
}
