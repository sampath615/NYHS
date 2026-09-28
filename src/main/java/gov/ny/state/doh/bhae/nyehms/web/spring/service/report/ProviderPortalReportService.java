package gov.ny.state.doh.bhae.nyehms.web.spring.service.report;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import gov.ny.state.doh.bhae.nyehms.web.spring.dao.report.ProviderPortalReportDAO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.reports.ProvPortalOocDnltcGttDataVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.reports.ProvPortalReportVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.reports.ProvPortalSubmissionCareStatusVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.reports.ProvPortalSubmissionReportVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonUtil;

@Service
public class ProviderPortalReportService {
	
	@Autowired ProviderPortalReportDAO providerPortalReportDAO;

	public List<JSONObject> getProvPortalSubmittedProviders(String fileType) {
		
		Map<String, String> inputParameter = new HashMap<String, String>();
		
		if (CommonUtil.isNotNullAndEmpty(fileType) && ( fileType.equals("P") || fileType.equals("O") || fileType.equals("D") )) {
			inputParameter.put("fileType1", fileType);
			inputParameter.put("fileType2", "");
		} else if (CommonUtil.isNotNullAndEmpty(fileType) && fileType.equals("ALL")) {
			inputParameter.put("fileType1", "O");
			inputParameter.put("fileType2", "D");
		}
		
		return providerPortalReportDAO.getProvPortalSubmittedProviders(inputParameter);
	}

	@Transactional
	public List<ProvPortalSubmissionReportVO> getProvPortalSNPReportData(String fromDate, String toDate, String provId, BindingResult dummyBindingResult, UserObject userObject) {
		
		Map<Object, Object> inputData = new HashMap<Object, Object>();
		
		inputData.put("p_file_type_cd", "P");
		inputData.put("p_prov_id", provId);
		inputData.put("p_from_date", fromDate);
		inputData.put("p_to_date", toDate);
		
		providerPortalReportDAO.getProvPortalReportData(inputData);
		
		@SuppressWarnings("unchecked")
		List<ProvPortalSubmissionReportVO> snpReportList = (List<ProvPortalSubmissionReportVO>)inputData.get("p_snp_report");
		
		return snpReportList;
	}

	@Transactional
	public ProvPortalReportVO getProvPortalOOCDNLTCReportData(HttpServletRequest request, String fromDate, String toDate, String provId,
			String portalFileType, BindingResult dummyBindingResult, UserObject userObject) {
		

		Map<Object, Object> inputData = new HashMap<Object, Object>();
		ProvPortalReportVO provPortalRptVO = null;
		
		inputData.put("p_file_type_cd", portalFileType);
		inputData.put("p_prov_id", provId);
		inputData.put("p_from_date", fromDate);
		inputData.put("p_to_date", toDate);
		
		providerPortalReportDAO.getProvPortalReportData(inputData);
		
		provPortalRptVO = new ProvPortalReportVO();
		
		@SuppressWarnings("unchecked")
		List<ProvPortalSubmissionReportVO> oocDnltcReportList = (List<ProvPortalSubmissionReportVO>)inputData.get("p_ooc_dnltc_report");
		
		@SuppressWarnings("unchecked")
		List<ProvPortalSubmissionCareStatusVO> oocDnltcCareStatusList = (List<ProvPortalSubmissionCareStatusVO>)inputData.get("p_ooc_dnltc_care_status");
		
		provPortalRptVO.setProvPortalSubmissionDataList(oocDnltcReportList);
		provPortalRptVO.setProvPortalCareStatusDataList(oocDnltcCareStatusList);
		
		@SuppressWarnings("unchecked")
		List<ProvPortalOocDnltcGttDataVO> oocDnltcGttData = (List<ProvPortalOocDnltcGttDataVO>)inputData.get("p_eprf_ooc_gtt_data");
		HttpSession session = request.getSession();
		session.setAttribute("oocDnltcGttDataObject", oocDnltcGttData);
		
		return provPortalRptVO;
	}

	@SuppressWarnings("unchecked")
	public List<Map<String, String>> getSelectedStatenoList(HttpServletRequest request, String fileType, String filename,  String provId, String uploadDate, int colPos, String gridName) {

		HttpSession session = request.getSession();
		List<ProvPortalOocDnltcGttDataVO> oocDnltcGttData = (List<ProvPortalOocDnltcGttDataVO>) session.getAttribute("oocDnltcGttDataObject");
		
		List<Map<String, String>> statenoConclusionList = new ArrayList<Map<String, String>>();
		Map<String, String> statenoConclusionMap = null;
		
		if (gridName.equals("provPortalOOCDNLTCReportTable")) {
		
			for (ProvPortalOocDnltcGttDataVO iterator : oocDnltcGttData ) {
				
				if (fileType.equals("DNLTC") || fileType.equals("OOC")) {
					if (CommonUtil.isNotNullAndEmpty(iterator.getFile_type_cd()) && fileType.equals(iterator.getFile_type_cd()) 
							&& CommonUtil.isNotNullAndEmpty(iterator.getUpload_filename()) && filename.equalsIgnoreCase(iterator.getUpload_filename())
							&& CommonUtil.isNotNullAndEmpty(iterator.getProvider_id()) && provId.equals(iterator.getProvider_id())
							&& CommonUtil.isNotNullAndEmpty(iterator.getUpload_dt()) && uploadDate.equals(iterator.getUpload_dt())) {
						
						statenoConclusionMap = new HashMap<String, String>();
						
						if (colPos == 6 && CommonUtil.isNotNullAndEmpty(iterator.getStateno()) ) {
							statenoConclusionMap.put("STATENO", iterator.getStateno());
						} else if (colPos == 7 && (CommonUtil.isNotNullAndEmpty(iterator.getMatch_cd()) 
									&& (iterator.getMatch_cd().equals("M") || iterator.getMatch_cd().equals("C_M"))) ) {
							statenoConclusionMap.put("STATENO", iterator.getStateno());
						} else if (colPos == 8 && (CommonUtil.isNotNullAndEmpty(iterator.getMatch_cd()) 
									&& (iterator.getMatch_cd().equals("N") || iterator.getMatch_cd().equals("C_N") || iterator.getMatch_cd().equals("N_N"))
									&& CommonUtil.isNullOrEmpty(iterator.getStateno()) ) ) {
							statenoConclusionMap.put("STATENO", iterator.getDocument_id().toString());
							statenoConclusionMap.put("DOCTYPE", getPrfTestTypeCode(iterator.getFile_type_cd()));
						} else if (colPos == 9 && ((CommonUtil.isNotNullAndEmpty(iterator.getHiv_cdc()) && iterator.getHiv_cdc().equals("Y")) 
								|| (CommonUtil.isNotNullAndEmpty(iterator.getHiv_cdc()) && iterator.getAids_cdc().equals("Y"))) ) {
							statenoConclusionMap.put("STATENO", iterator.getStateno());
						} else if (colPos == 10 && (CommonUtil.isNotNullAndEmpty(iterator.getHiv_aids_cdc_elig()) && iterator.getHiv_aids_cdc_elig().equals("Y")) ) {
							statenoConclusionMap.put("STATENO", iterator.getStateno());
						}	
						
						if (statenoConclusionMap.size() > 0) {	
							statenoConclusionList.add(statenoConclusionMap);	
						}
					}
				}
				else {
					
					statenoConclusionMap = new HashMap<String, String>();
					
					if (colPos == 6 && CommonUtil.isNotNullAndEmpty(iterator.getStateno()) ) {
						statenoConclusionMap.put("STATENO", iterator.getStateno());
					} else if (colPos == 7 && (CommonUtil.isNotNullAndEmpty(iterator.getMatch_cd()) 
								&& (iterator.getMatch_cd().equals("M") || iterator.getMatch_cd().equals("C_M"))) ) {
						statenoConclusionMap.put("STATENO", iterator.getStateno());
					} else if (colPos == 8 && (CommonUtil.isNotNullAndEmpty(iterator.getMatch_cd()) 
								&& (iterator.getMatch_cd().equals("N") || iterator.getMatch_cd().equals("C_N") || iterator.getMatch_cd().equals("N_N"))
								&& CommonUtil.isNullOrEmpty(iterator.getStateno()) ) ) {
						statenoConclusionMap.put("STATENO", iterator.getDocument_id().toString());
						statenoConclusionMap.put("DOCTYPE", getPrfTestTypeCode(iterator.getFile_type_cd()));
					} else if (colPos == 9 && ((CommonUtil.isNotNullAndEmpty(iterator.getHiv_cdc()) && iterator.getHiv_cdc().equals("Y")) 
							|| (CommonUtil.isNotNullAndEmpty(iterator.getHiv_cdc()) && iterator.getAids_cdc().equals("Y"))) ) {
						statenoConclusionMap.put("STATENO", iterator.getStateno());
					} else if (colPos == 10 && (CommonUtil.isNotNullAndEmpty(iterator.getHiv_aids_cdc_elig()) && iterator.getHiv_aids_cdc_elig().equals("Y")) ) {
						statenoConclusionMap.put("STATENO", iterator.getStateno());
					}
					
					if (statenoConclusionMap.size() > 0) {	
						statenoConclusionList.add(statenoConclusionMap);	
					}
				}
			}
			
		} else if (gridName.equals("provPortalCareStatusReportTable")) {
			for (ProvPortalOocDnltcGttDataVO iterator : oocDnltcGttData ) {
				
				if (fileType.equals("DNLTC") || fileType.equals("OOC")) {
					if (CommonUtil.isNotNullAndEmpty(iterator.getFile_type_cd()) && fileType.equals(iterator.getFile_type_cd()) 
							&& CommonUtil.isNotNullAndEmpty(iterator.getUpload_filename()) && filename.equals(iterator.getUpload_filename())) {
						
						statenoConclusionMap = new HashMap<String, String>();
						
						if (colPos == 3 && CommonUtil.isNotNullAndEmpty(iterator.getCare_status()) && iterator.getCare_status().equals("No confirmatory results reported") ) {
							statenoConclusionMap.put("STATENO", iterator.getStateno());
							statenoConclusionMap.put("CONCLUSION", iterator.getConclusion());			
						} else if (colPos == 4 && CommonUtil.isNotNullAndEmpty(iterator.getCare_status()) && iterator.getCare_status().equals("Not a provider of this patient") ) {
							statenoConclusionMap.put("STATENO", iterator.getStateno());
							statenoConclusionMap.put("CONCLUSION", iterator.getConclusion());						
						} if (colPos == 5 && CommonUtil.isNotNullAndEmpty(iterator.getCare_status()) && iterator.getCare_status().equals("Deceased") ) {
							statenoConclusionMap.put("STATENO", iterator.getStateno());
							statenoConclusionMap.put("CONCLUSION", iterator.getConclusion());	
						} else if (colPos == 6 && CommonUtil.isNotNullAndEmpty(iterator.getCare_status()) && iterator.getCare_status().equals("Current residence is out of state") ) {
							statenoConclusionMap.put("STATENO", iterator.getStateno());
							statenoConclusionMap.put("CONCLUSION", iterator.getConclusion());	
						} else if (colPos == 7 && CommonUtil.isNotNullAndEmpty(iterator.getCare_status()) && iterator.getCare_status().equals("In care") ) {
							statenoConclusionMap.put("STATENO", iterator.getStateno());
							statenoConclusionMap.put("CONCLUSION", iterator.getConclusion());	
						} else if (colPos == 8 && CommonUtil.isNotNullAndEmpty(iterator.getCare_status()) && iterator.getCare_status().equals("Diagnosed Never Linked to Care") ) {
							statenoConclusionMap.put("STATENO", iterator.getStateno());
							statenoConclusionMap.put("CONCLUSION", iterator.getConclusion());	
						} else if (colPos == 9 && CommonUtil.isNotNullAndEmpty(iterator.getCare_status()) && iterator.getCare_status().equals("Out of Care") ) {
							statenoConclusionMap.put("STATENO", iterator.getStateno());
							statenoConclusionMap.put("CONCLUSION", iterator.getConclusion());	
						} else if (colPos == 10 && CommonUtil.isNotNullAndEmpty(iterator.getCare_status()) && iterator.getCare_status().equals("None") ) {
							statenoConclusionMap.put("STATENO", iterator.getStateno());
							statenoConclusionMap.put("CONCLUSION", "Not Reviewed");	
						} else if (colPos == 11 && CommonUtil.isNotNullAndEmpty(iterator.getCare_status()) && 
								( iterator.getCare_status().equals("No confirmatory results reported") || iterator.getCare_status().equals("Not a provider of this patient")
										|| iterator.getCare_status().equals("Deceased") || iterator.getCare_status().equals("Current residence is out of state")
										|| iterator.getCare_status().equals("In care") || iterator.getCare_status().equals("Diagnosed Never Linked to Care")
										|| iterator.getCare_status().equals("Out of Care") || iterator.getCare_status().equals("None") )  ) {
							statenoConclusionMap.put("STATENO", iterator.getStateno());
							if (! iterator.getCare_status().equals("None")) {
								statenoConclusionMap.put("CONCLUSION", iterator.getConclusion());	
							} else {
								statenoConclusionMap.put("CONCLUSION", "Not Reviewed");	
							}
						}				
						
						if (statenoConclusionMap.size() > 0) {	
							statenoConclusionList.add(statenoConclusionMap);	
						}
					}
				}
				else {
					
					statenoConclusionMap = new HashMap<String, String>();
					
					if (colPos == 3 && CommonUtil.isNotNullAndEmpty(iterator.getCare_status()) && iterator.getCare_status().equals("No confirmatory results reported") ) {
						statenoConclusionMap.put("STATENO", iterator.getStateno());
						statenoConclusionMap.put("CONCLUSION", iterator.getConclusion());						
					} else if (colPos == 4 && CommonUtil.isNotNullAndEmpty(iterator.getCare_status()) && iterator.getCare_status().equals("Not a provider of this patient") ) {
						statenoConclusionMap.put("STATENO", iterator.getStateno());
						statenoConclusionMap.put("CONCLUSION", iterator.getConclusion());						
					} if (colPos == 5 && CommonUtil.isNotNullAndEmpty(iterator.getCare_status()) && iterator.getCare_status().equals("Deceased") ) {
						statenoConclusionMap.put("STATENO", iterator.getStateno());
						statenoConclusionMap.put("CONCLUSION", iterator.getConclusion());	
					} else if (colPos == 6 && CommonUtil.isNotNullAndEmpty(iterator.getCare_status()) && iterator.getCare_status().equals("Current residence is out of state") ) {
						statenoConclusionMap.put("STATENO", iterator.getStateno());
						statenoConclusionMap.put("CONCLUSION", iterator.getConclusion());	
					} else if (colPos == 7 && CommonUtil.isNotNullAndEmpty(iterator.getCare_status()) && iterator.getCare_status().equals("In care") ) {
						statenoConclusionMap.put("STATENO", iterator.getStateno());
						statenoConclusionMap.put("CONCLUSION", iterator.getConclusion());	
					} else if (colPos == 8 && CommonUtil.isNotNullAndEmpty(iterator.getCare_status()) && iterator.getCare_status().equals("Diagnosed Never Linked to Care") ) {
						statenoConclusionMap.put("STATENO", iterator.getStateno());
						statenoConclusionMap.put("CONCLUSION", iterator.getConclusion());	
					} else if (colPos == 9 && CommonUtil.isNotNullAndEmpty(iterator.getCare_status()) && iterator.getCare_status().equals("Out of Care") ) {
						statenoConclusionMap.put("STATENO", iterator.getStateno());
						statenoConclusionMap.put("CONCLUSION", iterator.getConclusion());	
					} else if (colPos == 10 && CommonUtil.isNotNullAndEmpty(iterator.getCare_status()) && iterator.getCare_status().equals("None") ) {
						statenoConclusionMap.put("STATENO", iterator.getStateno());
						statenoConclusionMap.put("CONCLUSION", "Not Reviewed");						
					} else if (colPos == 11 && CommonUtil.isNotNullAndEmpty(iterator.getCare_status()) && 
							( iterator.getCare_status().equals("No confirmatory results reported") || iterator.getCare_status().equals("Not a provider of this patient")
									|| iterator.getCare_status().equals("Deceased") || iterator.getCare_status().equals("Current residence is out of state")
									|| iterator.getCare_status().equals("In care") || iterator.getCare_status().equals("Diagnosed Never Linked to Care")
									|| iterator.getCare_status().equals("Out of Care") || iterator.getCare_status().equals("None") )  ) {
						statenoConclusionMap.put("STATENO", iterator.getStateno());
						if (! iterator.getCare_status().equals("None")) {
							statenoConclusionMap.put("CONCLUSION", iterator.getConclusion());	
						} else {
							statenoConclusionMap.put("CONCLUSION", "Not Reviewed");	
						}		
					}	
					
					if (statenoConclusionMap.size() > 0) {	
						statenoConclusionList.add(statenoConclusionMap);	
					}
				}
			}
			
		}
		
		return statenoConclusionList;
	}
	
	private String getPrfTestTypeCode(String fileTypeCode) {
		String testTypeCode = null;
		
		if (fileTypeCode.equals("OOC")) {
			testTypeCode = "PROVIDER_OOC";
		}	else if (fileTypeCode.equals("DNLTC")) {
			testTypeCode = "PROVIDER_DNLTC";
		}
		
		return testTypeCode;
	}
	
}
