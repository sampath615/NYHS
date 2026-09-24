package gov.ny.state.doh.bhae.nyehms.web.spring.dao.person;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.person.PersonDocHistoryVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.person.PersonFieldDocsVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.person.PersonFieldVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.person.PersonRisksVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.person.PersonViewVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.uploads.ClusterUploadVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.uploads.UpdatedDispositionsVO;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

public interface PersonDAO {
	/**
	 * This method is used to get the Person view data for the mentioned Best indicated date 
	 * 
	 * @param map containing input parameters
	 * @return
	 * @throws DataAccessException
	 */
	public List<PersonViewVO> getPersonViewData(Map<Object,Object> map)throws DataAccessException;
	
	/**
	 * This method is used to get the Field values available for the specified field name. 
	 * 
	 * If will traverse thorough all the documents associated to the stateno to get the complete values(Distinct values) 
	 * 
	 * @param map  containing input parameters
	 * @return Field values for the specified field name
	 * @throws DataAccessException
	 */
	public List<PersonFieldVO> getFieldValsWithBestIndicator(Map<Object,Object> map)throws DataAccessException;
	/**
	 * This method will return list of documents associated for the specified value of a stateno. 
	 * @param map  containing input parameters
	 * @return documents list
	 * @throws DataAccessException
	 */
	public List<PersonFieldDocsVO> getFieldDocsWithBestIndicator(Map<Object,Object> map)throws DataAccessException;
	/**
	 * This method is used to get the Person race data for the specified date
	 * 
	 * @param map  containing input parameters
	 * @return Person Race object
	 * @throws DataAccessException
	 */
	public List<PersonRisksVO> getPersonRaceData(Map<Object,Object> map)throws DataAccessException;
	/**
	 * This method is used to get the Person risk data for the specified date
	 * 
	 * @param map  containing input parameters
	 * @return person risk object
	 * @throws DataAccessException
	 */
	public List<PersonRisksVO> getPersonRisksData(Map<Object,Object> map)throws DataAccessException;
	/**
	 * This method is used to delete the document
	 * 
	 * @param inOutParameterMap  containing input parameters
	 * @throws DataAccessException
	 */
	public void deleteDocument(Map<Object,Object> inOutParameterMap)throws DataAccessException;

	public List<PersonDocHistoryVO> getPersonDocumentDetails(Map<String, Object> inputParameterMap);

	public List<String> getEharsStateno(String stateNo);

	public List<String> getNycEharsDetails(String stateNo);

	public List<Map<String, String>> getOriginCategories();

	public String getNyehmsStatenoForTheDocument(int docId);

	public List<PersonDocHistoryVO> getDocumentDetails(Map<Object, Object> map);

	public List<PersonDocHistoryVO> getPrfDocumentDetails(
			Map<Object, Object> map);

	public List<PersonDocHistoryVO> getLabDocumentDetails(
			Map<Object, Object> map);

	public List<String> getStatenoHistory(Map<Object, Object> map);

//	public List<String> geteHARSNysStatenoHistory(String stateNo);
//
//	public List<String> geteHARSNycStatenoHistory(String stateNo);

	public List<PersonDocHistoryVO> getHIVDocumentDetails(Map<String, Object> inputParameterMap);

	public List<Map<String, String>> getEharsStatenoMappings(Map<Object,Object> inOutParameterMap);

	public String checkStatenoIsActive(String stateNo);

	public List<PersonDocHistoryVO> getAIRSDocumentDetails(
			Map<Object, Object> map);

	public List<PersonDocHistoryVO> getMEDDocumentDetails(
			Map<Object, Object> map);

	public List<PersonDocHistoryVO> getADAPDocumentDetails(
			Map<Object, Object> map);

	public List<PersonDocHistoryVO> getDTHDocumentDetails(
			Map<Object, Object> map);

	public List<PersonDocHistoryVO> getNycDTHDocumentDetails(
			Map<Object, Object> map);
	
	public List<PersonDocHistoryVO> getCASADocumentDetails(Map<Object, Object> map);
	
	public List<PersonDocHistoryVO> getSYPHDocumentDetails(Map<Object, Object> map);

	public List<PersonDocHistoryVO> getNycPrfDocumentDetails(
			Map<Object, Object> map);

	public List<PersonDocHistoryVO> getEPRFOOCDocumentDetails(
			Map<Object, Object> map);

	public List<PersonDocHistoryVO> getSSDMDocumentDetails(
			Map<Object, Object> map);

	public List<PersonDocHistoryVO> getNycIJRDocumentDetails(
			Map<Object, Object> map);

	public List<String> getTriggeredMessageTypesForDocument(Map<Object, Object> inputParameterMap);

	public void triggerPsMessage(Map<Object, Object> inOutParameterMap);

	public List<PersonDocHistoryVO> getDinDoccDocumentDetails(
			Map<Object, Object> map);

	public List<PersonDocHistoryVO> getTBDocumentDetails(Map<Object, Object> map);
	
	public List<PersonDocHistoryVO> getCCDDocumentDetails(Map<Object, Object> map);

	public List<Integer> checkForExistingUpdatedMessageDocIds(
			Map<String, Object> inputParameterMap);

	public List<Integer> getReturnDocIdNotExistInTable(
			Map<String, Object> inputParameterMap);

	public void insertUpdatedReturnMessage(
			UpdatedDispositionsVO updatedDispositionsVO);

	public List<Map<String, Object>> checkExistingDateOfDeathForMessageDocIds(
			Map<String, Object> inputParameterMap);


	public List<PersonDocHistoryVO> getADAPNYCDocumentDetails(
			Map<Object, Object> map);

	public int checkForDuplicateDispositionCode(
			UpdatedDispositionsVO updatedDispositionsVO);

	public List<String> getNycCityNoDetails(String stateno);
	
	public List<ClusterUploadVO> getStatenoClusterAssociation(String stateNo);

	public List<PersonDocHistoryVO> getSTDConfDocumentDetails(
			Map<Object, Object> map);

	public void deletePendingPSMessageDocuments(Map<String, Object> inputParameterMap);

	public Map<String, String> getNYSEharsDxDetails(String stateno);
	
	public Map<String, String> getNYCEharsDxDetails(String stateno);

	public List<PersonDocHistoryVO> getDocumentDetailsFromStageTable(Map<Object, Object> map);

	public void deletePendingRiaDocument(Map<String, Object> inputParameterMap);


	public List<PersonDocHistoryVO> getClusterDocumentDetailsFromStageTable(Map<Object, Object> map);

	public List<PersonDocHistoryVO> getNBSDocumentDetails(Map<Object, Object> map);

}