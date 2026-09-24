package gov.ny.state.doh.bhae.nyehms.web.spring.service.document;

import gov.ny.state.doh.bhae.nyehms.web.spring.dao.document.NDIDocumentDAO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.NDICauseDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.NDIDocumentVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * This is implementation of NDIDocumentService interface. This provides
 * implementation of the methods to interact with NDI document.
 *
 * @author vxp06
 */
@Component
public class NDIDocumentServiceImpl implements NDIDocumentService {

    //private Log log = LogFactory.getLog(this.getClass());
 
 
    @Autowired
    NDIDocumentDAO nDIDocumentDAO;
 
    /**
     * {@inheritDoc}
     *
     * @param documentId
     * @return
     */
    @Override
    public NDIDocumentVO getNDIDoc(int docId) {
        NDIDocumentVO nDIDocumentVO = nDIDocumentDAO.getNDIDoc(docId);
        
        

    	String slash = "/";
    	String doubleDots = "..";
    	
    	StringBuilder DOD_TEXT = new StringBuilder();
    	
    	if(CommonUtil.isNotNullAndEmpty(nDIDocumentVO.getDAY_DEATH())){
    		DOD_TEXT.append(nDIDocumentVO.getDAY_DEATH());
    	}else{
    		DOD_TEXT.append(doubleDots);
    	}
    	
    	DOD_TEXT.append(slash);
    	
    	
    	if(CommonUtil.isNotNullAndEmpty(nDIDocumentVO.getMONTH_DEATH())){
    		DOD_TEXT.append(nDIDocumentVO.getMONTH_DEATH());
    	}else{
    		DOD_TEXT.append(doubleDots);
    	}
    	
    	DOD_TEXT.append(slash);
    	
    	if(CommonUtil.isNotNullAndEmpty(nDIDocumentVO.getYEAR_DEATH())){
    		DOD_TEXT.append(nDIDocumentVO.getYEAR_DEATH());
    	}else{
    		DOD_TEXT.append(doubleDots).append(doubleDots);
    	}
    	
    	nDIDocumentVO.setDOD_TEXT(DOD_TEXT.toString());
    	
        return nDIDocumentVO;
    }
    
    @Override
    public NDICauseDocVO getNDICauseDoc(int docId){
    	
    	return nDIDocumentDAO.getNDICauseDoc(docId);
    }

}
