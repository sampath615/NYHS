package gov.ny.state.doh.bhae.nyehms.web.junit.person;

import gov.ny.state.doh.bhae.nyehms.web.spring.dao.person.PersonDAO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.person.PersonFieldVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.person.PersonRisksVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.person.PersonViewVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.enums.AppConstants;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.person.PersionService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.validation.BindingResult;
import org.springframework.validation.MapBindingResult;

/*
 * Description: Simple Junit test to test DB connectivity. Please refer to JDBC properties file to see which DB is being tested.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:/spring-context-junit.xml"})
public class PersonTest {
	
	Map dummyMindingMap= new HashMap();
	BindingResult dummyBindingResult = new MapBindingResult(dummyMindingMap, "Sample");
	
	private static Logger log = LoggerFactory.getLogger(PersonTest.class);
	private String nyehmsLog=AppConstants.nyehmLogDebugString.getName();
	
	
	@Autowired
	PersionService personService;	
	@Autowired
	PersonDAO personDAO;
	
	String stateNo="0010166408";
	String sessionId="junitsessionid";
	UserObject userObject;
	BindingResult result;
	String strDate;
	Map bindingMap= new HashMap();
	
	@Test
    public void testMessage() throws Exception {		
		userObject=new UserObject();		
		userObject.setUserName("JUnit");
		result = new MapBindingResult(bindingMap, "Sample");		
		org.apache.ibatis.logging.LogFactory.useLog4JLogging();
		
		log.debug(nyehmsLog+ " Person Based Tests are starting -----");		
					
		//Get Person View Data
		getPersonViewData();
		//Get FieldValues with Best Indicated Date.
		//getFieldValWithBestIndicateData();
		//Save Best Indicated Data
		//saveBestIndicatedDate();
		
	}
	
	public void getPersonViewData(){
		
		//Get Person Personal Information
		PersonViewVO pvo =
				personService.getPersonViewData(stateNo, strDate, dummyBindingResult,userObject, sessionId);
				
			
		//PersonViewVO pvo= (PersonViewVO)cursorout.get(0);	
		
		log.debug(nyehmsLog+" --Frist Name ::  "+pvo.getFNAME());
		
		//Get Person Risks
		List<PersonRisksVO> cursorRisksOut =
				personService.getPersonRisksData(stateNo, strDate, dummyBindingResult,userObject, sessionId);
		
		log.debug(nyehmsLog+" returned PersonrisksVO objects size : "+cursorRisksOut.size());		
		PersonRisksVO prvo= (PersonRisksVO)cursorRisksOut.get(0);		
		log.debug(nyehmsLog+" IDU -- "+prvo.getIdu());
		
	}
	
	
	public void getFieldValWithBestIndicateData(){		
		//Get All Fields vals for a given state number.	
			List<PersonFieldVO> cursorPersonFieldVals=
					personService.getFieldValsWithBestIndicator(stateNo,
																	"LNAME", 
																	"PERSON", 
																	dummyBindingResult, userObject, sessionId);			
			for(int i=0; i<cursorPersonFieldVals.size(); i++){						
				log.debug(nyehmsLog+" Output :::: " +cursorPersonFieldVals.get(i).getField());
				log.debug(nyehmsLog+" Output :::: " +cursorPersonFieldVals.get(i).getFieldId());
			}
		
	}
	


}
