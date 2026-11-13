package gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.ccd;

import java.util.ArrayList;
import java.util.List;


public class RawCCDDocVO {
	
	private int documentId;	
	private RawCCDPersonDocVO thePerson;
	private StdCCDDocVo personStdData;
	private List<RawCCDPersonAddressVO> theAddressList = new ArrayList<>();
	private List<RawCCDProviderVO> theProviderList = new ArrayList<>();
	private List<RawCCDPayerVO> thePayerList = new ArrayList<>();
	private List<RawCCDEncounterVO> theEncounterList = new ArrayList<>();
	private List<RawCCDMedVO> theMedList = new ArrayList<>();
	//private List<RawCCDLabVO> theLabList = new ArrayList<RawCCDLabVO>();
	private List<RawCCDLabTestVO> theLabTestList = new ArrayList<>();
	private List<RawCCDProblemVO> theProblemList = new ArrayList<>();
	
	private long maxUTIICHECKEDRESULTKEY;
	private long minUTIICHECKEDRESULTKEY;
	private String hieName;
	private String payloadName;

	public int getDocumentId() {
		return documentId;
	}

	public void setDocumentId(int documentId) {
		this.documentId = documentId;
	}

	public RawCCDPersonDocVO getThePerson() {
		return thePerson;
	}

	public void setThePerson(RawCCDPersonDocVO thePerson) {
		this.thePerson = thePerson;
	}

	public StdCCDDocVo getPersonStdData() {
		return personStdData;
	}

	public void setPersonStdData(StdCCDDocVo personStdData) {
		this.personStdData = personStdData;
	}

	public List<RawCCDPersonAddressVO> getTheAddressList() {
		return theAddressList;
	}

	public void setTheAddressList(List<RawCCDPersonAddressVO> theAddressList) {
		this.theAddressList = theAddressList;
	}
	
	public void addAddress(RawCCDPersonAddressVO theAddressVO) {
		theAddressList.add(theAddressVO);
	}

	public List<RawCCDProviderVO> getTheProviderList() {
		return theProviderList;
	}

	public void setTheProviderList(List<RawCCDProviderVO> theProviderList) {
		this.theProviderList = theProviderList;
	}
	
	public void addProvider(RawCCDProviderVO theProvider) {
		theProviderList.add(theProvider);
	}

	public List<RawCCDPayerVO> getThePayerList() {
		return thePayerList;
	}

	public void setThePayerList(List<RawCCDPayerVO> thePayerList) {
		this.thePayerList = thePayerList;
	}
	
	public void addPayer(RawCCDPayerVO thePayer) {
		thePayerList.add(thePayer);
	}

	public List<RawCCDEncounterVO> getTheEncounterList() {
		return theEncounterList;
	}

	public void setTheEncounterList(List<RawCCDEncounterVO> theEncounterList) {
		this.theEncounterList = theEncounterList;
	}

	public void addEncounter(RawCCDEncounterVO theEncounter) {
		theEncounterList.add(theEncounter);
	}
	public List<RawCCDMedVO> getTheMedList() {
		return theMedList;
	}

	public void setTheMedList(List<RawCCDMedVO> theMedList) {
		this.theMedList = theMedList;
	}
	
	public void addMed(RawCCDMedVO theMed) {
		theMedList.add(theMed);
	}

	public List<RawCCDLabTestVO> getTheLabTestList() {
		return theLabTestList;
	}

	public void setTheLabList(List<RawCCDLabTestVO> theLabTestList) {
		this.theLabTestList = theLabTestList;
	}

	public List<RawCCDProblemVO> getTheProblemList() {
		return theProblemList;
	}

	public void setTheProblemList(List<RawCCDProblemVO> theProblemList) {
		this.theProblemList = theProblemList;
	}

	public void addLab(RawCCDLabTestVO theLabTest) {
		theLabTestList.add(theLabTest);
	}
	
	public long getMaxUTIICHECKEDRESULTKEY() {
		return maxUTIICHECKEDRESULTKEY;
	}

	public void setMaxUTIICHECKEDRESULTKEY(long maxUTIICHECKEDRESULTKEY) {
		this.maxUTIICHECKEDRESULTKEY = maxUTIICHECKEDRESULTKEY;
	}

	public long getMinUTIICHECKEDRESULTKEY() {
		return minUTIICHECKEDRESULTKEY;
	}

	public void setMinUTIICHECKEDRESULTKEY(long minUTIICHECKEDRESULTKEY) {
		this.minUTIICHECKEDRESULTKEY = minUTIICHECKEDRESULTKEY;
	}
	
	public String getHieName() {
		return hieName;
	}

	public void setHieName(String hieName) {
		this.hieName = hieName;
	}

	public String getPayloadName() {
		return payloadName;
	}

	public void setPayloadName(String payloadName) {
		this.payloadName = payloadName;
	}
}
