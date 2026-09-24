package gov.ny.state.doh.bhae.nyehms.web.junit.standardization.facility;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.standardization.facility.MasterFacilityVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.enums.AppConstants;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.standardization.facility.FacilityService;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.standardization.facility.OrphanSubFacilityService;

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
/*
 * Test case to test the Orphan sub facility functionalities
 * This test case is being run with Spring-Junit's SpringJUnit4ClassRunner
 * as a part of the gov.ny.state.doh.bhae.nyehms.web.junit.BuildTests category
 */
import org.springframework.validation.MapBindingResult;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:/spring-context-junit.xml"})
@Category(gov.ny.state.doh.bhae.nyehms.web.junit.BuildTests.class)
public class OrphanSubFacilityTest {
	/*
	 * Log reference for nyehmsapp file appender 
	 */
	private static Logger log = LoggerFactory.getLogger(OrphanSubFacilityTest.class);
	private String nyehmsLog=AppConstants.nyehmLogDebugString.getName();
	/*
	 * Reference to service locator by means of dependency injection
	 */
	@Autowired
	OrphanSubFacilityService orphanSubFacilityService;
	@Autowired
	FacilityService facilityService;
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
		
		log.debug(nyehmsLog+ " Test retrieving orphan sub facilities -----");		
		getOrphanSubFacilities();
		
		log.debug(nyehmsLog+ " link orphan sub facility to master facility with sample values -----");
		linkOrphanSubFacilityToMasterFacility();
		
		log.debug(nyehmsLog+ " link raw to orphan sub facility to master facility with sample values -----");
		linkRawToOrphanSubFacility();

		log.debug(nyehmsLog+ " add new master facility and link orphan sub facility to master facility with sample values -----");
		addNewMasterFacilityAndLinkOrphanSubFacility();
		
		log.debug(nyehmsLog+ " add new master facility and link raw to orphan sub facility with sample values -----");
		addNewMasterFacilityAndLinkRawToOrphanSubFacility();

	}
	
	public void getOrphanSubFacilities(){
		List orphanSubFacilities=orphanSubFacilityService.getOrphanSubFacilities(bindingResult, userObject);
				
		log.debug("Test returned "+orphanSubFacilities.size()+" orphan sub facilities****************");
	}
	public void linkOrphanSubFacilityToMasterFacility(){
		long subFacilityId=101;
		long masterFacilityId=22;
		orphanSubFacilityService.linkOrphanSubFacilityToMasterFacility(subFacilityId,masterFacilityId,bindingResult,userObject);
		log.debug("linkOrphanSubFacilityToMasterFacility returned *****"+((MapBindingResult)bindingResult).getTargetMap().get(AppConstants.NYEHM_TRANSACTION_MESSAGE.getName()));
	}
	
	public void linkRawToOrphanSubFacility(){
		long subFacilityId=101;
		long masterFacilityId=22;
		long rawSubFacilityId=10;
		orphanSubFacilityService.linkRawToMasterAndOrphanSubFacility(subFacilityId, masterFacilityId, rawSubFacilityId, bindingResult, userObject);
		log.debug("linkRawToOrphanSubFacility returned *****"+((MapBindingResult)bindingResult).getTargetMap().get(AppConstants.NYEHM_TRANSACTION_MESSAGE.getName()));
	}
	public void addNewMasterFacilityAndLinkOrphanSubFacility(){
		long subFacilityId=101;
		MasterFacilityVO masterFacilityVO=new MasterFacilityVO();
		//masterFacilityVO.setFacilityTypeCode("OTHER");
		masterFacilityVO.setMasterFacilityName("Test Master Facility1");
		orphanSubFacilityService.addNewMasterFacilityAndLinkOrphanSubFacility(subFacilityId,masterFacilityVO,bindingResult, userObject);
		log.debug("addNewMasterFacilityAndLinkOrphanSubFacility returned *****"+((MapBindingResult)bindingResult).getTargetMap().get(AppConstants.NYEHM_TRANSACTION_MESSAGE.getName()));
	}
	public void addNewMasterFacilityAndLinkRawToOrphanSubFacility(){
		long subFacilityId=101;
		long rawSubFacilityId=12;
		MasterFacilityVO masterFacilityVO=new MasterFacilityVO();
		//masterFacilityVO.setFacilityTypeCode("OTHER");
		masterFacilityVO.setMasterFacilityName("Test Master Facility1");
		orphanSubFacilityService.addNewMasterFacilityAndLinkRawToOrphanSubFacility(subFacilityId,rawSubFacilityId,masterFacilityVO,bindingResult,userObject);
		log.debug("addNewMasterFacilityAndLinkRawToOrphanSubFacility returned *****"+((MapBindingResult)bindingResult).getTargetMap().get(AppConstants.NYEHM_TRANSACTION_MESSAGE.getName()));
	}
}
