package gov.ny.state.doh.bhae.nyehms.web.junit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import gov.ny.state.doh.bhae.nyehms.web.spring.controller.person.PersonController;

/*
 * Author : Srikar Kummamuri
 * Date : 3/13/12 
 * Description: Simple Junit test to test DB connectivity. Please refer to JDBC properties file to see which DB is being tested.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:/spring-context-junit.xml"})		
public class AjaxCalls {
	
	private static Logger log = LoggerFactory.getLogger(AjaxCalls.class);
	
	@Autowired
	PersonController personController; 
	
	@Test
    public void testajax() throws Exception {
	
	
		
		
		
	
	}
	
	

}
