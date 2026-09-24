package gov.ny.state.doh.bhae.nyehms.web.spring.service.document;

import gov.ny.state.doh.bhae.nyehms.web.spring.constants.CommonConstants;
import gov.ny.state.doh.bhae.nyehms.web.spring.dao.document.PRFDocumentDAO;
import gov.ny.state.doh.bhae.nyehms.web.spring.dao.document.PRFPartnerDAO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.PRFDocumentVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.PRFPartnerVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.person.PersonViewVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.schedulers.ManualQuartzJobService;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonExceptionHandler;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonUtil;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.DateUtil;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.UnexpectedRollbackException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;



/**
 * This class provides implementation for methods defined in PRFDocumentService
 * interface.
 *
 * @author vxp06
 */
@Service
public class PRFDocumentServiceImpl implements PRFDocumentService {

	private static Logger log = LoggerFactory.getLogger(PRFDocumentServiceImpl.class);
    //private String nyehmsLog = AppConstants.nyehmLogDebugString.getName();

    @Autowired
    private CommonExceptionHandler commonExceptionHandler;
    
    @Autowired
    DocumentTransactionHelper documentTransactionHelper;
    
    @Autowired
    ManualQuartzJobService manualQuartzJobService;
    
    @Autowired
    PRFDocumentDAO pRFDocumentDAO;
    
    @Autowired
    PRFPartnerDAO pRFPartnerDAO;
    
    @Autowired
    DateUtil  dateUtil;
    
    private final int maxAge = Calendar.getInstance().get(Calendar.YEAR) - 1900; 

    /**
     * This method will return PRF document for a given document id
     * {@inheritDoc}
     *
     * @param documentID
     * @return
     */
    @Override
    public PRFDocumentVO getPRFDocument(int documentID) {
       
    	PRFDocumentVO pRFDocumentVO = pRFDocumentDAO.getPRFDoc(documentID);
    	
    	LinkedList<PRFPartnerVO> prfPartnerList = pRFPartnerDAO.getStdPRFPartner(documentID);
    	
    	pRFDocumentVO.setPartnerList(prfPartnerList);
        
        return pRFDocumentVO;
    }
    
    @Override
    public PersonViewVO getStdPRFDocumentForClericalView(int documentId){
    	
    	PersonViewVO pRFDocumentVO = pRFDocumentDAO.getStdPRFDocumentForClericalView(documentId);
    	
    	return pRFDocumentVO;
    }
    
    @Override
    public PRFDocumentVO getCompleteRawPRFDocumentContent(int docId){
    	
    	PRFDocumentVO pRFDocumentVO = pRFDocumentDAO.getCompleteRawPRFDocumentContent(docId);
    	
    	
    	LinkedList<PRFPartnerVO> prfPartnerList = pRFPartnerDAO.getPRFPartner(docId);
    	
    	pRFDocumentVO.setPartnerList(prfPartnerList);
    	
    	return pRFDocumentVO;
    }
    
   

    /**
     * This method accepts PRFDocumentVO object which contains 1. PRF document
     * fields 2. List of TTH documents 3. List of PRF Partners
     *
     * This method will create new document id for PRF and TTH docs and insert
     * them into corresponding RAW tables.
     *
     * PRF Partner document is child document of PRF document, so we do not
     * create new document id for it. In this case we just create a new partner
     * id into RAW_PRF_PARTNER table.
     *
     * In the end we call procedure to associate newly created PRF document with
     * stateno Note : We do not associate TTH or PRF Partner documents to
     * statenos There are fields into TTH and PRF PARTNER tables which link back
     * to parent PRF document
     *
     * @param pRFDocumentVO
     * @param result
     * @param userObject
     * @param sessionID
     * @throws UnexpectedRollbackException
     */
    @Override
    public int insertPRFDoc(PRFDocumentVO pRFDocumentVO,
            BindingResult result,
            UserObject userObject){
        
    		
    		int resultCount = pRFDocumentDAO.insertPrfDocData(pRFDocumentVO);
    		
    	    return resultCount;     

    }
    
    public int insertPRFPartner(PRFPartnerVO prfPartnerVO, BindingResult result,
            UserObject userObject){
    	
    	prfPartnerVO.setEntryReviewId(userObject.getUserId());
    	
    	int resultCount = pRFPartnerDAO.insertPRFPartner(prfPartnerVO);
		
	    return resultCount;  
    }
    
    @Override
    public void updateRawPrfDocument(PRFDocumentVO pRFDocumentVO, boolean isValidationFailed,
			BindingResult bindingResult, UserObject userObject){
    	boolean isError = false;
    	

		int currentDocumentId = pRFDocumentVO.getDOCUMENT_ID();
		
    	try{
    		
    		documentTransactionHelper.updateRawPrfDocument(pRFDocumentVO, isValidationFailed, bindingResult, userObject);
    		
	    	commonExceptionHandler.inspectDBTransacionResult(0, "PRF form Successfully updated.", bindingResult);
	    	
    	}catch(DataAccessException dataAccessException){
			isError = true;
			log.error("Failed to create the a new Document  {}",currentDocumentId);
			log.error(dataAccessException.getMessage());
		}catch(Exception exception){
			isError = true;
			log.error("Failed to create the a new Document {}",currentDocumentId);
			log.error(exception.getMessage());
		}
    	
    	if(isError){
    		commonExceptionHandler.inspectDBTransacionResult(-1, "Unable to update the PRF document, please contact helpline.", bindingResult);
    	}
    }
    
    
    @Override
    public void deleteRawPrfDoc(int docId, BindingResult bindingResult,
			UserObject userObject){
    	boolean isError = false;
    	try{
    		
    		documentTransactionHelper.deleteRawPrfDoc(docId, bindingResult, userObject);
	    	
    	}catch(DataAccessException dataAccessException){
			isError = true;
			log.error("Failed to delete the a prf Document  {}",docId);
			log.error(dataAccessException.getMessage());
		}catch(Exception exception){
			isError = true;
			log.error("Failed to delete the a prf Document {}",docId);
			log.error(exception.getMessage());
		}
    	
    	if(isError){
    		commonExceptionHandler.inspectDBTransacionResult(-1, "Unable to delete the PRF document, please contact helpline.", bindingResult);
    	}else{
    		commonExceptionHandler.inspectDBTransacionResult(0, "Successfully deleted the Raw PRF document", bindingResult);
    	}
    }
    
    @Override
    public void isValidPrfDoc(PRFDocumentVO pRFDocumentVO,
			BindingResult result){
    	
    	
    	String sexAtBirth = pRFDocumentVO.getSEX_AT_BIRTH();
    	
    	Map<String, String> errors = new HashMap<String, String>();
    	
    	
//    	if(CommonUtil.isNotNullAndEmpty(sexAtBirth)){
    	
	    	if(!pRFDocumentVO.isINIT_HIV_DIAG() && !pRFDocumentVO.isPREV_HIV_DIAG() && !pRFDocumentVO.isINIT_AIDS_DIAG() && !pRFDocumentVO.isPREV_AIDS_DIAG()){
	    		errors.put("TYPE_OF_REPORT_SECTION", "At least one Patient Type Report must be selected.");
	    	}else{
	    		
	    		if("Y".equals(pRFDocumentVO.getPT_ARV_EVER_TAKEN()) && "Y".equals(pRFDocumentVO.getPT_ARV_EVER_TAKEN()) && CommonUtil.isNullOrEmpty(pRFDocumentVO.getARV_PREVEN_PREP()) && CommonUtil.isNullOrEmpty(pRFDocumentVO.getPOST_EXP_ARV_PEP()) && CommonUtil.isNullOrEmpty(pRFDocumentVO.getARV_TREATMENT())){
	    				
	        		errors.put("arvGroup", "Ever taken any antiretroviral (ARVs) medications is YES. Atleast one ARV should be required.");
	    		}
	    		
	    		
	    	}
	    	
	    	String personZip = pRFDocumentVO.getPERSON_ZIP();
	    	
	    	if (CommonUtil.isNotNullAndEmpty(personZip) && (personZip.length() != 5 || !CommonUtil.isNumeric(personZip))) {
				errors.put("PERSON_ZIP","Person zip code must be exactly 5 numeric characters.");
			}
	    	
	    	//String _din = pRFDocumentVO.getDIN();
	    	
	//    	if(CommonUtil.isNotNullAndEmpty(_din) && (_din.length() != 7  || !checkDinFormat(_din))){
	//    		errors.put("DIN", "Patient DIN code must be exactly 7 characters, with 3rd being a letter (A-Z) and " +
	    	
	    	String altFname = pRFDocumentVO.getALT_FNAME();
	    	String altMname = pRFDocumentVO.getALT_MNAME();
	    	String altLname = pRFDocumentVO.getALT_LNAME();
	    	
	    	
	    	if (((CommonUtil.isNotNullAndEmpty(altFname) || CommonUtil.isNotNullAndEmpty(altMname))) && CommonUtil.isNullOrEmpty(altLname)) {
	    		errors.put("ALT_LNAME", "Patient Alias or Maiden Last Name is required");  
			}
	    	
	    	if(pRFDocumentVO.isRACE_WHITE() || pRFDocumentVO.isRACE_BLACK() || pRFDocumentVO.isRACE_ASIAN() || pRFDocumentVO.isRACE_PACIFIC() || pRFDocumentVO.isRACE_INDIAN() || pRFDocumentVO.isRACE_OTHER() || pRFDocumentVO.isRACE_UNKNOWN()){
	    		if(pRFDocumentVO.isRACE_OTHER() && ("HISPANIC".equalsIgnoreCase(pRFDocumentVO.getRACE_OTHER_TEXT()) || "Latino".equalsIgnoreCase(pRFDocumentVO.getRACE_OTHER_TEXT()))){
	        		errors.put("RACE_OTHER_TEXT", "Patient other race can't be Hispanic or Latino. Use ethnicity field to record Hispanic or non-Hispanic.");
	        	}
	        	if((pRFDocumentVO.isRACE_WHITE() || pRFDocumentVO.isRACE_BLACK() || pRFDocumentVO.isRACE_ASIAN() || pRFDocumentVO.isRACE_PACIFIC() || pRFDocumentVO.isRACE_INDIAN() || pRFDocumentVO.isRACE_OTHER()) && pRFDocumentVO.isRACE_UNKNOWN()){
	        		errors.put("RACE_UNKNOWN_SECTION", "Patient Unknown Race can't be selected if any other race is selected.");
	        	}
	    	}else{
	    		errors.put("raceGroup ", "Patient Race is required");
	    	}
	    	
	    	
	    	String swm = pRFDocumentVO.getSwm();
	    	String swf = pRFDocumentVO.getSwf();
	    	String idu = pRFDocumentVO.getIdu(); 
	    	String sexIdu = pRFDocumentVO.getSexIDU();
	    	String sexBI = pRFDocumentVO.getSexBI();
	    	String  sexAIDSHIV = pRFDocumentVO.getSexAIDSHIV();
	    	String riskRecvBldProd = pRFDocumentVO.getRcvdTransfusion();
	    	String perinatalExposure = pRFDocumentVO.getPerinatalExposure();
	    	String riskOtherText = pRFDocumentVO.getRISK_OTHER_TEXT();
	    	
	    	
	    	StringBuilder riskErrors = new StringBuilder("");
	    	
	    	if(CommonUtil.isNullOrEmpty(swm) && CommonUtil.isNullOrEmpty(swf) &&  CommonUtil.isNullOrEmpty(idu) &&  CommonUtil.isNullOrEmpty(sexIdu) &&  CommonUtil.isNullOrEmpty(sexBI) &&  CommonUtil.isNullOrEmpty(sexAIDSHIV) && 
	    			CommonUtil.isNullOrEmpty(riskRecvBldProd) && CommonUtil.isNullOrEmpty(perinatalExposure) && CommonUtil.isNullOrEmpty(pRFDocumentVO.getRiskOther())){
	    		riskErrors.append("Risk is a required - One or more of the options for Patient Risk can be selected");
	    	}else{
		    	if("M".equals(sexAtBirth) && "Y".equals(swm) && !"Y".equals(swf) && ("Y".equals(sexIdu) || "Y".equals(sexBI) || "Y".equals(sexAIDSHIV))){
		    		riskErrors.append("Patient Sex at birth is male, sex with male is YES and sex with female is not YES. Any heterosexual risks shouldn't be YES.");
		    	}else if("F".equals(sexAtBirth) && !"Y".equals(swm) && "Y".equals(swf) && ("Y".equals(sexIdu) || "Y".equals(sexBI) || "Y".equals(sexAIDSHIV))){
		    		riskErrors.append("Patient Sex at birth is female, sex with male is not YES and sex with female " +
							"is YES. Any heterosexual risks shouldn't be YES.");
		    	}
		    	
		    	if("Y".equals(perinatalExposure) && ("Y".equals(swm) || "Y".equals(swf) || "Y".equals(idu) || "Y".equals(sexIdu) || "Y".equals(sexBI) || "Y".equals(sexAIDSHIV) || "Y".equals(riskRecvBldProd) || !"".equals(riskOtherText))){
		    		riskErrors.append("Patient Perinatal Exposure risk is YES, all adult risks should be No." +
							"Other Risk text box should be blank.");
		    	}
	    	}
	    	
	    	if(!"".equals(riskErrors.toString())){
	    		errors.put("PATIENT_RISK",riskErrors.toString());
	    	}
	    	
	    	
	    	List<PRFPartnerVO> partnersList = pRFDocumentVO.getPartnerList(); 
	    	if(partnersList != null && partnersList.size() > 0){
	    				int index = -1;
	    	
			    	for (PRFPartnerVO pRFPartnerVO : partnersList) {
			    		index++;
			    		
			    		
			    		String _prfParterToString = pRFPartnerVO.toString();
			    		
			    		_prfParterToString = _prfParterToString.replaceAll("null", "").replaceAll("false", "");
			    		
			    		
			    		
			    		
			    		if(CommonUtil.isNotNullAndEmpty(_prfParterToString)){
			    			
			    			if(CommonUtil.isNullOrEmpty(pRFPartnerVO.getLname())){
			        			errors.put("partnerList_"+index+"_lname","Last Name is required");
			    			}
			    		
			
			        		String _partnerAliasLname = pRFPartnerVO.getAliasLname();
			        		String _partnerAliasFname = pRFPartnerVO.getAliasFname();
			        		String _partnerAliasMname = pRFPartnerVO.getAliasMname();
			
			        		if (((CommonUtil.isNotNullAndEmpty(_partnerAliasFname) || CommonUtil.isNotNullAndEmpty(_partnerAliasMname)) || CommonUtil.isNotNullAndEmpty(_partnerAliasLname) ) && (CommonUtil.isNullOrEmpty(_partnerAliasFname) || CommonUtil.isNullOrEmpty(_partnerAliasLname))) {
			        			errors.put("partnerList_"+index+"_aliasLname","Patient Alias or Maiden Last Name is required");
			        		}
			        		
			        		String partnerZip = pRFPartnerVO.getZip();
			        		
			        		if (CommonUtil.isNotNullAndEmpty(partnerZip) && (partnerZip.length() != 5 || !CommonUtil.isNumeric(partnerZip))) {
			    				errors.put("partnerList_"+index+"_zip","Partner zip code must be exactly 5 numeric characters.");
			    			}
			        		
			        		if ("M".equals(pRFPartnerVO.getSexAtBirth()) && "Y".equals(pRFPartnerVO.getPregnant())) {
			    				errors.put("partnerList_"+index+"_pregnant","When sex at birth is Male pregnant status should not be yes.");
			    			}
			        		
			        		if (CommonUtil.isNotNullAndEmpty(pRFPartnerVO.getTelephoneNo1Type1()) && CommonUtil.isNullOrEmpty(pRFPartnerVO.getTelephone1())) {
			        			errors.put("partnerList"+index+"_telephone1","Telephone number is required when the Telephone type is selected");
			    			}
			        		
			        		if (CommonUtil.isNotNullAndEmpty(pRFPartnerVO.getTelephoneNo2Type2()) && CommonUtil.isNullOrEmpty(pRFPartnerVO.getTelephone2())) {
			        			errors.put("partnerList"+index+"_telephone2","Telephone number is required when the Telephone type is selected");
			    			}
			        		
			        		
			        		
			        		
			        		
			        		if(pRFPartnerVO.isRaceOther() && !CommonUtil.isNotNullAndEmpty(pRFPartnerVO.getRaceOtherText())){
			        			errors.put("partnerList_"+index+"_raceOtherText","Partner other race is selected, please enter the value in " +
			    						"other text box.");
			        		}
			        		Integer _age = pRFPartnerVO.getAge();
			        		if (_age != null && (pRFPartnerVO.getAge() < 0 || pRFPartnerVO.getAge() > maxAge)) {
			        			errors.put("partnerList_"+index+"_age","Partner Estimated Age shouldn't greater than " + maxAge);
			    			}
			        		
/*			        		if (("Y").equals(pRFPartnerVO.getDvScreen()) && ("NA").equals(pRFPartnerVO.getDvRisk())) {
			        			errors.put("partnerList_"+index+"_dvRisk", "Partner DV Screening was done. " +
			    						"Please Enter the Result in DV Risk OR Leave it Blank if No Result.");
			    			}
			        		if (("N").equals(pRFPartnerVO.getDvScreen()) && ("N").equals(pRFPartnerVO.getDvRisk())) {
			        			errors.put("partnerList_"+index+"_dvRisk", "Partner : You Cannot Indicate 'No' Risk without a DV Screening. " +
			    						"Please Correct DV Risk or DV Screen.");
			    			}
			        		if (("NA").equals(pRFPartnerVO.getDvScreen()) && ("N").equals(pRFPartnerVO.getDvRisk())) {
			        			errors.put("partnerList_"+index+"_dvRisk", "Partner: You have Indicated DV Screening Was 'NA'. " +
			    						"Please Correct the DV Risk.");
			    			}
			        		
			        		if (("").equals(pRFPartnerVO.getDvScreen()) && ("N").equals(pRFPartnerVO.getDvRisk())) {
			        			errors.put("partnerList_"+index+"_dvRisk", "Partner: You Cannot Indicate 'No' Risk without a DV Screening. " +
			    						"Please Correct the DV Risk or DV Screen.");
			    			}*/
			        		if (CommonUtil.isNotNullAndEmpty(pRFPartnerVO.getDvScreen()) && pRFPartnerVO.getDvScreen().equals("Y") && CommonUtil.isNullOrEmpty(pRFPartnerVO.getDvRisk()) ) {
			        			errors.put("partnerList_"+index+"_dvRisk", "Partner Domestic violence screen done is marked as Yes, please mark the risk of domestic violence identified?.");
			        		}			        		
			        		
			        		
			        		if (CommonUtil.isNotNullAndEmpty(pRFPartnerVO.getPartnerAssistanceStatus()) && (pRFPartnerVO.getPartnerAssistanceStatus().equals("2")
			        				|| pRFPartnerVO.getPartnerAssistanceStatus().equals("4a") || pRFPartnerVO.getPartnerAssistanceStatus().equals("4b") )
			        				&& CommonUtil.isNullOrEmpty(pRFPartnerVO.getPartnerNotifiedDate())) {
			        			errors.put("partnerList_"+index+"_partnerNotifiedDate", 
			        					"Partner Assistance Status is Notified by Provider or Prov Conf Pt Notf Partner or Pt Reports Notif Partner, Date Notified is required.");
			        		}
			        		
			        		
			        		if(pRFPartnerVO.isRaceOther() && ("HISPANIC".equalsIgnoreCase(pRFPartnerVO.getRaceOtherText()) ||  "Latino".equalsIgnoreCase(pRFPartnerVO.getRaceOtherText()))){
			        			errors.put("partnerList_"+index+"_raceOtherText", "Partner other race can't be Hispanic or Latino. " +
			    						"Use ethnicity field to record Hispanic or non-Hispanic.");
			        		}
			        		
			        		
			        		if((pRFPartnerVO.isRaceWhite() || pRFPartnerVO.isRaceAsian() || pRFPartnerVO.isRaceBlack() || pRFPartnerVO.isRaceIndian() || pRFPartnerVO.isRaceOther() || pRFPartnerVO.isRacePacific() ) &&  
			        		pRFPartnerVO.isRaceUnknown()){
			        			errors.put("partnerList_"+index+"_raceUnknown", "partner Unknown Race can't be selected if any other race is selected.");
			        		}
			        		
			        		
			        		if("M".equals(pRFPartnerVO.getGender()) && "Y".equals(pRFPartnerVO.getPregnant())){
			        			errors.put("partnerList_"+index+"_gender", "Partner is a Pregnant Male? Please correct Gender or Pregnant Status.");
			        		}
			        		
			        		String emailAddress = pRFPartnerVO.getEmail(); 
			        		
			        		if(CommonUtil.isNotNullAndEmpty(emailAddress) && !validateEmail(emailAddress)){
			        			errors.put("partnerList_"+index+"_email", "Partner Email Address is not valid.");
			        		}
			    			
			    			
			    		}
			    		
			    		
			    		
			    		
					}
	    	}
	    	String completedEmailAddress = pRFDocumentVO.getCOMPLETER_EMAIL();
	    	if(CommonUtil.isNotNullAndEmpty(completedEmailAddress) && !validateEmail(completedEmailAddress)){
				errors.put("COMPLETER_EMAIL", "Completer Email Address is not valid.");
			}
/*    	}else{
    		if(!pRFDocumentVO.isINIT_HIV_DIAG() && !pRFDocumentVO.isPREV_HIV_DIAG() && !pRFDocumentVO.isINIT_AIDS_DIAG() && !pRFDocumentVO.isPREV_AIDS_DIAG()){
        		errors.put("TYPE_OF_REPORT_SECTION", "At least one Patient Type Report must be selected.");
        	}
        	
        	String personZip = pRFDocumentVO.getPERSON_ZIP();
        	
        	if (CommonUtil.isNotNullAndEmpty(personZip) && (personZip.length() != 5 || !CommonUtil.isNumeric(personZip))) {
    			errors.put("PERSON_ZIP","Person zip code must be exactly 5 numeric characters.");
    		}
        	
        	String _din = pRFDocumentVO.getDIN();
        	
        	if(CommonUtil.isNotNullAndEmpty(_din) && (_din.length() != 7  || !checkDinFormat(_din))){
        		errors.put("DIN", "Patient DIN code must be exactly 7 characters, with 3rd being a letter (A-Z) and " +
    					"rest should be numbers (Example: 99X9999).");
        	}
        	if(pRFDocumentVO.isRACE_OTHER() && !CommonUtil.isNotNullAndEmpty(pRFDocumentVO.getRACE_OTHER_TEXT())){
        		errors.put("RACE_OTHER_TEXT", "Patient other race is selected, please enter the value in other text box.");
        	}
        	
        	if ((CommonUtil.isNotNullAndEmpty(pRFDocumentVO.getALT_FNAME()) || CommonUtil.isNotNullAndEmpty(pRFDocumentVO.getALT_MNAME())) && !CommonUtil.isNotNullAndEmpty(pRFDocumentVO.getALT_LNAME())) {
        		errors.put("ALT_LNAME", "Enter Patient Alternate's Last Name.");  
    		}
        	
        	if(pRFDocumentVO.isRACE_OTHER() && "HISPANIC".equalsIgnoreCase(pRFDocumentVO.getRACE_OTHER_TEXT())){
        		errors.put("RACE_OTHER_TEXT", "Patient other race can't be Hispanic. Use ethnicity field to record Hispanic or non-Hispanic.");
        	}
        	
        	if((pRFDocumentVO.isRACE_WHITE() || pRFDocumentVO.isRACE_BLACK() || pRFDocumentVO.isRACE_ASIAN() || pRFDocumentVO.isRACE_PACIFIC() || pRFDocumentVO.isRACE_INDIAN() || pRFDocumentVO.isRACE_OTHER()) && pRFDocumentVO.isRACE_UNKNOWN()){
        		errors.put("RACE_UNKNOWN_SECTION", "Patient Unknown Race can't be selected if any other race is selected.");
        	}
        	
        	String patientGender = pRFDocumentVO.getGENDER();
        	String swm = pRFDocumentVO.getSwm();
        	String swf = pRFDocumentVO.getSwf();
        	String idu = pRFDocumentVO.getIdu(); 
        	String sexIdu = pRFDocumentVO.getSexIDU();
        	String sexBI = pRFDocumentVO.getSexBI();
        	String  sexAIDSHIV = pRFDocumentVO.getSexAIDSHIV();
        	String riskRecvBldProd = pRFDocumentVO.getRcvdTransfusion();
        	String riskOtherText = pRFDocumentVO.getRISK_OTHER_TEXT();
        	
        	
        	StringBuilder riskErrors = new StringBuilder("");
        	
        	if("M".equals(patientGender) && "Y".equals(swm) && !"Y".equals(swf) && ("Y".equals(sexIdu) || "Y".equals(sexBI) || "Y".equals(sexAIDSHIV))){
        		riskErrors.append("Patient gender is male, sex with male is YES and sex with female is not YES. Any heterosexual risks shouldn't be YES.");
        	}else if("F".equals(patientGender) && !"Y".equals(swm) && "Y".equals(swf) && ("Y".equals(sexIdu) || "Y".equals(sexBI) || "Y".equals(sexAIDSHIV))){
        		riskErrors.append("Patient gender is female, sex with male is not YES and sex with female " +
    					"is YES. Any heterosexual risks shouldn't be YES.");
        	}
        	
        	if("Y".equals(pRFDocumentVO.getPerinatalExposure()) && (!"N".equals(swm) || !"N".equals(swf) || !"N".equals(idu) || !"N".equals(sexIdu) || !"N".equals(sexBI) || !"N".equals(sexAIDSHIV) || !"N".equals(riskRecvBldProd) || !"".equals(riskOtherText))){
        		riskErrors.append("Patient Perinatal Exposure risk is YES, all adult risks should be No." +
    					"Other Risk text box should be blank.");
        	}
        	
        	if(!"".equals(riskErrors.toString())){
        		errors.put("PATIENT_RISK",riskErrors.toString());
        	}
        	
        	
        	List<PRFPartnerVO> partnersList = pRFDocumentVO.getPartnerList(); 
        	if(partnersList != null && partnersList.size() >= 0){
        		int index = -1;
        	
	        	for (PRFPartnerVO pRFPartnerVO : partnersList) {
	        		index++;
	        		
	        		if(!CommonUtil.isNotNullAndEmpty(pRFPartnerVO.getLname())){
	        			continue;
	        		}
	        		
	        		
	        		if(!CommonUtil.isNotNullAndEmpty(pRFPartnerVO.getAliasLname()) && (CommonUtil.isNotNullAndEmpty(pRFPartnerVO.getAliasFname()) || CommonUtil.isNotNullAndEmpty(pRFPartnerVO.getAliasMname()))){
	        			errors.put("partnerList_"+index+"_aliasLname","Enter Partner Alias's Last Name.");
	        		}
	        		
	        		String partnerZip = pRFPartnerVO.getZip();
	        		
	        		if (CommonUtil.isNotNullAndEmpty(partnerZip) && (partnerZip.length() != 5 || !CommonUtil.isNumeric(partnerZip))) {
	    				errors.put("partnerList_"+index+"_zip","Partner zip code must be exactly 5 numeric characters.");
	    			}
	        		
	        		if(pRFPartnerVO.isRaceOther() && !CommonUtil.isNotNullAndEmpty(pRFPartnerVO.getRaceOtherText())){
	        			errors.put("partnerList_"+index+"_raceOtherText","Partner other race is selected, please enter the value in " +
	    						"other text box.");
	        		}
	        		Integer _age = pRFPartnerVO.getAge();
	        		if (_age != null && (pRFPartnerVO.getAge() < 0 || pRFPartnerVO.getAge() > maxAge)) {
	        			errors.put("partnerList_"+index+"_age","Partner Estimated Age shouldn't greater than " + maxAge);
	    			}
	        		
	        		if (("Y").equals(pRFPartnerVO.getDvScreen()) && ("NA").equals(pRFPartnerVO.getDvRisk())) {
	        			errors.put("partnerList_"+index+"_dvRisk", "Partner DV Screening was done. " +
	    						"Please Enter the Result in DV Risk OR Leave it Blank if No Result.");
	    			}
	        		if (("N").equals(pRFPartnerVO.getDvScreen()) && ("N").equals(pRFPartnerVO.getDvRisk())) {
	        			errors.put("partnerList_"+index+"_dvRisk", "Partner : You Cannot Indicate 'No' Risk without a DV Screening. " +
	    						"Please Correct DV Risk or DV Screen.");
	    			}
	        		if (("NA").equals(pRFPartnerVO.getDvScreen()) && ("N").equals(pRFPartnerVO.getDvRisk())) {
	        			errors.put("partnerList_"+index+"_dvRisk", "Partner: You have Indicated DV Screening Was 'NA'. " +
	    						"Please Correct the DV Risk.");
	    			}
	        		
	        		if (("").equals(pRFPartnerVO.getDvScreen()) && ("N").equals(pRFPartnerVO.getDvRisk())) {
	        			errors.put("partnerList_"+index+"_dvRisk", "Partner: You Cannot Indicate 'No' Risk without a DV Screening. " +
	    						"Please Correct the DV Risk or DV Screen.");
	    			}
	        		
	        		
	        		if(pRFPartnerVO.isRaceOther() && "HISPANIC".equalsIgnoreCase(pRFPartnerVO.getRaceOtherText())){
	        			errors.put("partnerList_"+index+"_raceOtherText", "Partner other race can't be Hispanic. " +
	    						"Use ethnicity field to record Hispanic or non-Hispanic.");
	        		}
	        		
	        		
	        		if((pRFPartnerVO.isRaceWhite() || pRFPartnerVO.isRaceAsian() || pRFPartnerVO.isRaceBlack() || pRFPartnerVO.isRaceIndian() || pRFPartnerVO.isRaceOther() || pRFPartnerVO.isRacePacific() ) &&  
	        		pRFPartnerVO.isRaceUnknown()){
	        			errors.put("partnerList_"+index+"_raceUnknown", "Partner Unknown Race can't be selected if any other race is selected.");
	        		}
	        		
	        		
	        		if("M".equals(pRFPartnerVO.getGender()) && "Y".equals(pRFPartnerVO.getPregnant())){
	        			errors.put("partnerList_"+index+"_gender", "Partner is a Pregnant Male? Please correct Gender or Pregnant Status.");
	        		}
	        		
	        		String emailAddress = pRFPartnerVO.getEmail(); 
	        		
	        		if(CommonUtil.isNotNullAndEmpty(emailAddress) && !validateEmail(emailAddress)){
	        			errors.put("partnerList_"+index+"_email", "Partner Email Address is not valid.");
	        		}
	        		
	        		
	    		}
        	}
        	String completedEmailAddress = pRFDocumentVO.getCOMPLETER_EMAIL();
        	if(CommonUtil.isNotNullAndEmpty(completedEmailAddress) && !validateEmail(completedEmailAddress)){
    			errors.put("COMPLETER_EMAIL", "Completer Email Address is not valid.");
    		}
    	}*/
    	
    	
    	if(errors.size() > 0){
    		
    		Map<Object,Object> resultMap=((Map<Object,Object>)result.getTarget());
    		
    		resultMap.put("errors", errors);
    		
    		commonExceptionHandler.inspectDBTransacionResult(-301, "PRF form has errors, please correct it", result);
    		
    	}else{
    		commonExceptionHandler.inspectDBTransacionResult(0, "PRF has not errors.", result);
    	}
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    }
    
    @Override
    public int deletePRFPartners(int documentID){
    	return pRFPartnerDAO.deletePRFPartners(documentID);
    }
    
    @Override
    public int updatePrfDocStatusInRawTable(int docId, int current_process_Status, BindingResult bindingResult,
			UserObject userObject){
    	
    	Map<Object, Object> inputParameterMap = new HashMap<Object, Object>();
    	
    	inputParameterMap.put("docId", docId);
    	inputParameterMap.put("userId", userObject.getUserId());
    	inputParameterMap.put("current_process_Status", current_process_Status);
    	
    	return pRFDocumentDAO.updatePrfDocStatusInRawTable(inputParameterMap);
    	
    	
    }
    
    public void processRawPrfDoc(int docId, BindingResult bindingResult,
			UserObject userObject){
    	

    	Map<Object, Object> inputParameterMap = new HashMap<Object, Object>();

    	inputParameterMap.put("docId", docId);
    	inputParameterMap.put("userId", userObject.getUserId());
    	inputParameterMap.put("current_process_Status", CommonConstants.RAW_NEW_RECORD_PROCESS_STATUS);
    	
    	int resultCount = pRFDocumentDAO.updatePrfDocStatusInRawTable(inputParameterMap);
    	
    	if(resultCount > 0){
    		commonExceptionHandler.inspectDBTransacionResult(0, "Successfully Processed the Raw PRF document", bindingResult);
    	}else{
    		commonExceptionHandler.inspectDBTransacionResult(-1, "Unable to Process the PRF document, please contact helpline.", bindingResult);
    	}
    }
    
    private static boolean validateEmail(String email){
		boolean isValid =  false;

		try {
			InternetAddress iA = new InternetAddress(email);
			iA.validate();
			isValid = true;
		} catch (AddressException e) {}

		return isValid;
	}
    
    private static boolean checkDinFormat(String _inputValue){
    	String regEx = "[0-9][0-9][A-Za-z][0-9][0-9][0-9][0-9]";

		Pattern pattern = Pattern.compile(regEx);

		Matcher matcher = pattern.matcher(_inputValue);

		if (!matcher.matches()) {
			return false;
		}else{
			return true;
		}
    }
    
    @Override
    public int writeRawPRFRecordDataToStdTable(Map<Object, Object> docVO, BigDecimal documentId){
    	
    	docVO.put("FNAME", CommonUtil.NameValidation((String)docVO.get("FNAME")));
		docVO.put("MNAME", CommonUtil.NameValidation((String)docVO.get("MNAME")));
		docVO.put("LNAME", CommonUtil.NameValidation((String)docVO.get("LNAME")));
		
		docVO.put("DIN", CommonUtil.dinValidation((String)docVO.get("DIN")));
		docVO.put("PERSON_ADDRESS", CommonUtil.addressValidation((String)docVO.get("PERSON_ADDRESS")));
		docVO.put("NYSID", CommonUtil.nysidValidation((String)docVO.get("NYSID")));
		docVO.put("MED_RECORD_NUMBER", CommonUtil.normalizeString((String)docVO.get("MED_RECORD_NUMBER")));
		
		
		String RISK_OTHER_TEXT = (String)docVO.get("RISK_OTHER_TEXT");
		
		if(CommonUtil.isNotNullAndEmpty(RISK_OTHER_TEXT)){
			
			docVO.put("RISK_OTHER", "Y");
		}
		
		
		String RECV_BLOOD_PROD_DATE_TEXT = (String)docVO.get("RECV_BLOOD_PROD_DATE_TEXT");
		
		
		if(CommonUtil.isNotNullAndEmpty(RECV_BLOOD_PROD_DATE_TEXT)){
			
			docVO.put("RECV_BLOOD_PROD", "Y");
		}
		
		int resultCount = pRFDocumentDAO.writeRawPRFRecordDataToStdTable(docVO);
    	
    	return resultCount;
    }
    
    @Override
	public int getPrfReviewDocsCount(){
    	return pRFDocumentDAO.getPrfReviewDocsCount();

    }
    
    @Override
	public int writePRFDocAndPartnersData(Map<Object, Object> docVO,
			BigDecimal documentId){
		return documentTransactionHelper.writePRFDocAndPartnersData(docVO, documentId);
	}
    
    @Override
    public void writeRawPRFPartnerDataFromRawTable(BigDecimal documentId){

    		pRFDocumentDAO.writeRawPRFPartnerDataToStdTable(documentId.intValue());
    	
    }
    
    @Override
    @Transactional
    public int validateAndWriteRawPRFRecordDataToStdTable(
			Map<Object, Object> docVO, BigDecimal documentId){
    	
    	int resultCount = 0;

		BigDecimal existingProcessStatus = (BigDecimal) docVO.get("CURRENT_PROCESS_STATUS");
		
		
		if(existingProcessStatus.intValue() == 15){
			resultCount = writePRFDocAndPartnersData(docVO, documentId);	
		}else {
			
			Timestamp createDate = (Timestamp)docVO.get("CREATE_DATE");
			
			String dateOfBirthText = (String)docVO.get("DOB_TEXT");
			
			Timestamp dateOfBirthinDate = dateUtil.getSqlDateTimeStamp(dateOfBirthText);

			if(createDate != null && dateOfBirthinDate != null){
			
				long createDate_milliSeconds = createDate.getTime();
				long dateOfBirth_milliSeconds = dateOfBirthinDate.getTime();
	
				long milliSeconds = createDate_milliSeconds - dateOfBirth_milliSeconds;
				
				long days = milliSeconds / ( 24 * 60 * 60) / (1000);
				
				
				if(days<365){
					
					
					manualQuartzJobService.addValidationError(documentId, "Date of Birth to Enter date is less than 365 days.", 4, true, existingProcessStatus, "PRF");
					int _result =  manualQuartzJobService.writeErrorCode(4, documentId, "PRF");
					if(_result == 0){
						throw new DataRetrievalFailureException("Unable to update the validation error status for the document  "+documentId);
					}
					
					return 0;
					
					
				}
			}
			
			resultCount = writePRFDocAndPartnersData(docVO, documentId);	
			
			
			
			
		}
		return resultCount;
    	
    	
    }
}

