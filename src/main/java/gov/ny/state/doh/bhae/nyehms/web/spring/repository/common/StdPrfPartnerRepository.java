package gov.ny.state.doh.bhae.nyehms.web.spring.repository.common;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.common.StdPrfPartner;

/** The standardized partners of the provider report forms (table STD_PRF_PARTNER). */
public interface StdPrfPartnerRepository extends JpaRepository<StdPrfPartner, Integer> {

	/**
	 * Standardizes the partners of a form (an HQL insert ... select from RAW_PRF_PARTNER, with the former expressions:
	 * the texts UPPER(TRIM(...)), PARTNER_ASSISTANCE_STATUS only trimmed, numbers and dates as they are).
	 *
	 * @return the number of rows inserted
	 */
	@Modifying
	@Query("""
			insert into StdPrfPartner (address, age, aliasFname, aliasLname, aliasMname, cellPhone, city, complexion,
						county, createDate, createUid, currentGender, dobText, dvRisk, dvScreen, elicitedByAct, elicitedByPnap,
						email, entryReviewDate, entryReviewId, ethnicity, fname, hair, height, highPriority, homePhone,
						identifiedByHdPs, lname, maritalStatus, mname, otherCharacteristics, pager, partnerAssistanceStatus,
						partnerId, partnerNotifiedDateText, partnerStatus, partnerTestDateText, placeOfEmployment, plus4, pregnant,
						prfDocId, prfSrcId, raceAsian, raceBlack, raceIndian, raceOther, raceOtherText, racePacific, raceUnknown,
						raceWhite, spouse, state, weight, workHours, workPhone, workPhoneExt, zip, telephoneNo1, telephoneNo1Type,
						telephoneNo2, telephoneNo2Type, websiteMobApp1, websiteMobApp1Username, websiteMobApp2,
						websiteMobApp2Username, physicalDescription, sexAtBirth, additionalGenderIdentity)
				select upper(trim(source.address)), source.age, upper(trim(source.aliasFname)), upper(trim(source.aliasLname)),
						upper(trim(source.aliasMname)), upper(trim(source.cellPhone)), upper(trim(source.city)),
						upper(trim(source.complexion)), upper(trim(source.county)), source.createDate, upper(trim(source.createId)),
						upper(trim( source.gender)), upper(trim(source.dobText)), upper(trim(source.dvRisk)),
						upper(trim(source.dvScreen)), upper(trim(source.elicitedByAct)), upper(trim(source.elicitedByPnap)),
						upper(trim(source.email)), source.entryReviewDate, upper(trim(source.entryReviewId)),
						upper(trim(source.ethnicity)), upper(trim(source.fname)), upper(trim(source.hair)),
						upper(trim(source.height)), upper(trim(source.highPriority)), upper(trim(source.homePhone)),
						upper(trim(source.identifiedByHdPs)), upper(trim(source.lname)), upper(trim(source.maritalStatus)),
						upper(trim(source.mname)), upper(trim(source.otherCharacteristics)), upper(trim(source.pager)),
						trim(source.partnerAssistanceStatus), source.partnerId, upper(trim(source.partnerNotifiedDate )),
						upper(trim(source.partnerStatus)), upper(trim(source.partnerTestDate )),
						upper(trim(source.placeOfEmployment)), upper(trim(source.plus4)), upper(trim(source.pregnant)),
						source.prfDocId, source.prfSrcId, upper(trim(source.raceAsian)), upper(trim(source.raceBlack)),
						upper(trim(source.raceIndian)), upper(trim(source.raceOther)), upper(trim(source.raceOtherText)),
						upper(trim(source.racePacific)), upper(trim(source.raceUnknown)), upper(trim(source.raceWhite)),
						upper(trim(source.spouse)), upper(trim(source.state)), upper(trim(source.weight)),
						upper(trim(source.workHours)), upper(trim(source.workPhone)), upper(trim(source.workPhoneExt)),
						upper(trim(source.zip)), upper(trim(source.telephoneNo1)), upper(trim( source.telephoneNo1Type)), upper(trim(
						source.telephoneNo2)), upper(trim( source.telephoneNo2Type)), upper(trim( source.websiteMobApp1)),
						upper(trim( source.websiteMobApp1Username)), upper(trim( source.websiteMobApp2)), upper(trim(
						source.websiteMobApp2Username)), upper(trim( source.physicalDescription)), upper(trim( source.sexAtBirth)),
						upper(trim( source.additionalGenderIdentity))
				from RawPrfPartner source
				where source.prfDocId = ?1
			""")
	int insertFromRaw(int prfDocId);
}
