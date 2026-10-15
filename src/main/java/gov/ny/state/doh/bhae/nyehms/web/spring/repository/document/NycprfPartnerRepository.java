package gov.ny.state.doh.bhae.nyehms.web.spring.repository.document;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import gov.ny.state.doh.bhae.nyehms.web.spring.record.NycPrfPartnerData;
import gov.ny.state.doh.bhae.nyehms.web.spring.entity.document.StdNycprfPartner;
import gov.ny.state.doh.bhae.nyehms.web.spring.entity.document.StdNycprfPartnerId;

/**
 * Spring Data JPA repository for NYC PRF partners (replaces the MyBatis NycPRFPartnerDAO).
 */
public interface NycprfPartnerRepository extends JpaRepository<StdNycprfPartner, StdNycprfPartnerId> {

	/**
	 * The partners listed on an NYC PRF document: the standardized partner data plus the address
	 * as received, built as the form object the NYC PRF document screen binds to. Each partner is
	 * returned once per received address (once if it was received more than once with the same address).
	 *
	 * @param nycprfDocumentId document id of the NYC PRF form
	 * @return the partners; empty if there are none
	 */
	@Query("""
			select distinct new gov.ny.state.doh.bhae.nyehms.web.spring.record.NycPrfPartnerData(stdNycprfPartner, rawNycprfPartner.address)
			from StdNycprfPartner stdNycprfPartner
			join RawNycprfPartner rawNycprfPartner
			on rawNycprfPartner.patientId = stdNycprfPartner.patientId
			and rawNycprfPartner.nycprfDocumentId = :nycprfDocumentId
			""")
	List<NycPrfPartnerData> findPartnersOfDocument(long nycprfDocumentId);

	/**
	 * Inserts the standardized partners (STD_NYCPRF_PARTNER) of a document from the partners as
	 * received (RAW_NYCPRF_PARTNER); replaces NycPRFDocumentDAO.writeRawPRFPartnerDataToStdTable, with
	 * the same conversions (race and gender codes, upper-case, trim, FN_TO_DATE), done in Oracle.
	 * The flags, CREATE_UID ('1', '0', '900') and the document id are written as text because the
	 * entity maps those columns as text; Oracle stores the same value as for the former numbers.
	 *
	 * @param documentId document id of the NYC PRF form
	 * @return the number of partners inserted
	 */
	@Modifying
	@Transactional
	@Query("""
			insert into StdNycprfPartner (
				raceWhite, raceBlack, raceAsian, raceOther, raceUnknown, racePacific, raceIndian, raceCd, ethnicity,
				patientId, nycprfDocumentId, partnrid, lastName, firstName, middleName, birthSex, currentGender, dvScreen,
				altName, address, city, state, zip, email, homePhone, cellPhone, dobText, age, workPhone, riskViolence,
				notifiedDate, complexion, hair, height, maritalCode, pregnant, partnerAssistanceStatus, enterDate,
				eharsDiagDateText, cityno, priority, source, partners, referral, disease1, disease2, code, interview,
				interviewType, referralType, printed, notsend, partnerStatus, elicitedByPnap, partnerTestDateText,
				updatedDate, informedDate, spouse, highPriority, indexCityno, dispo, hivdispoDateText, createUid, createDate)
			select
				case
					when upper(trim(rawNycprfPartner.prtrace)) = 'WHITE' then '1'
					else '0' end,
				case
					when upper(trim(rawNycprfPartner.prtrace)) = 'BLACK' then '1'
					else '0' end,
				case
					when upper(trim(rawNycprfPartner.prtrace)) = 'ASIAN' then '1'
					else '0' end,
				case
					when upper(trim(rawNycprfPartner.prtrace)) in ('3', 'OTHER') then '1'
					else '0' end,
				case
					when upper(trim(rawNycprfPartner.prtrace)) = 'UNKNOWN' then '1'
					else '0' end,
				case
					when upper(trim(rawNycprfPartner.prtrace)) like '%HAWAIIAN%' then '1'
					else '0' end,
				case
					when upper(trim(rawNycprfPartner.prtrace)) = '5' then '1'
					else '0' end,
				case
					when upper(trim(rawNycprfPartner.prtrace)) = 'WHITE' then '01'
					when upper(trim(rawNycprfPartner.prtrace)) = 'BLACK' then '02'
					when upper(trim(rawNycprfPartner.prtrace)) = 'ASIAN' then '04'
					when upper(trim(rawNycprfPartner.prtrace)) in ('3', 'OTHER') then '06'
					when upper(trim(rawNycprfPartner.prtrace)) = 'UNKNOWN' then '09'
					when upper(trim(rawNycprfPartner.prtrace)) like '%HAWAIIAN%' then '04'
					when upper(trim(rawNycprfPartner.prtrace)) = '5' then '05'
					else null end,
				case
					when upper(trim(rawNycprfPartner.prtrace)) = '3' then '01'
					when upper(trim(rawNycprfPartner.prtcurgender)) = '01' then '01'
					when upper(trim(rawNycprfPartner.prtcurgender)) = '02' then '02'
					when upper(trim(rawNycprfPartner.prtcurgender)) = '09' then '09'
					when upper(trim(rawNycprfPartner.prtcurgender)) = 'Y' then '01'
					when upper(trim(rawNycprfPartner.prtcurgender)) = 'N' then '02'
					when upper(trim(rawNycprfPartner.prtcurgender)) = 'UN' then '09'
					when upper(trim(rawNycprfPartner.prtcurgender)) = 'UNK' then '09'
					when upper(trim(rawNycprfPartner.prtcurgender)) = 'U' then '09' end,
				trim(rawNycprfPartner.patientId),
				cast(rawNycprfPartner.nycprfDocumentId as String),
				trim(rawNycprfPartner.partnerid),
				trim(upper(rawNycprfPartner.prtlname)),
				trim(upper(rawNycprfPartner.prtfname)),
				trim(upper(rawNycprfPartner.prtmi)),
				trim(upper(rawNycprfPartner.prtsexbirth)),
				case
					when upper(trim(rawNycprfPartner.prtcurgender)) = 'M' then 'M'
					when upper(trim(rawNycprfPartner.prtcurgender)) = 'F' then 'F'
					when upper(trim(rawNycprfPartner.prtcurgender)) = 'T' then 'T'
					when upper(trim(rawNycprfPartner.prtcurgender)) = 'U' then 'U'
					when upper(trim(rawNycprfPartner.prtcurgender)) = 'R' then 'R'
					when upper(trim(rawNycprfPartner.prtcurgender)) = 'X' then 'X'
					when upper(trim(rawNycprfPartner.prtcurgender)) = 'Y' then 'Y'
					when upper(trim(rawNycprfPartner.prtcurgender)) = 'H' then 'H'
					when upper(trim(rawNycprfPartner.prtcurgender)) = '1' then 'M'
					when upper(trim(rawNycprfPartner.prtcurgender)) = '2' then 'F'
					when upper(trim(rawNycprfPartner.prtcurgender)) = 'MTF' then 'F'
					when upper(trim(rawNycprfPartner.prtcurgender)) = 'FTM' then 'M'
					else null end,
				trim(upper(rawNycprfPartner.prtdviolencedone)),
				trim(upper(rawNycprfPartner.prtalias)),
				trim(upper(rawNycprfPartner.address)),
				trim(upper(rawNycprfPartner.prtcity)),
				trim(upper(rawNycprfPartner.prtstate)),
				trim(upper(rawNycprfPartner.prtzip)),
				trim(upper(rawNycprfPartner.prtemail)),
				trim(upper(rawNycprfPartner.prthphone)),
				trim(upper(rawNycprfPartner.prtcphone)),
				trim(upper(rawNycprfPartner.prtdob)),
				trim(rawNycprfPartner.prtage),
				trim(upper(rawNycprfPartner.wrktel)),
				trim(upper(rawNycprfPartner.prtrskofdviolence)),
				trim(upper(rawNycprfPartner.prtdtnofitied)),
				trim(upper(rawNycprfPartner.prtcomplex)),
				trim(upper(rawNycprfPartner.prthair)),
				trim(upper(rawNycprfPartner.prtheiwei)),
				trim(upper(rawNycprfPartner.prtmaritalstus)),
				trim(upper(rawNycprfPartner.prtpregnant)),
				trim(upper(rawNycprfPartner.prtassisstatus)),
				trim(upper(rawNycprfPartner.prtenterdt)),
				trim(upper(rawNycprfPartner.prtdtharsdx)),
				trim(upper(rawNycprfPartner.cityno)),
				trim(upper(rawNycprfPartner.priority)),
				rawNycprfPartner.source,
				rawNycprfPartner.p,
				trim(upper(rawNycprfPartner.referral)),
				trim(upper(rawNycprfPartner.disease1)),
				trim(upper(rawNycprfPartner.disease2)),
				trim(upper(rawNycprfPartner.code)),
				trim(upper(rawNycprfPartner.interv)),
				trim(upper(rawNycprfPartner.typeinterv)),
				trim(upper(rawNycprfPartner.typeref)),
				trim(upper(rawNycprfPartner.printed)),
				trim(upper(rawNycprfPartner.notsend)),
				case
					when upper(trim(rawNycprfPartner.prfHivstus)) = 'UNKNOWN' then 'U'
					when upper(trim(rawNycprfPartner.prfHivstus)) = 'NEGATIVE' then 'N'
					when upper(trim(rawNycprfPartner.prfHivstus)) = 'POSITIVE' then 'P'
					else null end,
				trim(upper(rawNycprfPartner.prfElicited)),
				trim(upper(rawNycprfPartner.prfTstdt)),
				function('fn_to_date' as Date, trim(rawNycprfPartner.updated), 'MM/DD/YYYY'),
				function('fn_to_date' as Date, trim(rawNycprfPartner.pridtinf), 'MM/DD/YYYY'),
				trim(upper(rawNycprfPartner.prfSpouse)),
				trim(upper(rawNycprfPartner.hpriority)),
				trim(upper(rawNycprfPartner.indexcityno)),
				trim(upper(rawNycprfPartner.prthvdispo)),
				trim(upper(rawNycprfPartner.prthvdispodt)),
				'900',
				cast(sysdate as Date)
			from RawNycprfPartner rawNycprfPartner
			where rawNycprfPartner.nycprfDocumentId = :documentId

			""")
	int insertStdPartnersOfDocument(long documentId);
}
