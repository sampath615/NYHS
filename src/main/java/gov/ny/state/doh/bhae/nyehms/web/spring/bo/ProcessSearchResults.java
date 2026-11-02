package gov.ny.state.doh.bhae.nyehms.web.spring.bo;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.search.SearchPersonCriteriaVO;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
@Component
public class ProcessSearchResults {
	
	private static final Logger log = LoggerFactory.getLogger(ProcessSearchResults.class);
	
	//Replace the results row (each element) with identifier for italics and multiple value indicators. 
	public void	getProcessedPersonSearchResults
				(List<SearchPersonCriteriaVO> listToBeProcessed, HttpServletRequest request ){
		
		log.debug("****************************Processing Search results nad replacing strings  ");
		String pFN=request.getParameter("FIRST_NAME");
		String pLN=request.getParameter("LAST_NAME");
		String  pDOB = request.getParameter("DATE_OF_BIRTH");
		String pSSN=request.getParameter("SSN");
		String pDIN=request.getParameter("DIN");
		String pNYSID=request.getParameter("NYSID");
		String pDOD=request.getParameter("DATE_OD_DEATH");
		
		SearchPersonCriteriaVO spcvo = null;
		
		for(int i=0; i<listToBeProcessed.size(); i++){
			
			spcvo= listToBeProcessed.get(i);
						
			//Set the italics identifier for the searched string parameters.
			if( pFN != null && pFN.length()>0 && pFN.equalsIgnoreCase(spcvo.getFirstName()) ){
				spcvo.setFirstName(spcvo.getFirstName()+"~#&^italics~#&^");
			}if(pLN !=null && pLN.length()>0 && pLN.equalsIgnoreCase(spcvo.getLastName()) ){
				spcvo.setLastName(spcvo.getLastName()+"~#&^italics~#&^");
			}if(pDOB !=null && pDOB.length()>0 && pDOB.equals(spcvo.getDateOfBirth())){
				spcvo.setDateOfBirth(spcvo.getDateOfBirth());
			}if(pSSN !=null && pSSN.length()>0 && pSSN.equalsIgnoreCase(spcvo.getSsn()) ){
				spcvo.setSsn(spcvo.getSsn()+"~#&^italics~#&^");
			}if(pDIN !=null && pDIN.length()>0 && pDIN.equalsIgnoreCase(spcvo.getDin()) ){
				log.debug("***********************  Request DIN length is {} \"********** DB Din is {}",request.getParameter("DIN").length(),spcvo.getDin());
				spcvo.setDin(spcvo.getDin()+"~#&^italics~#&^");
			}if(pNYSID !=null && pNYSID.length()>0 && pNYSID.equalsIgnoreCase(spcvo.getNysid()) ){
				spcvo.setNysid(spcvo.getNysid()+"~#&^italics~#&^");
			}if(pDOD !=null && pDOD.length()>0 && pDOD.equals(spcvo.getDateOfDeath()) ){
				spcvo.setDateOfDeath(spcvo.getDateOfDeath());
			}
			
			//Set the multiple value indicator for fetched results			
//			if(spcvo.getFirstNameMultiIndicator().equals("Y")) 
//				spcvo.setFirstName(spcvo.getFirstName()+"*$@!multival*$@!");
//			if(spcvo.getLastNameMultiIndicator().equals("Y")) 
//				spcvo.setLastName(spcvo.getLastName()+"*$@!multival*$@!");
//			if(spcvo.getSsnMultiIndicator().equals("Y")) 
//				spcvo.setSsn(spcvo.getSsn()+"*$@!multival*$@!");
//			if(spcvo.getDinMultiIndicator().equals("Y")) 
//				spcvo.setDin(spcvo.getDin()+"*$@!multival*$@!");
//			if(spcvo.getNysidMultiIndicator().equals("Y")) 
//				spcvo.setNysid(spcvo.getNysid()+"*$@!multival*$@!");
//			if(spcvo.getDateOfBirthMultiIndicator().equals("Y"))
//				spcvo.setDateOfBirth(spcvo.getDateOfBirth()+"*$@!multival*$@!");
//			if(spcvo.getDateOfDeathMultiIndicator().equals("Y"))
//				spcvo.setDateOfDeath(spcvo.getDateOfDeath()+"*$@!multival*$@!");
//			
		}	
		// log.debug("**********Processing Search results END ***********  "+listToBeProcessed);
		
	}	

}
