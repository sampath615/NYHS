package gov.ny.state.doh.bhae.nyehms.web.spring.dao.lab;

import static gov.ny.state.doh.bhae.nyehms.web.spring.jpa.Values.longValue;
import static gov.ny.state.doh.bhae.nyehms.web.spring.jpa.Values.text;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.json.simple.JSONObject;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.lab.AcceptedTestResultDomainVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.lab.LabLoincMappingVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.entity.common.AccptdTstResultDomain;
import gov.ny.state.doh.bhae.nyehms.web.spring.entity.common.CodedEharsStdResults;
import gov.ny.state.doh.bhae.nyehms.web.spring.entity.common.CodedLabTestType;
import gov.ny.state.doh.bhae.nyehms.web.spring.entity.common.LabLoinc;
import gov.ny.state.doh.bhae.nyehms.web.spring.entity.common.LoincAccptdTstResultDomain;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.JpqlStatements;
import gov.ny.state.doh.bhae.nyehms.web.spring.record.LoincAtrDomainRow;
import gov.ny.state.doh.bhae.nyehms.web.spring.record.LoincTestTypeRow;
import gov.ny.state.doh.bhae.nyehms.web.spring.repository.common.AccptdTstResultDomainRepository;
import gov.ny.state.doh.bhae.nyehms.web.spring.repository.common.CodedEharsStdResultsRepository;
import gov.ny.state.doh.bhae.nyehms.web.spring.repository.common.CodedLabTestTypeRepository;
import gov.ny.state.doh.bhae.nyehms.web.spring.repository.common.LabLoincRepository;
import gov.ny.state.doh.bhae.nyehms.web.spring.repository.common.LoincAccptdTstResultDomainRepository;
import gov.ny.state.doh.bhae.nyehms.web.spring.repository.common.RawHivEclrsDocRepository;
import lombok.RequiredArgsConstructor;

/**
 * LOINC codes, their acceptable test results and the eHARS standard results they map to (formerly a MyBatis mapper
 * with the same name and methods), through Spring Data repositories over the entities of {@code entity.common}; the
 * entities are turned into the screens' VOs and JSON rows here.
 * <p>
 * The optional filters follow the former {@code <if>} / {@code <choose>} conditions (OGNL: a value is "empty" when it
 * is null or ""). A null value compared with {@code =} matched nothing in the former SQL, so such a call returns an
 * empty result / 0 here without a query (Spring Data would turn it into {@code is null}). The dates the former SQL set
 * with SYSDATE are the application server's clock.
 */
@Repository
@RequiredArgsConstructor
public class LoincResultDomainMappingDAO {

	private final JpqlStatements statements;
	private final LabLoincRepository labLoincs;
	private final CodedLabTestTypeRepository testTypes;
	private final AccptdTstResultDomainRepository testResults;
	private final LoincAccptdTstResultDomainRepository loincTestResults;
	private final CodedEharsStdResultsRepository eharsResults;
	private final RawHivEclrsDocRepository eclrsDocuments;

	/**
	 * The LOINC codes with their test type details, filtered by the given values.
	 * <p>
	 * As before: loincCode / loincDescription (LIKE patterns) and testType filter when not empty; a unitOfResult other
	 * than "select" and "" must match; an empty unitOfResult ("" or null) means "no unit". A null unitOfResult added
	 * both conditions ({@code = NULL} and {@code is null}), so it finds nothing (kept as it was).
	 *
	 * @param values loincCode, loincDescription, testType, unitOfResult
	 */
	public List<LabLoincMappingVO> getLoincAndDescriptionDetails(Map<Object, Object> values) {
		Object unitOfResult = values.get("unitOfResult");
		if (unitOfResult == null) {
			return new ArrayList<>();                                   // UNIT_OF_RESULT = NULL and UNIT_OF_RESULT is null
		}
		String unit = !"select".equals(unitOfResult) && !"".equals(unitOfResult) ? text(unitOfResult) : null;
		List<LoincTestTypeRow> rows = labLoincs.findWithTestType(emptyAsNull(values.get("loincCode")), emptyAsNull(values.get("loincDescription")),
				emptyAsNull(values.get("testType")), unit, "".equals(unitOfResult));
		return rows.stream().map(LoincResultDomainMappingDAO::toLoincMapping).collect(toList());
	}

	/** The active acceptable test results of a LOINC code, with their eHARS standard result. */
	public List<AcceptedTestResultDomainVO> getAtrDomainsAssoicatedToLoincCode(String loincCode) {
		return loincTestResults.findActiveOfLoinc(loincCode).stream().map(LoincResultDomainMappingDAO::toAcceptedTestResult).collect(toList());
	}

	/**
	 * Adds a LOINC code to the lookup (an existing code fails, as before).
	 *
	 * @param values loincCode, testTypeCode
	 */
	public int addNewLabLoincDetailsToLookup(Map<Object, Object> values) {
		// persisted, not saved: save would merge (update) an existing code instead of failing
		return statements.insert(LabLoinc.builder()
				.loincCd(text(values.get("loincCode")))
				.testTypeCd(text(values.get("testTypeCode")))
				.build());
	}

	/** The number of LAB_LOINC rows of a LOINC code. */
	public int getExistingLoincRecordsCount(String loincCode) {
		return loincCode == null ? 0 : (int) labLoincs.countByLoincCd(loincCode);
	}

	/**
	 * The acceptable test results whose VALUE_TYPE|STRING_VALUE|RANGE_LOWER_BOUND|RANGE_UPPER_BOUND equals the given
	 * text (all matches, as the former list select returned them).
	 */
	public List<Integer> checkForCustomTestData(String inputData) {
		return testResults.findKeysByCombinedValues(inputData).stream().map(key -> key == null ? null : key.intValue()).collect(toList());
	}

	/**
	 * Adds an acceptable test result (active, last updated now) and puts its generated ATR_DOMAIN_CD into the map as
	 * "autoGeneratedId" (a BigDecimal, as the former generated-key read returned the NUMBER column).
	 *
	 * @param values typeOfResult, acceptedValue, rangeLowerBound, rangeUpperBound, lastUpdatedBy
	 */
	@Transactional
	public int addNewAcceptableTestResults(Map<Object, Object> values) {
		AccptdTstResultDomain domain = testResults.saveAndFlush(AccptdTstResultDomain.builder()
				.valueType(text(values.get("typeOfResult")))
				.stringValue(text(values.get("acceptedValue")))
				.rangeLowerBound(text(values.get("rangeLowerBound")))
				.rangeUpperBound(text(values.get("rangeUpperBound")))
				.flagActive("Y")
				.lastUpdatedBy(longValue(values.get("lastUpdatedBy")))
				.lastUpdatedDate(now())
				.build());
		values.put("autoGeneratedId", domain.getAtrDomainCd() == null ? null : BigDecimal.valueOf(domain.getAtrDomainCd()));
		return 1;
	}

	/**
	 * The number of active associations of a LOINC code with any of the given acceptable test results.
	 *
	 * @param values loincCode, testResultsAttributeIds (a collection of ATR_DOMAIN_CD values; it must not be empty or
	 *        null: the former statement failed then too)
	 */
	public int checkLoincCodeAssociatedTestResults(Map<Object, Object> values) {
		List<Long> ids = inList(values.get("testResultsAttributeIds"), "testResultsAttributeIds");
		String loincCode = text(values.get("loincCode"));
		return loincCode == null ? 0 : (int) loincTestResults.countByFlagActiveAndLoincCdAndAtrDomainCdIn("Y", loincCode, ids);
	}

	/**
	 * Associates an acceptable test result with a LOINC code (active, last updated now).
	 *
	 * @param values attributeDomainId, loincCode, lastUpdatedBy, standardResults (the RESULTS_STD_CD)
	 */
	@Transactional
	public int assoicateTestResultsToLoincCode(Map<Object, Object> values) {
		loincTestResults.saveAndFlush(LoincAccptdTstResultDomain.builder()
				.atrDomainCd(longValue(values.get("attributeDomainId")))
				.loincCd(text(values.get("loincCode")))
				.flagActive("Y")
				.lastUpdatedDate(now())
				.lastUpdatedBy(longValue(values.get("lastUpdatedBy")))
				.resultsStdCd(text(values.get("standardResults")))
				.build());
		return 1;
	}

	/**
	 * Activates or deactivates an association (last updated now).
	 *
	 * @param values flagActive, p_lastUpdatedBy, loincAtrDomainMapId
	 * @return 1, or 0 if there is no such association
	 */
	@Transactional
	public int updateLoincAndTestResultsDomainStatus(Map<Object, Object> values) {
		Long id = longValue(values.get("loincAtrDomainMapId"));
		Optional<LoincAccptdTstResultDomain> association = id == null ? Optional.empty() : loincTestResults.findById(id);
		association.ifPresent(a -> a.changeStatus(text(values.get("flagActive")), longValue(values.get("p_lastUpdatedBy")), now()));
		return association.isPresent() ? 1 : 0;
	}

	/** The eHARS standard results: keys STANDARDRESULTSCODE and STANDARDRESULTSDESC, ordered by the description. */
	@SuppressWarnings("unchecked")
	public List<JSONObject> getEharsTestResults() {
		return eharsResults.findAllByDescription().stream().map(result -> {
			JSONObject row = new JSONObject();
			putIfSet(row, "STANDARDRESULTSCODE", result.getResultsStdCd());
			putIfSet(row, "STANDARDRESULTSDESC", concat(result.getResultsStd(), result.getResultInterpretation()));
			return row.isEmpty() ? null : row;
		}).collect(toList());
	}

	/** All test types, sorted. */
	public List<String> getCompleteLoincTestTypes() {
		return testTypes.findTestTypes();
	}

	/** The descriptions of a test type, sorted. */
	public List<String> getLoincDescriptionsForTestType(String testType) {
		return testTypes.findDescriptions(testType);
	}

	/**
	 * The units of the results (a null unit as a null element; NVL(UNIT_OF_RESULT, '') was the unit itself on Oracle),
	 * of one description and test type when loincDescription is not empty, else of all test types.
	 *
	 * @param values loincDescription, testType
	 */
	public List<String> getLoincUnitOfMeasurementsForTestType(Map<Object, Object> values) {
		Object loincDescription = values.get("loincDescription");
		return empty(loincDescription) ? testTypes.findUnits() : testTypes.findUnits(text(loincDescription), text(values.get("testType")));
	}

	/** The test type codes of a description, test type and unit (an empty unit: no unit). */
	public List<String> checkForReferenceLoincCodeMapping(LabLoincMappingVO labLoincMappingVO) {
		String description = labLoincMappingVO.getLoincDescription();
		String testType = labLoincMappingVO.getTestType();
		String unitOfResult = labLoincMappingVO.getUnitOfResult();
		if (description == null || testType == null) {
			return new ArrayList<>();                                   // DESCRIPTION = NULL / TEST_TYPE = NULL
		}
		List<CodedLabTestType> matches = empty(unitOfResult) ? testTypes.findByDescriptionAndTestTypeAndUnitOfResultIsNull(description, testType)
				: testTypes.findByDescriptionAndTestTypeAndUnitOfResult(description, testType, unitOfResult);
		return matches.stream().map(CodedLabTestType::getTestTypeCd).collect(toList());
	}

	/**
	 * The number of the given documents that are not Mayo lab (PFI 3263) LOINC 81641-3 documents (NOT IN with SQL NULL
	 * rules, as before: a document whose PFI or LOINC code is null is not counted unless the other one differs).
	 *
	 * @param values documentIds (it must not be empty or null: the former statement failed then too)
	 */
	public int verifyForMayoLabDocuments(Map<String, Integer[]> values) {
		List<Long> ids = inList(values.get("documentIds"), "documentIds");
		return (int) eclrsDocuments.countByMshlabpfiNotAndDocumentIdInOrObxloinccodeNotAndDocumentIdIn("3263", ids, "81641-3", ids);
	}

	private static LabLoincMappingVO toLoincMapping(LoincTestTypeRow row) {
		LabLoincMappingVO vo = new LabLoincMappingVO();
		boolean set = false;
		if (row.loinc().getLoincCd() != null) { vo.setLoincCode(row.loinc().getLoincCd()); set = true; }
		if (row.testType().getDescription() != null) { vo.setLoincDescription(row.testType().getDescription()); set = true; }
		if (row.testType().getTestType() != null) { vo.setTestType(row.testType().getTestType()); set = true; }
		if (row.testType().getUnitOfResult() != null) { vo.setUnitOfResult(row.testType().getUnitOfResult()); set = true; }
		return set ? vo : null;
	}

	private static AcceptedTestResultDomainVO toAcceptedTestResult(LoincAtrDomainRow row) {
		LoincAccptdTstResultDomain association = row.association();
		AccptdTstResultDomain domain = row.domain();
		CodedEharsStdResults standardResult = row.standardResult();
		AcceptedTestResultDomainVO vo = new AcceptedTestResultDomainVO();
		boolean set = false;
		if (association.getLatrdId() != null) { vo.setLoincAtrDomainMapId(association.getLatrdId().toString()); set = true; }
		if (domain.getAtrDomainCd() != null) { vo.setDomainCode(domain.getAtrDomainCd().toString()); set = true; }
		if (domain.getValueType() != null) { vo.setTypeOfResult(domain.getValueType()); set = true; }
		if (domain.getStringValue() != null) { vo.setAcceptedValue(domain.getStringValue()); set = true; }
		if (domain.getRangeLowerBound() != null) { vo.setRangeLowerBound(domain.getRangeLowerBound()); set = true; }
		if (domain.getRangeUpperBound() != null) { vo.setRangeUpperBound(domain.getRangeUpperBound()); set = true; }
		if (association.getFlagActive() != null) { vo.setFlagActive(association.getFlagActive()); set = true; }
		if (domain.getLastUpdatedBy() != null) { vo.setLastUpdatedBy(domain.getLastUpdatedBy().toString()); set = true; }
		if (domain.getLastUpdatedDateText() != null) { vo.setLastUpdatedDate(domain.getLastUpdatedDateText()); set = true; }
		// trim(to_char(RESULTS_STD || '' || RESULT_INTERPRETATION))
		String standardResults = trimSpaces(concat(standardResult.getResultsStd(), standardResult.getResultInterpretation()));
		if (standardResults != null) { vo.setStandardResults(standardResults); set = true; }
		return set ? vo : null;
	}

	/** Oracle's a || b: NULL adds nothing, NULL if both are NULL ('' is NULL on Oracle). */
	private static String concat(String a, String b) {
		String text = (a == null ? "" : a) + (b == null ? "" : b);
		return text.isEmpty() ? null : text;
	}

	/** Oracle's TRIM: leading and trailing spaces removed (only spaces), an empty result is NULL. */
	private static String trimSpaces(String text) {
		if (text == null) {
			return null;
		}
		int from = 0, to = text.length();
		while (from < to && text.charAt(from) == ' ') {
			from++;
		}
		while (to > from && text.charAt(to - 1) == ' ') {
			to--;
		}
		return from == to ? null : text.substring(from, to);
	}

	/** OGNL {@code x == null or x == ''}. */
	private static boolean empty(Object value) {
		return value == null || "".equals(value);
	}

	/** A filter value as text, null when empty (the former {@code <if>} left the condition out). */
	private static String emptyAsNull(Object value) {
		return empty(value) ? null : text(value);
	}

	/**
	 * The values of a former {@code <foreach>} IN list, as whole numbers. An empty or missing collection failed before
	 * (MyBatis wrote "in" without a list, or rejected the null collection), so it fails here too.
	 */
	private static List<Long> inList(Object collection, String name) {
		Collection<?> items;
		if (collection instanceof Collection<?> c) {
			items = c;
		} else if (collection instanceof Object[] array) {
			items = Arrays.asList(array);
		} else {
			throw new InvalidDataAccessApiUsageException("The IN list '" + name + "' is " + (collection == null ? "null" : "not a collection"));
		}
		if (items.isEmpty()) {
			throw new InvalidDataAccessApiUsageException("The IN list '" + name + "' is empty");
		}
		List<Long> ids = new ArrayList<>(items.size());
		for (Object item : items) {
			ids.add(longValue(item));
		}
		return ids;
	}

	@SuppressWarnings("unchecked")
	private static void putIfSet(JSONObject row, String key, Object value) {
		if (value != null) {
			row.put(key, value);
		}
	}

	private static Timestamp now() {
		return new Timestamp(System.currentTimeMillis());
	}

	private static <T> java.util.stream.Collector<T, ?, List<T>> toList() {
		return Collectors.toCollection(ArrayList::new);
	}
}
