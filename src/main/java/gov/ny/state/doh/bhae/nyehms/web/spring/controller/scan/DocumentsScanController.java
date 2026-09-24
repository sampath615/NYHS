package gov.ny.state.doh.bhae.nyehms.web.spring.controller.scan;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.MapBindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.CommonService;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.scan.DocumentsScanService;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonExceptionHandler;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonUtil;


@Controller
public class DocumentsScanController {	
	//Crate a dummy BindingResult for the JSON calls as they don't have that object to pass to service layer.
	//This done as all service layer depended on DAO accepts binding result and json calls dont have it.
	Map<Object,Object> dummyMindingMap= new HashMap<Object,Object>();
	BindingResult dummyBindingResult = new MapBindingResult(dummyMindingMap, "Sample");
	private static Logger log = LoggerFactory.getLogger(DocumentsScanController.class);
	
	@Autowired DocumentsScanService documentsScanService;
	@Autowired CommonService commonService;
	@Autowired CommonExceptionHandler commonExceptionHandler;
	
	@RequestMapping(value="/getScannedImageContent.html", method = RequestMethod.POST )
	public String getScannedImageContent(
			HttpServletRequest request, Map<String, Object> map)  
	{
		
		String docId = request.getParameter("docId");
		
		if(CommonUtil.isNotNullAndEmpty(docId)){
			
			Map<String, String> scanDocInfo = documentsScanService.getScanDocInfo(docId);
			
			map.put("scanDocInfo", scanDocInfo);
			
		}
		return "scanning/scannedImage";
	}
	
	@RequestMapping(value="/loadScanDialog", method = RequestMethod.POST )
	public String loadScanDialog(HttpServletRequest request)  
	{
		return "scanning/loadScanDialog";
	}

	
	
	@RequestMapping(value="/saveScanDocumentToStateNo.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Map<Object, Object> saveScanDocumentToStateNo(
			HttpServletRequest request, @RequestParam String stateNo, @RequestParam String documentType, @RequestParam("RemoteFile") CommonsMultipartFile RemoteFile)
	{
		
		UserObject userObject=(UserObject)request.getSession().getAttribute("UserObj");
		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		//searchService.saveScanDocumentToStateNo(stateNo, bindingResult, userObject);
		
		if(RemoteFile == null || RemoteFile.isEmpty()) {
			log.error("Log file is empty");
			//write a json response
			
			commonExceptionHandler.inspectDBTransacionResult(-1, "Upload file is empty", bindingResult);
			
			return targetMap;
		}
		
		documentsScanService.saveScanDocumentToStateNoSFTP(stateNo, documentType, RemoteFile, bindingResult, userObject);
		
		return targetMap;
		
	}
	
	@RequestMapping(value="/getEncryptedScannedDoc.pdf", method = RequestMethod.GET, produces="application/pdf" )
	public @ResponseBody byte[] getEncryptedScannedDoc(@RequestParam int docId, @RequestParam String documentType, Map<String, Object> map, HttpServletRequest request)
	{
		UserObject userObject = (UserObject) request.getSession().getAttribute("UserObj");
		
		return documentsScanService.getEncryptedScannedDoc(docId, documentType, dummyBindingResult, userObject);

	}
	
	

	/*
	@RequestMapping(value="/getScannedDocument.pdf", method = RequestMethod.GET, produces = "application/pdf" )
	public @ResponseBody byte[] getScannedImage(
			HttpServletRequest request, @RequestParam String fileName)  
	{
		UserObject userObject=(UserObject)request.getSession().getAttribute("UserObj");
		
		return documentsScanService.getScannedImage(fileName, userObject);
		
	}*/
	
	/*
	@RequestMapping(value="/disCardScanDocument.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Map<Object, Object> deleteScannedDocument(
			HttpServletRequest request, @RequestParam String fileName)
	{
		
		UserObject userObject=(UserObject)request.getSession().getAttribute("UserObj");
		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		//searchService.deCryptScannedDocument(stateNo, bindingResult, userObject);
		
		try {
			
			documentsScanService.disCardScanDocument(fileName, bindingResult, userObject);
					    
		}  catch (Exception e) {
			log.error("Exception while discarding the Scanned Document");
		}

		return targetMap;
		
	}*/
	

	/*
	
	@RequestMapping(value="/getScannedDocumentFileNames.json", method = RequestMethod.POST, produces = "application/json")
	public String getScannedDocumentFileNames(
			HttpServletRequest request, Map<String, Object> map)
	{
		
		UserObject userObject=(UserObject)request.getSession().getAttribute("UserObj");
		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		//searchService.deCryptScannedDocument(stateNo, bindingResult, userObject);
		List<String> scannedFileNames = documentsScanService.getScannedDocumentFileNames("SCANNED_FILES_LOCATION", bindingResult, userObject);
		
		map.put("scannedFileNames", scannedFileNames);
		
		return "scanning/ShowScannedFileNames";
		
	}
	*/
	
	
	
//	@RequestMapping(value = "/deleteScanDoc")
//	public String deleteScanDoc(HttpServletRequest request, Map<String, Object> map) {
//
//		
//		HttpSession session = request.getSession();
//		UserObject userObject = (UserObject) session.getAttribute("UserObj");
//		
//		if(userObject == null) {
//			return EnvironmentProperties.envProperties.getProperty("loginPage");
//		}else if(request.getParameter("DOCUMENT_ID") != null && request.getParameter("STATENO") != null && request.getParameter("DELETED_BY") != null) {
//			
//			String documentId = request.getParameter("DOCUMENT_ID");
//			String stateno = request.getParameter("STATENO");
//			String deletedBy = request.getParameter("DELETED_BY");
//			
//
//			
//			String transactionMessage = "Successfully deleted the document";
//			try {
//				String docType = documentsScanService.deleteScanDocument(documentId, stateno, deletedBy);
//				
//				transactionMessage = "Successfully deleted the "+docType +"("+documentId+")";
//			} catch (SftpException e) {
//				transactionMessage = e.getMessage();
//				
//			}catch (Exception e) {
//				transactionMessage = e.getMessage();
//				
//				if(CommonUtil.isNullOrEmpty(transactionMessage)) {
//					transactionMessage = "Failed while deleting the document";
//				}
//				
//			}
//			
//			log.error(documentId+"/"+stateno +"  Deleting status : "+transactionMessage);
//			
//			map.put("TRANSACTION_MESSAGE", transactionMessage);
//			
//			
//		}
//		
//		List<JSONObject> reciepientDetails = commonService.getReciepientDetails();
//		
//		map.put("USER_IDS", reciepientDetails);
//		
//		return "scheduler/deleteScanDocument";
//		
//		
//		
//	}
}
