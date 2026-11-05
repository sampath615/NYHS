package gov.ny.state.doh.bhae.nyehms.web.spring.dao.standardization.provider;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

import org.springframework.dao.InvalidDataAccessResourceUsageException;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.standardization.provider.ProviderDetailsVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.standardization.provider.RawProviderAddressVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.standardization.provider.SubProviderAddressVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements.CallParam;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.Row;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.Rows;

/**
 * The former MyBatis result maps of the provider standardization mappers, applied in Java to the rows of the
 * PKG_STANDARDIZATION cursors and of the views, with MyBatis' rules (default settings):
 * <ul>
 * <li>{@link #rawProviderAddress} — {@code rawProvAddrssResult}, a flat result map: its columns set their
 * properties (other columns are ignored: the map names every property, so nothing is auto-mapped); null values are
 * not set; a row that sets nothing is null.</li>
 * <li>{@link #providerDetails} — {@code getProvAndAddrssResultList}: provider, its addresses (by
 * SUB_PROVIDER_ADDRESS_ID), each address's phones (PHONE_NUMBER, distinct) and raw addresses (by
 * RAW_PROVIDER_ADDRESS_ID); {@link #orphanAddresses} — {@code orphanProviderAddresses}: address (by
 * SUB_PROVIDER_ADDRESS_ID) with its phones (PHONE, distinct). Nested result maps are grouped as MyBatis grouped them:
 * an object is identified by its id columns (all its columns if it has none) together with its parent's identity,
 * the first row of an object sets its properties, objects keep the order they first appear in, a value-less
 * identity is never merged, every object gets its (possibly empty) lists, and no auto-mapping applies.</li>
 * </ul>
 * Values are converted to the property types as MyBatis' type handlers read them ({@link Rows}).
 */
public final class ProviderMappings {

	private ProviderMappings() {
	}

	/**
	 * An IN parameter of a procedure call bound with its value's type (MyBatis bound a map value with the type
	 * handler of its class), or with the statement's jdbcType when it is null.
	 */
	static CallParam in(Object value, Class<?> typeIfNull) {
		return NativeStatements.in(value, value == null ? typeIfNull : value.getClass());
	}

	/** Cursor rows as they are; the result maps are applied afterwards. */
	static Row asRow(Row row) {
		return row;
	}

	/** A column of a result map: its column (label), the property it sets and that property's type. */
	record Result(String column, String property, Class<?> type) {
	}

	/** A collection of a result map: its element result map and the list property of the parent. */
	record Nested(ResultMap map, Function<Object, List<Object>> getter, BiConsumer<Object, List<Object>> setter) {
	}

	/**
	 * A result map: its id (distinguishes the identities of different maps), its id columns (none: all columns
	 * identify), its columns and collections; {@code scalar} maps (a collection of String) read their first column.
	 */
	record ResultMap(String id, List<Result> ids, List<Result> results, Class<?> type, Supplier<Object> factory, List<Nested> nested, boolean scalar) {
	}

	// ---------------------------------------------------------------- rawProvAddrssResult (RawProviderAddressDAO.xml)

	private static final List<Result> RAW_PROVIDER_ADDRESS = List.of(
			new Result("RAW_PROVIDER_ADDRESS_ID", "rawProviderAddressId", long.class),
			new Result("SUB_PROVIDER_ADDRESS_ID", "subProviderAddressId", long.class),
			new Result("COUNTY_FIPS", "countyFips", String.class),
			new Result("ADDRESS", "address", String.class),
			new Result("CITY", "city", String.class),
			new Result("STATE", "state", String.class),
			new Result("ZIPCODE", "zipcode", String.class),
			new Result("PLUS4", "plus4", String.class),
			new Result("PHONE", "phone", String.class),
			new Result("NPI", "rawProviderNPI", String.class),
			new Result("PROVIDER_FNAME", "rawProviderFname", String.class),
			new Result("PROVIDER_MNAME", "rawProviderMname", String.class),
			new Result("PROVIDER_LNAME", "rawProviderLname", String.class),
			new Result("PROVIDER_SUFFIX", "rawProviderSuffix", String.class),
			new Result("PROVIDER_FULLNAME", "rawProviderFullName", String.class),
			new Result("AUTO_ASSIGN_FUTURE", "autoAssignFuture", String.class));

	/**
	 * One row through the result map rawProvAddrssResult (null if it sets nothing). The result map names every
	 * property of RawProviderAddressVO, and MyBatis does not auto-map a property the result map names, so the other
	 * columns of a row are ignored.
	 */
	static RawProviderAddressVO rawProviderAddress(Row row) {
		List<String> labels = new ArrayList<>();
		List<Object> values = new ArrayList<>();
		for (Result result : RAW_PROVIDER_ADDRESS) {
			int index = row.indexOf(result.column());
			if (index >= 0 && row.get(index) != null) {
				labels.add(result.property());
				values.add(row.get(index));
			}
		}
		return Rows.bean(RawProviderAddressVO.class).map(new Row(labels, values));
	}

	/** Rows through the result map rawProvAddrssResult. */
	static List<RawProviderAddressVO> rawProviderAddresses(List<?> rows) {
		List<RawProviderAddressVO> results = new ArrayList<>();
		for (Object row : rows) {
			results.add(rawProviderAddress((Row) row));
		}
		return results;
	}

	// ---------------------------------------------------------- getProvAndAddrssResultList (ProviderDetailsDAO.xml)

	private static final ResultMap PROVIDER_PHONES = new ResultMap("providerDetails.phone", List.of(),
			List.of(new Result("PHONE_NUMBER", "phone", Object.class)), String.class, null, List.of(), true);

	private static final ResultMap PROVIDER_RAW_ADDRESSES = new ResultMap("providerDetails.rawProviderAddresses",
			List.of(new Result("raw_provider_address_id", "rawProviderAddressId", long.class)),
			List.of(new Result("raw_provider_address_id", "rawProviderAddressId", long.class),
					new Result("raw_address", "address", String.class),
					new Result("raw_city", "city", String.class),
					new Result("raw_state", "state", String.class),
					new Result("RAW_COUNTY_FIPS", "countyFips", String.class),
					new Result("raw_zipcode", "zipcode", String.class),
					new Result("raw_plus4", "plus4", String.class),
					new Result("raw_phone", "phone", String.class),
					new Result("raw_provider_fname", "rawProviderFname", String.class),
					new Result("raw_provider_mname", "rawProviderMname", String.class),
					new Result("raw_provider_lname", "rawProviderLname", String.class),
					new Result("raw_provider_fullname", "rawProviderFullName", String.class),
					new Result("RAW_NPI", "rawProviderNPI", String.class),
					new Result("raw_provider_suffix", "rawProviderSuffix", String.class),
					new Result("AUTO_ASSIGN_FUTURE", "autoAssignFuture", String.class)),
			RawProviderAddressVO.class, RawProviderAddressVO::new, List.of(), false);

	@SuppressWarnings("unchecked")
	private static final ResultMap PROVIDER_ADDRESSES = new ResultMap("providerDetails.subProviderAddresses",
			List.of(new Result("SUB_PROVIDER_ADDRESS_ID", "subProviderAddressId", long.class)),
			List.of(new Result("SUB_PROVIDER_ADDRESS_ID", "subProviderAddressId", long.class),
					new Result("COUNTY_FIPS", "countyFips", String.class),
					new Result("ADDRESS", "address", String.class),
					new Result("CITY", "city", String.class),
					new Result("STATE", "state", String.class),
					new Result("ZIPCODE", "zipcode", String.class),
					new Result("PLUS4", "plus4", String.class)),
			SubProviderAddressVO.class, SubProviderAddressVO::new,
			List.of(new Nested(PROVIDER_PHONES, a -> (List<Object>) (List<?>) ((SubProviderAddressVO) a).getPhone(),
							(a, list) -> ((SubProviderAddressVO) a).setPhone((List<String>) (List<?>) list)),
					new Nested(PROVIDER_RAW_ADDRESSES, a -> (List<Object>) (List<?>) ((SubProviderAddressVO) a).getRawProviderAddresses(),
							(a, list) -> ((SubProviderAddressVO) a).setRawProviderAddresses((List<RawProviderAddressVO>) (List<?>) list))),
			false);

	@SuppressWarnings("unchecked")
	private static final ResultMap PROVIDER_DETAILS = new ResultMap("providerDetails", List.of(),
			List.of(new Result("PROVIDER_ID", "providerId", long.class),
					new Result("PROVIDER_FNAME", "providerFname", String.class),
					new Result("PROVIDER_MNAME", "providerMname", String.class),
					new Result("PROVIDER_LNAME", "providerLname", String.class),
					new Result("PROVIDER_FULLNAME", "providerFullName", String.class),
					new Result("NPI", "providerNPI", String.class),
					new Result("SPECIALITY", "providerSpeciality", String.class),
					new Result("PROVIDER_SUFFIX", "providerSuffix", String.class)),
			ProviderDetailsVO.class, ProviderDetailsVO::new,
			List.of(new Nested(PROVIDER_ADDRESSES, p -> (List<Object>) (List<?>) ((ProviderDetailsVO) p).getSubProviderAddresses(),
					(p, list) -> ((ProviderDetailsVO) p).setSubProviderAddresses((List<SubProviderAddressVO>) (List<?>) list))),
			false);

	/** Cursor rows through the result map getProvAndAddrssResultList (providers with addresses, phones, raw addresses). */
	@SuppressWarnings("unchecked")
	static List<ProviderDetailsVO> providerDetails(List<?> rows) {
		return (List<ProviderDetailsVO>) (List<?>) group(PROVIDER_DETAILS, rows);
	}

	// --------------------------------------------------- orphanProviderAddresses (OrphanProviderAddressDAO.xml)

	private static final ResultMap ORPHAN_PHONES = new ResultMap("orphanProviderAddresses.phone", List.of(),
			List.of(new Result("PHONE", "phone", Object.class)), String.class, null, List.of(), true);

	@SuppressWarnings("unchecked")
	private static final ResultMap ORPHAN_ADDRESSES = new ResultMap("orphanProviderAddresses",
			List.of(new Result("SUB_PROVIDER_ADDRESS_ID", "subProviderAddressId", long.class)),
			List.of(new Result("SUB_PROVIDER_ADDRESS_ID", "subProviderAddressId", long.class),
					new Result("PROVIDER_ID", "providerId", long.class),
					new Result("COUNTY_FIPS", "countyFips", String.class),
					new Result("ADDRESS", "address", String.class),
					new Result("CITY", "city", String.class),
					new Result("STATE", "state", String.class),
					new Result("ZIPCODE", "zipcode", String.class),
					new Result("PLUS4", "plus4", String.class)),
			SubProviderAddressVO.class, SubProviderAddressVO::new,
			List.of(new Nested(ORPHAN_PHONES, a -> (List<Object>) (List<?>) ((SubProviderAddressVO) a).getPhone(),
					(a, list) -> ((SubProviderAddressVO) a).setPhone((List<String>) (List<?>) list))),
			false);

	/** Rows through the result map orphanProviderAddresses (addresses with their phones). */
	@SuppressWarnings("unchecked")
	static List<SubProviderAddressVO> orphanAddresses(List<Row> rows) {
		return (List<SubProviderAddressVO>) (List<?>) group(ORPHAN_ADDRESSES, rows);
	}

	// ------------------------------------------------------------------------------- nested result map grouping

	/** The top-level objects of rows through a nested result map (MyBatis handleRowValuesForNestedResultMap). */
	private static List<Object> group(ResultMap map, List<?> rows) {
		Map<List<Object>, Object> objects = new HashMap<>();
		List<Object> results = new ArrayList<>();
		for (Object element : rows) {
			Row row = (Row) element;
			List<Object> key = rowKey(map, row);
			Object partial = key == null ? null : objects.get(key);
			Object value = rowValue(map, row, key, partial, objects);
			if (partial == null) {
				results.add(value);                             // a new object (or null: a row without values)
			}
		}
		return results;
	}

	/** The object of a row: the known one (only its collections grow) or a new one; null if the row has no values. */
	private static Object rowValue(ResultMap map, Row row, List<Object> key, Object partial, Map<List<Object>, Object> objects) {
		if (partial != null) {
			applyNested(map, row, partial, key, objects);
			return partial;
		}
		Object value;
		if (map.scalar()) {
			String column = map.results().get(0).column();
			int index = row.indexOf(column);
			if (index < 0) {
				throw new InvalidDataAccessResourceUsageException("No column " + column + " in the result " + row.labels());
			}
			value = convert(row.get(index), String.class);
		} else {
			Object withProperties = properties(map, row);
			Object object = withProperties != null ? withProperties : map.factory().get();
			boolean found = applyNested(map, row, object, key, objects);
			value = found || withProperties != null ? object : null;
		}
		if (key != null) {
			objects.put(key, value);
		}
		return value;
	}

	/** Adds the row's new elements to the object's collections (each list created if missing); true if any was added. */
	private static boolean applyNested(ResultMap map, Row row, Object object, List<Object> parentKey, Map<List<Object>, Object> objects) {
		boolean found = false;
		for (Nested nested : map.nested()) {
			List<Object> list = nested.getter().apply(object);
			if (list == null) {
				list = new ArrayList<>();
				nested.setter().accept(object, list);
			}
			List<Object> rowKey = rowKey(nested.map(), row);
			List<Object> key = rowKey != null && parentKey != null ? Arrays.asList(rowKey, parentKey) : null;
			Object known = key == null ? null : objects.get(key);
			Object value = rowValue(nested.map(), row, key, known, objects);
			if (value != null && known == null) {
				list.add(value);
				found = true;
			}
		}
		return found;
	}

	/** The identity of a row's object: the map id with the id columns' (or all columns') values; null if none has a value. */
	private static List<Object> rowKey(ResultMap map, Row row) {
		List<Object> key = new ArrayList<>();
		key.add(map.id());
		for (Result result : map.ids().isEmpty() ? map.results() : map.ids()) {
			int index = row.indexOf(result.column());
			if (index < 0) {
				continue;
			}
			Object value = convert(row.get(index), result.type());
			if (value != null) {
				key.add(result.column());
				key.add(value);
			}
		}
		return key.size() < 2 ? null : key;
	}

	/** A new object with the row's non-null property values, or null if the row has none. */
	private static Object properties(ResultMap map, Row row) {
		List<String> labels = new ArrayList<>();
		List<Object> values = new ArrayList<>();
		for (Result result : map.results()) {
			int index = row.indexOf(result.column());
			if (index >= 0 && row.get(index) != null) {
				labels.add(result.property());
				values.add(row.get(index));
			}
		}
		return Rows.bean(map.type()).map(new Row(labels, values));
	}

	/** A value read as a property type (as its MyBatis type handler read it); Object: as it is. */
	private static Object convert(Object value, Class<?> type) {
		if (value == null) {
			return null;
		}
		return Rows.scalar(type).map(new Row(List.of("VALUE"), Collections.singletonList(value)));
	}
}
