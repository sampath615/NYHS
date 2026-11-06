package gov.ny.state.doh.bhae.nyehms.web.spring.repository.common;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.common.RasSiResponse;
import gov.ny.state.doh.bhae.nyehms.web.spring.record.SierraResponseRow;

/** The headers of the Sierra resistance responses (table RAS_SI_RESPONSE); saveAndFlush inserts a new one. */
public interface RasSiResponseRepository extends JpaRepository<RasSiResponse, Long> {

	/** The responses of a lab document with the person of its standardized HIV lab report (one row per pair). */
	@Query("""
			select new gov.ny.state.doh.bhae.nyehms.web.spring.record.SierraResponseRow(hiv.fname, hiv.lname, hiv.mname,
					hiv.dobText, response.seqResultId, response.documentId, hiv.calcCollectionDate, response.subType)
			from RasSiResponse response, StdHivEclrsDoc hiv
			where response.documentId = ?1
			and response.documentId = hiv.documentId
			""")
	List<SierraResponseRow> findWithLabReport(int documentId);

	/** The number of responses of a lab document (0 for a null id, as {@code = NULL} matched nothing). */
	@Query("select count(*) from RasSiResponse response where response.documentId = ?1")
	long countOfDocument(Integer documentId);
}
