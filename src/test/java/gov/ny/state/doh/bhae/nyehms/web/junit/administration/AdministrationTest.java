package gov.ny.state.doh.bhae.nyehms.web.junit.administration;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.administration.NyehmsRole;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.administration.NyehmsWorkerRegionsViewVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.enums.AppConstants;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.administration.AdministrationService;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.transaction.TransactionService;

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
public class AdministrationTest {
	/*
	 * 
	 */
	private static Logger log = LoggerFactory.getLogger(AdministrationTest.class);
	private String nyehmsLog=AppConstants.nyehmLogDebugString.getName();
	/*
	 * Reference to service locator by means of dependency injection
	 */
	@Autowired
	AdministrationService administrationService;
	
	@Autowired
	TransactionService transactionService;
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
		userObject.setWorkerId(141);
		resultMap=new HashMap<String,String>();
		bindingResult=new MapBindingResult(resultMap, "resultMap");		
		org.apache.ibatis.logging.LogFactory.useLog4JLogging();
		
		log.debug(nyehmsLog+ " get users with sample values -----");
		getSearchUsersList();
		//createUser();
		//terminateUser();
		getNyehmsRoles();
	}
	private void getSearchUsersList(){
		NyehmsWorkerRegionsViewVO nyehmsWorkerRegionsViewVO=new NyehmsWorkerRegionsViewVO();
		List<NyehmsWorkerRegionsViewVO> usersList=administrationService.getSearchUsersList(nyehmsWorkerRegionsViewVO, bindingResult, userObject);
		log.debug("getSearchUsersList returned ***** {}",usersList.size());
	}
	private void createUser(){
		NyehmsWorkerRegionsViewVO nyehmsWorkerRegionsViewVO=new NyehmsWorkerRegionsViewVO();
		nyehmsWorkerRegionsViewVO.setLegacyUid("dmi04");
		administrationService.creatingNewUserInHcsAndNyehms(nyehmsWorkerRegionsViewVO, bindingResult, userObject);
	}
	private void terminateUser(){
		administrationService.terminateUserFromHCSAndDB(304, "STANREP02", bindingResult, userObject);
	}
	
	private void getNyehmsRoles(){
		List<NyehmsRole> nyehmsRoles=administrationService.getSrchroleTypes(bindingResult, userObject);
		log.debug("******** there are {} roles***************",nyehmsRoles.size());
	}
	
}
