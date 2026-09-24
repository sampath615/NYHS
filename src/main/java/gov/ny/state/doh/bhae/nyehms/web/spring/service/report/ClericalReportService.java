package gov.ny.state.doh.bhae.nyehms.web.spring.service.report;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.springframework.validation.BindingResult;

import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;

public interface ClericalReportService {

	Map<String, Map<String, Map<String, BigInteger>>> getclericalReportByStaff(String fromDate, String toDate, String reportType, UserObject userObject,
			Map<String, Object> map);

	Map<String, Map<String, Map<String, BigInteger>>> getscanningReportByStaff(String fromDate, String toDate,
			UserObject userObject, Map<String, Object> map);

	List<JSONObject> clericalNonMatchReportData(String fromDate, String toDate,
			BindingResult dummyBindingResult, UserObject userObject);

}
