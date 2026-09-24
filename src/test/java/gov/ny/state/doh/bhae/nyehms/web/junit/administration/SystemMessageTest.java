package gov.ny.state.doh.bhae.nyehms.web.junit.administration;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.administration.SystemMessage;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.administration.SystemMessageArchive;
import gov.ny.state.doh.bhae.nyehms.web.spring.enums.AppConstants;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.administration.SystemMessageService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.validation.BindingResult;
import org.springframework.validation.MapBindingResult;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:/spring-context-junit.xml"})
@Category(gov.ny.state.doh.bhae.nyehms.web.junit.BuildTests.class)
public class SystemMessageTest {
	/*
	 * 
	 */
	private static Logger log = LoggerFactory.getLogger(SystemMessageTest.class);
	private String nyehmsLog=AppConstants.nyehmLogDebugString.getName();
	/*
	 * Reference to service locator by means of dependency injection
	 */
	@Autowired
	SystemMessageService systemMessageService;
	/*
	 * Sample session identifier
	 */
	String sessionId="junitsessionid";
	/*
	 * UserOjbect reference
	 */
	UserObject userObject;
	Map<String,String> resultMap;
	BindingResult bindingResult; 
	/*
	 * The method invoked by the Test case runner
	 */
	@Test
    public void testMessage() throws Exception {		
		userObject=new UserObject();
		userObject.setUserId("axp21");
		userObject.setWorkerId(141);
		userObject.setSessionId("sessionId");
		resultMap=new HashMap<String,String>();
		bindingResult=new MapBindingResult(resultMap, "resultMap");		
		org.apache.ibatis.logging.LogFactory.useLog4JLogging();
		
		log.debug(nyehmsLog+ " get system message archives -----");
		getSystemMessageArchives();
	}
	private void getSystemMessageArchives(){
		SystemMessage systemMessage = new SystemMessage();
		systemMessage.setMessageId(523);
		List<SystemMessageArchive> systemMessageArchives = systemMessageService.getSystemMessageArchives(systemMessage, bindingResult, userObject);
				
		log.debug("there are {} message archives",systemMessageArchives.size());
		for(SystemMessageArchive systemMessageArchive:systemMessageArchives){
			List<SystemMessage> systemMessages=systemMessageArchive.getSystemMessageArchives();
			log.debug("for msg id = "+systemMessageArchive.getMessageId()+" there are "+systemMessages.size()+" actions");
		}
		
	}
}
