package gov.ny.state.doh.bhae.nyehms.web.spring.dao.lab;

import static gov.ny.state.doh.bhae.nyehms.web.spring.dao.lab.LaftRows.id;
import static gov.ny.state.doh.bhae.nyehms.web.spring.jpa.Values.text;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.json.simple.JSONObject;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import gov.ny.state.doh.bhae.nyehms.web.spring.dao.report.StatusLabReportsDAO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.lab.ClinicalLaboratoryVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.lab.LabFacilitiesVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.lab.LabInteractionHistoryVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.lab.LabNewContactVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.lab.LabOpsReportsVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.lab.LabReportsCountVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.lab.LabSubmissionTypeVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.entity.lab.LabAlternativeContacts;
import gov.ny.state.doh.bhae.nyehms.web.spring.entity.lab.LabSubmissionTypeMapping;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.JpqlStatements;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements.RowMapper;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.Row;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.Rows;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;

/**
 * The lab maintenance screens and lab reports: lab search and profile, permits and instruments, LOINC and local codes,
 * communications, contacts, submission types, the lookups of these screens and the lab operation reports (formerly a
 * MyBatis mapper with the same name and methods).
 * <p>
 * JPQL over the entities of {@code entity.lab} / {@code entity.common}. The inserts are JPQL inserts of exactly the
 * columns the former inserts named (so the database defaults of the other columns stay as before), except the two
 * whose generated key is read back (a new contact, a new submission type), which are saved as entities. The dates the
 * screens send as text are converted by the database's {@code to_date(..., 'MM/DD/YYYY')} as before (in the JPQL, or
 * by a JPQL select before an entity is saved); {@code SYSDATE} stays the database's {@code sysdate}.
 * <p>
 * Native SQL, unchanged except that the values are bound, and why:
 * <ul>
 * <li>{@link #getLabSearchResults}, {@link #getFacilitiesDataForClia}: they select from the pipelined table function
 * {@code pkg_lab_maintenance.fn_lab_search} ({@code TABLE(...)});</li>
 * <li>{@link #getActiveAssociatedInstrumentCodesAndDescriptions}, {@link #getInstrumentCodesAndDescriptions},
 * {@link #getSubmissionTypes}, {@link #getPrimarySubmissionTypes}: {@code select *} of tables whose column lists are not
 * known, and the results (maps / JSON) carry every column under its own name;</li>
 * <li>{@link #getNycNysCommReportData}: {@code pkg_trk_common.fn_get_fullname} and {@code clobbytes} (database
 * functions of this schema);</li>
 * <li>{@link #getLabListByApprovalStatus}: {@code regexp_substr ... CONNECT BY} (the split of the status list),
 * {@code LISTAGG} and {@code COUNT(DISTINCT ...) OVER}.</li>
 * </ul>
 * The two procedures ({@link #getFeedbackMedicalReport}, {@link #getLabDetails}) return their rows through cursors,
 * read as text and mapped with the former result maps ({@link LabReportsResultMaps}); they run as JDBC calls on the
 * persistence context's connection ({@link #callWithTextCursors}).
 */
@Repository
@RequiredArgsConstructor
public class LabReportsDAO {

	/** {@code to_date(#{x}, 'MM/DD/YYYY')} of the text parameter ?n. */
	private static String toDate(int parameter) {
		return "function('to_date' as Timestamp, cast(?" + parameter + " as String), 'MM/DD/YYYY')";
	}

	private static final String LAB_SEARCH = """
			SELECT * FROM TABLE(pkg_lab_maintenance.fn_lab_search (?1, ?2, ?3, ?4, ?5, ?6, ?7, ?8, ?9))""";

	private static final String FACILITIES_FOR_CLIA = """
			SELECT PFI,LAB_NAME,ADDRESS,CITY,STATE,ZIP,COUNTRY,FLAG_ACTIVE, LAFT_STAFF FROM TABLE(pkg_lab_maintenance.fn_lab_search (?1, null, null, null, null, null, null, null, null))""";

	private static final String ACTIVE_ASSOCIATED_INSTRUMENTS = """
			SELECT *  FROM CODED_HIV_INSTRUMENTS chi
			             WHERE NOT EXISTS (SELECT HIV_INSTRUMENT_ID FROM LAB_PERMIT_INSTRUMENTS lpi
			                 WHERE lpi.LAB_PERMIT_ID = ?1
			                      AND (lpi.END_DATE IS NULL OR lpi.END_DATE > SYSDATE)
			                      AND lpi.HIV_INSTRUMENT_ID = chi.HIV_INSTRUMENT_ID)
			                      AND HIV_INSTRUMENT_CD IS NOT NULL ORDER BY INSTRUMENT_NAME""";

	private static final String INSTRUMENTS = """
			SELECT * FROM CODED_HIV_INSTRUMENTS WHERE HIV_INSTRUMENT_CD IS NOT NULL ORDER BY INSTRUMENT_NAME""";

	private static final String SUBMISSION_TYPES = """
			select * from CODED_LAB_SUBMISSION_TYPE where SUBMISSION_TYPE_CD not in (select SUBMISSION_TYPE_CD from LAB_SUBMISSION_TYPE_MAPPING  where PFI = ?1 and (END_DATE is null or END_DATE > SYSDATE))""";

	private static final String PRIMARY_SUBMISSION_TYPES = """
			select * from LAB_SUBMISSION_TYPE_MAPPING where PRIMARY_SUBMISSION = 'Y' and PFI = ?1 and (END_DATE is null or END_DATE > SYSDATE)""";

	private static final String NYC_NYS_COMM_REPORT = """
			SELECT a.clia, a.pfi, a.lab_name facilityName, Nvl(a.flag_serves_nyc, 'N') nycLab, a.laft_staff staffName,
			         pkg_trk_common.fn_get_fullname (u.last_name,
			                                         u.first_name,
			                                         u.middle_name,
			                                         NULL) laft_staff_name,
			         clobbytes(b.interaction_notes) comments, To_char(b.interaction_date, 'MM/DD/YYYY') as communicateDate,
			         c.communication_type_description communicationType
			    FROM lab_physical_facility a
			    LEFT OUTER JOIN lab_interaction_history b ON (a.pfi = b.pfi)
			    LEFT OUTER JOIN coded_lab_communication_type c ON b.communication_type = c.communication_type
			    LEFT OUTER JOIN nyehms_users u ON (a.laft_staff = u.legacy_uid and u.end_date is null)
			   WHERE a.pfi != '-999'
			     AND Trunc(b.interaction_date) BETWEEN To_Date(?1, 'MM/DD/YYYY') AND To_Date(?2, 'MM/DD/YYYY')
			ORDER BY interaction_date, pfi""";

	private static final String LAB_LIST_BY_APPROVAL_STATUS = """
			WITH loinc_data AS
			(
			 SELECT Distinct a.clia, a.pfi, a.lab_name facilityName, Nvl(a.flag_serves_nyc, 'N') nycLab,
			        b.approval_status_desc apporvalStatus, a.laft_staff staffName, performs_dta performDTA,
			        flag_blood_bank donorLab,a.address, a.city, a.state, a.zip, a.phy_off_lab phyofflab,
			        pkg_trk_common.fn_get_fullname (u.last_name,
			                                          u.first_name,
			                                          u.middle_name,
			                                          NULL) userName,
			        COUNT(Distinct a.pfi) OVER (PARTITION BY 1) statusRecCt,
			        COUNT(Distinct a.pfi) OVER (PARTITION BY b.approval_status_code) totalRecCt,
			        c.description lab_submission_type,
			        m.loinc_cd loinc, t.test_type testType,
			        To_char(m.start_date, 'MM/DD/YYYY') testingStartDate,
			        t.contact_name contactName, t.email contactEmail, t.phone contactPhone
			   FROM lab_physical_facility a
			   LEFT OUTER JOIN coded_lab_approval_status b ON (a.approval_status_code = b.approval_status_code)
			   LEFT OUTER JOIN nyehms_users u ON (a.laft_staff = u.legacy_uid and u.end_date is null)
			   LEFT OUTER JOIN lab_submission_type_mapping l ON l.pfi = a.pfi AND l.end_date IS NULL
			   LEFT OUTER JOIN coded_lab_submission_type c ON c.submission_type_cd = l.submission_type_cd
			   LEFT OUTER JOIN lab_loinc_mapping m ON m.pfi = a.pfi AND m.end_date IS NULL
			   LEFT OUTER JOIN lab_loinc ll ON ll.loinc_cd = m.loinc_cd
			   LEFT OUTER JOIN coded_lab_test_type t ON t.test_type_cd = ll.test_type_cd
			   LEFT OUTER JOIN lab_alternative_contacts t ON t.pfi = a.pfi AND t.main_contact = 'Y' AND t.retired_date IS NULL
			  WHERE a.pfi != '-999'
			%s
			)
			  SELECT clia, pfi, facilityName, nyclab, apporvalstatus, staffname, performDTA, donorlab, address, city, state, zip, phyofflab, username, statusrecct, totalrecct,
			  		Listagg(lab_submission_type, ', ') WITHIN GROUP (ORDER BY loinc) rptFileType , loinc, testType, testingStartDate, contactName, contactEmail, contactPhone
			  FROM loinc_data
			  GROUP BY clia, pfi, facilityName, nyclab, apporvalstatus, staffname, performDTA, donorlab, address, city, state, zip, phyofflab, username, statusrecct, totalrecct, loinc, testType, testingStartDate, contactName, contactEmail, contactPhone
			  ORDER BY pfi""";

	/** The former {@code <if test="approvalStatus != 0">}: the approval codes of the comma-separated list. */
	private static final String APPROVAL_STATUS_FILTER = """
			            AND b.approval_status_code IN        (
			                                                            SELECT Trim(regexp_substr(?1, '[^,]+', 1, level))
			                                                               FROM dual
			                                                CONNECT BY regexp_substr(?2, '[^,]+', 1, level) IS NOT NULL
			                                                )""";

	private final JpqlStatements jpql;
	private final NativeStatements statements;

	@PersistenceContext
	private EntityManager entityManager;

	// ---- procedures and the lab search -------------------------------------------------------------------------------

	/**
	 * The medical provider feedback report: calls {@code pkg_reporting.pr_gen_provider_summary_rpt} and puts its two
	 * cursors into the map as {@code p_overall_summary} (FeedbackReportOverallSummaryVOs) and {@code p_state_summary}
	 * (FeedbackReportStateSummaryVOs), as the MyBatis CALLABLE statement did.
	 *
	 * @param map p_provider_id
	 */
	@Transactional
	public void getFeedbackMedicalReport(Map<Object, Object> map) throws DataAccessException {
		List<List<?>> cursors = callWithTextCursors("pkg_reporting.pr_gen_provider_summary_rpt", map.get("p_provider_id"),
				LabReportsResultMaps::feedbackOverall, LabReportsResultMaps::feedbackState);
		map.put("p_overall_summary", cursors.get(0));
		map.put("p_state_summary", cursors.get(1));
	}

	/**
	 * The labs found by {@code pkg_lab_maintenance.fn_lab_search}, grouped by CLIA (each with its facilities).
	 *
	 * @param map p_clia, p_pfi, p_lab_name, p_address, p_city, p_state, p_zip, p_country, p_submission_type
	 */
	public List<ClinicalLaboratoryVO> getLabSearchResults(Map<Object, Object> map) throws DataAccessException {
		List<Row> rows = statements.list(LAB_SEARCH, LabReportsResultMaps.asRows(),
				NativeStatements.text(map.get("p_clia")), NativeStatements.text(map.get("p_pfi")), NativeStatements.text(map.get("p_lab_name")),
				NativeStatements.text(map.get("p_address")), NativeStatements.text(map.get("p_city")), NativeStatements.text(map.get("p_state")),
				NativeStatements.text(map.get("p_zip")), NativeStatements.text(map.get("p_country")),
				NativeStatements.text(map.get("p_submission_type")));
		return LabReportsResultMaps.laboratories(rows);
	}

	/**
	 * The profile of a lab: calls {@code pkg_lab_maintenance.pr_fetch_lab_profile} and puts its four cursors into the map
	 * as {@code p_permits} (LabPermitsVOs with their instruments), {@code p_loinc_codes} (LabLoincMappingVOs),
	 * {@code p_local_codes} (LabLocalCodesMappingVOs with their LOINC codes) and {@code p_map_codes} (LabTestMappingVOs),
	 * as the MyBatis CALLABLE statement did.
	 *
	 * @param map p_pfi
	 */
	@Transactional
	@SuppressWarnings("unchecked")
	public void getLabDetails(Map<Object, Object> map) throws DataAccessException {
		List<List<?>> cursors = callWithTextCursors("pkg_lab_maintenance.pr_fetch_lab_profile", map.get("p_pfi"),
				LabReportsResultMaps.asRows(), LabReportsResultMaps::loincCode, LabReportsResultMaps.asRows(), LabReportsResultMaps::testMapping);
		map.put("p_permits", LabReportsResultMaps.permits((List<Row>) cursors.get(0)));
		map.put("p_loinc_codes", cursors.get(1));
		map.put("p_local_codes", LabReportsResultMaps.localCodes((List<Row>) cursors.get(2)));
		map.put("p_map_codes", cursors.get(3));
	}

	/**
	 * Calls a procedure whose first parameter is IN and whose other parameters are OUT cursors (SYS_REFCURSOR), on the
	 * persistence context's connection in the caller's transaction, and returns each cursor's rows, mapped. Every cursor
	 * column is read with getString: all the columns of the former result maps were VARCHAR into text properties, and the
	 * VOs' other (auto-mapped) properties are text too, so MyBatis read every column as text (dates in the driver's
	 * text form). {@link NativeStatements#call} reads cursor columns with getObject, hence this method.
	 *
	 * @param in the IN value (bound as it is, or as a VARCHAR null, as {@code #{x,jdbcType=VARCHAR,mode=IN}} was)
	 */
	protected List<List<?>> callWithTextCursors(String procedure, Object in, RowMapper<?>... cursors) {
		String sql = "{call " + procedure + "(?" + ",?".repeat(cursors.length) + ")}";
		return entityManager.unwrap(Session.class).doReturningWork(connection -> {
			try (CallableStatement statement = connection.prepareCall(sql)) {
				if (in == null) {
					statement.setNull(1, Types.VARCHAR);
				} else {
					statement.setObject(1, in);
				}
				for (int i = 0; i < cursors.length; i++) {
					statement.registerOutParameter(i + 2, Types.REF_CURSOR);
				}
				statement.execute();
				List<List<?>> results = new ArrayList<>();
				for (int i = 0; i < cursors.length; i++) {
					results.add(LabReportsResultMaps.textRows(statement.getObject(i + 2, ResultSet.class), cursors[i]));
				}
				return results;
			}
		});
	}

	/**
	 * The facilities of a CLIA found by {@code pkg_lab_maintenance.fn_lab_search} (a row without values is a null
	 * element, as before).
	 *
	 * @param inOutParameterMap p_clia
	 */
	public List<LabFacilitiesVO> getFacilitiesDataForClia(Map<Object, Object> inOutParameterMap) throws DataAccessException {
		return statements.list(FACILITIES_FOR_CLIA, LabReportsResultMaps::facility, NativeStatements.text(inOutParameterMap.get("p_clia")));
	}

	// ---- permits and instruments --------------------------------------------------------------------------------------

	/** The permit codes the lab has no valid permit of: permitCode, permitDescription. */
	public List<Map<String, String>> getActiveAssociatedPermitCodesAndDescriptions(String pfi) throws DataAccessException {
		return jpql.list("""
				select cp.permitCd as PERMIT_CD, cp.permitDescription as PERMIT_DESCRIPTION from CodedPermits cp
				where not exists (select lp.permitCd from LabPermits lp where lp.pfi = ?1
				and (lp.dateValidTill is null or lp.dateValidTill > sysdate) and lp.permitCd = cp.permitCd)""",
				textMap("PERMIT_CD", "permitCode", "PERMIT_DESCRIPTION", "permitDescription"), pfi);
	}

	/** All the permit codes: permitCode, permitDescription. */
	public List<Map<String, String>> getPermitCodesAndDescriptionsForEdit() throws DataAccessException {
		return jpql.list("select cp.permitCd as PERMIT_CD, cp.permitDescription as PERMIT_DESCRIPTION from CodedPermits cp",
				textMap("PERMIT_CD", "permitCode", "PERMIT_DESCRIPTION", "permitDescription"));
	}

	/**
	 * The HIV instruments (with a code) the permit has no valid instrument row of, by name: instrumentId,
	 * instrumentName, and every other column of CODED_HIV_INSTRUMENTS under its own name (the former select *).
	 */
	@SuppressWarnings({"unchecked", "rawtypes"})
	public List<Map<String, String>> getActiveAssociatedInstrumentCodesAndDescriptions(long labPermitId) throws DataAccessException {
		return (List) statements.list(ACTIVE_ASSOCIATED_INSTRUMENTS, LabReportsResultMaps::instrumentMap, labPermitId);
	}

	/** The HIV instruments with a code, by name (see {@link #getActiveAssociatedInstrumentCodesAndDescriptions}). */
	@SuppressWarnings({"unchecked", "rawtypes"})
	public List<Map<String, String>> getInstrumentCodesAndDescriptions() throws DataAccessException {
		return (List) statements.list(INSTRUMENTS, LabReportsResultMaps::instrumentMap);
	}

	/**
	 * Changes a permit's validity, certification date and comments.
	 *
	 * @param map labPermitId, dateValidFrom, dateValidTill, bhaeCertDate (MM/DD/YYYY), lastUpdatedBy, comments
	 */
	@Transactional
	public int updateLabPermitDetails(Map<Object, Object> map) throws DataAccessException {
		return jpql.update("update LabPermits p set p.dateValidFrom = " + toDate(1) + ", p.dateValidTill = " + toDate(2)
				+ ", p.bhaeCertificationDate = " + toDate(3) + ", p.lastUpdatedBy = ?4, p.lastUpdatedDate = sysdate, p.comments = ?5"
				+ " where p.labPermitId = ?6",
				text(map.get("dateValidFrom")), text(map.get("dateValidTill")), text(map.get("bhaeCertDate")), id(map.get("lastUpdatedBy")),
				text(map.get("comments")), id(map.get("labPermitId")));
	}

	/**
	 * Changes the validity of a permit's instrument.
	 *
	 * @param map lpiId, instrumentDateValidFrom, instrumentDateValidTill (MM/DD/YYYY), lastUpdatedBy
	 */
	@Transactional
	public int updateLabInstrumentDetails(Map<Object, Object> map) throws DataAccessException {
		return jpql.update("update LabPermitInstruments i set i.startDate = " + toDate(1) + ", i.endDate = " + toDate(2)
				+ ", i.lastUpdatedBy = ?3, i.lastUpdatedDate = sysdate where i.lpiId = ?4",
				text(map.get("instrumentDateValidFrom")), text(map.get("instrumentDateValidTill")), id(map.get("lastUpdatedBy")),
				id(map.get("lpiId")));
	}

	/**
	 * Adds a permit to a lab.
	 *
	 * @param map pfi, permitCode, dateValidFrom, dateValidTill, bhaeCertDate (MM/DD/YYYY), comments, lastUpdatedBy
	 */
	@Transactional
	public int addNewPermitCodeToFacility(Map<Object, Object> map) throws DataAccessException {
		return jpql.update("insert into LabPermits (pfi, permitCd, dateValidFrom, dateValidTill, bhaeCertificationDate, comments, lastUpdatedBy,"
				+ " lastUpdatedDate) values (?1, ?2, " + toDate(3) + ", " + toDate(4) + ", " + toDate(5) + ", ?6, ?7, sysdate)",
				text(map.get("pfi")), text(map.get("permitCode")), text(map.get("dateValidFrom")), text(map.get("dateValidTill")),
				text(map.get("bhaeCertDate")), text(map.get("comments")), id(map.get("lastUpdatedBy")));
	}

	/**
	 * Adds an instrument to a permit.
	 *
	 * @param map labPermitId, instrumentId, lastUpdatedBy, dateValidFrom, dateValidTill (MM/DD/YYYY)
	 */
	@Transactional
	public int addNewInstrumentToPermit(Map<Object, Object> map) throws DataAccessException {
		return jpql.update("insert into LabPermitInstruments (labPermitId, hivInstrumentId, lastUpdatedBy, lastUpdatedDate, startDate, endDate)"
				+ " values (?1, ?2, ?3, sysdate, " + toDate(4) + ", " + toDate(5) + ")",
				id(map.get("labPermitId")), id(map.get("instrumentId")), id(map.get("lastUpdatedBy")), text(map.get("dateValidFrom")),
				text(map.get("dateValidTill")));
	}

	// ---- LOINC and local codes ----------------------------------------------------------------------------------------

	/** The LOINC codes the lab has no valid LOINC row of: loincCd, loincDescription (the test type's description). */
	public List<Map<String, String>> getActiveAndNonAssociatedLabLoincDescriptionDetails(String pfi) throws DataAccessException {
		return jpql.list("""
				select cl.loincCd as LOINC_CD, clt.description as DESCRIPTION from LabLoinc cl, CodedLabTestType clt
				where cl.testTypeCd = clt.testTypeCd and cl.loincCd not in (select llm.loincCd from LabLoincMapping llm where llm.pfi = ?1
				and (llm.endDate is null or llm.endDate > sysdate))""",
				textMap("LOINC_CD", "loincCd", "DESCRIPTION", "loincDescription"), pfi);
	}

	/** The lab's valid LOINC rows: loincMapId, loincCd, loincDescription (the test type's description). */
	public List<Map<String, String>> getActiveAssociatedLabLoincDescriptionDetails(String pfi) throws DataAccessException {
		return jpql.list("""
				select llm.labLoincMapId as LAB_LOINC_MAP_ID, cl.loincCd as LOINC_CD, clt.description as DESCRIPTION
				from LabLoincMapping llm, LabLoinc cl, CodedLabTestType clt
				where llm.pfi = ?1 and llm.loincCd = cl.loincCd and cl.testTypeCd = clt.testTypeCd
				and (llm.endDate is null or llm.endDate > sysdate)""",
				textMap("LAB_LOINC_MAP_ID", "loincMapId", "LOINC_CD", "loincCd", "DESCRIPTION", "loincDescription"), pfi);
	}

	/**
	 * Changes a LOINC row of a lab.
	 *
	 * @param map loincMapId, dateValidFrom, dateValidTill (MM/DD/YYYY), lastUpdatedBy, testKit, instrument,
	 *        vlDetectionLevel
	 */
	@Transactional
	public int updateLabLoincCodeDetails(Map<Object, Object> map) throws DataAccessException {
		return jpql.update("update LabLoincMapping m set m.startDate = " + toDate(1) + ", m.endDate = " + toDate(2)
				+ ", m.lastUpdatedBy = ?3, m.lastUpdatedDate = sysdate, m.testKit = ?4, m.instrument = ?5, m.vlDetectionLevel = ?6"
				+ " where m.labLoincMapId = ?7",
				text(map.get("dateValidFrom")), text(map.get("dateValidTill")), id(map.get("lastUpdatedBy")), text(map.get("testKit")),
				text(map.get("instrument")), text(map.get("vlDetectionLevel")), id(map.get("loincMapId")));
	}

	/**
	 * Changes the validity of a local code.
	 *
	 * @param map p_localCodeMapId, dateValidFrom, dateValidTill (MM/DD/YYYY), p_lastUpdatedBy
	 */
	@Transactional
	public int updateLabLocalCodeDetails(Map<Object, Object> map) throws DataAccessException {
		return jpql.update("update LabLocalCodes c set c.lastUpdatedBy = ?1, c.lastUpdatedDate = sysdate, c.startDate = " + toDate(2)
				+ ", c.endDate = " + toDate(3) + " where c.labLocalCdMapId = ?4",
				id(map.get("p_lastUpdatedBy")), text(map.get("dateValidFrom")), text(map.get("dateValidTill")), id(map.get("p_localCodeMapId")));
	}

	/**
	 * Ends the valid links of a LOINC row to the local codes.
	 *
	 * @param map p_loincMapId, p_endDate (MM/DD/YYYY), p_lastUpdatedBy
	 */
	@Transactional
	public int updateLabLocalCodeLoincDetails(Map<Object, Object> map) throws DataAccessException {
		return jpql.update("update LabLocalCdLoincMapping l set l.endDate = " + toDate(1) + ", l.lastUpdatedBy = ?2, l.lastUpdatedDate = sysdate"
				+ " where l.labLoincMapId = ?3 and (l.endDate is null or l.endDate > sysdate)",
				text(map.get("p_endDate")), id(map.get("p_lastUpdatedBy")), id(map.get("p_loincMapId")));
	}

	/**
	 * Changes a test code of a local code.
	 *
	 * @param inOutParameterMap p_localCodeMapId, p_testCode, p_testDescription, p_lastUpdatedBy, testCodeDateValidFrom,
	 *        testCodeDateValidTill (MM/DD/YYYY)
	 */
	@Transactional
	public int updateLabLocalCodeMappingDetails(Map<Object, Object> inOutParameterMap) {
		return jpql.update("update LabLocalCdTestCdMapping t set t.labLocalTestDesc = ?1, t.lastUpdatedBy = ?2, t.lastUpdatedDate = sysdate,"
				+ " t.startDate = " + toDate(3) + ", t.endDate = " + toDate(4) + " where t.labLocalCdMapId = ?5 and t.labLocalTestCd = ?6",
				text(inOutParameterMap.get("p_testDescription")), id(inOutParameterMap.get("p_lastUpdatedBy")),
				text(inOutParameterMap.get("testCodeDateValidFrom")), text(inOutParameterMap.get("testCodeDateValidTill")),
				id(inOutParameterMap.get("p_localCodeMapId")), id(inOutParameterMap.get("p_testCode")));
	}

	/**
	 * Adds a LOINC code to a lab.
	 *
	 * @param map pfi, loincCode, lastUpdatedBy, dateValidFrom, dateValidTill (MM/DD/YYYY), testKit, instrument,
	 *        vlDetectionLevel
	 */
	@Transactional
	public int addNewLabLoincToFacility(Map<Object, Object> map) throws DataAccessException {
		return jpql.update("insert into LabLoincMapping (pfi, loincCd, lastUpdatedBy, lastUpdatedDate, startDate, endDate, testKit, instrument,"
				+ " vlDetectionLevel) values (?1, ?2, ?3, sysdate, " + toDate(4) + ", " + toDate(5) + ", ?6, ?7, ?8)",
				text(map.get("pfi")), text(map.get("loincCode")), id(map.get("lastUpdatedBy")), text(map.get("dateValidFrom")),
				text(map.get("dateValidTill")), text(map.get("testKit")), text(map.get("instrument")), text(map.get("vlDetectionLevel")));
	}

	/**
	 * Adds a test code to a local code.
	 *
	 * @param map localCodeMapId, testDescription, lastUpdatedBy, dateValidFrom, dateValidTill (MM/DD/YYYY)
	 */
	@Transactional
	public int addNewLabTestToFacility(Map<Object, Object> map) throws DataAccessException {
		return jpql.update("insert into LabLocalCdTestCdMapping (labLocalCdMapId, labLocalTestDesc, lastUpdatedBy, lastUpdatedDate, startDate,"
				+ " endDate) values (?1, ?2, ?3, sysdate, " + toDate(4) + ", " + toDate(5) + ")",
				id(map.get("localCodeMapId")), text(map.get("testDescription")), id(map.get("lastUpdatedBy")), text(map.get("dateValidFrom")),
				text(map.get("dateValidTill")));
	}

	/**
	 * Adds a local code to a lab.
	 *
	 * @param map pfi, localCode, lastUpdatedBy, dateValidFrom, dateValidTill (MM/DD/YYYY)
	 */
	@Transactional
	public int addNewLabLocalCodeToFacility(Map<Object, Object> map) throws DataAccessException {
		return jpql.update("insert into LabLocalCodes (pfi, localCd, lastUpdatedBy, lastUpdatedDate, startDate, endDate)"
				+ " values (?1, ?2, ?3, sysdate, " + toDate(4) + ", " + toDate(5) + ")",
				text(map.get("pfi")), text(map.get("localCode")), id(map.get("lastUpdatedBy")), text(map.get("dateValidFrom")),
				text(map.get("dateValidTill")));
	}

	/** The keys of a lab's local code (pfi, localCode). */
	public List<Long> getLabLocalCodeMapId(Map<Object, Object> map) throws DataAccessException {
		return jpql.list("select c.labLocalCdMapId from LabLocalCodes c where c.pfi = ?1 and c.localCd = ?2", Rows.scalar(Long.class),
				text(map.get("pfi")), text(map.get("localCode")));
	}

	/**
	 * Links LOINC rows to a local code: one row per LOINC row, valid from now until the latest end date of that LOINC
	 * row (the former {@code (select Max(END_DATE) ...)} of each row).
	 *
	 * @param map localCodeMapId, loincMapIds (an array or a collection, not empty: the former statement failed then
	 *        too), lastUpdatedBy
	 * @return the number of rows inserted
	 */
	@Transactional
	public int linkLoincCodesToLoclCode(Map<Object, Object> map) throws DataAccessException {
		int count = 0;
		for (Long loincMapId : LaftRows.longs(map.get("loincMapIds"), "loincMapIds")) {
			count += jpql.update("insert into LabLocalCdLoincMapping (labLocalCdMapId, labLoincMapId, lastUpdatedBy, lastUpdatedDate, startDate,"
					+ " endDate) select cast(?1 as Long), cast(?2 as Long), cast(?3 as Long), sysdate, sysdate, max(m.endDate)"
					+ " from LabLoincMapping m where m.labLoincMapId = ?2",
					id(map.get("localCodeMapId")), loincMapId, id(map.get("lastUpdatedBy")));
		}
		return count;
	}

	/** The LOINC rows validly linked to a local code. */
	public List<Long> getSelectedLoincCodesOfLocalCode(Long localCodeMapId) throws DataAccessException {
		return jpql.list("select l.labLoincMapId from LabLocalCdLoincMapping l where l.labLocalCdMapId = ?1 and (l.endDate is null or l.endDate > sysdate)",
				Rows.scalar(Long.class), localCodeMapId);
	}

	/** Not used (no caller): the key of a lab's LOINC row (pfi, loincCode); more than one row fails, as before. */
	public String getLoincMapId(Map<Object, Object> map) throws DataAccessException {
		return jpql.one("select m.labLoincMapId from LabLoincMapping m where m.pfi = ?1 and m.loincCd = ?2", Rows.scalar(String.class),
				text(map.get("pfi")), text(map.get("loincCode")));
	}

	/** The lab of a local code (the first PFI; more than one row fails, as before). */
	public String checkForLocalCodeExistance(String localCode) {
		return jpql.one("select c.pfi from LabLocalCodes c where c.localCd = ?1", Rows.scalar(String.class), localCode);
	}

	/** The lab's valid LOINC codes. */
	public List<String> getLoincsForPfi(String pfi) {
		return jpql.list("select m.loincCd from LabLoincMapping m where m.pfi = ?1 and (m.endDate is null or m.endDate > sysdate)",
				Rows.scalar(String.class), pfi);
	}

	/** The test type's description and unit of a LOINC code: DESCRIPTION, UNIT_OF_RESULT. */
	public JSONObject getLoincReferenceValues(String loincCode) {
		return jpql.one("""
				select testType.description as DESCRIPTION, testType.unitOfResult as UNIT_OF_RESULT from LabLoinc ll, CodedLabTestType testType
				where ll.testTypeCd = testType.testTypeCd and ll.loincCd = ?1""", LaftRows.json(), loincCode);
	}

	/** The number of test codes of a local code with this description. */
	public int getExistingTestCodeRecordsCount(Map<Object, Object> inOutParameterMap) {
		return jpql.one("select count(*) from LabLocalCdTestCdMapping t where t.labLocalTestDesc = ?1 and t.labLocalCdMapId = ?2",
				Rows.scalar(Integer.class), text(inOutParameterMap.get("testDescription")), id(inOutParameterMap.get("localCodeMapId")));
	}

	/** The number of valid links of a LOINC row to local codes. */
	public int checkForActiveLoincsAssociatedToLocalCode(Map<Object, Object> inOutParameterMap) throws DataAccessException {
		return jpql.one("select count(*) from LabLocalCdLoincMapping l where l.labLoincMapId = ?1 and (l.endDate is null or l.endDate > sysdate)",
				Rows.scalar(Integer.class), id(inOutParameterMap.get("p_loincMapId")));
	}

	// ---- communications -------------------------------------------------------------------------------------------

	/**
	 * Not implemented: the former mapper had no statement for it, so a call failed (MyBatis' "Invalid bound statement");
	 * it still fails (kept as it was).
	 */
	public List<LabInteractionHistoryVO> getInterationHistory(String pfi) throws DataAccessException {
		throw new InvalidDataAccessApiUsageException("Invalid bound statement (not found): " + LabReportsDAO.class.getName() + ".getInterationHistory");
	}

	/**
	 * Not implemented: the former mapper had no statement for it, so a call failed (MyBatis' "Invalid bound statement");
	 * it still fails (kept as it was).
	 */
	public int updateLabCommunicationDetails(Map<Object, Object> map) throws DataAccessException {
		throw new InvalidDataAccessApiUsageException("Invalid bound statement (not found): " + LabReportsDAO.class.getName()
				+ ".updateLabCommunicationDetails");
	}

	/** The communications with a lab, newest first, with the type's description and the worker's name. */
	public List<LabInteractionHistoryVO> getCommunicationHistory(String pfi) throws DataAccessException {
		return jpql.list("""
				select lih.interactionId as interactionId, lih.interactionNotes as interactionNotes,
				lct.communicationTypeDescription as communicationType, lih.interactionBy as workerId,
				(u.lastName || ', ' || u.firstName || ' (' || u.legacyUid || ')') as interactionBy,
				format(lih.interactionDate as 'MM/dd/yyyy') as interactionDate
				from LabInteractionHistory lih join CodedLabCommunicationType lct on lih.communicationType = lct.communicationType
				join NyehmsUsers u on lih.interactionBy = u.workerId where lih.pfi = ?1 order by lih.interactionDate desc""",
				Rows.bean(LabInteractionHistoryVO.class), pfi);
	}

	/** A communication (its type as the code); more than one row fails, as before. */
	public LabInteractionHistoryVO getLabCommunication(String interactionId) throws DataAccessException {
		return jpql.one("""
				select lih.interactionId as interactionId, u.legacyUid as interactionBy, format(lih.interactionDate as 'MM/dd/yyyy') as interactionDate,
				lih.communicationType as communicationType, lih.interactionNotes as interactionNotes
				from LabInteractionHistory lih join NyehmsUsers u on lih.interactionBy = u.workerId where lih.interactionId = ?1""",
				Rows.bean(LabInteractionHistoryVO.class), id(interactionId));
	}

	/**
	 * Adds a communication with a lab.
	 *
	 * @param map interactionDate (MM/DD/YYYY), interactionBy, communicationType, interactionNotes, pfi
	 */
	@Transactional
	public int addNewCommunicationToFacility(Map<Object, Object> map) throws DataAccessException {
		return jpql.update("insert into LabInteractionHistory (interactionDate, interactionBy, communicationType, interactionNotes, pfi)"
				+ " values (" + toDate(1) + ", ?2, ?3, ?4, ?5)",
				text(map.get("interactionDate")), id(map.get("interactionBy")), text(map.get("communicationType")),
				text(map.get("interactionNotes")), text(map.get("pfi")));
	}

	/**
	 * Adds a communication note (dated now) to a lab.
	 *
	 * @param map pfi, p_lastUpdatedBy, interactionContactType, interactionNotes
	 */
	@Transactional
	public int addLabInteractionNotes(Map<Object, Object> map) throws DataAccessException {
		return jpql.update("insert into LabInteractionHistory (pfi, interactionDate, interactionBy, communicationType, interactionNotes)"
				+ " values (?1, sysdate, ?2, ?3, ?4)",
				text(map.get("pfi")), id(map.get("p_lastUpdatedBy")), text(map.get("interactionContactType")), text(map.get("interactionNotes")));
	}

	/** The current communication types by sort order: COMMUNICATION_TYPE, COMMUNICATION_TYPE_DESCRIPTION. */
	public List<JSONObject> getInteractionContactTypes() {
		return jpql.list("""
				select t.communicationType as COMMUNICATION_TYPE, t.communicationTypeDescription as COMMUNICATION_TYPE_DESCRIPTION
				from CodedLabCommunicationType t where t.endDate is null order by t.sortOrder""", LaftRows.json());
	}

	// ---- labs (CLIA, physical facilities) ---------------------------------------------------------------------------

	/**
	 * Changes a lab's details.
	 *
	 * @param map p_pfi, p_clia (the lab), p_lab_name, p_address, p_laftStaffAssigned, p_city, p_state, p_zip,
	 *        p_country, p_flag_active, p_lastUpdatedBy, p_flagServersNYC, p_flagBloodBank, p_approvalStatusCode,
	 *        p_lowVolume, p_physicianOfficeLab, p_refersDTATestingTo, p_performsDTA
	 */
	@Transactional
	public int saveLabFacilityDetails(Map<Object, Object> map) throws DataAccessException {
		return jpql.update("""
				update LabPhysicalFacility f set f.labName = ?1, f.address = ?2, f.laftStaff = ?3, f.city = ?4, f.state = ?5, f.zip = ?6,
				f.country = ?7, f.flagActive = ?8, f.lastUpdatedBy = ?9, f.lastUpdatedDate = sysdate, f.flagServesNyc = ?10,
				f.flagBloodBank = ?11, f.approvalStatusCode = ?12, f.lowVolume = ?13, f.phyOffLab = ?14, f.refersDtaTestingTo = ?15,
				f.performsDta = ?16 where f.pfi = ?17 and f.clia = ?18""",
				text(map.get("p_lab_name")), text(map.get("p_address")), text(map.get("p_laftStaffAssigned")), text(map.get("p_city")),
				text(map.get("p_state")), text(map.get("p_zip")), text(map.get("p_country")), text(map.get("p_flag_active")),
				id(map.get("p_lastUpdatedBy")), text(map.get("p_flagServersNYC")), text(map.get("p_flagBloodBank")),
				id(map.get("p_approvalStatusCode")), text(map.get("p_lowVolume")), text(map.get("p_physicianOfficeLab")),
				text(map.get("p_refersDTATestingTo")), text(map.get("p_performsDTA")), text(map.get("p_pfi")), text(map.get("p_clia")));
	}

	/** Moves a lab to another CLIA. */
	@Transactional
	public int changeCliaForPfi(Map<Object, Object> inOutParameterMap) {
		return jpql.update("update LabPhysicalFacility f set f.clia = ?1, f.lastUpdatedBy = ?2, f.lastUpdatedDate = sysdate where f.pfi = ?3",
				text(inOutParameterMap.get("clia")), id(inOutParameterMap.get("workerId")), text(inOutParameterMap.get("pfi")));
	}

	/** Adds a CLIA. */
	@Transactional
	public int addNewClia(String clia) throws DataAccessException {
		return jpql.update("insert into ClinicalLaboratory (clia) values (?1)", clia);
	}

	/**
	 * Adds labs to a CLIA.
	 *
	 * @param inOutParameterMap clia, lastUpdatedBy, labFacilitiyDetails (LabFacilitiesVOs, not empty: the former
	 *        statement failed then too)
	 * @return the number of labs inserted
	 */
	@Transactional
	public int addNewLabFacilitiesToClia(Map<Object, Object> inOutParameterMap) throws DataAccessException {
		int count = 0;
		for (Object item : items(inOutParameterMap.get("labFacilitiyDetails"), "labFacilitiyDetails")) {
			LabFacilitiesVO facility = (LabFacilitiesVO) item;
			count += jpql.update("insert into LabPhysicalFacility (pfi, labName, address, city, state, zip, country, clia, lastUpdatedBy,"
					+ " lastUpdatedDate, laftStaff) values (?1, ?2, ?3, ?4, ?5, ?6, ?7, ?8, ?9, sysdate, ?10)",
					facility.getPfi(), facility.getLabName(), facility.getAddress(), facility.getCity(), facility.getState(),
					facility.getZipCode(), facility.getCountry(), text(inOutParameterMap.get("clia")), id(inOutParameterMap.get("lastUpdatedBy")),
					facility.getLaftStaffAssigned());
		}
		return count;
	}

	/** All the CLIAs, in order. */
	public List<String> getClinicalLabarotories() throws DataAccessException {
		return jpql.list("select c.clia from ClinicalLaboratory c order by c.clia", Rows.scalar(String.class));
	}

	/** The CLIA if it exists (more than one row fails, as before). */
	public String checkCliaExiststence(String clia) throws DataAccessException {
		return jpql.one("select c.clia from ClinicalLaboratory c where c.clia = ?1", Rows.scalar(String.class), clia);
	}

	/** The PFI if it exists (more than one row fails, as before). */
	public String checkPFIExistence(String pfi) throws DataAccessException {
		return jpql.one("select f.pfi from LabPhysicalFacility f where f.pfi = ?1", Rows.scalar(String.class), pfi);
	}

	/** A lab's details (PFI, CLIA, LAB_NAME, ... PERFORMS_DTA); more than one row fails, as before. */
	public JSONObject geLabPhysicalFacilityDetails(Map<Object, Object> inOutParameterMap) throws DataAccessException {
		return jpql.one("""
				select f.pfi as PFI, f.clia as CLIA, f.labName as LAB_NAME, f.address as ADDRESS, f.city as CITY, f.state as STATE, f.zip as ZIP,
				f.country as COUNTRY, f.flagActive as FLAG_ACTIVE, f.flagServesNyc as FLAG_SERVES_NYC, f.flagBloodBank as FLAG_BLOOD_BANK,
				f.laftStaff as LAFT_STAFF, f.approvalStatusCode as APPROVAL_STATUS_CODE, f.lowVolume as LOW_VOLUME, f.phyOffLab as PHY_OFF_LAB,
				f.refersDtaTestingTo as REFERS_DTA_TESTING_TO, f.performsDta as PERFORMS_DTA
				from LabPhysicalFacility f where f.pfi = ?1 and f.clia = ?2""", LaftRows.json(),
				text(inOutParameterMap.get("pfi")), text(inOutParameterMap.get("clia")));
	}

	/** The last eCLRS HIV lab import date of a lab (MM/DD/YYYY), or null. */
	public String getlastEcrlsImportDate(String pfi) {
		return jpql.one("select format(max(r.mshcreatedate) as 'MM/dd/yyyy') from RawHivEclrsDoc r where r.mshlabpfi = ?1",
				Rows.scalar(String.class), pfi);
	}

	/** The distinct lab names other than DEFAULT and TEST, in order: labName, labDesc (the name again). */
	public List<Map<String, String>> getRefersDTATestingToMap() throws DataAccessException {
		// the same attribute twice: read by position (the select items' aliases are not kept apart)
		return jpql.list("select distinct f.labName as LAB_NAME, f.labName as LAB_DESC from LabPhysicalFacility f"
				+ " where f.labName not in ('DEFAULT', 'TEST') order by f.labName", row -> {
					Map<String, String> names = new HashMap<>();
					if (row.get(0) != null) {
						names.put("labName", row.get(0).toString());
						names.put("labDesc", row.get(1).toString());
					}
					return names.isEmpty() ? null : names;
				});
	}

	// ---- contacts -------------------------------------------------------------------------------------------------

	/**
	 * Adds an alternative contact to a lab.
	 *
	 * @param inOutParameterMap pfi, alternativeContactName, alternativeContactPhone, alternativeContactEmail,
	 *        alternativeContactFax
	 */
	@Transactional
	public int addNewAlternativeContact(Map<Object, Object> inOutParameterMap) throws DataAccessException {
		return jpql.update("insert into LabAlternativeContacts (pfi, contactName, phone, email, fax) values (?1, ?2, ?3, ?4, ?5)",
				text(inOutParameterMap.get("pfi")), text(inOutParameterMap.get("alternativeContactName")),
				text(inOutParameterMap.get("alternativeContactPhone")), text(inOutParameterMap.get("alternativeContactEmail")),
				text(inOutParameterMap.get("alternativeContactFax")));
	}

	/**
	 * Adds a contact to a lab and puts its new key into the VO (CONTACTID), as the former generated key did.
	 */
	@Transactional
	public void addNewContactToFacility(LabNewContactVO labNewContactVO) {
		LabAlternativeContacts contact = LabAlternativeContacts.builder()
				.pfi(labNewContactVO.getPFI())
				.contactName(labNewContactVO.getNAME())
				.phone(labNewContactVO.getPHONE())
				.email(labNewContactVO.getEMAIL())
				.contactTypeCode(labNewContactVO.getCONTACTTYPE())
				.sendReportCard(labNewContactVO.getREPORTCARD())
				.sendCountReport(labNewContactVO.getCOUNTS())
				.qa(labNewContactVO.getQA())
				.createdBy((long) labNewContactVO.getUserModified())
				.createdDate(sysdate())
				.notes(labNewContactVO.getNOTES())
				.mainContact(labNewContactVO.getMAINCONTACT())
				.phoneExt(labNewContactVO.getPHONEEXT())
				.build();
		jpql.insert(contact);
		labNewContactVO.setCONTACTID(contact.getContactId().intValue());
	}

	/**
	 * Adds contacts to a lab.
	 *
	 * @param inOutParameterMap pfi, workerId, pfiContactList (JSONObjects with NAME, PHONE, EMAIL, CONTACTTYPE,
	 *        REPORTCARD, COUNTS, QA; not empty: the former statement failed then too)
	 * @return the number of contacts inserted
	 */
	@Transactional
	public int addNewLabContactsToPFI(Map<Object, Object> inOutParameterMap) {
		int count = 0;
		for (Object item : items(inOutParameterMap.get("pfiContactList"), "pfiContactList")) {
			Map<?, ?> contact = (Map<?, ?>) item;
			count += jpql.update("insert into LabAlternativeContacts (pfi, contactName, phone, email, contactTypeCode, sendReportCard,"
					+ " sendCountReport, qa, createdBy, createdDate) values (?1, ?2, ?3, ?4, ?5, ?6, ?7, ?8, ?9, sysdate)",
					text(inOutParameterMap.get("pfi")), text(contact.get("NAME")), text(contact.get("PHONE")), text(contact.get("EMAIL")),
					text(contact.get("CONTACTTYPE")), text(contact.get("REPORTCARD")), text(contact.get("COUNTS")), text(contact.get("QA")),
					id(inOutParameterMap.get("workerId")));
		}
		return count;
	}

	/** Changes a lab contact. */
	@Transactional
	public int updatePFILabContact(LabNewContactVO labNewContactVO) {
		return jpql.update("""
				update LabAlternativeContacts c set c.phone = ?1, c.phoneExt = ?2, c.email = ?3, c.sendReportCard = ?4, c.sendCountReport = ?5,
				c.mainContact = ?6, c.qa = ?7, c.lastUpdatedBy = ?8, c.notes = ?9, c.lastUpdatedDate = sysdate where c.contactId = ?10""",
				labNewContactVO.getPHONE(), labNewContactVO.getPHONEEXT(), labNewContactVO.getEMAIL(), labNewContactVO.getREPORTCARD(),
				labNewContactVO.getCOUNTS(), labNewContactVO.getMAINCONTACT(), labNewContactVO.getQA(), (long) labNewContactVO.getUserModified(),
				labNewContactVO.getNOTES(), (long) labNewContactVO.getCONTACTID());
	}

	/**
	 * Retires a contact (the former {@code ${workerId}} is a bound value now).
	 *
	 * @param inOutParameterMap workerId, contactId
	 */
	@Transactional
	public int deleteContactForPFI(Map<Object, Object> inOutParameterMap) {
		return jpql.update("update LabAlternativeContacts c set c.retiredDate = sysdate, c.lastUpdatedBy = ?1, c.lastUpdatedDate = sysdate"
				+ " where c.contactId = ?2", id(inOutParameterMap.get("workerId")), id(inOutParameterMap.get("contactId")));
	}

	/**
	 * The lab's current contacts with their type's description: CONTACTID, RETIREDDATE, NAME, PHONE, PHONEEXT, EMAIL,
	 * CONTACTTYPE, REPORTCARD, COUNTS, DONOR, QA, CONTACTTYPEDESC, MAINCONTACT, NOTES.
	 */
	public List<JSONObject> geLabContactsForPFI(String pfi) throws DataAccessException {
		return jpql.list("""
				select labContact.contactId as CONTACTID, format(labContact.retiredDate as 'MM/dd/yyyy') as RETIREDDATE, labContact.contactName as NAME,
				labContact.phone as PHONE, labContact.phoneExt as PHONEEXT, labContact.email as EMAIL, labContact.contactTypeCode as CONTACTTYPE,
				labContact.sendReportCard as REPORTCARD, labContact.sendCountReport as COUNTS, labContact.sendDonorReport as DONOR,
				labContact.qa as QA, contactType.contactTypeDesc as CONTACTTYPEDESC, labContact.mainContact as MAINCONTACT, labContact.notes as NOTES
				from LabAlternativeContacts labContact, CodedPfiContactType contactType
				where labContact.pfi = ?1 and contactType.contactTypeCode = labContact.contactTypeCode and labContact.retiredDate is null""",
				LaftRows.json(), pfi);
	}

	/**
	 * All the lab's contacts with their type's description: CONTACTID, RETIREDDATE, NAME, PHONE, EMAIL, CONTACTTYPE,
	 * REPORTCARD, COUNTS, DONOR, STARHS, CONTACTTYPEDESC, MAINCONTACT.
	 */
	public List<JSONObject> getLabSubmissionTypesForPFI(String pfi) {
		return jpql.list("""
				select labContact.contactId as CONTACTID, format(labContact.retiredDate as 'MM/dd/yyyy') as RETIREDDATE, labContact.contactName as NAME,
				labContact.phone as PHONE, labContact.email as EMAIL, labContact.contactTypeCode as CONTACTTYPE,
				labContact.sendReportCard as REPORTCARD, labContact.sendCountReport as COUNTS, labContact.sendDonorReport as DONOR,
				labContact.sendStarhsReport as STARHS, contactType.contactTypeDesc as CONTACTTYPEDESC, labContact.mainContact as MAINCONTACT
				from LabAlternativeContacts labContact, CodedPfiContactType contactType
				where labContact.pfi = ?1 and contactType.contactTypeCode = labContact.contactTypeCode""",
				LaftRows.json(), pfi);
	}

	/** The current contact types by sort order: CONTACT_TYPE_CODE, CONTACT_TYPE_DESC. */
	public List<Map<String, String>> getPFIContactTypeMap() throws DataAccessException {
		return jpql.list("select t.contactTypeCode as CONTACT_TYPE_CODE, t.contactTypeDesc as CONTACT_TYPE_DESC from CodedPfiContactType t"
				+ " where t.endDate is null order by t.sortOrder",
				textMap("CONTACT_TYPE_CODE", "CONTACT_TYPE_CODE", "CONTACT_TYPE_DESC", "CONTACT_TYPE_DESC"));
	}

	// ---- submission types -----------------------------------------------------------------------------------------

	/**
	 * Adds a submission type to a lab and puts its new key into the VO (LAB_SUBMIT_ID), as the former generated key did.
	 */
	@Transactional
	public void addNewSubmissionTypeToFacility(LabSubmissionTypeVO labSubmissionTypeVO) {
		LabSubmissionTypeMapping submission = LabSubmissionTypeMapping.builder()
				.pfi(labSubmissionTypeVO.getPFI())
				.submissionTypeCd(labSubmissionTypeVO.getSUBMISSION_TYPE_CD())
				.lastUpdatedBy((long) labSubmissionTypeVO.getUserModified())
				.lastUpdatedDate(sysdate())
				.startDate(toDate(labSubmissionTypeVO.getSTART_DATE()))
				.endDate(toDate(labSubmissionTypeVO.getEND_DATE()))
				.lisVendor(labSubmissionTypeVO.getLIS_VENDOR())
				.build();
		jpql.insert(submission);
		labSubmissionTypeVO.setLAB_SUBMIT_ID(submission.getLabSubmitId().intValue());
	}

	/**
	 * Adds submission types to a lab.
	 *
	 * @param inOutParameterMap pfi, workerId, newSubmissionDataList (JSONObjects with SUBMISSION_TYPE_CD, START_DATE,
	 *        END_DATE (MM/DD/YYYY); not empty: the former statement failed then too)
	 * @return the number of submission types inserted
	 */
	@Transactional
	public int addNewSubmissionDataTypesToPFI(Map<Object, Object> inOutParameterMap) {
		int count = 0;
		for (Object item : items(inOutParameterMap.get("newSubmissionDataList"), "newSubmissionDataList")) {
			Map<?, ?> submission = (Map<?, ?>) item;
			count += jpql.update("insert into LabSubmissionTypeMapping (pfi, submissionTypeCd, lastUpdatedBy, lastUpdatedDate, startDate, endDate)"
					+ " values (?1, ?2, ?3, sysdate, " + toDate(4) + ", " + toDate(5) + ")",
					text(inOutParameterMap.get("pfi")), text(submission.get("SUBMISSION_TYPE_CD")), id(inOutParameterMap.get("workerId")),
					text(submission.get("START_DATE")), text(submission.get("END_DATE")));
		}
		return count;
	}

	/** Changes a submission type of a lab (primary flag and validity). */
	@Transactional
	public int updateSubmissionData(LabSubmissionTypeVO labSubmissionTypeVO) {
		return jpql.update("update LabSubmissionTypeMapping s set s.primarySubmission = ?1, s.startDate = " + toDate(2) + ", s.endDate = "
				+ toDate(3) + ", s.lastUpdatedBy = ?4, s.lastUpdatedDate = sysdate where s.labSubmitId = ?5",
				labSubmissionTypeVO.getPRIMARY_SUBMISSION(), labSubmissionTypeVO.getSTART_DATE(), labSubmissionTypeVO.getEND_DATE(),
				(long) labSubmissionTypeVO.getUserModified(), (long) labSubmissionTypeVO.getLAB_SUBMIT_ID());
	}

	/**
	 * Ends a submission type of a lab now (the former {@code ${workerId}} is a bound value now).
	 *
	 * @param inOutParameterMap workerId, submissionTypeId
	 */
	@Transactional
	public int deleteSubmissionTypeForPFI(Map<Object, Object> inOutParameterMap) {
		return jpql.update("update LabSubmissionTypeMapping s set s.endDate = sysdate, s.lastUpdatedBy = ?1, s.lastUpdatedDate = sysdate"
				+ " where s.labSubmitId = ?2", id(inOutParameterMap.get("workerId")), id(inOutParameterMap.get("submissionTypeId")));
	}

	/**
	 * The lab's submission types with their description and LIS vendor: LAB_SUBMIT_ID, SUBMISSION_TYPE_CD, START_DATE,
	 * END_DATE (MM/DD/YYYY), DESCRIPTION, PRIMARY_SUBMISSION, PFI, LIS_VENDOR, LIS_VENDOR_DESC.
	 */
	public List<JSONObject> getsubmissionDataTypesForPFI(String pfi) throws DataAccessException {
		return jpql.list("""
				select submissionTypeData.labSubmitId as LAB_SUBMIT_ID, submissionTypeData.submissionTypeCd as SUBMISSION_TYPE_CD,
				format(submissionTypeData.startDate as 'MM/dd/yyyy') as START_DATE, format(submissionTypeData.endDate as 'MM/dd/yyyy') as END_DATE,
				submissionDesc.description as DESCRIPTION, submissionTypeData.primarySubmission as PRIMARY_SUBMISSION, submissionTypeData.pfi as PFI,
				submissionTypeData.lisVendor as LIS_VENDOR, vendor.lisVendorDesc as LIS_VENDOR_DESC
				from LabSubmissionTypeMapping submissionTypeData
				left join CodedLabLisVendor vendor on submissionTypeData.lisVendor = vendor.lisVendorCd
				join CodedLabSubmissionType submissionDesc on submissionTypeData.submissionTypeCd = submissionDesc.submissionTypeCd
				where submissionTypeData.pfi = ?1""", LaftRows.json(), pfi);
	}

	/** The submission types the lab has no valid row of: every column of CODED_LAB_SUBMISSION_TYPE (the former select *). */
	public List<JSONObject> getSubmissionTypes(String pfi) {
		return statements.list(SUBMISSION_TYPES, Rows.json(), NativeStatements.text(pfi));
	}

	/** The lab's valid primary submission types: every column of LAB_SUBMISSION_TYPE_MAPPING (the former select *). */
	public List<JSONObject> getPrimarySubmissionTypes(String pfi) {
		return statements.list(PRIMARY_SUBMISSION_TYPES, Rows.json(), NativeStatements.text(pfi));
	}

	/** The LIS vendors: LIS_VENDOR_CD, LIS_VENDOR_DESC. */
	public List<JSONObject> getLabLisVendors() {
		return jpql.list("select v.lisVendorCd as LIS_VENDOR_CD, v.lisVendorDesc as LIS_VENDOR_DESC from CodedLabLisVendor v", LaftRows.json());
	}

	// ---- lookups --------------------------------------------------------------------------------------------------

	/**
	 * The approval statuses by sort order: APPROVAL_STATUS_CODE (as text) and APPROVAL_STATUS_DESC (the former result
	 * map named a CONTACT_TYPE_DESC column that the query does not have; the description came in by auto-mapping).
	 */
	public List<Map<String, String>> getLabApprovalStatusMap() throws DataAccessException {
		return jpql.list("select s.approvalStatusCode as APPROVAL_STATUS_CODE, s.approvalStatusDesc as APPROVAL_STATUS_DESC"
				+ " from CodedLabApprovalStatus s order by s.sortOrder",
				textMap("APPROVAL_STATUS_CODE", "APPROVAL_STATUS_CODE", "APPROVAL_STATUS_DESC", "APPROVAL_STATUS_DESC"));
	}

	/** The viral load detection levels by sort order: detectionCd, detectionDesc. */
	public List<Map<String, String>> getVLDetectionLevelsCodesAndDescriptions() throws DataAccessException {
		return jpql.list("select d.detectionCd as DETECTION_CD, d.detectionDesc as DETECTION_DESC from CodedVlDetectionLevels d order by d.sortOrder",
				textMap("DETECTION_CD", "detectionCd", "DETECTION_DESC", "detectionDesc"));
	}

	/** The test kits by sort order: testKitCd, testKitDesc. */
	public List<Map<String, String>> getTestKitCodesAndDescriptions() throws DataAccessException {
		return jpql.list("select k.testKitCd as TEST_KIT_CD, k.testKitDesc as TEST_KIT_DESC from CodedLabTestKit k order by k.sortOrder",
				textMap("TEST_KIT_CD", "testKitCd", "TEST_KIT_DESC", "testKitDesc"));
	}

	/** The instruments by sort order: instrumentCd, instrumentDesc. */
	public List<Map<String, String>> getLabInstrumentCodesAndDescriptions() throws DataAccessException {
		return jpql.list("select i.instrumentCd as INSTRUMENT_CD, i.instrumentDesc as INSTRUMENT_DESC from CodedLabInstrument i order by i.sortOrder",
				textMap("INSTRUMENT_CD", "instrumentCd", "INSTRUMENT_DESC", "instrumentDesc"));
	}

	// ---- reports --------------------------------------------------------------------------------------------------

	/**
	 * Not used (StatusReportsService reads it through StatusLabReportsDAO): the SQL the caller gives (SQL_QUERY) with the
	 * lab report count result map; the former statement was a copy of StatusLabReportsDAO's, so it runs the same way.
	 */
	public List<LabReportsCountVO> getLabReportCount(Map<String, String> inputMap) {
		return new StatusLabReportsDAO(statements).getLabReportCount(inputMap);
	}

	/**
	 * The communications with the labs (other than -999) between two dates (MM/DD/YYYY), by date and PFI.
	 *
	 * @param map p_from_date, p_to_date
	 */
	public List<LabOpsReportsVO> getNycNysCommReportData(Map<Object, Object> map) {
		return statements.list(NYC_NYS_COMM_REPORT, Rows.bean(LabOpsReportsVO.class), NativeStatements.text(map.get("p_from_date")),
				NativeStatements.text(map.get("p_to_date")));
	}

	/**
	 * The labs (other than -999) with their approval status, LAFT staff, submission types, LOINC codes and main contact,
	 * by PFI; only the given approval statuses when approvalStatus is not 0 (OGNL {@code approvalStatus != 0}: an empty
	 * text and a number equal to 0 select all; null filters by an empty list, so no lab; a text that is not a number,
	 * such as a list of several codes, fails, as before).
	 *
	 * @param map approvalStatus (a comma-separated list of approval status codes)
	 */
	public List<LabOpsReportsVO> getLabListByApprovalStatus(Map<Object, Object> map) {
		Object approvalStatus = map.get("approvalStatus");
		if (ognlNotZero(approvalStatus)) {
			return statements.list(String.format(LAB_LIST_BY_APPROVAL_STATUS, APPROVAL_STATUS_FILTER), Rows.bean(LabOpsReportsVO.class),
					approvalStatus, approvalStatus);
		}
		return statements.list(String.format(LAB_LIST_BY_APPROVAL_STATUS, ""), Rows.bean(LabOpsReportsVO.class));
	}

	/** The labs (other than -999) with their approval status, by status description. */
	public List<LabOpsReportsVO> getCountListByApprovalStatus() {
		return jpql.list("""
				select a.clia as clia, a.pfi as pfi, a.labName as facilityName, coalesce(a.flagServesNyc, 'N') as nycLab, a.performsDta as performDTA,
				a.flagBloodBank as donorLab, b.approvalStatusDesc as apporvalStatus, a.laftStaff as staffName, a.address as address, a.city as city,
				a.state as state, a.zip as zip
				from LabPhysicalFacility a left join CodedLabApprovalStatus b on a.approvalStatusCode = b.approvalStatusCode
				where a.pfi <> '-999' order by b.approvalStatusDesc""", Rows.bean(LabOpsReportsVO.class));
	}

	// ---- helpers --------------------------------------------------------------------------------------------------

	/**
	 * Map rows of a Map result map whose columns are read as text: the select items (labels) to their keys; null values
	 * left out, a row of nulls null.
	 */
	private static RowMapper<Map<String, String>> textMap(String... labelsAndKeys) {
		return row -> {
			Map<String, String> map = new HashMap<>();
			for (int i = 0; i < labelsAndKeys.length; i += 2) {
				Object value = row.get(labelsAndKeys[i]);
				if (value != null) {
					map.put(labelsAndKeys[i + 1], value instanceof BigDecimal number ? number.toPlainString() : value.toString());
				}
			}
			return map.isEmpty() ? null : map;
		};
	}

	/** The items of a former {@code <foreach>} collection; none fails (the former SQL then had no rows to insert). */
	private static List<Object> items(Object values, String name) {
		return new ArrayList<>(LaftRows.inList(values, name, value -> value));
	}

	/**
	 * OGNL {@code value != 0}: null is not 0 (OGNL's equal: null equals only null); a number is compared as a number; a
	 * text is read as a double (trimmed, an empty text is 0), and one that is not a number (e.g. a list "1,2") fails,
	 * as OGNL failed.
	 */
	static boolean ognlNotZero(Object value) {
		if (value == null) {
			return true;
		}
		if (value instanceof Number number) {
			return new BigDecimal(number.toString()).signum() != 0;
		}
		if (value instanceof Boolean flag) {
			return flag;                                        // OGNL: true is 1, false is 0
		}
		if (value instanceof Character character) {
			return character != 0;                              // OGNL: a character is its code
		}
		String text = value.toString().trim();
		if (text.isEmpty()) {
			return false;
		}
		try {
			return Double.parseDouble(text) != 0;
		} catch (NumberFormatException e) {
			throw new InvalidDataAccessApiUsageException("Error evaluating expression 'approvalStatus != 0': " + e, e);
		}
	}

	/** The database's SYSDATE (for the columns the former inserts set to SYSDATE). */
	private Timestamp sysdate() {
		return jpql.one("select sysdate", Rows.scalar(Timestamp.class));
	}

	/** {@code to_date(text, 'MM/DD/YYYY')} by the database (null for null). */
	private Timestamp toDate(String text) {
		if (text == null) {
			return null;
		}
		return jpql.one("select " + toDate(1), Rows.scalar(Timestamp.class), text);
	}
}
