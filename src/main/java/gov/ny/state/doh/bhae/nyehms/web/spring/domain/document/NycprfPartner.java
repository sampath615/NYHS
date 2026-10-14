package gov.ny.state.doh.bhae.nyehms.web.spring.domain.document;

import java.sql.Date;

/**
 * A partner listed on an NYC Partner Referral Form, as the NYC PRF document screen shows it
 * (read-only; replaces the former NycPRFPartnerVO class).
 * <p>
 * Built by NycprfPartnerRepository with a JPQL {@code select new}, whose select list must follow
 * the component order below. The upper-case component names are the property names the JSPs use
 * ({@code partnerList[i].LNAME}); JSP EL (Tomcat 11) and Spring's form tags read record components
 * directly. {@code raw_address} is the address as received (RAW_NYCPRF_PARTNER.PRTADDRESS).
 */
public record NycprfPartner(
		String PATIENT_ID,
		String NYCPRF_DOCUMENT_ID,
		String FNAME,
		String MNAME,
		String LNAME,
		String ALT_NAME,
		String STD_ADDRESS,
		String CITY,
		String STATE,
		String ZIP,
		String PLUS4,
		String HOME_PHONE,
		String CELL_PHONE,
		String PAGER,
		String EMAIL,
		String DOB_TEXT,
		String AGE,
		String CURRENT_GENDER,
		String ETHNICITY,
		String RACE_WHITE,
		String RACE_BLACK,
		String RACE_ASIAN,
		String RACE_INDIAN,
		String RACE_PACIFIC,
		String RACE_UNKNOWN,
		String RACE_OTHER,
		String RACE_OTHER_TEXT,
		String COMPLEXION_PARTNER,
		String HAIR_PARTNER,
		String HEIGHT,
		String WEIGHT,
		String PLACE_OF_EMPLOYMENT,
		String WORK_HOURS,
		String WORK_PHONE,
		String MARITAL_CODE,
		String PREGNANT,
		String SPOUSE,
		String DVSCREEN,
		String RISK_VIOLENCE,
		String PARTNER_ASSISTANCE_STATUS,
		String PARTNER_TEST_DATE_TEXT,
		String PARTNER_STATUS,
		Date HDISP_DT,
		String CREATE_UID,
		Date CREATE_DATE,
		Date INFORMED_DATE,
		String HIGH_PRIORITY,
		String ELICITED_BY_PNAP,
		String ELICITED_BY_ACT,
		String raw_address) {
}
