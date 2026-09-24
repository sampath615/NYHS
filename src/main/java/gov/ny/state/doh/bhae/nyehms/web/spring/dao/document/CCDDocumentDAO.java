package gov.ny.state.doh.bhae.nyehms.web.spring.dao.document;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.HitErrorVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.HitLoadLogVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.ccd.RawCCDDiagnosisVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.ccd.RawCCDEncounterVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.ccd.RawCCDLabResultVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.ccd.RawCCDLabTestVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.ccd.RawCCDMedVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.ccd.RawCCDPayerVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.ccd.RawCCDPersonAddressVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.ccd.RawCCDPersonDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.ccd.RawCCDProblemVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.ccd.RawCCDProviderVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.ccd.StdCCDDocVo;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.person.PersonViewVO;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

public interface CCDDocumentDAO {

	public void insertRawCcdDocData(RawCCDPersonDocVO rcdVO) throws DataAccessException;

	public void insertRawCcdPersonAddress(RawCCDPersonAddressVO rcpaVO) throws DataAccessException;

	public void insertRawCcdProvider(RawCCDProviderVO rcpi) throws DataAccessException;

	public void insertRawCcdPayer(RawCCDPayerVO thePayerVO) throws DataAccessException;

	public void insertRawCcdLabTest(RawCCDLabTestVO theLabTestVO) throws DataAccessException;

	public void insertRawCcdLabResult(RawCCDLabResultVO theLabResultVO) throws DataAccessException;

	public void insertRawCcdEncounter(RawCCDEncounterVO theEncounterVO) throws DataAccessException;

	public void insertRawCcdDiagnosis(RawCCDDiagnosisVO theDiagnosisVO) throws DataAccessException;

	public void insertRawCcdMed(RawCCDMedVO theMedVO) throws DataAccessException;

	public void insertRawCcdProblem(RawCCDProblemVO theProblemVO) throws DataAccessException;

	public RawCCDPersonDocVO getCCDDoc(int docId);

	public void insertHitLoadLog(HitLoadLogVO hitloadlog) throws DataAccessException;

	public void insertHitError(HitErrorVO hitErrorVO) throws DataAccessException;

	public List<Map<String, Object>> getAllCcdCountIndex() throws DataAccessException;

	public int insertCCDFromRAWToSTD(Map<Object, Object> stdDocData);

	public int insertDiagnosisFromRAWToSTD(Map<Object, Object> stdDiagnosisData);

	public int insertEXPFromRAWToSTD(Map<Object, Object> stdDocData);

	public StdCCDDocVo getStdCCDDoc(int docId);

	public List<RawCCDProviderVO> getCCDProviderList(int docId);

	public List<RawCCDEncounterVO> getCCDEncounterList(int docId);

	public List<RawCCDMedVO> getCCDMedList(int docId);

	public List<RawCCDPayerVO> getCCDPayerList(int docId);

	public List<Map<String, Object>> getChildEntryData(Map<String, Object> args);

	public int insertCcdAddressList(Map<String, Object> inputParameterMapAddress);

	public int insertCcdProvidersList(Map<String, Object> inputParameterMapProvider);

	public int insertCcdPayersList(Map<String, Object> inputParameterMapPayer);

	public int insertCcdMedsList(Map<String, Object> inputParameterMapMeds);

	public int insertCcdProblemsList(Map<String, Object> inputParameterMapProblems);

	public int insertCcdLabResultsList(Map<String, Object> inputParameterMapLabRes);

	public int insertCcdDiagnosisList(Map<String, Object> diagnosisMap);

	public List<Integer> getEncounterSequences(int encounterLength);

	public List<Integer> getLabTestSequences(int labTestlength);

	public int insertCcdEncounterList(Map<String, Object> inputParameterMapEncounters);

	public int insertCcdLabTestList(Map<String, Object> inputParameterMapLabs);

	public PersonViewVO getStdCCDDocumentForClericalReview(int documentId);

	public int updateRawDiagnosisMovedToSTDStatusCode(Map<Object, Object> inputOutputParameterMap);

	public int updateRawReviewedStatusCode(Map<Object, Object> inputOutputParameterMap);

	public int updateRawProcessDate(int currentDocumentId, String tableName, Date processDate);

	public int updateRawProcessDateInp(Map<Object, Object> inputOutputParameterMap);

	public List<Map<Object, Object>> getNewRawCCDList();
	
	public List<RawCCDPersonAddressVO> getCCDAddressList(int docId);
	
	public List<RawCCDProviderVO> getCCDProvidersList(int docId);
	
	public List<RawCCDProblemVO> getCCDProblemsList(int docId);
	
	public List<RawCCDEncounterVO> getCCDEncountersList(int docId);
	
	public List<RawCCDDiagnosisVO> getDiagnosisList(int encounterDocId);
	
	public List<RawCCDLabTestVO> getCCDLabTestsList(int docId);
	
	public List<RawCCDLabResultVO> getLabResultsList(int labTestDocId);
	
	public List<RawCCDMedVO> getCCDMedicationsList(int docId);
	
	public List<RawCCDPayerVO> getCCDPayersList(int docId);

	public void addUphnRecord(Map<String, Object> uphnData);

	public void updateUphnRecord(Map<String, Object> uphnData);


}

