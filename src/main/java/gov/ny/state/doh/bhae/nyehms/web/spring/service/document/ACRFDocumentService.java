package gov.ny.state.doh.bhae.nyehms.web.spring.service.document;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.CodedAcrfTestResultVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.CodedAcrfTestVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.CodedParentTestTypeVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.acrf.AcrfFormVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.acrf.PcrfSubstanceDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.acrf.RawAcrfCd4TstVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.acrf.RawAcrfDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.acrf.RawAcrfLabsVO;
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

public interface ACRFDocumentService {
	
	public RawAcrfDocVO getRawACRFDoc(String rawAcrfDocId, String documentType, BindingResult result, UserObject userObject, String sessionID);
	public void addRawACRFDoc(RawAcrfDocVO rawAcrfDoc,  String documentType, BindingResult result, UserObject userObject, String sessionID);
	public void updateRawACRFDoc(AcrfFormVO acrfFormVo,  String documentType, BindingResult result, UserObject userObject, String sessionID);
	public boolean addRawACRFLabsList(AcrfFormVO acrfFormVo, String documentType, BindingResult result, UserObject userObject, String sessionID);
	public List<RawAcrfLabsVO> getRawACRFLabs(String rawAcrfDocId, String documentType, BindingResult result, UserObject userObject, String sessionID);
	public List<RawAcrfCd4TstVO> getRawAcrfCd4Data(String rawAcrfDocId, String documentType, int acrfRevison, BindingResult result, UserObject userObject, String sessionID);	
	public AcrfFormVO getAcrfFormVO(String rawAcrfDocId, String documentType, BindingResult result, UserObject userObject, String sessionID);	
	public boolean updateRawACRFLabs(AcrfFormVO acrfFormVo, String documentType, BindingResult result, UserObject userObject, String sessionID);	
	public boolean updateRawAcrfCd4Data(AcrfFormVO acrfFormVo, String documentType, BindingResult result, UserObject userObject, String sessionID);	
	public boolean addRawAcrfCd4Data(AcrfFormVO acrfFormVo, String documentType, BindingResult result, UserObject userObject, String sessionID);		
	public boolean addACRFDoc(AcrfFormVO acrfFormVO, String documentType, String actionName, boolean havingNoErrorsinForm, String assignmentId, BindingResult result, UserObject userObject, String sessionID, String closureCategory);
	public boolean updateACRFDoc(AcrfFormVO acrfFormVO, String documentType, String actionName, boolean havingNoErrorsinForm, BindingResult result, UserObject userObject, String sessionID, String closureCategory);		
	public List<CodedAcrfTestVO> getCodedAcrfTest(BindingResult result, UserObject userObject, String sessionID);
	public List<CodedAcrfTestResultVO> getCodedAcrfResultTypes(String pParentTestType, String pAcrfTestCode, String condition, int acrfRevison, BindingResult result, UserObject userObject, String sessionID);
	public List<CodedParentTestTypeVO> getCodedParentTestType(BindingResult result, UserObject userObject, String sessionID);

	public void updateSurveillanceAssignmentWithAcrfDocId(AcrfFormVO acrfFormVO, String assignmentId, String documentType, BindingResult result, UserObject userObject,  String sessionID);
	
	public void discardACRFForm(String acrfDocId, String documentType, String assignmentId, String reabstract, BindingResult bindingResult, UserObject userObject, String sessionID); 
	
	public List<Map<String, String>> getStandardFacilityDetails();
	public List<Map<String, String>> getDocumentSourceList();
	public List<Map<String, String>> getAcrfOtherIdTypeList();
	
	public List<Map<String, String>> getCompleteRegionsList();
	
	public List<Map<String, String>> getCodedValues(String fieldName);
	public boolean addRawArvMedData(AcrfFormVO acrfFormVO,
			String documentType, BindingResult result, UserObject userObject, String sessionID);
	public boolean updateRawMedicationsData(AcrfFormVO acrfFormVO,
			String documentType, BindingResult result, UserObject userObject, String sessionID);
	public List<String> getArvUserTypeList();
	public void addACRFFormAndAssociateToStateno(String acrfReferenceRowId, AcrfFormVO acrfFormVO, String testType,
			String stateno, BindingResult bindingResult, UserObject userObject);
	public boolean addRawAcrfRidrData(AcrfFormVO acrfFormVO,
			BindingResult result, UserObject userObject, String sessionID);
	public boolean updateRawAcrfRidrData(AcrfFormVO acrfFormVO,
			BindingResult result, UserObject userObject, String sessionID);
	public RawAcrfDocVO getAcrfFormDataFromAcrf(String stateNo);
	public List<Map<String, String>> getACRFReferenceData(String referenceColumn);
	public List<Map<String, String>> getClinicalLaboratoryDetails();
	public List<PcrfSubstanceDocVO> getScreenSubstanceData(String docId);
	public List<PcrfSubstanceDocVO> getBpUseSubstanceList(String docId);
	public List<PcrfSubstanceDocVO> getBpScreenSubstanceList(String docId);
	public List<PcrfSubstanceDocVO> getBpScreenConditionsList(String docId);
	public List<PcrfSubstanceDocVO> getBpDxScreenConditionsList(String docId);
	public void updateSubStanceList(int documentId, List<PcrfSubstanceDocVO> list, String string, BindingResult result, UserObject userObject);
	public void updateScreenConditionsList(int documentId, List<PcrfSubstanceDocVO> bpScreenConditionsList, String string,
			BindingResult result, UserObject userObject);
	public void updatePregOutcomeList(int documentId, List<PcrfSubstanceDocVO> pregOutcomeList, String pregOutcome, BindingResult result, UserObject userObject);
	public List<PcrfSubstanceDocVO> getBpPregOutcomeList(String docId);
}
