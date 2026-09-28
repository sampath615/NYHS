package gov.ny.state.doh.bhae.nyehms.web.spring.view.uploads;

/**
 * PDF of the updated PS messages (see {@link AbstractKeyValuePdfView}).
 */
public class UpdatedMessagesPdfView extends AbstractKeyValuePdfView {

	public UpdatedMessagesPdfView() {
		super("Updated Messages", "DOCUMENT ID", "Message");
	}
}
