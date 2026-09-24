package gov.ny.state.doh.bhae.nyehms.web.spring.service.schedulers.helpers;

import gov.ny.state.doh.bhae.nyehms.web.spring.dao.document.CCDDocumentDAO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.ccd.RawCCDDiagnosisVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.ccd.RawCCDDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.ccd.RawCCDEncounterVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.ccd.RawCCDLabResultVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.ccd.RawCCDLabTestVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.ccd.RawCCDMedVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.ccd.RawCCDPayerVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.ccd.RawCCDPersonAddressVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.ccd.RawCCDPersonDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.ccd.RawCCDProblemVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.ccd.RawCCDProviderVO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CCDLoadSchedulerHelper {

	@Autowired
	private CCDDocumentDAO ccdDocumentDAO;

	public CCDLoadSchedulerHelper() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Sets the private ccdDocumentDAO variable for use from outside Spring,
	 * where autowiring does not happen.
	 * 
	 * @param ccdddao
	 */
	public void setCCDDocumentDAO(CCDDocumentDAO ccdddao) {
		ccdDocumentDAO = ccdddao;
	}

	private static Logger log = LoggerFactory.getLogger(CCDLoadSchedulerHelper.class);

	/**
	 * Inserts a row into RAW_CCD_DOC and zero or more rows into each of the
	 * nine other RAW_CCD_XXX tables. TODO Implement error checking and/or
	 * exception handling
	 */
	
	public void writeRawCCDData(RawCCDDocVO theCCD, boolean individualLabResultInsert) {
/*
		int docId = theCCD.getDocumentId();
		RawCCDPersonDocVO thePerson = theCCD.getThePerson();
		// thePerson.setDOCUMENT_ID(docId);
		thePerson.setCURRENT_PROCESS_STATUS(1);
		thePerson.setHieName(theCCD.getHieName());
		thePerson.setPayloadname(theCCD.getPayloadName());
		ccdDocumentDAO.insertRawCcdDocData(thePerson);
		// ADDRESSES
		List<RawCCDPersonAddressVO> theAddresses = theCCD.getTheAddressList();
		if (theAddresses != null && theAddresses.size() > 0) {
			Map<String, Object> inputParameterMapAddress = new HashMap<String, Object>();
			inputParameterMapAddress.put("ccdDocId", docId);
			inputParameterMapAddress.put("addressesList", theAddresses);
			ccdDocumentDAO.insertCcdAddressList(inputParameterMapAddress);
		}
		// PROVIDERS
		List<RawCCDProviderVO> theProviders = theCCD.getTheProviderList();
		if (theProviders != null && theProviders.size() > 0) {
			Map<String, Object> inputParameterMapProviders = new HashMap<String, Object>();
			inputParameterMapProviders.put("ccdDocId", docId);
			inputParameterMapProviders.put("providersList", theProviders);
			ccdDocumentDAO.insertCcdProvidersList(inputParameterMapProviders);
		}
		// PAYERS
		List<RawCCDPayerVO> thePayers = theCCD.getThePayerList();
		if (thePayers != null && thePayers.size() > 0) {
			Map<String, Object> inputParameterMapPayers = new HashMap<String, Object>();
			inputParameterMapPayers.put("ccdDocId", docId);
			inputParameterMapPayers.put("payersList", thePayers);
			ccdDocumentDAO.insertCcdPayersList(inputParameterMapPayers);
		}
		List<RawCCDLabTestVO> theLabTestVOList = theCCD.getTheLabTestList();
		int labTestVOListLength = theLabTestVOList.size();
		if (theLabTestVOList != null && theLabTestVOList.size() > 0) {
			List<Integer> labTestSequences = ccdDocumentDAO.getLabTestSequences(labTestVOListLength);
			List<RawCCDLabResultVO> completeLabResultVOList = new ArrayList<RawCCDLabResultVO>();
			for (int i = 0; i < labTestVOListLength; i++) {
				int labTestId = labTestSequences.get(i);
				RawCCDLabTestVO rawCCDLabTestVO = theLabTestVOList.get(i);
				rawCCDLabTestVO.setCcdLabTestId(labTestId);
				List<RawCCDLabResultVO> labResultVOList = rawCCDLabTestVO.getTheLabResult();

				if (labResultVOList != null && labResultVOList.size() > 0) {
					for (RawCCDLabResultVO theLabResultVO : labResultVOList) {
						theLabResultVO.setCcdLabTestId(labTestId);
					}
					completeLabResultVOList.addAll(labResultVOList);
				}
			}
			Map<String, Object> inputParameterForLabInsert = new HashMap<String, Object>();
			inputParameterForLabInsert.put("ccdDocId", docId);
			inputParameterForLabInsert.put("LabsList", theLabTestVOList);
			ccdDocumentDAO.insertCcdLabTestList(inputParameterForLabInsert);
			if (completeLabResultVOList != null && completeLabResultVOList.size() > 0) {
				if (!individualLabResultInsert) {
					Map<String, Object> labResultMap = new HashMap<String, Object>();
					labResultMap.put("ccdDocId", docId);
					labResultMap.put("labResultsList", completeLabResultVOList);
					ccdDocumentDAO.insertCcdLabResultsList(labResultMap);
				} else {
					for (RawCCDLabResultVO resultVO : completeLabResultVOList) {
						int labTestId = resultVO.getCcdLabTestId();
						int labResultId = resultVO.getCcdLabResultId();
						int testNumber = resultVO.getTestSeqNo();
						int resultNumber = resultVO.getResultSeqNo();
						try {
							ccdDocumentDAO.insertRawCcdLabResult(resultVO);
						} catch (Exception e) {
							log.error("Failed to insert individual lab result for " + " CCD with DOCUMENT_ID = " + docId
									+ ", Lab test with ID = " + labTestId + ", Lab result with ID = " + labResultId
									+ ", Number of Test =  " + testNumber + ", Number of Result = " + resultNumber
									+ "\nException message: " + e.getMessage());
						}
					}
				}
			}
		}
		List<RawCCDEncounterVO> theEncounterVOList = theCCD.getTheEncounterList();
		int encounterVOListLength = theEncounterVOList.size();
		if (theEncounterVOList != null && theEncounterVOList.size() > 0) {
			List<Integer> encounterSequences = ccdDocumentDAO.getEncounterSequences(encounterVOListLength);
			List<RawCCDDiagnosisVO> completeDiagnosisList = new ArrayList<RawCCDDiagnosisVO>();
			List<RawCCDEncounterVO> _currentList = new ArrayList<RawCCDEncounterVO>();
			for (int i = 0; i < encounterVOListLength; i++) {
				int encounterDocId = encounterSequences.get(i);
				RawCCDEncounterVO rawCCDEncounterVO = theEncounterVOList.get(i);
				rawCCDEncounterVO.setEncounterDocId(encounterDocId);
				List<RawCCDDiagnosisVO> oneEncounterDiagnosisList = rawCCDEncounterVO.getTheDiagnoses();
				if (oneEncounterDiagnosisList != null && oneEncounterDiagnosisList.size() > 0) {
					for (RawCCDDiagnosisVO theDiagnosisVO : oneEncounterDiagnosisList) {
						theDiagnosisVO.setEncounterDocId(encounterDocId);
					}
					completeDiagnosisList.addAll(oneEncounterDiagnosisList);
				}
				_currentList.add(rawCCDEncounterVO);

				if (completeDiagnosisList.size() > 2000 || _currentList.size() > 1000) {
					Map<String, Object> inputParameterForEncounters = new HashMap<String, Object>();
					inputParameterForEncounters.put("ccdDocId", docId);
					inputParameterForEncounters.put("encounterList", _currentList);
					ccdDocumentDAO.insertCcdEncounterList(inputParameterForEncounters);
					_currentList = new ArrayList<RawCCDEncounterVO>();
					if (completeDiagnosisList.size() > 0) {
						Map<String, Object> diagnosisMap = new HashMap<String, Object>();
						diagnosisMap.put("diagnosisList", completeDiagnosisList);
						ccdDocumentDAO.insertCcdDiagnosisList(diagnosisMap);
						completeDiagnosisList = new ArrayList<RawCCDDiagnosisVO>();
					}
				}
			}
			if (_currentList.size() > 0) {
				Map<String, Object> inputParameterForEncounters = new HashMap<String, Object>();
				inputParameterForEncounters.put("ccdDocId", docId);
				inputParameterForEncounters.put("encounterList", _currentList);
				ccdDocumentDAO.insertCcdEncounterList(inputParameterForEncounters);
				_currentList = new ArrayList<RawCCDEncounterVO>();
			}
			if (completeDiagnosisList.size() > 0) {
				Map<String, Object> diagnosisMap = new HashMap<String, Object>();
				diagnosisMap.put("diagnosisList", completeDiagnosisList);
				ccdDocumentDAO.insertCcdDiagnosisList(diagnosisMap);
			}
		}
		// MEDS
		List<RawCCDMedVO> theMeds = theCCD.getTheMedList();
		if (theMeds != null && theMeds.size() > 0) {
			Map<String, Object> inputParameterMapMed = new HashMap<String, Object>();
			inputParameterMapMed.put("ccdDocId", docId);
			inputParameterMapMed.put("medsList", theMeds);
			ccdDocumentDAO.insertCcdMedsList(inputParameterMapMed);
		}
		// PROBLEMS
		List<RawCCDProblemVO> theProblems = theCCD.getTheProblemList();
		if (theProblems != null && theProblems.size() > 0) {
			Map<String, Object> inputParameterProblems = new HashMap<String, Object>();
			inputParameterProblems.put("ccdDocId", docId);
			inputParameterProblems.put("problemsList", theProblems);
			ccdDocumentDAO.insertCcdProblemsList(inputParameterProblems);
		}
	}*/

}}
