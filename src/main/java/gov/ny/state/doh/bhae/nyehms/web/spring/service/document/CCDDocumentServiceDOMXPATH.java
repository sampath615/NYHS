package gov.ny.state.doh.bhae.nyehms.web.spring.service.document;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.ccd.RawCCDDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;

import java.util.Map;

import org.springframework.validation.BindingResult;
import org.w3c.dom.Document;


public interface CCDDocumentServiceDOMXPATH {
	
	public Document getDomDocumentFromCCDString (String theCCDString, Integer uphn_id )
			throws Exception;
	//public Document getDomDocumentFromCCDString (String theCCDString, Integer uphn_id ) throws ParserConfigurationException, IOException;
	
	public RawCCDDocVO getRawCCDDocVOFromDocument(Document d, Integer uphn_id, String clearText);
	
	public RawCCDDocVO getCCDDoc(
			int docId,
			BindingResult dummyBindingResult,
			UserObject userObject,
			String id);
	
	public int writeRawCCDRecordDataToStdTable(Map<Object, Object> stdCCDDocVO);
	
	//public int writeRawEXPRecordDataToStdTable(Map<Object, Object> stdEXpDocVO);
	

}
