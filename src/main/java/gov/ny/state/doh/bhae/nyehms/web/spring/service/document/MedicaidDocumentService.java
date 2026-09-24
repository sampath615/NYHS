package gov.ny.state.doh.bhae.nyehms.web.spring.service.document;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.InsDeathDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.MedicaidDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.person.PersonViewVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;

import java.math.BigDecimal;
import java.util.Map;

import org.springframework.validation.BindingResult;


public interface MedicaidDocumentService {

   
    public MedicaidDocVO getMedicaidDoc(int docId, BindingResult result, UserObject userObject, String sessionID);

	public int writeRawMedicaidRecordDataToStdTable(Map<Object, Object> docVO,
			BigDecimal documentId);

	public PersonViewVO getStdMedicaidDocumentForClericalReview(int documentId);

	public Map<Object, Object> getRawMedicaidDocAndProvidersData(int documentId);

	public Map<Object, Object> getStdMedicaidDocAndProvidersData(int documentId);

	public InsDeathDocVO getInsDthDoc(int docId,
			BindingResult dummyBindingResult, UserObject userObject, String id);

}
