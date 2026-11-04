package gov.ny.state.doh.bhae.nyehms.web.spring.service.review;


import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.RiaDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.ria.RiaFollowupDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.ria.RiaProviderFollowupVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.document.CommonDocumentService;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonExceptionHandler;


@Component
public class RiaTransactionHelper {

	private static Logger log = LoggerFactory.getLogger(RiaTransactionHelper.class);
	//private String nyehmsLog=AppConstants.nyehmLogDebugString.getName();	
	
	@Autowired	CommonExceptionHandler commonExceptionHandler;	
	
	@Autowired RiaReviewService riaReviewService;
	
	@Autowired
	CommonDocumentService commonDocumentService;

	@Transactional
	public void closeRiaDoc(RiaDocVO riaDoc, BindingResult bindingResult, UserObject userObject) {

		riaDoc.setLAST_UPDATED_BY(userObject.getWorkerId());
		
		//String actionName = riaDocData.getACTION_NAME();
		
		int resultCount = riaReviewService.updateRiaDocData(riaDoc, userObject);
		
		if(resultCount == 0) {
			throw new DataRetrievalFailureException("This document is already completed, please refresh the page.");
		}
		
		riaReviewService.updateRiaFollowupData(riaDoc.getFollowUpDocList(), riaDoc.getDOCUMENT_ID(), userObject);
		
		commonDocumentService.moveStdDocumentToDocAttrTables(riaDoc.getCURR_NYEHMS_STATENO(), riaDoc.getDOCUMENT_ID(), userObject, "RIA", null);

		commonExceptionHandler.inspectDBTransacionResult(0,
				"Successfully  closed the RIA document", bindingResult);
		
	}
	
	
	@Transactional
	public void updateRiaDocData(Map<String, String> data, Integer dOCUMENT_ID, List<RiaFollowupDocVO> followUpDocList,
			List<RiaProviderFollowupVO> provFollowUpList, BindingResult bindingResult, UserObject userObject) {

		
		int resultCount = riaReviewService.updateRiaDocDataToDB(data, dOCUMENT_ID, bindingResult, userObject);
		
		if(resultCount == 0) {
			throw new DataRetrievalFailureException("This document is already completed, please refresh the page.");
		}
		
		riaReviewService.updateRiaFollowupData(followUpDocList, dOCUMENT_ID, userObject);
		
		riaReviewService.updateRiaProvFollowupData(provFollowUpList, dOCUMENT_ID, userObject);

		
		commonExceptionHandler.inspectDBTransacionResult(0,
						"Successfully  saved the RIA document", bindingResult);
			
	}
	
}
