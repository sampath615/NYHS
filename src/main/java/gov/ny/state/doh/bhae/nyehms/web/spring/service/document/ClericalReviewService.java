package gov.ny.state.doh.bhae.nyehms.web.spring.service.document;

import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;

import java.util.List;
import java.util.Map;

import org.springframework.validation.BindingResult;

/*
 * ACRF JSP is treated as 3 parts for all CRUD operations. 
 * One is ACRF Doc table itself 2nd is ACRF Labs table and 3rd ACRF CD4 Table.
 * So we define CRUD (only select, insert, update)) operation separately for three tables even though 
 * it is single operation from user view.
 * This is done to modularize code so that each table CRUD can be handles separately in future.
 */	

public interface ClericalReviewService {
	
	public Map<String, Object> getDocumentIdAndPotentialMatchesForStatenos(String documentTypeToReview, String reviewType, BindingResult result, UserObject userObject);
	
	public void matchDocumentToStateno(long documentId, String stateno, BindingResult bindingResult, UserObject userObject);
	
	public void noMatchForADocument(long documentId, BindingResult bindingResult, UserObject userObject);

	public Map<String, Object> getDocumentIdAndPotentialMatchesForStatenosForQSReview(String filterCriteriaForStatusCode, String filterCriteriaForFileName, String filterCriteriaForFileType, String documentType,  
			BindingResult bindingResult, UserObject userObject);

	public void qualityStagedDocumentManualReview(long documentId, String stateno,
			String matchCode, String documentType, BindingResult bindingResult, UserObject userObject);

	public List<String> getQualityStageStatusCodesForTheFileName(
			String fileName, String documentType, BindingResult bindingResult,
			UserObject userObject);

	public List<String> getAvailabeDocumentTypes(String reviewType);

	public List<String> getDatafluxAvailabeDocumentTypes(String reviewType, UserObject userObject);

	public Map<String, Object> getDatafluxDocumentIdAndPotentialMatchesForStatenos(String documentType,
			String reviewType, BindingResult bindingResult, UserObject userObject);

	public void matchDocumentToStatenoInDataFlux(long documentId, String stateno, long fuzzyClusterId,
			BindingResult bindingResult, UserObject userObject);

	public void noMatchForDocumentToStatenoInDataFlux(long documentId, long fuzzyClusterId, String documentType, BindingResult bindingResult,
			UserObject userObject);

	public void reviewDatafluxStagedDocuments(long documentId, String stateno, Long clusterId, Long fuzzyClusterId, String match_cd, String documentType, String comments,
			BindingResult bindingResult, UserObject userObject);

	public void updateMatchResultsToDatafluxReviewDocument(long documentId, String stateno, Long clusterId,
			Long fuzzyClusterId, String match_cd, BindingResult bindingResult, UserObject userObject);

	public List<String> getDatafluxFileNamesToReview(String docType);

	public Map<String, Object> getDocumentIdAndPotentialMatchesForStatenosForDFReview(String fILTER_CRITERIA_STATUS,
			String fILTER_CRITERIA_FILE_NAME, String fILTER_CRITERIA_FILE_TYPE, String qS_REVIEW_DOCUMENT_TYPE,
			BindingResult bindingResult, UserObject userObject);

	public List<String> getDFStatusCodesForTheFileName(String fileName, String documentType,
			BindingResult bindingResult, UserObject userObject);

}
