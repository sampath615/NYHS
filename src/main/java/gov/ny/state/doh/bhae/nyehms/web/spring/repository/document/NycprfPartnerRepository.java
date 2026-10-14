package gov.ny.state.doh.bhae.nyehms.web.spring.repository.document;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.NycprfPartner;
import gov.ny.state.doh.bhae.nyehms.web.spring.entity.document.StdNycprfPartner;
import gov.ny.state.doh.bhae.nyehms.web.spring.entity.document.StdNycprfPartnerId;

/**
 * Spring Data JPA repository for NYC PRF partners (replaces the MyBatis NycPRFPartnerDAO).
 */
public interface NycprfPartnerRepository extends Repository<StdNycprfPartner, StdNycprfPartnerId> {

	/**
	 * The partners listed on an NYC PRF document: the standardized partner data plus the address
	 * as received, built as the form object the NYC PRF document screen binds to. Each partner is
	 * returned once even if it was received more than once with the same address.
	 *
	 * @param nycprfDocumentId document id of the NYC PRF form
	 * @return the partners; empty if there are none
	 */
	@Query("""
			select distinct new gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.NycprfPartner(
						s.patientId,
						s.nycprfDocumentId,
						s.firstName,
						s.middleName,
						s.lastName,
						s.altName,
						s.address,
						s.city,
						s.state,
						s.zip,
						s.plus4,
						s.homePhone,
						s.cellPhone,
						s.pager,
						s.email,
						s.dobText,
						s.age,
						s.currentGender,
						s.ethnicity,
						s.raceWhite,
						s.raceBlack,
						s.raceAsian,
						s.raceIndian,
						s.racePacific,
						s.raceUnknown,
						s.raceOther,
						s.raceOtherText,
						s.complexion,
						s.hair,
						s.height,
						s.weight,
						s.placeOfEmployment,
						s.workHours,
						s.workPhone,
						s.maritalCode,
						s.pregnant,
						s.spouse,
						s.dvScreen,
						s.riskViolence,
						s.partnerAssistanceStatus,
						s.partnerTestDateText,
						s.partnerStatus,
						s.hdispDate,
						s.createUid,
						s.createDate,
						s.informedDate,
						s.highPriority,
						s.elicitedByPnap,
						s.elicitedByAct,
						r.address)
			from StdNycprfPartner s, RawNycprfPartner r
			where r.patientId = s.patientId
			  and r.nycprfDocumentId = :nycprfDocumentId
			""")
	List<NycprfPartner> findPartnersOfDocument(long nycprfDocumentId);
}
