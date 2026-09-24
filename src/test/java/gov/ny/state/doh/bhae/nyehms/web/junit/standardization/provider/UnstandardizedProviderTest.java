package gov.ny.state.doh.bhae.nyehms.web.junit.standardization.provider;

import gov.ny.state.doh.bhae.nyehms.web.spring.enums.AppConstants;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.standardization.provider.UnStandardizedProviderService;

import java.util.HashMap;
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

/*
 * Test case to test the Unstandardized provider functionalities
 * This test case is being run with Spring-Junit's SpringJUnit4ClassRunner
 * as a part of the gov.ny.state.doh.bhae.nyehms.web.junit.BuildTests category
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:/spring-context-junit.xml"})
@Category(gov.ny.state.doh.bhae.nyehms.web.junit.BuildTests.class)
public class UnstandardizedProviderTest {
	/*
	 * Log reference for nyehmsapp file appender 
	 */
	private static Logger log = LoggerFactory.getLogger(UnstandardizedProviderTest.class);
	private String nyehmsLog=AppConstants.nyehmLogDebugString.getName();
	/*
	 * Reference to service locator by means of dependency injection
	 */
	@Autowired
	UnStandardizedProviderService unStandardizedProviderService;
	/*
	 * Sample provider identity
	 */
	long providerId=5;
	/*
	 * Sample session identifier
	 */
	String sessionId="junitsessionid";
	/*
	 * Dummy UserOjbect reference
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
		userObject.setWorkerId(141);
		resultMap=new HashMap<String,String>();
		bindingResult=new MapBindingResult(resultMap, "resultMap");		
		org.apache.ibatis.logging.LogFactory.useLog4JLogging();
		
		log.debug(nyehmsLog+ " Test flag as facility with sample values -----");		
		flagProviderDataAsFacilty();
		
	}
	/*
	 * Helper method to make the service locator business method
	 */
	public void flagProviderDataAsFacilty(){
		unStandardizedProviderService.flagProviderDataAsFacilty(providerId, bindingResult, userObject);				
		log.debug(nyehmsLog+" transaction message returned from server is : "+((MapBindingResult)bindingResult).getTargetMap().get(AppConstants.NYEHM_TRANSACTION_MESSAGE.getName()));		
	}
}

