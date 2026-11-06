package gov.ny.state.doh.bhae.nyehms.web.spring.dao.lab;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Function;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.lab.ClinicalLaboratoryVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.lab.FeedbackReportOverallSummaryVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.lab.FeedbackReportStateSummaryVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.lab.LabFacilitiesVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.lab.LabInstrumentDetailsVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.lab.LabLocalCodesMappingVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.lab.LabLoincMappingVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.lab.LabPermitsVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.lab.LabTestMappingVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements.RowMapper;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.Row;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.Rows;

/**
 * The former MyBatis result maps of LabReportsDAO, applied to the rows of its native statements and of its procedures'
 * cursors with the MyBatis rules (default settings):
 * <ul>
 * <li>Flat result maps ({@link #feedbackOverall}, {@link #feedbackState}, {@link #loincCode}, {@link #testMapping},
 * {@link #facility}, {@link #instrumentMap}): the listed columns set their properties; the other columns are
 * auto-mapped to a property of the same name (ignoring case; for a Map, a key named as the column) that the result map
 * does not already set; null values are not set; a listed column the rows do not have is skipped; a row that sets
 * nothing is null.</li>
 * <li>Nested result maps ({@link #laboratories}: CLIA -&gt; facilities, {@link #permits}: permit -&gt; instruments,
 * {@link #localCodes}: local code -&gt; LOINC codes): no auto-mapping. Rows are grouped by each level's key - its id
 * column, or all its columns if it has none, taking the non-null values only; a level whose key values are all null
 * is not grouped (a new object per row), and a child is grouped only within a grouped parent. The first row of a group
 * sets its values, the collections keep the first appearance order, a collection is an empty list when nothing was
 * found for it.</li>
 * </ul>
 * All the properties are text (the columns were read with getString; the procedures' cursors are read that way,
 * {@link #textRows}).
 */
final class LabReportsResultMaps {

	private LabReportsResultMaps() {
	}

	// ---- flat result maps -----------------------------------------------------------------------------------------

	/** feedBackMedicalReport: column -> property. */
	private static final Map<String, String> FEEDBACK_OVERALL = columns(
			"G_TOTAL_PRFS", "generateTotalPRFS", "RACE_FILLED_CNT", "raceFilledCount", "RACE_FILLED_PCT", "raceFilledPercentage",
			"GENDER_FILLED_CNT", "genderFilledCount", "GENDER_FILLED_PCT", "genderFilledPercenage", "DOB_FILLED_CNT", "dobFilledCount",
			"DOB_FILLED_PCT", "dobFilledPercentage", "ETH_FILLED_CNT", "ethnicityFilledCount", "ETH_FILLED_PCT", "ethnicityFilledPercentage",
			"DOD_FILLED_CNT", "dodFilledCount", "DOD_FILLED_PCT", "dodFilledPercentage", "ALL_RISK_FILLED_CNT", "allRiskFilledCount",
			"ALL_RISK_FILLED_PCT", "allRiskFilledPercentage", "PCT_COMPLETED_14_DAYS", "percentageCompleted14Days",
			"PCT_COMPLETED_29_DAYS", "percentageCompleted29Days", "PCT_COMPLETED_44_DAYS", "percentageCompleted44Days",
			"PCT_COMPLETED_59_DAYS", "percentageCompleted59Days", "PCT_DAYS_UNKNOWN", "percentageCompletedDaysUnknown",
			"PCT_INFORMED_2_DAYS", "percentageInFormed2Days", "PCT_INFORMED_6_DAYS", "percentageInFormed6Days",
			"PCT_INFORMED_2_WEEKS", "percentageInFormed2Weeks", "PCT_INFORMED_2_PLUS_WEEKS", "percentageInFormed2PlusWeeks",
			"PCT_INFORMED_UNKNOWN", "percentageInFormedDausUnkonwn", "TTH_COMP_COUNT", "testingTreatmentHistoryCount",
			"TTH_COMP_PCT", "testingTreatmentHistoryPercentage", "DOH_ASSISTANCE_REQ_CNT", "dohAssistanceRequiredCount",
			"DOH_ASSISTANCE_REQ_PCT", "dohAssistanceRequiredPercent", "TOTAL_PARTNERS_REPORTED", "totalPartnersReported",
			"NUM_ASSISTANCE_STATUS_PROVIDED", "numberAssistanceStatusProvided", "PCT_ASSISTANCE_STATUS_PROVIDED", "percentAssistanceStatusProvided",
			"NUM_PARTNER_NOTIFIED", "numberPartnerNotified", "PCT_PARTNER_NOTIFIED", "percentPartnerNotified",
			"NUM_PARTNER_NOT_NOTIFIED", "numberPartnerNotNotified", "PCT_PARTNER_NOT_NOTIFIED", "percentPartnerNotNotified",
			"INIT_DIAG_CNT", "initialDiagnoseCount", "AIDS_DIAG_CNT", "aidsDiagnoseCount", "HIV_DIAG_CNT", "hivDiagnoseCount",
			"TOP_RISK_REPORTED_CAT", "topRiskReportedCategory", "PRTNRS_CDNT_NAMED_CNT", "partnerContactNamedCount",
			"PRTNRS_CDNT_NAMED_PCT", "partnerContactNamedPercentage", "address_line1", "address1", "address_line2", "address2",
			"phone_number", "phoneNumber", "PROVIDER_NAME", "providerName", "new_diag_inf_pct", "newlyDiagnosedHIVPercentage",
			"total_hiv_incidence_reported", "totalHIVIncidenceReported");

	/** feedBackMedicalReportState: column -> property (stateDodFilledPercentage reads DOD_FILLED_PCT, as before). */
	private static final Map<String, String> FEEDBACK_STATE = columns(
			"G_STATE_PRFS", "generateStatePRFS", "ST_RACE_FILLED_CNT", "stateRaceFilledCount", "ST_RACE_FILLED_PCT", "stateRaceFilledPercentage",
			"ST_GENDER_FILLED_CNT", "stateGenderFilledCount", "ST_GENDER_FILLED_PCT", "stateGenderFilledPercenage",
			"ST_DOB_FILLED_CNT", "stateDobFilledCount", "ST_DOB_FILLED_PCT", "stateDobFilledPercentage",
			"ST_ETH_FILLED_CNT", "stateEthnicityFilledCount", "ST_ETH_FILLED_PCT", "stateEthnicityFilledPercentage",
			"ST_DOD_FILLED_CNT", "stateDodFilledCount", "DOD_FILLED_PCT", "stateDodFilledPercentage",
			"ST_ALL_RISK_FILLED_CNT", "stateAllRiskFilledCount", "ST_ALL_RISK_FILLED_PCT", "stateAllRiskFilledPercentage",
			"ST_PCT_COMPLETED_14_DAYS", "statePercentageCompleted14Days", "ST_PCT_COMPLETED_29_DAYS", "statePercentageCompleted29Days",
			"ST_PCT_COMPLETED_44_DAYS", "statePercentageCompleted44Days", "ST_PCT_COMPLETED_59_DAYS", "statePercentageCompleted59Days",
			"ST_PCT_DAYS_UNKNOWN", "statePercentageCompletedDaysUnknown", "ST_PCT_INFORMED_2_DAYS", "statePercentageInFormed2Days",
			"ST_PCT_INFORMED_6_DAYS", "statePercentageInFormed6Days", "ST_PCT_INFORMED_2_WEEKS", "statePercentageInFormed2Weeks",
			"ST_PCT_INFORMED_2_PLUS_WEEKS", "statePercentageInFormed2PlusWeeks", "ST_PCT_INFORMED_UNKNOWN", "statePercentageInFormedDausUnkonwn",
			"ST_TTH_EXIST_PCT", "stateTestingTreatmentHistoryPercentage", "ST_DOH_ASSISTANCE_REQ_PCT", "stateDohAssistanceRequiredPercent",
			"ST_PRTNRS_CDNT_NAMED_PCT", "statePartnerContactNamedPercentage", "ST_PCT_ASST_STATUS", "statePercentageAssistanceStatusProvided",
			"ST_PCT_PARTNER_NOTIFIED", "statePercentagePartnerNotified", "ST_PCT_PARTNER_NOT_NOTIFIED", "statePercentagePartnerNotNotified");

	/** getLabLoincCodes: column -> property. */
	private static final Map<String, String> LOINC_CODE = columns(
			"LAB_LOINC_MAP_ID", "loincMapId", "LOINC_CD", "loincCode", "DESCRIPTION", "loincDescription", "UNIT_OF_RESULT", "unitOfResult",
			"TEST_TYPE", "testType", "FLAG_ACTIVE", "flagActive", "LAST_UPDATED_BY", "lastUpdatedBy", "LAST_UPDATED_DATE", "lastUpdatedDate",
			"START_DATE", "dateValidFrom", "END_DATE", "dateValidTill", "TEST_KIT", "testKit", "TEST_KIT_DESC", "testKitDesc",
			"INSTRUMENT", "instrument", "INSTRUMENT_DESC", "instrumentDesc", "VL_DETECTION_LEVEL", "vlDetectionLevel",
			"DETECTION_DESC", "vlDetectionLevelDesc");

	/** getLabMapCodes: column -> property. */
	private static final Map<String, String> TEST_MAPPING = columns(
			"lab_local_cd_map_id", "localCodeMapId", "lab_local_test_cd", "testCode", "lab_local_test_desc", "testDescription",
			"FLAG_ACTIVE", "flagActive", "LAST_UPDATED_BY", "lastUpdatedBy", "LAST_UPDATED_DATE", "lastUpdatedDate",
			"START_DATE", "dateValidFrom", "END_DATE", "dateValidTill");

	/** labFacilitiesList (and the facilities of getClinicalLaboratoriesList): column -> property. */
	private static final Map<String, String> FACILITY = columns(
			"PFI", "pfi", "LAB_NAME", "labName", "ADDRESS", "address", "CITY", "city", "STATE", "state", "ZIP", "zipCode",
			"COUNTRY", "country", "FLAG_ACTIVE", "flagActive", "LAFT_STAFF", "laftStaffAssigned");

	/** instrumentCodesAndDescriptionsMap (a Map): column -> key. */
	private static final Map<String, String> INSTRUMENT_MAP = columns("HIV_INSTRUMENT_ID", "instrumentId", "INSTRUMENT_NAME", "instrumentName");

	/** A feedBackMedicalReport row. */
	static FeedbackReportOverallSummaryVO feedbackOverall(Row row) {
		return flat(row, FeedbackReportOverallSummaryVO.class, FEEDBACK_OVERALL);
	}

	/** A feedBackMedicalReportState row. */
	static FeedbackReportStateSummaryVO feedbackState(Row row) {
		return flat(row, FeedbackReportStateSummaryVO.class, FEEDBACK_STATE);
	}

	/** A getLabLoincCodes row. */
	static LabLoincMappingVO loincCode(Row row) {
		return flat(row, LabLoincMappingVO.class, LOINC_CODE);
	}

	/** A getLabMapCodes row. */
	static LabTestMappingVO testMapping(Row row) {
		return flat(row, LabTestMappingVO.class, TEST_MAPPING);
	}

	/** A labFacilitiesList row. */
	static LabFacilitiesVO facility(Row row) {
		return flat(row, LabFacilitiesVO.class, FACILITY);
	}

	/**
	 * An instrumentCodesAndDescriptionsMap row of {@code select *}: instrumentId and instrumentName (as text), and every
	 * other column under its own label with its value as read (getObject), null values left out; null for a row
	 * without values.
	 */
	static Map<String, Object> instrumentMap(Row row) {
		Map<String, Object> map = new HashMap<>();
		Set<String> mapped = new LinkedHashSet<>();
		INSTRUMENT_MAP.keySet().forEach(column -> mapped.add(upper(column)));
		Set<String> seen = new LinkedHashSet<>();
		for (int i = 0; i < row.size(); i++) {
			String label = row.labels().get(i);
			if (seen.add(upper(label)) && !mapped.contains(upper(label)) && !INSTRUMENT_MAP.containsValue(label) && row.get(i) != null) {
				map.put(label, row.get(i));
			}
		}
		INSTRUMENT_MAP.forEach((column, key) -> {
			if (row.indexOf(column) >= 0 && row.get(column) != null) {
				map.put(key, convert(row.get(column), String.class));
			}
		});
		return map.isEmpty() ? null : map;
	}

	private static <T> T flat(Row row, Class<T> type, Map<String, String> mapped) {
		Set<String> mappedColumns = new LinkedHashSet<>();
		Set<String> mappedProperties = new LinkedHashSet<>();
		mapped.forEach((column, property) -> {
			mappedColumns.add(upper(column));
			mappedProperties.add(upper(property));
		});
		Map<String, Class<?>> properties = PROPERTY_TYPES.computeIfAbsent(type, LabReportsResultMaps::propertyTypes);
		List<String> labels = new ArrayList<>();
		List<Object> values = new ArrayList<>();
		// auto-mapping first: the other columns onto a property of the same name that the result map does not set
		Set<String> seen = new LinkedHashSet<>();
		for (int i = 0; i < row.size(); i++) {
			String label = row.labels().get(i);
			String key = upper(label);
			if (!seen.add(key) || mappedColumns.contains(key) || mappedProperties.contains(key)) {
				continue;
			}
			Class<?> propertyType = properties.get(key);
			if (propertyType != null && SIMPLE_TYPES.contains(propertyType)) {
				labels.add(label);
				values.add(row.get(i));
			}
		}
		// then the listed columns
		mapped.forEach((column, property) -> {
			if (row.indexOf(column) >= 0) {
				labels.add(property);
				values.add(row.get(column));
			}
		});
		return Rows.bean(type).map(new Row(labels, values));
	}

	// ---- nested result maps ---------------------------------------------------------------------------------------

	/** One level of a nested result map. */
	private record Level(String id, Class<?> type, Map<String, String> columns, List<String> keyColumns, List<Child> children) {
	}

	/** A collection of a level: its getter, its setter and the level of its elements. */
	private record Child(Function<Object, List<Object>> get, BiConsumer<Object, List<Object>> set, Level level) {
	}

	private static final Level LAB_FACILITY = new Level("labFacility", LabFacilitiesVO.class, FACILITY, List.of("PFI"), List.of());

	@SuppressWarnings("unchecked")
	private static final Level LABORATORY = new Level("getClinicalLaboratoriesList", ClinicalLaboratoryVO.class, columns("CLIA", "clia"),
			List.of("CLIA"), List.of(new Child(o -> (List<Object>) (List<?>) ((ClinicalLaboratoryVO) o).getLabFacilities(),
					(o, list) -> ((ClinicalLaboratoryVO) o).setLabFacilities((List<LabFacilitiesVO>) (List<?>) list), LAB_FACILITY)));

	private static final Level INSTRUMENT = new Level("instrumentDetails", LabInstrumentDetailsVO.class, columns(
			"LPI_ID", "lpiId", "HIV_INSTRUMENT_ID", "hivInstrumentId", "HIV_INSTRUMENT_CD", "hivInstrumentCode", "INSTRUMENT_NAME", "instrumentName",
			"INST_FLAG_ACTIVE", "instrumentFlagActive", "INST_LAST_UPDATED_BY", "lastUpdatedBy", "INST_LAST_UPDATED_DATE", "lastUpdatedDate",
			"PERMIT_INSTR_STARTDATE", "dateValidFrom", "PERMIT_INSTR_ENDDATE", "dateValidTill"), List.of("LPI_ID"), List.of());

	private static final Map<String, String> PERMIT_COLUMNS = columns(
			"LAB_PERMIT_ID", "labPermitId", "PERMIT_CD", "permitCode", "PERMIT_DESCRIPTION", "permitDescription", "DATE_VALID_FROM", "dateValidFrom",
			"DATE_VALID_TILL", "dateValidTill", "BHAE_CERTIFICATION_DATE", "bhaeCertDate", "PERMIT_FLAG_ACTIVE", "permitFlagActive",
			"PERMIT_LAST_UPDATED_BY", "lastUpdatedBy", "PERMIT_LAST_UPDATED_DATE", "lastUpdatedDate", "BHAE_REPORTING_STATUS", "reportingStatus",
			"permit_comments", "comments");

	@SuppressWarnings("unchecked")
	private static final Level PERMIT = new Level("getLabPermits", LabPermitsVO.class, PERMIT_COLUMNS, new ArrayList<>(PERMIT_COLUMNS.keySet()),
			List.of(new Child(o -> (List<Object>) (List<?>) ((LabPermitsVO) o).getInstrumentDetails(),
					(o, list) -> ((LabPermitsVO) o).setInstrumentDetails((List<LabInstrumentDetailsVO>) (List<?>) list), INSTRUMENT)));

	private static final Level LOCAL_CODE_LOINC = new Level("loincMappingDetails", LabLoincMappingVO.class, columns(
			"LAB_LOCAL_CD_LOINC_MAP_ID", "localCodeLoincMapId", "LAB_LOINC_MAP_ID", "loincMapId", "LOINC_CD", "loincCode",
			"DESCRIPTION", "loincDescription", "FLAG_ACTIVE", "flagActive", "LAST_UPDATED_BY", "lastUpdatedBy", "LAST_UPDATED_DATE", "lastUpdatedDate",
			"LOINC_START_DATE", "dateValidFrom", "LOINC_END_DATE", "dateValidTill"), List.of("LAB_LOCAL_CD_LOINC_MAP_ID"), List.of());

	private static final Map<String, String> LOCAL_CODE_COLUMNS = columns(
			"LAB_LOCAL_CD_MAP_ID", "localCodeMapId", "LOCAL_CD", "localCode", "LOCALCD_FLAG_ACTIVE", "localCodeFlagActive",
			"LOCALCD_LAST_UPDATED_BY", "lastUpdatedBy", "LOCALCD_LAST_UPDATED_DATE", "lastUpdatedDate", "LC_CODE_START_DATE", "dateValidFrom",
			"LC_CODE_END_DATE", "dateValidTill");

	@SuppressWarnings("unchecked")
	private static final Level LOCAL_CODE = new Level("getLabLocalCodes", LabLocalCodesMappingVO.class, LOCAL_CODE_COLUMNS,
			new ArrayList<>(LOCAL_CODE_COLUMNS.keySet()), List.of(new Child(o -> (List<Object>) (List<?>) ((LabLocalCodesMappingVO) o).getLoincMappingDetails(),
					(o, list) -> ((LabLocalCodesMappingVO) o).setLoincMappingDetails((List<LabLoincMappingVO>) (List<?>) list), LOCAL_CODE_LOINC)));

	/** The rows grouped by getClinicalLaboratoriesList (CLIA -&gt; facilities by PFI). */
	@SuppressWarnings("unchecked")
	static List<ClinicalLaboratoryVO> laboratories(List<Row> rows) {
		return (List<ClinicalLaboratoryVO>) (List<?>) group(rows, LABORATORY);
	}

	/** The rows grouped by getLabPermits (permit by all its columns -&gt; instruments by LPI_ID). */
	@SuppressWarnings("unchecked")
	static List<LabPermitsVO> permits(List<Row> rows) {
		return (List<LabPermitsVO>) (List<?>) group(rows, PERMIT);
	}

	/** The rows grouped by getLabLocalCodes (local code by all its columns -&gt; LOINC codes by LAB_LOCAL_CD_LOINC_MAP_ID). */
	@SuppressWarnings("unchecked")
	static List<LabLocalCodesMappingVO> localCodes(List<Row> rows) {
		return (List<LabLocalCodesMappingVO>) (List<?>) group(rows, LOCAL_CODE);
	}

	/**
	 * The rows of a cursor, every column read with getString (by its label, as MyBatis named the columns), mapped; the
	 * cursor is closed. A null cursor has no rows.
	 */
	static List<Object> textRows(ResultSet resultSet, RowMapper<?> mapper) throws SQLException {
		List<Object> rows = new ArrayList<>();
		if (resultSet == null) {
			return rows;
		}
		try (resultSet) {
			ResultSetMetaData metaData = resultSet.getMetaData();
			List<String> labels = new ArrayList<>();
			for (int column = 1; column <= metaData.getColumnCount(); column++) {
				labels.add(metaData.getColumnLabel(column));
			}
			while (resultSet.next()) {
				List<Object> values = new ArrayList<>(labels.size());
				for (int column = 1; column <= labels.size(); column++) {
					values.add(resultSet.getString(column));
				}
				rows.add(mapper.map(new Row(labels, values)));
			}
		}
		return rows;
	}

	/** The rows as they are (for the nested result maps, which group them all). */
	static RowMapper<Row> asRows() {
		return row -> row;
	}

	private static List<Object> group(List<Row> rows, Level top) {
		Map<List<Object>, Object> objects = new HashMap<>();
		List<Object> results = new ArrayList<>();
		for (Row row : rows) {
			List<Object> key = rowKey(top, row);
			Object known = key == null ? null : objects.get(key);
			Object value = rowValue(top, row, key, known, objects);
			if (known == null) {
				results.add(value);
			}
		}
		return results;
	}

	/** The key of a level in a row (its id, the non-null key values as text), or null if all key values are null. */
	private static List<Object> rowKey(Level level, Row row) {
		List<Object> key = new ArrayList<>();
		key.add(level.id());
		for (String column : level.keyColumns()) {
			if (row.indexOf(column) >= 0) {
				Object value = convert(row.get(column), String.class);
				if (value != null) {
					key.add(upper(column));
					key.add(value);
				}
			}
		}
		return key.size() < 2 ? null : key;
	}

	private static List<Object> combine(List<Object> key, List<Object> parentKey) {
		if (key == null || parentKey == null) {
			return null;
		}
		List<Object> combined = new ArrayList<>(key);
		combined.addAll(parentKey);
		return combined;
	}

	private static Object rowValue(Level level, Row row, List<Object> key, Object known, Map<List<Object>, Object> objects) {
		if (known != null) {
			nested(level, row, known, key, objects);
			return known;
		}
		List<String> labels = new ArrayList<>();
		List<Object> values = new ArrayList<>();
		level.columns().forEach((column, property) -> {
			if (row.indexOf(column) >= 0) {
				labels.add(property);
				values.add(row.get(column));
			}
		});
		Object bean = Rows.bean(level.type()).map(new Row(labels, values));
		boolean found = bean != null;
		Object instance = found ? bean : newInstance(level.type());
		found = nested(level, row, instance, key, objects) || found;
		Object value = found ? instance : null;
		if (key != null) {
			objects.put(key, value);
		}
		return value;
	}

	/** Adds a row's new collection elements to an object; true if one was added. */
	private static boolean nested(Level level, Row row, Object parent, List<Object> parentKey, Map<List<Object>, Object> objects) {
		boolean found = false;
		for (Child child : level.children()) {
			List<Object> collection = child.get().apply(parent);
			if (collection == null) {
				collection = new ArrayList<>();
				child.set().accept(parent, collection);
			}
			List<Object> key = combine(rowKey(child.level(), row), parentKey);
			Object known = key == null ? null : objects.get(key);
			Object value = rowValue(child.level(), row, key, known, objects);
			if (value != null && known == null) {
				collection.add(value);
				found = true;
			}
		}
		return found;
	}

	// ---- helpers ----------------------------------------------------------------------------------------------------

	private static final Set<Class<?>> SIMPLE_TYPES = Set.of(String.class, long.class, Long.class, int.class, Integer.class, short.class,
			Short.class, byte.class, Byte.class, double.class, Double.class, float.class, Float.class, boolean.class, Boolean.class,
			char.class, Character.class, java.math.BigDecimal.class, java.math.BigInteger.class, java.util.Date.class,
			java.sql.Date.class, java.sql.Timestamp.class);

	private static final Map<Class<?>, Map<String, Class<?>>> PROPERTY_TYPES = new java.util.concurrent.ConcurrentHashMap<>();

	/** The settable properties of a bean (setters, then fields without setter) by upper-case name, with their types. */
	private static Map<String, Class<?>> propertyTypes(Class<?> type) {
		Map<String, Class<?>> properties = new HashMap<>();
		for (Class<?> c = type; c != null && c != Object.class; c = c.getSuperclass()) {
			for (java.lang.reflect.Method method : c.getDeclaredMethods()) {
				String name = method.getName();
				if (name.length() > 3 && name.startsWith("set") && method.getParameterCount() == 1
						&& !java.lang.reflect.Modifier.isStatic(method.getModifiers())) {
					properties.putIfAbsent(upper(name.substring(3)), method.getParameterTypes()[0]);
				}
			}
		}
		for (Class<?> c = type; c != null && c != Object.class; c = c.getSuperclass()) {
			for (java.lang.reflect.Field field : c.getDeclaredFields()) {
				int modifiers = field.getModifiers();
				if (!java.lang.reflect.Modifier.isStatic(modifiers) && !java.lang.reflect.Modifier.isFinal(modifiers)) {
					properties.putIfAbsent(upper(field.getName()), field.getType());
				}
			}
		}
		return properties;
	}

	private static final Map<Class<?>, RowMapper<?>> CONVERTERS = new java.util.concurrent.ConcurrentHashMap<>();

	/** A value converted to a property type as MyBatis' type handler for that type read it (null stays null). */
	private static Object convert(Object value, Class<?> type) {
		if (value == null) {
			return null;
		}
		return CONVERTERS.computeIfAbsent(type, Rows::scalar).map(new Row(List.of("VALUE"), Collections.singletonList(value)));
	}

	private static Object newInstance(Class<?> type) {
		try {
			return type.getDeclaredConstructor().newInstance();
		} catch (ReflectiveOperationException e) {
			throw new IllegalStateException("Cannot create " + type.getName(), e);
		}
	}

	private static Map<String, String> columns(String... columnsAndProperties) {
		Map<String, String> columns = new LinkedHashMap<>();
		for (int i = 0; i < columnsAndProperties.length; i += 2) {
			columns.put(columnsAndProperties[i], columnsAndProperties[i + 1]);
		}
		return columns;
	}

	private static String upper(String name) {
		return name.toUpperCase(Locale.ENGLISH);
	}
}
