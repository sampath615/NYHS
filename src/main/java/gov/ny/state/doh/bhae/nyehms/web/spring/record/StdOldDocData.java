package gov.ny.state.doh.bhae.nyehms.web.spring.record;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.document.StdStdOldDoc;

/**
 * An old STD (sexually transmitted disease) report as the STD old document screen shows it (StdOldDocument.jsp, model attribute
 * {@code stdOldDocVO}: {@code stdOldDocVO.stdStdOldDoc.<field>} etc.); replaces the former StdOldDocVO.
 *
 * @param stdStdOldDoc the standardized document (STD_STD_OLD_DOC)
 * @param stdSrcId STD_SRC_ID; its default if empty, as the former primitive property
 * @param age AGE; its default if empty, as the former primitive property
 * @param raceIndian RACE_INDIAN; its default if empty, as the former primitive property
 * @param raceBlack RACE_BLACK; its default if empty, as the former primitive property
 * @param raceAsian RACE_ASIAN; its default if empty, as the former primitive property
 * @param racePacific RACE_PACIFIC; its default if empty, as the former primitive property
 * @param raceWhite RACE_WHITE; its default if empty, as the former primitive property
 * @param raceMultiple RACE_MULTIPLE; its default if empty, as the former primitive property
 * @param raceOther RACE_OTHER; its default if empty, as the former primitive property
 * @param raceUnknown RACE_UNKNOWN; its default if empty, as the former primitive property
 * @param stateno STATENO of the current person match (PERSON_DOC_ASSOCIATION), if any
 * @param discardStatus DISCARD_STATUS of that match
 * @param rawpersonaddress rawPersonAddress; (raw1.PERSON_ADDRESS rawPersonAddress) its default if empty, as the former primitive property
 * @param rawprovideraddress rawProviderAddress; (raw1.PROV_ADDRESS rawProviderAddress) its default if empty, as the former primitive property
 */
public record StdOldDocData(StdStdOldDoc stdStdOldDoc,
		Long stdSrcId,
		Integer age,
		Boolean raceIndian,
		Boolean raceBlack,
		Boolean raceAsian,
		Boolean racePacific,
		Boolean raceWhite,
		Boolean raceMultiple,
		Boolean raceOther,
		Boolean raceUnknown,
		String stateno,
		String discardStatus,
		String rawpersonaddress,
		String rawprovideraddress) {
}
