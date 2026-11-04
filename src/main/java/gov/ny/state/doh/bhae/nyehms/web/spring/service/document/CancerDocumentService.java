package gov.ny.state.doh.bhae.nyehms.web.spring.service.document;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import gov.ny.state.doh.bhae.nyehms.web.spring.dao.document.CancerDocumentDAO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.CancerDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.person.PersonViewVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonExceptionHandler;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonUtil;

@Service
public class CancerDocumentService {
	
	@Autowired private CancerDocumentDAO cancerDocumentDAO;
	
	@Autowired CommonExceptionHandler commonExceptionHandler;
	

	public PersonViewVO getClericalDocumentAsPerson(int documentId) {
		return cancerDocumentDAO.getClericalDocumentAsPerson(documentId);
	}

	public CancerDocVO getCancerDoc(int docId, BindingResult bindingResult, UserObject userObject) {
		return cancerDocumentDAO.getCancerDocData(docId);
	}

	public void processCancerDocument(long documentId, String stateno, Long clusterId, String match_cd,
			BindingResult bindingResult, UserObject userObject) {
		
		Map<String, Object> inputParameterMap = new HashMap<>();

		inputParameterMap.put("DOCUMENT_ID", documentId);
		inputParameterMap.put("STATENO", stateno);
		
		inputParameterMap.put("p_worker_id", userObject.getWorkerId());
		
		if(CommonUtil.isNotNullAndEmpty(stateno)) {
			int resultCount = cancerDocumentDAO.updateCancerMotherStatenoDetails(inputParameterMap);
			
			if(resultCount > 0) {
				  commonExceptionHandler.inspectDBTransacionResult(0, "Successfully matched the stateno", bindingResult);
				  
			  }else {
				  commonExceptionHandler.inspectDBTransacionResult(-1, "Failed to update the status to match, please reload the page and try" , bindingResult);
				  
			  }
		}else {
			int resultCount = cancerDocumentDAO.updateNotaMatchDetails(inputParameterMap);
			
			 if(resultCount > 0) {
				 commonExceptionHandler.inspectDBTransacionResult(0, "document ("+documentId+") marked as Not a Match", bindingResult);
			 }else {
				 commonExceptionHandler.inspectDBTransacionResult(-1, "Failed to update the document("+documentId+") status to Not a match, please contact helpline", bindingResult);
			 }

	 		
		}
		
		
		
		

	}
}
