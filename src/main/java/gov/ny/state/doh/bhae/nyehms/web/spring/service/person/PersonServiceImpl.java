package gov.ny.state.doh.bhae.nyehms.web.spring.service.person;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import gov.ny.state.doh.bhae.nyehms.web.spring.dao.document.CancerDocumentDAO;
import gov.ny.state.doh.bhae.nyehms.web.spring.dao.document.ClusterDocumentDAO;
import gov.ny.state.doh.bhae.nyehms.web.spring.dao.person.PersonDAO;
import gov.ny.state.doh.bhae.nyehms.web.spring.dao.upload.FileUploadDAO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.common.AddressFieldVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.ClusterDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.person.PersonDocHistoryVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.person.PersonFieldDocsVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.person.PersonFieldVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.person.PersonRaceVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.person.PersonRisksVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.person.PersonViewVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.uploads.ClusterUploadVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.enums.AppConstants;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.CommonService;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.document.ClusterDocumentService;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.document.DocumentTransactionHelper;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.scan.DocumentsScanService;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonExceptionHandler;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonUtil;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.DateUtil;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.scheduler.LabCommonUtils;

/*
 * Author 	: Srikar Kummamuri
 * Date 	: 2/12/12
 */

@Service
public class PersonServiceImpl implements PersionService {

	private static Logger log = LoggerFactory.getLogger(PersonServiceImpl.class);
	private String nyehmsLog = AppConstants.nyehmLogDebugString.getName();

	@Autowired
	private PersonDAO personDAO;

	@Autowired
	private CommonExceptionHandler commonExceptionHandler;

	@Autowired
	private DocumentTransactionHelper documentTransactionHelper;

	@Autowired
	private PersonTransactionHelper personTransactionHelper;
	
	@Autowired CommonService commonService;
	
	@Autowired DocumentsScanService documentsScanService;
	
	@Autowired ClusterDocumentService clusterDocumentService;
	
	@Autowired ClusterDocumentDAO clusterDocumentDAO;
	
	@Autowired CancerDocumentDAO cancerDocumentDAO;
	
	@Autowired FileUploadDAO fileUploadDAO;

	@Autowired
	DateUtil dateUtil;


	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public PersonViewVO getPersonViewData(String stateNo, String date, BindingResult bindingResult,
			UserObject userObject, String sessionID) {
		Date stDt;

		log.debug("{} Date String from user  is {}",nyehmsLog ,date);
		if (date.equals(""))
			stDt = new Date();
		else
			stDt = dateUtil.getNyehmsDateFromString(date);

		if (stDt == null)
			stDt = new Date();

		log.debug("{} user sending date after dateutil conversion is :: {}",nyehmsLog, stDt);

		java.util.Map<Object, Object> map = new java.util.HashMap<Object, Object>();
		List<PersonViewVO> cursor = new ArrayList<PersonViewVO>();
		map.put("p_data_req_type", "DETL");
		map.put("p_state_no", stateNo);
		map.put("p_date", stDt);
		map.put("refCursor", cursor);
		map.put("p_calling_app_name", "NYEHMS");
		map.put("p_module_id", 16);
		map.put("p_worker_id", userObject.getWorkerId());
		
		List<PersonViewVO> personViewlist = new ArrayList<PersonViewVO>();

		personDAO.getPersonViewData(map);

		personViewlist = (List<PersonViewVO>) map.get("refCursor");

		if (personViewlist == null || personViewlist.size() == 0) {
			if (log.isDebugEnabled())
				log.debug("No Person view is available for the Stateno: {}",stateNo);
			return new PersonViewVO();
		} else {

			
			Map<String, Object> statenoDetails = documentTransactionHelper.getStatenoDetailsOfStateno(null, stateNo, "PERSON", userObject);
			
			PersonViewVO personViewVO = personViewlist.get(0);
			
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
			
			//added 7/10/20 @rzp05
			//getting & setting duplicate statenos in the list
			if(personViewVO.getDUPLICATE_STATENO() != null) {
				
				String duplicateStatenos = personViewVO.getDUPLICATE_STATENO();
				
				String[] duplicateStateNoList = duplicateStatenos.split(",");
				
				personViewVO.setDUPLICATE_STATENO_LIST(Arrays.asList(duplicateStateNoList));
			}
			
			
			
			return personViewVO;
		}
	}

	public List<PersonDocHistoryVO> getPersonDocumentDetails(String stateNo, String documentType, BindingResult dummyBindingResult,
			UserObject userObject, String id) {
		
		try{
			
			Map<String, Object> inputParameterMap = new HashMap<String, Object>();
			inputParameterMap.put("p_module_id", "16");
			inputParameterMap.put("p_worker_id", userObject.getWorkerId());
			
			if(!"ALL".equals(documentType)) {
				inputParameterMap.put("p_documentType", documentType);
			}
						
			inputParameterMap.put("p_stateNo", stateNo);
			
			return personDAO.getPersonDocumentDetails(inputParameterMap);
		}catch(Exception e){
			log.error("Failed to get the Document Details for the stateno {}",stateNo);
			log.error(e.getMessage());
			
			throw new DataRetrievalFailureException(
					"Failed to get the Document Details for the stateno  " + stateNo);
		}
	}

	@Override
	public List<PersonDocHistoryVO> getHIVDocumentDetails(String stateNo, BindingResult dummyBindingResult,
			UserObject userObject, String id) {
		
		Map<String, Object> inputParameterMap = new HashMap<String, Object>();
		inputParameterMap.put("p_module_id", "16");
		inputParameterMap.put("p_worker_id", userObject.getWorkerId());
		inputParameterMap.put("p_stateNo", stateNo);

		return personDAO.getHIVDocumentDetails(inputParameterMap);
	}

	@Override
	public List<PersonDocHistoryVO> getDocumentDetails(String documentId, String documentType,
			BindingResult dummyBindingResult, UserObject userObject, String id) {

		Map<Object, Object> map = new HashMap<Object, Object>();

		map.put("documentId", Integer.parseInt(documentId));
		map.put("documentType", documentType);

		map.put("tableName", LabCommonUtils.getStdTableName(documentType));
		
		if("STD_HIV_ECLRS_DOC".equals(LabCommonUtils.getStdTableName(documentType))) {
			return personDAO.getLabDocumentDetails(map);
		}else if("CLUST_PART".equals(documentType)) {
			return personDAO.getClusterDocumentDetailsFromStageTable(map);
		}else if("NBS".equals(documentType)) {
			return personDAO.getNBSDocumentDetails(map);
		}else if("CANCER".equals(documentType)) {
			return cancerDocumentDAO.getCancerDocumentDetails(map);
		}else {
			return personDAO.getDocumentDetailsFromStageTable(map);
		}

		
//			// Change INS here

	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PersonFieldVO> getFieldValsWithBestIndicator(String stateNo, String fieldName, String callingAppName,
			BindingResult result, UserObject userObject, String sessionID) {
		boolean errorFlag = false;
		List<PersonFieldVO> cursorPersonFieldVals = new ArrayList<PersonFieldVO>();
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("tblField", fieldName);
		map.put("stateNo", stateNo);
		map.put("p_calling_app_name", callingAppName);
		map.put("refCursor", cursorPersonFieldVals);
		map.put("p_module_id", 16);
		map.put("p_worker_id", userObject.getWorkerId());
		
		List<PersonFieldVO> list = new ArrayList<PersonFieldVO>();
		List<PersonFieldVO> distinctList = new ArrayList<PersonFieldVO>();
		try {

			log.debug("{}   Current SessionId ==============   {}",userObject.getWorkerId(), userObject.getSessionId());
			personDAO.getFieldValsWithBestIndicator(map);
			list = (List<PersonFieldVO>) map.get("refCursor");
			Map<String, PersonFieldVO> distictValsMap = new HashMap<String, PersonFieldVO>();
			for (PersonFieldVO personFieldVO : list) {
				distictValsMap.put(personFieldVO.getField().trim(), personFieldVO);
			}

			for (String field : distictValsMap.keySet()) {
				distinctList.add(distictValsMap.get(field));
			}

		} catch (DataAccessException e) {
			errorFlag = true;
			log.error(nyehmsLog + " SQL Exception Thrown " + e.getMessage());
			commonExceptionHandler.handleException(e, userObject, sessionID);
		} catch (Exception e) {
			errorFlag = true;
			log.error(nyehmsLog + " Run Time Error Thrown " + e.getMessage());
			commonExceptionHandler.handleException(e, userObject, sessionID);
		} finally {
			if (errorFlag)
				result.addError(new ObjectError("SQLError", "Transaction Failed. Please try again "));
		}
		return distinctList;
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PersonFieldDocsVO> getFieldDocsWithBestIndicator(String stateNo, String fieldName, String fieldVal,
			String callingAppName, BindingResult result, UserObject userObject, String sessionID) {
		boolean errorFlag = false;
		List<PersonFieldVO> cursorPersonFieldVals = new ArrayList<PersonFieldVO>();
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("tblField", fieldName);
		map.put("stateNo", stateNo);
		map.put("p_calling_app_name", callingAppName);
		map.put("refCursor", cursorPersonFieldVals);
		map.put("p_module_id", 16);
		map.put("p_worker_id", userObject.getWorkerId());
		
		List<PersonFieldDocsVO> list = new ArrayList<PersonFieldDocsVO>();
		List<PersonFieldDocsVO> listForFieldVal = new ArrayList<PersonFieldDocsVO>();

		try {
			personDAO.getFieldDocsWithBestIndicator(map);
			list = (List<PersonFieldDocsVO>) map.get("refCursor");

			for (PersonFieldDocsVO personFieldDocsVO : list) {
				if (personFieldDocsVO.getField().trim().equals(fieldVal.trim())) {
					listForFieldVal.add(personFieldDocsVO);
				}
			}

		} catch (DataAccessException e) {
			errorFlag = true;
			log.error(nyehmsLog + " SQL Exception Thrown " + e.getMessage());
			commonExceptionHandler.handleException(e, userObject, sessionID);
		} catch (Exception e) {
			errorFlag = true;
			log.error(nyehmsLog + " Run Time Error Thrown " + e.getMessage());
			commonExceptionHandler.handleException(e, userObject, sessionID);
		} finally {
			if (errorFlag)
				result.addError(new ObjectError("SQLError", "Transaction Failed. Please try again "));
		}
		return listForFieldVal;
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PersonRisksVO> getPersonRisksData(String stateNo, String date, BindingResult result,
			UserObject userObject, String sessionID) {
		boolean errorFlag = false;

		Date stDt;

		log.debug(nyehmsLog + "  Date String from user  is " + date);
		if (date.equals(""))
			stDt = new Date();
		else
			stDt = dateUtil.getNyehmsDateFromString(date);

		if (stDt == null)
			stDt = new Date();
		// if(stDt==null) stDt=new Date();
		java.util.Map<Object, Object> map = new java.util.HashMap<Object, Object>();
		List<PersonRisksVO> cursorRisksIn = new ArrayList<PersonRisksVO>();
		map.put("p_state_no", stateNo);
		map.put("p_date", stDt);
		map.put("refCursor", cursorRisksIn);
		map.put("p_calling_app_name", "NYEHMS");

		List<PersonRisksVO> list = new ArrayList<PersonRisksVO>();
		try {
			personDAO.getPersonRisksData(map);
			list = (List<PersonRisksVO>) map.get("refCursor");

		} catch (DataAccessException e) {
			errorFlag = true;
			log.error(nyehmsLog + " SQL Exception Thrown " + e.getMessage());
			commonExceptionHandler.handleException(e, userObject, sessionID);
			throw new DataRetrievalFailureException(
					"Unable to load the Person risk, please contact helpline  " + stateNo);
		} catch (Exception e) {
			errorFlag = true;
			log.error(nyehmsLog + " Run Time Error Thrown " + e.getMessage());
			commonExceptionHandler.handleException(e, userObject, sessionID);
			throw new DataRetrievalFailureException(
					"Unable to load the Person risk, please contact helpline  " + stateNo);
		} finally {
			if (errorFlag)
				result.addError(new ObjectError("SQLError", "Transaction Failed. Please try again "));
		}
		return list;
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public PersonRaceVO getPersonRaceData(String stateNo, String date, BindingResult result, UserObject userObject,
			String sessionID) {
		boolean errorFlag = false;

		Date stDt;

		if (date.equals(""))
			stDt = new Date();
		else
			stDt = dateUtil.getNyehmsDateFromString(date);

		if (stDt == null)
			stDt = new Date();

		java.util.Map<Object, Object> map = new java.util.HashMap<Object, Object>();
		List<PersonRaceVO> cursorRisksIn = new ArrayList<PersonRaceVO>();
		map.put("p_state_no", stateNo);
		map.put("p_date", stDt);
		map.put("refCursor", cursorRisksIn);

		List<PersonRaceVO> list = new ArrayList<PersonRaceVO>();
		PersonRaceVO personRaceVO = null;
		try {
			personDAO.getPersonRaceData(map);
			list = (List<PersonRaceVO>) map.get("refCursor");
			if (list != null && list.size() > 0 && list.get(0) != null) {
				personRaceVO = list.get(0);
			}
		} catch (DataAccessException e) {
			errorFlag = true;
			log.error(nyehmsLog + " SQL Exception Thrown " + e.getMessage());
			commonExceptionHandler.handleException(e, userObject, sessionID);
		} catch (Exception e) {
			errorFlag = true;
			log.error(nyehmsLog + " Run Time Error Thrown " + e.getMessage());
			commonExceptionHandler.handleException(e, userObject, sessionID);
		} finally {
			if (errorFlag)
				result.addError(new ObjectError("SQLError", "Transaction Failed. Please try again "));
		}
		return personRaceVO;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void deleteDocument(int documentId, String documentType, Integer discardReason, String discardReasonDescription, String comments, String stateno, String deleteFlag, BindingResult bindingResult,
			UserObject userObject) {

		boolean isError = false;
		try {
			if(documentType.startsWith("SCAN")) {
				documentsScanService.deleteScanDocument(documentId, documentType, stateno, discardReasonDescription, comments, bindingResult, userObject);
			}else {
				personTransactionHelper.deleteDocument(documentId, documentType, discardReason, comments, stateno, deleteFlag, bindingResult, userObject);
			}
			
			
		} catch (DataAccessException dataAccessException) {
			isError = true;
			log.error("Data access exception occured while deleting the document : {}", documentId);
			log.error(dataAccessException.getMessage());
		} catch (Exception exception) {
			isError = true;
			log.error("Exception occured while deleting the document : {}", documentId);
			log.error(exception.getMessage());
		}
		if (isError || bindingResult.hasErrors()) {
			commonExceptionHandler.inspectDBTransacionResult(-1,
					"Failed to delete the document, Please contact helpline.", bindingResult);
		} else if("Y".equals(deleteFlag)){
			commonExceptionHandler.inspectDBTransacionResult(0, "Successfully deleted the document.", bindingResult);
		}
	}

	@Override
	public List<String> getEharsStateno(String stateNo, BindingResult bindingResult, UserObject userObject) 
	{
		 List<String> eharsStatenoDetails = null;
		try {
			eharsStatenoDetails = personDAO.getEharsStateno(stateNo);
		} catch (DataAccessException e) {
			log.error(nyehmsLog + " SQL Exception Thrown while getting Ehars Stateno details" + e.getMessage());
			commonExceptionHandler.handleException(e, userObject, userObject.getSessionId());
		} catch (Exception e) {
			log.error(nyehmsLog + " Run Time Error Thrown while getting Ehars Stateno details " + e.getMessage());
			commonExceptionHandler.handleException(e, userObject, userObject.getSessionId());
		}
		return eharsStatenoDetails;
	}

	@Override
	public List<String> getNycEharsDetails(String stateNo, BindingResult bindingResult, UserObject userObject)
	{
		List<String> nycEharsDetails = null;
		try {
			nycEharsDetails = personDAO.getNycEharsDetails(stateNo);
		} catch (DataAccessException e) {
			log.error(nyehmsLog + " SQL Exception Thrown while getting NYC Ehars Stateno details " + e.getMessage());
			commonExceptionHandler.handleException(e, userObject, userObject.getSessionId());
		} catch (Exception e) {
			log.error(nyehmsLog + " Run Time Error Thrown while getting NYC Ehars Stateno details " + e.getMessage());
			commonExceptionHandler.handleException(e, userObject, userObject.getSessionId());
		}
		return nycEharsDetails;
	}
	
	@Override
	public List<String> getNycCityNoDetails(String stateno, Object object,
			UserObject userObject)
	{
		List<String> nycitynoDetails = null;
		try {
			nycitynoDetails = personDAO.getNycCityNoDetails(stateno);
		} catch (DataAccessException e) {
			log.error(nyehmsLog + " SQL Exception Thrown while getting NYC Cityno details" + e.getMessage());
			log.error("Stateno  : {}",stateno);
			commonExceptionHandler.handleException(e, userObject, userObject.getSessionId());
		} catch (Exception e) {
			log.error(nyehmsLog + " Run Time Error Thrown  while getting NYC Cityno details" + e.getMessage());
			commonExceptionHandler.handleException(e, userObject, userObject.getSessionId());
		}
		return nycitynoDetails;
	}
	

	@Override
	public List<Map<String, String>> getOriginCategories() {
		return personDAO.getOriginCategories();
	}

	@Override
	public String getNyehmsStatenoForTheDocument(int docId) {
		String nyehmsStateno = null;
		try {
			nyehmsStateno = personDAO.getNyehmsStatenoForTheDocument(docId);

		} catch (DataAccessException e) {
			log.error(nyehmsLog + " Failed to get the Stateno for the document selected " + e.getMessage());

		} catch (Exception e) {
			log.error(nyehmsLog + " Failed to get the Stateno for the document selected " + e.getMessage());

		}
		return nyehmsStateno;
	}

	public List<String> getStatenoHistory(String stateNo, BindingResult bindingResult, UserObject userObject) {

		Map<Object, Object> inOutParameterMap = new HashMap<Object, Object>();

		inOutParameterMap.put("p_state_no", stateNo);

		personDAO.getStatenoHistory(inOutParameterMap);

		List<String> p_stateno_hist = ((List<String>) inOutParameterMap.get("p_stateno_hist"));

		return p_stateno_hist;
	}


	@SuppressWarnings("unchecked")
	public JSONObject getEharsStatenosMapping(String[] statenos, BindingResult bindingResult, UserObject userObject) {

		Map<Object, Object> inOutParameterMap = new HashMap<Object, Object>();

		inOutParameterMap.put("statenos", statenos);

		List<Map<String, String>> eharsStatenoMappingList = personDAO.getEharsStatenoMappings(inOutParameterMap);

		JSONObject eharsAndNyehmsStatenoMapping = new JSONObject();

		String stateno1 = statenos[0];
		String stateno2 = statenos[1];
		String nyehmsStateno = null;
		String eharsStateno = null;
		String eharsStateno1Str = "";
		String eharsStateno2Str = "";

		for (Map<String, String> map : eharsStatenoMappingList) {

			nyehmsStateno = map.get("NYEHMS_STATENO");
			eharsStateno = map.get("EHARS_STATENO");

			if (nyehmsStateno.equalsIgnoreCase(stateno1)) {
				eharsStateno1Str += eharsStateno + ",";
			}
			if (nyehmsStateno.equalsIgnoreCase(stateno2)) {
				eharsStateno2Str += eharsStateno + ",";
			}

		}

		if (eharsStateno1Str.length() > 0) {
			eharsStateno1Str = eharsStateno1Str.substring(0, eharsStateno1Str.length() - 1);
		}
		if (eharsStateno2Str.length() > 0) {
			eharsStateno2Str = eharsStateno2Str.substring(0, eharsStateno2Str.length() - 1);
		}

		eharsAndNyehmsStatenoMapping.put(stateno1, stateno1 + "  (eHARS: " + eharsStateno1Str + ")");
		eharsAndNyehmsStatenoMapping.put(stateno2, stateno2 + "  (eHARS: " + eharsStateno2Str + ")");

		return eharsAndNyehmsStatenoMapping;
	}

	@Override
	public void updateStdDocument(Map<String, String> formParameters, BindingResult bindingResult,
			UserObject userObject) {
		boolean isError = false;
		int documentId = 0;
		try {

			documentId = Integer.parseInt(formParameters.get("documentId"));
			String documentType = formParameters.get("documentType");

			documentTransactionHelper.editUnknownOosStdDocument(formParameters, documentId, documentType, bindingResult,
					userObject);

		} catch (DataAccessException e) {
			isError = true;
			log.error("Document Id   {}", documentId);
			log.error(nyehmsLog + " Failed to update the document " + e.getMessage());
			commonExceptionHandler.handleException(e, userObject, userObject.getSessionId());
		} catch (Exception e) {
			isError = true;
			log.error("Document Id   {}", documentId);
			log.error(nyehmsLog + " Failed to update the document " + e.getMessage());
			commonExceptionHandler.handleException(e, userObject, userObject.getSessionId());
		}

		if (isError) {
			commonExceptionHandler.inspectDBTransacionResult(-1,
					"Failed to update the document status, Please contact helpline.", bindingResult);
		}

	}

	@Override
	public String checkStatenoIsActive(String stateNo, BindingResult bindingResult, UserObject userObject) {
		return personDAO.checkStatenoIsActive(stateNo);
	}

	@Override
	public void triggerPsMessage(int documentId, String stateno, String documentType, String messageType,
			String psMessageForce, String softErrorCode, AddressFieldVO addressFieldVO, String reason, String otherReason, BindingResult bindingResult, UserObject userObject) {
		boolean isError = false;
		try {

			personTransactionHelper.triggerPsMessage(documentId, stateno, documentType, messageType, psMessageForce,
					softErrorCode, addressFieldVO, reason, otherReason, bindingResult, userObject);

		} catch (DataAccessException e) {
			isError = true;
			log.error("Document Id   {}", documentId);
			log.error(nyehmsLog + " Failed to trigger a PS Message " + e.getMessage());
		} catch (Exception e) {
			isError = true;
			log.error("Document Id   {}", documentId);
			log.error(nyehmsLog + " Failed to trigger a PS Message " + e.getMessage());
			commonExceptionHandler.inspectDBTransacionResult(-1,
					"Failed to trigger a PS Message, Please contact helpline.", bindingResult);
		}
	}

	public void getPsMessageType(int documentId, String stateno, String documentType, BindingResult bindingResult,
			UserObject userObject) {

		boolean isError = false;
		try {

			personTransactionHelper.getPsMessageType(documentId, stateno, documentType, bindingResult, userObject);

		} catch (DataAccessException e) {
			isError = true;
			log.error("Document Id   {}", documentId);
			log.error(nyehmsLog + " Failed to get PS Message type for PRF document " + e.getMessage());
		} catch (Exception e) {
			isError = true;
			log.error("Document Id   {}", documentId);
			log.error(nyehmsLog + " Failed to get PS Message type for PRF document  " + e.getMessage());
		}

		if (isError) {
			commonExceptionHandler.inspectDBTransacionResult(-1,
					"Failed to trigger the PS Message, please contact helpline ", bindingResult);
		}

	}

	@Override
	public List<String> getTriggeredMessageTypesForDocument(Map<Object, Object> inputParameterMap) {
		return personDAO.getTriggeredMessageTypesForDocument(inputParameterMap);
	}

	@Override
	public void executeTriggerPsMessage(int documentId, String stateno, String testType, String messageType, AddressFieldVO addressFieldVO,
			BindingResult bindingResult, UserObject userObject) {

		Map<Object, Object> inOutParameterMap = new HashMap<Object, Object>();

		inOutParameterMap.put("p_stateno", stateno);
		inOutParameterMap.put("p_document_type", LabCommonUtils.getDocumentType(testType));
		inOutParameterMap.put("p_test_type_code", testType);
		inOutParameterMap.put("p_document_id", documentId);
		inOutParameterMap.put("p_TRIGGER_MSG_TYPE", messageType);
		inOutParameterMap.put("p_workerId", userObject.getUserId());
		
		if(CommonUtil.isNotNullAndEmpty(addressFieldVO.getAddress())){
			
			inOutParameterMap.put("p_change_address", "Y");
			inOutParameterMap.put("p_new_address", addressFieldVO.getAddress());
			inOutParameterMap.put("p_new_city", addressFieldVO.getCity());
			inOutParameterMap.put("p_new_state", addressFieldVO.getState());
			inOutParameterMap.put("p_new_county_fips", addressFieldVO.getCountyFipsCd());
			inOutParameterMap.put("p_new_zip", addressFieldVO.getZip());
			
			
			
		}
		
		

		personDAO.triggerPsMessage(inOutParameterMap);

		Integer statusCode = (Integer) inOutParameterMap.get("p_code_out");

		if (statusCode != 0) {

			log.error(nyehmsLog + "  : Failed while triggering PS Message stored procedure " + documentId);

			String transactionMessage = (String) inOutParameterMap.get("p_msg_out");
			log.error(transactionMessage);

			commonExceptionHandler.inspectDBTransacionResult(-1, transactionMessage, bindingResult);

			throw new DataRetrievalFailureException("Failed to trigger the PS Message.");
		} else {
			commonExceptionHandler.inspectDBTransacionResult(0, "Successfully triggered the PS Message", bindingResult);
		}

	}
	
	@Override
	public String getClusterIds(String stateNo, BindingResult result, UserObject userObject) {
		boolean errorFlag = false;
		String clusterIds = "";
		
		List<String> clusterDetailsList = new ArrayList<String>();

		try {
				List<ClusterUploadVO> statenoClusterIdsList = personDAO.getStatenoClusterAssociation(stateNo);
				if (statenoClusterIdsList != null ) {
					for(ClusterUploadVO clusterDetails : statenoClusterIdsList) {
						if (clusterDetails != null && CommonUtil.isNotNullAndEmpty(clusterDetails.getCurr_stateno())) {
							clusterDetailsList.add(clusterDetails.getClusterId());
						}
					}
				}
				
				clusterIds = clusterDetailsList.toString();	
				clusterIds = clusterIds.substring(1, clusterIds.length()-1);
						
		} catch (DataAccessException e) {
			errorFlag = true;
			log.error(nyehmsLog + " SQL Exception Thrown " + e.getMessage());
			commonExceptionHandler.handleException(e, userObject, userObject.getSessionId());
		} catch (Exception e) {
			errorFlag = true;
			log.error(nyehmsLog + " Run Time Error Thrown " + e.getMessage());
			commonExceptionHandler.handleException(e, userObject, userObject.getSessionId());
		} finally {
			if (errorFlag)
				result.addError(new ObjectError("SQLError", "Transaction Failed. Please try again "));
		}
		return clusterIds;
	}

	@Override
	public List<String> getNotAssociatedClusterIds(UserObject userObject, BindingResult result, String stateNo) {
		
		boolean errorFlag = false;
		List<String> clusterDetailsList = new ArrayList<String>();

		try {
			List<ClusterUploadVO> notAssocClusterDetails = personDAO.getStatenoClusterAssociation(stateNo);
			if (notAssocClusterDetails != null ) {
				for(ClusterUploadVO clusterDetails : notAssocClusterDetails) {
					if (clusterDetails != null && CommonUtil.isNullOrEmpty(clusterDetails.getCurr_stateno())) {
						clusterDetailsList.add(clusterDetails.getClusterId() + ", " + clusterDetails.getClusterType() + ", " + clusterDetails.getClusterDate());
					}
				}
			}
			
		} catch (DataAccessException e) {
			errorFlag = true;
			log.error(nyehmsLog + " SQL Exception Thrown " + e.getMessage());
			commonExceptionHandler.handleException(e, userObject, userObject.getSessionId());
		} catch (Exception e) {
			errorFlag = true;
			log.error(nyehmsLog + " Run Time Error Thrown " + e.getMessage());
			commonExceptionHandler.handleException(e, userObject, userObject.getSessionId());
		} finally {
			if (errorFlag)
				result.addError(new ObjectError("SQLError", "Transaction Failed. Please try again "));
		}
		return clusterDetailsList;
	}

	@Override
	public void associateStateNoToCluster(UserObject userObject, BindingResult bindingResult, String stateno, String clusterDetails, String identificationMethod, String identificationDate, long demoDocumentId) {
		
		boolean isError = false;
		ClusterDocVO currentDemoInfo = null;
		Map<String, Object> inputParameter = new HashMap<String, Object>();
		
		List<String> clusterDetailsList = Arrays.asList(clusterDetails.split("\\s*,\\s*"));
		
		String clusterId = clusterDetailsList.get(0).trim();
		String clusterType = clusterDetailsList.get(1).trim();
		inputParameter.put("nyehmsStateno", stateno);
		inputParameter.put("demoDocumentId", demoDocumentId);
		
		if (clusterType.equals("BHAE_M") || clusterType.equals("CDC_M")) {
		//	currentDemoInfo = clusterDocumentService.getStatenoDemographicInfoToaddCluster(clusterType, stateno, clusterId);
			currentDemoInfo = clusterDocumentDAO.getStatenoDemographic(inputParameter);
			if (currentDemoInfo == null) {
				isError = true;
			}
		}
		
		if ((clusterType.equals("BHAE_M") || clusterType.equals("CDC_M")) && isError) {
				commonExceptionHandler.inspectDBTransacionResult(-1, "NYEHMS stateno  " + stateno + " demographic info not found to associate it to Cluster.", bindingResult);
		} else {
			
				try {
					
					Map<String, Object> clusterDetailsMap = new HashMap<String, Object>();
					
					clusterDetailsMap.put("clusterId", clusterId);
					clusterDetailsMap.put("clusterType", clusterType);
					clusterDetailsMap.put("clusterDate", clusterDetailsList.get(2).trim());
					clusterDetailsMap.put("identificationMethodCode", identificationMethod);
					clusterDetailsMap.put("identificationDate", identificationDate);
					clusterDetailsMap.put("nyehmsStateno", stateno);
					clusterDetailsMap.put("userId", Integer.toString(userObject.getWorkerId()));
					clusterDetailsMap.put("uploadNyehmsStateno", "Manual_Add");
					clusterDetailsMap.put("demoDocumentId", demoDocumentId);
					
					int clusterIdPk = clusterDocumentDAO.getClusterPkNumber(clusterId);
					clusterDetailsMap.put("clusterIdPk", clusterIdPk);
					
					documentTransactionHelper.associateStateNoToCluster(clusterDetailsMap, currentDemoInfo, userObject, bindingResult);
		
				} catch (DataAccessException e) {
					isError = true;
					log.error("Stateno Id {}", stateno);
					log.error(nyehmsLog + " Failed to associate the stateno " + e.getMessage());
					commonExceptionHandler.handleException(e, userObject, userObject.getSessionId());
				} catch (Exception e) {
					isError = true;
					log.error("Stateno Id {}", stateno);
					log.error(nyehmsLog + " Failed to associate the stateno " + e.getMessage());
					commonExceptionHandler.handleException(e, userObject, userObject.getSessionId());
				}
		
				if (isError) {
					commonExceptionHandler.inspectDBTransacionResult(-1,
							"Failed to associate the stateno to cluster, Please contact helpline.", bindingResult);
				} else {
					commonExceptionHandler.inspectDBTransacionResult(0,
							"Stateno associated with cluster successfully.", bindingResult);
				}
			
			}

	
	}
	
	@Override
	public void deletePendingPSMessageDocuments(int documentId, UserObject userObject){
		Map<String, Object> inputParameterMap = new HashMap<String, Object>();
		inputParameterMap.put("p_document_id", documentId);
		inputParameterMap.put("p_workerId", userObject.getWorkerId());
		personDAO.deletePendingPSMessageDocuments(inputParameterMap);
	}
	
	@Override
	public Map<String, String> getNYSEharsDxDetails(String stateno){
		return personDAO.getNYSEharsDxDetails(stateno);
	}
	
	@Override
	public Map<String, String> getNYCEharsDxDetails(String stateno){
		return personDAO.getNYCEharsDxDetails(stateno);
	}
	
	@Override
	public void deleteRiaDocument(int documentId, UserObject userObject) {
		
		Map<String, Object> inputParameterMap = new HashMap<String, Object>();
		inputParameterMap.put("p_document_id", documentId);
		inputParameterMap.put("p_workerId", userObject.getWorkerId());
		personDAO.deletePendingRiaDocument(inputParameterMap);
		
	}

	@Override
	public Map<String, String> geteHarsPersonDataforTab(Map<String, String> eharPersonDataObj,
			BindingResult dummyBindingResult, UserObject userObject, String stateNo) {

		if (eharPersonDataObj != null && CommonUtil.isNotNullAndEmpty(eharPersonDataObj.get("Received Transfusion"))) {

			String value = eharPersonDataObj.get("Received Transfusion").trim();
			if (value.length() > 24) {
				eharPersonDataObj.put("First Date", value.substring(13, value.indexOf("Last Date:")));
			}
			if (value.length() >= 1) {

				eharPersonDataObj.put("Received Transfusion",
						value.substring(0, 1).equals("2") ? "Y" : value.substring(0, 1));
			}
		}
		if (eharPersonDataObj != null
				&& CommonUtil.isNotNullAndEmpty(eharPersonDataObj.get("Child Received Transfusion"))) {

			String value = eharPersonDataObj.get("Child Received Transfusion").replaceAll("\\|", " ").trim();
			if (value.length() > 24) {
				eharPersonDataObj.put("Child Received Transfusion First Date",
						value.substring(13, value.indexOf("Last Date:")));
			}
			if (value.length() >= 1) {

				eharPersonDataObj.put("Child Received Transfusion",
						value.substring(0, 1).equals("2") ? "Y" : value.substring(0, 1));
			}
		}

		if (eharPersonDataObj != null
				&& CommonUtil.isNotNullAndEmpty(eharPersonDataObj.get("No Identified Risk Factor"))
				&& eharPersonDataObj.get("No Identified Risk Factor").length() >= 1) {

			eharPersonDataObj.put("No Identified Risk Factor",
					(eharPersonDataObj.get("No Identified Risk Factor")).substring(0, 1));
		}

		if (eharPersonDataObj != null
				&& CommonUtil.isNotNullAndEmpty(eharPersonDataObj.get("Child No Identified Risk Factor"))
				&& eharPersonDataObj.get("Child No Identified Risk Factor").length() >= 1) {

			eharPersonDataObj.put("Child No Identified Risk Factor",
					(eharPersonDataObj.get("Child No Identified Risk Factor")).substring(0, 1));

		}

		if (eharPersonDataObj != null) {

			if ((eharPersonDataObj.get("Race 1") != null) || (eharPersonDataObj.get("Race 2") != null)
					|| (eharPersonDataObj.get("Race 3") != null) || (eharPersonDataObj.get("Race 4") != null)
					|| (eharPersonDataObj.get("Race 5") != null)) {

				StringBuilder race = new StringBuilder();

				if (CommonUtil.isNotNullAndEmpty(eharPersonDataObj.get("Race 1"))) {
					race.append(eharPersonDataObj.get("Race 1") + ", ");
				}
				if (CommonUtil.isNotNullAndEmpty(eharPersonDataObj.get("Race 2"))) {
					race.append(eharPersonDataObj.get("Race 2") + ", ");
				}
				if (CommonUtil.isNotNullAndEmpty(eharPersonDataObj.get("Race 3"))) {
					race.append(eharPersonDataObj.get("Race 3") + ", ");
				}
				if (CommonUtil.isNotNullAndEmpty(eharPersonDataObj.get("Race 4"))) {
					race.append(eharPersonDataObj.get("Race 4") + ", ");
				}
				if (CommonUtil.isNotNullAndEmpty(eharPersonDataObj.get("Race 5"))) {
					race.append(eharPersonDataObj.get("Race 5"));
				}

				eharPersonDataObj.put("Race 1-5", race.toString().replaceAll(", $", ""));

			}
			// adding display duplicate review summary
			if (eharPersonDataObj.get("RIDR Values") != null) {
				eharPersonDataObj.put("Display Summary", "Y");
			} else {
				eharPersonDataObj.put("Display Summary", "N");
			}

		}
		eharPersonDataObj.put("Nyehms StateNo", stateNo);

		return eharPersonDataObj;
	}

	public List<Map<String, String>> getDuplicateReviewSummary(String ridrValues) {

		List<Map<String, String>> stateNoSummary = new ArrayList<Map<String, String>>();
		Map<String, String> map;

		String ridrValArr[] = ridrValues.split("~");

		for (int i = 0; i < ridrValArr.length; i++) {

			map = new HashMap<String, String>();

			String ridrVal[] = ridrValArr[i].split("\\|");

			if (ridrVal.length == 3) {

				map.put("DUPLICATE_STATUS", ridrVal[0]);
				map.put("STATE_CD", ridrVal[1]);
				map.put("STATENO", ridrVal[2]);
			}

			stateNoSummary.add(map);
		}

		return stateNoSummary;
	}

}
	
