package gov.ny.state.doh.bhae.nyehms.web.spring.dao.lab;

import static gov.ny.state.doh.bhae.nyehms.web.spring.dao.lab.LaftRows.id;
import static gov.ny.state.doh.bhae.nyehms.web.spring.dao.lab.LaftRows.now;
import static gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements.cursor;
import static gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements.in;
import static gov.ny.state.doh.bhae.nyehms.web.spring.jpa.Values.text;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.lab.CLEPLabResultAccessionVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.lab.CLEPLabResultVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.lab.CLEPUploadSurveyVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.entity.lab.LaftClepAuditAccessionData;
import gov.ny.state.doh.bhae.nyehms.web.spring.entity.lab.LaftClepAuditSurveyForm;
import gov.ny.state.doh.bhae.nyehms.web.spring.entity.lab.LaftClepAuditSurveyUpload;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.JpqlStatements;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.Rows;
import gov.ny.state.doh.bhae.nyehms.web.spring.repository.lab.LaftClepAuditSurveyFormRepository;
import gov.ny.state.doh.bhae.nyehms.web.spring.repository.lab.LaftClepAuditSurveyUploadRepository;
import lombok.RequiredArgsConstructor;

/**
 * The CLEP audit survey forms, their accession numbers and the uploaded CLEP HIV surveys (formerly a MyBatis mapper with
 * the same name and methods; its JSON reads had already moved to ClepAuditResultsService), through Spring Data
 * repositories and JPQL over the entities of {@code entity.lab}; the entities are turned into the screens' VOs here.
 * <p>
 * The entered dates keep the former database conversions (FN_TO_DATE on write, TO_CHAR 'MM/DD/YYYY' on read, see the
 * entities), as do the upper-cased columns. Updates are JPQL bulk updates of the
 * same columns and rows as before (they return the number of rows changed). The dates the former SQL set with SYSDATE are the application server's clock.
 * The survey audit report stays a stored procedure call ({@code pr_survey_audit_report}, its columns decided by the
 * PL/SQL).
 */
@Repository
@RequiredArgsConstructor
public class ClepAuditResultsDAO {

	private static final String SURVEY_DATA = """
			select ls.surveyNumber as surveyNumber, ls.pfi as userEnteredPfi, ls.surveyDate as labDateOfSurvey, ls.surveyor as labSurveyorName,
			ls.receiveDate as faxReceivedDate, ls.instBasedReportableHiv as insBasedHivTest, ls.labUsesEclrs as rptNysDohByEclrs,
			ls.labUsesRefLab as labRefLabForTest, ls.referencePfi as labRefPFI, lpf.clia as pfiLabClia, lpf.labName as pfiLabName,
			lpf.address as pfiLabAddress, lpf.city as pfiLabCity, lpf.state as pfiLabState, lpf.zip as pfiLabZip, lpf.country as pfiLabCountry,
			lpf.laftStaff as pfiLabLaftStaff, cla.approvalStatusDesc as pfiLabApprovalStatus,
			reflab.labName as refLabName, reflab.city as refLabCity, reflab.state as refLabState,
			ls.reviewCompleted as reviewCompleted, ls.reviewCompletedBy as reviewCompletedBy, format(ls.reviewCompletedDate as 'MM/dd/yyyy') as reviewCompletedDate
			from LaftClepAuditSurveyForm ls
			join LabPhysicalFacility lpf on ls.pfi = lpf.pfi
			left join LabPhysicalFacility reflab on ls.referencePfi = reflab.pfi
			left join CodedLabApprovalStatus cla on lpf.approvalStatusCode = cla.approvalStatusCode
			where ls.surveyNumber = ?1""";

	private static final String SURVEY_ACCESSIONS = """
			select a.surveyNumber as surveyNumber, a.serialNo as accessionSerialNo, a.accessionNumber as accessionNo, a.collectionDate as collectionDate,
			a.testTypeCd4 as cd4, a.testTypeRes as genotypicResistance, a.testTypeHivImm as hivImmunoassay, a.testTypeSuppImm as supImmunoassay,
			a.testTypeHivNat as hivNAT, a.testTypeCul as cluture, a.testTypeOth as other, a.reportedInNyehms as resultRptNyehms,
			a.resolved as resolved, a.dateResolved as dateResolved, a.comments as comments, a.laftStaff as laftStaff
			from LaftClepAuditAccessionData a
			where a.surveyNumber = ?1""";

	private static final String SURVEY_UPLOAD = """
			select u.pfiUploadPk as uploadNumber, u.surveyNumber as surveyNumber, u.clepFileName as clepFilename,
			format(u.fileUploadDate as 'MM/dd/yyyy') as clepFilUploadDate, u.fileUploadBy as clepFilUploadBy, u.pfi as labPfi, u.clia as labClia,
			u.labName as labName, u.surveyBeginDate as surveyBeginDate, u.surveyEndDate as surveyEndDate, u.comments as comments,
			u.assignedStaffId as assignedStaffId, u.assignedDate as assignedDate, u.assignedBy as assignedBy, u.surveyCloseDate as surveyCloseDate,
			u.surveyCloseBy as surveyCloseBy
			from LaftClepAuditSurveyUpload u
			where u.pfiUploadPk = ?1""";

	private static final String RECRUITMENT_ASSESSMENT = """
			select ls.surveyNumber as surveyNumber, ls.pfi as userEnteredPfi, lpf.clia as pfiLabClia, lpf.labName as pfiLabName,
			cla.approvalStatusDesc as pfiLabApprovalStatus, ls.instBasedReportableHiv as insBasedHivTest, ls.labUsesEclrs as rptNysDohByEclrs,
			ls.recruitAssessStaffAssign as recruitAssessStaffAssign, ls.recruitAssessStaffAssignBy as recruitAssessStaffAssignBy,
			ls.recruitAssessDateAssign as recruitAssessDateAssign, ls.recruitAssessOutcome as recruitAssessOutcome,
			ls.recruitAssessComments as recruitAssessComments, ls.recruitAssessDateComplete as recruitAssessDateComplete,
			ls.recruitAssessStaffComplete as recruitAssessStaffComplete, ls.reviewCompleted as reviewCompleted,
			ls.reviewCompletedBy as reviewCompletedBy, format(ls.reviewCompletedDate as 'MM/dd/yyyy') as reviewCompletedDate
			from LaftClepAuditSurveyForm ls
			join LabPhysicalFacility lpf on ls.pfi = lpf.pfi
			left join CodedLabApprovalStatus cla on lpf.approvalStatusCode = cla.approvalStatusCode
			where ls.surveyNumber = ?1""";

	private static final String UPDATE_ACCESSION = """
			update LaftClepAuditAccessionData a set a.accessionNumber = ?1, a.collectionDate = function('FN_TO_DATE' as String, ?2, 'MM/DD/YYYY'), a.testTypeHivImm = ?3, a.testTypeHivNat = ?4,
			a.testTypeCd4 = ?5, a.testTypeCul = ?6, a.testTypeSuppImm = ?7, a.testTypeRes = ?8, a.testTypeOth = ?9, a.reportedInNyehms = ?10,
			a.resolved = ?11, a.dateResolved = function('FN_TO_DATE' as String, ?12, 'MM/DD/YYYY'), a.comments = ?13, a.laftStaff = ?14
			where a.surveyNumber = ?15 and a.serialNo = ?16""";

	private final JpqlStatements jpql;
	private final NativeStatements statements;
	private final LaftClepAuditSurveyFormRepository surveyForms;
	private final LaftClepAuditSurveyUploadRepository surveyUploads;

	/**
	 * Inserts a CLEP audit survey form (created now) and sets its generated SURVEY_NUMBER on the VO, as the former
	 * generated-key read did.
	 */
	@Transactional
	public int saveLaftClepAuditSurveyData(CLEPLabResultVO clepLabResultVO) throws DataAccessException {
		LaftClepAuditSurveyForm form = surveyForms.saveAndFlush(LaftClepAuditSurveyForm.builder()
				.pfi(clepLabResultVO.getUserEnteredPfi())
				.surveyDate(clepLabResultVO.getLabDateOfSurvey())
				.surveyor(clepLabResultVO.getLabSurveyorName())
				.receiveDate(clepLabResultVO.getFaxReceivedDate())
				.instBasedReportableHiv(clepLabResultVO.getInsBasedHivTest())
				.labUsesEclrs(clepLabResultVO.getRptNysDohByEclrs())
				.labUsesRefLab(clepLabResultVO.getLabRefLabForTest())
				.referencePfi(clepLabResultVO.getLabRefPFI())
				.createUid(clepLabResultVO.getClepUserId())
				.createDate(now())
				.build());
		if (form.getSurveyNumber() != null) {
			clepLabResultVO.setSurveyNumber(form.getSurveyNumber().intValue());
		}
		return 1;
	}

	/**
	 * Inserts the accession numbers of a survey.
	 *
	 * @param clepLabAccessionDataMap surveyNumber, accessionNoList (CLEPLabResultAccessionVOs; not empty: the former
	 *        statement failed then too)
	 * @return the number of rows inserted
	 */
	@Transactional
	public int saveLaftClepAccessionData(Map<String, Object> clepLabAccessionDataMap) throws DataAccessException {
		Long surveyNumber = id(clepLabAccessionDataMap.get("surveyNumber"));
		List<CLEPLabResultAccessionVO> rows = LaftRows.inList(clepLabAccessionDataMap.get("accessionNoList"), "accessionNoList",
				value -> (CLEPLabResultAccessionVO) value);
		for (CLEPLabResultAccessionVO row : rows) {
			// persisted, not saved: save would merge (update) an existing row instead of failing
			jpql.insert(accession(surveyNumber, (long) row.getAccessionSerialNo(), row));
		}
		return rows.size();
	}

	/** A survey form with its lab, reference lab and approval status, or null. */
	public CLEPLabResultVO getClepSurveyData(int surveyNo) {
		return jpql.one(SURVEY_DATA, Rows.bean(CLEPLabResultVO.class), (long) surveyNo);
	}

	/** The accession numbers of a survey. */
	public List<CLEPLabResultAccessionVO> getClepSurveyAccession(int surveyNo) {
		return jpql.list(SURVEY_ACCESSIONS, Rows.bean(CLEPLabResultAccessionVO.class), (long) surveyNo);
	}

	/**
	 * Changes an accession number row of a survey.
	 *
	 * @param clepLabAccessionDataMap surveyNumber, accessionSerialNo, accessionVO (the new values)
	 */
	public int updateLaftClepAccessionData(Map<String, Object> clepLabAccessionDataMap) throws DataAccessException {
		CLEPLabResultAccessionVO vo = (CLEPLabResultAccessionVO) clepLabAccessionDataMap.get("accessionVO");
		return jpql.update(UPDATE_ACCESSION, vo.getAccessionNo(), vo.getCollectionDate(), vo.isHivImmunoassay(), vo.isHivNAT(), vo.isCd4(), vo.isCluture(),
				vo.isSupImmunoassay(), vo.isGenotypicResistance(), vo.isOther(), vo.getResultRptNyehms(), vo.getResolved(), vo.getDateResolved(),
				vo.getComments(), vo.getLaftStaff(), id(clepLabAccessionDataMap.get("surveyNumber")), id(clepLabAccessionDataMap.get("accessionSerialNo")));
	}

	/**
	 * Marks a survey as reviewed ('Yes', now).
	 *
	 * @param map surveyNo, surveyReviewedBy
	 */
	public int updateSurveyReviewComplete(Map<String, Object> map) throws DataAccessException {
		return jpql.update("""
				update LaftClepAuditSurveyForm f set f.reviewCompleted = 'Yes', f.reviewCompletedBy = ?1, f.reviewCompletedDate = ?2
				where f.surveyNumber = ?3""", text(map.get("surveyReviewedBy")), now(), id(map.get("surveyNo")));
	}

	/**
	 * Inserts the rows of an uploaded CLEP HIV survey file (uploaded now).
	 *
	 * @param inputParameterMap clepHIVSurveyDataList (maps with PFI, CLIA, LAB_NAME, SURVEY_BEGIN_DATE, SURVEY_END_DATE,
	 *        fileName, userId; not empty: the former statement failed then too)
	 * @return the number of rows inserted
	 */
	@Transactional
	public int insertCLEPHIVSurveyData(Map<String, Object> inputParameterMap) throws DataAccessException {
		List<Map<?, ?>> rows = LaftRows.inList(inputParameterMap.get("clepHIVSurveyDataList"), "clepHIVSurveyDataList", value -> (Map<?, ?>) value);
		for (Map<?, ?> row : rows) {
			surveyUploads.saveAndFlush(LaftClepAuditSurveyUpload.builder()
					.pfi(text(row.get("PFI")))
					.clia(text(row.get("CLIA")))
					.labName(text(row.get("LAB_NAME")))
					.surveyBeginDate(text(row.get("SURVEY_BEGIN_DATE")))
					.surveyEndDate(text(row.get("SURVEY_END_DATE")))
					.clepFileName(text(row.get("fileName")))
					.fileUploadBy(text(row.get("userId")))
					.fileUploadDate(now())
					.build());
		}
		return rows.size();
	}

	/** An uploaded survey row, or null. */
	public CLEPUploadSurveyVO getClepSurveyUploadData(int uploadNo) {
		return jpql.one(SURVEY_UPLOAD, Rows.bean(CLEPUploadSurveyVO.class), (long) uploadNo);
	}

	/**
	 * Changes the follow-up of an uploaded survey row.
	 *
	 * @param map uploadNumber, assignedStaffId, assignedDate, assignedBy, surveyCloseDate, surveyCloseBy, comments
	 */
	public int updateFollowupHIVSurveyData(Map<String, Object> map) throws DataAccessException {
		return jpql.update("""
				update LaftClepAuditSurveyUpload u set u.assignedStaffId = ?1, u.assignedDate = function('FN_TO_DATE' as String, ?2, 'MM/DD/YYYY'), u.assignedBy = ?3, u.surveyCloseDate = function('FN_TO_DATE' as String, ?4, 'MM/DD/YYYY'),
				u.surveyCloseBy = ?5, u.comments = ?6
				where u.pfiUploadPk = ?7""", text(map.get("assignedStaffId")), text(map.get("assignedDate")), text(map.get("assignedBy")),
				text(map.get("surveyCloseDate")), text(map.get("surveyCloseBy")), text(map.get("comments")), id(map.get("uploadNumber")));
	}

	/** A survey form with its lab recruitment assessment, or null. */
	public CLEPLabResultVO getLabRecruitmentAssessmentData(int surveyNo) {
		return jpql.one(RECRUITMENT_ASSESSMENT, Rows.bean(CLEPLabResultVO.class), (long) surveyNo);
	}

	/**
	 * Changes the lab recruitment assessment of a survey form.
	 *
	 * @param map surveyNumber, recruitAssessStaffAssign, recruitAssessDateAssign, recruitAssessStaffAssignBy,
	 *        recruitAssessDateComplete, recruitAssessStaffComplete, recruitAssessOutcome, recruitAssessComments
	 */
	public int updateRecruitmentAssessmentFollowupData(Map<String, Object> map) throws DataAccessException {
		return jpql.update("""
				update LaftClepAuditSurveyForm f set f.recruitAssessStaffAssign = ?1, f.recruitAssessDateAssign = function('FN_TO_DATE' as String, ?2, 'MM/DD/YYYY'), f.recruitAssessStaffAssignBy = ?3,
				f.recruitAssessDateComplete = function('FN_TO_DATE' as String, ?4, 'MM/DD/YYYY'), f.recruitAssessStaffComplete = ?5, f.recruitAssessOutcome = ?6, f.recruitAssessComments = ?7
				where f.surveyNumber = ?8""", text(map.get("recruitAssessStaffAssign")), text(map.get("recruitAssessDateAssign")),
				text(map.get("recruitAssessStaffAssignBy")), text(map.get("recruitAssessDateComplete")), text(map.get("recruitAssessStaffComplete")),
				text(map.get("recruitAssessOutcome")), text(map.get("recruitAssessComments")), id(map.get("surveyNumber")));
	}

	/**
	 * The survey audit report: calls {@code pr_survey_audit_report} and puts its rows (JSONObjects) into the map as
	 * {@code p_survey_audit_data}, as the MyBatis CALLABLE statement did.
	 *
	 * @param map p_from_date, p_to_date (text)
	 * @return an empty list (the procedure returns its rows through the cursor)
	 */
	public List<JSONObject> getSurveyAuditReportData(Map<Object, Object> map) {
		List<Object> results = statements.call("pr_survey_audit_report",
				in(map.get("p_from_date"), String.class),
				in(map.get("p_to_date"), String.class),
				cursor(Rows.json()));
		map.put("p_survey_audit_data", results.get(2));
		return new ArrayList<>();
	}

	/** Changes the survey answers of a survey form (last updated now by clepUserId). */
	public int updateLaftClepAuditSurveyData(CLEPLabResultVO clepLabResultVO) throws DataAccessException {
		return jpql.update("""
				update LaftClepAuditSurveyForm f set f.instBasedReportableHiv = ?1, f.labUsesEclrs = ?2, f.labUsesRefLab = ?3, f.referencePfi = upper(?4),
				f.lastUpdateBy = upper(?5), f.lastUpdateDate = ?6
				where f.surveyNumber = ?7""", clepLabResultVO.getInsBasedHivTest(), clepLabResultVO.getRptNysDohByEclrs(),
				clepLabResultVO.getLabRefLabForTest(), clepLabResultVO.getLabRefPFI(), clepLabResultVO.getClepUserId(), now(),
				(long) clepLabResultVO.getSurveyNumber());
	}

	/** An accession row with the values of the form's VO (the test types as the form's booleans). */
	private static LaftClepAuditAccessionData accession(Long surveyNumber, Long serialNo, CLEPLabResultAccessionVO vo) {
		return LaftClepAuditAccessionData.builder()
				.surveyNumber(surveyNumber)
				.serialNo(serialNo)
				.accessionNumber(vo.getAccessionNo())
				.collectionDate(vo.getCollectionDate())
				.testTypeHivImm(vo.isHivImmunoassay())
				.testTypeHivNat(vo.isHivNAT())
				.testTypeCd4(vo.isCd4())
				.testTypeCul(vo.isCluture())
				.testTypeSuppImm(vo.isSupImmunoassay())
				.testTypeRes(vo.isGenotypicResistance())
				.testTypeOth(vo.isOther())
				.reportedInNyehms(vo.getResultRptNyehms())
				.resolved(vo.getResolved())
				.dateResolved(vo.getDateResolved())
				.comments(vo.getComments())
				.laftStaff(vo.getLaftStaff())
				.build();
	}
}
