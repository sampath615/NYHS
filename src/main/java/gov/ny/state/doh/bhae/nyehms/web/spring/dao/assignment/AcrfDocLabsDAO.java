package gov.ny.state.doh.bhae.nyehms.web.spring.dao.assignment;

import static gov.ny.state.doh.bhae.nyehms.web.spring.dao.assignment.CrfColumns.BOOLEAN;
import static gov.ny.state.doh.bhae.nyehms.web.spring.dao.assignment.CrfColumns.FLOAT;
import static gov.ny.state.doh.bhae.nyehms.web.spring.dao.assignment.CrfColumns.INTEGER;
import static gov.ny.state.doh.bhae.nyehms.web.spring.dao.assignment.CrfColumns.STRING;
import static gov.ny.state.doh.bhae.nyehms.web.spring.dao.assignment.CrfColumns.byClass;
import static gov.ny.state.doh.bhae.nyehms.web.spring.dao.assignment.CrfColumns.typed;
import static gov.ny.state.doh.bhae.nyehms.web.spring.dao.assignment.CrfColumns.upper;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.CodedAcrfTestResultVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.CodedAcrfTestVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.CodedParentTestTypeVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.acrf.AcrfRidrVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.acrf.CodedArvMedicationsVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.acrf.RawAcrfCd4TstVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.acrf.RawAcrfLabsVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.entity.assignment.RawAcrfCd4;
import gov.ny.state.doh.bhae.nyehms.web.spring.entity.assignment.RawAcrfLabs;
import gov.ny.state.doh.bhae.nyehms.web.spring.entity.assignment.RawCrfCd4;
import gov.ny.state.doh.bhae.nyehms.web.spring.entity.assignment.RawCrfLabs;
import gov.ny.state.doh.bhae.nyehms.web.spring.entity.assignment.RawPcrfCd4;
import gov.ny.state.doh.bhae.nyehms.web.spring.entity.assignment.RawPcrfLabs;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.JpqlStatements;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.Rows;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.Values;
import lombok.RequiredArgsConstructor;

/**
 * The lab, CD4, ARV and RIDR rows of the case report forms, their code lists, and the form ids of the surveillance
 * assignment (formerly a MyBatis mapper with the same name and methods).
 * <p>
 * The table and column names the callers used to paste into the SQL ({@code ${tableName}}, {@code ${testCodeColumn}},
 * {@code ${resultCodeColumn}}, {@code ${CRF_COLUMN_ID}}, {@code ${crfDocColumnName}}) are checked against the names the
 * callers pass (RAW_ACRF_ / RAW_PCRF_ LABS, CD4, ARVS; ACRF_ / PCRF_ TEST_CODE, RESULT_CODE; ACRF_ / PCRF_ DOC_ID;
 * ACRF_ / PCRF_ / REAB_CRF_ DOCUMENT_ID): another name is refused. The values are bound.
 * <p>
 * Labs and CD4 tests: JPQL over the entities RawAcrfLabs / RawPcrfLabs and RawAcrfCd4 / RawPcrfCd4 (inserted as
 * entities; LAB_SRC_ID / CD4_SRC_ID come from the database). The code lists: JPQL over their entities.
 * <p>
 * <b>Native SQL</b> (the same SQL as before, values bound): the ARV rows (RAW_ACRF_ARVS / RAW_PCRF_ARVS) and the RIDR
 * rows (RAW_ACRF_RIDR_INFO). These tables have no known key: the screens read each row's Oracle ROWID and send it
 * back to change or delete the row, and JPQL cannot select or address a ROWID. Without a key there is no entity, so
 * their inserts are native too.
 * <p>
 * <b>Kept as before:</b> getCodedAcrfTestResultTypes matches ACRF_REVISION LIKE '%revision%' (a null revision matches
 * every revision); getCodedParentTestTypes returns every row (also ended ones); the lab list is joined to
 * CODED_ACRF_TEST by parent test type and code (a lab without a matching test type is not listed).
 */
@Repository
@RequiredArgsConstructor
public class AcrfDocLabsDAO {

	private final NativeStatements nativeStatements;
	private final JpqlStatements jpql;

	/** The lab tables and their entities, test code and result code columns. */
	private static final Map<String, String[]> LAB_TABLES = Map.of(
			"RAW_ACRF_LABS", new String[] {"RawAcrfLabs", "ACRF_TEST_CODE", "ACRF_RESULT_CODE"},
			"RAW_PCRF_LABS", new String[] {"RawPcrfLabs", "PCRF_TEST_CODE", "PCRF_RESULT_CODE"});

	/** The CD4 tables and their entities. */
	private static final Map<String, String> CD4_TABLES = Map.of("RAW_ACRF_CD4", "RawAcrfCd4", "RAW_PCRF_CD4", "RawPcrfCd4");

	/** The ARV tables and their form id columns. */
	private static final Map<String, String> ARV_TABLES = Map.of("RAW_ACRF_ARVS", "ACRF_DOC_ID", "RAW_PCRF_ARVS", "PCRF_DOC_ID");

	/** The form id columns of SURVEILLANCE_ASSIGNMENT and their attributes. */
	private static final Map<String, String> CRF_DOC_COLUMNS = Map.of("ACRF_DOCUMENT_ID", "acrfDocumentId",
			"PCRF_DOCUMENT_ID", "pcrfDocumentId", "REAB_CRF_DOCUMENT_ID", "reabCrfDocumentId");

	/** The value an allow-list has for a name (a null or unknown name is refused). */
	private static <T> T allowed(Map<String, T> names, Object name, String what) {
		T value = name == null ? null : names.get(name.toString());
		if (value == null) {
			throw new IllegalArgumentException("Not a known " + what + ": " + name);
		}
		return value;
	}

	/** The lab table's entity (tableName), checked with its test and result code columns. */
	private static String labEntity(Map<String, Object> map) {
		String[] table = allowed(LAB_TABLES, map.get("tableName"), "lab table");
		if (!table[1].equals(map.get("testCodeColumn")) || !table[2].equals(map.get("resultCodeColumn"))) {
			throw new IllegalArgumentException("Not the code columns of " + map.get("tableName") + ": " + map.get("testCodeColumn") + ", "
					+ map.get("resultCodeColumn"));
		}
		return table[0];
	}

	/** The test types in use (END_DATE null or after now), by SORT_ORDER. */
	public List<CodedAcrfTestVO> getCodedAcrfResultTypes() throws DataAccessException {
		return jpql.list("select t.acrfTestCode as acrtTestCode, t.parentTestTypeCode as parentTestTypeCode, t.acrfTestDesc as acrtTestDesc,"
				+ " t.testSection as testSection from CodedAcrfTest t where t.endDate is null or t.endDate > sysdate order by t.sortOrder",
				Rows.bean(CodedAcrfTestVO.class));
	}

	/** All parent test types (the former SELECT *), in no particular order. */
	public List<CodedParentTestTypeVO> getCodedParentTestTypes() throws DataAccessException {
		return jpql.list("select p.parentTestTypeCode as parentTestTypeCode, p.parentTestTypeDesc as parentTestTypeDesc, p.createDate as createDate,"
				+ " p.createId as createId, p.endDate as endDate, p.endId as endId from CodedParentTestType p", Rows.bean(CodedParentTestTypeVO.class));
	}

	/**
	 * The lab tests of a form, by LAB_SRC_ID, with the test section of their test type.
	 *
	 * @param inputParameterMap tableName, testCodeColumn, resultCodeColumn (allow-listed), documentId
	 */
	public List<RawAcrfLabsVO> getRawACRFLabs(Map<String, Object> inputParameterMap) throws DataAccessException {
		return jpql.list("select lab.labSrcId as labSrcId, lab.documentId as documentId, lab.rapidTest as rapidTest, lab.copiesMl as copiesMl,"
				+ " lab.log as log, lab.collectionDate as collectionDate, lab.testCode as acrfTestCode, lab.parentTestType as parentTestType,"
				+ " lab.resultCode as acrfResultCode, lab.otherTest as otherTest, lab.manufacturer as manufacturer, lab.facilityName as facilityName,"
				+ " lab.facilityEharsUid as facilityEharsUid, lab.labName as labName, lab.clia as clia, lab.provName as providerName,"
				+ " lab.provFname as providerFname, lab.provLname as providerLname, lab.provMname as providerMname, lab.provSuffix as providerSuffix,"
				+ " lab.indexValue as indexValue, lab.hiv1CmlAnalyteResult as hiv1CmlAnalyteResult, lab.hiv1LogAnalyteResult as hiv1LogAnalyteResult,"
				+ " lab.hiv12AbAnalyteResult as hiv12AbAnalyteResult, lab.hiv1AbAnalyteResult as hiv1AbAnalyteResult,"
				+ " lab.hiv1AbAnalyteResultIndex as hiv1AbAnalyteResultIndex, lab.hiv2AbAnalyteResult as hiv2AbAnalyteResult,"
				+ " lab.hiv2AbAnalyteResultIndex as hiv2AbAnalyteResultIndex, lab.hiv1AgAnalyteResult as hiv1AgAnalyteResult,"
				+ " lab.hiv1AgAnalyteResultIndex as hiv1AgAnalyteResultIndex, lab.diagnosticAlgorithmStep as dignosticAlgorithm,"
				+ " lab.rapidTestType as rapidTestType, lab.interpretation as interpretation, lab.analyteInterpretation as analyteInterpretation,"
				+ " t.testSection as testSection"
				+ " from " + labEntity(inputParameterMap) + " lab join CodedAcrfTest t"
				+ " on lab.parentTestType = t.parentTestTypeCode and lab.testCode = t.acrfTestCode"
				+ " where lab.documentId = ?1 order by lab.labSrcId", Rows.bean(RawAcrfLabsVO.class), Values.longValue(inputParameterMap.get("documentId")));
	}

	/**
	 * Inserts a lab test; its LAB_SRC_ID (from the database) is put into the map as labSrcId, as MyBatis put the
	 * generated key: with the type of a labSrcId already in the map, else as a BigDecimal (as Oracle returned it).
	 *
	 * @param inputParameterMap tableName, testCodeColumn, resultCodeColumn (allow-listed), rawAcrfLabs, workerId
	 */
	@Transactional
	public void addRawACRFLabs(Map<String, Object> inputParameterMap) throws DataAccessException {
		RawAcrfLabsVO vo = (RawAcrfLabsVO) inputParameterMap.get("rawAcrfLabs");
		RawCrfLabs.RawCrfLabsBuilder<?, ?> lab = "RawAcrfLabs".equals(labEntity(inputParameterMap)) ? RawAcrfLabs.builder() : RawPcrfLabs.builder();
		RawCrfLabs entity = lab.documentId((long) vo.getDocumentId())
				.parentTestType(vo.getParentTestType())
				.testCode(vo.getAcrfTestCode())
				.resultCode(vo.getAcrfResultCode())
				.collectionDate(vo.getCollectionDate())
				.rapidTest(vo.isRapidTest())
				.copiesMl(vo.getCopiesMl())
				.log(vo.getLog())
				.otherTest(upper(vo.getOtherTest()))
				.manufacturer(vo.getManufacturer())
				.facilityName(vo.getFacilityName())
				.facilityEharsUid(vo.getFacilityEharsUid())
				.labName(vo.getLabName())
				.clia(vo.getClia())
				.provName(vo.getProviderName())
				.provFname(vo.getProviderFname())
				.provLname(vo.getProviderLname())
				.provMname(vo.getProviderMname())
				.provSuffix(vo.getProviderSuffix())
				.indexValue(vo.getIndexValue())
				.hiv1CmlAnalyteResult(vo.getHiv1CmlAnalyteResult())
				.hiv1LogAnalyteResult(vo.getHiv1LogAnalyteResult())
				.hiv12AbAnalyteResult(vo.getHiv12AbAnalyteResult())
				.hiv1AbAnalyteResult(vo.getHiv1AbAnalyteResult())
				.hiv1AbAnalyteResultIndex(vo.getHiv1AbAnalyteResultIndex())
				.hiv2AbAnalyteResult(vo.getHiv2AbAnalyteResult())
				.hiv2AbAnalyteResultIndex(vo.getHiv2AbAnalyteResultIndex())
				.hiv1AgAnalyteResult(vo.getHiv1AgAnalyteResult())
				.hiv1AgAnalyteResultIndex(vo.getHiv1AgAnalyteResultIndex())
				.diagnosticAlgorithmStep(vo.getDignosticAlgorithm())
				.rapidTestType(vo.getRapidTestType())
				.interpretation(vo.getInterpretation())
				.analyteInterpretation(vo.getAnalyteInterpretation())
				.updatedBy(Values.text(inputParameterMap.get("workerId")))
				.build();
		jpql.insert(entity);
		Object previous = inputParameterMap.get("labSrcId");
		Long key = entity.getLabSrcId();
		inputParameterMap.put("labSrcId", previous instanceof Integer ? (Object) key.intValue()
				: previous instanceof Long ? (Object) key : new BigDecimal(key));
	}

	/**
	 * The result codes in use (END_DATE null) of a form revision (ACRF_REVISION null or containing acrfRevison), by
	 * SORT_ORDER; with whereCondition 'yes' only those of a parent test type and test code.
	 *
	 * @param map acrfRevison, whereCondition, parentTestType, acrfTestCode
	 */
	public List<CodedAcrfTestResultVO> getCodedAcrfTestResultTypes(Map<Object, Object> map) throws DataAccessException {
		String query = "select r.acrfResultCode as acrfResultCode, r.acrfResultDesc as acrfResultDesc, r.parentTestType as parentTestType,"
				+ " r.acrfTestCode as acrfTestCode, r.acrfRevision as acrfRevison from CodedAcrfTestResult r"
				+ " where r.endDate is null and (r.acrfRevision is null or r.acrfRevision like concat('%', ?1, '%'))";
		Object revision = typed(STRING, map.get("acrfRevison") == null ? null : Values.text(map.get("acrfRevison")));
		if ("yes".equals(map.get("whereCondition"))) {
			return jpql.list(query + " and r.parentTestType = ?2 and r.acrfTestCode = ?3 order by r.sortOrder", Rows.bean(CodedAcrfTestResultVO.class),
					revision, byClass(map.get("parentTestType"), STRING), byClass(map.get("acrfTestCode"), STRING));
		}
		return jpql.list(query + " order by r.sortOrder", Rows.bean(CodedAcrfTestResultVO.class), revision);
	}

	/**
	 * Inserts a CD4 test.
	 *
	 * @param inputParameterMap tableName (allow-listed), rawAcrfCd4Tst, workerId
	 * @return 1
	 */
	@Transactional
	public int addRawAcrfCd4Data(Map<String, Object> inputParameterMap) throws DataAccessException {
		RawAcrfCd4TstVO vo = (RawAcrfCd4TstVO) inputParameterMap.get("rawAcrfCd4Tst");
		RawCrfCd4.RawCrfCd4Builder<?, ?> cd4 = "RawAcrfCd4".equals(allowed(CD4_TABLES, inputParameterMap.get("tableName"), "CD4 table"))
				? RawAcrfCd4.builder() : RawPcrfCd4.builder();
		return jpql.insert(cd4.documentId((long) vo.getDocumentId())
				.cd4Count(vo.getCd4Count())
				.cd4Percent(vo.getCd4Percent())
				.collectionDate(vo.getCollectionDate())
				.closestToCurrentDx(vo.getClosestToCurrentDx())
				.firstLowCd4(vo.isFirstLowCd4())
				.facilityName(vo.getFacilityName())
				.facilityEharsUid(vo.getFacilityEharsUid())
				.clia(vo.getClia())
				.labName(vo.getLabName())
				.provName(vo.getProviderName())
				.provFname(vo.getProviderFname())
				.provLname(vo.getProviderLname())
				.provMname(vo.getProviderMname())
				.provSuffix(vo.getProviderSuffix())
				.manufacturer(vo.getManufacturer())
				.updatedBy(Values.text(inputParameterMap.get("workerId")))
				.build());
	}

	/**
	 * The CD4 tests of a form, by CLOSEST_TO_CURRENT_DX.
	 *
	 * @param inputParameterMap tableName (allow-listed), documentId
	 */
	public List<RawAcrfCd4TstVO> getRawAcrfCd4Data(Map<String, Object> inputParameterMap) throws DataAccessException {
		return jpql.list("select c.cd4SrcId as cd4SrcId, c.documentId as documentId, c.cd4Count as cd4Count, c.cd4Percent as cd4Percent,"
				+ " c.closestToCurrentDx as closestToCurrentDx, c.firstLowCd4 as firstLowCd4, c.collectionDate as collectionDate,"
				+ " c.facilityName as facilityName, c.facilityEharsUid as facilityEharsUid, c.clia as clia, c.labName as labName,"
				+ " c.provName as providerName, c.provFname as providerFname, c.provLname as providerLname, c.provMname as providerMname,"
				+ " c.provSuffix as providerSuffix, c.manufacturer as manufacturer"
				+ " from " + allowed(CD4_TABLES, inputParameterMap.get("tableName"), "CD4 table") + " c where c.documentId = ?1"
				+ " order by c.closestToCurrentDx asc", Rows.bean(RawAcrfCd4TstVO.class), Values.longValue(inputParameterMap.get("documentId")));
	}

	/**
	 * Changes a lab test (DOCUMENT_ID and LAB_SRC_ID of rawAcrfLabs); UPDATED_DATE is now.
	 *
	 * @param inputParameterMap tableName, testCodeColumn, resultCodeColumn (allow-listed), rawAcrfLabs, workerId
	 */
	@Transactional
	public void updateRawACRFLabs(Map<String, Object> inputParameterMap) throws DataAccessException {
		RawAcrfLabsVO vo = (RawAcrfLabsVO) inputParameterMap.get("rawAcrfLabs");
		jpql.update("update " + labEntity(inputParameterMap) + " lab set lab.parentTestType = ?1, lab.testCode = ?2, lab.resultCode = ?3,"
				+ " lab.collectionDate = ?4, lab.rapidTest = ?5, lab.copiesMl = ?6, lab.log = ?7, lab.otherTest = ?8, lab.manufacturer = ?9,"
				+ " lab.facilityName = ?10, lab.facilityEharsUid = ?11, lab.labName = ?12, lab.clia = ?13, lab.provName = ?14, lab.provFname = ?15,"
				+ " lab.provLname = ?16, lab.provMname = ?17, lab.provSuffix = ?18, lab.indexValue = ?19, lab.hiv1CmlAnalyteResult = ?20,"
				+ " lab.hiv1LogAnalyteResult = ?21, lab.hiv12AbAnalyteResult = ?22, lab.hiv1AbAnalyteResult = ?23, lab.hiv1AbAnalyteResultIndex = ?24,"
				+ " lab.hiv2AbAnalyteResult = ?25, lab.hiv2AbAnalyteResultIndex = ?26, lab.hiv1AgAnalyteResult = ?27, lab.hiv1AgAnalyteResultIndex = ?28,"
				+ " lab.diagnosticAlgorithmStep = ?29, lab.rapidTestType = ?30, lab.interpretation = ?31, lab.analyteInterpretation = ?32,"
				+ " lab.updatedBy = ?33, lab.updatedDate = sysdate where lab.documentId = ?34 and lab.labSrcId = ?35",
				typed(STRING, vo.getParentTestType()), typed(STRING, vo.getAcrfTestCode()), typed(STRING, vo.getAcrfResultCode()),
				typed(STRING, vo.getCollectionDate()), typed(BOOLEAN, vo.isRapidTest()), typed(STRING, vo.getCopiesMl()), typed(STRING, vo.getLog()),
				typed(STRING, upper(vo.getOtherTest())), typed(STRING, vo.getManufacturer()), typed(STRING, vo.getFacilityName()),
				typed(STRING, vo.getFacilityEharsUid()), typed(STRING, vo.getLabName()), typed(STRING, vo.getClia()), typed(STRING, vo.getProviderName()),
				typed(STRING, vo.getProviderFname()), typed(STRING, vo.getProviderLname()), typed(STRING, vo.getProviderMname()),
				typed(STRING, vo.getProviderSuffix()), typed(STRING, vo.getIndexValue()), typed(STRING, vo.getHiv1CmlAnalyteResult()),
				typed(STRING, vo.getHiv1LogAnalyteResult()), typed(STRING, vo.getHiv12AbAnalyteResult()), typed(STRING, vo.getHiv1AbAnalyteResult()),
				typed(STRING, vo.getHiv1AbAnalyteResultIndex()), typed(STRING, vo.getHiv2AbAnalyteResult()), typed(STRING, vo.getHiv2AbAnalyteResultIndex()),
				typed(STRING, vo.getHiv1AgAnalyteResult()), typed(STRING, vo.getHiv1AgAnalyteResultIndex()), typed(STRING, vo.getDignosticAlgorithm()),
				typed(STRING, vo.getRapidTestType()), typed(STRING, vo.getInterpretation()), typed(STRING, vo.getAnalyteInterpretation()),
				byClass(inputParameterMap.get("workerId"), STRING), typed(INTEGER, vo.getDocumentId()), typed(INTEGER, vo.getLabSrcId()));
	}

	/**
	 * Changes a CD4 test (DOCUMENT_ID and CD4_SRC_ID of rawAcrfCd4Tst); UPDATED_DATE is now.
	 *
	 * @param inputParameterMap tableName (allow-listed), rawAcrfCd4Tst, workerId
	 */
	@Transactional
	public void updateRawAcrfCd4Data(Map<String, Object> inputParameterMap) throws DataAccessException {
		RawAcrfCd4TstVO vo = (RawAcrfCd4TstVO) inputParameterMap.get("rawAcrfCd4Tst");
		jpql.update("update " + allowed(CD4_TABLES, inputParameterMap.get("tableName"), "CD4 table") + " c set c.cd4Count = ?1, c.cd4Percent = ?2,"
				+ " c.collectionDate = ?3, c.closestToCurrentDx = ?4, c.firstLowCd4 = ?5, c.facilityName = ?6, c.facilityEharsUid = ?7, c.clia = ?8,"
				+ " c.labName = ?9, c.provName = ?10, c.provFname = ?11, c.provLname = ?12, c.provMname = ?13, c.provSuffix = ?14, c.manufacturer = ?15,"
				+ " c.updatedBy = ?16, c.updatedDate = sysdate where c.documentId = ?17 and c.cd4SrcId = ?18",
				typed(INTEGER, vo.getCd4Count()), typed(FLOAT, vo.getCd4Percent()), typed(STRING, vo.getCollectionDate()),
				typed(STRING, vo.getClosestToCurrentDx()), typed(BOOLEAN, vo.isFirstLowCd4()), typed(STRING, vo.getFacilityName()),
				typed(STRING, vo.getFacilityEharsUid()), typed(STRING, vo.getClia()), typed(STRING, vo.getLabName()), typed(STRING, vo.getProviderName()),
				typed(STRING, vo.getProviderFname()), typed(STRING, vo.getProviderLname()), typed(STRING, vo.getProviderMname()),
				typed(STRING, vo.getProviderSuffix()), typed(STRING, vo.getManufacturer()), byClass(inputParameterMap.get("workerId"), STRING),
				typed(INTEGER, vo.getDocumentId()), typed(INTEGER, vo.getCd4SrcId()));
	}

	/**
	 * Sets the form id column (crfDocColumnName, allow-listed) of an assignment to crfDocumentId, with LAST_UPDATED_BY
	 * (lastUpdatedBy) and LAST_UPDATED_DATE (now).
	 *
	 * @return the number of rows changed
	 */
	@Transactional
	public int updateSurveillanceAssignmentWithAcrfDocId(Map<Object, Object> map) throws DataAccessException {
		return jpql.update("update SurveillanceAssignment s set s." + allowed(CRF_DOC_COLUMNS, map.get("crfDocColumnName"), "form id column")
				+ " = ?1, s.lastUpdatedBy = ?2, s.lastUpdatedDate = sysdate where s.documentId = ?3",
				byClass(map.get("crfDocumentId"), INTEGER), byClass(map.get("lastUpdatedBy"), STRING), documentId(map.get("documentId")));
	}

	/**
	 * Sets the form id column (crfDocColumnName, allow-listed) of an assignment to crfDocumentId.
	 *
	 * @return the number of rows changed
	 */
	@Transactional
	public int updateSurveillanceAssignmentWithReabAcrfDocId(Map<Object, Object> map) throws DataAccessException {
		return jpql.update("update SurveillanceAssignment s set s." + allowed(CRF_DOC_COLUMNS, map.get("crfDocColumnName"), "form id column")
				+ " = ?1 where s.documentId = ?2", byClass(map.get("crfDocumentId"), INTEGER), documentId(map.get("documentId")));
	}

	/** A DOCUMENT_ID the caller passed as text or number (Oracle compared the number column with it as a number). */
	private static Object documentId(Object value) {
		return typed(org.hibernate.type.StandardBasicTypes.LONG, Values.longValue(value));
	}

	/** The eHARS facility ids of the standardized facilities (SUB_FACILITY_ID > 0), each once, in no particular order. */
	public String[] getEharsStandardFacilityDetails() {
		return jpql.list("select distinct s.eharsFacUid from SubFacility s where s.subFacilityId > 0 and s.eharsFacUid is not null",
				Rows.scalar(String.class)).toArray(new String[0]);
	}

	/** The document sources (DOCUMENT_SOURCE_CODE, DOCUMENT_SOURCE_DESCRIPTION), by description. */
	public List<Map<String, String>> getDocumentSourceList() {
		return textMaps(jpql.list("select d.documentSourceCode as DOCUMENT_SOURCE_CODE, d.documentSourceDescription as DOCUMENT_SOURCE_DESCRIPTION"
				+ " from CodedDocumentSource d order by d.documentSourceDescription", Rows.map()));
	}

	/** The other id types (OTHER_ID_TYPE, OTHER_ID_TYPE_DESC), by SORT_ORDER. */
	public List<Map<String, String>> getAcrfOtherIdTypeList() {
		return textMaps(jpql.list("select o.otherIdType as OTHER_ID_TYPE, o.otherIdTypeDesc as OTHER_ID_TYPE_DESC from CodedAcrfOtherIdType o"
				+ " order by o.sortOrder", Rows.map()));
	}

	/** The codes of a code list (LOOKUP_CD, LOOKUP_NAME), by name. */
	public List<Map<String, String>> getACRFReferenceData(String referenceColumn) {
		return textMaps(jpql.list("select r.lookupCd as LOOKUP_CD, r.lookupName as LOOKUP_NAME from CodedAcrfRefData r where r.refColumn = ?1"
				+ " order by r.lookupName", Rows.map(), typed(STRING, referenceColumn)));
	}

	/** The active laboratories other than CLIA -9999 (CLIA, LAB_NAME), by name. */
	public List<Map<String, String>> getClinicalLaboratoryDetails() {
		return textMaps(jpql.list("select l.clia as CLIA, l.labName as LAB_NAME from LabPhysicalFacility l where l.flagActive = 'Y' and l.clia <> '-9999'"
				+ " order by l.labName", Rows.map()));
	}

	/** Maps of text values (the code columns are text), as the former HashMaps of the rows (a row of nulls null). */
	@SuppressWarnings({"unchecked", "rawtypes"})
	private static List<Map<String, String>> textMaps(List<Map<String, Object>> rows) {
		return (List) rows;
	}

	/**
	 * Deletes a CD4 test (CD4_SRC_ID of rawAcrfCd4Tst).
	 *
	 * @param inputParameterMap tableName (allow-listed), rawAcrfCd4Tst
	 */
	@Transactional
	public void deleteRawAcrfCd4Data(Map<String, Object> inputParameterMap) {
		RawAcrfCd4TstVO vo = (RawAcrfCd4TstVO) inputParameterMap.get("rawAcrfCd4Tst");
		jpql.update("delete from " + allowed(CD4_TABLES, inputParameterMap.get("tableName"), "CD4 table") + " c where c.cd4SrcId = ?1",
				typed(INTEGER, vo.getCd4SrcId()));
	}

	/**
	 * Deletes a lab test (LAB_SRC_ID labSrcId).
	 *
	 * @param inputParameterMap tableName (allow-listed; the code columns are not used), labSrcId
	 */
	@Transactional
	public void deleteRawACRFLabs(Map<String, Object> inputParameterMap) {
		String entity = allowed(LAB_TABLES, inputParameterMap.get("tableName"), "lab table")[0];
		jpql.update("delete from " + entity + " lab where lab.labSrcId = ?1", byClass(inputParameterMap.get("labSrcId"), INTEGER));
	}

	// ARV and RIDR rows: native SQL, see the class comment (rows addressed by ROWID)

	/**
	 * Inserts an ARV row.
	 *
	 * @param inputParameterMap tableName, CRF_COLUMN_ID (allow-listed), codedArvMedicationsVO
	 * @return 1
	 */
	@Transactional
	public int addRawArvMedication(Map<String, Object> inputParameterMap) {
		CodedArvMedicationsVO vo = (CodedArvMedicationsVO) inputParameterMap.get("codedArvMedicationsVO");
		String table = arvTable(inputParameterMap);
		return nativeStatements.update("INSERT INTO " + table + " ( " + ARV_TABLES.get(table)
				+ ", ARV_USE_TYPE, ARV_MEDICATION, ARV_BEGIN_DATE, ARV_END_DATE, ARV_OTHER_USE_TYPE) VALUES (?1 , ?2 , ?3 , ?4 , ?5 , ?6 )",
				vo.getDocumentId(), NativeStatements.text(vo.getArvUseType()), NativeStatements.text(vo.getArvMedications()),
				NativeStatements.text(vo.getArvMedicationsDateBegan()), NativeStatements.text(vo.getArvMedicationsDateOfLastUse()),
				NativeStatements.text(vo.getOtherArvUseType()));
	}

	/**
	 * The ARV rows of a form, with their ROWID (arvSrcId).
	 *
	 * @param inputParameterMap tableName, CRF_COLUMN_ID (allow-listed), documentId
	 */
	public List<CodedArvMedicationsVO> getRawArvMedicationsData(Map<String, Object> inputParameterMap) {
		String table = arvTable(inputParameterMap);
		return nativeStatements.list("SELECT rowid arvSrcId, ARV_USE_TYPE arvUseType, ARV_MEDICATION arvMedications, ARV_BEGIN_DATE arvMedicationsDateBegan,"
				+ " ARV_END_DATE arvMedicationsDateOfLastUse, ARV_OTHER_USE_TYPE otherArvUseType FROM " + table + " WHERE " + ARV_TABLES.get(table) + "=?1",
				Rows.bean(CodedArvMedicationsVO.class), inputParameterMap.get("documentId"));
	}

	/**
	 * Deletes an ARV row by its ROWID (arvSrcId).
	 *
	 * @param inputParameterMap tableName (allow-listed), arvSrcId
	 * @return the number of rows deleted
	 */
	@Transactional
	public int deleteRawArvMedication(Map<String, Object> inputParameterMap) {
		return nativeStatements.update("delete from " + allowedArvTable(inputParameterMap.get("tableName")) + " where rowid = ?1",
				inputParameterMap.get("arvSrcId"));
	}

	/**
	 * Changes an ARV row by its ROWID (arvSrcId of codedArvMedicationsVO).
	 *
	 * @param inputParameterMap tableName, CRF_COLUMN_ID (allow-listed), codedArvMedicationsVO
	 */
	@Transactional
	public void updateRawArvMedication(Map<String, Object> inputParameterMap) {
		CodedArvMedicationsVO vo = (CodedArvMedicationsVO) inputParameterMap.get("codedArvMedicationsVO");
		String table = arvTable(inputParameterMap);
		nativeStatements.update("update " + table + " set " + ARV_TABLES.get(table) + " = ?1, ARV_USE_TYPE = ?2, ARV_MEDICATION = ?3 ,"
				+ " ARV_BEGIN_DATE = ?4 , ARV_END_DATE = ?5, ARV_OTHER_USE_TYPE = ?6 where rowid = ?7",
				vo.getDocumentId(), NativeStatements.text(vo.getArvUseType()), NativeStatements.text(vo.getArvMedications()),
				NativeStatements.text(vo.getArvMedicationsDateBegan()), NativeStatements.text(vo.getArvMedicationsDateOfLastUse()),
				NativeStatements.text(vo.getOtherArvUseType()), vo.getArvSrcId());
	}

	/** The ARV table (tableName), checked with its form id column (CRF_COLUMN_ID). */
	private static String arvTable(Map<String, Object> map) {
		String table = allowedArvTable(map.get("tableName"));
		if (!ARV_TABLES.get(table).equals(map.get("CRF_COLUMN_ID"))) {
			throw new IllegalArgumentException("Not the form id column of " + table + ": " + map.get("CRF_COLUMN_ID"));
		}
		return table;
	}

	private static String allowedArvTable(Object name) {
		allowed(ARV_TABLES, name, "ARV table");
		return name.toString();
	}

	/** The RIDR rows of a form (ACRF_DOC_ID), with their ROWID (RIDR_ROWID). */
	public List<AcrfRidrVO> getRawAcrfRidrData(String rawAcrfDocId) {
		return nativeStatements.list("SELECT rowid RIDR_ROWID, DUPLICATE_STATUS, STATE_CD , STATENO, COMMENTS FROM RAW_ACRF_RIDR_INFO WHERE ACRF_DOC_ID=?1",
				Rows.bean(AcrfRidrVO.class), rawAcrfDocId);
	}

	/**
	 * Inserts a RIDR row (ACRF_DOC_ID documentId).
	 *
	 * @return 1
	 */
	@Transactional
	public int addRawAcrfRidrInfo(AcrfRidrVO acrfRidrVO) {
		return nativeStatements.update("INSERT INTO RAW_ACRF_RIDR_INFO ( ACRF_DOC_ID, DUPLICATE_STATUS, STATE_CD, STATENO, COMMENTS) VALUES (?1 , ?2 , ?3 , ?4 , ?5 )",
				acrfRidrVO.getDocumentId(), NativeStatements.text(acrfRidrVO.getDUPLICATE_STATUS()), NativeStatements.text(acrfRidrVO.getSTATE_CD()),
				NativeStatements.text(acrfRidrVO.getSTATENO()), NativeStatements.text(acrfRidrVO.getCOMMENTS()));
	}

	/**
	 * Deletes a RIDR row by its ROWID.
	 *
	 * @return the number of rows deleted
	 */
	@Transactional
	public int deleteRawAcrfRidrData(String ridr_ROWID) {
		return nativeStatements.update("delete from RAW_ACRF_RIDR_INFO where rowid = ?1", ridr_ROWID);
	}

	/** Changes a RIDR row by its ROWID (RIDR_ROWID). */
	@Transactional
	public void updateRawAcrfRidrData(AcrfRidrVO acrfRidrVO) {
		nativeStatements.update("update RAW_ACRF_RIDR_INFO set ACRF_DOC_ID = ?1, DUPLICATE_STATUS = ?2, STATE_CD = ?3 , STATENO = ?4 , COMMENTS = ?5"
				+ " where rowid = ?6", acrfRidrVO.getDocumentId(), NativeStatements.text(acrfRidrVO.getDUPLICATE_STATUS()),
				NativeStatements.text(acrfRidrVO.getSTATE_CD()), NativeStatements.text(acrfRidrVO.getSTATENO()),
				NativeStatements.text(acrfRidrVO.getCOMMENTS()), acrfRidrVO.getRIDR_ROWID());
	}
}
