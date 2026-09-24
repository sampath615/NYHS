package gov.ny.state.doh.bhae.nyehms.web.spring.service.document;

import java.math.BigDecimal;
import java.util.Map;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.PRFDocumentVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.PRFPartnerVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.person.PersonViewVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;

import org.springframework.validation.BindingResult;

/**
 * This interface defines methods for interacting with PRF document.
 * 
 * @author vxp06
 */
public interface PRFDocumentService {

    /**
     * This method accepts document id and returns PRF document associated with
     * that document id.
     *
     * @param documentID
     * @return
     */
    public PRFDocumentVO getPRFDocument(int documentID);

    /**
     * This method accepts prf vo object which contains following 3 things
     *
     * 1. PRF fields are inserted into RAW PRF table 2. List of TTH fields is
     * inserted into RAW_TTH_DOC table 3. List of Partners is inserted into RAW
     * Partner table
     *
     * @param prfFormVO
     * @param result
     * @param userObject
     * @param sessionID
     */
    public int insertPRFDoc(PRFDocumentVO pRFDocumentVO, BindingResult result, UserObject userObject);

	public PersonViewVO getStdPRFDocumentForClericalView(int documentId);

	public PRFDocumentVO getCompleteRawPRFDocumentContent(int docId);

	public void updateRawPrfDocument(PRFDocumentVO pRFDocumentVO,
			boolean isValidationFailed, BindingResult bindingResult, UserObject userObject);

	public void isValidPrfDoc(PRFDocumentVO pRFDocumentVO,
			BindingResult bindingResult);

	public int insertPRFPartner(PRFPartnerVO prfPartnerVO, BindingResult result,
            UserObject userObject);

	public int deletePRFPartners(int currentDocumentId);

	public void deleteRawPrfDoc(int docId, BindingResult bindingResult,
			UserObject userObject);

	public int updatePrfDocStatusInRawTable(int docId, int current_process_Status, BindingResult bindingResult,
			UserObject userObject);

	public void processRawPrfDoc(int docId, BindingResult bindingResult,
			UserObject userObject);

	public int writeRawPRFRecordDataToStdTable(Map<Object, Object> docVO, BigDecimal documentId);

	public int getPrfReviewDocsCount();
	
	public int writePRFDocAndPartnersData(Map<Object, Object> docVO,
			BigDecimal documentId);

	public void writeRawPRFPartnerDataFromRawTable(BigDecimal documentId);

	public int validateAndWriteRawPRFRecordDataToStdTable(
			Map<Object, Object> docVO, BigDecimal documentId);
}
