package gov.ny.state.doh.bhae.nyehms.web.spring.bo;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.CodedAcrfTestResultVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.CodedAcrfTestVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.acrf.AcrfFormVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.acrf.AcrfRidrVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.acrf.RawAcrfCd4TstVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.acrf.RawAcrfDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.acrf.RawAcrfLabsVO;
//import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonUtil;
import gov.ny.state.doh.bhae.nyehms.web.spring.enums.AppConstants;
import gov.ny.state.doh.bhae.nyehms.web.spring.exception.NyehmsException;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.document.ACRFDocumentService;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.document.CommonDocumentService;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonExceptionHandler;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonUtil;

import java.util.ArrayList;
//import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

@Component
public class ProcessACRFForm {

	// @Autowired
	// private CommonUtil commonUtil;
	@Autowired
	private CommonExceptionHandler commonExceptionHandler;
	@Autowired
	ACRFDocumentService acrfFDocumentService;
	@Autowired
	CommonDocumentService commonDocumentService;

	private static Logger log = LoggerFactory.getLogger(ProcessACRFForm.class);

	private String nyehmsLog = AppConstants.nyehmLogDebugString.getName();

	public RawAcrfDocVO getRawAcrfDocInFormModelObject(AcrfFormVO acrfFormVO, BindingResult result, String testType,
			String documentType) throws NyehmsException {
		RawAcrfDocVO rawAcrfDoc = acrfFormVO.getRawAcrfDoc();
		rawAcrfDoc.setDocumentId(getAcrfDocTblPK(result, rawAcrfDoc.getDocumentId(), testType, documentType));
		return rawAcrfDoc;
	}

	// Generate Primary Key for the ACRF Table from DB Sequence
	public int getAcrfDocTblPK(BindingResult result, int documentId, String testType, String documentType)
			throws NyehmsException {
		Map<Object, Object> docIdMap = new HashMap<Object, Object>();

		if ("PCRF".equals(documentType)) {
			docIdMap.put("p_document_type", "PCRF");
		} else {
			docIdMap.put("p_document_type", "ACRF");
		}

		docIdMap.put("p_new_doc_id", "");
		if (documentId != 0) {
			docIdMap.put("p_parent_doc_id", documentId);
		}
		docIdMap.put("p_test_type", testType);

		commonDocumentService.getNewDocId(docIdMap);

		int newTBDocId = (Integer) docIdMap.get("p_new_doc_id");
		log.debug(nyehmsLog + " Generated PK Value for the ACRF DOC Table. Value ::{}", newTBDocId);
		return newTBDocId;
	}

	// This will process coded tables and create required objects for the JSP.
	public void papulateLabTestCodeObjects(String documentType, List<CodedAcrfTestVO> codedAcrfTestList, AcrfFormVO acrfFormVO,
			UserObject userObject, String sessionId) {

		log.debug(nyehmsLog + " -- {}", codedAcrfTestList);

		Map<String, List<CodedAcrfTestVO>> labTestMap = new HashMap<String, List<CodedAcrfTestVO>>();

		// Declare Test and Test-Result Objects to be inserted in model object
		List<CodedAcrfTestVO> hivAntiBodyNonTypDiffTstMap = new ArrayList<CodedAcrfTestVO>();
		List<CodedAcrfTestVO> hivAntiBodyTypDiffTstMap = new ArrayList<CodedAcrfTestVO>();
		List<CodedAcrfTestVO> hivQualitativeTstMap = new ArrayList<CodedAcrfTestVO>();
		List<CodedAcrfTestVO> hivQuantitativeTstMap = new ArrayList<CodedAcrfTestVO>();
		List<CodedAcrfTestVO> drugResistanceTstMap = new ArrayList<CodedAcrfTestVO>();

		boolean isNewForm = false;
		
		if(acrfFormVO.getRawAcrfDoc().getAcrfRevison() == 0 || acrfFormVO.getRawAcrfDoc().getAcrfRevison() > 2022) {
			isNewForm = true;
		}
		
		// Get the tests for each test type
		int ttCode = 0;
		for (int i = 0; i < codedAcrfTestList.size(); i++) {
			try {
				ttCode = Integer.parseInt(codedAcrfTestList.get(i).getParentTestTypeCode());
			} catch (NumberFormatException e) {
				log.error(nyehmsLog + "--- NUMBER FORMAT EXCEPTION  -- {}", e.getMessage());
				commonExceptionHandler.handleException(e, userObject, sessionId);
			} catch (Exception e) {
				log.error(nyehmsLog + " :: ERROR :: {}", e.getMessage());
				commonExceptionHandler.handleException(e, userObject, sessionId);
			}
			
			
			if(isNewForm) {
				String testTypeSection = ttCode + "_" + codedAcrfTestList.get(i).getTestSection();

				if (labTestMap.get(testTypeSection) != null) {
					List<CodedAcrfTestVO> labTestResults = labTestMap.get(testTypeSection);
					labTestResults.add(codedAcrfTestList.get(i));
					labTestMap.put(testTypeSection, labTestResults);

				} else {
					List<CodedAcrfTestVO> labTestResults = new ArrayList<CodedAcrfTestVO>();
					labTestResults.add(codedAcrfTestList.get(i));
					labTestMap.put(testTypeSection, labTestResults);
				}
			}else {
			
				if (ttCode == 1) {
					hivAntiBodyNonTypDiffTstMap.add(codedAcrfTestList.get(i));
				} else if (ttCode == 2) {
					hivAntiBodyTypDiffTstMap.add(codedAcrfTestList.get(i));
				} else if (ttCode == 3) {
					hivQualitativeTstMap.add(codedAcrfTestList.get(i));
				} else if (ttCode == 4) {
					hivQuantitativeTstMap.add(codedAcrfTestList.get(i));
				} else if (ttCode == 5) {
					drugResistanceTstMap.add(codedAcrfTestList.get(i));
				}
			}
			



		}

		// Add the objects to corresponding form objects in model object.
		acrfFormVO.setHivABNonTypTstMap(hivAntiBodyNonTypDiffTstMap);
		acrfFormVO.setHivABTypTstMap(hivAntiBodyTypDiffTstMap);
		acrfFormVO.setHivQualitativeTypTstMap(hivQualitativeTstMap);
		acrfFormVO.setHivQuantitativeTstMap(hivQuantitativeTstMap);
		acrfFormVO.setDrugResistanceTstMap(drugResistanceTstMap);
		acrfFormVO.setLabTestResultMap(labTestMap);
	}

	// Initialize Form objects for the ACRF Form for the first time view
	public AcrfFormVO getFirstTimeAcrfVO(String documentType, BindingResult result, UserObject userObject, String sessionID) {
		// Main Model Object for the ACRF Form
		AcrfFormVO acrfFormVO = new AcrfFormVO();
		// ACRF Document Table Object
		RawAcrfDocVO rawAcrfDoc = new RawAcrfDocVO();

		AcrfRidrVO acrfRidrVO = new AcrfRidrVO();

		List<AcrfRidrVO> acrfRidrList = new ArrayList<AcrfRidrVO>();

		acrfRidrList.add(acrfRidrVO);

		acrfFormVO.setAcrfRidrList(acrfRidrList);

		// ACRF Labs Table Object
		List<RawAcrfLabsVO> rawAcrfLabList = new ArrayList<RawAcrfLabsVO>();

		// Set the 4 lab objects for 4 test types.
		RawAcrfLabsVO rawAcrfLab;

		Map<String, Integer> labDataSectionDataMap = new HashMap<String, Integer>();
		labDataSectionDataMap.put("01", 2);
		labDataSectionDataMap.put("02", 3);
		labDataSectionDataMap.put("03", 3);
		labDataSectionDataMap.put("04", 1);
		labDataSectionDataMap.put("05", 1);

		for (int i = 1; i <= 5; i++) {

			String parentTestTypeCode = "0" + i;

			for (int testSectionIndx = 1; testSectionIndx <= labDataSectionDataMap
					.get(parentTestTypeCode); testSectionIndx++) {
				rawAcrfLab = new RawAcrfLabsVO();
				rawAcrfLab.setParentTestType(parentTestTypeCode);
				rawAcrfLab.setTestSection(Integer.toString(testSectionIndx));
				rawAcrfLabList.add(rawAcrfLab);
			}

		}

		// Get Lab Test Data from the reference table.
		List<CodedAcrfTestVO> codedAcrfTestList = acrfFDocumentService.getCodedAcrfTest(result, userObject, sessionID);
		acrfFormVO.setRawAcrfDoc(rawAcrfDoc);
		// Populate main model object with drop down lists for lab test data
		papulateLabTestCodeObjects(documentType, codedAcrfTestList, acrfFormVO, userObject, sessionID);
		List<RawAcrfCd4TstVO> immunoLogicCd4TstList = new ArrayList<RawAcrfCd4TstVO>();
		immunoLogicCd4TstList.add(new RawAcrfCd4TstVO());
		// Add custom objects to the main model object to be used in JSP page
		acrfFormVO.setRawAcrfLabs(rawAcrfLabList);
		acrfFormVO.setRawAcrfCd4Tst(immunoLogicCd4TstList);

		// Return main model object for ACRF form JSP
		return acrfFormVO;
	}

	public AcrfFormVO populateLabTestInAcrfVO(String documentType, AcrfFormVO acrfFormVO, BindingResult result, UserObject userObject,
			String sessionID) {
		List<CodedAcrfTestVO> codedAcrfTestList = acrfFDocumentService.getCodedAcrfTest(result, userObject, sessionID);
		papulateLabTestCodeObjects(documentType, codedAcrfTestList, acrfFormVO, userObject, sessionID);
		return acrfFormVO;
	}

	// Preserve user selected result along with all other result options in VO
	public void preserveLabResults(AcrfFormVO acrfFormVO, BindingResult result, UserObject userObject,
			String sessionID) {
		// Map pMap = new HashMap();
		List<RawAcrfLabsVO> rawAcrfLabsList = acrfFormVO.getRawAcrfLabs();
		List<CodedAcrfTestResultVO> codedAcrfResultCodesList = acrfFDocumentService.getCodedAcrfResultTypes("", "",
				"no", acrfFormVO.getRawAcrfDoc().getAcrfRevison(), result, userObject, sessionID);

		String testCode = "";
		String parentTest = "";
		Map<Object, Object> labResultMap = new HashMap<Object, Object>();
		for (RawAcrfLabsVO rawAcrfLabs : rawAcrfLabsList) {
			testCode = rawAcrfLabs.getAcrfTestCode();
			parentTest = rawAcrfLabs.getParentTestType();
			labResultMap = new HashMap<Object, Object>();
			// log.debug(nyehmsLog+" testCode :: {} resultCode:: {} parentTest :: {}",
			// testCode, resultCode, parentTest);
			for (CodedAcrfTestResultVO codedAcrfTestResult : codedAcrfResultCodesList) {
				if (codedAcrfTestResult.getParentTestType().equals(parentTest)
						&& codedAcrfTestResult.getAcrfTestCode().equals(testCode)) {
					log.debug(nyehmsLog + "  Adding to Labs Map Result :: {}", codedAcrfTestResult.getAcrfResultCode());
					labResultMap.put(codedAcrfTestResult.getAcrfResultCode(), codedAcrfTestResult.getAcrfResultDesc());
				}
				rawAcrfLabs.setLabResultMap(labResultMap);
			}
		}
		// Add Missing Parent Test type Codes		
		if(acrfFormVO.getRawAcrfDoc().getAcrfRevison() == 0 || acrfFormVO.getRawAcrfDoc().getAcrfRevison() > 2022) {
			addMissingLabParentTestCodesFor2023Form(rawAcrfLabsList, userObject, sessionID);
		}else {
			addMissingLabParentTestCodes(rawAcrfLabsList, userObject, sessionID);
		}

		

	}

	/*
	 * Insert blank lab record for any missing parent type code. This means, if save
	 * Labs, only got records for parent test type code 1 and 2, then add empty
	 * records for parent test type code 3 and 4. This is required as this Lab Data
	 * List is used in JSP to display all types of results.
	 */

	public void addMissingLabParentTestCodes(List<RawAcrfLabsVO> rawAcrfLabsList, UserObject userObject,
			String sessionId) {

		log.debug(nyehmsLog + "  Missing Parent Test Type Codes being added");
		List<Integer> exsistingCode = new ArrayList<Integer>();
		int parentTesttype = 0;
		for (RawAcrfLabsVO rawAcrfLabs : rawAcrfLabsList) {
			log.debug(nyehmsLog + "  Parent Test Type Code :  {}", rawAcrfLabs.getParentTestType());
			try {
				parentTesttype = Integer.parseInt(rawAcrfLabs.getParentTestType());
			} catch (NumberFormatException e) {
				log.error(nyehmsLog + "--- NUMBER FORMAT EXCEPTION  -- {}", e.getMessage());
				commonExceptionHandler.handleException(e, userObject, sessionId);
			} catch (Exception e) {
				log.error(nyehmsLog + " :: ERROR :: {}", e.getMessage());
				commonExceptionHandler.handleException(e, userObject, sessionId);
			}

			if (parentTesttype > 0) {
				exsistingCode.add(parentTesttype);
			}
			log.debug(nyehmsLog + " ACRF Lab List already contains parent test type :  {}", parentTesttype);
		}

		RawAcrfLabsVO rawAcrfLabs;
		for (int i = 1; i < 6; i++) {
			if (!exsistingCode.contains(i)) {
				rawAcrfLabs = new RawAcrfLabsVO();
				rawAcrfLabs.setParentTestType("0" + i);
				rawAcrfLabsList.add(rawAcrfLabs);
				log.debug(nyehmsLog + " ACRF Lab List missing and being added value :  0" + i);
			}
		}
	}

	public void addMissingLabParentTestCodesFor2023Form(List<RawAcrfLabsVO> rawAcrfLabsList, UserObject userObject,
			String sessionId) {
		
		List<String> labTestSectionMap = new ArrayList<String>();
		
		labTestSectionMap.add("01_1");
		labTestSectionMap.add("01_2");
		labTestSectionMap.add("02_1");
		labTestSectionMap.add("02_2");
		labTestSectionMap.add("02_3");
		labTestSectionMap.add("03_1");
		labTestSectionMap.add("03_2");
		labTestSectionMap.add("03_3");
		labTestSectionMap.add("04_1");
		labTestSectionMap.add("05_1");
		

		log.debug(nyehmsLog + "  Missing Parent Test Type Codes being added");
		String parentTesttype;
		String testSection;
		for (RawAcrfLabsVO rawAcrfLabs : rawAcrfLabsList) {
			log.debug(nyehmsLog + "  Parent Test Type Code :  {}", rawAcrfLabs.getParentTestType());
			try {
				parentTesttype = rawAcrfLabs.getParentTestType();
				testSection = rawAcrfLabs.getTestSection();
				
				labTestSectionMap.remove(parentTesttype+"_"+testSection);
				
			} catch (NumberFormatException e) {
				log.error(nyehmsLog + "--- NUMBER FORMAT EXCEPTION  -- {}", e.getMessage());
				commonExceptionHandler.handleException(e, userObject, sessionId);
			} catch (Exception e) {
				log.error(nyehmsLog + " :: ERROR :: {}", e.getMessage());
				commonExceptionHandler.handleException(e, userObject, sessionId);
			}
			
		}

		RawAcrfLabsVO rawAcrfLabs;
		for (String  labTestSection : labTestSectionMap) {
			
			parentTesttype = labTestSection.split("_")[0];
			testSection = labTestSection.split("_")[1];
			
			rawAcrfLabs = new RawAcrfLabsVO();
			rawAcrfLabs.setParentTestType(parentTesttype);
			rawAcrfLabs.setTestSection(testSection);
			rawAcrfLabsList.add(rawAcrfLabs);
			log.debug(nyehmsLog + " ACRF Lab List missing and being added value parentSection {}, testSEction :  " ,parentTesttype,testSection);
		}
		
		
	}

}
