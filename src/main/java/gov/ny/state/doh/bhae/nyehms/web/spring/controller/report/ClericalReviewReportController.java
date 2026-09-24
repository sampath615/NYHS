package gov.ny.state.doh.bhae.nyehms.web.spring.controller.report;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author rzp05
 * 
 * This controller will fetch the data for clerical review\clerical death review\scanning reports reviewd by staff 
 * 
 */

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.MapBindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import gov.ny.state.doh.bhae.nyehms.web.ajax.json.GridObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.enums.AppConstants;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.report.ClericalReportService;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.GetGridObject;

@Controller
public class ClericalReviewReportController {

	@Autowired
	private ClericalReportService clericalReportService;
	@Autowired         GetGridObject getGridObject;
	
	@RequestMapping(value = "/getclericalReviewReportbyStaff")
	public String getclericalReviewReportbyStaff(Map<String, Object> map, HttpServletRequest request) {

		HttpSession session = request.getSession();
		UserObject userObject = (UserObject) session.getAttribute("UserObj");
		if (userObject == null)
			userObject = new UserObject();
		
		map.put("reportType", "Clerical Review");

		return "reports/ClericalReviewReport";

	}
	
	
	@RequestMapping(value = "/getclericalDeathReviewReportbyStaff")
	public String getclericalDeathReviewReportbyStaff(Map<String, Object> map, HttpServletRequest request) {

		HttpSession session = request.getSession();
		UserObject userObject = (UserObject) session.getAttribute("UserObj");
		if (userObject == null)
			userObject = new UserObject();
		map.put("reportType", "Clerical Death Review");
		return "reports/ClericalReviewReport";

	}
	
	
	@RequestMapping(value="/clericalNonMatchReport")	
	public String clericalNonMatchReport(HttpServletRequest request, Map<String, Object> map) {
		
		return "reports/clericalNonMatchReport";
	}
	
	@RequestMapping(value="/clericalNonMatchReportData.json", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody GridObject clericalNonMatchReportData(@RequestParam String fromDate, @RequestParam String toDate, HttpServletRequest request, Map<String, Object> map){
		HttpSession session = request.getSession();
		UserObject userObject=(UserObject)session.getAttribute("UserObj");
		Map<Object, Object> dummyMindingMap= new HashMap<Object, Object>();
		BindingResult dummyBindingResult = new MapBindingResult(dummyMindingMap, "Sample");
		
		GridObject jsonResponse = getGridObject.GetGrid( clericalReportService.clericalNonMatchReportData(fromDate, toDate, dummyBindingResult, userObject));
		
		return jsonResponse;
		
	}
	
	/*
	 * @params -> date range (from/to)
	 * getting the data for clerical report by year. 
	 */
	
	
	@RequestMapping(value = "/getclericalReportsByStaffData.html", method = RequestMethod.POST)
	public String getclericalDeathReportsByStaffData(@RequestParam String fromDate, @RequestParam String toDate,
			@RequestParam String reportType, HttpServletRequest request, Map<String, Object> map) {

		HttpSession session = request.getSession();
		UserObject userObject = (UserObject) session.getAttribute("UserObj");

		Map<String, Map<String, Map<String, BigInteger>>> clericalReportsByDailyStaff = clericalReportService
				.getclericalReportByStaff(fromDate, toDate, reportType, userObject, map);

		map.put("clericalReportsByDailyStaff", clericalReportsByDailyStaff);

		return "reports/clericalReportsByDailyStaff";
	}
	
	/*
	 * @params -> date range (from/to)
	 * generating excel sheet clerical report by year. 
	 */
	
	@RequestMapping(value ="/generateClericalReport.xlsx",  method=RequestMethod.GET)
	public ModelAndView generateClericalReport(@RequestParam String fromDate,@RequestParam String toDate, @RequestParam String reportType,
			HttpServletRequest request, HttpServletResponse response, Map<String, Object> map) {
				
		Cookie myCookie = new Cookie("filedownload", "false");		
		response.addCookie(myCookie);		
		boolean isError = false;		
		ModelAndView mav = null;	
		
		HttpSession session = request.getSession();
		UserObject userObject=(UserObject)session.getAttribute("UserObj");
		
		Map<String, Map<String, Map<String, BigInteger>>> clericalReportsByDailyStaff = clericalReportService
				.getclericalReportByStaff(fromDate, toDate, reportType, userObject, map);
		
		if(clericalReportsByDailyStaff != null && clericalReportsByDailyStaff.size() > 0) {
			mav = new ModelAndView("generateClericalReportsByDailyStaff");
			mav.addObject("generateClericalReport", clericalReportsByDailyStaff);
		}else {
			isError = true;
		}
		Cookie myCookie1 = null;
		Cookie myCookie2 = null;
				
				if(!isError) {
					mav.addObject("fromDate", fromDate);
					mav.addObject("toDate", toDate);
					myCookie1 = new Cookie("TRANSACTION_MESSAGE", "Successfully generated the report.");
			        myCookie2 = new Cookie("TRANSACTION_RESULT", AppConstants.NYEHM_TRANSACTION_SUCCESS_CODE.getName());
				}else {
					myCookie1 = new Cookie("TRANSACTION_MESSAGE", "There is no data to generate the report.");
			        myCookie2 = new Cookie("TRANSACTION_RESULT", AppConstants.NYEHM_TRANSACTION_FAILURE_CODE.getName());
			        mav = new ModelAndView("reports/ClericalReviewReport");
				}
				
				 myCookie = new Cookie("filedownload", "true");
			     response.addCookie(myCookie);
			     response.addCookie(myCookie1);
			     response.addCookie(myCookie2);
			        
		return mav;
		
	}
	
	@RequestMapping(value = "/getscanningReportbyStaff")
	public String getscanningReportbyStaff(Map<String, Object> map, HttpServletRequest request) {

		HttpSession session = request.getSession();
		UserObject userObject = (UserObject) session.getAttribute("UserObj");
		if (userObject == null)
			userObject = new UserObject();

		return "reports/ScanningReviewReport";

	}
	
	@RequestMapping(value ="/getscanningReportsByStaffData.html", method = RequestMethod.POST)
	public String getscanningReportsByStaffData(@RequestParam String fromDate, @RequestParam String toDate,
									HttpServletRequest request, Map<String, Object> map) {
		
		HttpSession session = request.getSession();
		UserObject userObject = (UserObject) session.getAttribute("UserObj");
		
		Map<String, Map<String, Map<String, BigInteger>>> scanningReportsByDailyStaff = clericalReportService
				.getscanningReportByStaff(fromDate, toDate, userObject, map);
		
		map.put("scanningReportsByDailyStaff", scanningReportsByDailyStaff);
		
		return "reports/scanningReportsByDailyStaff";
	}
	//create scanning report by staff as an excel file
	@RequestMapping(value="/generateScanningReport.xlsx", method=RequestMethod.GET)
	public ModelAndView generateScanningReport(@RequestParam String fromDate,@RequestParam String toDate,
			HttpServletRequest request, HttpServletResponse response, Map<String, Object> map) {
		Cookie myCookie = new Cookie("filedownload", "false");		
		response.addCookie(myCookie);		
		boolean isError = false;		
		ModelAndView mav = null;	
		
		HttpSession session = request.getSession();
		UserObject userObject=(UserObject)session.getAttribute("UserObj");
		
		Map<String, Map<String, Map<String, BigInteger>>> scanningReportsByDailyStaff = clericalReportService
				.getscanningReportByStaff(fromDate, toDate, userObject, map);
		if(scanningReportsByDailyStaff != null && scanningReportsByDailyStaff.size() > 0) {
			mav = new ModelAndView("generateScanningReportByDailyStaff");
			mav.addObject("generateScanningReport", scanningReportsByDailyStaff);
		}else {
			isError = true;
		}
		Cookie myCookie1 = null;
		Cookie myCookie2 = null;
				
				if(!isError) {
					mav.addObject("fromDate", fromDate);
					mav.addObject("toDate", toDate);
					myCookie1 = new Cookie("TRANSACTION_MESSAGE", "Successfully generated the report.");
			        myCookie2 = new Cookie("TRANSACTION_RESULT", AppConstants.NYEHM_TRANSACTION_SUCCESS_CODE.getName());
				}else {
					myCookie1 = new Cookie("TRANSACTION_MESSAGE", "There is no data to generate the report.");
			        myCookie2 = new Cookie("TRANSACTION_RESULT", AppConstants.NYEHM_TRANSACTION_FAILURE_CODE.getName());
			        mav = new ModelAndView("reports/ScanningReviewReport");
				}
				
				 myCookie = new Cookie("filedownload", "true");
			     response.addCookie(myCookie);
			     response.addCookie(myCookie1);
			     response.addCookie(myCookie2);
			        
		return mav;
		
	}
}
