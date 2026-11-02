package gov.ny.state.doh.bhae.nyehms.web.spring.service.document;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import gov.ny.state.doh.bhae.nyehms.web.spring.dao.CommonDAO;
import gov.ny.state.doh.bhae.nyehms.web.spring.dao.document.HITDocumentDAO;
import gov.ny.state.doh.bhae.nyehms.web.spring.jdbc.JpaConnections;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.HitErrorVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.HitLoadLogVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.schedulers.helpers.PreMatchHITSchedulerHelper;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonExceptionHandler;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonUtil;

/**
 * Primarily makes extracts the contents of an HL7 Clinical Care Document and
 * stores it into tables in the database. The primary tool used, once the
 * document is decrypted, is the org.jdom2 package.
 * 
 * @author cdf02
 *
 */
@Service
public class HITCommonDocumentService {

	private static final Logger log = LoggerFactory.getLogger(HITCommonDocumentService.class);

	@Autowired
	HITDocumentDAO hitDocumentDAO;
	@Autowired
	private JpaConnections jpaConnections;
	@Autowired
	PreMatchHITSchedulerHelper preMatchHITSchedulerHelper;
	@Autowired
	private CommonExceptionHandler commonExceptionHandler;
	@Autowired
	private CommonDAO commonDAO;

	public void storeHitError(Throwable e, Integer uphnId, String decryptedPayload, String recordType, String repriseFlag, String comments) {
		log.error("HIT parsing error : {}", e.getMessage());
		HitErrorVO hitErrorVO = new HitErrorVO();
		hitErrorVO.setUphnTRANXINID(uphnId);
		//hitErrorVO.setPayloadCLEARTEXT(decryptedPayload);
		hitErrorVO.setPayloadCLEARTEXT(null);//@rzp05 added removed payload for now
		hitErrorVO.setErrorMESSAGE(e.getMessage() + CommonUtil.getStackTrace(e));
		hitErrorVO.setRecordType(recordType);
		hitErrorVO.setComments(comments);
		hitErrorVO.setRepriseFlag(repriseFlag);								 
		java.util.Date today = new java.util.Date();
		java.sql.Date timestamp = new java.sql.Date(today.getTime());
		hitErrorVO.setCreateDATETIME(timestamp);
		hitDocumentDAO.insertHitError(hitErrorVO);
		// commonExceptionHandler.handleException(e, userObject,sessionId);
	}

	public void insertCodedRace(Set<String> raceCodeErrors) {
		Set<String> newraceCodeErrors = new HashSet<>();
		Map<String, Object> inputParameters = new HashMap<>();
		// New Code was added by MXM69 on 11-July-2018 to avoid multiple entries
		for (String str : raceCodeErrors) {
			if (CommonUtil.isNotNullAndEmpty(str)) {
				str = str.toUpperCase();
			}
			int res = hitDocumentDAO.checkIfRaceExists(str);
			if (res == 0) {
				newraceCodeErrors.add(str);
			}
		}
		if(newraceCodeErrors.size() > 0) {
			inputParameters.put("raceCodeErrorsList", newraceCodeErrors);
			hitDocumentDAO.insertCodedRace(inputParameters);
		}
	}

	public List<Map<String, String>> getRaceCodes() {
		return hitDocumentDAO.getRaceCodes();
	}

	/**
	 * Performs all the Pre-match processing for HIT documents
	 * 
	 * @param schedulerStatusObject
	 *            List of all the status codes and messages from all the
	 *            pre-match processing steps so far
	 * @param recordType
	 *            One of "CCD", "EXP", "HL7"
	 * @param schedulerMetaData
	 *            Map containing all the key/value pairs from
	 *            LAB_SCHEDULER_PARAMETERS.
	 * @param initiator
	 *            Either "QUARTZ" if initiated by the regular scheduled Quartz
	 *            scheduler in spring-config.xml, or the user ID of the user who
	 *            initiated the trigger via the Manual page in
	 *            {@link gov.ny.state.doh.bhae.nyehms.web.spring.controller.schedulers.ManualQuartzJobController#triggerSchedulerJobManually(jakarta.servlet.http.HttpServletRequest, String, String)}
	 */
	public void extractNewPayloadsFromUphnToRaw(List<JSONObject> schedulerStatusObject, String recordType,
			Map<String, String> schedulerMetaData, String initiator) {
		log.error(initiator + " inside " + this.getClass());
		int maxUphnTranxInIdNow = hitDocumentDAO.getMaxUphnTranxInId();
		int maxUphnTranxInIdCheckedFromLog = hitDocumentDAO.getMaxUphnTranxInIdCheckedFromLog(recordType);
		int rowFetchLimit = 10; // default, for EXP only
		boolean individualLabResultInsert = false;
		if ("Y".equals(schedulerMetaData.get("DO_INDIVIDUAL_LAB_RESULT_INSERTS"))) {
			individualLabResultInsert = true;
		}
		HitLoadLogVO theNewHitLoadLogVO = new HitLoadLogVO();

		theNewHitLoadLogVO.setCreateDate(new java.util.Date());
		theNewHitLoadLogVO.setCreateId(initiator);
		theNewHitLoadLogVO.setDocType(recordType);
		theNewHitLoadLogVO.setMinUtiiChecked(maxUphnTranxInIdCheckedFromLog);
		theNewHitLoadLogVO.setMinUtiiRetrieved(0);
		theNewHitLoadLogVO.setMaxUtiiRetrieved(0);
		theNewHitLoadLogVO.setNoOfRetrievedDocs(0);
		theNewHitLoadLogVO.setNoOfSuccessfulDocs(0);
		theNewHitLoadLogVO.setNoOfFailedDocs(0);
		// The following setting of maxUtiiChecked is provisional; if the number
		// of rows retrieved is equal to the rowFetchLimit then it will be
		// reset to the maxUtiiRetrieved (see why?)
		theNewHitLoadLogVO.setMaxUtiiChecked(maxUphnTranxInIdNow);
		log.error("\n#### Before the insert #### {}", theNewHitLoadLogVO);
		
		rowFetchLimit = Integer.parseInt(schedulerMetaData.get("HIT_" + recordType + "_ROWS_FETCH_LIMIT"));
		
		Connection conn = null;
		ResultSet rs = null;
		Statement st = null;
		
		hitDocumentDAO.insertHitLoadLog(theNewHitLoadLogVO);
		log.error("New HIT_LOAD_LOG row inserted with LOG_ID = {}", theNewHitLoadLogVO.getLogID());
		log.error("\n#### After the insert #### {}", theNewHitLoadLogVO);
		
		
		try {
			
			if("HL7".equals(recordType) || "HIE_DTH".equals(recordType)) {
				
					conn = jpaConnections.appConnection();
					String sqlString = getUphnRowsWithJdbc(maxUphnTranxInIdCheckedFromLog, maxUphnTranxInIdNow,
							rowFetchLimit, recordType, theNewHitLoadLogVO);
					st = conn.createStatement();
					rs = st.executeQuery(sqlString);
					preMatchHITSchedulerHelper.extractHitPayloadsFromRsToRaw(rs, recordType, rowFetchLimit,
							theNewHitLoadLogVO, schedulerStatusObject, individualLabResultInsert);
			}else {//ccd records to process
					preMatchHITSchedulerHelper.extractCCDFilesFromScchoit(recordType, rowFetchLimit,
							theNewHitLoadLogVO, schedulerStatusObject, individualLabResultInsert);
			}
			
			
		} catch (Throwable t) {
			log.error(CommonUtil.getStackTrace(t).substring(0, 200));
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
				if (rs != null) {
					rs.close();
				}
				if (st != null) {
					st.close();
				}
			} catch (SQLException sqle) {
				log.error(sqle.getMessage());
				//log.error(CommonUtil.getStackTrace(sqle).substring(0, 200));
			}
		}
		
	}	
		
	public void extractHitErrorFromUphnToRaw(List<JSONObject> schedulerStatusObject, String recordType,
			Map<String, String> schedulerMetaData, String initiator) {
		log.error(initiator + " inside " + this.getClass());
		boolean individualLabResultInsert = false;
		if ("Y".equals(schedulerMetaData.get("DO_INDIVIDUAL_LAB_RESULT_INSERTS"))) {
			individualLabResultInsert = true;
		}
		if ("CCD".equals(recordType) || "HL7".equals(recordType) || "HIE_DTH".equals(recordType)) {
			Connection conn = null;
			ResultSet rs = null;
			Statement st = null;
			try {
				conn = jpaConnections.appConnection();
				String sqlString = getUphnRowsWithFromHitError(recordType);
				st = conn.createStatement();
				rs = st.executeQuery(sqlString);
				System.out.println(rs);
				// NEED TO MAKE ALTERATIONS
				preMatchHITSchedulerHelper.extractHitPayloadsFromHitErrorToRaw(rs, recordType, schedulerStatusObject,
						individualLabResultInsert);

			} catch (Throwable t) {
				log.error(CommonUtil.getStackTrace(t).substring(0, 200));
			} finally {
				try {
					if (conn != null) {
						conn.close();
					}
					if (rs != null) {
						rs.close();
					}
					if (st != null) {
						st.close();
					}
				} catch (SQLException sqle) {
					log.error(CommonUtil.getStackTrace(sqle).substring(0, 200));
				}
			}
		}
	}

	// /**
	// * Finds records that were missed during the initial load from
	// * UPHN_TRANX_IN, for whatever reason,
	// * that we want to retry loading into the RAW_CCD_XXX, RAW_HL7_XXX and
	// * RAW_HIE_DEATH_DOC tables.
	// * @param schedulerStatusObject List of statuses from the various stages
	// * @param recordType One of CCD, HL7, HIE_DTH
	// * @param schedulerMetaData java.util.Map of the contents of
	// LAB_SCHEDULER_PARAMETERS
	// * @param initiator "QUARTZ" if scheduler-initiated, or the user ID of the
	// * person who used the Manual Scheduler to initiate the job
	// */
	// public void extractReprisePayloadsFromUphnToRaw (
	// List<JSONObject> schedulerStatusObject,
	// String recordType,
	// Map<String, String> schedulerMetaData,
	/**
	 * Method to read the UPHN_TRANX_IN table using JDBC so as to get a
	 * ResultSet, presumed to do lazy loading to prevent too much data being
	 * read into memory at once. Currently necessary for CCD, unclear for HL7.
	 * 
	 * @param c
	 *            java.sql.Connection that has been opened for this purpose
	 * @param lastCheckedUtii
	 *            The last UPHN_TRANX_IN_ID from UPHN_TRANX_IN of this
	 *            recordType that was read, in an earlier run.
	 * @param maxUtiiToCheck
	 *            The highest UPHN_TRANX_IN_ID that we will check in this run.
	 * @param rowFetchLimit
	 *            The maximum number of rows of UPHN_TRANX_IN of this recordType
	 *            that we want to retrieve in this run.
	 * @param recordType
	 *            One of "CCD", "HL7", "EXP"
	 * @return A ResultSet containing the results of this query.
	 */
	public String getUphnRowsWithJdbc(int lastCheckedUtii, int maxUtiiToCheck, int rowFetchLimit, String recordType,
			HitLoadLogVO theNewHitLoadLogVO) {

		String ccdTopicAndClause = " and topic ='DEERPROD' and payloadsize != '0'";
		String expTopicAndClause = " and topic ='DEERHL7PROD' and payloadname like '%.csv' and payloadsize != '0'";
		String hl7TopicAndClause = " and topic = 'DEERHL7PROD' and payloadname not like '%.csv' and payloadsize != '0'";
		String topicAndClause = "";
		if ("CCD".equals(recordType)) {
			topicAndClause = ccdTopicAndClause;
		} else if ("HIE_DTH".equals(recordType)) {
			topicAndClause = expTopicAndClause;
		} else if ("HL7".equals(recordType)) {
			topicAndClause = hl7TopicAndClause;
		}
		/*String sqlString = "with ordered_uphn_tranx_in as \n"
				+ "(select uphn_tranx_in_id, topic, payloadsize, orgname, payloadname,\n"
				+ "payloadbinarycontent, receivedtime \n" + "from uphn_tranx_in order by uphn_tranx_in_id) \n "
				+ "select ordered_uphn_tranx_in.*, \n"
				+ "min(uphn_tranx_in_id) over(partition by 1 ) as minUtiiRetrieved, \n"
				+ "max(uphn_tranx_in_id) over(partition by 1 ) as maxUtiiRetrieved, \n"
				+ "count(*) over (partition by 1) rowRetrievedCount\n" + " from ordered_uphn_tranx_in"
				+ " where uphn_tranx_in_id > " + lastCheckedUtii + "\n and uphn_tranx_in_id <= " + maxUtiiToCheck
				+ topicAndClause + "\n and rownum <= " + rowFetchLimit + "\n order by uphn_tranx_in_id ASC";*/
		
		/* Added this new code on 19th April 2018 for not parsing the duplicate records based on payloadname*/		
		
		String sqlString = "with ordered_uphn_tranx_in as \n" + 
				"(select uphn_tranx_in_id, topic, payloadsize, orgname, payloadname, payloadbinarycontent, receivedtime \n" + 
				"from uphn_tranx_in \n" + 
				"order by uphn_tranx_in_id)\n" + 
				", final_data AS\n" + 
				"(\r\n" + 
				"select ordered_uphn_tranx_in.*, \n" + 
				"        min(uphn_tranx_in_id) over(partition by 1 ) as minUtiiRetrieved,\n" + 
				"        max(uphn_tranx_in_id) over(partition by 1 ) as maxUtiiRetrieved,\n" + 
				"        count(*) over (partition by 1) rowRetrievedCount\n" + 
				"        ,row_number() over (partition by lower(payloadname) order by uphn_tranx_in_id) rn \n" + 
				"from ordered_uphn_tranx_in\n" + 
				"where uphn_tranx_in_id >"  +lastCheckedUtii +"  and uphn_tranx_in_id <= "+ maxUtiiToCheck+"\r\n" + 
				 topicAndClause +" \r\n" + 
				"and rownum <="+ rowFetchLimit+" \r\n" + 
				")\r\n" + 
				"select uphn_tranx_in_id, topic, payloadsize, orgname, payloadname, payloadbinarycontent, receivedtime\r\n" + 
				"        ,minUtiiRetrieved, maxUtiiRetrieved, rowRetrievedCount\r\n" + 
				"from final_data\r\n" + 
				"where rn = 1 \r\n" + 
				"order by uphn_tranx_in_id ASC";
		
		log.error(sqlString);

		return sqlString;
	}

	private String getUphnRowsWithFromHitError(String recordType) {

		String sqlString = "SELECT UPHN_TRANX_IN_ID, TOPIC, PAYLOADSIZE, ORGNAME, PAYLOADNAME,\n"
				+ "PAYLOADBINARYCONTENT, RECEIVEDTIME FROM UPHN_TRANX_IN WHERE UPHN_TRANX_IN_ID IN (SELECT UPHN_TRANX_IN_ID FROM HIT_ERROR WHERE REPRISE_FLAG = 'Y' AND RECORD_TYPE = "
				+ "'" + recordType + "'" + "and DATE_OF_REPRISE is null)";
		log.error(sqlString);

		return sqlString;
	}

	public void updateHitLoadLogAfterOneRow(HitLoadLogVO theNewLoadLogVO) {
		hitDocumentDAO.updateHitLoadLogAfterOneRow(theNewLoadLogVO);
	}

	public void updateHitLoadLogAfterAllRows(HitLoadLogVO theNewLoadLogVO) {
		hitDocumentDAO.updateHitLoadLogAfterAllRows(theNewLoadLogVO);
	}

	public void insertHitMessageCount() throws DataAccessException {
		hitDocumentDAO.insertHitMessageCount();
	}

	public List<Map<String, Object>> getCountsForTodayMail() throws DataAccessException {
		return hitDocumentDAO.getCountsForTodayMail();
	}

	public List<Map<String, Object>> getCountsForHITLiveEvent() throws DataAccessException {
		return hitDocumentDAO.getCountsForHITLiveEvent();
	}

	public void deleteFromUphnTranxPropsIn() throws DataAccessException {
		hitDocumentDAO.deleteFromUphnTranxPropsIn();
	}

	public Map<String, String> getHitRaceCodeMapperData() {
		Map<String, String> hitRaceCodeMapperData = new TreeMap<>();
		List<Map<String, String>> hitRaceCodeMapperDataList = hitDocumentDAO.getHitRaceCodeMapperData();
		for (Map<String, String> metaData : hitRaceCodeMapperDataList) {
			hitRaceCodeMapperData.put(metaData.get("RACE_VALUE"), metaData.get("RACE"));
		}
		return hitRaceCodeMapperData;
	}

	public Map<String, String> getHitRaceValue(String parameterName) {
		return hitDocumentDAO.getHitRaceValue(parameterName);
	}
	
	public Map<String, String> getHitRaceData() {
		return hitDocumentDAO.getHitRaceData();
	}
	
	public Map<String, String> getHitEthnicityData() {
		return hitDocumentDAO.getHitEthnicityData();
	}

	public List<Map<Object, Object>> getHitErrorRepriseValue(String parameterName) {
		return hitDocumentDAO.getHitErrorRepriseValue(parameterName);
	}

	public void updateHitDocumentsStatus(long[] documentIds, String actionName,
			String documentType, BindingResult bindingResult, UserObject userObject) {
		
		for (long docId : documentIds) {
		Map<Object, Object> inputOutputParameterMap = new HashMap<>();
		inputOutputParameterMap.put("hitErrorId", docId);
		inputOutputParameterMap.put("actionName", actionName);
		int statusCode = hitDocumentDAO.updateHitDocumentsStatusinHITERRROR(inputOutputParameterMap);
		if (bindingResult != null) {
			if (statusCode > 0) {
				commonExceptionHandler.inspectDBTransacionResult(0, "Successfully updated the meta data. ",
						bindingResult);
			} else {
				commonExceptionHandler.inspectDBTransacionResult(-1,
						"Unable to update the Meta data. please contact helpline.", bindingResult);
			}
		}}
	}
	
	public void updateHitErrorComments(long id, String comments, BindingResult bindingResult, UserObject userObject) {
		// String transactionMessage = null;
		boolean isError = false; 
		try{
			
			Map<Object, Object> inOutParameterMap = new HashMap<>();

			inOutParameterMap.put("hiterrorid", id);
			inOutParameterMap.put("notes", comments);
			
			int resultCount = hitDocumentDAO.updateHitErrorComments(inOutParameterMap);
			
			if(resultCount == 0){
				isError = true;
			}
		}catch(DataAccessException dataAccessException){
			isError = true;
			
			log.error("Exception occured while updating error notes  {}",dataAccessException.getMessage());
		}catch(Exception exception){
			isError = true;
			log.error("Exception occured while updating error notes  {}",exception.getMessage());
		}
		
		if(isError){
			throw new DataRetrievalFailureException("Failed to update the error notes, please contact helpline.");
		}else{
			commonExceptionHandler.inspectDBTransacionResult(0, "Successfully updated the HIT Error notes.", bindingResult);
		}
		
	}

	public void updateHitRaceValue(String parameterName, String parameterValue1[], String parameterValue2,
			BindingResult bindingResult, String UserID) {
		Map<Object, Object> inputOutputParameterMap = new HashMap<>();
		String FinalRace = "";
		String RaceConcat="";
		for(String racevalue :  parameterValue1){
			FinalRace = FinalRace.concat(racevalue).concat(",");
		}
		if (FinalRace != null) {
			RaceConcat = FinalRace.toString();
			if (CommonUtil.isNotNullAndEmpty(RaceConcat)) {
				RaceConcat = RaceConcat.substring(0, RaceConcat.length() - 1);
			}
		}
		inputOutputParameterMap.put("parameterName", parameterName);
		inputOutputParameterMap.put("parameterValue1", RaceConcat);
		inputOutputParameterMap.put("parameterValue2", parameterValue2);
		inputOutputParameterMap.put("userId", UserID);
		int statusCode = hitDocumentDAO.updateHitaRaceValue(inputOutputParameterMap);
		if (bindingResult != null) {
			if (statusCode > 0) {
				//commonExceptionHandler.inspectDBTransacionResult(0, "Successfully updated the meta data. ",bindingResult);
			} else {
				commonExceptionHandler.inspectDBTransacionResult(-1,
						"Unable to update the Meta data. please contact helpline.", bindingResult);
			}
		}
	}

	public void updateHITErrorRepriseFlagStatus(int uphnId, String statusCode) {

		Map<Object, Object> inputOutputParameterMap = new HashMap<>();

		inputOutputParameterMap.put("statusCode", statusCode);
		inputOutputParameterMap.put("uphnID", uphnId);
		int resultcount = hitDocumentDAO.updateHITErrorRepriseFlagStatus(inputOutputParameterMap);
		if (resultcount == 0) {
			throw new DataRetrievalFailureException(new StringBuilder(
					"Failed to update Reprise Flag Status in HIT_ERROR for UPHN_TRANX_IN_ID = " + uphnId).toString());
		}
	}

	
	
	/**
	 * Method writeLogToDB will writee the logs in NYEHMS_APP_ERROR_LOG table 
	 * @param 'recordType' will categorize the logs whether it is for Hl7, CCD, HIE DTH
	 * @param 'queryType' will define the logs with the type of process and quesry executing (Insert or delete)
	 * @param 'action' defins the log as it 'STARTED' or 'ENDED'
	 * 
	 * Method 'prepareHITLOGMsgParameterValues' will set the parameters according with 
	 * the msg we want and write it to the logDB table
	 * // it can extended for CCD, HIE DTH if we want to write in DB logs
	 */
	public void writeLogToDB(String recordType, String processType, String action) {

		// get the parameters to write in log
		Map<Object, Object> messageParametersAndValues = prepareHITLOGMsgParameterValues(recordType, processType, action);

		if (messageParametersAndValues != null) {

			try {
				commonDAO.writeLogToDB(messageParametersAndValues);
			} catch (Exception e) {
				log.error(e.getMessage());
			}

		}

	}
	
	private Map<Object, Object> prepareHITLOGMsgParameterValues(String recordType, String processType, String action) {

		Map<Object, Object> messageParametersAndValues = new HashMap<>();


			// setting up timestamp
			messageParametersAndValues.put("p_log_date", new Timestamp(new Date().getTime()));

			// setting parameters to track logs for hl7 deduplication
			messageParametersAndValues.put("p_user_id", recordType + "_JOB");

			// setting the query type it is performing
			messageParametersAndValues.put("p_session_id", processType);

			// setting parameters to track logs according to the action and insert/or delete
			// process
			if (processType.equals("INSERT_PROCESS")) {

				if (action.equals("STARTED")) {
					messageParametersAndValues.put("p_log_message",
							recordType+" Insert process from UPHN to RAW started -> calling extractHitPayloadsFromRsToRaw()");
				} else {
					messageParametersAndValues.put("p_log_message",
							"Finished " +recordType +" Insert process by writing to all the RAW_"+ recordType+"_* tables");
				}

			}
			
			//de-duplication is only executing for hl7 (RAW_HL_INSURANCE table)
			if (recordType.equals("HL7") && processType.equals("DELETE_PROCESS")) {

				if (action.equals("STARTED")) {
					messageParametersAndValues.put("p_log_message",
							"HL7 deduplication process started -> calling deleteDuplicatesRowsInRawHL7Insuarance()");
				} else {
					messageParametersAndValues.put("p_log_message", "HL7 deduplication process ended");
				}

			}
			
			
		return messageParametersAndValues;
	}


}
