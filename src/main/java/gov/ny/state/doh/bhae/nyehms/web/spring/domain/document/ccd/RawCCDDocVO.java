package gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.ccd;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;


public class RawCCDDocVO {
	
	private int documentId;	
	private RawCCDPersonDocVO thePerson;
	private StdCCDDocVo personStdData;
	private List<RawCCDPersonAddressVO> theAddressList = new ArrayList<RawCCDPersonAddressVO>();
	private List<RawCCDProviderVO> theProviderList = new ArrayList<RawCCDProviderVO>();
	private List<RawCCDPayerVO> thePayerList = new ArrayList<RawCCDPayerVO>();
	private List<RawCCDEncounterVO> theEncounterList = new ArrayList<RawCCDEncounterVO>();
	private List<RawCCDMedVO> theMedList = new ArrayList<RawCCDMedVO>();
	//private List<RawCCDLabVO> theLabList = new ArrayList<RawCCDLabVO>();
	private List<RawCCDLabTestVO> theLabTestList = new ArrayList<RawCCDLabTestVO>();
	private List<RawCCDProblemVO> theProblemList = new ArrayList<RawCCDProblemVO>();
	
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
	
	
	

	public static void storeCCD (RawCCDDocVO theCCD, int ccddocId,
			SqlSession theSession) {
		RawCCDPersonDocVO thePerson = theCCD.getThePerson();
		thePerson.setDOCUMENT_ID(ccddocId);
		theSession.insert("insertRawCcdDocData", thePerson);
		//Get the addresses
		List<RawCCDPersonAddressVO> theAddresses = theCCD.getTheAddressList();
		for(int i = 0; i < theAddresses.size(); i++) {
			RawCCDPersonAddressVO theAddressVO = theAddresses.get(i);
			theAddressVO.setCcdDocID(ccddocId);
			theSession.insert("insertRawCcdPersonAddress", theAddressVO);
		}
		//Get the providers
		List<RawCCDProviderVO> theProviders = theCCD.getTheProviderList();
		System.out.println("Number of providers: " + theProviders.size());
		for (int i = 0; i < theProviders.size(); i++) {
			RawCCDProviderVO theProvider = theProviders.get(i);
			theProvider.setCcdDocID(ccddocId);
			theSession.insert("insertRawCcdProvider", theProvider);
		}
		//Get the payers
		List<RawCCDPayerVO> thePayers = theCCD.getThePayerList();
		System.out.println("Number of payers: " + thePayers.size());
		for (int i = 0; i < thePayers.size(); i++) {
			RawCCDPayerVO thePayer = thePayers.get(i);
			thePayer.setCcdDocID(ccddocId);
			theSession.insert("insertRawCcdPayer", thePayer);
		}
		//Get the labs
		List<RawCCDLabTestVO> theLabTests = theCCD.getTheLabTestList();
		System.out.println("Number of labs: " + theLabTests.size());
		for (int i = 0; i < theLabTests.size(); i++) {
			RawCCDLabTestVO theLabTest = theLabTests.get(i);
			theLabTest.setCcdDocID(ccddocId);
			theSession.insert("insertRawCcdLabTest", theLabTest);
		}
		//Get the encounters
		List<RawCCDEncounterVO> theEncounters = theCCD.getTheEncounterList();
		System.out.println("Number of encounters: " + theEncounters.size());
		for (int i = 0; i < theEncounters.size(); i++) {
			RawCCDEncounterVO theEncounter = theEncounters.get(i);
			theEncounter.setCcdDocID(ccddocId);
			theSession.insert("insertRawCcdEncounter", theEncounter);
		}
		//Get the meds
		List<RawCCDMedVO> theMeds = theCCD.getTheMedList();
		System.out.println("Number of meds " + theMeds.size());
		for (int i = 0; i < theMeds.size(); i++) {
			RawCCDMedVO theMed = theMeds.get(i);
			theMed.setCcdDocID(ccddocId);
			theSession.insert("insertRawCcdMed", theMed);
		}
		theSession.commit();
	}

/*
	public String toString() {
		StringBuffer sb = new StringBuffer();
	sb.append("*** The DOCUMENT_ID ***\n" + documentId);
	sb.append("*** THE PERSON ***\n");
	sb.append(thePerson.toString() + "\n\n");

	sb.append("*** ADDRESSES(" + theAddressList.size() + ") ***" + "\n");
	for (int i = 0; i < theAddressList.size(); i++) {
		sb.append("*** Address " + (i+1) + " ***\n");
		sb.append(theAddressList.get(i).toString());
	}
	sb.append("\n*** PROVIDERS(" + theProviderList.size() + ") ***" + "\n");
	for (int i = 0; i < theProviderList.size(); i++) {
		sb.append("*** Provider " + (i+1) + " ***\n");
		sb.append(theProviderList.get(i).toString());
	}
	sb.append("\n*** PAYERS(" + thePayerList.size() + ") ***" + "\n");
	for (int i = 0; i < thePayerList.size(); i++) {
		sb.append("*** Payer " + (i+1) + " ***\n");
		sb.append(thePayerList.get(i).toString() +" ***\n");
	}
	sb.append("\n*** ENCOUNTERS(" + theEncounterList.size() + ") ***\n");
	for (int i = 0; i < theEncounterList.size(); i++) {
		sb.append("*** Encounter " + (i+1) + " ***\n");
		sb.append(theEncounterList.get(i).toString());
	}
	sb.append("\n*** MEDS(" + theMedList.size() + ") ***\n");
	for (int i = 0; i < theMedList.size(); i++) {
		sb.append("*** Medication " + (i+1) + " ***\n");
		sb.append(theMedList.get(i).toString() +") ***\n");
	}
	sb.append("\n*** LABS(" + theLabTestList.size() + ") ***\n");
	for (int i = 0; i < theLabTestList.size(); i++) {
		sb.append("*** Lab " + (i+1) + " ***\n");
		sb.append(theLabTestList.get(i).toString() +") ***\n");
	}
	sb.append("\n*** PROBLEMS(" + theProblemList.size() + ") ***\n");
	for (int i = 0; i < theProblemList.size(); i++) {
		sb.append("*** Problem " + (i+1) + " ***\n");
		sb.append(theProblemList.get(i).toString() +") ***\n");
	}
	return sb.toString();
	}
*/
}
