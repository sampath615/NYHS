package gov.ny.state.doh.bhae.nyehms.web.spring.view.uploads;

/**
 * PDF of the record-search STATENOs that failed (see {@link AbstractKeyValuePdfView}).
 */
public class UploadedRecordSearchStatnosPdfView extends AbstractKeyValuePdfView {

	public UploadedRecordSearchStatnosPdfView() {
		super("Record Search STATENOs Failed", "STATENO", "Message");
	}
}
