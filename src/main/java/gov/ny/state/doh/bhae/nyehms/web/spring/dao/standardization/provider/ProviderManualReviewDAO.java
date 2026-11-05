package gov.ny.state.doh.bhae.nyehms.web.spring.dao.standardization.provider;

import static gov.ny.state.doh.bhae.nyehms.web.spring.dao.standardization.provider.ProviderMappings.in;
import static gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements.cursor;
import static gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements.out;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.standardization.provider.ProviderDetailsVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.standardization.provider.RawProviderAddressVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.Row;
import gov.ny.state.doh.bhae.nyehms.web.spring.record.RawProvsClericRvwRow;
import gov.ny.state.doh.bhae.nyehms.web.spring.repository.standardization.RawProvsClericRvwViewRepository;
import lombok.RequiredArgsConstructor;

/**
 * The manual review of raw provider addresses (formerly a MyBatis mapper with the same name and methods): the review
 * list through Spring Data, the match / unmatch and the candidate list are PKG_STANDARDIZATION procedures called
 * through {@link NativeStatements#call}; their OUT values and cursor rows are put back into the caller's map.
 */
@Repository
@RequiredArgsConstructor
public class ProviderManualReviewDAO {

	private static final List<String> CLERIC_COLUMNS = List.of("RAW_PROVIDER_ADDRESS_ID", "COUNTY_FIPS", "ADDRESS", "CITY", "STATE", "ZIPCODE", "PLUS4",
			"PHONE", "PROVIDER_FULLNAME", "PROVIDER_FNAME", "PROVIDER_MNAME", "PROVIDER_LNAME", "PROVIDER_SUFFIX");

	private final RawProvsClericRvwViewRepository clericReviewView;
	private final NativeStatements statements;

	/**
	 * Matches a raw provider address to another one ({@code pr_match_provider}).
	 *
	 * @param inOutParameterMap p_raw_prov_addr_id, p_mtch_raw_prov_addr_id, p_worker_id; receives statusCode,
	 *        statusMessage
	 */
	public void matchRawProviderAddress(Map<Object, Object> inOutParameterMap) {
		List<Object> results = statements.call("PKG_STANDARDIZATION.pr_match_provider",
				in(inOutParameterMap.get("p_raw_prov_addr_id"), Long.class),
				in(inOutParameterMap.get("p_mtch_raw_prov_addr_id"), Long.class),
				in(inOutParameterMap.get("p_worker_id"), Long.class),
				out(Integer.class),
				out(String.class));
		inOutParameterMap.put("statusCode", results.get(3));
		inOutParameterMap.put("statusMessage", results.get(4));
	}

	/**
	 * Unmatches a raw provider address ({@code pr_unmatch_provider}).
	 *
	 * @param inOutParameterMap p_raw_prov_addr_id, p_worker_id; receives statusCode, statusMessage
	 */
	public void unMatchRawProviderAddress(Map<Object, Object> inOutParameterMap) {
		List<Object> results = statements.call("PKG_STANDARDIZATION.pr_unmatch_provider",
				in(inOutParameterMap.get("p_raw_prov_addr_id"), Long.class),
				in(inOutParameterMap.get("p_worker_id"), Long.class),
				out(Integer.class),
				out(String.class));
		inOutParameterMap.put("statusCode", results.get(2));
		inOutParameterMap.put("statusMessage", results.get(3));
	}

	/**
	 * The candidate providers of a raw provider address ({@code pr_prov_manual_review_list}, a cursor): put into the
	 * map as {@code p_providers_out} (ProviderDetailsVOs, result map getProvAndAddrssResultList).
	 *
	 * @param inOutParameterMap p_raw_provider_address_id
	 * @return an empty list (the rows come back through the map)
	 */
	public List<ProviderDetailsVO> getProviderDetailsForManualReview(Map<Object, Object> inOutParameterMap) {
		List<Object> results = statements.call("PKG_STANDARDIZATION.pr_prov_manual_review_list",
				in(inOutParameterMap.get("p_raw_provider_address_id"), Long.class),
				cursor(ProviderMappings::asRow));
		inOutParameterMap.put("p_providers_out", ProviderMappings.providerDetails((List<?>) results.get(1)));
		return new ArrayList<>();
	}

	/** The raw provider addresses waiting for the manual review (NYEHMS_RAW_PROVS_CLERIC_RVW_VW, result map rawProvAddrssResult). */
	public List<RawProviderAddressVO> getClericalReviewRawProviderAddressList() {
		List<RawProviderAddressVO> results = new ArrayList<>();
		for (RawProvsClericRvwRow v : clericReviewView.findAllBy()) {
			results.add(ProviderMappings.rawProviderAddress(new Row(CLERIC_COLUMNS,
					Arrays.asList(v.rawProviderAddressId(), v.countyFips(), v.address(), v.city(), v.state(), v.zipcode(), v.plus4(),
							v.phone(), v.providerFullname(), v.providerFname(), v.providerMname(), v.providerLname(),
							v.providerSuffix()))));
		}
		return results;
	}
}
