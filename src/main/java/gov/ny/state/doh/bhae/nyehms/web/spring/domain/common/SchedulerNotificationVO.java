package gov.ny.state.doh.bhae.nyehms.web.spring.domain.common;

import java.util.ArrayList;
import java.util.List;


/**
 * This Class is used to track the status of Scheduler nightly process.
 * 
 * @author ska03
 *
 */
public class SchedulerNotificationVO{
	
	private List<String> schedulerStatusObj = new ArrayList<String>();
	
	
	public List<String> getSchedulerStatusObj(){
		return this.schedulerStatusObj;
	}
	
	
	public void resetSchedulerStatusObj(){
		schedulerStatusObj.clear();
	}
	
	
}
