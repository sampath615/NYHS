package gov.ny.state.doh.bhae.nyehms.web.spring.record;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.document.StdNycDeathDoc;

/**
 * An NYC death certificate as the NYC death document screen shows it (NycDeathDocument.jsp, model attribute
 * {@code stdNycDeathDocVO}: {@code stdNycDeathDocVO.stdNycDeathDoc.<field>} etc.); replaces the former NycDeathDocVO.
 *
 * @param stdNycDeathDoc the standardized document (STD_NYC_DEATH_DOC)
 * @param stateno STATENO of the current person match (PERSON_DOC_ASSOCIATION), if any
 * @param discardStatus DISCARD_STATUS of that match
 * @param resStreetNo RES_STREET_NO; its default if empty, as the former primitive property
 * @param resStreetDirect RES_STREET_DIRECT; its default if empty, as the former primitive property
 * @param resStreet RES_STREET; its default if empty, as the former primitive property
 * @param resStreetType RES_STREET_TYPE; its default if empty, as the former primitive property
 * @param resAptNo RES_APT_NO; its default if empty, as the former primitive property
 * @param causeOfDeath the cause of death shown, worked out by NycDeathDocumentService
 */
public record NycDeathDocData(StdNycDeathDoc stdNycDeathDoc,
		String stateno,
		String discardStatus,
		String resStreetNo,
		String resStreetDirect,
		String resStreet,
		String resStreetType,
		String resAptNo,
		String causeOfDeath) {

	/** The document as read (used by the JPQL constructor expression). */
	public NycDeathDocData(StdNycDeathDoc stdNycDeathDoc, String stateno, String discardStatus, String resStreetNo, String resStreetDirect, String resStreet, String resStreetType, String resAptNo) {
		this(stdNycDeathDoc, stateno, discardStatus, resStreetNo, resStreetDirect, resStreet, resStreetType, resAptNo, null);
	}

	/** The same document with {@code causeOfDeath}. */
	public NycDeathDocData withCauseOfDeath(String causeOfDeath) {
		return new NycDeathDocData(stdNycDeathDoc, stateno, discardStatus, resStreetNo, resStreetDirect, resStreet, resStreetType, resAptNo, causeOfDeath);
	}
}
