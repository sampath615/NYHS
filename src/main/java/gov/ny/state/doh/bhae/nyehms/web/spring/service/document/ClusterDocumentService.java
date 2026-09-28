package gov.ny.state.doh.bhae.nyehms.web.spring.service.document;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import jakarta.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import gov.ny.state.doh.bhae.nyehms.web.spring.dao.document.ClusterDocumentDAO;
import gov.ny.state.doh.bhae.nyehms.web.spring.dao.document.CommonDocLabsDAO;
import gov.ny.state.doh.bhae.nyehms.web.spring.dao.jdbc.ConnectionDAO;
import gov.ny.state.doh.bhae.nyehms.web.spring.dao.review.RecordSearchReviewDAO;
import gov.ny.state.doh.bhae.nyehms.web.spring.dao.upload.FileUploadDAO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.ClusterDocPSMessageVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.ClusterDocReportVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.ClusterDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.ClusterIdReportDataVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.ClusterIdReportVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.uploads.ClusterTemplateVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.uploads.ClusterUploadVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.uploads.ClustereHARSImportFlagVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.CommonService;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonExceptionHandler;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonUtil;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.DateUtil;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.SendEmail;

@Service
public class ClusterDocumentService {
	
	private static Logger log = LoggerFactory.getLogger(ClusterDocumentService.class);

	
	@Autowired
	private ClusterDocumentDAO clusterDocumentDAO;
	
	
	@Autowired
	private CommonExceptionHandler commonExceptionHandler;
	
	@Autowired CommonDocumentService commonDocumentService;
	
	@Autowired FileUploadDAO fileUploadDAO;
	
	@Autowired SendEmail sendEmail;
	
	@Autowired private RecordSearchReviewDAO recordSearchReviewDAO;	
	
	@Autowired
	private CommonDocLabsDAO commonDocLabsDAO;
	
	@Autowired CommonService commonService;
	
	@Autowired ConnectionDAO connectionDAO;
	
	@Autowired DateUtil dateUtil;
	
	List<String> statenoNotFoundInEhars = new ArrayList<>();
	
	@Autowired	CommonUtil commonUtil;

	public ClusterDocVO getClusterDocVO(int documentId, BindingResult result, UserObject userObject) {
		
		String docClusterType = clusterDocumentDAO.getDocumentClusterType(documentId);
		
		Map<Object, Object> inputData = new HashMap<>();
		
		inputData.put("documentId", documentId);
		inputData.put("docClusterType", docClusterType);
		
		ClusterDocVO clusterDocVO = clusterDocumentDAO.getClusterDoc(inputData);
		
		if(CommonUtil.isNotNullAndEmpty(clusterDocVO.getCLUSTER_TYPE()) ) {

			clusterDocVO.setCLUSTER_TYPE_DESC(getClusterTypeDesc(clusterDocVO.getCLUSTER_TYPE()));
		}
		
		if (CommonUtil.isNotNullAndEmpty(clusterDocVO.getSTATUS_FLAG())) {
			
			clusterDocVO.setSTATUS_FLAG(getStatusFlagDesc(clusterDocVO.getSTATUS_FLAG()));
		}

		if (CommonUtil.isNotNullAndEmpty(clusterDocVO.getNYC_STATUS_FLAG())) {
			
			clusterDocVO.setNYC_STATUS_FLAG(getStatusFlagDesc(clusterDocVO.getNYC_STATUS_FLAG()));
		}
		
		if (CommonUtil.isNotNullAndEmpty(clusterDocVO.getUPLOAD_NYEHMS_STATENO()) && clusterDocVO.getUPLOAD_NYEHMS_STATENO().equals("Manual_Add")) {
			clusterDocVO.setCREATE_DATE(clusterDocVO.getCREATE_DATE() + " : Manual Add : " + clusterDocVO.getADDED_BY_USER()); 
		} else {
			clusterDocVO.setCREATE_DATE(clusterDocVO.getCREATE_DATE() + " : Bulk Upload : " + clusterDocVO.getADDED_BY_USER()); 
		}
		
		String isResRecord = clusterDocumentDAO.isResistanceRecord(clusterDocVO.getSTATENO());
				clusterDocVO.setIS_RES_RECORD(isResRecord);
		
		if(clusterDocVO.getSTATENO() != null && !"-1".equals(clusterDocVO.getSTATENO())) {
			List<ClusterDocPSMessageVO> clusterDocPSMessageList = clusterDocumentDAO.getClusterPSMessageInfo(clusterDocVO.getSTATENO());
			clusterDocVO.setClusterDocPSMessageList(clusterDocPSMessageList);
		}
		
		return clusterDocVO;
	}
	public List<JSONObject> getClusterIdsByType(String clusterType) {
		
		List<JSONObject> fileNamesObj = clusterDocumentDAO.getClusterIdsByType(clusterType);
		
		return fileNamesObj;
	}

	public List<JSONObject> getExistingClusterIdsByType(String clusterType) {
		
		Map<String, String> existingClusterType = new HashMap<>();
		
		existingClusterType.put("clusterType", clusterType);
		
		List<JSONObject> fileNamesObj = clusterDocumentDAO.getExistingClusterIdsByType(existingClusterType);
		
		return fileNamesObj;
	}

	public List<ClusterDocReportVO> getClusterReportsByTypeAndId(String clusterType, String clusterId, BindingResult bindingResult, UserObject userObject) {

		Map<String, String> reportInputData = new HashMap<>();
		
		reportInputData.put("clusterType", clusterType);
		reportInputData.put("clusterId", clusterId);
		
		List<ClusterDocReportVO> clusterReportObj = clusterDocumentDAO.getClusterReportsByTypeAndId(reportInputData);

		return clusterReportObj;
	}

	public Map<String, String> getClusterTypeList() {
		
		Map<String, String> clusterTypeList = new HashMap<>();

		clusterTypeList.put("BHAE_M", "BHAE Molecular");
		clusterTypeList.put("BHAE_TS", "BHAE Space - Time");
		clusterTypeList.put("CDC_M", "CDC Molecular");
		
		return clusterTypeList;
	}
	
	public void insertIntoRawCluster(Map<String, Object> clusterDetailsMap, UserObject userObject) {
		
		String documentType = getSubDocumentType(clusterDetailsMap.get("clusterType").toString());
		
		clusterDetailsMap.put("currrentProcessStatus", 5);
		clusterDocumentDAO.insertDataIntoClusterRaw(clusterDetailsMap);
		
		commonDocumentService.writeDocumentTestType(Integer.parseInt(clusterDetailsMap.get("documentId").toString()), documentType);
		
	}
		
	public void insertIntoStdCluster(Map<String, Object> clusterDetailsMap, ClusterDocVO currentDemoInfo, UserObject userObject, BindingResult bindingResult) {
		
			clusterDetailsMap.put("currrentProcessStatus", 5);
			clusterDetailsMap.put("firstName", currentDemoInfo.getFIRST_NAME());
			clusterDetailsMap.put("middleName", currentDemoInfo.getMIDDLE_NAME());
			clusterDetailsMap.put("lastName", currentDemoInfo.getLAST_NAME());
			clusterDetailsMap.put("dob", currentDemoInfo.getDOB());
			clusterDetailsMap.put("demo_data_src", currentDemoInfo.getDEMO_DATA_SOURCE());
			clusterDetailsMap.put("nyehmsStateno", currentDemoInfo.getCURRENT_NYEHMS_STATENO());
			clusterDetailsMap.put("eharsStateno", currentDemoInfo.getEHARS_STATENO());
			clusterDetailsMap.put("cityno", currentDemoInfo.getCITYNO());
			clusterDetailsMap.put("demoDocumentId", clusterDetailsMap.get("demoDocumentId"));
			
			clusterDocumentDAO.insertDataIntoClusterStd(clusterDetailsMap);
			
			if (clusterDetailsMap.get("clusterType").toString().equals("BHAE_TS")) {
				checkAndUpdateRecordSearch(currentDemoInfo.getCURRENT_NYEHMS_STATENO(), userObject, bindingResult);
			}	
		
	}
	
	
	public int getClusterDocumentID() {
		
		Map<Object,Object> map = new HashMap<>();
		int newDocId=0;
		
		map.put("p_document_type","CLUSTER");
		map.put("p_new_doc_id", "");
		
		try{			
			commonDocumentService.getNewDocId(map);
			newDocId=(Integer)map.get("p_new_doc_id");
		}catch (DataAccessException e) {
			
			throw new DataRetrievalFailureException("Failed to create a new Cluster document for "+newDocId+" :  "+e.getMessage());
			
		}
		
		return newDocId;
	}
	
	public String getSubDocumentType (String clusterType) {
		String subDocType = null;
		if (clusterType.equals("CDC_M")) {
			subDocType = "CLUST_CDC_MOL";
		} else if (clusterType.equals("BHAE_M")) {
			subDocType = "CLUST_BHAE_MOL";			
		} else if (clusterType.equals("BHAE_TS")) {
			subDocType = "CLUST_BHAE_TS";			
		}
		
		return subDocType;
	}

	
	
	
	
	public void moveStdDocumentToDocAttrTables(long documentId, UserObject userObject){
		
		Map<Object, Object> inputOutputParameterMap = new HashMap<>();
		
		if (documentId == 0) {
			
			inputOutputParameterMap.put("p_stateno", null);
			inputOutputParameterMap.put("p_user_id", 900);
			inputOutputParameterMap.put("p_documentId", null);
			inputOutputParameterMap.put("p_document_type", "CLUSTER");
			inputOutputParameterMap.put("p_called_from", null);
			
		} else {
			
			inputOutputParameterMap.put("p_stateno", null);
			inputOutputParameterMap.put("p_user_id", userObject.getWorkerId());
			inputOutputParameterMap.put("p_documentId", documentId);
			inputOutputParameterMap.put("p_document_type", "CLUSTER");
			inputOutputParameterMap.put("p_called_from", null);
		}
		
		
		commonDocLabsDAO.moveStdDocumentToDocAttrTables(inputOutputParameterMap);
		
		Integer statusCode=(Integer)inputOutputParameterMap.get("p_code_out");
		
		if(statusCode != 0){
			
			log.error("Exception occured while inserting Std document in Person tables : {}",inputOutputParameterMap.get("p_msg_out"));
			
			throw new DataRetrievalFailureException("Failed while associating the CLUSTER documents.");
		}
		
	}
	
	
	
	public void checkAndUpdateRecordSearch(String stateNo, UserObject userObject, BindingResult bindingResult) {
		
		String transactionMessage = null;
		
		Map<Object, Object> inOutParameterMap = new HashMap<>();
		String requestComments = "CLUST_BHAE_TS";
		
		inOutParameterMap.put("p_stateno", stateNo);

		int resultCount = recordSearchReviewDAO.checkForExistingRecordSearchStateno(inOutParameterMap);
		
		if(resultCount > 0){
			
			Map<String, String> inOutParameters = new HashMap<>();
			
			inOutParameters.put("p_requestComments", requestComments);
			inOutParameters.put("STATENO", stateNo);

			recordSearchReviewDAO.updateAutoRecordSearchEntry(inOutParameters);
			
		}else{
			inOutParameterMap.put("FLAG_ORIGIN", "Automatic");
			inOutParameterMap.put("p_workerId", userObject.getWorkerId());
			
			inOutParameterMap.put("p_requestComments", requestComments);
			
			resultCount = recordSearchReviewDAO.addRecordSearchFlagStatusEntry(inOutParameterMap);
			
			if(resultCount == 0){
				
				transactionMessage = "Unsuccessfully flagged for Record Search.  Please contact the helpline.";
			}
			
		}
		
		log.error(transactionMessage);
		commonExceptionHandler.inspectDBTransacionResult(-1, transactionMessage, bindingResult);
		
	}
	
	
	public void insertNyehmsUploadCluster(ClusterUploadVO clusterUploadVO, BindingResult bindingResult,UserObject userObject, HttpSession session) {
		
		String clusterId = clusterUploadVO.getClusterId();
		String clusterType = clusterUploadVO.getClusterType();
		String clusterFileName = clusterUploadVO.getUploadClusterFile();
		String uploadType = clusterUploadVO.getUploadType();
		String tsUpdateRetire = clusterUploadVO.getTsUpdateRetire(); 
		
		clusterUploadVO.setClusterCountiesCds(getCodesPipeSep(clusterUploadVO.getClusterCountiesList()));
		clusterUploadVO.setTransmissionRiskCds(getCodesPipeSep(clusterUploadVO.getTransmissionRiskList()));
		clusterUploadVO.setRaceCds(getCodesPipeSep(clusterUploadVO.getRaceList()));
		
		int workerId = userObject.getWorkerId();
		
		if(uploadType.equalsIgnoreCase("E")) {
			clusterId = clusterUploadVO.getExistingClusterId();
		}
		
		Map<String, Object> nyehmsUploadClusterMap = new HashMap<>();
		
		nyehmsUploadClusterMap.put("clusterId", clusterId);
		nyehmsUploadClusterMap.put("clusterType", clusterType);
		nyehmsUploadClusterMap.put("clusterFileName", clusterFileName);
		nyehmsUploadClusterMap.put("clusterCounties", clusterUploadVO.getClusterCountiesCds());
		nyehmsUploadClusterMap.put("workerId", workerId);
		nyehmsUploadClusterMap.put("uploadType", uploadType);
		nyehmsUploadClusterMap.put("description", clusterUploadVO.getDescription());
		nyehmsUploadClusterMap.put("relatedness", clusterUploadVO.getRelatedness());
		nyehmsUploadClusterMap.put("moleTimeFrame", clusterUploadVO.getMoleTimeFrame());
		nyehmsUploadClusterMap.put("spaceTimeDxStartDate", clusterUploadVO.getSpaceTimeDxStartDate());
		nyehmsUploadClusterMap.put("spaceTimeDxEndDate", clusterUploadVO.getSpaceTimeDxEndDate());
		
		nyehmsUploadClusterMap.put("transmissionRisk", clusterUploadVO.getTransmissionRiskCds());
		nyehmsUploadClusterMap.put("birthSex", clusterUploadVO.getBirthSex());
		nyehmsUploadClusterMap.put("race", clusterUploadVO.getRaceCds());
		nyehmsUploadClusterMap.put("ethnicity", clusterUploadVO.getEthnicity());
		nyehmsUploadClusterMap.put("ageRangeFrom", clusterUploadVO.getAgeRangeFrom());
		nyehmsUploadClusterMap.put("ageRangeTo", clusterUploadVO.getAgeRangeTo());
		
		if (tsUpdateRetire.equals("U")) {
			nyehmsUploadClusterMap.put("tsUpdateRetire", 1);
		} else if (tsUpdateRetire.equals("R")) {
			nyehmsUploadClusterMap.put("tsUpdateRetire", 2);
		}
		
		if(clusterType.equalsIgnoreCase("BHAE_M") || clusterType.equalsIgnoreCase("CDC_M") ) {
			nyehmsUploadClusterMap.put("clusterIdentifyDate", clusterUploadVO.getClusterIdentifyDate());
		} else {
			nyehmsUploadClusterMap.put("clusterIdentifyDate", "");
		}
		
		if(uploadType.equalsIgnoreCase("N")) {
			clusterDocumentDAO.insertNyehmsUploadCluster(nyehmsUploadClusterMap);
		}
		int clusterIdPk = clusterDocumentDAO.getClusterPkNumber(clusterId);
		
		nyehmsUploadClusterMap.put("clusterIdPk", clusterIdPk);
		clusterUploadVO.setClusterIdPk(clusterIdPk);
		
		if(clusterType.equalsIgnoreCase("BHAE_M") || clusterType.equalsIgnoreCase("CDC_M") ) {
			clusterDocumentDAO.insertNyehmsUploadClusterFiles(nyehmsUploadClusterMap);
		} else if( (uploadType.equalsIgnoreCase("N") && clusterType.equalsIgnoreCase("BHAE_TS")) || (uploadType.equalsIgnoreCase("E") && clusterType.equalsIgnoreCase("BHAE_TS") && tsUpdateRetire.equals("A"))) {
			clusterDocumentDAO.insertNyehmsTimeSpaceCriteria(nyehmsUploadClusterMap);
		} else if(uploadType.equalsIgnoreCase("E") && clusterType.equalsIgnoreCase("BHAE_TS") && tsUpdateRetire.equals("U")) {
			clusterDocumentDAO.retireNyehmsTimeSpaceCluster(nyehmsUploadClusterMap);
			clusterDocumentDAO.insertNyehmsTimeSpaceCriteria(nyehmsUploadClusterMap);
		} else if(uploadType.equalsIgnoreCase("E") && clusterType.equalsIgnoreCase("BHAE_TS") && tsUpdateRetire.equals("R")) {
			clusterDocumentDAO.retireNyehmsTimeSpaceCluster(nyehmsUploadClusterMap);
		}
			
	}
	

	public void insertIntoRawStdCluster(List<Map<String, Object>> clusterDataList,ClusterUploadVO clusterUploadVO, BindingResult bindingResult,UserObject userObject, HttpSession session) {
		
		String clusterType = clusterUploadVO.getClusterType();
		String documentType = getSubDocumentType(clusterType);
		String clusterId = clusterUploadVO.getClusterId();
		int clusterIdPk = clusterUploadVO.getClusterIdPk();
		
		String uploadType = clusterUploadVO.getUploadType();
		
		List<ClusterTemplateVO> currentOfAlreadyUploadedStatenosDataList = new ArrayList<>() ;
		
		if (uploadType.equals("E")) {
			clusterId = clusterUploadVO.getExistingClusterId();
			List<ClusterTemplateVO> alreadyUploadedStatenosDataList = clusterDocumentDAO.getAlreadyUploadedClusterData(clusterIdPk);
			
			for (ClusterTemplateVO alreadyuploadedStatenoData : alreadyUploadedStatenosDataList) {
				String currentOfalreadyUploadedStateno = commonService.getCurrentActiveStateNo(alreadyuploadedStatenoData.getNyehmsStateno());
				
				if (CommonUtil.isNotNullAndEmpty(currentOfalreadyUploadedStateno) && !currentOfalreadyUploadedStateno.equals("-1")) {
					alreadyuploadedStatenoData.setNyehmsStateno(currentOfalreadyUploadedStateno);
				}
				currentOfAlreadyUploadedStatenosDataList.add(alreadyuploadedStatenoData);
			}
		}
		
		ClusterDocVO currentDemoInfo = null;
		
		Map<String, String> countyMap=commonService.getCountyCodes(bindingResult, userObject, session.getId());
		clusterUploadVO.setClusterCountiesCds(getCountyCodesPipeSep(clusterUploadVO, countyMap));
		
		Map<String, Object> inputParameter = null;
		int documentId = 0;
		String currentNyehmsStateno = null;
		String identificationMethodCode =  null;
		boolean isStatenoIdentificationMethodAlreadyUploaded = false;
		int dupStatenoCount = 0;
		
			for (Map<String, Object> map : clusterDataList) {
				
				inputParameter = new HashMap<String, Object>();
				currentDemoInfo = null;
				
				documentId = getClusterDocumentID();
				
				inputParameter.put("documentId", documentId);
				inputParameter.put("clusterId", clusterId);
				inputParameter.put("clusterIdPk", clusterIdPk);
				inputParameter.put("clusterType", clusterType);
				
				inputParameter.put("uploadNyehmsStateno", (String)map.get("NYEHMS_STATENO"));
				identificationMethodCode =  (String)map.get("IDENTIFICATION_METHOD_CODE");
				inputParameter.put("identificationMethodCode", identificationMethodCode);
				inputParameter.put("identificationDate", (String)map.get("IDENTIFICATION_DATE"));
				currentNyehmsStateno = (String)map.get("CURRENT_NYEHMS_STATENO");
									
				if (CommonUtil.isNotNullAndEmpty(currentNyehmsStateno) && !currentNyehmsStateno.equals("-1")) {
					
					if (uploadType.equalsIgnoreCase("E") ) {
						
						for (ClusterTemplateVO alreadyuploadedStatenoData : currentOfAlreadyUploadedStatenosDataList) {
							if (currentNyehmsStateno.equals(alreadyuploadedStatenoData.getNyehmsStateno()) &&
									( CommonUtil.isNotNullAndEmpty(identificationMethodCode) && identificationMethodCode.equals(alreadyuploadedStatenoData.identificationMethod) )) {
								isStatenoIdentificationMethodAlreadyUploaded = true;
								break;
							} else {
								isStatenoIdentificationMethodAlreadyUploaded = false;
							}
						}
						
					}
					inputParameter.put("nyehmsStateno", currentNyehmsStateno);
					
					if(!isStatenoIdentificationMethodAlreadyUploaded) {
						
						long demoDocumentId = Long.parseLong((String)map.get("DOCUMENT_ID"));
						inputParameter.put("demoDocumentId", demoDocumentId);
						currentDemoInfo = clusterDocumentDAO.getStatenoDemographic(inputParameter);
						if (currentDemoInfo == null) {
							inputParameter.put("currrentProcessStatus", 40);
						} else {
							inputParameter.put("currrentProcessStatus", 5);
						}
					} else {
						inputParameter.put("currrentProcessStatus", 45);
						dupStatenoCount++;
					}
				} else {
					inputParameter.put("currrentProcessStatus", 39);
				}
					
				inputParameter.put("userId", Integer.toString(userObject.getWorkerId()));
				
				clusterDocumentDAO.insertDataIntoClusterRaw(inputParameter);
				
				commonDocumentService.writeDocumentTestType(documentId, documentType);
				
				if (currentDemoInfo != null) {
				
					insertIntoStdCluster(inputParameter, currentDemoInfo, userObject, bindingResult);
				}
				
			}
			clusterUploadVO.setDupStatenoCount(dupStatenoCount);
	}
	
	
	private String getCountyCodesPipeSep(ClusterUploadVO clusterUploadVO, Map<String,String> countyMap){
		String countyCds = "";

		if(clusterUploadVO.getClusterCountiesList() != null){
			for(String county: clusterUploadVO.getClusterCountiesList()){
				if(CommonUtil.isNotNullAndEmpty(county)) {
					countyCds += "|" + county;
				}
			}
			countyCds = countyCds.startsWith("|") ? countyCds.substring(1) : countyCds;
		}
		return countyCds;
	}
	
	private String getCodesPipeSep(List<String> allListValues){
		String listCds = "";

		if(allListValues != null){
			for(String listVal: allListValues){
				if(CommonUtil.isNotNullAndEmpty(listVal)) {
					listCds += "|" + listVal;
				}
			}
			listCds = listCds.startsWith("|") ? listCds.substring(1) : listCds;
		}
		return listCds;
	}	
	
	@Transactional
	public ClusterIdReportVO getClusterIdReports(String clusterType, String clusterId, String showDocuments, BindingResult bindingResult, UserObject userObject) {			
		
		Map<Object, Object> inOutParameterMap = new HashMap<>();
		inOutParameterMap.put("p_cluster_id", clusterId);
		
		clusterDocumentDAO.getClusterIdReport(inOutParameterMap);
		
//		@SuppressWarnings("unchecked")
//		List<ClusterIdReportHeaderVO> clusterIdReportHeaderList = (List<ClusterIdReportHeaderVO>)inOutParameterMap.get("p_clust_head_data");
		
		@SuppressWarnings("unchecked")
		List<ClusterIdReportDataVO> clusterIdReportDataList = (List<ClusterIdReportDataVO>)inOutParameterMap.get("p_clust_data");
		
		
		ClusterIdReportVO clusterIdReportVO = new ClusterIdReportVO();
		
//		clusterIdReportVO.setClusterIdReportHeaderDetails(clusterIdReportHeaderDetails);
		
		for (ClusterIdReportDataVO rptRow : clusterIdReportDataList) {
			if (CommonUtil.isNotNullAndEmpty(rptRow.getSort_ehars_hiv_dx_dt())) {
				rptRow.setSort_ehars_hiv_dx_dt(rptRow.getSort_ehars_hiv_dx_dt().replace("..","01"));
			}
			if (CommonUtil.isNotNullAndEmpty(rptRow.getSort_ehars_aids_dx_dt())) {
				rptRow.setSort_ehars_aids_dx_dt(rptRow.getSort_ehars_aids_dx_dt().replace("..","01"));
			}
			if (CommonUtil.isNotNullAndEmpty(rptRow.getSort_identification_date())) {
				rptRow.setSort_identification_date(rptRow.getSort_identification_date().replace("..","01"));
			}
			if (CommonUtil.isNotNullAndEmpty(rptRow.getStatus_flag())) {
				rptRow.setStatus_flag(getStatusFlagDesc(rptRow.getStatus_flag()));
			}
			if (rptRow.getStateno().equals("-1")) {
				rptRow.setStateno("");
			}
			
		}
		
		List<ClusterIdReportDataVO> showDataReportList = new ArrayList<>() ; 
		if (showDocuments.equals("I")) {
			for (ClusterIdReportDataVO rptRow : clusterIdReportDataList) {
				if (CommonUtil.isNotNullAndEmpty(rptRow.getDissassociation_date()) )
					showDataReportList.add(rptRow);
			}
		} else if (showDocuments.equals("C")) {
			for (ClusterIdReportDataVO rptRow : clusterIdReportDataList) {
				if (!clusterType.equals("BHAE_TS")) {
					if (CommonUtil.isNotNullAndEmpty(rptRow.getEhars_hiv_dx_dt()) && rptRow.getIdentification_method().equalsIgnoreCase("Through analysis/notification") ) {
						showDataReportList.add(rptRow);
					}
				} else {
					if (CommonUtil.isNullOrEmpty(rptRow.getDissassociation_date()) && 
							CommonUtil.isNotNullAndEmpty(rptRow.getEhars_hiv_dx_dt()) && rptRow.getIdentification_method().equalsIgnoreCase("Through analysis/notification") ) {
						showDataReportList.add(rptRow);
					}
				}
			}
		} else if (showDocuments.equals("R")) {
			for (ClusterIdReportDataVO rptRow : clusterIdReportDataList) {
				if (!clusterType.equals("BHAE_TS")) {				
					if (CommonUtil.isNullOrEmpty(rptRow.getEhars_hiv_dx_dt()) || CommonUtil.isNullOrEmpty(rptRow.getStateno())) {
						showDataReportList.add(rptRow);
					}
				} else {
					if (CommonUtil.isNullOrEmpty(rptRow.getDissassociation_date()) && (CommonUtil.isNullOrEmpty(rptRow.getEhars_hiv_dx_dt()) || CommonUtil.isNullOrEmpty(rptRow.getStateno())) ) {
						showDataReportList.add(rptRow);
					}
				}
			}
		} else if (showDocuments.equals("T")) {
			for (ClusterIdReportDataVO rptRow : clusterIdReportDataList) {
				if (!clusterType.equals("BHAE_TS")) {				
					if (CommonUtil.isNotNullAndEmpty(rptRow.getEhars_hiv_dx_dt()) && rptRow.getIdentification_method().equalsIgnoreCase("Through investigation")) {
						showDataReportList.add(rptRow);
					}
				} else {
					if (CommonUtil.isNullOrEmpty(rptRow.getDissassociation_date()) && 
							CommonUtil.isNotNullAndEmpty(rptRow.getEhars_hiv_dx_dt()) && rptRow.getIdentification_method().equalsIgnoreCase("Through investigation")  ) {
						showDataReportList.add(rptRow);
					}
				}
			}
		} else {
			for (ClusterIdReportDataVO rptRow : clusterIdReportDataList) {
				if (CommonUtil.isNullOrEmpty(rptRow.getDissassociation_date()) ) {
					showDataReportList.add(rptRow);
				}
			}
		}
		clusterIdReportVO.setClusterIdReportDataList(showDataReportList);
		
		return clusterIdReportVO;

	}	
	
	public String getClusterTypeDesc(String clusterType) {
		
		String clusterTypeDesc = "";
		if (clusterType.equals("CDC_M")) {
			clusterTypeDesc = "CDC Molecular";
		} else if (clusterType.equals("BHAE_M")) {
			clusterTypeDesc = "BHAE Molecular";
		} else if (clusterType.equals("BHAE_TS")) {
			clusterTypeDesc = "BHAE Space - Time";
		}
		
		return clusterTypeDesc;
	}

	public String getStatusFlagDesc(String statusFlag) {
		
		String statusFlagDesc = "";
		if (statusFlag.equals("W")) {
			statusFlagDesc = "Warning";
		} else if (statusFlag.equals("R")) {
			statusFlagDesc = "Required Var Missing";
		} else if (statusFlag.equals("A")) {
			statusFlagDesc = "Active";
		} else if (statusFlag.equals("E")) {
			statusFlagDesc = "Error";
		} else if (statusFlag.equals("D")) {
			statusFlagDesc = "Deleted";
		}
		
		return statusFlagDesc;
	}
	
	public List<String> getRelatednessData() {
		return clusterDocumentDAO.getRelatednessData();
	}
	
	public JSONObject getClusterData(String clusterId) {
		return clusterDocumentDAO.getClusterData(clusterId);
	}
	
	public Map<String, String> getMoleTimeFrameData() {
		Map<String, String> moleTimeFrameCodes=new LinkedHashMap<>();

		List<Map<String, String>> getMoleTimeFrameDataList = clusterDocumentDAO.getMoleTimeFrameData();
		for(Map<String, String> moleTimeFrameMap : getMoleTimeFrameDataList){
			moleTimeFrameCodes.put(moleTimeFrameMap.get("code").trim(), moleTimeFrameMap.get("value").trim());
		}

		return moleTimeFrameCodes;
	}
		
	public void bhaeTSCriteriaProcess(ClusterUploadVO clusterUploadVO) {
		
		clusterDocumentDAO.bhaeTSCriteriaProcess(Integer.toString(clusterUploadVO.getClusterIdPk()));
		
	}
	
	public List<Map<String, String>> getClusterIDentificationMethod() {
		return clusterDocumentDAO.getClusterIDentificationMethod();
	}

	@Transactional
	public void updateClustereHARSImportFlag(int[] clusterIdPks, UserObject userObject, BindingResult bindingResult) {
		
		Map<String, Object> eHARSImportFlagMap = new HashMap<>();
		
		eHARSImportFlagMap.put("workerId", userObject.getWorkerId());
		
		for (long clusterIdPk : clusterIdPks) {
			
			eHARSImportFlagMap.put("clusterIdPk", clusterIdPk);
			clusterDocumentDAO.updateClustereHARSImportFlag(eHARSImportFlagMap);
			
		}
	}	
	
	public List<ClustereHARSImportFlagVO> geteHARSImportFlagData(UserObject userObject, BindingResult bindingResult) {
		
		return clusterDocumentDAO.geteHARSImportFlagData();
	}
	
	public JSONObject getClusterDisplayInfo(String clusterId) {
		return clusterDocumentDAO.getClusterDisplayInfo(clusterId);
	}
	
	public List<JSONObject> getMolClusterDemoDocumentIds(String stateNo, String identificationCd, String notAssocClusterDetails) {
		
		Map<String, Object> demoDocumentIDsMap = new HashMap<>();
		
		String clusterType = "";
		if (CommonUtil.isNotNullAndEmpty(notAssocClusterDetails) && notAssocClusterDetails.contains("BHAE_M")) {
			clusterType = "BHAE_M";
		} else {
			clusterType = "CDC_M";
		}
		
		demoDocumentIDsMap.put("stateno", stateNo);
		demoDocumentIDsMap.put("identificationCd", identificationCd);
		demoDocumentIDsMap.put("clusterType", clusterType);
		
		List<JSONObject> demoDocumentsIds = clusterDocumentDAO.getMolClusterDemoDocumentIds(demoDocumentIDsMap);
		
		if (clusterType.equals("CDC_M") && identificationCd.equals("1") && demoDocumentsIds.size() == 0) {
			
			demoDocumentsIds = clusterDocumentDAO.getCDCMolClusterRecordSearchRIDRDemoDocumentIds(demoDocumentIDsMap);
		}
		
		return demoDocumentsIds;
		
	}
	public List<Integer> getLinkedMolClusterGenoTypeDocuments(int documentId) {
		
		List<Integer> linkedDocIds = clusterDocumentDAO.getLinkedMolClusterGenoTypeDocuments(documentId);
		
		return linkedDocIds;
	}
	public Map<String, String> getTransmissionCategoryData() {
		
		Map<String, String> transCategCodes=new LinkedHashMap<>();

		List<Map<String, String>> transCategDataList = clusterDocumentDAO.getTransmissionCategoryData();
		for(Map<String, String> transCategMap : transCategDataList){
			transCategCodes.put(transCategMap.get("CATEGORY_CODE").trim(), transCategMap.get("CATEG_DESC_SHORT").trim());
		}

		return transCategCodes;
	}
	
	public Map<String, String> getEharsRaceValues() {
		Map<String, String> eharsRaceCodes=new LinkedHashMap<>();

		List<Map<String, String>> eharsRaceDataList = clusterDocumentDAO.getEharsRaceData();
		for(Map<String, String> eharsRaceMap : eharsRaceDataList){
			eharsRaceCodes.put(eharsRaceMap.get("EHARS_RACE_CODE").trim(), eharsRaceMap.get("EHARS_RACE_DESC").trim());
		}

		return eharsRaceCodes;
	}
	
	public Map<String, String> getEharsEthnicityValues() {
		Map<String, String> eharsEthnicityCodes=new LinkedHashMap<>();

		List<Map<String, String>> eharsEthnicityDataList = clusterDocumentDAO.getEharsEthnicityData();
		for(Map<String, String> eharsEthnicityMap : eharsEthnicityDataList){
			eharsEthnicityCodes.put(eharsEthnicityMap.get("EHARS_ETH_CODE").trim(), eharsEthnicityMap.get("EHARS_ETH_DESC").trim());
		}

		return eharsEthnicityCodes;
	}
	
	
	public void validateStatenoWithCluster(Map<String, Object> inputParameterMap, BindingResult bindingResult) {
		int resultCount = clusterDocumentDAO.isStatenoActive(inputParameterMap);
		
		if(resultCount == 0) {
			throw new DataRetrievalFailureException("This stateno("+(String)inputParameterMap.get("p_stateno")+") is not active");
		}
		
		List<String> statenoList = clusterDocumentDAO.isEharsStatenoExist(inputParameterMap);
		
		if(statenoList == null || statenoList.size() == 0) {
			throw new DataRetrievalFailureException("This stateno("+(String)inputParameterMap.get("p_stateno")+") does not exist in eHARS");
		}
		
		int clusterIdPk = clusterDocumentDAO.getClusterPkNumber((String)inputParameterMap.get("p_clusterId"));
		
		inputParameterMap.put("clusterIdPk", clusterIdPk);
		
		resultCount = clusterDocumentDAO.isStatenoWithCluster(inputParameterMap);
		
		if(resultCount > 0) {
			throw new DataRetrievalFailureException("This stateno("+(String)inputParameterMap.get("p_stateno")+") is already associated with this cluster");
		}
	}
	
	
	@Transactional
	public void associateStatenoToCluster(Map<String, Object> inputParameterMap, BindingResult bindingResult ) {
		
		int resultCount = clusterDocumentDAO.associateStatenoWithCluster(inputParameterMap);
		
		if(resultCount == 0) {
			throw new DataRetrievalFailureException("Failed to associate stateno with the Cluster, please contact helpline");
		}
		
		clusterDocumentDAO.bhaeTSCriteriaProcess((String)inputParameterMap.get("clusterIdPk").toString());
		
	}
}
