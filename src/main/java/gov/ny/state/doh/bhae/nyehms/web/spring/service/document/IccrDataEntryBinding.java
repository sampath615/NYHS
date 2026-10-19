package gov.ny.state.doh.bhae.nyehms.web.spring.service.document;

import java.beans.PropertyEditorSupport;
import java.sql.Timestamp;

import org.springframework.web.bind.WebDataBinder;

/**
 * How the posted ICCR data entry form is bound to the {@code RawIccrDoc} entity (and its {@code RawIccrTest}
 * rows), used by {@code ICCRDocumentController}:
 * <ul>
 * <li>to the fields directly (the entities have no setters);</li>
 * <li>only the fields of the form: the document id, the worker, the dates and the status of the row are
 * set by {@link IccrDocMapping#toRawDoc}, never taken from the request;</li>
 * <li>a date as the former insert's FN_TO_DATE(..., 'MM/DD/YYYY') read it: an invalid date or the empty date
 * mask ("../../....") is null.</li>
 * </ul>
 */
public final class IccrDataEntryBinding {

	private static final String[] FORM_FIELDS = {"firstName", "middleName", "lastName", "dob", "personAddress", "personCity",
			"personState", "personZip", "personCounty", "personPhone", "jurisdictionProvidingInfo", "provName", "provAddress",
			"provCity", "provState", "provZip", "provCounty", "provPhone", "resultsReceived", "comments",
			"iccrTestList[*].dateOfTest", "iccrTestList[*].testType", "iccrTestList[*].testResults",
			"iccrTestList[*].testResultCopies", "iccrTestList[*].testResultLogs"};

	private IccrDataEntryBinding() {
	}

	/** Configures the binder of the posted form. */
	public static void configure(WebDataBinder binder) {
		binder.initDirectFieldAccess();
		binder.setAllowedFields(FORM_FIELDS);
		binder.registerCustomEditor(Timestamp.class, new PropertyEditorSupport() {
			@Override
			public void setAsText(String text) {
				setValue(OracleConversions.fnToDate(text));
			}
		});
	}
}
