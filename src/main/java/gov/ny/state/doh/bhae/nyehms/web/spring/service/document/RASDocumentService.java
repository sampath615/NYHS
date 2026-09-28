package gov.ny.state.doh.bhae.nyehms.web.spring.service.document;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import gov.ny.state.doh.bhae.nyehms.web.spring.dao.document.RASDocumentDAO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.RASDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;

@Service
public class RASDocumentService {
	
	
	@Autowired
	RASDocumentDAO rasDocumentDAO;

	public RASDocVO getRASDocVO(int documentId, boolean sierraFlag, BindingResult result, UserObject userObject) {
		
		RASDocVO rasDoVo = null;
		
		if(sierraFlag) {
			rasDoVo = rasDocumentDAO.getSierraResponseData(documentId);
			
			rasDoVo.setGENESFOUNDTEXT(rasDocumentDAO.getRASDocGeneNames(rasDoVo.getSEQ_RESULT_ID()));
			
			List<String> drugClasses = rasDocumentDAO.getRASDocDrugClasses(rasDoVo.getSEQ_RESULT_ID());
			
			if(drugClasses.contains("PI")) {
				rasDoVo.setRESISTANCE_DRUG_CLASS_PR_STR("Y");
			}
			if(drugClasses.contains("INSTI")) {
				rasDoVo.setRESISTANCE_DRUG_CLASS_IN_STR("Y");
			}
			if(drugClasses.contains("NRTI")) {
				rasDoVo.setRESISTANCE_DRUG_CLASS_NRTI_STR("Y");
			}
			if(drugClasses.contains("NNRTI")) {
				rasDoVo.setRESISTANCE_DRUG_CLASS_NNRTI_STR("Y");
			}
		}else {
			rasDoVo = rasDocumentDAO.getRASDoc(documentId);
			
			rasDoVo.setRESISTANCE_DRUG_CLASS_PR_STR(convertResistanceDrugClassToBoolean(rasDoVo.getRESISTANCE_DRUG_CLASS_PR()));
			rasDoVo.setRESISTANCE_DRUG_CLASS_NRTI_STR(convertResistanceDrugClassToBoolean(rasDoVo.getRESISTANCE_DRUG_CLASS_NRTI()));
			rasDoVo.setRESISTANCE_DRUG_CLASS_NNRTI_STR(convertResistanceDrugClassToBoolean(rasDoVo.getRESISTANCE_DRUG_CLASS_NNRTI()));
			rasDoVo.setRESISTANCE_DRUG_CLASS_IN_STR(convertResistanceDrugClassToBoolean(rasDoVo.getRESISTANCE_DRUG_CLASS_IN()));

		}
		
		return rasDoVo;
	}
	
	public String convertResistanceDrugClassToBoolean(int intDrugClass) {
		String strDrugClass = "";
		
		if (intDrugClass > 0){
			strDrugClass = "Y";
		} else if (intDrugClass == 0){
			strDrugClass = "N";
		} else if (intDrugClass < 0){
			strDrugClass = "";
		}
		
		return strDrugClass;
	}
	
	public String getLinkedResDocuments(int documentId) {
		List<Integer> linkedDocIds = rasDocumentDAO.getLinkedResDocs(documentId);
		
		if (linkedDocIds.contains(Integer.valueOf(documentId))) {
			linkedDocIds.remove(Integer.valueOf(documentId));
		}
		
		if (linkedDocIds.size() == 0) {
			return "";
		} else {
			return linkedDocIds.toString();
		}
	}


	public void updateResistanceRasDocuments(int documentId, BindingResult bindingResult, UserObject userObject) {
		
		Map<Object, Object> inputOutputParameterMap = new HashMap<Object, Object>();
		
		inputOutputParameterMap.put("documentId", documentId);
		inputOutputParameterMap.put("user_id", userObject.getWorkerId());
		
		
		int isDocumentLinked = rasDocumentDAO.isDocumentLinked(documentId);
		
		if (isDocumentLinked == 0 ) {
			
			inputOutputParameterMap.put("rasElig", "X");
			
			rasDocumentDAO.updateGenSeqRasElig(inputOutputParameterMap);
			
		} else if (isDocumentLinked == 1 ) {
			
			int linkedRecCt = rasDocumentDAO.getLinkedRecCount(documentId);
			
			if (linkedRecCt == 1) {
				
				inputOutputParameterMap.put("rasElig", "I");
				rasDocumentDAO.updateGenSeqRasElig(inputOutputParameterMap);
				
				inputOutputParameterMap.put("vSeqStatus ", 2);
				rasDocumentDAO.updateRasSeqLinkStatus(inputOutputParameterMap);
				
				rasDocumentDAO.updateStdRasDoc(documentId);
				
			} else {
				
				List<Integer> linkedDocIds = rasDocumentDAO.getLinkedResDocs(documentId);
				
				for(Integer linkDocId :linkedDocIds) {
					
					if (linkDocId == documentId) {
						
						inputOutputParameterMap.put("rasElig", "I");
						inputOutputParameterMap.put("documentId", documentId);
						
					} else {
						inputOutputParameterMap.put("rasElig", "Y");
						inputOutputParameterMap.put("documentId", linkDocId);
						
					}
					
					rasDocumentDAO.updateGenSeqRasElig(inputOutputParameterMap);
					inputOutputParameterMap.put("vSeqStatus ", 2);
					rasDocumentDAO.updateRasSeqLinkStatus(inputOutputParameterMap);
					
				}
				
				rasDocumentDAO.updateStdRasDoc(documentId);
				
			}
			
			
			
		}
		
		
	}

}
