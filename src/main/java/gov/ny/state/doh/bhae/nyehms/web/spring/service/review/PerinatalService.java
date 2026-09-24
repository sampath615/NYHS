package gov.ny.state.doh.bhae.nyehms.web.spring.service.review;

import java.util.List;
import java.util.Map;

import org.springframework.validation.BindingResult;

import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;

public interface PerinatalService {

	 Map<String, Object> loadFacilityMonitoringReport(String birthFirstYear, String birthFirstQuarter, String birthSecondYear, String birthSecondQuarter, 
			 String facilityName, String letterDate, BindingResult bindingResult, UserObject userObject);

	List<Map<String, String>> getFacilityNamesByBirthCriteria(String birthFirstYear, String birthFirstQuarter, String birthSecondYear, String birthSecondQuarter,
			BindingResult bindingResult, UserObject userObject);

	Map<String, Object> getDocumentIdAndPotentialMatchesForStatenos(String documentType, String reviewType,
			BindingResult bindingResult, UserObject userObject);

}
