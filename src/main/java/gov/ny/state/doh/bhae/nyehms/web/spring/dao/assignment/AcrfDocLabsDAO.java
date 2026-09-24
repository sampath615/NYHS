package gov.ny.state.doh.bhae.nyehms.web.spring.dao.assignment;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.CodedAcrfTestResultVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.CodedAcrfTestVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.CodedParentTestTypeVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.acrf.AcrfRidrVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.acrf.CodedArvMedicationsVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.acrf.RawAcrfCd4TstVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.acrf.RawAcrfLabsVO;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Transactional;

public interface AcrfDocLabsDAO {	
	public List<CodedAcrfTestVO> getCodedAcrfResultTypes() throws DataAccessException;
	//public List<CodedAcrfTestResult> getCodedAcrfTestResultTypes() throws DataAccessException;
	public List<CodedParentTestTypeVO> getCodedParentTestTypes() throws DataAccessException;
	public List<RawAcrfLabsVO> getRawACRFLabs(Map<String, Object> inputParameterMap) throws DataAccessException;
	@Transactional
	public void addRawACRFLabs(Map<String, Object> inputParameterMap) throws DataAccessException;
	public List<CodedAcrfTestResultVO> getCodedAcrfTestResultTypes(Map<Object,Object> map)throws DataAccessException;
	@Transactional
	public int addRawAcrfCd4Data(Map<String, Object> inputParameterMap)throws DataAccessException;
	public List<RawAcrfCd4TstVO> getRawAcrfCd4Data(Map<String, Object> inputParameterMap)throws DataAccessException;
	@Transactional
	public void updateRawACRFLabs(Map<String, Object> inputParameterMap)throws DataAccessException;
	@Transactional
	public void updateRawAcrfCd4Data(Map<String, Object> inputParameterMap)throws DataAccessException;
	
	public int updateSurveillanceAssignmentWithAcrfDocId(Map<Object,Object> map)throws DataAccessException;
	public int updateSurveillanceAssignmentWithReabAcrfDocId(Map<Object,Object> map)throws DataAccessException;
	
	public String[] getEharsStandardFacilityDetails();
	public List<Map<String, String>> getDocumentSourceList();
	public List<Map<String, String>> getAcrfOtherIdTypeList();
	public void deleteRawACRFLabs(Map<String, Object> inputParameterMap);
	
	public void deleteRawAcrfCd4Data(Map<String, Object> inputParameterMap);
	public int addRawArvMedication(Map<String, Object> inputParameterMap);
	public List<CodedArvMedicationsVO> getRawArvMedicationsData(
			Map<String, Object> inputParameterMap);
	public int deleteRawArvMedication(Map<String, Object> inputParameterMap);
	public void updateRawArvMedication(
			Map<String, Object> inputParameterMap);
	public List<AcrfRidrVO> getRawAcrfRidrData(String rawAcrfDocId);
	public int addRawAcrfRidrInfo(AcrfRidrVO acrfRidrVO);
	public int deleteRawAcrfRidrData(String ridr_ROWID);
	public void updateRawAcrfRidrData(AcrfRidrVO acrfRidrVO);
	public List<Map<String, String>> getACRFReferenceData(String referenceColumn);
	public List<Map<String, String>> getClinicalLaboratoryDetails();
}
