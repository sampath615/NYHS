package gov.ny.state.doh.bhae.nyehms.web.spring.dao.standardization.provider;

import static gov.ny.state.doh.bhae.nyehms.web.spring.dao.standardization.provider.ProviderMappings.in;
import static gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements.cursor;
import static gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements.out;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.standardization.provider.RawProviderAddressVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.entity.standardization.RawProvsNoMatchView;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.Row;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.Rows;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.Values;
import gov.ny.state.doh.bhae.nyehms.web.spring.record.RawProvsNoMatchNewRow;
import gov.ny.state.doh.bhae.nyehms.web.spring.record.RawProvsNoMatchRow;
import gov.ny.state.doh.bhae.nyehms.web.spring.repository.standardization.RawProvsNoMatchNewViewRepository;
import gov.ny.state.doh.bhae.nyehms.web.spring.repository.standardization.RawProvsNoMatchViewRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;

/**
 * The raw provider addresses of the provider standardization (formerly a MyBatis mapper with the same name and
 * methods): the two grids read the views through Spring Data (the grid search is a specification), the links and
 * flags are the PKG_STANDARDIZATION procedures, called through {@link NativeStatements#call}; their OUT values and
 * cursor rows are put back into the caller's map under the keys MyBatis used.
 */
@Repository
@RequiredArgsConstructor
public class RawProviderAddressDAO {

	/** The grid's search fields: jqGrid name -> view attribute (the former &lt;choose&gt; on searchField). */
	private static final Map<String, String> SEARCH_FIELDS = Map.of(
			"rawProviderFullName", "providerFullname",
			"address", "address",
			"city", "city",
			"state", "state",
			"zipcode", "zipcode",
			"phone", "phone");

	/** The grid's search operators (the former &lt;choose&gt; on searchOper). */
	private static final List<String> SEARCH_OPERATORS = List.of("eq", "ne", "bw", "bn", "ew", "en", "cn", "nc", "in", "ni");

	private final RawProvsNoMatchViewRepository noMatchView;
	private final RawProvsNoMatchNewViewRepository noMatchNewView;
	private final NativeStatements statements;

	/**
	 * The raw provider addresses not yet standardized (NYEHMS_RAW_PROVS_NO_MATCH_VW): without a search the first 100
	 * rows; with a search ({@code _search} true) the rows whose upper-cased field matches the upper-cased value, all of
	 * them.
	 * <p>
	 * <b>Kept as before:</b> a search with an unknown field and an unknown operator returns the first 100 rows (the
	 * former {@code where ROWNUM <= 100}); an unknown field with a known operator, a known field with an unknown
	 * operator, and {@code in}/{@code ni} without values made invalid SQL and fail
	 * ({@link InvalidDataAccessResourceUsageException}).
	 *
	 * @param inOutParameterMap _search (Boolean), searchField, searchOper, searchString (a text; for in/ni an array or
	 *        collection of texts)
	 */
	public List<RawProviderAddressVO> getRawProvAddrsList(Map<Object, Object> inOutParameterMap) {
		if (!Boolean.TRUE.equals(inOutParameterMap.get("_search"))) {
			return noMatchList(noMatchView.findTop100By());
		}
		Object field = inOutParameterMap.get("searchField");
		String attribute = field instanceof String name ? SEARCH_FIELDS.get(name) : null;
		Object operator = inOutParameterMap.get("searchOper");
		boolean knownOperator = operator != null && SEARCH_OPERATORS.contains(operator);
		if (attribute == null && !knownOperator) {
			return noMatchList(noMatchView.findTop100By());
		}
		if (attribute == null || !knownOperator) {
			throw new InvalidDataAccessResourceUsageException("getRawProvAddrsList: invalid search (field " + field + ", operator " + operator
					+ "), the former SQL was invalid");
		}
		Object value = inOutParameterMap.get("searchString");
		List<String> values = "in".equals(operator) || "ni".equals(operator) ? texts(value) : null;
		Specification<RawProvsNoMatchView> search = (root, query, cb) -> {
			Expression<String> column = cb.upper(root.get(attribute));
			return switch ((String) operator) {
				case "eq" -> cb.equal(column, upper(cb, value));
				case "ne" -> cb.notEqual(column, upper(cb, value));
				case "bw" -> cb.like(column, cb.concat(upper(cb, value), "%"));
				case "bn" -> cb.notLike(column, cb.concat(upper(cb, value), "%"));
				case "ew" -> cb.like(column, cb.concat("%", upper(cb, value)));
				case "en" -> cb.notLike(column, cb.concat("%", upper(cb, value)));
				case "cn" -> cb.like(column, cb.concat(cb.concat("%", upper(cb, value)), "%"));
				case "nc" -> cb.notLike(column, cb.concat(cb.concat("%", upper(cb, value)), "%"));
				case "in" -> inList(cb, column, values);
				default -> cb.not(inList(cb, column, values));               // "ni"
			};
		};
		return noMatchList(noMatchView.findBy(search, query -> query.as(RawProvsNoMatchRow.class).all()));
	}

	/** UPPER(#{searchString}) */
	private static Expression<String> upper(CriteriaBuilder cb, Object value) {
		if (value != null && (value.getClass().isArray() || value instanceof Iterable<?>)) {
			throw new InvalidDataAccessResourceUsageException("getRawProvAddrsList: a list of values for a single-value operator");
		}
		return cb.upper(value == null ? cb.nullLiteral(String.class) : cb.literal(value.toString()));
	}

	/** column in (UPPER(#{v1}), UPPER(#{v2}), ...) */
	private static Predicate inList(CriteriaBuilder cb, Expression<String> column, List<String> values) {
		CriteriaBuilder.In<String> in = cb.in(column);
		for (String value : values) {
			in.value(cb.upper(value == null ? cb.nullLiteral(String.class) : cb.literal(value)));
		}
		return in;
	}

	/** The values of an in/ni search (the former &lt;foreach&gt;: an array or collection, not empty). */
	private static List<String> texts(Object value) {
		List<Object> elements = elements(value, "searchString");
		List<String> texts = new ArrayList<>();
		for (Object element : elements) {
			texts.add(element == null ? null : element.toString());
		}
		return texts;
	}

	/** The elements MyBatis' &lt;foreach&gt; iterated: an array, a collection or a map's values; none or null failed. */
	private static List<Object> elements(Object value, String name) {
		List<Object> elements = new ArrayList<>();
		if (value instanceof Iterable<?> iterable) {
			iterable.forEach(elements::add);
		} else if (value instanceof Map<?, ?> map) {
			elements.addAll(map.values());
		} else if (value != null && value.getClass().isArray()) {
			for (int i = 0; i < Array.getLength(value); i++) {
				elements.add(Array.get(value, i));
			}
		} else {
			throw new InvalidDataAccessResourceUsageException(name + " is not a list: " + value);
		}
		if (elements.isEmpty()) {
			throw new InvalidDataAccessResourceUsageException(name + " is empty, the former SQL was invalid");
		}
		return elements;
	}

	private static List<RawProviderAddressVO> noMatchList(List<RawProvsNoMatchRow> views) {
		List<RawProviderAddressVO> results = new ArrayList<>();
		for (RawProvsNoMatchRow v : views) {
			results.add(Rows.bean(RawProviderAddressVO.class).map(new Row(
					List.of("rawProviderAddressId", "countyFips", "address", "city", "state", "zipcode", "plus4", "rawProviderNPI", "phone",
							"rawProviderFullName", "rawProviderFname", "rawProviderMname", "rawProviderLname", "rawProviderSuffix"),
					Arrays.asList(v.rawProviderAddressId(), v.countyFips(), v.address(), v.city(), v.state(), v.zipcode(), v.plus4(),
							v.npi(), v.phone(), v.providerFullname(), v.providerFname(), v.providerMname(), v.providerLname(),
							v.providerSuffix()))));
		}
		return results;
	}

	/**
	 * Links a raw provider address to a provider address ({@code pr_link_raw_sub_provider}); the procedure commits
	 * or rolls back its own work.
	 *
	 * @param inOutParameterMap p_raw_provider_address_id, p_sub_provider_address_id, p_worker_id; receives p_code_out,
	 *        p_msg_out
	 */
	@Transactional
	public void linkRawProvToSubProvAddrs(Map<Object, Object> inOutParameterMap) {
		List<Object> results = statements.call("PKG_STANDARDIZATION.pr_link_raw_sub_provider",
				in(inOutParameterMap.get("p_raw_provider_address_id"), Integer.class),
				in(inOutParameterMap.get("p_sub_provider_address_id"), Integer.class),
				in(inOutParameterMap.get("p_worker_id"), Integer.class),
				out(Integer.class),
				out(String.class));
		inOutParameterMap.put("p_code_out", results.get(3));
		inOutParameterMap.put("p_msg_out", results.get(4));
	}

	/**
	 * Links several raw provider addresses to a provider address ({@code pr_link_raw_sub_provider_mult}).
	 *
	 * @param inOutParameterMap p_raw_provider_addr_id_list (comma-separated), p_sub_provider_address_id, p_worker_id;
	 *        receives p_code_out, p_msg_out
	 */
	@Transactional
	public void linkRawProvAddressesToSubProvAddrs(Map<Object, Object> inOutParameterMap) {
		List<Object> results = statements.call("PKG_STANDARDIZATION.pr_link_raw_sub_provider_mult",
				in(inOutParameterMap.get("p_raw_provider_addr_id_list"), String.class),
				in(inOutParameterMap.get("p_sub_provider_address_id"), Integer.class),
				in(inOutParameterMap.get("p_worker_id"), Integer.class),
				out(Integer.class),
				out(String.class));
		inOutParameterMap.put("p_code_out", results.get(3));
		inOutParameterMap.put("p_msg_out", results.get(4));
	}

	/**
	 * The raw provider addresses of a provider address ({@code PR_RAW_PROV_ADDRESSES_BY_SUB}, a cursor): put into the
	 * map as {@code rawProvAdrsListRefCursor} (RawProviderAddressVOs, result map rawProvAddrssResult).
	 *
	 * @param map subProviderAdrsId, rawSubProvIds
	 * @return an empty list (the rows come back through the map)
	 */
	public List<RawProviderAddressVO> getRawProvAdrsListBySubprovAdrsId(Map<Object, Object> map) {
		List<Object> results = statements.call("PKG_STANDARDIZATION.PR_RAW_PROV_ADDRESSES_BY_SUB",
				in(map.get("subProviderAdrsId"), Long.class),
				in(map.get("rawSubProvIds"), String.class),
				cursor(ProviderMappings::asRow));
		map.put("rawProvAdrsListRefCursor", ProviderMappings.rawProviderAddresses((List<?>) results.get(2)));
		return new ArrayList<>();
	}

	/**
	 * Flags provider data as facility data and copies it to the facility data ({@code pr_flip_to_facility}).
	 *
	 * @param inOutParamaterMap providerId, workerId; receives statusCode, statusMessage
	 */
	@Transactional
	public void flagProviderDataAsFacilty(Map<Object, Object> inOutParamaterMap) {
		List<Object> results = statements.call("PKG_STANDARDIZATION.pr_flip_to_facility",
				in(inOutParamaterMap.get("providerId"), Long.class),
				in(inOutParamaterMap.get("workerId"), Long.class),
				out(Integer.class),
				out(String.class));
		inOutParamaterMap.put("statusCode", results.get(2));
		inOutParamaterMap.put("statusMessage", results.get(3));
	}

	/**
	 * The raw provider addresses not yet standardized, new screen (NYEHMS_RAW_PROVS_NO_MATCH_NEW_VW): with
	 * {@code selected} true the selectable rows (SUB_PROVIDER_ADDRESS_ID -9999) of the given ids, otherwise the first
	 * 1000 rows. <b>Kept as before:</b> selected with no or null ids fails (the former SQL was invalid).
	 *
	 * @param inOutParameterMap selected (Boolean), selectedIds (an array or collection of ids)
	 */
	public List<RawProviderAddressVO> getRawProvAddrsNewList(Map<Object, Object> inOutParameterMap) {
		List<RawProvsNoMatchNewRow> views;
		if (Boolean.TRUE.equals(inOutParameterMap.get("selected"))) {
			List<Long> ids = new ArrayList<>();
			for (Object id : elements(inOutParameterMap.get("selectedIds"), "selectedIds")) {
				ids.add(Values.longValue(id));
			}
			views = noMatchNewView.findBySubProviderAddressIdAndRawProviderAddressIdIn(-9999L, ids);
		} else {
			views = noMatchNewView.findTop1000By();
		}
		List<RawProviderAddressVO> results = new ArrayList<>();
		for (RawProvsNoMatchNewRow v : views) {
			results.add(Rows.bean(RawProviderAddressVO.class).map(new Row(
					List.of("rawProviderAddressId", "address", "city", "state", "zipcode", "plus4", "rawProviderNPI", "phone", "rawProviderFullName",
							"rawProviderFname", "rawProviderMname", "rawProviderLname"),
					Arrays.asList(v.rawProviderAddressId(), v.address(), v.city(), v.state(), v.zipcode(), v.plus4(), v.npi(),
							v.phone(), v.providerFullname(), v.providerFname(), v.providerMname(), v.providerLname()))));
		}
		return results;
	}

	/**
	 * Flags the selected raw provider addresses as facility data ({@code pr_flip_to_facility_Multiple}).
	 *
	 * @param inOutParamaterMap p_raw_provider_addr_id_list (comma-separated), p_user_id; receives p_code_out, p_msg_out
	 */
	@Transactional
	public void flagSelectedRawProviderAsFacilty(Map<Object, Object> inOutParamaterMap) {
		List<Object> results = statements.call("PKG_STANDARDIZATION.pr_flip_to_facility_Multiple",
				in(inOutParamaterMap.get("p_raw_provider_addr_id_list"), String.class),
				in(inOutParamaterMap.get("p_user_id"), Long.class),
				out(Integer.class),
				out(String.class));
		inOutParamaterMap.put("p_code_out", results.get(2));
		inOutParamaterMap.put("p_msg_out", results.get(3));
	}
}
