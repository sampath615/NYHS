package gov.ny.state.doh.bhae.nyehms.web.spring.record;

/**
 * A scanned document as the scanning screens read it (StdScanDocRepository.findScanDocInfo).
 *
 * @param fileName FILE_NAME of the scanned document (STD_SCAN_DOC)
 * @param stateno STATENO of its current person match (PERSON_DOC_ASSOCIATION), if any
 */
public record ScanDocInfoRow(String fileName, String stateno) {
}
