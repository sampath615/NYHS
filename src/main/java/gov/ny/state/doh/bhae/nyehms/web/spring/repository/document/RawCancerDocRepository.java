package gov.ny.state.doh.bhae.nyehms.web.spring.repository.document;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.document.RawCancerDoc;
import gov.ny.state.doh.bhae.nyehms.web.spring.record.CancerDocRow;
import gov.ny.state.doh.bhae.nyehms.web.spring.record.CancerReviewRow;

/**
 * The cancer registry records as received (table RAW_CANCER_DOC). A record is found by TO_NUMBER(PATIENTIDNUMBER), as
 * before; the dates are converted by the database functions FN_TO_DATE and TO_CHAR, as before.
 */
public interface RawCancerDocRepository extends JpaRepository<RawCancerDoc, String> {

	/** A record with the name of its county, for the clerical review's person view. */
	@Query("""
			select new gov.ny.state.doh.bhae.nyehms.web.spring.record.CancerDocRow(s, cast(null as String), cast(null as Integer),
					function('TO_CHAR' as String, function('FN_TO_DATE', s.dateofbirth, 'MM/DD/YYYY'), 'MM/DD/YYYY'),
					function('TO_CHAR' as String, function('FN_TO_DATE', s.dateofdeathcanada, 'MM/DD/YYYY'), 'MM/DD/YYYY'),
					c.countyName)
			from RawCancerDoc s
			left join CodedCounty c on s.personCounty = c.countyFipsCode
			where function('TO_NUMBER' as BigDecimal, s.patientidnumber) = ?1
			""")
	List<CancerDocRow> findWithCounty(BigDecimal documentId);

	/** The records with this number, with the number read as text (the person's document history). */
	@Query("""
			select new gov.ny.state.doh.bhae.nyehms.web.spring.record.CancerDocRow(s,
					function('TO_NUMBER' as String, s.patientidnumber), cast(null as Integer),
					function('TO_CHAR' as String, function('FN_TO_DATE', s.dateofbirth, 'MM/DD/YYYY'), 'MM/DD/YYYY'),
					function('TO_CHAR' as String, function('FN_TO_DATE', s.dateofdeathcanada, 'MM/DD/YYYY'), 'MM/DD/YYYY'),
					cast(null as String))
			from RawCancerDoc s
			where function('TO_NUMBER' as BigDecimal, s.patientidnumber) = ?1
			""")
	List<CancerDocRow> findHistory(BigDecimal documentId);

	/** The records with this number, with the number read as a whole number (the cancer document screen). */
	@Query("""
			select new gov.ny.state.doh.bhae.nyehms.web.spring.record.CancerDocRow(s,
					cast(null as String), function('TO_NUMBER' as Integer, s.patientidnumber),
					function('TO_CHAR' as String, function('FN_TO_DATE', s.dateofbirth, 'MM/DD/YYYY'), 'MM/DD/YYYY'),
					function('TO_CHAR' as String, function('FN_TO_DATE', s.dateofdeathcanada, 'MM/DD/YYYY'), 'MM/DD/YYYY'),
					cast(null as String))
			from RawCancerDoc s
			where function('TO_NUMBER' as BigDecimal, s.patientidnumber) = ?1
			""")
	List<CancerDocRow> findDocument(BigDecimal documentId);

	/** The number of records with this process status and Dataflux match code. */
	long countByCurrentProcessStatusAndFluxMatchCd(Integer currentProcessStatus, String fluxMatchCd);

	/**
	 * A record to review (status 17, match code 'C'), picked pseudo-randomly as before: the lowest
	 * MOD(TO_NUMBER(PATIENTIDNUMBER) * the current fraction of a second, 1500).
	 */
	@Query("""
			select new gov.ny.state.doh.bhae.nyehms.web.spring.record.CancerReviewRow(
					function('TO_NUMBER' as BigDecimal, s.patientidnumber), s.fluxMatchCd, s.fluxPotStateno)
			from RawCancerDoc s
			where s.currentProcessStatus = 17 and s.fluxMatchCd = 'C'
			order by mod(function('TO_NUMBER' as Long, s.patientidnumber)
					* function('TO_NUMBER' as Long, function('TO_CHAR' as String, current_timestamp, 'FF')), 1500)
			fetch first 1 rows only
			""")
	List<CancerReviewRow> findOneToReview();

	/**
	 * Records the matched state number of a record still to review ('C', no matched state number): FLUX_MATCH_CD
	 * becomes 'C_M', REVIEW_DATE the database's SYSDATE.
	 *
	 * @return the number of rows changed
	 */
	@Modifying
	@Query("""
			update RawCancerDoc s set s.fluxMatchedStateno = ?1, s.reviewUid = ?2, s.reviewDate = sysdate,
					s.fluxMatchCd = concat(s.fluxMatchCd, '_M')
			where function('TO_NUMBER' as BigDecimal, s.patientidnumber) = ?3 and s.fluxMatchedStateno is null and s.fluxMatchCd = 'C'
			""")
	int updateMatch(String stateno, String reviewUid, BigDecimal documentId);

	/**
	 * Marks a record still to review ('C') as not a match: FLUX_MATCH_CD becomes 'C_N', REVIEW_DATE the database's
	 * SYSDATE.
	 *
	 * @return the number of rows changed
	 */
	@Modifying
	@Query("""
			update RawCancerDoc s set s.fluxMatchCd = concat(s.fluxMatchCd, '_N'), s.reviewUid = ?1, s.reviewDate = sysdate
			where function('TO_NUMBER' as BigDecimal, s.patientidnumber) = ?2 and s.fluxMatchCd = 'C'
			""")
	int updateNotAMatch(String reviewUid, BigDecimal documentId);
}
