package gov.ny.state.doh.bhae.nyehms.web.spring.repository.common;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.common.RawPrfPartner;

/** The partners of the provider report forms as entered or received (table RAW_PRF_PARTNER); saveAndFlush inserts one. */
public interface RawPrfPartnerRepository extends JpaRepository<RawPrfPartner, Integer> {

	/**
	 * Marks the partners of a form as deleted (status 9).
	 *
	 * @return the number of rows changed
	 */
	@Modifying
	@Query("update RawPrfPartner partner set partner.currentProcessStatus = 9 where partner.prfDocId = ?1")
	int markDeleted(int prfDocId);

	/**
	 * Copies the partners of a form to a copied form (an HQL insert ... select): every column as it is, PRF_DOC_ID the
	 * new form, PARTNER_ID new from SEQ_PRF_PARTNER (Hibernate renders the sequence's next value in the select, as the
	 * former SEQ_PRF_PARTNER.NEXTVAL).
	 *
	 * @return the number of rows inserted
	 */
	@Modifying
	@Query("""
			insert into RawPrfPartner (prfDocId, fname, mname, lname, aliasFname, aliasMname, aliasLname, address, city,
						state, zip, plus4, homePhone, cellPhone, pager, email, dobText, age, gender, ethnicity, raceWhite, raceBlack,
						raceAsian, raceIndian, racePacific, raceUnknown, raceOther, raceOtherText, complexion, hair, height, weight,
						otherCharacteristics, placeOfEmployment, workHours, workPhone, workPhoneExt, maritalStatus, pregnant, spouse,
						dvScreen, dvRisk, partnerAssistanceStatus, partnerNotifiedDate, partnerStatus, partnerTestDate,
						identifiedByHdPs, createId, createDate, entryReviewId, entryReviewDate, highPriority, elicitedByPnap,
						elicitedByAct, prfSrcId, county, telephoneNo1, telephoneNo1Type, telephoneNo2, telephoneNo2Type,
						websiteMobApp1, websiteMobApp1Username, websiteMobApp2, websiteMobApp2Username, physicalDescription,
						sexAtBirth, additionalGenderIdentity)
				select ?1, source.fname, source.mname, source.lname, source.aliasFname, source.aliasMname, source.aliasLname,
						source.address, source.city, source.state, source.zip, source.plus4, source.homePhone, source.cellPhone,
						source.pager, source.email, source.dobText, source.age, source.gender, source.ethnicity, source.raceWhite,
						source.raceBlack, source.raceAsian, source.raceIndian, source.racePacific, source.raceUnknown,
						source.raceOther, source.raceOtherText, source.complexion, source.hair, source.height, source.weight,
						source.otherCharacteristics, source.placeOfEmployment, source.workHours, source.workPhone,
						source.workPhoneExt, source.maritalStatus, source.pregnant, source.spouse, source.dvScreen, source.dvRisk,
						source.partnerAssistanceStatus, source.partnerNotifiedDate, source.partnerStatus, source.partnerTestDate,
						source.identifiedByHdPs, source.createId, source.createDate, source.entryReviewId, source.entryReviewDate,
						source.highPriority, source.elicitedByPnap, source.elicitedByAct, source.prfSrcId, source.county,
						source.telephoneNo1, source.telephoneNo1Type, source.telephoneNo2, source.telephoneNo2Type,
						source.websiteMobApp1, source.websiteMobApp1Username, source.websiteMobApp2, source.websiteMobApp2Username,
						source.physicalDescription, source.sexAtBirth, source.additionalGenderIdentity
				from RawPrfPartner source
				where source.prfDocId = ?2
			""")
	int copyPartners(Integer newPrfDocId, Integer oldPrfDocId);
}
