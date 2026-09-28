package gov.ny.state.doh.bhae.nyehms.web.spring.view.uploads;

/**
 * PDF of the PS confirmed STD upload errors (see {@link AbstractKeyValuePdfView}).
 */
public class PSConfirmedSTDPdfView extends AbstractKeyValuePdfView {

	public PSConfirmedSTDPdfView() {
		super("PS Confirmed STDs Errors", "CASEKEY", "Error Message");
	}
}
