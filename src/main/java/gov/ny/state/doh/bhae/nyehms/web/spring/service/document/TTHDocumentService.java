package gov.ny.state.doh.bhae.nyehms.web.spring.service.document;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.RawTTHDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;

import org.springframework.validation.BindingResult;

public interface TTHDocumentService {
	
	//public ADAPDocVO  getADAPDoc(int docId, BindingResult result, UserObject userObject, String sessionID);
	
	public void insertTTHDocData(RawTTHDocVO rawTTHDocVO, BindingResult result, UserObject userObject, String sessionID);	
	
	public void updateTTHDocData(RawTTHDocVO rawTTHDocVO, BindingResult result, UserObject userObject, String sessionID);
	
	public RawTTHDocVO getTTHDocData(RawTTHDocVO rawTTHDocVO, BindingResult result, UserObject userObject, String sessionID);

}
