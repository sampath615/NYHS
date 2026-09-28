package gov.ny.state.doh.bhae.nyehms.web.spring.controller.document;

import gov.ny.state.doh.bhae.nyehms.web.ajax.json.GridObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.common.AddressFieldVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.ClusterDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.ClusterIdReportVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.enums.AppConstants;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.document.ClusterDocumentService;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.document.DocumentTransactionHelper;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.BindingResults;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.MapBindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.GetGridObject;

@Controller
public class ClusterDocumentController {
	
	private static Logger log = LoggerFactory.getLogger(ClusterDocumentController.class);
	private String nyehmsLog = AppConstants.nyehmLogDebugString.getName();
	
	@Autowired
	GetGridObject getGridObject;
	
	@Autowired
	private ClusterDocumentService clusterDocumentService;
	
	@Autowired	DocumentTransactionHelper documentTransactionHelper;
	
	
	@RequestMapping(value = "/getClusterForm.html")
	public String getClusterDocAsHTML(@RequestParam(required=false) Integer docId, Map<String, Object> map, HttpServletRequest request, UserObject userObject)
	{
		
		log.debug("{}  incoming Cluster Doc ID is {}",nyehmsLog ,docId);
		

		Map<Object, Object> targetMap= new HashMap<>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		

		ClusterDocVO clusterDocVO = clusterDocumentService.getClusterDocVO(docId, bindingResult, userObject);
		
		Map<String, Object> nyehmsStatenoDetails = documentTransactionHelper.getStatenoDetailsOfStateno(null, clusterDocVO.getSTATENO(), null, userObject);
		
		log.debug("{}  Cluster VO is being fecthed from DB", nyehmsLog);
		
		map.put("clusterDocVO", clusterDocVO);
		map.put("nyehmsStatenoDetails", nyehmsStatenoDetails);		
		
//		map.put("hasEditFunctionality", true);

	return "document/ClusterDocument";
	
	}
	
	/**
	 * 
	 * @param allRequestParams
	 * @param DOCUMENT_ID
	 * @param DOCUMENT_TYPE_CODE
	 * @param SOFT_ERROR_ONLY
	 * @param request
	 * @return
	 */
/*	@RequestMapping(value="/updateClusterDocument.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Map<Object, Object> updateClusterDocument(
			@RequestParam Map<String, String> allRequestParams,
			@RequestParam int DOCUMENT_ID,
			@RequestParam String DOCUMENT_TYPE_CODE,
			@RequestParam String SOFT_ERROR_ONLY,
			HttpServletRequest request)
	{		
		//, @RequestParam int DOCUMENT_ID, @RequestParam String DOCUMENT_TYPE_CODE
		UserObject userObject=(UserObject)request.getSession().getAttribute("UserObj");
		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		clusterDocumentService.updateClusterDocument(allRequestParams, DOCUMENT_ID, DOCUMENT_TYPE_CODE, SOFT_ERROR_ONLY, bindingResult, userObject);
		return targetMap;
	}
*/	
	@RequestMapping(value="/getClusterReports")	
	public String getClusterReports(HttpServletRequest request, Map<String, Object> map) {
		
		map.put("clusterTypeList", clusterDocumentService.getClusterTypeList());
		
		List<Map<String, String>> clusterIdentificationMethod = clusterDocumentService.getClusterIDentificationMethod();
		map.put("clusterIdentificationMethod", clusterIdentificationMethod);		
		
		AddressFieldVO addressFieldVO = new AddressFieldVO();
		map.put("addressFieldVO", addressFieldVO);
		
		return "reports/ClusterReports";
	}	
	
	@RequestMapping(value = "/getClusterIdsByType.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody List<JSONObject> getClusterIdsByType(@RequestParam String clusterType, HttpServletRequest request) {

		List<JSONObject> clusterIdsObj = clusterDocumentService.getClusterIdsByType(clusterType);
		
		return clusterIdsObj;
	}	
	
	@RequestMapping(value = "/associateStatenoToCluster.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Map<String, String> associateStatenoToCluster(@RequestParam String clusterId, @RequestParam String stateno, @RequestParam String requestComments, HttpServletRequest request, UserObject userObject) {

		if(userObject==null) userObject= new UserObject();
		
		Map<String, String> targetMap= new HashMap<>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "Sample");
		
		documentTransactionHelper.associateStatenoToCluster(clusterId, stateno, requestComments, userObject, bindingResult);
		
		return targetMap;
	}		
	

	@RequestMapping(value = "/getExistingClusterIdsByType.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody List<JSONObject> getExistingClusterIdsByType(@RequestParam String clusterType, HttpServletRequest request) {

		List<JSONObject> clusterIdsObj = clusterDocumentService.getExistingClusterIdsByType(clusterType);
		
		return clusterIdsObj;
	}	
	
	@RequestMapping(value = "/getClusterData.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody JSONObject getClusterData(@RequestParam String clusterId, HttpServletRequest request) {
		
		return clusterDocumentService.getClusterData(clusterId);
	}	

	@RequestMapping(value="/getClusterReportsByTypeAndId.json", method = RequestMethod.POST)
	public @ResponseBody ClusterIdReportVO  getClusterReportsByTypeAndId(@RequestParam String clusterType, @RequestParam String clusterId, @RequestParam String showDocuments, HttpServletRequest request, Map<String, Object> map, UserObject userObject){		
		
		if(userObject==null) userObject= new UserObject();
		
		BindingResult bindingResult = BindingResults.newDummy();
		
		ClusterIdReportVO clusterRptObj = clusterDocumentService.getClusterIdReports(clusterType, clusterId, showDocuments, bindingResult, userObject);		
		
		return clusterRptObj;
		
	}	
	
	
	@RequestMapping(value = "/updateClustereHARSImportFlag.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Map<Object, Object> updateClustereHARSImportFlag(HttpServletRequest request, @RequestParam("clusterIdPks[]")  int[] clusterIdPks, Map<String, Object> map, UserObject userObject) {
		

		Map<Object, Object> targetMap= new HashMap<>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		clusterDocumentService.updateClustereHARSImportFlag(clusterIdPks, userObject, bindingResult);
		
		return targetMap;
		
	}		
	@RequestMapping("/geteHARSImportData")
	public String geteHARSImportData(Map<String, Object> pMap) {
		
		return "uploads/ClustereHARSImportFlag";
	}
	
	@RequestMapping(value="/loadeHARSImportFlagData.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody GridObject geteHARSImportFlagData(HttpServletRequest request, UserObject userObject){
		

		Map<Object, Object> targetMap= new HashMap<>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		GridObject jsonResponse = getGridObject.GetGrid(clusterDocumentService.geteHARSImportFlagData(userObject, bindingResult));
		
		return jsonResponse;

	}	
	
	@RequestMapping(value="/getClusterDisplayInfo.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody JSONObject getClusterDisplayInfo(HttpServletRequest request, @RequestParam String clusterId){
		
		JSONObject clusterCriteriaInfo = clusterDocumentService.getClusterDisplayInfo(clusterId);
		return clusterCriteriaInfo;
	}		
	
	
	@RequestMapping(value="/getMolClusterDemoDocumentIds.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody List<JSONObject> getMolClusterDemoDocumentIds(@RequestParam String stateNo, @RequestParam String identificationCode, @RequestParam String notAssocClusterDetails, HttpServletRequest request) {
		
		List<JSONObject> demoDocumentIdsObj = null;
		
		if (identificationCode != null && CommonUtil.isNotNullAndEmpty(identificationCode)) {
			demoDocumentIdsObj = clusterDocumentService.getMolClusterDemoDocumentIds(stateNo, identificationCode, notAssocClusterDetails);
		}

		return demoDocumentIdsObj;
	}	
	
	@RequestMapping(value="/getLinkedMolClusterGenoTypeDocuments.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	Map<Object, Object> getLinkedMolClusterGenoTypeDocuments(HttpServletRequest request, @RequestParam int documentId, UserObject userObject) {
		
		if(userObject==null) userObject= new UserObject();
		
		Map<Object, Object> targetMap= new HashMap<>();
		
		List<Integer> linkedDocumentIds = clusterDocumentService.getLinkedMolClusterGenoTypeDocuments(documentId);
		
		targetMap.put("linkedDocumentIds", linkedDocumentIds);
		
		return targetMap;
	
	}		
	
	
}
