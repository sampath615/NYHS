package gov.ny.state.doh.bhae.nyehms.web.spring.view.uploads;

import org.springframework.stereotype.Component;

/**
 * PDF of the updated PS messages (see {@link AbstractKeyValuePdfView}).
 */
@Component("updatedMessageResultsPdfView")
public class UpdatedMessagesPdfView extends AbstractKeyValuePdfView {

	public UpdatedMessagesPdfView() {
		super("Updated Messages", "DOCUMENT ID", "Message");
	}
}
