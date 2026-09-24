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
	
	
//	@Override
	
	
	
//	@Override
//	public boolean decryptAndSaveToBackup(String fileName,
	
//	@Override
//	public void disCardScanDocument(String fileName,
	
//	@Override
//	public void deleteScannedDocumentFromSourceLocation(String fileName,


//	@Override
//	public boolean encryptAndSaveScanDocument(String inputFileName, String outputFileName, boolean throwError, String inputLocation, String outputLocation,  
//			
//			// Writing the Standard data to stdData object
	
//	@Override
//	public void cleanRemainingScanDocs(Map<String, String> schedulerMetaData, BindingResult bindingResult,
	
//	@Override
//	public void decryptDocumentsFromBackup(Map<String, String> schedulerMetaData, BindingResult bindingResult,
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

