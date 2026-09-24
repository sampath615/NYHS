package gov.ny.state.doh.bhae.nyehms.web.spring.service.review;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import gov.ny.state.doh.bhae.nyehms.web.spring.dao.review.RiaRecordsReviewDAO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.RiaDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.acrf.AcrfRidrVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.ria.RiaFollowupDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.ria.RiaInterventionDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.ria.RiaProviderFollowupVO;
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
public class RiaReviewServiceImpl implements RiaReviewService {

	
	private static Logger log = LoggerFactory.getLogger(RiaReviewServiceImpl.class);
	
	@Autowired
    RiaRecordsReviewDAO riaRecordsReviewDAO;
	
	@Autowired
    RiaTransactionHelper riaTransactionHelper;
	
	@Autowired
	CommonExceptionHandler commonExceptionHandler;

	@Override
	public List<JSONObject> getRiaRecordsForReview(String PARAMETER_NAME, String PARAMETER_VALUE, BindingResult bindingResult, UserObject userObject){
		
		
		Map<String, String> inputParameterMap = new HashMap<String, String>();
		
		inputParameterMap.put("PARAMETER_NAME", PARAMETER_NAME);
		
		inputParameterMap.put("PARAMETER_VALUE", PARAMETER_VALUE);
		
		return riaRecordsReviewDAO.getRiaRecordsForReview(inputParameterMap);
		
	}
	
	@Override
	public RiaDocVO getRiaDoc(int docId, String actionType, BindingResult bindingResult, UserObject userObject, String id) {
		
		RiaDocVO riaDocVO = riaRecordsReviewDAO.getRiaDoc(docId);
		

		if("REVIEW".equals(actionType)) {
			
			Map<String, Object> inputParameterMap = new HashMap<String, Object>();
			inputParameterMap.put("DOCUMENT_ID", docId);
			inputParameterMap.put("WORKER_ID", userObject.getWorkerId());
			inputParameterMap.put("ACTIVITY_TYPE", "VIEWED");
		
			riaRecordsReviewDAO.addRiaActivityType(inputParameterMap);
		}
		
		List<RiaFollowupDocVO> followupDocList = riaRecordsReviewDAO.getRiaFollowUpData(docId);
		
		riaDocVO.setFollowUpDocList(followupDocList);
		
		
		List<RiaProviderFollowupVO> providerfollowupList = riaRecordsReviewDAO.getRiaProviderFollowUpData(docId);
		
		if(providerfollowupList == null || providerfollowupList.size() == 0) {
			
			providerfollowupList = new ArrayList<>();
			
			providerfollowupList.add(new RiaProviderFollowupVO());
		}
		
		riaDocVO.setRiaProviderFollowupList(providerfollowupList);
		
		String RIDR_INFO = riaDocVO.getRIDR_INFO();
		
		if(CommonUtil.isNotNullAndEmpty(RIDR_INFO)) {
			
			List<AcrfRidrVO> acrfRidrList = new ArrayList<AcrfRidrVO>();
					
			AcrfRidrVO ridrVO = null;
			
			String[] ridrList = RIDR_INFO.split("\\|");
			
			for (String ridrData : ridrList) {
				
				ridrVO = new AcrfRidrVO();
				
				String[] ridrFieldData = ridrData.split(",");
				
				ridrVO.setDUPLICATE_STATUS(ridrFieldData[0]);
				
				ridrVO.setSTATE_CD(ridrFieldData[1]);
				
				ridrVO.setSTATENO(ridrFieldData[2]);
				
				acrfRidrList.add(ridrVO);
				
			}
			
			riaDocVO.setAcrfRidrList(acrfRidrList);
			
			
		}else {
			
			List<AcrfRidrVO> acrfRidrList = new ArrayList<AcrfRidrVO>();
			
			AcrfRidrVO ridrVO = new AcrfRidrVO();
			
			acrfRidrList.add(ridrVO);
			
			riaDocVO.setAcrfRidrList(acrfRidrList);
		}
		
		
		return riaDocVO;
	}
	
	@Override
	public Map<String, List<Map<String, String>>> getRiaFollowUpDropDownData(){
		
		List<Map<String, String>> dropDownList = riaRecordsReviewDAO.getRiaFollowUpDropDownData();
		
		Map<String, List<Map<String, String>>> riaDropDownList = new HashMap<String, List<Map<String, String>>>();
		
		for (Map<String, String> map : dropDownList) {
			
			String REF_COLUMN = map.get("FOLLOW_UP_CD");
			
			List<Map<String, String>> columnDropDownData = riaDropDownList.get(REF_COLUMN);
			
			if(columnDropDownData == null) {
				columnDropDownData = new ArrayList<Map<String, String>>();
			}
			columnDropDownData.add(map);
			riaDropDownList.put(REF_COLUMN, columnDropDownData);
		}
		
		return riaDropDownList;
	}
	
	@Override
	public Map<String, List<Map<String, String>>> getRiaCodedDropDownData(){
		
		List<Map<String, String>> dropDownList = riaRecordsReviewDAO.getRiaCodedDropDownData();
		
		Map<String, List<Map<String, String>>> riaDropDownList = new HashMap<String, List<Map<String, String>>>();
		
		for (Map<String, String> map : dropDownList) {
			
			String REF_COLUMN = map.get("REF_COLUMN");
			
			List<Map<String, String>> columnDropDownData = riaDropDownList.get(REF_COLUMN);
			
			if(columnDropDownData == null) {
				columnDropDownData = new ArrayList<Map<String, String>>();
			}
			columnDropDownData.add(map);
			riaDropDownList.put(REF_COLUMN, columnDropDownData);
		}
		
		return riaDropDownList;
	}
	
	@Override
	public Map<String, Object> getNyehmsDataForRiaDoc(int docId){
		return riaRecordsReviewDAO.getNyehmsDataforRiaDoc(docId);
	}
	
	@Override
	public List<String> getRiaFieldProperties(){
		return riaRecordsReviewDAO.getRiaFieldProperties();
	}
	
	@Override
	public Map<String, String> getRiaFieldColumnProperties(String columnName){
		
		
		List<Map<String, String>> riaColumnPropertyList = riaRecordsReviewDAO.getRiaFieldColumnProperties();
		
		Map<String, String> fieldProperties = new HashMap<String, String>();
		
		for (Map<String, String> map : riaColumnPropertyList) {
			
			fieldProperties.put(map.get("REF_COLUMN"), map.get(columnName));
		}
		
		
		
		return fieldProperties;
	}
	
	@Override
	public void updateRiaDocData(Map<String, String> data, Integer DOCUMENT_ID, List<RiaFollowupDocVO> followUpDocList, List<RiaProviderFollowupVO> provFollowUpList, 
			BindingResult bindingResult, UserObject userObject) {
		
		boolean isError = false;
		String transactionMessage = null;
	   try{
		   
		   
		   riaTransactionHelper.updateRiaDocData(data, DOCUMENT_ID, followUpDocList, provFollowUpList, bindingResult, userObject);
		}catch(DataAccessException dataAccessException){
			isError = true;
			transactionMessage = dataAccessException.getMessage();
			log.error("Exception occured while Updating the RIA document data. ");
			log.error(transactionMessage);
			
			
			
			StringWriter sw = new StringWriter();
			
			PrintWriter pw = new PrintWriter(sw);
			
			dataAccessException.printStackTrace(pw);
			
			String sStackTrace = sw.toString(); // stack trace as a string
			
			log.error(sStackTrace);
			
			commonExceptionHandler.handleException(dataAccessException, userObject,userObject.getSessionId());
			
		}catch(Exception exception){
			isError = true;
			transactionMessage = exception.getMessage();
			log.error("Exception occured while Updating the RIA document data. ");
			log.error(transactionMessage);
			
			StringWriter sw = new StringWriter();
			
			PrintWriter pw = new PrintWriter(sw);
			
			exception.printStackTrace(pw);
			
			String sStackTrace = sw.toString(); // stack trace as a string
			
			log.error(sStackTrace);
			
			commonExceptionHandler.handleException(exception, userObject,userObject.getSessionId());
			
			
		}
	   
	   if(isError) {
	   commonExceptionHandler.inspectDBTransacionResult(-1,
			   transactionMessage, bindingResult);
	   }
	 
		
		
	}
	
	@Override
	public int updateRiaDocDataToDB(Map<String, String> data, Integer DOCUMENT_ID, BindingResult bindingResult, UserObject userObject) {
			
		   
		StringBuilder updateQuery = new StringBuilder("update nyehms_ria_doc set LAST_UPDATED_BY = '");
		
		updateQuery.append(userObject.getWorkerId());
		
		updateQuery.append("', LAST_UPDATED_DATE =  SYSDATE");
		
		Map<String, String> fieldProperties = getRiaFieldColumnProperties("COLUMN_TYPE");
		
		for (String columnName : data.keySet()) {
			
			if(columnName.startsWith("followUpDocList")) {
				continue;
			}
			
			String columnType = fieldProperties.get(columnName);
			
			if(CommonUtil.isNotNullAndEmpty(columnType)) {
				
				
				if("Date".equals(columnType)) {
					updateQuery.append(",").append(columnName).append("=").append("TO_DATE('").append(data.get(columnName)).append("', 'MM/DD/YYYY')");
				}else {
					String fieldValue = data.get(columnName);
					if(CommonUtil.isNotNullAndEmpty(fieldValue)) {
						fieldValue = fieldValue.replaceAll("'", "''");
					}
					updateQuery.append(",").append(columnName).append("=").append("'").append(fieldValue).append("'");
				}
				
			}
			
			
		}
		
		updateQuery.append(" where complete_Date is null and DOCUMENT_ID =").append(DOCUMENT_ID);
		
		Map<String, String> inputParameterMap = new HashMap<String, String>();
		
		inputParameterMap.put("updateQuery", updateQuery.toString());
		
		int resultCount = riaRecordsReviewDAO.saveRiaDoc(inputParameterMap);
		if(resultCount > 0) {
			Map<String, Object> prameterMap = new HashMap<String, Object>();
			prameterMap.put("DOCUMENT_ID", DOCUMENT_ID);
			prameterMap.put("WORKER_ID", userObject.getWorkerId());
			prameterMap.put("ACTIVITY_TYPE", "SAVED");
		
			riaRecordsReviewDAO.addRiaActivityType(prameterMap);
		}
		
		return resultCount;

		
		
		
	}
	
	@Override
	public void closeRiaDoc(RiaDocVO riaDoc, Integer dOCUMENT_ID, BindingResult bindingResult, UserObject userObject) {
			boolean isError = false;
			String transactionMessage = null;
		   try{
			   riaTransactionHelper.closeRiaDoc(riaDoc, bindingResult, userObject);
			}catch(DataAccessException dataAccessException){
				isError = true;
				transactionMessage = dataAccessException.getMessage();
				log.error("Exception occured while Closing the RIA document. ");
				log.error(transactionMessage);
				
				StringWriter sw = new StringWriter();
				
				PrintWriter pw = new PrintWriter(sw);
				
				dataAccessException.printStackTrace(pw);
				
				String sStackTrace = sw.toString(); // stack trace as a string
				
				log.error(sStackTrace);
				
				commonExceptionHandler.handleException(dataAccessException, userObject,userObject.getSessionId());
				
			}catch(Exception exception){
				isError = true;
				transactionMessage = "Failed to mark the RIA document as completed, please contact helpline.";
				log.error("Exception occured while Closing the RIA document. ");
				log.error(transactionMessage);
				log.error(exception.getMessage());
				
				StringWriter sw = new StringWriter();
				
				PrintWriter pw = new PrintWriter(sw);
				
				exception.printStackTrace(pw);
				
				String sStackTrace = sw.toString(); // stack trace as a string
				
				log.error(sStackTrace);
				
				commonExceptionHandler.handleException(exception, userObject,userObject.getSessionId());
			}
		   
		   if(isError) {
		   commonExceptionHandler.inspectDBTransacionResult(-1,
				   transactionMessage, bindingResult);
		   }
		   
		   
		
		
		
	}
	
	@Override
	public int updateRiaDocData(RiaDocVO riaDoc, UserObject userObject) {
		
	
		
		riaDoc.setLAST_UPDATED_BY(userObject.getWorkerId());
		int resultCount =  riaRecordsReviewDAO.closeRiaDoc(riaDoc);
		
		if(resultCount > 0) {
			Map<String, Object> prameterMap = new HashMap<String, Object>();
			prameterMap.put("DOCUMENT_ID", riaDoc.getDOCUMENT_ID());
			prameterMap.put("WORKER_ID", userObject.getWorkerId());
			prameterMap.put("ACTIVITY_TYPE", "COMPLETED");
		
			riaRecordsReviewDAO.addRiaActivityType(prameterMap);
		}
		return resultCount;
	}
	
	
	
	private boolean checkProvFollowUpDataExists(RiaProviderFollowupVO riaProviderFollowupVO) {
		
		if(CommonUtil.isNotNullAndEmpty(riaProviderFollowupVO.getCONTACT_REASON()) || 
				(CommonUtil.isNotNullAndEmpty(riaProviderFollowupVO.getDATE_OF_CONTACT()) && !"../../....".equals(riaProviderFollowupVO.getDATE_OF_CONTACT())) ||
						CommonUtil.isNotNullAndEmpty(riaProviderFollowupVO.getPROVIDER()) ||
						CommonUtil.isNotNullAndEmpty(riaProviderFollowupVO.getPROVIDER_ROLE()) ||
						CommonUtil.isNotNullAndEmpty(riaProviderFollowupVO.getFACILITY()) ||
						CommonUtil.isNotNullAndEmpty(riaProviderFollowupVO.getCONTACT_PERSON()) ||
						CommonUtil.isNotNullAndEmpty(riaProviderFollowupVO.getRIA_MATERIALS_PROVIDED())) {
			return true;
		}else {
			return false;
		}
		
	}
	
	
	@Override
	public void updateRiaProvFollowupData(List<RiaProviderFollowupVO> provFollowUpList, Integer dOCUMENT_ID,
			UserObject userObject) {
		for (RiaProviderFollowupVO riaProviderFollowupVO : provFollowUpList) {
			
			Map<String, Object > parameterMap = new HashMap<String, Object>();
			if(checkProvFollowUpDataExists(riaProviderFollowupVO)) {
				
				parameterMap.put("DATE_OF_CONTACT", riaProviderFollowupVO.getDATE_OF_CONTACT());
				parameterMap.put("CONTACT_REASON", riaProviderFollowupVO.getCONTACT_REASON());
				parameterMap.put("PROVIDER", riaProviderFollowupVO.getPROVIDER());
				parameterMap.put("PROVIDER_ROLE", riaProviderFollowupVO.getPROVIDER_ROLE());
				parameterMap.put("FACILITY", riaProviderFollowupVO.getFACILITY());
				parameterMap.put("CONTACT_PERSON", riaProviderFollowupVO.getCONTACT_PERSON());
				parameterMap.put("RIA_MATERIALS_PROVIDED", riaProviderFollowupVO.getRIA_MATERIALS_PROVIDED());
				parameterMap.put("WORKER_ID", userObject.getWorkerId());
				parameterMap.put("RIA_DOC_ID", dOCUMENT_ID);
				
				
				if(riaProviderFollowupVO.getRIA_PROVIDER_FOLLOW_UP_ID() == null) {
					riaRecordsReviewDAO.insertProvFollowUpData(parameterMap);					
				}else {
					parameterMap.put("RIA_PROVIDER_FOLLOW_UP_ID", riaProviderFollowupVO.getRIA_PROVIDER_FOLLOW_UP_ID());
					
					riaRecordsReviewDAO.updateProvFollowUpData(parameterMap);					
				}
						
			}else if(riaProviderFollowupVO.getRIA_PROVIDER_FOLLOW_UP_ID() != null && !checkProvFollowUpDataExists(riaProviderFollowupVO)){
				parameterMap.put("WORKER_ID", userObject.getWorkerId());
				parameterMap.put("RIA_PROVIDER_FOLLOW_UP_ID", riaProviderFollowupVO.getRIA_PROVIDER_FOLLOW_UP_ID());
				riaRecordsReviewDAO.deleteProvFollowUpData(parameterMap);
			}
		}
	
		
	}
	@Override
	public void updateRiaFollowupData(List<RiaFollowupDocVO> followUpDocList, Integer DOCUMENT_ID, UserObject userObject) {
		for (RiaFollowupDocVO riaFollowupDocVO : followUpDocList) {
			Integer FOLLOW_UP_ID =  riaFollowupDocVO.getRIA_FOLLOW_UP_ID();
			String FOLLOW_UP_CD = riaFollowupDocVO.getFOLLOW_UP_CD();
			String FOLLOW_UP_OTHER_TEXT = riaFollowupDocVO.getFOLLOWUP_OTHER_TEXT();
			
			if(FOLLOW_UP_ID == null && CommonUtil.isNotNullAndEmpty(FOLLOW_UP_CD)) {
				
				//String nextFollowUpId = riaRecordsReviewDAO.getNextFollowUpId();
				
				Map<String, Object > parameterMap = new HashMap<String, Object>();
				
				parameterMap.put("FOLLOW_UP_CD", FOLLOW_UP_CD);
				parameterMap.put("DOCUMENT_ID", DOCUMENT_ID);
				parameterMap.put("FOLLOW_UP_OTHER_TEXT", FOLLOW_UP_OTHER_TEXT);
				parameterMap.put("WORKER_ID", userObject.getWorkerId());
				//inputParameterMap.put("RIA_FOLLOW_UP_ID", nextFollowUpId);
				riaRecordsReviewDAO.insertFollowUpData(parameterMap);
				
				
				
				List<RiaInterventionDocVO> interventionList = riaFollowupDocVO.getInterventionDocList();
				
				if(interventionList != null) {
					List<Map<String, String>> interventionsList = new ArrayList<Map<String, String>>();
					for (RiaInterventionDocVO riaInterventionDocVO : interventionList) {
						if(CommonUtil.isNotNullAndEmpty(riaInterventionDocVO.getINTERVENTION_CD())) {
							Map<String, String> interventions = new HashMap<String, String>();
							interventions.put("CODE", riaInterventionDocVO.getINTERVENTION_CD());
							interventions.put("DATE", CommonUtil.parseDefaultDateValue(riaInterventionDocVO.getINTERVENTION_DATE()));
							interventions.put("OTHER_TEXT", riaInterventionDocVO.getINTERVENTION_OTHER_TEXT());
							
							interventionsList.add(interventions);
						}
					}
					
					if(interventionsList.size() > 0) {
						Map<String, Object> interventionMap = new HashMap<String, Object>();
						interventionMap.put("RIA_FOLLOW_UP_ID", parameterMap.get("RIA_FOLLOW_UP_ID"));
						interventionMap.put("WORKER_ID", userObject.getWorkerId());
						interventionMap.put("interventionsList", interventionsList);
						
						riaRecordsReviewDAO.insertInterventions(interventionMap);
					}
					
				}
				
				
				
				
			}else if(FOLLOW_UP_ID != null) {
				
				List<RiaInterventionDocVO> interventionList = riaFollowupDocVO.getInterventionDocList();
				
				if(interventionList != null) {
					List<String> interventions = new ArrayList<String>();
					for (RiaInterventionDocVO riaInterventionDocVO : interventionList) {
						if(CommonUtil.isNotNullAndEmpty(riaInterventionDocVO.getINTERVENTION_CD())) {
							interventions.add(riaInterventionDocVO.getINTERVENTION_CD());
						}
					}
					
					if(interventions.size() > 0) {
						Map<String, Object> interventionMap = new HashMap<String, Object>();
						interventionMap.put("RIA_FOLLOW_UP_ID", FOLLOW_UP_ID);
						interventionMap.put("WORKER_ID", userObject.getWorkerId());
						interventionMap.put("interventionsList", interventions);
						
						
						List<String> existingInterventions = riaRecordsReviewDAO.getExistingInterventions(interventionMap);
						
						if(existingInterventions.size() > 0) {
							
							for (String interventionCode : existingInterventions) {
								interventions.remove(interventionCode);
							}
							
							
						}
						
						
						List<Map<String, String>> interventionsList = new ArrayList<Map<String, String>>();
						
						for (RiaInterventionDocVO riaInterventionDocVO : interventionList) {
							if(CommonUtil.isNotNullAndEmpty(riaInterventionDocVO.getINTERVENTION_CD()) && interventions.indexOf(riaInterventionDocVO.getINTERVENTION_CD()) > -1) {
								Map<String, String> interventionsMap = new HashMap<String, String>();
								interventionsMap.put("CODE", riaInterventionDocVO.getINTERVENTION_CD());
								interventionsMap.put("DATE", CommonUtil.parseDefaultDateValue(riaInterventionDocVO.getINTERVENTION_DATE()));
								interventionsMap.put("OTHER_TEXT", riaInterventionDocVO.getINTERVENTION_OTHER_TEXT());
								
								interventionsList.add(interventionsMap);
							}
						}
						
						if(interventionsList.size() > 0) {
						
						interventionMap.put("interventionsList", interventionsList);
						riaRecordsReviewDAO.insertInterventions(interventionMap);
						}
					}
					
				}
				
				
				
				
			}
			
		}
	}
	
	@Override
	public Map<String, String> verifyRiaActivity(Integer DOCUMENT_ID, UserObject userObject){
		
		Map<String, Integer> inputParameterMap = new HashMap<String, Integer>();
		
		inputParameterMap.put("DOCUMENT_ID", DOCUMENT_ID);
		inputParameterMap.put("WORKER_ID", userObject.getWorkerId());
		
		List<Map<String, String>> riaActivity = riaRecordsReviewDAO.getRiaAcitity(inputParameterMap);
		
		
		String viewedMessage = "<span class='bold'>Other Users are viewing the Form</span><br/><br/>";
		
		String savedMessage = "";
		
		String closedMessage = "";
		
				
		Map<String, String> activityDetails = null;
		
		if(riaActivity != null && riaActivity.size() > 0) {
		
			StringBuffer activityContent = new StringBuffer("");
			
			for (Map<String, String> activity : riaActivity) {
				
				String ACTIVITY_DESC = activity.get("ACTIVITY_DESC");
				
				if("SAVED".equals(ACTIVITY_DESC)) {
					savedMessage = "<span class='bold'>Other Users saved the form.</span><br/><br/>";
				}else if("COMPLETED".equals(ACTIVITY_DESC)) {
					savedMessage = "<span class='bold'>Other Users completed the form, you can't save the data</span><br/><br/>";
				}
				
				activityContent.append("<span class='bold'>").append(activity.get("WORKER_NAME")).append("</span> ").append(ACTIVITY_DESC).append(" the Form<br/><br/>");
				
			}
			
			
			if(CommonUtil.isNotNullAndEmpty(closedMessage)) {
				activityContent.insert(0, closedMessage);
			}else if(CommonUtil.isNotNullAndEmpty(savedMessage)) {
				activityContent.insert(0, savedMessage);
			}else {
				activityContent.insert(0, viewedMessage);
			}
			
			activityDetails = new HashMap<String, String>();
			
			activityDetails.put("activity", activityContent.toString());
			
			return activityDetails;
			
		}else {
			return null;
		}
		
		
		
		
		
	}
	
	@Override
	public List<Map<String, String>> getRiaDocActivity(Integer dOCUMENT_ID, BindingResult bindingResult,
			UserObject userObject){
		
		return riaRecordsReviewDAO.getRiaDocActivity(dOCUMENT_ID);
	}
	
	@Override
	public Map<String, Object> statenosFlaggedForRia(String stateno1, String stateno2, UserObject userObject){
		
		Map<String, Object> inputParameterMap = new HashMap<String, Object>();
		inputParameterMap.put("stateno1", stateno1);
		inputParameterMap.put("stateno2", stateno2);
		
		int resultCount = riaRecordsReviewDAO.statenosFlaggedForRia(inputParameterMap);
		
		inputParameterMap.put("RESULT_COUNT", resultCount);
		
		return inputParameterMap;
	}
	
	@Override
	public Map<String, String> getRiaFollowUpReport(String riaFlaggedFromDate, String riaFlaggedToDate, BindingResult bindingResult,
			UserObject userObject){
		
		Map<String, Object> inputParameterMap = new HashMap<String, Object>();
		inputParameterMap.put("riaFlaggedFromDate", riaFlaggedFromDate);
		inputParameterMap.put("riaFlaggedToDate", riaFlaggedToDate);
		
		List<Map<String, String>> riaFollowUpReportList = riaRecordsReviewDAO.getRiaFollowUpReport(inputParameterMap);
		
		Map<String, String> riaFollowUpReport = new HashMap<String, String>();
		
		for (Map<String, String> map : riaFollowUpReportList) {
			riaFollowUpReport.put(map.get("PROGRAM_TYPE"), map.get("COUNT"));
		}
		
		return riaFollowUpReport;
		
		
	}
	
	@Override
	public Map<String, String> getRiaTreatmentReport(String riaFlaggedFromDate, String riaFlaggedToDate,
			BindingResult bindingResult, UserObject userObject){
		Map<String, Object> inputParameterMap = new HashMap<String, Object>();
		inputParameterMap.put("riaFlaggedFromDate", riaFlaggedFromDate);
		inputParameterMap.put("riaFlaggedToDate", riaFlaggedToDate);
		
		List<Map<String, String>> riaFollowUpReportList = riaRecordsReviewDAO.getRiaTreatmentReport(inputParameterMap);
		
		Map<String, String> riaTreatmentReport = new HashMap<String, String>();
		
		for (Map<String, String> map : riaFollowUpReportList) {
			riaTreatmentReport.put(map.get("ARV_TREATMENT"), map.get("COUNT"));
		}
		
		return riaTreatmentReport;
	}
	
	
	@Override
	public Map<String, Integer> getRiaEligibilityReport(String riaFlaggedFromDate, String riaFlaggedToDate, String[] personCounty, String reportType, BindingResult bindingResult, UserObject userObject){
		
		Map<String, Object> inputParameterMap = new HashMap<String, Object>();
		inputParameterMap.put("riaFlaggedFromDate", riaFlaggedFromDate);
		inputParameterMap.put("riaFlaggedToDate", riaFlaggedToDate);
		inputParameterMap.put("personCounties", personCounty);
		
		String riaCounty = Arrays.toString(personCounty);
		if(CommonUtil.isNotNullAndEmpty(riaCounty) && riaCounty.indexOf("ALL") != -1) {
			inputParameterMap.put("ALL_COUNTIES", true);
		}else {
			inputParameterMap.put("ALL_COUNTIES", false);
		}
		
		
		Map<String, Integer> riaEligibilityReport = riaRecordsReviewDAO.getRiaEligibilityReport(inputParameterMap);
		
		return riaEligibilityReport;
		
	}
	
	@Override
	public List<JSONObject> getRiaEligibilityRecordsByReport(String riaFlaggedFromDate, String riaFlaggedToDate, String[] personCounty, String pARAMETER_NAME,
			String pARAMETER_VALUE, BindingResult bindingResult, UserObject userObject){
			
		Map<String, Object> inputParameterMap = new HashMap<String, Object>();
		
		inputParameterMap.put("PARAMETER_NAME", pARAMETER_NAME);
		
		inputParameterMap.put("PARAMETER_VALUE", pARAMETER_VALUE);
		
		inputParameterMap.put("riaFlaggedFromDate", riaFlaggedFromDate);
		inputParameterMap.put("riaFlaggedToDate", riaFlaggedToDate);
		inputParameterMap.put("personCounties", personCounty);
		
		String riaCounty = Arrays.toString(personCounty);
		if(CommonUtil.isNotNullAndEmpty(riaCounty) && riaCounty.indexOf("ALL") != -1) {
			inputParameterMap.put("ALL_COUNTIES", true);
		}else {
			inputParameterMap.put("ALL_COUNTIES", false);
		}
		
		return riaRecordsReviewDAO.getRiaEligibilityRecordsByReport(inputParameterMap);
		
	}
	
	@Override
	public List<JSONObject> getRiaReportByFollowup(String riaFlaggedFromDate, String riaFlaggedToDate, String pARAMETER_NAME, String pARAMETER_VALUE,
			BindingResult bindingResult, UserObject userObject)	{
	Map<String, Object> inputParameterMap = new HashMap<String, Object>();
	
	inputParameterMap.put("PARAMETER_NAME", pARAMETER_NAME);
	
	inputParameterMap.put("PARAMETER_VALUE", pARAMETER_VALUE);
	
	inputParameterMap.put("riaFlaggedFromDate", riaFlaggedFromDate);
	inputParameterMap.put("riaFlaggedToDate", riaFlaggedToDate);
	
	return riaRecordsReviewDAO.getRiaReportByFollowup(inputParameterMap);
	
	}
	
	@Override
	public List<JSONObject> getRiaReportByTreatment(String riaFlaggedFromDate, String riaFlaggedToDate, String pARAMETER_NAME, String pARAMETER_VALUE,
			BindingResult bindingResult, UserObject userObject)	{
	Map<String, Object> inputParameterMap = new HashMap<String, Object>();
	
	inputParameterMap.put("PARAMETER_NAME", pARAMETER_NAME);
	
	inputParameterMap.put("PARAMETER_VALUE", pARAMETER_VALUE);
	
	inputParameterMap.put("riaFlaggedFromDate", riaFlaggedFromDate);
	inputParameterMap.put("riaFlaggedToDate", riaFlaggedToDate);
	
	return riaRecordsReviewDAO.getRiaReportByTreatment(inputParameterMap);
	
	}

	@Override
	public List<Map<String, String>> getRiaActivityReportData(String fromDate, String toDate, BindingResult dummyBindingResult,
			UserObject userObject, Object object){
		Map<String, String> inputParameterMap = new HashMap<String, String>();
		inputParameterMap.put("p_fromDate", fromDate);
		inputParameterMap.put("p_toDate", toDate);
		return riaRecordsReviewDAO.getRiaActivityReportData(inputParameterMap);
	}

	@Override
	public List<JSONObject> getRiaNoLinkedtoCareDataReport(String riaFlaggedFromDate, String riaFlaggedToDate,
			BindingResult bindingResult, UserObject userObject) {
		Map<String, Object> inputParameterMap = new HashMap<String, Object>();
		inputParameterMap.put("riaFlaggedFromDate", riaFlaggedFromDate);
		inputParameterMap.put("riaFlaggedToDate", riaFlaggedToDate);

		List<JSONObject> riaLinkedCareDataReport = riaRecordsReviewDAO
				.getRiaNoLinkedtoCareDataReport(inputParameterMap);

		return riaLinkedCareDataReport;
	}
}
