package gov.ny.state.doh.bhae.nyehms.web.spring.dao.document;

import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.springframework.dao.DataAccessException;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.hl7.RawHL7AddressVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.hl7.RawHL7DocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.hl7.RawHL7INSVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.hl7.RawHL7ObrVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.hl7.RawHL7ObxVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.hl7.StdHL7DocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.hl7.StdHL7RaceVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.person.PersonFieldVO;

public interface HL7DocumentDAO {

	public void insertRawHL7DocData(RawHL7DocVO rhdVO) throws DataAccessException;

	public int insertDiagnosisFromRAWToSTD(Map<Object, Object> stdDiagnosisData);

	public int insertHL7FromRAWToSTD(Map<Object, Object> stdHL7DocinsertVo);

	public int insertDG1List(Map<String, Object> inputParameterMapDG1List);

	public int insertRawHL7OBR(Map<String, Object> inputParameterMapRawHl7LabTest);

	public int insertRawHL7OBX(Map<String, Object> inputParameterMapRawHl7LabResult);

	public List<Integer> getHL7LabTestSequences(int labTestLength);

	public int insertINSList(Map<String, Object> inputParameterMapINSList);

	public int insertORCList(Map<String, Object> inputParameterMapORCList);

	public void updateCurrentProcessStatusInRaw();
	
	public void updateCPSinRAWfromNonMatchToMatchCCD();
	
	public StdHL7DocVO getStdHL7Doc(int docId);

	//public List<RawHL7ObrVO> getHL7LabTestList(int docId);
	
	public List<RawHL7ObrVO> getHL7LabTestList(Map<Object, Object> inputOutputParameterMap);

	public List<RawHL7ObxVO> getHL7LabResultList(int labtestId);

	public List<Map<Object, Object>> getHL7Diagnosis(Map<Object, Object> inputOutputParameterMap);

	public StdHL7DocVO getFilteredHL7Doc(Map<Object, Object> inputOutputParameterMap);

	public List<Map<Object, Object>> getHL7Medications(Map<Object, Object> inputOutputParameterMap);

	public List<Map<Object, Object>> getHL7Encounter(Map<Object, Object> inputOutputParameterMap);

	public List<RawHL7AddressVO> getFilteredHL7AddressList(Map<String, Object> inputOutputParameterMap);

	public List<RawHL7INSVO> getFilteredHL7InsuranceList(Map<String, Object> inputOutputParameterMap);

	public List<PersonFieldVO> getHL7DemogrphicFieldMultiValues(Map<Object, Object> inputOutputParameterMap);
	
	public List<PersonFieldVO> getHL7DOBDemogrphicFieldMultiValues(Map<Object, Object> inputOutputParameterMap);
		
	public void updateCurrentProcessStatusInRawForMPIChange();
	
	public List<Map<Object, Object>> getHL7LabData(Map<Object, Object> inputOutputParameterMap);
	
	public StdHL7RaceVO getHL7RaceVO(int docId);
	
	public StdHL7RaceVO getFilteredRaceVO(Map<Object, Object> inputOutputParameterMap);
	
	public void updateCPSinSTDNotMatchOnParameters();

	public void deleteDuplicatesRowsInRawHL7Insuarance();

	public void insertMPIPairsCheckingHL7andCCD();

	public void updateDataSTDMatchorNonMatchtoCCD(Map<String, Object> input);

	public List<JSONObject> getTotalCountForEachCPS(Map<String, Object> input);

	public void updateCPSAndDOCidinRAWHL7fromSTDHL7DOC();

	public void deleteRowsFromSTDHL7AfterBackedUpToRawHL7();

}
