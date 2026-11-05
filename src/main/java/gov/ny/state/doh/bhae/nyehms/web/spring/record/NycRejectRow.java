package gov.ny.state.doh.bhae.nyehms.web.spring.record;

import java.sql.Timestamp;

/**
 * A rejected NYC surveillance record as the NYC reject document screen reads it (NycSurveillanceRejectRepository.findDetails):
 * the record's columns, its current state number and its disposition's descriptions.
 *
 * @param stateno PKG_CONSOLIDATION.FN_GIVE_CURRENT_STATENO of the record's STATENO
 * @param dispositionDesc DISPOSITION_DESC of its disposition (CODED_NYC_REJECT_DISPOSITION), if any
 * @param dispositionDescShort DISPOSITION_DESC_SHORT of its disposition, if any
 * @param updateDate UPDATE_DATE as text (getString)
 */
public record NycRejectRow(Integer documentId,
		String stateno,
		String nycnumber,
		Integer rejectDocumentId,
		String dispositionCode,
		String dispositionDesc,
		String dispositionDescShort,
		String statusnotes,
		String statusnoncase,
		String statusUpdateDate,
		String stateno2,
		String uploadFilename,
		String uploadUid,
		Timestamp uploadDate,
		String comments,
		String updateUid,
		String updateDate) {
}
