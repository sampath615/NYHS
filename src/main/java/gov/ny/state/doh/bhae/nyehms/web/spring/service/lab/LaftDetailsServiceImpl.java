package gov.ny.state.doh.bhae.nyehms.web.spring.service.lab;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import gov.ny.state.doh.bhae.nyehms.web.spring.dao.jdbc.ConnectionDAO;
import gov.ny.state.doh.bhae.nyehms.web.spring.dao.lab.LaftDetailsDAO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.common.AddressFieldVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.lab.LaftDetailsVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.document.DocumentTransactionHelper;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonExceptionHandler;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonUtil;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.scheduler.LabCommonUtils;
import oracle.jdbc.OracleConnection;
import oracle.sql.ARRAY;
import oracle.sql.ArrayDescriptor;


/**
* This class is service interface between the user data to Database(MyBatis related configuration)
* 
* It renders the data from Database in the form List, Int, Sting etc uinsg MyBatis configuation file 
*  
* @author  
* @version 
*/
@Service
public class LaftDetailsServiceImpl implements LaftDetailsService {

	@Autowired
	private LaftDetailsDAO laftDetailsDAO ;
	
	@Autowired
	private CommonExceptionHandler commonExceptionHandler;
	
	@Autowired
	private LaftDetailsTransactionHelper laftDetailsTransactionHelper;
	

	@Autowired
	private DocumentTransactionHelper documentTransactionHelper;
	
	@Autowired
	private ConnectionDAO connectionDAO ;
	
	private static Logger log = LoggerFactory.getLogger(LaftDetailsServiceImpl.class);
	
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<LaftDetailsVO> getValidationFailedLabDocumentDetails(String documentType, BindingResult bindingResult, UserObject userObject){
		
		Map<Object, Object> inOutParameterMap = new HashMap<Object, Object>();
		
		
		inOutParameterMap.put("rawTableName", LabCommonUtils.getRawTableName(documentType));
		inOutParameterMap.put("documentType", documentType	);
		
		
		if("HIV_LAB".equals(documentType) ){
			return  laftDetailsDAO.getValidationFailedLabDocumentDetails(inOutParameterMap);
		}else{
			return  laftDetailsDAO.getValidationFailedAIRSDocumentDetails(inOutParameterMap);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<LaftDetailsVO> getValidationFailedDeletedLabDocumentDetails(String documentType, BindingResult bindingResult, UserObject userObject){
		
		Map<Object, Object> inOutParameterMap = new HashMap<Object, Object>();
		
		
		inOutParameterMap.put("rawTableName", LabCommonUtils.getRawTableName(documentType));
		inOutParameterMap.put("documentType", documentType	);
		
		
		if("HIV_LAB".equals(documentType) ){
			return  laftDetailsDAO.getValidationFailedDeletedLabDocumentDetails(inOutParameterMap);
		}else{
			return  laftDetailsDAO.getValidationFailedDeletedAIRSDocumentDetails(inOutParameterMap);
		}
	}
	
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<LaftDetailsVO> getHEDStatusDocuments(BindingResult bindingResult, UserObject userObject){
	
		List<LaftDetailsVO> documentErrorsList =  laftDetailsDAO.getHEDStatusDocuments();
		
		return documentErrorsList;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void updateSelectedDocumentStatus(long[] documentIds, String comments, String actionName, String documentType, BindingResult bindingResult, UserObject userObject){
		
		String transactionMessage = null;
		boolean isError = false; 
		try{
			laftDetailsTransactionHelper.updateSelectedDocumentStatus(documentIds, comments, actionName, documentType, bindingResult, userObject);
		}catch(DataAccessException dataAccessException){
			isError = true;
			transactionMessage = dataAccessException.getMessage();
			log.error("Exception occured while updating the document(s) status  {}",transactionMessage);
		}catch(Exception exception){
			isError = true;
			log.error("Exception occured while updating the document(s) status  {}",exception.getMessage());
			transactionMessage = "Failed to update the Document(s) status, Please contact the helpline.";
		}
		
		if(isError){
			commonExceptionHandler.inspectDBTransacionResult(-1, transactionMessage, bindingResult);
		}
	}
	
	@Override
	public void updateProviderAddress(int[] documentIds, AddressFieldVO addressFieldVO, String firstName,
			String lastName, String middleName, String suffix, String phone, String npi, BindingResult bindingResult, UserObject userObject) {
		String transactionMessage = null;
		boolean isError = false; 
		try{
			documentTransactionHelper.updateProviderAddress(documentIds, addressFieldVO, firstName, lastName, middleName, suffix, phone, npi, bindingResult, userObject);
		}catch(DataAccessException dataAccessException){
			isError = true;
			transactionMessage = dataAccessException.getMessage();
			log.error("Exception occured while updating the Provider details for HIV error document(s) {}",transactionMessage);
		}catch(Exception exception){
			isError = true;
			log.error("Exception occured while updating the Provider details for HIV error document(s) {}",exception.getMessage());
			transactionMessage = "Failed to update the Provider details, Please contact the helpline.";
		}
		
		if(isError){
			commonExceptionHandler.inspectDBTransacionResult(-1, transactionMessage, bindingResult);
		}
	}
	
	@Override
	public void updateRawLabAddressDetails(int[] documentIds, AddressFieldVO addressFieldVO,
			BindingResult bindingResult, UserObject userObject) {
		String transactionMessage = null;
		boolean isError = false; 
		try{
			documentTransactionHelper.updateRawLabAddressDetails(documentIds, addressFieldVO, bindingResult, userObject);
		}catch(DataAccessException dataAccessException){
			isError = true;
			transactionMessage = dataAccessException.getMessage();
			log.error("Exception occured while updating the Provider details for HIV error document(s) {}",transactionMessage);
		}catch(Exception exception){
			isError = true;
			log.error("Exception occured while updating the Provider details for HIV error document(s) {}",exception.getMessage());
			transactionMessage = "Failed to update the Provider details, Please contact the helpline.";
		}
		
		if(isError){
			commonExceptionHandler.inspectDBTransacionResult(-1, transactionMessage, bindingResult);
		}
	}
	
	
	public List<Long> getDocumentWithTheSpecificStatusCode(Map<Object, Object> inputOutPutParameterMap){
		return laftDetailsDAO.getDocumentWithTheSpecificStatusCode(inputOutPutParameterMap);
	}
	
	
	@Override
	public void updateDocumentsStatus(List<Long> documentIds, int statusCode, String comments, String actionName, String documentType, BindingResult bindingResult, UserObject userObject){
		
		String transactionMessage=null;
		Integer transactionStatusCode;
		
		Connection connection = connectionDAO.getConnectionObj();
		OracleConnection oracleConnection = null;
			ArrayDescriptor des;
			try {
				if(connection != null){
				
					oracleConnection = (OracleConnection)connection;
					   
					String schemaName = oracleConnection.physicalConnectionWithin().getCurrentSchema();
					
					 
					des = ArrayDescriptor.createDescriptor(schemaName+".NUM_ARR", connection);
					
					//Set<String> aasdfa = documentIdsAndStatus.entrySet();
				    
					
					ARRAY array_to_pass = new ARRAY(des,connection,documentIds.toArray());
					 
					Map<Object, Object> inOutParameterMap = new HashMap<Object, Object>();

					inOutParameterMap.put("p_document_list", array_to_pass);
					inOutParameterMap.put("p_current_status", statusCode);
					inOutParameterMap.put("p_user_id", userObject.getWorkerId());
					inOutParameterMap.put("p_comments", comments);
					inOutParameterMap.put("p_document_type", documentType);

					inOutParameterMap.put("p_code_out", -1);
					
					if("reprise".equals(actionName)){
						transactionMessage = "Successfully Reprised the document(s)";
						laftDetailsDAO.repriseErrorDocuments(inOutParameterMap);
					}else if("doNotReport".equals(actionName)){
						transactionMessage = "Successfully updated the status to Do not Report";
						laftDetailsDAO.updateStatusToDonotReport(inOutParameterMap);
					}else if("delete".equals(actionName)){
						transactionMessage = "Successfully Deleted the document(s)";
						laftDetailsDAO.deleteErrorDocuments(inOutParameterMap);
					}else if("undelete".equals(actionName)){
						transactionMessage = "Successfully Undeleted the document(s)";
						laftDetailsDAO.undeleteErrorDocuments(inOutParameterMap);							
					}else if("hold".equals(actionName)){
						transactionMessage = "Successfully changed the document(s) status to Hold";
						laftDetailsDAO.holdLabDocuments(inOutParameterMap);
					}else if("unhold".equals(actionName)){
						transactionMessage = "Successfully changed the document(s) status to Unhold";
						laftDetailsDAO.unholdLabDocuments(inOutParameterMap);
					}
					else{
						transactionMessage = "Failed to update the Document status, Please contact the helpline.";
					}
					
					transactionStatusCode=(Integer)inOutParameterMap.get("p_code_out");
					transactionMessage=transactionStatusCode==0?transactionMessage:(String)inOutParameterMap.get("p_msg_out");
					if(transactionStatusCode!=0){
						
						log.error("Failed to perfor the {} operation, Document Id : {}",actionName,documentIds.toArray());
						
						throw new DataRetrievalFailureException(transactionMessage);
					}
					if(bindingResult != null) {
						commonExceptionHandler.inspectDBTransacionResult(transactionStatusCode, transactionMessage, bindingResult);
					}
					 
				}
			}catch (SQLException sqlException) {
				
				log.error("Exception while updating the document status {}",sqlException.getMessage());
				
				if(bindingResult != null) {
					commonExceptionHandler.inspectDBTransacionResult(-1, "Failed to update the Document status, Please contact the helpline.", bindingResult);
				}
	
			}catch (Exception exception) {
				
				log.error("Exception while updating the document status {}",exception.getMessage());
				
				if(bindingResult != null) {
					commonExceptionHandler.inspectDBTransacionResult(-1, "Failed to update the Document status, Please contact the helpline.", bindingResult);
				}
	
			}
			finally{
		        try {
		            if (connection != null) {
		            	connection.close();
		            }
		            if(oracleConnection != null){
		            	oracleConnection.close();
		            }
		        } catch (SQLException sqlException) {
		        	log.error("Exception while updating LOINC details of LOINC Code {}",sqlException.getMessage());
		        }
			}
		
		
	}
	
	public void updateLabNotes(long id, String notes,
			BindingResult bindingResult, UserObject userObject){
		
		String transactionMessage = null;
		boolean isError = false; 
		try{
			
			Map<Object, Object> inOutParameterMap = new HashMap<Object, Object>();

			inOutParameterMap.put("p_documentId", id);
			inOutParameterMap.put("p_notes", notes);
			inOutParameterMap.put("p_worker_id", userObject.getWorkerId());
			
			int resultCount = laftDetailsDAO.updateLabNotes(inOutParameterMap);
			
			if(resultCount == 0){
				isError = true;
			}
		}catch(DataAccessException dataAccessException){
			isError = true;
			
			log.error("Exception occured while updating error notes  {}",dataAccessException.getMessage());
		}catch(Exception exception){
			isError = true;
			log.error("Exception occured while updating error notes  {}",exception.getMessage());
		}
		
		if(isError){
			throw new DataRetrievalFailureException("Failed to update the error notes, please contact helpline.");
		}else{
			commonExceptionHandler.inspectDBTransacionResult(0, "Successfully updated the error notes.", bindingResult);
		}
		
	}

	public void addNotesToProcessErrors(long[] documentIds, String notes, BindingResult bindingResult, UserObject userObject){
		
		String transactionMessage = null;
		boolean isError = false; 
		try{
			
			Map<Object, Object> inOutParameterMap = new HashMap<Object, Object>();

			inOutParameterMap.put("p_documentIds", documentIds);
			inOutParameterMap.put("p_notes", notes);
			inOutParameterMap.put("p_worker_id", userObject.getWorkerId());
			
			int resultCount = laftDetailsDAO.addNotesToProcessErrors(inOutParameterMap);
			
			if(resultCount == 0){
				isError = true;
			}
		}catch(DataAccessException dataAccessException){
			isError = true;
			
			log.error("Exception occured while updating error notes  {}",dataAccessException.getMessage());
		}catch(Exception exception){
			isError = true;
			log.error("Exception occured while updating error notes  {}",exception.getMessage());
		}
		
		if(isError){
			throw new DataRetrievalFailureException("Failed to update the error notes, please contact helpline.");
		}else{
			commonExceptionHandler.inspectDBTransacionResult(0, "Successfully updated the error notes.", bindingResult);
		}
		
	}
	
	@Override
	public List<JSONObject> getAddressSameAsEntries(
			BindingResult bindingResult, UserObject userObject){
		try{
			return laftDetailsDAO.getAddressSameAsEntries();
		}catch(Exception exception){
			log.error("Exception occurecd while fetching Address same as Entries  : {}",exception.getMessage());
		}
		return null;
	}
	
	@Override
	public void addRawProviderFacilityAddressSameAsResidence(String rawAddress,
			String addressFilterType, BindingResult bindingResult,
			UserObject userObject){
		boolean isError = false;
		
		try{
			
			Map<Object, Object> inOutParameterMap = new HashMap<Object, Object>();

			inOutParameterMap.put("p_filterType", addressFilterType);
			inOutParameterMap.put("p_address", rawAddress);
			inOutParameterMap.put("p_workerId", userObject.getUserId());
			
			
			int resultCount = laftDetailsDAO.checkCanBeResidenceAddressAlreadyAdded(inOutParameterMap);
			
			if(resultCount == 0){
				resultCount = laftDetailsDAO.addRawProviderFacilityAddressSameAsResidence(inOutParameterMap);
				
				if(resultCount > 0 ){
					commonExceptionHandler.inspectDBTransacionResult(0, "Successfully added the Address as can be residence", bindingResult);
				}else{
					isError = true;
				}
			}else{
				commonExceptionHandler.inspectDBTransacionResult(-1, addressFilterType+" address already added as can be residence",  bindingResult);
			}
			
		}catch(Exception exception){
			isError = true;
			log.error("Exception occurecd while adding the address as can be residence  : {}",exception.getMessage());

		}
		
		if(isError){
			commonExceptionHandler.inspectDBTransacionResult(-1, "Unable to add the Address as can be residence, please contact helpline", bindingResult);
		}
		
		
	}
	
	
	@Override
	public void updateAddressSameAsStatus(String addressId, String fLAG_ACTIVE,
			BindingResult bindingResult, UserObject userObject){
		boolean isError = false;
		
		try{
			
			Map<Object, Object> inOutParameterMap = new HashMap<Object, Object>();

			inOutParameterMap.put("addressId", addressId);
			
			if("N".equals(fLAG_ACTIVE)){
				inOutParameterMap.put("flag_active", fLAG_ACTIVE);
				inOutParameterMap.put("p_workerId", userObject.getUserId());
				
				int resultCount = laftDetailsDAO.updateAddressSameAsStatus(inOutParameterMap);
				
				if(resultCount > 0 ){
					commonExceptionHandler.inspectDBTransacionResult(0, "Successfully updated the Address can be residence status", bindingResult);
				}else{
					isError = true;
				}
			}else{
				commonExceptionHandler.inspectDBTransacionResult(-1, "you can't activate the Address which is already inactivated", bindingResult);
			}
			
			
			
			
		}catch(Exception exception){
			isError = true;
			log.error("Exception occurecd while updating the address can be residence status  : {}",exception.getMessage());

		}
		
		if(isError){
			commonExceptionHandler.inspectDBTransacionResult(-1, "Unable to update the Address can be residence status, please contact helpline", bindingResult);
		}
		
		
	}
	
	@Override
	public String getErrorMessage(int docId){
		return laftDetailsDAO.getErrorMessage(docId);
	}
	
	@Override
	public Map<String, Map<String, Map<String, Integer>>> getLabErrorReportsByType(BindingResult dummyBindingResult,
			UserObject userObject){

		List<Map<String, Object>> labErrorReportsByType = null;
		
		
		Map<String, Map<String, Integer>> softChecks = new HashMap<String, Map<String, Integer>>();
		
		Map<String, Map<String, Integer>> hardChecks = new HashMap<String, Map<String, Integer>>();
		
		Map<String, Map<String, Map<String, Integer>>> errorMap = new LinkedHashMap<String, Map<String, Map<String, Integer>>>();
		
		
		
		try{
			Map<String, String> inOutParameterMap = new HashMap<String, String>();
			
	
			
			labErrorReportsByType = laftDetailsDAO.getLabErrorReportsByType(inOutParameterMap);
			
			
			
			if(labErrorReportsByType == null || labErrorReportsByType.size() == 0){
				
				return null;
			}	
				
			for (Map<String, Object> errorDetails : labErrorReportsByType) {
				
				
				String ERROR_TYPE = (String)errorDetails.get("ERROR_TYPE");
				String ERROR_CATEGORY = (String)errorDetails.get("ERROR_CATEGORY");
				String ERROR_DESC = (String)errorDetails.get("ERROR_DESC");
				int COUNT = ((BigDecimal)errorDetails.get("COUNT")).intValue();
				
				
				
				if("SOFT ERROR".equals(ERROR_TYPE)){
					
					if(softChecks.get(ERROR_CATEGORY) != null){
						
						Map<String, Integer>  softErrors = softChecks.get(ERROR_CATEGORY);
						
						
						softErrors.put(ERROR_DESC, COUNT);
						
						softChecks.put(ERROR_CATEGORY, softErrors);
						
						
					}else{
						
						
						Map<String, Integer>  softErrors = new HashMap<String, Integer>();
						
						
						softErrors.put(ERROR_DESC, COUNT);
						
						softChecks.put(ERROR_CATEGORY, softErrors);
						
						
					}
					
					
					
					
				}else{
					
					if(hardChecks.get(ERROR_CATEGORY) != null){
						
						Map<String, Integer>  hardErrors = hardChecks.get(ERROR_CATEGORY);
						
						
						hardErrors.put(ERROR_DESC, COUNT);
						
						hardChecks.put(ERROR_CATEGORY, hardErrors);
						
						
					}else{
						
						
						Map<String, Integer>  hardErrors = new HashMap<String, Integer>();
						
						
						hardErrors.put(ERROR_DESC, COUNT);
						
						hardChecks.put(ERROR_CATEGORY, hardErrors);
						
						
					}
					
					
				}
				
				
			}
				
				

			
			
			
			errorMap.put("SOFT CHECKS", softChecks);
			errorMap.put("HARD CHECKS", hardChecks);
				
				
			
			
			
			
			
			
			
			
			
			
			
			
			
		}catch(Exception exception){
			
			log.error("Exception occurecd while retreving the Errors report By Type : {}",exception.getMessage());
			
			
			throw new DataRetrievalFailureException("Failed to get the Error Report, please contact helpline.");
			
			
		}
		return errorMap;
	
		
	}

	@Override
	public List<Map<String, Object>> getLabErrorReportsByFacilityName(
			BindingResult dummyBindingResult,
			UserObject userObject){
		
		List<Map<String, Object>> labErrorReportsByFacility = null;
		
		try{
			Map<String, String> inOutParameterMap = new HashMap<String, String>();
			
			
			labErrorReportsByFacility = laftDetailsDAO.getLabErrorReportsByFacilityName(inOutParameterMap);
			
			
		}catch(Exception exception){
			
			log.error("Exception occurecd while retreving the Errors report By Facility : {}",exception.getMessage());
			
			
			throw new DataRetrievalFailureException("Failed to get the Error Report, please contact helpline.");
			
			
		}
		return labErrorReportsByFacility;
	}
	
	@Override
	public List<Map<String, Object>> getLabErrorStatusReportByLab(
			String fromDate, String toDate, BindingResult dummyBindingResult,
			UserObject userObject){
		List<Map<String, Object>> errorStatusReportByLab = null;
		
		try{
			Map<String, String> inOutParameterMap = new HashMap<String, String>();
			
			inOutParameterMap.put("fromDate", fromDate);
			
			inOutParameterMap.put("toDate", toDate);
			
			errorStatusReportByLab = laftDetailsDAO.getLabErrorStatusReportByLab(inOutParameterMap);
			
			
		}catch(Exception exception){
			
			log.error("Exception occurecd while retrieving the Errors status report By Facility : {}",exception.getMessage());
			
			log.error("Date Criteria between From Date ({}) and To Date ({})",fromDate, toDate);
			
			throw new DataRetrievalFailureException("Failed to get the Error Report, please contact helpline.");
			
			
		}
		return errorStatusReportByLab;
	}
	
	@Override
	public Map<String, Map<String, Map<String, Map<String, Integer>>>> getLabErrorReportsByStatus(
			String fromDate, String toDate, Map<String, Object> map, BindingResult dummyBindingResult,
			UserObject userObject){
		
		
		List<Map<String, String>> errorReportsList = null;
		
		Set<String> workerNames = new HashSet<String>();
		
				
		//Week,     Date, 	UserId, data  
		Map<String, Map<String, Map<String, Map<String, Integer>>>> errorReportDataByStatus = new LinkedHashMap<String, Map<String, Map<String, Map<String, Integer>>>>();
		
		
		Map<String, Map<String, Map<String, Integer>>> statusCountByUserByWeek = new HashMap<String, Map<String, Map<String, Integer>>>();
				
		Map<String, Map<String, Integer>> totalStatusCountByUser = new HashMap<String, Map<String, Integer>>();		
		
		
		
		try{
			Map<String, String> inOutParameterMap = new HashMap<String, String>();
			
			inOutParameterMap.put("fromDate", fromDate);
			
			inOutParameterMap.put("toDate", toDate);
			
			errorReportsList = laftDetailsDAO.getLabErrorReportsByStatus(inOutParameterMap);
			
			
			
			
			for (Map<String, String> errorData : errorReportsList) {
				
				String updateId = errorData.get("UPDATEID");
				
				String createDate = errorData.get("CREATE_DATE")+"  ("+errorData.get("DAY_NAME")+")";
				
				//String createDayName = ;
				
				String actionName = errorData.get("REPRISE_DELETE");
				
				Integer count = Integer.parseInt(errorData.get("COUNT"));
				
				String week = errorData.get("YEAR_WEEK");
				
				
				if(CommonUtil.isNotNullAndEmpty(updateId)){
					workerNames.add(updateId);
				}
				
				
				
				

				if(errorReportDataByStatus.get(week) != null){
				
					//Error Report when Week is  null
					
					Map<String, Map<String, Map<String, Integer>>> errorReportsByDate = errorReportDataByStatus.get(week); 
					
					
					
					Map<String, Map<String, Integer>> weeklyStatusCountByUser = statusCountByUserByWeek.get(week);
					
					
					
					
					if(errorReportsByDate.get(createDate) != null){
						
						Map<String, Map<String, Integer>> errorReportStatusByUser = errorReportsByDate.get(createDate);
						
						
						
						if(errorReportStatusByUser.get(updateId) != null){
							
							
							//Start : Error Report by Create Date by User  
							Map<String, Integer> errorReportStatusData = errorReportStatusByUser.get(updateId);
							
							errorReportStatusData.put(actionName, count);
							
							
							errorReportStatusByUser.put(updateId, errorReportStatusData);
							
							//End : Error Report by Create Date by User
							
							
							
							
							//Start : Weekly Error Report by by User
							Map<String, Integer> weeklyStatusCounts = weeklyStatusCountByUser.get(updateId);
							
							
							if(weeklyStatusCounts.get(actionName) != null){
								
								weeklyStatusCounts.put(actionName, weeklyStatusCounts.get(actionName)+count);
								
							}else{
								
								weeklyStatusCounts.put(actionName, count);
								
							}
							
							weeklyStatusCountByUser.put(updateId, weeklyStatusCounts);
							
							statusCountByUserByWeek.put(week, weeklyStatusCountByUser);
							
							//End : Weekly Error Report by by User
							
							
							
							
							//Start : Total Error Report by by User
							Map<String, Integer> totalStatusCount = totalStatusCountByUser.get(updateId);
							
							
							if(totalStatusCount.get(actionName) != null){
								
								totalStatusCount.put(actionName, totalStatusCount.get(actionName)+count);
								
							}else{
								
								totalStatusCount.put(actionName, count);
								
							}
							
							totalStatusCountByUser.put(updateId, totalStatusCount);
							
							//End : Total Error Report by by User
							
							
							
							
							
							
							
							
						}else{
							
							
							//Start : Error Report by Create Date by User
							Map<String, Integer> errorReportStatusData = new HashMap<String, Integer>();
							
							errorReportStatusData.put(actionName, count);
							
							errorReportStatusByUser.put(updateId, errorReportStatusData);
							
							//End : Error Report by Create Date by User
							
							
							
							
							
							
							
							
							
							
							
							
							//Start : Weekly Error Report by by User

							if(weeklyStatusCountByUser.get(updateId) != null){
								
								Map<String, Integer> weeklyStatusCounts = weeklyStatusCountByUser.get(updateId);
								
								
								if(weeklyStatusCounts.get(actionName) != null){
									
									weeklyStatusCounts.put(actionName, weeklyStatusCounts.get(actionName)+count);
									
								}else{
									
									weeklyStatusCounts.put(actionName, count);
									
								}
								
								
								weeklyStatusCountByUser.put(updateId, weeklyStatusCounts);
								
								
								statusCountByUserByWeek.put(week, weeklyStatusCountByUser);
								
								
							}else{
								
								Map<String, Integer> statusCounts = new HashMap<String, Integer>();;
								
								statusCounts.put(actionName, count);
								
								weeklyStatusCountByUser.put(updateId, statusCounts);
								
								
								statusCountByUserByWeek.put(week, weeklyStatusCountByUser);
								
								
							}
							
							//End : Weekly Error Report by by User
							
							
							
							//Start : Total Error Report by by User

							Map<String, Integer> totalStatusCount = null;
							
							if(totalStatusCountByUser.get(updateId) != null){
								totalStatusCount = totalStatusCountByUser.get(updateId);
							}else{
								totalStatusCount = new HashMap<String, Integer>();
							}
							
							
							if(totalStatusCount.get(actionName) != null){
								
								totalStatusCount.put(actionName, totalStatusCount.get(actionName)+count);
								
							}else{
								
								totalStatusCount.put(actionName, count);
								
							}
								
							totalStatusCountByUser.put(updateId, totalStatusCount);
							
							//End : Total Error Report by by User
							
						}
						
						errorReportsByDate.put(createDate, errorReportStatusByUser);
						
						
						
						
						
						
						
						
						
						
						
					}else{
						
						//Start : Error Report by Create Date by User
						Map<String, Map<String, Integer>> errorReportStatusByUser = new HashMap<String, Map<String, Integer>>();
						
						Map<String, Integer> errorReportStatusData = new HashMap<String, Integer>();
						
						errorReportStatusData.put(actionName, count);
						
						errorReportStatusByUser.put(updateId, errorReportStatusData);
						
						
						errorReportsByDate.put(createDate, errorReportStatusByUser);
						//End : Error Report by Create Date by User
						
						
						
						//Start : Weekly Error Report by by User

						if(weeklyStatusCountByUser.get(updateId) != null){
							
							Map<String, Integer> weeklyStatusCounts = weeklyStatusCountByUser.get(updateId);
							
							
							if(weeklyStatusCounts.get(actionName) != null){
								
								weeklyStatusCounts.put(actionName, weeklyStatusCounts.get(actionName)+count);
								
							}else{
								
								weeklyStatusCounts.put(actionName, count);
								
							}
							
							
							weeklyStatusCountByUser.put(updateId, weeklyStatusCounts);
							
							
							statusCountByUserByWeek.put(week, weeklyStatusCountByUser);
							
							
						}else{
							
							Map<String, Integer> statusCounts = new HashMap<String, Integer>();;
							
							statusCounts.put(actionName, count);
							
							weeklyStatusCountByUser.put(updateId, statusCounts);
							
							
							statusCountByUserByWeek.put(week, weeklyStatusCountByUser);
							
							
						}
						//End : Weekly Error Report by by User
						
						
						//Start : Total Error Report by by User
						
						
						Map<String, Integer> totalStatusCount = null;
						
						if(totalStatusCountByUser.get(updateId) != null){
							totalStatusCount = totalStatusCountByUser.get(updateId);
						}else{
							totalStatusCount = new HashMap<String, Integer>();
						}
						
						
						if(totalStatusCount.get(actionName) != null){
							
							totalStatusCount.put(actionName, totalStatusCount.get(actionName)+count);
							
						}else{
							
							totalStatusCount.put(actionName, count);
							
						}
							
						totalStatusCountByUser.put(updateId, totalStatusCount);
						
						//End : Total Error Report by by User
						
						
						
						
						
					}
					
					
					
					
					

					errorReportDataByStatus.put(week, errorReportsByDate);
					
					
					
					
				}else{
					
					//Start : Error Report by Create Date by User
					Map<String, Map<String, Map<String, Integer>>> errorReportsByDate = new LinkedHashMap<String, Map<String, Map<String, Integer>>>();
					
					Map<String, Map<String, Integer>> errorReportStatusByUser = new HashMap<String, Map<String, Integer>>();
					
					Map<String, Integer> errorReportStatusData = new HashMap<String, Integer>();
					
					
					errorReportStatusData.put(actionName, count);
					
					errorReportStatusByUser.put(updateId, errorReportStatusData);
					
					
					errorReportsByDate.put(createDate, errorReportStatusByUser);
					
					errorReportDataByStatus.put(week, errorReportsByDate);
					//End : Error Report by Create Date by User
					
					
					
					
					//Start : Weekly Error Report by by User
					
					
					Map<String, Map<String, Integer>> statusCountByUser = new HashMap<String, Map<String, Integer>>();
					
					Map<String, Integer> statusCounts = new HashMap<String, Integer>();
					
					statusCounts.put(actionName, count);
					
					
					statusCountByUser.put(updateId, statusCounts);
					
					statusCountByUserByWeek.put(week, statusCountByUser);
					//End : Weekly Error Report by by User
					
					
					//Start : Total Error Report by by User
					
					
					
					Map<String, Integer> totalStatusCount = null;
					
					if(totalStatusCountByUser.get(updateId) != null){
						totalStatusCount = totalStatusCountByUser.get(updateId);
					}else{
						totalStatusCount = new HashMap<String, Integer>();
					}
					
					
					if(totalStatusCount.get(actionName) != null){
						
						totalStatusCount.put(actionName, totalStatusCount.get(actionName)+count);
						
					}else{
						
						totalStatusCount.put(actionName, count);
						
					}
						
					totalStatusCountByUser.put(updateId, totalStatusCount);
					//End : Total Error Report by by User
					
					
					
					
				}
				
			}
			map.put("weeklyTotalByUser", statusCountByUserByWeek);
			
			map.put("workerNames", workerNames);
			
			map.put("totalStatusCountByUser", totalStatusCountByUser);
			
			
			
			
		}catch(Exception exception){
			
			log.error("Exception occurecd while retreving the Errors status report By Solution : {}",exception.getMessage());
			
			log.error("Date Criteria between From Date ({}) and To Date ({})",fromDate ,toDate );
			
			throw new DataRetrievalFailureException("Failed to get the Error Report, please contact helpline.");
			
			
		}
		
		
		return errorReportDataByStatus;
		
		
	}
	
	@Override
	public void updateUnitsOfMeasure(int[] documentIds, String units, BindingResult bindingResult,
			UserObject userObject) {
		String transactionMessage = null;
		boolean isError = false; 
		try{
			documentTransactionHelper.updateUnitsOfMeasure(documentIds, units, bindingResult, userObject);
		}catch(DataAccessException dataAccessException){
			isError = true;
			transactionMessage = dataAccessException.getMessage();
			log.error("Exception occured while updating the Units of measurement for HIV error document(s) {}",transactionMessage);
		}catch(Exception exception){
			isError = true;
			log.error("Exception occured while updating the Provider details for HIV error document(s) {}",exception.getMessage());
			transactionMessage = "Failed to update the Units of measurement, Please contact the helpline.";
		}
		
		if(isError){
			commonExceptionHandler.inspectDBTransacionResult(-1, transactionMessage, bindingResult);
		}
	}
	
	@Override
	public List<Map<String, String>> getDocumentLabErrorsByCategory(String documentId, BindingResult dummyBindingResult,
			UserObject userObject){
		return laftDetailsDAO.getDocumentLabErrorsByCategory(documentId); 
	}
	
	@Override
	public void byPassDocumentError(String documentId, String errorCodes, BindingResult bindingResult,
			UserObject userObject) {
		
		
		try{
			
			Map<String, Object> inputParameterMap = new HashMap<String, Object>();
			
			inputParameterMap.put("documentId", documentId);
			inputParameterMap.put("errorCodeList", errorCodes.split(","));
			
			int resultCount = laftDetailsDAO.byPassDocumentError(inputParameterMap);
			
			if(resultCount > 0) {
				commonExceptionHandler.inspectDBTransacionResult(0, "Successfully updated the bypass flag(s)", bindingResult);
			}else {
				commonExceptionHandler.inspectDBTransacionResult(-1, "Unable to update the bypass flag(s), please refersh the page", bindingResult);
			}
			
			
		}catch(Exception exception){
			log.error("Failed to update bypass flags the error codes: {}, documentId: {}", errorCodes, documentId);
			log.error(exception.getMessage());
			commonExceptionHandler.inspectDBTransacionResult(-1, "Failed to update bypass flag(s), please refresh. If the error still persists, please contact helpline", bindingResult);
		}
		
	}
	
}
