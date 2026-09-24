package gov.ny.state.doh.bhae.nyehms.web.spring.dao.assignment;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.acrf.PcrfSubstanceDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.acrf.RawAcrfDocVO;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

public interface PcrfDocDAO {
	
	public RawAcrfDocVO getRawPCRFDoc(String documentId)throws DataAccessException;
	public void addRawPCRFDoc(RawAcrfDocVO rawAcrfDoc)throws DataAccessException;
	public void updateRawPCRFDoc(RawAcrfDocVO rawAcrfDoc)throws DataAccessException;
	public List<PcrfSubstanceDocVO> getScreenSubstanceData(String docId);
	public List<PcrfSubstanceDocVO> getBpUseSubstanceList(String docId);
	public List<PcrfSubstanceDocVO> getBpScreenSubstanceList(String docId);
	public List<PcrfSubstanceDocVO> getBpScreenConditionsList(String docId);
	public List<PcrfSubstanceDocVO> getBpDxScreenConditionsList(String docId);
	public void deleteSubStance(Map<String, Object> inputParameterMap);
	public void addSubStanceList(Map<String, Object> inputParameterMap);
	public void addConditionsList(Map<String, Object> inputParameterMap);
	public List<PcrfSubstanceDocVO> getBpPregOutcomeList(String docId);
	public List<PcrfSubstanceDocVO> getBpPregOutcomeDefaultList(String docId);
	public void addPregOutcomeList(Map<String, Object> inputParameterMap);
	
}
