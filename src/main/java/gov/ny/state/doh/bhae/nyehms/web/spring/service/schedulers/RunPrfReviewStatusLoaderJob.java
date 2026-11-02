package gov.ny.state.doh.bhae.nyehms.web.spring.service.schedulers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * Quartz scheduler class to load PRF Review Status data 
 * 
 * @author ska03
 *
 */
public class RunPrfReviewStatusLoaderJob extends QuartzJobBean{
		private PrfReviewStatusLoader prfReviewStatusLoader;
		
		private static final Logger log = LoggerFactory.getLogger(RunPrfReviewStatusLoaderJob.class);
		
		
		public void setPrfReviewStatusLoader(PrfReviewStatusLoader prfReviewStatusLoader) {
			this.prfReviewStatusLoader = prfReviewStatusLoader;
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
			prfReviewStatusLoader.loadPrfReviewStatus();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
}
