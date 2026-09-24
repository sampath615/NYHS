package gov.ny.state.doh.bhae.nyehms.web.spring.dao.lab;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.lab.ClinicalLaboratoryVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.lab.LabFacilitiesVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.lab.LabInteractionHistoryVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.lab.LabNewContactVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.lab.LabOpsReportsVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.lab.LabReportsCountVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.lab.LabSubmissionTypeVO;

import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.springframework.dao.DataAccessException;

public interface LabReportsDAO {
	
	public void getFeedbackMedicalReport(Map<Object,Object> map) throws DataAccessException;
	
	public List<ClinicalLaboratoryVO> getLabSearchResults(Map<Object,Object> map) throws DataAccessException;
	
	public void getLabDetails(Map<Object,Object> map) throws DataAccessException;
	
	public List<Map<String, String>> getActiveAssociatedPermitCodesAndDescriptions(String pfi)throws DataAccessException;
	
	public List<Map<String, String>> getPermitCodesAndDescriptionsForEdit()throws DataAccessException;
	
	public List<Map<String, String>> getActiveAssociatedInstrumentCodesAndDescriptions(long labPermitId)throws DataAccessException;
	
	public List<Map<String, String>> getActiveAndNonAssociatedLabLoincDescriptionDetails(String pfi)throws DataAccessException;
	
	public List<Map<String, String>> getActiveAssociatedLabLoincDescriptionDetails(String pfi)throws DataAccessException;
	
	public List<Map<String, String>> getInstrumentCodesAndDescriptions() throws DataAccessException;
	
	
	public int updateLabPermitDetails(Map<Object,Object> map)throws DataAccessException;
	
	
	public int updateLabInstrumentDetails(Map<Object,Object> map)throws DataAccessException;
	
	public int updateLabLoincCodeDetails(Map<Object,Object> map)throws DataAccessException;
	
	public int updateLabLocalCodeDetails(Map<Object,Object> map)throws DataAccessException;
	
	public int updateLabLocalCodeLoincDetails(Map<Object,Object> map)throws DataAccessException;
	
	public int updateLabLocalCodeMappingDetails(Map<Object, Object> inOutParameterMap);
	
	public int updateLabCommunicationDetails(Map<Object,Object> map)throws DataAccessException;
	
	
	public int addNewPermitCodeToFacility(Map<Object,Object> map)throws DataAccessException;
	
	public int addNewCommunicationToFacility(Map<Object,Object> map)throws DataAccessException;
	
	public int addNewInstrumentToPermit(Map<Object,Object> map)throws DataAccessException;

	public int addNewLabLoincToFacility(Map<Object,Object> map)throws DataAccessException;
	public int addNewLabTestToFacility(Map<Object,Object> map)throws DataAccessException;
	
	public int addNewLabLocalCodeToFacility(Map<Object,Object> map)throws DataAccessException;
	
	public int saveLabFacilityDetails(Map<Object,Object> map)throws DataAccessException;
	
	
	public List<Long> getLabLocalCodeMapId(Map<Object,Object> map)throws DataAccessException;
	
	public int linkLoincCodesToLoclCode(Map<Object,Object> map)throws DataAccessException;
	
	
	public List<Long> getSelectedLoincCodesOfLocalCode(Long localCodeMapId)throws DataAccessException;
	
	
	public String getLoincMapId(Map<Object,Object> map)throws DataAccessException;
	
	public List<LabInteractionHistoryVO> getInterationHistory(String pfi)throws DataAccessException;
	
	public List<LabInteractionHistoryVO> getCommunicationHistory(String pfi) throws DataAccessException;
	
	public LabInteractionHistoryVO getLabCommunication(String interactionId) throws DataAccessException;
	
	
	public int addLabInteractionNotes(Map<Object,Object> map)throws DataAccessException;
	
	public int addNewAlternativeContact(Map<Object,Object> inOutParameterMap)throws DataAccessException;
	
	public int addNewClia(String clia)throws DataAccessException;
	
	public int addNewLabFacilitiesToClia(Map<Object,Object> inOutParameterMap)throws DataAccessException;
	
	public List<String> getClinicalLabarotories()throws DataAccessException;
	
	public List<LabFacilitiesVO> getFacilitiesDataForClia(Map<Object,Object> inOutParameterMap)throws DataAccessException;
	
	public String checkCliaExiststence(String clia)throws DataAccessException;
	
	public String checkPFIExistence(String pfi)throws DataAccessException;
	
	
	public int addNewLabContactsToPFI(Map<Object,Object> inOutParameterMap);
	
	public int addNewSubmissionDataTypesToPFI(Map<Object,Object> inOutParameterMap);
	
	public JSONObject geLabPhysicalFacilityDetails(Map<Object, Object> inOutParameterMap)throws DataAccessException;
	
	public List<JSONObject> geLabContactsForPFI(String pfi)throws DataAccessException;
	
	public List<JSONObject> getsubmissionDataTypesForPFI(String pfi)throws DataAccessException;
	
	public int deleteContactForPFI(Map<Object,Object> inOutParameterMap);
	
	public int getExistingTestCodeRecordsCount(Map<Object,Object> inOutParameterMap);
	
	public int checkForActiveLoincsAssociatedToLocalCode(Map<Object,Object> inOutParameterMap)throws DataAccessException;
	
	public List<JSONObject> getInteractionContactTypes();
	
	public int updatePFILabContact(LabNewContactVO labNewContactVO);
	
	public int updateSubmissionData(LabSubmissionTypeVO labSubmissionTypeVO);
	
	public List<JSONObject> getLabSubmissionTypesForPFI(String pfi);
	
	public List<JSONObject> getSubmissionTypes(String pfi);
	
	public List<JSONObject> getPrimarySubmissionTypes(String pfi);

	public int deleteSubmissionTypeForPFI(Map<Object, Object> inOutParameterMap);

	public String getlastEcrlsImportDate(String pfi);

	

	public void addNewContactToFacility(LabNewContactVO labNewContactVO);

	public void addNewSubmissionTypeToFacility(
			LabSubmissionTypeVO labSubmissionTypeVO);

	public String checkForLocalCodeExistance(String localCode);

	public List<String> getLoincsForPfi(String pfi);
	public List<JSONObject> getLabLisVendors();
	
	public JSONObject getLoincReferenceValues(String loincCode);

	public int changeCliaForPfi(Map<Object, Object> inOutParameterMap);

	public List<LabReportsCountVO> getLabReportCount(Map<String, String> inputMap);
	
	public List<Map<String, String>> getPFIContactTypeMap() throws DataAccessException;
	
	public List<Map<String, String>> getLabApprovalStatusMap() throws DataAccessException;
	
	public List<LabOpsReportsVO> getNycNysCommReportData(Map<Object,Object> map);	
	
	public List<LabOpsReportsVO> getLabListByApprovalStatus(Map<Object,Object> map);	
	
	public List<LabOpsReportsVO> getCountListByApprovalStatus();	
	
	public List<Map<String, String>> getVLDetectionLevelsCodesAndDescriptions()throws DataAccessException;
	public List<Map<String, String>> getTestKitCodesAndDescriptions()throws DataAccessException;
	public List<Map<String, String>> getLabInstrumentCodesAndDescriptions()throws DataAccessException;
	public List<Map<String, String>> getRefersDTATestingToMap() throws DataAccessException;
}
 