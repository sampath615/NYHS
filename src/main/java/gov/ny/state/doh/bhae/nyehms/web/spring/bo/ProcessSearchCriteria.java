package gov.ny.state.doh.bhae.nyehms.web.spring.bo;

import gov.ny.state.doh.bhae.nyehms.web.spring.enums.AppConstants;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonUtil;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.GetGridObject;

import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

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
	
	
	public String getPersonSearchCriteriaMap(HttpServletRequest request){	
		String fieldNames =AppConstants.searchPersonViewSearchFields.getName();				
		String fieldValuesString="";		
		StringTokenizer st = new StringTokenizer(fieldNames, ",");		
		int i=0;
		while(st.hasMoreTokens()) { 
			String tkn = st.nextToken().replaceAll("\\s", "");			
			String fieldValue=request.getParameter(tkn);
			if(fieldValue==null) fieldValue="";						
			if(i==0){			
				fieldValuesString=fieldValue;				
			}else{
				fieldValuesString=fieldValuesString+","+fieldValue;
			}
			i++;
		}	
		
		log.debug("{}  fieldValuesString {}",nyehmsLog,fieldValuesString);
		
		return fieldValuesString;		
	}
}
