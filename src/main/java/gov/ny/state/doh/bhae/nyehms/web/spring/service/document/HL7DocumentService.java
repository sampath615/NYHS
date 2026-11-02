package gov.ny.state.doh.bhae.nyehms.web.spring.service.document;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import ca.uhn.hl7v2.DefaultHapiContext;
import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.HapiContext;
import ca.uhn.hl7v2.model.DataTypeException;
import ca.uhn.hl7v2.model.Message;
import ca.uhn.hl7v2.model.Varies;
import ca.uhn.hl7v2.model.v25.datatype.CE;
import ca.uhn.hl7v2.model.v25.datatype.CX;
import ca.uhn.hl7v2.model.v25.datatype.DTM;
import ca.uhn.hl7v2.model.v25.datatype.HD;
import ca.uhn.hl7v2.model.v25.datatype.XAD;
import ca.uhn.hl7v2.model.v25.datatype.XCN;
import ca.uhn.hl7v2.model.v25.datatype.XON;
import ca.uhn.hl7v2.model.v25.datatype.XPN;
import ca.uhn.hl7v2.model.v25.group.ADT_A01_INSURANCE;
import ca.uhn.hl7v2.model.v25.group.ADT_A03_INSURANCE;
import ca.uhn.hl7v2.model.v25.group.ADT_A05_INSURANCE;
import ca.uhn.hl7v2.model.v25.group.ADT_A06_INSURANCE;
import ca.uhn.hl7v2.model.v25.group.OMP_O09_ORDER;
import ca.uhn.hl7v2.model.v25.group.ORM_O01_ORDER;
import ca.uhn.hl7v2.model.v25.group.ORU_R01_OBSERVATION;
import ca.uhn.hl7v2.model.v25.group.ORU_R01_ORDER_OBSERVATION;
import ca.uhn.hl7v2.model.v25.group.ORU_R01_PATIENT;
import ca.uhn.hl7v2.model.v25.group.ORU_R01_PATIENT_RESULT;
import ca.uhn.hl7v2.model.v25.group.ORU_R01_VISIT;
import ca.uhn.hl7v2.model.v25.group.RDE_O11_ORDER;
import ca.uhn.hl7v2.model.v25.message.ADT_A01;
import ca.uhn.hl7v2.model.v25.message.ADT_A02;
import ca.uhn.hl7v2.model.v25.message.ADT_A03;
import ca.uhn.hl7v2.model.v25.message.ADT_A05;
import ca.uhn.hl7v2.model.v25.message.ADT_A06;
import ca.uhn.hl7v2.model.v25.message.ADT_A09;
import ca.uhn.hl7v2.model.v25.message.ADT_A12;
import ca.uhn.hl7v2.model.v25.message.ADT_A17;
import ca.uhn.hl7v2.model.v25.message.ADT_A18;
import ca.uhn.hl7v2.model.v25.message.ADT_A21;
import ca.uhn.hl7v2.model.v25.message.ADT_A39;
import ca.uhn.hl7v2.model.v25.message.ADT_A45;
import ca.uhn.hl7v2.model.v25.message.ADT_A50;
import ca.uhn.hl7v2.model.v25.message.ADT_A60;
import ca.uhn.hl7v2.model.v25.message.MDM_T02;
import ca.uhn.hl7v2.model.v25.message.OMP_O09;
import ca.uhn.hl7v2.model.v25.message.ORM_O01;
import ca.uhn.hl7v2.model.v25.message.ORU_R01;
import ca.uhn.hl7v2.model.v25.message.PPR_PC1;
import ca.uhn.hl7v2.model.v25.message.RDE_O11;
import ca.uhn.hl7v2.model.v25.segment.DG1;
import ca.uhn.hl7v2.model.v25.segment.EVN;
import ca.uhn.hl7v2.model.v25.segment.IN1;
import ca.uhn.hl7v2.model.v25.segment.IN2;
import ca.uhn.hl7v2.model.v25.segment.MRG;
import ca.uhn.hl7v2.model.v25.segment.MSH;
import ca.uhn.hl7v2.model.v25.segment.NTE;
import ca.uhn.hl7v2.model.v25.segment.OBR;
import ca.uhn.hl7v2.model.v25.segment.OBX;
import ca.uhn.hl7v2.model.v25.segment.ORC;
import ca.uhn.hl7v2.model.v25.segment.PID;
import ca.uhn.hl7v2.model.v25.segment.PV1;
import ca.uhn.hl7v2.model.v25.segment.RXC;
import ca.uhn.hl7v2.model.v25.segment.RXE;
import ca.uhn.hl7v2.model.v25.segment.RXO;
import ca.uhn.hl7v2.parser.Parser;
import ca.uhn.hl7v2.validation.impl.NoValidation;
import gov.ny.state.doh.bhae.nyehms.web.spring.constants.CommonConstants;
import gov.ny.state.doh.bhae.nyehms.web.spring.dao.document.HITDocumentDAO;
import gov.ny.state.doh.bhae.nyehms.web.spring.dao.document.HL7DocumentDAO;
import gov.ny.state.doh.bhae.nyehms.web.spring.jdbc.JpaConnections;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.hl7.RawHL7AddressVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.hl7.RawHL7Dg1VO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.hl7.RawHL7DocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.hl7.RawHL7INSVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.hl7.RawHL7MshVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.hl7.RawHL7ORCVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.hl7.RawHL7ObrVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.hl7.RawHL7ObxVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.hl7.RawHL7PidVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.hl7.RawHL7Pv1VO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.hl7.RawHL7XadVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.hl7.RawHL7XpnVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.hl7.StdHL7DocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.hl7.StdHL7RaceVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.person.PersonFieldVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.CommonService;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.schedulers.ManualQuartzJobService;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonUtil;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.PersonNameUtil;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.scheduler.LabCommonUtils;

/**
 * Uses the HAPI parser library to extract some desired fields from the HL7
 * version 2.5 messages from the HIE and store them into RAW_HL7_XXX tables in
 * the database.
 * 
 * @author cdf02
 *
 */
@Service
public class HL7DocumentService {

	private static final Logger log = LoggerFactory.getLogger(HL7DocumentService.class);
	

	@Autowired
	private HITCommonDocumentService hitCommonDocumentService;
	@Autowired
	CommonService commonService;
	@Autowired
	private HL7DocumentDAO hl7DocumentDAO;
	@Autowired
	HITDocumentDAO hitDocumentDAO;
	@Autowired
	JpaConnections jpaConnections;
	@Autowired
	ManualQuartzJobService manualQuartzJobService;

	@Autowired
	private CommonDocumentService commonDocumentService;
	private Map<String, String> raceMap = null;
	private Map<String, String> ethnicityMap = null;

	public void setRaceMap(Map<String, String> _raceMap) {
		this.raceMap = _raceMap;
	}

	public void setEthnicityMap(Map<String, String> _ethnicityMap) {
		this.ethnicityMap = _ethnicityMap;
	}

	public static final String separator = ",";
	static final HapiContext context = new DefaultHapiContext();
	static Parser p;
	{
		context.setValidationContext(new NoValidation());
		p = context.getGenericParser();
		
		context.getParserConfiguration().setDefaultObx2Type("ST");   
		/* 
		 * Setting the following system property allows you to specify a default
		 * value to assume if OBX-2 is missing. 
		 * It will be parsed as though it had read.
		 */	
		context.getParserConfiguration().setInvalidObx2Type("ST");  
	}

	/**
	 * Invokes the HAPI parser on the messageText which supposedly contains a valid
	 * version 2.5 message. If the parse fails, catches the HL7Exception and writes
	 * the information to the HIT_ERROR table.
	 */
	public Message getHL7MessageFromString(int uphnId, String messageText) throws HL7Exception {
		Message m = null;

		m = p.parse(messageText);

		return m;
	}
	

	/**
	 * Experimental version, but the class cast of all ADT to (ADT_A01) doesn't
	 * work. Returned to using the other, earlier version, with specific enumerated
	 * ADT types.
	 */

	/**
	 * Processes only the MSH, PID, and PV1 segments, plus the EVN for ADTs
	 * 
	 * @return The RawHL7DocVO value object containing only the fields common to the
	 *         ORU_R01 and the various ADTs, plus the EVN event type.
	 */

	@Transactional
	public RawHL7DocVO getRawHL7DocVOFromMessage(String rawMessageText, Message hapiMsg, int uphnId) throws Exception {
		RawHL7DocVO theDocVO = new RawHL7DocVO();
		theDocVO.setMshMessageText(rawMessageText);
		String messageClass = hapiMsg.getClass().getSimpleName();
		theDocVO.setMshMessageClass(messageClass);
		MSH theMSH = null;
		PID thePID = null;
		PV1 thePV1 = null;
		OBR theOBR = null;
		ORC theORC = null;
		OBX theOBX = null;
		MRG theMRG = null;
		// NTE theNTE = null;
		IN1 theINS = null;
		IN2 theINS2 = null;
		RXO theRXO = null;
		// RXC theRXC = null;
		RXE theRXE = null;
		// int nk1Count = 0, obxCount = 0, al1Count = 0, dg1Count = 0, pr1Count
		// = 0, gt1Count = 0, in1Count = 0;
		int in1Count = 0;
		if ("ADT".equals(messageClass.substring(0, 3))) {
			EVN theEVN = null;
			// String messageType = messageClass;
			List<DG1> dg1List = new ArrayList<>();
			if ("ADT_A01".equals(messageClass) || "ADT_A04".equals(messageClass) || "ADT_A08".equals(messageClass)
					|| "ADT_A11".equals(messageClass) || "ADT_A13".equals(messageClass)
					|| "ADT_A28".equals(messageClass) || "ADT_A31".equals(messageClass)) {
				ADT_A01 theA01 = (ADT_A01) hapiMsg;
				theMSH = theA01.getMSH();
				thePID = theA01.getPID();
				thePV1 = theA01.getPV1();
				theEVN = theA01.getEVN();
				dg1List = theA01.getDG1All();
				in1Count = theA01.getINSURANCEReps();
				List<RawHL7INSVO> theRawHL7InsVOList = new ArrayList<>();
				for (int i = 0; i < in1Count; i++) {
					ADT_A01_INSURANCE theIns = theA01.getINSURANCE(i);
					theINS = theIns.getIN1();
					theINS2 = theIns.getIN2();
					RawHL7INSVO hL7INSVO = new RawHL7INSVO();
					hL7INSVO = getINSVO(theINS, theINS2);
					hL7INSVO.setUphnId(uphnId);
					theRawHL7InsVOList.add(hL7INSVO);
				}
				if (theRawHL7InsVOList != null && theRawHL7InsVOList.size() > 0) {
					theDocVO.setTheINSList(theRawHL7InsVOList);
				}
			} else if ("ADT_A02".equals(messageClass)) {
				ADT_A02 theA02 = (ADT_A02) hapiMsg;
				theMSH = theA02.getMSH();
				thePID = theA02.getPID();
				thePV1 = theA02.getPV1();
				theEVN = theA02.getEVN();
			} else if ("ADT_A03".equals(messageClass)) {
				ADT_A03 theA03 = (ADT_A03) hapiMsg;
				theMSH = theA03.getMSH();
				thePID = theA03.getPID();
				thePV1 = theA03.getPV1();
				theEVN = theA03.getEVN();
				dg1List = theA03.getDG1All();
				in1Count = theA03.getINSURANCEReps();
				List<RawHL7INSVO> theRawHL7InsVOList = new ArrayList<>();
				for (int i = 0; i < in1Count; i++) {
					ADT_A03_INSURANCE theIns = theA03.getINSURANCE(i);
					theINS = theIns.getIN1();
					theINS2 = theIns.getIN2();
					RawHL7INSVO hL7INSVO = new RawHL7INSVO();
					hL7INSVO = getINSVO(theINS, theINS2);
					hL7INSVO.setUphnId(uphnId);
					theRawHL7InsVOList.add(hL7INSVO);
				}
				if (theRawHL7InsVOList != null && theRawHL7InsVOList.size() > 0) {
					theDocVO.setTheINSList(theRawHL7InsVOList);
				}
			} else if ("ADT_A05".equals(messageClass)) {
				ADT_A05 theA05 = (ADT_A05) hapiMsg;
				theMSH = theA05.getMSH();
				thePID = theA05.getPID();
				thePV1 = theA05.getPV1();
				theEVN = theA05.getEVN();
				dg1List = theA05.getDG1All();
				in1Count = theA05.getINSURANCEReps();
				List<RawHL7INSVO> theRawHL7InsVOList = new ArrayList<>();
				for (int i = 0; i < in1Count; i++) {
					ADT_A05_INSURANCE theIns = theA05.getINSURANCE(i);
					theINS = theIns.getIN1();
					theINS2 = theIns.getIN2();
					RawHL7INSVO hL7INSVO = new RawHL7INSVO();
					hL7INSVO = getINSVO(theINS, theINS2);
					hL7INSVO.setUphnId(uphnId);
					theRawHL7InsVOList.add(hL7INSVO);
				}
				if (theRawHL7InsVOList != null && theRawHL7InsVOList.size() > 0) {
					theDocVO.setTheINSList(theRawHL7InsVOList);
				}
			} else if ("ADT_A06".equals(messageClass)) {
				ADT_A06 theA06 = (ADT_A06) hapiMsg;
				theMSH = theA06.getMSH();
				thePID = theA06.getPID();
				thePV1 = theA06.getPV1();
				theEVN = theA06.getEVN();
				dg1List = theA06.getDG1All();
				in1Count = theA06.getINSURANCEReps();
				List<RawHL7INSVO> theRawHL7InsVOList = new ArrayList<>();
				for (int i = 0; i < in1Count; i++) {
					ADT_A06_INSURANCE theIns = theA06.getINSURANCE(i);
					theINS = theIns.getIN1();
					theINS2 = theIns.getIN2();
					RawHL7INSVO hL7INSVO = new RawHL7INSVO();
					hL7INSVO = getINSVO(theINS, theINS2);
					hL7INSVO.setUphnId(uphnId);
					theRawHL7InsVOList.add(hL7INSVO);
				}
				if (theRawHL7InsVOList != null && theRawHL7InsVOList.size() > 0) {
					theDocVO.setTheINSList(theRawHL7InsVOList);
				}
			} else if ("ADT_A18".equals(messageClass)) {
				ADT_A18 theA18 = (ADT_A18) hapiMsg;
				theMSH = theA18.getMSH();
				thePID = theA18.getPID();
				thePV1 = theA18.getPV1();
				theEVN = theA18.getEVN();
			} else if ("ADT_A09".equals(messageClass)) {
				ADT_A09 theA09 = (ADT_A09) hapiMsg;
				theMSH = theA09.getMSH();
				thePID = theA09.getPID();
				thePV1 = theA09.getPV1();
				theEVN = theA09.getEVN();
				dg1List = theA09.getDG1All();
			} else if ("ADT_A21".equals(messageClass)) {
				ADT_A21 theA21 = (ADT_A21) hapiMsg;
				theMSH = theA21.getMSH();
				thePID = theA21.getPID();
				thePV1 = theA21.getPV1();
				theEVN = theA21.getEVN();
			} else if ("ADT_A45".equals(messageClass)) {
				ADT_A45 theA45 = (ADT_A45) hapiMsg;
				theMSH = theA45.getMSH();
				thePID = theA45.getPID();
				theEVN = theA45.getEVN();
			} else if ("ADT_A50".equals(messageClass)) {
				ADT_A50 theA50 = (ADT_A50) hapiMsg;
				theMSH = theA50.getMSH();
				thePID = theA50.getPID();
				thePV1 = theA50.getPV1();
				theEVN = theA50.getEVN();
			} else if ("ADT_A17".equals(messageClass)) {
				ADT_A17 theA17 = (ADT_A17) hapiMsg;
				theMSH = theA17.getMSH();
				thePID = theA17.getPID();
				thePV1 = theA17.getPV1();
				theEVN = theA17.getEVN();
			} else if ("ADT_A12".equals(messageClass)) {
				ADT_A12 theA12 = (ADT_A12) hapiMsg;
				theMSH = theA12.getMSH();
				thePID = theA12.getPID();
				thePV1 = theA12.getPV1();
				theEVN = theA12.getEVN();
			}
				//changes @rzp05 (10/27/2019)		
				 else if ("ADT_A39".equals(messageClass)) {		
						ADT_A39 theA39 = (ADT_A39) hapiMsg;		
						theMSH = theA39.getMSH();		
						thePID = theA39.getPATIENT().getPID();		
						thePV1 = theA39.getPATIENT().getPV1();		
						theEVN = theA39.getEVN();		
						theMRG = theA39.getPATIENT().getMRG();		
					}		
				 else if ("ADT_A60".equals(messageClass)) {		
						ADT_A60 theA60 = (ADT_A60) hapiMsg;		
						theMSH = theA60.getMSH();		
						thePID = theA60.getPID();		
						thePV1 = theA60.getPV1();		
						theEVN = theA60.getEVN();		
					}	 else {
				theDocVO = null;
				log.error("Unexpected HL7 ADT Message type with UPHN_TRANX_IN_ID {}, type is {}; message text and stacktrace stored to HIT_ERROR."
							,uphnId, messageClass);
			
				hitCommonDocumentService
						.storeHitError(new Exception("Unexpected HL7 ADT Message type with UPHN_TRANX_IN_ID '" + uphnId
								+ "', type is " + messageClass), uphnId, rawMessageText, "HL7",null,null);
				
				return theDocVO;

			}
			List<RawHL7Dg1VO> dg1VOList = new ArrayList<>();
			if (dg1List != null) {
				for (DG1 theDg1 : dg1List) {
					RawHL7Dg1VO dg1VO = getDg1VOL(theDg1);
					dg1VO.setUphnId(uphnId);
					dg1VOList.add(dg1VO);
				}
			}
			if (dg1VOList != null && dg1VOList.size() > 0) {
				theDocVO.setTheDG1List(dg1VOList);
			}
			String messageType = theMSH.getMessageType().getMsg1_MessageCode().toString();
			String messageTrigger = theMSH.getMessageType().getMsg2_TriggerEvent().toString();
			if (!messageType.isEmpty() && !messageTrigger.isEmpty()) {
				String msgType = messageType + "_" + messageTrigger;
				theDocVO.setMshMessageType(msgType);
			} else {
				theDocVO.setMshMessageType(messageType);
			}
			if (theEVN != null) {
				String eventCodeString = theEVN.getEventTypeCode().getValue();
				theDocVO.setEvnEventType(eventCodeString);
			}
			theDocVO.setMshMessageType(messageType);
			theDocVO.setEvnEventDate(theEVN.getEvn6_EventOccurred().encode());
			theDocVO.setTheMshVO(getMshVO(theMSH));
			theDocVO.setThePidVO(getPidVO(thePID));
			theDocVO.setThePv1VO(getPv1VO(thePV1));
			theDocVO.setSegmentProfile(getHl7SegmentProfile(rawMessageText));
		} else if ("ORU_R01".equals(messageClass)) {
			theDocVO.setMshMessageType("ORU_R01");
			ORU_R01 theORU = (ORU_R01) hapiMsg;
			theMSH = theORU.getMSH();
			theDocVO.setTheMshVO(getMshVO(theMSH));
			String mshSendingFacilityCode = theDocVO.getTheMshVO().getMshSendingFacilityCode();
			ORU_R01_PATIENT_RESULT thePatientGroup = theORU.getPATIENT_RESULT(0);
			ORU_R01_PATIENT thePatient = thePatientGroup.getPATIENT();
			thePID = thePatient.getPID();
			RawHL7PidVO thePidVo = new RawHL7PidVO();
			thePidVo = getPidVO(thePID);
			if (thePidVo != null) {
				theDocVO.setThePidVO(thePidVo);
			}
			StringBuilder NTEString = new StringBuilder();
			List<NTE> theNTEList = thePatient.getNTEAll();
			if (theNTEList != null && theNTEList.size() > 0) {
				int NTRcount = theNTEList.size();
				for (int i = 0; i < NTRcount; i++) {
					NTE theNTEEle = thePatient.getNTE(i);
					StringBuilder NTEString_current = getNTEdata(theNTEEle);
					if (CommonUtil.isNotNullAndEmpty(NTEString_current.toString()) && i > 0) {
						NTEString = NTEString.append(" % ").append(NTEString_current);
					} else if (CommonUtil.isNotNullAndEmpty(NTEString_current.toString())) {
						NTEString = NTEString_current;
					}
				}
				if (CommonUtil.isNotNullAndEmpty(NTEString.toString()) && NTEString.length() > 3800) {
					String NTEStringfilter = NTEString.substring(0, 3500);
					thePidVo.setPidNote(NTEStringfilter.toString());

				} else {
					thePidVo.setPidNote(NTEString.toString());
				}
			}
			theDocVO.setSegmentProfile(getHl7SegmentProfile(rawMessageText));
			ORU_R01_VISIT thePatientVisit = thePatient.getVISIT();
			thePV1 = thePatientVisit.getPV1();
			theDocVO.setThePv1VO(getPv1VO(thePV1));
			List<ORU_R01_ORDER_OBSERVATION> theObrGroupList = thePatientGroup.getORDER_OBSERVATIONAll();
			List<RawHL7ObrVO> listObrVO = new ArrayList<>();
			int theObrCount = theObrGroupList.size();
			int theObxCount = 0;
			for (int i = 0; i < theObrCount; i++) {
				ORU_R01_ORDER_OBSERVATION theResult = thePatientGroup.getORDER_OBSERVATION(i);
				theORC = theResult.getORC();
				theOBR = theResult.getOBR();
				RawHL7ObrVO hL7TabTestVO = new RawHL7ObrVO();
				hL7TabTestVO = getOBRVO(theOBR, mshSendingFacilityCode);
				// NEW CODE ADDED FOR NTE
				StringBuilder NTEOBRString = new StringBuilder();
				List<NTE> theNTEOBRList = theResult.getNTEAll();
				if (theNTEOBRList != null && theNTEOBRList.size() > 0) {
					int NTROBRcount = theNTEOBRList.size();
					for (int j = 0; j < NTROBRcount; j++) {
						NTE theNTEOBREle = theResult.getNTE(j);
						StringBuilder NTEOBRString_current = getNTEdata(theNTEOBREle);
						if (CommonUtil.isNotNullAndEmpty(NTEOBRString_current.toString()) && j > 0) {
							NTEOBRString = NTEOBRString.append(" % ").append(NTEOBRString_current);
						} else if (CommonUtil.isNotNullAndEmpty(NTEOBRString_current.toString())) {
							NTEOBRString = NTEOBRString_current;
						}
					}
					if (CommonUtil.isNotNullAndEmpty(NTEOBRString.toString()) && NTEOBRString.length() > 4000) {
						String NTEOBRStringFilter = NTEOBRString.substring(0, 3500);
						hL7TabTestVO.setObrNote(NTEOBRStringFilter.toString());

					} else {
						hL7TabTestVO.setObrNote(NTEOBRString.toString());
					}
				}
				// END
				ORU_R01_ORDER_OBSERVATION theObrGroup = theObrGroupList.get(i);
				theObxCount = theObrGroup.getOBSERVATIONReps();
				List<RawHL7ObxVO> theRawHL7ObxVOList = new ArrayList<>();
				for (int j = 0; j < theObxCount; j++) {
					ORU_R01_OBSERVATION theOBXEntry = theObrGroup.getOBSERVATION(j);
					RawHL7ObxVO hl7LabResultVo = new RawHL7ObxVO();
					theOBX = theOBXEntry.getOBX();
					hl7LabResultVo = getObxVO(theOBX);
					// NEW CODE ADDED FOR NTE
					StringBuilder NTEOBXString = new StringBuilder();
					List<NTE> theNTEOBXList = theOBXEntry.getNTEAll();
					if (theNTEOBXList != null && theNTEOBXList.size() > 0) {
						int NTROBXcount = theNTEOBXList.size();
						for (int k = 0; k < NTROBXcount; k++) {
							NTE theNTEOBXEle = theOBXEntry.getNTE(k);
							StringBuilder NTEOBXStr_current = getNTEdata(theNTEOBXEle);
							if (CommonUtil.isNotNullAndEmpty(NTEOBXStr_current.toString()) && k > 0) {
								NTEOBXString = NTEOBXString.append(" % ").append(NTEOBXStr_current);
							} else if (CommonUtil.isNotNullAndEmpty(NTEOBXStr_current.toString())) {
								NTEOBXString = NTEOBXStr_current;
							}
						}
						if (CommonUtil.isNotNullAndEmpty(NTEOBXString.toString()) && NTEOBXString.length() > 4000) {
							String NTEOBXStringFilter = NTEOBXString.substring(0, 3500);
							hl7LabResultVo.setObxNote(NTEOBXStringFilter.toString());
						} else {
							hl7LabResultVo.setObxNote(NTEOBXString.toString());
						}
					}
					// END
					hl7LabResultVo.setUphnTranxInId(uphnId);
					theRawHL7ObxVOList.add(hl7LabResultVo);
				}
				if (hL7TabTestVO != null) {
					hL7TabTestVO.setTheOBXList(theRawHL7ObxVOList);
					hL7TabTestVO.setUphnTranxInId(uphnId);
					listObrVO.add(hL7TabTestVO);
				}
			}
			theDocVO.setTheOBRList(listObrVO);
		} else if ("ORM_O01".equals(messageClass)) {
			ORM_O01 theORM = (ORM_O01) hapiMsg;
			theMSH = theORM.getMSH();
			thePID = theORM.getPATIENT().getPID();
			thePV1 = theORM.getPATIENT().getPATIENT_VISIT().getPV1();
			List<ORM_O01_ORDER> theORMGroupList = theORM.getORDERAll();
			List<RawHL7ORCVO> listOrcVO = new ArrayList<>();
			int theOrcCount = theORMGroupList.size();
			if (theORMGroupList != null && theOrcCount > 0) {
				for (int i = 0; i < theOrcCount; i++) {
					ORM_O01_ORDER ormo01VO = theORMGroupList.get(i);
					theORC = ormo01VO.getORC();
					theRXO = ormo01VO.getORDER_DETAIL().getRXO();
					if (theORC != null) {
						RawHL7ORCVO hl7OrcVo = new RawHL7ORCVO();
						hl7OrcVo = getORCVO(theORC);
						if (hl7OrcVo != null) {
							hl7OrcVo.setUphnId(uphnId);
							if (theRXO != null) {
								hl7OrcVo.setRxoMedName(theRXO.getRequestedGiveCode().getCe2_Text().getValue());
							}
							listOrcVO.add(hl7OrcVo);
						}
					}

				}
			}
			if (listOrcVO != null && listOrcVO.size() > 0) {
				theDocVO.setTheORCList(listOrcVO);
			}
			String messageType = theMSH.getMessageType().getMsg1_MessageCode().toString();
			String messageTrigger = theMSH.getMessageType().getMsg2_TriggerEvent().toString();
			theDocVO.setTheMshVO(getMshVO(theMSH));
			theDocVO.setThePidVO(getPidVO(thePID));
			theDocVO.setThePv1VO(getPv1VO(thePV1));
			theDocVO.setSegmentProfile(getHl7SegmentProfile(rawMessageText));
			if (!messageType.isEmpty() && !messageTrigger.isEmpty()) {
				String msgType = messageType + "_" + messageTrigger;
				theDocVO.setMshMessageType(msgType);
			} else {
				theDocVO.setMshMessageType(messageType);
			}
		} else if ("RDE_O11".equals(messageClass)) {
			RDE_O11 theRDE = (RDE_O11) hapiMsg;
			theMSH = theRDE.getMSH();
			thePID = theRDE.getPATIENT().getPID();
			thePV1 = theRDE.getPATIENT().getPATIENT_VISIT().getPV1();
			List<RDE_O11_ORDER> theRDEGroupList = theRDE.getORDERAll();
			List<RawHL7ORCVO> listOrcVO = new ArrayList<>();
			int theOrcCount = theRDEGroupList.size();
			if (theRDEGroupList != null && theOrcCount > 0) {
				for (int i = 0; i < theOrcCount; i++) {
					RDE_O11_ORDER ormo01VO = theRDEGroupList.get(i);
					theORC = ormo01VO.getORC();
					theRXE = ormo01VO.getRXE();
					List<RXC> theRXCList = ormo01VO.getRXCAll();
					if (theORC != null) {
						RawHL7ORCVO hl7OrcVo = new RawHL7ORCVO();
						hl7OrcVo = getORCVO(theORC);
						if (hl7OrcVo != null) {
							hl7OrcVo.setUphnId(uphnId);
							if (theRXE != null) {
								hl7OrcVo.setRxeMedName(theRXE.getGiveCode().getCe2_Text().getValue());
								hl7OrcVo.setRxeoriginalOrderDate(
										theRXE.getOriginalOrderDateTime().getTs1_Time().encode());
								hl7OrcVo.setRxePharmRefill(
										theRXE.getPharmacyOfMostRecentFill().getCwe2_Text().getValue());
								hl7OrcVo.setRxeDispPharm(theRXE.getDispensingPharmacy().getCwe2_Text().getValue());
							}
							StringBuilder rxcString = new StringBuilder();
							if (theRXCList != null && theRXCList.size() > 0) {
								for (int p = 0; p < theRXCList.size(); p++) {
									RXC rxcVO = theRXCList.get(p);
									if (rxcVO != null) {
										rxcString.append("{\"RXC_MED_NAME (" + p + ") : \":\"")
												.append(rxcVO.getComponentCode().getCe2_Text().getValue())
												.append("\"},\n");
									}
								}
								if (rxcString.length() > 5) {
									if (rxcString.length() < 15) {
										rxcString.setLength(0);
										hl7OrcVo.setRxcMedName(rxcString.toString());
									} else {
										rxcString.deleteCharAt(rxcString.lastIndexOf(","));
										rxcString.append("]}");
										hl7OrcVo.setRxcMedName(rxcString.toString());
									}
								}
							}
							listOrcVO.add(hl7OrcVo);
						}
					}
				}
			}
			if (listOrcVO != null && listOrcVO.size() > 0) {
				theDocVO.setTheORCList(listOrcVO);
			}
			String messageType = theMSH.getMessageType().getMsg1_MessageCode().toString();
			String messageTrigger = theMSH.getMessageType().getMsg2_TriggerEvent().toString();
			theDocVO.setMshMessageType(messageType);
			theDocVO.setTheMshVO(getMshVO(theMSH));
			theDocVO.setThePidVO(getPidVO(thePID));
			theDocVO.setThePv1VO(getPv1VO(thePV1));
			theDocVO.setSegmentProfile(getHl7SegmentProfile(rawMessageText));
			if (!messageType.isEmpty() && !messageTrigger.isEmpty()) {
				String msgType = messageType + "_" + messageTrigger;
				theDocVO.setMshMessageType(msgType);
			} else {
				theDocVO.setMshMessageType(messageType);
			}
		} else if ("OMP_O09".equals(messageClass)) {
			OMP_O09 theOMP = (OMP_O09) hapiMsg;
			theMSH = theOMP.getMSH();
			thePID = theOMP.getPATIENT().getPID();
			thePV1 = theOMP.getPATIENT().getPATIENT_VISIT().getPV1();
			List<OMP_O09_ORDER> theOMPGroupList = theOMP.getORDERAll();
			List<RawHL7ORCVO> listOrcVO = new ArrayList<>();
			int theOrcCount = theOMPGroupList.size();
			if (theOMPGroupList != null && theOrcCount > 0) {
				for (int i = 0; i < theOrcCount; i++) {
					OMP_O09_ORDER ormo01VO = theOMPGroupList.get(i);
					theORC = ormo01VO.getORC();
					theRXO = ormo01VO.getRXO();
					if (theORC != null) {
						RawHL7ORCVO hl7OrcVo = new RawHL7ORCVO();
						hl7OrcVo = getORCVO(theORC);
						if (hl7OrcVo != null) {
							hl7OrcVo.setUphnId(uphnId);
							if (theRXO != null) {
								hl7OrcVo.setRxoMedName(theRXO.getRequestedGiveCode().getCe2_Text().getValue());
							}
							listOrcVO.add(hl7OrcVo);
						}
					}
				}
			}
			if (listOrcVO != null && listOrcVO.size() > 0) {
				theDocVO.setTheORCList(listOrcVO);
			}

			String messageType = theMSH.getMessageType().getMsg1_MessageCode().toString();
			String messageTrigger = theMSH.getMessageType().getMsg2_TriggerEvent().toString();
			theDocVO.setMshMessageType(messageType);
			theDocVO.setTheMshVO(getMshVO(theMSH));
			theDocVO.setThePidVO(getPidVO(thePID));
			theDocVO.setThePv1VO(getPv1VO(thePV1));
			theDocVO.setSegmentProfile(getHl7SegmentProfile(rawMessageText));
			if (!messageType.isEmpty() && !messageTrigger.isEmpty()) {
				String msgType = messageType + "_" + messageTrigger;
				theDocVO.setMshMessageType(msgType);
			} else {
				theDocVO.setMshMessageType(messageType);
			}
		}else if("PPR_PC1".equals(messageClass)) {
			PPR_PC1 thePPR = (PPR_PC1) hapiMsg;		
					
			theMSH =  thePPR.getMSH();		
			thePID = thePPR.getPID();		
			thePV1 = thePPR.getPATIENT_VISIT().getPV1();		
		    theOBR = thePPR.getPROBLEM().getORDER().getORDER_DETAIL().getOBR();		
		    				
			String messageType = theMSH.getMessageType().getMsg1_MessageCode().toString();		
			String messageTrigger = theMSH.getMessageType().getMsg2_TriggerEvent().toString();		
			theDocVO.setMshMessageType(messageType);		
			theDocVO.setTheMshVO(getMshVO(theMSH));		
			theDocVO.setThePidVO(getPidVO(thePID));		
			theDocVO.setThePv1VO(getPv1VO(thePV1));		
			theDocVO.setSegmentProfile(getHl7SegmentProfile(rawMessageText));		
					
			String mshSendingFacilityCode = theDocVO.getTheMshVO().getMshSendingFacilityCode();		
			RawHL7ObrVO hL7TabTestVO = new RawHL7ObrVO();		
			hL7TabTestVO = getOBRVO(theOBR, mshSendingFacilityCode);		
					
			if (!messageType.isEmpty() && !messageTrigger.isEmpty()) {		
				String msgType = messageType + "_" + messageTrigger;		
				theDocVO.setMshMessageType(msgType);		
			} else {		
				theDocVO.setMshMessageType(messageType);		
			}		
		} else if("MDM_T02".equals(messageClass)){		
					
			EVN theEVN = null;		
					
			MDM_T02 theMDM = (MDM_T02) hapiMsg;		
					
					
			theMSH =  theMDM.getMSH();		
			thePID = theMDM.getPID();		
			thePV1 = theMDM.getPV1();		
			theEVN = theMDM.getEVN();		
		    				
			String messageType = theMSH.getMessageType().getMsg1_MessageCode().toString();		
			String messageTrigger = theMSH.getMessageType().getMsg2_TriggerEvent().toString();		
			theDocVO.setMshMessageType(messageType);		
			theDocVO.setTheMshVO(getMshVO(theMSH));		
			theDocVO.setThePidVO(getPidVO(thePID));		
			theDocVO.setThePv1VO(getPv1VO(thePV1));		
			theDocVO.setSegmentProfile(getHl7SegmentProfile(rawMessageText));		
					
			if (!messageType.isEmpty() && !messageTrigger.isEmpty()) {		
				String msgType = messageType + "_" + messageTrigger;		
				theDocVO.setMshMessageType(msgType);		
			} else {		
				theDocVO.setMshMessageType(messageType);		
			}		
			if (theEVN != null) {		
				String eventCodeString = theEVN.getEventTypeCode().getValue();		
				theDocVO.setEvnEventType(eventCodeString);		
			}		
					
			theDocVO.setMshMessageType(messageType);		
			theDocVO.setEvnEventDate(theEVN.getEvn6_EventOccurred().encode());		
			theDocVO.setTheMshVO(getMshVO(theMSH));		
			theDocVO.setThePidVO(getPidVO(thePID));		
			theDocVO.setThePv1VO(getPv1VO(thePV1));		
			theDocVO.setSegmentProfile(getHl7SegmentProfile(rawMessageText));		
					
					
					
		} else {
			theDocVO = null;
			log.error("Unexpected HL7 Message type with UPHN_TRANX_IN_ID {}, type is {} ; message text and stacktrace stored to HIT_ERROR.",
					uphnId,messageClass);
		

			hitCommonDocumentService.storeHitError(new Exception(
					"Unexpected HL7 Message type with UPHN_TRANX_IN_ID '" + uphnId + "', type is " + messageClass),
					uphnId, rawMessageText, "HL7",null, null);
		}
		return theDocVO;
	}

	private static RawHL7MshVO getMshVO(MSH theMSH) throws DataTypeException {
		RawHL7MshVO theMshVO = new RawHL7MshVO();
		if (theMSH != null) {
			HD theSendingFacility = theMSH.getMsh4_SendingFacility();
			String theSendingFacilityName = theSendingFacility.getHd1_NamespaceID().getValue();
			theMshVO.setMshSendingFacilityName(theSendingFacilityName);

			String theSendingFacilityCode = theSendingFacility.getHd2_UniversalID().getValue();
			theMshVO.setMshSendingFacilityCode(theSendingFacilityCode);

			String theSendingFacilityCodeSystem = theSendingFacility.getUniversalIDType().toString();
			theMshVO.setMshSendingFacilityCodeSystem(theSendingFacilityCodeSystem);

			DTM messageTime = null;
			try {
				messageTime = theMSH.getDateTimeOfMessage().getTime();
				String mshCreateDateStr = messageTime.toString();
				theMshVO.setMshCreateDate(mshCreateDateStr);
			} catch (Exception e) {
			}
			theMshVO.setMshMessageControlId(theMSH.getMessageControlID().getValue());
		}
		return theMshVO;
	}

	private static String getHl7SegmentProfile(String hl7Message) {
		StringBuilder s = new StringBuilder();
		String[] segments = hl7Message.split("\r");
		boolean first = true;
		for (String st : segments) {
			if (!first) {
				s.append(separator);
			}
			first = false;
			if (st.length() >= 3) {
				s.append(st.substring(0, 3));
			} else {
				s.append(st);
			}
		}
		if (s.length() > 3900) {
			String segmentString = s.substring(0, 3900).toString();
			return segmentString;
		} else {
			return s.toString();
		}
	}

	private static StringBuilder getNTEdata(NTE NTEsegment) {
		int NTEsegmentCount = NTEsegment.getCommentReps();
		StringBuilder NTENote = new StringBuilder("");
		if (NTEsegmentCount > 0) {
			for (int i = 0; i < NTEsegmentCount; i++) {
				String NTEstr = NTEsegment.getNte3_Comment(i).getValue();
				if (NTEstr != null && !NTEstr.isEmpty() && i > 0) {
					NTENote = NTENote.append(" ~ ");
					NTENote = NTENote.append(NTEstr.toString());
				} else {
					return NTENote.append(NTEstr);
				}
			}
		}
		return NTENote;
	}

	private static RawHL7PidVO getPidVO(PID thePID) {
		RawHL7PidVO thePidVO = new RawHL7PidVO();
		if (thePID != null) {
			CE race = thePID.getRace(0);
			String raceCode = race.getCe1_Identifier().getValue();
			String raceCodingSystem = race.getCe3_NameOfCodingSystem().getValue();
			String raceCodeDesc = race.getCe2_Text().getValue();
			thePidVO.setPidRaceCodeRaw(raceCode);
			thePidVO.setPidEthnicGroupCode(thePID.getEthnicGroup(0).getCe1_Identifier().getValue());
			thePidVO.setPidRaceCodeSystem(raceCodingSystem);
			thePidVO.setPidRaceCodeDesc(raceCodeDesc);
			thePidVO.setPatientIdentifierJson(Integer.toString(thePID.getPatientIdentifierListReps()));
			StringBuilder pids = new StringBuilder();
			CX[] pidXPNArray = thePID.getPatientIdentifierList();
			CX exId = thePID.getPatientID();
			thePidVO.setPidPatientIdExternal(exId.getIDNumber().getValue());
			String facIdX = exId.getAssigningFacility().getUniversalID().getValue();
			pids.append("{\"patientIds\":[\n");
			boolean first = true;
			boolean piAlreadyExists = false;
			for (CX pid : pidXPNArray) {
				String facIdI = "";
				String idNumValue = pid.getIDNumber().getValue();
				if ((idNumValue == null) || ("".equals(idNumValue.trim()))) {
					continue;
				}
				String idType = pid.getIdentifierTypeCode().getValue();
				if (!first) {
					pids.append(",");
					first = false;
				}
				pids.append("{\"id\":\"").append(idNumValue).append("\",\"auth\":\"")
						.append(pid.getCx4_AssigningAuthority().getNamespaceID().getValue()).append("\",\"type\":\"")
						.append(idType).append("\"},\n");
				if ((idType == null) || ("".equals(idType.trim()))) {
					if (!piAlreadyExists) {
						idType = "PI";
						piAlreadyExists = true;
					} else {
						idType = "MR";
					}
				}
				if ("PI".equalsIgnoreCase(idType)) {
					thePidVO.setPidPatientIdInternal(idNumValue);
					facIdI = pid.getAssigningFacility().getUniversalID().getValue();
					if ((facIdI != null) && (facIdI.length() > 10)) {
						facIdI = facIdI.substring(facIdI.length() - 10);
					}
					thePidVO.setPidFacilityIdInternal(facIdI);
					piAlreadyExists = true;
				} else if ("PT".equalsIgnoreCase(idType)) {
					thePidVO.setPidPatientIdExternal(idNumValue);
					facIdX = exId.getAssigningFacility().getUniversalID().getValue();
					if ((facIdX != null) && (facIdX.length() > 10)) {
						facIdX = facIdX.substring(facIdX.length() - 10);
					}
					thePidVO.setPidFacilityIdExternal(facIdX);
				} else if ("MA".equalsIgnoreCase(idType)) {
					thePidVO.setPidMedicaidNumber(idNumValue);
				} else if ("SS".equalsIgnoreCase(idType)) {
					thePidVO.setPidSsn(idNumValue);
				} else if ("NYSDIN".equalsIgnoreCase(idType)) {
					thePidVO.setPidDin(idNumValue);
				} else if ("MRN".equalsIgnoreCase(idType)) {
					thePidVO.setPidMedicalRecordnumber(idNumValue);
					/* HD assignAuth = pid.getAssigningAuthority(); */
				} else if ("NYSID".equalsIgnoreCase(idType)) {
					thePidVO.setPidNysid(idNumValue);
				}
			}
			if (pids.length() > 5) {
				if (pids.length() < 25) {
					pids.setLength(0);
					thePidVO.setPidPatientIDList(pids.toString());
				} else {
					if(pids.length() >= 3996) {
						pids.delete(3996, pids.length()-1);
						pids.delete(pids.lastIndexOf(","), pids.length()-1);
					}else {
						pids.deleteCharAt((pids.lastIndexOf(",")));
					}
					pids.append("]}");
					thePidVO.setPidPatientIDList(pids.toString());
				}
			}
			thePidVO.setPatientIdentifierJson(pids.toString());
			thePidVO.setPidDeathDate(thePID.getPatientDeathDateAndTime().getTs1_Time().toString());
			CX theAltPatID = thePID.getPid4_AlternatePatientIDPID(0);
			String thePatID = theAltPatID.getCx1_IDNumber().getValue();
			thePidVO.setPidMpi(thePatID);
			XPN theXPN = thePID.getPatientName(0);
			thePidVO.setPatientName(getXpnVO(theXPN));
			try {
				thePidVO.setPidDateOfBirthRaw(thePID.getPid7_DateTimeOfBirth().encode());
			} catch (Exception e) {
				thePidVO.setPidDateOfBirthRaw("MALFORMED");
			}
			thePidVO.setPidDeathInd(thePID.getPatientDeathIndicator().getValue());
			try {
			} catch (Exception e) {
				thePidVO.setPidDeathInd("MALFORMED");
			}
			thePidVO.setPidVeteranStatus(thePID.getVeteransMilitaryStatus().getText().getValue());
			thePidVO.setPidSexCode(thePID.getPid8_AdministrativeSex().getValue());
			thePidVO.setPidPatientAccountNumber(thePID.getPatientAccountNumber().getCx1_IDNumber().toString());
			XAD theXAD = thePID.getPatientAddress(0);
			thePidVO.setPatientAddress(getXadVO(theXAD));
			thePidVO.setPidHomePhone(thePID.getPhoneNumberHome(0).getTelephoneNumber().getValue());
			thePidVO.setPidWorkPhone(thePID.getPhoneNumberBusiness(0).getTelephoneNumber().getValue());
		}
		return thePidVO;
	}

	private static RawHL7Pv1VO getPv1VO(PV1 thePV1) {
		RawHL7Pv1VO thePv1VO = new RawHL7Pv1VO();
		if (thePV1 != null) {
			thePv1VO.setPatientClass(thePV1.getPatientClass().getValue());
			CX theVisitNumberCX = thePV1.getPv119_VisitNumber();
			thePv1VO.setVisitNumber(theVisitNumberCX.getCx1_IDNumber().getValue());
			XCN[] theProviders = thePV1.getPv17_AttendingDoctor();
			int theProviderCount = theProviders.length;
			String theFirstProviderLastName = null;
			String theFirstProviderFirstName = null;
			String theFirstProviderMiddleName = null;
			String theFirstProviderId = null;
			String theFirstProviderAssigningAuthority = null;
			if (theProviderCount > 0) {
				XCN theFirstProvider = theProviders[0];
				theFirstProviderLastName = theFirstProvider.getFamilyName().getFn1_Surname().getValue();
				theFirstProviderFirstName = theFirstProvider.getGivenName().getValue();
				theFirstProviderMiddleName = theFirstProvider.getSecondAndFurtherGivenNamesOrInitialsThereof()
						.getValue();
				theFirstProviderId = theFirstProvider.getXcn1_IDNumber().getValue();
				theFirstProviderAssigningAuthority = theFirstProvider.getAssigningAuthority().getHd1_NamespaceID()
						.getValue();
			}
			RawHL7XpnVO pn = new RawHL7XpnVO();
			pn.setLastName(theFirstProviderLastName);
			pn.setFirstName(theFirstProviderFirstName);
			pn.setMiddleName(theFirstProviderMiddleName);
			thePv1VO.setProviderName(pn);
			thePv1VO.setProviderFirstId(theFirstProviderId);
			thePv1VO.setPv1ProviderID(theFirstProviderId);
			thePv1VO.setPv1ProviderAuth(theFirstProviderAssigningAuthority);
			thePv1VO.setProviderCount(theProviderCount);
			thePv1VO.setProviderJson(Integer.toString(theProviderCount));
			StringBuilder theProvidersJson = new StringBuilder();
			theProvidersJson.append("{\"providers\":[\n");
			boolean first = true;
			for (XCN p : theProviders) {
				if (!first) {
					theProvidersJson.append(",");
					first = false;
				}
				theProvidersJson.append("{\"id\":\"").append(p.getXcn1_IDNumber()).append("\",\"last\":\"")
						.append(p.getXcn2_FamilyName().getFn1_Surname().getValue()).append("\",\"first\":\"")
						.append(p.getXcn4_SecondAndFurtherGivenNamesOrInitialsThereof().getValue())
						.append("\",\"middle\":\"").append(p.getXcn3_GivenName().getValue()).append("\",\"auth\":\"")
						.append(p.getXcn9_AssigningAuthority().getNamespaceID().getValue()).append("\",\"type\":\"")
						.append(p.getXcn13_IdentifierTypeCode()).append("\",\"facility\":\"")
						.append(p.getXcn14_AssigningFacility().getNamespaceID().getValue()).append("\"},\n");
			}
			if (theProvidersJson.length() > 5) {
				if (theProvidersJson.length() < 25) {
					theProvidersJson.setLength(0);
					thePv1VO.setPv1AttendingProviderList(theProvidersJson.toString());
				} else {
					if (theProvidersJson.length() > 4000) {
						String ProvidersJson = theProvidersJson.substring(0, 3900).toString();
						ProvidersJson = ProvidersJson.concat("]}}");
						thePv1VO.setPv1AttendingProviderList(ProvidersJson);
					} else {
						theProvidersJson.deleteCharAt(theProvidersJson.lastIndexOf(","));
						theProvidersJson.append("]}");
						thePv1VO.setPv1AttendingProviderList(theProvidersJson.toString());
					}
				}
			}
			XCN[] theReferringProviders = thePV1.getPv18_ReferringDoctor();
			StringBuilder theRefProvidersJson = new StringBuilder();
			theRefProvidersJson.append("{\"providers\":[\n");
			boolean Reffirst = true;
			for (XCN p : theReferringProviders) {
				if (!Reffirst) {
					theRefProvidersJson.append(",");
					Reffirst = false;
				}
				theRefProvidersJson.append("{\"id\":\"").append(p.getXcn1_IDNumber()).append("\",\"last\":\"")
						.append(p.getXcn2_FamilyName().getFn1_Surname().getValue()).append("\",\"first\":\"")
						.append(p.getXcn4_SecondAndFurtherGivenNamesOrInitialsThereof().getValue())
						.append("\",\"middle\":\"").append(p.getXcn3_GivenName().getValue()).append("\",\"auth\":\"")
						.append(p.getXcn9_AssigningAuthority().getNamespaceID().getValue()).append("\",\"type\":\"")
						.append(p.getXcn13_IdentifierTypeCode()).append("\",\"facility\":\"")
						.append(p.getXcn14_AssigningFacility().getNamespaceID().getValue()).append("\"},\n");
			}
			if (theRefProvidersJson.length() > 5) {
				if (theRefProvidersJson.length() < 25) {
					theRefProvidersJson.setLength(0);
					thePv1VO.setPv1ReferringProviderList(theRefProvidersJson.toString());
				} else {
					if (theRefProvidersJson.length() > 4000) {
						String RefProvidersJson = theRefProvidersJson.substring(0, 3900).toString();
						RefProvidersJson = RefProvidersJson.concat("]}");
						thePv1VO.setPv1ReferringProviderList(RefProvidersJson);
					} else {
						theRefProvidersJson.deleteCharAt(theRefProvidersJson.lastIndexOf(","));
						theRefProvidersJson.append("]}");
						thePv1VO.setPv1ReferringProviderList(theRefProvidersJson.toString());
					}
				}
			}
		}
		return thePv1VO;
	}

	private static RawHL7Dg1VO getDg1VOL(DG1 theDG1) throws Exception {
		RawHL7Dg1VO dg1VO = new RawHL7Dg1VO();
		if (theDG1 != null) {
			dg1VO.setDxCodingMethod(theDG1.getDg12_DiagnosisCodingMethod().getValue());
			CE dg1Code = theDG1.getDiagnosisCodeDG1();
			if (dg1Code != null) {
				dg1VO.setDxCodeId(dg1Code.getCe1_Identifier().getValue());
				dg1VO.setDxCodetext(dg1Code.getCe2_Text().getValue());
				dg1VO.setDxCodeSystem(dg1Code.getCe3_NameOfCodingSystem().getValue());
			}
			dg1VO.setDxDesc(theDG1.getDg14_DiagnosisDescription().getValue());
			dg1VO.setDxDateText(theDG1.getDg15_DiagnosisDateTime().encode());
			dg1VO.setDxType(theDG1.getDg16_DiagnosisType().getValue());
		}
		return dg1VO;
	}

	private RawHL7ObrVO getOBRVO(OBR theOBR, String mshSendingFacilityCode) throws Exception {
		RawHL7ObrVO rawHL7LabTestVO = new RawHL7ObrVO();
		if (theOBR != null) {
			String ObrSetId = theOBR.getSetIDOBR().getValue();
			if (ObrSetId != null && !ObrSetId.isEmpty()) {
				int setId = Integer.parseInt(ObrSetId);
				rawHL7LabTestVO.setObrSetId(setId);
			}
			rawHL7LabTestVO.setObrAccessionNum(theOBR.getObr3_FillerOrderNumber().getEi1_EntityIdentifier().getValue());
			String obrCallBackPhone = theOBR.getObr17_OrderCallbackPhoneNumber(0).getTelephoneNumber().getValue();
			if (obrCallBackPhone != null && !obrCallBackPhone.isEmpty()) {
				rawHL7LabTestVO.setObrcallBackPhone(obrCallBackPhone);
			}

			rawHL7LabTestVO
					.setObrCollectionDate(CommonUtil.convertTimeStampToDate(theOBR.getObr7_ObservationDateTime()));
			rawHL7LabTestVO.setObrObservationDate(
					CommonUtil.convertTimeStampToDate(theOBR.getObr14_SpecimenReceivedDateTime()));// .getTime().getValueAsDate());
			XCN[] theProviders = theOBR.getObr16_OrderingProvider();
			int theProviderCount = theProviders.length;
			String theFirstProviderLastName = null;
			String theFirstProviderFirstName = null;
			String theFirstProviderMiddleName = null;
			String theFirstProviderNameSuffix = null;
			if (theProviderCount > 0) {
				XCN theFirstProvider = theProviders[0];
				theFirstProviderLastName = theFirstProvider.getFamilyName().getFn1_Surname().getValue();
				theFirstProviderFirstName = theFirstProvider.getGivenName().getValue();
				theFirstProviderMiddleName = theFirstProvider.getSecondAndFurtherGivenNamesOrInitialsThereof()
						.getValue();
				theFirstProviderNameSuffix = theFirstProvider.getProfessionalSuffix().getValue();
			}
			rawHL7LabTestVO.setObrProviderLastName(theFirstProviderLastName);
			rawHL7LabTestVO.setObrProviderFirstName(theFirstProviderFirstName);
			rawHL7LabTestVO.setObrProviderMiddleName(theFirstProviderMiddleName);
			rawHL7LabTestVO.setObrProviderNameSuffix(theFirstProviderNameSuffix);
			StringBuilder pids = new StringBuilder();
			XCN[] pidXPNArray = theOBR.getObr16_OrderingProvider();
			pids.append("{\"provider\":[\n");
			boolean first = true;
			boolean piAlreadyExists = false;
			for (XCN pid : pidXPNArray) {
				String idNumValue = pid.getIDNumber().getValue();
				if ((idNumValue == null) || ("".equals(idNumValue.trim()))) {
					continue;
				}
				String idType = pid.getAssigningAuthority().getNamespaceID().getValue();
				if (!first) {
					pids.append(",");
					first = false;
				}
				pids.append("{\"id\":\"").append(idNumValue).append("\",\"type\":\"").append(idType).append("\"},\n");
				if ((idType == null) || ("".equals(idType.trim()))) {
					if (!piAlreadyExists) {
						idType = "PI";
						piAlreadyExists = true;
					} else {
						idType = "MR";
					}
				}
				if (idType.equals(mshSendingFacilityCode)) {
					rawHL7LabTestVO.setObrProviderFacilityId(idNumValue);
				} else if ("LIC".equalsIgnoreCase(idType)) {
					rawHL7LabTestVO.setObrProviderLicenseId(idNumValue);
				} else if ("NPI".equalsIgnoreCase(idType)) {
					rawHL7LabTestVO.setObrProviderNpi(idNumValue);
				}
			}
			if (pids.length() > 5) {
				if (pids.length() < 25) {
					pids.setLength(0);
					rawHL7LabTestVO.setObrProviderIdList(pids.toString());

				} else {
					pids.deleteCharAt(pids.lastIndexOf(","));
					pids.append("]}");
					rawHL7LabTestVO.setObrProviderIdList(pids.toString());
				}

			}
			rawHL7LabTestVO.setObrSpecimenSourceCode(
					theOBR.getObr15_SpecimenSource().getSps1_SpecimenSourceNameOrCode().encode());
			rawHL7LabTestVO.setObrSpecimenSourcename(theOBR.getObr15_SpecimenSource().getSps2_Additives().encode());
			CE obxLoinc = theOBR.getObr4_UniversalServiceIdentifier();
			if (obxLoinc != null) {
				String obxTestCode = obxLoinc.getCe1_Identifier().getValue();
				if (obxTestCode != null && !obxTestCode.isEmpty()) {
					rawHL7LabTestVO.setObrTestCode(obxTestCode);
				}
				String obxTestDesc = obxLoinc.getCe2_Text().getValue();
				if (obxTestDesc != null && !obxTestDesc.isEmpty()) {
					rawHL7LabTestVO.setObrTestDesc(obxTestDesc);
				}
				String obxTestCodingSystem = obxLoinc.getCe3_NameOfCodingSystem().getValue();
				if (obxTestCodingSystem != null && !obxTestCodingSystem.isEmpty()) {
					rawHL7LabTestVO.setObrTestCodingSystem(obxTestCodingSystem);
					if (obxTestCodingSystem.equals("LN")) {
						rawHL7LabTestVO.setObrLoincCode(obxTestCode);
						rawHL7LabTestVO.setObrLoincDesc(obxTestDesc);
					}
				}
				String obxAltTestCode = obxLoinc.getCe4_AlternateIdentifier().getValue();
				if (obxAltTestCode != null && !obxAltTestCode.isEmpty()) {
					rawHL7LabTestVO.setObrAltTestCode(obxAltTestCode);
				}
				String obxAltTestDesc = obxLoinc.getCe5_AlternateText().getValue();
				if (obxAltTestDesc != null && !obxAltTestDesc.isEmpty()) {
					rawHL7LabTestVO.setObrAltTestDesc(obxAltTestDesc);
				}
				String obxAltTestCodingSystem = obxLoinc.getCe6_NameOfAlternateCodingSystem().getValue();
				if (obxAltTestCodingSystem != null && !obxAltTestCodingSystem.isEmpty()) {
					rawHL7LabTestVO.setObrAltTestCodingSystem(obxAltTestCodingSystem);
					if (obxAltTestCodingSystem.equals("LN")) {
						rawHL7LabTestVO.setObrLoincCode(obxAltTestCode);
						rawHL7LabTestVO.setObrLoincDesc(obxAltTestDesc);
					}
				}
			}
		}
		return rawHL7LabTestVO;
	}

	private static RawHL7ObxVO getObxVO(OBX theOBXEntry) throws Exception {
		RawHL7ObxVO rawHL7LabResultVO = new RawHL7ObxVO();
		if (theOBXEntry != null) {
			String ObxSetId = theOBXEntry.getSetIDOBX().getValue();
			if (ObxSetId != null && !ObxSetId.isEmpty()) {
				ObxSetId = ObxSetId.replaceAll("[^\\d]", ""); // added by rzp05
				int setId = Integer.parseInt(ObxSetId); 
				rawHL7LabResultVO.setObxSetId(setId);
			}
			CE obxLoinc = theOBXEntry.getObx3_ObservationIdentifier();
			if (obxLoinc != null) {
				String obxTestCode = obxLoinc.getCe1_Identifier().getValue();
				if (obxTestCode != null && !obxTestCode.isEmpty()) {
					rawHL7LabResultVO.setObxTestCode(obxTestCode);
				}
				String obxTestDesc = obxLoinc.getCe2_Text().getValue();
				if (obxTestDesc != null && !obxTestDesc.isEmpty()) {
					rawHL7LabResultVO.setObxTestDesc(obxTestDesc);
				}
				String obxTestCodingSystem = obxLoinc.getCe3_NameOfCodingSystem().getValue();
				if (obxTestCodingSystem != null && !obxTestCodingSystem.isEmpty()) {
					rawHL7LabResultVO.setObxTestCodingSystem(obxTestCodingSystem);
					if (obxTestCodingSystem.equals("LN")) {
						rawHL7LabResultVO.setObxLoincCode(obxTestCode);
						rawHL7LabResultVO.setObxLoincDesc(obxTestDesc);
					}
				}
				String obxAltTestCode = obxLoinc.getCe4_AlternateIdentifier().getValue();
				if (obxAltTestCode != null && !obxAltTestCode.isEmpty()) {
					rawHL7LabResultVO.setObxAltTestCode(obxAltTestCode);
				}
				String obxAltTestDesc = obxLoinc.getCe5_AlternateText().getValue();
				if (obxAltTestDesc != null && !obxAltTestDesc.isEmpty()) {
					rawHL7LabResultVO.setObxAltTestDesc(obxAltTestDesc);
				}
				String obxAltTestCodingSystem = obxLoinc.getCe6_NameOfAlternateCodingSystem().getValue();
				if (obxAltTestCodingSystem != null && !obxAltTestCodingSystem.isEmpty()) {
					rawHL7LabResultVO.setObxAltTestCodingSystem(obxAltTestCodingSystem);
					if (obxAltTestCodingSystem.equals("LN")) {
						rawHL7LabResultVO.setObxLoincCode(obxAltTestCode);
						rawHL7LabResultVO.setObxLoincDesc(obxAltTestDesc);
					}
				}
			}
			Varies[] theValues = theOBXEntry.getObservationValue();
			if (theValues.length > 0) {
				StringBuilder allResults = new StringBuilder();
				for (int i = 0; i < theValues.length; i++) {
					if (i > 0) {
						allResults.append("#");
					}
					allResults.append(theValues[i].getData().toString());
				}
				if (allResults != null && allResults.length() > 0
						&& allResults.charAt(allResults.length() - 1) == '#') {
					allResults = allResults.deleteCharAt(allResults.length() - 1);
				}
				if (allResults.length() > 4000) {
					String allResultsStr = allResults.substring(0, 3900).toString();
					rawHL7LabResultVO.setObxObservationResultText(allResultsStr);
				} else {
					rawHL7LabResultVO.setObxObservationResultText(allResults.toString());
				}
			}
			String theValueType = theOBXEntry.getValueType().getValue();
			if (theValueType != null && !theValueType.isEmpty()) {
				rawHL7LabResultVO.setObxObservationValueType(theValueType);
			}
			String unitofMeasure = theOBXEntry.getObx6_Units().getCe1_Identifier().getValue();
			if(unitofMeasure != null && unitofMeasure.length() <= 60) rawHL7LabResultVO.setObxUnitOfMeasure(unitofMeasure);
			rawHL7LabResultVO.setObxReferenceRange(theOBXEntry.getObx7_ReferencesRange().getValue());
			rawHL7LabResultVO.setObxAbnormalFlag(theOBXEntry.getObx8_AbnormalFlags(0).getValue());
		}
		return rawHL7LabResultVO;
	}

	private static RawHL7XpnVO getXpnVO(XPN theXPN) {
		RawHL7XpnVO theXpnVO = new RawHL7XpnVO();
		if (theXPN != null) {
			theXpnVO.setLastName(theXPN.getFamilyName().getFn1_Surname().getValue());
			theXpnVO.setFirstName(theXPN.getGivenName().getValue());
			theXpnVO.setMiddleName(theXPN.getSecondAndFurtherGivenNamesOrInitialsThereof().getValue());
			theXpnVO.setNameSuffix(theXPN.getSuffixEgJRorIII().getValue());
		}
		return theXpnVO;
	}

	private static RawHL7XadVO getXadVO(XAD theXAD) {
		RawHL7XadVO theXadVO = new RawHL7XadVO();
		if (theXAD != null) {
			theXadVO.setPidAddressLine1(theXAD.getStreetAddress().getSad1_StreetOrMailingAddress().getValue());
			theXadVO.setPidAddressLine2(theXAD.getOtherDesignation().getValue());
			theXadVO.setPidCity(theXAD.getCity().getValue());
			theXadVO.setPidState(theXAD.getStateOrProvince().getValue());
			theXadVO.setPidZipCode(theXAD.getZipOrPostalCode().getValue());
			theXadVO.setPidCountryOfOrigin(theXAD.getCountry().getValue());
			theXadVO.setPidCountryCode(theXAD.getXad6_Country().getValue());
		}
		return theXadVO;
	}

	private static RawHL7INSVO getINSVO(IN1 theIN1, IN2 theIN2) throws Exception {
		RawHL7INSVO rawHL7INSVO = new RawHL7INSVO();
		if (theIN1 != null) {
			String In1SetId = theIN1.getSetIDIN1().getValue();
			if (In1SetId != null && !In1SetId.isEmpty() && In1SetId.contains("^[0-9]+")) {
				int setId = Integer.parseInt(In1SetId);
				rawHL7INSVO.setSetId(setId);
			}
			rawHL7INSVO.setPlanId(theIN1.getInsurancePlanID().getCe2_Text().getValue());
			rawHL7INSVO.setCompanyId(theIN1.getInsuranceCompanyID(0).getCx1_IDNumber().getValue());
			XON InsuranceName = theIN1.getIn14_InsuranceCompanyName(0);
			if (InsuranceName != null) {
				rawHL7INSVO.setCompanyName(InsuranceName.getXon1_OrganizationName().getValue());
			}
			XAD companyAddress = theIN1.getIn15_InsuranceCompanyAddress(0);
			if (companyAddress != null) {
				rawHL7INSVO.setCompanyAddress1(
						companyAddress.getStreetAddress().getSad1_StreetOrMailingAddress().getValue());
				rawHL7INSVO.setCompanyAddress2(companyAddress.getOtherDesignation().getValue());
				rawHL7INSVO.setCompanyCity(companyAddress.getCity().getValue());
				rawHL7INSVO.setCompanyState(companyAddress.getStateOrProvince().getValue());
				String CompanyAddress = companyAddress.getZipOrPostalCode().getValue();
				if (CompanyAddress != null && !CompanyAddress.isEmpty()) {
					CompanyAddress = CompanyAddress.trim();
					rawHL7INSVO.setCompanyZipCode(CompanyAddress);
				}
				rawHL7INSVO.setCompanyCountry(companyAddress.getCountry().getValue());
				rawHL7INSVO.setCompanyCounty(companyAddress.getXad6_Country().getValue());
			}
			rawHL7INSVO.setCompanyPhone(theIN1.getIn17_InsuranceCoPhoneNumber(0).getTelephoneNumber().getValue());
			rawHL7INSVO.setGroupNumber(theIN1.getIn18_GroupNumber().getValue());
			rawHL7INSVO.setGroupName(theIN1.getIn19_GroupName(0).getOrganizationName().getValue());
			rawHL7INSVO.setInsuredGroupEmpId(theIN1.getIn110_InsuredSGroupEmpID(0).getCx1_IDNumber().getValue());
			rawHL7INSVO.setInsuredGroupEmpName(
					theIN1.getIn111_InsuredSGroupEmpName(0).getXon1_OrganizationName().getValue());
			rawHL7INSVO.setPlanEffectiveDate(theIN1.getIn112_PlanEffectiveDate().encode());
			rawHL7INSVO.setPlanExpirationDate(theIN1.getIn113_PlanExpirationDate().encode());
			rawHL7INSVO.setPlanType(theIN1.getIn115_PlanType().getValue());
			XPN insuranceName = theIN1.getIn116_NameOfInsured(0);
			if (insuranceName != null) {
				String firstName = insuranceName.getGivenName().getValue();
				String lastName = insuranceName.getFamilyName().getFn1_Surname().getValue();
				if(firstName != null && lastName != null && lastName.length() > 40) lastName = nameValidation(firstName,lastName);
				if(lastName != null && lastName.length() > 40) {
					//System.out.println("code enter after name validation");
					lastName = lastName.substring(0,40);
				}
				rawHL7INSVO.setInsuredFName(firstName);
				rawHL7INSVO.setInsuredLName(lastName);
				rawHL7INSVO.setInsuredMName(insuranceName.getSecondAndFurtherGivenNamesOrInitialsThereof().getValue());
			}
			rawHL7INSVO.setInsuredRelationShip(theIN1.getInsuredSRelationshipToPatient().getCe2_Text().getValue());
			rawHL7INSVO.setInsuredDOBText(theIN1.getIn118_InsuredSDateOfBirth().encode());
			XAD insuredAddress = theIN1.getIn119_InsuredSAddress(0);
			if (insuredAddress != null) {
				rawHL7INSVO.setInsuredAddress1(
						insuredAddress.getStreetAddress().getSad1_StreetOrMailingAddress().getValue());
				rawHL7INSVO.setInsuredAddress2(insuredAddress.getOtherDesignation().getValue());
				rawHL7INSVO.setInsuredCity(insuredAddress.getCity().getValue());
				rawHL7INSVO.setInsuredState(insuredAddress.getStateOrProvince().getValue());
				rawHL7INSVO.setInsuredZipCode(insuredAddress.getZipOrPostalCode().getValue());
				rawHL7INSVO.setInsuredCounty(insuredAddress.getXad6_Country().getValue());
				rawHL7INSVO.setInsuredCountry(insuredAddress.getCountry().getValue());
			}
			rawHL7INSVO.setPolicyNumber(theIN1.getPolicyNumber().getValue());
		}
		if (theIN2 != null) {
			rawHL7INSVO.setSsn(theIN2.getIn22_InsuredSSocialSecurityNumber().getValue());
			rawHL7INSVO.setEmployerName(theIN2.getIn23_InsuredSEmployerSNameAndID(0).getIDNumber().getValue());
			XPN medicaidName = theIN2.getIn27_MedicaidCaseName(0);
			if (medicaidName != null) {
				rawHL7INSVO.setMedicaidFname(medicaidName.getGivenName().getValue());
				rawHL7INSVO.setMedicaidLname(medicaidName.getFamilyName().getFn1_Surname().getValue());
				rawHL7INSVO.setMedicaidMname(medicaidName.getSecondAndFurtherGivenNamesOrInitialsThereof().getValue());
			}
			rawHL7INSVO.setMedicaidCaseNumber(theIN2.getIn28_MedicaidCaseNumber().getValue());
			rawHL7INSVO.setMaritalStatus(theIN2.getIn243_MaritalStatus(0).getCe1_Identifier().getValue());
			rawHL7INSVO.setNationality(theIN2.getIn241_Nationality().getCe1_Identifier().getValue());
			rawHL7INSVO.setEthnicgroup(theIN2.getIn242_EthnicGroup(0).getCe1_Identifier().getValue());
			rawHL7INSVO.setJobTitle(theIN2.getIn246_JobTitle().getValue());
			rawHL7INSVO.setJobStatus(theIN2.getIn248_JobStatus().getValue());
			rawHL7INSVO
					.setInsuredPhoneNumber(theIN2.getIn263_InsuredSPhoneNumberHome(0).getTelephoneNumber().getValue());
			rawHL7INSVO.setRace(theIN2.getIn271_Race(0).getCe2_Text().getValue());
		}
		return rawHL7INSVO;
	}
   //adding name validation to hl7 insurance
	private static String nameValidation(String firstName, String lastName) {
		StringBuilder sb = new StringBuilder(lastName);
		/// check if last name has first name
		if (lastName.endsWith(firstName)) {
			sb.substring(0, lastName.length() - firstName.length()).trim().replaceAll("[\\-/:!]+$", "").trim();
			lastName = sb.toString();
		}
		return lastName;
	}

	private static RawHL7ORCVO getORCVO(ORC theORC) {
		RawHL7ORCVO rawHL7ORCVO = new RawHL7ORCVO();
		if (theORC != null) {
			rawHL7ORCVO.setOrderControl(theORC.getOrc1_OrderControl().getValue());
			rawHL7ORCVO.setPlacerOrderNumber(theORC.getPlacerOrderNumber().getEi1_EntityIdentifier().getValue());
			rawHL7ORCVO.setFillerOrderNumber(theORC.getFillerOrderNumber().getEi1_EntityIdentifier().getValue());
			rawHL7ORCVO.setPlacerGroupNumber(theORC.getPlacerGroupNumber().getEi1_EntityIdentifier().getValue());
			rawHL7ORCVO.setOrderStatus(theORC.getOrderStatus().getValue());
			rawHL7ORCVO.setDateTimeOfTransaction(theORC.getDateTimeOfTransaction().getTs1_Time().toString());
			rawHL7ORCVO.setEnteringOrganization(theORC.getEnteringOrganization().getCe1_Identifier().getValue());
			XCN[] orderingProvider = theORC.getOrc12_OrderingProvider();
			int orderingProviderlngth = orderingProvider.length;
			if (orderingProviderlngth > 0) {
				XCN OrderProvFirst = orderingProvider[0];
				if (OrderProvFirst != null) {
					rawHL7ORCVO.setOrderProviderFname(OrderProvFirst.getGivenName().getValue());
					rawHL7ORCVO.setOrderProviderLname(OrderProvFirst.getFamilyName().getFn1_Surname().getValue());
					rawHL7ORCVO.setOrderProviderMname(
							OrderProvFirst.getSecondAndFurtherGivenNamesOrInitialsThereof().getValue());
					rawHL7ORCVO.setOrderProviderId(OrderProvFirst.getXcn1_IDNumber().getValue());
				}
			}
			rawHL7ORCVO.setOrderEffectiveDateTime(theORC.getOrc15_OrderEffectiveDateTime().getTs1_Time().toString());
			XAD OrderingProvAddr = theORC.getOrc24_OrderingProviderAddress(0);
			if (OrderingProvAddr != null) {
				rawHL7ORCVO.setOrderingProviderAddress1(
						OrderingProvAddr.getStreetAddress().getSad1_StreetOrMailingAddress().getValue());
				rawHL7ORCVO.setOrderingProviderAddress2(OrderingProvAddr.getOtherDesignation().getValue());
				rawHL7ORCVO.setOrderingProviderCity(OrderingProvAddr.getCity().getValue());
				rawHL7ORCVO.setOrderingProviderState(OrderingProvAddr.getStateOrProvince().getValue());
				rawHL7ORCVO.setOrderingProviderZipCode(OrderingProvAddr.getZipOrPostalCode().getValue());
				rawHL7ORCVO.setOrderingProviderCountry(OrderingProvAddr.getCountry().getValue());
				rawHL7ORCVO.setOrderingProviderCounty(OrderingProvAddr.getXad6_Country().getValue());
			}

			XAD OrderingFacilityAddr = theORC.getOrderingFacilityAddress(0);
			if (OrderingFacilityAddr != null) {
				rawHL7ORCVO.setOrderingFacilityAddress1(
						OrderingFacilityAddr.getStreetAddress().getSad1_StreetOrMailingAddress().getValue());
				rawHL7ORCVO.setOrderingFacilityAddress2(OrderingFacilityAddr.getOtherDesignation().getValue());
				rawHL7ORCVO.setOrderingFacilityCity(OrderingFacilityAddr.getCity().getValue());
				rawHL7ORCVO.setOrderingFacilityState(OrderingFacilityAddr.getStateOrProvince().getValue());
				rawHL7ORCVO.setOrderingFacilityZipCode(OrderingFacilityAddr.getZipOrPostalCode().getValue());
				rawHL7ORCVO.setOrderingFacilityCountry(OrderingFacilityAddr.getCountry().getValue());
				rawHL7ORCVO.setOrderingFacilityCounty(OrderingFacilityAddr.getXad6_Country().getValue());
			}
			rawHL7ORCVO
					.setOrderingFacilityPhone(theORC.getOrderingFacilityPhoneNumber(0).getTelephoneNumber().getValue());
			rawHL7ORCVO.setOrderingFacilityName(
					theORC.getOrc21_OrderingFacilityName(0).getXon1_OrganizationName().getValue());
			rawHL7ORCVO.setOrderType(theORC.getOrderType().getCwe1_Identifier().getValue());
		}
		return rawHL7ORCVO;
	}

	@Transactional
	private int writeRawHL7RecordDataToStdTable(ResultSet resultSet) throws SQLException {
		Map<Object, Object> stdHL7DocinsertVo = new HashMap<>();

		String FNAME_SPLIT = "";
		String MNAME_SPLIT = "";
		String MIDDLE_REPEAT = "";
		String[] FirstNameWithSuffix = new String[2];
		String[] MiddleNameWithSuffix = new String[2];
		String[] LastNameWithSuffix = new String[2];
		String Suffix = "";

		int uphnId = resultSet.getInt("UPHN_TRANX_IN_ID");
		int document_id = resultSet.getInt("DOCUMENT_ID");
		stdHL7DocinsertVo.put("SUFFIX", resultSet.getString("PIDNAMESUFFIX"));
		stdHL7DocinsertVo.put("DIN", CommonUtil.dinValidation(resultSet.getString("PIDDIN")));
		stdHL7DocinsertVo.put("DOCUMENT_ID", document_id);
		// stdHL7DocinsertVo.put("FNAME",
		// CommonUtil.NameValidation(resultSet.getString("PIDFIRSTNAME")));
		stdHL7DocinsertVo.put("GENDER", LabCommonUtils.checkCurrentGenderData(resultSet.getString("PIDSEXCODE")));
		// stdHL7DocinsertVo.put("LNAME",
		// CommonUtil.NameValidation(resultSet.getString("PIDLASTNAME")));
		stdHL7DocinsertVo.put("MED_RECORD_NUMBER", resultSet.getString("PIDMEDICALRECORDNUMBER"));
		// stdHL7DocinsertVo.put("MNAME",
		// CommonUtil.NameValidation(resultSet.getString("PIDMIDDLENAME")));

		String FirstName = CommonUtil.NameValidationHit(resultSet.getString("PIDFIRSTNAME"));
		String MiddleName = CommonUtil.NameValidationHit(resultSet.getString("PIDMIDDLENAME"));
		String LastName = CommonUtil.NameValidationHit(resultSet.getString("PIDLASTNAME"));
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

		stdHL7DocinsertVo.put("FNAME", FirstName);
		stdHL7DocinsertVo.put("MNAME", MiddleName);
		stdHL7DocinsertVo.put("LNAME", LastName);
		String SuffixFromFile = resultSet.getString("PIDNAMESUFFIX");
		if (CommonUtil.isNotNullAndEmpty(SuffixFromFile)) {
			SuffixFromFile = SuffixFromFile.trim().toUpperCase();
			SuffixFromFile = CommonUtil.NameValidationHit(SuffixFromFile);
			if (CommonUtil.isNotNullAndEmpty(Suffix)) {
				if (SuffixFromFile.equals(Suffix)) {
					stdHL7DocinsertVo.put("SUFFIX", Suffix);
				} else {
					stdHL7DocinsertVo.put("SUFFIX", SuffixFromFile + " " + Suffix);
				}
			} else {
				stdHL7DocinsertVo.put("SUFFIX", SuffixFromFile);
			}
		} else if (CommonUtil.isNotNullAndEmpty(Suffix)) {
			stdHL7DocinsertVo.put("SUFFIX", Suffix);
		}
		stdHL7DocinsertVo.put("MPI", resultSet.getString("PIDMPI"));
		stdHL7DocinsertVo.put("NYSID", CommonUtil.nysidValidation(resultSet.getString("PIDNYSID")));
		stdHL7DocinsertVo.put("ETHNICITY", "");
		String addressLine1 = "";
		if (CommonUtil.isNotNullAndEmpty(resultSet.getString("PIDADDRESSLINE1"))) {
			addressLine1 = resultSet.getString("PIDADDRESSLINE1") + " ";
		}
		String addressLine2 = "";
		if (CommonUtil.isNotNullAndEmpty(resultSet.getString("PIDADDRESSLINE2"))) {
			addressLine2 = resultSet.getString("PIDADDRESSLINE2") + " ";
		}
		stdHL7DocinsertVo.put("PERSON_ADDRESS",
				CommonUtil.addressValidation(new StringBuilder().append(addressLine1).append(addressLine2).toString()));
		stdHL7DocinsertVo.put("PERSON_CITY", resultSet.getString("PIDCITY"));
		String country = resultSet.getString("PIDCOUNTRYCODE");
		if (country != null && !country.isEmpty()) {
			if (!country.isEmpty()) {
				country = country.toUpperCase().trim();
			}
			if (country.equals("US") || country.equals("UNITED STATES")) {
				stdHL7DocinsertVo.put("PERSON_COUNTRY", "USA");
			} else {
				stdHL7DocinsertVo.put("PERSON_COUNTRY", country);
			}
		}
		stdHL7DocinsertVo.put("PERSON_EXTERNAL_ID", resultSet.getString("PIDFACILITYIDEXTERNAL"));
		stdHL7DocinsertVo.put("PERSON_INTERNAL_ID", resultSet.getString("PIDFACILITYIDINTERNAL"));
//		stdHL7DocinsertVo.put("PERSON_PHONE", CommonUtil.GetNumberFromString(resultSet.getString("PIDHOMEPHONE")));
		
		String person_phone = resultSet.getString("PIDHOMEPHONE");
		if (CommonUtil.isNotNullAndEmpty(person_phone)) {
			person_phone = CommonUtil.verifythephone(person_phone);
		}
		stdHL7DocinsertVo.put("PERSON_PHONE", person_phone);
		
		String RES_ZIP = CommonUtil.formatZipAndPlus4Code(resultSet.getString("PIDZIPCODE"));
		String zipcode = null;
		if (CommonUtil.isNotNullAndEmpty(RES_ZIP)) {
			if (RES_ZIP.length() >= 9) {
				zipcode = RES_ZIP.substring(0, 5);
				stdHL7DocinsertVo.put("PERSON_ZIP", zipcode);
				stdHL7DocinsertVo.put("PERSON_PLUS4", RES_ZIP.substring(5, 9));
				stdHL7DocinsertVo.put("PERSON_COUNTY", populateCountyValue(zipcode));

			} else if (RES_ZIP.length() == 5) {
				zipcode = RES_ZIP;
				stdHL7DocinsertVo.put("PERSON_ZIP", zipcode);
				stdHL7DocinsertVo.put("PERSON_COUNTY", populateCountyValue(zipcode));

			}
		} else {
			if (CommonUtil.isNullOrEmpty(RES_ZIP)) {
				stdHL7DocinsertVo.put("PERSON_COUNTY", "999");
			}
		}
		String personState = resultSet.getString("PIDSTATE");

		if (CommonUtil.isNotNullAndEmpty(personState)) {
			stdHL7DocinsertVo.put("PERSON_STATE", commonDocumentService.validateState(personState));
		}

		stdHL7DocinsertVo.put("PROVIDER_FNAME", CommonUtil.NameValidation(resultSet.getString("PV1PROVIDERFIRSTNAME")));
		stdHL7DocinsertVo.put("PROVIDER_LNAME", CommonUtil.NameValidation(resultSet.getString("PV1PROVIDERLASTNAME")));
		String providerId = resultSet.getString("PV1PROVIDERID");
		if (providerId != null && providerId.contains(" ")) {
			providerId = providerId.substring(0, providerId.indexOf(" "));
			stdHL7DocinsertVo.put("PROVIDER_ID", providerId);
		} else {
			stdHL7DocinsertVo.put("PROVIDER_ID", resultSet.getString("PV1PROVIDERID"));
		}
		stdHL7DocinsertVo.put("PROVIDER_ID_AUTH", resultSet.getString("PV1PROVIDERAUTH"));
		stdHL7DocinsertVo.put("PROVIDER_LNAME", CommonUtil.NameValidation(resultSet.getString("PV1PROVIDERLASTNAME")));

		String dob_text = resultSet.getString("PIDDATEOFBIRTHRAW");
		if (CommonUtil.isNotNullAndEmpty(dob_text) && dob_text.length() > 7) {
			if(dob_text.contains("/")) {
				stdHL7DocinsertVo.put("DOB_TEXT", dob_text);
			}else {
				dob_text = dob_text.substring(0, 8);
				stdHL7DocinsertVo.put("DOB_TEXT", dob_text);
			}
			//stdHL7DocinsertVo.put("DOB_TEXT", dob_text);
		} else {
			stdHL7DocinsertVo.put("DOB_TEXT", null);
		}

		stdHL7DocinsertVo.put("SSN", CommonUtil.formatSSNValue(resultSet.getString("PIDSSN")));
		stdHL7DocinsertVo.put("UPHN_TRANX_IN_ID", resultSet.getInt("UPHN_TRANX_IN_ID"));
		stdHL7DocinsertVo.put("BIRTH_COUNTRY", resultSet.getString("PIDCOUNTRYOFORIGIN"));
		stdHL7DocinsertVo.put("CURRENT_PROCESS_STATUS", "5");
		/*
		 * Adding 3 More Parameters from RAW to STD EVENT_DATE, EVENT_TYPE, ENCOUNTER_ID
		 */
		stdHL7DocinsertVo.put("EVENT_DATE", resultSet.getString("EVNEVENTDATE"));
		stdHL7DocinsertVo.put("EVENT_TYPE", resultSet.getString("EVNEVENTTYPE"));
		stdHL7DocinsertVo.put("ENCOUNTER_ID", resultSet.getString("PIDPATIENTACCOUNTNUM"));
		/* END */
		stdHL7DocinsertVo.put("DATE_RECEIVED", resultSet.getDate("MSHCREATEDATE"));
		stdHL7DocinsertVo.put("FACILITY_CODE", resultSet.getString("MSHSENDINGFACILITYNAME"));
		stdHL7DocinsertVo.put("FACILITY_NAME", resultSet.getString("FACILITY_NAME"));
		stdHL7DocinsertVo.put("HL7_MESSAGE_TYPE", resultSet.getString("MSHMESSAGETYPE"));
		stdHL7DocinsertVo.put("INSURANCE_NUMBER", resultSet.getString("PIDTYPEOFINSURANCE"));
		stdHL7DocinsertVo.put("MEDICAID_NUMBER", resultSet.getString("PIDMEDICAIDNUMBER"));
		stdHL7DocinsertVo.put("VETERAN_STATUS", resultSet.getString("PIDVETERANSTATUS"));
		stdHL7DocinsertVo.put("PROVIDER_ID_AUTH", resultSet.getString("PV1PROVIDERAUTH"));
		//stdHL7DocinsertVo.put("CCD_MATCH_STATENO", resultSet.getString("CCD_MATCH_STATENO"));
		stdHL7DocinsertVo.put("CCD_MATCH_DOC_ID", null);
		stdHL7DocinsertVo.put("CCD_MATCH_STATENO", null);
		java.util.Date today = new java.util.Date();
		java.sql.Date timestamp = new java.sql.Date(today.getTime());
		stdHL7DocinsertVo.put("MATCH_PROCESS_DATE", timestamp);

		stdHL7DocinsertVo.put("FACILITY_ADDRESS", resultSet.getString("FACILITY_ADDRESS"));
		stdHL7DocinsertVo.put("FACILITY_CITY", resultSet.getString("FACILITY_CITY"));

		String facilityState = resultSet.getString("FACILITY_STATE");

		if (CommonUtil.isNotNullAndEmpty(facilityState)) {
			stdHL7DocinsertVo.put("FACILITY_STATE",
					commonDocumentService.validateState(facilityState));
		}
		String faczipcode = null;
		String FAC_ZIP = CommonUtil.formatZipAndPlus4Code(resultSet.getString("FACILITY_ZIP"));
		if (CommonUtil.isNotNullAndEmpty(FAC_ZIP)) {
			if (FAC_ZIP.length() >= 9) {
				faczipcode = FAC_ZIP.substring(0, 5);
				stdHL7DocinsertVo.put("FACILITY_ZIP", FAC_ZIP.substring(0, 5));
				stdHL7DocinsertVo.put("FACILITY_COUNTY", populateCountyValue(faczipcode));
			} else if (FAC_ZIP.length() == 5) {
				stdHL7DocinsertVo.put("FACILITY_ZIP", FAC_ZIP);
				stdHL7DocinsertVo.put("FACILITY_COUNTY", populateCountyValue(FAC_ZIP));
			}
		} else {
			if (CommonUtil.isNullOrEmpty(FAC_ZIP)) {
				stdHL7DocinsertVo.put("FACILITY_COUNTY", "999");
			}
		}

		stdHL7DocinsertVo.put("RACE_WHITE", "0");
		stdHL7DocinsertVo.put("RACE_BLACK", "0");
		stdHL7DocinsertVo.put("RACE_ASIAN", "0");
		stdHL7DocinsertVo.put("RACE_INDIAN", "0");
		stdHL7DocinsertVo.put("RACE_PACIFIC", "0");
		stdHL7DocinsertVo.put("RACE_UNKNOWN", "0");
		stdHL7DocinsertVo.put("RACE_OTHER", "0");
		StringBuilder _raceUnTranslatableText = new StringBuilder();
		StringBuilder _raceUnOtherText = new StringBuilder();
		String ethnicity = resultSet.getString("PIDETHNICGROUPCODE");
		String raceCodeValue = resultSet.getString("PIDRACECODERAW");
		String racedescValue = resultSet.getString("RACECODEDESC");

		if (CommonUtil.isNotNullAndEmpty(ethnicity)) {
			ethnicity = ethnicity.toUpperCase().trim();
			if (ethnicityMap.get(ethnicity) != null) {
				String ethCodeValid = ethnicityMap.get(ethnicity);
				if (!ethCodeValid.equals("99")) {
					stdHL7DocinsertVo.put("ETHNICITY", ethnicityMap.get(ethnicity));
				} else {
					stdHL7DocinsertVo.put("ETHNICITY", "");
				}

				String raceCodeValid = raceMap.get(ethnicity);
				if (!raceCodeValid.equals("UNTRANSLATABLE")) {
					if (raceCodeValid.equalsIgnoreCase("BLACK")) {
						stdHL7DocinsertVo.put("RACE_BLACK", "1");
						stdHL7DocinsertVo.put("RACE_UNKNOWN", "0");
					} else if (raceCodeValid.equalsIgnoreCase("WHITE")) {
						stdHL7DocinsertVo.put("RACE_WHITE", "1");
						stdHL7DocinsertVo.put("RACE_UNKNOWN", "0");
					} else if (raceCodeValid.equalsIgnoreCase("INDIAN")) {
						stdHL7DocinsertVo.put("RACE_INDIAN", "1");
						stdHL7DocinsertVo.put("RACE_UNKNOWN", "0");
					} else if (raceCodeValid.equalsIgnoreCase("PACIFIC")) {
						stdHL7DocinsertVo.put("RACE_PACIFIC", "1");
						stdHL7DocinsertVo.put("RACE_UNKNOWN", "0");
					} else if (raceCodeValid.equalsIgnoreCase("ASIAN")) {
						stdHL7DocinsertVo.put("RACE_ASIAN", "1");
						stdHL7DocinsertVo.put("RACE_UNKNOWN", "0");
					} else if (raceCodeValid.equalsIgnoreCase("ASIAN,PACIFIC")) {
						stdHL7DocinsertVo.put("RACE_ASIAN", "1");
						stdHL7DocinsertVo.put("RACE_PACIFIC", "1");
						stdHL7DocinsertVo.put("RACE_UNKNOWN", "0");
					} else if (raceCodeValid.equalsIgnoreCase("OTHER")) {
						stdHL7DocinsertVo.put("RACE_OTHER", "1");
						stdHL7DocinsertVo.put("RACE_UNKNOWN", "0");
					}
				}
			} else {
				stdHL7DocinsertVo = null;
				commonDocumentService.updateRawDocumentStatus(document_id, "RAW_HL7_DOC", 4);
				return 4;
			}
		} else if (CommonUtil.isNotNullAndEmpty(raceCodeValue)) {
			raceCodeValue = raceCodeValue.toUpperCase().trim();
			if (ethnicityMap.get(raceCodeValue) != null) {
				String ethCodeValid = ethnicityMap.get(raceCodeValue);
				if (!ethCodeValid.equals("99")) {
					stdHL7DocinsertVo.put("ETHNICITY", ethnicityMap.get(raceCodeValue));
				} else {
					stdHL7DocinsertVo.put("ETHNICITY", "");
				}
			}
		} else if (CommonUtil.isNotNullAndEmpty(racedescValue)) {
			racedescValue = racedescValue.toUpperCase().trim();
			if (ethnicityMap.get(racedescValue) != null) {
				String ethCodeValid = ethnicityMap.get(racedescValue);
				if (!ethCodeValid.equals("99")) {
					stdHL7DocinsertVo.put("ETHNICITY", ethnicityMap.get(racedescValue));
				} else {
					stdHL7DocinsertVo.put("ETHNICITY", "");
				}
			}
		}

		if (CommonUtil.isNotNullAndEmpty(raceCodeValue)) {
			raceCodeValue = raceCodeValue.toUpperCase().trim();
			String theRaceCode = raceMap.get(raceCodeValue);
			if (theRaceCode != null && theRaceCode.contains(",")) {
				String[] parts_code = theRaceCode.split("\\,");
				for (int j = 0; j < parts_code.length; j++) {
					String raceCode = parts_code[j];
					if (raceCode.equalsIgnoreCase("BLACK")) {
						stdHL7DocinsertVo.put("RACE_BLACK", "1");
					} else if (raceCode.equalsIgnoreCase("WHITE")) {
						stdHL7DocinsertVo.put("RACE_WHITE", "1");
					} else if (raceCode.equalsIgnoreCase("INDIAN")) {
						stdHL7DocinsertVo.put("RACE_INDIAN", "1");
					} else if (raceCode.equalsIgnoreCase("PACIFIC")) {
						stdHL7DocinsertVo.put("RACE_PACIFIC", "1");
					} else if (raceCode.equalsIgnoreCase("ASIAN")) {
						stdHL7DocinsertVo.put("RACE_ASIAN", "1");
					} else if (raceCode.equalsIgnoreCase("OTHER")) {
						stdHL7DocinsertVo.put("RACE_OTHER", "1");
					} else if (raceCode.equalsIgnoreCase("UNTRANSLATABLE")) {
						_raceUnTranslatableText = _raceUnTranslatableText.append(raceCodeValue).append(", ");
					}
				}
			} else if (theRaceCode != null) {
				if (theRaceCode.equalsIgnoreCase("BLACK")) {
					stdHL7DocinsertVo.put("RACE_BLACK", "1");
				} else if (theRaceCode.equalsIgnoreCase("WHITE")) {
					stdHL7DocinsertVo.put("RACE_WHITE", "1");
				} else if (theRaceCode.equalsIgnoreCase("INDIAN")) {
					stdHL7DocinsertVo.put("RACE_INDIAN", "1");
				} else if (theRaceCode.equalsIgnoreCase("PACIFIC")) {
					stdHL7DocinsertVo.put("RACE_PACIFIC", "1");
				} else if (theRaceCode.equalsIgnoreCase("ASIAN")) {
					stdHL7DocinsertVo.put("RACE_ASIAN", "1");
				} else if (theRaceCode.equalsIgnoreCase("ASIAN,PACIFIC")) {
					stdHL7DocinsertVo.put("RACE_ASIAN", "1");
					stdHL7DocinsertVo.put("RACE_PACIFIC", "1");
				} else if (theRaceCode.equalsIgnoreCase("OTHER")) {
					stdHL7DocinsertVo.put("RACE_OTHER", "1");
					_raceUnOtherText = _raceUnOtherText.append(raceCodeValue).append(", ");
				} else if (theRaceCode.equalsIgnoreCase("UNKNOWN")) {
					stdHL7DocinsertVo.put("RACE_UNKNOWN", "1");
				} else if (theRaceCode.equalsIgnoreCase("UNTRANSLATABLE")) {
					_raceUnTranslatableText = _raceUnTranslatableText.append(raceCodeValue).append(", ");
				}
			} else {
				stdHL7DocinsertVo = null;
				commonDocumentService.updateRawDocumentStatus(document_id, "RAW_HL7_DOC", 4);
				/*
				 * hitCommonDocumentService.storeHitError(new Exception(
				 * "MOVE TO STD ERROR : Race Code Value did not Map to any RACE_VALUE in CODED_HIT_RACE_DESC for UPHN_TRANX_IN_ID = "
				 * + uphnId + " new value added to CODED_HIT_RACE_DESC with RACE_VALUE as = " +
				 * raceCodeValue + ".  "), uphnId, "", "HL7");
				 */
				return 4;
			}

			if (CommonUtil.isNotNullAndEmpty(_raceUnTranslatableText.toString())) {

				stdHL7DocinsertVo.put("RACE_UNTRANSLATABLE",
						_raceUnTranslatableText.substring(0, _raceUnTranslatableText.length() - 2));
			}

			if (CommonUtil.isNotNullAndEmpty(_raceUnOtherText.toString())) {

				stdHL7DocinsertVo.put("RACE_OTHER_TEXT", _raceUnOtherText.substring(0, _raceUnOtherText.length() - 2));
			}

		} else if (CommonUtil.isNotNullAndEmpty(racedescValue)) {
			racedescValue = racedescValue.toUpperCase().trim();
			String theRaceCode = raceMap.get(racedescValue);
			if (theRaceCode != null && theRaceCode.contains(",")) {
				String[] parts_code = theRaceCode.split("\\,");
				for (int j = 0; j < parts_code.length; j++) {
					String raceCode = parts_code[j];
					if (raceCode.equalsIgnoreCase("BLACK")) {
						stdHL7DocinsertVo.put("RACE_BLACK", "1");
					} else if (raceCode.equalsIgnoreCase("WHITE")) {
						stdHL7DocinsertVo.put("RACE_WHITE", "1");
					} else if (raceCode.equalsIgnoreCase("INDIAN")) {
						stdHL7DocinsertVo.put("RACE_INDIAN", "1");
					} else if (raceCode.equalsIgnoreCase("PACIFIC")) {
						stdHL7DocinsertVo.put("RACE_PACIFIC", "1");
					} else if (raceCode.equalsIgnoreCase("ASIAN")) {
						stdHL7DocinsertVo.put("RACE_ASIAN", "1");
					} else if (raceCode.equalsIgnoreCase("OTHER")) {
						stdHL7DocinsertVo.put("RACE_OTHER", "1");
					} else if (raceCode.equalsIgnoreCase("UNTRANSLATABLE")) {
						_raceUnTranslatableText = _raceUnTranslatableText.append(racedescValue).append(", ");
					}
				}
			} else if (theRaceCode != null) {
				if (theRaceCode.equalsIgnoreCase("BLACK")) {
					stdHL7DocinsertVo.put("RACE_BLACK", "1");
				} else if (theRaceCode.equalsIgnoreCase("WHITE")) {
					stdHL7DocinsertVo.put("RACE_WHITE", "1");
				} else if (theRaceCode.equalsIgnoreCase("INDIAN")) {
					stdHL7DocinsertVo.put("RACE_INDIAN", "1");
				} else if (theRaceCode.equalsIgnoreCase("PACIFIC")) {
					stdHL7DocinsertVo.put("RACE_PACIFIC", "1");
				} else if (theRaceCode.equalsIgnoreCase("ASIAN")) {
					stdHL7DocinsertVo.put("RACE_ASIAN", "1");
				} else if (theRaceCode.equalsIgnoreCase("ASIAN,PACIFIC")) {
					stdHL7DocinsertVo.put("RACE_ASIAN", "1");
					stdHL7DocinsertVo.put("RACE_PACIFIC", "1");
				} else if (theRaceCode.equalsIgnoreCase("OTHER")) {
					stdHL7DocinsertVo.put("RACE_OTHER", "1");
					_raceUnOtherText = _raceUnOtherText.append(racedescValue).append(", ");
				} else if (theRaceCode.equalsIgnoreCase("UNKNOWN")) {
					stdHL7DocinsertVo.put("RACE_UNKNOWN", "1");
				} else if (theRaceCode.equalsIgnoreCase("UNTRANSLATABLE")) {
					_raceUnTranslatableText = _raceUnTranslatableText.append(racedescValue).append(", ");
				}
			} else {
				stdHL7DocinsertVo = null;
				commonDocumentService.updateRawDocumentStatus(document_id, "RAW_HL7_DOC", 4);
				/*
				 * hitCommonDocumentService.storeHitError(new Exception(
				 * "MOVE TO STD ERROR : Race Code Value did not Map to any RACE_VALUE in CODED_HIT_RACE_DESC for UPHN_TRANX_IN_ID = "
				 * + uphnId + " new value added to CODED_HIT_RACE_DESC with RACE_VALUE as = " +
				 * raceCodeValue + ".  "), uphnId, "", "HL7");
				 */
				return 4;
			}

			if (CommonUtil.isNotNullAndEmpty(_raceUnTranslatableText.toString())) {

				stdHL7DocinsertVo.put("RACE_UNTRANSLATABLE",
						_raceUnTranslatableText.substring(0, _raceUnTranslatableText.length() - 2));
			}

			if (CommonUtil.isNotNullAndEmpty(_raceUnOtherText.toString())) {

				stdHL7DocinsertVo.put("RACE_OTHER_TEXT", _raceUnOtherText.substring(0, _raceUnOtherText.length() - 2));
			}
		} else if (CommonUtil.isNotNullAndEmpty(ethnicity)) {
			ethnicity = ethnicity.toUpperCase().trim();
			if (raceMap.get(ethnicity) != null) {
				String theRaceCode = raceMap.get(ethnicity);
				if (theRaceCode != null) {
					if (theRaceCode.equalsIgnoreCase("BLACK")) {
						stdHL7DocinsertVo.put("RACE_BLACK", "1");
					} else if (theRaceCode.equalsIgnoreCase("WHITE")) {
						stdHL7DocinsertVo.put("RACE_WHITE", "1");
					} else if (theRaceCode.equalsIgnoreCase("INDIAN")) {
						stdHL7DocinsertVo.put("RACE_INDIAN", "1");
					} else if (theRaceCode.equalsIgnoreCase("PACIFIC")) {
						stdHL7DocinsertVo.put("RACE_PACIFIC", "1");
					} else if (theRaceCode.equalsIgnoreCase("ASIAN")) {
						stdHL7DocinsertVo.put("RACE_ASIAN", "1");
					} else if (theRaceCode.equalsIgnoreCase("ASIAN,PACIFIC")) {
						stdHL7DocinsertVo.put("RACE_ASIAN", "1");
						stdHL7DocinsertVo.put("RACE_PACIFIC", "1");
					} else if (theRaceCode.equalsIgnoreCase("OTHER")) {
						stdHL7DocinsertVo.put("RACE_OTHER", "1");
						_raceUnOtherText = _raceUnOtherText.append(raceCodeValue).append(", ");
					} else if (theRaceCode.equalsIgnoreCase("UNKNOWN")) {
						stdHL7DocinsertVo.put("RACE_UNKNOWN", "1");
					} else if (theRaceCode.equalsIgnoreCase("UNTRANSLATABLE")) {
						_raceUnTranslatableText = _raceUnTranslatableText.append(raceCodeValue).append(", ");
					}
				}
			}

		}

		int resultCount = hl7DocumentDAO.insertHL7FromRAWToSTD(stdHL7DocinsertVo);
		if (resultCount > 0) {
			if (resultSet.getInt("CURRENT_PROCESS_STATUS") == 1) {
				commonDocumentService.updateRawDocumentStatus(document_id, "RAW_HL7_DOC", 5);
			} else if (resultSet.getInt("CURRENT_PROCESS_STATUS") == 4) {
				commonDocumentService.updateRawDocumentStatus(document_id, "RAW_HL7_DOC", 5);
			}
		} else if(resultCount == 0) {
			commonDocumentService.updateRawDocumentStatus(document_id, "RAW_HL7_DOC", 4);
		}

		return resultCount;
	}

	private String populateCountyValue(String _zip) {
		Map<String, String> zipCodeValues = commonService.getCountyNameByZip(_zip);
		String _county = null;
		String _state = null;
		if (zipCodeValues != null) {
			_county = zipCodeValues.get("COUNTY_FIPS_CD");
			_state = zipCodeValues.get("STATE_CD");
		}

		if (CommonUtil.isNullOrEmpty(_state)) {
			_county = "999";
		} else if (!"NY".equals(_state)) {
			_county = "998";
		}

		return _county;
	}

	@Transactional
	public void writeRawHL7Doc(RawHL7DocVO hl7VO) {
		int docId = hl7VO.getDocumentId();
		hl7DocumentDAO.insertRawHL7DocData(hl7VO);
		List<RawHL7ObrVO> theLabTestVOList = hl7VO.getTheOBRList();
		int LabTestVOListLength = theLabTestVOList.size();
		if (theLabTestVOList != null && theLabTestVOList.size() > 0) {
			List<Integer> LabTestSequences = hl7DocumentDAO.getHL7LabTestSequences(LabTestVOListLength);
			List<RawHL7ObxVO> completeLabResultList = new ArrayList<>();
			for (int i = 0; i < LabTestVOListLength; i++) {
				int LabTestDocId = LabTestSequences.get(i);
				RawHL7ObrVO rawHL7ObrVO = theLabTestVOList.get(i);
				rawHL7ObrVO.setHl7labTestId(LabTestDocId);
				List<RawHL7ObxVO> oneLabResultList = rawHL7ObrVO.getTheOBXList();
				if (oneLabResultList != null && oneLabResultList.size() > 0) {
					for (RawHL7ObxVO theLabResultVO : oneLabResultList) {
						theLabResultVO.setHl7labTestId(LabTestDocId);
					}
					completeLabResultList.addAll(oneLabResultList);
				}
			}
			Map<String, Object> inputParameterMapRawHl7LabTest = new HashMap<>();
			inputParameterMapRawHl7LabTest.put("hl7DocId", docId);
			inputParameterMapRawHl7LabTest.put("hl7LabTestList", theLabTestVOList);
			hl7DocumentDAO.insertRawHL7OBR(inputParameterMapRawHl7LabTest);
			if (completeLabResultList != null && completeLabResultList.size() > 0) {
				Map<String, Object> labResultMap = new HashMap<>();
				labResultMap.put("hl7DocId", docId);
				labResultMap.put("hl7LabResultList", completeLabResultList);
				hl7DocumentDAO.insertRawHL7OBX(labResultMap);
			}
		}
		List<RawHL7Dg1VO> theHL7Dg1List = hl7VO.getTheDG1List();
		if (theHL7Dg1List != null && theHL7Dg1List.size() > 0) {
			Map<String, Object> inputParameterMapAddress = new HashMap<>();
			inputParameterMapAddress.put("hl7DocId", docId);
			inputParameterMapAddress.put("hl7DG1List", theHL7Dg1List);
			hl7DocumentDAO.insertDG1List(inputParameterMapAddress);
		}
		List<RawHL7INSVO> theHL7INSList = hl7VO.getTheINSList();
		if (theHL7INSList != null && theHL7INSList.size() > 0) {
			//filtering duplicates rows from arrays
			@SuppressWarnings("unchecked")
			List<RawHL7INSVO> theDistinctHL7INSList = theHL7INSList.stream()
					  .filter(distinctByKeys(RawHL7INSVO:: getPlanId, RawHL7INSVO:: getCompanyId, RawHL7INSVO:: getCompanyName, RawHL7INSVO:: getCompanyAddress1, RawHL7INSVO:: getCompanyAddress2,
							  RawHL7INSVO:: getCompanyCity, RawHL7INSVO:: getCompanyState, RawHL7INSVO:: getCompanyZipCode, RawHL7INSVO:: getGroupName, RawHL7INSVO:: getPlanType,
							  RawHL7INSVO:: getInsuredFName, RawHL7INSVO:: getInsuredLName, RawHL7INSVO:: getInsuredDOBText, RawHL7INSVO:: getInsuredAddress1, RawHL7INSVO:: getInsuredAddress2,
							  RawHL7INSVO:: getInsuredCity, RawHL7INSVO:: getPolicyNumber, RawHL7INSVO:: getSsn, RawHL7INSVO:: getMedicaidFname, RawHL7INSVO:: getMedicaidLname,
							  RawHL7INSVO:: getMedicaidCaseNumber, RawHL7INSVO:: getInsuredPhoneNumber, RawHL7INSVO:: getPlanEffectiveDate, RawHL7INSVO:: getPlanExpirationDate))
					  .collect(Collectors.toList());
			
			Map<String, Object> inputParameterMapINSAddress = new HashMap<>();
			inputParameterMapINSAddress.put("hl7DocId", docId);
			inputParameterMapINSAddress.put("hl7INSList", theDistinctHL7INSList);
			hl7DocumentDAO.insertINSList(inputParameterMapINSAddress);
			//remove duplicates row in hl7 insurance table after insertion to maintain records as distinct.
			//hl7DocumentDAO.deleteDuplicatesRowsInRawHL7Insuarance();
		}
		List<RawHL7ORCVO> theHL7ORCList = hl7VO.getTheORCList();
		if (theHL7ORCList != null && theHL7ORCList.size() > 0) {
			Map<String, Object> inputParameterMapORC = new HashMap<>();
			inputParameterMapORC.put("hl7DocId", docId);
			inputParameterMapORC.put("hl7ORCList", theHL7ORCList);
			hl7DocumentDAO.insertORCList(inputParameterMapORC);
		}
	}
	
	//will make sure to check duplicates and remove them  taking fields properties combination
	@SuppressWarnings("unchecked") 
	private <T> Predicate<T> distinctByKeys(Function<? super T, ?>... keyExtractors) 
	  {
	    final Map<List<?>, Boolean> seen = new ConcurrentHashMap<>();
	     
	    return t -> 
	    {
	      final List<?> keys = Arrays.stream(keyExtractors)
	                  .map(ke -> ke.apply(t))
	                  .collect(Collectors.toList());
	       
	      return seen.putIfAbsent(keys, Boolean.TRUE) == null;
	    };
	  }

	public void deleteDuplicatesRowsInRawHL7Insuarance() {
		hl7DocumentDAO.deleteDuplicatesRowsInRawHL7Insuarance();
		log.error("Finished deleting the duplicates from Raw HL7 Insurance table");// to test how long it is taking
	}
	
	
	public void updateCurrentProcessStatusInRaw() {
		log.error("Before updating HL7 Current Process status By Match Code");
		hl7DocumentDAO.updateCurrentProcessStatusInRaw();
		log.error("After updating HL7 Current Process status By Match Code");
	}

	public void updateCPSinRAWfromNonMatchToMatchCCD() {
		// log.error("After updating All HL7 in RAW from 36 to 1 for all ccd that were
		// first non match and then became a match Current Process status By Match
		// Code");
		hl7DocumentDAO.updateCPSinRAWfromNonMatchToMatchCCD();
	}
	
	public void insertMPIPairsCheckingHL7andCCD() {
		hl7DocumentDAO.insertMPIPairsCheckingHL7andCCD();
	}
	
	public void updateDataSTDMatchorNonMatchtoCCD(Map<String, String> statusObject,
			Map<String, String> schedulerMetaData) {

		statusObject.put("Status", "Records could not rematched! Process Failed.");
		log.error("Calling stored procedure to rematch hl7 against ccd-");
		Map<String, Object> input = new HashMap<>();
		input.put("p_process_status", schedulerMetaData.get("CPS_HL7_CURSOR_TO_MATCH_AGAINST_CCD"));
		input.put("p_days", schedulerMetaData.get("PAST_DATE_TO_PICK_HL7_CURSOR"));
		List<Map<String, String>> recordsDetailsList = new ArrayList<>();
		Map<String, String> recordsDetails = new HashMap<>();

		List<JSONObject> countsBeforeProcessing = hl7DocumentDAO.getTotalCountForEachCPS(input);

		hl7DocumentDAO.updateDataSTDMatchorNonMatchtoCCD(input);

		List<JSONObject> countsAfterProcessing = hl7DocumentDAO.getTotalCountForEachCPS(input);

		String transactionMessage = (String) input.get("p_error_msg");
		String errCode = (String) input.get("p_code_out");

		if (errCode.equals("0")) {
			statusObject.put("Status", transactionMessage);

			for (int i = 0; i < countsBeforeProcessing.size(); i++) {

				recordsDetails = new HashMap<String, String>();

				String beforeCount = countsBeforeProcessing.get(i).get("COUNT").toString();

				String cps = countsBeforeProcessing.get(i).get("PROCESS_STATUS").toString();

				recordsDetails.put("Current Process Status", cps);
				recordsDetails.put("Before Processing Records", beforeCount);

				recordsDetailsList.add(recordsDetails);

			}

			for (int i = 0; i < countsAfterProcessing.size(); i++) {

				String afterCount = countsAfterProcessing.get(i).get("COUNT").toString();

				String cps = countsAfterProcessing.get(i).get("PROCESS_STATUS").toString();

				for (Map<String, String> res : recordsDetailsList) {

					if (cps.equals(res.get("Current Process Status"))) {
						res.put("After Processing Records", afterCount);
					}

				}

			}

		}
		log.error(transactionMessage);

		manualQuartzJobService.sendHl7CCDRematchingRecordsDetails(schedulerMetaData, statusObject, recordsDetailsList);
	}
	
	public void updateCPSinSTDNotMatchOnParameters() {
		hl7DocumentDAO.updateCPSinSTDNotMatchOnParameters();
	}
	
	public void updateSTDtoRAWStatusDocIdAndDeleteRecordsinStdHL7DOC() {
		
		hl7DocumentDAO.updateCPSAndDOCidinRAWHL7fromSTDHL7DOC();
		
		hl7DocumentDAO.deleteRowsFromSTDHL7AfterBackedUpToRawHL7();
		
	}
	

	public void writeRawHL7RecordsToStdLayer(List<JSONObject> schedulerStatusObject) {
		Connection conn = null;
		ResultSet rs = null;
		Statement st = null;
		String transactionStatus = CommonConstants.TRANSACTION_STATUS_SUCCESS;
		String transactionMessage = "Successfully stored all the Raw HL7 records in the Standard layer";
		JSONObject statusObject = new JSONObject();
		List<String> failedToValidateDocuments = new ArrayList<>();
		Set<String> raceCodeErrors = new HashSet<>();
		try {
			log.error("Before fetching the RAW HL7 Data");
			conn = jpaConnections.appConnection();
			StringBuilder sqlQuery = new StringBuilder("");
			
			sqlQuery.append("SELECT H.*,\r\n" + 
					"       chf.FACILITY_NAME,\r\n" + 
					"       chf.FACILITY_ADDRESS,\r\n" + 
					"       chf.FACILITY_CITY,\r\n" + 
					"       chf.FACILITY_STATE,\r\n" + 
					"       chf.FACILITY_ZIP,\r\n" + 
					"       R.COUNTY_FIPS_CD \r\n" + 
					"FROM RAW_HL7_DOC H, CODED_HIE_FACILITY chf, NYEHMS_COUNTY_REFERENCE R \r\n" + 
					"WHERE H.current_process_status IN  (1,4)\r\n" + 
					"AND H.MSHSENDINGFACILITYNAME = chf.FACILITY_CODE(+) \r\n" + 
					"AND UPPER (H.pidcity) = R.CITY(+) \r\n" + 
					"AND H.PIDZIPCODE = R.ZIP_CD(+) \r\n" + 
					"AND UPPER (H.PIDSTATE) = R.STATE_CD(+) \r\n" + 
					"AND R.NUM_CITY(+) = 1 ORDER BY H.DOCUMENT_ID  ");
			st = conn.createStatement();
			rs = st.executeQuery(sqlQuery.toString());
			int counter = 0;
			int raceErrorCounter = 0;
			while (rs.next()) {
				counter++;
				try {
					int insertStatus = writeRawHL7RecordDataToStdTable(rs);
					if (insertStatus == 4) {
						raceErrorCounter++;
						String pidRaceCodeRaw = rs.getString("PIDRACECODERAW");
						String pidRacedesc = rs.getString("RACECODEDESC");
						if (CommonUtil.isNotNullAndEmpty(pidRaceCodeRaw)) {
							pidRaceCodeRaw = pidRaceCodeRaw.toUpperCase().trim();
							int res = hitDocumentDAO.checkIfRaceExists(pidRaceCodeRaw);
							if (res == 0) {
								raceCodeErrors.add(pidRaceCodeRaw);
							}
						} else if (CommonUtil.isNotNullAndEmpty(pidRacedesc)) {
								pidRacedesc = pidRacedesc.toUpperCase().trim();
								int res = hitDocumentDAO.checkIfRaceExists(pidRacedesc);
								if (res == 0) {
									raceCodeErrors.add(pidRacedesc);
								}
						} 
						String pidEthnicityRaw = rs.getString("PIDETHNICGROUPCODE");
						if (CommonUtil.isNotNullAndEmpty(pidEthnicityRaw)) {
							pidEthnicityRaw = pidEthnicityRaw.toUpperCase().trim();
							int res = hitDocumentDAO.checkIfRaceExists(pidEthnicityRaw);
							if (res == 0) {
								raceCodeErrors.add(pidEthnicityRaw);
							}
						}
					}
				} catch (DataAccessException e) {
					String _exceptionMessage = " Exception Message for DOCUMENT_ID = " + rs.getInt("DOCUMENT_ID")
							+ " and UPHN_TRANX_IN_ID = " + rs.getInt("UPHN_TRANX_IN_ID") + e.getMessage()
							+ "<br/><br/>";
					failedToValidateDocuments.add(_exceptionMessage);
					log.error(_exceptionMessage);
				} catch (Exception e) {
					String _exceptionMessage = " Exception while storing the HL7 record or updating RAW_HL7_DOC:  "
							+ CommonUtil.getStackTrace(e).substring(0, 200) + "<br/><br/>";
					failedToValidateDocuments.add(_exceptionMessage);
					log.error(_exceptionMessage);
				}
			}
			if (counter == 0) {
				transactionStatus = CommonConstants.NO_DATA_RETRIEVED;
				transactionMessage = "No HL7's Data Retrieved to move from Raw layer to Standard layer";
				statusObject.put(CommonConstants.TRANSACTION_STATUS, transactionStatus);
				statusObject.put(CommonConstants.TRANSACTION_MESSAGE, transactionMessage);
				return;
			}

			if (raceCodeErrors != null && raceCodeErrors.size() > 0) {
				log.error("SIZE OF ENTRIES TO BE INSERTED : {}", raceCodeErrors.size());
				log.error("LIST OF RACE TO BE ADDED {}", raceCodeErrors.toString());
				hitCommonDocumentService.insertCodedRace(raceCodeErrors);
			}

			int failureCount = failedToValidateDocuments.size();
			int success = counter - failureCount - raceErrorCounter;
			if (raceErrorCounter > 0) {
				transactionMessage = "Number of HL7's failed while storing  in the Standard layer due to Race Map failure are = "
						+ raceErrorCounter + "<br>" + "<br>" + " Update Race/Ethncity value in CODED_HIT_RACE_DESC : "
						+ raceCodeErrors.toString();
				statusObject.put("RACE_CODE_ERRORS", transactionMessage);
			}

			if (success == counter) {
				transactionStatus = CommonConstants.TRANSACTION_STATUS_SUCCESS;
				transactionMessage = "All the Documents Successfully moved to Standard layer";
			} else if (failureCount + raceErrorCounter == counter) {
				transactionStatus = CommonConstants.TRANSACTION_STATUS_FAILURE;
				transactionMessage = "All the Documents failed while moving it to Standard layer";
			} else if (failureCount > 0 || raceErrorCounter > 0) {
				transactionStatus = CommonConstants.TRANSACTION_STATUS_SUCCESS;
				transactionMessage = "Some Documents failed while moving it to Standard layer";
			}
			statusObject.put(CommonConstants.TRANSACTION_STATUS, transactionStatus);
			statusObject.put(CommonConstants.TRANSACTION_MESSAGE, transactionMessage);
			statusObject.put("Total Documents", counter);
			statusObject.put("Total Documents Moved to STD table", success);
			statusObject.put("Total Documents failed ", failureCount + raceErrorCounter);
			statusObject.put("RACE Code Failed Count ", raceErrorCounter);

		} catch (Throwable t) {
			log.error(CommonUtil.getStackTrace(t).substring(0, 200));
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
				if (rs != null) {
					rs.close();
				}
				if (st != null) {
					st.close();
				}
			} catch (SQLException sqle) {
				log.error(CommonUtil.getStackTrace(sqle).substring(0, 200));
			}
		}
		schedulerStatusObject.add(statusObject);
	}

	public RawHL7DocVO getHL7Doc(int docId, BindingResult dummyBindingResult, UserObject userObject, String id) {
		RawHL7DocVO rawHL7DocVO = new RawHL7DocVO();
		StdHL7DocVO hl7PersonDocVO = null;
		StdHL7RaceVO stdHL7RaceDocVO = new StdHL7RaceVO();

		if (log.isDebugEnabled()) {
			log.debug(" fetching for hl7 docId : {}", docId);
		}
		try {
			hl7PersonDocVO = hl7DocumentDAO.getStdHL7Doc(docId);
			if (hl7PersonDocVO != null) {
				rawHL7DocVO.setPersonStdData(hl7PersonDocVO);

				stdHL7RaceDocVO = hl7DocumentDAO.getHL7RaceVO(docId);
				if (stdHL7RaceDocVO != null) {
					String raceOtherText = stdHL7RaceDocVO.getRaceOtherText();
					if (raceOtherText != null) {
						String distinctraceOtherText = deDup(raceOtherText);
						if (!distinctraceOtherText.isEmpty()) {
							stdHL7RaceDocVO.setRaceOtherText(distinctraceOtherText);
						}
					}
					rawHL7DocVO.setPersonRaceData(stdHL7RaceDocVO);
				}

				/*
				 * List<RawHL7ObrVO> theLabTestList = new ArrayList<RawHL7ObrVO>();
				 * theLabTestList =
				 * hl7DocumentDAO.getHL7LabTestList(hl7PersonDocVO.getDocumentId()); if
				 * (theLabTestList != null && theLabTestList.size() > 0) {
				 * rawHL7DocVO.setTheOBRList(theLabTestList);
				 * System.out.println("Number of Entries Retrived are : " +
				 * theLabTestList.size()); }
				 */
			}
		} catch (DataAccessException e) {
			log.error(e.getMessage());
			log.error("Failed to fetch the HL7 Document : {}", docId);
		} catch (Exception e) {
			log.error(e.getMessage());
			log.error("Failed to fetch the HL7 Document : {}", docId);
		}
		return rawHL7DocVO;
	}

	public RawHL7DocVO getFilteredHL7Doc(int docId, String startDate, String endDate) {
		RawHL7DocVO rawHL7DocVO = new RawHL7DocVO();
		StdHL7DocVO hl7PersonDocVO = null;
		StdHL7RaceVO stdHL7RaceDocVO = new StdHL7RaceVO();
		if (log.isDebugEnabled()) {
			log.debug(" fetching for hl7 docId : {}", docId);
		}

		try {
			Map<Object, Object> inputOutputParameterMap = new HashMap<>();
			inputOutputParameterMap.put("docId", docId);
			inputOutputParameterMap.put("startDate", startDate);
			inputOutputParameterMap.put("endDate", endDate);
			hl7PersonDocVO = hl7DocumentDAO.getFilteredHL7Doc(inputOutputParameterMap);
			if (hl7PersonDocVO != null) {
				rawHL7DocVO.setPersonStdData(hl7PersonDocVO);
				stdHL7RaceDocVO = hl7DocumentDAO.getFilteredRaceVO(inputOutputParameterMap);
				if (stdHL7RaceDocVO != null) {
					String raceOtherText = stdHL7RaceDocVO.getRaceOtherText();
					if (raceOtherText != null) {
						String distinctraceOtherText = deDup(raceOtherText);
						if (!distinctraceOtherText.isEmpty()) {
							stdHL7RaceDocVO.setRaceOtherText(distinctraceOtherText);
						}
					}
					rawHL7DocVO.setPersonRaceData(stdHL7RaceDocVO);
				}
				// List<RawHL7AddressVO> theHL7AddressList = new ArrayList<RawHL7AddressVO>();
				// theHL7AddressList =
				// theINSList =
			}
		} catch (DataAccessException e) {
			log.error(e.getMessage());
			log.error("Failed to fetch the HL7 Document : {}", docId);
		} catch (Exception e) {
			log.error(e.getMessage());
			log.error("Failed to fetch the HL7 Document : {}", docId);
		}
		return rawHL7DocVO;
	}

	public List<Map<Object, Object>> getHL7Diagnosis(int docId, String startDate, String endDate) {
		Map<Object, Object> inputOutputParameterMap = new HashMap<>();
		inputOutputParameterMap.put("docId", docId);
		inputOutputParameterMap.put("startDate", startDate);
		inputOutputParameterMap.put("endDate", endDate);
		return hl7DocumentDAO.getHL7Diagnosis(inputOutputParameterMap);
	}

	public List<Map<Object, Object>> getHL7Medication(int docId, String startDate, String endDate) {
		Map<Object, Object> inputOutputParameterMap = new HashMap<>();
		inputOutputParameterMap.put("docId", docId);
		inputOutputParameterMap.put("startDate", startDate);
		inputOutputParameterMap.put("endDate", endDate);
		return hl7DocumentDAO.getHL7Medications(inputOutputParameterMap);
	}

	public List<Map<Object, Object>> getHL7Encounter(int docId, String startDate, String endDate) {
		List<Map<Object, Object>> abc;
		Map<Object, Object> inputOutputParameterMap = new HashMap<>();
		inputOutputParameterMap.put("docId", docId);
		inputOutputParameterMap.put("startDate", startDate);
		inputOutputParameterMap.put("endDate", endDate);
		return hl7DocumentDAO.getHL7Encounter(inputOutputParameterMap);
	}

	public List<PersonFieldVO> getHL7DemogrphicFieldMultiValues(int docId, String startDate, String endDate,
			String colsName) {
		List<PersonFieldVO> list = new ArrayList<>();
		Map<Object, Object> inputOutputParameterMap = new HashMap<>();
		inputOutputParameterMap.put("docId", docId);
		inputOutputParameterMap.put("startDate", startDate);
		inputOutputParameterMap.put("endDate", endDate);
		inputOutputParameterMap.put("colsName", colsName);
		if (colsName.equalsIgnoreCase("DATE_OF_BIRTH")) {
			list = hl7DocumentDAO.getHL7DOBDemogrphicFieldMultiValues(inputOutputParameterMap);
		} else {
			list = hl7DocumentDAO.getHL7DemogrphicFieldMultiValues(inputOutputParameterMap);
		}
		return list;
	}

	// Code Added on 01-FEB-18
	public void updateCurrentProcessStatusInRawForMPIChange() {
		log.error("Before updating HL7 Current Process For MPI_CHANGE File");
		hl7DocumentDAO.updateCurrentProcessStatusInRawForMPIChange();
		log.error("After updating HL7 Current Process status FOR MPI_CHANGE File");
	}

	public void writeRawHL7MPIChangeRecordsToStdLayer(List<JSONObject> schedulerStatusObject) {
		Connection conn = null;
		ResultSet rs = null;
		Statement st = null;
		String transactionStatus = CommonConstants.TRANSACTION_STATUS_SUCCESS;
		String transactionMessage = "Successfully stored all the Raw HL7 records With MPI CHANGE entries in the Standard layer";
		JSONObject statusObject = new JSONObject();
		List<String> failedToValidateDocuments = new ArrayList<>();
		Set<String> raceCodeErrors = new HashSet<>();
		try {
			log.error("Before fetching the RAW HL7 Data");
			conn = jpaConnections.appConnection();
			StringBuilder sqlQuery = new StringBuilder("");
			sqlQuery.append("WITH data AS ")
					.append("(select distinct old_mpi not_in_std_mpi, current_mpi in_std_mpi from mpi_change ")
					.append("where exists (select 1 from raw_hl7_doc h where h.pidMPI=mpi_change.old_mpi) ")
					.append("and not exists (select 1 from std_ccd_doc s where s.MPI=mpi_change.old_mpi ) ")
					.append(" and exists (select 1 from std_ccd_doc s2 where s2.MPI=mpi_change.current_mpi and s2.matched_stateno is not null) ")
					.append("union ")
					.append("select distinct current_mpi not_in_std_mpi, old_mpi in_std_mpi from mpi_change ")
					.append("where exists (select 1 from raw_hl7_doc h where h.pidMPI=mpi_change.current_mpi) ")
					.append("and not exists (select 1 from std_ccd_doc s where s.MPI=mpi_change.current_mpi) ")
					.append("and exists (select 1 from std_ccd_doc s2 where s2.MPI=mpi_change.old_mpi and s2.matched_stateno is not null) ")
					.append(")select c.*, a.document_id CCD_MATCH_DOC_ID, a.matched_stateno CCD_MATCH_STATENO ")
					.append(",chf.FACILITY_NAME, chf.FACILITY_ADDRESS, chf.FACILITY_CITY, ")
					.append("chf.FACILITY_STATE, chf.FACILITY_ZIP, R.COUNTY_FIPS_CD ")
					.append("from std_ccd_doc a, data b, raw_hl7_doc c, CODED_HIE_FACILITY chf , NYEHMS_COUNTY_REFERENCE R  ")
					.append("WHERE c.MSHSENDINGFACILITYNAME = chf.FACILITY_CODE (+) ")
					.append("AND UPPER(c.pidcity) = R.CITY (+) AND    c.PIDZIPCODE = R.ZIP_CD (+) ")
					.append("AND UPPER(c.PIDSTATE) = R.STATE_CD (+) AND  r.NUM_CITY(+) =1 ")
					.append("AND a.mpi = b.in_std_mpi ").append("AND b.not_in_std_mpi = c.pidmpi ")
					.append(" AND c.current_process_status = 37");
			st = conn.createStatement();
			rs = st.executeQuery(sqlQuery.toString());
			int counter = 0;
			int raceErrorCounter = 0;
			while (rs.next()) {
				counter++;
				try {
					int insertStatus = writeRawHL7RecordDataToStdTable(rs);
					if (insertStatus == 4) {
						raceErrorCounter++;
						String pidRaceCodeRaw = rs.getString("PIDRACECODERAW");
						if (CommonUtil.isNotNullAndEmpty(pidRaceCodeRaw)) {
							pidRaceCodeRaw = pidRaceCodeRaw.toUpperCase().trim();
							int res = hitDocumentDAO.checkIfRaceExists(pidRaceCodeRaw);
							if (res == 0) {
								raceCodeErrors.add(pidRaceCodeRaw);
							}
						}

						String pidEthnicityRaw = rs.getString("PIDETHNICGROUPCODE");
						if (CommonUtil.isNotNullAndEmpty(pidEthnicityRaw)) {
							pidEthnicityRaw = pidEthnicityRaw.toUpperCase().trim();
							int res = hitDocumentDAO.checkIfRaceExists(pidEthnicityRaw);
							if (res == 0) {
								raceCodeErrors.add(pidEthnicityRaw);
							}
						}
					}
				} catch (DataAccessException e) {
					String _exceptionMessage = " Exception Message for DOCUMENT_ID = " + rs.getInt("DOCUMENT_ID")
							+ " and UPHN_TRANX_IN_ID = " + rs.getInt("UPHN_TRANX_IN_ID") + e.getMessage()
							+ "<br/><br/>";
					failedToValidateDocuments.add(_exceptionMessage);
					log.error(_exceptionMessage);
				} catch (Exception e) {
					String _exceptionMessage = " Exception while storing the HL7 record for MPI_CHANGE PROCESS or updating RAW_HL7_DOC:  "
							+ CommonUtil.getStackTrace(e).substring(0, 200) + "<br/><br/>";
					failedToValidateDocuments.add(_exceptionMessage);
					log.error(_exceptionMessage);
				} 
			}
			if (counter == 0) {
				// statusObject.put(CommonConstants.TRANSACTION_STATUS,
				// transactionStatus);
				// statusObject.put(CommonConstants.TRANSACTION_MESSAGE,
				// transactionMessage);
				return;
			}

			if (raceCodeErrors != null && raceCodeErrors.size() > 0) {
				log.error("SIZE OF ENTRIES TO BE INSERTED for MPI_CHANGE PROCESS: {}", raceCodeErrors.size());
				log.error("LIST OF RACE TO BE ADDED for MPI_CHANGE PROCESS {}", raceCodeErrors.toString());
				hitCommonDocumentService.insertCodedRace(raceCodeErrors);
			}

			int failureCount = failedToValidateDocuments.size();
			int success = counter - failureCount - raceErrorCounter;
			if (raceErrorCounter > 0) {
				// transactionMessage = "Number of HL7's failed while storing in
				// the Standard layer due to Race Map failure are = "
				// + raceErrorCounter + "<br>" + "<br>" + " New Value added to
				// CODED_HIT_RACE_DESC are : "
			}

			if (success == counter) {
				if (success > 0) {
					// layer with MPI Change are ="
					// + success;
				}
			} else if (failureCount + raceErrorCounter == counter) {
				// transactionStatus =
			} else if (failureCount > 0 || raceErrorCounter > 0) {
				// transactionStatus =
			}
			// statusObject.put(CommonConstants.TRANSACTION_STATUS,
			// transactionStatus);
			// statusObject.put(CommonConstants.TRANSACTION_MESSAGE,
			// statusObject.put("Total Documents failed ", failureCount +

		} catch (Throwable t) {
			log.error(CommonUtil.getStackTrace(t).substring(0, 200));
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
				if (rs != null) {
					rs.close();
				}
				if (st != null) {
					st.close();
				}
			} catch (SQLException sqle) {
				log.error(CommonUtil.getStackTrace(sqle).substring(0, 200));
			}
		}
		// schedulerStatusObject.add(statusObject);

	}

	public List<RawHL7ObrVO> getHL7LabData(int documentID, String startDate, String endDate) {
		Map<Object, Object> inputOutputParameterMap = new HashMap<>();
		inputOutputParameterMap.put("docId", documentID);
		inputOutputParameterMap.put("startDate", startDate);
		inputOutputParameterMap.put("endDate", endDate);
		List<RawHL7ObrVO> theLabTestList = new ArrayList<>();
		theLabTestList = hl7DocumentDAO.getHL7LabTestList(inputOutputParameterMap);
		if (theLabTestList != null && theLabTestList.size() > 0) {
			for (int i = 0; i < theLabTestList.size(); i++) {
				RawHL7ObrVO rawHL7ObrVO = new RawHL7ObrVO();
				rawHL7ObrVO = theLabTestList.get(i);
				if (rawHL7ObrVO != null) {
					int labtestId = rawHL7ObrVO.getHl7labTestId();
					if (labtestId > 0) {
						List<RawHL7ObxVO> lstLabResultList = new ArrayList<>();
						lstLabResultList = hl7DocumentDAO.getHL7LabResultList(labtestId);
						if (lstLabResultList != null && lstLabResultList.size() > 0) {
							rawHL7ObrVO.setTheOBXList(lstLabResultList);
						}
					}
				}
			}

		}
		return theLabTestList;
	}

	public String deDup(String s) {
		return new LinkedHashSet<String>(Arrays.asList(s.split(", "))).toString().replaceAll("(^\\[|\\]$)", "")
				.replace(", ", ",");
	}

	public List<RawHL7AddressVO> getHL7AddressList(int documentID, String startDate, String endDate) {
		Map<String, Object> inputOutputParameterMap = new HashMap<>();
		inputOutputParameterMap.put("docId", documentID);
		inputOutputParameterMap.put("startDate", startDate);
		inputOutputParameterMap.put("endDate", endDate);
		return hl7DocumentDAO.getFilteredHL7AddressList(inputOutputParameterMap);
	}

	public List<RawHL7INSVO> getHL7InsuranceList(int documentID, String startDate, String endDate) {
		Map<String, Object> inputOutputParameterMap = new HashMap<>();
		inputOutputParameterMap.put("docId", documentID);
		inputOutputParameterMap.put("startDate", startDate);
		inputOutputParameterMap.put("endDate", endDate);
		return hl7DocumentDAO.getFilteredHL7InsuranceList(inputOutputParameterMap);

	}

	

	
}