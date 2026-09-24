package gov.ny.state.doh.bhae.nyehms.web.spring.service.document;

import gov.ny.state.doh.bhae.nyehms.web.spring.dao.document.HIEDTHDocumentDAO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.exp.RawHIEDTHDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.exp.StdHIEDTHDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.enums.AppConstants;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.schedulers.helpers.CommonSchedulerHelper;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.sql.Blob;
import java.sql.Date;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

import us.ny.state.health.hie.endec.EnDecrypter;

@Service
public class HIEDTHDocumentServiceImpl implements HIEDTHDocumentService {

	public static final String SALT = "";
	public static final String STRENGTH = "strong";
	public static final String UPHN_SECRET_KEY = "KfCxa7zbxOrqXAbraDgBrM";
	private static Logger log = LoggerFactory.getLogger(HIEDTHDocumentServiceImpl.class);
	private String nyehmsLog = AppConstants.nyehmLogDebugString.getName();
	@Autowired
	HIEDTHDocumentDAO hiedthDocumentDAO;
	@Autowired
	CommonDocumentService commonDocumentService;

	@Autowired
	CommonSchedulerHelper commonSchedulerHelper;

	@Override
	public String convertBlobtoEncryptedEncodedString(Blob blob) {
		StringBuffer strOut = new StringBuffer();
		String aux;
		BufferedReader br;
		try {
			br = new BufferedReader(new InputStreamReader(blob.getBinaryStream()));
			while ((aux = br.readLine()) != null) {
				strOut.append(aux);
			}
		} catch (SQLException sqlException) {
			log.error("Error reading line: {}", sqlException.getMessage());
		} catch (IOException ioException) {
			log.error("Error converting blob to binary stream: {}", ioException.getMessage());
		}

		return strOut.toString();
	}

	@Override
	public String decryptDecodeHIEDTHBinaryString(String encryptedEncodedString) {
		String decryptedString = "";
		try {
			EnDecrypter edw = EnDecrypter.genEnDec(STRENGTH, UPHN_SECRET_KEY);
			String encodedDecrypted = edw.crypt(EnDecrypter.MODE_DECRYPT, encryptedEncodedString, SALT);
			decryptedString = new String(Base64.decodeBase64(encodedDecrypted));

		} catch (Exception e) {
			log.error(nyehmsLog + "  " + e.getMessage());
		}
		return decryptedString;// commonExceptionHandler.handleException(e,
								// userObject,sessionId);

	}

	@Override
	public Document getDomDocumentFromHIEDTHString(String theCCDString, Integer uphn_id) {

		BufferedReader br = new BufferedReader(new StringReader(theCCDString));
		InputSource is = new InputSource();
		Document document = null;
		is.setCharacterStream(br);
		try {
			DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			document = db.parse(is);

		} catch (Exception e) {
			log.error(nyehmsLog + "  " + e.getMessage());
			// commonExceptionHandler.handleException(e, userObject,sessionId);
		}
		// catch (SAXException SAXEe){}

		finally {
			// if(rollbackFlag)
			// commonExceptionHandler.populateBindObjTrsnFail(dummyBindingResult);
		}
		return document;
	}

	public Map<String, String> getMapFromNodeMap(NamedNodeMap n) {
		Map<String, String> attrs = new TreeMap<String, String>();
		for (int i = 0; i < n.getLength(); i++) {
			Node attr = n.item(i);
			attrs.put(attr.getNodeName(), attr.getNodeValue());
		}
		return attrs;
	}

	@Override
	@Transactional
	public void storeRawHIEDTHDocVOFromDocument(String fileString, Integer uphn_id, String hieName, Date myDate) {

		RawHIEDTHDocVO rawDeathDocVO = null;
		String[] Task = null;
		String splitBy = ",";
		Scanner scanner = new Scanner(fileString);
		scanner.nextLine();
		while (scanner.hasNextLine()) {
			String newline = scanner.nextLine();
			Task = newline.split(splitBy);
			if (Task != null) {
				rawDeathDocVO = new RawHIEDTHDocVO();
				rawDeathDocVO.setSourceFacility(Task[0]);
				rawDeathDocVO.setMRN(Task[1]);
				rawDeathDocVO.setHXMPIID(Task[2]);
				rawDeathDocVO.setFirstName(Task[3]);
				rawDeathDocVO.setLastName(Task[4]);
				rawDeathDocVO.setBirthDate(Task[5]);
				rawDeathDocVO.setDeathDate(Task[6]);
				rawDeathDocVO.setDeathTime(Task[7]);
				rawDeathDocVO.setCurrentProcessStatus("1");
				Map<Object, Object> inputOutputParameters = new HashMap<Object, Object>();
				inputOutputParameters.put("p_document_type", "HIE_DTH");
				inputOutputParameters.put("p_received_date", myDate);
				commonDocumentService.getNewDocId(inputOutputParameters);
				int newDocId = (Integer) inputOutputParameters.get("p_new_doc_id");
				rawDeathDocVO.setDocumentId(newDocId);
				rawDeathDocVO.setReceivedDate(myDate);
				rawDeathDocVO.setHieName(hieName);
				hiedthDocumentDAO.insertRawHIEDTHDocData(rawDeathDocVO);
			}
		}
		scanner.close();
	}

	@Override
	public int writeRawHIEDTHRecordDataToStdTable(Map<Object, Object> stdHieDthDocVO) {
		Map<Object, Object> stdHIEDTHDocinsertVo = new HashMap<Object, Object>();
		stdHIEDTHDocinsertVo.put("DOCUMENT_ID", stdHieDthDocVO.get("DOCUMENT_ID"));
		stdHIEDTHDocinsertVo.put("SOURCE_FACILITY", stdHieDthDocVO.get("SOURCE_FACILITY"));
		stdHIEDTHDocinsertVo.put("MEDICAL_RECORD_NUMBER",
				CommonUtil.normalizeString((String) stdHieDthDocVO.get("MEDICAL_RECORD_NUMBER")));
		stdHIEDTHDocinsertVo.put("MPIID", stdHieDthDocVO.get("MPIID"));
		stdHIEDTHDocinsertVo.put("FNAME", CommonUtil.NameValidation((String) stdHieDthDocVO.get("FNAME")));
		stdHIEDTHDocinsertVo.put("LNAME", CommonUtil.NameValidation((String) stdHieDthDocVO.get("LNAME")));
		stdHIEDTHDocinsertVo.put("DOB_TEXT", stdHieDthDocVO.get("DOB_TEXT"));
		stdHIEDTHDocinsertVo.put("DOD_TEXT", stdHieDthDocVO.get("DOD_TEXT"));
		stdHIEDTHDocinsertVo.put("DEATHTIME", stdHieDthDocVO.get("DEATHTIME"));
		stdHIEDTHDocinsertVo.put("CURRENT_PROCESS_STATUS", "5");
		stdHIEDTHDocinsertVo.put("RECEIVED_DATE", stdHieDthDocVO.get("RECEIVED_DATE"));
		stdHIEDTHDocinsertVo.put("HIE_NAME", stdHieDthDocVO.get("HIE_NAME"));
		stdHIEDTHDocinsertVo.put("MATCH_CD", stdHieDthDocVO.get("MATCH_CD"));
		stdHIEDTHDocinsertVo.put("MATCH_STATENO", stdHieDthDocVO.get("STATENO"));
		return hiedthDocumentDAO.insertHIEDTHFromRAWToSTD(stdHIEDTHDocinsertVo);
	}

	public HIEDTHDocumentServiceImpl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public StdHIEDTHDocVO getHIEDTHDoc(int docId, BindingResult dummyBindingResult, UserObject userObject, String id) {
		StdHIEDTHDocVO stdExpDocVO = new StdHIEDTHDocVO();

		if (log.isDebugEnabled()) {
			log.debug(" fetching for exp docId : {}", docId);
		}
		try {
			stdExpDocVO = hiedthDocumentDAO.getStdHIEDTHDoc(docId);
		} catch (DataAccessException e) {
			log.error(e.getMessage());
			log.error("Failed to fetch the HIE DTH Document : {}", docId);
		} catch (Exception e) {
			log.error(e.getMessage());
			log.error("Failed to fetch the HIE DTH Document :  {}", docId);
		}
		return stdExpDocVO;
	}

}
