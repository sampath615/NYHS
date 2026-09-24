package gov.ny.state.doh.bhae.nyehms.web.spring.service.lab;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import gov.ny.state.doh.bhae.nyehms.web.spring.constants.CommonConstants;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonExceptionHandler;

/**
 * This class provides helper methods for document functions.
 * 
 * @author vxp06
 */
@Component
public class LaftDetailsTransactionHelper {

	private static Logger log = LoggerFactory.getLogger(LaftDetailsTransactionHelper.class);
	
	@Autowired
	private LaftDetailsService laftDetailsService;

	@Autowired
	private CommonExceptionHandler commonExceptionHandler;
	
	@Transactional
	public void updateSelectedDocumentStatus(long[] documentIds, String comments, String actionName, String documentType, BindingResult bindingResult, UserObject userObject){

		
		boolean status = false;
		
		List<Integer> statusCodes = new ArrayList<Integer>();
		
		if("undelete".equals(actionName)){
			statusCodes.add(CommonConstants.RAW_RECORD_DELETED_PROCESS_STATUS);
		}else if("HIV_LAB".equals(documentType)){
			statusCodes.add(CommonConstants.RAW_RECORD_VALIDATION_ERRORS_PROCESS_STATUS);
			statusCodes.add(CommonConstants.RAW_RECORD_CLIA_PFI_ERROR_PROCESS_STATUS);
			statusCodes.add(CommonConstants.RAW_HIV_MULTISTEP_ERROR_DOCUMENT);
		}else if("PRF".equals(documentType)){
			statusCodes.add(CommonConstants.RAW_RECORD_VALIDATION_ERRORS_PROCESS_STATUS);
		}else{
			statusCodes.add(CommonConstants.AIRS_AGENCY_NOT_IN_REFERNCE_TABLE);
			statusCodes.add(CommonConstants.AIRS_LNAME_NOT_AVAILABE);
			statusCodes.add(CommonConstants.AIRS_TC_ID_NOT_AVAILABLE);
			statusCodes.add(CommonConstants.AIRS_AGENCY_NOT_AVAILABE);
			statusCodes.add(CommonConstants.AIRS_DATE_CREATED_NOT_AVAILABLE);
			statusCodes.add(CommonConstants.AIRS_URN_NOT_AVAILABLE);
		}
		
		if( documentIds != null && documentIds.length > 0){
		
			for (Integer statusCode : statusCodes) {
				
				Map<Object, Object> inputOutPutParameterMap = new HashMap<Object, Object>();
				
				inputOutPutParameterMap.put("statusCode" , statusCode);
				
				inputOutPutParameterMap.put("documentIds" , documentIds);
				
				List<Long> documentIdsWithStatusCodes = laftDetailsService.getDocumentWithTheSpecificStatusCode(inputOutPutParameterMap);
				
				if(documentIdsWithStatusCodes.size() > 0){
					status = true;
					laftDetailsService.updateDocumentsStatus(documentIdsWithStatusCodes, statusCode, comments, actionName, documentType, bindingResult, userObject);
				}
				
			}
		}
		
		if(!status){
			commonExceptionHandler.inspectDBTransacionResult(-1, "Failed to update the Document(s) status, Please contact the helpline.", bindingResult);
		}
	}

	
	
}
