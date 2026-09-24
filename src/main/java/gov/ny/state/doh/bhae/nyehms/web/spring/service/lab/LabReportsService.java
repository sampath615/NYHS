package gov.ny.state.doh.bhae.nyehms.web.spring.service.lab;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.lab.ClinicalLaboratoryVO;
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
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.reports.ProvPortalSubmissionReportVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.uploads.ClustereHARSImportFlagVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;

import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.springframework.validation.BindingResult;



public interface LabReportsService {
	
	//get the RawProviderAddress for Standardize
	public FeedbackReportVO getFeedbackMedicalReport(String providerId, String startDate, String endDate, BindingResult result, UserObject userObject);
	
	
	public List<ClinicalLaboratoryVO> getLabSearchResults(BindingResult bindingResult,UserObject userObject, String clia, LabFacilitiesVO labFacilitiesVO );
	
	public void saveLabFacilityDetails(BindingResult bindingResult,UserObject userObject, LabFacilitiesVO labFacilitiesVO);
	
	
	public LabDetailsVO getLabDetails(BindingResult bindingResult,UserObject userObject, String pfi );
	
	public List<Map<String, String>> getActiveAssociatedPermitCodeAndDescriptionDetails(String pfi, BindingResult result, UserObject userObject);
	
	public List<Map<String, String>> getPermitCodesAndDescriptionsForEdit(BindingResult result, UserObject userObject);
		
	public List<Map<String, String>> getActiveAssociatedInstrumentCodeAndDescriptionDetails(long labPermitId, BindingResult result, UserObject userObject);
	
	public List<Map<String, String>> getInstrumentCodeAndDescriptionDetails(BindingResult result, UserObject userObject);
	
	public Map<String, String> getActiveAndNonAssociatedLabLoincDescriptionDetails(String pfi,BindingResult bindingResult,UserObject userObject);
	
	public void updateLabPermitDetails(BindingResult bindingResult, UserObject userObject, LabPermitsVO labPermitsVO);
	
	public void updateLabCommunicationDetails(BindingResult bindingResult, UserObject userObject, LabInteractionHistoryVO labInteractionHistoryVO);
	
	public void updateLabInstrumentDetails(BindingResult bindingResult, UserObject userObject, LabInstrumentDetailsVO labInstrumentDetailsVO);
	
	public int updateLabLoincCodeDetails(BindingResult bindingResult, UserObject userObject, LabLoincMappingVO labLoincMappingVO);
	
	public void updateLabLocalCodeDetails(LabLocalCodesMappingVO labLocalCodesMappingVO, BindingResult bindingResult, UserObject userObject);
	
	public void updateLabLocalCodeLoincDetails(LabLoincMappingVO labLoincMappingVO, BindingResult bindingResult, UserObject userObject);
	
	public void updateLabLocalCodeMappingDetails(LabTestMappingVO labTestMappingVO, BindingResult bindingResult, UserObject userObject);
	
	//public void addNewLabPermitAndInstrument(LabPermitsVO labPermitsVO,long instrumentCode, BindingResult bindingResult,UserObject userObject);
	
	public void addNewPermitToFacility(LabPermitsVO labPermitsVO, BindingResult bindingResult,UserObject userObject);
	
	public void addNewCommunicationToFacility(LabInteractionHistoryVO labInteractionHistoryVO, BindingResult bindingResult,UserObject userObject);
	
	public void addNewInstrumentToPermit(long labPermitId,long instrumentId,String dateValidFrom,String dateValidTill,BindingResult bindingResult,UserObject userObject);
	
	public void addNewLabLoincToFacility(LabLoincMappingVO labLoincMappingVO, BindingResult bindingResult,UserObject userObject);
	
	public void updateLabLoincToFacility(LabLoincMappingVO labLoincMappingVO, BindingResult bindingResult,UserObject userObject);
	
	public void addNewLabTestToFacility(long localCodeMapId, String testDescription, String dateValidFrom, String dateValidTill, BindingResult bindingResult, UserObject userObject);
	
	public long addNewLabLocalCodeToFacility(String pfi,String localCode, String dateValidFrom, String dateValidTill, BindingResult bindingResult,UserObject userObject);
	
	
	//public void linkLoincCodesToLoclCode(long localCodeMapId,long[] loincMapIds,BindingResult result,UserObject userObject);
	
	public void updateLoincDetailsOfLocalCode(long localCodeMapId, String pfi, long[]  loincCodes,BindingResult bindingResult,UserObject userObject);
	
	
	public List<Long> getSelectedLoincCodesOfLocalCode(long localCodeMapId,BindingResult bindingResult,UserObject userObject);
	
	public void linkLoincCodesToLoclCode(long localCodeMapId, long[]  loincCodes,BindingResult bindingResult,UserObject userObject);
	
	
	public List<LabInteractionHistoryVO> getInterationHistory(String pfi, BindingResult bindingResult,UserObject userObject);
	
	public List<LabInteractionHistoryVO> getCommunicationHistory(String pfi, BindingResult bindingResult,UserObject userObject);
	
	public LabInteractionHistoryVO getLabCommunication(String interactionId, BindingResult bindingResult,UserObject userObject);
		
	public List<JSONObject> getInteractionContactTypes(BindingResult bindingResult,UserObject userObject);
	
	public void addLabInteractionNotes(String pfi, String interactionNotes, String interactionContactType, BindingResult bindingResult, UserObject userObject);
	
	public void addNewAlternativeContactTOPFI(String pfi, String alternativeContactName, String alternativeContactPhone, String alternativeContactEmail, String alternativeContactFax, BindingResult bindingResult, UserObject userObject);
	
	public int addNewClia(BindingResult bindingResult,UserObject userObject,String clia);
	
	public Integer addNewLabDetailsToClia(BindingResult bindingResult,UserObject userObject,String clia, List<LabFacilitiesVO> labFacilitiyDetails);
	
	public List<String> getClinicalLabarotories(BindingResult bindingResult, UserObject userObject);
	
	public List<LabFacilitiesVO> getFacilitiesDataForClia(BindingResult bindingResult, UserObject userObject, String clia);
	
	public String checkCliaExiststence(BindingResult bindingResult, UserObject userObject, String clia);
	
	public void checkPFIExistence(BindingResult bindingResult,UserObject  userObject, String pfi);
	
	public JSONObject getApprovalStatus(BindingResult bindingResult , UserObject userObject);
	
	public void addNewLabContactsToPFI(BindingResult bindingResult, UserObject userObject,String pfi, List<JSONObject> pfiContactList);
	
	public void addNewSubmissionDataTypesToPFI(BindingResult bindingResult,UserObject userObject,String pfi, List<JSONObject> newSubmissionDataList);
	
	public JSONObject geLabPhysicalFacilityDetails(String pfi, String clia, BindingResult bindingResult, UserObject userObject);
	
	public List<JSONObject> getLabContactsForPFI(String pfi,BindingResult bindingResult, UserObject userObject);
	
	public void deleteContactForPFI(BindingResult bindingResult, UserObject userObject, String contactId);
	
	public Map<String, String> getActiveAssociatedLabLoincDescriptionDetails(String pfi,BindingResult bindingResult,UserObject userObject);
	
	public List<JSONObject> getLabSubmissionTypesForPFI(String pfi,BindingResult bindingResult, UserObject userObject);
	
	public JSONObject getSubmissionTypes(String pfi);
	public JSONObject getLabLisVendors();
	
	
	public List<JSONObject> getsubmissionDataTypesForPFI(String pfi,BindingResult bindingResult, UserObject userObject);


	public void deleteSubmissionType(BindingResult bindingResult,
			UserObject userObject, String submissionTypeId, String pfi);


	public String getlastEcrlsImportDate(String pfi,
			BindingResult bindingResult, UserObject userObject);
	
	public int addNewContactToFacility(BindingResult bindingResult,
			UserObject userObject, LabNewContactVO labNewContactVO);


	public int addNewSubmissionTypeToFacility(BindingResult bindingResult,
			UserObject userObject, LabSubmissionTypeVO labSubmissionTypeVO);


	public void updateLabSubmissionTypeDetails(BindingResult bindingResult,
			UserObject userObject, LabSubmissionTypeVO labSubmissionTypeVO);


	public void updateLabContactDetails(BindingResult bindingResult,
			UserObject userObject, LabNewContactVO labNewContactVO);

	public void addNewLabLocalCodeAndLoincDetailsToFacility(String pfi,String localCode,long[] loincCodes, String dateValidFrom, String dateValidTill, BindingResult bindingResult,UserObject userObject);
	
	public void updateLoincCodeDetails(BindingResult bindingResult, UserObject userObject, LabLoincMappingVO labLoincMappingVO);
	
	public void addNewLabDetails(BindingResult bindingResult,UserObject userObject,String clia, List<LabFacilitiesVO> labFacilitiyDetails);


	public List<String> getLoincsForPfi(
			BindingResult bindingResult, UserObject userObject, String pfi);


	public JSONObject getLoincReferenceValues(BindingResult bindingResult,
			UserObject userObject, String loincCode);


	public void changeCliaForPfi(BindingResult bindingResult,
			UserObject userObject, String clia, String pfi);


	public JSONObject getPFIContactTypeMap(BindingResult result, UserObject userObject);
	
	public JSONObject getLabApprovalStatusMap(BindingResult result, UserObject userObject);
	
	public List<LabOpsReportsVO> getNycNysCommReportData(String fromDate,String  toDate, String nycServiceFlag, BindingResult dummyBindingResult, UserObject userObject);
	
	public List<LabOpsReportsVO> getListOfLabsByApprovalStatus(UserObject userObject, BindingResult bindingResult, String approvalStatus);
	
	public List<LabOpsReportsVO> getCountOfLabsByApprovalStatus(UserObject userObject, BindingResult bindingResult);
	
	public List<Map<String, String>> getVLDetectionLevelsCodesAndDescriptions(BindingResult result, UserObject userObject);
	public List<Map<String, String>> getTestKitCodesAndDescriptions(BindingResult result, UserObject userObject);
	public List<Map<String, String>> getInstrumentCodesAndDescriptions(BindingResult result, UserObject userObject);
	
	public List<Map<String, String>> getRefersDTATestingToMap(BindingResult result, UserObject userObject);
}