package gov.ny.state.doh.bhae.nyehms.web.spring.jms;

import java.io.Serializable;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.person.PersonViewVO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;




import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message; 
import javax.jms.ObjectMessage;
import javax.jms.Session;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

@Component
public class EnQueMessage {

	//@Autowired
    //private JmsTemplate jmsTemplate;
 
	private static Logger log = LoggerFactory.getLogger(EnQueMessage.class);
		
	public void sendPersonReport(final String qName, final Object object){
		 log.debug("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++Message Created Strp 1 ");
		
		/*
		 jmsTemplate.send(qName,
		        new MessageCreator() {
		          public Message createMessage(Session session) throws JMSException {		             
		               ObjectMessage objMessage = session.createObjectMessage();
		               objMessage.setObject((Serializable) object);
		               return objMessage;
		          }
		        }
		        );
		 */
		 
		        log.debug("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++Message Created Step 2");
		    
	}
	
}
