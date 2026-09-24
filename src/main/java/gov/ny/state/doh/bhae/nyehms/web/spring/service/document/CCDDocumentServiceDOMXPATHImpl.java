package gov.ny.state.doh.bhae.nyehms.web.spring.service.document;

import java.io.BufferedReader;
import java.io.StringReader;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import gov.ny.state.doh.bhae.nyehms.web.spring.dao.document.CCDDocumentDAO;
//import gov.ny.state.doh.bhae.hit.ccd.CCDHandler;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.ccd.RawCCDAddressVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.ccd.RawCCDDiagnosisVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.ccd.RawCCDDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.ccd.RawCCDEncounterVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.ccd.RawCCDLabResultVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.ccd.RawCCDLabTestVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.ccd.RawCCDMedVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.ccd.RawCCDNameVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.ccd.RawCCDPayerVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.ccd.RawCCDPersonAddressVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.ccd.RawCCDPersonDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.ccd.RawCCDProblemVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.ccd.RawCCDProviderVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.ccd.StdCCDDocVo;
import gov.ny.state.doh.bhae.nyehms.web.spring.enums.AppConstants;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonUtil;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.scheduler.LabCommonUtils;


/**
 * Primarily makes extracts the contents of an HL7 Clinical Care Document
 * and stores it into tables in the database.  The primary tools used,
 * once the document is decrypted, are the org.w3c.dom package and the
 * java.xml.xpath package.
 * @author cdf02
 *
 */
@Service
public class CCDDocumentServiceDOMXPATHImpl implements CCDDocumentServiceDOMXPATH  {


	private static Logger log = LoggerFactory.getLogger(CCDDocumentServiceDOMXPATHImpl.class);

	private String nyehmsLog=AppConstants.nyehmLogDebugString.getName();	


	@Autowired private CCDDocumentDAO ccdDocumentDAO;


    private static final String[][] SectionNames =  {
            {"48765-2", "alertSection"},
            {"10160-0", "medSection"},
            {"11450-4", "problemSection"},
            {"46240-8", "encounterSection"},
            {"47519-4", "procedureSection"},
            {"30954-2", "labSection"},
            {"29762-2", "socialSection"},
            {"8716-3",  "vitalSection"},
            {"42348-3", "advancedirSection"},
            {"10157-6", "familySection"},
            {"47420-5", "functionalSection"},
            {"11369-6", "immunizationSection"},
            {"10190-7", "mentalSection"},
            {"46264-8", "deviceSection"},
            {"61144-2", "dietSection"},
            {"48768-6", "payerSection"},
            {"18776-5", "planSection"}
    };
    private static Map<String, String> theSectionNameMap = new HashMap<String, String>();

    static{
        for (String[] kv: SectionNames) {
            theSectionNameMap.put(kv[0], kv[1]);
        }
    }
	private static XPath xPath;
	static{
		xPath = XPathFactory.newInstance().newXPath();
	}
	@Override
	public Document getDomDocumentFromCCDString (String theCCDString, Integer uphnId) 
    	throws Exception
	{
		BufferedReader br = new BufferedReader(new StringReader(theCCDString));
		InputSource is = new InputSource();
		Document document = null;
		is.setCharacterStream(br);
		DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		document = db.parse(is);
		return document;
	}

	public static Map<String, String> getMapFromNodeMap(NamedNodeMap n) {
		Map<String, String> attrs = new TreeMap<String, String>();
		for (int i = 0; i < n.getLength(); i++) {
			Node attr = n.item(i);
			attrs.put(attr.getNodeName(), attr.getNodeValue());
		}
		return attrs;
	}

	@Override
	public RawCCDDocVO getRawCCDDocVOFromDocument(Document d, Integer uphn_id, String cleartext) //throws Exception 
	{
		boolean rollbackFlag=false;
		Element theClinDocElement = d.getDocumentElement();
		theClinDocElement.normalize();
		RawCCDDocVO theCCD = new RawCCDDocVO();
		try{
			Element thePatientRoleElement = (Element)(xPath.evaluate(
					"/ClinicalDocument/recordTarget/patientRole",theClinDocElement, XPathConstants.NODE));
			RawCCDPersonDocVO thePerson = getRawCcdPersonDocVOFromDocument(
					theClinDocElement,
					thePatientRoleElement,
					uphn_id);
			//thePerson.setCleartextPayload(cleartext);
			theCCD.setThePerson(thePerson);
			/************ GET THE ADDRESSES **************/
			NodeList theAddressList = thePatientRoleElement.getElementsByTagName("addr");
			List<RawCCDPersonAddressVO> theAddressVOList = getAddressVOList(theAddressList);
			theCCD.setTheAddressList(theAddressVOList);
			/************ GET THE PROVIDERS ************/
			Element theDocumentationOfElement = (Element)(xPath.evaluate(
					"/ClinicalDocument/documentationOf",theClinDocElement, XPathConstants.NODE));
			if(theDocumentationOfElement!=null)
			{
				List<RawCCDProviderVO> theProviderList = getProviderVOList(theDocumentationOfElement);
				theCCD.setTheProviderList(theProviderList);
			}
			/*********** GET THE SECTIONS **************/
			List<RawCCDPayerVO> thePayerVOList = getPayerVOList(theClinDocElement);
			theCCD.setThePayerList(thePayerVOList);
			List<RawCCDEncounterVO> theEncounterVOList = getEncounterVOList(theClinDocElement);
			theCCD.setTheEncounterList(theEncounterVOList);
			List<RawCCDLabTestVO> theLabTestVOList = getLabTestVOList(theClinDocElement);
			theCCD.setTheLabList(theLabTestVOList);
			List<RawCCDMedVO> theMedVOList = getMedVOList(theClinDocElement);
			theCCD.setTheMedList(theMedVOList);
			List<RawCCDProblemVO> theProblemList = getProblemVOList(theClinDocElement);
			theCCD.setTheProblemList(theProblemList);
		}
		catch (Exception e) 
		{
			rollbackFlag=true;
			log.error(nyehmsLog+"  "+ e.getMessage());
			//commonExceptionHandler.handleException(e, userObject,sessionId);
		}
		return theCCD;	
	}

	private static RawCCDPersonDocVO getRawCcdPersonDocVOFromDocument(
			Element theClinDocElement,
			Element personDocVOElement,
			Integer uphn_id) throws Exception
	{
		RawCCDPersonDocVO rawCCDPersonVO = new RawCCDPersonDocVO();
//		Element personDocVOElement = (Element)(xPath.evaluate(
//				"/ClinicalDocument/recordTarget/patientRole",theClinDocElement, XPathConstants.NODE));
		rawCCDPersonVO.setDATE_CREATED((String)xPath.evaluate(
				"/ClinicalDocument/effectiveTime/@value",
				theClinDocElement, XPathConstants.STRING));
		rawCCDPersonVO.setCURRENT_PROCESS_STATUS(1);
		Element patientElement = (Element)(xPath.evaluate(
				"./patient", personDocVOElement, XPathConstants.NODE));
		Element patientNameElement = (Element)(xPath.evaluate(
				"./name", patientElement, XPathConstants.NODE));
		RawCCDNameVO patientName = getNameVOFromNameElement(patientNameElement);
		rawCCDPersonVO.setName(patientName);
		rawCCDPersonVO.setDOB_TEXT((String)xPath.evaluate(
				"./birthTime/@value",
				patientElement, XPathConstants.STRING));
		rawCCDPersonVO.setGENDER((String)xPath.evaluate(
				"./administrativeGenderCode/@code",
				patientElement, XPathConstants.STRING));
		rawCCDPersonVO.setPERSON_PHONE_HOME((String)xPath.evaluate(
				"./telecom[@use='HP']/@value",
				personDocVOElement, XPathConstants.STRING));
		rawCCDPersonVO.setLANGUAGE((String)xPath.evaluate(
				"./languageCommunication/languageCode/@code",
				patientElement, XPathConstants.STRING));
		rawCCDPersonVO.setETHNICITY((String)xPath.evaluate(
				"./ethnicGroupCode/translation/@displayName",
				patientElement, XPathConstants.STRING));
		rawCCDPersonVO.setPERSON_PHONE_WORK((String)xPath.evaluate(
				"./telecom[@use='WP']/@value",
				personDocVOElement, XPathConstants.STRING));
		rawCCDPersonVO.setPERSON_PHONE_CELL((String)xPath.evaluate(
				"./telecom[@use='MC']/@value",
				personDocVOElement, XPathConstants.STRING));
		String MPI =((String)xPath.evaluate(
				"./id/@extension",
				personDocVOElement, XPathConstants.STRING));
		rawCCDPersonVO.setMPI(MPI);
		rawCCDPersonVO.setUPHN_TRANX_IN_ID(Integer.parseInt(uphn_id.toString()));

		////////NEW CODE ADDED FOR MULTIPLE RACE_CODE/////////////
		StringBuilder raceCodeValue = new StringBuilder(); 
		String raceCodeFinal="";
		raceCodeValue.append((String)xPath.evaluate(
				"./patient/raceCode/translation/@code",
				personDocVOElement, XPathConstants.STRING));

//		Element raceCodeVOElement = (Element)(xPath.evaluate(
//				"/ClinicalDocument/recordTarget/patientRole/patient",theClinDocElement, XPathConstants.NODE));
		//NodeList raceCodeList = raceCodeVOElement.getElementsByTagName("raceCode");
		NodeList sdtcRaceCodeList = patientElement.getElementsByTagName("sdtc:raceCode");
		if(sdtcRaceCodeList!=null && sdtcRaceCodeList.getLength() > 0) {
			raceCodeValue.append("|");
			for (int i = 0; i < sdtcRaceCodeList.getLength(); i++) {
				Element raceCodeElement = (Element)sdtcRaceCodeList.item(i);
				String racecodesdtc = ((String)xPath.evaluate(
					"./translation/@code", raceCodeElement, XPathConstants.STRING));
				raceCodeValue.append(racecodesdtc);
				raceCodeValue.append("|");
			}
			raceCodeFinal = raceCodeValue.substring(0,raceCodeValue.length()-1);
		}

		StringBuilder raceCodeDesc = new StringBuilder();
		String RaceDescFinal="";
		raceCodeDesc.append((String)xPath.evaluate(
				"./raceCode/translation/@code",
				patientElement, XPathConstants.STRING));

		Element raceCodeDescVOElement = (Element)(xPath.evaluate(
				"/ClinicalDocument/recordTarget/patientRole/patient",theClinDocElement, XPathConstants.NODE));
		//NodeList raceCodeList = raceCodeVOElement.getElementsByTagName("raceCode");
		NodeList sdtcRaceCodeDescList = raceCodeDescVOElement.getElementsByTagName("sdtc:raceCode");
		if(sdtcRaceCodeList!=null && sdtcRaceCodeDescList.getLength() > 0)
		{
			raceCodeDesc.append("|");
			for (int i = 0; i < sdtcRaceCodeDescList.getLength(); i++) 
			{
				Element raceCodeDescElement = (Element)sdtcRaceCodeDescList.item(i);
				String racecodesdtc = ((String)xPath.evaluate(
					"./translation/@displayName", raceCodeDescElement, XPathConstants.STRING));
				raceCodeDesc.append(racecodesdtc);
				raceCodeDesc.append("|");
			}
			RaceDescFinal=raceCodeDesc.substring(0,raceCodeDesc.length()-1);
		}

		//rawCCDPersonVO.setRACE_DESC((String)xPath.evaluate(
				//"./patient/raceCode/@displayName",
				//PersonDocVOElement, XPathConstants.STRING));
		//rawCCDPersonVO.setRACE_CODE((String)xPath.evaluate(
				//"./patient/raceCode/translation/@code",
				//PersonDocVOElement, XPathConstants.STRING));
		rawCCDPersonVO.setRACE_CODE(raceCodeFinal);
		rawCCDPersonVO.setRACE_DESC(RaceDescFinal);
		return rawCCDPersonVO;	
	}

	private static List<RawCCDPersonAddressVO> getAddressVOList(NodeList theAddressElementList)
	throws Exception {
		List<RawCCDPersonAddressVO> theAddressVOList = new ArrayList<RawCCDPersonAddressVO>();
		for (int i = 0; i < theAddressElementList.getLength(); i++) {
			Element addressElement = (Element)theAddressElementList.item(i);
			RawCCDPersonAddressVO theAddressVO =
					getPersonAddressVO(addressElement);
			theAddressVOList.add(theAddressVO);
		}
		return theAddressVOList;
	}

	private static RawCCDPersonAddressVO getPersonAddressVO(Element addressElement) throws Exception
	{
		RawCCDPersonAddressVO personAddressVO = new RawCCDPersonAddressVO();
		String UseableStartTime = (String)xPath.evaluate(
				"./useablePeriod/low/@value",addressElement, XPathConstants.STRING);
		RawCCDAddressVO commonAddressVO = getCommonAddressVO(addressElement);
		personAddressVO.setTheCCDAddrObject(commonAddressVO);
		personAddressVO.setUseableStartTime(UseableStartTime);
		String UseableEndTime = (String)xPath.evaluate(
				"./useablePeriod/high/@value",addressElement, XPathConstants.STRING);
		personAddressVO.setUseableEndTime(UseableEndTime);

		return personAddressVO;
	}
	
	private static RawCCDAddressVO getCommonAddressVO(Element addressElement)
	throws Exception
	{
		RawCCDAddressVO commonAddressVO = new RawCCDAddressVO();
		commonAddressVO.setCcdUse((String)xPath.evaluate(
				"./@use", addressElement, XPathConstants.STRING));
		commonAddressVO.setStreet1((String)xPath.evaluate(
				"./streetAddressLine[1]", addressElement, XPathConstants.STRING));
		commonAddressVO.setStreet2((String)xPath.evaluate(
				"./streetAddressLine[2]", addressElement, XPathConstants.STRING));
		commonAddressVO.setCity((String)xPath.evaluate(
				"./city", addressElement, XPathConstants.STRING));
		commonAddressVO.setState((String)xPath.evaluate(
				"./state", addressElement, XPathConstants.STRING));
		commonAddressVO.setZip((String)xPath.evaluate(
				"./postalCode", addressElement, XPathConstants.STRING));
		commonAddressVO.setCountry((String)xPath.evaluate(
				"./country", addressElement, XPathConstants.STRING));
		commonAddressVO.setCounty((String)xPath.evaluate(
				"./county", addressElement, XPathConstants.STRING));
		return commonAddressVO;
	}

	private static RawCCDNameVO getNameVOFromNameElement(Element theNameElement) throws Exception
	{
		RawCCDNameVO theName = new RawCCDNameVO();
		if(theNameElement!=null)
		{
			theName.setLastName((String)xPath.evaluate(
					"./family", theNameElement, XPathConstants.STRING));
			theName.setFirstName((String)xPath.evaluate(
					"./given[1]", theNameElement, XPathConstants.STRING));
			theName.setMiddleName((String)xPath.evaluate(
					"./given[2]", theNameElement, XPathConstants.STRING));
		}
	    return theName;
	}

	private static List<RawCCDProviderVO> getProviderVOList(Element documentationOfElement)	throws Exception
	{
		List<RawCCDProviderVO> theProviderList = new ArrayList<RawCCDProviderVO>();
		if (documentationOfElement != null) {
			NodeList thePerformerElements = documentationOfElement.getElementsByTagName("performer");
			for (int i = 0; i < thePerformerElements.getLength(); i++) {
				Element thePerformerElement = (Element)thePerformerElements.item(i);
				theProviderList.add(getRawProviderVOFromPerformerElement(thePerformerElement));
			}
		}
		return theProviderList;
	}

	private static RawCCDProviderVO getRawProviderVOFromPerformerElement(
			Element thePerformerElement) throws Exception
	{

		RawCCDProviderVO theProviderVO = new RawCCDProviderVO();

		theProviderVO.setProviderTypeCode((String)xPath.evaluate(
				"./functionCode/@code", thePerformerElement, XPathConstants.STRING));
		theProviderVO.setProviderTypeDesc((String)xPath.evaluate(
				"./functionCode/@displayName", thePerformerElement, XPathConstants.STRING));
		Element providerNameElement = (Element)(xPath.evaluate(
				"./assignedEntity/assignedPerson/name",thePerformerElement, XPathConstants.NODE));
		RawCCDNameVO providerName = getNameVOFromNameElement(providerNameElement);
		theProviderVO.setProviderName(providerName);
		Element providerAddrElement = (Element)(xPath.evaluate(
				"./assignedEntity/addr",thePerformerElement, XPathConstants.NODE));
		RawCCDAddressVO theAddressVO = getCommonAddressVO((providerAddrElement));
		theProviderVO.setAddress(theAddressVO);
		theProviderVO.setPhone((String)xPath.evaluate(
				"./assignedEntity/telecom/@value", thePerformerElement, XPathConstants.STRING));
		String theProviderStartTime = (String)xPath.evaluate(
				"./time/high/@value",thePerformerElement, XPathConstants.STRING);
		theProviderVO.setStartDateText(theProviderStartTime);
		String theProviderEndTime = (String)xPath.evaluate(
				"./time/low/@value",thePerformerElement, XPathConstants.STRING);
		theProviderVO.setStartDateText(theProviderEndTime);
		return theProviderVO;
	}

	private static List<RawCCDPayerVO> getPayerVOList(Element theClinDocElement) throws Exception 
	{
		NodeList thePayerEntries = (NodeList)(xPath.evaluate(
				"/ClinicalDocument/component/structuredBody/component/section/code[@code='48768-6']/../entry",theClinDocElement, XPathConstants.NODESET));

		List<RawCCDPayerVO> thePayerVOList = new ArrayList<RawCCDPayerVO>();
		if (thePayerEntries != null) {
			for (int i = 0; i < thePayerEntries.getLength(); i++) {
				Element thePayerEntryElement = (Element)thePayerEntries.item(i);
				RawCCDPayerVO thePayerVO = getRawPayerVOFromPayerElement(thePayerEntryElement);
				thePayerVOList.add(thePayerVO);			
			}
		}
		return thePayerVOList;
	}

	public static RawCCDPayerVO getRawPayerVOFromPayerElement(Element thePayerEntryElement)
	throws Exception {
		RawCCDPayerVO thePayerVO = new RawCCDPayerVO();
		Element thePayerAct= (Element)xPath.evaluate(
				"./act/entryRelationship/act[1]",
				thePayerEntryElement, XPathConstants.NODE);
		Element coverageIdElement = (Element)xPath.evaluate(
				"./participant[@typeCode='COV']/participantRole/id[1]",
				thePayerAct, XPathConstants.NODE);
		String policyIdText = coverageIdElement.getAttribute("extension");
		thePayerVO.setPolicyId(policyIdText);
		Element thePayerName = (Element)xPath.evaluate(
				"./performer/assignedEntity/representedOrganization/name",
				thePayerAct, XPathConstants.NODE);
		thePayerVO.setPolicyName(thePayerName.getTextContent());
		Element payerAddrElement = (Element)(xPath.evaluate(          
				"./act/entryRelationship/act/performer/assignedEntity/representedOrganization/addr",
				thePayerEntryElement, XPathConstants.NODE));
		if(payerAddrElement!=null)
		{
			RawCCDAddressVO thepayerAddressVO = getCommonAddressVO((payerAddrElement));
			thePayerVO.setPolicyAddress(thepayerAddressVO);
		}
		thePayerVO.setPolicyPhone((String)xPath.evaluate(
				"./act/entryRelationship/act/performer/assignedEntity/representedOrganization/telecom",
				thePayerEntryElement, XPathConstants.STRING));
		thePayerVO.setMemberId((String)xPath.evaluate(
				"./act/entryRelationship/act/participant[@typeCode='COV']/participantRole/id/@extension",
				thePayerEntryElement, XPathConstants.STRING));
		Element memberNamePayerElement = (Element)(xPath.evaluate(
				"./act/entryRelationship/act/participant[@typeCode='COV']/participantRole/playingEntity/name",
				thePayerEntryElement, XPathConstants.NODE));
		RawCCDNameVO providerName = getNameVOFromNameElement(memberNamePayerElement);
		thePayerVO.setMemberName(providerName);
		Element memberAddrElement = (Element)(xPath.evaluate(
				"./act/entryRelationship/act/participant[@typeCode='COV']/participantRole/addr",
				thePayerEntryElement, XPathConstants.NODE));
		if(memberAddrElement!=null) {
		      RawCCDAddressVO theMemberAddressVO = getCommonAddressVO((memberAddrElement));
		      thePayerVO.setMemberAddress(theMemberAddressVO);
		}
		thePayerVO.setMemberPhone((String)xPath.evaluate(
				"./act/entryRelationship/act/participant[@typeCode='COV']/participantRole/telecom",
				thePayerEntryElement, XPathConstants.STRING));
		thePayerVO.setMemberDobText((String)xPath.evaluate(
				"./act/entryRelationship/act/participant[@typeCode='COV']/participantRole/playingEntity/birthTime/@value",
				thePayerEntryElement, XPathConstants.STRING));
		thePayerVO.setHolderId((String)xPath.evaluate(
				"./act/entryRelationship/act/participant[@typeCode='HLD']/participantRole/id/@extension",
				thePayerEntryElement, XPathConstants.STRING));
		Element holderNamePayerElement = (Element)(xPath.evaluate(
				"./act/entryRelationship/act/participant[@typeCode='HLD']/participantRole/playingEntity/name",
				thePayerEntryElement, XPathConstants.NODE));
		RawCCDNameVO holderName = getNameVOFromNameElement(holderNamePayerElement);
		thePayerVO.setHolderName(holderName);
		Element payerHolderAddrElement = (Element)(xPath.evaluate("./act/entryRelationship/act/participant[@typeCode='HLD']/participantRole/addr",
				thePayerEntryElement, XPathConstants.NODE));
		if(payerHolderAddrElement!=null) {
			RawCCDAddressVO thepayerHolderAddressVO = getCommonAddressVO((payerHolderAddrElement));
			thePayerVO.setHolderAddress(thepayerHolderAddressVO);
		}
		thePayerVO.setHolderPhone((String)xPath.evaluate(
				"./act/entryRelationship/act/participant[@typeCode='HLD']/participantRole/telecom",
				thePayerEntryElement, XPathConstants.STRING));
		thePayerVO.setHolderDobText((String)xPath.evaluate(
				"./act/entryRelationship/act/participant[@typeCode='HLD']/participantRole/playingEntity/birthTime/@value",
				thePayerEntryElement, XPathConstants.STRING));
		String theProviderStartTime = (String)xPath.evaluate(
				"./act/entryRelationship/act/participant[@typeCode='COV']/time/low/@value",
				thePayerEntryElement, XPathConstants.STRING);
		thePayerVO.setStartDateText(theProviderStartTime);
		String theProviderEndTime = (String)xPath.evaluate(
				"./act/entryRelationship/act/participant[@typeCode='COV']/time/high/@value",
				thePayerEntryElement, XPathConstants.STRING);
		thePayerVO.setEndDateText(theProviderEndTime);

		return thePayerVO;
	}


	private List<RawCCDEncounterVO> getEncounterVOList(Element theClinDocElement) throws Exception 
	{
		NodeList theEncounterEntries = (NodeList)(xPath.evaluate(
				"/ClinicalDocument/component/structuredBody/component/section/code[@code='46240-8']/../entry",
				theClinDocElement, XPathConstants.NODESET));
		List<RawCCDEncounterVO> theEncounterVOList = new ArrayList<RawCCDEncounterVO>();
		if (theEncounterEntries != null) {
			int encounterLength = theEncounterEntries.getLength();
			List<Integer> encounterSequences = 	ccdDocumentDAO.getEncounterSequences(encounterLength);
			for (int i = 0; i < encounterLength; i++) {
				Element theEncounterEntryElement = (Element)theEncounterEntries.item(i);
				RawCCDEncounterVO theEncounterVO = getRawEncounterVOFromEncounterElement(theEncounterEntryElement);
				List<RawCCDDiagnosisVO> theDiagnosislst = getDiagnosisVOList(theEncounterEntryElement, theClinDocElement, encounterSequences.get(i));
				theEncounterVO.setEncounterDocId(encounterSequences.get(i));
				
				if(theDiagnosislst !=null && theDiagnosislst.size() >0)
				{
					theEncounterVO.setTheDiagnoses(theDiagnosislst);
				}
				theEncounterVOList.add(theEncounterVO);			
			}
		}
		return theEncounterVOList;
	}

	private static RawCCDEncounterVO getRawEncounterVOFromEncounterElement(
			Element theEncounterEntryElement) throws Exception 
	{
		RawCCDEncounterVO theEncounterVO = new RawCCDEncounterVO();
		String theEncounterStartTime = (String)xPath.evaluate(
				"./encounter/effectiveTime/low/@value",
				theEncounterEntryElement, XPathConstants.STRING);
		theEncounterVO.setStartDateText(theEncounterStartTime);
		String theEncounterEndTime = (String)xPath.evaluate(
				"./encounter/effectiveTime/high/@value",
				theEncounterEntryElement, XPathConstants.STRING);
		theEncounterVO.setEndDateText(theEncounterEndTime);
		theEncounterVO.setEncounterType((String)xPath.evaluate(
				"./encounter/code/@displayName",
				theEncounterEntryElement, XPathConstants.STRING));
		Element EncProviderNameElement = (Element)(xPath.evaluate(
				"./encounter/performer/assignedEntity/assignedPerson/name",
				theEncounterEntryElement, XPathConstants.NODE));
		RawCCDNameVO providerName = getNameVOFromNameElement(EncProviderNameElement);
		theEncounterVO.setProviderName(providerName);
		theEncounterVO.setFacilityName((String)xPath.evaluate(
				"./encounter/informant/assignedEntity/representedOrganization/name",
				theEncounterEntryElement, XPathConstants.STRING));
		theEncounterVO.setFacilityDept((String)xPath.evaluate(
				"./encounter/participant/participantRole/id/@extension",
				theEncounterEntryElement, XPathConstants.STRING));
		theEncounterVO.setEncounterId((String)xPath.evaluate(
				"./encounter/id/@extension",
				theEncounterEntryElement, XPathConstants.STRING));
		theEncounterVO.setAdmissionType((String)xPath.evaluate(
				"./encounter/priorityCode/translation/@code",
				theEncounterEntryElement, XPathConstants.STRING));
		return theEncounterVO;
	}

	private static List<RawCCDDiagnosisVO> getDiagnosisVOList(Element theEncounterEntryElement, Element theClinDocElement, int encounterDocId) throws Exception 
	{
		List<RawCCDDiagnosisVO> theDiagnosisVOList = new ArrayList<RawCCDDiagnosisVO>();
		NodeList theDiagnosisEntries = (NodeList)(xPath.evaluate(
					"./encounter/entryRelationship",
					theEncounterEntryElement, XPathConstants.NODESET));

		if (theDiagnosisEntries != null) {
			for (int i = 0; i < theDiagnosisEntries.getLength(); i++) {
				Element theDiagnosisEntryElement = (Element)theDiagnosisEntries.item(i);
				RawCCDDiagnosisVO theDiagnosisVO = getRawDiagnosisVOFromEncounterElement(theDiagnosisEntryElement,theClinDocElement);
				
				theDiagnosisVO.setEncounterDocId(encounterDocId);
				theDiagnosisVOList.add(theDiagnosisVO);			
			}
		}
		return theDiagnosisVOList;
	}

	private static RawCCDDiagnosisVO getRawDiagnosisVOFromEncounterElement(
			Element theDiagnosisEntryElement,Element theClinDocElement) throws Exception 
	{
		String value ="";
		value = ((String)xPath.evaluate(
				"./act/entryRelationship/observation/value/@nullFlavor",
				theDiagnosisEntryElement, XPathConstants.STRING));
		RawCCDDiagnosisVO theDiagnosisVO = new RawCCDDiagnosisVO();
		if(value.equals("UNK"))
		{
			String diagnosis_text_section = ((String)xPath.evaluate(
					"./act/entryRelationship/observation/text/reference/@value",
					theDiagnosisEntryElement, XPathConstants.STRING));
			if(!diagnosis_text_section.isEmpty())
			{
				diagnosis_text_section = diagnosis_text_section.substring(1);
			}
			Node theEncounterDiagnosis = (Node)(xPath.evaluate(
                    "/ClinicalDocument//content[@ID='"+diagnosis_text_section+"']",
                    theClinDocElement, XPathConstants.NODE));
			if(theEncounterDiagnosis!=null)
			{
				theDiagnosisVO.setDiagnosisText(theEncounterDiagnosis.getTextContent());
			}
		}
		else if(value.equals("OTH"))
		{
			String theDiagnosisText = (String)xPath.evaluate(
					"./act/entryRelationship/observation/value[@nullFlavor='OTH']/translation/@displayName",
					theDiagnosisEntryElement, XPathConstants.STRING);
			theDiagnosisVO.setDiagnosisText(theDiagnosisText);

			String theDiagnosisCode = (String)xPath.evaluate(
					"./act/entryRelationship/observation/value[@nullFlavor='OTH']/translation/@code",
					theDiagnosisEntryElement, XPathConstants.STRING);
			theDiagnosisVO.setDiagnosisCode(theDiagnosisCode);

			String theDiagnosisCodeSystemCode = (String)xPath.evaluate(
					"./act/entryRelationship/observation/value[@nullFlavor='OTH']/translation/@codeSystemName",
					theDiagnosisEntryElement, XPathConstants.STRING);
			theDiagnosisVO.setDiagnosisCodeSystemCode(theDiagnosisCodeSystemCode);

			String theDiagnosisCodeSystemOid = (String)xPath.evaluate(
					"./act/entryRelationship/observation/value[@nullFlavor='OTH']/translation/@codeSystem",
					theDiagnosisEntryElement, XPathConstants.STRING);
			theDiagnosisVO.setDiagnosisCodeSystemOid(theDiagnosisCodeSystemOid);
		}
		else
		{
			String theDiagnosisText = (String)xPath.evaluate(
					"./act/entryRelationship/observation/value/@displayName",
					theDiagnosisEntryElement, XPathConstants.STRING);
			theDiagnosisVO.setDiagnosisText(theDiagnosisText);

			String theDiagnosisCode = (String)xPath.evaluate(
					"./act/entryRelationship/observation/value/@code",
					theDiagnosisEntryElement, XPathConstants.STRING);
			theDiagnosisVO.setDiagnosisCode(theDiagnosisCode);

			String theDiagnosisCodeSystemCode = (String)xPath.evaluate(
					"./act/entryRelationship/observation/value/@codeSystemName",
					theDiagnosisEntryElement, XPathConstants.STRING);
			theDiagnosisVO.setDiagnosisCodeSystemCode(theDiagnosisCodeSystemCode);

			String theDiagnosisCodeSystemOid = (String)xPath.evaluate(
					"./act/entryRelationship/observation/value/@codeSystem",
					theDiagnosisEntryElement, XPathConstants.STRING);
			theDiagnosisVO.setDiagnosisCodeSystemOid(theDiagnosisCodeSystemOid);	
		}
		return theDiagnosisVO;
	}

	private List<RawCCDLabTestVO> getLabTestVOList(Element theClinDocElement) throws Exception 
	{
		NodeList theLabTestEntries = (NodeList)(xPath.evaluate(
				"/ClinicalDocument/component/structuredBody/component/section/code[@code='30954-2']/../entry",
				theClinDocElement, XPathConstants.NODESET));
		List<RawCCDLabTestVO> theLabTestVOList = new ArrayList<RawCCDLabTestVO>();
		if (theLabTestEntries != null) 
		{
			int labTestlength = theLabTestEntries.getLength();
			List<Integer> labTestSequences = ccdDocumentDAO.getLabTestSequences(labTestlength);
			for (int i = 0; i < labTestlength; i++) 
			{
				Element theLabTestEntryElement = (Element)theLabTestEntries.item(i);
				RawCCDLabTestVO theLabTestVO = getRawLabTestVOFromLabElement(theLabTestEntryElement,theClinDocElement);
				List<RawCCDLabResultVO> theLabResultlst = getRawLabResultVOList(theLabTestEntryElement, theClinDocElement,labTestSequences.get(i));
				theLabTestVO.setCcdLabTestId(labTestSequences.get(i));
				if(theLabResultlst !=null && theLabResultlst.size() >0)
				{
					theLabTestVO.setTheLabResult(theLabResultlst);
				}
				theLabTestVOList.add(theLabTestVO);			
			}
		}
		return theLabTestVOList;
	}

	private static RawCCDLabTestVO getRawLabTestVOFromLabElement(Element theLabTestEntryElement, Element theClinDocElement) throws Exception
	{
		RawCCDLabTestVO theLabTestVO = new RawCCDLabTestVO();

		String OTHCodetest = (String)xPath.evaluate(
					"./organizer/code/@nullFlavor",
					theLabTestEntryElement, XPathConstants.STRING);
		if(OTHCodetest.equals("OTH"))
		{
			String thetestCode= (String)xPath.evaluate(
					"./organizer/code/translation/@code",
					theLabTestEntryElement, XPathConstants.STRING);
			theLabTestVO.setTestCode(thetestCode);

			String theTestCodeCodingSystem = (String)xPath.evaluate(
					"./organizer/code/translation/@codeSystem",
					theLabTestEntryElement, XPathConstants.STRING);
			theLabTestVO.setTestCodeCodeSystem(theTestCodeCodingSystem);

			String theTestCodeDisplayName = (String)xPath.evaluate(
					"./organizer/code/translation/@displayName",
					theLabTestEntryElement, XPathConstants.STRING);
			theLabTestVO.setTestCodeDisplayName(theTestCodeDisplayName);	
		}
		else
		{
			String thetestCode= (String)xPath.evaluate(
				"./organizer/code/@code",
				theLabTestEntryElement, XPathConstants.STRING);
			theLabTestVO.setTestCode(thetestCode);

			String theTestCodeCodingSystem = (String)xPath.evaluate(
				"./organizer/code/@codeSystem",
				theLabTestEntryElement, XPathConstants.STRING);
			theLabTestVO.setTestCodeCodeSystem(theTestCodeCodingSystem);

			String theTestCodeDisplayName = (String)xPath.evaluate(
				"./organizer/code/@displayName",
				theLabTestEntryElement, XPathConstants.STRING);
			theLabTestVO.setTestCodeDisplayName(theTestCodeDisplayName);
		}

		String theTestDate = (String)xPath.evaluate(
				"./organizer/effectiveTime/@value",
				theLabTestEntryElement, XPathConstants.STRING);
		theLabTestVO.setTestDate(theTestDate);

		String theTestEncounterNumber = (String)xPath.evaluate(
				"./organizer/component/encounter/id/@extension",
				theLabTestEntryElement, XPathConstants.STRING);
		theLabTestVO.setTestEncounterNumber(theTestEncounterNumber);

		String theSpecimen = (String)xPath.evaluate(
				"./organizer/component/procedure/specimen/specimenRole/specimenPlayingEntity/code/@code",
				theLabTestEntryElement, XPathConstants.STRING);
		theLabTestVO.setSpecimen(theSpecimen);

		String theTestComments = ((String)xPath.evaluate(
				"./organizer/component/procedure/code/originalText/reference/@value",
				theLabTestEntryElement, XPathConstants.STRING));
		if(!theTestComments.isEmpty())
		{
			theTestComments = theTestComments.substring(1);
			Node theTestCommentsvalue = (Node)(xPath.evaluate(
	                "/ClinicalDocument//td[@ID='"+theTestComments+"']",
	                theClinDocElement, XPathConstants.NODE));
			theLabTestVO.setTestComments(theTestCommentsvalue.getTextContent());
		}

		return theLabTestVO;
	}

	private static List<RawCCDLabResultVO> getRawLabResultVOList(Element theLabTestEntryElement, Element theClinDocElement,int ccdLabTestId) throws Exception 
	{
		List<RawCCDLabResultVO> theLabResultVOList = new ArrayList<RawCCDLabResultVO>();
		NodeList theLabResultEntries = (NodeList)(xPath.evaluate(
					"./organizer/component/observation",
					theLabTestEntryElement, XPathConstants.NODESET));

		if (theLabResultEntries != null) {
			for (int i = 0; i < theLabResultEntries.getLength(); i++) {
				Element theLabResultEntryElement = (Element)theLabResultEntries.item(i);
				RawCCDLabResultVO theLabResultVO = getRawLabResultVOFromLabTestElement(theLabResultEntryElement,theClinDocElement);
				theLabResultVO.setCcdLabTestId(ccdLabTestId);
				theLabResultVOList.add(theLabResultVO);			
			}
		}
		return theLabResultVOList;
	}

	private static RawCCDLabResultVO getRawLabResultVOFromLabTestElement(
			Element theLabResultEntryElement,Element theClinDocElement) throws Exception 
	{
		RawCCDLabResultVO theLabResultVO = new RawCCDLabResultVO();
		String OTHCodetest = (String)xPath.evaluate(
				"./code/@nullFlavor",
				theLabResultEntryElement, XPathConstants.STRING);
		if(OTHCodetest.equals("OTH"))
		{
				String theComponentCode = (String)xPath.evaluate(
						"./code/translation/@code",
						theLabResultEntryElement, XPathConstants.STRING);
				theLabResultVO.setComponentCode(theComponentCode);

				String theComponentCodeSystem = (String)xPath.evaluate(
						"./code/translation/@codeSystem",
						theLabResultEntryElement, XPathConstants.STRING);
				theLabResultVO.setComponentCodeSystem(theComponentCodeSystem);

				String theComponentDisplayName = (String)xPath.evaluate(
						"./code/translation/@displayName",
						theLabResultEntryElement, XPathConstants.STRING);
				theLabResultVO.setComponentDisplayName(theComponentDisplayName);	
		 }
		 else
		 {
		 	String theComponentCode = (String)xPath.evaluate(
						"./code/@code",
						theLabResultEntryElement, XPathConstants.STRING);
				theLabResultVO.setComponentCode(theComponentCode);

				String theComponentCodeSystem = (String)xPath.evaluate(
						"./code/@codeSystem",
						theLabResultEntryElement, XPathConstants.STRING);
				theLabResultVO.setComponentCodeSystem(theComponentCodeSystem);

				String theComponentDisplayName = (String)xPath.evaluate(
						"./code/@displayName",
						theLabResultEntryElement, XPathConstants.STRING);
				theLabResultVO.setComponentDisplayName(theComponentDisplayName);
	     }
		 ////////////////////////////////////////////////////////////////////		
		String theRangeReferenceHighValue = (String)xPath.evaluate(
				"./referenceRange/observationRange/value/high/@value",
				theLabResultEntryElement, XPathConstants.STRING);
		String theRangeReferenceHighUnit = (String)xPath.evaluate(
				"./referenceRange/observationRange/value/high/@unit",
				theLabResultEntryElement, XPathConstants.STRING);
		String ReferenceRangeHigh = theRangeReferenceHighValue +" " + theRangeReferenceHighUnit ;
		theLabResultVO.setReferenceRangeHigh(ReferenceRangeHigh);

		String theRangeReferenceLowValue = (String)xPath.evaluate(
				"./referenceRange/observationRange/value/low/@value",
				theLabResultEntryElement, XPathConstants.STRING);
		String theRangeReferenceLowUnit = (String)xPath.evaluate(
				"./referenceRange/observationRange/value/low/@unit",
				theLabResultEntryElement, XPathConstants.STRING);
		String ReferenceRangeLow = theRangeReferenceLowValue +" " + theRangeReferenceLowUnit ;
		theLabResultVO.setReferenceRangeLow(ReferenceRangeLow);

		String theResultComments = (String)xPath.evaluate(
				"./entryRelationship/act/code[@code='48767-8']/../text/reference/@value",
				theLabResultEntryElement, XPathConstants.STRING);
		if(!theResultComments.isEmpty())
		{
			theResultComments = theResultComments.substring(1);
			Node theResultCommentsvalue = (Node)(xPath.evaluate(
	                "/ClinicalDocument//td[@ID='"+theResultComments+"']",
	                theClinDocElement, XPathConstants.NODE));
			if(theResultCommentsvalue!=null)
			{
				theLabResultVO.setResultComments(theResultCommentsvalue.getTextContent());
			}
		}

		//theLabResultVO.setResultComments(theResultComments);

		String theResultDateText = (String)xPath.evaluate(
				"./effectiveTime/@value",
				theLabResultEntryElement, XPathConstants.STRING);
		theLabResultVO.setResultDateText(theResultDateText);

		Element resultCodeElement = (Element)(xPath.evaluate(
				"/ClinicalDocument/component/structuredBody/component/section/code[@code='30954-2']/../entry/organizer/component/procedure",theClinDocElement, XPathConstants.NODE));

//		String theResultFacilityCode = (String)xPath.evaluate(
//				"./performer/assignedEntity/id/@extension",
//				resultCodeElement, XPathConstants.STRING);
//		theLabResultVO.setResultFacilityCode(theResultFacilityCode);
//
//		String theResultFacilityCodingSystem = (String)xPath.evaluate(
//				"./performer/assignedEntity/id/@root",
//				resultCodeElement, XPathConstants.STRING);
//		theLabResultVO.setResultFacilityCodingSystem(theResultFacilityCodingSystem);

		String theResultStatus = (String)xPath.evaluate(
				"./statusCode/@code",
				resultCodeElement, XPathConstants.STRING);
		theLabResultVO.setResultStatus(theResultStatus);

		String resultValue = null;
		//Element resultValueElement = (Element)(xPath.evaluate(
				//"/ClinicalDocument/component/structuredBody/component/section/code[@code='30954-2']/../entry/organizer/component/observation",theClinDocElement, XPathConstants.NODE));
		//Section start for result Value
		String theResultValue = (String)xPath.evaluate(
				"./value/@value",
				theLabResultEntryElement, XPathConstants.STRING);
		String theResultUnit = (String)xPath.evaluate(
				"./value/@unit",
				theLabResultEntryElement, XPathConstants.STRING);
		resultValue = theResultValue + theResultUnit ;
		/*if(!theResultValue.isEmpty() || !theResultUnit.isEmpty())
		{

		}*/
		if(resultValue.length()<2)
		{
			String theResultValueDirect = (String)xPath.evaluate(
					"./value",
					theLabResultEntryElement, XPathConstants.STRING);
			if(!theResultValueDirect.isEmpty())
			{
				resultValue = theResultValueDirect.replace("&gt;",">");
				resultValue = resultValue.replace("&lt;","<");
			}
			else
			{
				String theResultValueRef = (String)xPath.evaluate(
						"./value[@nullFlavor='NA']/../text/reference/@value",
						theLabResultEntryElement, XPathConstants.STRING);
				if(!theResultValueRef.isEmpty())
				{
					theResultValueRef = theResultValueRef.substring(1);
					Node theResultValueRefvalue = (Node)(xPath.evaluate(
			                "/ClinicalDocument//td[@ID='"+theResultComments+"']",
			                theClinDocElement, XPathConstants.NODE));
					if(theResultValueRefvalue!=null)
					{
						resultValue = theResultValueRefvalue.getTextContent();
						resultValue = resultValue.replace("&gt;",">");
						resultValue = resultValue.replace("&lt;","<");
					}
				}
			}
		}
		theLabResultVO.setResultValue(resultValue);
		//Section END for result Value

		return theLabResultVO;
	}

	private static List<RawCCDMedVO> getMedVOList(Element theClinDocElement) throws Exception 
	{
		NodeList theMedEntries = (NodeList)(xPath.evaluate(
				"/ClinicalDocument/component/structuredBody/component/section/code[@code='10160-0']/../entry",
				theClinDocElement, XPathConstants.NODESET));
		List<RawCCDMedVO> theMedVOList = new ArrayList<RawCCDMedVO>();
		if (theMedEntries != null) {
			for (int i = 0; i < theMedEntries.getLength(); i++) {
				Element theMedEntryElement = (Element)theMedEntries.item(i);
				RawCCDMedVO theMedVO = getRawMedVOFromMedElement(theMedEntryElement);
				theMedVOList.add(theMedVO);			
			}
		}
		return theMedVOList;
	}

	private static RawCCDMedVO getRawMedVOFromMedElement(Element theMedEntryElement) throws Exception 
	{
		RawCCDMedVO theMedVO = new RawCCDMedVO();

		String theOrderedMedicationName = (String)xPath.evaluate(
				"./substanceAdministration/entryRelationship/supply[@moodCode='INT']/product/manufacturedProduct/manufacturedMaterial/name",
				theMedEntryElement, XPathConstants.STRING);
		theMedVO.setOrderedMedicationName(theOrderedMedicationName);
		String theMedStartTime = (String)xPath.evaluate(
				"./substanceAdministration/effectiveTime/low/@value",
				theMedEntryElement, XPathConstants.STRING);
		theMedVO.setStartDateText(theMedStartTime);
		String theMedStopTime = (String)xPath.evaluate(
				"./substanceAdministration/effectiveTime/high/@value",
				theMedEntryElement, XPathConstants.STRING);
		theMedVO.setStopDateText(theMedStopTime);
		String theMedicationDosageValue = (String)xPath.evaluate(
				"./substanceAdministration/doseQuantity/@value",
				theMedEntryElement, XPathConstants.STRING);
		theMedVO.setDosageValue(theMedicationDosageValue);
		String theMedicationDosageUnit = (String)xPath.evaluate(
				"./substanceAdministration/doseQuantity/@unit",
				theMedEntryElement, XPathConstants.STRING);
		theMedVO.setDosageUnit(theMedicationDosageUnit);
		String theFilledMedicationName = (String)xPath.evaluate(
				"./substanceAdministration/entryRelationship/supply[@moodCode='EVN']/product/manufacturedProduct/manufacturedMaterial/name",
				theMedEntryElement, XPathConstants.STRING);
		theMedVO.setFilledMedicationName(theFilledMedicationName);
		/*
		String theCurrentMedication = (String)xPath.evaluate(
				"/substanceAdministration/entryRelationship/supply/product/manufacturedProduct/manufacturedMaterial/code/originalText/reference/@value",
				theMedEntryElement, XPathConstants.STRING);
		theMedVO.setCurrentMedication(theCurrentMedication);
		*/
		Element MedOrderingProviderNameElement = (Element)(xPath.evaluate(
				"./substanceAdministration/entryRelationship/supply/author/assignedAuthor/assignedPerson/name",
				theMedEntryElement, XPathConstants.NODE));
		RawCCDNameVO OrderingProviderName = getNameVOFromNameElement(MedOrderingProviderNameElement);
		theMedVO.setOrderingProvName(OrderingProviderName);

	    /*
	     * 	//DONT HAVE THE PATH FOR THE ITEM

		String MedFrequency = (String)xPath.evaluate(
				"/substanceAdministration/entryRelationship/supply/product/manufacturedProduct/manufacturedMaterial/code/originalText/reference/@value",
				theMedEntryElement, XPathConstants.STRING);
		theMedVO.setFrequency(MedFrequency);


		*/
		return theMedVO;
	}

	private static List<RawCCDProblemVO> getProblemVOList(Element theClinDocElement) throws Exception 
	{
		NodeList theProblemEntries = (NodeList)(xPath.evaluate(
				"/ClinicalDocument/component/structuredBody/component/section/code[@code='11450-4']/../entry",
				theClinDocElement, XPathConstants.NODESET));
		List<RawCCDProblemVO> theProblemVOList = new ArrayList<RawCCDProblemVO>();
		if (theProblemEntries != null) {
			for (int i = 0; i < theProblemEntries.getLength(); i++) {
				Element theProblemEntryElement = (Element)theProblemEntries.item(i);
				RawCCDProblemVO theProblemVO = getRawProblemVOFromMedElement(theProblemEntryElement,theClinDocElement);
				theProblemVOList.add(theProblemVO);			
			}
		}
		return theProblemVOList;
	}

	private static RawCCDProblemVO getRawProblemVOFromMedElement(Element theProblemEntryElement, Element theClinDocElement) throws Exception 
	{
		RawCCDProblemVO theProblemVO = new RawCCDProblemVO();

		String theProblemDateText = (String)xPath.evaluate(
				"./act/entryRelationship/observation/effectiveTime/low/@value",
				theProblemEntryElement, XPathConstants.STRING);
		theProblemVO.setProblemDateText(theProblemDateText);

		Element problemProviderNameElement = (Element)(xPath.evaluate(
				"./act/author/assignedAuthor/assignedPerson/name",
				theProblemEntryElement, XPathConstants.NODE));
		RawCCDNameVO providerName = getNameVOFromNameElement(problemProviderNameElement);
		theProblemVO.setProviderName(providerName);

		String theFacilityName = (String)xPath.evaluate(
				"./act/author/assignedAuthor/representedOrganization/name",
				theProblemEntryElement, XPathConstants.STRING);
		theProblemVO.setFacilityName(theFacilityName);
		////////////////////////////////////////////////////////////////////////////////////////////////////
		String OTHProblemCode = (String)xPath.evaluate(
				"./act/entryRelationship/observation/value/@nullFlavor",
				theProblemEntryElement, XPathConstants.STRING);
		if(OTHProblemCode.equals("OTH"))
		{
			String theProblemCode = (String)xPath.evaluate(
					"./act/entryRelationship/observation/value/translation/@code",
					theProblemEntryElement, XPathConstants.STRING);
			theProblemVO.setProblemCode(theProblemCode);

			String theProblemCodeSystemName = (String)xPath.evaluate(
					"./act/entryRelationship/observation/value/translation/@codeSystemName",
					theProblemEntryElement, XPathConstants.STRING);
			theProblemVO.setProblemCodeSystemName(theProblemCodeSystemName);

			String theProblemDisplayName = (String)xPath.evaluate(
					"./act/entryRelationship/observation/value/translation/@displayName",
					theProblemEntryElement, XPathConstants.STRING);
			theProblemVO.setProblemDisplayName(theProblemDisplayName);

		 }
		else if(OTHProblemCode.equals("UNK"))
		{
			String Problem_text_section = ((String)xPath.evaluate(
					"./act/entryRelationship/observation/value/originalText/reference/@value",
					theProblemEntryElement, XPathConstants.STRING));
			if(!Problem_text_section.isEmpty())
			{
				Problem_text_section = Problem_text_section.substring(1);
			}
			Node theProblemDisplayName = (Node)(xPath.evaluate(
                    "/ClinicalDocument//td[@ID='"+Problem_text_section+"']",
                    theClinDocElement, XPathConstants.NODE));
			if(theProblemDisplayName!=null)
			{
				theProblemVO.setProblemDisplayName(theProblemDisplayName.getTextContent());
			}

		 }
		 else
		 {
			 	String theProblemCode = (String)xPath.evaluate(
						"./act/entryRelationship/observation/value/@code",
						theProblemEntryElement, XPathConstants.STRING);
				theProblemVO.setProblemCode(theProblemCode);

				String theProblemCodeSystemName = (String)xPath.evaluate(
						"./act/entryRelationship/observation/value/@codeSystemName",
						theProblemEntryElement, XPathConstants.STRING);
				theProblemVO.setProblemCodeSystemName(theProblemCodeSystemName);

				String theProblemDisplayName = (String)xPath.evaluate(
						"./act/entryRelationship/observation/value/@displayName",
						theProblemEntryElement, XPathConstants.STRING);
				theProblemVO.setProblemDisplayName(theProblemDisplayName);

	     }
		////////////////////////////////////////////////////////////////////////////////////////////////////
		/*String theProblemCode = (String)xPath.evaluate(
				"./act/entryRelationship/observation/value/@code",
				theProblemEntryElement, XPathConstants.STRING);
		theProblemVO.setProblemCode(theProblemCode);

		String theProblemCodeSystemName = (String)xPath.evaluate(
				"./act/entryRelationship/observation/value/@codeSystemName",
				theProblemEntryElement, XPathConstants.STRING);
		theProblemVO.setProblemCodeSystemName(theProblemCodeSystemName);

		String theProblemDisplayName = (String)xPath.evaluate(
				"./act/entryRelationship/observation/value/@displayName",
				theProblemEntryElement, XPathConstants.STRING);
		theProblemVO.setProblemDisplayName(theProblemDisplayName);*/

		String theEncounterID = (String)xPath.evaluate(
				"./act/entryRelationship/encounter/id/@extension",
				theProblemEntryElement, XPathConstants.STRING);
		theProblemVO.setEncounterId(theEncounterID);

		String theProblemCategory = (String)xPath.evaluate(
				"./act/entryRelationship/observation/code/@displayName",
				theProblemEntryElement, XPathConstants.STRING);
		theProblemVO.setProblemCategory(theProblemCategory);

		return theProblemVO;
	}

	@Override
	public RawCCDDocVO getCCDDoc(int docId, BindingResult dummyBindingResult,
			UserObject userObject, String id) 
	{
    	RawCCDDocVO rawCCDDocVO = new RawCCDDocVO();
		StdCCDDocVo ccdPersonDocVO = null;

        if(log.isDebugEnabled()){
        	log.debug(" fetching for ccd docId : {}", docId);
        }
        try 
        {
        	ccdPersonDocVO = ccdDocumentDAO.getStdCCDDoc(docId);
        	if(ccdPersonDocVO != null)
        	{
        		rawCCDDocVO.setPersonStdData(ccdPersonDocVO);
        		rawCCDDocVO.setTheAddressList(ccdDocumentDAO.getCCDAddressList(ccdPersonDocVO.getDocumentId()));
        	}
        }
        catch (DataAccessException e) 
        {
        	log.error(e.getMessage());
            log.error("Failed to fetch the CCD Document : {}", docId);
        }
        catch (Exception e) 
        {
        	log.error(e.getMessage());
        	log.error("Failed to fetch the CCD Document : {}", docId);
        } 
        return rawCCDDocVO;
	}

	@Override
	public int writeRawCCDRecordDataToStdTable(Map<Object, Object> stdCCDDocVO) 
	{
		Map<Object, Object> stdCCDDocinsertVo = new HashMap<Object, Object>();

		stdCCDDocinsertVo.put("DOCUMENT_ID",stdCCDDocVO.get("DOCUMENT_ID"));
		stdCCDDocinsertVo.put("FNAME",CommonUtil.NameValidation((String)stdCCDDocVO.get("FNAME")));
		stdCCDDocinsertVo.put("MNAME",CommonUtil.NameValidation((String)stdCCDDocVO.get("MNAME")));
		stdCCDDocinsertVo.put("LNAME",CommonUtil.NameValidation((String)stdCCDDocVO.get("LNAME")));
		stdCCDDocinsertVo.put("DOB_TEXT", stdCCDDocVO.get("DOB_TEXT"));
		stdCCDDocinsertVo.put("GENDER", LabCommonUtils.checkCurrentGenderData((String) stdCCDDocVO.get("GENDER")));
		stdCCDDocinsertVo.put("ETHNICITY",LabCommonUtils.checkEthnicityData((String)stdCCDDocVO.get("ETHNICITY")));//"ETHNICITY"

		String race_cd = (String) stdCCDDocVO.get("RACE_CODE"); //O|Don't_Know|Hispanic/Latino,_Asian,|8
		stdCCDDocinsertVo.put("RACE_WHITE","0");
		stdCCDDocinsertVo.put("RACE_BLACK","0");
		stdCCDDocinsertVo.put("RACE_ASIAN","0");
		stdCCDDocinsertVo.put("RACE_INDIAN","0");
		stdCCDDocinsertVo.put("RACE_PACIFIC","0");
		stdCCDDocinsertVo.put("RACE_UNKNOWN","0");
		stdCCDDocinsertVo.put("RACE_OTHER","0");
		stdCCDDocinsertVo.put("RACE_OTHER_TEXT","0");
		//Adding this boolean parameter for unknown if any of other is true unknown should be 0
		boolean unknown_race =true;
    	if(CommonUtil.isNotNullAndEmpty(race_cd))
    	{
    		race_cd = race_cd.trim().toUpperCase();
    		String[] list = race_cd.split("\\|");
    		for(String s : list)
    		{
    			    String race_item = s;
    			    //if(race_item.contains("WHITE"))
		    		if(race_item.contains("WHITE") || race_item.contains("EUROPEAN") || race_item.contains("CAUCASIAN") || race_item.contains("2106-3")|| race_item.matches("(WHT|W).*"))
		    		{
		    			//stdCCDDocinsertVo.put("RACE_CD","04");
		    			stdCCDDocinsertVo.put("RACE_WHITE","1");
		    			unknown_race = false;
		    			stdCCDDocinsertVo.put("RACE_UNKNOWN","0");
		    		}
		    		else if(race_item.contains("BLACK") || race_item.contains("AFRICAN") || race_item.contains("AFRICAN_AMERICAN") ||race_item.contains("AFRICAN AMERICAN") || race_item.contains("2056-0") || race_item.contains("2054-5") || race_item.matches("(B|BLK).*"))
		    		{
		    			stdCCDDocinsertVo.put("RACE_BLACK","1");
		    			unknown_race = false;
		    			stdCCDDocinsertVo.put("RACE_UNKNOWN","0");

		    		}
		    		else if(race_item.contains("ASIAN") || race_item.contains("2028-9") || race_item.matches("(A).*"))
		    		{
		    			stdCCDDocinsertVo.put("RACE_ASIAN","1");
		    			unknown_race = false;
		    			stdCCDDocinsertVo.put("RACE_UNKNOWN","0");

		    		}
		    		else if (race_item.contains("INDIAN") || race_item.contains("IND") || race_item.contains("NATIVE ALASKAN") || race_item.contains("1002-5")|| race_item.matches("(IND|I).*")) // 2029-7 ASIAN INDIAN
		    		{
		    			stdCCDDocinsertVo.put("RACE_INDIAN","1");
		    			unknown_race = false;
		    			stdCCDDocinsertVo.put("RACE_UNKNOWN","0");

					}
		    		else if(race_item.contains("PACIFIC") || race_item.contains("PACIFIC_ISLANDER") || race_item.contains("HAWAIIAN") || race_item.contains("PACIFIC ISLANDER") || race_item.contains("2076-8") || race_item.matches("(P|PAC).*"))
		    		{
						stdCCDDocinsertVo.put("RACE_PACIFIC","1");
						unknown_race = false;
						stdCCDDocinsertVo.put("RACE_UNKNOWN","0");

					}
		    		else if(race_item.contains("MULTIRACIAL") || race_item.contains("MIXED") || race_item.contains("OTHER") || race_item.contains("2131-1") || race_item.matches("(OTH|O|M|MXD|MIX).*"))
					{
						stdCCDDocinsertVo.put("RACE_OTHER","1");
						stdCCDDocinsertVo.put("RACE_OTHER_TEXT",race_item);
						unknown_race = false;
						stdCCDDocinsertVo.put("RACE_UNKNOWN","0");
					}
					else if(race_item.contains("UNK") || race_item.contains("UNKNOWN") || race_item.contains("DONT KNOW") || race_item.contains("UNSPECIFIED") || race_item.contains("DECLINED") || race_item.matches("(UNK|U).*"))
					{
						if(unknown_race == true)
						stdCCDDocinsertVo.put("RACE_UNKNOWN","1");						
					}
					else
					{
						stdCCDDocinsertVo.put("RACE_OTHER","1");
						stdCCDDocinsertVo.put("RACE_OTHER_TEXT",race_item);
						unknown_race = false;
						stdCCDDocinsertVo.put("RACE_UNKNOWN","0");
					}
    	   }
    	}
    	else
    	{
    		stdCCDDocinsertVo.put("RACE_UNKNOWN","1");
    		//stdCCDDocinsertVo.put("RACE_OTHER_TEXT","NO VALUE");
    	}

    	stdCCDDocinsertVo.put("LANGUAGE", stdCCDDocVO.get("LANGUAGE"));

    	String addressLine1 = "";
		if (CommonUtil.isNotNullAndEmpty((String) stdCCDDocVO.get("PERSON_ADDRESS_1"))) {
			addressLine1 = (String) stdCCDDocVO.get("PERSON_ADDRESS_1") + " ";
		}
		String addressLine2 = "";
		if (CommonUtil.isNotNullAndEmpty((String) stdCCDDocVO.get("PERSON_ADDRESS_2"))) {
			addressLine2 = (String) stdCCDDocVO.get("PERSON_ADDRESS_2") + " ";
		}

		stdCCDDocinsertVo.put("PERSON_ADDRESS",CommonUtil.addressValidation(new StringBuilder().append(addressLine1).append(addressLine2).toString()));
		stdCCDDocinsertVo.put("PERSON_CITY", stdCCDDocVO.get("PERSON_CITY"));
		stdCCDDocinsertVo.put("PERSON_STATE", stdCCDDocVO.get("PERSON_STATE"));
		String RES_ZIP = CommonUtil.formatZipAndPlus4Code((String) stdCCDDocVO.get("PERSON_ZIP"));
		if (CommonUtil.isNotNullAndEmpty(RES_ZIP)) 
		{
			if (RES_ZIP.length() > 5) 
			{
				stdCCDDocinsertVo.put("PERSON_ZIP", RES_ZIP.substring(0, 5));
				stdCCDDocinsertVo.put("PERSON_PLUS4", RES_ZIP.substring(5));
			} 
			else
			{
				stdCCDDocinsertVo.put("PERSON_ZIP", RES_ZIP);
			}
		}
		stdCCDDocinsertVo.put("PERSON_COUNTRY", stdCCDDocVO.get("PERSON_COUNTRY"));
		stdCCDDocinsertVo.put("PERSON_ADDRESS_ID", stdCCDDocVO.get("PERSON_ADDRESS_ID"));
		stdCCDDocinsertVo.put("PERSON_PHONE_HOME", CommonUtil.GetNumberFromString((String)stdCCDDocVO.get("PERSON_PHONE_HOME")));
		stdCCDDocinsertVo.put("PERSON_PHONE_CELL", CommonUtil.GetNumberFromString((String)stdCCDDocVO.get("PERSON_PHONE_CELL")));
		stdCCDDocinsertVo.put("PERSON_PHONE_WORK", CommonUtil.GetNumberFromString((String)stdCCDDocVO.get("PERSON_PHONE_WORK")));
		stdCCDDocinsertVo.put("ADDRESS_USE", stdCCDDocVO.get("ADDRESS_USE"));
		stdCCDDocinsertVo.put("USEABLE_PERIOD_LOW", stdCCDDocVO.get("USEABLE_PERIOD_LOW"));
		stdCCDDocinsertVo.put("USEABLE_PERIOD_HIGH", stdCCDDocVO.get("USEABLE_PERIOD_HIGH"));
		stdCCDDocinsertVo.put("CREATE_DATE",new Date(Calendar.getInstance().getTimeInMillis()));
		stdCCDDocinsertVo.put("MPI", stdCCDDocVO.get("MPI"));
		stdCCDDocinsertVo.put("CURRENT_PROCESS_STATUS", "5");
		return ccdDocumentDAO.insertCCDFromRAWToSTD(stdCCDDocinsertVo);
	}
}
																																																																							