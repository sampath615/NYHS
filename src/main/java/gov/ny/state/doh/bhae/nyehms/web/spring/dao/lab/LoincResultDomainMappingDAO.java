package gov.ny.state.doh.bhae.nyehms.web.spring.dao.lab;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.lab.AcceptedTestResultDomainVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.lab.LabLoincMappingVO;

import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;

public interface LoincResultDomainMappingDAO {
	
	public List<LabLoincMappingVO> getLoincAndDescriptionDetails(Map<Object,Object> inOutParameterMap);
	
	public List<AcceptedTestResultDomainVO> getAtrDomainsAssoicatedToLoincCode(String loincCode);
	
	public int addNewLabLoincDetailsToLookup(Map<Object,Object> inOutParameterMap);
	
	public int getExistingLoincRecordsCount(String loincCode);
	
	public List<Integer> checkForCustomTestData(String inputData);
	
	public int addNewAcceptableTestResults(Map<Object,Object> inOutParameterMap);
	
	public int checkLoincCodeAssociatedTestResults(Map<Object,Object> inOutParameterMap);
	
	public int assoicateTestResultsToLoincCode(Map<Object,Object> inOutParameterMap);
	
	public int updateLoincAndTestResultsDomainStatus(Map<Object,Object> inOutParameterMap);
	
	public List<JSONObject> getEharsTestResults();
	
	public List<String> getCompleteLoincTestTypes();
	
	public List<String> getLoincDescriptionsForTestType(String testType);
	
	public List<String> getLoincUnitOfMeasurementsForTestType(Map<Object,Object> inOutParameterMap);
	
	public List<String> checkForReferenceLoincCodeMapping(LabLoincMappingVO labLoincMappingVO);

	public int verifyForMayoLabDocuments(Map<String, Integer[]> inputParameterMap);

}
 