package gov.ny.state.doh.bhae.nyehms.web.spring.dao.assignment;

import java.util.List;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.acrf.RawAcrfDocVO;

import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Transactional;

public interface AcrfDocDAO {
	
	public RawAcrfDocVO getRawACRFDoc(String documentId)throws DataAccessException;
	@Transactional
	public void addRawACRFDoc(RawAcrfDocVO rawAcrfDoc)throws DataAccessException;
	@Transactional
	public void updateRawACRFDoc(RawAcrfDocVO rawAcrfDoc)throws DataAccessException;
	public List<String> getArvUserTypeList();
	public RawAcrfDocVO getAcrfFormDataFromAcrf(String stateNo);

	
}
