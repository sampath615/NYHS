package gov.ny.state.doh.bhae.nyehms.web.spring.service.scan;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.jcraft.jsch.SftpException;

import gov.ny.state.doh.bhae.nyehms.web.spring.enums.AppConstants;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonExceptionHandler;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.SendEmail;

@Component
public class DocumentsScanHelper {
	
	private static Logger log = LoggerFactory.getLogger(DocumentsScanHelper.class);
	//private String nyehmsLog=AppConstants.nyehmLogDebugString.getName();
	
	@Autowired
	private CommonExceptionHandler commonExceptionHandler;
	
	@Autowired
	private DocumentsScanService documentsScanService;
	
	@Autowired
	SendEmail sendEmail;
	
	@Transactional
	public void saveScanDocumentToStateNoSFTP(String stateNo, String documentType, CommonsMultipartFile scannedFile,
			BindingResult bindingResult, UserObject userObject) throws SftpException, Exception {
		
			int docId = documentsScanService.createTheScanDocumentDetailsInDb(stateNo, null, documentType, bindingResult, userObject);
			
			log.error("Created the Scanned DocumentId and Associted to STATENO, Started Encrypting the data");
			
			documentsScanService.storeScannedImageFileToSchoit(docId+".pdf", scannedFile, "SCANNED_FILES_ENCRYPT_LOCATION", documentType, bindingResult, userObject);
			
			//documentsScanService.encryptAndSaveScanDocument(fileName, docId+".pdf", true, "SCANNED_FILES_LOCATION", "SCANNED_FILES_ENCRYPT_LOCATION", bindingResult, userObject);
						
			commonExceptionHandler.inspectDBTransacionResult(0, "Successfully associated the scanned document to STATENO.", bindingResult);
			
	}

	
//	public void cleanRemainingScanDocs(Map<String, String> schedulerMetaData, BindingResult bindingResult, UserObject userObject) throws MessagingException {
//
//		
//		int totalAvailDocs = 0;
//		
//		boolean documentsEncrypted = false;
//		boolean hasFailedDocuments = false;
//		
//		List<String> availableFileNames = documentsScanService.getScannedDocumentFileNames("SCANNED_FILES_LOCATION", bindingResult, userObject);
//		
//		StringBuilder encryptedFilesMessageContent = new StringBuilder("The listed scanned documents have been encrypted for security purposes");
//		
//		StringBuilder failedFilesMessageContent = new StringBuilder("Unable to encrypt the following scan documents");
//		
//		totalAvailDocs = availableFileNames.size();
//		
//		log.error("total available scanned documents : "+totalAvailDocs);
//		
//		if(availableFileNames == null || availableFileNames.size() == 0){
//			
//			//statusObject.put(CommonConstants.TRANSACTION_STATUS, CommonConstants.TRANSACTION_STATUS_SUCCESS);
//
//			//statusObject.put(CommonConstants.TRANSACTION_MESSAGE, "There is no Documents available to Clean");
//			
//			//commonExceptionHandler.inspectDBTransacionResult(-1, "There is no Documents to Encrypt", bindingResult);
//			
//			log.error("There is no Scanned documents availabe to Encrypt");
//			
//			return;
//			
//		}else{
//			
//			
//			//statusObject.put("Total Documents", totalAvailDocs);
//			
//			for (String fileName : availableFileNames) {
//				
//				try{
//					
//					boolean isError = documentsScanService.encryptAndSaveScanDocument(fileName, fileName, false, "SCANNED_FILES_LOCATION", "ENCRYPT_BACKUP_LOCATION", bindingResult, userObject);
//							
//					if(!isError){
//						documentsEncrypted = true;
//						encryptedFilesMessageContent.append("<br/><br/>").append(fileName);
//					}else{
//						hasFailedDocuments = true;
//						failedFilesMessageContent.append("<br/><br/>").append(fileName);
//					}
//				
//				}catch(DataAccessException dataAccessException) {
//					
//					hasFailedDocuments = true;
//					
//					String transactionMessage = dataAccessException.getMessage();
//					
//					log.error("Exception while encrypting the remaining scanned document("+fileName+").  Error :   " + transactionMessage);
//					
//					failedFilesMessageContent.append("<br/><br/>").append(fileName);
//				}
//				
//			}
//			
//			if(documentsEncrypted && "Y".equals(schedulerMetaData.get("REQUIRED_TO_SEND_SCAN_NOTIFICATION"))){
//				
//				encryptedFilesMessageContent.append("<br/><br/>NYEHMS Development Team");
//			
//				sendEmail.sendSSLMessage(
//						schedulerMetaData.get("SCAN_NOTIFICATIONS_TO").split(","),
//						"SCAN document(s) encrypted", encryptedFilesMessageContent.toString() ,
//						schedulerMetaData.get("NOTIFICATIONS_FROM"), schedulerMetaData.get("SCAN_NOTIFICATIONS_CC").split(","),
//						CommonConstants.RESPONSE_CONTENT_TYPE_HTML);
//			}
//			
//			if(hasFailedDocuments){
//				
//				failedFilesMessageContent.append("<br/><br/>NYEHMS Development Team");
//				
//				sendEmail.sendSSLMessage(
//						schedulerMetaData.get("SCAN_FAILED_NOTIFICATIONS_TO").split(","),
//						"Failed : SCAN Documents encrypting", failedFilesMessageContent.toString() ,
//						schedulerMetaData.get("NOTIFICATIONS_FROM"), null,
//						CommonConstants.RESPONSE_CONTENT_TYPE_HTML);
//			}
//			
//		}
//	}


//	public void decryptDocumentsFromBackup(Map<String, String> schedulerMetaData, BindingResult bindingResult,
//			UserObject userObject) throws MessagingException {
//
//		int totalAvailDocs = 0;
//		
//		boolean documentsDecrypted = false;
//		boolean hasFailedDocuments = false;
//		
//		StringBuilder decryptedFilesMessageContent = new StringBuilder("The listed scanned documents have been decrypted and are awaiting STATENO association") ;
//		
//		StringBuilder failedFilesMessageContent = new StringBuilder("Unable to decrypt the following scan documents");
//		
//		
//		List<String> availableFileNames = documentsScanService.getScannedDocumentFileNames("ENCRYPT_BACKUP_LOCATION", bindingResult, userObject);
//		
//		totalAvailDocs = availableFileNames.size();
//		
//		log.error("total available scanned documents to decrypt : "+totalAvailDocs);
//		
//		if(availableFileNames == null || availableFileNames.size() == 0){
//			
////			statusObject.put(CommonConstants.TRANSACTION_STATUS, CommonConstants.TRANSACTION_STATUS_SUCCESS);
////
////			statusObject.put(CommonConstants.TRANSACTION_MESSAGE, "There is no Documents available to Clean");
////			
////			commonExceptionHandler.inspectDBTransacionResult(-1, "There is no Documents to Decrypt", bindingResult);
//			
//			log.error("There is no Scanned documents availabe to decrypt from Backup folder");
//			
//			return;
//			
//		}else{
//			
//			
//			//statusObject.put("Total Documents", totalAvailDocs);
//			
//			for (String fileName : availableFileNames) {
//				
//				
//				try{
//				
//					boolean isError = documentsScanService.decryptAndSaveToBackup(fileName, bindingResult, userObject);
//					
//					if(!isError){
//						documentsDecrypted = true;
//						decryptedFilesMessageContent.append("<br/><br/>").append(fileName);
//					}else{
//						hasFailedDocuments = true;
//						failedFilesMessageContent.append("<br/><br/>").append(fileName);
//					}
//					
//				
//				}catch(DataAccessException dataAccessException) {
//					
//					String transactionMessage = dataAccessException.getMessage();
//					
//					log.error("Exception while decrypting the scanned document("+fileName+") from Backup Folder.  Error :   " + transactionMessage);
//					
//					failedFilesMessageContent.append("<br/><br/>").append(fileName);
//				}
//				
//				
//				
//			}
//			
//			if(documentsDecrypted && "Y".equals(schedulerMetaData.get("REQUIRED_TO_SEND_SCAN_NOTIFICATION"))){
//				
//				decryptedFilesMessageContent.append("<br/><br/>NYEHMS Development Team");
//				
//				sendEmail.sendSSLMessage(
//						schedulerMetaData.get("SCAN_NOTIFICATIONS_TO").split(","),
//						"SCAN document(s) decrypted, awaiting STATENO association", decryptedFilesMessageContent.toString() ,
//						schedulerMetaData.get("NOTIFICATIONS_FROM"), schedulerMetaData.get("SCAN_NOTIFICATIONS_CC").split(","),
//						CommonConstants.RESPONSE_CONTENT_TYPE_HTML);
//			}
//			
//			if(hasFailedDocuments){
//				
//				failedFilesMessageContent.append("<br/><br/>NYEHMS Development Team");
//				
//				sendEmail.sendSSLMessage(
//						schedulerMetaData.get("SCAN_FAILED_NOTIFICATIONS_TO").split(","),
//						"Failed : SCAN Documents decrypting", failedFilesMessageContent.toString() ,
//						schedulerMetaData.get("NOTIFICATIONS_FROM"), null,
//						CommonConstants.RESPONSE_CONTENT_TYPE_HTML);
//			}
//		
//		}
//		
//	}


//	@Transactional
//	public void disCardScanDocument(String fileName,
//			BindingResult bindingResult, UserObject userObject) {
//
//		
//		
//		StringBuilder outPutFileName = new StringBuilder("");
//		
//		if(fileName.indexOf(".") != -1){
//			outPutFileName.append(fileName.substring(0, fileName.indexOf(".")));
//		}else{
//			outPutFileName.append(fileName);
//		}
//		
//		outPutFileName.append("_").append(userObject.getUserId()).append("_");
//		
//		Calendar date = Calendar.getInstance();
//		
//		outPutFileName.append(date.get(Calendar.YEAR)).append(date.get(Calendar.MONTH)+1).append(date.get(Calendar.DAY_OF_MONTH)).append("_").append(date.get(Calendar.HOUR)).append(date.get(Calendar.MINUTE)).append(date.get(Calendar.SECOND)).append(".pdf");
//		
//		documentsScanService.encryptAndSaveScanDocument(fileName, outPutFileName.toString(), true, "SCANNED_FILES_LOCATION", "DISCARD_SCAN_FILE_LOCATION", bindingResult, userObject);
//		
//		commonExceptionHandler.inspectDBTransacionResult(0, "Successfully discarded the scanned document.", bindingResult);
//		
//		//documentsScanService.deleteScannedDocumentFromSourceLocation(fileName, bindingResult, userObject);
//		
//		
//	}
	
	
	
}
