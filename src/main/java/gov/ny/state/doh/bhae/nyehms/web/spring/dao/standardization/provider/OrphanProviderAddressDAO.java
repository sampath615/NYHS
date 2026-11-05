package gov.ny.state.doh.bhae.nyehms.web.spring.dao.standardization.provider;

import static gov.ny.state.doh.bhae.nyehms.web.spring.dao.standardization.provider.ProviderMappings.in;
import static gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements.out;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.standardization.provider.SubProviderAddressVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.Row;
import gov.ny.state.doh.bhae.nyehms.web.spring.record.ProvAddrOrphanRow;
import gov.ny.state.doh.bhae.nyehms.web.spring.repository.standardization.ProvAddrOrphanViewRepository;
import lombok.RequiredArgsConstructor;

/**
 * The orphan provider address operations (formerly a MyBatis mapper with the same name and methods): the orphan list
 * through Spring Data (rows grouped per address as the former result map grouped them), the link a PKG_STANDARDIZATION
 * procedure called through {@link NativeStatements#call}.
 */
@Repository
@RequiredArgsConstructor
public class OrphanProviderAddressDAO {

	private static final List<String> ORPHAN_COLUMNS = List.of("SUB_PROVIDER_ADDRESS_ID", "PROVIDER_ID", "COUNTY_FIPS", "ADDRESS", "CITY", "STATE",
			"ZIPCODE", "PLUS4", "PHONE");

	private final ProvAddrOrphanViewRepository orphanView;
	private final NativeStatements statements;

	/**
	 * The orphan provider addresses (NYEHMS_PROV_ADDR_ORPHAN_VW), one per address with its phones (result map
	 * orphanProviderAddresses).
	 */
	public List<SubProviderAddressVO> getOrphanProviderAddresses() {
		List<Row> rows = new ArrayList<>();
		for (ProvAddrOrphanRow r : orphanView.findAllBy()) {
			rows.add(new Row(ORPHAN_COLUMNS, Arrays.asList(r.subProviderAddressId(), r.providerId(), r.countyFips(), r.address(), r.city(), r.state(),
					r.zipcode(), r.plus4(), r.phone())));
		}
		return ProviderMappings.orphanAddresses(rows);
	}

	/**
	 * Links an orphan address to a provider ({@code pr_link_sub_provider_addr_name}); afterwards it is no longer an
	 * orphan.
	 *
	 * @param inOutParameterMap providerAddressId, providerId, workerId; receives statusCode, statusMessage
	 */
	public void linkOrphanAddressToProvider(Map<Object, Object> inOutParameterMap) {
		List<Object> results = statements.call("PKG_STANDARDIZATION.pr_link_sub_provider_addr_name",
				in(inOutParameterMap.get("providerAddressId"), Integer.class),
				in(inOutParameterMap.get("providerId"), Integer.class),
				in(inOutParameterMap.get("workerId"), Integer.class),
				out(Integer.class),
				out(String.class));
		inOutParameterMap.put("statusCode", results.get(3));
		inOutParameterMap.put("statusMessage", results.get(4));
	}
}
