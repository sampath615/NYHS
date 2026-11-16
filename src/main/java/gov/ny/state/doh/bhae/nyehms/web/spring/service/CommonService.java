package gov.ny.state.doh.bhae.nyehms.web.spring.service;

import gov.ny.state.doh.bhae.nyehms.web.spring.config.StatusEmailProperties;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import jakarta.mail.MessagingException;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import gov.ny.state.doh.bhae.nyehms.web.spring.constants.CommonConstants;
import gov.ny.state.doh.bhae.nyehms.web.spring.dao.CommonDAO;
import gov.ny.state.doh.bhae.nyehms.web.spring.jdbc.ConnectionDAO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.common.AddressFieldVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.enums.AppConstants;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonExceptionHandler;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonUtil;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.SendEmail;

@Slf4j
@Service
public class CommonService {


	private String nyehmsLog=AppConstants.nyehmLogDebugString.getName();

	@Autowired         CommonDAO              commonDAO;
	@Autowired private ConnectionDAO          connectionDAO;
	@Autowired private CommonExceptionHandler commonExceptionHandler;
	@Autowired	       CommonUtil             commonUtil;
	@Autowired  private SendEmail sendEmail;


	public List<String> getAddressDropDownInfo(
			AddressFieldVO addressFieldVO,
			BindingResult result,
			UserObject userObject,
			String sessionID)
	{
		boolean rollbackFlag=false;
		List<String> resultList= new ArrayList<>();
		try{
			List<AddressFieldVO> listFetch= commonDAO.getAddressDropDownInfo(addressFieldVO);
			for(AddressFieldVO addressField : listFetch){
				if(addressFieldVO.getFieldName().equalsIgnoreCase("county")){
					resultList.add(commonUtil.trimCounty(addressField.getFieldName()));
					resultList.add(addressField.getCountyFipsCd());
				}else{
					resultList.add(addressField.getFieldName());
				}
			}
		}catch (DataAccessException e) {
			rollbackFlag=true;
			log.error(nyehmsLog+" DataAccessException:: {}",e.toString());
			commonExceptionHandler.handleException(e, userObject,sessionID);
		} catch(Exception e){
			rollbackFlag=true;
			log.error(nyehmsLog+" DataAccessException:: {}",e.toString());
			commonExceptionHandler.handleException(e, userObject,sessionID);
		}finally{
			if(rollbackFlag){
				result.addError(new ObjectError("SQLError", "Transaction Failed. Please try again "));
				log.error(nyehmsLog+" Exception thown and added to Binding Result Object---- ");
			}
		}
		return resultList;
	}

	public void getAddressFieldsCombination(
			AddressFieldVO addressFieldVO,
			BindingResult bindingResult,
			UserObject userObject)
	{
		int  statusCode = commonDAO.getAddressFieldsCombination(addressFieldVO);
		if(statusCode > 0){
			commonExceptionHandler.inspectDBTransacionResult(0, "Address Fields Combination Exists" , bindingResult);
		}else{
			commonExceptionHandler.inspectDBTransacionResult(-1, "Address Fields Combination doesn't  Exists" , bindingResult);
		}
	}

	public Map<String, String> getStateCodes(
			BindingResult result,
			UserObject userObject,
			String sessionID)
	{
		boolean rollbackFlag=false;
		Map<String, String> stateCodes=new LinkedHashMap<>();
		try{
			List<Map<String, String>> stateMapList = commonDAO.getStateCodes();
			for(Map<String, String> stateMap : stateMapList){
				stateCodes.put(stateMap.get("code"), stateMap.get("value"));
			}
		}catch (DataAccessException e) {
			rollbackFlag=true;
			log.error(nyehmsLog+" DataAccessException:: {}", e.toString());
			commonExceptionHandler.handleException(e, userObject,sessionID);
		} catch(Exception e){
			rollbackFlag=true;
			log.error(nyehmsLog+" DataAccessException:: {}", e.toString());
			commonExceptionHandler.handleException(e, userObject,sessionID);
		}finally{
			if(rollbackFlag){
				result.addError(new ObjectError("SQLError", "Transaction Failed. Please try again "));
				log.error(nyehmsLog+" Exception thown and added to Binding Result Object---- ");
			}
		}
		return stateCodes;
	}

	public Map<String, String> getCountyCodes(BindingResult result, UserObject userObject, String sessionID) {
		boolean rollbackFlag=false;

		Map<String, String> countyCodes=new LinkedHashMap<>();
		try{
			List<Map<String, String>> countyMapList = commonDAO.getCountyCodes();
			for(Map<String, String> countyMap : countyMapList){
				countyCodes.put(countyMap.get("code"), commonUtil.trimCounty(countyMap.get("value")));
			}
		}catch (DataAccessException e) {
			rollbackFlag=true;
			log.error(nyehmsLog+" DataAccessException:: {}",e.toString());
			commonExceptionHandler.handleException(e, userObject,sessionID);
		}catch(Exception e){
			rollbackFlag=true;
			log.error(nyehmsLog+" DataAccessException:: {}",e.toString());
			commonExceptionHandler.handleException(e, userObject,sessionID);
		}finally{
			if(rollbackFlag){
				result.addError(new ObjectError("SQLError", "Transaction Failed. Please try again "));
				log.error(nyehmsLog+" Exception thown and added to Binding Result Object---- ");
			}
		}
		return countyCodes;
	}


	public Map<String, String> getCountryCodes(BindingResult bindingResult,
	                                           UserObject userObject, String sessionID) {
		boolean rollbackFlag=false;

		Map<String, String> countryCodes=new LinkedHashMap<>();
		try{
			List<Map<String, String>> countryMapList = commonDAO.getCountryCodes();
			for(Map<String, String> countryMap : countryMapList){
				countryCodes.put(countryMap.get("CODE"), commonUtil.trimCounty(countryMap.get("VALUE")));
			}
		}catch (DataAccessException e) {
			rollbackFlag=true;
			log.error(nyehmsLog+" DataAccessException:: {}",e.toString());
			commonExceptionHandler.handleException(e, userObject,sessionID);
		}catch(Exception e){
			rollbackFlag=true;
			log.error(nyehmsLog+" DataAccessException:: {}",e.toString());
			commonExceptionHandler.handleException(e, userObject,sessionID);
		}finally{
			if(rollbackFlag){
				bindingResult.addError(new ObjectError("SQLError", "Transaction Failed. Please try again "));
				log.error(nyehmsLog+" Exception thown and added to Binding Result Object---- ");
			}
		}
		return countryCodes;
	}



	public int getTrainingDate(String userId, BindingResult bindingResult) {
		// TODO Auto-generated method stub
		Date trainingDate = commonDAO.getCNSTrainingDate(userId);
		String statusMessage = null;
		int statusCode = -1;
		if(trainingDate != null){
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(trainingDate);
			calendar.add(Calendar.YEAR, 1);
			Date currentDate = new Date();
			int noOfHoursDueForTraining = (int)((calendar.getTime().getTime()-currentDate.getTime())/ (1000 * 60 * 60));
			if(noOfHoursDueForTraining > 0 ){
				statusCode = 0;
			}
			statusMessage = CommonUtil.cnsTrainingDateStatusMessage(noOfHoursDueForTraining, calendar);
		}else{
			statusMessage = "Please contact Central office at 518-474-4284 to schedule a training.";
		}
		commonExceptionHandler.inspectDBTransacionResult(statusCode, statusMessage , bindingResult);
		return statusCode;
	}



	public JSONObject getUserPermissions(int workerId, String workerRegionId, BindingResult bindingResult) {

		Map<Object, Object> inOutParameterMap = new HashMap<>();

		inOutParameterMap.put("workerId",workerId);
		inOutParameterMap.put("workerRegionId",workerRegionId);

		// TODO Auto-generated method stub
		List<Map<String, String>> userPermissionsList = commonDAO.getUserPermissions(inOutParameterMap);

		JSONObject userPermissions = new JSONObject();
		for(Map<String, String> permission : userPermissionsList){
			userPermissions.put(permission.get("permissionId"), permission.get("permission"));
		}
		//userPermissions.append("\"").append(permission.get("permissionId")).append("\"").append(":").append("\"").append(permission.get("permission")).append("\"");
		return userPermissions;
	}

	public void setLoginDate(int workerId, BindingResult bindingResult) {
		// TODO Auto-generated method stub


		commonDAO.setClientSession();

		int resultCount = commonDAO.setLoginTimeStamp(workerId);

		if(resultCount < 1){

			log.error("Unable to update the last login Date for User Id {}",workerId);
		}
	}

	public String getImprovedStandardAddress(String customAddress)
	{
		Map<Object, Object> inOutParameterMap = new HashMap<>();
		inOutParameterMap.put("customAddress",customAddress);
		commonDAO.getImprovedStandardAddress(inOutParameterMap);
		String improvedCustomAddress =  (String)inOutParameterMap.get("standardAddress");
		return improvedCustomAddress;
	}

	public JSONObject getDocumentData(String documentId, String docType,
	                                  BindingResult dummyBindingResult, UserObject userObject){
		String sqlQuery = "";
		if("ECLRS".equals(docType) || "VL".equals(docType) || "QUAN_VL".equals(docType) || "LOW_CD4".equals(docType) || "HCD4".equals(docType) || "VHCD4".equals(docType) || "CD4".equals(docType) || "WB".equals(docType) || "SCREEN".equals(docType) || "DIFFERENTIATING".equals(docType) || "QUAL_VL".equals(docType) || "GENOTYPE".equals(docType) || "RESISTANCE".equals(docType) || "IFA".equals(docType) || "P24".equals(docType) || "CULTURE".equals(docType)){
			sqlQuery = "select docTable.FNAME, docTable.MNAME, docTable.LNAME,  subFacility.EHARS_FAC_UID   from STD_HIV_ECLRS_DOC docTable , SUB_FACILITY subFacility WHERE  docTable.DOCUMENT_ID = "+documentId+" and docTable.FACILITY_NAME = subFacility.FACILITY_NAME(+) and rownum < 2";
		}else if("HIV_LAB".equals(docType)){
			sqlQuery = "select docTable.FNAME, docTable.MNAME, docTable.LNAME,  subFacility.EHARS_FAC_UID   from STD_HIV_ECLRS_DOC docTable , SUB_FACILITY subFacility WHERE  docTable.DOCUMENT_ID = "+documentId+" and docTable.FACILITY_NAME = subFacility.FACILITY_NAME(+) and rownum < 2";
		}else if("STD_LAB".equals(docType)){
			sqlQuery = "select docTable.FNAME, docTable.MNAME, docTable.LNAME,  subFacility.EHARS_FAC_UID   from STD_STD_ECLRS_DOC docTable , SUB_FACILITY subFacility WHERE  docTable.DOCUMENT_ID = "+documentId+" and docTable.FACILITY_NAME = subFacility.FACILITY_NAME(+) and rownum < 2";
		}else if("TB".equals(docType)){
			sqlQuery = "select docTable.FNAME, docTable.MNAME, docTable.LNAME,  subFacility.EHARS_FAC_UID   from STD_TB_DOC docTable , SUB_FACILITY subFacility WHERE  docTable.DOCUMENT_ID = "+documentId+" and docTable.FACILITY_NAME = subFacility.FACILITY_NAME(+)  and rownum < 2";
		}else if("DTH".equals(docType)){
			sqlQuery = "select docTable.FNAME, docTable.MNAME, docTable.LNAME,  subFacility.EHARS_FAC_UID   from STD_DEATH_DOC docTable , SUB_FACILITY subFacility WHERE  docTable.DOCUMENT_ID = "+documentId+" and docTable.FACILITY_NAME = subFacility.FACILITY_NAME(+)  and rownum < 2";
		}else if("NYC_DTH".equals(docType)){
			sqlQuery = "select docTable.FNAME, docTable.MNAME, docTable.LNAME,  subFacility.EHARS_FAC_UID   from STD_NYC_DEATH_DOC docTable , SUB_FACILITY subFacility WHERE  docTable.DOCUMENT_ID = "+documentId+" and docTable.FACILITY_NAME = subFacility.FACILITY_NAME(+)  and rownum < 2";
		}else if("CASA".equals(docType)){
			sqlQuery = "select docTable.FNAME, docTable.MNAME, docTable.LNAME,  '' as EHARS_FAC_UID   from STD_CASA_DOC docTable WHERE  docTable.DOCUMENT_ID = "+documentId+" and rownum < 2";
		}else if("DOCCS".equals(docType)){
			sqlQuery = "select * from (select docTable.FNAME, docTable.MNAME, docTable.LNAME,  subFacility.EHARS_FAC_UID , fn_to_date(ADMIN_DATE_TEXT, 'MM/DD/YYYY') from STD_DIN_DOCCS_DOC docTable,  SUB_FACILITY subFacility WHERE  docTable.DOCCS_DOC_ID = "+documentId+" and docTable.CURRENT_FACILITY_NAME  = subFacility.FACILITY_NAME(+) order by fn_to_date(ADMIN_DATE_TEXT, 'MM/DD/YYYY') desc ) where rownum < 2";
		}else if("PRF".equals(docType)){
			sqlQuery = "select docTable.FNAME, docTable.MNAME, docTable.LNAME,  subFacility.EHARS_FAC_UID   from STD_PRF_DOC  docTable , SUB_FACILITY subFacility WHERE   docTable.DOCUMENT_ID = "+documentId+" and docTable.FACILITY_NAME = subFacility.FACILITY_NAME(+)  and rownum < 2";
		}else if("NYC_PRF".equals(docType)){
			sqlQuery = "select docTable.FNAME, docTable.MNAME, docTable.LNAME, '' as EHARS_FAC_UID   from STD_NYCPRF_DOC  docTable  WHERE   docTable.DOCUMENT_ID = "+documentId+"  and rownum < 2";
		}else if("ACRF".equals(docType)){
			sqlQuery = "";
		}else if("AIRS".equals(docType)){
			sqlQuery = "select docTable.FNAME, docTable.MNAME, docTable.LNAME,  subFacility.EHARS_FAC_UID   from STD_AIRS_DOC   docTable,  SUB_FACILITY subFacility  WHERE  docTable.DOCUMENT_ID = "+documentId+" and docTable.FACILITY_NAME  = subFacility.FACILITY_NAME(+) and   rownum < 2";
		}else if("ADAP".equals(docType)){
			sqlQuery = "select docTable.FNAME, docTable.MNAME , docTable.LNAME, '' as EHARS_FAC_UID  from STD_ADAP_DOC docTable WHERE  docTable.DOCUMENT_ID = "+documentId+" and rownum < 2";
		}else if("SSDM".equals(docType)){
			sqlQuery = "select docTable.FNAME, docTable.MNAME, docTable.LNAME, '' as EHARS_FAC_UID   from STD_SSDM_DOC docTable WHERE  docTable.DOCUMENT_ID = "+documentId+" and rownum < 2";
		}else if("LEG_PS".equals(docType) || "LEGACY_PS".equals(docType)){
			sqlQuery = "select docTable.FNAME, docTable.MNAME, docTable.LNAME, '' as EHARS_FAC_UID   from STD_LEGACY_PS_ASSIGN  docTable  WHERE   docTable.DOCUMENT_ID = "+documentId+"  and rownum < 2";
		}else if("NDI".equals(docType)){
			sqlQuery = "select docTable.FNAME, docTable.MNAME, docTable.LNAME, '' as EHARS_FAC_UID   from STD_SSDM_DOC docTable WHERE  docTable.DOCUMENT_ID = "+documentId+" and rownum < 2";
		}else if("NYC_IJR".equals(docType)){
			sqlQuery = "select docTable.FNAME, docTable.MNAME, docTable.LNAME, '' as EHARS_FAC_UID  from STD_NYCIJR_DOC docTable WHERE  docTable.DOCUMENT_ID = "+documentId+" and rownum < 2";
		}else if("ICCR".equals(docType)){
			sqlQuery = "select docTable.FNAME, docTable.MNAME, docTable.LNAME, '' as EHARS_FAC_UID  from STD_ICCR_DOC docTable WHERE  docTable.DOCUMENT_ID = "+documentId+" and rownum < 2";
		}else if("OLDHARS".equals(docType)){
			//tableName = contextPath + "/web/getOLDHARSForm.html";
		}else if("EHARS".equals(docType)){
			//tableName = contextPath + "/web/getEHARSForm.html";
		}else if(docType=="OLD_CASES"){
			sqlQuery = "select docTable.FNAME, docTable.MNAME, docTable.LNAME, '' as EHARS_FAC_UID  from STD_OLD_CASES_DOC docTable WHERE  docTable.DOCUMENT_ID = "+documentId+" and rownum < 2";
		}else if(docType=="TRK_ALIAS"){
			sqlQuery = "select docTable.FNAME, docTable.MNAME, docTable.LNAME, '' as EHARS_FAC_UID  from NYEHMS_USER_ENTERED_ALIAS docTable WHERE  docTable.DOCUMENT_ID = "+documentId+" and rownum < 2";
		}else if(docType=="TRK_ADDR"){
			sqlQuery = "select docTable.FNAME, docTable.MNAME, docTable.LNAME, '' as EHARS_FAC_UID  from NYEHMS_USER_ENTERED_ADDRESS docTable WHERE  docTable.DOCUMENT_ID = "+documentId+" and rownum < 2";
		}else if("MED".equals(docType) || "INS".equals(docType)){
			sqlQuery = "select docTable.FNAME, docTable.MNAME, docTable.LNAME, '' as EHARS_FAC_UID  from STD_MEDICAID_DOC docTable WHERE  docTable.DOCUMENT_ID = "+documentId+" and rownum < 2";
		}else if(docType=="MED_OLD" || docType=="INS_OLD"){
			sqlQuery = "select docTable.FNAME, docTable.MNAME, docTable.LNAME, '' as EHARS_FAC_UID  from STD_MED_OLD_DOC docTable WHERE  docTable.DOCUMENT_ID = "+documentId+" and rownum < 2";
		}else if(docType=="STD_OLD"){
			sqlQuery = "select docTable.FNAME, docTable.MNAME, docTable.LNAME,  subFacility.EHARS_FAC_UID   from STD_STD_OLD_DOC   docTable,  SUB_FACILITY subFacility  WHERE  docTable.DOCUMENT_ID = "+documentId+" and docTable.FACILITY_NAME  = subFacility.FACILITY_NAME(+) and   rownum < 2";
		}else if(docType=="STD_OLD"){
			sqlQuery = "select docTable.FNAME, docTable.MNAME, docTable.LNAME, '' as EHARS_FAC_UID  from STD_STD_CONFIRMED_DOC docTable WHERE  docTable.DOCUMENT_ID = "+documentId+" and rownum < 2";
		}

		JSONObject documentData = new JSONObject();

		if("".equals(sqlQuery)){
			return documentData;
		}
		Connection connection = connectionDAO.getConnectionObj();

		Statement statement = null;
		ResultSet resultSet = null;



		try {
			if (connection != null) {

				if(log.isDebugEnabled()){
					log.debug("Oracle server database Connection is available "+connection);
				}
				statement = connection.createStatement();

				resultSet = statement.executeQuery(sqlQuery);

				while(resultSet.next()){
					documentData.put("FNAME", resultSet.getString(1));
					documentData.put("MNAME", resultSet.getString(2));
					documentData.put("LNAME", resultSet.getString(3));
					documentData.put("EHARS_FAC_UID", resultSet.getString(4));
				}

				if(log.isDebugEnabled()){
					log.debug("Documnent data "+documentData.toString());
				}

			}
		} catch (SQLException sqlException) {

			log.error("Exception while retrieving triggering document data "
					+ sqlException.getMessage());
		} finally {
			try {
				if (connection != null) {
					connection.close();
				}
				if (statement != null) {
					statement.close();
				}
				if (resultSet != null) {
					resultSet.close();
				}
			} catch (SQLException sqlException) {
				log.error("Exception while closing the connection"
						+ sqlException.getMessage());
			}
		}


		return documentData;
	}

	public String getSoundexValue(String name){
		String soundexValue = commonDAO.getSoundexValue(name);

		if(soundexValue == null || "".equals(soundexValue)){
			log.error("Unable to find the Soundex Value for Name {}",name);
			throw new DataRetrievalFailureException("Unable to find the Soundex Value for Name "+name);
		}
		return soundexValue;
	}


	public List<JSONObject> getPsMessageTypes(){
		List<JSONObject> psMessageTypes = null;

		try{
			psMessageTypes =  commonDAO.getPsMessageTypes();
		} catch(Exception exception) {
			log.error("Failed to get coded PS Message types  {}",exception.getMessage());
		}
		return psMessageTypes;
	}

	public String getCurrentActiveStateNo(String _stateno){
		return commonDAO.getCurrentActiveStateNo(_stateno);
	}


	public int insertNyehmsAgreedTime(UserObject userObject){

		try{

			Map<String, Object> inputParameter = new HashMap<>();

			inputParameter.put("WORKER_ID", userObject.getWorkerId());

			return  commonDAO.insertNyehmsAgreedTime(inputParameter);

		}catch(Exception exception){
			log.error("{}  :  Failed while storing the Agreed time ",userObject.getWorkerId(),exception.getMessage());

			return 0;
		}


	}


	public Map<String, String> getCountyNameByZip(String pERSON_ZIP){
		return commonDAO.getCountyNameByZip(pERSON_ZIP);
	}

	public void checkNYSEharsDataExists(){

		int resultCount = commonDAO.checkNYSEharsDataExists();

		if(resultCount == 0){

		}
	}

	public void checkNYCEharsDataExists(){

		int resultCount = commonDAO.checkNYCEharsDataExists();

		if(resultCount == 0){

		}
	}

	public Map<String, Map<String, String>> getZipCodeReferenceData(){

		List<Map<String, String>> addressList = commonDAO.getZipCodeReferenceData();

		Map<String, Map<String, String>> zipCodeReferenceData = new HashMap<>();

		Map<String, String> zipCodeDetails = null;

		for (Map<String, String> map : addressList) {

			zipCodeDetails = new HashMap<String, String>();
			//, CITY, , , 
			zipCodeDetails.put("CITY", map.get("CITY"));
			zipCodeDetails.put("COUNTY", map.get("COUNTY"));
			zipCodeDetails.put("COUNTY_FIPS_CD", map.get("COUNTY_FIPS_CD"));
			zipCodeDetails.put("STATE_CD", map.get("STATE_CD"));

			zipCodeReferenceData.put(map.get("ZIP_CD"), zipCodeDetails);

		}

		return zipCodeReferenceData;


	}

	public Map<String, String> getCountyCodeReferenceData(){

		List<Map<String, String>> countyCodeList = commonDAO.getCountyCodeReferenceData();

		Map<String, String> countyCodeReferenceData = new HashMap<>();

		for (Map<String, String> countyMap : countyCodeList) {

			countyCodeReferenceData.put(countyMap.get("COUNTY_CD"), countyMap.get("COUNTY_NAME"));

		}

		return countyCodeReferenceData;


	}

	public Map<String, String> getNotificationList(String columnName){


		Map<String, String> notificationEmailListByAddressType = new HashMap<>();

		notificationEmailListByAddressType.put("COLUMN_NAME", columnName);

		List<Map<String, String>> notificationList = commonDAO.getNotificationList(notificationEmailListByAddressType);

		for (Map<String, String> map : notificationList) {

			notificationEmailListByAddressType.put(map.get("NOTIFICATION_TYPE"), map.get("EMAIL_ADDRS"));

		}


		return notificationEmailListByAddressType;


	}

	public void sendStatusSummary(List<Map<String, Object>> assignmentList, StatusEmailProperties.Summary layout,
	                              String emailIdentifier, String subject, String bodyMessage) {

		StringBuilder mailContent = new StringBuilder("");

		LocalDateTime ldt = LocalDateTime.now();
		String todayDate = DateTimeFormatter.ofPattern("MM/dd/yyyy", Locale.ENGLISH).format(ldt);


		if(assignmentList != null && assignmentList.size() > 0) {


			String[] headerNamesList = layout.headers().split(",");
			String[] columnNamesList = layout.columns().split(",");

			mailContent.append("<span style='font-weight:bold'>"+subject+" ("+todayDate+")</span><br/><br/>");

			if(CommonUtil.isNotNullAndEmpty(bodyMessage)) {
				mailContent.append("<span style='font-weight:bold'>"+bodyMessage+"</span><br/><br/>");
			}

			mailContent.append("<table border='1' width='180px;' ><tr>");

			for (String header : headerNamesList) {
				mailContent.append("<th>"+header+"</th>");
			}


			for (Map<String, Object> summary : assignmentList) {
				mailContent.append("<tr>");

				for (String columnName : columnNamesList) {
					mailContent.append("<td>");
					mailContent.append(summary.get(columnName));
					mailContent.append("</td>");
				}

				mailContent.append("</tr>");

			}

			mailContent.append("</table>");

			//subject = "Today's "+subject;

		}else {
			subject = layout.noData();

			mailContent.append(subject);


		}

		mailContent.append("<br/><br/><br/>Regards, <br/> NYEHMS Team ");

		try {

			sendEmail.processEmail(null, subject+" ("+todayDate+")", mailContent.toString(), emailIdentifier, CommonConstants.RESPONSE_CONTENT_TYPE_HTML);

		} catch (MessagingException e) {
			log.error(mailContent.toString());

			log.error("failed to send the DATAFLUX and Quality stage summary to Users ", e);

		}

	}


	public JSONObject getCRFReqActivityList()  {

		List<Map<String, String>> crfReqActivityList = commonDAO.getCRFReqActivityList();

		JSONObject crfReqactivities = new JSONObject();
		for(Map<String, String> reqActivity : crfReqActivityList){
			crfReqactivities.put(reqActivity.get("activityCd"), reqActivity.get("activityDesc"));
		}

		return crfReqactivities;
	}

	public List<String> getDocumentListForReminder(){
		return commonDAO.getDocumentListForReminder();
	}

	public List<Map<String, String>> nyehmsInactiveUserDetailsList(){
		return commonDAO.nyehmsInactiveUserDetailsList();
	}

	public List<Map<String, String>> nyehmsTrainingUsersExpireList(){
		return commonDAO.nyehmsTrainingUsersExpireList();
	}
}



