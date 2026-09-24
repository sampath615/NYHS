package gov.ny.state.doh.bhae.nyehms.web.junit.standardization.provider;

import gov.ny.state.doh.bhae.nyehms.web.spring.enums.AppConstants;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.standardization.provider.OrphanProviderAddressService;

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
public class OrphanProviderAddressTest {
	/*
	 * Log reference for nyehmsapp file appender 
	 */
	private static Logger log = LoggerFactory.getLogger(OrphanProviderAddressTest.class);
	private String nyehmsLog=AppConstants.nyehmLogDebugString.getName();
	/*
	 * Reference to service locator by means of dependency injection
	 */
	@Autowired
	OrphanProviderAddressService orphanProviderAddressService;
	/*
	 * Sample provider identity
	 */
	long providerId=5;
	long providerAddressId=41;
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
		
		log.debug(nyehmsLog+ " Test getOrphanProviderAddresses with sample values -----");		
		getOrphanProviderAddresses();
		log.debug(nyehmsLog+ " Test linkOrphanAddressToProvider with sample values -----");		
		linkOrphanAddressToProvider();
	}
	/*
	 * Helper method to make the service locator business method
	 */
	public void getOrphanProviderAddresses(){
		List orphanProviderAddresses=orphanProviderAddressService.getOrphanProviderAddresses(bindingResult, userObject);				
		log.debug(nyehmsLog+" number of orphan provider addresses is : "+orphanProviderAddresses.size());		
	}
	
	public void linkOrphanAddressToProvider(){
		orphanProviderAddressService.linkOrphanAddressToProvider(providerAddressId, providerId, bindingResult, userObject);				
		log.debug(nyehmsLog+" linkOrphanAddressToProvider retturned : "+((MapBindingResult)bindingResult).getTargetMap().get(AppConstants.NYEHM_TRANSACTION_MESSAGE.getName()));		
	}
}
