package gov.ny.state.doh.bhae.nyehms.web.spring.dao.standardization.provider;

import static gov.ny.state.doh.bhae.nyehms.web.spring.dao.standardization.provider.ProviderMappings.in;
import static gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements.out;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements;
import lombok.RequiredArgsConstructor;

/**
 * Merging and unlinking providers and provider addresses (formerly a MyBatis mapper with the same name and methods):
 * PKG_STANDARDIZATION procedures with comma-separated id lists, called through {@link NativeStatements#call}; their
 * OUT values are put into the caller's map as p_code_out, p_msg_out.
 */
@Repository
@RequiredArgsConstructor
public class MergeAndUnlinkProvDAO {

	private final NativeStatements statements;

	/** Unlinks raw provider addresses from their address ({@code pr_unlink_raw_provider_address}): p_tounlink_ids, p_parent_id, p_user_id. */
	public void unlinkRawProviderAndAddress(Map<Object, Object> map) {
		unlink("PKG_STANDARDIZATION.pr_unlink_raw_provider_address", map);
	}

	/** Unlinks provider addresses from their provider ({@code pr_unlink_sub_provider_address}): p_tounlink_ids, p_parent_id, p_user_id. */
	public void unlinkSubProviderAddress(Map<Object, Object> map) {
		unlink("PKG_STANDARDIZATION.pr_unlink_sub_provider_address", map);
	}

	private void unlink(String procedure, Map<Object, Object> map) {
		List<Object> results = statements.call(procedure,
				in(map.get("p_tounlink_ids"), String.class),
				in(map.get("p_parent_id"), Long.class),
				in(map.get("p_user_id"), Long.class),
				out(Integer.class),
				out(String.class));
		map.put("p_code_out", results.get(3));
		map.put("p_msg_out", results.get(4));
	}

	/**
	 * Merges provider addresses into a retained one ({@code pr_merge_sub_prov_addresses}).
	 *
	 * @param map p_sub_prov_addrs_list, p_retained_sub_prov_addrs, p_sub_prov, p_user_id
	 */
	public void mergeSubProviderAddress(Map<Object, Object> map) {
		List<Object> results = statements.call("PKG_STANDARDIZATION.pr_merge_sub_prov_addresses",
				in(map.get("p_sub_prov_addrs_list"), String.class),
				in(map.get("p_retained_sub_prov_addrs"), Long.class),
				in(map.get("p_sub_prov"), Long.class),
				in(map.get("p_user_id"), Long.class),
				out(Integer.class),
				out(String.class));
		map.put("p_code_out", results.get(4));
		map.put("p_msg_out", results.get(5));
	}

	/**
	 * Merges providers into a retained one ({@code pr_merge_sub_providers}).
	 *
	 * @param map p_sub_prov_list, p_retained_sub_prov, p_user_id
	 */
	public void mergeSubProviders(Map<Object, Object> map) {
		List<Object> results = statements.call("PKG_STANDARDIZATION.pr_merge_sub_providers",
				in(map.get("p_sub_prov_list"), String.class),
				in(map.get("p_retained_sub_prov"), Long.class),
				in(map.get("p_user_id"), Long.class),
				out(Integer.class),
				out(String.class));
		map.put("p_code_out", results.get(3));
		map.put("p_msg_out", results.get(4));
	}
}
