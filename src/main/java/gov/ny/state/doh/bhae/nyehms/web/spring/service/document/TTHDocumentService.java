package gov.ny.state.doh.bhae.nyehms.web.spring.service.document;

import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.UnexpectedRollbackException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import gov.ny.state.doh.bhae.nyehms.web.spring.dao.document.TTHDocumentDAO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.RawTTHDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.enums.AppConstants;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonExceptionHandler;

@RequiredArgsConstructor
@Service
public class TTHDocumentService {

	private static final Logger log = LoggerFactory.getLogger(TTHDocumentService.class);
	private String nyehmsLog=AppConstants.nyehmLogDebugString.getName();	
	
	private final TTHDocumentDAO tTHDocumentDAO;
	private final CommonExceptionHandler commonExceptionHandler;	
	
	private final CommonDocumentService commonDocumentService;
		
//	@Override
//	public ADAPDocVO getADAPDoc(int docId, 
//									BindingResult result, 
//									UserObject userObject, 
//			if(rollbackFlag)

	@Transactional
	public void insertTTHDocData(RawTTHDocVO rawTTHDocVO, BindingResult result, UserObject userObject, String sessionID) throws UnexpectedRollbackException {
		boolean rollbackFlag=false;
		int newDocId=0;
		
		Map<Object, Object> map = new HashMap<>();
		map.put("p_document_type", "TTH");
		map.put("p_new_doc_id", "");	
		try{			
			commonDocumentService.getNewDocId(map);				
			newDocId=(Integer)map.get("p_new_doc_id");
			rawTTHDocVO.setDocumentId(newDocId);
			
			int resultCount  = tTHDocumentDAO.insertTTHDocData(rawTTHDocVO);
			
			if(resultCount < 1){
				log.error("{} Failed to insert the TTH information for {}", nyehmsLog,newDocId);
			}
		}catch (DataAccessException e) {
			rollbackFlag=true;
			log.error(nyehmsLog+" DataAccessException:: "+e.toString());	
			commonExceptionHandler.handleException(e, userObject,sessionID);				
		} finally{						
			if(rollbackFlag){					
				commonExceptionHandler.populateBindObjTrsnFail(result);
				log.error(nyehmsLog+" Exception thown and added to Binding Result Object---- ");
			}
		}
	

	}
	
	public void updateTTHDocData(RawTTHDocVO rawTTHDocVO, BindingResult result, UserObject userObject, String sessionID){
		boolean rollbackFlag=false;
		try{			
			tTHDocumentDAO.updateTTHDocData(rawTTHDocVO);		
		}catch (DataAccessException e) {
			rollbackFlag=true;
			log.error(nyehmsLog+" DataAccessException:: "+e.toString());	
			commonExceptionHandler.handleException(e, userObject,sessionID);				
		} finally{						
			if(rollbackFlag){					
				commonExceptionHandler.populateBindObjTrsnFail(result);
				log.error(nyehmsLog+" Exception thown and added to Binding Result Object---- ");
			}
		}
	}
	
	public RawTTHDocVO getTTHDocData(RawTTHDocVO rawTTHDocVO, BindingResult result, UserObject userObject, String sessionID){
		boolean errorFlag=false;
		try{
			rawTTHDocVO= tTHDocumentDAO.getTTHDocData(rawTTHDocVO);
		} catch (DataAccessException e) {
			errorFlag=true;
			log.error(nyehmsLog+"  "+ e.getMessage());
			commonExceptionHandler.handleException(e, userObject,sessionID);
		} catch (Exception e) {
			errorFlag=true;
			log.error(nyehmsLog+"  "+ e.getMessage());
			commonExceptionHandler.handleException(e, userObject,sessionID);
		}finally{						
			if(errorFlag)
				result.addError(new ObjectError("SQLError", "Transaction Failed. Please try again "));
		}
		
		return rawTTHDocVO;
	}
	
}
