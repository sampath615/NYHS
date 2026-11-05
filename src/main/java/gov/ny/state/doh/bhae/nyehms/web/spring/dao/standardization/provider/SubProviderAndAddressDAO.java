package gov.ny.state.doh.bhae.nyehms.web.spring.dao.standardization.provider;

import static gov.ny.state.doh.bhae.nyehms.web.spring.dao.standardization.provider.ProviderMappings.in;
import static gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements.out;
import static gov.ny.state.doh.bhae.nyehms.web.spring.jpa.Values.longValue;
import static gov.ny.state.doh.bhae.nyehms.web.spring.jpa.Values.text;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.standardization.provider.SubProviderAndAddressVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.entity.common.RawProviderAddress;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements;
import gov.ny.state.doh.bhae.nyehms.web.spring.repository.common.RawProviderAddressRepository;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;

/**
 * Provider and provider address creation (formerly a MyBatis mapper with the same name and methods): the new provider
 * / address are PKG_STANDARDIZATION procedures called through {@link NativeStatements#call} (their OUT values put
 * back into the caller's map), the raw provider address is an entity saved through Spring Data, and the existence
 * check is a specification.
 */
@Repository
@RequiredArgsConstructor
public class SubProviderAndAddressDAO {

	/** The existence check's columns, in the former order: entity attribute, VO property. */
	private static final List<Map.Entry<String, Function<SubProviderAndAddressVO, String>>> EXISTENCE_COLUMNS = List.of(
			Map.entry("address", SubProviderAndAddressVO::getAddress),
			Map.entry("city", SubProviderAndAddressVO::getCity),
			Map.entry("state", SubProviderAndAddressVO::getState),
			Map.entry("zipcode", SubProviderAndAddressVO::getZipcode),
			Map.entry("phone", SubProviderAndAddressVO::getPhone),
			Map.entry("countyFips", SubProviderAndAddressVO::getCountyFips),
			Map.entry("providerFname", SubProviderAndAddressVO::getProviderFname),
			Map.entry("providerMname", SubProviderAndAddressVO::getProviderMname),
			Map.entry("providerLname", SubProviderAndAddressVO::getProviderLname),
			Map.entry("providerSuffix", SubProviderAndAddressVO::getProviderSuffix),
			Map.entry("plus4", SubProviderAndAddressVO::getPlus4));

	private final RawProviderAddressRepository rawProviderAddresses;
	private final NativeStatements statements;

	/**
	 * Adds a new provider with a new address ({@code pr_add_sub_provider}).
	 *
	 * @param map p_provider_fname, p_provider_mname, p_provider_lname, p_provider_suffix, p_county_fips, p_address,
	 *        p_city, p_state, p_zipcode, p_plus4, p_phone, p_extension, p_worker_id, p_npi, p_speciality; receives
	 *        p_sub_provider_id_out, p_sub_provider_address_id_out (Long), p_code_out, p_msg_out
	 */
	@Transactional
	public void addNewSubProvAddrs(Map<Object, Object> map) {
		List<Object> results = statements.call("PKG_STANDARDIZATION.pr_add_sub_provider",
				in(map.get("p_provider_fname"), String.class),
				in(map.get("p_provider_mname"), String.class),
				in(map.get("p_provider_lname"), String.class),
				in(map.get("p_provider_suffix"), String.class),
				in(map.get("p_county_fips"), String.class),
				in(map.get("p_address"), String.class),
				in(map.get("p_city"), String.class),
				in(map.get("p_state"), String.class),
				in(map.get("p_zipcode"), String.class),
				in(map.get("p_plus4"), String.class),
				in(map.get("p_phone"), String.class),
				in(map.get("p_extension"), String.class),
				in(map.get("p_worker_id"), String.class),
				in(map.get("p_npi"), String.class),
				out(Long.class),
				out(Long.class),
				out(Integer.class),
				out(String.class),
				in(map.get("p_speciality"), String.class));
		map.put("p_sub_provider_id_out", results.get(14));
		map.put("p_sub_provider_address_id_out", results.get(15));
		map.put("p_code_out", results.get(16));
		map.put("p_msg_out", results.get(17));
	}

	/**
	 * Adds a new address to an existing provider ({@code pr_add_sub_provider_address}).
	 *
	 * @param inOutParameterMap p_provider_id, p_county_fips, p_address, p_city, p_state, p_zipcode, p_plus4, p_phone,
	 *        p_extension, p_worker_id; receives p_sub_provider_address_id_out (Long), p_code_out, p_msg_out
	 */
	@Transactional
	public void addNewAddrs(Map<Object, Object> inOutParameterMap) {
		List<Object> results = statements.call("PKG_STANDARDIZATION.pr_add_sub_provider_address",
				in(inOutParameterMap.get("p_provider_id"), Integer.class),
				in(inOutParameterMap.get("p_county_fips"), String.class),
				in(inOutParameterMap.get("p_address"), String.class),
				in(inOutParameterMap.get("p_city"), String.class),
				in(inOutParameterMap.get("p_state"), String.class),
				in(inOutParameterMap.get("p_zipcode"), String.class),
				in(inOutParameterMap.get("p_plus4"), String.class),
				in(inOutParameterMap.get("p_phone"), String.class),
				in(inOutParameterMap.get("p_extension"), String.class),
				in(inOutParameterMap.get("p_worker_id"), String.class),
				out(Long.class),
				out(Integer.class),
				out(String.class));
		inOutParameterMap.put("p_sub_provider_address_id_out", results.get(10));
		inOutParameterMap.put("p_code_out", results.get(11));
		inOutParameterMap.put("p_msg_out", results.get(12));
	}

	/**
	 * Inserts a raw provider address, its key from RAW_PROVIDER_ADDRESS_SEQ, and puts the key into the map as
	 * {@code rawProviderId} (a BigDecimal, as the former generated key was read).
	 *
	 * @param inOutParameterMap p_subprovideraddress_id, p_county_fips, p_address, p_city, p_state, p_plus4,
	 *        p_zipcode, p_phone, p_provider_fname, p_provider_mname, p_provider_lname, p_provider_suffix, p_npi
	 * @return 1, the number of rows inserted
	 */
	@Transactional
	public int createRawProviderAddress(Map<Object, Object> inOutParameterMap) {
		RawProviderAddress saved = rawProviderAddresses.saveAndFlush(RawProviderAddress.builder()
				.subProviderAddressId(longValue(inOutParameterMap.get("p_subprovideraddress_id")))
				.countyFips(text(inOutParameterMap.get("p_county_fips")))
				.address(text(inOutParameterMap.get("p_address")))
				.city(text(inOutParameterMap.get("p_city")))
				.state(text(inOutParameterMap.get("p_state")))
				.plus4(text(inOutParameterMap.get("p_plus4")))
				.zipcode(text(inOutParameterMap.get("p_zipcode")))
				.phone(text(inOutParameterMap.get("p_phone")))
				.providerFname(text(inOutParameterMap.get("p_provider_fname")))
				.providerMname(text(inOutParameterMap.get("p_provider_mname")))
				.providerLname(text(inOutParameterMap.get("p_provider_lname")))
				.providerSuffix(text(inOutParameterMap.get("p_provider_suffix")))
				.npi(text(inOutParameterMap.get("p_npi")))
				.build());
		inOutParameterMap.put("rawProviderId", BigDecimal.valueOf(saved.getRawProviderAddressId()));
		return 1;
	}

	/**
	 * The id of a raw provider address with exactly these values (an empty or missing value: the column is null), 0
	 * if there is none.
	 * <p>
	 * <b>Kept as before:</b> of several matching rows the first one read is returned, not the highest id (the former
	 * {@code NVL(MAX(id), 0) ... and rownum < 2} took the maximum of one row).
	 */
	public int checkRawProviderAddressExistance(SubProviderAndAddressVO subProviderAndAddressVO) {
		Specification<RawProviderAddress> sameValues = (root, query, cb) -> {
			List<Predicate> predicates = new ArrayList<>();
			for (Map.Entry<String, Function<SubProviderAndAddressVO, String>> column : EXISTENCE_COLUMNS) {
				String value = column.getValue().apply(subProviderAndAddressVO);
				predicates.add(value != null && !value.isEmpty() ? cb.equal(root.get(column.getKey()), value) : cb.isNull(root.get(column.getKey())));
			}
			return cb.and(predicates.toArray(new Predicate[0]));
		};
		return rawProviderAddresses.findBy(sameValues, query -> query.first())
				.map(row -> row.getRawProviderAddressId().intValue())
				.orElse(0);
	}
}
