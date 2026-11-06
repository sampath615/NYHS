package gov.ny.state.doh.bhae.nyehms.web.spring.dao.document;

import static gov.ny.state.doh.bhae.nyehms.web.spring.jpa.BoundValues.decimal;
import static gov.ny.state.doh.bhae.nyehms.web.spring.jpa.BoundValues.integer;
import static gov.ny.state.doh.bhae.nyehms.web.spring.jpa.BoundValues.longValue;
import static gov.ny.state.doh.bhae.nyehms.web.spring.jpa.BoundValues.text;
import static gov.ny.state.doh.bhae.nyehms.web.spring.jpa.BoundValues.toDate;

import java.sql.Timestamp;
import java.util.LinkedList;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.PRFPartnerVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.entity.common.RawPrfPartner;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.JpqlStatements;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.Rows;
import gov.ny.state.doh.bhae.nyehms.web.spring.repository.common.RawPrfPartnerRepository;
import lombok.RequiredArgsConstructor;

/**
 * The partners named on the provider report forms (formerly a MyBatis mapper with the same name and methods), through
 * JPQL and a Spring Data repository over RawPrfPartner and StdPrfPartner. No native SQL.
 * <p>
 * The two reads are JPQL whose select items carry the former labels; the rows fill PRFPartnerVO as MyBatis filled it
 * ({@link Rows#bean}). CREATE_DATE is shown as TO_CHAR(..., 'MM/DD/YYYY') showed it; ENTRY_REVIEW_DATE is read as text
 * (getString), as before. A new partner gets PARTNER_ID from SEQ_PRF_PARTNER; its CREATE_DATE is TO_DATE(createDate,
 * 'MM/DD/YYYY') done in Java (one- or two-digit month and day, four-digit year; anything else is refused, as Oracle
 * refused it) and its ENTRY_REVIEW_DATE the application server's clock (formerly SYSDATE).
 */
@Repository
@RequiredArgsConstructor
public class PRFPartnerDAO {

	/** The former getPRFPartner. */
	private static final String RAW_PARTNERS = """
			select raw.prfDocId as documentId, raw.partnerId as partnerId, raw.fname as fname, raw.mname as mname,
						raw.lname as lname, raw.aliasFname as aliasFname, raw.aliasMname as aliasMname, raw.aliasLname as aliasLname,
						raw.address as address, raw.city as city, raw.state as state, raw.zip as zip, raw.plus4 as plus4, raw.county
						as county, raw.homePhone as homePhone, raw.cellPhone as cellPhone, raw.pager as pager, raw.email as email,
						raw.dobText as dobText, raw.age as age, raw.gender as gender, raw.ethnicity as ethnicity, raw.raceWhite as
						raceWhite, raw.raceBlack as raceBlack, raw.raceAsian as raceAsian, raw.raceIndian as raceIndian,
						raw.racePacific as racePacific, raw.raceUnknown as raceUnknown, raw.raceOther as raceOther, raw.raceOtherText
						as raceOtherText, raw.complexion as complexion, raw.hair as hair, raw.height as height, raw.weight as weight,
						raw.otherCharacteristics as otherCharacteristics, raw.placeOfEmployment as placeOfEmployment, raw.workHours
						as workHours, raw.workPhone as workPhone, raw.workPhoneExt as workPhoneExt, raw.maritalStatus as
						maritalStatus, raw.pregnant as pregnant, raw.spouse as spouse, raw.dvScreen as dvScreen, raw.dvRisk as
						dvRisk, raw.partnerAssistanceStatus as partnerAssistanceStatus, raw.partnerNotifiedDate as
						partnerNotifiedDate, raw.partnerStatus as partnerStatus, raw.partnerTestDate as partnerTestDate,
						raw.identifiedByHdPs as identifiedByHdPs, raw.createId as createId, format(raw.createDate as 'MM/dd/yyyy') as
						createDate, raw.entryReviewId as entryReviewId, raw.entryReviewDateText as entryReviewDate, raw.highPriority
						as highPriority, raw.elicitedByPnap as elicitedByPnap, raw.elicitedByAct as elicitedByAct, raw.telephoneNo1
						as telephone1, raw.telephoneNo1Type as telephoneNo1Type1, raw.telephoneNo2 as telephone2,
						raw.telephoneNo2Type as telephoneNo2Type2, raw.websiteMobApp1 as websiteMobileApp1,
						raw.websiteMobApp1Username as websiteMobileApp1Username, raw.websiteMobApp2 as websiteMobileApp2,
						raw.websiteMobApp2Username as websiteMobileApp2Username, raw.physicalDescription as physicalDescription,
						raw.sexAtBirth as sexAtBirth, raw.additionalGenderIdentity as additionalPartGenderIdentity
				from RawPrfPartner raw where raw.prfDocId = ?1
			""";

	/** The former getStdPRFPartner: the standardized partners with the dates of their raw row. */
	private static final String STD_PARTNERS = """
			select std.prfDocId as documentId, std.partnerId as partnerId, std.fname as fname, std.mname as mname,
						std.lname as lname, std.aliasFname as aliasFname, std.aliasMname as aliasMname, std.aliasLname as aliasLname,
						std.address as address, std.city as city, std.state as state, std.zip as zip, std.plus4 as plus4, std.county
						as county, std.homePhone as homePhone, std.cellPhone as cellPhone, std.pager as pager, std.email as email,
						std.dobText as dobText, std.age as age, std.currentGender as gender, std.ethnicity as ethnicity,
						std.raceWhite as raceWhite, std.raceBlack as raceBlack, std.raceAsian as raceAsian, std.raceIndian as
						raceIndian, std.racePacific as racePacific, std.raceUnknown as raceUnknown, std.raceOther as raceOther,
						std.raceOtherText as raceOtherText, std.complexion as complexion, std.hair as hair, std.height as height,
						std.weight as weight, std.otherCharacteristics as otherCharacteristics, std.placeOfEmployment as
						placeOfEmployment, std.workHours as workHours, std.workPhone as workPhone, std.workPhoneExt as workPhoneExt,
						std.maritalStatus as maritalStatus, std.pregnant as pregnant, std.spouse as spouse, std.dvScreen as dvScreen,
						std.dvRisk as dvRisk, std.partnerAssistanceStatus as partnerAssistanceStatus, raw.partnerNotifiedDate as
						partnerNotifiedDate, std.partnerStatus as partnerStatus, raw.partnerTestDate as partnerTestDate,
						std.identifiedByHdPs as identifiedByHdPs, format(std.createDate as 'MM/dd/yyyy') as createDate,
						std.entryReviewId as entryReviewId, std.entryReviewDateText as entryReviewDate, std.highPriority as
						highPriority, std.elicitedByPnap as elicitedByPnap, std.elicitedByAct as elicitedByAct, std.telephoneNo1 as
						telephone1, std.telephoneNo1Type as telephoneNo1Type1, std.telephoneNo2 as telephone2, std.telephoneNo2Type
						as telephoneNo2Type2, std.websiteMobApp1 as websiteMobileApp1, std.websiteMobApp1Username as
						websiteMobileApp1Username, std.websiteMobApp2 as websiteMobileApp2, std.websiteMobApp2Username as
						websiteMobileApp2Username, std.physicalDescription as physicalDescription, std.sexAtBirth as sexAtBirth,
						std.additionalGenderIdentity as additionalPartGenderIdentity
				from StdPrfPartner std, RawPrfPartner raw where std.partnerId = raw.partnerId and std.prfDocId = ?1
			""";

	private final JpqlStatements jpql;
	private final RawPrfPartnerRepository rawPartners;

	/** The partners of a form as entered or received. */
	public LinkedList<PRFPartnerVO> getPRFPartner(int docId) throws DataAccessException {
		return new LinkedList<>(jpql.list(RAW_PARTNERS, Rows.bean(PRFPartnerVO.class), docId));
	}

	/**
	 * Inserts a partner entered on the data entry screen.
	 *
	 * @return 1
	 */
	@Transactional
	public int insertPRFPartner(PRFPartnerVO stdPRFPartnerVO) {
		PRFPartnerVO vo = stdPRFPartnerVO;
		Timestamp now = new Timestamp(System.currentTimeMillis());
		RawPrfPartner partner =
				RawPrfPartner.builder()
					.prfDocId(integer(vo.getDocumentId()))
					.fname(text(vo.getFname()))
					.mname(text(vo.getMname()))
					.lname(text(vo.getLname()))
					.aliasFname(text(vo.getAliasFname()))
					.aliasMname(text(vo.getAliasMname()))
					.aliasLname(text(vo.getAliasLname()))
					.address(text(vo.getAddress()))
					.city(text(vo.getCity()))
					.state(text(vo.getState()))
					.zip(text(vo.getZip()))
					.plus4(text(vo.getPlus4()))
					.homePhone(text(vo.getHomePhone()))
					.cellPhone(text(vo.getCellPhone()))
					.pager(text(vo.getPager()))
					.email(text(vo.getEmail()))
					.dobText(text(vo.getDobText()))
					.age(decimal(vo.getAge()))
					.gender(text(vo.getGender()))
					.ethnicity(text(vo.getEthnicity()))
					.raceWhite(text(vo.isRaceWhite()))
					.raceBlack(text(vo.isRaceBlack()))
					.raceAsian(text(vo.isRaceAsian()))
					.raceIndian(text(vo.isRaceIndian()))
					.racePacific(text(vo.isRacePacific()))
					.raceUnknown(text(vo.isRaceUnknown()))
					.raceOther(text(vo.isRaceOther()))
					.raceOtherText(text(vo.getRaceOtherText()))
					.complexion(text(vo.getComplexion()))
					.hair(text(vo.getHair()))
					.height(text(vo.getHeight()))
					.weight(text(vo.getWeight()))
					.otherCharacteristics(text(vo.getOtherCharacteristics()))
					.placeOfEmployment(text(vo.getPlaceOfEmployment()))
					.workHours(text(vo.getWorkHours()))
					.workPhone(text(vo.getWorkPhone()))
					.workPhoneExt(text(vo.getWorkPhoneExt()))
					.maritalStatus(text(vo.getMaritalStatus()))
					.pregnant(text(vo.getPregnant()))
					.spouse(text(vo.getSpouse()))
					.dvScreen(text(vo.getDvScreen()))
					.dvRisk(text(vo.getDvRisk()))
					.partnerAssistanceStatus(text(vo.getPartnerAssistanceStatus()))
					.partnerNotifiedDate(text(vo.getPartnerNotifiedDate()))
					.partnerStatus(text(vo.getPartnerStatus()))
					.partnerTestDate(text(vo.getPartnerTestDate()))
					.identifiedByHdPs(text(vo.isIdentifiedByHdPs()))
					.createId(text(vo.getCreateId()))
					.createDate(toDate(vo.getCreateDate()))
					.entryReviewId(text(vo.getEntryReviewId()))
					.entryReviewDate(now)
					.highPriority(text(vo.getHighPriority()))
					.elicitedByPnap(text(vo.getElicitedByPnap()))
					.elicitedByAct(text(vo.getElicitedByAct()))
					.prfSrcId(longValue(vo.getPrfSrcCd()))
					.county(text(vo.getCounty()))
					.telephoneNo1(text(vo.getTelephone1()))
					.telephoneNo1Type(text(vo.getTelephoneNo1Type1()))
					.telephoneNo2(text(vo.getTelephone2()))
					.telephoneNo2Type(text(vo.getTelephoneNo2Type2()))
					.websiteMobApp1(text(vo.getWebsiteMobileApp1()))
					.websiteMobApp1Username(text(vo.getWebsiteMobileApp1Username()))
					.websiteMobApp2(text(vo.getWebsiteMobileApp2()))
					.websiteMobApp2Username(text(vo.getWebsiteMobileApp2Username()))
					.physicalDescription(text(vo.getPhysicalDescription()))
					.sexAtBirth(text(vo.getSexAtBirth()))
					.additionalGenderIdentity(text(vo.getAdditionalPartGenderIdentity()))
					.build();
		rawPartners.saveAndFlush(partner);
		return 1;
	}

	/**
	 * Marks the partners of a form as deleted (status 9).
	 *
	 * @return the number of rows changed
	 */
	@Transactional
	public int deletePRFPartners(int documentID) {
		return rawPartners.markDeleted(documentID);
	}

	/** The standardized partners of a form. */
	public LinkedList<PRFPartnerVO> getStdPRFPartner(int documentID) {
		return new LinkedList<>(jpql.list(STD_PARTNERS, Rows.bean(PRFPartnerVO.class), documentID));
	}
}
