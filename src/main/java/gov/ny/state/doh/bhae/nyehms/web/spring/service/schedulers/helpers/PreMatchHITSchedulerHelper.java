package gov.ny.state.doh.bhae.nyehms.web.spring.service.schedulers.helpers;

import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Date;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import java.util.stream.Collectors;

import org.jdom2.Document;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.xml.sax.SAXException;

import com.jcraft.jsch.ChannelSftp;

import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.model.Message;
import ca.uhn.hl7v2.parser.EncodingNotSupportedException;
import gov.ny.state.doh.bhae.nyehms.web.spring.constants.CommonConstants;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.HitLoadLogVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.ccd.RawCCDDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.hl7.RawHL7DocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.document.CCDDocumentService;
//import gov.ny.state.doh.bhae.nyehms.web.spring.service.document.CCDDocumentServiceJDOM;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.document.CommonDocumentService;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.document.HIEDTHDocumentService;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.document.HIEDocumentService;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.document.HITCommonDocumentService;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.document.HL7DocumentService;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.scan.RemoteServerUtil;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonUtil;

/**
 * Class to perform most pre-match processing to data from Health Information
 * Exchange (HIE) partners. As of 1/10/2017 the only one is Heatlthix from Long
 * Island. The three types of documents received are
 * <ul>
 * <li>Continuity of Care Document (CCD)</li>
 * <li>HL7 Version 2.5 message (HL7)</li>
 * <li>Healthix non-standard "Expiration" file (HIE_DEATH)</li>
 * </ul>
 * 
 * @author cdf02
 *
 */
@Component
public class PreMatchHITSchedulerHelper {

	private static Logger log = LoggerFactory.getLogger(PreMatchHITSchedulerHelper.class);

	@Autowired
	private CommonDocumentService commonDocumentService;
	@Autowired
	private CCDDocumentService ccdDocumentService;
	@Autowired
	private HIEDTHDocumentService hiedthDocumentService;

	@Autowired
	private HIEDocumentService hieDocumentService;
	@Autowired
	private HL7DocumentService hl7DocumentService;
	@Autowired
	HITCommonDocumentService hitCommonDocumentService;
	@Autowired
	RemoteServerUtil remoteServer;

	public PreMatchHITSchedulerHelper() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Takes the new rows obtained from UPHN_TRANX_IN; determines the max and min
	 * UPHN_TRANX_IN_ID values retrieved and the total row count of the ResultSet,
	 * then for each row calls the method that extracts the message in
	 * PAYLOADBINARYCONTENT, parses the message, and stores the result into the
	 * appropriate RAW_XXX_DOC table.
	 * 
	 * @param rs
	 *            {@link java.sql.ResultSet} of rows from UPHN_TRANX_IN of type
	 *            "recordType".
	 * @param recordType
	 *            As of 11/16/2016, one of "CCD" or "HL7" or "EXP"
	 * @param rowFetchLimit
	 *            The maximum rows that we want in the ResultSet
	 * @param theNewLoadLogVO
	 *            The VO for the new row to be inserted into HIT_LOAD_LOG
	 * @param schedulerStatusObject
	 *            The accumulator of processing status messages. atomic action in
	 *            the Pre-Match.
	 */
	public void extractHitPayloadsFromRsToRaw(ResultSet rs, String recordType, int rowFetchLimit,
			HitLoadLogVO theNewLoadLogVO, List<JSONObject> schedulerStatusObject, boolean individualLabResultInserts) {

		JSONObject statusObject = new JSONObject();
		String transactionStatus = CommonConstants.TRANSACTION_STATUS_SUCCESS;
		String transactionMessage = "";

		try {

			List<Map<String, String>> raceCodeList = hitCommonDocumentService.getRaceCodes();
			Map<String, String> raceMap = new HashMap<String, String>();
			Map<String, String> ethnicityMap = new HashMap<String, String>();
			for (Map<String, String> raceCode : raceCodeList) {
				String _stdRaceValue = raceCode.get("RACE");
				String _stdEthnicityValue = raceCode.get("ETHNICITY");
				if (CommonUtil.isNotNullAndEmpty(_stdRaceValue)) {
					raceMap.put(raceCode.get("RACE_VALUE"), _stdRaceValue);
				}
				if (CommonUtil.isNotNullAndEmpty(_stdEthnicityValue)) {
					ethnicityMap.put(raceCode.get("RACE_VALUE"), _stdEthnicityValue);
				}
			}

			ccdDocumentService.setRaceMap(raceMap);
			ccdDocumentService.setEthnicityMap(ethnicityMap);
			hl7DocumentService.setRaceMap(raceMap);
			hl7DocumentService.setEthnicityMap(ethnicityMap);

			int rowCount = 0, successCount = 0, failureCount = 0;
			
			//writing logs to db for inserting hl7 process
			if(rs.isBeforeFirst()) hitCommonDocumentService.writeLogToDB(recordType,"INSERT_PROCESS","STARTED");
			
			while (rs.next()) {

				// log.error("Before Processing the document
				// "+Runtime.getRuntime().freeMemory());

				try {
					rowCount++;
					int payloadSize = rs.getInt("PAYLOADSIZE");
					int uphnId = rs.getInt("UPHN_TRANX_IN_ID");
					log.debug("Row count {} in ResultSet for UPHN_TRANX_IN_ID {} of size {}",rowCount,uphnId,payloadSize);
					// Get log counts from the first row; set values in the new
					// log row
					// The query was cleverly constructed to have the columns
					// minUtiiRetrieved, maxUtiiRetrieved and rowRetrievedCount
					// in every row. They are unnecessary and ignored in every
					// row
					// but the first
					if (rowCount == 1) {
						theNewLoadLogVO.setMinUtiiRetrieved(rs.getInt("minUtiiRetrieved"));
						theNewLoadLogVO.setMaxUtiiRetrieved(rs.getInt("maxUtiiRetrieved"));
						int noOfRowsRetrieved = rs.getInt("rowRetrievedCount");
						log.error("\n#### Number of rows retrieved ####  {}", noOfRowsRetrieved);
						theNewLoadLogVO.setNoOfRetrievedDocs(noOfRowsRetrieved);
						if (noOfRowsRetrieved == rowFetchLimit) {
							theNewLoadLogVO.setMaxUtiiChecked(rs.getInt("maxUtiiRetrieved"));
						}
						log.error("\n#### Before the first update #### {}", theNewLoadLogVO);
						hitCommonDocumentService.updateHitLoadLogAfterOneRow(theNewLoadLogVO);
					}
					// process each ResultSet row
					boolean success = true;
					success = storeResultSetPayloadToRawHieTables(rs, recordType, individualLabResultInserts);
					if (success) {
						successCount++;
					} else {
						failureCount++;
					}
				} catch (Exception e) {
					transactionStatus = CommonConstants.TRANSACTION_STATUS_FAILURE;
					transactionMessage = e.getMessage();
					log.error("Exception message : {}", CommonUtil.getStackTrace(e).substring(0, 200));
				}
			}

			if (rowCount == 0) {
				transactionMessage = "Zero rows retrieved.";
			} else {
				 hitCommonDocumentService.writeLogToDB(recordType,"INSERT_PROCESS","ENDED");
				//now delete the hl7 duplicate insurance
				//remove duplicates row in hl7 insurance table after insertion to maintain records as distinct.
				//log.error("Finished writing RAW records inside extractHitPayloadsFromRsToRaw()");
				if(successCount != 0 && recordType.equals("HL7")) {
					
				    hitCommonDocumentService.writeLogToDB(recordType,"DELETE_PROCESS","STARTED");
					
					hl7DocumentService.deleteDuplicatesRowsInRawHL7Insuarance();
				
					hitCommonDocumentService.writeLogToDB(recordType, "DELETE_PROCESS","ENDED");
				}
					
				
				theNewLoadLogVO.setNoOfRetrievedDocs(rowCount);
				theNewLoadLogVO.setNoOfSuccessfulDocs(successCount);
				theNewLoadLogVO.setNoOfFailedDocs(failureCount);
				log.error("\n#### Before the last update #### {}", theNewLoadLogVO);
				hitCommonDocumentService.updateHitLoadLogAfterAllRows(theNewLoadLogVO);
				transactionMessage = rowCount + " " + recordType + " rows retrieved; " + successCount
						+ " processed successfully, " + failureCount + " not processed successfully.";
			}
		} catch (Exception e) {
			transactionStatus = CommonConstants.TRANSACTION_STATUS_FAILURE;
			transactionMessage = e.getMessage();
			log.error("Exception message : {}", CommonUtil.getStackTrace(e).substring(0, 200));
		}
		statusObject.put(CommonConstants.STATUS_ACTION_NAME, recordType + " rows from UPHN_TRANX_IN to RAW table(s).");
		statusObject.put(CommonConstants.TRANSACTION_STATUS, transactionStatus);
		statusObject.put(CommonConstants.TRANSACTION_MESSAGE, transactionMessage);
		schedulerStatusObject.add(statusObject);
	}

	
	public void extractCCDFilesFromScchoit(String recordType, int rowFetchLimit, HitLoadLogVO theNewLoadLogVO,
			List<JSONObject> schedulerStatusObject, boolean individualLabResultInserts) {

		JSONObject statusObject = new JSONObject();
		String transactionStatus = CommonConstants.TRANSACTION_STATUS_SUCCESS;
		String transactionMessage = "";
		String hieName = "Healthix, Inc";
		ChannelSftp obj_SFTPChannel = null;
		Integer uphnId = null;

		InputStream inputStream = null;
		Document ccdDocument = null;
		//final String CURR_DIR = "/H:/BHAE/HAEGroup/Data/CCD";
		final String SFTP_WORKING_DIR = "/H:/BHAE/HAEGroup/Data/CCD/";
		final String SFTP_ARCHIVE_DIR = "/H:/BHAE/HAEGroup/Data/CCD/Archived/";
		try {

			List<Map<String, String>> raceCodeList = hitCommonDocumentService.getRaceCodes();
			Map<String, String> raceMap = new HashMap<String, String>();
			Map<String, String> ethnicityMap = new HashMap<String, String>();
			for (Map<String, String> raceCode : raceCodeList) {
				String _stdRaceValue = raceCode.get("RACE");
				String _stdEthnicityValue = raceCode.get("ETHNICITY");
				if (CommonUtil.isNotNullAndEmpty(_stdRaceValue)) {
					raceMap.put(raceCode.get("RACE_VALUE"), _stdRaceValue);
				}
				if (CommonUtil.isNotNullAndEmpty(_stdEthnicityValue)) {
					ethnicityMap.put(raceCode.get("RACE_VALUE"), _stdEthnicityValue);
				}
			}

			ccdDocumentService.setRaceMap(raceMap);
			ccdDocumentService.setEthnicityMap(ethnicityMap);
			hl7DocumentService.setRaceMap(raceMap);
			hl7DocumentService.setEthnicityMap(ethnicityMap);

			int rowCount = 0, successCount = 0, failureCount = 0;

			obj_SFTPChannel = remoteServer.getRemoteServerSession();

			if (obj_SFTPChannel == null) {
				throw new DataRetrievalFailureException("Remote server(Scchoit31) connection not established.");
			}

			obj_SFTPChannel.pwd();

			//obj_SFTPChannel.cd(CURR_DIR);

			@SuppressWarnings("unchecked")
			List<ChannelSftp.LsEntry> xmlfilesList = new ArrayList<ChannelSftp.LsEntry>(obj_SFTPChannel.ls(SFTP_WORKING_DIR))  ;
			
			//removes if any directory is in the list 
			xmlfilesList = xmlfilesList.stream().filter(entry -> !entry.getAttrs().isDir())
					.collect(Collectors.toList());

			obj_SFTPChannel.cd(SFTP_WORKING_DIR);

			for (Object entry : xmlfilesList) {

				ChannelSftp.LsEntry fileLSEntry = (ChannelSftp.LsEntry) entry;

				java.sql.Date dateCreated = new Date(fileLSEntry.getAttrs().getMTime() * 1000L);

				log.error("File created date {}", dateCreated);
				
				String fileName = fileLSEntry.getFilename();
				String sourcePath = SFTP_WORKING_DIR + fileName;
				String archivePath = SFTP_ARCHIVE_DIR + fileName;
				int fileSize = (int) fileLSEntry.getAttrs().getSize();
				inputStream = obj_SFTPChannel.get(fileName);

				SAXBuilder sb = new SAXBuilder();

				ccdDocument = sb.build(inputStream);

				XMLOutputter outputter = new XMLOutputter(Format.getPrettyFormat());

				String ccdDoccumentString = outputter.outputString(ccdDocument);
				log.error("File name {}", fileLSEntry.getFilename());

				Map<String, Object> uphnData = new HashMap<String, Object>();
				uphnData.put("p_fileName", fileName);
				uphnData.put("p_dateCreated", dateCreated);
				uphnData.put("p_fileSize", fileSize);
				uphnData.put("p_hieName", hieName);

				ccdDocumentService.addUphnRecord(uphnData);
				BigDecimal _value = (BigDecimal) uphnData.get("uphnId");
				uphnId = _value.intValue();

				if (uphnId == null) {
					throw new DataRetrievalFailureException("Failed to create uphn id");
				}

				log.error("UphnId {}", uphnId);

				try {
					rowCount++;

					if (rowCount == 1) {
						int noOfRowsRetrieved = xmlfilesList.size();
						log.error("\n#### Number of rows retrieved ####  {}", noOfRowsRetrieved);
						theNewLoadLogVO.setNoOfRetrievedDocs(noOfRowsRetrieved);

						log.error("\n#### Before the first update #### {}", theNewLoadLogVO);
						hitCommonDocumentService.updateHitLoadLogAfterOneRow(theNewLoadLogVO);
					}
					// process each ResultSet row
					boolean success = true;

					log.error("inserting CCD Doc data");
					System.out.println(sourcePath);
					success = ccdDocumentService.processParsedHITdata(uphnData, recordType, ccdDoccumentString,
							individualLabResultInserts, uphnId);

					log.error("inserted the CCD Doc data");
					// obj_SFTPChannel.rm(fileName);
					obj_SFTPChannel.rename(sourcePath, archivePath);// moving the file to archive
					log.error("Removed the CCD file from the directory");

					if (success) {
						successCount++;
					} else {
						failureCount++;
					}
					
					
				} catch (Exception exception) {
					log.error("Exception message : {}", CommonUtil.getStackTrace(exception).substring(0, 200));
					transactionStatus = CommonConstants.TRANSACTION_STATUS_FAILURE;
					transactionMessage = exception.getMessage();
				} finally {
					if (inputStream != null) {
						inputStream.close();
					}
					if (ccdDocument != null) {
						ccdDocument.removeContent();
					}
				}
				
				if(rowCount >= rowFetchLimit) {//processing 500 records daily
					System.out.println("500 records processed sucessfully");
					break;
				}

			}

			log.error("Number of files fetched for processing- ", xmlfilesList == null ? 0 : xmlfilesList.size());

			if (rowCount == 0) {
				transactionMessage = "Zero rows retrieved.";
			} else {
				hitCommonDocumentService.writeLogToDB(recordType, "INSERT_PROCESS", "ENDED");

				theNewLoadLogVO.setNoOfRetrievedDocs(rowCount);
				theNewLoadLogVO.setNoOfSuccessfulDocs(successCount);
				theNewLoadLogVO.setNoOfFailedDocs(failureCount);
				log.error("\n#### Before the last update #### {}", theNewLoadLogVO);
				hitCommonDocumentService.updateHitLoadLogAfterAllRows(theNewLoadLogVO);
				transactionMessage = rowCount + " " + recordType + " rows retrieved; " + successCount
						+ " processed successfully, " + failureCount + " not processed successfully.";
			}

		} catch (Exception e) {

			transactionStatus = CommonConstants.TRANSACTION_STATUS_FAILURE;
			transactionMessage = e.getMessage();
			log.error("Exception message : {}", CommonUtil.getStackTrace(e).substring(0, 200));

		}
		statusObject.put(CommonConstants.STATUS_ACTION_NAME, recordType + " rows from UPHN_TRANX_IN to RAW table(s).");
		statusObject.put(CommonConstants.TRANSACTION_STATUS, transactionStatus);
		statusObject.put(CommonConstants.TRANSACTION_MESSAGE, transactionMessage);
		schedulerStatusObject.add(statusObject);
	}
	
	@Transactional
	public boolean insertParsedHitData(String decryptedPayloadString, Integer uphnId, String processingStage, String hieName,
			String fileName, Date dateCreated, String recordType, boolean individualLabResultInserts,
			Integer payLoadSize) throws SAXException, HL7Exception, Exception {
		 int newDocId;
		 
		 if(recordType.equals("CCD")) {
			 	processingStage = "parse CCD";
				Map<Object, Object> inputOutputParameters = new HashMap<Object, Object>();
				inputOutputParameters.put("p_document_type", "CCD");
				inputOutputParameters.put("p_received_date", dateCreated);
				
				commonDocumentService.getNewDocId(inputOutputParameters);
				
				 newDocId = (Integer) inputOutputParameters.get("p_new_doc_id");
				 RawCCDDocVO theCCDVO = null;
				 Document ccdDocument = ccdDocumentService.convertStringToDocument(decryptedPayloadString);
				 if(ccdDocument == null) {
					 return false;
				 }else{
						processingStage = "extract CCDVO";
						theCCDVO = ccdDocumentService.getRawCCDDocVOFromDocument(ccdDocument, uphnId, newDocId, null);
						theCCDVO.setDocumentId(newDocId);
						theCCDVO.setHieName(hieName);
						theCCDVO.setPayloadName(fileName);
						processingStage = "write CCDVO";
						// ccdLoadSchedulerHelper.writeRawCCDData(theCCDVO, individualLabResultInserts);
						// Code Added on 19-APR-18 for roll-backing the transactions
						ccdDocumentService.writeRawCCDData(theCCDVO, individualLabResultInserts);
				}
		 }else if(recordType.equals("HIE_DTH")){
			 hiedthDocumentService.storeRawHIEDTHDocVOFromDocument(decryptedPayloadString, uphnId, hieName, dateCreated);
		 }else if(recordType.equals("HL7"))  {
				processingStage = "parse HL7";

				Map<Object, Object> inputOutputParameters = new HashMap<Object, Object>();
				inputOutputParameters.put("p_document_type", "HL7");
				inputOutputParameters.put("p_received_date", dateCreated);
				commonDocumentService.getNewDocId(inputOutputParameters);

				newDocId = (Integer) inputOutputParameters.get("p_new_doc_id");
				Message mess = null;

				try {
					mess = hl7DocumentService.getHL7MessageFromString(uphnId, decryptedPayloadString);

				} catch (EncodingNotSupportedException e) {
					log.error("Encoding not supported occured and calling getUnbreakHL7Message() to fix the issue. ");

					String modifedMessage = null;

					if (CommonUtil.isNotNullAndEmpty(decryptedPayloadString)) {

						modifedMessage = getUnbreakHL7Message(decryptedPayloadString);

						mess = hl7DocumentService.getHL7MessageFromString(uphnId, modifedMessage);
					}

				}
				if (mess == null) {
					return false;
				} else {
					processingStage = "extract HL7VO";
					RawHL7DocVO theRawHL7DocVO = hl7DocumentService.getRawHL7DocVOFromMessage(decryptedPayloadString, mess,
							uphnId);
					if (theRawHL7DocVO != null) {
						processingStage = "write HL7VO";
						theRawHL7DocVO.setDocumentId(newDocId);
						theRawHL7DocVO.setUphnId(uphnId);
						theRawHL7DocVO.setCurrentProcessStatus(1);
						theRawHL7DocVO.setUphnPayloadSize(payLoadSize);
						theRawHL7DocVO.setUphnReceivedTime(dateCreated);
						theRawHL7DocVO.setHieName(hieName);
						hl7DocumentService.writeRawHL7Doc(theRawHL7DocVO);
					} else {
						return false;
					}
				}
			}
		return true;
	}
	
	public void extractHitPayloadsFromHitErrorToRaw(ResultSet rs, String recordType,
			List<JSONObject> schedulerStatusObject, boolean individualLabResultInserts) {

		JSONObject statusObject = new JSONObject();
		String transactionStatus = CommonConstants.TRANSACTION_STATUS_SUCCESS;
		String transactionMessage = "";

		try {

			List<Map<String, String>> raceCodeList = hitCommonDocumentService.getRaceCodes();
			Map<String, String> raceMap = new HashMap<String, String>();
			Map<String, String> ethnicityMap = new HashMap<String, String>();
			for (Map<String, String> raceCode : raceCodeList) {
				String _stdRaceValue = raceCode.get("RACE");
				String _stdEthnicityValue = raceCode.get("ETHNICITY");
				if (CommonUtil.isNotNullAndEmpty(_stdRaceValue)) {
					raceMap.put(raceCode.get("RACE_VALUE"), _stdRaceValue);
				}
				if (CommonUtil.isNotNullAndEmpty(_stdEthnicityValue)) {
					ethnicityMap.put(raceCode.get("RACE_VALUE"), _stdEthnicityValue);
				}				
			}

			ccdDocumentService.setRaceMap(raceMap);
			ccdDocumentService.setEthnicityMap(ethnicityMap);
			hl7DocumentService.setRaceMap(raceMap);
			hl7DocumentService.setEthnicityMap(ethnicityMap);

			int rowCount = 0, successCount = 0, failureCount = 0;
			while (rs.next()) {
				try {
					rowCount++;
					int payloadSize = rs.getInt("PAYLOADSIZE");
					int uphnId = rs.getInt("UPHN_TRANX_IN_ID");
					boolean success = true;
					hitCommonDocumentService.updateHITErrorRepriseFlagStatus(uphnId, "P");
					success = storeResultSetPayloadToRawHieTables(rs, recordType, individualLabResultInserts);
					if (success) {
						successCount++;
						// hitCommonDocumentService.updateHITErrorRepriseFlagStatus(uphnId, "P");
					} else {
						failureCount++;
						// hitCommonDocumentService.updateHITErrorRepriseFlagStatus(uphnId, "P");
					}
				} catch (Exception e) {
					transactionStatus = CommonConstants.TRANSACTION_STATUS_FAILURE;
					transactionMessage = e.getMessage();
					log.error("Exception message : {}", CommonUtil.getStackTrace(e).substring(0, 200));
				}
			}

			if (rowCount == 0) {
				transactionMessage = "Zero rows retrieved from HIT_ERROR to RAW";
			} else {
				transactionMessage = rowCount + " " + recordType + " rows retrieved from HIT_ERROR " + successCount
						+ " processed successfully, " + failureCount + " not processed successfully.";
			}
		} catch (Exception e) {
			transactionStatus = CommonConstants.TRANSACTION_STATUS_FAILURE;
			transactionMessage = e.getMessage();
			log.error("Exception message : {}", CommonUtil.getStackTrace(e).substring(0, 200));
		}
		statusObject.put(CommonConstants.STATUS_ACTION_NAME, recordType + " rows from HIT_ERROR to RAW table(s).");
		statusObject.put(CommonConstants.TRANSACTION_STATUS, transactionStatus);
		statusObject.put(CommonConstants.TRANSACTION_MESSAGE, transactionMessage);
		schedulerStatusObject.add(statusObject);
	}

	/**
	 * Reads fields from the rs parameter; decrypts and decodes the
	 * PAYLOADBINARYCONTENT field, which is supposed to contain one of the two
	 * record types (see below); parses the document; stores those into the
	 * RAW_CCD_XXX tables in the database; adds a row to HIT_LOAD_LOG
	 * 
	 * @param rs
	 *            A {@link java.sql.ResultSet} with the cursor pointing to the row
	 *            to process
	 * @param recordType
	 *            one of "CCD", "HL7"
	 * @return boolean Whether or not the row was processed successfully.
	 */

	public boolean storeResultSetPayloadToRawHieTables(ResultSet rs, String recordType,
			boolean individualLabResultInserts) {

		boolean isSuccessful = true;
		String decryptedPayloadString = "";
		int uphnId = 0;
		String processingStage = "decrypt";
		String hieName = null;
		String payloadName = null;
		Date myDate = null;
		int payLoadSize = 0;
		try {
			uphnId = rs.getInt("UPHN_TRANX_IN_ID");
			payloadName = rs.getString("PAYLOADNAME");
			Blob theBlob = rs.getBlob("PAYLOADBINARYCONTENT");
			hieName = rs.getString("ORGNAME");
			myDate = rs.getDate("RECEIVEDTIME");
			payLoadSize = rs.getInt("PAYLOADSIZE");
			String nameCheck = payloadName.substring(0, 9);
			
			if (nameCheck.equals("cleartext")) {
				// This if condition is used for the files which are created for
				// testing Purpose
				decryptedPayloadString = new String(theBlob.getBytes(1l, (int) theBlob.length()));
			} else {
					
					if(recordType.equals("CCD")) {
						decryptedPayloadString = hieDocumentService.convertBlobtoEncryptedEncodedString(theBlob);
					}else {
						decryptedPayloadString = hieDocumentService
								.decryptDecodeCCDBinaryString(hieDocumentService.convertBlobtoEncryptedEncodedString(theBlob));
					}

				
				
			}
		} catch (Throwable t) {
			log.error("Some error while reading from the ResultSet row or decrypting the PAYLOADBINARYCONTENT:\n {}", CommonUtil.getStackTrace(t).substring(0, 200));
			isSuccessful = false;
		}

		if (isSuccessful) {
			
				Map<String,Object> uphnData = new HashMap<>();
				uphnData.put("p_hieName", hieName);
				uphnData.put("p_dateCreated", myDate);
				uphnData.put("p_fileName", payloadName);
				uphnData.put("p_fileSize", payLoadSize);
				uphnData.put("p_processingStage", processingStage);
				
			isSuccessful = ccdDocumentService.processParsedHITdata(uphnData,recordType, decryptedPayloadString, individualLabResultInserts,uphnId);
			
			}

		return isSuccessful;
	}
	
	/**
	 * Fixing line breaks in HL7 messages which is causing encoding not supported error
	 * @param  messageText - passing actual message
	 * @return new modified hl7 message without broken hl7 message 
	 */
	private String getUnbreakHL7Message(String messageText) {

		String arr[] = messageText.split("\r");

		StringBuilder modifiedMessage = new StringBuilder();

		for (int i = 0; i < arr.length; i++) {

			if ((arr[i].substring(0, 4)).matches("^[a-zA-Z0-9]{3}\\|")) {

				modifiedMessage.append(arr[i]);

			} else {
				modifiedMessage.append(" " + arr[i]);
			}

			if (i != arr.length - 1 && (arr[i + 1].substring(0, 4)).matches("^[a-zA-Z0-9]{3}\\|")) {
				modifiedMessage.append("\r");
			}

		}
		return modifiedMessage.toString();
	}

	/**
	 * 
	 * @param recordType
	 *            One of "CCD", "EXP", "HL7"
	 * @param schedulerMetaData
	 *            A Map of the contents of the LAB_SCHEDULER_PARAMETERS table
	 * @param schedulerStatusObject
	 *            The collector of log messages for each processing step
	 */
	public void writeRawHitRecordsToStd(String recordType, Map<String, String> schedulerMetaData,
			List<JSONObject> schedulerStatusObject) {
		Map<String, String> statusObject = new LinkedHashMap<String,String>();
		
		if ("CCD".equals(recordType)
				&& "Y".equals(schedulerMetaData.get("REQUIRED_TO_STORE_CCD_RECORDS_IN_STD_LAYER"))) {
			try {

				List<Map<Object, Object>> rawCCDDocAndAddressList = ccdDocumentService.getNewRawCCDList();

				log.error("Size of list is : {}", rawCCDDocAndAddressList.size());
				writeRawCCDRecordsToStdLayer(rawCCDDocAndAddressList, schedulerStatusObject);
			} catch (Exception e) {
				log.error("Exception processing Raw Hit Records To Std layer for CCD: {}",CommonUtil.getStackTrace(e).substring(0, 200));
			}
		}

		// code added on 8/22/2019
		// new implementation of raw_hie_death_doc
		if ("HIE_DTH".equals(recordType)
				&& "Y".equals(schedulerMetaData.get("REQUIRED_TO_STORE_HIE_DTH_RECORDS_IN_STD_LAYER"))) {
			// first will remove all the duplicates before checking for the status 36 or 37
			// else 1;
			// duplicates will check first for those who are already standardized to
			// std_hie_detah_doc
			try {
				// Update the duplicates records to 2 in RAW_HIE_DEATH_DOC where MPIID IN
				// STD_HIE_DEATH_DOC AND HAVE DUPLICATE RECORDS in NEW File that is Received.
				hieDocumentService.updateDuplicatesThatMoveToSTD();

				// Update the duplicates records to 2 in RAW_HIE_DEATH_DOC where MPIID NOT IN
				// STD_HIE_DEATH_DOC AND HAVE DUPLICATE RECORDS in SAME FILE AND ONLY ONE WILL
				// MOVE TO STD
				// checking the records urder by received date
				hieDocumentService.updateDuplicatesReceivedInSameFile();

				// Find and insert the pair of mpi's which are different but alloted to same
				// person having same First name, Last name and same DOB.
				// performing insert before checking any operation like matching the
				// raw_hie_death_doc with std_ccd for non matches or matches.
				hieDocumentService.insertMpisPairInMPICHANGE();

				// Update the current_process_status of 36 for the records which has MPIID in
				// CCD as non matches and 37 for records that do not have CCD in STD_CCD_DOC
				// else 1
				// 36 if Match_code in CCD is in (N,C_N) else 1 (if it got changed from (N,C_N)
				// to (M,C_M))
				hieDocumentService.updateCPSinRAWComparingCCD();

				List<Map<Object, Object>> rawExpDocList = hieDocumentService.getNewRawHIEDTHList();
				writeRawHIEDTHRecordsToStdLayer(rawExpDocList, schedulerStatusObject);
			} catch (Exception e) {
				log.error("Exception processing Raw Hit Records To Std layer for HIE_DTH: {}", CommonUtil.getStackTrace(e).substring(0, 200));
			}

		}

		if ("HL7".equals(recordType)
				&& "Y".equals(schedulerMetaData.get("REQUIRED_TO_STORE_HL7_RECORDS_IN_STD_LAYER"))) {
			try {
				// Code Added on 21st May 2018 for Updating the current process status to 1 for
				// all the hl7 with current_Process_status of 36 who's ccd with MPI got changed
				// from (N, C_N) to (M,C_M) // commented on 23-oct-2018 as all records are moved
				// from raw to STD
				// hl7DocumentService.updateCPSinRAWfromNonMatchToMatchCCD();
				// Updating the current_Process_status to 18 if MPI is NULL in HL7. 37 if Match
				// code in CCD is 'C", 36 if Match_code in CCD is in (N,C_N) 4 if it error'ed
				// out
				// and else 1 //commented on 23-oct-2018 as all records are moved from raw to
				// STD
				// hl7DocumentService.updateCurrentProcessStatusInRaw();

				hl7DocumentService.writeRawHL7RecordsToStdLayer(schedulerStatusObject);

				// Below SERVICE is for Moving Records from RAW to STD whose Current Process
				// Status is 37 With the MPI change and once they are moved to STD current
				// process is UPdated to 41
				////// Date Added : 01-FEB-18//////////commented on 23-oct-2018 as all records
				// are moved from raw to STD
				// hl7DocumentService.writeRawHL7MPIChangeRecordsToStdLayer(schedulerStatusObject);

				// writeRawHL7RecordsToStdLayer(rawHL7DocList,schedulerStatusObject);

				// Added: 8/16/2021 added by: rzp05
				// this method will insert mpi pairs which are not exists in mpi_change table.
				hl7DocumentService.insertMPIPairsCheckingHL7andCCD();

				// Code added to match the HL7's to CCD which were matched to 10 and which
				// matched to non-match to 14 Dt.Added: 23-OCTOBER-2018
				
				// retrieving default or manually updated Current Process Status from
				// LAB_SCHEDULER_PARAMETERS
				// and days which will pick duration to process hl7 cursor from sysdate - {days}
				// to match against CCDs.
				hl7DocumentService.updateDataSTDMatchorNonMatchtoCCD(statusObject, schedulerMetaData);

				// code added to match the remaining HL7's which did not match to CCD and are
				// remaining with current_process_status = 5.
				hl7DocumentService.updateCPSinSTDNotMatchOnParameters();
				
				// code added to update the match_ccd_doc_id from std to raw hl7 doc for status 14
				// and will delete after updating the ccd_doc_id from std table with status 14 records
				hl7DocumentService.updateSTDtoRAWStatusDocIdAndDeleteRecordsinStdHL7DOC();
				
			
				
				
				
			} catch (Exception e) {
				log.error("Exception processing Raw Hit Records To Std layer for HL7: {}", CommonUtil.getStackTrace(e).substring(0, 200));
			}
		}
		// if
		// ("Y".equals(schedulerMetaData.get("REQUIRED_TO_STORE_DIAG_RECORDS_IN_STD_LAYER")))
		// {
		// // CODE FOR MOVED_TO_STD
		// try {
		// List<Map<Object, Object>> rawDiagnosisList =
		// // Code for REVIEWED DIAGNOSIS
		// List<Map<Object, Object>> rawDiagnosisReviewedList =
		// log.error("Exception processing Raw Hit Records To Std layer for
		// DIAGNOSIS : "

	}

	public void writeRawCCDRecordsToStdLayer(List<Map<Object, Object>> rawccddocAddrList,
			List<JSONObject> schedulerStatusObject) {
		JSONObject statusObjectSuccess = new JSONObject();
		JSONObject statusObjectFailure = new JSONObject();
		JSONObject statusObjectZeroCount = new JSONObject();
		JSONObject statusObjectRaceCodeFailure = new JSONObject();
		StringBuilder exceptionMessage = new StringBuilder();
		int failure = 0;
		int success = 0;
		int raceErrorCounter = 0;
		String transactionStatus = CommonConstants.TRANSACTION_STATUS_SUCCESS;
		String transactionMessage = "Successfully stored all the Raw CCD records in the Standard layer";
		List<String> failedToValidateDocuments = new ArrayList<String>();
		int totalDocuments = rawccddocAddrList.size();
		log.error("Writing Raw CCD data to Std table");

		if (totalDocuments == 0) {
			String count = "Size of list retrieved is : " + totalDocuments;
			transactionStatus = CommonConstants.NO_DATA_RETRIEVED;
			transactionMessage = "No CCD's Data Retrieved to move from Raw layer to Standard layer";
			statusObjectZeroCount.put(CommonConstants.TRANSACTION_STATUS, transactionStatus);
			statusObjectZeroCount.put(CommonConstants.TRANSACTION_MESSAGE, transactionMessage);
		} else {
			List<Integer> validDocumentIds = new ArrayList<Integer>();
			log.error("Total count of CCD's to parse in STD are {}", rawccddocAddrList.size());

			Set<String> raceCodeErrors = new HashSet<String>();
			for (Map<Object, Object> stdCCDDocVo : rawccddocAddrList) {
				try {
					BigDecimal documentId = (BigDecimal) stdCCDDocVo.get("DOCUMENT_ID");
					int insertStatus = writeRawCCDRecordDataToStdTable(stdCCDDocVo);
					if (insertStatus == 4) {
						raceErrorCounter++;
						String pidRaceCodeRaw = (String) stdCCDDocVo.get("RACE_CODE_ERROR");
						if (CommonUtil.isNotNullAndEmpty(pidRaceCodeRaw)) {
							pidRaceCodeRaw = pidRaceCodeRaw.toUpperCase();
							raceCodeErrors.add(pidRaceCodeRaw);
						}
					}
					// commonDocumentService.updateRawDocumentStatus(documentId.intValue(),
					// "RAW_CCD_DOC", 5);
				} catch (DataAccessException e) {
					BigDecimal documentId = (BigDecimal) stdCCDDocVo.get("DOCUMENT_ID");
					java.util.Date today = new java.util.Date();
					java.sql.Date timestamp = new java.sql.Date(today.getTime());
					ccdDocumentService.updateRawProcessDate(documentId.intValue(), "RAW_CCD_DOC", timestamp);
					String _exceptionMessage = " Exception Message for DOCUMENT_ID = " + stdCCDDocVo.get("DOCUMENT_ID")
							+ " and UPHN_TRANX_IN_ID = " + stdCCDDocVo.get("UPHN_TRANX_IN_ID") + e.getMessage();
					exceptionMessage.append(_exceptionMessage);
					exceptionMessage.append("<br>");
					exceptionMessage.append("<br>");
					failedToValidateDocuments.add(_exceptionMessage.toString());
					log.error(_exceptionMessage);
				} catch (Exception e) {
					String _exceptionMessage = " Exception while storing the CCD record or updating RAW_CCD_DOC:  "
							+ CommonUtil.getStackTrace(e).substring(0, 200);
					failedToValidateDocuments.add(_exceptionMessage.toString());
					log.error(_exceptionMessage);
				}
			}
			if (raceCodeErrors != null && raceCodeErrors.size() > 0) {
				log.error("SIZE OF ENTRIES TO BE INSERTED : {}", raceCodeErrors.size());
				log.error("LIST OF RACE TO BE ADDED {}", raceCodeErrors.toString());
				hitCommonDocumentService.insertCodedRace(raceCodeErrors);
			}
			failure = failedToValidateDocuments.size();
			success = totalDocuments - failure - raceErrorCounter;
			if (raceErrorCounter > 0) {
				String transactionStatusforRaceFailure = CommonConstants.TRANSACTION_STATUS_FAILURE;
				String transactionRaceMessageFailure = "Number of CCD's failed while storing  in the Standard layer due to Race Map failure are = "
						+ raceErrorCounter + "<br>" + "<br>" + " Update Race/Ethncity value in CODED_HIT_RACE_DESC : "
						+ raceCodeErrors.toString();
				statusObjectRaceCodeFailure.put(CommonConstants.TRANSACTION_STATUS, transactionStatusforRaceFailure);
				statusObjectRaceCodeFailure.put(CommonConstants.TRANSACTION_MESSAGE, transactionRaceMessageFailure);
			}
			if (success > 0) {
				String transactionStatusSuccess = CommonConstants.TRANSACTION_STATUS_SUCCESS;
				String transactionMessageSuccess = "Number of CCD's succeeded while storing  in the Standard layer = "
						+ success;
				statusObjectSuccess.put(CommonConstants.TRANSACTION_STATUS, transactionStatusSuccess);
				statusObjectSuccess.put(CommonConstants.TRANSACTION_MESSAGE, transactionMessageSuccess);
			}

			if (failure > 0) {
				String transactionStatusFailure = CommonConstants.TRANSACTION_STATUS_FAILURE;
				String transactionMessageFailure = "Number of CCD's failed while storing  in the Standard layer = "
						+ failedToValidateDocuments.size() + "<br>" + "<br>" + "ERROR MESSAGE : " + "<br>"
						+ exceptionMessage;
				statusObjectFailure.put(CommonConstants.TRANSACTION_STATUS, transactionStatusFailure);
				statusObjectFailure.put(CommonConstants.TRANSACTION_MESSAGE, transactionMessageFailure);
			}
		}
		log.error(transactionMessage);
		if (totalDocuments == 0) {
			schedulerStatusObject.add(statusObjectZeroCount);
		} else {
			if (success > 0) {
				schedulerStatusObject.add(statusObjectSuccess);
			}
			if (failure > 0) {
				schedulerStatusObject.add(statusObjectFailure);
			}
			if (raceErrorCounter > 0) {
				schedulerStatusObject.add(statusObjectRaceCodeFailure);
			}
		}
	}

	private int writeRawCCDRecordDataToStdTable(Map<Object, Object> stdCCDDocVO) {
		int resultCount = ccdDocumentService.writeRawCCDRecordDataToStdTable(stdCCDDocVO);
		return resultCount;
	}

	public void writeRawHIEDTHRecordsToStdLayer(List<Map<Object, Object>> rawExpDocList,
			List<JSONObject> schedulerStatusObject) {
		String transactionStatus = CommonConstants.TRANSACTION_STATUS_SUCCESS;
		String transactionMessage = "Succesfully stored all the Raw HIE DTH records in the Standard layer";
		List<String> failedToValidateDocuments = new ArrayList<String>();
		int totalDocuments = rawExpDocList.size();
		log.error("Writing Raw HIE_DTH data to Std table {}", totalDocuments);
		if (totalDocuments == 0) {
			log.error("No data available to store in Standard HIE_DTH table.");
			return;
		}
		List<Integer> validDocumentIds = new ArrayList<Integer>();
		JSONObject statusObject = new JSONObject();
		// statusObject.put(CommonConstants.STATUS_ACTION_NAME, "Storing unique
		// Raw CCD data in Standard table.");
		for (Map<Object, Object> stdEXPDocVo : rawExpDocList) {
			try {
				BigDecimal documentId = (BigDecimal) stdEXPDocVo.get("DOCUMENT_ID");
				int insertStatus = writeRawHIEDTHRecordDataToStdTable(stdEXPDocVo);
				commonDocumentService.updateRawDocumentStatus(documentId.intValue(), "RAW_HIE_DEATH_DOC", 5);
				/*
				 * if(insertStatus == 0) { throw new
				 * DataRetrievalFailureException("DataAccessException while storing the CCD record"
				 * ); }
				 */
			} catch (DataAccessException e) {
				// String _exceptionMessage = stdCCDDocVo.getDOCUMENT_ID()+"
				// DataAccessException while storing the CCD record : "
				// +e.getMessage();
				String _exceptionMessage = " DataAccessException while storing the HIE_DTH record :  " + e.getMessage();
				failedToValidateDocuments.add(_exceptionMessage.toString());
				log.error(_exceptionMessage);
			} catch (Exception e) {
				// String _exceptionMessage = stdCCDDocVo.getDOCUMENT_ID()+"
				// Exception while storing the CCD record : " +e.getMessage();
				String _exceptionMessage = " Exception while storing the HIE_DTH record :  " + e.getMessage();
				failedToValidateDocuments.add(_exceptionMessage.toString());
				log.error(_exceptionMessage);
			}
		}
		if (failedToValidateDocuments.size() == totalDocuments) {
			transactionStatus = CommonConstants.TRANSACTION_STATUS_FAILURE;
			transactionMessage = "All the documents were failed while storing all the Raw HIE_DTH records in the Standard layer";
		} else if (failedToValidateDocuments.size() > 0) {
			transactionMessage = "Few documents were failed while storing all the Raw HIE_DTH records in the Standard layer";
		}
		log.error(transactionMessage);
		statusObject.put(CommonConstants.TRANSACTION_STATUS, transactionStatus);
		statusObject.put(CommonConstants.TRANSACTION_MESSAGE, transactionMessage);
		schedulerStatusObject.add(statusObject);
	}

	private int writeRawHIEDTHRecordDataToStdTable(Map<Object, Object> stdCCDDocVO) {

		int resultCount = hiedthDocumentService.writeRawHIEDTHRecordDataToStdTable(stdCCDDocVO);
		if (resultCount != 0) {
			// resultCount =
			// manualQuartzJobService.updateTheRawStdRecordToStandardize(ecrlsLabDocVO.getDOCUMENT_ID());
		}
		return resultCount;
	}

	public void writeRawDiagnosisToStdLayer(List<Map<Object, Object>> rawDiagDataList,
			List<JSONObject> schedulerStatusObject) {
		String transactionStatus = CommonConstants.TRANSACTION_STATUS_SUCCESS;
		String transactionMessage = "Succesfully stored filtered Raw Diagnosis records in the Standard layer";
		List<String> failedToValidateDocuments = new ArrayList<String>();
		int totalDocuments = rawDiagDataList.size();
		log.error("Writing Filtered Raw Diagnosis data to Std table");
		if (totalDocuments == 0) {
			log.error("No data available to store in Standard HIE_DTH table.");
			return;
		}
		List<Integer> validDocumentIds = new ArrayList<Integer>();
		JSONObject statusObject = new JSONObject();
		for (Map<Object, Object> stdDiagnosisVo : rawDiagDataList) {
			try {
				BigDecimal diagnosisId = (BigDecimal) stdDiagnosisVo.get("DIAGNOSIS_ID");
				int insertStatus = writeRawDiagnosisDataToStdTable(stdDiagnosisVo);
				// IF NEEDED TO UPDATE A COLUMN FOR STATUS CHECK
				ccdDocumentService.updateRawDiagnosisMovedToSTDStatus(diagnosisId.intValue(), "RAW_CCD_DIAGNOSIS", 'Y');

			} catch (DataAccessException e) {

				String _exceptionMessage = " DataAccessException while storing the Diagnosis record :  "
						+ e.getMessage();
				failedToValidateDocuments.add(_exceptionMessage.toString());
				log.error(_exceptionMessage);
			} catch (Exception e) {

				String _exceptionMessage = " Exception while storing the Diagnosis record :  " + e.getMessage();
				failedToValidateDocuments.add(_exceptionMessage.toString());
				log.error(_exceptionMessage);
			}
		}
		if (failedToValidateDocuments.size() == totalDocuments) {
			transactionStatus = CommonConstants.TRANSACTION_STATUS_FAILURE;
			transactionMessage = "All the documents were failed while storing all the RAW_CCD_DIAGNOSIS records in the Standard layer";
		} else if (failedToValidateDocuments.size() > 0) {
			transactionMessage = "Few documents were failed while storing all the Raw RAW_CCD_DIAGNOSIS records in the Standard layer";
		}
		log.error(transactionMessage);
		statusObject.put(CommonConstants.TRANSACTION_STATUS, transactionStatus);
		statusObject.put(CommonConstants.TRANSACTION_MESSAGE, transactionMessage);
		schedulerStatusObject.add(statusObject);
	}

	private int writeRawReviewedList(List<Map<Object, Object>> lstrawCCDDiagVO) {
		for (Map<Object, Object> rawReviewedDiagnosisVo : lstrawCCDDiagVO) {

			BigDecimal diagnosisId = (BigDecimal) rawReviewedDiagnosisVo.get("DIAGNOSIS_ID");
			ccdDocumentService.updateRawReviewedStatus(diagnosisId.intValue(), "RAW_CCD_DIAGNOSIS", 'Y');

		}
		return 0;
	}

	private int writeRawDiagnosisDataToStdTable(Map<Object, Object> stdCCDDiagVO) {

		int resultCount = ccdDocumentService.writeRawDiagnosisDataToStdTable(stdCCDDiagVO);
		if (resultCount != 0) {
			// resultCount =
			// manualQuartzJobService.updateTheRawStdRecordToStandardize(ecrlsLabDocVO.getDOCUMENT_ID());
		}
		return resultCount;
	}

	

}