package gov.ny.state.doh.bhae.nyehms.web.spring.service.review;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import gov.ny.state.doh.bhae.nyehms.web.spring.dao.review.PSMessageReviewDAO;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonExceptionHandler;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonUtil;


/**
* This class is service interface between the user data to Database(MyBatis related configuration)
* 
* It renders the data from Database in the form List, Int, Sting etc uinsg MyBatis configuation file 
*  
* @author  
* @version 
*/
@Service
public class PSMessageReviewServiceImpl implements PSMessageReviewService {

	
	private static Logger log = LoggerFactory.getLogger(PSMessageReviewServiceImpl.class);
	
	@Autowired
    PSMessageReviewDAO psMessageReviewDAO;
	
	@Autowired
	private CommonExceptionHandler commonExceptionHandler;
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<JSONObject> getPsMessageReviewDocuments(String RESOLVED_STATUS, String PARAMETER_NAME,String MSG_TYPE_NAME, BindingResult bindingResult, UserObject userObject){
		
		Map<String, Object> inputParameterMap = new HashMap<String, Object>();
		
		inputParameterMap.put("RESOLVED_STATUS", RESOLVED_STATUS);
		if(CommonUtil.isNotNullAndEmpty(PARAMETER_NAME)) {
			inputParameterMap.put("PARAMETER_NAME", PARAMETER_NAME);
		}
		if(CommonUtil.isNotNullAndEmpty(MSG_TYPE_NAME)) {
			inputParameterMap.put("MSG_TYPE_NAME", MSG_TYPE_NAME);
		}
		
		inputParameterMap.put("p_module_id", "16");
		inputParameterMap.put("p_worker_id", userObject.getWorkerId());
		
		
		
		List<JSONObject> psMessagesList =  psMessageReviewDAO.getPsMessageReviewDocuments(inputParameterMap);
		
		return psMessagesList;
	}
	
	@Override
	public void updatePsMessageReviewStatus(long triggerDocId, String actionName, String comments, BindingResult bindingResult,
			UserObject userObject) {
		
		Map<Object, Object> inOutParameterMap = new HashMap<Object, Object>();

		inOutParameterMap.put("p_trigger_doc_id", triggerDocId);
		inOutParameterMap.put("p_comments", comments);
		inOutParameterMap.put("p_worker_id", userObject.getWorkerId());
		
		int resultCount = 0;
		
		
		psMessageReviewDAO.updateReviewComments(inOutParameterMap);
		
		if("delete".equals(actionName)) {
			resultCount = psMessageReviewDAO.deletePsMessage(inOutParameterMap);
			
			if(resultCount > 0) {
				commonExceptionHandler.inspectDBTransacionResult(0, "Successfully deleted the Message(s)",  bindingResult);
			}else {
				commonExceptionHandler.inspectDBTransacionResult(-1, "Failed to delete the Message(s), please contact helpline",  bindingResult);
			}
			
		}else if("process".equals(actionName)){
			resultCount = psMessageReviewDAO.processPsMessage(inOutParameterMap);
			
			if(resultCount > 0) {
				commonExceptionHandler.inspectDBTransacionResult(0, "Successfully processed the Message(s)",  bindingResult);
			}else {
				commonExceptionHandler.inspectDBTransacionResult(-1, "Failed to process the Message(s), please contact helpline",  bindingResult);
			}
			
		}else {
			commonExceptionHandler.inspectDBTransacionResult(-1, "Unknown action found, please contact helpline",  bindingResult);
		}
		
		
		
		
	}
	
	@Override
	public List<Map<String, String>> savePSMessageComments(String comments, Integer dOCUMENT_ID, UserObject userObject) {
		
		Map<Object, Object> inOutParameterMap = new HashMap<Object, Object>();

		inOutParameterMap.put("p_documentId", dOCUMENT_ID);
		inOutParameterMap.put("p_comments", comments);
		inOutParameterMap.put("p_workerId", userObject.getWorkerId());
		
		psMessageReviewDAO.savePSMessageComments(inOutParameterMap);
		
		return  psMessageReviewDAO.getPSMessageComments(dOCUMENT_ID);
		
	}
	
	@Override
	public List<Map<String, String>> saveLAFTMessageComments(String comments,String reason, Integer dOCUMENT_ID, UserObject userObject) {
		
		Map<Object, Object> inOutParameterMap = new HashMap<Object, Object>();

		inOutParameterMap.put("p_comments", comments);
		inOutParameterMap.put("p_reason", reason);
		inOutParameterMap.put("p_documentId", dOCUMENT_ID);
		inOutParameterMap.put("p_workerId", userObject.getWorkerId());
		
		psMessageReviewDAO.saveLAFTMessageComments(inOutParameterMap);
		
		return  psMessageReviewDAO.getPSMessageComments(dOCUMENT_ID);
		
	}
	
	@Override
	public String getPsMessageReport(String fromDate, String toDate, String reportType,
			BindingResult dummyBindingResult, UserObject userObject, ModelAndView mav){
		
		Configuration configuration = null;
		
		StringWriter responseContent = new StringWriter();
		try {
		List<String> countyNames= psMessageReviewDAO.getCountyCodes();
		
		JSONObject psMessageReport = new JSONObject();
		
		Map<Object, Object> inputParameterMap = new HashMap<Object, Object>();

		inputParameterMap.put("p_fromDate", fromDate);
		inputParameterMap.put("p_toDate", toDate);
		inputParameterMap.put("p_reportType", reportType);
		
		List<Map<String, Object>> psMessageReportList = psMessageReviewDAO.getPsMessageReport(inputParameterMap);
		
		
		
		Map<String, Object> countyTotalMap = new LinkedHashMap<String, Object>();
		Map<String, Object> metricTotalMap = new LinkedHashMap<String, Object>();
		Map<String, Map<String, Object>> weekTotalByCountyMap = new LinkedHashMap<String, Map<String, Object>>(); 
		
		for (Map<String, Object> map : psMessageReportList) {
			
			String metric = (String)map.get("METRIC");
			metricTotalMap.put(metric, map.get("METRIC_TOTAL"));
			
			String county = (String)map.get("COUNTY");
			countyTotalMap.put(county, map.get("COUNTY_TOTAL"));
			

			
			Map<String, Object> countyCountByMeteric = null;
			
			if(weekTotalByCountyMap.get(county)!= null){
				
				countyCountByMeteric = weekTotalByCountyMap.get(county);
			}else {
				
				countyCountByMeteric = new LinkedHashMap<String, Object>();

			}
			
			countyCountByMeteric.put(metric, map.get("GROUP_COUNT"));
			
			weekTotalByCountyMap.put(county, countyCountByMeteric);
			
		}
		
		
		configuration = prepareConfiguration();
    	
		configuration.setClassForTemplateLoading(this.getClass(), "/templates/");

		Template template = configuration.getTemplate("psMessageReport.ftl");

        
        
        psMessageReport.put("countyTotalMap", countyTotalMap);
		psMessageReport.put("metricTotalMap", metricTotalMap);
		psMessageReport.put("weekTotalByCountyMap", weekTotalByCountyMap);
		psMessageReport.put("countyList", countyNames);
		
        template.process(psMessageReport, responseContent);
        
        if(mav != null) {
        	mav.addObject("countyTotalMap", countyTotalMap);
        	mav.addObject("metricTotalMap", metricTotalMap);
        	mav.addObject("weekTotalByCountyMap", weekTotalByCountyMap);
        	mav.addObject("countyList", countyNames);
        	mav.addObject("reportType", reportType);
        	mav.addObject("fromDate", fromDate);
        	mav.addObject("toDate", toDate);
        }
        
        
        
		  } catch (IOException | TemplateException e) {
	        	
	        	log.error(e.getMessage());
	            throw new RuntimeException(e);
	        	 
			
			}catch(Exception e) {
				
				String transactionMessage = "Exception occured while creating the lab report by weekly :"+e.getMessage();
				
				log.error(transactionMessage);
				log.error(CommonUtil.getStackTrace(e));
				
				throw new DataRetrievalFailureException(transactionMessage);
				
			}finally {
				//try {
					//workbook.close();
					
//		             if(obj_SFTPChannel != null) {
//							obj_SFTPChannel.disconnect();
//					 }
//		             if(writer != null && !writer.isCloseStream()) {
//		            	 writer.close();
//		             }
//		             if(pdfDocument != null) {
//		            	 pdfDocument.close();
//		             }
//		             if(obj_InputStream != null) {
//		            	 obj_InputStream.close();
//		             }
		             
//				} catch (IOException e) {
//
//					String transactionMessage = "Exception occured while closing the JSCH channel :"+e.getMessage();
//					
//					log.error(transactionMessage);
//					log.error(CommonUtil.getStackTrace(e));
//					
//					throw new DataRetrievalFailureException(transactionMessage);
//
//				}
			}
        
		
		
		return responseContent.toString();
	}
	
	private Configuration prepareConfiguration() {
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_23);
        configuration.setDefaultEncoding("UTF-8");
        configuration.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        configuration.setLogTemplateExceptions(false);
        return configuration;
    }

	@Override
	public List<Map<String, String>> getPSDispositionReport(String fromDate, String toDate, String messageType, String messageCounty,
			String messageDisposition, BindingResult dummyBindingResult, UserObject userObject, Object object) {
		
		
		
		Map<String, Object> inputParameterMap = new HashMap<String, Object>();
		
		if(CommonUtil.isNotNullAndEmpty(messageCounty) && messageCounty.indexOf("ALL") != -1) {
			inputParameterMap.put("ALL_COUNTIES", true);
			inputParameterMap.put("messageCounties", "ALL");
		}else {
			inputParameterMap.put("ALL_COUNTIES", false);
			inputParameterMap.put("messageCounties", messageCounty);
		}
		
		inputParameterMap.put("fromDate", fromDate);
		inputParameterMap.put("toDate", toDate);
		inputParameterMap.put("messageTypes", messageType);
		inputParameterMap.put("messageDispositions", messageDisposition);
		
		psMessageReviewDAO.getPSDispositionReport(inputParameterMap);
		
		return (List<Map<String, String>>)inputParameterMap.get("p_ps_disp_report");
		
	}
	
	@Override
	public List<Map<String, String>> getPSOOCExpsReport(String fromDate, String toDate, String messageCounty,
			BindingResult dummyBindingResult, UserObject userObject, Object object){
		Map<String, Object> inputParameterMap = new HashMap<String, Object>();
		
		if(CommonUtil.isNotNullAndEmpty(messageCounty) && messageCounty.indexOf("ALL") != -1) {
			inputParameterMap.put("ALL_COUNTIES", true);
			inputParameterMap.put("messageCounties", "ALL");
		}else {
			inputParameterMap.put("ALL_COUNTIES", false);
			inputParameterMap.put("messageCounties", messageCounty);
		}
		
		inputParameterMap.put("fromDate", fromDate);
		inputParameterMap.put("toDate", toDate);
		
		psMessageReviewDAO.getPSOOCExpsReport(inputParameterMap);
		
		return (List<Map<String, String>>)inputParameterMap.get("p_ooc_exps_report");
	}
}
