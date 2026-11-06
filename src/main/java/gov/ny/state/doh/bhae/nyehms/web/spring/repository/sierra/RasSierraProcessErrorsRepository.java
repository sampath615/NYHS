package gov.ny.state.doh.bhae.nyehms.web.spring.repository.sierra;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.sierra.RasSierraProcessErrors;
import gov.ny.state.doh.bhae.nyehms.web.spring.entity.sierra.RasSierraProcessErrorsId;

/** The lab documents the Sierra job could not parse or is to parse again (table RAS_SIERRA_PROCESS_ERRORS). */
public interface RasSierraProcessErrorsRepository extends JpaRepository<RasSierraProcessErrors, RasSierraProcessErrorsId> {

	/** Adds a failed document at the database's SYSDATE (an HQL insert: written at once, as before). */
	@Modifying
	@Query("""
			insert into RasSierraProcessErrors (documentId, errorDesc, activeFlag, createId, createDate)
			values (?1, ?2, ?3, ?4, sysdate)
			""")
	int insertError(Long documentId, String errorDesc, String activeFlag, String createId);

	/**
	 * The active failed documents (flag 'Y', not reprised) with their gene sequence: rows of [DOCUMENT_ID, GEN_SEQ,
	 * ERROR_DESC, CREATE_DATE], one per sequence.
	 */
	@Query("""
			select errors.documentId, gen.genSeq, errors.errorDesc, errors.createDate
			from RasSierraProcessErrors errors, GenSequence gen
			where gen.labDocId = errors.documentId and errors.activeFlag = 'Y' and errors.repriseFlag is null
			""")
	List<Object[]> findActiveFailed();

	/** The number of rows of a document marked for reprise ('R'); 0 for a null id. */
	@Query("select count(*) from RasSierraProcessErrors errors where errors.documentId = ?1 and errors.activeFlag = 'R'")
	long countMarkedForReprise(Long documentId);

	/**
	 * Sets the comments, the worker and the flag (and UPDATE_DATE, the database's SYSDATE) of the active ('Y') rows of
	 * the documents.
	 *
	 * @return the number of rows changed
	 */
	@Modifying
	@Query("""
			update RasSierraProcessErrors errors set errors.comments = ?1, errors.updateId = ?2, errors.updateDate = sysdate,
					errors.activeFlag = ?3
			where errors.activeFlag = 'Y' and errors.documentId in ?4
			""")
	int changeActiveRows(String comments, String updateId, String activeFlag, Collection<Long> documentIds);

	/** Marks every row of a document as reprised ('R_P'). */
	@Modifying
	@Query("update RasSierraProcessErrors errors set errors.activeFlag = 'R_P' where errors.documentId = ?1")
	int markReprised(Long documentId);

	/**
	 * Adds a reprise row ('R', 'missing document_id', by 'NYEHMS' at the database's SYSDATE) for every RAS eligible
	 * sequence from lab document 89073264 (a fixed lower bound, as before) up to the last parsed one that has no Sierra
	 * response (an HQL insert ... select).
	 *
	 * @return the number of rows inserted
	 */
	@Modifying
	@Query("""
			insert into RasSierraProcessErrors (documentId, errorDesc, activeFlag, updateDate, updateId, createId, createDate)
			select seq.labDocId, 'missing document_id', 'R', cast(null as Timestamp), cast(null as String), 'NYEHMS', sysdate
			from GenSequence seq
			where seq.labDocId between 89073264 and (select max(log.documentIdMax) from RasSierraLog log)
			and seq.rasElig = 'Y'
			and not exists (select 1 from RasSiResponse response where response.documentId = seq.labDocId)
			""")
	int insertMissingDocuments();
}
