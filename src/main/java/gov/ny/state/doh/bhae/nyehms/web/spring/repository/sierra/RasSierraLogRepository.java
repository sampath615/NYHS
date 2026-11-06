package gov.ny.state.doh.bhae.nyehms.web.spring.repository.sierra;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.sierra.RasSierraLog;
import gov.ny.state.doh.bhae.nyehms.web.spring.entity.sierra.RasSierraLogId;

/** The runs of the Sierra job (table RAS_SIERRA_LOG). */
public interface RasSierraLogRepository extends JpaRepository<RasSierraLog, RasSierraLogId> {

	/** The highest lab document id parsed so far (null if there is no run). */
	@Query("select max(log.documentIdMax) from RasSierraLog log")
	Integer lastParsedDocumentId();

	/** Adds a run, created by 'NYEHMS' at the database's SYSDATE (an HQL insert: written at once, as before). */
	@Modifying
	@Query("""
			insert into RasSierraLog (documentIdMin, documentIdMax, noOfErrors, totalNoRecords, createDate, createId)
			values (?1, ?2, ?3, ?4, sysdate, 'NYEHMS')
			""")
	int insertRun(Integer documentIdMin, Integer documentIdMax, Integer noOfErrors, Integer totalNoRecords);
}
