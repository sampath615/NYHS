package gov.ny.state.doh.bhae.nyehms.web.junit.standardization.facility;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.standardization.facility.MasterFacilityVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.standardization.facility.RawSubFacilityVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.standardization.facility.SubFacilityVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.enums.AppConstants;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.standardization.facility.SearchManageFacilityService;

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
public class SearchManageFacilityTest {
	/*
	 * Log reference for nyehmsapp file appender 
	 */
	private static Logger log = LoggerFactory.getLogger(SearchManageFacilityTest.class);
	private String nyehmsLog=AppConstants.nyehmLogDebugString.getName();
	/*
	 * Reference to service locator by means of dependency injection
	 */
	@Autowired
	SearchManageFacilityService searchManageFacilityService;
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
		
		
		log.debug(nyehmsLog+ " get facility relations -----");
		getFacilityRelations();
	}
	private void getFacilityRelations(){
		List facilityDetailsByMasterFacility =searchManageFacilityService.getFacilityDetailsByMasterFacility(new MasterFacilityVO(), bindingResult, userObject);
		log.debug(nyehmsLog+ " get facility relations by master facility returned-----"+facilityDetailsByMasterFacility.size());
		List facilityDetailsBySubFacility =searchManageFacilityService.getFacilityDetailsBySubFacility(new SubFacilityVO(), bindingResult, userObject);
		log.debug(nyehmsLog+ " get facility relations by sub facility returned-----"+facilityDetailsBySubFacility.size());
		List facilityDetailsByRawSubFacility =searchManageFacilityService.getFacilityDetailsByRawSubFacility(new RawSubFacilityVO(), bindingResult, userObject);
		log.debug(nyehmsLog+ " get facility relations by raw facility returned-----"+facilityDetailsByRawSubFacility.size());

	}
}
