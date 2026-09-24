package gov.ny.state.doh.bhae.nyehms.web.spring.dao.document;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Transactional;

public interface ClericalReviewDAO {
	
	public Map<String, Object> getDocumentIdAndPotentialMatchesForStatenos(Map<String, Object> inputParameterMap) throws DataAccessException;;
	
	public void matchDocumentToStateno(Map<Object,Object> map) throws DataAccessException;
	
	public void  noMatchForADocument(Map<Object,Object> map) throws DataAccessException;
	
	public List<Integer> getClericalDocuments(Map<String, Object> inOutParameterMap) throws DataAccessException;

	public Map<String, Object> getQualityStagedDocumentToReview(
			Map<Object, Object> inOutParamaterMap);
	
	public Map<String, Object> getQualityStagedDocumentToReviewByParent(
			Map<Object, Object> inOutParamaterMap);

	public List<String> getStatnosForClericalDocument(BigDecimal document_id);

	public List<String> getMatchingStatnoForDocument(Map<Object,Object> map);
	
	public void qualityStagedDocumentManualReviewForEPRFOOC(
			Map<Object, Object> inOutParamaterMap);

	public void qualityStagedDocumentManualReviewForNycIJR(
			Map<Object, Object> inOutParamaterMap);

	public void qualityStagedDocumentManualReviewForICCR(
			Map<Object, Object> inOutParamaterMap);

	@Transactional
	public void qualityStagedDocumentManualReviewForADAP(
			Map<Object, Object> inOutParamaterMap);
	
	public List<String> getQualityStageStatusCodesForTheFileName(
			Map<String, String> inputParameterMap);

	public int getDocumentsCountToReviewWithSearchCriteria(
			Map<Object, Object> inOutParamaterMap);
	
	public int getDocumentsCountToReviewWithSearchCriteriaByParent(
			Map<Object, Object> inOutParamaterMap);

	public List<String> getPotentialStatenosForDinDoccsDocument(Integer document_id);

	public List<String> getClericalReviewAvailabeDocumentTypes();

	public void qualityStagedDocumentManualReviewForResistance(
			Map<Object, Object> inOutParamaterMap);

	public List<String> getClericalLabDeathReviewAvailabeDocumentTypes();

	public List<String> getDatafluxAvailabeDocumentTypes(Map<String, Object> inputParameterMap);

	public List<Integer> getDatafluxClericalDocuments(Map<String, Object> inOutParameterMap);

	public List<String> getDatafluxDocumentIdAndPotentialMatchesForStatenos(
			Map<String, Object> inOutParameterMap);

	public void updateMatchDetailsInStageTable(Map<String, Object> inOutParameterMap);

	public void updateMatchDetailsInClustersTable(Map<String, Object> inOutParameterMap);

	public void updateNotaMatchDetailsInStageTable(Map<String, Object> inOutParameterMap);

	public void updateNotaMatchDetailsInClustersTable(Map<String, Object> inOutParameterMap);

	public Map<String, Object> getDatafluxClericalReviewDocumentDetails(Map<String, Object> inOutParameterMap);

	public void processDatafluxStageDocument(Map<String, Object> inOutParameterMap);

	public String isTheDocumentValidToProcess(Map<String, Object> inOutParameterMap);

	public List<String> getDatafluxFileNamesToReview(String docType);

	public int getTotalDocCountToReviewInDataFluxStage(Map<Object, Object> inOutParamaterMap);

	public Map<String, Object> getDatafluxStagedDocumentToReview(Map<Object, Object> inOutParamaterMap);

	public List<String> getDFStatusCodesForTheFileName(Map<String, String> inputParameterMap);

	public List<Integer> getClusterPartnerReviewDocuments(Map<String, Object> inOutParameterMap);

	public Map<String, Object> getClusterPartnerReviewDocumentDetails(Map<String, Object> inOutParameterMap);


}
