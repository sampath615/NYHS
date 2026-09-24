package gov.ny.state.doh.bhae.nyehms.web.spring.service.document;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.exp.StdHIEDTHDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;

import java.sql.Blob;
import java.sql.Date;
import java.util.Map;

import org.springframework.validation.BindingResult;
import org.w3c.dom.Document;

public interface HIEDTHDocumentService {
	
	public String convertBlobtoEncryptedEncodedString(Blob blob);
	
	public String decryptDecodeHIEDTHBinaryString (String encryptedEncodedString);
	
	public Document getDomDocumentFromHIEDTHString (String theCCDString,Integer uphn_id);
	
	public void storeRawHIEDTHDocVOFromDocument(String fileString, Integer uphn_id, String hieName, Date myDate);
	
	public int writeRawHIEDTHRecordDataToStdTable(Map<Object, Object> stdEXPDocVO);
	
	public StdHIEDTHDocVO getHIEDTHDoc(
			int docId,
			BindingResult dummyBindingResult,
			UserObject userObject,
			String id);

}
