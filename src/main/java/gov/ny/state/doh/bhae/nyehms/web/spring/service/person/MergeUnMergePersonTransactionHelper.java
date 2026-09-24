package gov.ny.state.doh.bhae.nyehms.web.spring.service.person;

import java.util.HashMap;
import java.util.Map;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.person.FlaggedStatenoVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.enums.AppConstants;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.document.CommonDocumentService;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.review.RecordSearchReviewService;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonExceptionHandler;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
	/*
	 * A helper class to manage all operations which should be executed in a transaction.
	 * The methods are annotated with Spring @Transactional to ensure the transaction.
	 * This class is annotated as @Service in order to load as a Spring managed bean.
	 */
@Service
public class MergeUnMergePersonTransactionHelper {
	/*
	 * Spring dependency injected reference to CommonExceptionHandler
	 */
	@Autowired
	CommonExceptionHandler commonExceptionHandler;
	/*
	 * Spring dependency injected reference to ProviderService
	 */
	@Autowired
	MergeUnMergePersonService mergeUnMergePersonService;
	

	@Autowired
	RecordSearchReviewService recordSearchReviewService;
	

	@Autowired
	CommonDocumentService commonDocumentService;
	

	private static Logger log = LoggerFactory.getLogger(MergeUnMergePersonTransactionHelper.class);

	private String nyehmsLog = AppConstants.nyehmLogDebugString.getName();

	@Transactional
	public void createNewStatenoAndLinkDocuments(String stateno_from, String documentIds, String doccsDocumentList, BindingResult result, 
			UserObject userObject) {
		String stateno = mergeUnMergePersonService.createNewStateno(result, userObject);
		if(stateno!=null){
			mergeUnMergePersonService.unMergeTheDocuments(stateno_from, stateno, documentIds, doccsDocumentList, result,userObject);
		}
	}

	@Transactional
	public void mergeStatenos(FlaggedStatenoVO flaggedStatenoVO,
			BindingResult bindingResult, UserObject userObject) {
		
		
		
		if(CommonUtil.isNotNullAndEmpty(flaggedStatenoVO.getRecSearchActionName())){
			
			String recSearchActionName = flaggedStatenoVO.getRecSearchActionName();
			
			if("unflag".equals(recSearchActionName)){
				
				int resultCount = recordSearchReviewService.changeRecordSearchStatusForMultipleStatenos(flaggedStatenoVO.getRecSearchUnflaggedStateno().split(","), flaggedStatenoVO.getRecSearchComments(), bindingResult, userObject);
				
				
				if(resultCount == 0){
					
					log.error("Failed to unflag the Record Search while merging two statenos");
					
					throw new DataRetrievalFailureException("Failed to unflag the Record Search while merging two statenos");
					
				}
				
			}else if("replace".equals(recSearchActionName)){
				
				recordSearchReviewService.replaceRecordSearchStateno(flaggedStatenoVO.getRecSearchSourceStateno(), flaggedStatenoVO.getRecSearchStatenoToReplace(), flaggedStatenoVO.getRecSearchComments());
				
			}
			
		}
		
		
		Integer statusCode = mergeUnMergePersonService.callMergeStatenosService(flaggedStatenoVO, bindingResult, userObject);
		
//		if(statusCode == 0){
//			mergeUnMergePersonService.commitTransaction();
//		}
		
		
	}

	@Transactional
	public void flagStatenosAndMergeStatenoPairs(
			FlaggedStatenoVO flaggedStatenoVO, BindingResult bindingResult,
			UserObject userObject) {

		flaggedStatenoVO.setOriginCategorieVal("14");

		Integer duplicationId =  mergeUnMergePersonService.flagStatenoPairsAsSame(flaggedStatenoVO, bindingResult, userObject);
		
		if(duplicationId == null){
			throw new DataRetrievalFailureException("Failed to flag the stateno pair ");
		}else{
			flaggedStatenoVO.setDuplicationId(duplicationId);
			
			Integer statusCode = mergeUnMergePersonService.callMergeStatenosService(flaggedStatenoVO, bindingResult, userObject);
			
			if(statusCode != 0){
				throw new DataRetrievalFailureException("Failed to merge the stateno pair ");
			}
			
			
		}
		
	}
	
	
	public void unMergeTheDocuments(String stateno1, String stateno2,
			String documentIds, String doccsDocumentList,
			BindingResult bindingResult, UserObject userObject) {
		
		boolean isError = false; 
		try{
			
			mergeUnMergePersonService.unMergeTheDocuments(stateno1, stateno2, documentIds, doccsDocumentList, bindingResult, userObject);
			
		}catch(DataAccessException dataAccessException){
			isError = true;
			log.error("Failed while unmerging the documents ({}) from statno1({}) to stateno2 ({}) ",documentIds,stateno1,stateno2);
			log.error("Exception : {}",dataAccessException.getMessage());
			
			commonExceptionHandler.handleException(dataAccessException, userObject,userObject.getSessionId());
		}catch(Exception exception){
			isError = true;
			
			log.error("Failed while unmerging the documents ({}) from statno1({}) to stateno2 ({}) ",documentIds,stateno1,stateno2);
			log.error("Exception : {}",exception.getMessage());
			
			commonExceptionHandler.handleException(exception, userObject,userObject.getSessionId());
		}
		
		
		if(isError  && (bindingResult.getAllErrors() == null || bindingResult.getAllErrors().size() == 0) ){
			
			commonExceptionHandler.inspectDBTransacionResult(-1, "Failed while unmerging the documents to stateno, please contact helpline", bindingResult);

		}
	}

	@Transactional
	public void rejectStatenoFlagPair(FlaggedStatenoVO flaggedStatenoVO,
			BindingResult bindingResult, UserObject userObject) {

		
		log.error("Duplication Id : {}    $$$$$$$$$  Reject Category  : {}",flaggedStatenoVO.getDuplicationId(),flaggedStatenoVO.getRejectStatus());
		
		if("D".equals(flaggedStatenoVO.getRejectStatus())){
			
			
			Map<Object, Object> docIdMap = new HashMap<Object, Object>();
			
			docIdMap.put("p_document_type", "ACRF");
			
			docIdMap.put("p_test_type", "RIDR_ACRF");
			
			commonDocumentService.getNewDocId(docIdMap);		

			int newAcrfDocId=(Integer)docIdMap.get("p_new_doc_id");	
			
			if(newAcrfDocId == 0){
				
				log.error("Failed to create a new Document for ACRF.");
				
				throw new DataRetrievalFailureException("Failed to create a new Document for ACRF.");
				
			}
			
			//commonDocumentService.writeDocumentTestType(newAcrfDocId, "RIDR_ACRF");
			
			
			mergeUnMergePersonService.addACRFForRejectedStateno(newAcrfDocId, flaggedStatenoVO.getStateno1(), flaggedStatenoVO.getStateno2(), bindingResult, userObject);
			
			mergeUnMergePersonService.addACRFDuplicateReviewData(newAcrfDocId, flaggedStatenoVO.getStateno2(), bindingResult, userObject);
			
			
			commonDocumentService.moveStdDocumentToDocAttrTables(flaggedStatenoVO.getStateno1(), newAcrfDocId, userObject, "ACRF", "DOC_TO_STATENO");
			
			
			
			
			
			docIdMap.put("p_new_doc_id", 0);
						
			commonDocumentService.getNewDocId(docIdMap);		

			newAcrfDocId=(Integer)docIdMap.get("p_new_doc_id");	
			
			if(newAcrfDocId == 0){
				
				log.error("Failed to create a new Document for ACRF.");
				
				throw new DataRetrievalFailureException("Failed to create a new Document for ACRF.");
				
			}
			
			
			//commonDocumentService.writeDocumentTestType(newAcrfDocId, "RIDR_ACRF");
			
			mergeUnMergePersonService.addACRFForRejectedStateno(newAcrfDocId, flaggedStatenoVO.getStateno2(), flaggedStatenoVO.getStateno1(), bindingResult, userObject);
			
			mergeUnMergePersonService.addACRFDuplicateReviewData(newAcrfDocId, flaggedStatenoVO.getStateno1(), bindingResult, userObject);
			
			commonDocumentService.moveStdDocumentToDocAttrTables(flaggedStatenoVO.getStateno2(), newAcrfDocId, userObject, "ACRF", "DOC_TO_STATENO");
			
			
		}
		
		mergeUnMergePersonService.updateRejectStatusForStatenoFlagPair(flaggedStatenoVO, bindingResult, userObject);
	}
}
