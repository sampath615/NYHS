package gov.ny.state.doh.bhae.nyehms.web.spring.bo;

import gov.ny.state.doh.bhae.nyehms.web.spring.enums.AppConstants;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonUtil;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.GetGridObject;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProcessSearchCriteria {	
	
	private static Logger log = LoggerFactory.getLogger(ProcessSearchCriteria.class);
	private String nyehmsLog=AppConstants.nyehmLogDebugString.getName();
	
	@Autowired
	GetGridObject getGridObject;
	
	
	@Autowired
	CommonUtil commonUtil;
	
	
}
