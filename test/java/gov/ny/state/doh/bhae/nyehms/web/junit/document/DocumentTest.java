package gov.ny.state.doh.bhae.nyehms.web.junit.document;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Map;

import gov.ny.state.doh.bhae.nyehms.web.spring.enums.AppConstants;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.UnexpectedRollbackException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.MapBindingResult;

import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.document.DeathDocumentService;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.document.DoccsDocumentService;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.document.LabDocumentService;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.document.NycPRFDocumentService;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.document.PRFDocumentService;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.document.TBDocumentService;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.NycPRFDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.NycPRFPartnerVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.NycPrfFormVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.PrfFormVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.DeathDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.DoccsDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.LabDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.PRFPartnerVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.StdEcrlsLabDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.TBDocVO;

/*
 * Author : Srikar Kummamuri
 * Date : 10/24/12
 * Description: Simple Junit test to test DB connectivity. Please refer to JDBC properties file to see which DB is being tested.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:/spring-context-junit.xml"})
public class DocumentTest {
	
	private static Logger log = LoggerFactory.getLogger(DocumentTest.class);
	private String nyehmsLog=AppConstants.nyehmLogDebugString.getName();
	
	@Autowired
	DeathDocumentService deathDocumentSrvice;	
	@Autowired
	TBDocumentService tbDocumentSrvice;	
	@Autowired
	DoccsDocumentService doccsDocumentService;	
	@Autowired
	LabDocumentService labDocumentService;
	@Autowired
	PRFDocumentService pRFDocumentService;
	@Autowired
	NycPRFDocumentService nycPRFDocumentService;
	
	@Autowired
	private MessageSource messageSource;
	
	
	@Test
	//@Category(gov.ny.state.doh.bhae.nyehms.web.junit.BuildTests.class)
    public void testMessage() throws Exception {		
				
		log.debug(nyehmsLog+ " Document Based Tests are starting -----");
		
		Map bindingMap= new HashMap();
		BindingResult result = new MapBindingResult(bindingMap, "Sample");		
		UserObject userObject=new UserObject();
		userObject.setUserName("JUnit");	
		userObject.setUserId("JUnit");
		String sessionId="junitsessionid";		
		org.apache.ibatis.logging.LogFactory.useLog4JLogging();
		
		
		//log.debug(nyehmsLog+" *************  Message Bundel: "+messageSource.getMessage("label.footer", null, Locale.getDefault()));
		log.debug(nyehmsLog+" *************  Message Bundel: "+messageSource.getMessage("label.footer", null, null));
		
		//log.debug(nyehmsLog+" *************  Message Bundel: "+context.getMessage("successful", null, Locale.getDefault())); 
		
		//Test TB Doc
		//testTBDoc(result, userObject, sessionId);
		//Test Death
		//testDeathDoc(result, userObject, sessionId);
		//Test Doccs
		//testDoccsDoc(result, userObject, sessionId);
		//Test Lab 
		//testLabDoc(result, userObject, sessionId);
		//Test PRF
		testPRFDoc(result, userObject, sessionId);
		//Test NycPRF
		//testNycPRFDoc(result, userObject, sessionId);
	}
	
	
	
	//Test PRF Doc
	public void testPRFDoc(BindingResult result, UserObject userObject, String sessionId){
		//Fetch PRF Document.
//		int prfDocId=30701;
//		PrfFormVO prfFormVO= pRFDocumentService.getPRFDoc(prfDocId, result, userObject, sessionId);
//		log.debug(nyehmsLog+ " First name from PRF Document :: " +prfFormVO.getStdPRFDocVO().getFname());
//		
//		for(PRFPartnerVO  stdPRFPartnerVO:  prfFormVO.getStdPRFPartnerVOList()){
//			log.debug(nyehmsLog+ " Last name from PRF Partner Document :: " +stdPRFPartnerVO.getLname());
//		}		
//		//Add one more new partner
//		LinkedList linkedList=prfFormVO.getStdPRFPartnerVOList();		
//		PRFPartnerVO stdPRFPartnerVO=new PRFPartnerVO();
//		stdPRFPartnerVO.setFname("HaiGone");		
//		linkedList.add(stdPRFPartnerVO);		
//		prfFormVO.setStdPRFPartnerVOList(linkedList);		
//		//Save the fetched PRF Dcouemnt
//		pRFDocumentService.insertPRFDoc(prfFormVO, result, userObject, sessionId);		
	}
	
	//Test Lab Doc
	public void testLabDoc(BindingResult result, UserObject userObject, String sessionId){		
		//Fetch Lab Document
		StdEcrlsLabDocVO stdLabDocVO = labDocumentService.getLabDoc(39348, "HIV_LAB", result, userObject, sessionId);
		if(stdLabDocVO!=null){
			log.debug(nyehmsLog+" Firtname From Labs  Document for id -- :: 39348 :::  " +stdLabDocVO.getFNAME());			
		}		
		//Insert LAB Document
		LabDocVO stdLabDocVOInsert=new LabDocVO();
		stdLabDocVOInsert.setFname("LabKnockFirst");
		stdLabDocVOInsert.setLname("LabKnockLast");
		labDocumentService.insertLabDocData(stdLabDocVOInsert, result, userObject, sessionId);		
	}
	
	//Test Doccs Doc
	
	public void testDoccsDoc(BindingResult result, UserObject userObject, String sessionId){		
		//Fetch Doccs Document
		log.debug(nyehmsLog+"--Fething Data from Doccs Document.");
		DoccsDocVO stdDoccsDocVO=doccsDocumentService.getDoccsDoc(3913141, result, userObject, sessionId);
		if(stdDoccsDocVO!=null){
			log.debug(nyehmsLog+" Firtname From DOCCS Document for id -- :: 3913141 :::  " +stdDoccsDocVO);			
		}		
		//Insert Doccs Document
		DoccsDocVO stdDoccsDocVOInsert= new DoccsDocVO();
		//stdDoccsDocVOInsert.setFname("KnockKnock");		
		//doccsDocumentService.insertDocsDocData(stdDoccsDocVOInsert, result, userObject, sessionId);
	}
	
	//Test Death Doc
	
	public void testDeathDoc(BindingResult result, UserObject userObject, String sessionId){		
		
		//Fetch Death Document
		DeathDocVO stdDeathDocVO=deathDocumentSrvice.getDeathDoc(3912362, result, userObject, sessionId);
		if(stdDeathDocVO!=null){
			//log.debug(nyehmsLog+" stateno From Death Document for id -- :: 3912362 : " +stdDeathDocVO.getST());			
		
		}
		
		//Insert a new Death Document. Use previously generated docid and seq value.
		DeathDocVO insertStdDeathDocVO = new DeathDocVO();		
		insertStdDeathDocVO.setFNAME("TestOneFN");
		insertStdDeathDocVO.setLNAME("TestOneLN");
			
		log.debug(nyehmsLog+ " Inserting Death Doc..");
		//deathDocumentSrvice.insertDeathDocData(insertStdDeathDocVO, result, userObject, sessionId);
		
		
	}
	
	
	public void testTBDoc(BindingResult result, UserObject userObject, String sessionId){		
		
		//Fetch TB Document
		TBDocVO tBDocVO=tbDocumentSrvice.getTBDoc(3347554, result, userObject, sessionId);
		if(tBDocVO!=null){
			log.debug(nyehmsLog+" City From TB Document for id -- :: 3347554 :::  " +tBDocVO.getPERSON_CITY());			
		}
						
		//Insert a new TB Document. Use previously generated docid and seq value.
		TBDocVO insertStdTBDocVO = new TBDocVO();		
		insertStdTBDocVO.setFNAME("TestOneFNF");
		insertStdTBDocVO.setLNAME("TestOneLN");
		//insertStdTBDocVO.setDocumentId(23);
		insertStdTBDocVO.setDOCUMENT_ID(3347554);
		//insertStdTBDocVO.setSsn("3333333333333333333333333333333388888888888888888888899999999999999");
		
		
		log.debug(nyehmsLog+ "******************************************** Inserting Raw TB Doc..");		
		//tbDocumentSrvice.insertTBDocData(insertStdTBDocVO, result, userObject, sessionId);
		
		if(result.hasErrors()){
			log.debug(nyehmsLog+" *** Server ERROR "+result.getErrorCount());
		}
		
		log.debug(nyehmsLog+" *** ******************************TB Test Over");
		
	}
	
	
	public void testNycPRFDoc(BindingResult result, UserObject userObject, String sessionId){		
		
		//Fetch NYCPRF Document		
//		int nycPrfDocId=3883637;
//		NycPrfFormVO nycPrfFormVO= nycPRFDocumentService.getNycPRFDoc(nycPrfDocId, result, userObject, sessionId);
//		log.debug(nyehmsLog+ " First name from PRF Document :: " +nycPrfFormVO.getNycPRFDocVO().getFname());
//		
//		
//		for(NycPRFPartnerVO  nycPRFPartnerVO:  nycPrfFormVO.getNycPRFPartnerVOList()){
//			log.debug(nyehmsLog+ " Last name from PRF Partner Document :: " +nycPRFPartnerVO.getLname());
//		}
////		/*
//		//Add one more new partner
//		LinkedList linkedList=nycPrfFormVO.getNycPRFPartnerVOList();		
//		NycPRFPartnerVO stdPRFPartnerVO=new PRFPartnerVO();
//		stdPRFPartnerVO.setFname("HaiGone");		
//		linkedList.add(stdPRFPartnerVO);		
//		prfFormVO.setStdPRFPartnerVOList(linkedList);		
//		//Save the fetched PRF Dcouemnt
//		pRFDocumentService.insertPRFDoc(prfFormVO, result, userObject, sessionId);
//		*/	

	}
	
	

	
	
	
	
}
