package gov.ny.state.doh.bhae.nyehms.web.spring.dao.document;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.exp.RawHIEDTHDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.exp.StdHIEDTHDocVO;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

public interface HIEDTHDocumentDAO 
{
	public void insertRawHIEDTHDocData(RawHIEDTHDocVO reddVO)
			throws DataAccessException;
	
	//public int writeRawEXPRecordDataToStdTable(Map<Object, Object> stdEXPDocVO);
	
	//public int insertEXPFromRAWToSTD(Map<Object, Object> stdDocData);
	
	//public int insertEXPFromRAWToSTD(Map<Object, Object> stdDocData);
	
	public int insertHIEDTHFromRAWToSTD(Map<Object, Object> stdDocData);
	
	public StdHIEDTHDocVO getStdHIEDTHDoc(int docId);
	
	public List<Map<Object, Object>> getNewRawHIEDTHList();
	
	//hie_death_doc implementation// added on 8/22/2019
	public void updateDuplicatesThatMoveToSTD();
	
	public void updateDuplicatesReceivedInSameFile();
	
	public void insertMpisPairInMPICHANGE();
	
    public void updateCPSinRAWComparingCCD();

	

}
