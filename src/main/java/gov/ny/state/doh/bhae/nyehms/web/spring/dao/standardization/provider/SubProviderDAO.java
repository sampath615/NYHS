package gov.ny.state.doh.bhae.nyehms.web.spring.dao.standardization.provider;

import static gov.ny.state.doh.bhae.nyehms.web.spring.dao.standardization.provider.ProviderMappings.in;
import static gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements.out;
import static gov.ny.state.doh.bhae.nyehms.web.spring.jpa.Values.longValue;
import static gov.ny.state.doh.bhae.nyehms.web.spring.jpa.Values.text;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements;
import gov.ny.state.doh.bhae.nyehms.web.spring.repository.common.RawProviderAddressRepository;
import gov.ny.state.doh.bhae.nyehms.web.spring.repository.standardization.SubProviderAddressRepository;
import lombok.RequiredArgsConstructor;

/**
 * New providers and the "unable to standardize" marks (formerly a MyBatis mapper with the same name and methods):
 * the new provider is a PKG_STANDARDIZATION procedure called through {@link NativeStatements#call}, the marks are
 * load-change-save through Spring Data. The former SYSDATE of LAST_UPDATED_DATE is the application server's clock
 * (to the second).
 */
@Repository
@RequiredArgsConstructor
public class SubProviderDAO {

	private final RawProviderAddressRepository rawProviderAddresses;
	private final SubProviderAddressRepository subProviderAddresses;
	private final NativeStatements statements;

	/**
	 * Adds a new provider name ({@code pr_add_provider_name}).
	 *
	 * @param map p_provider_fname, p_provider_mname, p_provider_lname, p_provider_suffix, p_worker_id; receives
	 *        p_provider_id_out (Long), p_code_out, p_msg_out
	 */
	public void addNewProvider(Map<Object, Object> map) {
		List<Object> results = statements.call("PKG_STANDARDIZATION.pr_add_provider_name",
				in(map.get("p_provider_fname"), String.class),
				in(map.get("p_provider_mname"), String.class),
				in(map.get("p_provider_lname"), String.class),
				in(map.get("p_provider_suffix"), String.class),
				in(map.get("p_worker_id"), String.class),
				out(Long.class),
				out(Integer.class),
				out(String.class));
		map.put("p_provider_id_out", results.get(5));
		map.put("p_code_out", results.get(6));
		map.put("p_msg_out", results.get(7));
	}

	/**
	 * Marks a raw provider address as "unable to standardize" (SUB_PROVIDER_ADDRESS_ID 0).
	 *
	 * @param map recordId (the raw address), userId
	 * @return 1, or 0 if there is no such raw address
	 */
	@Transactional
	public int unableStandardizeRawProviderRecord(Map<Object, Object> map) {
		Long recordId = longValue(map.get("recordId"));
		if (recordId == null) {
			return 0;                                           // the former "where ... = NULL" matched nothing
		}
		return rawProviderAddresses.findById(recordId).map(row -> {
			row.unableToStandardize(now(), text(map.get("userId")));
			return 1;
		}).orElse(0);
	}

	/**
	 * Marks a provider address as "unable to standardize" (PROVIDER_ID 0).
	 *
	 * @param map recordId (the provider address), userId
	 * @return 1, or 0 if there is no such address
	 */
	@Transactional
	public int unableStandardizeProviderAddressRecord(Map<Object, Object> map) {
		Long recordId = longValue(map.get("recordId"));
		if (recordId == null) {
			return 0;                                           // the former "where ... = NULL" matched nothing
		}
		return subProviderAddresses.findById(recordId).map(row -> {
			row.unableToStandardize(now(), text(map.get("userId")));
			return 1;
		}).orElse(0);
	}

	/** SYSDATE: now, to the second (an Oracle DATE). */
	private static Timestamp now() {
		return new Timestamp(System.currentTimeMillis() / 1000 * 1000);
	}
}
