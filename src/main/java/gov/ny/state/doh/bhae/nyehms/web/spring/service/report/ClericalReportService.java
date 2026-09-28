package gov.ny.state.doh.bhae.nyehms.web.spring.service.report;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import gov.ny.state.doh.bhae.nyehms.web.spring.dao.report.ClericalReportDAO;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;

@Service
public class ClericalReportService {

	@Autowired
	private ClericalReportDAO clericalReportDAO;

	public Map<String, Map<String, Map<String, BigInteger>>> getclericalReportByStaff(String fromDate, String toDate,
			String reportType, UserObject userObject, Map<String, Object> map) {

		Map<String, String> inputParameterMap = new HashMap<String, String>();

		inputParameterMap.put("fromDate", fromDate);
		inputParameterMap.put("toDate", toDate);

		List<JSONObject> clericalReportData = null;

		if (reportType.equals("Clerical Death Review")) {
			clericalReportData = clericalReportDAO.getclericalDeathReportByStaff(inputParameterMap);

		} else {
			clericalReportData = clericalReportDAO.getclericalReportByStaff(inputParameterMap);
		}

		Map<String, Map<String, Map<String, BigInteger>>> clericalReportDataFormtted = getclericalReportDataFormatted(
				clericalReportData, map);
		map.put("reportType", reportType);
		return clericalReportDataFormtted;
	}

	public Map<String, Map<String, Map<String, BigInteger>>> getscanningReportByStaff(String fromDate, String toDate,
			UserObject userObject, Map<String, Object> map) {

		Map<String, String> inputParameterMap = new HashMap<String, String>();

		inputParameterMap.put("fromDate", fromDate);
		inputParameterMap.put("toDate", toDate);

		List<JSONObject> scaningReportData = clericalReportDAO.getScanningReportByStaff(inputParameterMap);

		Map<String, Map<String, Map<String, BigInteger>>> scanningReportDataFormtted = getscanningReportDataFormatted(
				scaningReportData, map);

		return scanningReportDataFormtted;
	}

	private Map<String, Map<String, Map<String, BigInteger>>> getscanningReportDataFormatted(
			List<JSONObject> scaningReportData, Map<String, Object> map) {

		Map<String, Map<String, Map<String, BigInteger>>> userMap = new LinkedHashMap<>();
		Map<String, Map<String, BigInteger>> totalCountByUser = new HashMap<>();

		for (JSONObject obj : scaningReportData) {

			String name = (String) obj.get("SCANNED_BY");
			BigInteger count = new BigInteger(obj.get("COUNT").toString());
			String date = (String) obj.get("CREATE_DATE");

			if (!userMap.containsKey(date)) {
				userMap.put(date, new HashMap<>());
			}
			Map<String, Map<String, BigInteger>> getUserNameByDate = userMap.get(date);

			if (!getUserNameByDate.containsKey(name)) {
				getUserNameByDate.put(name, new HashMap<>());
			}

			Map<String, BigInteger> getCountByName = getUserNameByDate.get(name);

			getCountByName.put("count", count);

			if (!totalCountByUser.containsKey(name)) {
				totalCountByUser.put(name, new HashMap<>());
			}

			if (totalCountByUser.containsKey(name)) {
				Map<String, BigInteger> gettotalCount = totalCountByUser.get(name);

				if (!gettotalCount.containsKey("totalCount")) {
					gettotalCount.put("totalCount", BigInteger.ZERO);
				}

				// get curr count and add it to totalCouunt
				BigInteger total_count_so_far = count.add(gettotalCount.get("totalCount"));

				gettotalCount.put("totalCount", total_count_so_far);
			}

		}

		map.put("totalCountByUser", totalCountByUser);

		return userMap;
	}

	private Map<String, Map<String, Map<String, BigInteger>>> getclericalReportDataFormatted(
			List<JSONObject> clericalReportData, Map<String, Object> map) {

		Map<String, Map<String, Map<String, BigInteger>>> userMap = new LinkedHashMap<>();
		Map<String, Map<String, BigInteger>> totalCountByUser = new HashMap<>();

		for (JSONObject obj : clericalReportData) {

			String date = (String) obj.get("REVIEW_DT");
			String name = (String) obj.get("REVIEW_NAME");
			String flux_match_code = (String) obj.get("FLUX_MATCH_CD");
			BigInteger count = new BigInteger(obj.get("COUNT").toString());

			if (!userMap.containsKey(date)) {

				userMap.put(date, new HashMap<String, Map<String, BigInteger>>());

			}

			Map<String, Map<String, BigInteger>> getUserDetailsMap = userMap.get(date);

			// put the name and match non match

			if (!getUserDetailsMap.containsKey(name)) {
				getUserDetailsMap.put(name, new HashMap<String, BigInteger>());
			}
			if (!totalCountByUser.containsKey(name)) {
				totalCountByUser.put(name, new HashMap<String, BigInteger>());
			}

			Map<String, BigInteger> getUserMatchCodeDetailsMap = getUserDetailsMap.get(name);

			if (flux_match_code.equals("C_M")) {
				getUserMatchCodeDetailsMap.put("Match", count);
			} else {
				getUserMatchCodeDetailsMap.put("NonMatch", count);
			}

			if (totalCountByUser.containsKey(name)) {

				HashMap<String, BigInteger> gettotalCountBYMatchNonMatch = (HashMap<String, BigInteger>) totalCountByUser
						.get(name);
				if (!gettotalCountBYMatchNonMatch.containsKey("Match")) {
					gettotalCountBYMatchNonMatch.put("Match", BigInteger.valueOf(0));
				}
				if (!gettotalCountBYMatchNonMatch.containsKey("NonMatch")) {
					gettotalCountBYMatchNonMatch.put("NonMatch", BigInteger.valueOf(0));
				}

				if (flux_match_code.equals("C_M")) {

					// get curr running count for that user
					BigInteger curr_count_match = getUserMatchCodeDetailsMap.get("Match");

					BigInteger total_so_far_match_count = gettotalCountBYMatchNonMatch.get("Match");
					BigInteger totalMCount = total_so_far_match_count.add(curr_count_match);
					gettotalCountBYMatchNonMatch.put("Match", totalMCount);
				} else {
					BigInteger curr_count_nonmatch = getUserMatchCodeDetailsMap.get("NonMatch");
					BigInteger total_so_far_curr_non_match_count = gettotalCountBYMatchNonMatch.get("NonMatch");
					BigInteger totalNMCount = total_so_far_curr_non_match_count.add(curr_count_nonmatch);
					gettotalCountBYMatchNonMatch.put("NonMatch", totalNMCount);
				}

			}
		}
		map.put("userTotalCountByName", totalCountByUser);

		return userMap;
	}
	
	public List<JSONObject> clericalNonMatchReportData(String fromDate, String toDate,
			BindingResult dummyBindingResult, UserObject userObject){
		
		Map<Object, Object> inputData = new HashMap<Object, Object>();
		inputData.put("p_from_date", fromDate);
		inputData.put("p_to_date", toDate);
		
		return clericalReportDAO.clericalNonMatchReportData(inputData);
		
	}

}
