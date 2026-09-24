package gov.ny.state.doh.bhae.nyehms.web.spring.service.scan;

import java.util.Map;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.jcraft.jsch.SftpException;

import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;

public interface DocumentsScanService {


//	public byte[]  getScannedImage(String fileName, UserObject userObject);


	public void saveScanDocumentToStateNoSFTP(String stateNo, String documentType, CommonsMultipartFile scannedFile, 
			BindingResult bindingResult, UserObject userObject);

//	public void disCardScanDocument(String fileName,
//			BindingResult bindingResult, UserObject userObject);

	public byte[] getEncryptedScannedDoc(int docId, String documentType, BindingResult dummyBindingResult,
			UserObject userObject);

//	public List<String> getScannedDocumentFileNames(String location, BindingResult bindingResult,
//			UserObject userObject);


	public int createTheScanDocumentDetailsInDb(String stateNo, String fileName, String documentType, BindingResult bindingResult, UserObject userObject);
	
//	public boolean encryptAndSaveScanDocument(String fileName, String outputFileName, boolean throwError, String inputLocation, String outputLocation,  
//			BindingResult bindingResult, UserObject userObject);


//	public void cleanRemainingScanDocs(Map<String, String> schedulerMetaData, BindingResult bindingResult,
//			UserObject userObject);

//
//	public void decryptDocumentsFromBackup(Map<String, String> schedulerMetaData, BindingResult bindingResult,
//			UserObject userObject);


//	public boolean decryptAndSaveToBackup(String fileName,
//			BindingResult bindingResult, UserObject userObject);


//	public void deleteScannedDocumentFromSourceLocation(String fileName,
//			BindingResult bindingResult, UserObject userObject);


	public Map<String, String> getScanDocInfo(String docId);
	
	public void storeScannedImageFileToSchoit(String outputFileName,  
            CommonsMultipartFile file, String outputLocation, String documentType, BindingResult bindingResult, UserObject userObject);
	

	@Transactional
	public String deleteScanDocument(int documentId, String documentType, String stateno, String discardReasonDescription, String comments, BindingResult bindingResult, UserObject userObject) throws SftpException ;
	
}
