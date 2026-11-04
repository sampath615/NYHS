package gov.ny.state.doh.bhae.nyehms.web.spring.service.lab;

import static gov.ny.state.doh.bhae.nyehms.web.spring.repository.common.JsonRows.number;
import static gov.ny.state.doh.bhae.nyehms.web.spring.repository.common.JsonRows.text;

import java.util.List;

import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import gov.ny.state.doh.bhae.nyehms.web.spring.repository.common.JsonRows;
import gov.ny.state.doh.bhae.nyehms.web.spring.repository.common.JsonRows.Column;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;
import lombok.RequiredArgsConstructor;

/**
 * The viral load reports (formerly ViralLoadReportsDAO): Oracle report queries (CTEs, ROW_NUMBER, date arithmetic)
 * run as JPA native queries with bound values, their rows returned as JSONObjects as before ({@link JsonRows}).
 */
@RequiredArgsConstructor
@Service
public class ViralLoadReportsService {

	/** Open eligible RIA records whose latest viral load is "not detectable". */
	private static final String NDVL_REPORT = """
			WITH open_elig_ria AS
			(
			    SELECT *
			      FROM nyehms_ria_doc
			     WHERE complete_date IS NULL
			       AND Nvl(ria_eligible, 'N') != 'N' and MERGED_RIA_STATENO IS NULL and nvl(STATENO_DELETED, 'N') != 'Y'
			), vl_labs AS
			(
			SELECT eligRia.document_id RIA_DOCUMENT_ID, 'VL'  LAB_DOCUMENT_TYPE, p.stateno , eligRia.lab_doc_id DOCUMENT_ID , s.interpret_cd, c.interpret_cd_group, s.calc_collection_date, s.FNAME, s.LNAME, s.DOB_TEXT
			        , ROW_NUMBER() OVER (PARTITION BY p.stateno ORDER BY s.calc_collection_date DESC) vlrn
			  FROM person_doc_association p, std_hiv_eclrs_doc s, coded_interpret_code_group c, open_elig_ria eligRia
			WHERE
			    eligRia.curr_nyehms_stateno = p.stateno
			   AND p.document_id = s.document_id
			   AND p.dissassociation_date IS NULL
			   AND s.interpret_cd = c.interpret_cd
			   AND c.test_type_cd = 'VL'
			)
			SELECT STATENO "stateno", RIA_DOCUMENT_ID, LAB_DOCUMENT_TYPE "documentType", TO_CHAR(DOCUMENT_ID) "documentId", INTERPRET_CD, INTERPRET_CD_GROUP, TO_CHAR(CALC_COLLECTION_DATE, 'MM/DD/YYYY') COLLECTION_DATE, FNAME, LNAME, DOB_TEXT
			  FROM vl_labs
			WHERE vlrn = 1
			   AND interpret_cd_group = 'NDVL'
			""";

	/** Open eligible RIA records without a quantitative viral load after the HIV diagnosis; the days filter follows. */
	private static final String NO_ADDITIONAL_REPORT = """
			WITH labs AS
			(
			SELECT Distinct p.stateno, fn_to_date(s.calc_collection_date, 'MM/DD/YYYY') calc_collection_date
			  FROM person_doc_association p, std_hiv_eclrs_doc s
			WHERE p.document_id = s.document_id
			   AND s.interpret_cd in ('QUAN_VL','DET_QUAN_VL','ND_QUAN_VL')
			), final_labs AS
			(
			SELECT Distinct a.curr_nyehms_stateno stateno, r.calc_ehars_hiv_dx_dt, r.RIA_FLAGGED_DATE
			       , sysdate - fn_to_date(r.calc_ehars_hiv_dx_dt, 'MM/DD/YYYY') days
			  FROM nyehms_ria_doc a, all_ria_vw r
			WHERE a.complete_date IS NULL
			   AND Nvl(a.ria_eligible, 'X') != 'N'
			   AND r.curr_nyehms_stateno = a.curr_nyehms_stateno
			   AND r.calc_ehars_hiv_dx_dt IS NOT NULL
			   AND NOT EXISTS (SELECT 1
			                     FROM labs l
			                    WHERE r.curr_nyehms_stateno = l.stateno
			                      AND l.calc_collection_date > fn_to_date(r.calc_ehars_hiv_dx_dt, 'MM/DD/YYYY'))
			)
			SELECT stateno, calc_ehars_hiv_dx_dt, round(days) days, TO_CHAR(RIA_FLAGGED_DATE, 'MM/DD/YYYY') RIA_FLAGGED_DATE
			  FROM final_labs
			""";

	/** The "<" results of the last 30 days of a person, with the lab's note. */
	private static final String VL_OBX_NOTES = """
			SELECT r.document_id "documentId", TO_CHAR(r.mshcreateDate, 'MM/DD/YYYY') "receivedDate", to_char(r.obxnote) "obxNote"
			  FROM std_hiv_eclrs_doc a
			  JOIN person_doc_association b ON b.document_id = a.document_id AND b.dissassociation_date IS NULL
			  JOIN raw_hiv_eclrs_doc r ON r.document_id = a.document_id
			WHERE Trim(a.test_result) LIKE '<%'
			   AND Trim(a.test_result) != '<0'
			   AND Trunc(a.calc_collection_date) > Trunc(sysdate) - 30
			   AND (a.prov_county not in ('005','047','061','081','085','999','998') OR a.facility_county not in ('005','047','061','081','085','999','998'))
			   AND b.stateno = ?1
			""";

	private static final List<Column> NDVL_REPORT_COLUMNS = List.of(text("stateno"), number("RIA_DOCUMENT_ID"), text("documentType"),
			text("documentId"), text("INTERPRET_CD"), text("INTERPRET_CD_GROUP"), text("COLLECTION_DATE"), text("FNAME"), text("LNAME"),
			text("DOB_TEXT"));

	private static final List<Column> NO_ADDITIONAL_REPORT_COLUMNS = List.of(text("STATENO"), text("CALC_EHARS_HIV_DX_DT"), number("DAYS"),
			text("RIA_FLAGGED_DATE"));

	private static final List<Column> VL_OBX_NOTES_COLUMNS = List.of(number("documentId"), text("receivedDate"), text("obxNote"));

	private final JsonRows jsonRows;

	public List<JSONObject> loadNdvlReportData(BindingResult dummyBindingResult, UserObject userObject) {
		return jsonRows.list(NDVL_REPORT, NDVL_REPORT_COLUMNS);
	}

	/**
	 * The records without an additional viral load.
	 *
	 * @param dateRange days since the diagnosis: "x" (more than x days) or "x-y" (from x to y days)
	 */
	public List<JSONObject> getNoAdditionalReportData(String dateRange, BindingResult dummyBindingResult,
			UserObject userObject, Object object) {
		String[] dateRanges = dateRange.split("-");
		if (dateRanges.length > 1) {
			return jsonRows.list(NO_ADDITIONAL_REPORT + " where  days BETWEEN ?1 AND ?2", NO_ADDITIONAL_REPORT_COLUMNS, dateRanges[0],
					dateRanges[1]);
		}
		return jsonRows.list(NO_ADDITIONAL_REPORT + " where days > ?1", NO_ADDITIONAL_REPORT_COLUMNS, dateRange);
	}

	public List<JSONObject> getVLObxNotes(String stateno, BindingResult result, UserObject userObject) {
		return jsonRows.list(VL_OBX_NOTES, VL_OBX_NOTES_COLUMNS, stateno);
	}
}
