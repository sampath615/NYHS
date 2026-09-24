package gov.ny.state.doh.bhae.nyehms.web.junit;

import gov.ny.state.doh.bhae.nyehms.web.spring.dao.CommonDAO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.common.AddressFieldVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.enums.AppConstants;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserAuthorization;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.CommonService;

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

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:/spring-context-junit.xml"})	
public class CommonTest {
		
		@Autowired
		UserAuthorization userAuthorization;
		
		@Autowired
		CommonService commonService;
		@Autowired
		CommonDAO commonDAO;
					
		private static Logger log = LoggerFactory.getLogger(CommonTest.class);
		private String nyehmsLog=AppConstants.nyehmLogDebugString.getName();
		
		//Declare test variables.
		final String tstWorkerId="141";
		final String tstWorkerRegionId="1015";
		
		//private Date tstStDate = new java.util.Date();
		//private Date tstEdDate = new java.util.Date();
		final String tstCallingProgram="NYEHMS";	
		UserObject userObject=new UserObject();
		
		String sessionId="junitsessionid";	
		BindingResult result = new MapBindingResult(new HashMap<String,String>(), "Sample");
		
		@Test
	    public void testMessage() throws Exception {			
			log.debug(nyehmsLog+"-----Test");			
			userObject.setUserName("Junit");			
			org.apache.ibatis.logging.LogFactory.useLog4JLogging();			
			//Test User Object Creation
			testUserObject();
			//Test Address Drop Down Pop up.
			testAddressDropDown();
			//Get all State Codes
			testStateCodes();
			//Get all County Codes
			testCountyCodes();
		}	
		
		
		public void testStateCodes(){			
			Map<String, String> map=commonService.getStateCodes(result, userObject, sessionId);			
			for(String str: map.keySet()){
				log.debug(nyehmsLog+"-----State Code :::::::::: is "+str);	
			}			
		}
		
		
		public void testCountyCodes(){			
			Map<String, String> map=commonService.getCountyCodes(result, userObject, sessionId);
			
			for(String str: map.keySet()){
				 log.debug(nyehmsLog+ " ----- County Code :::: "+str + " : " + map.get(str));	
			}
			
		}
		
		public void testUserObject(){			
			UserObject userObject=userAuthorization.getUserObject("VXT05");
			if(userObject!=null){
				log.debug(nyehmsLog+"-----User Worker ID is "+userObject.getWorkerId());	
			}			
		}
		
		
		public void testAddressDropDown(){			
			AddressFieldVO addressFieldVO= new AddressFieldVO();	
			
			addressFieldVO.setZip("12084");			
			addressFieldVO.setFieldName("CITY");
			//addressFieldVO.setFieldName("county");			
			
			if(addressFieldVO.getCountry().equals("Unknown") || addressFieldVO.getCountry().equals("")){
				addressFieldVO.setCountry("");
			}			
			//addressFieldVO.setCounty("Out of State");
			//addressFieldVO.setState("");			
			List<String> list=commonService.getAddressDropDownInfo(addressFieldVO, result, userObject, sessionId);
			
			for(String str: list){
				 log.debug(nyehmsLog+ " ----- Request String :::: "+str);	
			}
		}
	
	

}
