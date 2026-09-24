package gov.ny.state.doh.bhae.nyehms.web.junit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import gov.ny.state.doh.bhae.nyehms.web.spring.jms.EnQueMessage;

/*
 * Author : Srikar Kummamuri
 * Date : 3/13/12
 * Description: Simple Junit test to test DB connectivity. Please refer to JDBC properties file to see which DB is being tested.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:/spring-context-junit.xml"})	
public class EnQueMessageTest {

	private static Logger log = LoggerFactory.getLogger(EnQueMessageTest.class);
	
	//@Autowired
	//private PersionService personService;
	
	@Autowired
	EnQueMessage enqueMessage;
	
	@Test
    public void testActiveMQ() throws Exception {			
		for(int i=0; i<10; i++){
			log.debug("***************************************************************************** Calling mesage Q Method for {}  time",i);
			
			//personService.getPersonName(1);
			
			//String qName ="TestMessageQ";
			
			String qName ="nyehms.jms.que";
			
			//PersonViewVO object = personService.getPersonName(1);
			
			//enqueMessage.sendPersonReport(qName, "This is a TestMessage");
		}
	
	}	
}
