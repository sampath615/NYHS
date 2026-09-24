package gov.ny.state.doh.bhae.nyehms.web.spring.jms;


//import gov.ny.state.doh.bhae.nyehms.web.spring.domain.person.PersonViewVO;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

@Component
public class DeQueMessage implements MessageListener {
	
	//private Log log = LogFactory.getLog(DeQueMessage.class);
	
	
	@Override
	public void onMessage(Message message){
		
		 if ( message instanceof ObjectMessage ){
			 
			try{ 
			//ObjectMessage om = (ObjectMessage) message;		
			//PersonViewVO po = (PersonViewVO) om.getObject();
			//log.debug("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^!!!!!!!!!!!!!!!!!!!!!!!!!!! "+po.getLname());
			}catch(Exception e){
				//log.error("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@Exception occured   --     "+e.getMessage());
			}
				
		 }
		
		
	}

}
