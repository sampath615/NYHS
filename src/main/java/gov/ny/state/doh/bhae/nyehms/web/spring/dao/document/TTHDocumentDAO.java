package gov.ny.state.doh.bhae.nyehms.web.spring.dao.document;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.RawTTHDocVO;

import org.springframework.dao.DataAccessException;

public interface TTHDocumentDAO {	
	//public ADAPDocVO  getADAPDoc(int docId)throws DataAccessException;
	public int insertTTHDocData(RawTTHDocVO rawTTHDocVO)throws DataAccessException;
	public int updateTTHDocData(RawTTHDocVO rawTTHDocVO)throws DataAccessException;
	public RawTTHDocVO getTTHDocData(RawTTHDocVO rawTTHDocVO)throws DataAccessException;
	
}
