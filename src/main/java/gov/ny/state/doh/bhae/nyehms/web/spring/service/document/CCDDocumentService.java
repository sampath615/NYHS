package gov.ny.state.doh.bhae.nyehms.web.spring.service.document;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.jdom2.Document;
import org.springframework.validation.BindingResult;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.ccd.RawCCDDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.ccd.RawCCDEncounterVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.ccd.RawCCDLabTestVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.ccd.RawCCDMedVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.ccd.RawCCDPayerVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.ccd.RawCCDPersonAddressVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.ccd.RawCCDProblemVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.ccd.RawCCDProviderVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.person.PersonViewVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;

public interface CCDDocumentService {

	//public Document getDomDocumentFromCCDString(String theCCDString) throws Exception;
	// public Document getDomDocumentFromCCDString (String theCCDString, Integer
	// uphn_id ) throws ParserConfigurationException, IOException;

	public RawCCDDocVO getRawCCDDocVOFromDocument(Document d, int uphn_id, int docId, String clearText)
			throws Exception;

	public RawCCDDocVO getCCDDoc(int docId, BindingResult dummyBindingResult, UserObject userObject, String id);

	public int writeRawCCDRecordDataToStdTable(Map<Object, Object> stdCCDDocVO);
	
	public int writeRawDiagnosisDataToStdTable(Map<Object, Object> stdCCDDiagnosisVO);

	public PersonViewVO getStdCCDDocumentForClericalReview(int documentId);

	public void updateRawDiagnosisMovedToSTDStatus(int currentDocumentId, String tableName, char statusCode);

	public void updateRawReviewedStatus(int currentDocumentId, String tableName, char statusCode);

	public void updateRawProcessDate(int currentDocumentId, String tableName, Date processDate);

	// public int writeRawEXPRecordDataToStdTable(Map<Object, Object>
	// stdEXpDocVO);
	public void setRaceMap(Map<String, String> _raceMap);

	public void setEthnicityMap(Map<String, String> ethnicityMap);

	public List<Map<Object, Object>> getNewRawCCDList();
	
	public void writeRawCCDData(RawCCDDocVO theCCD, boolean individualLabResultInsert);
	
	public List<RawCCDPersonAddressVO> getCCDAddressList(int docId);
	public List<RawCCDProviderVO> getCCDProvidersList(int docId);
	public List<RawCCDProblemVO> getCCDProblemsList(int docId);
	public List<RawCCDEncounterVO> getCCDEncountersList(int docId);
	public List<RawCCDLabTestVO> getCCDLabTestsList(int docId);
	public List<RawCCDMedVO> getCCDMedicationsList(int docId);
	public List<RawCCDPayerVO> getCCDPayersList(int docId);

	public void addUphnRecord(Map<String, Object> uphnData);

	public boolean processParsedHITdata(Map<String, Object> uphnData, String recordType,
			String decryptedDocument, boolean individualLabResultInserts, Integer uphnId);

	public Document convertStringToDocument(String decryptedDocument) throws Exception;



}
