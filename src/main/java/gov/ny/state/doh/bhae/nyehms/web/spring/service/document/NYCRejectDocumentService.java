package gov.ny.state.doh.bhae.nyehms.web.spring.service.document;

import java.util.List;

import org.springframework.validation.BindingResult;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.person.NYCRejectVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.uploads.UploadNYCSurvRejectVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;

public interface NYCRejectDocumentService {
	
	public List<NYCRejectVO> getNYCRejectDetails(int documentId, BindingResult result, UserObject userObject);
	
	public void updateNYCRejectDocumentComments(int documentId, String comments, UserObject userObject);

}
