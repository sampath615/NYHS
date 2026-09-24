package gov.ny.state.doh.bhae.nyehms.web.junit.administration;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.administration.ModuleAndPermissionsVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.enums.AppConstants;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.administration.SearchManageProfileService;

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

public class SearchManageProfileTest {
	/*
	 * 
	 */
	private static Logger log = LoggerFactory.getLogger(SearchManageProfileTest.class);
	private String nyehmsLog=AppConstants.nyehmLogDebugString.getName();
	/*
	 * Reference to service locator by means of dependency injection
	 */
	@Autowired
	SearchManageProfileService searchManageProfileService;
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
		
		log.debug(nyehmsLog+ " get permissions with sample values -----");
		getModuleAndPermissionsByRole();
		
	}
	public void getModuleAndPermissionsByRole(){
		List<ModuleAndPermissionsVO> moduleAndPermissionsByRole=
		searchManageProfileService.getModuleAndPermissionsByRole(3, bindingResult, userObject);
		log.debug("there are {} modules for this role",moduleAndPermissionsByRole.size());
	}
}
