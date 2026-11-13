package gov.ny.state.doh.bhae.nyehms.web.spring.dao.document;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.HitErrorVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.HitLoadLogVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.entity.document.HitError;
import gov.ny.state.doh.bhae.nyehms.web.spring.entity.document.HitLoadLog;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.BoundValues;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.JpqlStatements;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.Rows;
import lombok.RequiredArgsConstructor;

/**
 * The Health Information Technology (HIT) documents' common data: the UPHN payload loader's log, the load errors and
 * their reprise, the HIT race value mapping, the daily message counts (formerly a MyBatis mapper with the same name
 * and methods; the callers are unchanged).
 * <p>
 * JPQL over the entities UphnTranxIn, HitLoadLog, HitError and CodedHitRaceDesc; the log and error rows are inserted
 * as entities (their keys from HIT_LOAD_LOG_SEQ / HIT_ERROR_SEQ, as the former inserts), the race values by HQL
 * inserts (one per value, formerly {@code union all}). "Today" and "now" (formerly {@code TRUNC(SYSDATE)} and
 * {@code SYSDATE}) are the application server's clock. Native SQL ({@link HITDocumentSql}, the mapper's text), with
 * the reason:
 * <ul>
 * <li>{@link #insertHitMessageCount}: an insert from a CTE with a sequence, per-day aggregates ({@code TRUNC}) and
 * Oracle date arithmetic;</li>
 * <li>{@link #getCountsForTodayMail}: {@code ROW_NUMBER() OVER}, {@code NEXT_DAY}, {@code TO_CHAR} day and month
 * names, a right outer join with a {@code FROM DUAL} union;</li>
 * <li>{@link #getCountsForHITLiveEvent}: per-day aggregates ({@code TRUNC(receivedtime)}) over a union;</li>
 * <li>{@link #deleteFromUphnTranxPropsIn}: UPHN_TRANX_PROPS_IN has no entity (its key is not known), Oracle date
 * arithmetic.</li>
 * </ul>
 */
@Repository
@RequiredArgsConstructor
public class HITDocumentDAO {

	private final NativeStatements statements;
	private final JpqlStatements jpql;

	// ---- the loader's log --------------------------------------------------------------------------------------------

	/** The highest UPHN transaction id received. @throws DataRetrievalFailureException if there is none (the former primitive result failed too) */
	public int getMaxUphnTranxInId() throws DataAccessException {
		return required(jpql.one("select max(u.uphnTranxInId) from UphnTranxIn u", Rows.scalar(Integer.class)), "UPHN_TRANX_IN");
	}

	/** The highest UPHN transaction id a loader run of a document type checked. @throws DataRetrievalFailureException if there is none */
	public int getMaxUphnTranxInIdCheckedFromLog(String recordType) throws DataAccessException {
		return required(jpql.one("select max(l.maxUtiiChecked) from HitLoadLog l where l.docType = ?1", Rows.scalar(Integer.class), recordType),
				"HIT_LOAD_LOG of " + recordType);
	}

	private static int required(Integer value, String table) {
		if (value == null) {
			throw new DataRetrievalFailureException("No maximum id in " + table);
		}
		return value;
	}

	/** Writes a loader run; its key (HIT_LOAD_LOG_SEQ) is put into the VO, as before. */
	public void insertHitLoadLog(HitLoadLogVO hitloadlog) throws DataAccessException {
		HitLoadLog entry = HitLoadLog.builder().docType(hitloadlog.getDocType()).minUtiiChecked(hitloadlog.getMinUtiiChecked())
				.minUtiiRetrieved(hitloadlog.getMinUtiiRetrieved()).maxUtiiRetrieved(hitloadlog.getMaxUtiiRetrieved())
				.maxUtiiChecked(hitloadlog.getMaxUtiiChecked()).noOfFailedDocs(hitloadlog.getNoOfFailedDocs())
				.noOfRetrievedDocs(hitloadlog.getNoOfRetrievedDocs()).noOfSuccessfulDocs(hitloadlog.getNoOfSuccessfulDocs())
				.createDate(BoundValues.timestamp(hitloadlog.getCreateDate())).createId(hitloadlog.getCreateId()).build();
		jpql.insert(entry);
		hitloadlog.setLogID(entry.getLogId().intValue());
	}

	/** Records the ids retrieved so far and the count of a run. */
	public void updateHitLoadLogAfterOneRow(HitLoadLogVO hitloadlog) throws DataAccessException {
		jpql.update("update HitLoadLog l set l.minUtiiRetrieved = ?1, l.maxUtiiRetrieved = ?2, l.maxUtiiChecked = ?3, l.noOfRetrievedDocs = ?4 where l.logId = ?5",
				hitloadlog.getMinUtiiRetrieved(), hitloadlog.getMaxUtiiRetrieved(), hitloadlog.getMaxUtiiChecked(), hitloadlog.getNoOfRetrievedDocs(),
				(long) hitloadlog.getLogID());
	}

	/** Records the final counts of a run. */
	public void updateHitLoadLogAfterAllRows(HitLoadLogVO hitloadlog) throws DataAccessException {
		jpql.update("update HitLoadLog l set l.noOfFailedDocs = ?1, l.noOfRetrievedDocs = ?2, l.noOfSuccessfulDocs = ?3 where l.logId = ?4",
				hitloadlog.getNoOfFailedDocs(), hitloadlog.getNoOfRetrievedDocs(), hitloadlog.getNoOfSuccessfulDocs(), (long) hitloadlog.getLogID());
	}

	// ---- the load errors ---------------------------------------------------------------------------------------------

	/** Writes a load error (key from HIT_ERROR_SEQ; CREATE_DATETIME the VO's day). */
	public void insertHitError(HitErrorVO hitErrorVO) throws DataAccessException {
		jpql.insert(HitError.builder().uphnTranxInId(hitErrorVO.getUphnTRANXINID()).payloadCleartext(hitErrorVO.getPayloadCLEARTEXT())
				.errorMessage(hitErrorVO.getErrorMESSAGE()).createDatetime(BoundValues.timestamp(hitErrorVO.getCreateDATETIME()))
				.recordType(hitErrorVO.getRecordType()).repriseFlag(hitErrorVO.getRepriseFlag()).comments(hitErrorVO.getComments()).build());
	}

	/**
	 * The errors of a record type (or of all, for 'ALL') not yet reprised: HIT_ERROR_ID, UPHN_TRANX_IN_ID, ERROR (the
	 * message's first 400 characters), CREATE_DATETIME (MM/DD/YYYY), RECORD_TYPE, REPRISE_FLAG, DATE_OF_REPRISE, NOTES.
	 */
	@SuppressWarnings({"unchecked", "rawtypes"})
	public List<Map<Object, Object>> getHitErrorRepriseValue(String parameterName) {
		String jpqlText = """
				select e.hitErrorId as HIT_ERROR_ID, e.uphnTranxInId as UPHN_TRANX_IN_ID, substring(e.errorMessage, 1, 400) as ERROR,
				       format(e.createDatetime as 'MM/dd/yyyy') as CREATE_DATETIME, e.recordType as RECORD_TYPE, e.repriseFlag as REPRISE_FLAG,
				       e.dateOfReprise as DATE_OF_REPRISE, e.comments as NOTES
				from HitError e where e.dateOfReprise is null and e.repriseFlag is null""";
		if ("ALL".equals(parameterName)) {
			return (List) jpql.list(jpqlText, Rows.map());
		}
		return (List) jpql.list(jpqlText + " and e.recordType = ?1", Rows.map(), parameterName);
	}

	/** Sets the reprise flag of the errors of a UPHN transaction flagged 'Y', with the time. @param inputOutputParameterMap statusCode, uphnID */
	public int updateHITErrorRepriseFlagStatus(Map<Object, Object> inputOutputParameterMap) {
		return jpql.update("update HitError e set e.repriseFlag = ?1, e.dateOfReprise = ?2 where e.uphnTranxInId = ?3 and e.repriseFlag = 'Y'",
				BoundValues.text(inputOutputParameterMap.get("statusCode")), now(), BoundValues.integer(inputOutputParameterMap.get("uphnID")));
	}

	/** Sets the reprise flag of an error. @param inputOutputParameterMap actionName, hitErrorId */
	public int updateHitDocumentsStatusinHITERRROR(Map<Object, Object> inputOutputParameterMap) {
		return jpql.update("update HitError e set e.repriseFlag = ?1 where e.hitErrorId = ?2",
				BoundValues.text(inputOutputParameterMap.get("actionName")), BoundValues.longValue(inputOutputParameterMap.get("hitErrorId")));
	}

	/** Sets the comments of an error. @param inputOutputParameterMap notes, hiterrorid */
	public int updateHitErrorComments(Map<Object, Object> inputOutputParameterMap) {
		return jpql.update("update HitError e set e.comments = ?1 where e.hitErrorId = ?2",
				BoundValues.text(inputOutputParameterMap.get("notes")), BoundValues.longValue(inputOutputParameterMap.get("hiterrorid")));
	}

	// ---- the race values ---------------------------------------------------------------------------------------------

	/**
	 * Adds race values found in payloads (CREATE_DATE today), one row each.
	 *
	 * @param inputParameters raceCodeErrorsList (a collection; an empty one fails, as the former empty {@code union all} did)
	 * @return the rows added
	 */
	@Transactional
	public int insertCodedRace(Map<String, Object> inputParameters) {
		Object list = inputParameters.get("raceCodeErrorsList");
		if (!(list instanceof Collection<?> codes) || codes.isEmpty()) {
			throw new InvalidDataAccessResourceUsageException("No race values to add: " + list);
		}
		int count = 0;
		for (Object code : codes) {
			count += jpql.update("insert into CodedHitRaceDesc (raceValue, createDate) values (?1, ?2)", BoundValues.text(code), today());
		}
		return count;
	}

	/** The number of rows with a race value. */
	public int checkIfRaceExists(String str) {
		return jpql.one("select count(r) from CodedHitRaceDesc r where r.raceValue = ?1", Rows.scalar(Integer.class), str);
	}

	/** The race values with their race and ethnicity, by value: RACE_VALUE, RACE, ETHNICITY. */
	@SuppressWarnings({"unchecked", "rawtypes"})
	public List<Map<String, String>> getRaceCodes() {
		return (List) jpql.list("select distinct r.raceValue as RACE_VALUE, r.race as RACE, r.ethnicity as ETHNICITY from CodedHitRaceDesc r order by r.raceValue", Rows.map());
	}

	/** The race values not yet mapped (never updated), by value: RACE_VALUE, RACE, ETHNICITY. */
	@SuppressWarnings({"unchecked", "rawtypes"})
	public List<Map<String, String>> getHitRaceCodeMapperData() {
		return (List) jpql.list("""
				select r.raceValue as RACE_VALUE, r.race as RACE, r.ethnicity as ETHNICITY from CodedHitRaceDesc r
				where r.updatedDate is null and r.updatedBy is null order by r.raceValue""", Rows.map());
	}

	/** The race and ethnicity of a race value: RACE, ETHNICITY; null if none. */
	@SuppressWarnings({"unchecked", "rawtypes"})
	public Map<String, String> getHitRaceValue(String parameterName) {
		return (Map) jpql.one("select r.race as RACE, r.ethnicity as ETHNICITY from CodedHitRaceDesc r where r.raceValue = ?1", Rows.map(), parameterName);
	}

	/** The one distinct mapped race (RACE); fails when there are several, as before. */
	@SuppressWarnings({"unchecked", "rawtypes"})
	public Map<String, String> getHitRaceData() {
		return (Map) jpql.one("select distinct r.race as RACE from CodedHitRaceDesc r where r.race is not null", Rows.map());
	}

	/** The one distinct mapped ethnicity (ETHNICITY); fails when there are several, as before. */
	@SuppressWarnings({"unchecked", "rawtypes"})
	public Map<String, String> getHitEthnicityData() {
		return (Map) jpql.one("select distinct r.ethnicity as ETHNICITY from CodedHitRaceDesc r where r.ethnicity is not null", Rows.map());
	}

	/** Maps a race value to a race and ethnicity, by a user, today. @param inputOutputParameterMap parameterName, parameterValue1, parameterValue2, userId */
	public int updateHitaRaceValue(Map<Object, Object> inputOutputParameterMap) {
		return jpql.update("update CodedHitRaceDesc r set r.race = ?1, r.ethnicity = ?2, r.updatedDate = ?3, r.updatedBy = ?4 where r.raceValue = ?5",
				BoundValues.text(inputOutputParameterMap.get("parameterValue1")), BoundValues.text(inputOutputParameterMap.get("parameterValue2")), today(),
				BoundValues.text(inputOutputParameterMap.get("userId")), BoundValues.text(inputOutputParameterMap.get("parameterName")));
	}

	// ---- the message counts ---------------------------------------------------------------------------------------------

	/** Adds yesterday's and earlier days' payload counts per type to HIT_MESSAGE_COUNTS. */
	public void insertHitMessageCount() throws DataAccessException {
		statements.update(HITDocumentSql.INSERT_HIT_MESSAGE_COUNT);
	}

	/** The latest counts per type with the received-as-expected flags for the daily mail. */
	public List<Map<String, Object>> getCountsForTodayMail() throws DataAccessException {
		return statements.list(HITDocumentSql.COUNTS_FOR_TODAY_MAIL, Rows.map());
	}

	/** Today's payload counts per type. */
	public List<Map<String, Object>> getCountsForHITLiveEvent() throws DataAccessException {
		return statements.list(HITDocumentSql.COUNTS_FOR_HIT_LIVE_EVENT, Rows.map());
	}

	/** Deletes the properties of the payloads received more than 30 days ago. */
	public void deleteFromUphnTranxPropsIn() throws DataAccessException {
		statements.update(HITDocumentSql.DELETE_FROM_UPHN_TRANX_PROPS_IN);
	}

	// ---- helpers ------------------------------------------------------------------------------------------------------

	/** The application server's clock (formerly SYSDATE). */
	private static Timestamp now() {
		return new Timestamp(System.currentTimeMillis());
	}

	/** Today at midnight (formerly TRUNC(SYSDATE)). */
	private static Timestamp today() {
		return Timestamp.valueOf(LocalDate.now().atStartOfDay());
	}
}
