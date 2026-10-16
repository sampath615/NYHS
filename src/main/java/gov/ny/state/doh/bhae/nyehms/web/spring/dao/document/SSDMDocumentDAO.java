package gov.ny.state.doh.bhae.nyehms.web.spring.dao.document;


import java.util.Map;


/**
 * This is interface for SSDM documents. Please see SSDMDocumentDAO.xml for its
 * implementations.
 *
 * @author vxp06
 */
public interface SSDMDocumentDAO {

	public int writeStdDocData(Map<Object, Object> docVO);

	public int writeAllRawSSDMDocDataToStdTable();

	public int updateRawDocumentStatus();

	public void documentInitialValidation();

}
