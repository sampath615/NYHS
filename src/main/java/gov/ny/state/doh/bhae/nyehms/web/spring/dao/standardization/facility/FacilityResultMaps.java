package gov.ny.state.doh.bhae.nyehms.web.spring.dao.standardization.facility;

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
import java.util.function.Supplier;

import org.springframework.dao.InvalidDataAccessResourceUsageException;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.standardization.facility.MasterFacilityDetailsVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.standardization.facility.RawSubFacilityVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.standardization.facility.SubFacilityForRawFacilityVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.standardization.facility.SubFacilityVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements.RowMapper;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.Row;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.Rows;

/**
 * The former MyBatis result maps of the facility standardization mappers, applied to result rows (of a JPQL
 * projection or of a PKG_STANDARDIZATION procedure's cursor) with the MyBatis rules (default settings):
 * <ul>
 * <li>{@link #rawSubFacility} (rawSubFacilityResult) and {@link #subFacilityForRawFacility} (subFacForRawFacResult):
 * flat result maps. The listed columns set their properties; the other columns are auto-mapped to a property of the
 * same name (ignoring case) that the result map does not already set; null values are not set; a row that sets nothing
 * is null.</li>
 * <li>{@link #masterFacilities} (masterFacilitiesWithSubFacilities, master -&gt; sub facilities -&gt; phones and raw
 * facilities) and {@link #orphanSubFacilities} (subFacility, sub facility -&gt; phones): nested result maps, no
 * auto-mapping. Rows are grouped by each level's key - its id column, or all its columns if it has none, taking the
 * non-null values only; a level whose key values are all null is not grouped (a new object per row), and a child is
 * grouped only within a grouped parent. The first row of a group sets its values, the collections keep the first
 * appearance order, a collection is an empty list when nothing was found for it, a phone column that the rows do not
 * have fails (the phone list reads it by name), other missing columns are skipped.</li>
 * </ul>
 */
public final class FacilityResultMaps {

	private FacilityResultMaps() {
	}

	/** rawSubFacilityResult (RawSubFacilityDAO): column -> property. */
	private static final Map<String, String> RAW_SUB_FACILITY = columns(
			"RAW_SUB_FACILITY_ID", "rawSubFacilityId", "SUB_FACILITY_ID", "subFacilityId", "FACILITY_TYPE_CODE", "facilityTypeCode",
			"FACILITY_TYPE_DESC", "facilityTypeDesc", "FACILITY_NAME", "facilityName", "FACILITY_DEPARTMENT", "facilityDepartment",
			"LEGACY_FACILITY_ID", "legacyFacilityId", "LEGACY_FACILITY_TYPE", "legacyFacilityType", "ADDRESS", "address", "CITY", "city",
			"STATE", "state", "ZIPCODE", "zipcode", "PLUS4", "plus4", "PHONE", "phone", "NPI", "npi",
			"LEGACY_SEQ_NUM_ADDRESS", "legacySeqNumAddress", "CREATE_UID", "createUid", "DATE_CREATED", "dateCreated",
			"DATE_ENDED", "dateEnded", "END_UID", "endUid", "POT_RAW_FACILITY_ID", "pmRawFacilityId");

	/** subFacForRawFacResult (SubFacilityDAO): column -> property. */
	private static final Map<String, String> SUB_FACILITY_FOR_RAW = columns(
			"SUB_FACILITY_ID", "subFacilityId", "INPUT_RAW_FACILITY_ID", "inputRawFacilityId");

	/** A rawSubFacilityResult row (with auto-mapping), or null if it sets nothing. */
	public static RawSubFacilityVO rawSubFacility(Row row) {
		return flat(row, RawSubFacilityVO.class, RAW_SUB_FACILITY);
	}

	/** A subFacForRawFacResult row (with auto-mapping), or null if it sets nothing. */
	public static SubFacilityForRawFacilityVO subFacilityForRawFacility(Row row) {
		return flat(row, SubFacilityForRawFacilityVO.class, SUB_FACILITY_FOR_RAW);
	}

	/** The rows grouped by masterFacilitiesWithSubFacilities (MasterFacilityDetailsDAO, FacilityManualReviewDAO). */
	@SuppressWarnings("unchecked")
	public static List<MasterFacilityDetailsVO> masterFacilities(List<Row> rows) {
		return (List<MasterFacilityDetailsVO>) (List<?>) group(rows, MASTER);
	}

	/** The rows grouped by subFacility (OrphanSubFacilityDAO). */
	@SuppressWarnings("unchecked")
	public static List<SubFacilityVO> orphanSubFacilities(List<Row> rows) {
		return (List<SubFacilityVO>) (List<?>) group(rows, ORPHAN_SUB_FACILITY);
	}

	// ---- flat result maps -----------------------------------------------------------------------------------------

	private static <T> T flat(Row row, Class<T> type, Map<String, String> mapped) {
		Set<String> mappedColumns = new LinkedHashSet<>();
		Set<String> mappedProperties = new LinkedHashSet<>();
		mapped.forEach((column, property) -> {
			mappedColumns.add(upper(column));
			mappedProperties.add(upper(property));
		});
		Map<String, Class<?>> properties = PROPERTY_TYPES.computeIfAbsent(type, FacilityResultMaps::propertyTypes);
		List<String> labels = new ArrayList<>();
		List<Object> values = new ArrayList<>();
		// auto-mapping: the other columns onto a property of the same name that the result map does not set
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
		// the listed columns
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
	private record Level(String id, Class<?> type, Map<String, String> columns, List<String> keyColumns, Map<String, Class<?>> keyTypes,
			String stringColumn, List<Child> children) {
	}

	/** A collection of a level: its getter, its setter and the level of its elements. */
	private record Child(Function<Object, List<Object>> get, BiConsumer<Object, List<Object>> set, Supplier<Level> level) {
	}

	private static final Level PHONE = new Level("phone", String.class, Map.of(), List.of("PHONE"), Map.of("PHONE", String.class), "PHONE",
			List.of());

	private static final Level RAW_FACILITY = level("rawFacility", RawSubFacilityVO.class, columns(
			"RAW_SUB_FACILITY_ID", "rawSubFacilityId", "RAW_FACILITY_TYPE_CODE", "facilityTypeCode", "RAW_FACILITY_NAME", "facilityName",
			"RAW_FACILITY_DEPARTMENT", "facilityDepartment", "RAW_ADDRESS", "address", "RAW_CITY", "city", "RAW_STATE", "state",
			"RAW_ZIPCODE", "zipcode", "RAW_PLUS4", "plus4", "RAW_PHONE", "phone", "RAW_COUNTY_CD", "countyFips", "RAW_NPI", "npi"),
			List.of("RAW_SUB_FACILITY_ID"), List.of());

	@SuppressWarnings("unchecked")
	private static final Level SUB_FACILITY = level("subFacility", SubFacilityVO.class, columns(
			"SUB_FACILITY_ID", "subFacilityId", "FACILITY_TYPE_CODE", "facilityTypeCode", "FACILITY_NAME", "facilityName",
			"FACILITY_DEPARTMENT", "facilityDepartment", "COUNTY_FIPS", "countyFips", "ADDRESS", "address", "CITY", "city",
			"STATE", "state", "ZIPCODE", "zipcode", "NPI", "facilityNpi"),
			List.of("SUB_FACILITY_ID"), List.of(
					new Child(o -> (List<Object>) (List<?>) ((SubFacilityVO) o).getPhone(),
							(o, list) -> ((SubFacilityVO) o).setPhone((List<String>) (List<?>) list), () -> PHONE),
					new Child(o -> (List<Object>) (List<?>) ((SubFacilityVO) o).getRawFacilities(),
							(o, list) -> ((SubFacilityVO) o).setRawFacilities((List<RawSubFacilityVO>) (List<?>) list), () -> RAW_FACILITY)));

	@SuppressWarnings("unchecked")
	private static final Level MASTER = level("masterFacility", MasterFacilityDetailsVO.class, columns(
			"MASTER_FACILITY_ID", "masterFacilityId", "MASTER_FAC_TYPE_CODE", "masterFacilityTypeCode",
			"MASTER_FACILITY_NAME", "masterFacilityName"),
			List.of(), List.of(
					new Child(o -> (List<Object>) (List<?>) ((MasterFacilityDetailsVO) o).getSubFacilities(),
							(o, list) -> ((MasterFacilityDetailsVO) o).setSubFacilities((List<SubFacilityVO>) (List<?>) list), () -> SUB_FACILITY)));

	@SuppressWarnings("unchecked")
	private static final Level ORPHAN_SUB_FACILITY = level("orphanSubFacility", SubFacilityVO.class, columns(
			"SUB_FACILITY_ID", "subFacilityId", "FACILITY_TYPE_CODE", "facilityTypeCode", "FACILITY_NAME", "facilityName",
			"FACILITY_DEPARTMENT", "facilityDepartment", "ADDRESS", "address", "CITY", "city", "STATE", "state", "ZIPCODE", "zipcode"),
			List.of("SUB_FACILITY_ID"), List.of(
					new Child(o -> (List<Object>) (List<?>) ((SubFacilityVO) o).getPhone(),
							(o, list) -> ((SubFacilityVO) o).setPhone((List<String>) (List<?>) list), () -> PHONE)));

	/** A level of a bean type; its key columns (all its columns if none are given) are read with their property types. */
	private static Level level(String id, Class<?> type, Map<String, String> columns, List<String> idColumns, List<Child> children) {
		Map<String, Class<?>> properties = propertyTypes(type);
		List<String> keyColumns = idColumns.isEmpty() ? new ArrayList<>(columns.keySet()) : idColumns;
		Map<String, Class<?>> keyTypes = new HashMap<>();
		for (String column : keyColumns) {
			keyTypes.put(column, wrapper(properties.get(upper(columns.get(column)))));
		}
		return new Level(id, type, columns, keyColumns, keyTypes, null, children);
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

	/** The key of a level in a row (its id, the non-null key values), or null if all key values are null. */
	private static List<Object> rowKey(Level level, Row row) {
		List<Object> key = new ArrayList<>();
		key.add(level.id());
		for (String column : level.keyColumns()) {
			if (row.indexOf(column) >= 0) {
				Object value = convert(row.get(column), level.keyTypes().get(column));
				if (value != null) {
					key.add(column);
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
		Object value;
		if (level.stringColumn() != null) {
			if (row.indexOf(level.stringColumn()) < 0) {
				throw new InvalidDataAccessResourceUsageException("Invalid column name " + level.stringColumn() + " (read by the result map)");
			}
			value = convert(row.get(level.stringColumn()), String.class);
		} else {
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
			value = found ? instance : null;
		}
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
			Level childLevel = child.level().get();
			List<Object> key = combine(rowKey(childLevel, row), parentKey);
			Object known = key == null ? null : objects.get(key);
			Object value = rowValue(childLevel, row, key, known, objects);
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

	private static Class<?> wrapper(Class<?> type) {
		if (type == long.class) {
			return Long.class;
		}
		if (type == int.class) {
			return Integer.class;
		}
		return type == null ? Object.class : type;
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
