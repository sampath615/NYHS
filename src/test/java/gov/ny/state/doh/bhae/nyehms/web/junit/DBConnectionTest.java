package gov.ny.state.doh.bhae.nyehms.web.junit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/*
 * Author : Srikar Kummamuri
 * Date : 3/13/12
 * Description: Simple Junit test to test DB connectivity. Please refer to JDBC properties file to see which DB is being tested.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:/spring-context-junit.xml"})		
@Category(gov.ny.state.doh.bhae.nyehms.web.junit.BuildTests.class)
public class DBConnectionTest {	
	
	
	private static Logger log = LoggerFactory.getLogger(DBConnectionTest.class);
	
			
	@Test
    public void testMessage() throws Exception {
		
	}	
}
