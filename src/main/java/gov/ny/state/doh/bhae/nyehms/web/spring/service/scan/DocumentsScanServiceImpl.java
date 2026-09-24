package gov.ny.state.doh.bhae.nyehms.web.spring.service.scan;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.SftpException;

import gov.ny.state.doh.bhae.nyehms.web.spring.dao.document.ScanDocumentDAO;
import gov.ny.state.doh.bhae.nyehms.web.spring.enums.AppConstants;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.document.CommonDocumentService;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonExceptionHandler;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonUtil;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.EnvironmentProperties;

@Service
public class DocumentsScanServiceImpl implements DocumentsScanService{
	
	private static Logger log = LoggerFactory.getLogger(DocumentsScanServiceImpl.class);
	//private String nyehmsLog=AppConstants.nyehmLogDebugString.getName();
	
	@Autowired
	private CommonExceptionHandler commonExceptionHandler;
	@Autowired
	CommonDocumentService commonDocumentService;
	
	@Autowired
	DocumentsScanHelper documentsScanHelper;
	
	@Autowired
	RemoteServerUtil remoteServer;

	@Autowired
	ScanDocumentDAO scanDocumentDAO;
	
//	@Override
//	public List<String> getScannedDocumentFileNames(String location, BindingResult bindingResult,
//			UserObject userObject){
//		
//		String transactionMessage = null;
//		ChannelSftp obj_SFTPChannel = null;
//		boolean isError = false;
//
//		List<String> scannedFileNames = new ArrayList<String>();
//		obj_SFTPChannel = remoteServer.getRemoteServerSession();
//		
//		if(obj_SFTPChannel == null){
//			throw new DataRetrievalFailureException("Remote server(Scchoit31) connection not established.");
//		}
//		
//		try {
//			
//			
//			Vector<LsEntry> folderList = obj_SFTPChannel.ls(EnvironmentProperties.envProperties.getProperty(location));
//			if(folderList.size() == 0){
//				log.error("There is no scanned documents available for review");
//			}else{
//			
//				for (LsEntry element : folderList) {
//					
//					String fileName = element.getFilename(); 
//					
//					if(!".".equals(fileName) &&  !"..".equals(fileName) && fileName.contains(".pdf")){
//						
//						scannedFileNames.add(element.getFilename());
//					}
//					
//					
//				}
//				
//				Collections.sort(scannedFileNames);
//				
//			}
//             
//					    
//		} catch (SftpException e) {
//			e.printStackTrace();
//			isError = true;
//			e.printStackTrace();
//			transactionMessage = e.getMessage();
//			log.error("SftpException while getting the file names from the Server:  " + transactionMessage);
//		} catch (Exception e) {
//			isError = true;
//			transactionMessage = e.getMessage();
//			log.error("Exception while getting the file names from the Server:  " + transactionMessage);
//		}
//		
//		if(isError){
//			throw new DataRetrievalFailureException("Unable to get the scanned documents details from the Server. Error  : "+transactionMessage);
//		}
//		
//		
//		
//		return scannedFileNames;
//		
//	}
	
	
//	@Override
//	public byte[] getScannedImage(String fileName, UserObject userObject){
//		
//		String transactionMessage = null;
//		
//		ChannelSftp obj_SFTPChannel = null;
//		
//		InputStream obj_InputStream = null;
//		
//		boolean isError = false;
//
//		byte[] targetArray = null;
//		
//		obj_SFTPChannel = remoteServer.getRemoteServerSession();
//		
//		if(obj_SFTPChannel == null){
//			throw new DataRetrievalFailureException("Remote server(Scchoit31) connection not established.");
//		}
//		
//		try {
//			
//				obj_InputStream = new ByteArrayInputStream("".getBytes());
//				
//				obj_SFTPChannel.cd(EnvironmentProperties.envProperties.getProperty("SCANNED_FILES_LOCATION"));
//				
//				obj_InputStream = obj_SFTPChannel.get(fileName);
//	
//			    targetArray = IOUtils.toByteArray(obj_InputStream);
//
//
//			
//		}  catch (SftpException e) {
//			isError = true;
//			transactionMessage = e.getMessage();
//			log.error("SftpException while retreiving the Scanned Document :  " + transactionMessage);
//		} catch (Exception e) {
//			
//			isError = true;
//			transactionMessage = e.getMessage();
//			log.error("Exception while retreiving the Scanned Document  :  " + transactionMessage);
//		} finally {
//
//			try {
//				if (obj_InputStream != null) {
//					obj_InputStream.close();
//				}
//			} catch (IOException e) {
//				log.error("IOException while closing the channels  " + e.getMessage());
//			}
//		}
//		
//		if(isError){
//			throw new DataRetrievalFailureException("Exception while retreiving the Scanned Document. Error  : "+transactionMessage);
//		}
//		
//		return targetArray;
//
//	}
	
	
	
//	@Override
//	public boolean decryptAndSaveToBackup(String fileName,
//			BindingResult bindingResult, UserObject userObject){
//		
//		
//		String transactionMessage = null;
//		ChannelSftp obj_SFTPChannel = null;
//		InputStream obj_InputStream = null;
//		OutputStream outStream = null;
//		CipherOutputStream cos = null;
//		boolean isError = false;
//		
//		obj_SFTPChannel = remoteServer.getRemoteServerSession();
//		
//		if(obj_SFTPChannel == null){
//			throw new DataRetrievalFailureException("Remote server(Scchoit31) connection not established.");
//		}
//		
//		try {
//			
//			obj_SFTPChannel.cd(EnvironmentProperties.envProperties.getProperty("ENCRYPT_BACKUP_LOCATION"));
//			
//			obj_InputStream = obj_SFTPChannel.get(fileName);
//			
//			byte k[] = "R1D0fPa!".getBytes();
//			
//			SecretKeySpec key = new SecretKeySpec(k, "DES");  
//			
//			Cipher enc = Cipher.getInstance("DES");
//			
//			enc.init(Cipher.DECRYPT_MODE, key);
//			
//			outStream = obj_SFTPChannel.put(EnvironmentProperties.envProperties.getProperty("DECRYPT_BACKUP_LOCATION")+fileName);
//				
//			
//			cos = new CipherOutputStream(outStream, enc);
//			
//			cos.write(IOUtils.toByteArray(obj_InputStream));
//			
//			
//			obj_SFTPChannel.rm(EnvironmentProperties.envProperties.getProperty("ENCRYPT_BACKUP_LOCATION")+fileName);
//             
//					    
//		}  catch (SftpException e) {
//			isError = true;
//			transactionMessage = e.getMessage();
//			log.error("SftpException while Decrypting the document and Storing it to Backup Folder:  " + transactionMessage);
//		} catch (Exception e) {
//			isError = true;
//			transactionMessage = e.getMessage();
//			log.error("Exception while Decrypting the document and Storing it to Backup Folder:  " + transactionMessage);
//		} finally {
//
//			try {
//				if (obj_InputStream != null) {
//					obj_InputStream.close();
//				}
//				if (outStream != null) {
//					outStream.close();
//				}
//				if (cos != null) {
//					cos.close();
//				}
//			} catch (IOException e) {
//				log.error("IOException while closing the channels  " + e.getMessage());
//			} catch (Exception e) {
//				log.error("exception while closing the channels  " + e.getMessage());
//			}
//			
//			
//			if(isError){
//				throw new DataRetrievalFailureException("Exception while Decrypting the document("+fileName+") and Storing it to Backup Folder");
//			}
//		}
//		
//		return isError;
//	}
	
//	@Override
//	public void disCardScanDocument(String fileName,
//			BindingResult bindingResult, UserObject userObject){
//		
//		
//		
//		
//		boolean isError = false;
//		String transactionMessage = null;
//		
//		try {
//			
//			documentsScanHelper.disCardScanDocument(fileName, bindingResult, userObject);
//		}  catch (DataAccessException dataAccessException) {
//			isError = true;
//			transactionMessage = dataAccessException.getMessage();
//			log.error("Exception while deleting the scanned document("+fileName+").  Error :   " + transactionMessage);
//		} catch (Exception e) {
//			isError = true;
//			transactionMessage = e.getMessage();
//			log.error("Exception while associating the scanned document("+fileName+").  Error :   " + transactionMessage);
//		}
//		
//		if(isError){
//			commonExceptionHandler.inspectDBTransacionResult(-1, "Failed while deleting the scanned document. Please contact helpline", bindingResult);
//			
//			//throw new DataRetrievalFailureException("Exception while associating the scanned document("+fileName+") to STATENO("+stateNo+").  Error :   " + transactionMessage);
//		}
//		
//		
//	}
	
//	@Override
//	public void deleteScannedDocumentFromSourceLocation(String fileName,
//			BindingResult bindingResult, UserObject userObject){
//		
//		String transactionMessage = null;
//		
//		ChannelSftp obj_SFTPChannel = null;
//		
//		obj_SFTPChannel = remoteServer.getRemoteServerSession();
//		
//		if(obj_SFTPChannel == null){
//			
//			commonExceptionHandler.inspectDBTransacionResult(-1, "Failed while discarding the scanned document.", bindingResult);
//			
//			throw new DataRetrievalFailureException("Remote server(Scchoit31) connection not established.");
//		}
//		
//		try {
//			
//			obj_SFTPChannel.rm(EnvironmentProperties.envProperties.getProperty("SCANNED_FILES_LOCATION")+fileName);
//			
//			commonExceptionHandler.inspectDBTransacionResult(0, "Successfully discarded the scanned document.", bindingResult);
//					    
//		}  catch (SftpException e) {
//			transactionMessage = e.getMessage();
//			log.error("SftpException while deleting file("+fileName+") :  " + transactionMessage);
//			
//			commonExceptionHandler.inspectDBTransacionResult(-1, "There is no such file exist.", bindingResult);
//			
//			throw new DataRetrievalFailureException("Exception while discarding the scanned document. Error  : "+transactionMessage+" \n\n File Name : "+fileName);
//			
//		} catch (Exception e) {
//			transactionMessage = e.getMessage();
//			log.error("Exception while deleting file("+fileName+") :  " + transactionMessage);
//			
//			commonExceptionHandler.inspectDBTransacionResult(-1, "Failed while discarding the scanned document.", bindingResult);
//			
//			throw new DataRetrievalFailureException("Exception while discarding the scanned document. Error  : "+transactionMessage+" \n\n File Name : "+fileName);
//			
//		} 
//		
//	}


//	@Override
//	public boolean encryptAndSaveScanDocument(String inputFileName, String outputFileName, boolean throwError, String inputLocation, String outputLocation,  
//			BindingResult bindingResult, UserObject userObject) {
//		ChannelSftp obj_SFTPChannel = null;
//		InputStream obj_InputStream = null;
//		OutputStream outStream = null;
//		CipherOutputStream cos = null;
//		
//		boolean isError = false;
//		String transactionMessage = null; 
//		
//		obj_SFTPChannel = remoteServer.getRemoteServerSession();
//		
//		if(obj_SFTPChannel == null){
//			throw new DataRetrievalFailureException("Remote server(Scchoit31) connection not established.");
//		}
//		
//		try {
//			
//			// Writing the Standard data to stdData object
//			obj_InputStream = new ByteArrayInputStream("".getBytes());
//			
//			obj_SFTPChannel.cd(EnvironmentProperties.envProperties.getProperty(inputLocation));
//			
//			obj_InputStream = obj_SFTPChannel.get(inputFileName);
//			
//			byte k[] = "R1D0fPa!".getBytes();
//			
//			SecretKeySpec key = new SecretKeySpec(k, "DES");  
//			
//			Cipher enc = Cipher.getInstance("DES");
//			
//			enc.init(Cipher.ENCRYPT_MODE, key);
//			
//			outStream = obj_SFTPChannel.put(EnvironmentProperties.envProperties.getProperty(outputLocation)+outputFileName);
//				
//			cos = new CipherOutputStream(outStream, enc);
//			
//			cos.write(IOUtils.toByteArray(obj_InputStream));
//			
//			
//			obj_SFTPChannel.rm(EnvironmentProperties.envProperties.getProperty(inputLocation)+inputFileName);
//			
//					    
//		} catch(SftpException sftpException){
//			isError = true;
//			transactionMessage = sftpException.getMessage();
//			log.error("sftpException while encrypting the document :   " + transactionMessage);
//		}catch (NoSuchAlgorithmException e) {
//			isError = true;
//			transactionMessage = e.getMessage();
//			log.error("NoSuchAlgorithmException while encrypting the document  " + transactionMessage);
//		} catch (NoSuchPaddingException e) {
//			isError = true;
//			transactionMessage = e.getMessage();
//			log.error("NoSuchPaddingException while encrypting the document   " + transactionMessage);
//		}catch (IOException ioException) {
//			isError = true;
//			transactionMessage = ioException.getMessage();
//			log.error("IOException while encrypting the document   " + transactionMessage);
//		} catch (InvalidKeyException invalidKeyException) {
//			isError = true;
//			transactionMessage = invalidKeyException.getMessage();
//			log.error("InvalidKeyException while encrypting the document   " + transactionMessage);
//		}finally {
//			
//			
//			try {
//				if (obj_InputStream != null) {
//					obj_InputStream.close();
//				}
//				
//				if (outStream != null) {
//					outStream.close();
//				}
//				
//				if (cos != null) {
//					cos.close();
//				}
//				
//				
//			} catch (IOException e) {
//				log.error("IOException while closing the channels  " + e.getMessage());
//			}
//			
//			if(isError && throwError){
//				throw new DataRetrievalFailureException("Exception while encrypting the document("+inputFileName+").  Error :   " + transactionMessage);
//			}
//			
//		}
//		
//		return isError;
//		
//		
//	}
	
//	@Override
//	public void cleanRemainingScanDocs(Map<String, String> schedulerMetaData, BindingResult bindingResult,
//			UserObject userObject){
//		
//		boolean isError = false;
//		String transactionMessage = null;
//		
//		//JSONObject statusObject = new JSONObject();
//		
//		//statusObject.put(CommonConstants.STATUS_ACTION_NAME, "Clean and encrypt Scanned Documents");
//		
//		
//		try {
//			
//			documentsScanHelper.cleanRemainingScanDocs(schedulerMetaData, bindingResult, userObject);
//			
//		}  catch (DataAccessException dataAccessException) {
//			isError = true;
//			transactionMessage = dataAccessException.getMessage();
//			log.error("Exception while cleaning the Scanned Documents : "+transactionMessage);
//		} catch (Exception e) {
//			isError = true;
//			transactionMessage = e.getMessage();
//			log.error("Exception while cleaning the Scanned Documents : "+transactionMessage);
//		}
//		
//		if(isError){
//			
//			//statusObject.put(CommonConstants.TRANSACTION_STATUS, CommonConstants.TRANSACTION_STATUS_FAILURE);
//			
//			//statusObject.put(CommonConstants.TRANSACTION_MESSAGE, "Unable to initate the cleaning Job for Scanned Documents.");
//			
//			//commonExceptionHandler.inspectDBTransacionResult(-1, "Encrypting job failed. Please contact helpline", bindingResult);
//			
//			throw new DataRetrievalFailureException("Encryption job failed :"+transactionMessage);
//			
//		}
//	
//	}
	
//	@Override
//	public void decryptDocumentsFromBackup(Map<String, String> schedulerMetaData, BindingResult bindingResult,
//			UserObject userObject){
//		
//		boolean isError = false;
//		String transactionMessage = null;
//		
//		//statusObject.put(CommonConstants.STATUS_ACTION_NAME, "Decrypting the Encrypted Scanned Documents");
//		
//		
//		try {
//			
//			documentsScanHelper.decryptDocumentsFromBackup(schedulerMetaData, bindingResult, userObject);
//			
//		}  catch (DataAccessException dataAccessException) {
//			isError = true;
//			transactionMessage = dataAccessException.getMessage();
//			log.error("Exception while decrypting the documents from Backup folder : "+transactionMessage);
//		} catch (Exception e) {
//			isError = true;
//			transactionMessage = e.getMessage();
//			log.error("Exception while decrypting the documents from Backup folder : "+transactionMessage);
//		}
//		
//		if(isError){
//			
////			statusObject.put(CommonConstants.TRANSACTION_STATUS, CommonConstants.TRANSACTION_STATUS_FAILURE);
////			
////			statusObject.put(CommonConstants.TRANSACTION_MESSAGE, "Unable to initate the Decrypting Job for Scanned Documents.");
////			
////			commonExceptionHandler.inspectDBTransacionResult(-1, "Decrypting job failed. Please contact helpline", bindingResult);
//			
//			throw new DataRetrievalFailureException("Decryption job failed :"+transactionMessage);
//			
//		}
//
//	}
//	
	@Override
	public Map<String, String> getScanDocInfo(String docId){
		return scanDocumentDAO.getScanDocInfo(docId);
	}
	
	

	@Override
	public byte[] getEncryptedScannedDoc(int docId, String documentType, BindingResult dummyBindingResult,
			UserObject userObject){
		
		
		String transactionMessage = null;
		ChannelSftp obj_SFTPChannel = null;
		InputStream obj_InputStream = null;
		ByteArrayOutputStream outputStream = null;
		CipherOutputStream cos = null;
		byte[] targetArray = null;
		boolean isError = false;
		
		obj_SFTPChannel = remoteServer.getRemoteServerSession();
		
		if(obj_SFTPChannel == null){
			throw new DataRetrievalFailureException("Remote server(Scchoit31) connection not established.");
		}
		
		try {
						
			obj_SFTPChannel.cd(EnvironmentProperties.envProperties.getProperty("SCANNED_FILES_ENCRYPT_LOCATION")+documentType+"/");
			
			String referenceFileName = docId+".pdf";
			
			obj_InputStream = obj_SFTPChannel.get(referenceFileName);
						
			byte k[] = "R1D0fPa!".getBytes();
			
			SecretKeySpec key = new SecretKeySpec(k, "DES");  
			
			Cipher enc = Cipher.getInstance("DES");
			
			enc.init(Cipher.DECRYPT_MODE, key);
						
			outputStream = new ByteArrayOutputStream();
			
			cos = new CipherOutputStream(outputStream, enc);
						
			cos.write(IOUtils.toByteArray(obj_InputStream));
			
			targetArray = outputStream.toByteArray();
             
					    
		}  catch (SftpException e) {
			isError = true;
			transactionMessage = e.getMessage();
			log.error("SftpException while loading the scanned document from Scchoit31:  {}", transactionMessage);
			
			
			
			StringWriter sw = new StringWriter();
			
			PrintWriter pw = new PrintWriter(sw);
			
			e.printStackTrace(pw);
			
			String sStackTrace = sw.toString(); // stack trace as a string
			
			log.error(sStackTrace);
			
			
			
		} catch (Exception e) {
			isError = true;
			transactionMessage = e.getMessage();
			log.error("Exception while loading the scanned document from Scchoit31:  {}", transactionMessage);
			
			StringWriter sw = new StringWriter();
			
			PrintWriter pw = new PrintWriter(sw);
			
			e.printStackTrace(pw);
			
			String sStackTrace = sw.toString(); // stack trace as a string
			
			log.error(sStackTrace);
		} finally {

			try {
				if (obj_InputStream != null) {
					obj_InputStream.close();
				}
				if (outputStream != null) {
					outputStream.close();
				}
				if (cos != null) {
					cos.close();
				}
				if(obj_SFTPChannel != null) {
					obj_SFTPChannel.disconnect();
				}
			} catch (IOException e) {
				log.error("IOException while closing the channels  ",e.getMessage());
			} catch (Exception e) {
				log.error("exception while closing the channels  ", e.getMessage());
			}
			
			
			if(isError){
				throw new DataRetrievalFailureException("Exception while loading the Scanned Document. \nError Message : "+transactionMessage+  "       \nDocument Id : "+docId);
			}
		}
		
		
		return targetArray;
		
		
	}
	
	
	@Override
	public int createTheScanDocumentDetailsInDb(String stateNo, String fileName, String documentType, BindingResult bindingResult, UserObject userObject){
		Map<Object, Object> docIdMap = new HashMap<Object, Object>();

		docIdMap.put("p_document_type", "SCAN");
		
		docIdMap.put("p_test_type", documentType);
		
		docIdMap.put("p_new_doc_id", "");	
		
		commonDocumentService.getNewDocId(docIdMap);		

		int newDocId=(Integer)docIdMap.get("p_new_doc_id");
		
		docIdMap.put("documentId", newDocId);
		
		docIdMap.put("fileName", documentType);
		
		docIdMap.put("stateNo", stateNo);
		
		docIdMap.put("workerId", userObject.getWorkerId());
		
		scanDocumentDAO.addStdScanEntry(docIdMap);
		
		scanDocumentDAO.createEntryInPersonDocAssociation(docIdMap);
		
		scanDocumentDAO.createEntryInDocumentPerson(docIdMap);
		
		return newDocId;
	}
	
	@Override
	public void saveScanDocumentToStateNoSFTP(String stateNo, String documentType, CommonsMultipartFile scannedFile,
			BindingResult bindingResult, UserObject userObject){
		
		
		boolean isError = false;
		String transactionMessage = null;
		
		try {
			
			documentsScanHelper.saveScanDocumentToStateNoSFTP(stateNo, documentType, scannedFile, bindingResult, userObject);
		}  catch (DataAccessException dataAccessException) {
			isError = true;
			transactionMessage = dataAccessException.getMessage();
			log.error("Exception while associating the scanned document to STATENO ({}).  Error :  {}",stateNo, transactionMessage);
		} catch (Exception e) {
			isError = true;
			transactionMessage = e.getMessage();
			log.error("Exception while associating the scanned document to STATENO ({}).  Error :  {}",stateNo, transactionMessage);
		}
		
		if(isError){
			commonExceptionHandler.inspectDBTransacionResult(-1, "Failed while associating the scanned document to STATENO. Please contact helpline", bindingResult);
			
			//throw new DataRetrievalFailureException("Exception while associating the scanned document("+fileName+") to STATENO("+stateNo+").  Error :   " + transactionMessage);
		}
	
	}
	
	
	
	@Override
    public void storeScannedImageFileToSchoit(String outputFileName,  
                  CommonsMultipartFile file, String outputLocation, String documentType, BindingResult bindingResult, UserObject userObject){
           
           ChannelSftp obj_SFTPChannel = null;
           InputStream obj_InputStream = null;
           OutputStream outStream = null;
           CipherOutputStream cos = null;
           
           boolean isError = false;
           String transactionMessage = null; 
           
           obj_SFTPChannel = remoteServer.getRemoteServerSession();
           
           if(obj_SFTPChannel == null){
                  throw new DataRetrievalFailureException("Remote server(Scchoit31) connection not established.");
           }
           
           try {
                  
                  // Writing the Standard data to stdData object
                  obj_InputStream = new ByteArrayInputStream(file.getBytes());
                  
                  //obj_SFTPChannel.cd(EnvironmentProperties.envProperties.getProperty(inputLocation));
                  
                  //obj_InputStream = obj_SFTPChannel.get(inputFileName);
                  
                  byte k[] = "R1D0fPa!".getBytes();
                  
                  SecretKeySpec key = new SecretKeySpec(k, "DES");  
                  
                  Cipher enc = Cipher.getInstance("DES");
                  
                  enc.init(Cipher.ENCRYPT_MODE, key);
                  
                  outStream = obj_SFTPChannel.put(EnvironmentProperties.envProperties.getProperty(outputLocation)+documentType+"/"+outputFileName);
                        
                  cos = new CipherOutputStream(outStream, enc);
                  
                  cos.write(IOUtils.toByteArray(obj_InputStream));
                  
                  
           //obj_SFTPChannel.rm(EnvironmentProperties.envProperties.getProperty(inputLocation)+inputFileName);
                  
                                   
           } catch(SftpException sftpException){
                  isError = true;
                  transactionMessage = sftpException.getMessage();
                  log.error("sftpException while encrypting the document :   {}", transactionMessage);
                  
                  StringWriter sw = new StringWriter();
      			
	      			PrintWriter pw = new PrintWriter(sw);
	      			
	      			sftpException.printStackTrace(pw);
	      			
	      			String sStackTrace = sw.toString(); // stack trace as a string
	      			
	      			log.error(sStackTrace);
           }catch (NoSuchAlgorithmException e) {
                  isError = true;
                  transactionMessage = e.getMessage();
                  log.error("NoSuchAlgorithmException while encrypting the document  {}", transactionMessage);
                  
                  StringWriter sw = new StringWriter();
      			
      			PrintWriter pw = new PrintWriter(sw);
      			
      			e.printStackTrace(pw);
      			
      			String sStackTrace = sw.toString(); // stack trace as a string
      			
      			log.error(sStackTrace);
           } catch (NoSuchPaddingException e) {
                  isError = true;
                  transactionMessage = e.getMessage();
                  log.error("NoSuchPaddingException while encrypting the document   {}", transactionMessage);
                  
                  StringWriter sw = new StringWriter();
      			
      			PrintWriter pw = new PrintWriter(sw);
      			
      			e.printStackTrace(pw);
      			
      			String sStackTrace = sw.toString(); // stack trace as a string
      			
      			log.error(sStackTrace);
           }catch (IOException ioException) {
                  isError = true;
                  transactionMessage = ioException.getMessage();
                  log.error("IOException while encrypting the document   {}", transactionMessage);
                  
                  StringWriter sw = new StringWriter();
      			
      			PrintWriter pw = new PrintWriter(sw);
      			
      			ioException.printStackTrace(pw);
      			
      			String sStackTrace = sw.toString(); // stack trace as a string
      			
      			log.error(sStackTrace);
           } catch (InvalidKeyException invalidKeyException) {
                  isError = true;
                  transactionMessage = invalidKeyException.getMessage();
                  log.error("InvalidKeyException while encrypting the document   {}", transactionMessage);
                  
                  
                  StringWriter sw = new StringWriter();
      			
      			PrintWriter pw = new PrintWriter(sw);
      			
      			invalidKeyException.printStackTrace(pw);
      			
      			String sStackTrace = sw.toString(); // stack trace as a string
      			
      			log.error(sStackTrace);
           }finally {
                  
                  
                  try {
                        if (obj_InputStream != null) {
                               obj_InputStream.close();
                        }
                        
                        if (outStream != null) {
                               outStream.close();
                        }
                        
                        if (cos != null) {
                               cos.close();
                        }
                        if(obj_SFTPChannel != null) {
        					obj_SFTPChannel.disconnect();
        				}
                        
                  } catch (IOException e) {
                        log.error("IOException while closing the channels  {}", e.getMessage());
                  }
                  
               if(isError ){
                      throw new DataRetrievalFailureException("Exception while encrypting the document.  Error :   " + transactionMessage);
               }
                  
           }
           
           //return isError;
           
           
    }
	
	
	@Override
	@Transactional
	public String deleteScanDocument(int documentId, String scanDocType, String stateno, String discardReasonDescription, String comments, BindingResult bindingResult, UserObject userObject) throws SftpException {
		
		ChannelSftp obj_SFTPChannel = null;

		try {
		
//			scanDocType = scanDocumentDAO.getDocDetails(documentId);
//		
//			if(CommonUtil.isNullOrEmpty(scanDocType) || scanDocType.indexOf("SCAN") == -1){
//				 throw new DataRetrievalFailureException("The input document is not a SCAN document or its already deleted");
//			}
		
		Map<String, Object> inputParameterMap = new HashMap<String, Object>();
		
		inputParameterMap.put("DOCUMENT_ID", documentId);
		inputParameterMap.put("STATENO", stateno);
		inputParameterMap.put("DELETED_BY", userObject.getWorkerId());
		
		StringBuilder sb = new StringBuilder(discardReasonDescription);
		
		if(CommonUtil.isNotNullAndEmpty(comments)) {
			inputParameterMap.put("COMMENTS", sb.append(" : ").append(comments).toString());
		}else {
			inputParameterMap.put("COMMENTS", sb.toString());
		}
		
		
		
		
		int resultCount = scanDocumentDAO.deleteDocumentInPersonDocAssociation(inputParameterMap);
		
		if(resultCount == 0) {
			throw new DataRetrievalFailureException("Document and STATENO combination doesn't exist ");
		}
		
		resultCount = scanDocumentDAO.deleteDocumentInDocumentPersonAttribute(inputParameterMap);
		if(resultCount == 0) {
			throw new DataRetrievalFailureException("Failed to delete from document_person_Attr ");
		}
		resultCount = scanDocumentDAO.deleteDocumentInDocumentLocator(inputParameterMap);
		if(resultCount == 0) {
				throw new DataRetrievalFailureException("Failed to delete from document_locator ");
		}
		
		resultCount = scanDocumentDAO.updateStdScanDocTable(inputParameterMap);
		if(resultCount == 0) {
				throw new DataRetrievalFailureException("Failed to delete from document_locator ");
		}

      
		obj_SFTPChannel = remoteServer.getRemoteServerSession();
        
        if(obj_SFTPChannel == null){
               throw new DataRetrievalFailureException("Remote server(Scchoit31) connection not established.");
        }
        
     
               
               
       obj_SFTPChannel.rm(EnvironmentProperties.envProperties.getProperty("SCANNED_FILES_ENCRYPT_LOCATION")+scanDocType+"/"+documentId+".pdf");
                     
               
		}finally {
			if(obj_SFTPChannel != null) {
				obj_SFTPChannel.disconnect();
			}
		}
		
		
		return scanDocType;
		
	}
}

