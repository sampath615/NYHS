package gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.hl7;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class RawHL7DocVO {

	public RawHL7DocVO() {
		// TODO Auto-generated constructor stub
	}

	private long uphnId;
	private int documentId;
	private StdHL7DocVO personStdData;
	private Date uphnReceivedTime;
	private int uphnPayloadSize;
	private int currentProcessStatus;
	private String nycShipDate;
	private String eventDateTimeText;
	private RawHL7MshVO theMshVO;
	private RawHL7PidVO thePidVO;
	private RawHL7Pv1VO thePv1VO;
	// Only one of the two below can be non-null
	private RawHL7ADTVO theADTVO;
	private RawHL7ORUVO theORUVO;
	private RawHL7ObrVO theOBRVO;
	private String hieName;
	private String mshMessageText;
	private String mshMessageType;
	private String mshMessageClass;
	private List<RawHL7Dg1VO> theDG1List = new ArrayList<RawHL7Dg1VO>();
	private List<RawHL7ObrVO> theOBRList = new ArrayList<RawHL7ObrVO>();
	private String evnEventType;
	private String evnEventDate;
	private String segmentProfile;
	private List<RawHL7INSVO> theINSList = new ArrayList<RawHL7INSVO>();
	private List<RawHL7ORCVO> theORCList = new ArrayList<RawHL7ORCVO>();
	private List<RawHL7AddressVO> theAddressList = new ArrayList<RawHL7AddressVO>();
	private List<RawHL7MedicationVO> theMedicationList = new ArrayList<RawHL7MedicationVO>();
	private StdHL7RaceVO personRaceData;

	private int ccdMatchDocId;

	public long getUphnId() {
		return uphnId;
	}

	public void setUphnId(long uphnId) {
		this.uphnId = uphnId;
	}

	public int getDocumentId() {
		return documentId;
	}

	public void setDocumentId(int documentId) {
		this.documentId = documentId;
	}

	public StdHL7DocVO getPersonStdData() {
		return personStdData;
	}

	public void setPersonStdData(StdHL7DocVO personStdData) {
		this.personStdData = personStdData;
	}

	public StdHL7RaceVO getPersonRaceData() {
		return personRaceData;
	}

	public void setPersonRaceData(StdHL7RaceVO personRaceData) {
		this.personRaceData = personRaceData;
	}

	public Date getUphnReceivedTime() {
		return uphnReceivedTime;
	}

	public void setUphnReceivedTime(Date uphnReceivedTime) {
		this.uphnReceivedTime = uphnReceivedTime;
	}

	public int getUphnPayloadSize() {
		return uphnPayloadSize;
	}

	public void setUphnPayloadSize(int uphnPayloadSize) {
		this.uphnPayloadSize = uphnPayloadSize;
	}

	public int getCurrentProcessStatus() {
		return currentProcessStatus;
	}

	public void setCurrentProcessStatus(int currentProcessStatus) {
		this.currentProcessStatus = currentProcessStatus;
	}

	public String getNycShipDate() {
		return nycShipDate;
	}

	public void setNycShipDate(String nycShipDate) {
		this.nycShipDate = nycShipDate;
	}

	public String getEventDateTimeText() {
		return eventDateTimeText;
	}

	public void setEventDateTimeText(String eventDateTimeText) {
		this.eventDateTimeText = eventDateTimeText;
	}

	public RawHL7MshVO getTheMshVO() {
		return theMshVO;
	}

	public void setTheMshVO(RawHL7MshVO theMshVO) {
		this.theMshVO = theMshVO;
	}

	public RawHL7PidVO getThePidVO() {
		return thePidVO;
	}

	public void setThePidVO(RawHL7PidVO thePidVO) {
		this.thePidVO = thePidVO;
	}

	public RawHL7Pv1VO getThePv1VO() {
		return thePv1VO;
	}

	public void setThePv1VO(RawHL7Pv1VO thePv1VO) {
		this.thePv1VO = thePv1VO;
	}

	public RawHL7ADTVO getTheADTVO() {
		return theADTVO;
	}

	public void setTheADTVO(RawHL7ADTVO theADTVO) {
		this.theADTVO = theADTVO;
	}

	public RawHL7ORUVO getTheORUVO() {
		return theORUVO;
	}

	public void setTheORUVO(RawHL7ORUVO theORUVO) {
		this.theORUVO = theORUVO;
	}

	public RawHL7ObrVO getTheOBRVO() {
		return theOBRVO;
	}

	public void setTheOBRVO(RawHL7ObrVO theOBRVO) {
		this.theOBRVO = theOBRVO;
	}

	public String getHieName() {
		return hieName;
	}

	public void setHieName(String hieName) {
		this.hieName = hieName;
	}

	public String getMshMessageText() {
		return mshMessageText;
	}

	public void setMshMessageText(String mshMessageText) {
		this.mshMessageText = mshMessageText;
	}

	public String getMshMessageType() {
		return mshMessageType;
	}

	public void setMshMessageType(String mshMessageType) {
		this.mshMessageType = mshMessageType;
	}

	public String getMshMessageClass() {
		return mshMessageClass;
	}

	public void setMshMessageClass(String mshMessageClass) {
		this.mshMessageClass = mshMessageClass;
	}

	public List<RawHL7Dg1VO> getTheDG1List() {
		return theDG1List;
	}

	public void setTheDG1List(List<RawHL7Dg1VO> theDG1List) {
		this.theDG1List = theDG1List;
	}

	public List<RawHL7ObrVO> getTheOBRList() {
		return theOBRList;
	}

	public void setTheOBRList(List<RawHL7ObrVO> theOBRList) {
		this.theOBRList = theOBRList;
	}

	public String getEvnEventType() {
		return evnEventType;
	}

	public void setEvnEventType(String evnEventType) {
		this.evnEventType = evnEventType;
	}

	public String getEvnEventDate() {
		return evnEventDate;
	}

	public void setEvnEventDate(String evnEventDate) {
		this.evnEventDate = evnEventDate;
	}

	public String getSegmentProfile() {
		return segmentProfile;
	}

	public void setSegmentProfile(String segmentProfile) {
		this.segmentProfile = segmentProfile;
	}

	public List<RawHL7INSVO> getTheINSList() {
		return theINSList;
	}

	public void setTheINSList(List<RawHL7INSVO> theINSList) {
		this.theINSList = theINSList;
	}

	public List<RawHL7ORCVO> getTheORCList() {
		return theORCList;
	}

	public void setTheORCList(List<RawHL7ORCVO> theORCList) {
		this.theORCList = theORCList;
	}

	public List<RawHL7AddressVO> getTheAddressList() {
		return theAddressList;
	}

	public void setTheAddressList(List<RawHL7AddressVO> theAddressList) {
		this.theAddressList = theAddressList;
	}

	public int getCcdMatchDocId() {
		return ccdMatchDocId;
	}

	public void setCcdMatchDocId(int ccdMatchDocId) {
		this.ccdMatchDocId = ccdMatchDocId;
	}

	public List<RawHL7MedicationVO> getTheMedicationList() {
		return theMedicationList;
	}

	public void setTheMedicationList(List<RawHL7MedicationVO> theMedicationList) {
		this.theMedicationList = theMedicationList;
	}

	public String toString() {
		return ReflectionToStringBuilder.toString(this) + "\n*************************************";
	}

}