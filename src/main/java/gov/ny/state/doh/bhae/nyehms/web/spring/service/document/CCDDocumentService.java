package gov.ny.state.doh.bhae.nyehms.web.spring.service.document;

import java.io.StringReader;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.Namespace;
import org.jdom2.filter.ElementFilter;
import org.jdom2.input.SAXBuilder;
import org.jdom2.util.IteratorIterable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.xml.sax.SAXException;

import ca.uhn.hl7v2.HL7Exception;
import gov.ny.state.doh.bhae.nyehms.web.spring.dao.document.CCDDocumentDAO;
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
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.person.PersonViewVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.schedulers.helpers.PreMatchHITSchedulerHelper;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonUtil;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.PersonNameUtil;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.scheduler.LabCommonUtils;

/**
 * Primarily makes extracts the contents of an HL7 Clinical Care Document and
 * stores it into tables in the database. The primary tool used, once the
 * document is decrypted, is the org.jdom2 package.
 * 
 * @author cdf02
 *
 */
@Service
public class CCDDocumentService {

	private static Logger log = LoggerFactory.getLogger(CCDDocumentService.class);
	@Autowired
	private HITCommonDocumentService hitCommonDocumentService;
	@Autowired
	private CCDDocumentDAO ccdDocumentDAO;
	@Autowired
	private CommonDocumentService commonDocumentService;
	@Autowired
	PreMatchHITSchedulerHelper preMatchHITSchedulerHelper;
	public static final Namespace ns = Namespace.getNamespace("urn:hl7-org:v3");
	public static final Namespace sdtcns = Namespace.getNamespace("urn:hl7-org:sdtc");
	public static final Namespace xsins = Namespace.getNamespace("http://www.w3.org/2001/XMLSchema-instance");
	private Element payerSection;
	private Element encounterSection;
	private Element labSection;
	private Element problemSection;
	private Element medsSection;
	private Map<String, String> encounterReferenceMap = null;
	private Map<String, String> labReferenceMap = null;
	private Map<String, String> problemReferenceMap = null;
	private Map<String, String> raceMap = null;

	/* private Set<String> raceCodeErrors = null; */

	private Map<String, String> ethnicityMap = null;

	public void setRaceMap(Map<String, String> _raceMap) {
		this.raceMap = _raceMap;
	}

	public void setEthnicityMap(Map<String, String> _ethnicityMap) {
		this.ethnicityMap = _ethnicityMap;
	}

	
	public Document convertStringToDocument(String theCCDString) throws Exception{
		SAXBuilder saxBuilder = new SAXBuilder();
		
		return saxBuilder.build(new StringReader(theCCDString));
	}

	public RawCCDDocVO getRawCCDDocVOFromDocument(Document clinicalDocument, int uphnId, int docId, String cleartext)
			throws Exception {

		RawCCDDocVO theCCD = null;
		Element clinDocElement = null;
		try {
			clinDocElement = clinicalDocument.getRootElement();
			theCCD = new RawCCDDocVO();
			payerSection = null;
			encounterSection = null;
			labSection = null;
			problemSection = null;
			medsSection = null;

			getSections(clinDocElement);
			// PERSON
			RawCCDPersonDocVO thePerson = getRawCcdPersonDocVOFromDocument(clinDocElement);
			// thePerson.setCleartextPayload(cleartext);
			thePerson.setDOCUMENT_ID(docId);
			thePerson.setUPHN_TRANX_IN_ID(uphnId);
			theCCD.setThePerson(thePerson);
			// ADDRESSES
			theCCD.setTheAddressList(getAddressVOList(clinDocElement, uphnId, docId));
			// PROVIDERS
			theCCD.setTheProviderList(getProviderVOList(clinDocElement, uphnId, docId));
			// PAYERS
			theCCD.setThePayerList(getPayerVOList(uphnId, docId));
			// ENCOUNTERS and DIAGNOSES
			theCCD.setTheEncounterList(getEncounterVOList(uphnId, docId));
			// LAB TESTS and LAB RESULTS
			theCCD.setTheLabList(getLabTestVOList(uphnId, docId));
			// MEDICATIONS
			theCCD.setTheMedList(getMedVOList(uphnId, docId));
			// PROBLEMS
			theCCD.setTheProblemList(getProblemVOList(uphnId, docId));

		} finally {
			payerSection = null;
			encounterSection = null;
			labSection = null;
			problemSection = null;
			medsSection = null;

			encounterReferenceMap = null;
			labReferenceMap = null;
			problemReferenceMap = null;
		}
		return theCCD;
	}

	private static RawCCDPersonDocVO getRawCcdPersonDocVOFromDocument(Element clinDocElement) throws Exception {
		RawCCDPersonDocVO rawCCDPersonVO = new RawCCDPersonDocVO();

		Element effectiveTimeElement = clinDocElement.getChild("effectiveTime", ns);
		rawCCDPersonVO.setDATE_CREATED(effectiveTimeElement.getAttribute("value").getValue());
		Element recordTargetElement = clinDocElement.getChild("recordTarget", ns);
		Element patientRoleElement = recordTargetElement.getChild("patientRole", ns);
		Element idElement = patientRoleElement.getChild("id", ns);
		rawCCDPersonVO.setMPI(idElement.getAttribute("extension").getValue());
		List<Element> telecomElements = patientRoleElement.getChildren("telecom", ns);
		for (Element te : telecomElements) {
			Attribute useAttribute = te.getAttribute("use");
			if (useAttribute != null) {
				String useAttributeValue = useAttribute.getValue();
				String valueAttributeValue = te.getAttribute("value").getValue();
				if ("HP".equals(useAttributeValue)) {
					rawCCDPersonVO.setPERSON_PHONE_HOME(valueAttributeValue);
				} else if ("WP".equals(useAttributeValue)) {
					rawCCDPersonVO.setPERSON_PHONE_WORK(valueAttributeValue);
				} else if ("MC".equals(useAttributeValue)) {
					rawCCDPersonVO.setPERSON_PHONE_CELL(valueAttributeValue);
				}
			}
		}
		Element patientElement = patientRoleElement.getChild("patient", ns);
		Element patientNameElement = patientElement.getChild("name", ns);
		RawCCDNameVO patientNameVO = getNameVOFromNameElement(patientNameElement);
		rawCCDPersonVO.setName(patientNameVO);
		if (patientElement != null) {
			Element dobElement = patientElement.getChild("birthTime", ns);
			if (dobElement != null) {
				rawCCDPersonVO.setDOB_TEXT(patientElement.getChild("birthTime", ns).getAttribute("value").getValue());
			}
		}

		rawCCDPersonVO
				.setGENDER(patientElement.getChild("administrativeGenderCode", ns).getAttribute("code").getValue());
		Element languageCommunicationElement = patientElement.getChild("languageCommunication", ns);
		if (languageCommunicationElement != null) {
			Element languageCodeElement = languageCommunicationElement.getChild("languageCode", ns);
			if (languageCodeElement != null) {
				rawCCDPersonVO.setLANGUAGE(languageCodeElement.getAttribute("code").getValue());
			}
		}
		List<String> raceCodes = new ArrayList<>();
		List<String> raceDisplayNames = new ArrayList<>();
		List<Element> raceCodeElements = patientElement.getChildren("raceCode", ns);
		for (Element rce : raceCodeElements) {
			Element translationElement = rce.getChild("translation", ns);
			if (translationElement != null) {
				raceCodes.add(translationElement.getAttribute("code").getValue());
				raceDisplayNames.add(translationElement.getAttribute("displayName").getValue());
			}
		}
		List<Element> sdtcRaceCodeElements = patientElement.getChildren("raceCode", sdtcns);
		for (Element rce : sdtcRaceCodeElements) {
			Element translationElement = rce.getChild("translation", ns);
			if (translationElement != null) {
				raceCodes.add(translationElement.getAttribute("code").getValue());
				raceDisplayNames.add(translationElement.getAttribute("displayName").getValue());
			}
		}

		rawCCDPersonVO.setRACE_CODE(CommonUtil.join("|", raceCodes));
		rawCCDPersonVO.setRACE_DESC(CommonUtil.join("|", raceDisplayNames));
		Element ethnicityElement = patientElement.getChild("ethnicGroupCode", ns);
		if (ethnicityElement != null) {
			Element translationElement = ethnicityElement.getChild("translation", ns);
			if (translationElement != null) {
				String ethnicity = translationElement.getAttribute("displayName").getValue();
				rawCCDPersonVO.setETHNICITY(ethnicity);
			}
		}
		return rawCCDPersonVO;
	}

	private static List<RawCCDPersonAddressVO> getAddressVOList(Element clinDocElement, int uphnId, int docId)
			throws Exception {
		List<RawCCDPersonAddressVO> theAddressVOList = new ArrayList<>();
		Element patientRoleElement = descendPath(clinDocElement, "recordTarget/patientRole", ns);
		List<Element> theAddressElementList = patientRoleElement.getChildren("addr", ns);
		int numberOfAddresses = theAddressElementList.size();
		for (int addrCounter = 1; addrCounter <= numberOfAddresses; addrCounter++) {
			Attribute nullFlavorAttribute = null;
			String nullFlavorString = null;
			Element addr = theAddressElementList.get(addrCounter - 1);
			if (addr != null) {
				nullFlavorAttribute = addr.getAttribute("nullFlavor");
				if (nullFlavorAttribute != null) {
					nullFlavorString = nullFlavorAttribute.getValue();
				} else {
					nullFlavorAttribute = addr.getAttribute("use");
					if (nullFlavorAttribute != null) {
						nullFlavorString = nullFlavorAttribute.getValue();
					}
				}
			}
			if (!nullFlavorString.equals("UNK")) {
				RawCCDPersonAddressVO thePAVO = new RawCCDPersonAddressVO();
				thePAVO.setUphnId(uphnId);
				thePAVO.setCcdDocID(docId);
				thePAVO.setAddressSeqNo(addrCounter);
				thePAVO.setTheCCDAddrObject(getCommonAddressVO(addr));
				List<Element> ups = addr.getChildren("useablePeriod", ns);
				if (ups.size() > 0) {
					Element up = ups.get(0);
					Element low = up.getChild("low", ns);
					if (low != null) {
						Attribute lowValueAttr = low.getAttribute("value");
						if (lowValueAttr != null) {
							String lowValueAttrString = lowValueAttr.getValue();
							thePAVO.setUseableStartTime(lowValueAttrString);
						}
					}
					Element high = up.getChild("high", ns);
					if (high != null) {
						Attribute highValueAttr = high.getAttribute("value");
						if (highValueAttr != null) {
							String highValueAttrString = highValueAttr.getValue();
							thePAVO.setUseableEndTime(highValueAttrString);
						}
					}
				}
				theAddressVOList.add(thePAVO);
			}
		}
		return theAddressVOList;
	}

	private static RawCCDAddressVO getCommonAddressVO(Element addressElement) throws Exception {
		RawCCDAddressVO commonAddressVO = new RawCCDAddressVO();
		if (addressElement != null) {
			commonAddressVO.setCcdUse(addressElement.getAttributeValue("use"));
			List<Element> streetName = addressElement.getChildren("streetAddressLine", ns);
			if (streetName != null && streetName.size() > 0) {
				commonAddressVO.setStreet1(streetName.get(0).getText());
				if (streetName.size() > 1) {
					commonAddressVO.setStreet2(streetName.get(1).getText());
				}
			}
			commonAddressVO.setCity(getPathValue(addressElement, "city", null, ns));
			commonAddressVO.setState(getPathValue(addressElement, "state", null, ns));
			commonAddressVO.setZip(getPathValue(addressElement, "postalCode", null, ns));
			commonAddressVO.setCountry(getPathValue(addressElement, "country", null, ns));
			commonAddressVO.setCounty(getPathValue(addressElement, "county", null, ns));
		}
		return commonAddressVO;
	}

	private static List<RawCCDProviderVO> getProviderVOList(Element theClinDocElement, int uphnId, int docId)
			throws Exception {
		List<RawCCDProviderVO> theProviderVOList = new ArrayList<>();
		Element serviceEventElement = descendPath(theClinDocElement, "documentationOf/serviceEvent", ns);
		if (serviceEventElement != null) {
			List<Element> components = serviceEventElement.getChildren("performer", ns);
			for (int providerCounter = 1; providerCounter <= components.size(); providerCounter++) {
				RawCCDProviderVO theProviderVO = getRawProviderVOFromProviderElementByDom(
						components.get(providerCounter - 1));
				theProviderVO.setUphnId(uphnId);
				theProviderVO.setCcdDocID(docId);
				theProviderVO.setProviderSeqNo(providerCounter);
				theProviderVOList.add(theProviderVO);
			}
		}
		return theProviderVOList;
	}

	private static RawCCDProviderVO getRawProviderVOFromProviderElementByDom(Element performerElement)
			throws Exception {
		RawCCDProviderVO theProviderVO = new RawCCDProviderVO();
		if (performerElement != null) {
			theProviderVO.setProviderTypeCode(getPathValue(performerElement, "functionCode", "code", ns));
			theProviderVO.setProviderTypeDesc(getPathValue(performerElement, "functionCode", "displayName", ns));
			RawCCDNameVO providerName = new RawCCDNameVO();
			Element providerNameElement = descendPath(performerElement, "assignedEntity/assignedPerson/name", ns);
			if (providerNameElement != null) {
				providerName = getNameVOFromNameElement(providerNameElement);
			}
			// ADDED NEW CODE FOR FETCHING PROVIDER_ASSIGNED_ID and AUTH DATE
			// 6/16/2017
			theProviderVO.setProviderAssignedID(getPathValue(performerElement, "assignedEntity/id", "extension", ns));
			theProviderVO.setProviderAssignedIDAuth(getPathValue(performerElement, "assignedEntity/id", "root", ns));
			theProviderVO.setProviderName(providerName);
			Element providerAddr = descendPath(performerElement, "assignedEntity/addr", ns);
			if (providerAddr != null) {
				RawCCDAddressVO theAddressVO = getCommonAddressVO(providerAddr);
				theProviderVO.setAddress(theAddressVO);
			}
			theProviderVO.setProviderName(providerName);
			theProviderVO.setPhone(getPathValue(performerElement, "assignedEntity/telecom", "value", ns));
			theProviderVO.setStartDateText(getPathValue(performerElement, "time/low", "value", ns));
			theProviderVO.setEndDateText(getPathValue(performerElement, "time/high", "value", ns));
		}
		return theProviderVO;
	}

	public List<RawCCDPayerVO> getPayerVOList(int uphnId, int docId) throws Exception {
		List<RawCCDPayerVO> payerVOList = new ArrayList<>();
		if (payerSection != null) {
			List<Element> payerEntries = payerSection.getChildren("entry", ns);
			int numberOfPayers = payerEntries.size();
			for (int payerCounter = 1; payerCounter <= numberOfPayers; payerCounter++) {
				RawCCDPayerVO newPayer = getPayerVOFromPayerEntry(payerEntries.get(payerCounter - 1));
				newPayer.setUphnId(uphnId);
				newPayer.setCcdDocID(docId);
				newPayer.setPayerSeqNo(payerCounter);
				payerVOList.add(newPayer);
			}
		}
		return payerVOList;
	}

	public static RawCCDPayerVO getPayerVOFromPayerEntry(Element payerEntry) throws Exception {
		RawCCDPayerVO thePayerVO = new RawCCDPayerVO();
		Element covElement = null;
		Element hldElement = null;
		Element actElement = payerEntry.getChild("act", ns);
		Element entryRelationshipElement = actElement.getChild("entryRelationship", ns);
		Element thePayerAct = entryRelationshipElement.getChild("act", ns);
		List<Element> participantsElements = thePayerAct.getChildren("participant", ns);
		for (Element te : participantsElements) {
			String typeCodeAttr = te.getAttribute("typeCode").getValue();
			if ("COV".equals(typeCodeAttr)) {
				covElement = te.getChild("participantRole", ns);
			} else if ("HLD".equals(typeCodeAttr)) {
				hldElement = te.getChild("participantRole", ns);
			}
		}
		Element thePerformerElement = thePayerAct.getChild("performer", ns);
		if (thePerformerElement != null) {
			thePayerVO.setPolicyId(
					getPathValue(thePerformerElement, "assignedEntity/representedOrganization/id", "extension", ns));
			thePayerVO.setPolicyName(
					getPathValue(thePerformerElement, "assignedEntity/representedOrganization/name", null, ns));
			Element policyAddr = descendPath(thePerformerElement, "assignedEntity/representedOrganization/addr", ns);
			if (policyAddr != null) {
				RawCCDAddressVO thePolicyAddressVO = getCommonAddressVO(policyAddr);
				thePayerVO.setPolicyAddress(thePolicyAddressVO);
			}
			thePayerVO.setPolicyPhone(
					getPathValue(thePerformerElement, "assignedEntity/representedOrganization/telecom", null, ns));

			thePayerVO.setMemberPhone(getPathValue(thePerformerElement, "telecom", null, ns));
		}
		if (covElement != null) {
			thePayerVO.setMemberId(getPathValue(covElement, "id", "extension", ns));
			RawCCDNameVO providerName = new RawCCDNameVO();
			Element providerNameElement = descendPath(covElement, "playingEntity/name", ns);
			if (providerNameElement != null) {
				providerName = getNameVOFromNameElement(providerNameElement);
			}
			thePayerVO.setMemberName(providerName);
			Element memberAddr = descendPath(covElement, "addr", ns);
			if (memberAddr != null) {
				RawCCDAddressVO theMemberAddressVO = getCommonAddressVO(memberAddr);
				thePayerVO.setMemberAddress(theMemberAddressVO);
			}
			Element playingEntitycovBirth = descendPath(covElement, "playingEntity", ns);
			thePayerVO.setMemberDobText(getPathValue(playingEntitycovBirth, "birthTime", "value", sdtcns));// 18330907000000
			Element playingEntityhldBirth = descendPath(covElement, "playingEntity", ns);
			thePayerVO.setHolderDobText(getPathValue(playingEntityhldBirth, "birthTime", "value", sdtcns));// 18330907000000
			thePayerVO.setStartDateText(getPathValue(covElement, "time/low", "value", ns));
			thePayerVO.setEndDateText(getPathValue(covElement, "time/high", "value", ns));
		}
		if (hldElement != null) {
			thePayerVO.setHolderId(getPathValue(hldElement, "id", "extension", ns));
			RawCCDNameVO holderName = new RawCCDNameVO();
			Element holderNameElement = descendPath(hldElement, "playingEntity/name", ns);
			if (holderNameElement != null) {
				holderName = getNameVOFromNameElement(holderNameElement);
			}
			thePayerVO.setHolderName(holderName);
			Element holderAddr = descendPath(hldElement, "addr", ns);
			if (holderAddr != null) {
				RawCCDAddressVO theHolderAddressVO = getCommonAddressVO(holderAddr);
				thePayerVO.setHolderAddress(theHolderAddressVO);
			}
			thePayerVO.setHolderPhone(getPathValue(hldElement, "telecom", null, ns));
		}
		return thePayerVO;
	}

	public List<RawCCDEncounterVO> getEncounterVOList(int uphnId, int docId) throws Exception {
		List<RawCCDEncounterVO> theEncounterVOList = new ArrayList<>();
		if (encounterSection != null) {
			List<Element> encounterEntryElements = encounterSection.getChildren("entry", ns);
			int encounterLength = encounterEntryElements.size();
			for (int i = 1; i <= encounterLength; i++) {
				Element e = encounterEntryElements.get(i - 1);
				RawCCDEncounterVO newEncounter = getRawEncounterVOFromEncounterElement(e, ns);
				newEncounter.setUphnId(uphnId);
				newEncounter.setCcdDocID(docId);
				newEncounter.setEncounterSeqNo(i);
				List<RawCCDDiagnosisVO> theDiagnosisList = getDiagnosisVOList(e, uphnId, docId, i);
				newEncounter.setTheDiagnoses(theDiagnosisList);
				theEncounterVOList.add(newEncounter);
			}
		}
		return theEncounterVOList;
	}

	private static RawCCDEncounterVO getRawEncounterVOFromEncounterElement(Element encounterEntryElement,
			Namespace nameSpace) throws Exception {
		RawCCDEncounterVO theEncounterVO = new RawCCDEncounterVO();
		if (encounterEntryElement != null) {
			Element encounterElement = encounterEntryElement.getChild("encounter", ns);
			if (encounterElement != null) {
				theEncounterVO.setStartDateText(getPathValue(encounterElement, "effectiveTime/low", "value", ns));
				theEncounterVO.setEndDateText(getPathValue(encounterElement, "effectiveTime/high", "value", ns));
				theEncounterVO.setEncounterType(getPathValue(encounterElement, "code", "displayName", ns));
				RawCCDNameVO providerName = new RawCCDNameVO();
				Element providerNameElement = descendPath(encounterElement,
						"performer/assignedEntity/assignedPerson/name", nameSpace);
				if (providerNameElement != null) {
					providerName = getNameVOFromNameElement(providerNameElement);
				}
				theEncounterVO.setProviderName(providerName);
				theEncounterVO.setFacilityName(getPathValue(encounterElement,
						"informant/assignedEntity/representedOrganization/name", null, ns));
				theEncounterVO.setFacilityDept(
						getPathValue(encounterElement, "participant/participantRole/id", "extension", ns));
				// Added new columns
				theEncounterVO.setParticipantFacilityName(
						getPathValue(encounterElement, "participant/participantRole/playingEntity/name", null, ns));
				theEncounterVO.setFacilityTypeCode(
						getPathValue(encounterElement, "participant/participantRole/code", "code", ns));
				theEncounterVO.setFacilityTypeCodeSystem(
						getPathValue(encounterElement, "participant/participantRole/code", "codeSystem", ns));
				theEncounterVO.setFacilityTypeCodeSystemName(
						getPathValue(encounterElement, "participant/participantRole/code", "codeSystemName", ns));
				theEncounterVO.setFacilityType(
						getPathValue(encounterElement, "participant/participantRole/code", "displayName", ns));

				List<Element> encounterIds = encounterElement.getChildren("id", nameSpace);
				if (encounterIds != null) {
					for (Element idElement : encounterIds) {
						String extension = idElement.getAttributeValue("extension");
						if (CommonUtil.isNotNullAndEmpty(extension)) {
							if(extension.length() < 111 && !extension.contains("_")) {
								theEncounterVO.setEncounterId(extension);
							}else {
								String encounterUniqueId = fetchUniqueEncounterId(extension);
								theEncounterVO.setEncounterId(encounterUniqueId);
							}
						
							
						}
					}
				}
				theEncounterVO.setAdmissionType(getPathValue(encounterElement, "priorityCode/translation", "code", ns));
			}
		}
		return theEncounterVO;
	}
	//this method will remove the repeated faulty ids and numbers attached in the String and return unique encounterID
	private static String fetchUniqueEncounterId(String encounterId) {
		
		Pattern pattern = Pattern.compile("[a-zA-Z0-9-]+");
		Matcher matcher = pattern.matcher(encounterId);
		
		Set<String> allNumbers = new HashSet<>();
		String longestUniqueId = "";
		int maxLength = 0;
		
		//collecting all numbers
		while(matcher.find()) {
			String currentId = matcher.group();
			allNumbers.add(currentId);
		}
		
		//identify the longest number that is not a substring of any other number
		for(String number: allNumbers) {
			boolean isSubstring = false;
			
			for(String otherNumber : allNumbers) {
				if(!number.equals(otherNumber) && otherNumber.contains(number)) {
					isSubstring = true;
					break;
				}
			}
			
			if(!isSubstring && number.length() > maxLength) {
				longestUniqueId = number;
				maxLength = number.length();
			}
		}
		// fail to figure out the exact encounterID
		//we can expect the encounter id error failing records while parsing
		if(longestUniqueId.isEmpty()) {// fail to figure out the exact encounterID
			return encounterId;
		}
		
		return longestUniqueId;
	}

	private List<RawCCDDiagnosisVO> getDiagnosisVOList(Element encounterEntry, int uphnId, int ccdDocId,
			int encounterSeqNo) throws Exception {
		List<RawCCDDiagnosisVO> theDiagnosisVOList = new ArrayList<>();
		IteratorIterable<Element> encounterObservationIterator = encounterEntry
				.getDescendants(new ElementFilter("observation", ns));
		int diagnosisCounter = 0;
		for (Element e : encounterObservationIterator) {
			diagnosisCounter++;
			RawCCDDiagnosisVO diagnosisVO = getRawDiagnosisVOFromObservationElement(e, encounterReferenceMap);
			diagnosisVO.setUphnId(uphnId);
			diagnosisVO.setCcdDocID(ccdDocId);
			diagnosisVO.setEncounterSeqNo(encounterSeqNo);
			diagnosisVO.setDiagnosisSeqNo(diagnosisCounter);
			theDiagnosisVOList.add(diagnosisVO);
		}
		return theDiagnosisVOList;
	}

	private RawCCDDiagnosisVO getRawDiagnosisVOFromObservationElement(Element theObservationElement,
			Map<String, String> diagnosisMap) throws Exception {
		RawCCDDiagnosisVO theDiagnosisVO = new RawCCDDiagnosisVO();
		if (theObservationElement != null) {
			Element observationValueElement = theObservationElement.getChild("value", ns);
			if (observationValueElement != null) {
				Attribute nullFlavorAttribute = observationValueElement.getAttribute("nullFlavor");
				String nullFlavorString = null;
				if (nullFlavorAttribute != null) {
					nullFlavorString = nullFlavorAttribute.getValue();
				}
				if ("UNK".equals(nullFlavorString)) {
					String diagnosisText = getPathValue(theObservationElement, "text/reference", "value", ns);
					if (!diagnosisText.isEmpty()) {
						diagnosisText = diagnosisText.substring(1);
					}
					String theEncounterDiagnosis = diagnosisMap.get(diagnosisText);
					if (theEncounterDiagnosis != null) {
						theDiagnosisVO.setDiagnosisText(theEncounterDiagnosis);
					}
				} else if ("OTH".equals(nullFlavorString)) {
					Element translation = observationValueElement.getChild("translation", ns);
					if (translation != null) {
						theDiagnosisVO.setDiagnosisText(translation.getAttribute("displayName").getValue());
						theDiagnosisVO.setDiagnosisCode(translation.getAttribute("code").getValue());
						theDiagnosisVO
								.setDiagnosisCodeSystemCode(translation.getAttribute("codeSystemName").getValue());
						theDiagnosisVO.setDiagnosisCodeSystemOid(translation.getAttribute("codeSystem").getValue());
					}
				} else {
					theDiagnosisVO.setDiagnosisText(observationValueElement.getAttribute("displayName").getValue());
					theDiagnosisVO.setDiagnosisCode(observationValueElement.getAttribute("code").getValue());
					theDiagnosisVO.setDiagnosisCodeSystemCode(
							observationValueElement.getAttribute("codeSystemName").getValue());
					theDiagnosisVO
							.setDiagnosisCodeSystemOid(observationValueElement.getAttribute("codeSystem").getValue());
				}
			}
		}
		return theDiagnosisVO;
	}

	public List<RawCCDLabTestVO> getLabTestVOList(int uphnId, int docId) throws Exception {
		List<RawCCDLabTestVO> labVOList = new ArrayList<>();
		if (labSection != null) {
			List<Element> labEntries = labSection.getChildren("entry", ns);
			int testNumber = 0;
			for (Element l : labEntries) {
				testNumber++;
				RawCCDLabTestVO newLab = getRawLabTestVOFromLabElement(l, uphnId, docId, testNumber);
				newLab.setUphnId(uphnId);
				newLab.setCcdDocID(docId);
				newLab.setTestSeqNo(testNumber);
				labVOList.add(newLab);
			}
		}
		return labVOList;
	}

	private RawCCDLabTestVO getRawLabTestVOFromLabElement(Element theLabEntryElement, int uphnId, int docId,
			int testNumber) throws Exception {
		RawCCDLabTestVO theLabTestVO = new RawCCDLabTestVO();
		Element codeElement = null;
		Attribute organizerNullFlavor = null;
		if (theLabEntryElement != null) {
			Element organizerElement = theLabEntryElement.getChild("organizer", ns);
			if (organizerElement != null) {
				codeElement = organizerElement.getChild("code", ns);
			}
			if (codeElement != null) {
				organizerNullFlavor = codeElement.getAttribute("nullFlavor");
			}
			List<Element> idElements = organizerElement.getChildren("id", ns);
			if (idElements != null && idElements.size() > 0) {
				Element ideZero = idElements.get(0);
				if (ideZero != null) {
					Attribute ideAttributerootfiller = ideZero.getAttribute("root");
					if (ideAttributerootfiller != null) {
						String ideElementrootfiller = ideAttributerootfiller.getValue();
						if (ideElementrootfiller != null && !ideElementrootfiller.isEmpty()) {
							theLabTestVO.setFilleridRoot(ideElementrootfiller);
						}
					}
					Attribute ideAttributeextensionfiller = ideZero.getAttribute("extension");
					if (ideAttributeextensionfiller != null) {
						String ideElementextensionfiller = ideAttributeextensionfiller.getValue();
						if (ideElementextensionfiller != null && !ideElementextensionfiller.isEmpty()) {
							theLabTestVO.setFillerIdExtension(ideElementextensionfiller);
						}
					}
					Attribute ideAttributeAuthorityNamefiller = ideZero.getAttribute("assigningAuthorityName");
					if (ideAttributeAuthorityNamefiller != null) {

						String ideElementassigningAuthorityNamefiller = ideAttributeAuthorityNamefiller.getValue();
						if (ideElementassigningAuthorityNamefiller != null
								&& !ideElementassigningAuthorityNamefiller.isEmpty()) {
							theLabTestVO.setFillerIdAuth(ideElementassigningAuthorityNamefiller);
						}
					}
				}
				if (idElements.size() > 1) {
					Element ideOne = idElements.get(1);
					if (ideOne != null) {
						Attribute ideAttributerootPlacer = ideOne.getAttribute("root");
						if (ideAttributerootPlacer != null) {
							String ideElementrootplacer = ideAttributerootPlacer.getValue();
							if (ideElementrootplacer != null && !ideElementrootplacer.isEmpty()) {
								theLabTestVO.setPlacerIdRoot(ideElementrootplacer);
							}
						}
						Attribute ideAttributeextensionPlacer = ideOne.getAttribute("extension");
						if (ideAttributeextensionPlacer != null) {
							String ideElementextensionplacer = ideAttributeextensionPlacer.getValue();
							if (ideElementextensionplacer != null && !ideElementextensionplacer.isEmpty()) {
								theLabTestVO.setPlacerIdExtension(ideElementextensionplacer);
							}
						}
						Attribute ideAttributeAuthorityNamePlacer = ideOne.getAttribute("assigningAuthorityName");
						if (ideAttributeAuthorityNamePlacer != null) {
							String ideElementassigningAuthorityNameplacer = ideAttributeAuthorityNamePlacer.getValue();
							if (ideElementassigningAuthorityNameplacer != null
									&& !ideElementassigningAuthorityNameplacer.isEmpty()) {
								theLabTestVO.setPlacerIdAuth(ideElementassigningAuthorityNameplacer);
							}
						}
					}
				}
			}
			// LAB TEST CODE
			String organizerNullFlavorValue = null;
			if (organizerNullFlavor != null) {
				organizerNullFlavorValue = organizerNullFlavor.getValue();
			}
			if ("OTH".equals(organizerNullFlavorValue)) {
				Element translationElement = codeElement.getChild("translation", ns);
				if (translationElement != null) {
					theLabTestVO.setTestCode(translationElement.getAttribute("code").getValue());
					theLabTestVO.setTestCodeCodeSystem(translationElement.getAttribute("codeSystem").getValue());
					theLabTestVO.setTestCodeDisplayName(translationElement.getAttribute("displayName").getValue());
					theLabTestVO.setTestCodeSystemName(translationElement.getAttribute("codeSystemName").getValue());
				}
			} else if (codeElement != null) {
				Attribute codeAttribute = codeElement.getAttribute("code");
				if (codeAttribute != null) {
					theLabTestVO.setTestCode(codeAttribute.getValue());
				}
				Attribute codeSystemAttribute = codeElement.getAttribute("codeSystem");
				if (codeSystemAttribute != null) {
					theLabTestVO.setTestCodeCodeSystem(codeSystemAttribute.getValue());
				}
				Attribute displayNameAttribute = codeElement.getAttribute("displayName");
				if (displayNameAttribute != null) {
					theLabTestVO.setTestCodeDisplayName(displayNameAttribute.getValue());
				}
				Attribute codeSystemName = codeElement.getAttribute("codeSystemName");
				if (codeSystemName != null) {
					theLabTestVO.setTestCodeSystemName(codeSystemName.getValue());
				}
			}
			Element effectiveTimeElement = organizerElement.getChild("effectiveTime", ns);
			if (effectiveTimeElement != null) {
				Attribute valueAttribute = effectiveTimeElement.getAttribute("value");
				if (valueAttribute != null) {
					theLabTestVO.setTestDate(effectiveTimeElement.getAttribute("value").getValue());
				}
			}
			if(organizerElement != null) {
				List<Element> 	componentElementList = organizerElement.getChildren("component", ns);//null
			
			for (Element te : componentElementList) {
				if (te != null) {
					Element encounterIdElement = te.getChild("encounter", ns);
					if (encounterIdElement != null) {
						Element idElement = encounterIdElement.getChild("id", ns);
						if (idElement != null) {
							theLabTestVO.setTestEncounterNumber(idElement.getAttribute("extension").getValue());
						}
					}
				}
			}
		}
			Element componentElement = organizerElement.getChild("component", ns);
			Element procedureElement = componentElement.getChild("procedure", ns);
			if (procedureElement != null) {
				String specimenCode = getPathValue(procedureElement,
						"specimen/specimenRole/specimenPlayingEntity/code/", "code", ns);
				theLabTestVO.setSpecimen(specimenCode);
				Element facilityIdElement = descendPath(procedureElement, "performer/assignedEntity/id", ns);
				if (facilityIdElement != null) {
					
					List<Attribute> attributesList = facilityIdElement.getAttributes();
					
					if(attributesList != null && !attributesList.isEmpty()) {
						
						if(attributesList.stream().anyMatch(attr -> attr.getName() == "extension")) {
							theLabTestVO.setTestFacilityCode(facilityIdElement.getAttribute("extension").getValue());
						}
						
						if(attributesList.stream().anyMatch(attr -> attr.getName() == "root")) {
							theLabTestVO.setTestFacilityCodeSystem(facilityIdElement.getAttribute("root").getValue());
						}
					}
				}
				
				String theTestCommentsId = getPathValue(procedureElement, "code/originalText/reference", "value", ns);
				if (theTestCommentsId != null) {
					theTestCommentsId = theTestCommentsId.substring(1);
					theLabTestVO.setTestComments(labReferenceMap.get(theTestCommentsId));
				}
			}
			List<RawCCDLabResultVO> theLabResultlst = getRawLabResultVOList(organizerElement, uphnId, docId,
					testNumber);
			if (theLabResultlst != null && theLabResultlst.size() > 0) {
				theLabTestVO.setTheLabResult(theLabResultlst);
			}
		}
		return theLabTestVO;
	}

	/**
	 * Gets a list of lab result VO objects for this lab test, given the organizer
	 * element of this lab section entry.
	 * 
	 * @param organizerElement
	 * @return
	 * @throws Exception
	 */
	private List<RawCCDLabResultVO> getRawLabResultVOList(Element organizerElement, int uphnId, int docId,
			int testNumber) throws Exception {
		List<RawCCDLabResultVO> theLabResultVOList = new ArrayList<>();
		List<Element> componentEntries = organizerElement.getChildren("component", ns);
		int resultNumberWithinTest = 0;
		for (Element ce : componentEntries) {
			Element observationElement = ce.getChild("observation", ns);
			if (observationElement != null) {
				resultNumberWithinTest++;
				RawCCDLabResultVO newLabResult = getRawLabResultVOFromObservationElement(observationElement);
				newLabResult.setUphnId(uphnId);
				newLabResult.setCcdDocID(docId);
				newLabResult.setResultSeqNo(resultNumberWithinTest);
				newLabResult.setTestSeqNo(testNumber);
				theLabResultVOList.add(newLabResult);
			}
		}
		return theLabResultVOList;
	}

	private RawCCDLabResultVO getRawLabResultVOFromObservationElement(Element observationElement) throws Exception {
		RawCCDLabResultVO theLabResultVO = new RawCCDLabResultVO();
		Element codeElement = null;
		Attribute codeNullFlavorAttribute = null;
		Attribute resultValueAttribute = null;
		if (observationElement != null) {
			codeElement = observationElement.getChild("code", ns);
			if (codeElement != null) {
				codeNullFlavorAttribute = codeElement.getAttribute("nullFlavor");
			}
			String codeNullFlavor = null;
			if (codeNullFlavorAttribute != null) {
				codeNullFlavor = codeNullFlavorAttribute.getValue();
			}
			if ("OTH".equals(codeNullFlavor)) {
				Element translationElement = codeElement.getChild("translation", ns);
				theLabResultVO.setComponentCode(translationElement.getAttribute("code").getValue());
				theLabResultVO.setComponentCodeSystem(translationElement.getAttribute("codeSystem").getValue());
				theLabResultVO.setComponentDisplayName(translationElement.getAttribute("displayName").getValue());
				theLabResultVO.setComponentCodeSystemName(translationElement.getAttribute("codeSystemName").getValue());
			} else {
				if (codeElement != null) {// added by @rzp05

					List<Attribute> attrlist = codeElement.getAttributes();
					// System.out.println(list);
					if (attrlist != null) {

						for (Attribute attr : attrlist) {

							if (attr.getName().equals("code")) {
								theLabResultVO.setComponentCode(attr.getValue());
							} else if (attr.getName().equals("codeSystem")) {
								theLabResultVO.setComponentCodeSystem(attr.getValue());
							} else if (attr.getName().equals("displayName")) {
								theLabResultVO.setComponentDisplayName(attr.getValue());
							} else if (attr.getName().equals("codeSystemName")) {
								theLabResultVO.setComponentCodeSystemName(attr.getValue());
							}

						}
					}

				}
			}
			String theRangeReferenceHighValue = getPathValue(observationElement,
					"referenceRange/observationRange/value/high", "value", ns);
			String theRangeReferenceHighUnit = getPathValue(observationElement,
					"referenceRange/observationRange/value/high", "unit", ns);
			String ReferenceRangeHigh = theRangeReferenceHighValue + " " + theRangeReferenceHighUnit;
			theLabResultVO.setReferenceRangeHigh(ReferenceRangeHigh);

			String theRangeReferenceLowValue = getPathValue(observationElement,
					"referenceRange/observationRange/value/low", "value", ns);
			String theRangeReferenceLowUnit = getPathValue(observationElement,
					"referenceRange/observationRange/value/low", "unit", ns);
			String ReferenceRangeLow = theRangeReferenceLowValue + " " + theRangeReferenceLowUnit;
			theLabResultVO.setReferenceRangeLow(ReferenceRangeLow);
			List<Element> entryRelElements = observationElement.getChildren("entryRelationship", ns);
			for (Element ere : entryRelElements) {
				Element ereActElement = ere.getChild("act", ns);
				if (ereActElement != null) {
					Element ereActCodeElement = ereActElement.getChild("code", ns);
					if (ereActCodeElement != null) {
						Attribute ereacCodeAttribute = ereActCodeElement.getAttribute("code");
						if (ereacCodeAttribute != null) {
							String ereaCodeAttrValue = ereacCodeAttribute.getValue();
							if ("48767-8".equals(ereaCodeAttrValue)) {
								Element actTextReferenceElement = descendPath(ereActElement, "text/reference", ns);
								String referenceId = actTextReferenceElement.getAttribute("value").getValue()
										.substring(1);
								theLabResultVO.setResultComments(labReferenceMap.get(referenceId));
							}
						}
					}
				}
			}
			theLabResultVO.setResultDateText(getPathValue(observationElement, "effectiveTime", "value", ns));
			theLabResultVO.setResultStatus(getPathValue(observationElement, "statusCode", "code", ns));
			Element valueElement = observationElement.getChild("value", ns);
			if (valueElement != null) {
				resultValueAttribute = valueElement.getAttribute("value");
			}
			if (resultValueAttribute != null) {
				String theResultValue = resultValueAttribute.getValue();
				String theResultUnit = valueElement.getAttribute("unit").getValue();
				String combinedResultValue = theResultValue + " " + theResultUnit;
				theLabResultVO.setResultValue(combinedResultValue);
				
			} else {
				String resultReference = getPathValue(observationElement, "text/reference", "value", ns).substring(1);
				String resultText = labReferenceMap.get(resultReference);
				String adjustedResultText = null;
				if (resultText != null) {
					adjustedResultText = resultText.replace("&gt;", ">").replace("&lt;", "<");
				}
				theLabResultVO.setResultValue(adjustedResultText);
			}
			
		}
		return theLabResultVO;
	}

	public List<RawCCDMedVO> getMedVOList(int uphnId, int docId) throws Exception {
		List<RawCCDMedVO> medVOList = new ArrayList<>();
		if (medsSection != null) {
			List<Element> medEntries = medsSection.getChildren("entry", ns);
			int medCounter = 0;
			for (Element m : medEntries) {
				medCounter++;
				RawCCDMedVO newMed = getRawMedVOFromMedElement(m);
				newMed.setUphnId(uphnId);
				newMed.setCcdDocID(docId);
				newMed.setMedSeqNo(medCounter);
				medVOList.add(newMed);
			}
		}
		return medVOList;
	}

	private static RawCCDMedVO getRawMedVOFromMedElement(Element medEntryElement) throws Exception {
		RawCCDMedVO theMedVO = new RawCCDMedVO();
		if (medEntryElement != null) {
			Element substanceAdmin = medEntryElement.getChild("substanceAdministration", ns);
			if (substanceAdmin != null) {
				theMedVO.setStartDateText(getPathValue(substanceAdmin, "effectiveTime/low", "value", ns));
				theMedVO.setStopDateText(getPathValue(substanceAdmin, "effectiveTime/high", "value", ns));
				Element doseQuantityElement = descendPath(substanceAdmin, "doseQuantity", ns);
				if (doseQuantityElement != null) {
					theMedVO.setDosageValue(getPathValue(doseQuantityElement, "", "value", ns));
					theMedVO.setDosageUnit(getPathValue(doseQuantityElement, "", "unit", ns));
				}
				List<Element> entryRels = substanceAdmin.getChildren("entryRelationship", ns);
				for (Element er : entryRels) {
					Element supplyElement = er.getChild("supply", ns);
					if (supplyElement != null) {
						String moodCode = supplyElement.getAttribute("moodCode").getValue();
						String medName = getPathValue(supplyElement,
								"product/manufacturedProduct/manufacturedMaterial/name", null, ns);
						if ("INT".equals(moodCode)) {
							theMedVO.setOrderedMedicationName(medName);
							Element orderingProviderName = descendPath(supplyElement,
									"author/assignedAuthor/assignedPerson/name", ns);
							theMedVO.setOrderingProvName(getNameVOFromNameElement(orderingProviderName));
						} else if ("EVN".equals(moodCode)) {
							theMedVO.setFilledMedicationName(medName);
						}
					}
				}
			}
		}
		return theMedVO;
	}

	public List<RawCCDProblemVO> getProblemVOList(int uphnId, int docId) throws Exception {
		List<RawCCDProblemVO> problemVOList = new ArrayList<>();
		if (problemSection != null) {
			List<Element> problemEntries = problemSection.getChildren("entry", ns);
			int problemCounter = 0;
			for (Element p : problemEntries) {
				problemCounter++;
				RawCCDProblemVO newProblem = getRawProblemVOFromEntry(p);
				newProblem.setUphnId(uphnId);
				newProblem.setCcdDocID(docId);
				newProblem.setProblemSeqNo(problemCounter);
				problemVOList.add(newProblem);
			}
		}
		return problemVOList;
	}

	private RawCCDProblemVO getRawProblemVOFromEntry(Element theProblemEntryElement) throws Exception {
		RawCCDProblemVO theProblemVO = new RawCCDProblemVO();
		Element actElement = theProblemEntryElement.getChild("act", ns);
		Element authorElement = actElement.getChild("author", ns);
		List<Element> entryRelElements = actElement.getChildren("entryRelationship", ns);
		Element observationElement = null;
		Element encounterElement = null;
		for (Element er : entryRelElements) {
			if (observationElement == null) {
				observationElement = er.getChild("observation", ns);
			}
			if (encounterElement == null) {
				encounterElement = er.getChild("encounter", ns);
			}
		}
		theProblemVO.setProblemDateText(getPathValue(observationElement, "effectiveTime/low", "value", ns));
		Element nameElement = null;
		Element repOrgElement = null;
		if (authorElement != null) {
			nameElement = descendPath(authorElement, "assignedAuthor/assignedPerson/name", ns);
			if (nameElement != null) {
				RawCCDNameVO providerNameVO = getNameVOFromNameElement(nameElement);
				theProblemVO.setProviderName(providerNameVO);
			}
			repOrgElement = descendPath(authorElement, "assignedAuthor/representedOrganization", ns);
			if (repOrgElement != null) {
				theProblemVO.setFacilityName(getPathValue(repOrgElement, "name", null, ns));
			}
		}
		Element valueElement = descendPath(observationElement, "value", ns);
		if (valueElement != null) {
			Attribute nullFlavorAttribute = valueElement.getAttribute("nullFlavor");
			if (nullFlavorAttribute != null) {
				String valueCode = nullFlavorAttribute.getValue();
				if ("OTH".equals(valueCode)) {
					Element translationElement = valueElement.getChild("translation", ns);
					if (translationElement != null) {
						theProblemVO.setProblemCode(translationElement.getAttribute("code").getValue());
						theProblemVO
								.setProblemCodeSystemName(translationElement.getAttribute("codeSystemName").getValue());
						theProblemVO.setProblemDisplayName(translationElement.getAttribute("displayName").getValue());
						theProblemVO.setProblemCodeSystem(translationElement.getAttribute("codeSystem").getValue());
					}
				} else if ("UNK".equals(valueCode)) {
					String valueTextId = getPathValue(valueElement, "originalText/reference", "value", ns);
					if (valueTextId != null && !valueTextId.isEmpty()) {
						valueTextId = valueTextId.substring(1);
						theProblemVO.setProblemDisplayName(problemReferenceMap.get(valueTextId));
					}
				}
			} else {
				theProblemVO.setProblemCode(getPathValue(valueElement, null, "code", ns));
				theProblemVO.setProblemCodeSystemName(getPathValue(valueElement, null, "codeSystemName", ns));
				theProblemVO.setProblemCodeSystem(getPathValue(valueElement, null, "codeSystem", ns));
				theProblemVO.setProblemDisplayName(getPathValue(valueElement, null, "displayName", ns));
			}
		}
		if (encounterElement != null) {
			theProblemVO.setEncounterId(getPathValue(encounterElement, "id", "extension", ns));
		}
		if (observationElement != null) {
			theProblemVO.setProblemCategory(getPathValue(observationElement, "code", "displayName", ns));
		}
		return theProblemVO;
	}

	private static RawCCDNameVO getNameVOFromNameElement(Element theNameElement) throws Exception {
		RawCCDNameVO theName = new RawCCDNameVO();
		if (theNameElement != null) {
			theName.setLastName(theNameElement.getChildText("family", ns));
			List<Element> givenNameElements = (theNameElement.getChildren("given", ns));
			int givenNameCount = givenNameElements.size();
			if (givenNameCount > 0) {
				theName.setFirstName(givenNameElements.get(0).getText());
			}
			if (givenNameCount > 1) {
				theName.setMiddleName(givenNameElements.get(1).getText());
			}
		}
		return theName;
	}

	private void getSections(Element clinDocElement) {

		Element structuredBodyElement = descendPath(clinDocElement, "component/structuredBody", ns);
		List<Element> componentList = structuredBodyElement.getChildren("component", ns);
		for (Element component : componentList) {
			String sectionCode = getPathValue(component, "section/code", "code", ns);
			if ("48768-6".equals(sectionCode)) {
				payerSection = component.getChild("section", ns);
			} else if ("46240-8".equals(sectionCode)) {
				encounterSection = component.getChild("section", ns);
			} else if ("10160-0".equals(sectionCode)) {
				medsSection = component.getChild("section", ns);
			} else if ("30954-2".equals(sectionCode)) {
				labSection = component.getChild("section", ns);
			} else if ("11450-4".equals(sectionCode)) {
				problemSection = component.getChild("section", ns);
			}
		}
		if (encounterSection != null) {
			encounterReferenceMap = getEncounterIDMap(encounterSection);
		}
		if (labSection != null) {
			labReferenceMap = getTextIDMap(labSection);
		}
		if (problemSection != null) {
			problemReferenceMap = getTextIDMap(problemSection);
		}
	}

	/**
	 * In the encounter section, finds the content elements that have ID attributes,
	 * and returns a Map where the keys are the ID attribute values and the map
	 * values are the text content of the corresponding content elements.
	 * 
	 * @param sectionElement
	 * @return
	 */
	private Map<String, String> getEncounterIDMap(Element sectionElement) {
		Map<String, String> idMap = new HashMap<>();
		Element tbodyElement = descendPath(sectionElement, "text/table/tbody", ns);
		IteratorIterable<Element> idAttributesIterator = tbodyElement.getDescendants(new ElementFilter("content", ns));
		for (Element contentElement : idAttributesIterator) {
			String keyString = contentElement.getAttribute("ID").getValue();
			String valueString = contentElement.getText();
			idMap.put(keyString, valueString);
		}
		return idMap;
	}

	/**
	 * Given a CCD section, extracts every td element in the text element of that
	 * section that has an ID attribute, and returns a map where the keys are the ID
	 * attribute values and the map values are the text contents of the
	 * corresponding td elements.
	 * 
	 * @param sectionElement
	 * @return
	 */
	private Map<String, String> getTextIDMap(Element sectionElement) {
		Map<String, String> idMap = new HashMap<>();
		Element tbodyElement = descendPath(sectionElement, "text/table/tbody", ns);
		if (tbodyElement != null) {
			IteratorIterable<Element> idAttributesIterator = tbodyElement.getDescendants(new ElementFilter("td", ns));
			for (Element contentElement : idAttributesIterator) {
				Attribute idAttribute = contentElement.getAttribute("ID");
				if (idAttribute != null) {
					String keyString = idAttribute.getValue();
					String valueString = contentElement.getText();
					idMap.put(keyString, valueString);
				}
			}
		}
		return idMap;
	}

	/**
	 * Given an Element in the Document, a path down from that Element, an Attribute
	 * of the element at the bottom of the path, and a Namespace, returns the text
	 * of the Element at the end of the path if attributeName is null, or the text
	 * value of the Attribute with that name of the terminal Element if
	 * attributeName is not null.
	 * 
	 * @param context
	 *            The Element from which the path descent is to begin
	 * @param path
	 *            An xPath-like series of tag names separated by "/"
	 * @param attributeName
	 *            Null if the desired value is the text of the tag at the end of the
	 *            path; an Attribute name if an attribute of the terminal Element is
	 *            what is desired.
	 * @param nameSpace
	 *            Namespace required by jdom2
	 * @return The desired value, or null if any part of the path does not exist or
	 *         if the attribute does not exist or if any element in the path is in a
	 *         different Namespace
	 */
	private static String getPathValue(Element context, String path, String attributeName, Namespace nameSpace) {
		String targetValue = null;
		Element targetElement = null;
		if (context != null) {
			if (CommonUtil.isNotNullAndEmpty(path)) {
				targetElement = descendPath(context, path, nameSpace);
			} else {
				targetElement = context;
			}
			if (targetElement != null) {
				if (attributeName != null) {
					targetValue = targetElement.getAttributeValue(attributeName);
				} else {
					targetValue = targetElement.getText();
				}
			}
		}
		return targetValue;
	}

	/**
	 * Given a context and a path and a namespace, returns the Element at the end of
	 * the path in the document, where each node in the path is evaluated with the
	 * given Namespace, or null if it does not exist.
	 * 
	 * @param referenceElement
	 * @param path
	 * @param nameSpace
	 * @return
	 */
	private static Element descendPath(Element context, String path, Namespace nameSpace) {

		Element targetElement = null;
		if (context != null) {
			String[] pathTagArray = path.split("/");

			for (int index = 0; index < pathTagArray.length; index++) {
				if (index == 0) {
					targetElement = context.getChild(pathTagArray[0], nameSpace);
				} else {
					if (targetElement == null) {
						break;
					} else {
						targetElement = targetElement.getChild(pathTagArray[index], nameSpace);
					}
				}
			}
		}
		return targetElement;
	}

	public RawCCDDocVO getCCDDoc(int docId, BindingResult dummyBindingResult, UserObject userObject, String id) {
		RawCCDDocVO rawCCDDocVO = new RawCCDDocVO();
		StdCCDDocVo ccdPersonDocVO = null;

		if (log.isDebugEnabled()) {
			log.debug(" fetching for ccd docId : {}", docId);
		}
		try {
			ccdPersonDocVO = ccdDocumentDAO.getStdCCDDoc(docId);
			if (ccdPersonDocVO != null) {
				rawCCDDocVO.setPersonStdData(ccdPersonDocVO);
				// rawCCDDocVO.setTheAddressList(ccdDocumentDAO.getCCDAddressList(ccdPersonDocVO.getDocumentId()));
			}
		} catch (DataAccessException e) {
			log.error(e.getMessage());
			log.error("Failed to fetch the CCD Document : {}", docId);
		} catch (Exception e) {
			log.error(e.getMessage());
			log.error("Failed to fetch the CCD Document : {}", docId);
		}
		return rawCCDDocVO;
	}

	/*
	 * This Query was Previously used to move data from raw to STD before it was
	 * replaces by NEW UPDATED Query : SELECT * FROM RAW_CCD_DOC r ,
	 * RAW_CCD_PERSON_ADDRESS c WHERE r.document_id = c.CCD_DOC_ID (+) AND
	 * r.CURRENT_PROCESS_STATUS IN (1,4) AND CASE WHEN c.ADDRESS_USE IS NULL THEN
	 * 'HP' ELSE c.ADDRESS_USE END = 'HP'
	 */

	@Transactional
	public int writeRawCCDRecordDataToStdTable(Map<Object, Object> stdCCDDocVO) {
		Map<Object, Object> stdCCDDocinsertVo = new HashMap<>();

		String FNAME_SPLIT = "";
		String MNAME_SPLIT = "";
		String MIDDLE_REPEAT = "";
		String[] FirstNameWithSuffix = new String[2];
		String[] MiddleNameWithSuffix = new String[2];
		String[] LastNameWithSuffix = new String[2];
		String Suffix = "";

		stdCCDDocinsertVo.put("DOCUMENT_ID", stdCCDDocVO.get("DOCUMENT_ID"));
		stdCCDDocinsertVo.put("UPHN_TRANX_IN_ID", stdCCDDocVO.get("UPHN_TRANX_IN_ID"));
		String FirstName = CommonUtil.NameValidationHit((String) stdCCDDocVO.get("FNAME"));
		String MiddleName = CommonUtil.NameValidationHit((String) stdCCDDocVO.get("MNAME"));
		String LastName = CommonUtil.NameValidationHit((String) stdCCDDocVO.get("LNAME"));

		// Validation for the name where apart from text and space every special
		// character will be converted to space and apostrophe will be removed if found
		if (CommonUtil.isNotNullAndEmpty(FirstName)) {
			FirstName = FirstName.toUpperCase().trim();
			FirstNameWithSuffix = PersonNameUtil.splitSuffix(FirstName);
		}
		if (CommonUtil.isNotNullAndEmpty(MiddleName)) {
			MiddleName = MiddleName.toUpperCase().trim();
			MiddleNameWithSuffix = PersonNameUtil.splitSuffix(MiddleName);
		}
		if (CommonUtil.isNotNullAndEmpty(LastName)) {
			LastName = LastName.toUpperCase().trim();
			LastNameWithSuffix = PersonNameUtil.splitSuffix(LastName);
		}

		// Name and suffix are separated if found
		if (FirstNameWithSuffix != null) {
			FirstName = FirstNameWithSuffix[0];
			Suffix = FirstNameWithSuffix[1];
		}

		if (MiddleNameWithSuffix != null) {
			MiddleName = MiddleNameWithSuffix[0];
			if (!CommonUtil.isNotNullAndEmpty(Suffix)) {
				Suffix = MiddleNameWithSuffix[1];
			}
		}

		if (LastNameWithSuffix != null) {
			LastName = LastNameWithSuffix[0];
			if (!CommonUtil.isNotNullAndEmpty(Suffix)) {
				Suffix = LastNameWithSuffix[1];
			}
		}
		// First Name is separated based on the first space i.e. First word before space
		// is FNAME_SPLIT and everything after that space is MNAME_SPLIT
		if (CommonUtil.isNotNullAndEmpty(FirstName) && CommonUtil.containsWhiteSpace(FirstName)) {
			FNAME_SPLIT = FirstName.substring(0, FirstName.indexOf(' ')).trim();
			MNAME_SPLIT = FirstName.substring(FirstName.indexOf(' ') + 1).trim();
			// Condition to verify if MNAME SPLIT like (L L) so we can later use it to make
			// it just L
			if (CommonUtil.containsWhiteSpace(MNAME_SPLIT) && MNAME_SPLIT.length() == 3) {
				char firstletter = MNAME_SPLIT.charAt(0);
				char middleletter = MNAME_SPLIT.charAt(1);
				char lastletter = MNAME_SPLIT.charAt(2);
				if (firstletter == lastletter && middleletter == ' ') {
					MIDDLE_REPEAT = firstletter + " " + lastletter;
				}
			} // Condition to verify that MNAME_SPLIT is L and MIDDLE NAME IS L L so later we
				// can just put L in MIDDLENAME and remove L from LSPLIT
			else if (MNAME_SPLIT.length() == 1) {
				MIDDLE_REPEAT = MNAME_SPLIT + " " + MNAME_SPLIT;
			}

			// (When String Contains AKA ) OR (FIRSTNAME length = 3 FirstName)
			// then First and Middle Name will remain the same
			if (MNAME_SPLIT.contains("AKA ") || (FirstName.length() == 3)) {
				FirstName = FirstName;
				MiddleName = MiddleName;
			} // (MiddleName length is greater than 1
			else if (CommonUtil.isNotNullAndEmpty(MiddleName) && MiddleName.length() > 1) {
				// CHECK FIRST FIRST LETTER IN MNAME_SPLIT and MIDDLE NAME If SAME OR MIDDLE
				// NAME EQUALS MNAME_SPLIT
				if (String.valueOf(MiddleName.charAt(0)).equals(MNAME_SPLIT) || MiddleName.equals(MNAME_SPLIT)) {
					FirstName = FNAME_SPLIT;
					// when MNAME SPLIT and MIDDLE NAME has repeat values like L L and L L then
					// MIDDLE NAME LIKE L
					if (MIDDLE_REPEAT.equals(MiddleName)) {
						MiddleName = String.valueOf(MiddleName.charAt(0));
					} else {
						MiddleName = MiddleName;
					}
				}
			} // Condition when MNAME_SPLIT is greater than 1 and middlename is just single
				// letter
			else if (MNAME_SPLIT.length() > 1 && CommonUtil.isNotNullAndEmpty(MiddleName) && MiddleName.length() == 1) {
				// If MNAME_SPLIT first initial and Middle name is equal then everything is
				// MPSPLIT
				// is middlename
				if (String.valueOf(MNAME_SPLIT.charAt(0)).equals(MiddleName)) {
					// Condition where MNAME_SPLIT is (L L) and middle NAME is L then in that case
					// first name will be FNAME_SPLIT and MIDDLE NAME WILL BE MIDDLENAME
					if (MNAME_SPLIT.equals(MIDDLE_REPEAT)) {
						FirstName = FNAME_SPLIT;
						MiddleName = MiddleName;
					}
					// Case when MNAME_SPLIT is LAX and MIDDLE is L since L and LAX has 1st initial
					// equal then LAX will assigned to middlename
					else {
						FirstName = FNAME_SPLIT;
						MiddleName = MNAME_SPLIT;
					}
				}
				// MIDDLE_NAME is EQUAL to MNAME_SPLIT OR WHEN MIDDLENAME IS NULL AND
				// FNAME_SPLIT is greater than 3 (i.e name A B PQRS will be avoided and kept as
				// it is)
				// MNAME_SPLIT IS not empty then middlename = MNAME SPLIT
			} else if ((MNAME_SPLIT.equals(MiddleName)) || (!CommonUtil.isNotNullAndEmpty(MiddleName)
					&& CommonUtil.isNotNullAndEmpty(MNAME_SPLIT) && (FNAME_SPLIT.length() > 2))) {
				FirstName = FNAME_SPLIT;
				MiddleName = MNAME_SPLIT;
			} // If any of the condition do not fit in above criteria then we will keep first
				// and middle name as it is
			else {
				FirstName = FirstName;
				MiddleName = MiddleName;
			}
		}
		stdCCDDocinsertVo.put("FNAME", FirstName);
		stdCCDDocinsertVo.put("MNAME", MiddleName);
		stdCCDDocinsertVo.put("LNAME", LastName);
		stdCCDDocinsertVo.put("SUFFIX", Suffix);
		stdCCDDocinsertVo.put("DOB_TEXT", stdCCDDocVO.get("DOB_TEXT"));
		stdCCDDocinsertVo.put("GENDER", LabCommonUtils.checkCurrentGenderData((String) stdCCDDocVO.get("GENDER")));
		stdCCDDocinsertVo.put("LANGUAGE", stdCCDDocVO.get("LANGUAGE"));
		stdCCDDocinsertVo.put("ETHNICITY", "");
		String addressLine1 = "";
		if (CommonUtil.isNotNullAndEmpty((String) stdCCDDocVO.get("PERSON_ADDRESS_1"))) {
			addressLine1 = (String) stdCCDDocVO.get("PERSON_ADDRESS_1") + " ";
		}
		String addressLine2 = "";
		if (CommonUtil.isNotNullAndEmpty((String) stdCCDDocVO.get("PERSON_ADDRESS_2"))) {
			addressLine2 = (String) stdCCDDocVO.get("PERSON_ADDRESS_2") + " ";
		}
		stdCCDDocinsertVo.put("PERSON_ADDRESS",
				CommonUtil.addressValidation(new StringBuilder().append(addressLine1).append(addressLine2).toString()));
		stdCCDDocinsertVo.put("PERSON_CITY", stdCCDDocVO.get("PERSON_CITY"));
		String state = (String) stdCCDDocVO.get("PERSON_STATE");
		if (state != null && !state.isEmpty()) {
			if (!state.isEmpty()) {
				state = state.toUpperCase();
			}
			if (state.length() <= 2) {
				stdCCDDocinsertVo.put("PERSON_STATE", stdCCDDocVO.get("PERSON_STATE"));
			} else if (state.equals("NEW YORK")) {
				stdCCDDocinsertVo.put("PERSON_STATE", "NY");
			} else if (state.length() > 2) {
				state = state.substring(0, 2);
				stdCCDDocinsertVo.put("PERSON_STATE", state);
			}
		}

		String RES_ZIP = CommonUtil.formatZipAndPlus4Code((String) stdCCDDocVO.get("PERSON_ZIP"));
		if (CommonUtil.isNotNullAndEmpty(RES_ZIP)) {
			if (RES_ZIP.length() >= 9) {
				stdCCDDocinsertVo.put("PERSON_ZIP", RES_ZIP.substring(0, 5));
				stdCCDDocinsertVo.put("PERSON_PLUS4", RES_ZIP.substring(5, 9));
			} else if (RES_ZIP.length() > 5 && RES_ZIP.length() < 9) {
				stdCCDDocinsertVo.put("PERSON_ZIP", "");
			} else {
				stdCCDDocinsertVo.put("PERSON_ZIP", RES_ZIP);
			}
		}
		stdCCDDocinsertVo.put("PERSON_COUNTRY", stdCCDDocVO.get("PERSON_COUNTRY"));
		stdCCDDocinsertVo.put("PERSON_ADDRESS_ID", stdCCDDocVO.get("PERSON_ADDRESS_ID"));
		
		String person_phone_home = (String)stdCCDDocVO.get("PERSON_PHONE_HOME");
		if (CommonUtil.isNotNullAndEmpty(person_phone_home)) {
			person_phone_home = CommonUtil.verifythephone(person_phone_home);
		}
		stdCCDDocinsertVo.put("PERSON_PHONE_HOME", person_phone_home);
		
		String person_phone_cell = (String)stdCCDDocVO.get("PERSON_PHONE_CELL");
		if (CommonUtil.isNotNullAndEmpty(person_phone_cell)) {
			person_phone_cell = CommonUtil.verifythephone(person_phone_cell);
		}
		stdCCDDocinsertVo.put("PERSON_PHONE_CELL", person_phone_cell);
		
		String person_phone_work = (String)stdCCDDocVO.get("PERSON_PHONE_WORK");
		if (CommonUtil.isNotNullAndEmpty(person_phone_work)) {
			person_phone_work = CommonUtil.verifythephone(person_phone_work);
		}
		stdCCDDocinsertVo.put("PERSON_PHONE_WORK", person_phone_work);
		
		stdCCDDocinsertVo.put("ADDRESS_USE", stdCCDDocVO.get("ADDRESS_USE"));
		stdCCDDocinsertVo.put("USEABLE_PERIOD_LOW", stdCCDDocVO.get("USEABLE_PERIOD_LOW"));
		stdCCDDocinsertVo.put("USEABLE_PERIOD_HIGH", stdCCDDocVO.get("USEABLE_PERIOD_HIGH"));
		stdCCDDocinsertVo.put("CREATE_DATE", new Date(Calendar.getInstance().getTimeInMillis()));
		stdCCDDocinsertVo.put("MPI", stdCCDDocVO.get("MPI"));
		stdCCDDocinsertVo.put("CURRENT_PROCESS_STATUS", "5");
		stdCCDDocinsertVo.put("HIE_NAME", stdCCDDocVO.get("HIE_NAME"));

		// Changing the logic from race_code to race_desc 13-JUL-18
		stdCCDDocinsertVo.put("RACE_WHITE", "0");
		stdCCDDocinsertVo.put("RACE_BLACK", "0");
		stdCCDDocinsertVo.put("RACE_ASIAN", "0");
		stdCCDDocinsertVo.put("RACE_INDIAN", "0");
		stdCCDDocinsertVo.put("RACE_PACIFIC", "0");
		stdCCDDocinsertVo.put("RACE_UNKNOWN", "0");
		stdCCDDocinsertVo.put("RACE_OTHER", "0");
		StringBuilder _raceUnTranslatableText = new StringBuilder();
		StringBuilder _raceUnOtherText = new StringBuilder();
		String race_cd = (String) stdCCDDocVO.get("RACE_CODE");
		String race_desc = (String) stdCCDDocVO.get("RACE_DESC");
		// NEW CODE ADDED FOR ETHNICITY TO GET ADDED IF NOT FOUND IN CODED TABLE
		String ethnicity = (String) stdCCDDocVO.get("ETHNICITY");
		if (CommonUtil.isNotNullAndEmpty(ethnicity)) {
			ethnicity = ethnicity.toUpperCase().trim();
			if (ethnicityMap.get(ethnicity) != null) {
				String ethCodeValid = ethnicityMap.get(ethnicity);
				if (!ethCodeValid.equals("99")) {
					stdCCDDocinsertVo.put("ETHNICITY", ethCodeValid); // LabCommonUtils.checkEthnicityData(ethnicity));
				}
				String raceCodeValid = raceMap.get(ethnicity);
				if (!raceCodeValid.equals("UNTRANSLATABLE")) {
					if (raceCodeValid.equalsIgnoreCase("BLACK")) {
						stdCCDDocinsertVo.put("RACE_BLACK", "1");
						stdCCDDocinsertVo.put("RACE_UNKNOWN", "0");
					} else if (raceCodeValid.equalsIgnoreCase("WHITE")) {
						stdCCDDocinsertVo.put("RACE_WHITE", "1");
						stdCCDDocinsertVo.put("RACE_UNKNOWN", "0");
					} else if (raceCodeValid.equalsIgnoreCase("INDIAN")) {
						stdCCDDocinsertVo.put("RACE_INDIAN", "1");
						stdCCDDocinsertVo.put("RACE_UNKNOWN", "0");
					} else if (raceCodeValid.equalsIgnoreCase("PACIFIC")) {
						stdCCDDocinsertVo.put("RACE_PACIFIC", "1");
						stdCCDDocinsertVo.put("RACE_UNKNOWN", "0");
					} else if (raceCodeValid.equalsIgnoreCase("ASIAN")) {
						stdCCDDocinsertVo.put("RACE_ASIAN", "1");
						stdCCDDocinsertVo.put("RACE_UNKNOWN", "0");
					} else if (raceCodeValid.equalsIgnoreCase("ASIAN,PACIFIC")) {
						stdCCDDocinsertVo.put("RACE_ASIAN", "1");
						stdCCDDocinsertVo.put("RACE_PACIFIC", "1");
						stdCCDDocinsertVo.put("RACE_UNKNOWN", "0");
					} else if (raceCodeValid.equalsIgnoreCase("OTHER")) {
						stdCCDDocinsertVo.put("RACE_OTHER", "1");
						stdCCDDocinsertVo.put("RACE_UNKNOWN", "0");
					}
				}
			} else {
				stdCCDDocinsertVo = null;
				BigDecimal uphnId = (BigDecimal) stdCCDDocVO.get("UPHN_TRANX_IN_ID");
				BigDecimal document_id = (BigDecimal) stdCCDDocVO.get("DOCUMENT_ID");
				stdCCDDocVO.put("RACE_CODE_ERROR", ethnicity);
				commonDocumentService.updateRawDocumentStatus(document_id.intValue(), "RAW_CCD_DOC", 4);
				return 4;
			}
		}

		/* Race Logic Start From Here */
		if (CommonUtil.isNotNullAndEmpty(race_desc)) {
			race_cd = race_cd.toUpperCase().trim();
			race_desc = race_desc.toUpperCase().trim();
			String[] list_cd = race_cd.split("\\|");
			String[] list_desc = race_desc.split("\\|");
			for (int i = 0; i < list_desc.length; i++) {
				// String race_item = list_cd[i];
				String race_description = list_desc[i];
				if (race_description.contains(",")) {
					// String[] parts_code = race_item.split("\\,");
					String[] parts_desc = race_description.split("\\,");
					for (int j = 0; j < parts_desc.length; j++) {
						// String raceCode = parts_code[j].trim();
						String raceDescr = parts_desc[j].trim();
						if (raceMap.get(raceDescr) != null) {
							if (!stdCCDDocinsertVo.get("ETHNICITY").equals("01")
									&& !stdCCDDocinsertVo.get("ETHNICITY").equals("02")) {
								String theEthnicity = ethnicityMap.get(raceDescr);
								if (CommonUtil.isNotNullAndEmpty(theEthnicity) && !(theEthnicity).equals("U")) {
									theEthnicity = theEthnicity.toUpperCase().trim();
									if (!theEthnicity.equals("99")) {
										stdCCDDocinsertVo.put("ETHNICITY", theEthnicity);
									}
									// continue;
								}
							}
							// Using Race_description here
							String theRaceDesc = raceMap.get(raceDescr);
							theRaceDesc = theRaceDesc.toUpperCase().trim();
							if (theRaceDesc.contains(",")) {
								String[] par_code = theRaceDesc.split("\\,");
								for (int p = 0; p < par_code.length; p++) {
									String raceCodeVal = par_code[p];
									String theRaceCodeCom = raceMap.get(raceCodeVal);
									log.error("item nos {} : = {}", p, par_code.toString());
									if (theRaceCodeCom.equalsIgnoreCase("BLACK")) {
										stdCCDDocinsertVo.put("RACE_BLACK", "1");
										stdCCDDocinsertVo.put("RACE_UNKNOWN", "0");
									} else if (theRaceCodeCom.equalsIgnoreCase("WHITE")) {
										stdCCDDocinsertVo.put("RACE_WHITE", "1");
										stdCCDDocinsertVo.put("RACE_UNKNOWN", "0");
									} else if (theRaceCodeCom.equalsIgnoreCase("INDIAN")) {
										stdCCDDocinsertVo.put("RACE_INDIAN", "1");
										stdCCDDocinsertVo.put("RACE_UNKNOWN", "0");
									} else if (theRaceCodeCom.equalsIgnoreCase("PACIFIC")) {
										stdCCDDocinsertVo.put("RACE_PACIFIC", "1");
										stdCCDDocinsertVo.put("RACE_UNKNOWN", "0");
									} else if (theRaceCodeCom.equalsIgnoreCase("ASIAN")) {
										stdCCDDocinsertVo.put("RACE_ASIAN", "1");
										stdCCDDocinsertVo.put("RACE_UNKNOWN", "0");
									} else if (theRaceCodeCom.equalsIgnoreCase("OTHER")) {
										stdCCDDocinsertVo.put("RACE_OTHER", "1");
										stdCCDDocinsertVo.put("RACE_UNKNOWN", "0");
										_raceUnOtherText = _raceUnOtherText.append(raceDescr).append(", ");
									} else if (theRaceCodeCom.equalsIgnoreCase("UNKNOWN")) {
										if (stdCCDDocinsertVo.get("RACE_BLACK") == "0"
												&& stdCCDDocinsertVo.get("RACE_WHITE") == "0"
												&& stdCCDDocinsertVo.get("RACE_INDIAN") == "0"
												&& stdCCDDocinsertVo.get("RACE_PACIFIC") == "0"
												&& stdCCDDocinsertVo.get("RACE_ASIAN") == "0"
												&& stdCCDDocinsertVo.get("RACE_OTHER") == "0") {
											stdCCDDocinsertVo.put("RACE_UNKNOWN", "1");
										}
									} else if (theRaceCodeCom.equalsIgnoreCase("UNTRANSLATABLE")) {
										_raceUnTranslatableText = _raceUnTranslatableText.append(raceDescr)
												.append(", ");
									}
								}
							} else {
								if (theRaceDesc.equalsIgnoreCase("BLACK")) {
									stdCCDDocinsertVo.put("RACE_BLACK", "1");
									stdCCDDocinsertVo.put("RACE_UNKNOWN", "0");
								} else if (theRaceDesc.equalsIgnoreCase("WHITE")) {
									stdCCDDocinsertVo.put("RACE_WHITE", "1");
									stdCCDDocinsertVo.put("RACE_UNKNOWN", "0");
								} else if (theRaceDesc.equalsIgnoreCase("INDIAN")) {
									stdCCDDocinsertVo.put("RACE_INDIAN", "1");
									stdCCDDocinsertVo.put("RACE_UNKNOWN", "0");
								} else if (theRaceDesc.equalsIgnoreCase("PACIFIC")) {
									stdCCDDocinsertVo.put("RACE_PACIFIC", "1");
									stdCCDDocinsertVo.put("RACE_UNKNOWN", "0");
								} else if (theRaceDesc.equalsIgnoreCase("ASIAN")) {
									stdCCDDocinsertVo.put("RACE_ASIAN", "1");
									stdCCDDocinsertVo.put("RACE_UNKNOWN", "0");
								} else if (theRaceDesc.equalsIgnoreCase("ASIAN,PACIFIC")) {
									stdCCDDocinsertVo.put("RACE_ASIAN", "1");
									stdCCDDocinsertVo.put("RACE_PACIFIC", "1");
									stdCCDDocinsertVo.put("RACE_UNKNOWN", "0");
								} else if (theRaceDesc.equalsIgnoreCase("OTHER")) {
									stdCCDDocinsertVo.put("RACE_OTHER", "1");
									stdCCDDocinsertVo.put("RACE_UNKNOWN", "0");
									_raceUnOtherText = _raceUnOtherText.append(raceDescr).append(", ");
								} else if (theRaceDesc.equalsIgnoreCase("UNKNOWN")) {
									if (stdCCDDocinsertVo.get("RACE_BLACK") == "0"
											&& stdCCDDocinsertVo.get("RACE_WHITE") == "0"
											&& stdCCDDocinsertVo.get("RACE_INDIAN") == "0"
											&& stdCCDDocinsertVo.get("RACE_PACIFIC") == "0"
											&& stdCCDDocinsertVo.get("RACE_ASIAN") == "0"
											&& stdCCDDocinsertVo.get("RACE_OTHER") == "0") {
										stdCCDDocinsertVo.put("RACE_UNKNOWN", "1");
									}
								} else if (theRaceDesc.equalsIgnoreCase("UNTRANSLATABLE")) {

									_raceUnTranslatableText = _raceUnTranslatableText.append(raceDescr).append(", ");
								}
							}
						} else {
							stdCCDDocinsertVo = null;
							BigDecimal uphnId = (BigDecimal) stdCCDDocVO.get("UPHN_TRANX_IN_ID");
							BigDecimal document_id = (BigDecimal) stdCCDDocVO.get("DOCUMENT_ID");
							log.error("Document_id - {}", document_id.intValue());
							log.error("RACE_CODE_VALUE {}", raceDescr);
							stdCCDDocVO.put("RACE_CODE_ERROR", raceDescr);
							commonDocumentService.updateRawDocumentStatus(document_id.intValue(), "RAW_CCD_DOC", 4);
							// commented because we dont these anymore
							/*
							 * hitCommonDocumentService.storeHitError(new Exception(
							 * "MOVE TO STD ERROR : Race Code Value did not Map to any RACE_VALUE in CODED_HIT_RACE_DESC for UPHN_TRANX_IN_ID = "
							 * + uphnId + " new value added to CODED_HIT_RACE_DESC with RACE_VALUE as = " +
							 * raceCode + ".  "), uphnId.intValue(), "", "CCD");
							 */
							return 4;
						}
					}
				} else {
					String raceCode = list_cd[i];
					String raceDescr = list_desc[i];
					if (raceMap.get(raceDescr) != null) {
						if (!stdCCDDocinsertVo.get("ETHNICITY").equals("01")
								&& !stdCCDDocinsertVo.get("ETHNICITY").equals("02")) {
							String theEthnicity = ethnicityMap.get(raceCode);
							if (CommonUtil.isNotNullAndEmpty(theEthnicity) && !(theEthnicity).equals("U")) {
								theEthnicity = theEthnicity.toUpperCase().trim();
								if (!theEthnicity.equals("99")) {
									stdCCDDocinsertVo.put("ETHNICITY", theEthnicity);
								}
								// continue;
							}
						}
						String theRaceDesc = raceMap.get(raceDescr);
						if (theRaceDesc.contains(",")) {
							String[] parts_code = theRaceDesc.split("\\,");
							for (int j = 0; j < parts_code.length; j++) {
								String raceCodeVal = parts_code[j];
								if (raceCodeVal.equalsIgnoreCase("BLACK")) {
									stdCCDDocinsertVo.put("RACE_BLACK", "1");
									stdCCDDocinsertVo.put("RACE_UNKNOWN", "0");
								} else if (raceCodeVal.equalsIgnoreCase("WHITE")) {
									stdCCDDocinsertVo.put("RACE_WHITE", "1");
									stdCCDDocinsertVo.put("RACE_UNKNOWN", "0");
								} else if (raceCodeVal.equalsIgnoreCase("INDIAN")) {
									stdCCDDocinsertVo.put("RACE_INDIAN", "1");
									stdCCDDocinsertVo.put("RACE_UNKNOWN", "0");
								} else if (raceCodeVal.equalsIgnoreCase("PACIFIC")) {
									stdCCDDocinsertVo.put("RACE_PACIFIC", "1");
									stdCCDDocinsertVo.put("RACE_UNKNOWN", "0");
								} else if (raceCodeVal.equalsIgnoreCase("ASIAN")) {
									stdCCDDocinsertVo.put("RACE_ASIAN", "1");
									stdCCDDocinsertVo.put("RACE_UNKNOWN", "0");
								} else if (raceCodeVal.equalsIgnoreCase("OTHER")) {
									stdCCDDocinsertVo.put("RACE_OTHER", "1");
									stdCCDDocinsertVo.put("RACE_UNKNOWN", "0");
									_raceUnOtherText = _raceUnOtherText.append(raceDescr).append(", ");
								} else if (raceCodeVal.equalsIgnoreCase("UNKNOWN")) {
									if (stdCCDDocinsertVo.get("RACE_BLACK") == "0"
											&& stdCCDDocinsertVo.get("RACE_WHITE") == "0"
											&& stdCCDDocinsertVo.get("RACE_INDIAN") == "0"
											&& stdCCDDocinsertVo.get("RACE_PACIFIC") == "0"
											&& stdCCDDocinsertVo.get("RACE_ASIAN") == "0"
											&& stdCCDDocinsertVo.get("RACE_OTHER") == "0") {
										stdCCDDocinsertVo.put("RACE_UNKNOWN", "1");
									}
								} else if (raceCodeVal.equalsIgnoreCase("UNTRANSLATABLE")) {
									_raceUnTranslatableText = _raceUnTranslatableText.append(raceDescr).append(", ");
								}
							}
						} else {
							if (theRaceDesc.equalsIgnoreCase("BLACK")) {
								stdCCDDocinsertVo.put("RACE_BLACK", "1");
								stdCCDDocinsertVo.put("RACE_UNKNOWN", "0");
							} else if (theRaceDesc.equalsIgnoreCase("WHITE")) {
								stdCCDDocinsertVo.put("RACE_WHITE", "1");
								stdCCDDocinsertVo.put("RACE_UNKNOWN", "0");
							} else if (theRaceDesc.equalsIgnoreCase("INDIAN")) {
								stdCCDDocinsertVo.put("RACE_INDIAN", "1");
								stdCCDDocinsertVo.put("RACE_UNKNOWN", "0");
							} else if (theRaceDesc.equalsIgnoreCase("PACIFIC")) {
								stdCCDDocinsertVo.put("RACE_PACIFIC", "1");
								stdCCDDocinsertVo.put("RACE_UNKNOWN", "0");
							} else if (theRaceDesc.equalsIgnoreCase("ASIAN")) {
								stdCCDDocinsertVo.put("RACE_ASIAN", "1");
								stdCCDDocinsertVo.put("RACE_UNKNOWN", "0");
							} else if (theRaceDesc.equalsIgnoreCase("OTHER")) {
								stdCCDDocinsertVo.put("RACE_OTHER", "1");
								stdCCDDocinsertVo.put("RACE_UNKNOWN", "0");
								_raceUnOtherText = _raceUnOtherText.append(raceDescr).append(", ");
							} else if (theRaceDesc.equalsIgnoreCase("UNKNOWN")) {
								if (stdCCDDocinsertVo.get("RACE_BLACK") == "0"
										&& stdCCDDocinsertVo.get("RACE_WHITE") == "0"
										&& stdCCDDocinsertVo.get("RACE_INDIAN") == "0"
										&& stdCCDDocinsertVo.get("RACE_PACIFIC") == "0"
										&& stdCCDDocinsertVo.get("RACE_ASIAN") == "0"
										&& stdCCDDocinsertVo.get("RACE_OTHER") == "0") {
									stdCCDDocinsertVo.put("RACE_UNKNOWN", "1");
								}
							} else if (theRaceDesc.equalsIgnoreCase("UNTRANSLATABLE")) {
								_raceUnTranslatableText = _raceUnTranslatableText.append(raceDescr).append(", ");
							}
						}
					} else {
						stdCCDDocinsertVo = null;
						BigDecimal uphnId = (BigDecimal) stdCCDDocVO.get("UPHN_TRANX_IN_ID");
						BigDecimal document_id = (BigDecimal) stdCCDDocVO.get("DOCUMENT_ID");
						stdCCDDocVO.put("RACE_CODE_ERROR", raceDescr);
						commonDocumentService.updateRawDocumentStatus(document_id.intValue(), "RAW_CCD_DOC", 4);
						/*
						 * hitCommonDocumentService.storeHitError(new Exception(
						 * "MOVE TO STD ERROR : Race Code Value did not Map to any RACE_VALUE in CODED_HIT_RACE_DESC for UPHN_TRANX_IN_ID = "
						 * + uphnId + " new value added to CODED_HIT_RACE_DESC with RACE_VALUE as = " +
						 * raceCode + ".  "), uphnId.intValue(), "","CCD");
						 */
						return 4;
					}
					if (_raceUnTranslatableText != null) {
						String othUntransstr = _raceUnTranslatableText.toString();
						if (CommonUtil.isNotNullAndEmpty(othUntransstr)) {
							othUntransstr = othUntransstr.substring(0, othUntransstr.length() - 2);
							stdCCDDocinsertVo.put("RACE_UNTRANSLATABLE", othUntransstr);
						}
					}
					if (_raceUnOtherText != null) {
						String othRacestr = _raceUnOtherText.toString();
						if (CommonUtil.isNotNullAndEmpty(othRacestr)) {
							othRacestr = othRacestr.substring(0, othRacestr.length() - 2);
							stdCCDDocinsertVo.put("RACE_OTHER_TEXT", othRacestr);
						}
					}

				}
			}
		} else {
			ethnicity = (String) stdCCDDocVO.get("ETHNICITY");
			if (CommonUtil.isNotNullAndEmpty(ethnicity)) {
				ethnicity = ethnicity.toUpperCase().trim();
				if (raceMap.get(ethnicity) != null) {
					String theRaceDesc = raceMap.get(ethnicity);
					if (theRaceDesc.equalsIgnoreCase("BLACK")) {
						stdCCDDocinsertVo.put("RACE_BLACK", "1");
						stdCCDDocinsertVo.put("RACE_UNKNOWN", "0");
					} else if (theRaceDesc.equalsIgnoreCase("WHITE")) {
						stdCCDDocinsertVo.put("RACE_WHITE", "1");
						stdCCDDocinsertVo.put("RACE_UNKNOWN", "0");
					} else if (theRaceDesc.equalsIgnoreCase("INDIAN")) {
						stdCCDDocinsertVo.put("RACE_INDIAN", "1");
						stdCCDDocinsertVo.put("RACE_UNKNOWN", "0");
					} else if (theRaceDesc.equalsIgnoreCase("PACIFIC")) {
						stdCCDDocinsertVo.put("RACE_PACIFIC", "1");
						stdCCDDocinsertVo.put("RACE_UNKNOWN", "0");
					} else if (theRaceDesc.equalsIgnoreCase("ASIAN")) {
						stdCCDDocinsertVo.put("RACE_ASIAN", "1");
						stdCCDDocinsertVo.put("RACE_UNKNOWN", "0");
					} else if (theRaceDesc.equalsIgnoreCase("OTHER")) {
						stdCCDDocinsertVo.put("RACE_OTHER", "1");
						stdCCDDocinsertVo.put("RACE_UNKNOWN", "0");
					} else if (theRaceDesc.equalsIgnoreCase("UNKNOWN")) {
						if (stdCCDDocinsertVo.get("RACE_BLACK") == "0" && stdCCDDocinsertVo.get("RACE_WHITE") == "0"
								&& stdCCDDocinsertVo.get("RACE_INDIAN") == "0"
								&& stdCCDDocinsertVo.get("RACE_PACIFIC") == "0"
								&& stdCCDDocinsertVo.get("RACE_ASIAN") == "0"
								&& stdCCDDocinsertVo.get("RACE_OTHER") == "0") {
							stdCCDDocinsertVo.put("RACE_UNKNOWN", "1");
						}
					}
				}
			}
		}

		if (stdCCDDocinsertVo != null) {
			BigDecimal document_id = (BigDecimal) stdCCDDocVO.get("DOCUMENT_ID");
			int resultCount = ccdDocumentDAO.insertCCDFromRAWToSTD(stdCCDDocinsertVo);
			if (resultCount > 0) {
				commonDocumentService.updateRawDocumentStatus(document_id.intValue(), "RAW_CCD_DOC", 5);
			}
			return resultCount;
		} else {
			return 0;
		}

	}

	public int writeRawDiagnosisDataToStdTable(Map<Object, Object> stdCCDDiagnosisVO) {
		Map<Object, Object> stdCCDDiagnosisinsertVo = new HashMap<>();
		stdCCDDiagnosisinsertVo.put("DIAGNOSIS_ID", stdCCDDiagnosisVO.get("DIAGNOSIS_ID"));
		stdCCDDiagnosisinsertVo.put("ENCOUNTER_DOC_ID", stdCCDDiagnosisVO.get("ENCOUNTER_DOC_ID"));
		stdCCDDiagnosisinsertVo.put("DIAGNOSIS_TEXT", stdCCDDiagnosisVO.get("DIAGNOSIS_TEXT"));
		// FILTER THE DIAGNOSIS CODE BY USING SUBSTRING
		String DIAGNOSIS_CODE = (String) stdCCDDiagnosisVO.get("DIAGNOSIS_CODE");
		String str = DIAGNOSIS_CODE;
		int index = str.indexOf('_');
		if (index > 0) {
			DIAGNOSIS_CODE = str.substring(0, index);
		}
		stdCCDDiagnosisinsertVo.put("DIAGNOSIS_CODE", DIAGNOSIS_CODE);
		stdCCDDiagnosisinsertVo.put("DIAGNOSIS_CODE_SYSTEM_CODE", stdCCDDiagnosisVO.get("DIAGNOSIS_CODE_SYSTEM_CODE"));
		stdCCDDiagnosisinsertVo.put("DIAGNOSIS_CODE_SYSTEM_OID", stdCCDDiagnosisVO.get("DIAGNOSIS_CODE_SYSTEM_OID"));
		stdCCDDiagnosisinsertVo.put("UPHN_TRANX_IN_ID", stdCCDDiagnosisVO.get("UPHN_TRANX_IN_ID"));
		stdCCDDiagnosisinsertVo.put("CCD_DOC_ID", stdCCDDiagnosisVO.get("CCD_DOC_ID"));
		stdCCDDiagnosisinsertVo.put("ENCOUNTER_SEQ_NO", stdCCDDiagnosisVO.get("ENCOUNTER_SEQ_NO"));
		stdCCDDiagnosisinsertVo.put("DIAGNOSIS_SEQ_NO", stdCCDDiagnosisVO.get("DIAGNOSIS_SEQ_NO"));
		stdCCDDiagnosisinsertVo.put("START_DATE_TEXT", stdCCDDiagnosisVO.get("START_DATE_TEXT"));
		return ccdDocumentDAO.insertDiagnosisFromRAWToSTD(stdCCDDiagnosisinsertVo);
	}

	public PersonViewVO getStdCCDDocumentForClericalReview(int documentId) {
		PersonViewVO clericalPersonViewVO = ccdDocumentDAO.getStdCCDDocumentForClericalReview(documentId);
		return clericalPersonViewVO;
	}

	public void updateRawDiagnosisMovedToSTDStatus(int currentDocumentId, String tableName, char statusCode) {
		Map<Object, Object> inputOutputParameterMap = new HashMap<>();
		inputOutputParameterMap.put("tableName", tableName);
		inputOutputParameterMap.put("statusCode", statusCode);
		inputOutputParameterMap.put("diagnosisID", currentDocumentId);
		int resultcount = ccdDocumentDAO.updateRawDiagnosisMovedToSTDStatusCode(inputOutputParameterMap);
		if (resultcount == 0) {
			throw new DataRetrievalFailureException(new StringBuilder(
					"Failed to update MOVED_TO_STD in " + tableName + " for CCD_DOC_ID = " + currentDocumentId)
							.toString());
		}
	}

	public void updateRawReviewedStatus(int currentDocumentId, String tableName, char statusCode) {
		Map<Object, Object> inputOutputParameterMap = new HashMap<>();
		inputOutputParameterMap.put("tableName", tableName);
		inputOutputParameterMap.put("statusCode", statusCode);
		inputOutputParameterMap.put("diagnosisID", currentDocumentId);
		int resultcount = ccdDocumentDAO.updateRawReviewedStatusCode(inputOutputParameterMap);
		if (resultcount == 0) {
			throw new DataRetrievalFailureException(new StringBuilder(
					"Failed to update MOVED_TO_STD in " + tableName + " for CCD_DOC_ID = " + currentDocumentId)
							.toString());
		}
	}

	public void updateRawProcessDate(int currentDocumentId, String tableName, java.util.Date processDate) {
		Map<Object, Object> inputOutputParameterMap = new HashMap<>();
		inputOutputParameterMap.put("tableName", tableName);
		inputOutputParameterMap.put("processDate", processDate);
		inputOutputParameterMap.put("document_ID", currentDocumentId);
		int resultcount = ccdDocumentDAO.updateRawProcessDateInp(inputOutputParameterMap);
		if (resultcount == 0) {
			throw new DataRetrievalFailureException(new StringBuilder(
					"Failed to update CURRENT_STATUS_CODE in " + tableName + " for DOCUMENT_ID = " + currentDocumentId)
							.toString());
		}
	}

	public List<Map<Object, Object>> getNewRawCCDList() {

		return ccdDocumentDAO.getNewRawCCDList();

	}

	public void writeRawCCDData(RawCCDDocVO theCCD, boolean individualLabResultInsert) {
		int docId = theCCD.getDocumentId();
		RawCCDPersonDocVO thePerson = theCCD.getThePerson();
		// thePerson.setDOCUMENT_ID(docId);
		thePerson.setCURRENT_PROCESS_STATUS(1);
		thePerson.setHieName(theCCD.getHieName());
		thePerson.setPayloadname(theCCD.getPayloadName());
		ccdDocumentDAO.insertRawCcdDocData(thePerson);
		// ADDRESSES
		List<RawCCDPersonAddressVO> theAddresses = theCCD.getTheAddressList();
		if (theAddresses != null && theAddresses.size() > 0) {
			Map<String, Object> inputParameterMapAddress = new HashMap<>();
			inputParameterMapAddress.put("ccdDocId", docId);
			inputParameterMapAddress.put("addressesList", theAddresses);
			ccdDocumentDAO.insertCcdAddressList(inputParameterMapAddress);
		}
		// PROVIDERS
		List<RawCCDProviderVO> theProviders = theCCD.getTheProviderList();
		if (theProviders != null && theProviders.size() > 0) {
			Map<String, Object> inputParameterMapProviders = new HashMap<>();
			inputParameterMapProviders.put("ccdDocId", docId);
			inputParameterMapProviders.put("providersList", theProviders);
			
			ccdDocumentDAO.insertCcdProvidersList(inputParameterMapProviders);
		}
		// PAYERS
		List<RawCCDPayerVO> thePayers = theCCD.getThePayerList();
		if (thePayers != null && thePayers.size() > 0) {
			Map<String, Object> inputParameterMapPayers = new HashMap<>();
			inputParameterMapPayers.put("ccdDocId", docId);
			inputParameterMapPayers.put("payersList", thePayers);
			ccdDocumentDAO.insertCcdPayersList(inputParameterMapPayers);
		}
		// LAB TESTS and LAB RESULTS
		/*
		 * RAW_CCD_LAB_TEST.CCD_LAB_TEST_ID values are assigned here rather than while
		 * walking the XML tree of the CCD so as to keep the code that gets the
		 * RawCCDLabTestVO (ccdDocumentService.getRawCCDDocVOFromDocument) free of any
		 * database use. Use of any Spring/Mybatis code in there would prevent it from
		 * being called from simple driver programs outside of Spring for development
		 * and testing and other possible ad hoc uses.
		 */
		List<RawCCDLabTestVO> theLabTestVOList = theCCD.getTheLabTestList();
		int labTestVOListLength = theLabTestVOList.size();
		// System.out.println("Number of Lab Tests: " +
		// theLabTestVOList.size());
		if (theLabTestVOList != null && theLabTestVOList.size() > 0) {
			List<Integer> labTestSequences = ccdDocumentDAO.getLabTestSequences(labTestVOListLength);
			List<RawCCDLabResultVO> completeLabResultVOList = new ArrayList<>();
			for (int i = 0; i < labTestVOListLength; i++) {
				int labTestId = labTestSequences.get(i);
				RawCCDLabTestVO rawCCDLabTestVO = theLabTestVOList.get(i);
				rawCCDLabTestVO.setCcdLabTestId(labTestId);
				List<RawCCDLabResultVO> labResultVOList = rawCCDLabTestVO.getTheLabResult();

				if (labResultVOList != null && labResultVOList.size() > 0) {
					for (RawCCDLabResultVO theLabResultVO : labResultVOList) {
						theLabResultVO.setCcdLabTestId(labTestId);
					}
					completeLabResultVOList.addAll(labResultVOList);
				}
			}
			Map<String, Object> inputParameterForLabInsert = new HashMap<>();
			inputParameterForLabInsert.put("ccdDocId", docId);
			//@added on 10/30 
			if (theLabTestVOList.size() > 0) {

				int Size = theLabTestVOList.size();

				// Where condition can accept 1000 rows at a time.
				for (int index = 0; index < Size; index = index + 1000) {

					if (index + 1000 > Size) {
						inputParameterForLabInsert.put("LabsList", theLabTestVOList.subList(index, Size));
					} else {
						inputParameterForLabInsert.put("LabsList", theLabTestVOList.subList(index, index + 1000));
					}
					
					ccdDocumentDAO.insertCcdLabTestList(inputParameterForLabInsert);

				}
			}
			
			if (completeLabResultVOList != null && completeLabResultVOList.size() > 0) {
				if (!individualLabResultInsert) {
					Map<String, Object> labResultMap = new HashMap<>();
					labResultMap.put("ccdDocId", docId);
					labResultMap.put("labResultsList", completeLabResultVOList);
					ccdDocumentDAO.insertCcdLabResultsList(labResultMap);
				} else {
					for (RawCCDLabResultVO resultVO : completeLabResultVOList) {
						int labTestId = resultVO.getCcdLabTestId();
						int labResultId = resultVO.getCcdLabResultId();
						int testNumber = resultVO.getTestSeqNo();
						int resultNumber = resultVO.getResultSeqNo();
						String resultValue = resultVO.getResultValue();
						if (resultValue.length() > 3999) {
							resultValue = CommonUtil.truncatedToByteLength(resultValue, 4000);
							resultVO.setResultValue(resultValue);
						}

						try {
							ccdDocumentDAO.insertRawCcdLabResult(resultVO);
						} catch (Exception e) {
							log.error(
									"Failed to insert individual lab result for CCD with DOCUMENT_ID = {}, Lab test with ID = {}, Lab result with ID = {}",
									docId, labTestId, labResultId);
							log.error("Number of Test = {}, Number of Result = {} \nException message: {}", testNumber,
									resultNumber, e.getMessage());

						}
					}
				}
			}
		}

		// ENCOUNTERS and DIAGNOSES
		/*
		 * RAW_CCD_ENCOUNTER.ENCOUNTER_DOC_ID values are assigned here rather than while
		 * walking the XML tree of the CCD so as to keep the code that gets the
		 * RawCCDEncounterVO (ccdDocumentService.getRawCCDDocVOFromDocument) free of any
		 * database use. Use of any Spring/Mybatis code in there would prevent it from
		 * being called from simple driver programs outside of Spring for development
		 * and testing and other possible ad hoc uses.
		 */
		List<RawCCDEncounterVO> theEncounterVOList = theCCD.getTheEncounterList();
		int encounterVOListLength = theEncounterVOList.size();
		if (theEncounterVOList != null && theEncounterVOList.size() > 0) {
			List<Integer> encounterSequences = ccdDocumentDAO.getEncounterSequences(encounterVOListLength);
			List<RawCCDDiagnosisVO> completeDiagnosisList = new ArrayList<>();
			List<RawCCDEncounterVO> _currentList = new ArrayList<>();
			for (int i = 0; i < encounterVOListLength; i++) {
				int encounterDocId = encounterSequences.get(i);
				RawCCDEncounterVO rawCCDEncounterVO = theEncounterVOList.get(i);
				rawCCDEncounterVO.setEncounterDocId(encounterDocId);
				List<RawCCDDiagnosisVO> oneEncounterDiagnosisList = rawCCDEncounterVO.getTheDiagnoses();
				if (oneEncounterDiagnosisList != null && oneEncounterDiagnosisList.size() > 0) {
					for (RawCCDDiagnosisVO theDiagnosisVO : oneEncounterDiagnosisList) {
						theDiagnosisVO.setEncounterDocId(encounterDocId);
					}
					completeDiagnosisList.addAll(oneEncounterDiagnosisList);
				}
				_currentList.add(rawCCDEncounterVO);

				if (completeDiagnosisList.size() > 2000 || _currentList.size() > 1000) {
					Map<String, Object> inputParameterForEncounters = new HashMap<>();
					inputParameterForEncounters.put("ccdDocId", docId);
					inputParameterForEncounters.put("encounterList", _currentList);
					ccdDocumentDAO.insertCcdEncounterList(inputParameterForEncounters);
					_currentList = new ArrayList<RawCCDEncounterVO>();
					if (completeDiagnosisList.size() > 0) {
						Map<String, Object> diagnosisMap = new HashMap<>();
						diagnosisMap.put("diagnosisList", completeDiagnosisList);
						ccdDocumentDAO.insertCcdDiagnosisList(diagnosisMap);
						completeDiagnosisList = new ArrayList<RawCCDDiagnosisVO>();
					}
				}
			}
			if (_currentList.size() > 0) {
				Map<String, Object> inputParameterForEncounters = new HashMap<>();
				inputParameterForEncounters.put("ccdDocId", docId);
				inputParameterForEncounters.put("encounterList", _currentList);
				ccdDocumentDAO.insertCcdEncounterList(inputParameterForEncounters);
				_currentList = new ArrayList<RawCCDEncounterVO>();
			}
			if (completeDiagnosisList.size() > 0) {
				Map<String, Object> diagnosisMap = new HashMap<>();
				diagnosisMap.put("diagnosisList", completeDiagnosisList);
				ccdDocumentDAO.insertCcdDiagnosisList(diagnosisMap);
			}
		}
		// MEDS
		List<RawCCDMedVO> theMeds = theCCD.getTheMedList();
		if (theMeds != null && theMeds.size() > 0) {
			Map<String, Object> inputParameterMapMed = new HashMap<>();
			inputParameterMapMed.put("ccdDocId", docId);
			
			int Size = theMeds.size();

			// Where condition can accept 1000 arguments.
			for (int index = 0; index < Size; index = index + 1000) {

				if (index + 1000 > Size) {
					inputParameterMapMed.put("medsList", theMeds.subList(index, Size));
				} else {
					inputParameterMapMed.put("medsList", theMeds.subList(index, index + 1000));
				}

				ccdDocumentDAO.insertCcdMedsList(inputParameterMapMed);

			}
		}
		// PROBLEMS
		List<RawCCDProblemVO> theProblems = theCCD.getTheProblemList();
		if (theProblems != null && theProblems.size() > 0) {
			Map<String, Object> inputParameterProblems = new HashMap<>();
			inputParameterProblems.put("ccdDocId", docId);
			int Size = theProblems.size();
		
			// Where condition can accept 1000 arguments.
			for (int index = 0; index < Size; index = index + 1000) {

				if (index + 1000 > Size) {
					inputParameterProblems.put("problemsList", theProblems.subList(index, Size));
				} else {
					inputParameterProblems.put("problemsList", theProblems.subList(index, index + 1000));
				}
				
				
				
				ccdDocumentDAO.insertCcdProblemsList(inputParameterProblems);

			}
		}

	}

	public List<RawCCDPersonAddressVO> getCCDAddressList(int documentID) {
		return ccdDocumentDAO.getCCDAddressList(documentID);
	}

	public List<RawCCDProviderVO> getCCDProvidersList(int documentID) {
		return ccdDocumentDAO.getCCDProvidersList(documentID);
	}

	public List<RawCCDProblemVO> getCCDProblemsList(int documentID) {
		return ccdDocumentDAO.getCCDProblemsList(documentID);
	}

	public List<RawCCDEncounterVO> getCCDEncountersList(int documentID) {
		Map<Object, Object> inputOutputParameterMap = new HashMap<>();
		inputOutputParameterMap.put("CCD_DOC_ID", documentID);
		List<RawCCDEncounterVO> theEncounterList = new ArrayList<>();
		theEncounterList = ccdDocumentDAO.getCCDEncountersList(documentID);
		if (theEncounterList != null && theEncounterList.size() > 0) {
			for (int i = 0; i < theEncounterList.size(); i++) {
				RawCCDEncounterVO rawCCDEncounterVO = new RawCCDEncounterVO();
				rawCCDEncounterVO = theEncounterList.get(i);
				if (rawCCDEncounterVO != null) {
					int encounterDocId = rawCCDEncounterVO.getEncounterDocId();
					if (encounterDocId > 0) {
						List<RawCCDDiagnosisVO> lstDiagnosisList = new ArrayList<>();
						lstDiagnosisList = ccdDocumentDAO.getDiagnosisList(encounterDocId);
						if (lstDiagnosisList != null && lstDiagnosisList.size() > 0) {
							rawCCDEncounterVO.setTheDiagnoses(lstDiagnosisList);
						}
					}
				}
			}

		}
		return theEncounterList;
	}

	public List<RawCCDLabTestVO> getCCDLabTestsList(int documentID) {
		Map<Object, Object> inputOutputParameterMap = new HashMap<>();
		inputOutputParameterMap.put("CCD_DOC_ID", documentID);
		List<RawCCDLabTestVO> theLabTestList = new ArrayList<>();
		theLabTestList = ccdDocumentDAO.getCCDLabTestsList(documentID);
		if (theLabTestList != null && theLabTestList.size() > 0) {
			for (int i = 0; i < theLabTestList.size(); i++) {
				RawCCDLabTestVO rawCCDLabTestVO = new RawCCDLabTestVO();
				rawCCDLabTestVO = theLabTestList.get(i);
				if (rawCCDLabTestVO != null) {
					int labTestId = rawCCDLabTestVO.getCcdLabTestId();
					if (labTestId > 0) {
						List<RawCCDLabResultVO> lstLabResultList = new ArrayList<>();
						lstLabResultList = ccdDocumentDAO.getLabResultsList(labTestId);
						if (lstLabResultList != null && lstLabResultList.size() > 0) {
							rawCCDLabTestVO.setTheLabResult(lstLabResultList);
						}
					}
				}
			}
		}
		return theLabTestList;
	}

	public List<RawCCDMedVO> getCCDMedicationsList(int documentID) {
		return ccdDocumentDAO.getCCDMedicationsList(documentID);
	}

	public List<RawCCDPayerVO> getCCDPayersList(int documentID) {
		return ccdDocumentDAO.getCCDPayersList(documentID);
	}

	public void addUphnRecord(Map<String, Object> uphnData) {
		ccdDocumentDAO.addUphnRecord(uphnData);
		
	}


	public boolean processParsedHITdata(Map<String, Object> uphnData, String recordType, String decryptedPayloadString,
			boolean individualLabResultInserts, Integer uphnId) {
		
		boolean isSuccessful = true;
		String comments = null;
		String repriseFlag = null;
		String processingStage = (String) uphnData.get("p_processingStage");
		String hieName =  (String) uphnData.get("p_hieName");
		String fileName = (String) uphnData.get("p_fileName");
		java.sql.Date dateCreated = (Date) uphnData.get("p_dateCreated");
		Integer payLoadSize =  (Integer) uphnData.get("p_fileSize");
		
		
		try {
			isSuccessful = preMatchHITSchedulerHelper.insertParsedHitData(decryptedPayloadString,uphnId,processingStage,hieName,fileName,dateCreated,recordType,individualLabResultInserts,payLoadSize);
		}catch (SAXException saxe) {
			// CCD failure to parse
			String errorMessage = "Row from UPHN_TRANX_IN with UPHN_TRANX_IN_ID '" + uphnId
			+ "' failed to parse as a CCD message; " + "message text and stacktrace stored to HIT_ERROR.";
			log.error(errorMessage + " " + CommonUtil.getStackTrace(saxe).substring(0, 200));
			hitCommonDocumentService.storeHitError(saxe, uphnId, decryptedPayloadString, recordType, repriseFlag, comments);
			isSuccessful = false;
		}catch (HL7Exception hl7e) {
			// HL7 failure to parse
			log.error("Row from UPHN_TRANX_IN with UPHN_TRANX_IN_ID '" + uphnId
					+ "' failed to parse as an HL7 message; " + "message text and stacktrace stored to HIT_ERROR.");
			hitCommonDocumentService.storeHitError(hl7e, uphnId, decryptedPayloadString, recordType,repriseFlag, comments);
			isSuccessful = false;
		} catch (Exception e) {
				// Expiration failure to parse and anything else
				log.error("Exception processing from UPHN_TRANX_IN_ID = " + uphnId + " at stage :" + processingStage + ":\n"
				+ CommonUtil.getStackTrace(e).substring(0, 200));
				if ((e.getMessage() != null)
				&& e.getMessage().contains("unique constraint")) {
				// call DAO for updating the table comment colum as duplicate(payloadname)
				// passing comments as "Duplicate (PayloadName)"
				comments = "Duplicate PayloadName";
				repriseFlag="N";
				}
				hitCommonDocumentService.storeHitError(e, uphnId, decryptedPayloadString, recordType,repriseFlag, comments);
				isSuccessful = false;
		} catch (Throwable t) {
				log.error("Exception processing from UPHN_TRANX_IN_ID = " + uphnId + " at stage :" + processingStage + ":\n"
				+ CommonUtil.getStackTrace(t).substring(0, 200));
				hitCommonDocumentService.storeHitError(t, uphnId, decryptedPayloadString, recordType,repriseFlag, comments);
				isSuccessful = false;
			}
			
		if(!isSuccessful && recordType.equals("CCD")) { // if unsuccessful
			uphnData.put("p_ccdDocument",decryptedPayloadString.getBytes(StandardCharsets.UTF_8));//getting data in byte array
			log.error("updating uphn table with uphn id-" + uphnId);
			ccdDocumentDAO.updateUphnRecord(uphnData);
			log.error("updated uphn_tranx_in table with ccd record successfully");
		}
		
			return isSuccessful;
	}


	
}
