package gov.ny.state.doh.bhae.nyehms.web.junit.standardization.facility;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.standardization.facility.MasterFacilityVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.standardization.facility.SubFacilityVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.enums.AppConstants;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.standardization.facility.FacilityService;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.standardization.facility.OrphanSubFacilityService;

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

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:/spring-context-junit.xml"})
@Category(gov.ny.state.doh.bhae.nyehms.web.junit.BuildTests.class)
public class FacilityTest {
	/*
	 * Log reference for nyehmsapp file appender 
	 */
	private static Logger log = LoggerFactory.getLogger(FacilityTest.class);
	private String nyehmsLog=AppConstants.nyehmLogDebugString.getName();
	/*
	 * Reference to service locator by means of dependency injection
	 */
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
		userObject.setUserId("axp21");
		resultMap=new HashMap<String,String>();
		bindingResult=new MapBindingResult(resultMap, "resultMap");		
		org.apache.ibatis.logging.LogFactory.useLog4JLogging();
		
		log.debug(nyehmsLog+ " add new sub facility with sample values -----");
		insertNewStdFacility();
		createMasterAndSubFacilities();
	}
	public void insertNewStdFacility(){
		SubFacilityVO subFacilityVO=new SubFacilityVO();
		subFacilityVO.setFacilityTypeCode("OTHER");
		long rawSubFacilityId=13;
		facilityService.insertNewStdFacility(subFacilityVO, rawSubFacilityId, bindingResult, userObject);
		long subFacilityId=subFacilityVO.getSubFacilityId();
		log.debug("insertNewStdFacility returned *****"+subFacilityId);
	}
	public void createMasterAndSubFacilities(){
		MasterFacilityVO masterFacilityVO=new MasterFacilityVO();
		masterFacilityVO.setMasterFacilityName("MasterFacility03261320");
		SubFacilityVO subFacilityVO=new SubFacilityVO();
		subFacilityVO.setFacilityName("SubFacility03261320");
		subFacilityVO.setFacilityTypeCode("OTHER");
		long rawSubFacilityId=13;
		facilityService.createMasterAndSubFacilities(masterFacilityVO, subFacilityVO, rawSubFacilityId, bindingResult, userObject);
		log.debug("createMasterAndSubFacilities returned transaction message : "+((MapBindingResult)bindingResult).getTargetMap().get(AppConstants.NYEHM_TRANSACTION_MESSAGE.getName()));
		log.debug("createMasterAndSubFacilities returned transaction message : "+((MapBindingResult)bindingResult).getTargetMap().get(AppConstants.NYEHM_DB_TRANSACTION_ERROR_CODE.getName()));
	}

}
