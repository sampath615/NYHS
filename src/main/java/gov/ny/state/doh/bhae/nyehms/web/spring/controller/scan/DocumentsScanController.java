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
import gov.ny.state.doh.bhae.nyehms.web.spring.util.BindingResults;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonExceptionHandler;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonUtil;


@Controller
public class DocumentsScanController {	
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
			HttpServletRequest request, @RequestParam String stateNo, @RequestParam String documentType, @RequestParam("RemoteFile") CommonsMultipartFile RemoteFile, UserObject userObject)
	{
		
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
	public @ResponseBody byte[] getEncryptedScannedDoc(@RequestParam int docId, @RequestParam String documentType, Map<String, Object> map, HttpServletRequest request, UserObject userObject)
	{
		
		return documentsScanService.getEncryptedScannedDoc(docId, documentType, BindingResults.newDummy(), userObject);

	}
	
	

	
	

	
	
	
}
