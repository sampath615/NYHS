package gov.ny.state.doh.bhae.nyehms.web.spring.service.document;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.dataentry.ICCRDataEntryVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.dataentry.ICCRTestVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.record.AcrfTestResult;
import gov.ny.state.doh.bhae.nyehms.web.spring.record.IccrDocData;
import gov.ny.state.doh.bhae.nyehms.web.spring.repository.document.AcrfTestResultRepository;
import gov.ny.state.doh.bhae.nyehms.web.spring.repository.document.IccrDocRepository;
import gov.ny.state.doh.bhae.nyehms.web.spring.repository.document.IccrTestRepository;
import gov.ny.state.doh.bhae.nyehms.web.spring.repository.document.RawIccrDocRepository;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonExceptionHandler;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonUtil;

@Service
public class ICCRDocumentService {
	
	@Autowired private CommonExceptionHandler commonExceptionHandler;	
	@Autowired private DocumentTransactionHelper documentTransactionHelper;
	
	@Autowired private IccrDocRepository iccrDocRepository;
	@Autowired private RawIccrDocRepository rawIccrDocRepository;
	@Autowired private IccrTestRepository iccrTestRepository;
	@Autowired private AcrfTestResultRepository acrfTestResultRepository;
	
	private static Logger log = LoggerFactory.getLogger(ICCRDocumentService.class);


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

	public List<AcrfTestResult> getCodedTestTypeResult(String testType) {
		return acrfTestResultRepository.findCurrentResultsOfTest(testType);
	}

	/**
	 * Saves an ICCR report entered on the data entry screen (RAW_ICCR_DOC) with the tests filled in
	 * on it (RAW_ICCR_TESTS).
	 */
	public void insertICCRRawData(ICCRDataEntryVO iccrDataEntryVO, UserObject userObject) {
		rawIccrDocRepository.saveAndFlush(IccrDocMapping.toRawDoc(iccrDataEntryVO));   // flush: insert now, before the tests

		List<ICCRTestVO> iccrTestDataList = iccrDataEntryVO.getIccrTestList();
		if (iccrTestDataList == null) {
			return;
		}
		for (ICCRTestVO iccrTestVO : iccrTestDataList) {
			if (isFilledIn(iccrTestVO)) {
				iccrTestRepository.insertTest(iccrDataEntryVO.getDOCUMENT_ID(),
						OracleConversions.fnToDate(iccrTestVO.getDATE_OF_TEST()),
						iccrTestVO.getTEST_TYPE(),
						iccrTestVO.getTEST_RESULTS(),
						iccrTestVO.getTEST_RESULT_COPIES(),
						iccrTestVO.getTEST_RESULT_LOGS());
			}
		}
	}

	/** A test row of the form is saved if its type, date ("../../...." is the empty date mask) or result is filled in. */
	private static boolean isFilledIn(ICCRTestVO iccrTestVO) {
		return CommonUtil.isNotNullAndEmpty(iccrTestVO.getTEST_TYPE())
				|| (!"../../....".equals(iccrTestVO.getDATE_OF_TEST()) && CommonUtil.isNotNullAndEmpty(iccrTestVO.getDATE_OF_TEST()))
				|| CommonUtil.isNotNullAndEmpty(iccrTestVO.getTEST_RESULTS());
	}

	public IccrDocData getICCRDocument(int docId) {
		IccrDocData iccrDocVO = iccrDocRepository.findDocument(docId);
		return iccrDocVO.withIccrTestList(iccrTestRepository.findTestsOfDocument(docId));
	}

	/**
	 * Writes the standardized report (STD_ICCR_DOC) of a received report.
	 *
	 * @param docVO the received report (a RAW_ICCR_DOC row), value per column name; not changed
	 * @return 1
	 */
	public int writeRawICCRDocDataToStdTable(Map<Object, Object> docVO, BigDecimal documentId) {
		iccrDocRepository.saveAndFlush(IccrDocMapping.toStdDoc(docVO));
		return 1;
	}

}
