package gov.ny.state.doh.bhae.nyehms.web.spring.service.document;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import gov.ny.state.doh.bhae.nyehms.web.spring.dao.document.NYCRejectDocumentDAO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.person.NYCRejectVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.uploads.UploadNYCSurvRejectVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;

@RequiredArgsConstructor
@Service
public class NYCRejectDocumentService {
	
	
	private final NYCRejectDocumentDAO nycRejectDocumentDAO;

	public void updateNYCRejectDocumentComments(int documentId, String comments, UserObject userObject) {

		Map<String, Object> nycRejectDocumentMap = new HashMap<>();
		
		nycRejectDocumentMap.put("documentId", documentId);
		nycRejectDocumentMap.put("comments", comments);
		nycRejectDocumentMap.put("workerId", userObject.getWorkerId());
		
		nycRejectDocumentDAO.updateNYCRejectDocumentComments(nycRejectDocumentMap);
	}

	public List<NYCRejectVO> getNYCRejectDetails(int documentId, BindingResult result, UserObject userObject) {
		// TODO Auto-generated method stub
		Map<Object, Object> inputOutputParameterMap = new HashMap<>();
		
		inputOutputParameterMap.put("documentId", documentId);
		
		List<NYCRejectVO> nycRejectDetails = nycRejectDocumentDAO.getNYCRejectDetails(inputOutputParameterMap); 
		
		for (NYCRejectVO currentDoc : nycRejectDetails) {
			UploadNYCSurvRejectVO nycRejectVO = currentDoc.getRejectDocumentVO();
			nycRejectVO.setDISPOSITION_DESC(nycRejectVO.getDISPOSITION_CODE() + " - " + nycRejectVO.getDISPOSITION_DESC());
			currentDoc.setRejectDocumentVO(nycRejectVO);
		}
		
		return nycRejectDetails;
		
	}

}
