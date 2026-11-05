package gov.ny.state.doh.bhae.nyehms.web.spring.record;

/**
 * A row of the scanning report: the documents one worker scanned on one day.
 *
 * @param count the number of documents
 * @param scannedBy the worker's first and last name
 * @param createDate the day, MM/DD/YYYY
 */
public record ScanningCountRow(Long count, String scannedBy, String createDate) {
}
