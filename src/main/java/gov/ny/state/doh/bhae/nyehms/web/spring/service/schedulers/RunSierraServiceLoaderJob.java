package gov.ny.state.doh.bhae.nyehms.web.spring.service.schedulers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * Quartz scheduler class to load sierra response to RAS tables 
 * 
 * @author ska03
 *
 */
public class RunSierraServiceLoaderJob extends QuartzJobBean{
		private SierraServiceLoader sierraServiceLoader;
		
		private static Logger log = LoggerFactory.getLogger(RunSierraServiceLoaderJob.class);
		
		
		public void setSierraServiceLoader(SierraServiceLoader sierraServiceLoader) {
			this.sierraServiceLoader = sierraServiceLoader;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {
		//Performing the Clean on Raw HIV Records
		try{
			log.error("started sending PRF Review status");
			sierraServiceLoader.loadRASDocumentsSierraResponse();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
}
