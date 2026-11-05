package gov.ny.state.doh.bhae.nyehms.web.spring.dao.report;

import static gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements.cursor;
import static gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements.in;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.springframework.stereotype.Repository;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.reports.ProvPortalOocDnltcGttDataVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.reports.ProvPortalSubmissionCareStatusVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.reports.ProvPortalSubmissionReportVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.Rows;
import gov.ny.state.doh.bhae.nyehms.web.spring.record.PortalProviderNameRow;
import gov.ny.state.doh.bhae.nyehms.web.spring.repository.report.PortalProvidersRepository;
import lombok.RequiredArgsConstructor;

/**
 * The provider portal submission reports (formerly a MyBatis mapper with the same name and methods): the provider list
 * through a Spring Data repository (JPQL over PortalProviders, PortalProviderUserMapping, EprfProvPortalFileUpload),
 * the reports through the procedure {@code pr_get_eprf_ooc_reports} (the report logic is PL/SQL).
 */
@Repository
@RequiredArgsConstructor
public class ProviderPortalReportDAO {

	private final PortalProvidersRepository portalProviders;
	private final NativeStatements statements;

	/**
	 * The providers who uploaded files of the given types: PORTAL_PROV_ID, PROVIDER_NAME (JSONObjects, null values left
	 * out, a row without values null).
	 *
	 * @param inputParameter fileType1, fileType2
	 */
	@SuppressWarnings("unchecked")
	public List<JSONObject> getProvPortalSubmittedProviders(Map<String, String> inputParameter) {
		List<JSONObject> rows = new ArrayList<>();
		for (PortalProviderNameRow provider : portalProviders.findSubmittedProviders(inputParameter.get("fileType1"),
				inputParameter.get("fileType2"))) {
			JSONObject row = new JSONObject();
			if (provider.portalProvId() != null) {
				row.put("PORTAL_PROV_ID", provider.portalProvId());
			}
			if (provider.providerName() != null) {
				row.put("PROVIDER_NAME", provider.providerName());
			}
			rows.add(row.isEmpty() ? null : row);
		}
		return rows;
	}

	/**
	 * Calls {@code pr_get_eprf_ooc_reports} (p_file_type_cd, p_prov_id, p_from_date, p_to_date in, as text) and puts its
	 * four cursors into the map as the former CALLABLE statement did: {@code p_snp_report} and
	 * {@code p_ooc_dnltc_report} (ProvPortalSubmissionReportVOs), {@code p_ooc_dnltc_care_status}
	 * (ProvPortalSubmissionCareStatusVOs), {@code p_eprf_ooc_gtt_data} (ProvPortalOocDnltcGttDataVOs). The VOs are filled
	 * by column label as the former result maps (property = column) filled them.
	 *
	 * @return an empty list (the procedure returns its rows through the cursors)
	 */
	public List<ProvPortalSubmissionReportVO> getProvPortalReportData(Map<Object, Object> map) {
		List<Object> results = statements.call("pr_get_eprf_ooc_reports",
				in(map.get("p_file_type_cd"), String.class),
				in(map.get("p_prov_id"), String.class),
				in(map.get("p_from_date"), String.class),
				in(map.get("p_to_date"), String.class),
				cursor(Rows.bean(ProvPortalSubmissionReportVO.class)),
				cursor(Rows.bean(ProvPortalSubmissionReportVO.class)),
				cursor(Rows.bean(ProvPortalSubmissionCareStatusVO.class)),
				cursor(Rows.bean(ProvPortalOocDnltcGttDataVO.class)));
		map.put("p_snp_report", results.get(4));
		map.put("p_ooc_dnltc_report", results.get(5));
		map.put("p_ooc_dnltc_care_status", results.get(6));
		map.put("p_eprf_ooc_gtt_data", results.get(7));
		return new ArrayList<>();
	}

	/**
	 * Not used: the former mapper declared this method but had no statement for it, so calling it failed (MyBatis
	 * "Invalid bound statement"); it still fails (kept as it was).
	 *
	 * @throws UnsupportedOperationException always
	 */
	public List<ProvPortalSubmissionReportVO> getProvPortalOOCDNLTCReportData(Map<Object, Object> map) {
		throw new UnsupportedOperationException("Invalid bound statement (not found): "
				+ ProviderPortalReportDAO.class.getName() + ".getProvPortalOOCDNLTCReportData");
	}
}
