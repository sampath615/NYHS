package gov.ny.state.doh.bhae.nyehms.web.spring.repository.common;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.common.StdScanDoc;
import gov.ny.state.doh.bhae.nyehms.web.spring.record.ScanningCountRow;

/** The scanned documents (table STD_SCAN_DOC). */
public interface StdScanDocRepository extends JpaRepository<StdScanDoc, Long> {

	/**
	 * The scanning report: the documents scanned per worker name and day (MM/DD/YYYY) within the given days, ordered by
	 * the day. The days are the reports' MM/DD/YYYY texts, read by the database's TO_DATE as before; the worker name is
	 * first and last name joined with {@code ||} (a missing part an empty text in Oracle).
	 */
	@Query("""
			select new gov.ny.state.doh.bhae.nyehms.web.spring.record.ScanningCountRow(count(*), x.scannedBy, x.createDate)
			from (select s.documentId as documentId, format(s.createDate as 'MM/dd/yyyy') as createDate,
			             n.firstName || ' ' || n.lastName as scannedBy
			      from StdScanDoc s, NyehmsUsers n
			      where trunc(s.createDate, day) between cast(to_date(:fromDate, 'mm/dd/yyyy') as Timestamp) and cast(to_date(:toDate, 'mm/dd/yyyy') as Timestamp)
			        and s.createdBy = n.workerId) x
			group by x.scannedBy, x.createDate
			order by to_date(x.createDate, 'mm/dd/yyyy')
			""")
	List<ScanningCountRow> findScanningCounts(@Param("fromDate") String fromDate, @Param("toDate") String toDate);
}
