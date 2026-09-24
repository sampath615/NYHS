package gov.ny.state.doh.bhae.nyehms.web.spring.dao.document;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.NycPRFPartnerVO;

import java.util.LinkedList;

import org.springframework.dao.DataAccessException;

public interface NycPRFPartnerDAO {
	
	public LinkedList<NycPRFPartnerVO> getNycPRFPartner(int docId)throws DataAccessException;
}
