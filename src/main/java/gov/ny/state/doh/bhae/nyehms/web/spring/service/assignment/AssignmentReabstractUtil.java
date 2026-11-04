package gov.ny.state.doh.bhae.nyehms.web.spring.service.assignment;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import gov.ny.state.doh.bhae.nyehms.web.spring.dao.assignment.AcrfDocLabsDAO;
import gov.ny.state.doh.bhae.nyehms.web.spring.dao.document.CommonDocLabsDAO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.CodedAcrfTestVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.acrf.CodedArvMedicationsVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.acrf.RawAcrfCd4TstVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.acrf.RawAcrfDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.acrf.RawAcrfLabsVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.CommonService;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.document.ACRFDocumentService;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.document.CommonDocumentService;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonUtil;


@Service
public class AssignmentReabstractUtil {
	
	@Autowired CommonDocLabsDAO commonDocLabsDAO;	
	@Autowired AcrfDocLabsDAO acrfLabDAO;		
	@Autowired CommonDocumentService commonDocumentService;
	@Autowired CommonService commonService;
	@Autowired ACRFDocumentService acrfFDocumentService;
	public boolean isLabTestExist(String parentTestType, String crfTestCode , String crfResultCode , String docType ,List<Map<String, Object>> labData) {
		boolean labTestExist = false;
		
		for (Map<String,Object> secondLabDataCurr : labData) {
			String secondParentTestType = (String) secondLabDataCurr.get("PARENT_TEST_TYPE");
			String secondCrfTestCode = (String) secondLabDataCurr.get(docType + "_TEST_CODE");
			String secondCrfResultCode = (String) secondLabDataCurr.get(docType + "_RESULT_CODE");
			
			if (!parentTestType.equals("05") && !parentTestType.equals("04")) {
				if (parentTestType.equals(secondParentTestType) && crfTestCode.equals(secondCrfTestCode)  && crfResultCode.equals(secondCrfResultCode) ) {
					labTestExist = true;
					break;
				}
			} else {
				if (parentTestType.equals(secondParentTestType) && crfTestCode.equals(secondCrfTestCode)) {
					labTestExist = true;
					break;
				}
			}
		}		
		
		return labTestExist;
	}
	
	public List<Map<String, Object>> getLabSameData(List<Map<String, Object>> labData1, List<Map<String, Object>> labData2, String docType, List<String> rawCrfLabDiffList, String documentId) {
		
		List<Map<String, Object>> labSameDataList = new ArrayList<>();
		String dataTypeId = "LAB_SRC_ID";
		for (Map<String,Object> labCurData1 : labData1) {
			String parentTestType = (String) labCurData1.get("PARENT_TEST_TYPE");
			String crfTestcode = (String) labCurData1.get(docType + "_TEST_CODE");
			String crfResultCode = (String) labCurData1.get(docType + "_RESULT_CODE");
			boolean isLabTestExist = isLabTestExist(parentTestType, crfTestcode, crfResultCode, docType, labData2);
			BigDecimal fsCrfSrcId = (BigDecimal) labCurData1.get(dataTypeId);
			labCurData1.remove(dataTypeId);
			if (isLabTestExist) {
				for (Map<String,Object> labCurData2 : labData2) {
					BigDecimal reabCrfSrcId = (BigDecimal) labCurData2.get(dataTypeId);
					labCurData2.remove(dataTypeId);
					if (labCurData1.equals(labCurData2)) {
						Map<String, Object> labDataMap = new HashMap<>();
						labDataMap.put("LAB_SRC_ID1", fsCrfSrcId);
						labDataMap.put("LAB_SRC_ID2", reabCrfSrcId);
						
						labCurData2.put(dataTypeId, reabCrfSrcId);
						labSameDataList.add(labDataMap);
						break;
					} 
					labCurData2.put(dataTypeId, reabCrfSrcId);
				}
			}
			labCurData1.put(dataTypeId, fsCrfSrcId);
		}	
		return labSameDataList;
	}
	
	public boolean isCD4TestExist(String closestToCurrentDx, List<Map<String, Object>> cd4Data) {
		boolean cd4TestExist = false;
		for (Map<String,Object> cd4DataCurr : cd4Data) {
			String SecClosestToCurrentDx = (String) cd4DataCurr.get("CLOSEST_TO_CURRENT_DX");
		//	String secondCollectionDate = (String) cd4DataCurr.get("COLLECTION_DATE");
			if (closestToCurrentDx.equals(SecClosestToCurrentDx)) {
				cd4TestExist = true;
				break;
			}
		}		
		return cd4TestExist;
	}
	
	public List<Map<String, Object>> getCd4SameData(List<Map<String, Object>> cd4Data1, List<Map<String, Object>> cd4Data2, String docType, List<String> rawCrfCd4DiffList, String documentId) {
		
		List<Map<String, Object>> cd4SameDataList = new ArrayList<>();
		String dataTypeId = "CD4_SRC_ID";
		for (Map<String,Object> cd4CurData1 : cd4Data1) {
			String closestToCurrentDx = (String) cd4CurData1.get("CLOSEST_TO_CURRENT_DX");
		//	String collectionDate = (String) cd4CurData1.get("COLLECTION_DATE");
			boolean isCd4TestExist = isCD4TestExist(closestToCurrentDx, cd4Data2);
			BigDecimal crfSrcIdData1 = (BigDecimal) cd4CurData1.get(dataTypeId);
			cd4CurData1.remove(dataTypeId);
			if (isCd4TestExist) {
				for (Map<String,Object> cd4CurData2 : cd4Data2) {
					BigDecimal crfSrcIdData2 = (BigDecimal) cd4CurData2.get(dataTypeId);
					cd4CurData2.remove(dataTypeId);
					if (cd4CurData1.equals(cd4CurData2)) {
						Map<String, Object> cd4DataMap = new HashMap<>();
						cd4DataMap.put("CD4_SRC_ID1", crfSrcIdData1);
						cd4DataMap.put("CD4_SRC_ID2", crfSrcIdData2);
						
						cd4CurData2.put(dataTypeId, crfSrcIdData2);
						cd4SameDataList.add(cd4DataMap);
						break;
					}
					cd4CurData2.put(dataTypeId, crfSrcIdData2);
				}
			} 
			cd4CurData1.put(dataTypeId, crfSrcIdData1);
		}	
		return cd4SameDataList;
	}

	public boolean isArvUseTypeExist(String arvUseType, List<Map<String, String>> arvUseTypeData) {
		boolean arvUseTypeExist = false;
		
			for (Map<String,String> secondArvCurr : arvUseTypeData) {
				String secondArvUseType = secondArvCurr.get("ARV_USE_TYPE");
				if (arvUseType.equals(secondArvUseType)) {
					arvUseTypeExist = true;
					break;
				}
			}
		return arvUseTypeExist;
	}

	public boolean isArvUseTypeExistInData(String arvUseType, List<CodedArvMedicationsVO> arvMedicationsList) {
		boolean arvUseTypeExist = false;
		
			for (CodedArvMedicationsVO secondArvCurr : arvMedicationsList) {
				String secondArvUseType = secondArvCurr.getArvUseType();
				if (arvUseType.equals(secondArvUseType)) {
					arvUseTypeExist = true;
					break;
				}
			}
		return arvUseTypeExist;
	}
	
	public List<String[]> createArvDataForDisplay(List<CodedArvMedicationsVO> arvFirstMedicationsList, List<CodedArvMedicationsVO> arvSecondMedicationsList){
		
		List<String[]> arvDisplayInfo = new ArrayList<>();
		String[] arvData;
		List<JSONObject> arvMedCodedList = commonDocLabsDAO.getCodedArvs();
		arvFirstMedicationsList = getArvMedDesc(arvFirstMedicationsList, arvMedCodedList);
		arvSecondMedicationsList = getArvMedDesc(arvSecondMedicationsList, arvMedCodedList);
	
		for (CodedArvMedicationsVO firstArvVO : arvFirstMedicationsList) {
			String arvUseType = firstArvVO.getArvUseType();
			String arvUseTypeNoSpace = arvUseType.replaceAll(" ", "");
			boolean arvUseTypeExist = isArvUseTypeExistInData(arvUseType, arvSecondMedicationsList);
		
			if (arvUseTypeExist) {
				for (CodedArvMedicationsVO secArvVO : arvSecondMedicationsList) {
					if (arvUseType.equals(secArvVO.getArvUseType())) {
						arvData = new String[6];
						arvData[0] = arvUseType;
						arvData[1] = "ARV Medications";
						arvData[2] = firstArvVO.getArvMedications();
						arvData[3] = secArvVO.getArvMedications();
						arvData[4] = arvUseTypeNoSpace;
						arvData[5] = "ARV_MEDICATION";
						arvDisplayInfo.add(arvData);
						
						arvData = new String[6];
						arvData[0] = "";
						arvData[1] = "Begin Date";
						arvData[2] = firstArvVO.getArvMedicationsDateBegan();
						arvData[3] = secArvVO.getArvMedicationsDateBegan();
						arvData[4] = arvUseTypeNoSpace;
						arvData[5] = "ARV_BEGIN_DATE";
						arvDisplayInfo.add(arvData);
						
						arvData = new String[6];
						arvData[0] = "";
						arvData[1] = "End Date";
						arvData[2] = firstArvVO.getArvMedicationsDateOfLastUse();
						arvData[3] = secArvVO.getArvMedicationsDateOfLastUse();
						arvData[4] = arvUseTypeNoSpace;
						arvData[5] = "ARV_END_DATE";
						arvDisplayInfo.add(arvData);
					}
				}
			} else {
				arvData = new String[6];
				arvData[0] = arvUseType;
				arvData[1] = "ARV Medications";
				arvData[2] = firstArvVO.getArvMedications();
				arvData[3] = "";
				arvData[4] = arvUseTypeNoSpace;
				arvData[5] = "ARV_MEDICATION";
				arvDisplayInfo.add(arvData);
				
				arvData = new String[6];
				arvData[0] = "";
				arvData[1] = "Begin Date";
				arvData[2] = firstArvVO.getArvMedicationsDateBegan();
				arvData[3] = "";
				arvData[4] = arvUseTypeNoSpace;
				arvData[5] = "ARV_BEGIN_DATE";
				arvDisplayInfo.add(arvData);
				
				arvData = new String[6];
				arvData[0] = "";
				arvData[1] = "End Date";
				arvData[2] = firstArvVO.getArvMedicationsDateOfLastUse();
				arvData[3] = "";
				arvData[4] = arvUseTypeNoSpace;
				arvData[5] = "ARV_END_DATE";
				arvDisplayInfo.add(arvData);
				
			}
		}
		
		for (CodedArvMedicationsVO secArvVO : arvSecondMedicationsList) {
			String arvUseType = secArvVO.getArvUseType();
			String arvUseTypeNoSpace = arvUseType.replaceAll(" ", "");
			boolean arvUseTypeExist = isArvUseTypeExistInData(arvUseType, arvFirstMedicationsList);
		
			if (!arvUseTypeExist) {
				arvData = new String[6];
				arvData[0] = arvUseType;
				arvData[1] = "ARV Medications";
				arvData[2] = "";
				arvData[3] = secArvVO.getArvMedications();
				arvData[4] = arvUseTypeNoSpace;
				arvData[5] = "ARV_MEDICATION";
				arvDisplayInfo.add(arvData);
				
				arvData = new String[6];
				arvData[0] = "";
				arvData[1] = "Begin Date";
				arvData[2] = "";
				arvData[3] = secArvVO.getArvMedicationsDateBegan();
				arvData[4] = arvUseTypeNoSpace;
				arvData[5] = "ARV_BEGIN_DATE";
				arvDisplayInfo.add(arvData);
				
				arvData = new String[6];
				arvData[0] = "";
				arvData[1] = "End Date";
				arvData[2] = "";
				arvData[3] = secArvVO.getArvMedicationsDateOfLastUse();
				arvData[4] = arvUseTypeNoSpace;
				arvData[5] = "ARV_END_DATE";
				arvDisplayInfo.add(arvData);
				
			}
		}
		
		return arvDisplayInfo;
		
	}
	
	public Map<String, String> getARVDiffData(List<Map<String, String>> arvUseData1, List<Map<String, String>> arvUseData2, 
										List<String> rawCrfArvDiffList, String documentId) {
		
		Map<String, String> arvUseTypeDiffMap = new HashMap<>();
	
		for (Map<String,String> arvCur2 : arvUseData2) {
			String diffArvsFields = "";
			String arvUseType2 = arvCur2.get("ARV_USE_TYPE");
			boolean arvUseTypeExist = isArvUseTypeExist(arvUseType2, arvUseData1);
			if (arvUseTypeExist) {
				for (Map<String,String> arvCur1 : arvUseData1) {
					if (arvCur2.equals(arvCur1)) {
						break;
					} else {
						if (arvUseType2.equals(arvCur1.get("ARV_USE_TYPE")) ) {
							for (String compareFieldName : rawCrfArvDiffList) {
								if (CommonUtil.isNotNullAndEmpty(arvCur2.get(compareFieldName)) && CommonUtil.isNotNullAndEmpty(arvCur1.get(compareFieldName)) &&
										!arvCur2.get(compareFieldName).equals(arvCur1.get(compareFieldName))) {
									diffArvsFields += compareFieldName + ",";
								} else if ( (CommonUtil.isNotNullAndEmpty(arvCur2.get(compareFieldName)) && CommonUtil.isNullOrEmpty(arvCur1.get(compareFieldName)))
										|| (CommonUtil.isNotNullAndEmpty(arvCur2.get(compareFieldName)) && CommonUtil.isNullOrEmpty(arvCur1.get(compareFieldName))) ) {
									diffArvsFields += compareFieldName + ",";
								}
							}
							
							if (CommonUtil.isNotNullAndEmpty(diffArvsFields) && diffArvsFields.endsWith(",")) {
								diffArvsFields = diffArvsFields.substring(0, diffArvsFields.length()-1);
							}
							System.out.println("ARV Diff fields : " + documentId + " Type : " + arvUseType2 + " Col : " + diffArvsFields);
							arvUseTypeDiffMap.put(arvUseType2, diffArvsFields);
						}
					}
				}
			} else {
				System.out.println("ARV Diff fields Not Exist: " + documentId + " Type : " + arvUseType2 + " Col : " + String.join(",", rawCrfArvDiffList) );
				arvUseTypeDiffMap.put(arvUseType2, "");
			}
		}
		
		return arvUseTypeDiffMap;
	
	}
	
	
	public Map<String, String> getARVSameData(List<Map<String, String>> arvUseData1, List<Map<String, String>> arvUseData2, 
										List<String> rawCrfArvDiffList, String documentId) {
		
		Map<String, String> arvUseTypeDiffMap = new HashMap<>();
	
		for (Map<String,String> arvCur2 : arvUseData2) {
			String arvUseType2 = arvCur2.get("ARV_USE_TYPE");
			boolean arvUseTypeExist = isArvUseTypeExist(arvUseType2, arvUseData1);
			if (arvUseTypeExist) {
				for (Map<String,String> arvCur1 : arvUseData1) {
					if (arvCur2.equals(arvCur1)) {
						arvUseTypeDiffMap.put(arvUseType2, "Y");
						break;
					} 
				}
			}
		}
		
		return arvUseTypeDiffMap;
	
	}

	public List<CodedArvMedicationsVO> getArvMedDesc(List<CodedArvMedicationsVO> arvMedicationsList, List<JSONObject> arvMedCodedList) {
		
		for (CodedArvMedicationsVO arvMedVO : arvMedicationsList) {
			if (CommonUtil.isNotNullAndEmpty(arvMedVO.getArvMedications())) {
				String arvMedDesc = "";
				String arvMedicationUsed = arvMedVO.getArvMedications();
				List<String> arvMedicationUsedList = new ArrayList<>();
				if (arvMedicationUsed.contains(",")) {
					arvMedicationUsedList = Arrays.asList(arvMedicationUsed.split(","));
				} else {
					arvMedicationUsedList.add(arvMedicationUsed);
				}
				
				for (String arvMedCode : arvMedicationUsedList) {
					for (JSONObject jsonObj : arvMedCodedList) {
						if (arvMedCode.equalsIgnoreCase((String)jsonObj.get("LOOKUP_CD")) ) {
							arvMedDesc += (String)jsonObj.get("ARV_DESC") + ", ";
							break;
						}
					}
				}
				arvMedDesc = arvMedDesc.replaceAll(", $", "");
				arvMedVO.setArvMedications(arvMedDesc);
			}
			
		}
		
		return arvMedicationsList;
	}

	public RawAcrfDocVO getPersonInfoCodesDesc(RawAcrfDocVO crfDocVO, BindingResult bindingResult, UserObject userObject, HttpServletRequest request) {
		
		List<JSONObject> addressTypes = commonDocLabsDAO.getAddressTypes();
		Map<String, String> codedCountries = commonDocumentService.getEharsCodedCountryValues();
		codedCountries.put("O", "Other");
		codedCountries.put("OTHER", "Other");
		codedCountries.put("U", "Unknown");
		codedCountries.put("UNKNOWN", "Unknown");
		Map<String, String> countyMap=commonService.getCountyCodes(bindingResult, userObject, request.getSession().getId());
		List<Map<String, String>> documentSourceList = acrfFDocumentService.getDocumentSourceList();
		List<JSONObject> codedSexAtBirthValues = commonDocLabsDAO.getCodedSexAtBirthValues();
		List<Map<String, String>> ethnicities = acrfFDocumentService.getCodedValues("ETHNICITY");
		List<Map<String, String>> occupations = acrfFDocumentService.getCodedValues("OCCUPATION");
		
			for (JSONObject addressType : addressTypes) {
				if (CommonUtil.isNotNullAndEmpty(crfDocVO.getAddressTypeCode()) && crfDocVO.getAddressTypeCode().equals((String)addressType.get("ADDRESS_TYPE_CODE")) ) {
					crfDocVO.setAddressTypeCode((String)addressType.get("ADDRESS_TYPE_DESCRIPTION"));
				}
				if (CommonUtil.isNotNullAndEmpty(crfDocVO.getHivAddressTypeCode()) && crfDocVO.getHivAddressTypeCode().equals((String)addressType.get("ADDRESS_TYPE_CODE")) ) {
					crfDocVO.setHivAddressTypeCode((String)addressType.get("ADDRESS_TYPE_DESCRIPTION"));
				}
				if (CommonUtil.isNotNullAndEmpty(crfDocVO.getAidsAddressTypeCode()) && crfDocVO.getAidsAddressTypeCode().equals((String)addressType.get("ADDRESS_TYPE_CODE")) ) {
					crfDocVO.setAidsAddressTypeCode((String)addressType.get("ADDRESS_TYPE_DESCRIPTION"));
				}
			}
			
			crfDocVO.setCountyFips(countyMap.get(crfDocVO.getCountyFips()));
			crfDocVO.setReportingHealthDeptCityCounty(countyMap.get(crfDocVO.getReportingHealthDeptCityCounty()));
			crfDocVO.setResHivCountyFips(countyMap.get(crfDocVO.getResHivCountyFips()));
			crfDocVO.setResAidsCountyFips(countyMap.get(crfDocVO.getResAidsCountyFips()));
			crfDocVO.setResPerinatalCountyFips(countyMap.get(crfDocVO.getResPerinatalCountyFips()));
			crfDocVO.setResPediatricSeroCountyFips(countyMap.get(crfDocVO.getResPediatricSeroCountyFips()));
			
			crfDocVO.setCountry(codedCountries.get(crfDocVO.getCountry()));
			crfDocVO.setBirthCountry(codedCountries.get(crfDocVO.getBirthCountry()));
			crfDocVO.setResHivCountry(codedCountries.get(crfDocVO.getResHivCountry()));
			crfDocVO.setResAidsCountry(codedCountries.get(crfDocVO.getResAidsCountry()));
			
			for (Map<String, String> docSrc : documentSourceList) {
				if (crfDocVO.getDocumentSource().equals((String)docSrc.get("DOCUMENT_SOURCE_CODE")) ) {
					crfDocVO.setDocumentSource((String)docSrc.get("DOCUMENT_SOURCE_DESCRIPTION"));
					break;
				}
			}
			
			String survMethodCd = crfDocVO.getSurveillanceMethodCode();
			switch(survMethodCd) {
			case "ACT" -> crfDocVO.setSurveillanceMethodCode("Active");
			case "PAS" -> crfDocVO.setSurveillanceMethodCode("Passive");
			case "FUP" -> crfDocVO.setSurveillanceMethodCode("Follow up");
			case "REAB" -> crfDocVO.setSurveillanceMethodCode("Reabstraction");
			case "U" -> crfDocVO.setSurveillanceMethodCode("Unknown");
			}

			String rptMedCd = crfDocVO.getReportMediumCode();
			switch(rptMedCd) {
			case "FV" -> crfDocVO.setReportMediumCode("Field Visit");
			case "Mailed" -> crfDocVO.setReportMediumCode("Mailed");
			case "Faxed" -> crfDocVO.setReportMediumCode("Faxed");
			case "Phone" -> crfDocVO.setReportMediumCode("Phone");
			case "ET" -> crfDocVO.setReportMediumCode("Phone");
			case "CDDisk" -> crfDocVO.setReportMediumCode("CD/Disk");
			}

			String vitalStatCd = crfDocVO.getVitalStatisticsCode();
			switch(vitalStatCd) {
			case "A" -> crfDocVO.setVitalStatisticsCode("Alive");
			case "D" -> crfDocVO.setVitalStatisticsCode("Dead");
			}		
			
			String code = crfDocVO.getBirthSex();
			for (JSONObject sexAtBirth : codedSexAtBirthValues) {
				if (code.equals((String)sexAtBirth.get("SEX_CODE")) ) {
					crfDocVO.setBirthSex((String)sexAtBirth.get("SEX_DESCRIPTION"));
					break;
				}
			}
			
			String genderCd = crfDocVO.getGenderCode();
			switch(genderCd) {
			case "M" -> crfDocVO.setGenderCode("Male");
			case "F" -> crfDocVO.setGenderCode("Female");
			case "X" -> crfDocVO.setGenderCode("Transgender Male-to-Female(MTF)");
			case "Y" -> crfDocVO.setGenderCode("Transgender Female-to-Male(FTM)");
			case "U" -> crfDocVO.setGenderCode("Unknown");
			case "O" -> crfDocVO.setGenderCode("Additional gender identity");
			}

			String ethnicityCd = crfDocVO.getEthnicityCode();
			switch(ethnicityCd) {
			case "01" -> crfDocVO.setEthnicityCode("Hispanic/Latino");
			case "02" -> crfDocVO.setEthnicityCode("Not Hispanic/Latino");
			case "09" -> crfDocVO.setEthnicityCode("Unknown");
			}
			
			for (Map<String, String> ethnicity : ethnicities) {
				if (crfDocVO.getExpandedEthnicity().equals((String)ethnicity.get("lookup_cd")) ) {
					crfDocVO.setExpandedEthnicity((String)ethnicity.get("lookup_name"));
					break;
				}
			}
			
			for (Map<String, String> occupation : occupations) {
				if (crfDocVO.getOccupation().equals((String)occupation.get("lookup_cd")) ) {
					crfDocVO.setOccupation((String)occupation.get("lookup_name"));
					break;
				}
			}
			
			String mainTthSrc = crfDocVO.getMainARVSource();
			switch(mainTthSrc) {
			case "PT_INTV" -> crfDocVO.setMainARVSource("Patient Interview");
			case "MED_REC" -> crfDocVO.setMainARVSource("Medical Record Review");
			case "PROV_RPT" -> crfDocVO.setMainARVSource("Provider Report");
			case "NHM_E_PEMS" -> crfDocVO.setMainARVSource("NHM&E");
			case "OTH" -> crfDocVO.setMainARVSource("Other");
			}
			
			crfDocVO.setAliasDateOfBirth(CommonUtil.parseDefaultDateValue(crfDocVO.getAliasDateOfBirth()));
			crfDocVO.setDateOfDeath(CommonUtil.parseDefaultDateValue(crfDocVO.getDateOfDeath()));
			crfDocVO.setDateRecvClottingFactor(CommonUtil.parseDefaultDateValue(crfDocVO.getDateRecvClottingFactor()));
			crfDocVO.setFirstDateRecvBlood(CommonUtil.parseDefaultDateValue(crfDocVO.getFirstDateRecvBlood()));
			crfDocVO.setLastDateRecvBlood(CommonUtil.parseDefaultDateValue(crfDocVO.getLastDateRecvBlood()));
			crfDocVO.setDateArvsBegan(CommonUtil.parseDefaultDateValue(crfDocVO.getDateArvsBegan()));
			crfDocVO.setDateLastUsedArvs(CommonUtil.parseDefaultDateValue(crfDocVO.getDateLastUsedArvs()));
			crfDocVO.setDateReported(CommonUtil.parseDefaultDateValue(crfDocVO.getDateReported()));
			crfDocVO.setDateFirstPosHivTest(CommonUtil.parseDefaultDateValue(crfDocVO.getDateFirstPosHivTest()));
			crfDocVO.setDateLastNegHivTest(CommonUtil.parseDefaultDateValue(crfDocVO.getDateLastNegHivTest()));
			
			//PCRF
			crfDocVO.setMotherFirstPosTestDate(CommonUtil.parseDefaultDateValue(crfDocVO.getMotherFirstPosTestDate()));
			crfDocVO.setMotherFirstRecvBloodDate(CommonUtil.parseDefaultDateValue(crfDocVO.getMotherFirstRecvBloodDate()));
			crfDocVO.setMotherLastRecvBloodDate(CommonUtil.parseDefaultDateValue(crfDocVO.getMotherLastRecvBloodDate()));
			crfDocVO.setClottingFactorBeforeDiagRecvDate(CommonUtil.parseDefaultDateValue(crfDocVO.getClottingFactorBeforeDiagRecvDate()));
			crfDocVO.setFirstRecvBloodBeforeDiagDate(CommonUtil.parseDefaultDateValue(crfDocVO.getFirstRecvBloodBeforeDiagDate()));
			crfDocVO.setLastRecvBloodBeforeDiagDate(CommonUtil.parseDefaultDateValue(crfDocVO.getLastRecvBloodBeforeDiagDate()));
			crfDocVO.setArvPriorPregnancyBeganDate(CommonUtil.parseDefaultDateValue(crfDocVO.getArvPriorPregnancyBeganDate()));
			crfDocVO.setArvPriorPregnancyLastUseDate(CommonUtil.parseDefaultDateValue(crfDocVO.getArvPriorPregnancyLastUseDate()));
			crfDocVO.setArvDuringPregnancyBeganDate(CommonUtil.parseDefaultDateValue(crfDocVO.getArvDuringPregnancyBeganDate()));
			crfDocVO.setArvDuringPregnancyLastUseDate(CommonUtil.parseDefaultDateValue(crfDocVO.getArvDuringPregnancyLastUseDate()));
			crfDocVO.setArvLaborPregnancyBeganDate(CommonUtil.parseDefaultDateValue(crfDocVO.getArvLaborPregnancyBeganDate()));
			crfDocVO.setArvLaborPregnancyLastUseDate(CommonUtil.parseDefaultDateValue(crfDocVO.getArvLaborPregnancyLastUseDate()));

		//}
		return crfDocVO;
		
	}
	
	public List<String[]> createCD4DataForDisplay(List<RawAcrfCd4TstVO> rawFsAcrfCd4Tst, List<RawAcrfCd4TstVO> rawReabAcrfCd4Tst, List<Map<String, Object>> compareDataMapList){
		
		List<String[]> cd4DisplayInfo = new ArrayList<>();
		String[] cd4Data;
		
		for (Map<String, Object> currentDataMap : compareDataMapList) {
			if (currentDataMap.get("TAB_NAME").equals("CD4_INFO")) {
				int fsSrcId =  ((BigDecimal)currentDataMap.get("FS_SRC_ID")).intValue();
				int reabSrcId =  ((BigDecimal)currentDataMap.get("REAB_SRC_ID")).intValue();
				String sameValue =  (String)currentDataMap.get("SAME_VALUES");
				String diffColNames =  (String)currentDataMap.get("DIFFERENT_COLUMN_NAMES");
	//			System.out.println("Sanjay ARV use Type: " + currentDataMap.get("ARV_USE_TYPE") + " Same : " + currentDataMap.get("SAME_VALUES") + " Col : " + currentDataMap.get("DIFFERENT_COLUMN_NAMES"));
				if (fsSrcId != 0 && reabSrcId != 0) {
					RawAcrfCd4TstVO fsCd4Test = getCd4Test(fsSrcId, rawFsAcrfCd4Tst);
					RawAcrfCd4TstVO reabCd4Test = getCd4Test(reabSrcId, rawReabAcrfCd4Tst);
					cd4Data = new String[7];
					cd4Data[0] = fsSrcId + "_" + reabSrcId;
					cd4Data[1] = "CD4 Count";
					cd4Data[2] = CommonUtil.convertIntToStr(fsCd4Test.getCd4Count());
					cd4Data[3] = CommonUtil.convertIntToStr(reabCd4Test.getCd4Count());
					cd4Data[4] = fsSrcId + "_" + reabSrcId;
					cd4Data[5] = "CD4_COUNT";		
					cd4Data[6] = reabCd4Test.getClosestToCurrentDx();	
					cd4DisplayInfo.add(cd4Data);
					
					cd4Data = new String[7];
					cd4Data[0] = "";
					cd4Data[1] = "CD4 Percent";
					cd4Data[2] = CommonUtil.convertFloatToStr(fsCd4Test.getCd4Percent());
					cd4Data[3] = CommonUtil.convertFloatToStr(reabCd4Test.getCd4Percent());
					cd4Data[4] = fsSrcId + "_" + reabSrcId;
					cd4Data[5] = "CD4_PERCENT";		
					cd4Data[6] = reabCd4Test.getClosestToCurrentDx();		
					cd4DisplayInfo.add(cd4Data);
					
					cd4Data = new String[7];
					cd4Data[0] = "";
					cd4Data[1] = "Collection Date";
					cd4Data[2] = CommonUtil.parseDefaultDateValue(fsCd4Test.getCollectionDate());
					cd4Data[3] = CommonUtil.parseDefaultDateValue(reabCd4Test.getCollectionDate());
					cd4Data[4] = fsSrcId + "_" + reabSrcId;
					cd4Data[5] = "COLLECTION_DATE";		
					cd4Data[6] = reabCd4Test.getClosestToCurrentDx();		
					cd4DisplayInfo.add(cd4Data);
				} else if (fsSrcId != 0 && reabSrcId == 0) {
					RawAcrfCd4TstVO fsCd4Test = getCd4Test(fsSrcId, rawFsAcrfCd4Tst);
					cd4Data = new String[7];
					cd4Data[0] = fsSrcId + "_" + reabSrcId;
					cd4Data[1] = "CD4 Count";
					cd4Data[2] = CommonUtil.convertIntToStr(fsCd4Test.getCd4Count());
					cd4Data[3] = "";
					cd4Data[4] = fsSrcId + "_" + reabSrcId;
					cd4Data[5] = "CD4_COUNT";		
					cd4Data[6] = fsCd4Test.getClosestToCurrentDx();
					cd4DisplayInfo.add(cd4Data);
					
					cd4Data = new String[7];
					cd4Data[0] = "";
					cd4Data[1] = "CD4 Percent";
					cd4Data[2] = CommonUtil.convertFloatToStr(fsCd4Test.getCd4Percent());
					cd4Data[3] = "";
					cd4Data[4] = fsSrcId + "_" + reabSrcId;
					cd4Data[5] = "CD4_PERCENT";		
					cd4Data[6] = fsCd4Test.getClosestToCurrentDx();		
					cd4DisplayInfo.add(cd4Data);
					
					cd4Data = new String[7];
					cd4Data[0] = "";
					cd4Data[1] = "Collection Date";
					cd4Data[2] = CommonUtil.parseDefaultDateValue(fsCd4Test.getCollectionDate());
					cd4Data[3] = "";
					cd4Data[4] = fsSrcId + "_" + reabSrcId;
					cd4Data[5] = "COLLECTION_DATE";		
					cd4Data[6] = fsCd4Test.getClosestToCurrentDx();	
					cd4DisplayInfo.add(cd4Data);
				} else if (fsSrcId == 0 && reabSrcId != 0) {
					RawAcrfCd4TstVO reabCd4Test = getCd4Test(reabSrcId, rawReabAcrfCd4Tst);
					cd4Data = new String[7];
					cd4Data[0] = fsSrcId + "_" + reabSrcId;
					cd4Data[1] = "CD4 Count";
					cd4Data[2] = "";
					cd4Data[3] = CommonUtil.convertIntToStr(reabCd4Test.getCd4Count());
					cd4Data[4] = fsSrcId + "_" + reabSrcId;
					cd4Data[5] = "CD4_COUNT";	
					cd4Data[6] = reabCd4Test.getClosestToCurrentDx();	
					cd4DisplayInfo.add(cd4Data);
					
					cd4Data = new String[7];
					cd4Data[0] = "";
					cd4Data[1] = "CD4 Percent";
					cd4Data[2] = "";
					cd4Data[3] = CommonUtil.convertFloatToStr(reabCd4Test.getCd4Percent());
					cd4Data[4] = fsSrcId + "_" + reabSrcId;
					cd4Data[5] = "CD4_PERCENT";		
					cd4Data[6] = reabCd4Test.getClosestToCurrentDx();
					cd4DisplayInfo.add(cd4Data);
					
					cd4Data = new String[7];
					cd4Data[0] = "";
					cd4Data[1] = "Collection Date";
					cd4Data[2] = "";
					cd4Data[3] = CommonUtil.parseDefaultDateValue(reabCd4Test.getCollectionDate());
					cd4Data[4] = fsSrcId + "_" + reabSrcId;
					cd4Data[5] = "COLLECTION_DATE";			
					cd4Data[6] = reabCd4Test.getClosestToCurrentDx();
					cd4DisplayInfo.add(cd4Data);
				}
			}
		}		

		return cd4DisplayInfo;
		
	}
	
	public RawAcrfCd4TstVO getCd4Test(int srcId, List<RawAcrfCd4TstVO> rawAcrfCd4Tst) {
		
		RawAcrfCd4TstVO cd4Test = new RawAcrfCd4TstVO();
		for (RawAcrfCd4TstVO currentVo : rawAcrfCd4Tst) {
			if (srcId == currentVo.getCd4SrcId()) {
				cd4Test = currentVo;
				break;
			}
		}
		return cd4Test;
	}
	
	public List<String[]> createLabDataForDisplay(List<RawAcrfLabsVO> rawFsAcrfLabs, List<RawAcrfLabsVO> rawReabAcrfLabs, List<Map<String, Object>> compareDataMapList){
		
		List<String[]> labDisplayInfo = new ArrayList<>();
		String[] labData;
		
		List<CodedAcrfTestVO> acrfTestCodeList = acrfLabDAO.getCodedAcrfResultTypes();
		
		rawFsAcrfLabs = getLabsCodesDesc(rawFsAcrfLabs, acrfTestCodeList);
		rawReabAcrfLabs = getLabsCodesDesc(rawReabAcrfLabs, acrfTestCodeList);
		
		for (Map<String, Object> currentDataMap : compareDataMapList) {
			if (currentDataMap.get("TAB_NAME").equals("LAB_INFO")) {
				int fsSrcId =  ((BigDecimal)currentDataMap.get("FS_SRC_ID")).intValue();
				int reabSrcId =  ((BigDecimal)currentDataMap.get("REAB_SRC_ID")).intValue();
				String sameValue =  (String)currentDataMap.get("SAME_VALUES");
				String diffColNames =  (String)currentDataMap.get("DIFFERENT_COLUMN_NAMES");
				if (fsSrcId != 0 && reabSrcId != 0) {
					RawAcrfLabsVO fsLabTest = getLabTest(fsSrcId, rawFsAcrfLabs);
					RawAcrfLabsVO reabLabTest = getLabTest(reabSrcId, rawReabAcrfLabs);
					labData = new String[7];
					labData[0] = reabLabTest.getParentTestType();
					labData[1] = fsSrcId + "_" + reabSrcId;
					labData[2] = "Test Type";
					labData[3] = fsLabTest.getAcrfTestCode();
					labData[4] = reabLabTest.getAcrfTestCode();
					labData[5] = fsSrcId + "_" + reabSrcId;
					labData[6] = "ACRF_TEST_CODE";
					labDisplayInfo.add(labData);
					
					labData = new String[7];
					labData[0] = reabLabTest.getParentTestType();
					labData[1] = "";
					labData[2] = "Test Result";
					labData[3] = fsLabTest.getAcrfResultCode();
					labData[4] = reabLabTest.getAcrfResultCode();
					labData[5] = fsSrcId + "_" + reabSrcId;
					labData[6] = "ACRF_RESULT_CODE";
					labDisplayInfo.add(labData);
					
					labData = new String[7];
					labData[0] = reabLabTest.getParentTestType();
					labData[1] = "";
					labData[2] = "Collection Date";
					labData[3] = fsLabTest.getCollectionDate();
					labData[4] = reabLabTest.getCollectionDate();
					labData[5] = fsSrcId + "_" + reabSrcId;
					labData[6] = "COLLECTION_DATE";
					labDisplayInfo.add(labData);
					
					labData = new String[7];
					labData[0] = reabLabTest.getParentTestType();
					labData[1] = "";
					labData[2] = "Rapid Test";
					labData[3] = String.valueOf(fsLabTest.isRapidTest());
					labData[4] = String.valueOf(reabLabTest.isRapidTest());
					labData[5] = fsSrcId + "_" + reabSrcId;
					labData[6] = "RAPID_TEST";
					labDisplayInfo.add(labData);
					
					labData = new String[7];
					labData[0] = reabLabTest.getParentTestType();
					labData[1] = "";
					labData[2] = "Copies/mL";
					labData[3] = fsLabTest.getCopiesMl();
					labData[4] = reabLabTest.getCopiesMl();
					labData[5] = fsSrcId + "_" + reabSrcId;
					labData[6] = "COPIES_ML";
					labDisplayInfo.add(labData);
					
					labData = new String[7];
					labData[0] = reabLabTest.getParentTestType();
					labData[1] = "";
					labData[2] = "Log";
					labData[3] = fsLabTest.getLog();
					labData[4] = reabLabTest.getLog();
					labData[5] = fsSrcId + "_" + reabSrcId;
					labData[6] = "LOG";
					labDisplayInfo.add(labData);
					
					labData = new String[7];
					labData[0] = reabLabTest.getParentTestType();
					labData[1] = "";
					labData[2] = "HIV-1 Ag";
					labData[3] = fsLabTest.getHiv1AgAnalyteResult();
					labData[4] = reabLabTest.getHiv1AgAnalyteResult();
					labData[5] = fsSrcId + "_" + reabSrcId;
					labData[6] = "HIV1_AG_ANALYTE_RESULT";
					labDisplayInfo.add(labData);
					
					labData = new String[7];
					labData[0] = reabLabTest.getParentTestType();
					labData[1] = "";
					labData[2] = "HIV-1 Ab";
					labData[3] = fsLabTest.getHiv1AbAnalyteResult();
					labData[4] = reabLabTest.getHiv1AbAnalyteResult();
					labData[5] = fsSrcId + "_" + reabSrcId;
					labData[6] = "HIV1_AB_ANALYTE_RESULT";
					labDisplayInfo.add(labData);
					
					labData = new String[7];
					labData[0] = reabLabTest.getParentTestType();
					labData[1] = "";
					labData[2] = "HIV-2 Ab";
					labData[3] = fsLabTest.getHiv2AbAnalyteResult();
					labData[4] = reabLabTest.getHiv2AbAnalyteResult();
					labData[5] = fsSrcId + "_" + reabSrcId;
					labData[6] = "HIV2_AB_ANALYTE_RESULT";
					labDisplayInfo.add(labData);
					
				} else if (fsSrcId != 0 && reabSrcId == 0) {
					RawAcrfLabsVO fsLabTest = getLabTest(fsSrcId, rawFsAcrfLabs);
					labData = new String[7];
					labData[0] = fsLabTest.getParentTestType();
					labData[1] = fsSrcId + "_" + reabSrcId;
					labData[2] = "Test Type";
					labData[3] = fsLabTest.getAcrfTestCode();
					labData[4] = "";
					labData[5] = fsSrcId + "_" + reabSrcId;
					labData[6] = "ACRF_TEST_CODE";
					labDisplayInfo.add(labData);
					
					labData = new String[7];
					labData[0] = fsLabTest.getParentTestType();
					labData[1] = "";
					labData[2] = "Test Result";
					labData[3] = fsLabTest.getAcrfResultCode();
					labData[4] = "";
					labData[5] = fsSrcId + "_" + reabSrcId;
					labData[6] = "ACRF_RESULT_CODE";
					labDisplayInfo.add(labData);
					
					labData = new String[7];
					labData[0] = fsLabTest.getParentTestType();
					labData[1] = "";
					labData[2] = "Collection Date";
					labData[3] = fsLabTest.getCollectionDate();
					labData[4] = "";
					labData[5] = fsSrcId + "_" + reabSrcId;
					labData[6] = "COLLECTION_DATE";
					labDisplayInfo.add(labData);
					
					labData = new String[7];
					labData[0] = fsLabTest.getParentTestType();
					labData[1] = "";
					labData[2] = "Rapid Test";
					labData[3] = String.valueOf(fsLabTest.isRapidTest());
					labData[4] = "";
					labData[5] = fsSrcId + "_" + reabSrcId;
					labData[6] = "RAPID_TEST";
					labDisplayInfo.add(labData);
					
					labData = new String[7];
					labData[0] = fsLabTest.getParentTestType();
					labData[1] = "";
					labData[2] = "Copies/mL";
					labData[3] = fsLabTest.getCopiesMl();
					labData[4] = "";
					labData[5] = fsSrcId + "_" + reabSrcId;
					labData[6] = "COPIES_ML";
					labDisplayInfo.add(labData);
					
					labData = new String[7];
					labData[0] = fsLabTest.getParentTestType();
					labData[1] = "";
					labData[2] = "Log";
					labData[3] = fsLabTest.getLog();
					labData[4] = "";
					labData[5] = fsSrcId + "_" + reabSrcId;
					labData[6] = "LOG";
					labDisplayInfo.add(labData);
					
					labData = new String[7];
					labData[0] = fsLabTest.getParentTestType();
					labData[1] = "";
					labData[2] = "HIV-1 Ag";
					labData[3] = fsLabTest.getHiv1AgAnalyteResult();
					labData[4] = "";
					labData[5] = fsSrcId + "_" + reabSrcId;
					labData[6] = "HIV1_AG_ANALYTE_RESULT";
					labDisplayInfo.add(labData);
					
					labData = new String[7];
					labData[0] = fsLabTest.getParentTestType();
					labData[1] = "";
					labData[2] = "HIV-1 Ab";
					labData[3] = fsLabTest.getHiv1AbAnalyteResult();
					labData[4] = "";
					labData[5] = fsSrcId + "_" + reabSrcId;
					labData[6] = "HIV1_AB_ANALYTE_RESULT";
					labDisplayInfo.add(labData);
					
					labData = new String[7];
					labData[0] = fsLabTest.getParentTestType();
					labData[1] = "";
					labData[2] = "HIV-2 Ab";
					labData[3] = fsLabTest.getHiv2AbAnalyteResult();
					labData[4] = "";
					labData[5] = fsSrcId + "_" + reabSrcId;
					labData[6] = "HIV2_AB_ANALYTE_RESULT";
					labDisplayInfo.add(labData);
					
				} else if (fsSrcId == 0 && reabSrcId != 0) {
					RawAcrfLabsVO reabLabTest = getLabTest(reabSrcId, rawReabAcrfLabs);
					labData = new String[7];
					labData[0] = reabLabTest.getParentTestType();
					labData[1] = fsSrcId + "_" + reabSrcId;
					labData[2] = "Test Type";
					labData[3] = "";
					labData[4] = reabLabTest.getAcrfTestCode();
					labData[5] = fsSrcId + "_" + reabSrcId;
					labData[6] = "ACRF_TEST_CODE";
					labDisplayInfo.add(labData);
					
					labData = new String[7];
					labData[0] = reabLabTest.getParentTestType();
					labData[1] = "";
					labData[2] = "Test Result";
					labData[3] = "";
					labData[4] = reabLabTest.getAcrfResultCode();
					labData[5] = fsSrcId + "_" + reabSrcId;
					labData[6] = "ACRF_RESULT_CODE";
					labDisplayInfo.add(labData);
					
					labData = new String[7];
					labData[0] = reabLabTest.getParentTestType();
					labData[1] = "";
					labData[2] = "Collection Date";
					labData[3] = "";
					labData[4] = reabLabTest.getCollectionDate();
					labData[5] = fsSrcId + "_" + reabSrcId;
					labData[6] = "COLLECTION_DATE";
					labDisplayInfo.add(labData);
					
					labData = new String[7];
					labData[0] = reabLabTest.getParentTestType();
					labData[1] = "";
					labData[2] = "Rapid Test";
					labData[3] = "";
					labData[4] = String.valueOf(reabLabTest.isRapidTest());
					labData[5] = fsSrcId + "_" + reabSrcId;
					labData[6] = "RAPID_TEST";
					labDisplayInfo.add(labData);
					
					labData = new String[7];
					labData[0] = reabLabTest.getParentTestType();
					labData[1] = "";
					labData[2] = "Copies/mL";
					labData[3] = "";
					labData[4] = reabLabTest.getCopiesMl();
					labData[5] = fsSrcId + "_" + reabSrcId;
					labData[6] = "COPIES_ML";
					labDisplayInfo.add(labData);
					
					labData = new String[7];
					labData[0] = reabLabTest.getParentTestType();
					labData[1] = "";
					labData[2] = "Log";
					labData[3] = "";
					labData[4] = reabLabTest.getLog();
					labData[5] = fsSrcId + "_" + reabSrcId;
					labData[6] = "LOG";
					labDisplayInfo.add(labData);
					
					labData = new String[7];
					labData[0] = reabLabTest.getParentTestType();
					labData[1] = "";
					labData[2] = "HIV-1 Ag";
					labData[3] = "";
					labData[4] = reabLabTest.getHiv1AgAnalyteResult();
					labData[5] = fsSrcId + "_" + reabSrcId;
					labData[6] = "HIV1_AG_ANALYTE_RESULT";
					labDisplayInfo.add(labData);
					
					labData = new String[7];
					labData[0] = reabLabTest.getParentTestType();
					labData[1] = "";
					labData[2] = "HIV-1 Ab";
					labData[3] = "";
					labData[4] = reabLabTest.getHiv1AbAnalyteResult();
					labData[5] = fsSrcId + "_" + reabSrcId;
					labData[6] = "HIV1_AB_ANALYTE_RESULT";
					labDisplayInfo.add(labData);
					
					labData = new String[7];
					labData[0] = reabLabTest.getParentTestType();
					labData[1] = "";
					labData[2] = "HIV-2 Ab";
					labData[3] = "";
					labData[4] = reabLabTest.getHiv2AbAnalyteResult();
					labData[5] = fsSrcId + "_" + reabSrcId;
					labData[6] = "HIV2_AB_ANALYTE_RESULT";
					labDisplayInfo.add(labData);
					
				}
			}
		}		

		return labDisplayInfo;
	}
	
	public RawAcrfLabsVO getLabTest(int srcId, List<RawAcrfLabsVO> rawAcrfLabs) {
		
		RawAcrfLabsVO labTest = new RawAcrfLabsVO();
		for (RawAcrfLabsVO currentVo : rawAcrfLabs) {
			if (srcId == currentVo.getLabSrcId()) {
				labTest = currentVo;
				break;
			}
		}
		return labTest;
	}	
	
	public List<RawAcrfLabsVO> getLabsCodesDesc(List<RawAcrfLabsVO> rawAcrfLabs, List<CodedAcrfTestVO> acrfTestCodeList) {
		
		for (RawAcrfLabsVO currentVO : rawAcrfLabs) {
			String acrfTestCode = currentVO.getAcrfTestCode();
			String hiv1AbAnalyteResult = currentVO.getHiv1AbAnalyteResult();
			String hiv2AbAnalyteResult = currentVO.getHiv2AbAnalyteResult();
			String hiv1AgAnalyteResult = currentVO.getHiv1AgAnalyteResult();
				for (CodedAcrfTestVO testCodeVO : acrfTestCodeList) {
					if (CommonUtil.isNotNullAndEmpty(acrfTestCode) && acrfTestCode.equals(testCodeVO.getAcrtTestCode())) {
						currentVO.setAcrfTestCode(testCodeVO.getAcrtTestDesc());
					}
					if (CommonUtil.isNotNullAndEmpty(hiv1AbAnalyteResult) && hiv1AbAnalyteResult.equals(testCodeVO.getAcrtTestCode())) {
						currentVO.setHiv1AbAnalyteResult(testCodeVO.getAcrtTestDesc());
					}
					if (CommonUtil.isNotNullAndEmpty(hiv2AbAnalyteResult) && hiv2AbAnalyteResult.equals(testCodeVO.getAcrtTestCode())) {
						currentVO.setHiv2AbAnalyteResult(testCodeVO.getAcrtTestDesc());
					}
					if (CommonUtil.isNotNullAndEmpty(hiv1AgAnalyteResult) && hiv1AgAnalyteResult.equals(testCodeVO.getAcrtTestCode())) {
						currentVO.setHiv1AgAnalyteResult(testCodeVO.getAcrtTestDesc());
					}
				}
		}
		return rawAcrfLabs;
	
	}
	
	public List<Map<String, Object>> removeBlankCd4Data(List<Map<String, Object>> cd4DataList) {
		
		List<Map<String, Object>> newCd4DataList = new ArrayList<>();
		
		if (cd4DataList.size() > 0) {
			for (Map<String, Object> currObj : cd4DataList) {
				String dateValue = currObj.get("COLLECTION_DATE").toString();
				if (CommonUtil.isNotNullAndEmpty(dateValue) && !"../../....".equals(dateValue) ) {
					newCd4DataList.add(currObj);
				}
	        }
		}
		
		return newCd4DataList;
	}
	
}
