package gov.ny.state.doh.bhae.nyehms.web.spring.repository.document;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.document.RawAirsDoc;
import gov.ny.state.doh.bhae.nyehms.web.spring.record.AirsAgencyErrorRow;
import gov.ny.state.doh.bhae.nyehms.web.spring.record.AirsAgencyRow;

/** The received AIRS reports (table RAW_AIRS_DOC) and the AIRS agencies (CODED_AIRS_AGENCIES). */
public interface RawAirsDocRepository extends JpaRepository<RawAirsDoc, Integer> {

	/** An agency that is not retired, by id. */
	@Query("select new gov.ny.state.doh.bhae.nyehms.web.spring.record.AirsAgencyRow(a.agencyId, a.lastUpgradeDate)"
			+ " from CodedAirsAgencies a where a.retiredDate is null and a.agencyId = ?1")
	List<AirsAgencyRow> findActiveAgency(String agencyId);

	/**
	 * Stops the waiting reports (status 1) that hold no data (TC_ID 'NO_RECORDS' or URN 'NO_RECORD') at the raw layer
	 * (status 18, PROCESS_DATE the database's SYSDATE).
	 *
	 * @return the number of rows changed
	 */
	@Modifying
	@Query("update RawAirsDoc r set r.currentProcessStatus = 18, r.processDate = sysdate"
			+ " where r.currentProcessStatus = 1 and (r.tcId = 'NO_RECORDS' or r.urn = 'NO_RECORD')")
	int stopNoRecords();

	/** The reports stopped for an unknown agency (status 25), counted per agency and creation day (MM/DD/YYYY). */
	@Query("select new gov.ny.state.doh.bhae.nyehms.web.spring.record.AirsAgencyErrorRow(r.agencyId, format(r.createDate as 'MM/dd/yyyy'), count(*))"
			+ " from RawAirsDoc r where r.currentProcessStatus = 25"
			+ " group by r.agencyId, format(r.createDate as 'MM/dd/yyyy') order by r.agencyId asc")
	List<AirsAgencyErrorRow> countAgencyErrors();

	/**
	 * As {@link #countAgencyErrors()}, for the reports processed within the given time range (the day of the former
	 * TRUNC(PROCESS_DATE) = TRUNC(SYSDATE)).
	 */
	@Query("select new gov.ny.state.doh.bhae.nyehms.web.spring.record.AirsAgencyErrorRow(r.agencyId, format(r.createDate as 'MM/dd/yyyy'), count(*))"
			+ " from RawAirsDoc r where r.currentProcessStatus = 25 and r.processDate >= ?1 and r.processDate < ?2"
			+ " group by r.agencyId, format(r.createDate as 'MM/dd/yyyy') order by r.agencyId asc")
	List<AirsAgencyErrorRow> countAgencyErrorsProcessedBetween(Timestamp from, Timestamp to);

	/** The reports stopped for an unknown agency (status 25) whose agency is now known and not retired, once each. */
	@Query("select distinct r.documentId from RawAirsDoc r, CodedAirsAgencies a"
			+ " where r.currentProcessStatus = 25 and r.agencyId = a.agencyId and a.retiredDate is null")
	List<Integer> findDocumentIdsWithValidAgency();
}
