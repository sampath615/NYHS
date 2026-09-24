package gov.ny.state.doh.bhae.nyehms.web.spring.dao.document;

import java.util.List;
import java.util.Map;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.CancerDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.person.PersonDocHistoryVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.person.PersonViewVO;

public interface CancerDocumentDAO {

	public PersonViewVO getClericalDocumentAsPerson(int documentId);

	public List<PersonDocHistoryVO> getCancerDocumentDetails(Map<Object, Object> map);

	public CancerDocVO getCancerDocData(int docId);

	public int updateCancerMotherStatenoDetails(Map<String, Object> inputParameterMap);

	public int updateNotaMatchDetails(Map<String, Object> inOutParameterMap);
	
	public int getTotalCancerDocCount();

	public Map<String, Object> getCancerDocumentToReview(Map<Object, Object> inOutParamaterMap);

}
