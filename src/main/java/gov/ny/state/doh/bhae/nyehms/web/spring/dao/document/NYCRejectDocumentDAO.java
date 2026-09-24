package gov.ny.state.doh.bhae.nyehms.web.spring.dao.document;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.person.NYCRejectVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.person.PSMessageVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.uploads.UploadNYCSurvRejectVO;

public interface NYCRejectDocumentDAO {

	public List<NYCRejectVO> getNYCRejectDetails(Map<Object, Object> inputOutputParameterMap);
	
	public int updateNYCRejectDocumentComments(Map<String, Object> nycRejectDocumentMap);
	
}
