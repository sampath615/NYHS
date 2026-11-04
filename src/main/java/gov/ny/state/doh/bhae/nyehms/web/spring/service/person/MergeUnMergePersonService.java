package gov.ny.state.doh.bhae.nyehms.web.spring.service.person;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import gov.ny.state.doh.bhae.nyehms.web.spring.dao.person.MergeUnMergePersonDAO;
import gov.ny.state.doh.bhae.nyehms.web.spring.dao.person.PersonDAO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.person.BadlyMergedStatenosVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.person.FlaggedStatenoVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.person.PersonRaceVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.person.PersonVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.person.PersonViewVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.document.DocumentTransactionHelper;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonExceptionHandler;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonUtil;

/*
 * Author 	: Sampath Kumar
 * Date 	: 6/23/14
 */

@Service
public class MergeUnMergePersonService {

	private static Logger log = LoggerFactory.getLogger(MergeUnMergePersonService.class);

	@Autowired
	private MergeUnMergePersonDAO mergeUnMergePersonDAO;
	@Autowired
	private PersonDAO personDAO;

	@Autowired
	MergeUnMergePersonTransactionHelper mergeUnMergePersonTransactionHelper;
	
	@Autowired
	DocumentTransactionHelper documentTransactionHelper;
	
	@Autowired
	private CommonExceptionHandler commonExceptionHandler;
	
	@Autowired private PersionService personService;

	private final String FLAG_STATE_NOS_SAME = "STATENOs flagged succesfully";
	private final String FETCH_PERSON_VIEW_OBJECT_RESPONSE_SUCCESS = "Fethced Person view response succesfully";
	private final String FLAG_AS_BAD_MERGE = "STATENO has been flagged as a bad merge and is currently pending review";
	private final String UNFLAG_AS_BAD_MERGE = "Unflagged as Bad Merge";
	private final String UNMERGE_DOCUMENT_SUCCESS = "Moved the document(s) from STATENO ";
	private final String IS_SIMILAR_PAIR_EXISTIS_VAR = "IS_SIMILAR_PAIR_EXISTIS";

	public List<FlaggedStatenoVO> getFlaggedStatenosList(
			BindingResult bindingResult, UserObject userObject) {

		List<FlaggedStatenoVO> flaggedStatenosList = new ArrayList<>();

		flaggedStatenosList = mergeUnMergePersonDAO.getFlaggedStatenosList();

		return flaggedStatenosList;
	}

	public Integer flagStatenoPairsAsSame(FlaggedStatenoVO flaggedStatenoVO,
			BindingResult bindingResult, UserObject userObject) {
		String transactionMessage = null;
		Integer statusCode = null;
		
		
		Integer duplicationId = null;

		Map<Object, Object> inOutParameterMap = new HashMap<>();

		inOutParameterMap.put("p_stateno1", flaggedStatenoVO.getStateno1());
		inOutParameterMap.put("p_stateno2", flaggedStatenoVO.getStateno2());
		inOutParameterMap.put("p_originCategorie", flaggedStatenoVO.getOriginCategorieVal());
		inOutParameterMap.put("p_flagged_by", userObject.getWorkerId());
		inOutParameterMap.put("p_request_comments",
				flaggedStatenoVO.getRequestComments());

		mergeUnMergePersonDAO.flagStatenoPairsAsSame(inOutParameterMap);

		statusCode = (Integer) inOutParameterMap.get("p_code_out");

		transactionMessage = statusCode == 0 ? FLAG_STATE_NOS_SAME
				: (String) inOutParameterMap.get("p_msg_out");

		if (statusCode == 0) {
			Map<Object, Object> resultMap = ((Map<Object, Object>) bindingResult
					.getTarget());

			duplicationId = (Integer)inOutParameterMap.get("p_duplication_id");
			resultMap.put("duplicationId",duplicationId);
		}else {
			log.error(transactionMessage);
		}

		commonExceptionHandler.inspectDBTransacionResult(statusCode,
				transactionMessage, bindingResult);

		return duplicationId;

	}

	public void mergeStatenos(FlaggedStatenoVO flaggedStatenoVO,
			BindingResult bindingResult, UserObject userObject) {
		
		String transactionMessage = null;
		Integer statusCode = null;
		
		try{
			
			mergeUnMergePersonTransactionHelper.mergeStatenos(flaggedStatenoVO,bindingResult,userObject);
			
		}catch(DataAccessException dataAccessException){
			log.error("DataAccessException occured while calling the Match summary stored procedure {}",dataAccessException.getMessage());
			transactionMessage = "Unable to Merge two STATENOs.";
			statusCode = -1;
		}catch(Exception exception){
			log.error("DataAccessException occured while calling the Match summary stored procedure {}",exception.getMessage());
			transactionMessage = "Unable to Merge two STATENOs.";
			statusCode = -1;
		}
		
		
		if(statusCode != null){
			commonExceptionHandler.inspectDBTransacionResult(statusCode,
					transactionMessage, bindingResult);
		}

		

	}
	
	public void updateMergeFollowUp(long id, String followUp,
			BindingResult bindingResult, UserObject userObject){
		int resultCount = 0;
		
		try{
			
			Map<Object, Object> inOutParameterMap = new HashMap<>();

			inOutParameterMap.put("duplicationId", id);
			inOutParameterMap.put("followUp", followUp);
			inOutParameterMap.put("workerId", userObject.getWorkerId());

			
			resultCount =  mergeUnMergePersonDAO.updateMergeFollowUp(inOutParameterMap);
			
		}catch(DataAccessException dataAccessException){
			log.error("{} : Exception occured while updating the Follow up status for the selected pair {}",id,dataAccessException.getMessage());
		}catch(Exception exception){
			log.error("{}  : DataAccessException occured while updating the Follow up status for the selected pair {}",id,exception.getMessage());
		}
		
		if(resultCount > 0){
			commonExceptionHandler.inspectDBTransacionResult(0,
					"Successfully updated the Follow up status for the selected pair", bindingResult);
		}else{
			commonExceptionHandler.inspectDBTransacionResult(-1,
					"Failed to update the Follow up status for the selected pair, Please contact helpline", bindingResult);
			
		}
	}
	
	public Integer callMergeStatenosService(FlaggedStatenoVO flaggedStatenoVO,BindingResult bindingResult, UserObject userObject){
		
		String transactionMessage = null;
		Integer statusCode = null;
		
		Map<Object, Object> inOutParameterMap = new HashMap<>();

		inOutParameterMap.put("p_duplication_id",
				flaggedStatenoVO.getDuplicationId());
		inOutParameterMap.put("p_mergedState_No",
				flaggedStatenoVO.getSelectedStNo());
		
		inOutParameterMap.put("p_worker_id", userObject.getWorkerId());
		inOutParameterMap.put("p_review_comments",
				flaggedStatenoVO.getRequestComments());

		mergeUnMergePersonDAO.mergeStatenos(inOutParameterMap);

		statusCode = (Integer) inOutParameterMap.get("p_code_out");

		StringBuilder successMessage = new StringBuilder("");
		successMessage.append("The STATENO pair of ").append(flaggedStatenoVO.getStateno1()).append(" and ").append(flaggedStatenoVO.getStateno2()).append(" has been merged successfully ");
		
		
		transactionMessage = statusCode == 0 ? successMessage.toString()
				: (String) inOutParameterMap.get("p_msg_out");
		
		
		commonExceptionHandler.inspectDBTransacionResult(statusCode,
				transactionMessage, bindingResult);
		
		if(statusCode == 0){
			
			
			commonExceptionHandler.addBindingResultMessage(bindingResult,IS_SIMILAR_PAIR_EXISTIS_VAR, inOutParameterMap.get("p_similar_pairs_exist"));
			
		}else{
			throw new DataRetrievalFailureException(transactionMessage);
		}
		
	

		
		return statusCode;
	}
	public void commitTransaction(){
		mergeUnMergePersonDAO.commitCurrentTransaction();
	}
	
	public void flagAsBadMerge(FlaggedStatenoVO flaggedStatenoVO,
			BindingResult bindingResult, UserObject userObject) {
		String transactionMessage = null;
		Integer statusCode = null;

		Map<Object, Object> inOutParameterMap = new HashMap<>();

		inOutParameterMap.put("p_stateno", flaggedStatenoVO.getStateno1());
		inOutParameterMap.put("p_flagged_by", userObject.getWorkerId());
		inOutParameterMap.put("p_request_comments",
				flaggedStatenoVO.getRequestComments());

		mergeUnMergePersonDAO.flagBadMerge(inOutParameterMap);

		statusCode = (Integer) inOutParameterMap.get("p_code_out");

		transactionMessage = statusCode == 0 ? FLAG_AS_BAD_MERGE
				: (String) inOutParameterMap.get("p_msg_out");

		if (statusCode == 0) {
			commonExceptionHandler.addBindingResultMessage(bindingResult,
					"unMergeId", inOutParameterMap.get("p_unmerge_id"));
		}
		
		commonExceptionHandler.inspectDBTransacionResult(statusCode,
				transactionMessage, bindingResult);


	}
	public void unflagAsBadMerge(FlaggedStatenoVO flaggedStatenoVO,
			BindingResult bindingResult, UserObject userObject) {
		String transactionMessage = null;
		Integer statusCode = null;

		Map<Object, Object> inOutParameterMap = new HashMap<>();

		inOutParameterMap.put("p_stateno", flaggedStatenoVO.getStateno1());
		inOutParameterMap.put("p_worker_id", userObject.getWorkerId());
		inOutParameterMap.put("p_review_comments",
				flaggedStatenoVO.getRequestComments());

		mergeUnMergePersonDAO.unflagBadMerge(inOutParameterMap);

		statusCode = (Integer) inOutParameterMap.get("p_code_out");

		transactionMessage = statusCode == 0 ? UNFLAG_AS_BAD_MERGE
				: (String) inOutParameterMap.get("p_msg_out");

		if (statusCode == 0) {
			commonExceptionHandler.addBindingResultMessage(bindingResult,
					"unMergeId", inOutParameterMap.get("p_unmerge_id"));
		}

		commonExceptionHandler.inspectDBTransacionResult(statusCode,
				transactionMessage, bindingResult);


	}

	public List<FlaggedStatenoVO> getFlagHistory(
			FlaggedStatenoVO flaggedStatenoVO, BindingResult result,
			UserObject userObject) {

		List<FlaggedStatenoVO> statenosFlagHistoryList = new ArrayList<>();

		Map<Object, Object> inOutParameterMap = new HashMap<>();

		inOutParameterMap.put("p_stateno_1", flaggedStatenoVO.getStateno1());
		inOutParameterMap.put("p_stateno_2", flaggedStatenoVO.getStateno2());

		mergeUnMergePersonDAO.flagHistory(inOutParameterMap);

		statenosFlagHistoryList = (List<FlaggedStatenoVO>) inOutParameterMap
				.get("p_flag_history");

		return statenosFlagHistoryList;
	}
	
	
	public List<FlaggedStatenoVO> getSimilarFlaggedPairs(String duplicationId, BindingResult result,UserObject userObject) {

		List<FlaggedStatenoVO> statenosFlagHistoryList = new ArrayList<>();

		Map<Object, Object> inOutParameterMap = new HashMap<>();

		inOutParameterMap.put("p_duplication_id", duplicationId);
		
		mergeUnMergePersonDAO.getSimilarFlaggedPairs(inOutParameterMap);

		statenosFlagHistoryList = (List<FlaggedStatenoVO>) inOutParameterMap.get("p_similar_pairs_to_review");

		return statenosFlagHistoryList;
	}
	
	
	public List<BadlyMergedStatenosVO> getBadlyMergedStatenos(BindingResult result,UserObject userObject) {

		 List<BadlyMergedStatenosVO> badlyMergedStatenosList = mergeUnMergePersonDAO.getBadlyMergedStatenosList();

		return badlyMergedStatenosList;
	}
	
	
	
	
	public void rejectStatenoFlagPair(FlaggedStatenoVO flaggedStatenoVO,
			BindingResult bindingResult, UserObject userObject) {
		
		boolean isError = false; 
		try{
			mergeUnMergePersonTransactionHelper.rejectStatenoFlagPair(flaggedStatenoVO, bindingResult,userObject);
		}catch(DataAccessException dataAccessException){
			isError = true;
			log.error("Failed to to Reject the stateno pair, Duplication Id : {}",flaggedStatenoVO.getDuplicationId());
			log.error(dataAccessException.getMessage());
		}catch(Exception exception){
			isError = true;
			log.error("Failed to to Reject the stateno pair, Duplication Id : {}",flaggedStatenoVO.getDuplicationId());
			log.error(exception.getMessage());
		}
		
		if(isError || bindingResult.hasErrors()){
			commonExceptionHandler.inspectDBTransacionResult(-1, "Failed to reject the stateno pair, Please contact helpline.", bindingResult);
		}
		
	}
	
	
	

	public void updateRejectStatusForStatenoFlagPair(FlaggedStatenoVO flaggedStatenoVO,
			BindingResult bindingResult, UserObject userObject) {
		String transactionMessage = null;
		Integer statusCode = null;

		Map<Object, Object> inOutParameterMap = new HashMap<>();

		inOutParameterMap.put("p_duplication_id",
				flaggedStatenoVO.getDuplicationId());
		inOutParameterMap.put("p_worker_id", userObject.getWorkerId());
		inOutParameterMap.put("p_review_comments",
				flaggedStatenoVO.getRequestComments());

		mergeUnMergePersonDAO.rejectStatenoFlagPair(inOutParameterMap);

		statusCode = (Integer) inOutParameterMap.get("p_code_out");

		StringBuilder successMessage = new StringBuilder("");
		successMessage.append("The STATENO pair of ").append(flaggedStatenoVO.getStateno1()).append(" and ").append(flaggedStatenoVO.getStateno2()).append(" has been marked \"Not a match\"");
		
		transactionMessage = statusCode == 0 ? successMessage.toString()
				: (String) inOutParameterMap.get("p_msg_out");
		
		if(statusCode == 0){
			commonExceptionHandler.addBindingResultMessage(bindingResult,IS_SIMILAR_PAIR_EXISTIS_VAR, inOutParameterMap.get("p_similar_pairs_exist"));
		}

		commonExceptionHandler.inspectDBTransacionResult(statusCode,
				transactionMessage, bindingResult);

		if (statusCode != 0){
			throw new DataRetrievalFailureException(transactionMessage);
		}

	}

	@SuppressWarnings("unchecked")
	public List<PersonVO> getPersonViewObjects(String duplicationId,
			String stateno1, String stateno2, String startDate,
			BindingResult bindingResult, UserObject userObject) {

		String transactionMessage = null;
		Integer statusCode = null;

		java.util.Map<Object, Object> inOutParameterMap = new java.util.HashMap<>();

		inOutParameterMap.put("p_duplication_id", duplicationId);
		inOutParameterMap.put("p_module_id", "16");
		inOutParameterMap.put("p_access_user_id", userObject.getWorkerId());

		mergeUnMergePersonDAO.fetchPersonObjects(inOutParameterMap);

		List<PersonVO> personViewList = new ArrayList<>();

		PersonVO personVO = null;

		List<PersonViewVO> personViewVOList = null;
		List<PersonRaceVO> personRaceVOList = null;
		List<String> statenoHistoryData = null;
		PersonViewVO personViewVO = null;
		PersonRaceVO personRaceVO = null;
		List<String> eHARSNysStatenoHistoryData = new ArrayList<> ();
		List<String> eHARSNycStatenoHistoryData = new ArrayList<> ();

		String[][] outputCursorsDetails = {
				{ "p_stateno_1_details", "p_stateno_1_race",
						 stateno1, "p_stateno_1_mrg_history" },
				{ "p_stateno_2_details", "p_stateno_2_race",
						 stateno2, "p_stateno_2_mrg_history" } };

		statusCode = (Integer) inOutParameterMap.get("p_code_out");

		transactionMessage = statusCode == 0 ? FETCH_PERSON_VIEW_OBJECT_RESPONSE_SUCCESS
				: (String) inOutParameterMap.get("p_msg_out");

		if (statusCode == 0) {
			for (String[] cursorSet : outputCursorsDetails) {
				personVO = new PersonVO();
				
				
				personViewVOList = ((List<PersonViewVO>) inOutParameterMap
						.get(cursorSet[0]));
				
				
				if (personViewVOList != null && personViewVOList.size() > 0) {
					personViewVO = personViewVOList.get(0);

					if (personViewVO != null) {
					//added @rzp05 7/10/2020
										//getting & setting duplicate statenos in the list
										if(personViewVO.getDUPLICATE_STATENO() != null) {
							
											String duplicateStatenos = personViewVO.getDUPLICATE_STATENO();
							
											String[] duplicateStateNoList = duplicateStatenos.split(",");
							
											personViewVO.setDUPLICATE_STATENO_LIST(Arrays.asList(duplicateStateNoList));
										}
						
						personRaceVOList = ((List<PersonRaceVO>) inOutParameterMap
								.get(cursorSet[1]));
						
						if (personRaceVOList != null
								&& personRaceVOList.size() > 0) {
							
							personRaceVO = personRaceVOList.get(0);
							
							if(personRaceVO == null){
								personRaceVO = new PersonRaceVO();
							}
							personViewVO.setPersonRaceVO(personRaceVO);
						}else{
							personViewVO.setPersonRaceVO(new PersonRaceVO());
						}
						personViewVO.setStateNo(cursorSet[2]);
						personViewVO.setbIndicatedDate(startDate);
						
						String statenoClusterIds = personService.getClusterIds(cursorSet[2], bindingResult, userObject);
						personViewVO.setClusterIds(statenoClusterIds);						
							
						Map<String, Object> statenoDetails = documentTransactionHelper.getStatenoDetailsOfStateno(null, cursorSet[2], "PERSON", userObject);
						
						if(statenoDetails.get("NYC_CITYNO") != null){
							personViewVO.setNYC_CITYNO((String)statenoDetails.get("NYC_CITYNO"));
						}
						
						if(statenoDetails.get("NYC_CITYNO_LIST") != null){
							personViewVO.setNYC_CITYNO_LIST((List<String>)statenoDetails.get("NYC_CITYNO_LIST"));
						}
						if(statenoDetails.get("NYC_EHARS_STATENO") != null){
							personViewVO.setNYC_EHARS_STATENO((String)statenoDetails.get("NYC_EHARS_STATENO"));
						}
						if(statenoDetails.get("NYC_EHARS_STATENO_LIST") != null){
							personViewVO.setNYC_EHARS_STATENO_LIST((List<String>)statenoDetails.get("NYC_EHARS_STATENO_LIST"));
						}
						if(statenoDetails.get("EHARS_STATENO") != null){
							personViewVO.setEHARS_STATENO((String)statenoDetails.get("EHARS_STATENO"));
						}
						if(statenoDetails.get("EHARS_STATENO_LIST") != null){
							personViewVO.setEHARS_STATENO_LIST((List<String>)statenoDetails.get("EHARS_STATENO_LIST"));
						}
						
						personViewVO.setNYS_STAGE_ZERO_DX((String)statenoDetails.get("NYS_STAGE_ZERO_DX"));
						personViewVO.setNYS_STAGE_CD4_OI_DX((String)statenoDetails.get("NYS_STAGE_CD4_OI_DX"));
						personViewVO.setNYC_STAGE_ZERO_DX((String)statenoDetails.get("NYC_STAGE_ZERO_DX"));
						personViewVO.setNYC_STAGE_CD4_OI_DX((String)statenoDetails.get("NYC_STAGE_CD4_OI_DX"));
							
						personVO.setPersonViewVO(personViewVO);
					}

				}

				
				Map<Object,Object> _map = new HashMap<>();
				
				_map.put("p_state_no", cursorSet[2]);
				
				personDAO.getStatenoHistory(_map);
				
				statenoHistoryData =  ((List<String>) _map.get("p_stateno_hist")); 

				if (statenoHistoryData != null && statenoHistoryData.size() > 0) {
					personVO.setStatenoHistory(statenoHistoryData);
				}
				
				
				personViewList.add(personVO);
			}
		}

		


		return personViewList;

	}
	
	
	
	public List<BadlyMergedStatenosVO> getBadMergeFlagHistory(
			String stateno, BindingResult result,
			UserObject userObject) {

		List<BadlyMergedStatenosVO> statenoFlagHistory = new ArrayList<>();

		Map<Object, Object> inOutParameterMap = new HashMap<>();

		inOutParameterMap.put("p_stateno", stateno);
		
		mergeUnMergePersonDAO.getBadMergeFlagHistory(inOutParameterMap);

		statenoFlagHistory = (List<BadlyMergedStatenosVO>) inOutParameterMap
				.get("p_flag_history");

		return statenoFlagHistory;
	}
	
	
	@Transactional
	public void unMergeTheDocuments(String stateno1, String stateno2, String documentIds, String doccsDocumentList, BindingResult bindingResult, UserObject userObject) {
		String transactionMessage = null;
		Integer statusCode = null;

		Map<Object, Object> inOutParameterMap = new HashMap<>();
		
		inOutParameterMap.put("p_document_ids", documentIds);
		inOutParameterMap.put("p_from_stateno", stateno1);
		inOutParameterMap.put("p_to_stateno", stateno2);
		inOutParameterMap.put("p_worker_id", userObject.getWorkerId());
		inOutParameterMap.put("p_doccs_document_list", doccsDocumentList);
		

		mergeUnMergePersonDAO.unMergeTheDocuments(inOutParameterMap);

		statusCode = (Integer) inOutParameterMap.get("p_code_out");

		transactionMessage = statusCode == 0 ? new StringBuilder(UNMERGE_DOCUMENT_SUCCESS).append(stateno1).append(" to STATENO ").append(stateno2).toString()
				: (String) inOutParameterMap.get("p_msg_out");
		
			commonExceptionHandler.inspectDBTransacionResult(statusCode,
				transactionMessage, bindingResult);

		if (statusCode != 0)
			throw new DataRetrievalFailureException(transactionMessage);

	}
	
	public String createNewStateno(BindingResult bindingResult, UserObject userObject) {
		String transactionMessage = null;
		Integer statusCode = null;
		String stateno = null;

		Map<Object, Object> inOutParameterMap = new HashMap<>();
		
		inOutParameterMap.put("p_caller", "Unmerge");
		

		mergeUnMergePersonDAO.createNewStateno(inOutParameterMap);

		stateno = (String) inOutParameterMap.get("p_new_stateno_out");
		
		if(stateno == null){
			commonExceptionHandler.inspectDBTransacionResult(-1,
					"Unable to create a new STATENO", bindingResult);
		}
		
		return stateno;
	}
	
	public void createNewStatenoAndLinkDocuments(String stateno_from, String documentIds, String doccsDocumentList, BindingResult bindingResult, UserObject userObject){
		
		String transactionMessage = null;
		boolean isError = false; 
		try{
			mergeUnMergePersonTransactionHelper.createNewStatenoAndLinkDocuments(stateno_from, documentIds, doccsDocumentList, bindingResult,userObject);
		}catch(DataAccessException dataAccessException){
			isError = true;
			transactionMessage = dataAccessException.getMessage();
			log.error("Failed to create the new STATENO for the documents Selected  {}",transactionMessage);
			log.error("Stateno  : {}  DocumentIds : {}",stateno_from,documentIds);
		}catch(Exception exception){
			isError = true;
			log.error("Failed to create the new STATENO for the documents Selected  {}",exception.getMessage());
			log.error("Stateno  : {}   DocumentIds  : {}",stateno_from,documentIds);
			transactionMessage = "Failed to create the new STATENO for the documents Selected, please contact helpline.";
		}
		
		if(isError){
			commonExceptionHandler.inspectDBTransacionResult(-1, transactionMessage, bindingResult);
		}
	}
	
	public void flagStatenosAndMergeStatenoPairs(
			FlaggedStatenoVO flaggedStatenoVO, BindingResult bindingResult,
			UserObject userObject){
		
		boolean isErroredOut = false;
		
		try{
			
			mergeUnMergePersonTransactionHelper.flagStatenosAndMergeStatenoPairs(flaggedStatenoVO, bindingResult, userObject);		
			
		}catch(DataAccessException dataAccessException){
			
			isErroredOut = true;
			log.error("Merging stateno1 :{}  and stateno2 :{} ",flaggedStatenoVO.getStateno1(),flaggedStatenoVO.getStateno2());
			log.error("Data Access Exception occured while flagging and merging Stateno pair {}",dataAccessException.getMessage());
		}catch(Exception exception){
			isErroredOut = true;
			log.error("Merging stateno1 :{}  and stateno2 : {}",flaggedStatenoVO.getStateno1(),flaggedStatenoVO.getStateno2());
			log.error("Exception occured while flagging and merging Stateno pair {}",exception.getMessage());
		}
		
		if(isErroredOut){
			commonExceptionHandler.inspectDBTransacionResult(-1,
					"Failed to merge the stateno pair, please contant helpline.", bindingResult);
		}
	}
	
	public void addACRFForRejectedStateno(int documentId, String stateno1, String stateno2,
			BindingResult bindingResult, UserObject userObject){
		

		boolean isErroredOut = false;
		
		int resultCount = 0;
		
		try{
			
			Map<String, Object> inputParameter = new HashMap<>();
			
			
			inputParameter.put("DOCUMENT_ID", documentId);
			
			inputParameter.put("ASSOCIATE_STATENO", stateno1);
			
			inputParameter.put("DUPLICATE_STATENO", stateno2);
			
			inputParameter.put("PERSON_COMPLETING", userObject.getUserId());
			
			inputParameter.put("DOCUMENT_SOURCE", "A06.29");
			
			
			resultCount = mergeUnMergePersonDAO.addACRFForRejectedStateno(inputParameter);
			
			
			
		}catch(DataAccessException dataAccessException){
			
			isErroredOut = true;
			
			log.error("Data Access Exception occured while adding ACRF document for the Rejected Stateno pair {}",dataAccessException.getMessage());
		}catch(Exception exception){
			isErroredOut = true;
			log.error("Exception occured while adding ACRF document for the Rejected Stateno pair {}",exception.getMessage());
		}
		
		if(isErroredOut || resultCount == 0){
			
			log.error("Failed while creating ACRF document for the Reject stateno pair");
			log.error("Rejected stateno1 : {}  and stateno2 : {}",stateno1,stateno2);
			throw new DataRetrievalFailureException("Failed while creating ACRF document for the Reject stateno pair");
		}
		
		
	}
	
	
	public void addACRFDuplicateReviewData(int documentId, String stateno2,
			BindingResult bindingResult, UserObject userObject){
		
		
		Map<String, Object> inputParameter = new HashMap<>();
		
		
		inputParameter.put("DOCUMENT_ID", documentId);
		
		inputParameter.put("NYEHMS_STATENO", stateno2);
		
		
		mergeUnMergePersonDAO.addACRFDuplicateReviewData(inputParameter);
		
		
		
	}
	
	public void validateDodAgainstCollectionDate(String stateno,
			String documentIds, String doccsDocumentList,
			BindingResult bindingResult, UserObject userObject){
		
		   Map<String, Object> inputParameterMap = new HashMap<>();
		   
		   
		
			String[] doccsDocumentIds = null;
			
			if(CommonUtil.isNotNullAndEmpty(doccsDocumentList)){
				
				doccsDocumentIds = doccsDocumentList.split(",");
						
			}else{
				doccsDocumentIds = new String[]{"0"};
				
			}
			
			String[] documentIdsToMove = null;
			
			if(CommonUtil.isNotNullAndEmpty(documentIds)){
				
				documentIdsToMove = documentIds.split(",");
						
			}
			
			inputParameterMap.put("doccsDocumentIds", doccsDocumentIds);
			inputParameterMap.put("stateno_target", stateno);
			inputParameterMap.put("unmergeDocuments", documentIdsToMove);
			
			
			try{
			
				String result = mergeUnMergePersonDAO.validateDodAgainstCollectionDate(inputParameterMap);
				
				if("Y".equals(result)){
					
					commonExceptionHandler.inspectDBTransacionResult(-1, "Collection Date is greater than Date of Death.", bindingResult);
				}
				
				
			}catch(DataAccessException dataAccessException){
				log.error("Unable to Validate the DOD Validation against Collection Date :  {}",dataAccessException.getMessage());
				log.error("Stateno  "+ stateno +"   Document Ids need to move  "+documentIds);
				throw new DataRetrievalFailureException("Unable to Validate the DOD Validation against Collection Date");
			}catch(Exception exception){
				log.error("Unable to Validate the DOD Validation against Collection Date : {}",exception.getMessage());
				log.error("Stateno {}   Document Ids need to move  {} ", stateno,documentIds);
				throw new DataRetrievalFailureException("Unable to Validate the DOD Validation against Collection Date");
			}
	}
	
	public void validateDodAgainstCollectionDateBeforeMerge(String stateno1,
			String stateno2, BindingResult bindingResult, UserObject userObject){
		
		   		Map<String, Object> inputParameterMap = new HashMap<>();
		   	
				inputParameterMap.put("stateno1", stateno1);
				inputParameterMap.put("stateno2", stateno2);
				
				try{
				
					String result = mergeUnMergePersonDAO.validateDodAgainstCollectionDateBeforeMerge(inputParameterMap);
					
					if("Y".equals(result)){
						
						commonExceptionHandler.inspectDBTransacionResult(-1, "Collection Date is greater than Date of Death.", bindingResult);
					}
					
					
				}catch(DataAccessException dataAccessException){
					log.error("Unable to Validate the DOD Validation against Collection Date :  {}",dataAccessException.getMessage());
					log.error("STATENO1- {} STATENO2- {} ", stateno1,stateno2);
					throw new DataRetrievalFailureException("Unable to Validate the DOD Validation against Collection Date");
				}catch(Exception exception){
					log.error("Unable to Validate the DOD Validation against Collection Date : {}",exception.getMessage());
					log.error("STATENO1- {} STATENO2- {}", stateno1,stateno2);
					throw new DataRetrievalFailureException("Unable to Validate the DOD Validation against Collection Date");
				}
	}
	
	
	public List<Map<String, Object>> getMergeReportsByOrigin(String fromDate, String toDate, UserObject userObject) {
		List<Map<String, Object>> mergeReportsByOrigin = null;
		
		try{
			Map<String, String> inOutParameterMap = new HashMap<>();	
			inOutParameterMap.put("fromDate", fromDate);			
			inOutParameterMap.put("toDate", toDate);
			mergeReportsByOrigin = mergeUnMergePersonDAO.getMergeReportsByOrigin(inOutParameterMap);	
		} catch(Exception exception){
			log.error("Exception occurecd while retreving the Merge Report By Origin : {}",exception.getMessage());
			throw new DataRetrievalFailureException("Failed to get the Merge Report, please contact helpline.");	
		}
		return mergeReportsByOrigin;
	}
	
	public List<Map<String, Object>> getMergeReportsByBadMerges(String fromDate, String toDate, UserObject userObject) {
		List<Map<String, Object>> mergeReportsByBadMerges = null;
		
		try{
			Map<String, String> inOutParameterMap = new HashMap<>();	
			inOutParameterMap.put("fromDate", fromDate);			
			inOutParameterMap.put("toDate", toDate);
			mergeReportsByBadMerges = mergeUnMergePersonDAO.getMergeReportsByBadMerges(inOutParameterMap);	
		} catch(Exception exception){
			log.error("Exception occurecd while retreving the Merge Report By Bad Merges : {}",exception.getMessage());
			throw new DataRetrievalFailureException("Failed to get the Merge Report, please contact helpline.");	
		}
		return mergeReportsByBadMerges;
	}
	
	public Map<String, Map<String, Map<String, Map<String, Integer>>>> getMergeReportsByDailyStaff(String fromDate, String toDate, Map<String, Object> map,
			UserObject userObject) {
		Map<String, Map<String, Map<String, Map<String, Integer>>>> dataMapByWeeks = new LinkedHashMap<String, Map<String, Map<String, Map<String, Integer>>>>();		
		Map<String, Map<String, Map<String, Integer>>> weeklyCountByWeeks = new HashMap<>();
		Map<String, Map<String, Integer>> totalCountByUsers = new HashMap<>();
		List<String> userNamesList = new ArrayList<>();

		try{
			Map<String, String> inOutParameterMap = new HashMap<>();	
			inOutParameterMap.put("fromDate", fromDate);			
			inOutParameterMap.put("toDate", toDate);
			List<Map<String, Object>> mergeReportsListRaw = mergeUnMergePersonDAO.getMergeReportsByDailyStaff(inOutParameterMap);	
			
			//First fill in all dates with empty rows
			String[] dayOfWeekStrings = {"SUNDAY", "MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY"};
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy_MM_dd");
			Date dateFrom = sdf.parse(fromDate);
			Date dateTo = sdf.parse(toDate);
			Date currentDate = dateFrom;
			Calendar cal = Calendar.getInstance();
			
			while (currentDate.before(dateTo) || currentDate.equals(dateTo)) {
				cal.setTime(currentDate);
				if (cal.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY && cal.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {					
					String currentDateInMap = sdf.format(currentDate) + " (" + dayOfWeekStrings[cal.get(Calendar.DAY_OF_WEEK)-1] + ")";
					Calendar cal2 = (Calendar) cal.clone();
					cal2.add(Calendar.DATE, -(cal.get(Calendar.DAY_OF_WEEK)-2));
					Date mondayOfWeek = cal2.getTime();
					String mondayOfWeekString = sdf2.format(mondayOfWeek);

					if (dataMapByWeeks.get(mondayOfWeekString) != null) {
						dataMapByWeeks.get(mondayOfWeekString).put(currentDateInMap, null);
					} else {
						Map<String, Map<String, Map<String, Integer>>> emptyRowsByDates = new LinkedHashMap<>();
						emptyRowsByDates.put(currentDateInMap, null);
						dataMapByWeeks.put(mondayOfWeekString, emptyRowsByDates);
					}
				}
				cal.add(Calendar.DATE, 1);
				currentDate = cal.getTime();
			}
			
			//Convert the mergeReportsListRaw to mergeReportsByDailyStaff arranged by Week, Date, User, Status, Count		
			for (Map<String, Object> mergeData : mergeReportsListRaw) {
				String weekOf = (String)mergeData.get("WEEKOF");
				String dayName = ((String)mergeData.get("DAY_NAME")).replaceAll("\\s+", "");
				String updatedDate = (String)mergeData.get("UPDATED_DATE") + " (" + dayName + ")";
				String updatedUser = (String)mergeData.get("UPDATED_USER");
				String status = (String)mergeData.get("STATUS");
				Integer count = (Integer)(((BigDecimal)mergeData.get("COUNT")).intValueExact());
				
				//Update user names list
				if (!userNamesList.contains(updatedUser)) {
					userNamesList.add(updatedUser);
				}

				//Get the data map for the week
				Map<String, Map<String, Map<String, Integer>>> dataMapByDates = null;
				if (null == dataMapByWeeks.get(weekOf)) {
					dataMapByDates = new LinkedHashMap<String, Map<String, Map<String, Integer>>>();
				} else {
					dataMapByDates = dataMapByWeeks.get(weekOf);
				}
				
				//Get the data map for the date
				Map<String, Map<String, Integer>> dataMapByUsers = null;
				if (null == dataMapByDates.get(updatedDate)) {
					dataMapByUsers = new HashMap<String, Map<String, Integer>>();
				} else {
					dataMapByUsers = dataMapByDates.get(updatedDate);
				}
				
				//Get the data map for the user
				Map<String, Integer> dataMapByStatus = null;
				if (null == dataMapByUsers.get(updatedUser)) {
					dataMapByStatus = new HashMap<String, Integer>();
				} else {
					dataMapByStatus = dataMapByUsers.get(updatedUser);							
				}
						
				dataMapByStatus.put(status, count);
				dataMapByUsers.put(updatedUser, dataMapByStatus);
				dataMapByDates.put(updatedDate, dataMapByUsers);
				dataMapByWeeks.put(weekOf, dataMapByDates);
				
				//Get the weekly count for users for the week
				Map<String, Map<String, Integer>> weeklyCountByUsers = null;
				if (null == weeklyCountByWeeks.get(weekOf)) {
					weeklyCountByUsers = new HashMap<String, Map<String, Integer>>();
				} else {
					weeklyCountByUsers = weeklyCountByWeeks.get(weekOf);
				}
				
				//Get the weekly count for user
				Map<String, Integer> weeklyCountByStatus = null;
				if (null == weeklyCountByUsers.get(updatedUser)) {
					weeklyCountByStatus = new HashMap<String, Integer>();
				} else {
					weeklyCountByStatus = weeklyCountByUsers.get(updatedUser);
				}
				
				Integer weeklyCurr = 0;
				if (null != weeklyCountByStatus.get(status)) {
					weeklyCurr = weeklyCountByStatus.get(status);
				}
				weeklyCountByStatus.put(status, weeklyCurr + count);
				weeklyCountByUsers.put(updatedUser, weeklyCountByStatus);
				weeklyCountByWeeks.put(weekOf, weeklyCountByUsers);
				
				//Get the total count for user
				Map<String, Integer> totalCountByStatus = null;
				if (null == totalCountByUsers.get(updatedUser)) {
					totalCountByStatus = new HashMap<String, Integer>();
				} else {
					totalCountByStatus = totalCountByUsers.get(updatedUser);
				}
				
				Integer totalCurr = 0;
				if (null != totalCountByStatus.get(status)) {
					totalCurr = totalCountByStatus.get(status);
				}
				totalCountByStatus.put(status, totalCurr + count);
				totalCountByUsers.put(updatedUser, totalCountByStatus);
			}	
			
		} catch(Exception exception){
			log.error("Exception occurecd while retreving the Merge Report By Daily Staff : {}", exception);
			throw new DataRetrievalFailureException("Failed to get the Merge Report, please contact helpline.");	
		}
		
		userNamesList.sort(null);
		map.put("userNamesList", userNamesList);
		map.put("weeklyCountByWeeks", weeklyCountByWeeks);
		map.put("totalCountByUsers", totalCountByUsers);
		
		return dataMapByWeeks;
	}
	
	public Map<String, Map<String, Map<String, Map<String, Integer>>>> getBadMergeReportsByDailyStaff(String fromDate, String toDate, Map<String, Object> map,
			UserObject userObject) {
		Map<String, Map<String, Map<String, Map<String, Integer>>>> dataMapByWeeks = new LinkedHashMap<String, Map<String, Map<String, Map<String, Integer>>>>();		
		Map<String, Map<String, Map<String, Integer>>> weeklyCountByWeeks = new HashMap<>();
		Map<String, Map<String, Integer>> totalCountByUsers = new HashMap<>();
		List<String> userNamesList = new ArrayList<>();

		try{
			Map<String, String> inOutParameterMap = new HashMap<>();	
			inOutParameterMap.put("fromDate", fromDate);			
			inOutParameterMap.put("toDate", toDate);
			List<Map<String, Object>> mergeReportsListRaw = mergeUnMergePersonDAO.getBadMergeReportsByDailyStaff(inOutParameterMap);	
			
			//First fill in all dates with empty rows
			String[] dayOfWeekStrings = {"SUNDAY", "MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY"};
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy_MM_dd");
			Date dateFrom = sdf.parse(fromDate);
			Date dateTo = sdf.parse(toDate);
			Date currentDate = dateFrom;
			Calendar cal = Calendar.getInstance();
			
			while (currentDate.before(dateTo) || currentDate.equals(dateTo)) {
				cal.setTime(currentDate);
				if (cal.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY && cal.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {					
					String currentDateInMap = sdf.format(currentDate) + " (" + dayOfWeekStrings[cal.get(Calendar.DAY_OF_WEEK)-1] + ")";
					Calendar cal2 = (Calendar) cal.clone();
					cal2.add(Calendar.DATE, -(cal.get(Calendar.DAY_OF_WEEK)-2));
					Date mondayOfWeek = cal2.getTime();
					String mondayOfWeekString = sdf2.format(mondayOfWeek);

					if (dataMapByWeeks.get(mondayOfWeekString) != null) {
						dataMapByWeeks.get(mondayOfWeekString).put(currentDateInMap, null);
					} else {
						Map<String, Map<String, Map<String, Integer>>> emptyRowsByDates = new LinkedHashMap<>();
						emptyRowsByDates.put(currentDateInMap, null);
						dataMapByWeeks.put(mondayOfWeekString, emptyRowsByDates);
					}
				}
				cal.add(Calendar.DATE, 1);
				currentDate = cal.getTime();
			}
			
			//Convert the mergeReportsListRaw to mergeReportsByDailyStaff arranged by Week, Date, User, Status, Count		
			for (Map<String, Object> mergeData : mergeReportsListRaw) {
				String weekOf = (String)mergeData.get("WEEKOF");
				String dayName = ((String)mergeData.get("DAY_NAME")).replaceAll("\\s+", "");
				String updatedDate = (String)mergeData.get("UPDATED_DATE") + " (" + dayName + ")";
				String updatedUser = (String)mergeData.get("UPDATED_USER");
				String status = (String)mergeData.get("STATUS");
				Integer count = (Integer)(((BigDecimal)mergeData.get("COUNT")).intValueExact());
				
				//Update user names list
				if (!userNamesList.contains(updatedUser)) {
					userNamesList.add(updatedUser);
				}

				//Get the data map for the week
				Map<String, Map<String, Map<String, Integer>>> dataMapByDates = null;
				if (null == dataMapByWeeks.get(weekOf)) {
					dataMapByDates = new LinkedHashMap<String, Map<String, Map<String, Integer>>>();
				} else {
					dataMapByDates = dataMapByWeeks.get(weekOf);
				}
				
				//Get the data map for the date
				Map<String, Map<String, Integer>> dataMapByUsers = null;
				if (null == dataMapByDates.get(updatedDate)) {
					dataMapByUsers = new HashMap<String, Map<String, Integer>>();
				} else {
					dataMapByUsers = dataMapByDates.get(updatedDate);
				}
				
				//Get the data map for the user
				Map<String, Integer> dataMapByStatus = null;
				if (null == dataMapByUsers.get(updatedUser)) {
					dataMapByStatus = new HashMap<String, Integer>();
				} else {
					dataMapByStatus = dataMapByUsers.get(updatedUser);							
				}
						
				dataMapByStatus.put(status, count);
				dataMapByUsers.put(updatedUser, dataMapByStatus);
				dataMapByDates.put(updatedDate, dataMapByUsers);
				dataMapByWeeks.put(weekOf, dataMapByDates);
				
				//Get the weekly count for users for the week
				Map<String, Map<String, Integer>> weeklyCountByUsers = null;
				if (null == weeklyCountByWeeks.get(weekOf)) {
					weeklyCountByUsers = new HashMap<String, Map<String, Integer>>();
				} else {
					weeklyCountByUsers = weeklyCountByWeeks.get(weekOf);
				}
				
				//Get the weekly count for user
				Map<String, Integer> weeklyCountByStatus = null;
				if (null == weeklyCountByUsers.get(updatedUser)) {
					weeklyCountByStatus = new HashMap<String, Integer>();
				} else {
					weeklyCountByStatus = weeklyCountByUsers.get(updatedUser);
				}
				
				Integer weeklyCurr = 0;
				if (null != weeklyCountByStatus.get(status)) {
					weeklyCurr = weeklyCountByStatus.get(status);
				}
				weeklyCountByStatus.put(status, weeklyCurr + count);
				weeklyCountByUsers.put(updatedUser, weeklyCountByStatus);
				weeklyCountByWeeks.put(weekOf, weeklyCountByUsers);
				
				//Get the total count for user
				Map<String, Integer> totalCountByStatus = null;
				if (null == totalCountByUsers.get(updatedUser)) {
					totalCountByStatus = new HashMap<String, Integer>();
				} else {
					totalCountByStatus = totalCountByUsers.get(updatedUser);
				}
				
				Integer totalCurr = 0;
				if (null != totalCountByStatus.get(status)) {
					totalCurr = totalCountByStatus.get(status);
				}
				totalCountByStatus.put(status, totalCurr + count);
				totalCountByUsers.put(updatedUser, totalCountByStatus);
			}	
			
		} catch(Exception exception){
			log.error("Exception occurecd while retreving the Merge Report By Daily Staff : {}", exception);
			throw new DataRetrievalFailureException("Failed to get the Merge Report, please contact helpline.");	
		}
		
		userNamesList.sort(null);
		map.put("userNamesList", userNamesList);
		map.put("weeklyCountByWeeks", weeklyCountByWeeks);
		map.put("totalCountByUsers", totalCountByUsers);
		
		return dataMapByWeeks;
	}
	
	public List<Integer> getDependentDocumentList(int documentId){
		return mergeUnMergePersonDAO.getDependentDocumentList(documentId);
	}
}
