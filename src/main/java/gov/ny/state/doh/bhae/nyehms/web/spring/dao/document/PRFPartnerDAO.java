package gov.ny.state.doh.bhae.nyehms.web.spring.dao.document;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.PRFPartnerVO;

import java.util.LinkedList;

import org.springframework.dao.DataAccessException;

public interface PRFPartnerDAO {	
	public LinkedList<PRFPartnerVO> getPRFPartner(int docId)throws DataAccessException;
	
	public int insertPRFPartner(PRFPartnerVO stdPRFPartnerVO);

	public int deletePRFPartners(int documentID);

	public LinkedList<PRFPartnerVO> getStdPRFPartner(int documentID);
}
