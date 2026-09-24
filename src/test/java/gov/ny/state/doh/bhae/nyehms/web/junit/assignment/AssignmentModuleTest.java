package gov.ny.state.doh.bhae.nyehms.web.junit.assignment;

import gov.ny.state.doh.bhae.nyehms.web.spring.bo.ProcessACRFForm;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.assignment.AssignmentWorkerRegionsVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.assignment.AssignmentsHomeVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.assignment.AssignmentsListVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.CodedAcrfTestVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.CodedAcrfTestResultVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.CodedParentTestTypeVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.acrf.AcrfFormVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.acrf.RawAcrfCd4TstVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.acrf.RawAcrfDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.acrf.RawAcrfLabsVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.DateUtil;

import org.springframework.validation.BindingResult;
import org.springframework.validation.MapBindingResult;

import gov.ny.state.doh.bhae.nyehms.web.spring.enums.AppConstants;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.validation.BindingResult; 

import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.assignment.AssignmentService;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.document.ACRFDocumentService;

/*
 * Author : Srikar Kummamuri
 * Date : 8/15/12
 * Description: Simple Junit test to test DB connectivity. Please refer to JDBC properties file to see which DB is being tested.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:/spring-context-junit.xml"})		
public class AssignmentModuleTest {
	
	@Autowired
	ProcessACRFForm processACRFForm;
	@Autowired
	AssignmentService assignmentService;
	@Autowired
	org.mybatis.spring.SqlSessionTemplate sqlSession;
	@Autowired
	DateUtil dateUtil;
	@Autowired
	ACRFDocumentService acrfFDocumentService;
		
	private static Logger log = LoggerFactory.getLogger(AssignmentModuleTest.class);
	private String nyehmsLog=AppConstants.nyehmLogDebugString.getName();
	
	//Declare test variables.
	final String tstWorkerId="141";
	final String tstWorkerRegionId="1015";
	
	private Date tstStDate = new java.util.Date();
	private Date tstEdDate = new java.util.Date();
	final String tstCallingProgram="NYEHMS";	
	UserObject userObject=new UserObject();
	
	String sessionId="junitsessionid";	
	BindingResult result = new MapBindingResult(new HashMap(), "Sample");
	
	@Test
    public void testMessage() throws Exception {
		
		userObject.setUserName("JUnit");		
		org.apache.ibatis.logging.LogFactory.useLog4JLogging();		
		tstStDate=dateUtil.getNyehmsDateFromString("01/01/1950");			
		log.debug(nyehmsLog+" Assignment Module Junit Test Start ");
		//Test Worker Regions Fetch Call
		testAssignmentWorkerRegions();
		//Test Assignment List fetch for a worker.
		testGetAssignmentList();
		//Test Test Code populate in ACRF Form
		testTestCodePapulationInACRF();
		//Test Result Code populate in ACRF Form
		testResultCodePapulationInACRF();
		//Test adding a new ACRF From
		testAddACRFform();
		//Test fethcing the ACRF Form components
		//testGetACRFForm();
		//Test updating ACRF Form 
		//testUpdateAcrfForm();
		//Test adding of missed test codes
		//testdMissingPatentTestcodesAdd();		
	}	
	
	
	public void testAssignmentWorkerRegions(){		
		List<AssignmentWorkerRegionsVO> list=assignmentService.getAssignmentWorkerRegions(tstWorkerId, 
																						result,
																						userObject,
																						sessionId);
		
		if(list.size()>0) {																				
			for(int i=0; i<list.size(); i++){
				log.debug(nyehmsLog+" Worker Region ID : {}",list.get(i).getWorkerRegionId());	
			}
		}else{
			log.debug(nyehmsLog+" Worker Regions Cursor count are 0");
		}
	}
	
	
	public void testGetAssignmentList(){		
		
		AssignmentsHomeVO assignmentsHomeVO = new AssignmentsHomeVO();
		
		assignmentsHomeVO.setWorkerId(Integer.parseInt(tstWorkerId));
		assignmentsHomeVO.setFromDate("01/01/2000");
		assignmentsHomeVO.setTodate(new Date().toString());
		assignmentsHomeVO.setWorkerRegionId(tstWorkerRegionId);
		assignmentsHomeVO.setAssignmentType("CLOSED");
		List<AssignmentsListVO> asmtCursorOut =
		assignmentService.getAssignmentsList(assignmentsHomeVO, 
											result,
											userObject,
											sessionId);		
		
		log.debug(nyehmsLog+" Assignments Cursor Count is "+asmtCursorOut.size());	
				
		if(asmtCursorOut.size()>0){
			for(int i=0; i<asmtCursorOut.size(); i++ ){
				log.debug(nyehmsLog+ " Assignment ID: "+ asmtCursorOut.get(i).getAssignmentId());			
			}
		}else{
			log.debug(nyehmsLog+" Worker Assignment List Cursor count are 0");
		}	
	}
	
	
	public void testTestCodePapulationInACRF(){			
		
		AcrfFormVO tstVO = new AcrfFormVO();		
		List<CodedAcrfTestVO> codedAcrfTestList = acrfFDocumentService.getCodedAcrfTest(result,userObject,sessionId);
		processACRFForm.papulateLabTestCodeObjects("ACRF", codedAcrfTestList, 
												tstVO,													
												userObject,
												sessionId);	
		Map<String, List<CodedAcrfTestVO>> mp = tstVO.getLabTestResultMap();
		
		log.debug(nyehmsLog+"  getHivABNonTypTstMap Size:: "+mp.size());
		try{		
			for (String key: mp.keySet()) {
				List<CodedAcrfTestVO> codedAcrfTestVOList = mp.get(key);
				for (CodedAcrfTestVO codedAcrfTestVO : codedAcrfTestVOList) {
					log.debug(nyehmsLog+"   getHivABNonTypTstMap key = value :: "+ codedAcrfTestVO.getAcrtTestCode() + " = " + codedAcrfTestVO.getAcrtTestDesc()); 
					
				}
			}
		}catch(Exception e){
			log.error(nyehmsLog+"  error "+e.getMessage());
		}
		
	}
	
	public void testResultCodePapulationInACRF(){
		Map pMap = new HashMap();			
		List<CodedAcrfTestResultVO> codedAcrfResultcodes=acrfFDocumentService.getCodedAcrfResultTypes("", 
																								"", 
																								"",
																								0, result,
																								userObject,																									
																								sessionId);		
		CodedAcrfTestResultVO codedAcrfTestResult2;			
		for(int ijk=0; ijk<codedAcrfResultcodes.size(); ijk++){		
			codedAcrfTestResult2=codedAcrfResultcodes.get(ijk);			
			log.debug(nyehmsLog+" Result Code::  "+codedAcrfTestResult2.getAcrfResultCode() +
					"*** Desc is :: "+codedAcrfTestResult2.getAcrfResultDesc()+
					"**** Parent Test Type ::"+codedAcrfTestResult2.getParentTestType());		
		}
		
	}
	
	public void testAddACRFform(){
		
		//Add ACRF Form Test		
		AcrfFormVO acrfFormAddTst = new AcrfFormVO();		
		RawAcrfDocVO rawACRFDocAddTst = new RawAcrfDocVO();
		rawACRFDocAddTst.setFname("FirstJunit");
		rawACRFDocAddTst.setLname("LastJunit");
		rawACRFDocAddTst.setTbPulmonary1("01/01/2012");
		List<RawAcrfLabsVO>  rawAcrfLabsAddTstList = new ArrayList();
		List<RawAcrfCd4TstVO> rawAcrfCd4AddTstList = new ArrayList();				
		
		//Create 2 Lab Records for this test
		RawAcrfLabsVO rawAcrfLabsAddTst;
		for(int i=0; i<2; i++){
			rawAcrfLabsAddTst = new RawAcrfLabsVO();			
			rawAcrfLabsAddTst.setAcrfResultCode("01");
			rawAcrfLabsAddTst.setAcrfTestCode("A1");
			rawAcrfLabsAddTst.setParentTestType("01");
			rawAcrfLabsAddTstList.add(rawAcrfLabsAddTst);
		}
		
		//Create 2 more Labs Records with wrong data/ no result code.
		/*
		for(int i=0; i<2; i++){
			RawAcrfLabsVO rawAcrfLabsAddTst = new RawAcrfLabsVO();			
			rawAcrfLabsAddTst.setAcrfResultCode("0");
			rawAcrfLabsAddTst.setAcrfTestCode("A1");
			rawAcrfLabsAddTst.setParentTestType("01");
			rawAcrfLabsAddTstList.add(rawAcrfLabsAddTst);
		}*/
	
		//Create 2 ACRF CD4 Test Records for this test
		RawAcrfCd4TstVO rawAcrfCd4AddTst;
		for(int k=0; k<2; k++){
			rawAcrfCd4AddTst = new RawAcrfCd4TstVO();			
			rawAcrfCd4AddTst.setCd4Count(k);
			rawAcrfCd4AddTstList.add(rawAcrfCd4AddTst);
		}
		
		//Prepare ACRFVO Envelop.		
		acrfFormAddTst.setRawAcrfDoc(rawACRFDocAddTst);
		acrfFormAddTst.setRawAcrfLabs(rawAcrfLabsAddTstList);
		acrfFormAddTst.setRawAcrfCd4Tst(rawAcrfCd4AddTstList);			
		//Call Service Layer to add this ACRF Form VO.			
		try{
			acrfFDocumentService.addACRFDoc(acrfFormAddTst, "ACRF", "validate", false, null, result, userObject, sessionId, null);
	
			log.debug(nyehmsLog+" Inserted ACRF FROM with ACRF Doc ID :: "+
										acrfFormAddTst.getRawAcrfDoc().getDocumentId());
		}catch(Exception e){
			log.error(nyehmsLog +"  Exception in inserting ACRF Form VO Into DB "+e.getMessage());	
		}
		
	}
	
	public void testGetACRFForm(){
		
		//Fetch ACRF Form From the Database.			
		String rawAcrfDocIDfetch= "1447";
		//Fetch Raw ACRF Doc.
		
		AcrfFormVO acrfFormVO=acrfFDocumentService.getAcrfFormVO(rawAcrfDocIDfetch, "ACRF",
									result,
									userObject,
									sessionId );
		
		log.debug(nyehmsLog+" Docuemnt ID of fetched ACRF Doc :" +acrfFormVO.getRawAcrfDoc().getDocumentId());			
		
		List<RawAcrfLabsVO> rawACRFLabsListFetch =acrfFormVO.getRawAcrfLabs();
		for(RawAcrfLabsVO rawAcrfLabs:rawACRFLabsListFetch){
			log.debug(nyehmsLog+" ACRF Labs Fetched & Test Code:::  "+rawAcrfLabs.getAcrfTestCode());
		}	
		
		List<RawAcrfCd4TstVO>  rawAcrfCd4TstListFetch =acrfFormVO.getRawAcrfCd4Tst();
		
		for(RawAcrfCd4TstVO rawAcrfCd4Tst:rawAcrfCd4TstListFetch){
			log.debug(nyehmsLog+" CD4 Test Fetched and CD4 Count :: "+rawAcrfCd4Tst.getCd4Count());
		}
		
	}
	
	
	public void testUpdateAcrfForm(){
		
		String rawAcrfDocIDfetch= "1447";
		
		AcrfFormVO acrfFormVO=acrfFDocumentService.getAcrfFormVO(rawAcrfDocIDfetch, "ACRF",
															result,
															userObject,
															sessionId );
		List<RawAcrfLabsVO> rawACRFLabsListUpdate=acrfFormVO.getRawAcrfLabs();
		List<RawAcrfCd4TstVO> rawAcrfCd4TstListUpdate=acrfFormVO.getRawAcrfCd4Tst();
		RawAcrfDocVO rawAcrfDocUpdate=acrfFormVO.getRawAcrfDoc();
		
		rawAcrfDocUpdate.setFname("updFname15");
		rawAcrfDocUpdate.setReceivedDate("01/01/2012");
		//Update RAW ACRF DOC
		acrfFDocumentService.updateRawACRFDoc(acrfFormVO,  "ACRF",
										result, 
										userObject, 
										sessionId);
		log.debug(nyehmsLog+" Updating ACRF Form VO just completed");
		
	}
	
	public void testdMissingPatentTestcodesAdd(){						
		List<RawAcrfLabsVO>  rawAcrfLabsListMisAdd = new ArrayList();
		RawAcrfLabsVO rawAcrfLabstoBeAdded = new RawAcrfLabsVO();
		rawAcrfLabstoBeAdded.setParentTestType("01");
		rawAcrfLabsListMisAdd.add(rawAcrfLabstoBeAdded);
		processACRFForm.addMissingLabParentTestCodes(rawAcrfLabsListMisAdd,														
													userObject, 
													sessionId);
		for(RawAcrfLabsVO rawAcrfLabs :rawAcrfLabsListMisAdd){
			log.debug(nyehmsLog+"  Parent Test Type Code added is "+rawAcrfLabs.getParentTestType());
		}			
	}
	
	
	

}
