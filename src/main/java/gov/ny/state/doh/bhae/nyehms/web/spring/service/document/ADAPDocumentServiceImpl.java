package gov.ny.state.doh.bhae.nyehms.web.spring.service.document;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import gov.ny.state.doh.bhae.nyehms.web.spring.constants.CommonConstants;
import gov.ny.state.doh.bhae.nyehms.web.spring.dao.document.ADAPDocumentDAO;
import gov.ny.state.doh.bhae.nyehms.web.spring.dao.document.DocumentDAO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.ADAPDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.person.PersonRisksVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.person.PersonViewVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.enums.AppConstants;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.CommonService;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonExceptionHandler;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonUtil;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.EnvironmentProperties;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.scheduler.LabCommonUtils;

/**
 * {@inheritDoc}
 *
 * @author vxp06
 */
@Service
public class ADAPDocumentServiceImpl implements ADAPDocumentService {

    private static  Logger log = LoggerFactory.getLogger(ADAPDocumentServiceImpl.class);
    private String nyehmsLog = AppConstants.nyehmLogDebugString.getName();

    @Autowired
    DocumentTransactionHelper documentTransactionHelper;
    @Autowired
    ADAPDocumentDAO aDAPDocumentDAO;
    @Autowired
    DocumentDAO documentDAO;
    @Autowired
    private CommonExceptionHandler commonExceptionHandler;
    @Autowired
    CommonService commonService;
    
    private Map<String, Map<String, String>> zipCodeReferenceMap = new HashMap<String, Map<String, String>>();

    
    /**
     * {@inheritDoc}
     *
     * @param docId
     * @param result
     * @param userObject
     * @param sessionID
     * @return
     */
    @Override
    public ADAPDocVO getADAPDoc(int docId,BindingResult result,UserObject userObject,
                                String sessionID) {
        
        ADAPDocVO stdADAPDocVO = null;
        boolean rollbackFlag = false;
        log.debug("{} fetching for docId : {}",nyehmsLog, docId);
        try {
            stdADAPDocVO = aDAPDocumentDAO.getADAPDoc(docId);
        } catch (DataAccessException e) {
            rollbackFlag = true;
            log.error(nyehmsLog + "  " + e.getMessage());
            commonExceptionHandler.handleException(e, userObject, sessionID);
        } catch (Exception e) {
            rollbackFlag = true;
            log.error(nyehmsLog + "  " + e.getMessage());
            commonExceptionHandler.handleException(e, userObject, sessionID);
        } finally {
            if (rollbackFlag) {
                commonExceptionHandler.populateBindObjTrsnFail(result);
            }
        }

        return stdADAPDocVO;
    }
    
    
    @Override
    public ADAPDocVO getADAPNYCDoc(int docId,BindingResult result,UserObject userObject,
                                String sessionID) {
        
        ADAPDocVO stdADAPDocVO = null;
        boolean rollbackFlag = false;
        log.debug("{} fetching for docId : {}",nyehmsLog,docId);
        try {
            stdADAPDocVO = aDAPDocumentDAO.getADAPNYCDoc(docId);
        } catch (DataAccessException e) {
            rollbackFlag = true;
            log.error("{} ADAP NYC DOC {}",nyehmsLog, e.getMessage());
            commonExceptionHandler.handleException(e, userObject, sessionID);
        } catch (Exception e) {
            rollbackFlag = true;
            log.error("{} ADAP NYC DOC {}",nyehmsLog, e.getMessage());
            commonExceptionHandler.handleException(e, userObject, sessionID);
        } finally {
            if (rollbackFlag) {
                commonExceptionHandler.populateBindObjTrsnFail(result);
            }
        }

        return stdADAPDocVO;
    }


    
    @Override
    public int writeRawADAPRecordDataToStdTable(Map<Object, Object> docVO,
			BigDecimal documentId){
    	
    	Map<Object, Object> stdDocData = new HashMap<Object, Object>();

    	//stdDocData.put("MATCHED_STATENO","");
    	//stdDocData.put("PERSON_CENSUS_TRACT","");
    	//stdDocData.put("PERSON_LATITUDE","");
    	//stdDocData.put("PERSON_LONGITUDE","");
    	//stdDocData.put("CERT_PROV_CENSUS_TRACT","");
    	//stdDocData.put("CERT_PROV_LATITUDE","");
    	//stdDocData.put("CERT_PROV_LONGITUDE","");
//    	stdDocData.put("PROV_CENSUS_TRACT_PCP","");
//    	stdDocData.put("PROV_CENSUS_TRACT_SUFFIX_PCP","");
//    	stdDocData.put("PCP_PROV_LATITUDE","");
//    	stdDocData.put("PCP_PROV_LONGITUDE","");
//    	stdDocData.put("RX_PROV_CENSUS_TRACT","");
//    	stdDocData.put("RX_PROV_LATITUDE","");
//    	stdDocData.put("RX_PROV_LONGITUDE","");
//    	stdDocData.put("MATCH_WEIGHT","");
//    	stdDocData.put("MATCH_PASS","");
//    	stdDocData.put("MATCH_CD","");
//    	
    	
    	
    	stdDocData.put("DOCUMENT_ID",docVO.get("DOCUMENT_ID"));


    	stdDocData.put("FNAME",CommonUtil.NameValidation((String)docVO.get("FIRST")));
    	stdDocData.put("LNAME",CommonUtil.NameValidation((String)docVO.get("LAST")));
    	stdDocData.put("DOB_TEXT",docVO.get("DOB"));
    	
    	String ssn = CommonUtil.formatSSNValue((String)docVO.get("SSN"));
    	//Need to remove this logic : its already handled in formatSSN function
    	if(CommonUtil.isNotNullAndEmpty(ssn) && ssn.startsWith("9")){
    		stdDocData.put("SSN","");	
    	}else{
    		stdDocData.put("SSN",ssn);
    	}
    	
    	
    	stdDocData.put("MEDICAID_NUMBER",CommonUtil.checkMedicaidNumberFormat((String)docVO.get("MCD_NUMBER")));
    	
    	stdDocData.put("ADAP_ID",docVO.get("ID"));
    	
    	String CURRENT_GENDER = LabCommonUtils.checkCurrentGenderData((String)docVO.get("SEX"));
    	
    	if(CommonUtil.isNotNullAndEmpty(CURRENT_GENDER) && ("M".equals(CURRENT_GENDER) || "F".equals(CURRENT_GENDER))){
    		stdDocData.put("BIRTH_SEX",CURRENT_GENDER);
    	}
    	
    	
    	stdDocData.put("CURRENT_GENDER",CURRENT_GENDER);
    	
    	
    	
    	
    	
    	stdDocData.put("ETHNICCD",LabCommonUtils.checkEthnicityData((String)docVO.get("HISPANIC")));
    	
    	String race_cd = (String) docVO.get("RACE");
    	stdDocData.put("RACE_INDIAN","0");
    	stdDocData.put("RACE_BLACK","0");
    	stdDocData.put("RACE_PACIFIC","0");
    	stdDocData.put("RACE_UNKNOWN","0");
    	stdDocData.put("RACE_WHITE","0");
    	stdDocData.put("RACE_ASIAN","0");
    	stdDocData.put("RACE_MULTIPLE","0");
    	stdDocData.put("RACE_OTHER","0");
    	stdDocData.put("CREATE_UID","900");
    	stdDocData.put("CREATE_DATE",new Date(Calendar.getInstance().getTimeInMillis()));
    	
    	
    	if(CommonUtil.isNotNullAndEmpty(race_cd)){
    		race_cd = race_cd.trim();
    		if("A".equals(race_cd)){
    			stdDocData.put("RACE_CD","04");
    			stdDocData.put("RACE_ASIAN","1");
    		}else if("W".equals(race_cd)){
    			stdDocData.put("RACE_WHITE","1");
    			stdDocData.put("RACE_CD","01");
    		}else if("U".equals(race_cd)){
    			stdDocData.put("RACE_UNKNOWN","1");
    			stdDocData.put("RACE_CD","09");
    		}else if ("I".equals(race_cd)) {
    			stdDocData.put("RACE_PACIFIC","1");
    			stdDocData.put("RACE_CD","04");
			}else if("B".equals(race_cd)){
				stdDocData.put("RACE_BLACK","1");
				stdDocData.put("RACE_CD","02");
			}else if("N".equals(race_cd)){
				stdDocData.put("RACE_INDIAN","1");
				stdDocData.put("RACE_CD","05");
			}
    	}
    	
    	
    	stdDocData.put("CARE_OF",docVO.get("CAREOF"));
    	
    	String apt = "";
    	
    	if(docVO.get("APT") != null){
    		apt = (String)docVO.get("APT");
    	}
    	
    	stdDocData.put("PERSON_ADDRESS",CommonUtil.addressValidation((String)docVO.get("STREET")+" "+apt));
    	
    	
    	stdDocData.put("PERSON_CITY",docVO.get("CITY"));
    	
    	stdDocData.put("PERSON_STATE",docVO.get("STATE"));
    	stdDocData.put("PERSON_ZIP",CommonUtil.formatZipCode((String)docVO.get("ZIP")));
    	
    	
    	String pstate = (String)docVO.get("PSTATE");
    	String pzip = CommonUtil.formatZipCode((String)docVO.get("PZIP"));
    	String paddress = (String) docVO.get("PSTREET");
    	String plname = (String) docVO.get("PLAST");
    	String pfname = (String) docVO.get("PFIRST");
    	
    	String pcountyValue = null;
    	
    	Map<String, String> zipCodeReferenceData = zipCodeReferenceMap.get(pzip);
		
    	float certNPIHeirarcy = 0;
    	float rxNPIHeirarcy = 0;
    	float pcpNPIHeirarcy = 0;
    	
		if (zipCodeReferenceData != null && zipCodeReferenceData.get("COUNTY_FIPS_CD") != null) {
			pcountyValue = zipCodeReferenceData.get("COUNTY_FIPS_CD");
			pstate = zipCodeReferenceData.get("STATE_CD");
		}
		
		if(CommonUtil.isNullOrEmpty(pstate)) {
			pcountyValue = "999";
		}else if(CommonUtil.isNotNullAndEmpty(pstate) && !"NY".equals(pstate)) {
			pcountyValue = "998";
		}
		
		stdDocData.put("CERT_PROV_FNAME",pfname);
    	stdDocData.put("CERT_PROV_LNAME",plname);
    	stdDocData.put("CERT_PROV_ADDRESS",paddress);
    	stdDocData.put("CERT_PROV_CITY",(String) docVO.get("PCITY"));
    	stdDocData.put("CERT_PROV_STATE",pstate);
    	stdDocData.put("CERT_PROV_ZIP",pzip);
    	//stdDocData.put("RX_PROV_PLUS4",subProviderAddress.get("PLUS4"));
    	stdDocData.put("CERT_PROV_COUNTY",pcountyValue);
    	stdDocData.put("CERT_PROV_PHONE",(String)docVO.get("PT1"));
    	
		
		
		if(CommonUtil.isNotNullAndEmpty(pcountyValue) && "NY".equals(pstate) && !CommonConstants.NY_CITY_COUNTIES.contains(pcountyValue)) {
			certNPIHeirarcy = 4.1f;
		}else if("NY".equals(pstate) && CommonConstants.NY_CITY_COUNTIES.contains(pcountyValue)) {
			certNPIHeirarcy = 3.1f;
		}else if("NY".equals(pstate)) {
			certNPIHeirarcy = 2.1f;
		}else if(CommonUtil.isNotNullAndEmpty(pstate) && !"NY".equals(pstate)) {
			certNPIHeirarcy = 1.1f;
		}else if(CommonUtil.isNotNullAndEmpty(paddress) || CommonUtil.isNotNullAndEmpty(pfname) || CommonUtil.isNotNullAndEmpty(plname)){
			certNPIHeirarcy = 0.1f;
		}
			
		List<String> npis = new ArrayList<String>();
    	
    	String pcpNpi = (String) docVO.get("LAST_PC");
    	//String certNPI = (String) docVO.get("PROVNPI");
    	String rxNpi = (String) docVO.get("LAST_RX");
    	
    	if(CommonUtil.isNotNullAndEmpty(pcpNpi)){
    		npis.add(pcpNpi);
    	}
    	
//    	if(CommonUtil.isNotNullAndEmpty(certNPI)){
//    		npis.add(certNPI);
//    	}
    	if(CommonUtil.isNotNullAndEmpty(rxNpi)){
    		npis.add(rxNpi);
    	}
    	
    	
    	
    	
    	Map<Object, Object> inputParameters = new HashMap<Object, Object>();
    	inputParameters.put("npis", npis);
    	
    	List<Map<String, String>> subProviderAddressList =  new ArrayList<Map<String,String>>() ;
    	
    	stdDocData.put("FLAG_CHOSEN_PROVIDER","RX");
    	
    	if(npis.size() > 0){
    		subProviderAddressList = aDAPDocumentDAO.getSubProviderAddressesForTheNPS(inputParameters);
    	}else {
    		stdDocData.put("FLAG_CHOSEN_PROVIDER","CERT");
    	}
    	
    	
    	for (Map<String, String> subProviderAddress : subProviderAddressList) {
    
    		String _npi = subProviderAddress.get("NPI");
    		
    		if(CommonUtil.isNullOrEmpty(_npi)){
    			break;
    		}
    		if(_npi.equals(rxNpi)){
    			stdDocData.put("RX_PROV_FNAME",subProviderAddress.get("PROVIDER_FNAME"));
    	    	stdDocData.put("RX_PROV_MNAME",subProviderAddress.get("PROVIDER_MNAME"));
    	    	stdDocData.put("RX_PROV_LNAME",subProviderAddress.get("PROVIDER_LNAME"));
    	    	stdDocData.put("RX_PROV_ADDRESS",subProviderAddress.get("ADDRESS"));
    	    	stdDocData.put("RX_PROV_CITY",subProviderAddress.get("CITY"));
    	    	stdDocData.put("RX_PROV_STATE",subProviderAddress.get("STATE"));
    	    	stdDocData.put("RX_PROV_ZIP",subProviderAddress.get("ZIPCODE"));
    	    	stdDocData.put("RX_PROV_PLUS4",subProviderAddress.get("PLUS4"));
    	    	stdDocData.put("RX_PROV_COUNTY",subProviderAddress.get("COUNTY_FIPS"));
    	    	stdDocData.put("RX_PROV_PHONE",subProviderAddress.get("PHONE"));
    	    	
    	    	rxNPIHeirarcy = Integer.parseInt(subProviderAddress.get("HIERARCHY")) + 0.2f;
    	    	
    	    	//stdDocData.put("FLAG_CHOSEN_PROVIDER","RX");
    		}else if(_npi.equals(pcpNpi)){
    			stdDocData.put("PCP_PROV_FNAME",subProviderAddress.get("PROVIDER_FNAME"));
    	    	stdDocData.put("PCP_PROV_MNAME",subProviderAddress.get("PROVIDER_MNAME"));
    	    	stdDocData.put("PCP_PROV_LNAME",subProviderAddress.get("PROVIDER_LNAME"));
    	    	stdDocData.put("PCP_PROV_ADDRESS",subProviderAddress.get("ADDRESS"));
    	    	stdDocData.put("PCP_PROV_CITY",subProviderAddress.get("CITY"));
    	    	stdDocData.put("PCP_PROV_STATE",subProviderAddress.get("STATE"));
    	    	stdDocData.put("PCP_PROV_ZIP",subProviderAddress.get("ZIPCODE"));
    	    	stdDocData.put("PCP_PROV_PLUS4",subProviderAddress.get("PLUS4"));
    	    	stdDocData.put("PCP_PROV_COUNTY",subProviderAddress.get("COUNTY_FIPS"));
    	    	stdDocData.put("PCP_PROV_PHONE",subProviderAddress.get("PHONE"));
    	    	pcpNPIHeirarcy = Integer.parseInt(subProviderAddress.get("HIERARCHY")) + 0.3f;
    	    	//stdDocData.put("FLAG_CHOSEN_PROVIDER","PCP");
    		}
    		
			
		}
    	
    	if(pcpNPIHeirarcy > rxNPIHeirarcy && pcpNPIHeirarcy > certNPIHeirarcy) {
    		stdDocData.put("FLAG_CHOSEN_PROVIDER","PCP");
    	}else if(certNPIHeirarcy > rxNPIHeirarcy && certNPIHeirarcy > pcpNPIHeirarcy) {
    		stdDocData.put("FLAG_CHOSEN_PROVIDER","CERT");
    	}else {
    		stdDocData.put("FLAG_CHOSEN_PROVIDER","RX");
    	}
    	
    	
    	stdDocData.put("CERT_PROV_NPI", (String) docVO.get("PROVNPI"));
    	stdDocData.put("PCP_PROV_NPI",pcpNpi);
    	stdDocData.put("RX_PROV_NPI",rxNpi);
    	
    	//stdDocData.put("RX_PROVIDER_TYPE","");
    	

    	//stdDocData.put("RAW_PROVIDER_ADDRESS_ID","");
    	stdDocData.put("FIRST_HIV_TEST_DATE_TEXT",docVO.get("YRFIRST"));
    	stdDocData.put("ADAP_FILE",docVO.get("ADAP_FILE"));
    	stdDocData.put("SWM",LabCommonUtils.checkRiskData((String)docVO.get("SMALE")));
    	stdDocData.put("SWF",LabCommonUtils.checkRiskData((String)docVO.get("SFEMALE")));
    	stdDocData.put("IDU",LabCommonUtils.checkRiskData((String)docVO.get("IVDU")));
    	stdDocData.put("SEX_PARTNER_IDU",LabCommonUtils.checkRiskData((String)docVO.get("SIVDU")));
    	stdDocData.put("SEX_PARTNER_HIV",LabCommonUtils.checkRiskData((String)docVO.get("SPWA")));
    	stdDocData.put("RECV_BLOOD_PROD",LabCommonUtils.checkRiskData((String)docVO.get("TRANS")));
    	stdDocData.put("PERINATAL",LabCommonUtils.checkRiskData((String)docVO.get("MATER")));
    	stdDocData.put("RISK_OTHER",LabCommonUtils.checkRiskData((String)docVO.get("ROTHER")));
    	//stdDocData.put("RISK_UNKNOWN","");
    	//stdDocData.put("SEX_PARTNER_MSM","");
    	//stdDocData.put("RISK11","");
    	//stdDocData.put("SEX_PARTNER_HEMOPHILIAC","");
    	//stdDocData.put("SEX_PARTNER_TRANSFUSION","");
    	//stdDocData.put("SEX_PARTNER_TRANSPLANT","");
    	//stdDocData.put("RECV_TRANSPLANT","");
    	stdDocData.put("HEALTHCARE_WORKER",LabCommonUtils.checkRiskData((String)docVO.get("HEALTH")));
    	stdDocData.put("RECEIVED_DATE",docVO.get("RECV_DT"));
    	stdDocData.put("CURRENT_PROCESS_STATUS", CommonConstants.RAW_RECORD_VALIDATION_SUCCESS_PROCESS_STATUS);
    	//stdDocData.put("NYC_SHIP_DT","");
    	//stdDocData.put("CITYNO","");

    	//stdDocData.put("EHARS_IMPORT_DATE","");
    	//stdDocData.put("QS_PARENT_DOC_ID","");
    	//stdDocData.put("RECEIVED_DATE","");

    	
    	//stdDocData.put("EHARS_STATUS_CD","");
    	//stdDocData.put("ASSESS_ASSIGN","");
    	//stdDocData.put("REVIEW_UID","");
    	//stdDocData.put("REVIEW_DT","");
    	//stdDocData.put("ADAP_SRC_ID","");    	
    	//stdDocData.put("MNAME","");
    	//stdDocData.put("SUFFIX","");
    	
    	stdDocData.put("CREATE_UID","900");
    	stdDocData.put("CREATE_DATE",new Date(Calendar.getInstance().getTimeInMillis()));
    	
    	return  aDAPDocumentDAO.writeStdDocData(stdDocData);
    	
    	
    	
    }
    
    @Override
    public PersonViewVO getStdADAPDocumentForClericalReview(int documentId){
    	PersonViewVO adapDocVO = aDAPDocumentDAO.getStdADAPDocumentForClericalReview(documentId);
    	
    	return adapDocVO;
    }
    
    @Override
    public List<String> getFileNames(){
    	return aDAPDocumentDAO.getFileNames();
    }
    
    @Override
    public List<String> getFileNameNotShippedToNYC(String nycShipStatus){
    	
    	Map<String, Object> inParameterMap = new HashMap<String, Object>();
    	
    	inParameterMap.put("nycShipStatus", nycShipStatus);
    	
    	return aDAPDocumentDAO.getFileNameNotShippedToNYC(inParameterMap);
    }
    
    @Override
    @Transactional
    public List<Map<String, String>> generateADAPNYCFile(String fileName, String nycShiStatus,  
			BindingResult bindingResult, UserObject userObject){

		
		
		
		List<Map<String, String>> adapNYCData = null;
		
		
			
			
		Map<String, Object> inParameterMap = new HashMap<String, Object>();
		
		
		inParameterMap.put("fileName", fileName);
		inParameterMap.put("nycShiStatus", nycShiStatus);
		
		
		adapNYCData = aDAPDocumentDAO.generateNYCData(inParameterMap);
		
		if(adapNYCData == null || adapNYCData.size() == 0){
			throw new DataRetrievalFailureException("There is no data available to generate NYC Data.");
		}
		
		
		
		
		if("notShipped".equals(nycShiStatus) || "notShippedAllFiles".equals(nycShiStatus)){
			
			inParameterMap.put("adapNYCData", adapNYCData);
			inParameterMap.put("workerId", userObject.getWorkerId());
		
			int resultCount = aDAPDocumentDAO.updateNYCDateForTheFile(inParameterMap);
			
			if(resultCount != adapNYCData.size()){
				
				log.error("Count Not matching between reterival and updating the NYC Ship date count.");
				
				
				throw new DataRetrievalFailureException("Failed while generating NYC Data, Please contact helpline.");
			}
		}
		
		return adapNYCData;
    	
    }
    
    
    @Override
    @Transactional
    public void uploadNYCReturnFileData(MultipartFile uploadedFile,
			BindingResult bindingResult, UserObject userObject)throws IOException{
    	
    	boolean isError = false; 
		
		XSSFWorkbook workbook = null;
		XSSFSheet worksheet = null;
		
		String transactionMessage = null;
		
				
		List<Map<String, Object>>  fileDataList = new ArrayList<Map<String, Object>>();
		
			
			
			
			if(uploadedFile.isEmpty()){
				
				isError = true;
				transactionMessage = "The file uploaded contains no Data";
				
			}else{
				workbook = new XSSFWorkbook(uploadedFile.getInputStream());
				
				 worksheet = workbook.getSheetAt(0);
				
				 
				int index = 0; 
				
				
				String[] ADAP_RETURN_FILE_COLUMN_NAMES = EnvironmentProperties.envProperties.getProperty("ADAP_RETURN_FILE_COLUMN_NAMES").split(",");
				
				//String[] RIDR_DB_COLUMN_NAMES = EnvironmentProperties.envProperties.getProperty("RIDR_DB_COLUMN_NAMES").split(",");
				
				
				
				int COLUMN_NAMES_LENGTH = ADAP_RETURN_FILE_COLUMN_NAMES.length;
				
				//int workerId = userObject.getWorkerId();
				
				
				while (index <= worksheet.getLastRowNum()) {
					
					XSSFRow row = worksheet.getRow(index++);
					
					if(index == 1){
						
						
						
						for(int columnIndex = 0; columnIndex < COLUMN_NAMES_LENGTH; columnIndex++){
							
							String columnNameInSheet = row.getCell(columnIndex).getStringCellValue();
							
							columnNameInSheet = columnNameInSheet.replaceAll("\n", " ");
							
							if(!ADAP_RETURN_FILE_COLUMN_NAMES[columnIndex].equalsIgnoreCase(columnNameInSheet.trim())){
								
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
					
					for(int columnIndex = 0; columnIndex < COLUMN_NAMES_LENGTH; columnIndex++){
						
						if(!(row.getCell(columnIndex) == null || CommonUtil.isNullOrEmpty(row.getCell(columnIndex).toString()))){
							
							hasRowData = true;
							
							break;
						}
						
					}
					
					
					if(!hasRowData){
						continue;
					}
					
					
					Map<String, Object> fileData = new HashMap<String, Object>();
					
					DataFormatter fmt = new DataFormatter();
					
					
							
					for (Cell c : row) {
						 CellReference cr = new CellReference(c);
					       System.out.println("Cell " + cr.formatAsString() + " is " + 
					                          fmt.formatCellValue(c) );
					       
					       
					       fileData.put(ADAP_RETURN_FILE_COLUMN_NAMES[c.getColumnIndex()], fmt.formatCellValue(c));
					}
					
					
					
//					for(int columnIndex = 0; columnIndex < COLUMN_NAMES_LENGTH; columnIndex++){
//						
//						if(row.getCell(columnIndex) != null){
//							fileData.put(ADAP_RETURN_FILE_COLUMN_NAMES[columnIndex], row.getCell(columnIndex).toString());
//						}
//						
//						
////						if(columnIndex == 1){
////							sequenceNumbers.add((int)row.getCell(columnIndex).getNumericCellValue());
////						}
//
//						
//					}
					
					


					if(fileData.get("STDRPTS_ID") != null && fileData.get("NYCNUMBER") != null){
						Map<String, Object> inputParameterMap = new HashMap<String, Object>();
						
						
						inputParameterMap.put("documentId", fileData.get("STDRPTS_ID"));
						inputParameterMap.put("cityno", fileData.get("NYCNUMBER"));
						
						
						int resultCount = aDAPDocumentDAO.compareNYCCitynoWithNYEHMS(inputParameterMap);
						
						if(resultCount > 0){
							
							fileData.put("REVIEW_STATUS", "A");
							fileData.put("REVIEWED_ID", "900");
							fileData.put("REVIEWED_DATE", new Date(Calendar.getInstance().getTimeInMillis()));
							
						}
						
						
						
					}
					
					

					fileDataList.add(fileData);
					
					
					
				}
				
				
			}
	        
			
			workbook.close();
			
			if(!isError){
				Map<String, Object> inputParameterMap = new HashMap<String, Object>();
				
				inputParameterMap.put("fileDataList", fileDataList);
				inputParameterMap.put("workerId", userObject.getWorkerId());
				
				
				//documentDAO.writeRidrData(inputParameterMap);
				int resultCount = aDAPDocumentDAO.uploadNYCReturnFileData(inputParameterMap);

				
				
				inputParameterMap.remove("fileDataList");
				
				//inputParameterMap.put("sequenceNumbers", sequenceNumbers);
				
				
				//int resultCount = recordSearchReviewDAO.updateCDCReturnDataInfo(inputParameterMap);

				if(resultCount == 0){
					
					commonExceptionHandler.inspectDBTransacionResult(-1, "There is no data to upload.", bindingResult);
					
					throw new DataRetrievalFailureException("There is no data to upload");
					
				}else{
					
					commonExceptionHandler.inspectDBTransacionResult(0, "Successfully uploaded NYC Return File Data.", bindingResult);
				}
				
				
				
				
			}else{
				commonExceptionHandler.inspectDBTransacionResult(-1, transactionMessage, bindingResult);
			}
    	
    }
    
    @Override
    public PersonViewVO getNYCADAPDocumentForClericalReview(int documentId){
    	
    	PersonViewVO adapDocVO = aDAPDocumentDAO.getNYCADAPDocumentForClericalReview(documentId);
    	
    	return adapDocVO;
    	
    	
    }
    
    @Override
    public Map<String, Object> getADAPDocumentIdToReview(
			BindingResult bindingResult, UserObject userObject){
    	
    	boolean isError = false;
		 
    	Map<String, Object> inOutParameterMap = new HashMap<String, Object>();
		  try {
			  List<Integer> nycADAPDocuments = aDAPDocumentDAO.getNCYADAPDocumentsToREview();
		    	
		    	
				 
				 Random randomNumber = new Random();
				  
				  if(nycADAPDocuments.size() > 0){
				 
					  inOutParameterMap.put("TRANSACTION_RESULT", "TRANSACTION_SUCCESS");
					  
					  inOutParameterMap.put("totalDocuments", nycADAPDocuments.size());
					  inOutParameterMap.put("DOCUMENT_ID", nycADAPDocuments.get(randomNumber.nextInt(nycADAPDocuments.size())));
					  
					  
				  }else{
					  
					  inOutParameterMap.put("TRANSACTION_RESULT", "TRANSACTION_FAILURE");
					  inOutParameterMap.put("TRANSACTION_MESSAGE", "There is no Pending Documents to Review");
				  }
	        } catch (DataAccessException e) {
	        	isError = true;
	            log.error(nyehmsLog + "  Failed to Fetch the Data for NYC File Review " + e.getMessage());
	            commonExceptionHandler.handleException(e, userObject, userObject.getSessionId());
	        } catch (Exception e) {
	        	isError = true;
	            log.error(nyehmsLog + "  Failed to Fetch the Data for NYC File Review " + e.getMessage());
	            commonExceptionHandler.handleException(e, userObject, userObject.getSessionId());
	        }
		  
		  if(isError){
			  inOutParameterMap.put("TRANSACTION_RESULT", "TRANSACTION_FAILURE");
			  inOutParameterMap.put("TRANSACTION_MESSAGE", "Failed to load the Documents for Review, Please contact helpline");
		  }
		  
		  return inOutParameterMap;
    	
    }
    
    @Override
    public PersonRisksVO loadADAPRiskContent(int documentId, String origin,
			BindingResult bindingResult, UserObject userObject, String id){
    	
    	if("NYC".equals(origin)){
    		return aDAPDocumentDAO.loadADAPRiskContentForNYCDocument(documentId);
    	}else{
    		return aDAPDocumentDAO.loadADAPRiskContentForNYEHMSDocument(documentId);
    	}
    	
    	
    }
    
    @Override
    public void updateNYCADAPDocumentMatchStatus(int documentId,
			String reviewCd,  String cityno, String comments, BindingResult bindingResult, UserObject userObject){
    	
    	
    	Map<String, Object> inOutParameterMap = new HashMap<String, Object>();
    	
    	
    	
    	if("D".equals(reviewCd) && CommonUtil.isNullOrEmpty(cityno)){
    		
    		commonExceptionHandler.inspectDBTransacionResult(-1, "cityno is required to mark it as Disagree.", bindingResult);
    		
    		return;
    		
    	}
    	
    	inOutParameterMap.put("documentId", documentId);
    	inOutParameterMap.put("reviewCd", reviewCd);
    	inOutParameterMap.put("cityno", cityno);
    	inOutParameterMap.put("reviewComments", comments);
    	
    	inOutParameterMap.put("workerId", userObject.getWorkerId());
    	
    	
    	
    	int resultCount = aDAPDocumentDAO.updateNYCADAPDocumentMatchStatus(inOutParameterMap);
    	
    	if(resultCount > 0){
    		    		
    		if("D".equals(reviewCd)){
    			commonExceptionHandler.inspectDBTransacionResult(0, "DisAgreed the ADAP NYC Feedback document", bindingResult);
    		}else{
    			commonExceptionHandler.inspectDBTransacionResult(0, "Agreed ADAP NYC Feedback document", bindingResult);
    		}
    		
    		
    	}else{

    		commonExceptionHandler.inspectDBTransacionResult(-1, "This Document already reviewed by other user.", bindingResult);
    	}
    	
    	
    	
    }
    
    @Override
    public List<String> getADAPNYCFileNames(){
    	return aDAPDocumentDAO.getADAPNYCFileNames();
    }
    
    @Override
    public List<Map<String, String>> generateNYCFileSummary(String fileName,
			BindingResult bindingResult, UserObject userObject){
    	
    	
    	List<Map<String, String>> nycFileSummaryData = aDAPDocumentDAO.generateNYCFileSummary(fileName);
    	
    	if(nycFileSummaryData.size() == 0 ){
    		
    		log.error("There is no data to Generate ADAP NYC Summary File  {}", fileName);
    		
    		commonExceptionHandler.inspectDBTransacionResult(-1, "There is no data to Generate ADAP NYC Summary File", bindingResult);
    		
    	}else{
    		commonExceptionHandler.inspectDBTransacionResult(0, "Successfully generated the ADAP NYC Summary File", bindingResult);
    	}
    	
    	return nycFileSummaryData;
    	
    	
    }
    
    
    @Override
    public List<Map<String, String>> generateADAPNYCQualityStageMatchSummary(String fileName,
			BindingResult bindingResult, UserObject userObject){
    	
    	
    	List<Map<String, String>> nycFileQualityStageSummaryData = aDAPDocumentDAO.generateADAPNYCQualityStageMatchSummary(fileName);
    	
    	if(nycFileQualityStageSummaryData.size() == 0 ){
    		
    		log.error("There is no data to Generate ADAP NYC Summary File   {}",fileName);
    		
    		commonExceptionHandler.inspectDBTransacionResult(-1, "There is no data to Generate ADAP NYC Summary File", bindingResult);
    		
    	}else{
    		commonExceptionHandler.inspectDBTransacionResult(0, "Successfully generated the ADAP NYC Summary File", bindingResult);
    	}
    	
    	return nycFileQualityStageSummaryData;
    	
    	
    }
    
    
    
    
    @Override
    public List<Map<String, String>> generateADAPNYCShippedMatchSummary(
			String fileName, BindingResult bindingResult, UserObject userObject){
    	
    	List<Map<String, String>> nycShippedMatchSummary = aDAPDocumentDAO.generateADAPNYCShippedMatchSummary(fileName);
    	
    	if(nycShippedMatchSummary.size() == 0 ){
    		
    		log.error("There is no data to Generate ADAP NYC Summary File   {}",fileName);
    		
    		commonExceptionHandler.inspectDBTransacionResult(-1, "There is no data to Generate ADAP NYC Summary File", bindingResult);
    		
    	}else{
    		commonExceptionHandler.inspectDBTransacionResult(0, "Successfully generated the ADAP NYC Summary File", bindingResult);
    	}
    	
    	return nycShippedMatchSummary;
    	
    	
    	
    }
    
    @Override
    public List<Map<String, String>> generateADAPNYCDiscrepancyFile(
			String fileName, BindingResult bindingResult, UserObject userObject){
    	
    	List<Map<String, String>> adapNYCData = null;
		
		
		
		
		Map<String, Object> inParameterMap = new HashMap<String, Object>();
		
		
		inParameterMap.put("fileName", fileName);
		
		adapNYCData = aDAPDocumentDAO.generateADAPNYCDiscrepancyFile(inParameterMap);
		
		return adapNYCData;
    }
    
    @Override
    public void checkCityNoExists(String cityno, BindingResult bindingResult,
			UserObject userObject){
    	
    	
    	int resultCount = aDAPDocumentDAO.checkCityNoExists(cityno);
    	
    	
    	if(resultCount > 0 ){
    		commonExceptionHandler.inspectDBTransacionResult(0, "CITYNO exists ", bindingResult);
    	}else{
    		commonExceptionHandler.inspectDBTransacionResult(-1, "CITYNO("+cityno+") doesn't exist, please verify the data.", bindingResult);
    	}
    	
    }
    
    @Override
	public void loadReferenceVariables() {
    	zipCodeReferenceMap = commonService.getZipCodeReferenceData();
	}
    
    @Override
    public void resetDocumentReferenceVariables() {
    	zipCodeReferenceMap.clear();
    }
}
