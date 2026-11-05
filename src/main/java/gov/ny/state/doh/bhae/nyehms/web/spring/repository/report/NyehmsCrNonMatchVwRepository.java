package gov.ny.state.doh.bhae.nyehms.web.spring.repository.report;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.report.NyehmsCrNonMatchVw;
import gov.ny.state.doh.bhae.nyehms.web.spring.record.ClericalNonMatchRow;

/** The clerical review non-matches (view NYEHMS_CR_NON_MATCH_VW). */
public interface NyehmsCrNonMatchVwRepository extends JpaRepository<NyehmsCrNonMatchVw, String> {

	/**
	 * The non-matches reviewed between two days (MM/DD/YYYY texts, read by the database's TO_DATE as before; the
	 * review date compared with its time, as before), in the view's order.
	 */
	@Query("""
			select new gov.ny.state.doh.bhae.nyehms.web.spring.record.ClericalNonMatchRow(v.stateno, v.documentTypeCode,
			       v.reviewedBy, v.reviewDate)
			from NyehmsCrNonMatchVw v
			where v.reviewDate between cast(to_date(:fromDate, 'mm/dd/yyyy') as Timestamp) and cast(to_date(:toDate, 'mm/dd/yyyy') as Timestamp)
			""")
	List<ClericalNonMatchRow> findReviewedBetween(@Param("fromDate") Object fromDate, @Param("toDate") Object toDate);
}
