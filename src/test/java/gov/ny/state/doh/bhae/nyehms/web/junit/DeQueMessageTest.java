package gov.ny.state.doh.bhae.nyehms.web.junit;

import javax.jms.JMSException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:/spring-context-junit.xml"})
public class DeQueMessageTest {
	
	private static Logger log = LoggerFactory.getLogger(DeQueMessageTest.class);
	
	//@Autowired
    //private JmsTemplate jmsTemplate;
	
	@Test
	public void receive() throws JMSException 
	{
		for(int i=0; i<10; i++){
		//Message message = jmsTemplate.receive("nyehms.jms.que");		
		//ObjectMessage om = (ObjectMessage) message;		
		//PersonNameVO po = (PersonNameVO) om.getObject();
		//log.debug("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%^^^^^^^^^^^^^^^&&&&&&&&&&&&&&&&&"+om);
		}
	}

}
