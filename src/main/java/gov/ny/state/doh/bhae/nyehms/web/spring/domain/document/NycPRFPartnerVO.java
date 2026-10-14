package gov.ny.state.doh.bhae.nyehms.web.spring.domain.document;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * A partner on an NYC Partner Referral Form, as the NYC PRF document screen shows and binds it.
 * <p>
 * The all-arguments constructor is used by NycprfPartnerRepository (JPQL {@code select new}): its
 * parameters follow the field order below, so keep that order in sync with the query.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NycPRFPartnerVO {	
	private String PATIENT_ID;
	private String NYCPRF_DOCUMENT_ID;
	private String FNAME;
	private String MNAME;
	private String LNAME;
	private String ALT_NAME;
	private String STD_ADDRESS;
	private String CITY;
	private String STATE;
	private String ZIP;
	private String PLUS4;
	private String HOME_PHONE;
	private String CELL_PHONE;
	private String PAGER;
	private String EMAIL;
	private String DOB_TEXT;
	private String AGE;
	private String CURRENT_GENDER;
	private String ETHNICITY;
	private String RACE_WHITE;
	private String RACE_BLACK;
	private String RACE_ASIAN;
	private String RACE_INDIAN;
	private String RACE_PACIFIC;
	private String RACE_UNKNOWN;
	private String RACE_OTHER;
	private String RACE_OTHER_TEXT;
	private String COMPLEXION_PARTNER;
	private String HAIR_PARTNER;
	private String HEIGHT;
	private String WEIGHT;
	private String PLACE_OF_EMPLOYMENT;
	private String WORK_HOURS;
	private String WORK_PHONE;
	private String MARITAL_CODE;
	private String PREGNANT;
	private String SPOUSE;
	private String DVSCREEN;
	private String RISK_VIOLENCE;
	private String PARTNER_ASSISTANCE_STATUS;
	private String PARTNER_TEST_DATE_TEXT;
	private String PARTNER_STATUS;
	private Date HDISP_DT;
	private String CREATE_UID;
	private Date CREATE_DATE;
	private Date INFORMED_DATE;
	private String HIGH_PRIORITY;
	private String ELICITED_BY_PNAP;
	private String ELICITED_BY_ACT;
	private String raw_address;
}
