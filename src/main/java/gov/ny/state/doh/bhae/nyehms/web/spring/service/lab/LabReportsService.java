package gov.ny.state.doh.bhae.nyehms.web.spring.service.lab;

import java.sql.Array;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;


import gov.ny.state.doh.bhae.nyehms.web.spring.dao.jdbc.ConnectionDAO;
import gov.ny.state.doh.bhae.nyehms.web.spring.dao.lab.LabReportsDAO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.lab.ClinicalLaboratoryVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.lab.FeedbackReportOverallSummaryVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.lab.FeedbackReportStateSummaryVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.lab.FeedbackReportVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.lab.LabDetailsVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.lab.LabFacilitiesVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.lab.LabInstrumentDetailsVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.lab.LabInteractionHistoryVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.lab.LabLocalCodesMappingVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.lab.LabLoincMappingVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.lab.LabNewContactVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.lab.LabOpsReportsVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.lab.LabPermitsVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.lab.LabSubmissionTypeVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.lab.LabTestMappingVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.enums.AppConstants;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonExceptionHandler;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonUtil;
import oracle.jdbc.OracleConnection;


/**
* This class is service interface between the user data to Database(MyBatis related configuration)
* 
* It renders the data from Database in the form List, Int, Sting etc uinsg MyBatis configuation file 
*  
* @author  
* @version 
*/
@Service
public class LabReportsService {

	@Autowired
	private LabReportsDAO labReportsDAO ;
	
	@Autowired
	private ConnectionDAO connectionDAO ;
	
	@Autowired
	private CommonExceptionHandler commonExceptionHandler;
	
	@Autowired
	LabReportsTransactionHelper labReportsTransactionHelper;
	

	
	
	private static Logger log = LoggerFactory.getLogger(LabReportsService.class);
	

	private final static String ADD_NEW_LOCAL_CODES_AND_LINK_LOINC_CODES_SUCCESS_MESSAGE="LOINC codes has been linked to newly created Local Code";
	
	
	public FeedbackReportVO getFeedbackMedicalReport(String providerId,
			String startDate, String endDate, BindingResult bindingResult,
			UserObject userObject) {
		
		Map<Object, Object> inOutParameterMap = new HashMap<>();
		inOutParameterMap.put("p_provider_id", providerId);
		
		labReportsDAO.getFeedbackMedicalReport(inOutParameterMap);
		
		List<FeedbackReportOverallSummaryVO> medicalReportsOverallList =(List<FeedbackReportOverallSummaryVO>)inOutParameterMap.get("p_overall_summary");
		List<FeedbackReportStateSummaryVO> medicalReportsStateList =(List<FeedbackReportStateSummaryVO>)inOutParameterMap.get("p_state_summary");
		FeedbackReportVO feedbackReportVO = new FeedbackReportVO();
		feedbackReportVO.setFeedbackReportOverallSummaryVO(medicalReportsOverallList.get(0));
		feedbackReportVO.setFeedbackReportStateSummaryVO(medicalReportsStateList.get(0));
		return feedbackReportVO;
	}
	
	
	public List<ClinicalLaboratoryVO> getLabSearchResults(BindingResult bindingResult,UserObject userObject, String clia, LabFacilitiesVO labFacilitiesVO ) {
		String transactionMessage=null;
		Integer statusCode=null;
		Map<Object, Object> inOutParameterMap = new HashMap<>();
		inOutParameterMap.put("p_clia", clia);
		inOutParameterMap.put("p_pfi", labFacilitiesVO.getPfi());
		inOutParameterMap.put("p_lab_name", labFacilitiesVO.getLabName());
		inOutParameterMap.put("p_address", labFacilitiesVO.getAddress());
		inOutParameterMap.put("p_city", labFacilitiesVO.getCity());
		inOutParameterMap.put("p_state", labFacilitiesVO.getState());
		inOutParameterMap.put("p_zip", labFacilitiesVO.getZipCode());
		inOutParameterMap.put("p_country", labFacilitiesVO.getCountry());
		inOutParameterMap.put("p_submission_type", "");
		
		List<ClinicalLaboratoryVO> labaratoriesList =  labReportsDAO.getLabSearchResults(inOutParameterMap);
		
		return labaratoriesList;
	}


	public LabDetailsVO getLabDetails(BindingResult bindingResult,
			UserObject userObject, String pfi) {
		
		Map<Object, Object> inOutParameterMap = new HashMap<>();
		inOutParameterMap.put("p_pfi", pfi);
		
		List<LabDetailsVO> labDetailsList = new ArrayList<>();
		
		
		labReportsDAO.getLabDetails(inOutParameterMap);
		
		LabDetailsVO labDetailsVO = new LabDetailsVO();
		
		List<LabPermitsVO> labPermitsVO = (List<LabPermitsVO>)inOutParameterMap.get("p_permits");
		List<LabLoincMappingVO> labLoincMappingVO = (List<LabLoincMappingVO>)inOutParameterMap.get("p_loinc_codes");
		List<LabLocalCodesMappingVO> labLocalCodesMappingList = (List<LabLocalCodesMappingVO>)inOutParameterMap.get("p_local_codes");
		
		//Map<String,LabTestMappingVO> testMapCodeList = (Map<String,LabTestMappingVO>)inOutParameterMap.get("p_map_codes");
		
		
		List<LabTestMappingVO> testCodeMappingListFromDB = (List<LabTestMappingVO>)inOutParameterMap.get("p_map_codes");
		
		Map<String, List<LabTestMappingVO>> localCodeTotestCodesMappingList = new HashMap<>();
		
		
		for (LabTestMappingVO labTestMappingVO : testCodeMappingListFromDB) {
			
			
			String _localCodeMapId = labTestMappingVO.getLocalCodeMapId();

			
			if(localCodeTotestCodesMappingList.get(_localCodeMapId) != null){
				
				localCodeTotestCodesMappingList.get(_localCodeMapId).add(labTestMappingVO);
				
			}else{
				List<LabTestMappingVO> labTestMappingList = new ArrayList<>();
				labTestMappingList.add(labTestMappingVO);
				localCodeTotestCodesMappingList.put(_localCodeMapId, labTestMappingList);
			}
			
			
		}
		
		
		for (LabLocalCodesMappingVO labLocalCodesMappingVO : labLocalCodesMappingList) {
			
			String _localCodeMapId = labLocalCodesMappingVO.getLocalCodeMapId();
			
			if(localCodeTotestCodesMappingList.get(_localCodeMapId) != null){
				
				labLocalCodesMappingVO.setTestMappingDetails((List<LabTestMappingVO>)localCodeTotestCodesMappingList.get(_localCodeMapId));
				
			}else{
				labLocalCodesMappingVO.setTestMappingDetails(new ArrayList<LabTestMappingVO>());
			}
			
		}
		
		labDetailsVO.setLabLocalCodesMappingVO(labLocalCodesMappingList);
		labDetailsVO.setLabLoincMappingVO(labLoincMappingVO);
		labDetailsVO.setLabPermitsVO(labPermitsVO);
		
		labDetailsList.add(labDetailsVO);
		
		return labDetailsVO;
	}
	
	public List<Map<String, String>> getActiveAssociatedPermitCodeAndDescriptionDetails(String pfi, BindingResult result, UserObject userObject) {
			//Map<String, String> permitCodeAndDescriptionMap = new HashMap<String, String>();
			
			List<Map<String, String>> permitCodesAndDescriptionsList = labReportsDAO.getActiveAssociatedPermitCodesAndDescriptions(pfi);
		return permitCodesAndDescriptionsList;
	}
	
	public List<Map<String, String>> getPermitCodesAndDescriptionsForEdit(BindingResult result, UserObject userObject) {
			
			List<Map<String, String>> permitCodesAndDescriptionsList = labReportsDAO.getPermitCodesAndDescriptionsForEdit();
		return permitCodesAndDescriptionsList;
	}

	public List<Map<String, String>> getActiveAssociatedInstrumentCodeAndDescriptionDetails(long labPermitId, BindingResult result, UserObject userObject) {
			List<Map<String, String>> instrumentCodesAndDescriptionsList = labReportsDAO.getActiveAssociatedInstrumentCodesAndDescriptions(labPermitId);
		return instrumentCodesAndDescriptionsList;
	}
	
	public List<Map<String, String>> getInstrumentCodeAndDescriptionDetails(BindingResult result, UserObject userObject) {
			List<Map<String, String>> instrumentCodesAndDescriptionsList = labReportsDAO.getInstrumentCodesAndDescriptions();
		return instrumentCodesAndDescriptionsList;
	}
	
	public Map<String, String> getActiveAndNonAssociatedLabLoincDescriptionDetails(String pfi, BindingResult result, UserObject userObject) {
			Map<String, String> loincCodeAndDescriptionMap = new TreeMap<>();
			
			List<Map<String, String>> loincCodesAndDescriptionsList = labReportsDAO.getActiveAndNonAssociatedLabLoincDescriptionDetails(pfi);
			for(Map<String, String> permit : loincCodesAndDescriptionsList){
                            loincCodeAndDescriptionMap.put(permit.get("loincCd"), permit.get("loincCd")+" : "+ permit.get("loincDescription"));
			}
		return loincCodeAndDescriptionMap;
	}
	
	public Map<String, String> getActiveAssociatedLabLoincDescriptionDetails(String pfi, BindingResult result, UserObject userObject) {
			Map<String, String> loincCodeAndDescriptionMap = new TreeMap<>();
			
			List<Map<String, String>> loincCodesAndDescriptionsList = labReportsDAO.getActiveAssociatedLabLoincDescriptionDetails(pfi);
			for(Map<String, String> loinc : loincCodesAndDescriptionsList){
                            loincCodeAndDescriptionMap.put(loinc.get("loincCd"), loinc.get("loincCd")+" : "+ loinc.get("loincDescription"));
			}
		return loincCodeAndDescriptionMap;
	}
	
	public void updateLabPermitDetails(BindingResult bindingResult, UserObject userObject, LabPermitsVO labPermitsVO) {
		String transactionMessage=null;
		Integer statusCode;
		
		Map<Object, Object> inOutParameterMap = new HashMap<>();
		
		inOutParameterMap.put("labPermitId", labPermitsVO.getLabPermitId());
		inOutParameterMap.put("dateValidFrom", labPermitsVO.getDateValidFrom());
		inOutParameterMap.put("dateValidTill", labPermitsVO.getDateValidTill());
		inOutParameterMap.put("lastUpdatedBy", userObject.getWorkerId());
		inOutParameterMap.put("bhaeCertDate", labPermitsVO.getBhaeCertDate());
		inOutParameterMap.put("comments", labPermitsVO.getComments());
		
		int resultCount = labReportsDAO.updateLabPermitDetails(inOutParameterMap);
				
		if(resultCount > 0){
			transactionMessage = "Successfully updated the Lab Permit Details";
			statusCode = 0;
		}else{
			transactionMessage = "Unable to update the details, Please contact administrator";
			statusCode = -1;
		}
	
		
		commonExceptionHandler.inspectDBTransacionResult(statusCode, transactionMessage, bindingResult);
		
	}
	
	
	public void updateLabCommunicationDetails(BindingResult bindingResult, UserObject userObject, LabInteractionHistoryVO labInteractionHistoryVO) {
		String transactionMessage=null;
		Integer statusCode;
		
		Map<Object, Object> inOutParameterMap = new HashMap<>();
		
		inOutParameterMap.put("interactionId", labInteractionHistoryVO.getInteractionId());
		inOutParameterMap.put("communicationType", labInteractionHistoryVO.getCommunicationType());
		inOutParameterMap.put("interactionNotes", labInteractionHistoryVO.getInteractionNotes());
		inOutParameterMap.put("interactionDate", labInteractionHistoryVO.getInteractionDate());
		inOutParameterMap.put("issueResolved", labInteractionHistoryVO.getIssueResolved());
		
		int resultCount = labReportsDAO.updateLabCommunicationDetails(inOutParameterMap);
				
		if(resultCount > 0){
			transactionMessage = "Successfully updated the Lab Communication Details";
			statusCode = 0;
		}else{
			transactionMessage = "Unable to update the details, Please contact administrator";
			statusCode = -1;
		}

		commonExceptionHandler.inspectDBTransacionResult(statusCode, transactionMessage, bindingResult);	
	}
	
	public void changeCliaForPfi(BindingResult bindingResult,
			UserObject userObject, String clia, String pfi){
		String transactionMessage=null;
		Integer statusCode;
		
		Map<Object, Object> inOutParameterMap = new HashMap<>();
		
		inOutParameterMap.put("clia", clia);
		inOutParameterMap.put("pfi", pfi);
		inOutParameterMap.put("workerId", userObject.getWorkerId());
		
		
		int resultCount = labReportsDAO.changeCliaForPfi(inOutParameterMap);
				
		if(resultCount > 0){
			transactionMessage = "Successfully associated the PFI to a new CLIA : "+clia;
			statusCode = 0;
		}else{
			transactionMessage = "failed associate the PFI to a new CLIA, Please contact helpline";
			statusCode = -1;
		}
	
		
		commonExceptionHandler.inspectDBTransacionResult(statusCode, transactionMessage, bindingResult);
	}
	
	public void updateLabInstrumentDetails(BindingResult bindingResult, UserObject userObject, LabInstrumentDetailsVO labInstrumentDetailsVO) {
		String transactionMessage=null;
		Integer statusCode;
		
		
		Map<Object, Object> inOutParameterMap = new HashMap<>();
		
		inOutParameterMap.put("instrumentFlagActive", labInstrumentDetailsVO.getInstrumentFlagActive());
		inOutParameterMap.put("lpiId", labInstrumentDetailsVO.getLpiId());
		inOutParameterMap.put("lastUpdatedBy", userObject.getWorkerId());
		inOutParameterMap.put("instrumentDateValidFrom", labInstrumentDetailsVO.getDateValidFrom());
		inOutParameterMap.put("instrumentDateValidTill", labInstrumentDetailsVO.getDateValidTill());
		
		int resultCount = labReportsDAO.updateLabInstrumentDetails(inOutParameterMap);
		
		if(resultCount > 0){
			transactionMessage = "Successfully updated the Lab Instrument Details";
			statusCode = 0;
		}else{
			transactionMessage = "Unable to update the details, Please contact administrator";
			statusCode = -1;
		}
	
		
		commonExceptionHandler.inspectDBTransacionResult(statusCode, transactionMessage, bindingResult);
		
	}
	
	
	public int updateLabLoincCodeDetails(BindingResult bindingResult, UserObject userObject, LabLoincMappingVO labLoincMappingVO) {
		String transactionMessage=null;
		Integer statusCode;
		int resultCount = 0;
		boolean hasError = false;
		String dateValidFrom = labLoincMappingVO.getDateValidFrom();
		String dateValidTill = labLoincMappingVO.getDateValidTill();
		
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
    	try {
			Date dateFrom = sdf.parse(dateValidFrom);
			
			if(!"".equals(dateValidTill) && dateValidTill != null ){
				
				Date today = sdf.parse(sdf.format(new Date()));
				Date dateTo = sdf.parse(dateValidTill);
				
				if(dateFrom.compareTo(dateTo)>0){
					commonExceptionHandler.inspectDBTransacionResult(-1, "Start date should be prior to End date", bindingResult);
					hasError = true;
				}else if(today.compareTo(dateTo) > 0){
					commonExceptionHandler.inspectDBTransacionResult(-1, "End date can't be a past date", bindingResult);
					hasError = true;
				}
			}
			
		} catch (ParseException e) {
			
			commonExceptionHandler.inspectDBTransacionResult(-1, "Unable to update the LOINC details, please check the input data.", bindingResult);
			hasError = true;
			log.error("unable to parse the incoming data");
		}
    	
    	
    	if(!hasError){
		
			Map<Object, Object> inOutParameterMap = new HashMap<>();
			
			inOutParameterMap.put("flagActive", labLoincMappingVO.getFlagActive());
			inOutParameterMap.put("loincMapId", labLoincMappingVO.getLoincMapId());
			inOutParameterMap.put("loincDateValidFrom", dateValidFrom);
			inOutParameterMap.put("loincDateValidTill", dateValidTill);
			inOutParameterMap.put("lastUpdatedBy", userObject.getWorkerId());
			
			resultCount = labReportsDAO.updateLabLoincCodeDetails(inOutParameterMap);
			
			if(resultCount > 0){
				transactionMessage = "Successfully updated the Lab LOINC Details";
				statusCode = 0;
			}else{
				transactionMessage = "Unable to update the Lab LOINC details, Please contact helpline.";
				statusCode = -1;
			}
		
			
			if(statusCode == -1)throw new DataRetrievalFailureException(transactionMessage);
			
			commonExceptionHandler.inspectDBTransacionResult(statusCode, transactionMessage, bindingResult);
    	}
		return resultCount;
	}
	

	public void addNewPermitToFacility(LabPermitsVO labPermitsVO, BindingResult bindingResult,UserObject userObject){
		
		String transactionMessage=null;
		Integer statusCode;
		
		Map<Object, Object> inOutParameterMap = new HashMap<>();
		
		inOutParameterMap.put("permitCode", labPermitsVO.getPermitCode());
		inOutParameterMap.put("dateValidFrom", labPermitsVO.getDateValidFrom());
		inOutParameterMap.put("dateValidTill", labPermitsVO.getDateValidTill());
		inOutParameterMap.put("bhaeCertDate", labPermitsVO.getBhaeCertDate());
		inOutParameterMap.put("comments", labPermitsVO.getComments());
		inOutParameterMap.put("lastUpdatedBy", userObject.getWorkerId());
		inOutParameterMap.put("pfi", labPermitsVO.getPfi());
		
		 
		int resultCount = labReportsDAO.addNewPermitCodeToFacility(inOutParameterMap);
			
		if(resultCount > 0){
			transactionMessage = "Successfully added the new Lab Permit";
			statusCode = 0;
		}else{
			transactionMessage = "Unable to add the new Lab permit, Please contact administrator";
			statusCode = -1;
		}
	 
		commonExceptionHandler.inspectDBTransacionResult(statusCode, transactionMessage, bindingResult);
		
	
	}
	

	public void addNewCommunicationToFacility(LabInteractionHistoryVO labInteractionHistoryVO, BindingResult bindingResult,UserObject userObject){
		
		String transactionMessage=null;
		Integer statusCode;
		
		Map<Object, Object> inOutParameterMap = new HashMap<>();
		
		inOutParameterMap.put("communicationType", labInteractionHistoryVO.getCommunicationType());
	//	inOutParameterMap.put("issueResolved", labInteractionHistoryVO.getIssueResolved());
		inOutParameterMap.put("interactionDate", labInteractionHistoryVO.getInteractionDate());
		inOutParameterMap.put("interactionNotes", labInteractionHistoryVO.getInteractionNotes());
		inOutParameterMap.put("interactionBy", userObject.getWorkerId());		
		inOutParameterMap.put("pfi", labInteractionHistoryVO.getPfi());
		 
		int resultCount = labReportsDAO.addNewCommunicationToFacility(inOutParameterMap);
			
		if(resultCount > 0){
			transactionMessage = "Successfully added the new Lab Communication";
			statusCode = 0;
		}else{
			transactionMessage = "Unable to add the new Lab Communication, Please contact administrator";
			statusCode = -1;
		}
	 
		commonExceptionHandler.inspectDBTransacionResult(statusCode, transactionMessage, bindingResult);
	}
	
	
	public void addNewInstrumentToPermit(long labPermitId,long instrumentId, String dateValidFrom, String dateValidTill, BindingResult bindingResult,UserObject userObject){
		
		String transactionMessage=null;
		Integer statusCode;
		
		Map<Object, Object> inOutParameterMap = new HashMap<>();
		
		inOutParameterMap.put("labPermitId", labPermitId);
		inOutParameterMap.put("instrumentId", instrumentId);
		inOutParameterMap.put("dateValidTill", dateValidTill);
		inOutParameterMap.put("dateValidFrom", dateValidFrom);
		inOutParameterMap.put("lastUpdatedBy", userObject.getWorkerId());
		 
		int resultCount = labReportsDAO.addNewInstrumentToPermit(inOutParameterMap);
			
		if(resultCount > 0){
			transactionMessage = "Successfully added the new Instrument to the Existing Permit";
			statusCode = 0;
		}else{
			transactionMessage = "Unable to add the Instrument details to the Lab permit, Please contact administrator";
			statusCode = -1;
		}
	 
		commonExceptionHandler.inspectDBTransacionResult(statusCode, transactionMessage, bindingResult);
	}
	
	public void addNewLabLoincToFacility(LabLoincMappingVO labLoincMappingVO, BindingResult bindingResult,UserObject userObject){
		
		String transactionMessage=null;
		Integer statusCode;
		
		Map<Object, Object> inOutParameterMap = new HashMap<>();
		
		inOutParameterMap.put("pfi", labLoincMappingVO.getPfi());
		inOutParameterMap.put("loincCode", labLoincMappingVO.getLoincCode());
		inOutParameterMap.put("lastUpdatedBy", userObject.getWorkerId());
		inOutParameterMap.put("dateValidTill", labLoincMappingVO.getDateValidTill());
		inOutParameterMap.put("dateValidFrom", labLoincMappingVO.getDateValidFrom());
		inOutParameterMap.put("testKit", labLoincMappingVO.getTestKit());
		inOutParameterMap.put("instrument", labLoincMappingVO.getInstrument());
		inOutParameterMap.put("vlDetectionLevel", labLoincMappingVO.getVlDetectionLevel());
		inOutParameterMap.put("pfi", labLoincMappingVO.getPfi());		
		
		int resultCount = labReportsDAO.addNewLabLoincToFacility(inOutParameterMap);
		if(resultCount > 0){
			transactionMessage = "Successfully added the new LOINC details to the facility";
			statusCode = 0;
		}else{
			transactionMessage = "Unable to add the LOINC details to the Lab facility, Please contact administrator";
			statusCode = -1;
		}

		commonExceptionHandler.inspectDBTransacionResult(statusCode, transactionMessage, bindingResult);
	
	}
	
	public void updateLabLoincToFacility(LabLoincMappingVO labLoincMappingVO, BindingResult bindingResult,UserObject userObject){
		
		String transactionMessage=null;
		Integer statusCode;
		
		Map<Object, Object> inOutParameterMap = new HashMap<>();
		
		inOutParameterMap.put("loincMapId", labLoincMappingVO.getLoincMapId());
		inOutParameterMap.put("pfi", labLoincMappingVO.getPfi());
		inOutParameterMap.put("loincCode", labLoincMappingVO.getLoincCode());
		inOutParameterMap.put("lastUpdatedBy", userObject.getWorkerId());
		inOutParameterMap.put("dateValidTill", labLoincMappingVO.getDateValidTill());
		inOutParameterMap.put("dateValidFrom", labLoincMappingVO.getDateValidFrom());
		inOutParameterMap.put("testKit", labLoincMappingVO.getTestKit());
		inOutParameterMap.put("instrument", labLoincMappingVO.getInstrument());
		inOutParameterMap.put("vlDetectionLevel", labLoincMappingVO.getVlDetectionLevel());
		
		int resultCount = labReportsDAO.updateLabLoincCodeDetails(inOutParameterMap);
		if(resultCount > 0){
			transactionMessage = "Successfully updated the LOINC details to the facility";
			statusCode = 0;
		}else{
			transactionMessage = "Unable to updated the LOINC details to the Lab facility, Please contact administrator";
			statusCode = -1;
		}

		commonExceptionHandler.inspectDBTransacionResult(statusCode, transactionMessage, bindingResult);
	
	}	
	
	
	public void addNewLabTestToFacility(long localCodeMapId,String testDescription,String dateValidFrom, String dateValidTill, BindingResult bindingResult,UserObject userObject){
		
		String transactionMessage=null;
		Integer statusCode;
		
		Map<Object, Object> inOutParameterMap = new HashMap<>();
		
		inOutParameterMap.put("localCodeMapId", localCodeMapId);
		//inOutParameterMap.put("flagActive", "Y");
		inOutParameterMap.put("testDescription", testDescription);
		inOutParameterMap.put("dateValidTill", dateValidTill);
		inOutParameterMap.put("dateValidFrom", dateValidFrom);
		inOutParameterMap.put("lastUpdatedBy", userObject.getWorkerId());	
		
		
		int existingRecordsCount = labReportsDAO.getExistingTestCodeRecordsCount(inOutParameterMap); 
		
		if(existingRecordsCount > 0){
			transactionMessage = "Given Test code is exist in the same Local code";
			statusCode = -1;
		}else{
			
			int resultCount = labReportsDAO.addNewLabTestToFacility(inOutParameterMap);
			
			if(resultCount > 0){
				transactionMessage = "Successfully Added New Test Codes To The Lab Local Code";
				statusCode = 0;
			}else{
				transactionMessage = "Unable to add the test details to the Lab facility, Please contact administrator";
				statusCode = -1;
			}
		}
		 
		commonExceptionHandler.inspectDBTransacionResult(statusCode, transactionMessage, bindingResult);	
	}
	
	
	public long addNewLabLocalCodeToFacility(String pfi,String localCode, String dateValidFrom, String dateValidTill, BindingResult bindingResult,UserObject userObject){
		String transactionMessage=null;
		Integer statusCode;
		List<Long> localCodeMapId = null;
		
		Map<Object, Object> inOutParameterMap = new HashMap<>();
		
		inOutParameterMap.put("pfi", pfi);
		inOutParameterMap.put("localCode", localCode);
		inOutParameterMap.put("dateValidTill", dateValidTill);
		inOutParameterMap.put("dateValidFrom", dateValidFrom);
		inOutParameterMap.put("lastUpdatedBy", userObject.getWorkerId());
		
		 
		int resultCount = labReportsDAO.addNewLabLocalCodeToFacility(inOutParameterMap);
			
		if(resultCount > 0){
			
			localCodeMapId = labReportsDAO.getLabLocalCodeMapId(inOutParameterMap);
			
			
			
			transactionMessage = "Successfully added the new local code to the facility";
			statusCode = 0;
		}else{
			transactionMessage = "Unable to add the local code details to the Lab facility, Please contact administrator";
			statusCode = -1;
		}
		
		if(statusCode == -1)throw new DataRetrievalFailureException(transactionMessage);
		
		commonExceptionHandler.inspectDBTransacionResult(statusCode, transactionMessage, bindingResult);
		
		if(localCodeMapId != null && localCodeMapId.size() > 0){
			return localCodeMapId.get(0);
		}else{
			return 0;
		}
	}
	
	
	public void linkLoincCodesToLoclCode(long localCodeMapId, long[]  loincCodes,BindingResult bindingResult,UserObject userObject){
		
		String transactionMessage=null;
		Integer statusCode;
		
		Map<Object, Object> inOutParameterMap = new HashMap<>();
		 
		inOutParameterMap.put("localCodeMapId", localCodeMapId);
		inOutParameterMap.put("loincMapIds", loincCodes);
		inOutParameterMap.put("lastUpdatedBy", userObject.getWorkerId());
		
		
		int resultCount = labReportsDAO.linkLoincCodesToLoclCode(inOutParameterMap);
		
		
		if(resultCount > 0){
			transactionMessage = "Successfully added LOINC details to Local code";
			statusCode = 0;
		}else{
			transactionMessage = "Failed to associate the LOINC codes to Local code, Please contact helpline.";
			statusCode = -1;
		}
		
		if(statusCode == -1)throw new DataRetrievalFailureException(transactionMessage);
		
		commonExceptionHandler.inspectDBTransacionResult(statusCode, transactionMessage, bindingResult);
	}
	
	public int addNewClia(BindingResult bindingResult,UserObject userObject,String clia){
		
		String transactionMessage=null;
		Integer statusCode = -1;
		
		int resultCount = labReportsDAO.addNewClia(clia);
		
		if(resultCount > 0){
			statusCode = 0;
			transactionMessage = "Successfully added Clinical laboratary details";
		}else{
			transactionMessage = "Failed to add new Clia, Please contact helpline.";
		}
		
		if(statusCode == -1)throw new DataRetrievalFailureException(transactionMessage);
		
		commonExceptionHandler.inspectDBTransacionResult(statusCode, transactionMessage, bindingResult);
		
		return resultCount;
	}
	
	
	
	
	public Integer addNewLabDetailsToClia(BindingResult bindingResult,UserObject userObject,String clia, List<LabFacilitiesVO> labFacilitiyDetails){
		
		String transactionMessage=null;
		Integer statusCode = -1;
		Map<Object, Object> inOutParameterMap = new HashMap<>();
		 
		inOutParameterMap.put("clia", clia);
		inOutParameterMap.put("lastUpdatedBy", userObject.getWorkerId());
		
		inOutParameterMap.put("labFacilitiyDetails", labFacilitiyDetails);
		
		
		
		int resultCount =  labReportsDAO.addNewLabFacilitiesToClia(inOutParameterMap);
		
		if(resultCount ==  labFacilitiyDetails.size()){
			transactionMessage = "Successfully added new Lab details";
			statusCode = 0;
		}else{
			statusCode = -1;
			transactionMessage = "Unable to add the Lab details, Please contact administrator";
		}
			
		commonExceptionHandler.inspectDBTransacionResult(statusCode, transactionMessage, bindingResult);
		
		if(statusCode == -1)throw new DataRetrievalFailureException(transactionMessage);
		
		return resultCount;
	}
	
	
	public void saveLabFacilityDetails(BindingResult bindingResult,UserObject userObject, LabFacilitiesVO labFacilitiesVO ){
		String transactionMessage=null;
		Integer statusCode=null;
		Map<Object, Object> inOutParameterMap = new HashMap<>();
		inOutParameterMap.put("p_pfi", labFacilitiesVO.getPfi());
		inOutParameterMap.put("p_lab_name", labFacilitiesVO.getLabName());
		inOutParameterMap.put("p_laftStaffAssigned", labFacilitiesVO.getLaftStaffAssigned());
		inOutParameterMap.put("p_flag_active", labFacilitiesVO.getFlagActive());
		inOutParameterMap.put("p_address", labFacilitiesVO.getAddress());
		inOutParameterMap.put("p_city", labFacilitiesVO.getCity());
		inOutParameterMap.put("p_state", labFacilitiesVO.getState());
		inOutParameterMap.put("p_zip", labFacilitiesVO.getZipCode());
		inOutParameterMap.put("p_country", labFacilitiesVO.getCountry());
		inOutParameterMap.put("p_flagServersNYC", labFacilitiesVO.getFlagServersNYC());
		inOutParameterMap.put("p_flagBloodBank", labFacilitiesVO.getFlagBloodBank());
		inOutParameterMap.put("p_clia", labFacilitiesVO.getClia());
		inOutParameterMap.put("p_approvalStatusCode", labFacilitiesVO.getApprovalStatusCode());
		inOutParameterMap.put("p_refersDTATestingTo", labFacilitiesVO.getRefersDTATestingTo());
		inOutParameterMap.put("p_lowVolume", labFacilitiesVO.getLowVolume());
		inOutParameterMap.put("p_physicianOfficeLab", labFacilitiesVO.getPhysicianOfficeLab());
		
		inOutParameterMap.put("p_lastUpdatedBy", userObject.getWorkerId());
		inOutParameterMap.put("p_performsDTA", labFacilitiesVO.getPerformsDTA());
		
		
		
		int resultCount = labReportsDAO.saveLabFacilityDetails(inOutParameterMap);
		
		if(resultCount > 0){
			transactionMessage = "Successfully updated the Lab Facility details";
			statusCode = 0;
		}else{
			transactionMessage = "Unable to update the Lab Facility details, Please contact administrator";
			statusCode = -1;
		}
		
		commonExceptionHandler.inspectDBTransacionResult(statusCode, transactionMessage, bindingResult);
	}
	
	public void updateLabLocalCodeDetails(LabLocalCodesMappingVO labLocalCodesMappingVO, BindingResult bindingResult, UserObject userObject) {
		
		String transactionMessage=null;
		Integer statusCode;
		
		Map<Object, Object> inOutParameterMap = new HashMap<>();
		inOutParameterMap.put("p_flagActive", labLocalCodesMappingVO.getLocalCodeFlagActive());
		inOutParameterMap.put("p_lastUpdatedBy", userObject.getWorkerId());
		inOutParameterMap.put("dateValidTill", labLocalCodesMappingVO.getDateValidTill());
		inOutParameterMap.put("dateValidFrom", labLocalCodesMappingVO.getDateValidFrom());		
		inOutParameterMap.put("p_localCodeMapId", labLocalCodesMappingVO.getLocalCodeMapId());
		
		
		int resultCount = labReportsDAO.updateLabLocalCodeDetails(inOutParameterMap);
		
		if(resultCount > 0){
			transactionMessage = "Successfully updated the Lab Local code Details";
			statusCode = 0;
		}else{
			transactionMessage = "Unable to update the details, Please contact administrator";
			statusCode = -1;
		}
		
		commonExceptionHandler.inspectDBTransacionResult(statusCode, transactionMessage, bindingResult);
		
	}
	
	public void updateLabLocalCodeLoincDetails(LabLoincMappingVO labLoincMappingVO, BindingResult bindingResult, UserObject userObject) {
		
		String transactionMessage=null;
		Integer statusCode;
		
		Map<Object, Object> inOutParameterMap = new HashMap<>();
		inOutParameterMap.put("p_startDate", labLoincMappingVO.getDateValidFrom());
		inOutParameterMap.put("p_endDate", labLoincMappingVO.getDateValidTill());
		inOutParameterMap.put("p_lastUpdatedBy", userObject.getWorkerId());
		inOutParameterMap.put("p_loincMapId", labLoincMappingVO.getLoincMapId());
		
		int existingRecordsMatch = labReportsDAO.checkForActiveLoincsAssociatedToLocalCode(inOutParameterMap);
		
		
		if(existingRecordsMatch > 0){
			int resultCount = labReportsDAO.updateLabLocalCodeLoincDetails(inOutParameterMap);
			
			if(resultCount < 1){
				transactionMessage = "Unable to update the LOINC details , Please contact helpline.";
				statusCode = -1;
				commonExceptionHandler.inspectDBTransacionResult(statusCode, transactionMessage, bindingResult);
				throw new DataRetrievalFailureException(transactionMessage);
				
				
			}
			
		}
		
	}
	

	public void updateLabLocalCodeMappingDetails(LabTestMappingVO labTestMappingVO, BindingResult bindingResult, UserObject userObject) {
		
		String transactionMessage=null;
		Integer statusCode;
		
		Map<Object, Object> inOutParameterMap = new HashMap<>();
		inOutParameterMap.put("p_flagActive", labTestMappingVO.getFlagActive());
		inOutParameterMap.put("p_lastUpdatedBy", userObject.getWorkerId());
		inOutParameterMap.put("p_localCodeMapId", labTestMappingVO.getLocalCodeMapId());
		inOutParameterMap.put("p_testCode", labTestMappingVO.getTestCode());
		inOutParameterMap.put("p_testDescription", labTestMappingVO.getTestDescription());
		inOutParameterMap.put("testCodeDateValidFrom", labTestMappingVO.getDateValidFrom());
		inOutParameterMap.put("testCodeDateValidTill", labTestMappingVO.getDateValidTill());
		
		
		
		int resultCount = labReportsDAO.updateLabLocalCodeMappingDetails(inOutParameterMap);
		
		if(resultCount > 0){
			transactionMessage = "Successfully updated the Test codes of a Lab Local code";
			statusCode = 0;
		}else{
			transactionMessage = "Unable to update the mapping details, Please contact administrator";
			statusCode = -1;
		}
		
		commonExceptionHandler.inspectDBTransacionResult(statusCode, transactionMessage, bindingResult);
	}
	
	public void updateLoincDetailsOfLocalCode(long localCodeMapId, String pfi, long[]  loincCodes,BindingResult bindingResult,UserObject userObject){
		
		String transactionMessage=null;
		Integer statusCode;
		
		Connection connection = connectionDAO.getConnectionObj();
		
		CallableStatement callableStatement = null;
		
			try {
				if(connection != null){
				
					OracleConnection oracleConnection = connection.unwrap(OracleConnection.class);
					
					String schemaName = oracleConnection.getCurrentSchema();
					
					 
					Array array_to_pass = oracleConnection.createOracleArray(schemaName+".NUM_ARR", loincCodes);
					 
					 callableStatement = connection.prepareCall("call pkg_lab_maintenance.pr_merge_lab_localcd_loincs(?,?,?,?,?)");
					 
					 
					 callableStatement.setLong(1, localCodeMapId);
					 callableStatement.setArray(2, array_to_pass);
					 callableStatement.setInt(3, userObject.getWorkerId());
					 callableStatement.registerOutParameter(4, Types.INTEGER);
					 callableStatement.registerOutParameter(5, Types.VARCHAR);
					 
					 callableStatement.execute();
					 
					 
					 statusCode=(Integer)callableStatement.getInt(4);
					 transactionMessage=statusCode==0?"Successfully updated LOINC details of a Local Code":(String)callableStatement.getString(5);
					 commonExceptionHandler.inspectDBTransacionResult(statusCode, transactionMessage, bindingResult);
					 
				}else{
					
					log.error("Connection not available from Data source");
					
					commonExceptionHandler.inspectDBTransacionResult(-1, "Connection not available from Data source", bindingResult);
				}
				 
				 
			} catch (SQLException sqlException) {
				
				log.error("Exception while updating LOINC details of LOINC Code {}",sqlException.getMessage());
				
				commonExceptionHandler.inspectDBTransacionResult(-1, sqlException.getMessage(), bindingResult);
	
			}
			finally{
		        try {
		            if (connection != null) {
		            	connection.close();
		            }
		            if (callableStatement != null) {
		            	callableStatement.close();
		            }
		        } catch (SQLException sqlException) {
		        	log.error("Exception while updating LOINC details of LOINC Code {}",sqlException.getMessage());
		        }
			}
		}
       	
	public List<Long> getSelectedLoincCodesOfLocalCode(long localCodeMapId,BindingResult bindingResult,UserObject userObject){
		
		
		return labReportsDAO.getSelectedLoincCodesOfLocalCode(localCodeMapId);
		
	}
	
	public List<LabInteractionHistoryVO> getInterationHistory(String pfi, BindingResult bindingResult,UserObject userObject){
		
		return labReportsDAO.getInterationHistory(pfi);
	}
	
	public List<LabInteractionHistoryVO> getCommunicationHistory(String pfi, BindingResult bindingResult,UserObject userObject){
		
		return labReportsDAO.getCommunicationHistory(pfi);
	}
	
	public LabInteractionHistoryVO getLabCommunication(String interactionId, BindingResult bindingResult,UserObject userObject){
		
		return labReportsDAO.getLabCommunication(interactionId);
	}
	
	public List<JSONObject> getInteractionContactTypes(BindingResult bindingResult,UserObject userObject){
		
		return labReportsDAO.getInteractionContactTypes();
	}
	
	public void addLabInteractionNotes(String pfi, String interactionNotes, String interactionContactType, BindingResult bindingResult, UserObject userObject){
		String transactionMessage=null;
		Integer statusCode;
		
		Map<Object, Object> inOutParameterMap = new HashMap<>();
		inOutParameterMap.put("pfi", pfi);
		inOutParameterMap.put("interactionNotes", interactionNotes);
		inOutParameterMap.put("interactionContactType", interactionContactType);
		inOutParameterMap.put("p_lastUpdatedBy", userObject.getWorkerId());
		
		int resultCount = labReportsDAO.addLabInteractionNotes(inOutParameterMap);
		
		if(resultCount > 0){
			transactionMessage = "Successfully Recorded Lab Communication Comment";
			statusCode = 0;
		}else{
			transactionMessage = "Unable to add the details, Please contact administrator";
			statusCode = -1;
		}
		
		commonExceptionHandler.inspectDBTransacionResult(statusCode, transactionMessage, bindingResult);
	}
	public void addNewAlternativeContactTOPFI(String pfi, String alternativeContactName, String alternativeContactPhone, String alternativeContactEmail, String alternativeContactFax, BindingResult bindingResult, UserObject userObject){
		
		String transactionMessage=null;
		Integer statusCode;
		
		Map<Object, Object> inOutParameterMap = new HashMap<>();
		inOutParameterMap.put("pfi", pfi);
		inOutParameterMap.put("alternativeContactName", alternativeContactName);
		inOutParameterMap.put("alternativeContactPhone", alternativeContactPhone);
		inOutParameterMap.put("alternativeContactEmail", alternativeContactEmail);
		inOutParameterMap.put("alternativeContactFax", alternativeContactFax);
		inOutParameterMap.put("p_lastUpdatedBy", userObject.getWorkerId());
		
		int resultCount = labReportsDAO.addNewAlternativeContact(inOutParameterMap);
		
		if(resultCount > 0){
			transactionMessage = "Successfully added new Alternative Contact";
			statusCode = 0;
		}else{
			transactionMessage = "Unable to add the details, Please contact administrator";
			statusCode = -1;
		}
		
		commonExceptionHandler.inspectDBTransacionResult(statusCode, transactionMessage, bindingResult);
		
		
	}
	public List<String> getClinicalLabarotories(BindingResult bindingResult, UserObject userObject){
		return labReportsDAO.getClinicalLabarotories();
	}
	
	public List<LabFacilitiesVO> getFacilitiesDataForClia(BindingResult bindingResult, UserObject userObject, String clia){
		
		Map<Object, Object> inOutParameterMap = new HashMap<>();
		inOutParameterMap.put("p_clia", clia);
		
		List<LabFacilitiesVO> facilityDetailsList =  labReportsDAO.getFacilitiesDataForClia(inOutParameterMap);
		
		if(facilityDetailsList != null && facilityDetailsList.size() ==1 && facilityDetailsList.get(0) == null){
			return new ArrayList<LabFacilitiesVO>();
		}else{
			return facilityDetailsList;
		}
		
	}
	public String checkCliaExiststence(BindingResult bindingResult, UserObject userObject, String clia){
		
		return labReportsDAO.checkCliaExiststence(clia);
		
	}
	public void checkPFIExistence(BindingResult bindingResult,UserObject  userObject, String pfi){
		
		String transactionMessage=null;
		Integer statusCode;
		
		String pfi_db = labReportsDAO.checkPFIExistence(pfi);
		
		if(pfi_db == null){
			transactionMessage = "PFI is unique.";
			statusCode = 0;
		}else{
			transactionMessage = "PFI is already Exist, add new PFI";
			statusCode = -1;
		}
		
		commonExceptionHandler.inspectDBTransacionResult(statusCode, transactionMessage, bindingResult);
	}
	
	public JSONObject getApprovalStatus(BindingResult bindingResult , UserObject userObject){
		
		List<Map<String, String>> approvalStatusDetails = labReportsDAO.getLabApprovalStatusMap();
		
		JSONObject approvalStatusJsonObj = new JSONObject();
		
		for (Map<String, String> map : approvalStatusDetails) {
			
			approvalStatusJsonObj.put(map.get("APPROVAL_STATUS_CODE"), map.get("APPROVAL_STATUS_DESC"));
			
		}
		
		return approvalStatusJsonObj;
		
	}
	
	public void addNewLabContactsToPFI(BindingResult bindingResult, UserObject userObject,String pfi, List<JSONObject> pfiContactList){
		
		String transactionMessage=null;
		Integer statusCode = -1;
		Map<Object, Object> inOutParameterMap = new HashMap<>();
		 
		inOutParameterMap.put("pfi", pfi);
		inOutParameterMap.put("workerId", userObject.getWorkerId());
		inOutParameterMap.put("pfiContactList", pfiContactList);
		
		
		
		int resultCount =  labReportsDAO.addNewLabContactsToPFI(inOutParameterMap);
		
		if(resultCount ==  pfiContactList.size()){
			transactionMessage = "Successfully added new Lab details";
			statusCode = 0;
		}else{
			transactionMessage = "unable to add the Contact Details to physical facility: "+pfi;
			statusCode = -1;
		}
			
		if(statusCode == -1)throw new DataRetrievalFailureException(transactionMessage);
		
		commonExceptionHandler.inspectDBTransacionResult(statusCode, transactionMessage, bindingResult);
	
	}
	
	public void addNewSubmissionDataTypesToPFI(BindingResult bindingResult,UserObject userObject,String pfi, List<JSONObject> newSubmissionDataList){
		String transactionMessage=null;
		Integer statusCode = -1;
		Map<Object, Object> inOutParameterMap = new HashMap<>();
		 
		inOutParameterMap.put("pfi", pfi);
		inOutParameterMap.put("workerId", userObject.getWorkerId());
		inOutParameterMap.put("newSubmissionDataList", newSubmissionDataList);
		
		
		
		int resultCount =  labReportsDAO.addNewSubmissionDataTypesToPFI(inOutParameterMap);
		
		if(resultCount ==  newSubmissionDataList.size()){
			transactionMessage = "Successfully added new Submission details";
			statusCode = 0;
		}else{
			transactionMessage = "unable to add the Submission Details to physical facility: "+pfi;
			statusCode = -1;
		}
			
		commonExceptionHandler.inspectDBTransacionResult(statusCode, transactionMessage, bindingResult);
		
		if(statusCode == -1)throw new DataRetrievalFailureException(transactionMessage);
	}
	
	public JSONObject geLabPhysicalFacilityDetails(String pfi, String clia, BindingResult bindingResult, UserObject userObject){
		
		Map<Object, Object> inOutParameterMap = new HashMap<>();
		 
		inOutParameterMap.put("pfi", pfi);
		inOutParameterMap.put("clia", clia);
		
		JSONObject physicalFacilityDetails =  labReportsDAO.geLabPhysicalFacilityDetails(inOutParameterMap);
		
		List<JSONObject> labContactsForPFI = getLabContactsForPFI(pfi, bindingResult, userObject);
				
		physicalFacilityDetails.put("pfiContact", labContactsForPFI);
		
		List<JSONObject> submissionDataTypeList = getsubmissionDataTypesForPFI(pfi, bindingResult, userObject);
		
		physicalFacilityDetails.put("submissionDataTypeList", submissionDataTypeList);
		
		return physicalFacilityDetails;
		
	}
	public List<JSONObject> getLabSubmissionTypesForPFI(String pfi,BindingResult bindingResult, UserObject userObject){
		
		List<JSONObject> labContactDetailsList =  labReportsDAO.getLabSubmissionTypesForPFI(pfi);
		
		if(labContactDetailsList == null){
			labContactDetailsList = new ArrayList<JSONObject>();
		}
		
		return labContactDetailsList;
	}
	
	public List<JSONObject> getLabContactsForPFI(String pfi,BindingResult bindingResult, UserObject userObject){
		
		List<JSONObject> labContactDetailsList =  labReportsDAO.geLabContactsForPFI(pfi);
		
		if(labContactDetailsList == null){
			labContactDetailsList = new ArrayList<JSONObject>();
		}
		
		return labContactDetailsList;
	}
	
	public List<JSONObject> getsubmissionDataTypesForPFI(String pfi,BindingResult bindingResult, UserObject userObject){
		
		List<JSONObject> submissionDataTypesList =  labReportsDAO.getsubmissionDataTypesForPFI(pfi);
		
		if(submissionDataTypesList == null){
			submissionDataTypesList = new ArrayList<JSONObject>();
		}
		
		return submissionDataTypesList;
	}
	
	
	public void deleteContactForPFI(BindingResult bindingResult, UserObject userObject, String contactId){
		String transactionMessage=null;
		Integer statusCode;
		
		Map<Object, Object> inOutParameterMap = new HashMap<>();
		inOutParameterMap.put("workerId", userObject.getWorkerId());
		inOutParameterMap.put("contactId", contactId);
		
		int resultCount = labReportsDAO.deleteContactForPFI(inOutParameterMap);
		
		if(resultCount > 0){
			transactionMessage = "Successfully retired the contact.";
			statusCode = 0;
		}else{
			transactionMessage = "Unable to retire the contact, please contact helpline.";
			statusCode = -1;
		}
		
		commonExceptionHandler.inspectDBTransacionResult(statusCode, transactionMessage, bindingResult);
	}
	
	
	public void deleteSubmissionType(BindingResult bindingResult,
			UserObject userObject, String submissionTypeId, String pfi){
		
		String transactionMessage=null;
		Integer statusCode;
		
		Map<Object, Object> inOutParameterMap = new HashMap<>();
		inOutParameterMap.put("workerId", userObject.getWorkerId());
		inOutParameterMap.put("submissionTypeId", submissionTypeId);
		
		int resultCount = labReportsDAO.deleteSubmissionTypeForPFI(inOutParameterMap);
		
		if(resultCount > 0){
			transactionMessage = "Successfully retired the submission Type.";
			statusCode = 0;
			List<JSONObject> primarySubmissionTypes = labReportsDAO.getPrimarySubmissionTypes(pfi);
			if (primarySubmissionTypes.size() <= 0) {
				transactionMessage="Successfully retired the submission Type. There is no PRIMARY submission type for this facility now.";
			}
		}else{
			transactionMessage = "Unable to retire the submission type, please contact helpline.";
			statusCode = -1;
		}
		
		commonExceptionHandler.inspectDBTransacionResult(statusCode, transactionMessage, bindingResult);
		
	}
	
	public JSONObject getSubmissionTypes(String pfi){
		List<JSONObject> submissionTypeList = labReportsDAO.getSubmissionTypes(pfi);
		
		
		JSONObject submissionTypes = new JSONObject();
		
		for (JSONObject jsonObject : submissionTypeList) {
			
			submissionTypes.put(jsonObject.get("SUBMISSION_TYPE_CD"), jsonObject.get("DESCRIPTION"));
			
		}
		
		return submissionTypes;
		
	}
	
	public String getlastEcrlsImportDate(String pfi,
			BindingResult bindingResult, UserObject userObject){
		
		String ecrlsImportDate = labReportsDAO.getlastEcrlsImportDate(pfi);
		
		return ecrlsImportDate;
	}
	
	public int addNewContactToFacility(BindingResult bindingResult,
			UserObject userObject, LabNewContactVO labNewContactVO){
		
		String transactionMessage="Successfully created the new Contact to Lab Facility";
		Integer statusCode = 0;
		
		labNewContactVO.setUserModified(userObject.getWorkerId());
		
		labReportsDAO.addNewContactToFacility(labNewContactVO);
		
		int contactId = labNewContactVO.getCONTACTID();
		
		if(contactId == 0){
			statusCode = -1;
			transactionMessage = "Failed to create newContact to Lab Facility";
		}
		
		
		commonExceptionHandler.inspectDBTransacionResult(statusCode, transactionMessage, bindingResult);
		
		return contactId;
	}
	
	public int addNewSubmissionTypeToFacility(BindingResult bindingResult,
			UserObject userObject, LabSubmissionTypeVO labSubmissionTypeVO){
		
		String transactionMessage="Successfully created the Submission type to Lab Facility";
		Integer statusCode = 0;
		
		labSubmissionTypeVO.setUserModified(userObject.getWorkerId());
		
		labReportsDAO.addNewSubmissionTypeToFacility(labSubmissionTypeVO);
		
		int submissionId = labSubmissionTypeVO.getLAB_SUBMIT_ID();
		
		if(submissionId == 0){
			statusCode = -1;
			transactionMessage = "Failed to new Submission type to Lab Facility";
		}
		
		
		commonExceptionHandler.inspectDBTransacionResult(statusCode, transactionMessage, bindingResult);
		
		return submissionId;
	}
	
	public void updateLabSubmissionTypeDetails(BindingResult bindingResult,
			UserObject userObject, LabSubmissionTypeVO labSubmissionTypeVO){
		String transactionMessage="Successfully updated the Submission type details";
		Integer statusCode = 0;
		
		labSubmissionTypeVO.setUserModified(userObject.getWorkerId());
		
		int resultCount  = labReportsDAO.updateSubmissionData(labSubmissionTypeVO);
				
		if(resultCount == 0){
			statusCode = -1;
			transactionMessage = "Failed to update the Submission type details.";
		}
		
		String pfi = labSubmissionTypeVO.getPFI();
		List<JSONObject> primarySubmissionTypes = labReportsDAO.getPrimarySubmissionTypes(pfi);
		if (primarySubmissionTypes.size() <= 0) {
			transactionMessage="Successfully updated the Submission type details. There is no PRIMARY submission type for this facility now.";
		}
		
		
		commonExceptionHandler.inspectDBTransacionResult(statusCode, transactionMessage, bindingResult);
	}
	
	public void updateLabContactDetails(BindingResult bindingResult,
			UserObject userObject, LabNewContactVO labNewContactVO){
		String transactionMessage="Successfully updated the Lab Contact details.";
		Integer statusCode = 0;
		
		labNewContactVO.setUserModified(userObject.getWorkerId());
		
		int resultCount  = labReportsDAO.updatePFILabContact(labNewContactVO);
		
		if(resultCount == 0){
			statusCode = -1;
			transactionMessage = "Failed to update the Lab Contact details.";
		}
		
		
		commonExceptionHandler.inspectDBTransacionResult(statusCode, transactionMessage, bindingResult);
	}
	
	public void addNewLabLocalCodeAndLoincDetailsToFacility(String pfi,String localCode,long[] loincCodes, String dateValidFrom, String dateValidTill, BindingResult bindingResult,UserObject userObject){
		
		String transactionMessage = null;
		Integer statusCode = -1;
		try{
			
			String _existingPfi = labReportsDAO.checkForLocalCodeExistance(localCode);
			
			if(_existingPfi != null && !"".equals(_existingPfi.trim())){
				transactionMessage =   "Please create new Local code, Given Local code already exist for PFI: "+_existingPfi;
			}else{
				labReportsTransactionHelper.addNewLabLocalCodeAndLoincDetailsToFacility(pfi, localCode, loincCodes, dateValidFrom, dateValidTill,bindingResult,userObject);
				if(bindingResult.hasErrors()){
					transactionMessage =  "Failed to create the new Lab Local code, Please contact helpline.";
				}else{
					statusCode = 0;
					transactionMessage =   ADD_NEW_LOCAL_CODES_AND_LINK_LOINC_CODES_SUCCESS_MESSAGE;
				}
			}
		
		}catch(DataAccessException dataAccessException){
			transactionMessage = dataAccessException.getMessage();
			log.error(dataAccessException.getMessage());
		}catch(Exception exception){
			transactionMessage = AppConstants.NYEHM_USER_FRIENDLY_ERROR_MESSAGE.getName();
			log.error(exception.getMessage());
		}
		
		commonExceptionHandler.inspectDBTransacionResult(statusCode, transactionMessage, bindingResult);
	}
	

	public void updateLoincCodeDetails(BindingResult bindingResult, UserObject userObject, LabLoincMappingVO labLoincMappingVO){
		String transactionMessage = null;
		boolean isError = false; 
		try{
			labReportsTransactionHelper.updateLoincCodeDetails(bindingResult, userObject, labLoincMappingVO);
		}catch(DataAccessException dataAccessException){
			isError = true;
			transactionMessage = dataAccessException.getMessage();
			log.error("Failed to update the LOINC Details {}",transactionMessage);
		}catch(Exception exception){
			isError = true;
			log.error("Failed to update the LOINC Details  {}",exception.getMessage());
			transactionMessage = "Failed to update the LOINC Details, please contact helpline.";
		}
		
		if(isError){
			commonExceptionHandler.inspectDBTransacionResult(-1, transactionMessage, bindingResult);
		}
	}
	

	
	

	public void addNewLabDetails(BindingResult bindingResult,UserObject userObject,String clia, List<LabFacilitiesVO> labFacilitiyDetails){
		
		
		String transactionMessage = null;
		boolean isError = false; 
		try{
			labReportsTransactionHelper.addNewLabDetails(bindingResult, userObject, clia, labFacilitiyDetails);
		}catch(DataAccessException dataAccessException){
			isError = true;
			transactionMessage = "Failed to add new Lab Facility Details, please contact helpline.";
			log.error("Failed to add new Lab Facility Details  {}",dataAccessException.getMessage());
		}catch(Exception exception){
			isError = true;
			log.error("Failed to add new Lab Facility Details  {}",exception.getMessage());
			transactionMessage = "Failed to add new Lab Facility Details, please contact helpline.";
		}
		
		if(isError){
			commonExceptionHandler.inspectDBTransacionResult(-1, transactionMessage, bindingResult);
		}
	}
	
	public List<String> getLoincsForPfi(
			BindingResult bindingResult, UserObject userObject, String pfi){
		return labReportsDAO.getLoincsForPfi(pfi);
	}
	
	public JSONObject getLoincReferenceValues(BindingResult bindingResult,
			UserObject userObject, String loincCode){
		return labReportsDAO.getLoincReferenceValues(loincCode);
	}
	
		
		
		
	@SuppressWarnings("unchecked")
	public JSONObject getPFIContactTypeMap(BindingResult result, UserObject userObject) {
		List<Map<String, String>> pfiContactTypeList = labReportsDAO.getPFIContactTypeMap();
		JSONObject pfiContactTypeObj = new JSONObject();
		
		for (Map<String, String> map : pfiContactTypeList) {
			
			pfiContactTypeObj.put(map.get("CONTACT_TYPE_CODE"), map.get("CONTACT_TYPE_DESC"));
			
		}
		
		return pfiContactTypeObj;
	}


	@SuppressWarnings("unchecked")
	public JSONObject getLabApprovalStatusMap(BindingResult result, UserObject userObject) {
		
		List<Map<String, String>> labApprovalStatusList = labReportsDAO.getLabApprovalStatusMap();
		JSONObject labApprovalStatusObj = new JSONObject();
		
		for (Map<String, String> map : labApprovalStatusList) {
			labApprovalStatusObj.put(map.get("APPROVAL_STATUS_CODE"), map.get("APPROVAL_STATUS_DESC"));
		}
		return labApprovalStatusObj;
	}


	public List<LabOpsReportsVO> getNycNysCommReportData(String fromDate, String toDate, String nycServiceFlag, BindingResult dummyBindingResult, UserObject userObject) {
		
		Map<Object, Object> inputData = new HashMap<>();
		inputData.put("p_from_date", fromDate);
		inputData.put("p_to_date", toDate);
		
		List<LabOpsReportsVO> nycNysCommReportList = labReportsDAO.getNycNysCommReportData(inputData);
		
		if (CommonUtil.isNotNullAndEmpty(nycServiceFlag) && nycServiceFlag.equals("Y")) {
			List<LabOpsReportsVO> nycNysCommReportList2 = new ArrayList<>(); 
			for (LabOpsReportsVO currObj : nycNysCommReportList) {
				if (currObj.getNycLab().equals("Y")) {
					nycNysCommReportList2.add(currObj);
				}
			}
			nycNysCommReportList = nycNysCommReportList2;
		}
		
		return nycNysCommReportList;
	}


	public List<LabOpsReportsVO> getListOfLabsByApprovalStatus(UserObject userObject, BindingResult bindingResult, String approvalStatus) {
		
		Map<Object, Object> inputData = new HashMap<>();
		inputData.put("approvalStatus", approvalStatus);
		
		List<LabOpsReportsVO> listOfLabsByApprovalStatus = labReportsDAO.getLabListByApprovalStatus(inputData);
		
		return listOfLabsByApprovalStatus;
	}


	public List<LabOpsReportsVO> getCountOfLabsByApprovalStatus(UserObject userObject, BindingResult bindingResult) {
		
		List<LabOpsReportsVO> countOfLabsByApprovalStatus = labReportsDAO.getCountListByApprovalStatus();
		
		for (LabOpsReportsVO lab :  countOfLabsByApprovalStatus) {
			if (CommonUtil.isNullOrEmpty(lab.getApporvalStatus())) {
				lab.setApporvalStatus("No Reporting Status");
			}
		}
		
		return countOfLabsByApprovalStatus;
		
	}


	public List<Map<String, String>> getVLDetectionLevelsCodesAndDescriptions(BindingResult result, UserObject userObject) {
		List<Map<String, String>> vlDetectionLevelsCodesAndDescriptionsList = labReportsDAO.getVLDetectionLevelsCodesAndDescriptions();
		return vlDetectionLevelsCodesAndDescriptionsList;
	}

	public List<Map<String, String>> getTestKitCodesAndDescriptions(BindingResult result, UserObject userObject) {
		List<Map<String, String>> testKitCodesAndDescriptionsList = labReportsDAO.getTestKitCodesAndDescriptions();
		return testKitCodesAndDescriptionsList;
	}

	public List<Map<String, String>> getInstrumentCodesAndDescriptions(BindingResult result, UserObject userObject) {
		List<Map<String, String>> instrumentCodesAndDescriptionsList = labReportsDAO.getLabInstrumentCodesAndDescriptions();
		return instrumentCodesAndDescriptionsList;
	}


	public List<Map<String, String>> getRefersDTATestingToMap(BindingResult result, UserObject userObject) {
		List<Map<String, String>>refersDTATestingToList = labReportsDAO.getRefersDTATestingToMap();
		return refersDTATestingToList;
	}


	public JSONObject getLabLisVendors() {
		
		List<JSONObject> labListVendorsList = labReportsDAO.getLabLisVendors();
		JSONObject labListVendorsObj = new JSONObject();
		
		for (JSONObject jsonObject : labListVendorsList) {
			
			labListVendorsObj.put(jsonObject.get("LIS_VENDOR_CD"), jsonObject.get("LIS_VENDOR_DESC"));
			
		}
		
		return labListVendorsObj;
	}
	
}


