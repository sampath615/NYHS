package gov.ny.state.doh.bhae.nyehms.web.spring.dao.lab;

import static gov.ny.state.doh.bhae.nyehms.web.spring.dao.lab.LaftRows.now;
import static gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements.cursor;
import static gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements.in;
import static gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements.number;
import static gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements.out;
import static gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements.text;
import static gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements.timestamp;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.lab.LAFT_LAB_RECON_BHAE_COMMENTS;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.lab.LAFT_LAB_RECON_SUMM_REC;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.JpqlStatements;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.Rows;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.Values;
import gov.ny.state.doh.bhae.nyehms.web.spring.repository.lab.LaftLabSurveyResponseRepository;
import lombok.RequiredArgsConstructor;

/**
 * The LAFT count reconciliation (formerly a MyBatis mapper with the same name and methods). The reconciliation logic is
 * in the PL/SQL package LAFT_LAB_RECON_PROCESS: its procedures are called through {@link NativeStatements#call} and
 * their OUT values and cursor rows put back into the caller's map under the former keys. The reads and writes of the
 * periods, survey e-mails and survey responses are JPQL over the entities of {@code entity.lab}.
 * <p>
 * {@link #insertEditSurveyData} stays native SQL: it fills the global temporary table LAFT_RECON_SUMM_GTT, which
 * {@code edit_recon_summary} reads in the same transaction (the caller, LabReportsTransactionHelper, runs both in one
 * transaction, so they share the persistence context's connection). The dates the former SQL set with SYSDATE are the
 * application server's clock.
 */
@Repository
@RequiredArgsConstructor
public class LaftCountReconcileDAO {

	/** The former ORDER BY to_date(COLLECTION_START_DATE, 'MM/DD/YYYY') DESC (the sort value is selected, as DISTINCT needs it on H2). */
	private static final String COLLECTION_PERIODS = """
			select distinct l.laftReconId, l.collectionStartDate, l.collectionEndDate,
			function('to_date' as Timestamp, l.collectionStartDate, 'MM/DD/YYYY')
			from LaftLabReconList l
			order by function('to_date' as Timestamp, l.collectionStartDate, 'MM/DD/YYYY') desc""";

	/** One row of the former INSERT ... SELECT ... FROM dual UNION ALL ... */
	private static final String SURVEY_EDIT_ROW = "select ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, FN_TO_DATE(?,'MM/DD/YYYY') from dual";

	private static final String INSERT_SURVEY_RESPONSE = """
			insert into LaftLabSurveyResponse (laftReconId, surveyResponseFilename, uploadDate, workerId, pfi, clia, wesBlot, hiv12RnaNatQl,
			hiv1RnaNatQl, hiv2RnaNatQl, hiv1RnaCopy, hiv1RnaLog, cd4Pct, cd4Abs, genoResist, dtaScreenAlere, dtaScreen, bioplexHiv1Ag, bioplexHiv1Ab,
			bioplexHiv2Ab, elecsysHiv12Ab, dtaDiff, dtaDiffHiv1, dtaDiffHiv2, hiv1Dna, hiv2RnaDna, ifa, openEndedResponse, surveyCompletedBy,
			surveyCompletedByEmail, surveyCompletedByPhone, surveyCompletedDate)
			values (?1, ?2, ?3, ?4, ?5, ?6, ?7, ?8, ?9, ?10, ?11, ?12, ?13, ?14, ?15, ?16, ?17, ?18, ?19, ?20, ?21, ?22, ?23, ?24, ?25, ?26, ?27, ?28,
			?29, ?30, ?31, function('FN_TO_DATE' as Timestamp, ?32, 'MM/DD/YYYY'))""";

	/**
	 * The keys of the uploaded file's rows (the template's column names) for the answer columns, in the order of
	 * {@link #INSERT_SURVEY_RESPONSE} from wesBlot to surveyCompletedByPhone.
	 */
	private static final String[] SURVEY_RESPONSE_KEYS = {"Western_Blot(Pos/IN/Neg)", "RNA_NAAT_HIV_1/2_RNA_QL", "RNA_NAAT/HIV_1_RNA_QL",
			"RNA_NAAT/HIV_2_RNA_QL", "Viral_Load_(copies/ml)", "Viral_Load_(log_copies)", "CD4_Percent", "CD4_Absolute", "Genotype_Resistance",
			"Abbott_Determine", "HIV_1_HIV_2_Immunoassay", "HIV1_Ag", "HIV1_Ab", "HIV2_Ab", "HIV1+2_Ab", "Geenius_Assay_Interpretation", "Geenius_HIV_1",
			"Geenius_HIV_2", "HIV_1_DNA", "HIV_2_DNA", "IFA", "Open_Ended_Response", "Survey_Completor_Name", "Survey_Completor_Email_Id",
			"Survey_Completor_Phone"};

	private final JpqlStatements jpql;
	private final NativeStatements statements;
	private final LaftLabSurveyResponseRepository surveyResponses;

	/**
	 * The reconciliation periods, newest start first: LAFT_RECON_ID, COLLECTION_START_DATE, COLLECTION_END_DATE and
	 * COLLECTIONPERIOD ("start - end").
	 *
	 * @param inputParameterMap not used (as before)
	 */
	@SuppressWarnings("unchecked")
	public List<JSONObject> getPFICountCollectionPeriod(Map<String, Object> inputParameterMap) {
		return jpql.list(COLLECTION_PERIODS, row -> {
			JSONObject period = new JSONObject();
			putIfSet(period, "LAFT_RECON_ID", row.get(0));
			putIfSet(period, "COLLECTION_START_DATE", row.get(1));
			putIfSet(period, "COLLECTION_END_DATE", row.get(2));
			// COLLECTION_START_DATE || ' - ' || COLLECTION_END_DATE (a NULL part adds nothing)
			period.put("COLLECTIONPERIOD", (row.get(1) == null ? "" : row.get(1)) + " - " + (row.get(2) == null ? "" : row.get(2)));
			return period;
		});
	}

	/**
	 * The reconciliation dashboard: calls {@code get_recon_summary} and puts its five cursors into the map as
	 * p_recon_dash, p_recon_summ_out, p_recon_list_pfis, p_survey_response (JSONObjects) and p_bhae_comments_cur
	 * (LAFT_LAB_RECON_BHAE_COMMENTS).
	 *
	 * @param map p_laft_recon_id, p_pfi
	 * @return an empty list (the procedure returns its rows through the cursors)
	 */
	public List<JSONObject> getReconcileDashboardData(Map<Object, Object> map) {
		List<Object> results = statements.call("LAFT_LAB_RECON_PROCESS.get_recon_summary",
				in(map.get("p_laft_recon_id"), String.class),
				in(map.get("p_pfi"), String.class),
				cursor(Rows.json()),
				cursor(Rows.json()),
				cursor(Rows.json()),
				cursor(Rows.bean(LAFT_LAB_RECON_BHAE_COMMENTS.class)),
				cursor(Rows.json()));
		map.put("p_recon_dash", results.get(2));
		map.put("p_recon_summ_out", results.get(3));
		map.put("p_recon_list_pfis", results.get(4));
		map.put("p_bhae_comments_cur", results.get(5));
		map.put("p_survey_response", results.get(6));
		return new ArrayList<>();
	}

	/**
	 * The open surveys of a worker: calls {@code get_recon_summary_for_worker} and puts its rows into the map as p_pfi_cur.
	 *
	 * @param map p_laft_recon_id, p_worker_id
	 * @return an empty list (the procedure returns its rows through the cursor)
	 */
	public List<JSONObject> getCurOpenSurveysData(Map<Object, Object> map) {
		List<Object> results = statements.call("LAFT_LAB_RECON_PROCESS.get_recon_summary_for_worker",
				in(map.get("p_laft_recon_id"), String.class),
				in(map.get("p_worker_id"), String.class),
				cursor(Rows.json()));
		map.put("p_pfi_cur", results.get(2));
		return new ArrayList<>();
	}

	/**
	 * Has no statement: the former mapper declared this method without SQL, so calling it failed (it is not called).
	 *
	 * @throws InvalidDataAccessApiUsageException always
	 */
	public JSONObject getLabDetails(String pfi) {
		throw new InvalidDataAccessApiUsageException("LaftCountReconcileDAO.getLabDetails has no statement (it had none in the former MyBatis mapper)");
	}

	/**
	 * Saves the edited reconciliation of a lab: calls {@code edit_recon_summary} (which reads the rows
	 * {@link #insertEditSurveyData} put into LAFT_RECON_SUMM_GTT in the same transaction).
	 *
	 * @param map p_laft_recon_id, p_pfi, p_bhae_comments, p_update_worker_id, p_initial_contact_date, p_reconciled,
	 *        p_accession_compare
	 */
	public void updateLabReconcileData(Map<Object, Object> map) throws DataAccessException {
		statements.call("LAFT_LAB_RECON_PROCESS.edit_recon_summary",
				in(map.get("p_laft_recon_id"), String.class),
				in(map.get("p_pfi"), String.class),
				in(map.get("p_bhae_comments"), String.class),
				in(map.get("p_update_worker_id"), String.class),
				in(map.get("p_initial_contact_date"), String.class),
				in(map.get("p_reconciled"), String.class),
				in(map.get("p_accession_compare"), String.class));
	}

	/**
	 * Puts the edited survey counts into the global temporary table LAFT_RECON_SUMM_GTT. Native: a global temporary
	 * table, read by {@code edit_recon_summary} in the same transaction (the SQL is unchanged, the values bound).
	 *
	 * @param map surveyEditDataList (LAFT_LAB_RECON_SUMM_RECs; not empty: the former statement failed then too)
	 * @return the number of rows inserted
	 */
	@Transactional
	public int insertEditSurveyData(Map<String, Object> map) throws DataAccessException {
		List<LAFT_LAB_RECON_SUMM_REC> rows = LaftRows.inList(map.get("surveyEditDataList"), "surveyEditDataList", value -> (LAFT_LAB_RECON_SUMM_REC) value);
		StringBuilder sql = new StringBuilder("INSERT INTO LAFT_RECON_SUMM_GTT (LAFT_RECON_SUMM_PK, LAFT_RECON_ID, PFI, CLIA, UPDATE_WORKER_ID, "
				+ "LAFT_TEST_CD, INITIAL_LAB_COUNT, CURRENT_LAB_COUNT, SURVEY_LAB_COUNT, ADJUSTED_SURVEY_LAB_COUNT, COUNT_DIFFERENCE, DATE_RESOLVED)\n");
		List<Object> values = new ArrayList<>();
		for (LAFT_LAB_RECON_SUMM_REC row : rows) {
			if (!values.isEmpty()) {
				sql.append(" Union all ");
			}
			sql.append(SURVEY_EDIT_ROW);
			values.add(number(row.getLAFT_RECON_SUMM_PK()));
			values.add(text(row.getLAFT_RECON_ID()));
			values.add(text(row.getPFI()));
			values.add(text(row.getCLIA()));
			values.add(text(row.getUPDATE_WORKER_ID()));
			values.add(text(row.getLAFT_TEST_CD()));
			values.add(number(row.getINITIAL_LAB_COUNT()));
			values.add(number(row.getCURRENT_LAB_COUNT()));
			values.add(number(row.getSURVEY_LAB_COUNT()));
			values.add(number(row.getADJUSTED_SURVEY_LAB_COUNT()));
			values.add(number(row.getCOUNT_DIFFERENCE()));
			values.add(timestamp(row.getDATE_RESOLVED()));    // #{DATE_RESOLVED,jdbcType=DATE}: the text as it is, a null as a DATE null
		}
		return statements.update(numbered(sql.toString()), values.toArray());
	}

	/**
	 * The recipients of the survey e-mail: calls {@code get_survey_email_recipients} and puts its rows into the map as
	 * p_survey_email and its message as p_message.
	 *
	 * @return an empty list (the procedure returns its rows through the cursor)
	 */
	public List<JSONObject> getSurveyEmailRecipients(Map<String, Object> emailMap) {
		List<Object> results = statements.call("LAFT_LAB_RECON_PROCESS.get_survey_email_recipients",
				cursor(Rows.json()),
				out(String.class));
		emailMap.put("p_survey_email", results.get(0));
		emailMap.put("p_message", results.get(1));
		return new ArrayList<>();
	}

	/**
	 * Records that the survey e-mail was sent (now) for the given survey tracks.
	 *
	 * @param map hcsUserId, trackPkList (the SURVEY_TRACK_PK values, as text such as " 12": the database converted them
	 *        to numbers, as here)
	 */
	public void updateSurveyEmailSendData(Map<String, Object> map) throws DataAccessException {
		jpql.update("update LaftLabSurveyTrack t set t.surveySentDate = ?1, t.surveySentBy = ?2 where t.surveyTrackPk in ?3", now(),
				Values.text(map.get("hcsUserId")), LaftRows.longs(map.get("trackPkList"), "trackPkList"));
	}

	/** The number of survey response rows uploaded from a file of this name (a null name matched nothing). */
	public int getLaftSurveyResponseFilenameCt(String uploadFileName) {
		return uploadFileName == null ? 0 : (int) surveyResponses.countBySurveyResponseFilename(uploadFileName);
	}

	/**
	 * Inserts the rows of an uploaded survey response file (uploaded now).
	 *
	 * @param inputParameterMap laftSurveyResponseDataList (maps with collectionPeriod, fileName, userId, PFI, CLIA, the
	 *        template's answer columns and Survey_Completed_Date; not empty: the former statement failed then too)
	 * @return the number of rows inserted
	 */
	@Transactional
	public int insertLaftSurveyResponseData(Map<String, Object> inputParameterMap) throws DataAccessException {
		List<Map<?, ?>> rows = LaftRows.inList(inputParameterMap.get("laftSurveyResponseDataList"), "laftSurveyResponseDataList",
				value -> (Map<?, ?>) value);
		int inserted = 0;
		for (Map<?, ?> row : rows) {
			List<Object> values = new ArrayList<>();
			values.add(Values.text(row.get("collectionPeriod")));
			values.add(Values.text(row.get("fileName")));
			values.add(now());
			values.add(Values.text(row.get("userId")));
			values.add(Values.text(row.get("PFI")));
			values.add(Values.text(row.get("CLIA")));
			for (String key : SURVEY_RESPONSE_KEYS) {
				values.add(Values.text(row.get(key)));
			}
			values.add(Values.text(row.get("Survey_Completed_Date")));
			inserted += jpql.update(INSERT_SURVEY_RESPONSE, values.toArray());
		}
		return inserted;
	}

	/**
	 * Processes an uploaded survey response file: calls {@code process_survey_response}.
	 *
	 * @param map p_laft_recon_id, p_survey_filename
	 */
	public void processUploadedSurveyResponse(Map<Object, Object> map) throws DataAccessException {
		statements.call("LAFT_LAB_RECON_PROCESS.process_survey_response",
				in(map.get("p_laft_recon_id"), String.class),
				in(map.get("p_survey_filename"), String.class));
	}

	/**
	 * The accession numbers of a lab: calls {@code get_accession_list} and puts its rows into the map as p_accession_list.
	 *
	 * @param map p_laft_recon_id, p_pfi
	 * @return an empty list (the procedure returns its rows through the cursor)
	 */
	public List<JSONObject> getAccessionList(Map<Object, Object> map) {
		List<Object> results = statements.call("LAFT_LAB_RECON_PROCESS.get_accession_list",
				in(map.get("p_laft_recon_id"), String.class),
				in(map.get("p_pfi"), String.class),
				cursor(Rows.json()));
		map.put("p_accession_list", results.get(2));
		return new ArrayList<>();
	}

	/**
	 * Refreshes the NYEHMS counts of a lab: calls {@code refresh_nyehms_lab_count}.
	 *
	 * @param map p_recon_id, p_pfi, p_worker_id
	 */
	public void refreshNyehmsLabCount(Map<Object, Object> map) throws DataAccessException {
		statements.call("LAFT_LAB_RECON_PROCESS.refresh_nyehms_lab_count",
				in(map.get("p_recon_id"), String.class),
				in(map.get("p_pfi"), String.class),
				in(map.get("p_worker_id"), String.class));
	}

	/**
	 * Adds a lab to a reconciliation: calls {@code add_pfi} and puts its OUT values into the map as p_code (an Integer)
	 * and p_msg_out.
	 *
	 * @param map p_recon_id, p_pfi, p_laft_staff, p_worker_id
	 */
	public void saveAddReconcilePFI(Map<String, Object> map) throws DataAccessException {
		List<Object> results = statements.call("LAFT_LAB_RECON_PROCESS.add_pfi",
				in(map.get("p_recon_id"), String.class),
				in(map.get("p_pfi"), String.class),
				in(map.get("p_laft_staff"), String.class),
				in(map.get("p_worker_id"), String.class),
				out(Integer.class),
				out(String.class));
		map.put("p_code", results.get(4));
		map.put("p_msg_out", results.get(5));
	}

	/**
	 * Removes a lab from a reconciliation: calls {@code delete_pfi}.
	 *
	 * @param map p_recon_id, p_pfi, p_worker_id
	 */
	public void deleteReconcilePFI(Map<String, Object> map) throws DataAccessException {
		statements.call("LAFT_LAB_RECON_PROCESS.delete_pfi",
				in(map.get("p_recon_id"), String.class),
				in(map.get("p_pfi"), String.class),
				in(map.get("p_worker_id"), String.class));
	}

	/** The PFIs of the survey responses already uploaded for a reconciliation period (each once). */
	public List<String> getDuplicatePFIFromSurveyResponse(String collectionPeriod) {
		return jpql.list("select distinct r.pfi from LaftLabSurveyResponse r where r.laftReconId = ?1", row -> (String) row.get(0), collectionPeriod);
	}

	/** SQL with plain ? parameters numbered ?1, ?2, ... in order. */
	private static String numbered(String sql) {
		StringBuilder numbered = new StringBuilder();
		int n = 0;
		for (char c : sql.toCharArray()) {
			if (c == '?') {
				numbered.append('?').append(++n);
			} else {
				numbered.append(c);
			}
		}
		return numbered.toString();
	}

	@SuppressWarnings("unchecked")
	private static void putIfSet(JSONObject row, String key, Object value) {
		if (value != null) {
			row.put(key, value);
		}
	}
}
