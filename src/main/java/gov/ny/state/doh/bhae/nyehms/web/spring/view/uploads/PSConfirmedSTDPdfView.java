package gov.ny.state.doh.bhae.nyehms.web.spring.view.uploads;

import org.springframework.stereotype.Component;

/**
 * PDF of the PS confirmed STD upload errors (see {@link AbstractKeyValuePdfView}).
 */
@Component("psConfirmedSTDErrorPdfView")
public class PSConfirmedSTDPdfView extends AbstractKeyValuePdfView {

	public PSConfirmedSTDPdfView() {
		super("PS Confirmed STDs Errors", "CASEKEY", "Error Message");
	}
}
