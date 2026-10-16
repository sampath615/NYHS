package gov.ny.state.doh.bhae.nyehms.web.spring.record;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.document.StdDeathDoc;

/**
 * A death certificate as the death document screen shows it (DeathDocument.jsp, model attribute
 * {@code stdDeathDocVO}: {@code stdDeathDocVO.stdDeathDoc.<field>} etc.); replaces the former DeathDocVO.
 *
 * @param stdDeathDoc the standardized document (STD_DEATH_DOC)
 * @param stateno STATENO of the current person match (PERSON_DOC_ASSOCIATION), if any
 * @param discardStatus DISCARD_STATUS of that match
 * @param resStreetType res_street_type; its default if empty, as the former primitive property
 * @param resAptNo res_apt_no; its default if empty, as the former primitive property
 * @param resStreetNo res_street_no; its default if empty, as the former primitive property
 * @param resStreet res_street; its default if empty, as the former primitive property
 * @param resStreetDirect res_street_direct; its default if empty, as the former primitive property
 * @param causeOfDeath the cause of death shown, worked out by DeathDocumentService
 */
public record DeathDocData(StdDeathDoc stdDeathDoc,
		String stateno,
		String discardStatus,
		String resStreetType,
		String resAptNo,
		String resStreetNo,
		String resStreet,
		String resStreetDirect,
		String causeOfDeath) {

	/** The document as read (used by the JPQL constructor expression). */
	public DeathDocData(StdDeathDoc stdDeathDoc, String stateno, String discardStatus, String resStreetType, String resAptNo, String resStreetNo, String resStreet, String resStreetDirect) {
		this(stdDeathDoc, stateno, discardStatus, resStreetType, resAptNo, resStreetNo, resStreet, resStreetDirect, null);
	}

	/** The same document with {@code causeOfDeath}. */
	public DeathDocData withCauseOfDeath(String causeOfDeath) {
		return new DeathDocData(stdDeathDoc, stateno, discardStatus, resStreetType, resAptNo, resStreetNo, resStreet, resStreetDirect, causeOfDeath);
	}
}
