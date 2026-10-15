package gov.ny.state.doh.bhae.nyehms.web.spring.view.uploads;

import org.springframework.stereotype.Component;

/**
 * PDF of the record-search STATENOs that failed (see {@link AbstractKeyValuePdfView}).
 */
@Component
public class UploadedRecordSearchStatnosPdfView extends AbstractKeyValuePdfView {

	public UploadedRecordSearchStatnosPdfView() {
		super("Record Search STATENOs Failed", "STATENO", "Message");
	}
}
