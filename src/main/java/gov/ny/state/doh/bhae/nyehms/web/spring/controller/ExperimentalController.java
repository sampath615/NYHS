package gov.ny.state.doh.bhae.nyehms.web.spring.controller;

import java.math.BigDecimal;
import java.sql.Clob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.MapBindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import gov.ny.state.doh.bhae.nyehms.web.spring.dao.document.CCDDocumentDAO;
import gov.ny.state.doh.bhae.nyehms.web.spring.enums.AppConstants;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.document.HITCommonDocumentService;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.schedulers.ManualQuartzJobService;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.GetGridObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.scheduler.LabCommonUtils;

@Controller
public class ExperimentalController {

	@Autowired
	ManualQuartzJobService manualQuartzJobService;
	@Autowired
	CCDDocumentDAO ccdDocumentDAO;
	@Autowired
	HITCommonDocumentService hitCommonDocumentService;
	@Autowired
	GetGridObject getGridObject;

	private static Logger log = LoggerFactory.getLogger(ExperimentalController.class);

	@RequestMapping(value = "/experimentHome")
	public String experimentHome(HttpServletRequest request, Map<String, String> map) {
		HttpSession session = request.getSession();
		UserObject userObject = (UserObject) session.getAttribute("UserObj");
		Map<Object, Object> targetMap = new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		// Checking the Permissions for the user
		int statusCode = manualQuartzJobService.getPermissionsAccessForManualQuartzJob(bindingResult, userObject);
		log.error("userObject: {}",userObject);
		log.error("targetMap: {}", targetMap);
		log.error("statusCode: {}",statusCode);
		map.put(AppConstants.NYEHM_TRANSACTION_MESSAGE.getName(),
				(String) targetMap.get(AppConstants.NYEHM_TRANSACTION_MESSAGE.getName()));
		if (AppConstants.NYEHM_TRANSACTION_SUCCESS_CODE.getName()
				.equals(targetMap.get(AppConstants.NYEHM_TRANSACTION_RESULT.getName()))) {
			// the user has permissions
			return "experimental/experimentHome";
		} else {
			// the user does not have the permissions
			log.debug("Redirecting to Error page");
			return "common/errorPage";
		}
	}

	@RequestMapping(value = "/experiment1")
	public String experiment1(HttpServletRequest request, Map<String, String> map) {
		HttpSession session = request.getSession();
		UserObject userObject = (UserObject) session.getAttribute("UserObj");
		Map<Object, Object> targetMap = new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		// Checking the Permissions for the user
		int statusCode = manualQuartzJobService.getPermissionsAccessForManualQuartzJob(bindingResult, userObject);
		map.put(AppConstants.NYEHM_TRANSACTION_MESSAGE.getName(),
				(String) targetMap.get(AppConstants.NYEHM_TRANSACTION_MESSAGE.getName()));
		if (AppConstants.NYEHM_TRANSACTION_SUCCESS_CODE.getName()
				.equals(targetMap.get(AppConstants.NYEHM_TRANSACTION_RESULT.getName()))) {
			// If user has permissions
			return "experimental/experiment1";
		} else {
			// if user is not having the permissions
			log.debug("Redirecting to Error page");
			return "common/errorPage";
		}
	}

	@RequestMapping(value = "/experiment2")
	public String experiment2(HttpServletRequest request, HttpServletResponse response, Map<String, String> map) {
		HttpSession session = request.getSession();
		UserObject userObject = (UserObject) session.getAttribute("UserObj");
		Map<Object, Object> targetMap = new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		// Checking the Permissions for the user
		return ("experimental/experiment2");
	}

	// @RequestMapping(value = "/experiment2")
	// public String experiment2(
	// HttpServletRequest request,
	// Map<String, String> map)
	// BindingResult bindingResult = new MapBindingResult(targetMap,
	// "targetMap");
	// // Checking the Permissions for the user
	// int statusCode =
	// manualQuartzJobService.getPermissionsAccessForManualQuartzJob(bindingResult,
	// userObject);
	// map.put(AppConstants.NYEHM_TRANSACTION_MESSAGE.getName(),
	// (String)
	// // The user has permissions
	// List<Map<String, String>> ccdCounts =
	// // if user is not having the permissions

	/*
	 * @RequestMapping(value = "/hitracecodemapper") public String
	 * hitracecodemapper(HttpServletRequest request, HttpServletResponse
	 * response, Map<String, String> map) { HttpSession session =
	 * request.getSession(); UserObject userObject = (UserObject)
	 * session.getAttribute("UserObj"); Map<Object, Object> targetMap = new
	 * HashMap<Object, Object>(); BindingResult bindingResult = new
	 * MapBindingResult(targetMap, "targetMap"); // Checking the Permissions for
	 * the user return ("experimental/hitracecodemapper"); }
	 */

	@RequestMapping(value = "/hitracecodemapper")
	public String hitracecodemapper(HttpServletRequest request, HttpServletResponse response, Map<String, String> map) {

		HttpSession session = request.getSession();
		UserObject userObject = (UserObject) session.getAttribute("UserObj");
		Map<Object, Object> targetMap = new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		// Checking the Permissions for the user
		int statusCode = manualQuartzJobService.getPermissionsAccessForManualQuartzJob(bindingResult, userObject);

		if (log.isDebugEnabled()) {
			log.debug("userObject: {}", userObject);
			log.debug("targetMap: {}", targetMap);
			log.debug("statusCode: {}", statusCode);
		}

		map.put(AppConstants.NYEHM_TRANSACTION_MESSAGE.getName(),
				(String) targetMap.get(AppConstants.NYEHM_TRANSACTION_MESSAGE.getName()));
		if (statusCode == 0) {
			// If user having permissions
			if (log.isDebugEnabled()) {
				log.debug("Redirecting to scheduler metadata page");
			}
			Map<String, String> hitracecodemap = hitCommonDocumentService.getHitRaceCodeMapperData();
			session.setAttribute("hitracecodemap", hitracecodemap);
			return ("experimental/hitracecodemapper");
		} else {
			// if user is not having the permissions
			if (log.isDebugEnabled()) {
				log.debug("Redirecting to Error page");
			}
			return "common/errorPage";
		}
	}

	@RequestMapping(value = "/hiterrorreprise")
	public String hiterrorreprise(HttpServletRequest request, HttpServletResponse response, Map<String, String> map) {

		HttpSession session = request.getSession();
		UserObject userObject = (UserObject) session.getAttribute("UserObj");
		Map<Object, Object> targetMap = new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		// Checking the Permissions for the user
		int statusCode = manualQuartzJobService.getPermissionsAccessForManualQuartzJob(bindingResult, userObject);

		if (log.isDebugEnabled()) {
			log.debug("userObject: {}",userObject);
			log.debug("targetMap: {}",targetMap);
			log.debug("statusCode: {}",statusCode);
		}

		map.put(AppConstants.NYEHM_TRANSACTION_MESSAGE.getName(),
				(String) targetMap.get(AppConstants.NYEHM_TRANSACTION_MESSAGE.getName()));
		if (statusCode == 0) {
			// If user having permissions
			if (log.isDebugEnabled()) {
				log.debug("Redirecting to scheduler metadata page");
			}
			Map<String, String> hiterrorreprise = null;// hitCommonDocumentService.getHitRaceCodeMapperData();
			session.setAttribute("hiterrorreprise", hiterrorreprise);
			return ("experimental/hiterrorreprise");
		} else {
			// if user is not having the permissions
			if (log.isDebugEnabled()) {
				log.debug("Redirecting to Error page");
			}
			return "common/errorPage";
		}
	}

	@RequestMapping(value = "/getHitRaceValue.json") // , produces ="application/json")
	public @ResponseBody Map<Object, Object> getHitRaceValue(HttpServletRequest request,
			@RequestParam String parameterName) {
		HttpSession session = request.getSession();
		UserObject userObject = (UserObject) session.getAttribute("UserObj");
		Map<Object, Object> targetMap = new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		Integer statusCode = manualQuartzJobService.getPermissionsAccessForManualQuartzJob(bindingResult, userObject);
		if (statusCode == 0) {
			Map<String, String> metadataMap = hitCommonDocumentService.getHitRaceValue(parameterName);
			if (metadataMap != null) {
				targetMap.put(AppConstants.NYEHM_TRANSACTION_RESULT.getName(),
						AppConstants.NYEHM_TRANSACTION_SUCCESS_CODE.getName());
				targetMap.put("parameterValueMap", metadataMap);
			} else {
				targetMap.put(AppConstants.NYEHM_TRANSACTION_RESULT.getName(),
						AppConstants.NYEHM_TRANSACTION_FAILURE_CODE.getName());
				targetMap.put(AppConstants.NYEHM_TRANSACTION_MESSAGE.getName(), "No Value has been assigned to Race Value and Ethnicity");
			}
		} else {
			targetMap.put(AppConstants.NYEHM_TRANSACTION_RESULT.getName(),
					AppConstants.NYEHM_TRANSACTION_FAILURE_CODE.getName());
			targetMap.put(AppConstants.NYEHM_TRANSACTION_MESSAGE.getName(), "You don't have access to view this page.");
		}
		return targetMap;
	}
	
	

	@RequestMapping(value = "/updateHitRaceValue.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Map<Object, Object> updateHitRaceValue(HttpServletRequest request, Map<String, Object> map,
			@RequestParam String parameterName, @RequestParam("parameterValue1[]")  String[] parameterValue1,//@RequestParam String parameterValue1,
			@RequestParam String parameterValue2) {
		String UserId = new String();
		HttpSession session = request.getSession();
		UserObject userObject = (UserObject) session.getAttribute("UserObj");
		if(userObject !=null) {
			UserId = userObject.getUserId();
		}
		Map<Object, Object> targetMap = new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		// Check the user's permissions
		int statusCode = manualQuartzJobService.getPermissionsAccessForManualQuartzJob(bindingResult, userObject);
		if (statusCode == 0) { // the user has permission
			if (log.isDebugEnabled()) {
				log.debug("updating the Hit race data value");
			}
			hitCommonDocumentService.updateHitRaceValue(parameterName, parameterValue1, parameterValue2, bindingResult, UserId);
		}
		return targetMap;
	}

	@RequestMapping(value = "/getHitErrorRepriseValue.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody List<Map<Object, Object>> getHitErrorRepriseValue(@RequestParam String documentType,
			HttpServletRequest request) {
		return hitCommonDocumentService.getHitErrorRepriseValue(documentType);
	}
	
	@RequestMapping(value="/updateHitDocumentsStatus.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Map<Object, Object> updateHitDocumentsStatus(HttpServletRequest request, @RequestParam("documentIds[]")  long[] documentIds, @RequestParam String documentType, @RequestParam String actionName, @RequestParam(value="notes", required = false) String notes){
		
		HttpSession session = request.getSession();
		UserObject userObject=(UserObject)session.getAttribute("UserObj");

		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		hitCommonDocumentService.updateHitDocumentsStatus(documentIds, actionName, documentType, bindingResult, userObject);
		
		return targetMap;
	}
	
	@RequestMapping(value="/updateHitErrorComments.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody  Map<Object, Object> updateHitErrorComments(HttpServletRequest request, @RequestParam long id, @RequestParam String NOTES){	
		System.out.println("Code is working here");
		HttpSession session = request.getSession();
		UserObject userObject=(UserObject)session.getAttribute("UserObj");
		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");		
		hitCommonDocumentService.updateHitErrorComments(id, NOTES, bindingResult, userObject);		
		return targetMap;
	}

	public String getCCDIndex(List<Map<String, Object>> ccdCounts) {

		StringBuilder sb = new StringBuilder();
		String[] columnHeadings = { "DOCUMENT_ID", "UPHN_TRANX_IN_ID", "ADDR", "PROV", "PAYER", "LABS", "ENCOUNTER",
				"MED", "DIAG", "LAB_RES", "PROB" };
		sb.append("<table border='1'>");
		// sb.append("<tr>\n<th>DOCUMENT_ID</th>");
		sb.append("<tr>\n");
		for (int i = 0; i < columnHeadings.length; i++) {
			sb.append("<th>" + columnHeadings[i] + "</th>\n");
		}
		sb.append("</th>\n");
		for (Map<String, Object> row : ccdCounts) {
			// log.error(row.keySet());
			BigDecimal documentNumber = (BigDecimal) row.get("DOCUMENT_ID");
			String documentId = documentNumber.toPlainString();
			BigDecimal uphn = (BigDecimal) row.get("UPHN_TRANX_IN_ID");
			String uphnId = uphn.toPlainString();
			sb.append("<tr>\n");
			// sb.append("<td><a href=\"getCcdTable.html?docId="+ documentId
			// +"&table=RAW_CCD_DOC"+"\">"+ "</a></td>\n");
			sb.append("<td><a href=\"getCcdTable.html?docId=" + documentId + "&table=" + columnHeadings[0] + "\">"
					+ ((BigDecimal) row.get(columnHeadings[0])).toPlainString() + "</a></td>\n");
			for (int i = 1; i < columnHeadings.length; i++) {
				if (columnHeadings[i].equals("UPHN_TRANX_IN_ID")) {
					sb.append("<td><a href=\"getCcdTable.html?docId=" + uphnId + "&table=" + columnHeadings[i] + "\">"
							+ ((BigDecimal) row.get(columnHeadings[i])).toPlainString() + "</a></td>\n");
				} else {
					sb.append("<td><a href=\"getCcdTable.html?docId=" + documentId + "&table=" + columnHeadings[i]
							+ "\">" + ((BigDecimal) row.get(columnHeadings[i])).toPlainString() + "</a></td>\n");
				}
			}
		}
		sb.append("</tr></table>");
		// log.error("CCD Index Table: \n" + sb.toString());
		return sb.toString();
	}

	@RequestMapping(value = "/getCcdTable.html")
	public @ResponseBody String getCcdTableRows(HttpServletRequest request, Map<String, String> map) throws Exception {
		log.error("Made it to /getCcdTable.html");
		String docId = request.getParameter("docId");
		String table = request.getParameter("table");
		HttpSession session = request.getSession();
		UserObject userObject = (UserObject) session.getAttribute("UserObj");
		Map<Object, Object> targetMap = new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		// Checking the Permissions for the user
		int statusCode = manualQuartzJobService.getPermissionsAccessForManualQuartzJob(bindingResult, userObject);
		map.put(AppConstants.NYEHM_TRANSACTION_MESSAGE.getName(),
				(String) targetMap.get(AppConstants.NYEHM_TRANSACTION_MESSAGE.getName()));
		if (docId == null) {
			List<Map<String, Object>> ccdCounts = ccdDocumentDAO.getAllCcdCountIndex();
			return getCCDIndex(ccdCounts);
		} else {
			String table_long = "";
			String param = "";
			// String Query="select * from " +table + " where CCD_DOC_ID="+
			// docId;
			if (table.equals("ADDR")) {
				table_long = "RAW_CCD_PERSON_ADDRESS";
				param = "ccd_doc_id";
			} else if (table.equals("DOCUMENT_ID")) {
				table_long = "RAW_CCD_DOC";
				param = "document_id";
			} else if (table.equals("UPHN_TRANX_IN_ID")) {
				table_long = "UPHN_TRANX_IN";
				param = "uphn_tranx_in_id";
			} else if (table.equals("PROV")) {
				table_long = "RAW_CCD_PROVIDER";
				param = "ccd_doc_id";
			} else if (table.equals("PAYER")) {
				table_long = "RAW_CCD_PAYER";
				param = "ccd_doc_id";
			} else if (table.equals("LABS")) {
				table_long = "RAW_CCD_LAB_TEST";
				param = "ccd_doc_id";
			} else if (table.equals("ENCOUNTER")) {
				table_long = "RAW_CCD_ENCOUNTER";
				param = "ccd_doc_id";
			} else if (table.equals("MED")) {
				table_long = "RAW_CCD_MED";
				param = "ccd_doc_id";
			} else if (table.equals("DIAG")) {
				table_long = "RAW_CCD_DIAGNOSIS";
				param = "ccd_doc_id";
			} else if (table.equals("LAB_RES")) {
				table_long = "RAW_CCD_LAB_RESULT";
				param = "ccd_doc_id";
			} else if (table.equals("PROB")) {
				table_long = "RAW_CCD_PROBLEM";
				param = "ccd_doc_id";
			}
			List<String> columnList = LabCommonUtils.getCCDDocList(table_long);
			Map<String, Object> inOutParameterMap = new HashMap<String, Object>();
			inOutParameterMap.put("docId", docId);
			inOutParameterMap.put("table", table_long);
			inOutParameterMap.put("parameter", param);
			List<Map<String, Object>> childDataEntry = ccdDocumentDAO.getChildEntryData(inOutParameterMap);
			return makeHTMLTable(childDataEntry, columnList);
		}
	}

	@RequestMapping(value = "/experiment3")
	public String experiment3(HttpServletRequest request, Map<String, String> map) {
		HttpSession session = request.getSession();
		UserObject userObject = (UserObject) session.getAttribute("UserObj");
		Map<Object, Object> targetMap = new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		// Checking the Permissions for the user
		int statusCode = manualQuartzJobService.getPermissionsAccessForManualQuartzJob(bindingResult, userObject);
		map.put(AppConstants.NYEHM_TRANSACTION_MESSAGE.getName(),
				(String) targetMap.get(AppConstants.NYEHM_TRANSACTION_MESSAGE.getName()));
		if (AppConstants.NYEHM_TRANSACTION_SUCCESS_CODE.getName()
				.equals(targetMap.get(AppConstants.NYEHM_TRANSACTION_RESULT.getName()))) {
			// If user has permissions
			return "experimental/experiment3";
		} else {
			// if user is not having the permissions
			log.debug("Redirecting to Error page");
			return "common/errorPage";
		}
	}

	/**
	 * This method is used to load the Quartz scheduler manual page.
	 * 
	 * Before loading, the page will check the permissions for the logged-in
	 * user. If the user has the appropriate permissions then he/she will be
	 * redirected to jsp/scheduler/manualQuartzSchedulerJob.jsp. If the user
	 * does not have permissions then he/she will be redirected to the Error
	 * page
	 * 
	 * @param request
	 *            the HttpServletRequest object
	 * @param map
	 *            A map that stores transaction information
	 * @return the jsp path of the returned page.
	 */
	@RequestMapping(value = "/simplestManualQuartzJob")
	public String simplestManualQuartzJob(HttpServletRequest request, Map<String, String> map) {

		HttpSession session = request.getSession();
		UserObject userObject = (UserObject) session.getAttribute("UserObj");
		Map<Object, Object> targetMap = new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		// Check the user's permissions
		manualQuartzJobService.getPermissionsAccessForManualQuartzJob(bindingResult, userObject);
		map.put(AppConstants.NYEHM_TRANSACTION_MESSAGE.getName(),
				(String) targetMap.get(AppConstants.NYEHM_TRANSACTION_MESSAGE.getName()));
		if (AppConstants.NYEHM_TRANSACTION_SUCCESS_CODE.getName()
				.equals(targetMap.get(AppConstants.NYEHM_TRANSACTION_RESULT.getName()))) {
			// If user having permissions
			if (log.isDebugEnabled()) {
				log.debug("Redirecting to manual Quartz scheduler page");
			}
			return "experimental/simplestManualQuartzSchedulerJob";
		} else {
			// if user is not having the permissions
			if (log.isDebugEnabled()) {
				log.debug("Redirecting to Error page");
			}
			return "common/errorPage";
		}
	}

	public static String makeHTMLTable(List<Map<String, Object>> rs, List<String> columnList) throws SQLException {
		return makeHTMLTable(rs, columnList, true);
	}

	public static String makeHTMLTable(List<Map<String, Object>> rs, List<String> columnList, boolean showColumnType)
			throws SQLException {

		StringBuffer sb = new StringBuffer();
		String clobSubstring = "";
		sb.append("<table border='2' cellpadding='2' cellspacing='0'>");
		sb.append("<tr>");
		for (String columnName : columnList) {
			String columnNameBroken = columnName.replace("_", "<br>");
			sb.append("<th>").append(columnNameBroken).append("</th>");
		}
		sb.append("</tr>");
		for (Map<String, Object> map : rs) {
			sb.append("<tr>");
			for (String columnName : columnList) {
				sb.append("<td>");
				Object columnValue = map.get(columnName);
				if (columnValue instanceof Clob) {
					if (columnValue != null) {
						if (((Clob) columnValue).getSubString(1, (int) ((Clob) columnValue).length()).length() > 50) {
							clobSubstring = ((Clob) columnValue).getSubString(1, (int) ((Clob) columnValue).length())
									.substring(0, 50).toString();
						} else {
							clobSubstring = ((Clob) columnValue).getSubString(1, (int) ((Clob) columnValue).length());
						}
					}
					columnValue = clobSubstring;
				}
				if (columnValue != null) {
					sb.append(columnValue);
				}
				sb.append("</td>");
			}
			sb.append("</tr>");
		}
		sb.append("</table>");
		return sb.toString();
	}

}
