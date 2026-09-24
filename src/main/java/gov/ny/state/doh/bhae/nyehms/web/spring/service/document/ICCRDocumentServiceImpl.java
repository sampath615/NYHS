package gov.ny.state.doh.bhae.nyehms.web.spring.service.document;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import gov.ny.state.doh.bhae.nyehms.web.spring.dao.document.ICCRDocumentDAO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.dataentry.ICCRDataEntryVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.dataentry.ICCRTestVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.ICCRDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.person.PersonViewVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonExceptionHandler;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonUtil;

@Service
public class ICCRDocumentServiceImpl implements ICCRDocumentService {
	
	@Autowired private CommonExceptionHandler commonExceptionHandler;	
	@Autowired private DocumentTransactionHelper documentTransactionHelper;
	
	@Autowired private ICCRDocumentDAO iccrDocumentDAO;
	
	private static Logger log = LoggerFactory.getLogger(ICCRDocumentServiceImpl.class);


	public void saveICCRDataEntryForm(ICCRDataEntryVO iccrDataEntryVO, BindingResult bindingResult, UserObject userObject) {
		
		try{
			
			documentTransactionHelper.insertICCRRawData(iccrDataEntryVO, userObject, bindingResult);
		
			commonExceptionHandler.inspectDBTransacionResult(0, "ICCR Document Form saved successfully.", bindingResult);
			
		}catch(DataAccessException exception){
			
    		log.error("Error while Saving ICCR Document Form.  {}",exception.getMessage());
    		
    		commonExceptionHandler.handleException(exception, userObject, userObject.getSessionId());
    		
    		commonExceptionHandler.inspectDBTransacionResult(-1, exception.getMessage(), bindingResult);
    		
		} catch (Exception exception) {
			
			log.error("Error while Saving ICCR Document Form.  {}",exception.getMessage());
    		
    		commonExceptionHandler.handleException(exception, userObject, userObject.getSessionId());
    		
    		commonExceptionHandler.inspectDBTransacionResult(-1, "Error while Saving ICCR Document Form, please contact the Helpline.", bindingResult);
		}
		
	}

	@Override
	public List<JSONObject> getCodedTestTypeResult(String testType) {
		
		List<JSONObject> codedTestTypeValues = iccrDocumentDAO.getCodedTestTypeResult(testType);
		return codedTestTypeValues;
	}

	@Override
	public void insertICCRRawData(ICCRDataEntryVO iccrDataEntryVO, UserObject userObject) {
		
		int resultCount = iccrDocumentDAO.saveICCRDocumentData(iccrDataEntryVO);
		
		if(resultCount == 0){
			
			log.error("Failed while storing ICCR Document data.");
			
			throw new DataRetrievalFailureException("Error while saving ICCR Document, please contact the Helpline.");
		}
		
		List <ICCRTestVO> iccrTestDataList = iccrDataEntryVO.getIccrTestList();
		
		if(iccrTestDataList != null && iccrTestDataList.size() > 0){
			
			List<ICCRTestVO> finalICCRTestDataList = new ArrayList<ICCRTestVO>();

		
			for (ICCRTestVO iccrTestVO : iccrTestDataList) {
				if(CommonUtil.isNotNullAndEmpty(iccrTestVO.getTEST_TYPE()) 
						|| (!"../../....".equals(iccrTestVO.getDATE_OF_TEST()) && CommonUtil.isNotNullAndEmpty(iccrTestVO.getDATE_OF_TEST()) )
						|| CommonUtil.isNotNullAndEmpty(iccrTestVO.getTEST_RESULTS()) ) {
					finalICCRTestDataList.add(iccrTestVO);
				}
			}
			
			
			if(finalICCRTestDataList != null  && finalICCRTestDataList.size() > 0){
				
				Map<String, Object> inputParameterMap  = new HashMap<String, Object>();
				
				inputParameterMap.put("ICCR_DOCUMENT_ID", iccrDataEntryVO.getDOCUMENT_ID());
				inputParameterMap.put("finalICCRTestDataList", finalICCRTestDataList);
				
				resultCount = iccrDocumentDAO.saveICCRTestData(inputParameterMap);
				
	
				if(resultCount == 0){
					
					log.error("Failed while storing ICCR Test data.");
					
					throw new DataRetrievalFailureException("Error while saving ICCR Document, please contact the Helpline.");
				}
			}
		}
		
	}

	@Override
	public ICCRDocVO getICCRDocument(int docId) {
		
		ICCRDocVO iccrDocVO = iccrDocumentDAO.getICCRDocument(docId);
		
		List<ICCRTestVO> iccrDocTestList = iccrDocumentDAO.getICCRDocTestData(docId);
		
		iccrDocVO.setIccrTestList(iccrDocTestList);
		
		return iccrDocVO;
	}
	

	@Override
	public int writeRawICCRDocDataToStdTable(Map<Object, Object> docVO, BigDecimal documentId) {

		docVO.put("FNAME", CommonUtil.NameValidation((String)docVO.get("FIRST_NAME")));
		docVO.put("MNAME", CommonUtil.NameValidation((String)docVO.get("MIDDLE_NAME")));
		docVO.put("LNAME", CommonUtil.NameValidation((String)docVO.get("LAST_NAME")));
		docVO.put("PERSON_ADDRESS", CommonUtil.addressValidation((String)docVO.get("PERSON_ADDRESS")));
		
		docVO.put("PROV_NAME", CommonUtil.NameValidation((String)docVO.get("PROV_NAME")));
		docVO.put("PROV_ADDRESS", CommonUtil.addressValidation((String)docVO.get("PROV_ADDRESS")));		
		
		docVO.put("CURRENT_PROCESS_STATUS", 5);
		
    	return iccrDocumentDAO.writeStdDocData(docVO);
	}

	@Override
	public PersonViewVO getStdICCRDocumentForClericalReview(int documentId) {
		return iccrDocumentDAO.getStdICCRDocumentForClericalReview(documentId);
	}

}
