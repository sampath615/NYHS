package gov.ny.state.doh.bhae.nyehms.web.spring.dao.document;

import java.util.List;
import java.util.Map;


import gov.ny.state.doh.bhae.nyehms.web.spring.domain.person.NYCRejectVO;

public interface NYCRejectDocumentDAO {

	public List<NYCRejectVO> getNYCRejectDetails(Map<Object, Object> inputOutputParameterMap);
	
	public int updateNYCRejectDocumentComments(Map<String, Object> nycRejectDocumentMap);
	
}
