package gov.ny.state.doh.bhae.nyehms.web.spring.service.document;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import gov.ny.state.doh.bhae.nyehms.web.spring.dao.document.HIEDTHDocumentDAO;
import us.ny.state.health.hie.endec.EnDecrypter;

@RequiredArgsConstructor
@Service
public class HIEDocumentService {

	private static final Logger log = LoggerFactory.getLogger(HIEDocumentService.class);


	public static final String SALT = "";
	public static final String STRENGTH = "strong";
	public static final String UPHN_SECRET_KEY = "KfCxa7zbxOrqXAbraDgBrM";
	
	private final HIEDTHDocumentDAO hieDTHDocumentDAO;
	
	public String convertBlobtoEncryptedEncodedString(Blob blob) {
		StringBuilder strOut = new StringBuilder();
		String aux;
		BufferedReader br;
		try
		{
			br = new BufferedReader(new InputStreamReader(blob.getBinaryStream(), StandardCharsets.UTF_8));
			while ((aux=br.readLine())!=null) 
			{
				strOut.append(aux);
			}
		}
		catch(SQLException sqlException)
		{
			log.error("Error reading line: {}",sqlException.getMessage());
		}
		catch(IOException ioException)
		{
			log.error("Error converting blob to binary stream: {}",ioException.getMessage());
		}
		
		return strOut.toString();
	}
	
	/**
	 * Takes an OITS encrypted, encoded string and returns a cleartext string;
	 * uses the endec package which is presumably from OITS; also the Apache
	 * codec package rather than the one built into Java 8 (and 7?) because
	 * we are currently constrained to use Java 6.
	 * @todo Since this will be used for HL7 as well as CCD, should change
	 * the name of this method to "decryptDecodeUPHNBinaryString" or something
	 * like that.
	 * @param encryptedEncodedString The input string in the encrypted,
	 * encoded form in which OITS stores it in UPHN_TRANX_IN.PAYLOADBINARYCONTENT
	 * @return The (base64) decoded, decrypted string.
	 * @throws Exception
	 */
	public String decryptDecodeCCDBinaryString (String encryptedEncodedString) 
    {
		
		String decryptedString="";
        try {
			EnDecrypter edw = EnDecrypter.genEnDec(STRENGTH, UPHN_SECRET_KEY);
			String encodedDecrypted = edw.crypt(EnDecrypter.MODE_DECRYPT, encryptedEncodedString, SALT);
			decryptedString = new String(Base64.decodeBase64(encodedDecrypted));
		}
		catch (Exception e) 		{
			log.error("**************  {}", e.getMessage());
		}
        return decryptedString;//commonExceptionHandler.handleException(e, userObject,sessionId);
		
	}

	public List<Map<Object, Object>> getNewRawHIEDTHList(){
		return hieDTHDocumentDAO.getNewRawHIEDTHList();
	}
	
	public void updateDuplicatesThatMoveToSTD(){
		hieDTHDocumentDAO.updateDuplicatesThatMoveToSTD();		
	}
	
	
	public void updateDuplicatesReceivedInSameFile(){
		hieDTHDocumentDAO.updateDuplicatesReceivedInSameFile();		
	}
	
	

	public void insertMpisPairInMPICHANGE() {
		hieDTHDocumentDAO.insertMpisPairInMPICHANGE();
		
	}

	public void updateCPSinRAWComparingCCD() {
		hieDTHDocumentDAO.updateCPSinRAWComparingCCD();
		
	}
}
