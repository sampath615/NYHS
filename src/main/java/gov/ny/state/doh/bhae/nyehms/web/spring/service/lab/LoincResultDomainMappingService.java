package gov.ny.state.doh.bhae.nyehms.web.spring.service.lab;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.lab.AcceptedTestResultDomainVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.lab.LabLoincMappingVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;

import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.springframework.validation.BindingResult;




public interface LoincResultDomainMappingService {
	
	public List<LabLoincMappingVO> getLoincAndDescriptionDetails(BindingResult bindingResult,UserObject userObject, LabLoincMappingVO labLoincMappingVO);
	
	public List<AcceptedTestResultDomainVO> getAtrDomainsAssoicatedToLoincCode(String loincCode, BindingResult bindingResult, UserObject userObject);
	
	public void addNewLabLoincDetailsToLookup(BindingResult bindingResult, UserObject userObject, String loincCode, String testTypeCode);
	
	public int addNewAcceptableTestResults(AcceptedTestResultDomainVO acceptedTestResultDomainVO, BindingResult bindingResult, UserObject userObject);
	
	public List<Integer> checkForCustomTestData(String inputData, BindingResult bindingResult, UserObject userObject);
	
	public int checkLoincCodeAssociatedTestResults(String loincCode, List<Integer> testResultsAttributeIds, BindingResult bindingResult, UserObject userObject);
	
	public int assoicateTestResultsToLoincCode(String loincCode, int attributeDomainId, String standardResults, BindingResult bindingResult, UserObject userObject );
	
	public void updateLoincAndTestResultsDomainStatus(long loincAtrDomainMapId,String flagActive, BindingResult bindingResult, UserObject userObject);
	
	public List<JSONObject> getEharsTestResults(BindingResult bindingResult, UserObject userObject);
	
	public List<String> getCompleteLoincTestTypes(UserObject userObject);
	
	public List<String> getLoincDescriptionsForTestType(UserObject userObject, String testType);
	
	public List<String> getLoincUnitOfMeasurementsForTestType(UserObject userObject, String loincDescription, String testType);
	
	public List<String> checkForReferenceLoincCodeMapping(BindingResult bindingResult, UserObject userObject, LabLoincMappingVO labLoincMappingVO);
	
	public int getExistingLoincRecordsCount(BindingResult bindingResult, UserObject userObject, String loincCode);
	
	public void addNewAcceptableTestResultsAndAssociateToLoincCode(AcceptedTestResultDomainVO acceptedTestResultDomainVO, BindingResult bindingResult, UserObject userObject);

	public Map<String, String> getLoincAndUnitsOfMeasurementCombination(
			String loincCd, BindingResult bindingResult, UserObject userObject);

	public void changeLoincMultiSpotDocuments(Integer[] documentIds, String documentType,
			int rawDocumentEditedProcessStatus, BindingResult bindingResult, UserObject userObject);

}
