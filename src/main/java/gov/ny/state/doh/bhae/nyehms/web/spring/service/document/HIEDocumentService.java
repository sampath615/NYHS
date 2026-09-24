package gov.ny.state.doh.bhae.nyehms.web.spring.service.document;

import java.sql.Blob;
import java.util.List;
import java.util.Map;

public interface HIEDocumentService {

	public String convertBlobtoEncryptedEncodedString(Blob blob);
	
	public String decryptDecodeCCDBinaryString (String encryptedEncodedString);

	public List<Map<Object, Object>> getNewRawHIEDTHList();

	public void updateDuplicatesReceivedInSameFile();
	
	public void updateDuplicatesThatMoveToSTD();
	
	//method added on 8/22/2019
	public void insertMpisPairInMPICHANGE();
	
	public void updateCPSinRAWComparingCCD();
	
	
	
}
