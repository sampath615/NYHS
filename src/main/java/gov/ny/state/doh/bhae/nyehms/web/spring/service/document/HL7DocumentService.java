package gov.ny.state.doh.bhae.nyehms.web.spring.service.document;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.hl7.RawHL7AddressVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.hl7.RawHL7Dg1VO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.hl7.RawHL7DocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.hl7.RawHL7INSVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.hl7.RawHL7ObrVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.person.PersonFieldVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.springframework.validation.BindingResult;

import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.model.Message;

public interface HL7DocumentService {

	public Message getHL7MessageFromString (int uphnId, String messageText) throws HL7Exception;
	
	public RawHL7DocVO getRawHL7DocVOFromMessage(String rawMessageText,	Message m, int uphnId) throws Exception;
	
	public void writeRawHL7Doc (RawHL7DocVO hl7Vo);
	
	//public int writeRawHL7RecordDataToStdTable(Map<Object, Object> stdHL7DocVO);

	public void setRaceMap(Map<String, String> _raceMap);
	
	public void setEthnicityMap(Map<String, String> ethnicityMap);

	public void updateCurrentProcessStatusInRaw();
	
	public void updateCPSinRAWfromNonMatchToMatchCCD();

	public void writeRawHL7RecordsToStdLayer(List<JSONObject> schedulerStatusObject);
	
	public RawHL7DocVO getHL7Doc(int docId, BindingResult dummyBindingResult, UserObject userObject, String id);
	
	//Filtered Data for HL7 Doc based on Start and end Date
	public RawHL7DocVO getFilteredHL7Doc(int docId, String startDate, String endDate);
	
	public List<Map<Object, Object>> getHL7Diagnosis(int docId, String startDate, String endDate);
	
	public List<Map<Object, Object>> getHL7Medication(int docId, String startDate, String endDate);
	
	public List<Map<Object, Object>> getHL7Encounter(int docId, String startDate, String endDate);
	
	public List<PersonFieldVO> getHL7DemogrphicFieldMultiValues(int documentID, String startDate, String endDate,String colsName);
	
	public void updateCurrentProcessStatusInRawForMPIChange();
	
	public void writeRawHL7MPIChangeRecordsToStdLayer(List<JSONObject> schedulerStatusObject);
	
	public List<RawHL7ObrVO> getHL7LabData(int docId, String startDate, String endDate);
	
	public List<RawHL7AddressVO> getHL7AddressList(int documentID,
			String startDate, String endDate);

	public List<RawHL7INSVO> getHL7InsuranceList(int documentID,
			String startDate, String endDate);
    
	public void insertMPIPairsCheckingHL7andCCD();	
	
	public void updateDataSTDMatchorNonMatchtoCCD(Map<String,String> statusObject, Map<String, String> schedulerMetaData);
	
	public void updateCPSinSTDNotMatchOnParameters();

	public void updateSTDtoRAWStatusDocIdAndDeleteRecordsinStdHL7DOC();

	public void deleteDuplicatesRowsInRawHL7Insuarance();

}
