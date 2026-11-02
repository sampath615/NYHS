package gov.ny.state.doh.bhae.nyehms.web.spring.service.document;

import gov.ny.state.doh.bhae.nyehms.web.spring.record.NdiCauseDocData;
import gov.ny.state.doh.bhae.nyehms.web.spring.record.NdiDocData;
import gov.ny.state.doh.bhae.nyehms.web.spring.repository.document.NdiDocRepository;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonUtil;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

/**
 * This is implementation of NDIDocumentService interface. This provides
 * implementation of the methods to interact with NDI document.
 *
 * @author vxp06
 */
@RequiredArgsConstructor
@Component
public class NDIDocumentService {

    //private Log log = LogFactory.getLog(this.getClass());
 
 
    private final NdiDocRepository ndiDocRepository;
 
    /**
     * {@inheritDoc}
     *
     * @param documentId
     * @return
     */
    public NdiDocData getNDIDoc(int docId) {
        NdiDocData nDIDocumentVO = ndiDocRepository.findDocument(docId);
        
        

    	String slash = "/";
    	String doubleDots = "..";
    	
    	StringBuilder DOD_TEXT = new StringBuilder();
    	
    	if(CommonUtil.isNotNullAndEmpty(nDIDocumentVO.stdNdiDoc().getDayDeath())){
    		DOD_TEXT.append(nDIDocumentVO.stdNdiDoc().getDayDeath());
    	}else{
    		DOD_TEXT.append(doubleDots);
    	}
    	
    	DOD_TEXT.append(slash);
    	
    	
    	if(CommonUtil.isNotNullAndEmpty(nDIDocumentVO.stdNdiDoc().getMonthDeath())){
    		DOD_TEXT.append(nDIDocumentVO.stdNdiDoc().getMonthDeath());
    	}else{
    		DOD_TEXT.append(doubleDots);
    	}
    	
    	DOD_TEXT.append(slash);
    	
    	if(CommonUtil.isNotNullAndEmpty(nDIDocumentVO.stdNdiDoc().getYearDeath())){
    		DOD_TEXT.append(nDIDocumentVO.stdNdiDoc().getYearDeath());
    	}else{
    		DOD_TEXT.append(doubleDots).append(doubleDots);
    	}
        return nDIDocumentVO.withDodText(DOD_TEXT.toString());
    }
    
    public NdiCauseDocData getNDICauseDoc(int docId){
    	
    	return ndiDocRepository.findCauseDocument(docId);
    }

}
