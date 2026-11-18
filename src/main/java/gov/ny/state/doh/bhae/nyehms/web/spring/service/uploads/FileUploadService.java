package gov.ny.state.doh.bhae.nyehms.web.spring.service.uploads;

import gov.ny.state.doh.bhae.nyehms.web.spring.config.UploadProperties;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import gov.ny.state.doh.bhae.nyehms.web.spring.config.MailProperties;
import jakarta.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import com.symantec.scanengine.api.Policy;
import com.symantec.scanengine.api.Result;
import com.symantec.scanengine.api.ScanEngine;
import com.symantec.scanengine.api.ScanException;
import com.symantec.scanengine.api.StreamScanRequest;
import com.symantec.scanengine.api.ThreatInfo;

import au.com.bytecode.opencsv.CSVReader;
import gov.ny.state.doh.bhae.nyehms.web.spring.constants.CommonConstants;
import gov.ny.state.doh.bhae.nyehms.web.spring.dao.CommonDAO;
import gov.ny.state.doh.bhae.nyehms.web.spring.dao.document.ClusterDocumentDAO;
import gov.ny.state.doh.bhae.nyehms.web.spring.dao.document.CommonDocLabsDAO;
import gov.ny.state.doh.bhae.nyehms.web.spring.dao.document.DocumentDAO;
import gov.ny.state.doh.bhae.nyehms.web.spring.dao.document.LabDocumentDAO;
import gov.ny.state.doh.bhae.nyehms.web.spring.jdbc.JpaConnections;
import gov.ny.state.doh.bhae.nyehms.web.spring.dao.person.PersonDAO;
import gov.ny.state.doh.bhae.nyehms.web.spring.dao.upload.FileUploadDAO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.uploads.ClusterUploadVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.uploads.UploadNYCSurvRejectVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.CommonService;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.document.ClusterDocumentService;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.document.CommonDocumentService;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.document.DocumentTransactionHelper;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.document.HIVDocumentTransactionHelper;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.document.STDConfDocumentService;
import gov.ny.state.doh.bhae.nyehms.web.spring.repository.upload.CdcRidrInfoRepository;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonExceptionHandler;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonUtil;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.DateUtil;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.SendEmail;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.scheduler.LabCommonUtils;

/**
* This class is service interface between the user data to Database(MyBatis related configuration)
* 
* It renders the data from Database in the form List, Int, Sting etc uinsg MyBatis configuation file 
*  
* @author  
* @version 
*/
@Service
public class FileUploadService {

	@Autowired
	private UploadProperties uploadProperties;
	
	@Autowired
	FileUploadTransactionHelper fileUploadTransactionHelper;
	@Autowired
	private CommonExceptionHandler commonExceptionHandler;
	
	@Autowired PersonDAO personDAO;
	@Autowired
	private CdcRidrInfoRepository cdcRidrInfoRepository;
	
	@Autowired CommonDAO commonDAO;
	
	@Autowired CommonService commonService;
	
	@Autowired
	CommonDocumentService commonDocumentService;	
	
	@Autowired private CommonDocLabsDAO	commonDocLabsDAO;	
	
	@Autowired DocumentDAO documentDAO;
	

	@Autowired LabDocumentDAO labDocumentDAO;
	
	@Autowired FileUploadDAO fileUploadDAO;
	
	@Autowired JpaConnections jpaConnections;
	
	@Autowired SendEmail sendEmail;

	@Autowired MailProperties mailProperties;
	
	@Autowired ClusterDocumentService clusterDocumentService;
	
	@Autowired DocumentTransactionHelper documentTransactionHelper;
	
	@Autowired ClusterDocumentDAO clusterDocumentDAO;
	@Autowired HIVDocumentTransactionHelper hivDocumentTransactionHelper;
	
	@Autowired STDConfDocumentService stdConfDocumentService;
	
	private static final Logger log = LoggerFactory.getLogger(FileUploadService.class);
	
//	@Override
//	public Map<Object, Object> uploadUpdatedDispositionsFile(MultipartFile updatedDispositionFile,
//					//|| DateOfDeathCell.getStringCellValue().isEmpty()
//
//					//|| dispositionCell.getStringCellValue().isEmpty() 
//						// persist data into database in here
	
	
	public List<Integer> checkForExistingUpdatedMessageDocIds(
			Set<Integer> returnMessageDocIds){
		
		Map<String, Object> inputParameterMap = new HashMap<>();
		
		inputParameterMap.put("returnMessageDocIds", returnMessageDocIds);
		
		return personDAO.checkForExistingUpdatedMessageDocIds(inputParameterMap);
	}
	
	public List<Integer> getReturnDocIdNotExistInTable(Set<Integer> returnMessageDocIds){
		
		Map<String, Object> inputParameterMap = new HashMap<>();
		
		inputParameterMap.put("returnMessageDocIds", returnMessageDocIds);
		
		return personDAO.getReturnDocIdNotExistInTable(inputParameterMap);
		
	}
	
//	@Override
//	public int checkForDuplicateDispositionCode(
//	@Override
//	public void insertUpdatedDispositionCode(
	
//	@Override
//	public List<Map<String, Object>> checkExistingDateOfDeathForMessageDocIds(


	public void uploadRIDRData(MultipartFile inputFile,
			BindingResult bindingResult, UserObject userObject) {

		
		boolean isError = false; 
		
		XSSFWorkbook workbook = null;
		XSSFSheet worksheet = null;
		
		String transactionMessage = null;
		
		String fileName = null;
		
		List<Map<String, Object>>  ridrDataList = new ArrayList<>();
		
		try{
			
			
			if(inputFile.isEmpty()){
				
				isError = true;
				transactionMessage = "The file uploaded contains no RIDR Data";
				
			}else{
				 workbook = new XSSFWorkbook(inputFile.getInputStream());
				
				 fileName = inputFile.getOriginalFilename();
				
				
				 worksheet = workbook.getSheetAt(0);
				
				 
				int index = 0; 
				
				
				String[] RIDR_COLUMN_NAMES = uploadProperties.ridrColumnNames().split(",");
				
				String[] RIDR_DB_COLUMN_NAMES = uploadProperties.ridrDbColumnNames().split(",");
				
				
				
				int RIDR_COLUMN_NAMES_LENGTH = RIDR_COLUMN_NAMES.length;
				
				//int workerId = userObject.getWorkerId();
				
				
				while (index <= worksheet.getLastRowNum()) {
					
					XSSFRow row = worksheet.getRow(index++);
					
					if(index == 1){
						
						
						
						for(int columnIndex = 0; columnIndex < RIDR_COLUMN_NAMES_LENGTH; columnIndex++){
							
							
							String columnNameInSheet = row.getCell(columnIndex).getStringCellValue();
							
							columnNameInSheet = columnNameInSheet.replaceAll("\n", " ");
							
							if(!RIDR_COLUMN_NAMES[columnIndex].equalsIgnoreCase(columnNameInSheet.trim())){
								
								isError = true; 
								
								transactionMessage = "Column headers must match the template";
								
								break;
								
							}
							
						}
						
						
						if(isError){
							break;
						}else{
							continue;
						}
						
						
						
						
					}
					
					
					
					
					
					if(row == null){
						
						if(log.isDebugEnabled()){
							log.debug("Empty is row is there");
						}
						continue;
					}
					
					
					boolean hasRowData = false;
					
					for(int columnIndex = 0; columnIndex < RIDR_COLUMN_NAMES_LENGTH; columnIndex++){
						
						if(!(row.getCell(columnIndex) == null || CommonUtil.isNullOrEmpty(row.getCell(columnIndex).toString()))){
							
							hasRowData = true;
							
							break;
						}
						
					}
					
					
					if(!hasRowData){
						continue;
					}
					
					
					Map<String, Object> ridrData = new HashMap<>();
					
					for (int columnIndex = 0; columnIndex < RIDR_COLUMN_NAMES_LENGTH; columnIndex++) {
						
						
						if(row.getCell(columnIndex) != null){
							ridrData.put(RIDR_DB_COLUMN_NAMES[columnIndex], row.getCell(columnIndex).toString().trim());
						}
						
					}
					
					//ridrData.put("workerId", workerId);
					
					ridrDataList.add(ridrData);
					
					
					
				}
				
				
			}
	        
			
			if(!isError){
				Map<String, List<Map<String, Object>>> inputParameterMap = new HashMap<>();
				
				inputParameterMap.put("ridrDataList", ridrDataList);
				
				
				
				//documentDAO.writeRidrData(inputParameterMap);
				
				
				
				fileUploadTransactionHelper.uploadRidrData(fileName, ridrDataList, userObject);
				
				
				commonExceptionHandler.inspectDBTransacionResult(0, "Successfully inserted all the RIDR information.", bindingResult);
			}
			
			
			
		}catch(DataAccessException dataAccessException){
			isError = true;
			
			transactionMessage = "Failed while loading the data, please contact helpline";
			
			log.error("Failed to read the file.   {}",dataAccessException.getMessage());
		}catch(Exception exception){
			isError = true;
			
			transactionMessage = "Failed while loading the data, please contact helpline";
			
			log.error("Failed to read the file.   {}",exception.getMessage());
		}finally{
			
			try {
				workbook.close();
			} catch (IOException e) {
				log.error("IO EXCEPTION while closing the workbook   :  {}",e.getMessage());
			}catch (Exception e) {
				log.error("EXCEPTION while closing the workbook   :  {}",e.getMessage());
			}
		}
		
		if(isError){
			commonExceptionHandler.inspectDBTransacionResult(-1, transactionMessage, bindingResult);
		}
		
	}
	
	public void identifyCDCRidrDuplicates(String fileName){
		
		fileUploadDAO.identifyCDCRidrDuplicates(fileName);
	}
	
	
	@Transactional
	/**
	 * Stores the rows of an uploaded CDC RIDR file (CDC_RIDR_INFO), in JDBC batches, in the caller's transaction.
	 */
	public void uploadRidrDataAsBatch(String fileName, List<Map<String, Object>> ridrDataList, int workerId) {
		cdcRidrInfoRepository.saveAll(ridrDataList.stream().map(row -> CdcRidrInfoMapping.toRow(row, fileName, workerId)).toList());
		cdcRidrInfoRepository.flush();
	}
	
	
	public Result scanFile(InputStream inputStream, String fileName) {
		//Note: the fileName parameter is not used here.  You could reference it in the logs, //for example.
		
		Result result = null;
		
		//For testing purposes, use this string.  This string should generate a virus detection:  
		//	X5O!P%@AP[4\PZX54(P^)7CC)7}$EICAR-STANDARD-ANTIVIRUS-TEST-FILE!$H+H*
		//See http://www.eicar.org/86-0-Intended-use.html
		//Need to escape the single backslash.
	    
		//Instantiate the scan engine:
		ScanEngine.ScanEngineInfo scanEngineTobeUsed = new ScanEngine.ScanEngineInfo("scanserv",1344);
		Vector <ScanEngine.ScanEngineInfo> scanEnginesForScanning = new Vector <>();
		scanEnginesForScanning.add(scanEngineTobeUsed); 
		ScanEngine scanEngine = null;
		
		try {
			scanEngine = ScanEngine.createScanEngine(scanEnginesForScanning, 60000, 10000);
		} catch (ScanException e) {
			log.error("Error while creating scan engine.",e);
			return result;
		}

		//This output stream will contain the original file contents or, if it contained a virus,  it will contain information about the detected virus. For example, when testing  
		//	with the EICAR test string, it contains the following html:
		//	<html><title>Content Blocked Notice</title>The content you just requested contains EICAR Test String and was blocked by the Symantec Protection Engine based on local administrator settings.  Contact your local administrator for further information.</html>
		ByteArrayOutputStream baoStream = new ByteArrayOutputStream();
		
		StreamScanRequest scanRequest = null;
		try {
			scanRequest = scanEngine.createStreamScanRequest("", null, baoStream, Policy.DEFAULT);
		} catch (ScanException e) {
			log.error("Error while creating scan request.",e);
			return result;
		}
	
		//Rather than trying to send the entire InputStream content at once to the scan engine (which can cause failure), break it up into smaller chunks and send them to the scan engine:
		byte[] buff = new byte[4096];
		long bytesToRead = 0;
		try {
			bytesToRead = inputStream.available();
		} catch (IOException e) {
			log.error("Error while obtaining bytes to read.",e);
			return result;
		}
		int buffCapRead = buff.length;
		int bytesRead = 0;
	
		do
		{
			if (bytesToRead >= buffCapRead)	{
				buffCapRead = buff.length;
			}
			else {
				buffCapRead = (int) bytesToRead;
			}
	
			// Refresh data buffer.
			buff = new byte[buffCapRead];
	
			try {
				bytesRead = inputStream.read(buff, 0, buffCapRead);
			} catch (IOException e) {
				log.error("Error while reading bytes.",e);
				return result;
			}
			// Send the bytes to scan engine
			try {
				scanRequest.send(buff);
			} catch (ScanException e) {
				log.error("Error while sending bytes to scan engine.",e);
				return result;
			}
			bytesToRead = bytesToRead - bytesRead;
		} while (bytesToRead > 0);
	
		try {
			inputStream.close();
		} catch (IOException e) {
			log.error("Error closing input stream.",e);
		}
		
		//Perform the scan:
		try {
			result = scanRequest.finish();
		} catch (ScanException e) {
			log.error("Error while performing scan.",e);
			return result;
		}
	  
		return result;
	}

	public Map<Object, Object> validateAndScanFile(MultipartFile fileData, BindingResult bindingResult) throws IOException {
		
		boolean isError = false;
		String transactionMessage = null;
		Map<Object, Object> resultMap = new HashMap<>();
		String uploadFileName = fileData.getOriginalFilename();
		
		if (uploadFileName.length() > 255 || !Pattern.matches("[a-zA-Z0-9]{1,200}\\.[a-zA-Z0-9]{1,10}", uploadFileName)) {
			isError = true;
			transactionMessage = "The filename " + uploadFileName + " must be less than 255 characters, only contain alphanumeric characters (Aa-Zz and 0-9) and a maximum of one period(.)";
		}
		
		if(!isError){
			Result scanResult = scanFile(fileData.getInputStream(), uploadFileName);
			if(scanResult == null) {
				isError = true;
				transactionMessage = "Unable to perform virus check on file  <b>" + uploadFileName + "</b>. Please upload the file again.";
			} else if (scanResult != null) {
				int totalInfectionCt = scanResult.getTotalInfection();
				if (totalInfectionCt > 0) {
					isError = true;
					log.error("Virus(es) detected in uploaded file");
					log.error("Total infection: {}", scanResult.getTotalInfection());
					ThreatInfo[] threatInfoArray = scanResult.getThreatInfo();
					for(int t = 0; t < threatInfoArray.length; t++) {
						ThreatInfo threatInfo = threatInfoArray[t];
						StringBuilder strBuild = new StringBuilder();
						strBuild.append("Threat info: disposition = ").append(threatInfo.getDisposition()).append(", file name = ").append(threatInfo.getFileName()).append(", violation Id = ").append(threatInfo.getViolationId())
							.append(", violation Name = ").append(threatInfo.getViolationName()).append(", threat category = ").append(threatInfo.getThreatCategory()).append(", status = ").append(scanResult.getStatus());
						log.error(strBuild.toString());
					}
					transactionMessage = "Uploaded file " + uploadFileName + " contains a virus!!! Please scan your file and remove the virus and then try again.";
				}
			}
		}
				
		if(isError){
			commonExceptionHandler.inspectDBTransacionResult(-1, transactionMessage, bindingResult);
			resultMap.put(isError, transactionMessage);
		}		
		return resultMap;
	}


	public 	Map<Object, Object> uploadClusterData(MultipartFile inputFile, ClusterUploadVO clusterUploadVO, BindingResult bindingResult, UserObject userObject, HttpSession session) {
		
		boolean isError = false; 
		
		XSSFWorkbook workbook = null;
		XSSFSheet worksheet = null;
		String transactionMessage = null;
		List<Map<String, Object>>  clusterDataList = new ArrayList<>();
		Map<Object, Object> resultMap = new HashMap<>();
		String clusterFileName = null;
		boolean uploadSuccess = false;
		int noDatRowCt = 0;
		String clusterType = clusterUploadVO.getClusterType();
		try{
			if(!inputFile.getOriginalFilename().endsWith(".xlsx")) {
				isError = true;
				transactionMessage = "Only MS Excel file with .xlsx extension are allowed. Please use the given template.";				
			} else if(inputFile.isEmpty()){
				isError = true;
				transactionMessage = "The file uploaded contains no Cluster Data";
			}else{
				clusterFileName = inputFile.getOriginalFilename();
				workbook = new XSSFWorkbook(inputFile.getInputStream());
				worksheet = workbook.getSheetAt(0);
				int index = 0; 

				String[] CLUSTER_COLUMN_NAMES = null;
				String[] CLUSTER_DB_COLUMN_NAMES = null;
				
				CLUSTER_COLUMN_NAMES = uploadProperties.clusterBhaeColumnNames().split(",");
				CLUSTER_DB_COLUMN_NAMES = uploadProperties.clusterBhaeDbColumnNames().split(",");
				
				int CLUSTER_COLUMN_NAMES_LENGTH = CLUSTER_COLUMN_NAMES.length;
				
				while (index <= worksheet.getLastRowNum()) {
					
					XSSFRow row = worksheet.getRow(index++);
					
					if(index == 1){
						
						for(int columnIndex = 0; columnIndex < CLUSTER_COLUMN_NAMES_LENGTH; columnIndex++){
							
							String columnNameInSheet = row.getCell(columnIndex).getStringCellValue();
							
							columnNameInSheet = columnNameInSheet.replaceAll("\n", " ");
							
							if(!CLUSTER_COLUMN_NAMES[columnIndex].equalsIgnoreCase(columnNameInSheet.trim())){
								
								isError = true; 
								transactionMessage = "Column headers must match the template.";
								break;
								
							}
						}
						
						if(isError){
							break;
						}else if (worksheet.getLastRowNum() == 0){
							isError = true; 
							transactionMessage = "No data in the file to upload.";
							break;
						} else {
							continue;
						}
					}
					
					if(row == null){
						
						if(log.isDebugEnabled()){
							log.debug("Empty row is there.");
						}
						continue;
					}
					
					boolean hasRowData = false;
					
					for(int columnIndex = 0; columnIndex < CLUSTER_COLUMN_NAMES_LENGTH; columnIndex++){
						
						if(!(row.getCell(0) == null || CommonUtil.isNullOrEmpty(row.getCell(0).toString()))){
							
							hasRowData = true;
							break;
						}
					}
					
					if(!hasRowData){
						continue;
					}
					
					
					Map<String, Object> clusterData = new HashMap<>();
					DataFormatter fmt = new DataFormatter();
					
					for (Cell c : row) {
						 clusterData.put(CLUSTER_COLUMN_NAMES[c.getColumnIndex()], fmt.formatCellValue(c));
					}
					clusterDataList.add(clusterData);
				}
				
				if (!isError && clusterDataList.size() == 0) {
					isError = true;
					transactionMessage = "The selected file contains no Cluster Data. Please use template to create the data file.";
				}
				
 				if (!isError) {
					resultMap = validateClusterMolecularData(clusterDataList);
					
					if (resultMap.size() > 0) {
						isError = true;
						transactionMessage = "Following data errors are found, please fix and upload again.";
					}
 				}				
			}
			
			if(!isError){
				Map<String, List<Map<String, Object>>> inputParameterMap = new HashMap<>();
				
				inputParameterMap.put("clusterDataList", clusterDataList);
				
				documentTransactionHelper.uploadClusterFileData(clusterDataList, clusterUploadVO, userObject, bindingResult, session);
				
				int nysnoErrCt = fileUploadDAO.errorCountinUpload(clusterUploadVO.getClusterIdPk());
				
				String dataUploadMsg = "The file was uploaded. ";
				
				if (noDatRowCt > 0) {
					dataUploadMsg += noDatRowCt + " records do not have NYEHMS stateno. ";
				}		
				
				if (nysnoErrCt > 0) {
					dataUploadMsg += nysnoErrCt + " STATENO were not found in NYEHMS.";
				}			
				
				int dupStateNos = clusterUploadVO.getDupStatenoCount();
				if (dupStateNos > 0) {
					dataUploadMsg += dupStateNos + " stateno was duplicate. ";
				}
				
				commonExceptionHandler.inspectDBTransacionResult(0, dataUploadMsg , bindingResult);
				
				uploadSuccess = true;

			}
	        
			
		}catch(DataAccessException dataAccessException){
			isError = true;
			
			transactionMessage = "Failed while loading the data, please contact helpline";
			log.error("Failed to read the file.   {}",dataAccessException.getMessage());
		}catch(Exception exception){
			isError = true;
			
			transactionMessage = "Failed while loading the data, please contact helpline";
			log.error("Failed to read the file.   {}",exception.getMessage());
		}finally{
			
			try {
				workbook.close();
			} catch (IOException e) {
				log.error("IO EXCEPTION while closing the workbook   :  {}",e.getMessage());
			}catch (Exception e) {
				log.error("EXCEPTION while closing the workbook   :  {}",e.getMessage());
			}
		}
		
		if(isError){
			commonExceptionHandler.inspectDBTransacionResult(-1, transactionMessage, bindingResult);
		}
		
		return resultMap;
		
	}

	public boolean validateClusterId(String clusterId) {
		
		int clusterIdCount = fileUploadDAO.validateClusterId(clusterId);
		
		if (clusterIdCount > 0) {
			return true;
		}
		return false;
	}



	public Map<String, String> uploadRecordSearchFile(MultipartFile uploadedRecordSearchFile,
			BindingResult bindingResult, UserObject userObject){
		
		boolean isError = false; 
		
		XSSFWorkbook workbook = null;
		XSSFSheet worksheet = null;
		
		String transactionMessage = null;
		
		
		
		Map<String, String> resultMap = new HashMap<>();
		
		Map<String, String> uploadRecordSearchStatenoMap = new HashMap<>();

		
		
		try{
			
			
			
			if(uploadedRecordSearchFile.isEmpty()){
				
				isError = true;
				transactionMessage = "The file uploaded contains no STATENO(S)";
				
			}else{
				workbook = new XSSFWorkbook(uploadedRecordSearchFile.getInputStream());
				
				 worksheet = workbook.getSheetAt(0);
				
				 
				int index = 0; 
				
				
				String[] UPLOAD_RECORD_SEARCH_COLUMN_NAMES = uploadProperties.recordSearchColumnNames().split(",");
				
				
				int UPLOAD_RECORD_SEARCH_COLUMN_NAMES_LENGTH = UPLOAD_RECORD_SEARCH_COLUMN_NAMES.length;
				
				
				
				while (index <= worksheet.getLastRowNum()) {
					
					XSSFRow row = worksheet.getRow(index++);
					
					if(index == 1){
						
						
						
						
						for(int columnIndex = 0; columnIndex < UPLOAD_RECORD_SEARCH_COLUMN_NAMES_LENGTH; columnIndex++){
							
							String columnNameInSheet = row.getCell(columnIndex).getStringCellValue();
							
							columnNameInSheet = columnNameInSheet.replaceAll("\n", " ");
							
							if(!UPLOAD_RECORD_SEARCH_COLUMN_NAMES[columnIndex].equalsIgnoreCase(columnNameInSheet.trim())){
								
								isError = true; 
								
								transactionMessage = "Column headers must match the template";
								
								break;
								
							}
							
						}
						
						if(isError){
							break;
						}else{
							continue;
						}
						
					}
					
					if(row == null){
						
						if(log.isDebugEnabled()){
							log.debug("Empty is row is there");
						}
						continue;
					}
					
					
					boolean hasRowData = false;
					
					
					for(int columnIndex = 0; columnIndex < UPLOAD_RECORD_SEARCH_COLUMN_NAMES_LENGTH; columnIndex++){
						
						if(!(row.getCell(columnIndex) == null || CommonUtil.isNullOrEmpty(row.getCell(columnIndex).toString()))){
							
							hasRowData = true;
							
							break;
						}
						
					}
					
					if(!hasRowData){
						continue;
					}
					
					
										
					if(row.getCell(0) == null && CommonUtil.isNotNullAndEmpty(row.getCell(0).toString())){
						isError = true;
						
						
						transactionMessage = "STATENO is missing at Line number "+index+", please correct the Data";
						
						log.error(transactionMessage);
						
						break;
					}
					
					String STATENO = row.getCell(0).toString().trim();
					
					
					if(uploadRecordSearchStatenoMap.get(STATENO) != null){
						
						isError = true;
						transactionMessage = "There are duplicate records in the file upload, please de-duplicate file";
						
						log.error(transactionMessage);
						
						break;
						
					}
					
					StringBuilder failedMessage = new StringBuilder();

					
					
					Cell dispositionCell = row.getCell(1); 

					//|| dispositionCell.getStringCellValue().isEmpty() 
					if(dispositionCell == null  || CommonUtil.isNullOrEmpty(dispositionCell.toString())){
						
						resultMap.put(STATENO, "Comments are mandatory for every Record Search STATENO.");
						
						continue;
					}
					
						
					String comments = row.getCell(1).toString().trim();;
					
					
					uploadRecordSearchStatenoMap.put(STATENO, comments);
							
					
					
					
					
				}
				
				
				if(!isError && uploadRecordSearchStatenoMap.size() == 0){
					isError = true;
					transactionMessage = "The file uploaded contains no return messages";
					log.error(transactionMessage);
				}
				
			}
	        
			
			
			
			if(!isError){
				
				
				fileUploadTransactionHelper.uploadRecordSearchStateno(uploadRecordSearchStatenoMap, resultMap, bindingResult, userObject);
				
			}
			
			
			
			
			
			
		}catch(DataAccessException dataAccessException){
			isError = true;
			
			transactionMessage = "Failed to read the file, please contact helpline";
			
			log.error("Failed to read the file.   {}",dataAccessException.getMessage());
		}catch(Exception exception){
			isError = true;
			
			transactionMessage = "Failed to read the file, please contact helpline";
			
			log.error("Failed to read the file.   {}",exception.getMessage());
		}finally{
			
			try {
				workbook.close();
			} catch (IOException e) {
				log.error("IO EXCEPTION while closing the workbook   :  {}",e.getMessage());
			}catch (Exception e) {
				log.error("EXCEPTION while closing the workbook   :  {}",e.getMessage());
			}
		}
		
		if(isError){
			commonExceptionHandler.inspectDBTransacionResult(-1, transactionMessage, bindingResult);
		}
		
		
		return resultMap;
		
		
	}
	
	
	public Map<String, List<String>> getSTATENOHistoryDataFile(MultipartFile uploadedStatenoFile,
			BindingResult bindingResult, UserObject userObject){
		
		boolean isError = false; 
		
		XSSFWorkbook workbook = null;
		XSSFSheet worksheet = null;
		
		String transactionMessage = null;
		
		Map<String, List<String>> statenoHistoryMap = new HashMap<>();
		
		try{
			
			
			
			if(uploadedStatenoFile.isEmpty()){
				
				isError = true;
				transactionMessage = "The file uploaded contains no STATENO(S)";
				
			}else{
				workbook = new XSSFWorkbook(uploadedStatenoFile.getInputStream());
				
				 worksheet = workbook.getSheetAt(0);
				
				 
				int index = 0; 
				
				
				String[] UPLOAD_UTILITY_STATENO_HISTORY_COLUMN_NAMES = uploadProperties.statenoHistoryColumnName().split(",");
				
				
				int UPLOAD_UTILITY_STATENO_HISTORY_COLUMN_NAMES_LENGTH = UPLOAD_UTILITY_STATENO_HISTORY_COLUMN_NAMES.length;
				
				
				
				while (index <= worksheet.getLastRowNum()) {
					
					XSSFRow row = worksheet.getRow(index++);
					
					if(index == 1){
						
						
						
						
						for(int columnIndex = 0; columnIndex < UPLOAD_UTILITY_STATENO_HISTORY_COLUMN_NAMES_LENGTH; columnIndex++){
							
							String columnNameInSheet = row.getCell(columnIndex).getStringCellValue();
							
							columnNameInSheet = columnNameInSheet.replaceAll("\n", " ");
							
							if(!UPLOAD_UTILITY_STATENO_HISTORY_COLUMN_NAMES[columnIndex].equalsIgnoreCase(columnNameInSheet.trim())){
								
								isError = true; 
								
								transactionMessage = "Column headers must match the template";
								
								break;
								
							}
							
						}
						
						if(isError){
							break;
						}else{
							continue;
						}
						
					}
					
					if(row == null){
						
						if(log.isDebugEnabled()){
							log.debug("Empty is row is there");
						}
						continue;
					}
					
					
					boolean hasRowData = false;
					
					
					for(int columnIndex = 0; columnIndex < UPLOAD_UTILITY_STATENO_HISTORY_COLUMN_NAMES_LENGTH; columnIndex++){
						
						if(!(row.getCell(columnIndex) == null || CommonUtil.isNullOrEmpty(row.getCell(columnIndex).toString()))){
							
							hasRowData = true;
							
							break;
						}
						
					}
					
					if(!hasRowData){
						continue;
					}
					
					
										
					
					
					String STATENO = row.getCell(0).toString().trim();
					
					try{
						
						Map<Object, Object> inOutParameterMap = new HashMap<>();

						inOutParameterMap.put("p_state_no", STATENO);

						personDAO.getStatenoHistory(inOutParameterMap);

						List<String> p_stateno_hist = ((List<String>) inOutParameterMap.get("p_stateno_hist"));

						if(p_stateno_hist == null || p_stateno_hist.size() == 0){
							
							p_stateno_hist = new ArrayList<String>();
							
							p_stateno_hist.add("There is no History for this STATENO");
							
							statenoHistoryMap.put(STATENO, p_stateno_hist);
							
						}else{
							
							statenoHistoryMap.put(STATENO, p_stateno_hist);
							
						}
						
						
						
						
					}catch(Exception e){
						
						
						List<String> p_stateno_hist = new ArrayList<>();
						
						p_stateno_hist.add("Failed to get the STATENO History");
						
						log.error("Failed to get the STATENO History for : {}",STATENO);
						
						log.error(e.getMessage());
						
						statenoHistoryMap.put(STATENO,p_stateno_hist);
						
					}
					
				}
				
				
				
			}
			
			
		}catch(DataAccessException dataAccessException){
			isError = true;
			
			transactionMessage = "Failed to read the file, please contact helpline";
			
			log.error("Failed to read the file.   {}",dataAccessException.getMessage());
		}catch(Exception exception){
			isError = true;
			
			transactionMessage = "Failed to read the file, please contact helpline";
			
			log.error("Failed to read the file.   {}",exception.getMessage());
		}finally{
			
			try {
				workbook.close();
			} catch (IOException e) {
				log.error("IO EXCEPTION while closing the workbook   :  {}",e.getMessage());
			}catch (Exception e) {
				log.error("EXCEPTION while closing the workbook   :  {}",e.getMessage());
			}
		}
		
		if(isError){
			commonExceptionHandler.inspectDBTransacionResult(-1, transactionMessage, bindingResult);
		}
		
		
		return statenoHistoryMap;

	}
	
	
	
	public Map<String, String> getCurrentStatenoDataFile(MultipartFile uploadedStatenoFile,
			BindingResult dummyBindingResult, UserObject userObject){
		
		boolean isError = false; 
		
		XSSFWorkbook workbook = null;
		XSSFSheet worksheet = null;
		
		String transactionMessage = null;
		
		Map<String, String> currentStatenoMap = new HashMap<>();
		
		List<String> inputStatenos = new ArrayList<>();
		
		try{
			
			
			
			if(uploadedStatenoFile.isEmpty()){
				
				isError = true;
				transactionMessage = "The file uploaded contains no STATENO(S)";
				
			}else{
				workbook = new XSSFWorkbook(uploadedStatenoFile.getInputStream());
				
				 worksheet = workbook.getSheetAt(0);
				
				 
				int index = 0; 
				
				
				String[] UPLOAD_UTILITY_STATENO_HISTORY_COLUMN_NAMES = uploadProperties.statenoHistoryColumnName().split(",");
				
				
				int UPLOAD_UTILITY_STATENO_HISTORY_COLUMN_NAMES_LENGTH = UPLOAD_UTILITY_STATENO_HISTORY_COLUMN_NAMES.length;
				
				
				
				while (index <= worksheet.getLastRowNum()) {
					
					XSSFRow row = worksheet.getRow(index++);
					
					if(index == 1){
						
						
						
						
						for(int columnIndex = 0; columnIndex < UPLOAD_UTILITY_STATENO_HISTORY_COLUMN_NAMES_LENGTH; columnIndex++){
							
							String columnNameInSheet = row.getCell(columnIndex).getStringCellValue();
							
							columnNameInSheet = columnNameInSheet.replaceAll("\n", " ");
							
							if(!UPLOAD_UTILITY_STATENO_HISTORY_COLUMN_NAMES[columnIndex].equalsIgnoreCase(columnNameInSheet.trim())){
								
								isError = true; 
								
								transactionMessage = "Column headers must match the template";
								
								break;
								
							}
							
						}
						
						if(isError){
							break;
						}else{
							continue;
						}
						
					}
					
					if(row == null){
						
						if(log.isDebugEnabled()){
							log.debug("Empty is row is there");
						}
						continue;
					}
					
					
					boolean hasRowData = false;
					
					
					for(int columnIndex = 0; columnIndex < UPLOAD_UTILITY_STATENO_HISTORY_COLUMN_NAMES_LENGTH; columnIndex++){
						
						if(!(row.getCell(columnIndex) == null || CommonUtil.isNullOrEmpty(row.getCell(columnIndex).toString()))){
							
							hasRowData = true;
							
							break;
						}
						
					}
					
					if(!hasRowData){
						continue;
					}
					
					
					String STATENO = row.getCell(0).toString().trim();
					
					if(CommonUtil.isNotNullAndEmpty(STATENO)) {
						inputStatenos.add(STATENO);
					}
					
				}
				
				
				
			}
			
			
			if(inputStatenos.size() == 0) {
				isError = true;
				
				transactionMessage = "There is no Data in the uploaded sheet.";
			}
			

			
		
			Map<String, List<String>> inputMap = new HashMap<>();
			
			inputMap.put("STATENOS", inputStatenos);
			
			List<Map<String, String>> currentStatenoList = fileUploadDAO.getCurrentActiveStatenoList(inputMap);
			
			
			for (Map<String, String> map : currentStatenoList) {
				
				currentStatenoMap.put(map.get("ACTUAL_STATENO"), map.get("CURRENT_STATENO"));
			}
			
			
			
		}catch(DataAccessException dataAccessException){
			isError = true;
			
			transactionMessage = "Failed to read the file, please contact helpline";
			
			log.error("Failed to read the file.   {}",dataAccessException.getMessage());
		}catch(Exception exception){
			isError = true;
			
			transactionMessage = "Failed to read the file, please contact helpline";
			
			log.error("Failed to read the file.  {}",exception.getMessage());
		}finally{
			
			try {
				workbook.close();
			} catch (IOException e) {
				log.error("IO EXCEPTION while closing the workbook   :  {}",e.getMessage());
			}catch (Exception e) {
				log.error("EXCEPTION while closing the workbook   :  {}",e.getMessage());
			}
		}
		
		if(isError){
			commonExceptionHandler.inspectDBTransacionResult(-1, transactionMessage, dummyBindingResult);
		}
		
		
		return currentStatenoMap;

	}
	
	public void updatedHIVDataWithNYCData(MultipartFile inputFile,
			BindingResult bindingResult, UserObject userObject){
		
		boolean isError = false; 
		
		XSSFWorkbook workbook = null;
		XSSFSheet worksheet = null;
		
		String transactionMessage = null;
		
		Map<Integer, Map<String, Object>>  updatedHivData = new HashMap<>();
		
		List<String> columnNames = new ArrayList<>(); 
		
		List<String>  successDocuments = new ArrayList<>();
		
		List<String> failureDocuments = new ArrayList<>();
		
		try{
			
			
			
			if(inputFile.isEmpty()){
				
				isError = true;
				transactionMessage = "The file uploaded contains no HIV Data";
				
			}else{
				workbook = new XSSFWorkbook(inputFile.getInputStream());
				
				 worksheet = workbook.getSheetAt(0);
				
				 
				int index = 0; 
				
				
				//String[] RIDR_COLUMN_NAMES = EnvironmentProperties.envProperties.getProperty("RIDR_COLUMN_NAMES").split(",");
				
				//String[] RIDR_DB_COLUMN_NAMES = EnvironmentProperties.envProperties.getProperty("RIDR_DB_COLUMN_NAMES").split(",");
				
				
				
				//int RIDR_COLUMN_NAMES_LENGTH = RIDR_COLUMN_NAMES.length;
				
				//int workerId = userObject.getWorkerId();
				

				int columnIndex = 0;
				
				while (index <= worksheet.getLastRowNum()) {
					
					XSSFRow row = worksheet.getRow(index++);
					
					if(index == 1){
						
						
						boolean readColumnNames = true;
						
						
						while(readColumnNames){
							
							
							if(row.getCell(columnIndex) == null || CommonUtil.isNullOrEmpty(row.getCell(columnIndex).toString())){
								
								
								readColumnNames = false;;
								
								columnIndex--;
								
							}else{
								
								columnNames.add(row.getCell(columnIndex).getStringCellValue());
								
							}
							
							columnIndex++;
							
						}
						
						if(columnIndex < 2){
							isError = true;
							transactionMessage = "The file uploaded contains no HIV Data";
						}
						
						if(isError){
							break;
						}else{
							continue;
						}
						
						
						
						
					}
					
					
					
					
					
					if(row == null){
						
						if(log.isDebugEnabled()){
							log.debug("Empty is row is there");
						}
						continue;
					}
					
					
					boolean hasRowData = false;
					
					for(int indexCount = 0; indexCount < columnIndex; indexCount++){
						
						if(!(row.getCell(indexCount) == null || CommonUtil.isNullOrEmpty(row.getCell(indexCount).toString()))){
							
							hasRowData = true;
							
							break;
						}
						
					}
					
					
					if(!hasRowData){
						continue;
					}
					
					
					Map<String, Object> documentData = new HashMap<>();
					
					Integer originalDocumentId = null;
					
					Integer derivedDocumentId = null;
					
					for (int indexCount = 0; indexCount < columnIndex; indexCount++) {
						
						
						if(row.getCell(indexCount) != null){
							
							
							
							
							if(indexCount == 0){
								
								originalDocumentId = (int) row.getCell(indexCount).getNumericCellValue();
									
								derivedDocumentId = originalDocumentId;
										
								loadHivDocumentCurrentProcessDetails(originalDocumentId, derivedDocumentId, documentData, failureDocuments);
								
								
								if(documentData.size() == 0){
									
									break;
									
								}else{
									
									derivedDocumentId = (Integer)documentData.get("DOCUMENT_ID");
								}
								
								
							}
							
							String _columnValue = row.getCell(indexCount).toString();
							
							if(_columnValue.endsWith(".0")){
								
								
								_columnValue = _columnValue.substring(0, _columnValue.length() - 2);
							}
							
							documentData.put(columnNames.get(indexCount), _columnValue);
						}
						
					}
					
					//ridrData.put("workerId", workerId);
					
					if(documentData.size() > 0 && updatedHivData.get(derivedDocumentId) == null){
						
						updatedHivData.put(derivedDocumentId, documentData);
						
					}
					
					
					
					
				}
				
				
			}
	        
			
			if(!isError){
				
				
				
				List<Map<String, String>> stdToRawColumnsList = commonDocLabsDAO.getStdToRawColumnList(LabCommonUtils.getDocumentType("HIV_LAB"));
				
				Map<String, String> documentColumnMappingObj = LabCommonUtils.convertColumnListMapObj(stdToRawColumnsList, "RAW_COLUMN", "STD_COLUMN");
				
				
				
				
				for (Integer derivedDocumentId : updatedHivData.keySet()) {
					
					try{
					
						hivDocumentTransactionHelper.editHivDocumentWithNYCData(derivedDocumentId, updatedHivData.get(derivedDocumentId), documentColumnMappingObj, bindingResult, userObject, successDocuments, failureDocuments);
					
					}catch(Exception exception){
						
						log.error("{}  : Failed to edit the Document {}",derivedDocumentId,exception.getMessage());
						
						failureDocuments.add(derivedDocumentId+"  : Failed to edit the Document "+exception.getMessage());
					}
					
					
				}
				
				StringBuilder mailContent = new StringBuilder();
				
				mailContent.append("Documents update details ");
				
				
				
				if(failureDocuments.size() > 0){
					
					mailContent.append("<br/><br/><br/><h2 style='font-weight:bold'>Failed Documents</h2><br/><br/>");
					
					
					mailContent.append("<table border='1'><tr><th>Failed Message</th></tr>");
					
					for (String message: failureDocuments) {
						
						mailContent.append("<tr><td>");
						mailContent.append(message);
						mailContent.append("</td>");
					}
					
					mailContent.append("</table>");
					
				}
				

				if(successDocuments.size() > 0){
					
					mailContent.append("<br/><br/><br/><h2 style='font-weight:bold'>Success Documents</h2><br/><br/>");
					
					
					mailContent.append("<table border='1'><tr><th>Success Message</th></tr>");
					
					for (String message: successDocuments) {
						
						mailContent.append("<tr><td>");
						mailContent.append(message);
						mailContent.append("</td>");
					}
					
					mailContent.append("</table>");
					
				}
				
				
				sendEmail.sendSSLMessage(
						mailProperties.hivDocumentUpdateDateTo().split(","),
						"Updating HIV Data status", mailContent.toString(),
						mailProperties.helpLine().from(), userObject.getEmailAddress().split(","), null,
						CommonConstants.RESPONSE_CONTENT_TYPE_HTML);
				
				
				//commonExceptionHandler.inspectDBTransacionResult(0, "Successfully inserted all the RIDR information.", bindingResult);
			}
			
			
			
		}catch(DataAccessException dataAccessException){
			isError = true;
			
			transactionMessage = "Failed while loading the data, please contact helpline";
			
			log.error("Failed to read the file.   {}",dataAccessException.getMessage());
		}catch(Exception exception){
			isError = true;
			
			transactionMessage = "Failed while loading the data, please contact helpline";
			
			log.error("Failed to read the file.   {}",exception.getMessage());
		}finally{
			
			try {
				workbook.close();
			} catch (IOException e) {
				log.error("IO EXCEPTION while closing the workbook   :  {}",e.getMessage());
			}catch (Exception e) {
				log.error("EXCEPTION while closing the workbook   :  {}",e.getMessage());
			}
		}
		
		if(isError){
			commonExceptionHandler.inspectDBTransacionResult(-1, transactionMessage, bindingResult);
		}else{
			commonExceptionHandler.inspectDBTransacionResult(0, "Successfully uploaded the HIV Data", bindingResult);
		}
		
		
	}
	
	private void loadHivDocumentCurrentProcessDetails(Integer originalDocumentId, Integer derivedDocumentId, Map<String, Object> documentData, List<String> failureDocuments){
		
		Map<String, Object> documentDetails = labDocumentDAO.hivDocumentCurrentProcessStatusDetails(derivedDocumentId);
		
		int current_process_Status_raw = (Integer)documentDetails.get("CURRENT_PROCESS_STATUS_RAW");
		
		if(current_process_Status_raw == 0){
			failureDocuments.add("Input document doesn't exist");
		}
		else if(current_process_Status_raw == 9 || current_process_Status_raw == 33 || current_process_Status_raw == 8  || current_process_Status_raw == 18){
			
			failureDocuments.add("Input document in "+current_process_Status_raw+ " status, can't be edited");
			
		}else if(current_process_Status_raw == 20){
			
			derivedDocumentId = documentDAO.getNewDocumentIdUsingParentDocument(derivedDocumentId);
			
			loadHivDocumentCurrentProcessDetails(originalDocumentId, derivedDocumentId, documentData, failureDocuments);
			
			
		}else if(current_process_Status_raw == 19){
			
			derivedDocumentId = documentDAO.getNewDocumentIdUsingMultiSpotDocument(derivedDocumentId);
			
			loadHivDocumentCurrentProcessDetails(originalDocumentId, derivedDocumentId, documentData, failureDocuments);
			
		}else{
			
			documentData.putAll(documentDetails);
			
			
		}
	}
	
	
	public Map<Object, Object> uploadPSConfirmedStdsFile(MultipartFile uploadPSConfirmSTDFile,BindingResult bindingResult, UserObject userObject) throws IOException{
		
		boolean isError = false; 
		String transactionMessage = null;
		
		List<Map<String, Object>>  fileDataList = new ArrayList<>();
		Map<Object, Object> resultMap = new HashMap<>();
		String uploadFileName = uploadPSConfirmSTDFile.getOriginalFilename();
		CSVReader csvread = null;
		
		try{
			
 			if(!uploadFileName.endsWith(".csv")) {
 				isError = true;
 				transactionMessage = "Only Comma Separated Values file with .csv extension are allowed. Please use the given template.";			
 			} else if(uploadPSConfirmSTDFile.isEmpty()){
				isError = true;
				transactionMessage = "The file uploaded contains no data.";
			}else{			
				
				log.error("STD Confirm File Upload Process Started.");
			
				csvread = new CSVReader(new InputStreamReader(uploadPSConfirmSTDFile.getInputStream()));
				List<String[]> rowLists = csvread.readAll();
				
				String[] columnHeader = rowLists.get(0);
				String[] UPLOAD_PS_CONFIRMED_STD_COLUMN_NAMES = uploadProperties.psConfirmedStdColumnNames().split(",");
				int UPLOAD_PS_CONFIRMED_STD_COLUMN_NAMES_LENGTH = UPLOAD_PS_CONFIRMED_STD_COLUMN_NAMES.length;
				
				for(int columnIndex = 0; columnIndex < UPLOAD_PS_CONFIRMED_STD_COLUMN_NAMES_LENGTH; columnIndex++){
					
					String headerColumnName = columnHeader[columnIndex];
					headerColumnName = headerColumnName.replaceAll("[^a-zA-Z0-9_]", "");
					headerColumnName = headerColumnName.replaceAll("\n", " ");
					
					if(!UPLOAD_PS_CONFIRMED_STD_COLUMN_NAMES[columnIndex].equalsIgnoreCase(headerColumnName.trim())){
						isError = true; 
						transactionMessage = "Column headers must match the template.";
						break;
					}
				}
				
				if (!isError) {
					
					for(int i=1; i<rowLists.size(); i++){
						Map<String, Object> fileData = new HashMap<>();
						String[] recordRow = rowLists.get(i);
	
						// Check if row is null, then ignored
						boolean hasRowData = false;
						for(int columnIndex = 0; columnIndex < UPLOAD_PS_CONFIRMED_STD_COLUMN_NAMES_LENGTH; columnIndex++){
							
							if(!(CommonUtil.isNullOrEmpty(recordRow[columnIndex]))){
								hasRowData = true;
								break;
							}
						}
						
						if (hasRowData) {
							for(int columnIndex=0; columnIndex<UPLOAD_PS_CONFIRMED_STD_COLUMN_NAMES_LENGTH;columnIndex++){					
								if (!(columnIndex == 11 || columnIndex == 15 || columnIndex == 30 || columnIndex == 52) ) {
									fileData.put(UPLOAD_PS_CONFIRMED_STD_COLUMN_NAMES[columnIndex], recordRow[columnIndex]);
								} else {
									if (CommonUtil.isNotNullAndEmpty(recordRow[columnIndex]) && recordRow[columnIndex].trim().length() < 10) {
										fileData.put(UPLOAD_PS_CONFIRMED_STD_COLUMN_NAMES[columnIndex], DateUtil.convertDateMMDDYYYY(recordRow[columnIndex]));
									} else {
										fileData.put(UPLOAD_PS_CONFIRMED_STD_COLUMN_NAMES[columnIndex], recordRow[columnIndex]);
									}
								}
							}
							
							fileDataList.add(fileData);
						}
					}
				}
			}
			if (fileDataList.size() == 0 && !isError) {
				isError = true;
				transactionMessage = "The selected file contains no PS Confirmed STD Data. Please use template to create the data file.";
			}
			
			
			Map<String, List<String>> codedStdConfData = stdConfDocumentService.getStdConfLookupData();
			
			
			if (!isError) {
				resultMap = validatePSConfirmedSTDData(fileDataList, codedStdConfData);
				
				if (resultMap.size() > 0) {
					isError = true;
					transactionMessage = "Following data errors are found, please fix and upload again.";
				}
			}
			
			if(!isError){
				
				log.error("STD Confirm File Upload : No Data Error Found.");
				
				fileUploadDAO.deleteTempSTDConfirmTableData();
				
				fileUploadTransactionHelper.uploadPSConfirmedSTDData(uploadFileName, fileDataList, userObject);	
				
				log.error("STD Confirm File Uploaded Successfully. : " );
			}
		}catch(DataAccessException dataAccessException){
			isError = true;
			transactionMessage = "Error occured, please contact helpline." + dataAccessException.getCause();
			log.error("Failed to read the file, DataAccessException : {}",dataAccessException.getMessage());
			dataAccessException.printStackTrace();
		}catch(Exception exception){
			isError = true;
			transactionMessage = "Failed to read the file, please contact helpline. " + exception.toString() ;
			log.error("Failed to read the file.   {}",exception.getMessage());
			exception.printStackTrace();
		}finally{
			try {
				if (csvread != null) { csvread.close();}
				fileUploadDAO.deleteTempSTDConfirmTableData();
			} catch (Exception e) {
				log.error("EXCEPTION while closing CSV Reader:  {}",e.getMessage());
			}
		}
		
		if(isError){
			commonExceptionHandler.inspectDBTransacionResult(-1, transactionMessage, bindingResult);
		}else{
			commonExceptionHandler.inspectDBTransacionResult(0, "Successfully uploaded the PS Confirmed STD Data of " + fileDataList.size() + " records.", bindingResult);
		}
		
		return resultMap;
				
	}
		
	private Map<Object, Object>  validatePSConfirmedSTDData(List<Map<String, Object>>  fileDataList, Map<String, List<String>> stdRefDataMap) {
		
		int counter = 0;
		String CASEKEYString;
		StringBuilder errorMessage;
		Map<Object, Object> dataErrorMapList = new HashMap<>();
		
		for (Map<String, Object> dataRow : fileDataList) {
			
			errorMessage = new StringBuilder();
			counter++;
			
			if ( (dataRow.get("CASEKEY") == null || CommonUtil.isNullOrEmpty(dataRow.get("CASEKEY").toString()) ) || !CommonUtil.isNumeric(dataRow.get("CASEKEY").toString()) ) {
				CASEKEYString = "Row " + counter;
				errorMessage.append("CASEKEY is a required field and can only be a numeric value.<br/>");
			} else {
				CASEKEYString = dataRow.get("CASEKEY").toString();
			}
			
			errorMessage = validateVar(dataRow, "HINSERIALNO", "INT", errorMessage);
			errorMessage = validateVar(dataRow, "FNAME", "STRING", errorMessage);
			errorMessage = validateVar(dataRow, "LNAME", "STRING", errorMessage);
			errorMessage = validateVar(dataRow, "PERSON_STATE", "PERSON_STATE", errorMessage);
			errorMessage = validateVar(dataRow, "PERSON_ZIP", "ZIP", errorMessage);

			if (dataRow.get("PERSON_COUNTY") != null  && CommonUtil.isNotNullAndEmpty(dataRow.get("PERSON_COUNTY").toString()) && 
					!(CommonUtil.isNumeric(dataRow.get("PERSON_COUNTY").toString()) && (dataRow.get("PERSON_COUNTY").toString().length()  == 3) ) ) {
				errorMessage.append("PERSON_COUNTY can only have numeric value of 3 char.<br/>");
			}
			
			errorMessage = validateVar(dataRow, "PERSON_PHONE", "PHONE", errorMessage);
			errorMessage = validateVar(dataRow, "DOB", "DATE_REQUIRED", errorMessage);
			
			if (dataRow.get("ETHNICITY") != null  && CommonUtil.isNotNullAndEmpty(dataRow.get("ETHNICITY").toString()) 
					&& (!(dataRow.get("ETHNICITY").toString().equals("1") || dataRow.get("ETHNICITY").toString().equals("2") || dataRow.get("ETHNICITY").toString().equals("9"))) ) {
				errorMessage.append("ETHNICITY can only have values 1, 2 or 9.<br/>");
			}
			if (dataRow.get("BIRTH_SEX") != null  && CommonUtil.isNotNullAndEmpty(dataRow.get("BIRTH_SEX").toString()) 
					&& (!(dataRow.get("BIRTH_SEX").toString().equals("1") || dataRow.get("BIRTH_SEX").toString().equals("2") )) ) {
				errorMessage.append("BIRTH_SEX can only have values 1 or 2.<br/>");
			}
			if (dataRow.get("CURRENT_GENDER") != null  && CommonUtil.isNotNullAndEmpty(dataRow.get("CURRENT_GENDER").toString()) 
					&& (!(dataRow.get("CURRENT_GENDER").toString().equalsIgnoreCase("M") || dataRow.get("CURRENT_GENDER").toString().equalsIgnoreCase("F")  || dataRow.get("CURRENT_GENDER").toString().equalsIgnoreCase("X") 
							 || dataRow.get("CURRENT_GENDER").toString().equalsIgnoreCase("Y")  || dataRow.get("CURRENT_GENDER").toString().equalsIgnoreCase("A")  || dataRow.get("CURRENT_GENDER").toString().equalsIgnoreCase("U") )) ) {
				errorMessage.append("CURRENT_GENDER can only have values M, F, X, Y, A or U.<br/>");
			}
			errorMessage = validateVar(dataRow, "DATE_OF_DEATH", "DATE_NOT_REQUIRED", errorMessage);
			
			errorMessage = validateVar(dataRow, "PROV_PHONE", "PHONE", errorMessage);
			errorMessage = validateVar(dataRow, "PROV_ST", "STATE", errorMessage);
			errorMessage = validateVar(dataRow, "PROV_ZIP", "ZIP", errorMessage);
			errorMessage = validateVar(dataRow, "DIAG_DATE", "DATE_REQUIRED", errorMessage);

			List<String> diseaseRefList = stdRefDataMap.get("DISEASE");
			
			
			if ( (dataRow.get("DISEASE") == null || CommonUtil.isNullOrEmpty(dataRow.get("DISEASE").toString()) || !(CommonUtil.isNumeric(dataRow.get("DISEASE").toString()) 
					&& diseaseRefList.contains(dataRow.get("DISEASE").toString())))) {
				errorMessage.append("DISEASE(").append(dataRow.get("DISEASE").toString()).append(") is a required field and can only have values ").append(diseaseRefList.stream().collect(Collectors.joining(", "))).append(".<br/>");
			}

			errorMessage = validateVar(dataRow, "RACE_WHITE", "RACE", errorMessage);
			errorMessage = validateVar(dataRow, "RACE_BLACK", "RACE", errorMessage);
			errorMessage = validateVar(dataRow, "RACE_INDIAN", "RACE", errorMessage);
			errorMessage = validateVar(dataRow, "RACE_ASIAN", "RACE", errorMessage);
			errorMessage = validateVar(dataRow, "RACE_PACIFIC", "RACE", errorMessage);
			errorMessage = validateVar(dataRow, "RACE_UNKNOWN", "RACE", errorMessage);
			errorMessage = validateVar(dataRow, "RACE_OTHER", "RACE", errorMessage);
			errorMessage = validateVar(dataRow, "RISK_SEX_MALE", "RISK_SEX", errorMessage);			
			errorMessage = validateVar(dataRow, "RISK_SEX_FEMALE", "RISK_SEX", errorMessage);			
			errorMessage = validateVar(dataRow, "RISK_SEX_TRANSGENDER", "RISK_SEX", errorMessage);			
			errorMessage = validateVar(dataRow, "RISK_SEX_IDU", "RISK_SEX", errorMessage);			
			errorMessage = validateVar(dataRow, "RISK_SEX_DRUGS", "RISK_SEX", errorMessage);			
			errorMessage = validateVar(dataRow, "RISK_SEX_EXCHANGED", "RISK_SEX", errorMessage);			
			errorMessage = validateVar(dataRow, "RISK_SEX_ANONYMOUS", "RISK_SEX", errorMessage);			
			errorMessage = validateVar(dataRow, "RISK_SEX_MSM", "RISK_SEX", errorMessage);			
			errorMessage = validateVar(dataRow, "RISK_SEX_WITHOUT_CONDOM", "RISK_SEX", errorMessage);			
			errorMessage = validateVar(dataRow, "RISK_DRUGS_USED", "RISK_DRUG", errorMessage);		
			List<String> drugCodeRefList = stdRefDataMap.get("DRUG_USED_LIST");
			errorMessage = validateDrugUsedList(dataRow.get("RISK_DRUG_USED_LIST"), errorMessage, drugCodeRefList);		
			errorMessage = validateVar(dataRow, "RISK_DRUG_INCARCERATED", "RISK_DRUG", errorMessage);		
			errorMessage = validateVar(dataRow, "RISK_DRUG_PARAPHENALIA", "RISK_DRUG", errorMessage);		
			errorMessage = validateVar(dataRow, "RISK_IDU", "RISK_DRUG", errorMessage);		
			
			List<String> x900TestRefList = stdRefDataMap.get("X900TEST");
			errorMessage = validateVar(dataRow, "NINEHUNDREDTEST", "900TEST", errorMessage, x900TestRefList);	
			
			List<String> testTechnologyRefList = stdRefDataMap.get("TEST_TECHNOLOGY");
			
			if (dataRow.get("TESTTECHNOLOGY") != null  && CommonUtil.isNotNullAndEmpty(dataRow.get("TESTTECHNOLOGY").toString()) 
					&& !testTechnologyRefList.contains(dataRow.get("TESTTECHNOLOGY").toString().trim()) ) {
				errorMessage.append("TESTTECHNOLOGY(").append(dataRow.get("TESTTECHNOLOGY").toString()).append(") can only have values ").append(testTechnologyRefList.stream().collect(Collectors.joining(", "))).append(".<br/>");
			}	
			
			List<String> x900PosRefList = stdRefDataMap.get("X900POSITIVE");
			errorMessage = validateVar(dataRow, "X900POSITIVE", "900TEST", errorMessage, x900PosRefList);	
			errorMessage = validateVar(dataRow, "X900DXDATE", "DATE_NOT_REQUIRED", errorMessage);
			
			List<String> currentTestResultRefList = stdRefDataMap.get("CURRENT900TESTRESULT");
			
			if (dataRow.get("CURRENT900TESTRESULT") != null  && CommonUtil.isNotNullAndEmpty(dataRow.get("CURRENT900TESTRESULT").toString()) 
					&& !currentTestResultRefList.contains(dataRow.get("CURRENT900TESTRESULT").toString().trim())) {
				errorMessage.append("CURRENT900TESTRESULT can only have values ").append(currentTestResultRefList.stream().collect(Collectors.joining(", "))).append(".<br/>");
			}	

			if (CommonUtil.isNotNullAndEmpty(errorMessage.toString())) {
				dataErrorMapList.put(CASEKEYString, errorMessage);
			}
		}	
		
		return dataErrorMapList;
	}


	@Transactional
	public void uploadPSConfirmedSTDData(String fileName, List<Map<String, Object>> stdConfirmDataList, int workerId) throws SQLException {
		
		Connection conn = jpaConnections.appConnection();
		PreparedStatement ps = null;
		
		final int batchSize = 1000;
		int count = 0;
		
		try {
			ps = conn.prepareStatement("insert into STG_STD_CONFIRMED_DOC ( "
					+ "CASEKEY, HINSERIALNO, FNAME, MNAME, LNAME, PERSON_ADDRESS, PERSON_CITY, PERSON_STATE, PERSON_ZIP, PERSON_COUNTY, PERSON_PHONE, DOB, ETHNICITY"
					+ ", BIRTH_SEX, CURRENT_GENDER, DATE_OF_DEATH, PROV_FNAME, PROV_LNAME, PROV_PHONE, PROV_ADDRESS, PROV_CITY, PROV_ST, PROV_ZIP"
					+ ", RACE_WHITE, RACE_BLACK, RACE_INDIAN, RACE_ASIAN, RACE_PACIFIC, RACE_UNKNOWN, RACE_OTHER, DIAG_DATE, DISEASE, RISK_SEX_MALE"
					+ ", RISK_SEX_FEMALE, RISK_SEX_TRANSGENDER, RISK_SEX_IDU, RISK_SEX_DRUGS, RISK_SEX_EXCHANGED, RISK_SEX_ANONYMOUS, RISK_SEX_MSM, RISK_SEX_WITHOUT_CONDOM, RISK_DRUGS_USED, RISK_DRUG_USED_LIST"
					+ ", RISK_DRUG_OTHER_TEXT, RISK_DRUG_INCARCERATED, RISK_DRUG_PARAPHENALIA, RISK_IDU, RISK_OTHER_TEXT, FIELDNOTE, NINEHUNDREDTEST, TESTTECHNOLOGY, X900POSITIVE, X900DXDATE"
					+ ", CURRENT900TESTRESULT, UPLOAD_ID, UPLOAD_DT, CURRENT_PROCESS_STATUS, FILENAME )"
					+ "	values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,"
							//  1  2  3  4  5  6  7  8  9 10 11 12 13  
					+ "			?, ?, ?, ?, ?, ?, ?, ?, ?, ?,"
							//  1  2  3  4  5  6  7  8  9 10 
					+ "			?, ?, ?, ?, ?, ?, ?, ?, ?, ?,"
							//  1  2  3  4  5  6  7  8  9 10
					+ "			?, ?, ?, ?, ?, ?, ?, ?, ?, ?,"
							//  1  2  3  4  5  6  7  8  9 10 
					+ "			?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, '"+workerId+"', SYSDATE, 1, ?)");
							//  1  2  3  4  5  6  7  8  9 10 11								12			
			
			for (Map<String, Object> map : stdConfirmDataList) {
				
				ps.setString(1, (String)map.get("CASEKEY"));
				ps.setString(2, (String)map.get("HINSERIALNO"));
				ps.setString(3, (String)map.get("FNAME"));
				ps.setString(4, (String)map.get("MNAME"));
				ps.setString(5, (String)map.get("LNAME"));
				ps.setString(6, (String)map.get("PERSON_ADDRESS"));
				ps.setString(7, (String)map.get("PERSON_CITY"));
				ps.setString(8, (String)map.get("PERSON_STATE"));
				ps.setString(9, (String)map.get("PERSON_ZIP"));
				ps.setString(10, (String)map.get("PERSON_COUNTY"));
				ps.setString(11, (String)map.get("PERSON_PHONE"));
				ps.setString(12, (String)map.get("DOB"));
				ps.setString(13, (String)map.get("ETHNICITY"));
				
				ps.setString(14, (String)map.get("BIRTH_SEX"));
				ps.setString(15, (String)map.get("CURRENT_GENDER"));
				ps.setString(16, (String)map.get("DATE_OF_DEATH"));				
				ps.setString(17, (String)map.get("PROV_FNAME"));
				ps.setString(18, (String)map.get("PROV_LNAME"));
				ps.setString(19, (String)map.get("PROV_PHONE"));
				ps.setString(20, (String)map.get("PROV_ADDRESS"));
				ps.setString(21, (String)map.get("PROV_CITY"));
				ps.setString(22, (String)map.get("PROV_ST"));
				ps.setString(23, (String)map.get("PROV_ZIP"));
				
				ps.setString(24, (String)map.get("RACE_WHITE"));
				ps.setString(25, (String)map.get("RACE_BLACK"));
				ps.setString(26, (String)map.get("RACE_INDIAN"));
				ps.setString(27, (String)map.get("RACE_ASIAN"));
				ps.setString(28, (String)map.get("RACE_PACIFIC"));
				ps.setString(29, (String)map.get("RACE_UNKNOWN"));
				ps.setString(30, (String)map.get("RACE_OTHER"));
				ps.setString(31, (String)map.get("DIAG_DATE"));
				ps.setString(32, (String)map.get("DISEASE"));
				ps.setString(33, (String)map.get("RISK_SEX_MALE"));
				
				ps.setString(34, (String)map.get("RISK_SEX_FEMALE"));
				ps.setString(35, (String)map.get("RISK_SEX_TRANSGENDER"));
				ps.setString(36, (String)map.get("RISK_SEX_IDU"));
				ps.setString(37, (String)map.get("RISK_SEX_DRUGS"));
				ps.setString(38, (String)map.get("RISK_SEX_EXCHANGED"));
				ps.setString(39, (String)map.get("RISK_SEX_ANONYMOUS"));
				ps.setString(40, (String)map.get("RISK_SEX_MSM"));
				ps.setString(41, (String)map.get("RISK_SEX_WITHOUT_CONDOM"));
				ps.setString(42, (String)map.get("RISK_DRUGS_USED"));
				ps.setString(43, (String)map.get("RISK_DRUG_USED_LIST"));
				
				ps.setString(44, (String)map.get("RISK_DRUG_OTHER_TEXT"));
				ps.setString(45, (String)map.get("RISK_DRUG_INCARCERATED"));
				ps.setString(46, (String)map.get("RISK_DRUG_PARAPHENALIA"));
				ps.setString(47, (String)map.get("RISK_IDU"));
				ps.setString(48, (String)map.get("RISK_OTHER_TEXT"));
				ps.setString(49, (String)map.get("FIELDNOTE"));
				ps.setString(50, (String)map.get("NINEHUNDREDTEST"));
				ps.setString(51, (String)map.get("TESTTECHNOLOGY"));
				ps.setString(52, (String)map.get("X900POSITIVE"));
				ps.setString(53, (String)map.get("X900DXDATE"));
				
				ps.setString(54, (String)map.get("CURRENT900TESTRESULT"));
				ps.setString(55, fileName);
				
				ps.addBatch();
				
				if (++count % batchSize == 0) {
					ps.executeBatch();
		//			log.error("PS Confirmed Batch Update is in progress : " + count);
				}
				
			}
			
			ps.executeBatch();
			log.error("PS Confirmed Batch Update completed : {}", count);
							
		} finally {
			try{
				if(ps != null){
					ps.close();
				}
				if(conn != null){
					conn.close();
				}
			} catch (SQLException e) {
				log.error("Exception occured while closing the connection objects : {}", e.getMessage());
			}
		}
		
	}

	public int getStdConfDocumentID() {
		
		Map<Object,Object> map = new HashMap<>();
		int newDocId=0;
		
		map.put("p_document_type","STD_CONF");
		map.put("p_new_doc_id", "");
		map.put("p_parent_doc_id", "");
		
		try{			
			commonDocumentService.getNewDocId(map);
			newDocId=(Integer)map.get("p_new_doc_id");
		}catch (DataAccessException e) {
			throw new DataRetrievalFailureException("Failed to create a new STD_CONF document for "+newDocId+" :  "+e.getMessage());
		}
		
		return newDocId;
	}
	
	private StringBuilder validateVar (Map<String, Object> dataRow , String passedVar, String varType, StringBuilder errorMessage) {
		return validateVar (dataRow , passedVar, varType, errorMessage, null);
	}
	
	private StringBuilder validateVar (Map<String, Object> dataRow , String passedVar, String varType, StringBuilder errorMessage, List<String> refDataList) {
		Object currentObj = dataRow.get(passedVar);
		
		if(varType.equals("STRING")) {
			if ( (currentObj == null || CommonUtil.isNullOrEmpty(currentObj.toString()) ) ) {
				errorMessage.append( passedVar + "  is a required field.<br/>");
			}
		} else if(varType.equals("INT")) {
			if ( (currentObj == null || CommonUtil.isNullOrEmpty(currentObj.toString()) ) || !CommonUtil.isNumeric(currentObj.toString()) ) {
				errorMessage.append( passedVar + " is a required field  and can only be a numeric value.<br/>");
			}
		} else if(varType.equals("DATE_REQUIRED")) {
			if ( (currentObj == null || CommonUtil.isNullOrEmpty(currentObj.toString()) ) || !DateUtil.isValidDate(currentObj.toString()) ) {
				errorMessage.append( passedVar + " is a required field and must follow the format MM/DD/YYYY. Should be greater than 01/01/1900 and no future dates.<br/>");
			}
		}  else if(varType.equals("RACE")) {
			if ( currentObj != null && CommonUtil.isNotNullAndEmpty(currentObj.toString()) && (!(currentObj.toString().equals("1") || currentObj.toString().equals("0") )) ) {
				errorMessage.append( passedVar + " can only have values 0 or 1.<br/>");
			}
		}  else if(varType.equals("RISK_SEX")) {
			if ( currentObj != null && CommonUtil.isNotNullAndEmpty(currentObj.toString()) && (!(currentObj.toString().equalsIgnoreCase("Y") || currentObj.toString().equalsIgnoreCase("N")  || currentObj.toString().equalsIgnoreCase("U") 
					|| currentObj.toString().equalsIgnoreCase("R") || currentObj.toString().equalsIgnoreCase("D")  || currentObj.toString().equalsIgnoreCase("O")  || currentObj.toString().equalsIgnoreCase("X") )) ) {
				errorMessage.append( passedVar + " can only have values Y,N,U,R,D,O or X.<br/>");
			}
		}  else if(varType.equals("RISK_DRUG")) {
			if ( currentObj != null && CommonUtil.isNotNullAndEmpty(currentObj.toString()) && (!(currentObj.toString().equalsIgnoreCase("Y") || currentObj.toString().equalsIgnoreCase("N")  || currentObj.toString().equalsIgnoreCase("U") 
					|| currentObj.toString().equalsIgnoreCase("R") || currentObj.toString().equalsIgnoreCase("D")  )) ) {
				errorMessage.append( passedVar + " can only have values Y,N,U,R or D values.<br/>");
			}
		}  else if(varType.equals("900TEST")) {
			if ( currentObj != null && CommonUtil.isNotNullAndEmpty(currentObj.toString()) &&  !refDataList.contains(currentObj.toString().trim()) ) {
				errorMessage.append( passedVar + " can only have values ").append(refDataList.stream().collect(Collectors.joining(", "))).append(".<br/>");
			}
		}  else if(varType.equals("DATE_NOT_REQUIRED")) {
			if ( (currentObj != null && CommonUtil.isNotNullAndEmpty(currentObj.toString()) ) && !DateUtil.isValidDate(currentObj.toString()) ) {
				errorMessage.append( passedVar + " must follow the format MM/DD/YYYY. Should be greater than 01/01/1900 and no future dates.<br/>");
			}
		} else if (varType.equals("PERSON_STATE")) {
			if ( (currentObj == null || CommonUtil.isNullOrEmpty(currentObj.toString()) ) || (currentObj.toString().length() != 2 ) ) {
				errorMessage.append( passedVar + " is required field. Only 2 char of state code is allowed..<br/>");
			}
		} else if (varType.equals("STATE")) {
			if ( (currentObj != null && CommonUtil.isNotNullAndEmpty(currentObj.toString()) ) && (currentObj.toString().length() != 2 ) ) {
				errorMessage.append( passedVar + "  can only have value of 2 char.<br/>");
			}
		} else if (varType.equals("ZIP")) {
			if ( (currentObj != null && CommonUtil.isNotNullAndEmpty(currentObj.toString()) ) && (!CommonUtil.isZipValid(currentObj.toString().trim()) ) ) {
				errorMessage.append( passedVar + " should be in 12345 or 12345-1234 format only.<br/>");
			}
		} else if (varType.equals("STRING10")) {
			if ( (currentObj != null && CommonUtil.isNotNullAndEmpty(currentObj.toString()) ) && (currentObj.toString().length() > 10 ) ) {
				errorMessage.append( passedVar + "  can only have value of 10 char max.<br/>");
			}
		}
		
	return errorMessage;
	}
	
	private String setValidateRowVar(Object curObj) {
		
		if (curObj == null) {
			return "";
		}
		else if (CommonUtil.isNotNullAndEmpty(curObj.toString())) {
			return curObj.toString().trim().toUpperCase();
		}
		
		return "";
		
	}
	
	public StringBuilder validateDrugUsedList (Object drugUsedList, StringBuilder errorMessage, List<String> validDrugCodes) {
		
			if ( drugUsedList != null && CommonUtil.isNotNullAndEmpty(drugUsedList.toString()) ) {
				
				boolean allExist = Arrays.stream(drugUsedList.toString().split(",")).map(String::valueOf).allMatch(validDrugCodes::contains);
				
				if (!allExist) {
				errorMessage
						.append("RISK_DRUG_USED_LIST can only have values ").append(validDrugCodes.stream()
								.map(Integer::valueOf).sorted().map(String::valueOf).collect(Collectors.joining(", ")))
						.append(".<br/>");
				}
			}
	return errorMessage;
	}


	public boolean insertTSCriteria(ClusterUploadVO clusterUploadVO, BindingResult bindingResult, UserObject userObject, HttpSession session) {
		// 
		boolean isError = false; 
		String transactionMessage = null;
		boolean uploadSuccess = false;
		try{
				documentTransactionHelper.uploadClusterFileData(null, clusterUploadVO, userObject, bindingResult, session);
				
				String dataUploadMsg = "The criteria was uploaded. ";
				
				if (clusterUploadVO.getTsUpdateRetire().equals("U")) {
					dataUploadMsg = "The criteria was updated. ";
				} else if (clusterUploadVO.getTsUpdateRetire().equals("R")) {
					dataUploadMsg = "The criteria was retired. ";
				} else if (clusterUploadVO.getTsUpdateRetire().equals("A")) {
					dataUploadMsg = "The criteria was activated. ";
				}
				
				commonExceptionHandler.inspectDBTransacionResult(0, dataUploadMsg , bindingResult);
				
				uploadSuccess = true;

		}catch(DataAccessException dataAccessException){
			isError = true;
			
			transactionMessage = "Failed while loading the data, please contact helpline";
			log.error("Failed to upload the Time Space Criteria (dataAccessException).   {}",dataAccessException.getMessage());
		}catch(Exception exception){
			isError = true;
			
			transactionMessage = "Failed while loading the data, please contact helpline";
			log.error("Failed to upload the Time Space Criteria (exception).   {}",exception.getMessage());
		}
		
		if(isError){
			commonExceptionHandler.inspectDBTransacionResult(-1, transactionMessage, bindingResult);
		}
		
	return uploadSuccess;
		
	
	}	

	public void moveSTDConfirmFromTempToRaw() {
		
		// Moving document_ids from Stage Table to Document_Locator Table
		fileUploadDAO.moveSTDConfirmDocumentID();
		
		// Moving document_ids from STage Table to RAW_STD_CONFIRMED_DOC Table
		fileUploadDAO.moveSTDConfirmFromTempToRaw();	
		
		// Deleting all data from the staging table
		fileUploadDAO.deleteTempSTDConfirmTableData();
		
	}

	public Map<Object, Object> uploadNYCSurvRejectFile(MultipartFile uploadNYCRejectFile, BindingResult bindingResult, UserObject userObject) throws IOException {
		
		boolean isError = false; 
		XSSFWorkbook workbook = null;
		XSSFSheet worksheet = null;
		String transactionMessage = null;
		
		HSSFWorkbook oldWorkbook = null;
		HSSFSheet  oldWorksheet = null;
		
		List<Map<String, Object>>  fileDataList = new ArrayList<>();
		Map<Object, Object> resultMap = new HashMap<>();
		String uploadFileName = uploadNYCRejectFile.getOriginalFilename();
		
		try{
			
 			if( !(uploadFileName.endsWith(".xlsx") || uploadFileName.endsWith(".xls")) ) {
 				isError = true;
 				transactionMessage = "Only MS Excel file with .xlsx or .xls extension are allowed. Please use the given template.";				
 			} else if(uploadNYCRejectFile.isEmpty()){
				isError = true;
				transactionMessage = "The file uploaded contains no data.";
			}else{
				
				if (uploadFileName.endsWith(".xlsx")) {
				workbook = new XSSFWorkbook(uploadNYCRejectFile.getInputStream());
				worksheet = workbook.getSheetAt(0);
				} else {
					oldWorkbook = new HSSFWorkbook(uploadNYCRejectFile.getInputStream());
					oldWorksheet = oldWorkbook.getSheetAt(0);
				}
				
				int index = 0; 
				
				String[] UPLOAD_NYC_SURV_REJECT_COLUMN_NAMES = uploadProperties.nycSurvRejectColumnNames().split(",");
				
				int UPLOAD_NYC_SURV_REJECT_COLUMN_NAMES_LENGTH = UPLOAD_NYC_SURV_REJECT_COLUMN_NAMES.length;
				
				if (uploadFileName.endsWith(".xlsx")) {
					while (index <= worksheet.getLastRowNum()) {
						
						XSSFRow row = worksheet.getRow(index++);
						
						if(index == 1){
							for(int columnIndex = 0; columnIndex < UPLOAD_NYC_SURV_REJECT_COLUMN_NAMES_LENGTH; columnIndex++){
								
								String columnNameInSheet = row.getCell(columnIndex).getStringCellValue();
								columnNameInSheet = columnNameInSheet.replaceAll("\n", " ");
								
								if(!UPLOAD_NYC_SURV_REJECT_COLUMN_NAMES[columnIndex].equalsIgnoreCase(columnNameInSheet.trim())){
									isError = true; 
									transactionMessage = "Column headers must match the template.";
									break;
								}
							}
							
							if(isError){
								break;
							}else{
								continue;
							}
						}
						
						if(row == null){
							
							if(log.isDebugEnabled()){
								log.debug("Empty row is there");
							}
							continue;
						}
						
						boolean hasRowData = false;
						for(int columnIndex = 0; columnIndex < UPLOAD_NYC_SURV_REJECT_COLUMN_NAMES_LENGTH; columnIndex++){
							
							if(!(row.getCell(columnIndex) == null || CommonUtil.isNullOrEmpty(row.getCell(columnIndex).toString()))){
								hasRowData = true;
								break;
							}
						}
						
						if(!hasRowData){
							continue;
						}
						
						Map<String, Object> fileData = new HashMap<>();
						DataFormatter fmt = new DataFormatter();
								
						for (Cell c : row) {
							 CellReference cr = new CellReference(c);
						       fileData.put(UPLOAD_NYC_SURV_REJECT_COLUMN_NAMES[c.getColumnIndex()], fmt.formatCellValue(c));
						}
						
						fileDataList.add(fileData);
					}
				} else {

					while (index <= oldWorksheet.getLastRowNum()) {
						
						HSSFRow row = oldWorksheet.getRow(index++);
						
						if(index == 1){
							for(int columnIndex = 0; columnIndex < UPLOAD_NYC_SURV_REJECT_COLUMN_NAMES_LENGTH; columnIndex++){
								
								String columnNameInSheet = row.getCell(columnIndex).getStringCellValue();
								columnNameInSheet = columnNameInSheet.replaceAll("\n", " ");
								
								if(!UPLOAD_NYC_SURV_REJECT_COLUMN_NAMES[columnIndex].equalsIgnoreCase(columnNameInSheet.trim())){
									isError = true; 
									transactionMessage = "Column headers must match the template.";
									break;
								}
							}
							
							if(isError){
								break;
							}else{
								continue;
							}
						}
						
						if(row == null){
							
							if(log.isDebugEnabled()){
								log.debug("Empty row is there");
							}
							continue;
						}
						
						boolean hasRowData = false;
						for(int columnIndex = 0; columnIndex < UPLOAD_NYC_SURV_REJECT_COLUMN_NAMES_LENGTH; columnIndex++){
							
							if(!(row.getCell(columnIndex) == null || CommonUtil.isNullOrEmpty(row.getCell(columnIndex).toString()))){
								hasRowData = true;
								break;
							}
						}
						
						if(!hasRowData){
							continue;
						}
						
						Map<String, Object> fileData = new HashMap<>();
						DataFormatter fmt = new DataFormatter();
								
						for (Cell c : row) {
							 CellReference cr = new CellReference(c);
						       fileData.put(UPLOAD_NYC_SURV_REJECT_COLUMN_NAMES[c.getColumnIndex()], fmt.formatCellValue(c));
						}
						
						fileDataList.add(fileData);
					}
				
				}
				
 				if (fileDataList.size() == 0 && !isError) {
 					isError = true;
 					transactionMessage = "The selected file contains no NYC Surv Reject Data. Please use template to create the data file.";
 				}
				
 				if (!isError) {
					resultMap = validateNYCSurvRejectData(fileDataList);
					
					if (resultMap.size() > 0) {
						isError = true;
						transactionMessage = "Following data errors are found, please fix and upload again.";
					}
 				}
			}
			
			if(!isError){
				
				List<UploadNYCSurvRejectVO> nycSurvRejectList = new ArrayList<>();
				
				log.error("NYC Surv Reject File Upload : No Data Error Found.");
				
				for (Map<String, Object> fileDataRow :fileDataList ) {
					UploadNYCSurvRejectVO nycSurvRejectVO = new UploadNYCSurvRejectVO();
					
					nycSurvRejectVO.setSTATENO(fileDataRow.get("STATENO").toString().trim().toUpperCase());
					nycSurvRejectVO.setNYCNUMBER(setValidateRowVar(fileDataRow.get("NYCNUMBER")));
					nycSurvRejectVO.setREJECT_DOCUMENT_ID(Integer.parseInt(fileDataRow.get("DOCUMENT_ID").toString()));
					nycSurvRejectVO.setDISPOSITION_CODE(fileDataRow.get("STATUSDISPOSITION").toString().trim().split("-")[0]);
					nycSurvRejectVO.setSTATUSNOTES(setValidateRowVar(fileDataRow.get("STATUSNOTES")));
					nycSurvRejectVO.setSTATUSNONCASE(setValidateRowVar(fileDataRow.get("STATUSNONCASE")));
					nycSurvRejectVO.setSTATUS_UPDATE_DATE(fileDataRow.get("UPDTE").toString().trim());
					nycSurvRejectVO.setSTATENO2(setValidateRowVar(fileDataRow.get("STATENO2")));
					nycSurvRejectVO.setUPLOAD_FILENAME(uploadFileName);
					nycSurvRejectVO.setUPLOAD_UID(userObject.getUserId());
				
					nycSurvRejectList.add(nycSurvRejectVO);
				}
				
				log.error("NYS Surv Reject File Upload, Total Records: {}", nycSurvRejectList.size());
				
				fileUploadTransactionHelper.uploadNYCSurvRejectData(nycSurvRejectList);				
			}
		}catch(DataAccessException dataAccessException){
			isError = true;
			transactionMessage = "Failed to read the file, DataAccessException, please contact helpline.";
			log.error("Failed to read the file, DataAccessException : {}",dataAccessException.getMessage());
			dataAccessException.printStackTrace();
		}catch(Exception exception){
			isError = true;
			transactionMessage = "Failed to read the file, please contact helpline.";
			log.error("Failed to read the file.   {}",exception.getMessage());
			exception.printStackTrace();
		}finally{
			try {
				workbook.close();
			} catch (IOException e) {
				log.error("IO EXCEPTION while closing the workbook   :  {}",e.getMessage());
			}catch (Exception e) {
				log.error("EXCEPTION while closing the workbook   :  {}",e.getMessage());
			}
		}
		
		if(isError){
			commonExceptionHandler.inspectDBTransacionResult(-1, transactionMessage, bindingResult);
		}else{
			commonExceptionHandler.inspectDBTransacionResult(0, "Successfully uploaded the NYC Surv Reject Data.", bindingResult);
		}
		
		return resultMap;
				
	}


	public int getNYCSurvRejectDocumentID() {
		
		Map<Object,Object> map = new HashMap<>();
		int newDocId=0;
		
		map.put("p_document_type","NYC_REJECT");
		map.put("p_new_doc_id", "");
		map.put("p_parent_doc_id", "");
		
		try{			
			commonDocumentService.getNewDocId(map);
			newDocId=(Integer)map.get("p_new_doc_id");
		}catch (DataAccessException e) {
			throw new DataRetrievalFailureException("Failed to create a new NYC_REJECT document for "+newDocId+" :  "+e.getMessage());
		}
		
		return newDocId;
	}	
	
	private Map<Object, Object>  validateNYCSurvRejectData(List<Map<String, Object>>  fileDataList) {
		
		int counter = 0;
		String STATENOString;
		StringBuilder errorMessage;
		Map<Object, Object> dataErrorMapList = new HashMap<>();
		
		for (Map<String, Object> dataRow : fileDataList) {
			
			errorMessage = new StringBuilder();
			counter++;
			
			if ( (dataRow.get("STATENO") == null || CommonUtil.isNullOrEmpty(dataRow.get("STATENO").toString()) ) ) {
				STATENOString = "Row " + counter;
				errorMessage.append("STATENO is a required field.<br/>");
			} else {
				STATENOString = dataRow.get("STATENO").toString();
			}

			errorMessage = validateVar(dataRow, "DOCUMENT_ID", "INT", errorMessage);
			errorMessage = validateVar(dataRow, "STATUSDISPOSITION", "STRING", errorMessage);
			
			if (dataRow.get("STATUSDISPOSITION") != null && CommonUtil.isNotNullAndEmpty( dataRow.get("STATUSDISPOSITION").toString()) ) {
				String dispCode = dataRow.get("STATUSDISPOSITION").toString().trim().split("-")[0];
				if ( !(dispCode.toString().equals("1") || dispCode.toString().equals("2")  || dispCode.toString().equals("3") 
						|| dispCode.toString().equals("4") || dispCode.toString().equals("5") || dispCode.toString().equals("6")  || dispCode.toString().equals("7") 
						|| dispCode.toString().equals("8") || dispCode.toString().equals("9")) ) {
					errorMessage.append( "STATUSDISPOSITION should start from any one of these values :  1,2,3,4,5,6,7,8 or 9.<br/>");
				}
			}
			
			errorMessage = validateVar(dataRow, "UPDTE", "DATE_REQUIRED", errorMessage);
			errorMessage = validateVar(dataRow, "STATENO", "STRING10", errorMessage);
			errorMessage = validateVar(dataRow, "NYCNUMBER", "STRING10", errorMessage);
			errorMessage = validateVar(dataRow, "STATENO2", "STRING10", errorMessage);

			if (CommonUtil.isNotNullAndEmpty(errorMessage.toString())) {
				dataErrorMapList.put(STATENOString, errorMessage);
			}
		}	
		
		return dataErrorMapList;
	}

	public void uploadNYCSurvRejectData(List<UploadNYCSurvRejectVO> nycSurvRejectList) {
		
		for (UploadNYCSurvRejectVO nycSurvRejectRow : nycSurvRejectList) {
			nycSurvRejectRow.setDOCUMENT_ID(getNYCSurvRejectDocumentID());
			fileUploadDAO.insertNYCSurvRejectDataRow(nycSurvRejectRow);
		}
	}
	
	private Map<Object, Object>  validateClusterMolecularData(List<Map<String, Object>>  fileDataList) {
		
		int counter = 0;
		String STATENOString;
		StringBuilder errorMessage;
		Map<Object, Object> dataErrorMapList = new HashMap<>();
		String currentOfUploadStaterno;
		boolean isNyehmsStateNoValid;
		boolean isIdentificationCodeValid;
		String identificationCode;
		
		for (Map<String, Object> dataRow : fileDataList) {
			
			errorMessage = new StringBuilder();
			isNyehmsStateNoValid = false;
			isIdentificationCodeValid = false;
			identificationCode = "";
			counter++;
			
			if ( (dataRow.get("NYEHMS_STATENO") == null || CommonUtil.isNullOrEmpty(dataRow.get("NYEHMS_STATENO").toString()) ) ) {
				STATENOString = "Row " + counter;
				errorMessage.append("NYEHMS_STATENO is a required field.<br/>");
			} else {
				STATENOString = dataRow.get("NYEHMS_STATENO").toString();
				String uploadedstateno = (String) dataRow.get("NYEHMS_STATENO").toString();
				currentOfUploadStaterno = commonService.getCurrentActiveStateNo(uploadedstateno);
				if (CommonUtil.isNotNullAndEmpty(currentOfUploadStaterno) && !currentOfUploadStaterno.equals("-1")) {
					isNyehmsStateNoValid = true;
					dataRow.put("CURRENT_NYEHMS_STATENO", currentOfUploadStaterno);
				} /*else {
					errorMessage.append("NYEHMS_STATENO is not a valid STATENO.<br/>");
				}*/
			}
			
			if ( (dataRow.get("IDENTIFICATION_METHOD_CODE") == null || CommonUtil.isNullOrEmpty(dataRow.get("IDENTIFICATION_METHOD_CODE").toString()) ) ) {
				errorMessage.append("IDENTIFICATION_METHOD_CODE is a required field.<br/>");
			} else if (CommonUtil.isNotNullAndEmpty(dataRow.get("IDENTIFICATION_METHOD_CODE").toString()) 
					&& !(dataRow.get("IDENTIFICATION_METHOD_CODE").toString().trim().equals("1") || dataRow.get("IDENTIFICATION_METHOD_CODE").toString().trim().equals("2"))){
				errorMessage.append("IDENTIFICATION_METHOD_CODE should be either 1 or 2 only.<br/>");
			}	else {
				isIdentificationCodeValid = true;
				identificationCode = dataRow.get("IDENTIFICATION_METHOD_CODE").toString().trim();
			}
			
			if ( (dataRow.get("DOCUMENT_ID") == null || CommonUtil.isNullOrEmpty(dataRow.get("DOCUMENT_ID").toString()) ) || !CommonUtil.isNumeric(dataRow.get("DOCUMENT_ID").toString()) ) {
				errorMessage.append( " DOCUMENT_ID is a required field  and can only be a numeric value.<br/>");
			} else if (isNyehmsStateNoValid && isIdentificationCodeValid){
							//Check the document id for valid Genotype Document Id associated with current stateno
							//Check the document id for valid Document Id (document_locator only)
							int recCt = clusterDocumentDAO.validateClusterDocumentId(dataRow);
							if (recCt <= 0 && "1".equals(identificationCode)) {
								errorMessage.append( " DOCUMENT_ID is not GENOTYPE if Identication is 'Through analysis/notification' or DOCUMENT_ID is not valid if Identication is 'Through investigation'.<br/>");
							}else if(recCt <= 0) {
								errorMessage.append( " DOCUMENT_ID is not HIV or Cluster Partner document.<br/>");
							}
								
			}
			
			errorMessage = validateVar(dataRow, "IDENTIFICATION_DATE", "DATE_REQUIRED", errorMessage);

			if (CommonUtil.isNotNullAndEmpty(errorMessage.toString())) {
				dataErrorMapList.put(STATENOString, errorMessage);
			}
		}	
		
		return dataErrorMapList;
	}	

}