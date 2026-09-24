package gov.ny.state.doh.bhae.nyehms.web.junit.search;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import gov.ny.state.doh.bhae.nyehms.web.spring.bo.GetTblNameMap;
import gov.ny.state.doh.bhae.nyehms.web.spring.controller.person.PersonController;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.search.SearchPersonCriteriaVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.enums.AppConstants;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.assignment.AssignmentService;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.person.PersionService;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.GetSQLSession;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.search.SearchService;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.validation.BindingResult;
import org.springframework.validation.MapBindingResult;

//import org.hibernate.SessionFactory;
//import org.hibernate.cfg.Configuration;
//import org.hibernate.Session;

/*
 * Author : Srikar Kummamuri
 * Date : 8/10/12
 * Description: Simple Junit test to test DB connectivity. Please refer to JDBC properties file to see which DB is being tested.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:/spring-context-junit.xml"})		
public class SearchEngineTest {

	
	private static Logger log = LoggerFactory.getLogger(SearchEngineTest.class);
	private String nyehmsLog=AppConstants.nyehmLogDebugString.getName();
	
	Map bindingMap= new HashMap();
	BindingResult result = new MapBindingResult(bindingMap, "Sample");	



	//@Autowired
	//private SessionFactory sessionFactory;


	@Autowired
	private GetSQLSession sqlSession;		
	
	@Autowired
	private AssignmentService assignmentService;
	
	@Autowired
	private PersionService personService;	
	
	@Autowired
	private PersonController personController;
	
	@Autowired
	private GetTblNameMap getTblNameMap;
	
	@Autowired
	SearchService searchService;
	
			
	@Test
    public void testMessage() throws Exception {
		
		UserObject userObject=new UserObject();	
		String sessionId="junitsessionid";
		userObject.setUserName("juint");
		
		SearchPersonCriteriaVO searchPersonCriteriaVO = new SearchPersonCriteriaVO();
		searchPersonCriteriaVO.setLastName("k*");
		List<SearchPersonCriteriaVO> cursorout =
		searchService.searchPersonFields(searchPersonCriteriaVO, "N", "NORMAL", result, userObject, sessionId);
				
		log.debug(nyehmsLog+"-- cursorout size :: "+cursorout.size());	
		
		for(SearchPersonCriteriaVO searchPersonCriteriaVOObj :cursorout){		
			log.debug(nyehmsLog+"-- cursorout size :: "+searchPersonCriteriaVOObj.getFirstName());		
		}
		
	}
	
	
}
