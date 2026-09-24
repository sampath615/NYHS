package gov.ny.state.doh.bhae.nyehms.web.spring.service.document;

import gov.ny.state.doh.bhae.nyehms.web.spring.bo.ProcessACRFForm;
import gov.ny.state.doh.bhae.nyehms.web.spring.dao.administration.NyehmsWorkerRegionsViewDAO;
import gov.ny.state.doh.bhae.nyehms.web.spring.dao.assignment.AcrfDocDAO;
import gov.ny.state.doh.bhae.nyehms.web.spring.dao.assignment.AcrfDocLabsDAO;
import gov.ny.state.doh.bhae.nyehms.web.spring.dao.assignment.PcrfDocDAO;
import gov.ny.state.doh.bhae.nyehms.web.spring.dao.jdbc.ConnectionDAO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.CodedAcrfTestResultVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.CodedAcrfTestVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.CodedParentTestTypeVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.acrf.AcrfFormVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.acrf.AcrfRidrVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.acrf.CodedArvMedicationsVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.acrf.PcrfSubstanceDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.acrf.RawAcrfCd4TstVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.acrf.RawAcrfDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.acrf.RawAcrfLabsVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.enums.AppConstants;
import gov.ny.state.doh.bhae.nyehms.web.spring.exception.NyehmsException;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.assignment.AssignmentTransactionHelper;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonExceptionHandler;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.UnexpectedRollbackException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

@Service
public class ACRFDocumentServiceImpl implements ACRFDocumentService{
	
	
	private static final Logger log = LoggerFactory.getLogger(ACRFDocumentServiceImpl.class);
	private String nyehmsLog=AppConstants.nyehmLogDebugString.getName();
	
	@Autowired
	private CommonExceptionHandler commonExceptionHandler;
	
	@Autowired
	private AcrfDocDAO acrfDocDAO;
	@Autowired
	private PcrfDocDAO pcrfDocDAO;
	@Autowired
	private AcrfDocLabsDAO acrfLabDAO;		
	@Autowired
	private AssignmentTransactionHelper assignmentTransactionHelper;
	@Autowired
	private ProcessACRFForm processACRFForm;
	
	@Autowired
	private ConnectionDAO connectionDAO;
	
	
	
	@Autowired
	NyehmsWorkerRegionsViewDAO nyehmsWorkerRegionsViewDAO;
	
	@Autowired CommonDocumentService commonDocumentService;
	
	@Override
	public RawAcrfDocVO getRawACRFDoc(String rawAcrfDocId, 
									String documentType, BindingResult result, 
									UserObject userObject, 
									String sessionID) {
		RawAcrfDocVO rawAcrfDoc=null;
		boolean errorFlag=false;
		log.debug("{}  rawAcrfDocId {}",nyehmsLog, rawAcrfDocId);
		try{
			
			if("PCRF".equals(documentType) || "NBS_PCRF".equals(documentType)){
				rawAcrfDoc= pcrfDocDAO.getRawPCRFDoc(rawAcrfDocId);
			}else{
				rawAcrfDoc= acrfDocDAO.getRawACRFDoc(rawAcrfDocId);
			}
			
		} catch (DataAccessException e) {
			errorFlag=true;
			log.error("ACRF Doc Id {}", rawAcrfDocId);
			log.error(nyehmsLog+"  "+ e.getMessage());
			
			commonExceptionHandler.handleException(e, userObject,sessionID);
		} catch (Exception e) {
			errorFlag=true;
			log.error("ACRF Doc Id  {}",rawAcrfDocId);
			log.error(nyehmsLog+"  "+ e.getMessage());
			commonExceptionHandler.handleException(e, userObject,sessionID);
		}finally{						
			if(errorFlag)
				result.addError(new ObjectError("SQLError", "Transaction Failed. Please try again "));
		}
		
		return rawAcrfDoc;
	}
	
	
	@Override
	public void addRawACRFDoc(RawAcrfDocVO rawAcrfDoc, String documentType, 
							BindingResult result, 
							UserObject userObject, 
							String sessionID) {		
		rawAcrfDoc.setWorkerId(userObject.getUserId());
		if("PCRF".equals(documentType)){
			pcrfDocDAO.addRawPCRFDoc(rawAcrfDoc);
		}else{
			log.error("ACRF received date : {}, documentType: {}, documentId: {}", rawAcrfDoc.getReceivedDate(), documentType, rawAcrfDoc.getDocumentId());
			acrfDocDAO.addRawACRFDoc(rawAcrfDoc);
		}
			
//			if(errorFlag)
	}
	
	
	@Override
	public void updateRawACRFDoc(AcrfFormVO acrfFormVo, String documentType, 
								BindingResult result, 
								UserObject userObject, 
								String sessionID) {
			RawAcrfDocVO rawAcrfDoc = acrfFormVo.getRawAcrfDoc();
			rawAcrfDoc.setWorkerId(userObject.getUserId());
			log.debug("{}  Updating with Doc ID {}",nyehmsLog,rawAcrfDoc.getAcrfSrcId());
			
			if("PCRF".equals(documentType)){
				pcrfDocDAO.updateRawPCRFDoc(rawAcrfDoc);
			}else{
				log.error("ACRF received date : {}, documentType: {}, documentId: {}", rawAcrfDoc.getReceivedDate(), documentType, rawAcrfDoc.getDocumentId());
				acrfDocDAO.updateRawACRFDoc(rawAcrfDoc);
			}
			
//			if(errorFlag)
	}
	
	@Override
	public void updateSubStanceList(int documentId, List<PcrfSubstanceDocVO> subStanceList, String substanceName, BindingResult result, UserObject userObject) {
		
		Map<String, Object> inputParameterMap = new HashMap<String, Object>();
		
		inputParameterMap.put("PCRF_SECTION", substanceName);
		inputParameterMap.put("DOCUMENT_ID", documentId);
		
		log.debug("Saving the Substance data for {}", substanceName);
		
		pcrfDocDAO.deleteSubStance(inputParameterMap);
		
		List<PcrfSubstanceDocVO> actualSubstanceList = new ArrayList<>();
		
		for (PcrfSubstanceDocVO pcrfSubstanceDocVO : subStanceList) {
			
			if(CommonUtil.isNotNullAndEmpty(pcrfSubstanceDocVO.getSubStancecode())){
				actualSubstanceList.add(pcrfSubstanceDocVO);
			}
		}
		if(actualSubstanceList.size() > 0) {
			inputParameterMap.put("SUBSTANCE_LIST", actualSubstanceList);
			pcrfDocDAO.addSubStanceList(inputParameterMap);
		}
		
	}
	
	@Override
	public void updateScreenConditionsList(int documentId, List<PcrfSubstanceDocVO> bpScreenConditionsList, String conditionName,
			BindingResult result, UserObject userObject) {
		
		Map<String, Object> inputParameterMap = new HashMap<String, Object>();
		
		inputParameterMap.put("PCRF_SECTION", conditionName);
		inputParameterMap.put("DOCUMENT_ID", documentId);
		
		
		log.debug("Saving the Substance data for {}", conditionName);
		
		pcrfDocDAO.deleteSubStance(inputParameterMap);
		
		List<PcrfSubstanceDocVO> actualSubstanceList = new ArrayList<>();
		
		for (PcrfSubstanceDocVO pcrfSubstanceDocVO : bpScreenConditionsList) {
			
			if(CommonUtil.isNotNullAndEmpty(pcrfSubstanceDocVO.getSubStancecode())){
				actualSubstanceList.add(pcrfSubstanceDocVO);
			}
		}
		if(actualSubstanceList.size() > 0) {
			inputParameterMap.put("CONDITIONS_LIST", actualSubstanceList);
			pcrfDocDAO.addConditionsList(inputParameterMap);
		}
		
	}
	
	@Override
	public boolean addRawACRFLabsList(AcrfFormVO acrfFormVo, String documentType,
								BindingResult result, 
								UserObject userObject, 
								String sessionID) {
		
		boolean hasLabDataChange = false;
//		boolean errorFlag=false;
		// Loop all records and insert.
//		try{			
			List<RawAcrfLabsVO> rawAcrfLabsList = acrfFormVo.getRawAcrfLabs();
			int documentId=acrfFormVo.getRawAcrfDoc().getDocumentId();
			
			Map<String, Object> inputParameterMap = new HashMap<String, Object>();
			
			if("PCRF".equals(documentType)){
				inputParameterMap.put("tableName", "RAW_PCRF_LABS");
				inputParameterMap.put("testCodeColumn", "PCRF_TEST_CODE");
				inputParameterMap.put("resultCodeColumn", "PCRF_RESULT_CODE");
			}else{
				inputParameterMap.put("tableName", "RAW_ACRF_LABS");
				inputParameterMap.put("testCodeColumn", "ACRF_TEST_CODE");
				inputParameterMap.put("resultCodeColumn", "ACRF_RESULT_CODE");
			}
			
			for(RawAcrfLabsVO rawAcrfLabs : rawAcrfLabsList){		
				
				rawAcrfLabs.setDocumentId(documentId);				
				int resultCode=0;
				log.debug("{} Result Code : {}",nyehmsLog,  rawAcrfLabs.getAcrfResultCode());
				if(rawAcrfLabs.getAcrfResultCode() !=null && !"".equals(rawAcrfLabs.getAcrfResultCode())){
					resultCode=Integer.parseInt(rawAcrfLabs.getAcrfResultCode());
				}
				
				log.debug("{}  DocID: {}",nyehmsLog,rawAcrfLabs.getAcrfSrcId()
						+" Parent Test ::"+rawAcrfLabs.getParentTestType()
						+" Test Code :: "+rawAcrfLabs.getAcrfTestCode()
						+" Result Code :: "+rawAcrfLabs.getAcrfResultCode());
				
				if(resultCode > 0 || ("04".equals(rawAcrfLabs.getParentTestType()) || "05".equals(rawAcrfLabs.getParentTestType())) && CommonUtil.isNotNullAndEmpty(rawAcrfLabs.getAcrfTestCode())) {	
					log.debug(nyehmsLog+"  Inserting Raw ACRF Labs with : " + rawAcrfLabs.getAcrfResultCode());
					
					inputParameterMap.put("rawAcrfLabs", rawAcrfLabs);
					
					acrfLabDAO.addRawACRFLabs(inputParameterMap);
					hasLabDataChange = true;
				}
			}
//			if(errorFlag)
			
			return hasLabDataChange;
		
	}
	
	@Override
	public boolean addRawArvMedData(AcrfFormVO acrfFormVO,  String documentType,
			BindingResult result, UserObject userObject, String sessionID){
		boolean hasArvMedChange = false;
		
		List<CodedArvMedicationsVO> rawAcrfLabsList = acrfFormVO.getArvMedicationsList();
		
		int documentId=acrfFormVO.getRawAcrfDoc().getDocumentId();
		
		Map<String, Object> inputParameterMap = new HashMap<String, Object>();
		
		if("PCRF".equals(documentType)){
			inputParameterMap.put("tableName", "RAW_PCRF_ARVS");
			inputParameterMap.put("CRF_COLUMN_ID", "PCRF_DOC_ID");
		}else{
			inputParameterMap.put("tableName", "RAW_ACRF_ARVS");
			inputParameterMap.put("CRF_COLUMN_ID", "ACRF_DOC_ID");
		}
		
		for(CodedArvMedicationsVO codedArvMedicationsVO : rawAcrfLabsList){
			
			codedArvMedicationsVO.setDocumentId(documentId);
			
			int resultCode=0;
			if(codedArvMedicationsVO.getArvUseType() !=null){
				
				inputParameterMap.put("codedArvMedicationsVO", codedArvMedicationsVO);
				
				resultCode = acrfLabDAO.addRawArvMedication(inputParameterMap);
			}
			
			
			if(resultCode>0) {	
				hasArvMedChange = true;
			}
		}
		
		return hasArvMedChange;
	}
	
	@Override
	public boolean addRawAcrfRidrData(AcrfFormVO acrfFormVO,
			BindingResult result, UserObject userObject, String sessionID){
		boolean hasAcrfRidrChange = false;
		
		List<AcrfRidrVO> acrfRidrList = acrfFormVO.getAcrfRidrList();
		
		
		int documentId=acrfFormVO.getRawAcrfDoc().getDocumentId();
		
		for(AcrfRidrVO acrfRidrVO : acrfRidrList){
			
			acrfRidrVO.setDocumentId(documentId);
			
			int resultCode=0;
			if(CommonUtil.isNotNullAndEmpty(acrfRidrVO.getDUPLICATE_STATUS())){
				
				resultCode = acrfLabDAO.addRawAcrfRidrInfo(acrfRidrVO);
			}
			
			
			if(resultCode>0) {	
				hasAcrfRidrChange = true;
			}
		}
		
		return hasAcrfRidrChange;
	}
	
	@Override
	public List<CodedParentTestTypeVO> getCodedParentTestType(BindingResult result, 
															UserObject userObject, 
															String sessionID) {
		boolean errorFlag=false;
		List<CodedParentTestTypeVO> list= new ArrayList<CodedParentTestTypeVO>();
		try{
			list= acrfLabDAO.getCodedParentTestTypes();
		} catch (DataAccessException e) {
			errorFlag=true;
			log.error(nyehmsLog+"  "+ e.getMessage());
			commonExceptionHandler.handleException(e, userObject,sessionID);
		} catch (Exception e) {
			errorFlag=true;
			log.error(nyehmsLog+"  "+ e.getMessage());
			commonExceptionHandler.handleException(e, userObject,sessionID);
		}finally{						
			if(errorFlag)
				result.addError(new ObjectError("SQLError", "Transaction Failed. Please try again "));
		}
		return list;
	}
	
	
	@Override
	public boolean addACRFDoc(AcrfFormVO acrfFormVO,  String documentType, String actionName, boolean havingNoErrorsinForm, String assignmentId, 
			BindingResult result, 
			UserObject userObject, 
			String sessionID, String closureCateogry){
		
		boolean errorFlag=false;
		boolean hasLabDataChange = false;
		try{
			hasLabDataChange = assignmentTransactionHelper.addACRFDocTrans(acrfFormVO, documentType, assignmentId,  actionName, havingNoErrorsinForm, result, userObject, sessionID, "SURV_ASS", closureCateogry);
		}catch (NyehmsException e) {
			errorFlag=true;
			log.error(nyehmsLog+"  "+ e.getMessage());
			commonExceptionHandler.handleException(e, userObject,sessionID);
		} catch(UnexpectedRollbackException e){
			errorFlag=true;
			commonExceptionHandler.handleException(e, userObject,sessionID);
			log.error(nyehmsLog+" UnexpectedRollbackException :::  " + e.toString());			
		}catch (Exception e) {
			errorFlag=true;
			log.error(nyehmsLog+"  "+ e.getMessage());
			commonExceptionHandler.handleException(e, userObject,sessionID);
		}finally{						
			if(errorFlag)
				result.addError(new ObjectError("SQLError", "Transaction Failed. Please try again "));
		}
		return hasLabDataChange;
	}
	
	@Override
	public void addACRFFormAndAssociateToStateno(String acrfReferenceRowId, AcrfFormVO acrfFormVO, String testType, 
			String stateno, BindingResult bindingResult, UserObject userObject){
		
		
		boolean errorFlag=false;
		int parentDocumentId  = acrfFormVO.getRawAcrfDoc().getDocumentId();
				
		
		try{
			assignmentTransactionHelper.addACRFFormAndAssociateToStateno(acrfReferenceRowId, acrfFormVO, testType, stateno, bindingResult, userObject, parentDocumentId);
			
			if(parentDocumentId == 0){
				commonExceptionHandler.inspectDBTransacionResult(0, "Successfully associated the ACRF document to "+stateno, bindingResult);
			}else{
				commonExceptionHandler.inspectDBTransacionResult(0, "Successfully edited the "+testType+" document", bindingResult);
			}
			
		} catch(DataAccessException dataAccessException){
			errorFlag=true;
			log.error("{} dataAccessException ::: {}",nyehmsLog, dataAccessException.getMessage());		
			//commonExceptionHandler.inspectDBTransacionResult(-1, "Failed to associate the ACRF document to NYEHMS Stateno : "+stateno, bindingResult);
		}catch (NyehmsException e) {
			errorFlag=true;
			log.error(nyehmsLog+"  "+ e.getMessage());
		} catch(UnexpectedRollbackException e){
			errorFlag=true;
			log.error("{} UnexpectedRollbackException ::: {}",nyehmsLog, e.toString());			
		}catch (Exception e) {
			errorFlag=true;
			log.error(nyehmsLog+"  "+ e.getMessage());
		}finally{						
			if(errorFlag){
				
				
				
				if(parentDocumentId == 0){
					
					log.error("Failed to associated the ACRF document to stateno {}"+ stateno);
					log.error("Test Type  {}", testType);
					log.error("ACRF Reference Source Id {}",acrfReferenceRowId);
					
					commonExceptionHandler.inspectDBTransacionResult(-1, "Failed to associated the ACRF document to stateno, please contact helpline", bindingResult);
				}else{
					
					log.error("Failed while editing the ACRF document");
					log.error("Test Type {}", testType);
					log.error("ACRF Reference Source Id {}",acrfReferenceRowId);
					
					commonExceptionHandler.inspectDBTransacionResult(-1, "Failed while editing the "+testType+" document, please contact helpline", bindingResult);
				}
				
			}
		}
	}
	
	@Override	
	public boolean updateACRFDoc(AcrfFormVO acrfFormVO,  String documentType, String actionName, boolean havingNoErrorsinForm,
								BindingResult result, 
								UserObject userObject, 
								String sessionID, String closureCategory) {		
		boolean errorFlag=false;
		
		boolean hasLabDataChange = false;
		try{
			hasLabDataChange = assignmentTransactionHelper.updateACRFDocTrans(acrfFormVO, documentType, actionName, havingNoErrorsinForm, result, userObject, sessionID, closureCategory);
		}catch(UnexpectedRollbackException e){
			errorFlag=true;
			commonExceptionHandler.handleException(e, userObject,sessionID);
			log.error("{} UnexpectedRollbackException :::  {}",nyehmsLog,e.toString());			
		}catch (Exception e) {
			errorFlag=true;
			log.error(nyehmsLog+"  "+ e.getMessage());
			commonExceptionHandler.handleException(e, userObject,sessionID);
		}finally{						
			if(errorFlag)
				result.addError(new ObjectError("SQLError", "Transaction Failed. Please try again "));
		}		
		return hasLabDataChange;
	}
	
	@Override
	public boolean addRawAcrfCd4Data(AcrfFormVO acrfFormVO,  String documentType,
								BindingResult result, 
								UserObject userObject, 
								String sessionID) {
		boolean hasLabCd4Changes = false;
//		boolean errorFlag=false;
		List<RawAcrfCd4TstVO> rawAcrfCd4TstList = acrfFormVO.getRawAcrfCd4Tst();
		log.debug(nyehmsLog+"  Processing CD$Data for Save. Size : "+rawAcrfCd4TstList.size());
//		try{
			int documentId= 0;
			
			Map<String, Object> inputParameterMap = new HashMap<String, Object>();
			
			if("PCRF".equals(documentType)){
				inputParameterMap.put("tableName", "RAW_PCRF_CD4");
			}else{
				inputParameterMap.put("tableName", "RAW_ACRF_CD4");
			}
			
			for(RawAcrfCd4TstVO rawAcrfCd4Tst:rawAcrfCd4TstList ){
				documentId= acrfFormVO.getRawAcrfDoc().getDocumentId();
				log.debug("{}  Inserting RAWCD4Test Count: {}  ACRFDocID ::  {}",nyehmsLog, rawAcrfCd4Tst.getCd4Count(),documentId);
				//Set the ACRF DOC ID			
				rawAcrfCd4Tst.setDocumentId(documentId);
				//Save CD4Test Record
				if(//(rawAcrfCd4Tst.isDefaultCd4()) || 
						(rawAcrfCd4Tst.getCd4Count() != null || rawAcrfCd4Tst.getCd4Percent() != null)){
					
					inputParameterMap.put("rawAcrfCd4Tst", rawAcrfCd4Tst);
					
					acrfLabDAO.addRawAcrfCd4Data(inputParameterMap);
					hasLabCd4Changes = true;
				}
			}
//			if(errorFlag)
			return hasLabCd4Changes;
		
	}
	
	@Override
	public List<RawAcrfCd4TstVO> getRawAcrfCd4Data(String rawAcrfDocId, 
												String documentType, int acrfRevison, BindingResult result, 
												UserObject userObject, 
												String sessionID) {
		boolean errorFlag=false;
		List<RawAcrfCd4TstVO>  rawAcrfCd4TstList = new ArrayList<RawAcrfCd4TstVO>();
		try{
			
			String tableName = "";
			
			if("PCRF".equals(documentType)){
				tableName = "RAW_PCRF_CD4";
			}else{
				tableName = "RAW_ACRF_CD4";
			}
			
			Map<String, Object> inputParameterMap = new HashMap<String, Object>();
			
			inputParameterMap.put("tableName", tableName);
			inputParameterMap.put("documentId", rawAcrfDocId);
			
			rawAcrfCd4TstList=acrfLabDAO.getRawAcrfCd4Data(inputParameterMap);
			if(rawAcrfCd4TstList.size() == 0){
				rawAcrfCd4TstList.add(new RawAcrfCd4TstVO());
			}else {
				
				boolean hasFirstCD4 = false;
				boolean hasSecondCD4 = false;
				boolean hasThirdCD4 = false;
				
				for (RawAcrfCd4TstVO rawAcrfCd4TstVO : rawAcrfCd4TstList) {
					
					if("1".equals(rawAcrfCd4TstVO.getClosestToCurrentDx())) {
						hasFirstCD4 = true;
					}else if("2".equals(rawAcrfCd4TstVO.getClosestToCurrentDx())) {
						hasSecondCD4 = true;
					}else if("3".equals(rawAcrfCd4TstVO.getClosestToCurrentDx())) {
						hasThirdCD4 = true;
					}
				}
				
				if(!hasFirstCD4) {
					rawAcrfCd4TstList.add(0, new RawAcrfCd4TstVO());
				}
				if(acrfRevison < 2023) {
				if(!hasSecondCD4) {
					rawAcrfCd4TstList.add(1, new RawAcrfCd4TstVO());
				}
				if(!hasThirdCD4) {
					rawAcrfCd4TstList.add(2, new RawAcrfCd4TstVO());
				}
				}
				
				
				
				
				
			}
		}catch (DataAccessException e) {
			errorFlag=true;
			log.error(nyehmsLog+"  "+ e.getMessage());
			commonExceptionHandler.handleException(e, userObject,sessionID);
		} catch (Exception e) {
			errorFlag=true;
			log.error(nyehmsLog+"  "+ e.getMessage());
			commonExceptionHandler.handleException(e, userObject,sessionID);
		}finally{						
			if(errorFlag)
				result.addError(new ObjectError("SQLError", "Transaction Failed. Please try again "));
		}
		return rawAcrfCd4TstList;
	}
	
	
	@Override
	public AcrfFormVO getAcrfFormVO(String rawAcrfDocId, String documentType,  
									BindingResult result, 
									UserObject userObject, 
									String sessionID) {		
		//Fetch all 3 table data, ACRF, LABS, CD4 and prepare main envelope object for print end.
		//Main envelope object.
		AcrfFormVO acrfFormVO = new AcrfFormVO();
		//Fetch ACRF DOC for given doc id.
		RawAcrfDocVO rawAcrfDocFetch=getRawACRFDoc(rawAcrfDocId, documentType, result,userObject, sessionID);
		
		//Fetch Lab Results for given doc Id.
		List<RawAcrfLabsVO> rawACRFLabsListFetch = getRawACRFLabs(rawAcrfDocId, documentType, result,userObject, sessionID);
		//Fetch CD4 Results for a given doc Id.
		List<RawAcrfCd4TstVO>  rawAcrfCd4TstListFetch = getRawAcrfCd4Data(rawAcrfDocId, documentType, rawAcrfDocFetch.getAcrfRevison(), result,userObject, sessionID);
		
		//Fetching Arv medications data
		List<CodedArvMedicationsVO>  arvMedicationsList = getRawArvMedicationsData(rawAcrfDocId, documentType, result,userObject, sessionID);
		
		if(!"PCRF".equals(documentType)){
			//Fetching RIDR data
			List<AcrfRidrVO>  acrfRidrList = getRawAcrfRidrData(rawAcrfDocId, documentType, result,userObject, sessionID);
			
			acrfFormVO.setAcrfRidrList(acrfRidrList);
		}		
		
		//Add all the individual objects to ACRF Form VO envelop.
		acrfFormVO.setRawAcrfDoc(rawAcrfDocFetch);
		acrfFormVO.setRawAcrfLabs(rawACRFLabsListFetch);
		acrfFormVO.setRawAcrfCd4Tst(rawAcrfCd4TstListFetch);
		acrfFormVO.setArvMedicationsList(arvMedicationsList);
		
		
		//Populate the Map objects for the LAB Tests in ACRF FORM VO main envelope.
		processACRFForm.populateLabTestInAcrfVO(documentType, acrfFormVO, result, userObject, sessionID);
		//If user already selected Lab data, preserve that data in ACRF FORM VO main envelope.		
		processACRFForm.preserveLabResults(acrfFormVO, result, userObject, sessionID);			
		return acrfFormVO;
	}
	
	private List<AcrfRidrVO> getRawAcrfRidrData(String rawAcrfDocId,
			String documentType, BindingResult result, UserObject userObject, String sessionID) {
		boolean errorFlag=false;
		List<AcrfRidrVO>  acrfRidrList = new ArrayList<AcrfRidrVO>();
		try{
			acrfRidrList=acrfLabDAO.getRawAcrfRidrData(rawAcrfDocId);
		}catch (DataAccessException e) {
			errorFlag=true;
			log.error(nyehmsLog+"  "+ e.getMessage());
			commonExceptionHandler.handleException(e, userObject,sessionID);
		} catch (Exception e) {
			errorFlag=true;
			log.error(nyehmsLog+"  "+ e.getMessage());
			commonExceptionHandler.handleException(e, userObject,sessionID);
		}finally{						
			if(errorFlag)
				result.addError(new ObjectError("SQLError", "Transaction Failed. Please try again "));
		}
		return acrfRidrList;
	}


	private List<CodedArvMedicationsVO> getRawArvMedicationsData(
			String rawAcrfDocId, String documentType, BindingResult result, UserObject userObject,
			String sessionID) {
		boolean errorFlag=false;
		List<CodedArvMedicationsVO>  arvMedicationsList = new ArrayList<CodedArvMedicationsVO>();
		try{
			
			
			
			Map<String, Object> inputParameterMap = new HashMap<String, Object>();
			
			if("PCRF".equals(documentType)){
				inputParameterMap.put("tableName", "RAW_PCRF_ARVS");
				inputParameterMap.put("CRF_COLUMN_ID", "PCRF_DOC_ID");
			}else{
				inputParameterMap.put("tableName", "RAW_ACRF_ARVS");
				inputParameterMap.put("CRF_COLUMN_ID", "ACRF_DOC_ID");
				
			}
			
			
			
			
			inputParameterMap.put("documentId", rawAcrfDocId);
			
			
			
			arvMedicationsList=acrfLabDAO.getRawArvMedicationsData(inputParameterMap);
		}catch (DataAccessException e) {
			errorFlag=true;
			log.error(nyehmsLog+"  "+ e.getMessage());
			commonExceptionHandler.handleException(e, userObject,sessionID);
		} catch (Exception e) {
			errorFlag=true;
			log.error(nyehmsLog+"  "+ e.getMessage());
			commonExceptionHandler.handleException(e, userObject,sessionID);
		}finally{						
			if(errorFlag)
				result.addError(new ObjectError("SQLError", "Transaction Failed. Please try again "));
		}
		return arvMedicationsList;
	}


	@Override
	public boolean updateRawACRFLabs(AcrfFormVO acrfFormVO,  String documentType,
								BindingResult result, 
								UserObject userObject, 
								String sessionID) {
		
		boolean hasLabDataChange = false;
//		boolean errorFlag=false;
		List<RawAcrfLabsVO> rawAcrfLabsList=acrfFormVO.getRawAcrfLabs();
		log.debug(nyehmsLog+" Lab Records :: - "+rawAcrfLabsList.toString());		
//		try{
		Map<String, Object> inputParameterMap = new HashMap<String, Object>();
		
		if("PCRF".equals(documentType)){
			inputParameterMap.put("tableName", "RAW_PCRF_LABS");
			inputParameterMap.put("testCodeColumn", "PCRF_TEST_CODE");
			inputParameterMap.put("resultCodeColumn", "PCRF_RESULT_CODE");
		}else{
			inputParameterMap.put("tableName", "RAW_ACRF_LABS");
			inputParameterMap.put("testCodeColumn", "ACRF_TEST_CODE");
			inputParameterMap.put("resultCodeColumn", "ACRF_RESULT_CODE");
		}
		
		inputParameterMap.put("workerId", userObject.getWorkerId());
		
		
			for(RawAcrfLabsVO rawAcrfLabs : rawAcrfLabsList){			
				rawAcrfLabs.setDocumentId(acrfFormVO.getRawAcrfDoc().getDocumentId());	
				
				inputParameterMap.put("rawAcrfLabs", rawAcrfLabs);
				
				//Detect if user added any new Lab data while updating and if so, insert new data while updating old data		
				if(rawAcrfLabs.getLabSrcId()==0 && 
						(
								CommonUtil.isNotNullAndEmpty(rawAcrfLabs.getAcrfResultCode()) 
								||
								("05".equals(rawAcrfLabs.getParentTestType()) || ( "04".equals(rawAcrfLabs.getParentTestType()))) && CommonUtil.isNotNullAndEmpty(rawAcrfLabs.getAcrfTestCode())
						)
				){
					log.debug(nyehmsLog+ " New LAB Record Found while Updating. Inserting new record.....");					
						acrfLabDAO.addRawACRFLabs(inputParameterMap);			
						hasLabDataChange = true;
				}else if(rawAcrfLabs.getLabSrcId()>0){
					log.debug(nyehmsLog+ " LAB Record being updated..........");
					if(rawAcrfLabs.getAcrfTestCode() == null || "".equals(rawAcrfLabs.getAcrfTestCode())){
						
						inputParameterMap.put("labSrcId", rawAcrfLabs.getLabSrcId());
						
						acrfLabDAO.deleteRawACRFLabs(inputParameterMap);
						hasLabDataChange = true;
					}else{
						acrfLabDAO.updateRawACRFLabs(inputParameterMap);
					}
				}
			}
//			if(errorFlag)
			return hasLabDataChange;
		
	}

	@Override
	public boolean updateRawMedicationsData(AcrfFormVO acrfFormVO, String documentType, 
			BindingResult result, UserObject userObject, String sessionID){
		
		boolean hasArvMedicationChange = false;
		
		List<CodedArvMedicationsVO> arvMedicationsList =  acrfFormVO.getArvMedicationsList();
		
		int acrfDocId = acrfFormVO.getRawAcrfDoc().getDocumentId(); 
		
		Map<String, Object> inputParameterMap = new HashMap<String, Object>();
		
		if("PCRF".equals(documentType)){
			inputParameterMap.put("tableName", "RAW_PCRF_ARVS");
			inputParameterMap.put("CRF_COLUMN_ID", "PCRF_DOC_ID");
		}else{
			inputParameterMap.put("tableName", "RAW_ACRF_ARVS");
			inputParameterMap.put("CRF_COLUMN_ID", "ACRF_DOC_ID");
		}
		
		for (CodedArvMedicationsVO codedArvMedicationsVO : arvMedicationsList) {
			
			codedArvMedicationsVO.setDocumentId(acrfDocId);
			
			inputParameterMap.put("codedArvMedicationsVO", codedArvMedicationsVO);
			
			int resultCode = 0;
			if(CommonUtil.isNotNullAndEmpty(codedArvMedicationsVO.getArvSrcId())){
				if(codedArvMedicationsVO.getArvUseType()  == null){
					
					inputParameterMap.put("arvSrcId", codedArvMedicationsVO.getArvSrcId());
					
					
					resultCode = acrfLabDAO.deleteRawArvMedication(inputParameterMap);
				}else{
					
					acrfLabDAO.updateRawArvMedication(inputParameterMap);
				}

			}else if(CommonUtil.isNotNullAndEmpty(codedArvMedicationsVO.getArvUseType())){
				
				resultCode = acrfLabDAO.addRawArvMedication(inputParameterMap);
				
			}
			if(resultCode > 0){
				hasArvMedicationChange = true;
			}
		}
		return hasArvMedicationChange;
		
	}
	
	@Override
	public boolean updateRawAcrfRidrData(AcrfFormVO acrfFormVO,
			BindingResult result, UserObject userObject, String sessionID){
		
		boolean hasAcrfRidrChange = false;
		
		List<AcrfRidrVO> acrfRidrList =  acrfFormVO.getAcrfRidrList();
		
		int acrfDocId = acrfFormVO.getRawAcrfDoc().getDocumentId(); 
		
		for (AcrfRidrVO acrfRidrVO : acrfRidrList) {
			
			int resultCode = 0;
			if(CommonUtil.isNotNullAndEmpty(acrfRidrVO.getRIDR_ROWID())){
				if(CommonUtil.isNullOrEmpty(acrfRidrVO.getDUPLICATE_STATUS())){
					
					resultCode = acrfLabDAO.deleteRawAcrfRidrData(acrfRidrVO.getRIDR_ROWID());
				}else{
					acrfRidrVO.setDocumentId(acrfDocId);
					acrfLabDAO.updateRawAcrfRidrData(acrfRidrVO);
				}

			}else if(CommonUtil.isNotNullAndEmpty(acrfRidrVO.getDUPLICATE_STATUS())){
				
				acrfRidrVO.setDocumentId(acrfDocId);
				resultCode = acrfLabDAO.addRawAcrfRidrInfo(acrfRidrVO);
				
			}
			if(resultCode > 0){
				hasAcrfRidrChange = true;
			}
		}
		return hasAcrfRidrChange;
		
	}
	@Override
	public boolean updateRawAcrfCd4Data(AcrfFormVO acrfFormVO,  String documentType,
									BindingResult result, 
									UserObject userObject, 
									String sessionID) {
		boolean hasLabCd4Change = false;
//		boolean errorFlag=false;
		List<RawAcrfCd4TstVO> rawAcrfCd4TstList =  acrfFormVO.getRawAcrfCd4Tst();
		
		log.debug("{}  CD4 Record : --   {}",nyehmsLog,rawAcrfCd4TstList.toString());
		
		
		Map<String, Object> inputParameterMap = new HashMap<String, Object>();
		
		if("PCRF".equals(documentType)){
			inputParameterMap.put("tableName", "RAW_PCRF_CD4");
		}else{
			inputParameterMap.put("tableName", "RAW_ACRF_CD4");
		}
		inputParameterMap.put("workerId", userObject.getWorkerId());
		
//		try{
			for(RawAcrfCd4TstVO rawAcrfCd4Tst:rawAcrfCd4TstList){
				
				rawAcrfCd4Tst.setDocumentId(acrfFormVO.getRawAcrfDoc().getDocumentId());
			log.debug("{}   Updating CD 4 Test with Doc ID : {}  With CD4 ID : {}", nyehmsLog,rawAcrfCd4Tst.getDocumentId(),rawAcrfCd4Tst.getCd4SrcId());
				
				inputParameterMap.put("rawAcrfCd4Tst", rawAcrfCd4Tst);
				
				//Detect if user added any new CD4 data while updating and if so, insert new data while updating old data
				if(rawAcrfCd4Tst.getCd4SrcId()==0 && (
						//(rawAcrfCd4Tst.isDefaultCd4()) || 
						(rawAcrfCd4Tst.getCd4Count() != null || rawAcrfCd4Tst.getCd4Percent() != null))){
					log.debug(nyehmsLog+ " New CD4 Record Found while Updating. Inserting new record.....");
					
					acrfLabDAO.addRawAcrfCd4Data(inputParameterMap);
					hasLabCd4Change = true;
				}else if(rawAcrfCd4Tst.getCd4SrcId()>0){
					if((!rawAcrfCd4Tst.isDefaultCd4()) && (rawAcrfCd4Tst.getCd4Count() == null && rawAcrfCd4Tst.getCd4Percent() == null)){
						log.debug(nyehmsLog+ " CD4 Record being deleted..........");
						
						acrfLabDAO.deleteRawAcrfCd4Data(inputParameterMap);
						hasLabCd4Change = true;
					}else{
						log.debug(nyehmsLog+ " CD4 Record being updated..........");
						acrfLabDAO.updateRawAcrfCd4Data(inputParameterMap);
					}
					
				}
				
			}		
//			if(errorFlag)
			return hasLabCd4Change;
	}	
	
	
	@Override
	public List<RawAcrfLabsVO> getRawACRFLabs(String rawAcrfDocId, 
											String documentType, BindingResult result, 
											UserObject userObject, 
											String sessionID){
		// TODO Auto-generated method stub
		boolean errorFlag=false;
		List<RawAcrfLabsVO> rawACRFLabsList = new ArrayList<RawAcrfLabsVO>();
		try{
			log.debug("{} Fetching LAB Results for doc id {}", nyehmsLog, rawAcrfDocId);
			
			
			Map<String, Object> inputParameterMap = new HashMap<String, Object>();
			
			
			if("PCRF".equals(documentType)){
				inputParameterMap.put("tableName", "RAW_PCRF_LABS");
				inputParameterMap.put("testCodeColumn", "PCRF_TEST_CODE");
				inputParameterMap.put("resultCodeColumn", "PCRF_RESULT_CODE");
			}else{
				inputParameterMap.put("tableName", "RAW_ACRF_LABS");
				inputParameterMap.put("testCodeColumn", "ACRF_TEST_CODE");
				inputParameterMap.put("resultCodeColumn", "ACRF_RESULT_CODE");
			}
			
			
			inputParameterMap.put("documentId", rawAcrfDocId);
			
			rawACRFLabsList=acrfLabDAO.getRawACRFLabs(inputParameterMap);
		}catch (DataAccessException e) {
			errorFlag=true;
			log.error(nyehmsLog+"  "+ e.getMessage());
			commonExceptionHandler.handleException(e, userObject,sessionID);
		} catch (Exception e) {
			errorFlag=true;
			log.error(nyehmsLog+"  "+ e.getMessage());
			commonExceptionHandler.handleException(e, userObject,sessionID);
		}finally{						
			if(errorFlag)
				result.addError(new ObjectError("SQLError", "Transaction Failed. Please try again "));
		}
		return rawACRFLabsList;
	}
	
	@Override
	public List<CodedAcrfTestVO> getCodedAcrfTest(BindingResult result, 
												UserObject userObject, 
												String sessionID) { 
		boolean errorFlag=false;
		List<CodedAcrfTestVO> list = new ArrayList<CodedAcrfTestVO>();
		try{
			list=acrfLabDAO.getCodedAcrfResultTypes();
		} catch (DataAccessException e) {
			errorFlag=true;
			log.error(nyehmsLog+"  "+ e.getMessage());
			commonExceptionHandler.handleException(e, userObject,sessionID);
		} catch (Exception e) {
			errorFlag=true;
			log.error(nyehmsLog+"  "+ e.getMessage());
			commonExceptionHandler.handleException(e, userObject,sessionID);
		}finally{						
			if(errorFlag)
				result.addError(new ObjectError("SQLError", "Transaction Failed. Please try again "));
		}
		return list;
	}

	@Override
	public List<CodedAcrfTestResultVO> getCodedAcrfResultTypes(String parentTestType, 
															String acrfTestCode, 
															String condition, 
															int acrfRevison, 
															BindingResult result, 
															UserObject userObject, 
															String sessionID) { 
		boolean errorFlag=false;
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("parentTestType", parentTestType);
		map.put("acrfTestCode", acrfTestCode);
		map.put("whereCondition", condition);
		
		if(acrfRevison == 0) {
			acrfRevison = 2023;
		}
		map.put("acrfRevison", acrfRevison);
		
		List<CodedAcrfTestResultVO> listMap = new ArrayList<CodedAcrfTestResultVO>();
		try{
			listMap = acrfLabDAO.getCodedAcrfTestResultTypes(map);
		} catch (DataAccessException e) {
			errorFlag=true;
			log.error(nyehmsLog+"  "+ e.getMessage());
			commonExceptionHandler.handleException(e, userObject,sessionID);
		} catch (Exception e) {
			errorFlag=true;
			log.error(nyehmsLog+"  "+ e.getMessage());
			commonExceptionHandler.handleException(e, userObject,sessionID);
		}finally{						
			if(errorFlag)
				result.addError(new ObjectError("SQLError", "Transaction Failed. Please try again "));
		}
		return listMap;
	}
	
	@Override
	public void updateSurveillanceAssignmentWithAcrfDocId(AcrfFormVO acrfFormVO, String documentType, String documentId, BindingResult bindingResult, UserObject userObject, String sessionID) {
		
		Integer statusCode = -1;
		String transactionMessage = null;
		
		Map<Object, Object> docIdMap = new HashMap<Object, Object>();
		
		int acrfDocumentId = 0;
		
		docIdMap.put("documentId", documentId);
		docIdMap.put("lastUpdatedBy", userObject.getWorkerId());
		
		
			int resultCount = 0;
			if(acrfFormVO.getRawAcrfDoc() != null){
				acrfDocumentId = acrfFormVO.getRawAcrfDoc().getDocumentId();
				
				if (acrfFormVO.getReabstract().equals("Y")) {
					docIdMap.put("crfDocColumnName", "REAB_CRF_DOCUMENT_ID");
					commonDocumentService.writeDocumentTestType(acrfDocumentId, "REABSTRACT");
				} else {
				
					if("PCRF".equals(documentType)){
						docIdMap.put("crfDocColumnName", "PCRF_DOCUMENT_ID");
					}else{
						docIdMap.put("crfDocColumnName", "ACRF_DOCUMENT_ID");
					}
				}
				
				docIdMap.put("crfDocumentId", acrfDocumentId);
				
				if (acrfFormVO.getReabstract().equals("N")) {
					resultCount = acrfLabDAO.updateSurveillanceAssignmentWithAcrfDocId(docIdMap); 
				} else {
					resultCount = acrfLabDAO.updateSurveillanceAssignmentWithReabAcrfDocId(docIdMap); 
				}
			}
	
			if(resultCount > 0){
				
				transactionMessage = "Successfully updated Surveillance table with Acrf Id";
				
				statusCode = 0;
				
				if(log.isDebugEnabled()){
					log.debug("Successfully updated the Surveillance table with ACRF Id");
				}
			}else{
				
					transactionMessage = "Failed to update Surveillance table with Acrf Id";
					log.error("Failed to update Surveillance table with Acrf Id for Assignment Id  {}",documentId);
				
				statusCode = -1;
				
				
			}
			commonExceptionHandler.inspectDBTransacionResult(statusCode, transactionMessage, bindingResult);
			

		
		
		
		
	}
	
	
	@Override
	public void discardACRFForm(String acrfDocId, String documentType, String assignmentId, String reabstract, BindingResult bindingResult, UserObject userObject, String sessionID) {
		
		Integer statusCode = -1;
		String transactionMessage = null;
		
		try{
		
			assignmentTransactionHelper.discardACRFForm(acrfDocId, documentType, assignmentId, reabstract, userObject, bindingResult);
				
		
		}catch(Exception e){
		
			
			transactionMessage = "Failed to discard Acrf Form, please contact helpline";
			
			log.error("Failed to discard Acrf Form for Assignment Id {}",assignmentId);
		
			log.error(e.getMessage());
			
			statusCode = -1;
			
			commonExceptionHandler.inspectDBTransacionResult(statusCode, transactionMessage, bindingResult);
			
		}
		
		
	}
	
	
	@Override
	public List<Map<String, String>> getStandardFacilityDetails(){
		
		String[] eharsFacUids =  acrfLabDAO.getEharsStandardFacilityDetails();
		
		List<Map<String, String>> eharsFacilities = new ArrayList<Map<String, String>>();
		
		Connection connection = connectionDAO.getSqlServerConnectionObj();
		
		Statement statement = null;
		ResultSet resultSet = null;

		try {
			if (connection != null) {
				
				if(log.isDebugEnabled()){
					log.debug("SQL server database Connection is available {}",connection);
				}
				statement = connection.createStatement();
				
				StringBuilder queryString =new StringBuilder("select facility_uid, name1 from facility_code where facility_uid in ('");
				
				queryString.append(StringUtils.join(eharsFacUids,"','"));
				queryString.append("') order by name1");
				resultSet = statement.executeQuery(queryString.toString());
				
				while(resultSet.next()){
					Map<String, String> _eharsFacility = new HashMap<String, String>();
					_eharsFacility.put("facility_uid", resultSet.getString(1));
					_eharsFacility.put("facility_name", resultSet.getString(2));
					eharsFacilities.add(_eharsFacility);
					
				}
			}
		}catch (SQLException sqlException) {

			log.error("Exception while retrieving the Ehars data from SQL Server {}"
					, sqlException.getMessage());
		} finally {
			try {
				if (connection != null) {
					connection.close();
				}
				if (statement != null) {
					statement.close();
				}
				if (resultSet != null) {
					resultSet.close();
				}
			} catch (SQLException sqlException) {
				log.error("Exception while closing the connection {}", sqlException.getMessage());
			}
		}
		return eharsFacilities;
	}
	
	
	@Override
	public List<Map<String, String>> getCodedValues(String fieldName){
		
		
		List<Map<String, String>> codedValues = new ArrayList<Map<String, String>>();
		
		Connection connection = connectionDAO.getSqlServerConnectionObj();
		
		Statement statement = null;
		ResultSet resultSet = null;

		try {
			if (connection != null) {
				
				if(log.isDebugEnabled()){
					log.debug("SQL server database Connection is available "+connection);
				}
				statement = connection.createStatement();
				
				StringBuilder queryString =new StringBuilder("select lookup_cd, lookup_name from lookup_code where lookup_type = '"+fieldName+"'");
				
				
				queryString.append(" order by lookup_name");
				resultSet = statement.executeQuery(queryString.toString());
				
				while(resultSet.next()){
					Map<String, String> _codedValue = new HashMap<String, String>();
					_codedValue.put("lookup_cd", resultSet.getString(1));
					_codedValue.put("lookup_name", resultSet.getString(2));
					codedValues.add(_codedValue);
					
				}
			}
		}catch (SQLException sqlException) {

			log.error("Exception while retrieving the Ehars data from SQL Server {}",
					sqlException.getMessage());
		} finally {
			try {
				if (connection != null) {
					connection.close();
				}
				if (statement != null) {
					statement.close();
				}
				if (resultSet != null) {
					resultSet.close();
				}
			} catch (SQLException sqlException) {
				log.error("Exception while closing the connection {}",
						 sqlException.getMessage());
			}
		}
		return codedValues;
	}
	
	@Override
	public List<Map<String, String>> getDocumentSourceList(){
		
		return acrfLabDAO.getDocumentSourceList();
	}
	
	@Override
	public List<Map<String, String>> getAcrfOtherIdTypeList(){
		
		return acrfLabDAO.getAcrfOtherIdTypeList();
	}
	
	@Override
	public List<Map<String, String>> getCompleteRegionsList(){
		
		return nyehmsWorkerRegionsViewDAO.getSearchRegionDescription();
	}
	
	@Override
	public List<String> getArvUserTypeList(){
		return acrfDocDAO.getArvUserTypeList();
	}
	
	@Override
	public RawAcrfDocVO getAcrfFormDataFromAcrf(String stateNo){
		
		return acrfDocDAO.getAcrfFormDataFromAcrf(stateNo);
		
	}
	
	@Override
	public List<Map<String, String>> getACRFReferenceData(String referenceColumn){
		
		return acrfLabDAO.getACRFReferenceData(referenceColumn);
		
	}
	
	@Override
	public List<Map<String, String>> getClinicalLaboratoryDetails(){
		return acrfLabDAO.getClinicalLaboratoryDetails();
	}
	
	@Override
	public List<PcrfSubstanceDocVO> getScreenSubstanceData(String docId){
		return pcrfDocDAO.getScreenSubstanceData(docId);
	}
	@Override
	public List<PcrfSubstanceDocVO> getBpUseSubstanceList(String docId){
		return pcrfDocDAO.getBpUseSubstanceList(docId);
	}
	
	@Override
	public List<PcrfSubstanceDocVO> getBpScreenSubstanceList(String docId){
		return pcrfDocDAO.getBpScreenSubstanceList(docId);
	}
	@Override
	public List<PcrfSubstanceDocVO> getBpScreenConditionsList(String docId){
		return pcrfDocDAO.getBpScreenConditionsList(docId);
	}
	@Override
	public List<PcrfSubstanceDocVO> getBpDxScreenConditionsList(String docId){
		return pcrfDocDAO.getBpDxScreenConditionsList(docId);
	}
	@Override
	public List<PcrfSubstanceDocVO> getBpPregOutcomeList(String docId){
		
		if(CommonUtil.isNotNullAndEmpty(docId)) {
			return pcrfDocDAO.getBpPregOutcomeList(docId);
		}else {
			
			List<PcrfSubstanceDocVO> pcrfSubstanceList = new ArrayList<>(); 
			for(int i=1; i<=5;i++) {
				pcrfSubstanceList.add(new PcrfSubstanceDocVO());
			}
			
			
			return pcrfSubstanceList;
		}
		
	}


	@Override
	public void updatePregOutcomeList(int documentId, List<PcrfSubstanceDocVO> pregOutcomeList, String pregOutcome,
			BindingResult result, UserObject userObject) {
		
		Map<String, Object> inputParameterMap = new HashMap<String, Object>();
		
		inputParameterMap.put("PCRF_SECTION", pregOutcome);
		inputParameterMap.put("DOCUMENT_ID", documentId);
		
		log.debug("Saving the Substance data for {}", pregOutcome);
		
		pcrfDocDAO.deleteSubStance(inputParameterMap);
		
//		List<PcrfSubstanceDocVO> actualPregOutcomeList = new ArrayList<>();
		
			inputParameterMap.put("PREGOUTCOME_LIST", pregOutcomeList);
			pcrfDocDAO.addPregOutcomeList(inputParameterMap);
//		}
		
	}
	
}
