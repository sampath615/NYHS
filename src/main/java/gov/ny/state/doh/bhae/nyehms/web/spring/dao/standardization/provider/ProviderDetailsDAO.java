package gov.ny.state.doh.bhae.nyehms.web.spring.dao.standardization.provider;

import static gov.ny.state.doh.bhae.nyehms.web.spring.dao.standardization.provider.ProviderMappings.in;
import static gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements.cursor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.standardization.provider.ProviderDetailsVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.standardization.provider.RawProviderAddressVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.JpqlStatements;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.Rows;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.Values;
import lombok.RequiredArgsConstructor;

/**
 * Provider search operations (formerly a MyBatis mapper with the same name and methods): the four searches are
 * PKG_STANDARDIZATION procedures with a cursor, called through {@link NativeStatements#call}; their rows go through
 * the former result map getProvAndAddrssResultList ({@link ProviderMappings#providerDetails}) and are put back into
 * the caller's map as {@code p_sub_prov_out}. The raw address search is JPQL over RawProviderAddress.
 */
@Repository
@RequiredArgsConstructor
public class ProviderDetailsDAO {

	/** The raw address search's optional conditions, in the former order: map key, condition. */
	private static final String[][] RAW_CONDITIONS = {
			{"p_provider_fname", "r.providerFname like ?"},
			{"p_provider_lname", "r.providerLname like ?"},
			{"p_provider_mname", "r.providerMname like ?"},
			{"p_address", "r.address like ?"},
			{"p_city", "r.city like ?"},
			{"p_state", "r.state = ?"},
			{"p_zipcode", "r.zipcode like ?"},
			{"p_county_fips", "r.countyFips = ?"},
			{"p_phone", "r.phone like ?"},
			{"p_provider_npi", "r.npi like ?"}};

	private final JpqlStatements jpql;
	private final NativeStatements statements;

	/**
	 * The providers with their addresses matching the criteria ({@code pr_sub_provider_search}).
	 *
	 * @param map p_provider_fname, p_provider_lname, p_provider_mname, p_address, p_city, p_state, p_zipcode,
	 *        p_county_fips, p_phone, p_provider_npi; receives p_sub_prov_out (ProviderDetailsVOs)
	 * @return an empty list (the rows come back through the map)
	 */
	public List<ProviderDetailsVO> getSrchProviderDetailsWithAddressList(Map<Object, Object> map) {
		List<Object> results = statements.call("PKG_STANDARDIZATION.pr_sub_provider_search",
				in(map.get("p_provider_fname"), String.class),
				in(map.get("p_provider_lname"), String.class),
				in(map.get("p_provider_mname"), String.class),
				in(map.get("p_address"), String.class),
				in(map.get("p_city"), String.class),
				in(map.get("p_state"), String.class),
				in(map.get("p_zipcode"), String.class),
				in(map.get("p_county_fips"), String.class),
				in(map.get("p_phone"), String.class),
				in(map.get("p_provider_npi"), String.class),
				cursor(ProviderMappings::asRow));
		map.put("p_sub_prov_out", ProviderMappings.providerDetails((List<?>) results.get(10)));
		return new ArrayList<>();
	}

	/**
	 * The providers matching a name / NPI ({@code pr_prov_search_by_name}).
	 *
	 * @param map p_fname, p_lname, p_mname, p_npi; receives p_sub_prov_out (ProviderDetailsVOs)
	 * @return an empty list (the rows come back through the map)
	 */
	public List<ProviderDetailsVO> getProviderDetailsByProvider(Map<Object, Object> map) {
		List<Object> results = statements.call("PKG_STANDARDIZATION.pr_prov_search_by_name",
				in(map.get("p_fname"), String.class),
				in(map.get("p_lname"), String.class),
				in(map.get("p_mname"), String.class),
				in(map.get("p_npi"), String.class),
				cursor(ProviderMappings::asRow));
		map.put("p_sub_prov_out", ProviderMappings.providerDetails((List<?>) results.get(4)));
		return new ArrayList<>();
	}

	/**
	 * The providers with an address matching the criteria ({@code pr_prov_search_by_address}).
	 *
	 * @param map p_address, p_city, p_state, p_zipcode, p_county_fips, p_phone; receives p_sub_prov_out
	 * @return an empty list (the rows come back through the map)
	 */
	public List<ProviderDetailsVO> getProviderDetailsByProviderAddress(Map<Object, Object> map) {
		List<Object> results = statements.call("PKG_STANDARDIZATION.pr_prov_search_by_address",
				in(map.get("p_address"), String.class),
				in(map.get("p_city"), String.class),
				in(map.get("p_state"), String.class),
				in(map.get("p_zipcode"), String.class),
				in(map.get("p_county_fips"), String.class),
				in(map.get("p_phone"), String.class),
				cursor(ProviderMappings::asRow));
		map.put("p_sub_prov_out", ProviderMappings.providerDetails((List<?>) results.get(6)));
		return new ArrayList<>();
	}

	/**
	 * The providers with a raw address matching the criteria ({@code pr_prov_search_by_raw}).
	 *
	 * @param map p_raw_fname, p_raw_lname, p_raw_mname, p_raw_address, p_raw_city, p_raw_state, p_raw_zipcode,
	 *        p_raw_county_fips, p_raw_phone, p_raw_npi; receives p_sub_prov_out
	 * @return an empty list (the rows come back through the map)
	 */
	public List<ProviderDetailsVO> getProviderDetailsByRawProviderAddress(Map<Object, Object> map) {
		List<Object> results = statements.call("PKG_STANDARDIZATION.pr_prov_search_by_raw",
				in(map.get("p_raw_fname"), String.class),
				in(map.get("p_raw_lname"), String.class),
				in(map.get("p_raw_mname"), String.class),
				in(map.get("p_raw_address"), String.class),
				in(map.get("p_raw_city"), String.class),
				in(map.get("p_raw_state"), String.class),
				in(map.get("p_raw_zipcode"), String.class),
				in(map.get("p_raw_county_fips"), String.class),
				in(map.get("p_raw_phone"), String.class),
				in(map.get("p_raw_npi"), String.class),
				cursor(ProviderMappings::asRow));
		map.put("p_sub_prov_out", ProviderMappings.providerDetails((List<?>) results.get(10)));
		return new ArrayList<>();
	}

	/**
	 * The raw provider addresses matching the given criteria (each condition only when its value is not null; none:
	 * every raw address, as before). The full name is built by the database function
	 * {@code pkg_trk_common.fn_get_fullname}, called from JPQL.
	 *
	 * @param map p_provider_fname, p_provider_lname, p_provider_mname, p_address, p_city, p_zipcode, p_phone,
	 *        p_provider_npi (LIKE patterns), p_state, p_county_fips (equal)
	 */
	public List<RawProviderAddressVO> getRawProviderDetails(Map<Object, Object> map) {
		StringBuilder query = new StringBuilder("select r.rawProviderAddressId as rawProviderAddressId, r.address as address, r.city as city,"
				+ " r.state as state, r.countyFips as countyFips, r.zipcode as zipcode, r.plus4 as plus4, r.phone as phone,"
				+ " function('pkg_trk_common.fn_get_fullname', r.providerLname, r.providerFname, r.providerMname, r.providerSuffix) as rawProviderFullName,"
				+ " r.npi as rawProviderNPI from RawProviderAddress r");
		List<Object> parameters = new ArrayList<>();
		for (String[] condition : RAW_CONDITIONS) {
			Object value = map.get(condition[0]);
			if (value != null) {
				parameters.add(Values.text(value));
				query.append(parameters.size() == 1 ? " where " : " and ").append(condition[1]).append(parameters.size());
			}
		}
		return jpql.list(query.toString(), Rows.bean(RawProviderAddressVO.class), parameters.toArray());
	}
}
