package gov.ny.state.doh.bhae.nyehms.web.spring.service.document;

import gov.ny.state.doh.bhae.nyehms.web.spring.dao.document.NycPRFDocumentDAO;
import gov.ny.state.doh.bhae.nyehms.web.spring.entity.document.StdNycprfPartner;
import gov.ny.state.doh.bhae.nyehms.web.spring.record.NycPrfDocData;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.person.PersonViewVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.repository.document.NycprfDocRepository;
import gov.ny.state.doh.bhae.nyehms.web.spring.repository.document.NycprfPartnerRepository;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

@Service
public class NycPRFDocumentService {

	@Autowired
	NycPRFDocumentDAO nycPRFDocumentDAO;
	@Autowired
	NycprfDocRepository nycprfDocRepository;
	@Autowired
	NycprfPartnerRepository nycprfPartnerRepository;
	@Autowired
	DocumentTransactionHelper documentTransactionHelper;	
	
	
	
	public NycPrfDocData getNycPRFDoc(int docId, BindingResult result, UserObject userObject, String sessionID) {
		return nycprfDocRepository.findDocument(docId)
				.withPartnerList(nycprfPartnerRepository.findPartnersOfDocument(docId));
	}
	
	/**
	 * Writes the standardized form (STD_NYCPRF_DOC) for a received NYC PRF record.
	 *
	 * @param docVO the received record (RAW_NYCPRF_DOC), value per field name
	 * @return the number of rows written
	 */
	public int writeRawNycPRFRecordDataToStdTable(Map<Object, Object> docVO) {
		nycprfDocRepository.saveAndFlush(NycPrfStdDocMapping.toStdDoc(docVO));   // flush: insert now, before the partners
		return 1;
	}

	/**
	 * Writes the standardized partners (STD_NYCPRF_PARTNER) of a document from the partners as received.
	 */
	public void writeRawNycPRFPartnerDataFromRawTable(BigDecimal documentId) {
		List<StdNycprfPartner> partners = nycprfPartnerRepository.findReceivedPartnersOfDocument(documentId.longValue()).stream()
				.map(NycPrfStdPartnerMapping::toStdPartner)
				.toList();
		nycprfPartnerRepository.saveAllAndFlush(partners);
	}

	public PersonViewVO getNYCPRFDocumentForClericalReview(int documentId){
		
		
		PersonViewVO nycPRFDocVO = nycPRFDocumentDAO.getNYCPRFDocumentForClericalReview(documentId);
    	
    	return nycPRFDocVO;
		
	}
	
	/**
	 * Standardizes a received NYC PRF form and its partners in one transaction (the import's entry point).
	 *
	 * @return the number of standardized forms written
	 */
	public int writeNYCPRFDocAndPartnersData(Map<Object, Object> docVO, BigDecimal documentId) {
		return documentTransactionHelper.writeNYCPRFDocAndPartnersData(docVO, documentId);
	}
}
