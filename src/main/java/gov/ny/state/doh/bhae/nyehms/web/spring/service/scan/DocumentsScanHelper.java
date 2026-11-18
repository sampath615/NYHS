package gov.ny.state.doh.bhae.nyehms.web.spring.service.scan;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import com.jcraft.jsch.SftpException;

import gov.ny.state.doh.bhae.nyehms.web.spring.enums.AppConstants;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonExceptionHandler;

@Component
public class DocumentsScanHelper {
	
	private static final Logger log = LoggerFactory.getLogger(DocumentsScanHelper.class);
	//private String nyehmsLog=AppConstants.nyehmLogDebugString.getName();
	
	@Autowired
	private CommonExceptionHandler commonExceptionHandler;
	
	@Autowired
	private DocumentsScanService documentsScanService;
	
	
	@Transactional
	public void saveScanDocumentToStateNoSFTP(String stateNo, String documentType, MultipartFile scannedFile,
			BindingResult bindingResult, UserObject userObject) throws SftpException, Exception {
		
			int docId = documentsScanService.createTheScanDocumentDetailsInDb(stateNo, null, documentType, bindingResult, userObject);
			
			log.error("Created the Scanned DocumentId and Associted to STATENO, Started Encrypting the data");
			
			documentsScanService.storeScannedImageFileToSchoit(docId+".pdf", scannedFile, documentType, bindingResult, userObject);
			
			//documentsScanService.encryptAndSaveScanDocument(fileName, docId+".pdf", true, "SCANNED_FILES_LOCATION", "SCANNED_FILES_ENCRYPT_LOCATION", bindingResult, userObject);
						
			commonExceptionHandler.inspectDBTransacionResult(0, "Successfully associated the scanned document to STATENO.", bindingResult);
			
	}

	
//			
//				sendEmail.sendSSLMessage(
//						schedulerMetaData.get("SCAN_NOTIFICATIONS_TO").split(","),
//						"SCAN document(s) encrypted", encryptedFilesMessageContent.toString() ,
//						schedulerMetaData.get("NOTIFICATIONS_FROM"), schedulerMetaData.get("SCAN_NOTIFICATIONS_CC").split(","),
//				
//				sendEmail.sendSSLMessage(
//						schedulerMetaData.get("SCAN_FAILED_NOTIFICATIONS_TO").split(","),
//						"Failed : SCAN Documents encrypting", failedFilesMessageContent.toString() ,
//						schedulerMetaData.get("NOTIFICATIONS_FROM"), null,


//	public void decryptDocumentsFromBackup(Map<String, String> schedulerMetaData, BindingResult bindingResult,
//				
//				sendEmail.sendSSLMessage(
//						schedulerMetaData.get("SCAN_NOTIFICATIONS_TO").split(","),
//						"SCAN document(s) decrypted, awaiting STATENO association", decryptedFilesMessageContent.toString() ,
//						schedulerMetaData.get("NOTIFICATIONS_FROM"), schedulerMetaData.get("SCAN_NOTIFICATIONS_CC").split(","),
//				
//				sendEmail.sendSSLMessage(
//						schedulerMetaData.get("SCAN_FAILED_NOTIFICATIONS_TO").split(","),
//						"Failed : SCAN Documents decrypting", failedFilesMessageContent.toString() ,
//						schedulerMetaData.get("NOTIFICATIONS_FROM"), null,


//	@Transactional
//	public void disCardScanDocument(String fileName,
	
	
	
}
