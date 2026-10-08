package gov.ny.state.doh.bhae.nyehms.web.spring.domain.document;

import java.sql.Date;
import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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

	/**
	 * Empty partner, filled by form binding and by MyBatis.
	 */
	public NycPRFPartnerVO() {
	}

	/**
	 * Partner as read by NycprfPartnerRepository (JPQL constructor expression). The dates are
	 * date-only values, as the MyBatis mapper returned them.
	 */
	public NycPRFPartnerVO(
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
			LocalDate HDISP_DT,
			String CREATE_UID,
			LocalDate CREATE_DATE,
			LocalDate INFORMED_DATE,
			String HIGH_PRIORITY,
			String ELICITED_BY_PNAP,
			String ELICITED_BY_ACT,
			String raw_address) {
		this.PATIENT_ID = PATIENT_ID;
		this.NYCPRF_DOCUMENT_ID = NYCPRF_DOCUMENT_ID;
		this.FNAME = FNAME;
		this.MNAME = MNAME;
		this.LNAME = LNAME;
		this.ALT_NAME = ALT_NAME;
		this.STD_ADDRESS = STD_ADDRESS;
		this.CITY = CITY;
		this.STATE = STATE;
		this.ZIP = ZIP;
		this.PLUS4 = PLUS4;
		this.HOME_PHONE = HOME_PHONE;
		this.CELL_PHONE = CELL_PHONE;
		this.PAGER = PAGER;
		this.EMAIL = EMAIL;
		this.DOB_TEXT = DOB_TEXT;
		this.AGE = AGE;
		this.CURRENT_GENDER = CURRENT_GENDER;
		this.ETHNICITY = ETHNICITY;
		this.RACE_WHITE = RACE_WHITE;
		this.RACE_BLACK = RACE_BLACK;
		this.RACE_ASIAN = RACE_ASIAN;
		this.RACE_INDIAN = RACE_INDIAN;
		this.RACE_PACIFIC = RACE_PACIFIC;
		this.RACE_UNKNOWN = RACE_UNKNOWN;
		this.RACE_OTHER = RACE_OTHER;
		this.RACE_OTHER_TEXT = RACE_OTHER_TEXT;
		this.COMPLEXION_PARTNER = COMPLEXION_PARTNER;
		this.HAIR_PARTNER = HAIR_PARTNER;
		this.HEIGHT = HEIGHT;
		this.WEIGHT = WEIGHT;
		this.PLACE_OF_EMPLOYMENT = PLACE_OF_EMPLOYMENT;
		this.WORK_HOURS = WORK_HOURS;
		this.WORK_PHONE = WORK_PHONE;
		this.MARITAL_CODE = MARITAL_CODE;
		this.PREGNANT = PREGNANT;
		this.SPOUSE = SPOUSE;
		this.DVSCREEN = DVSCREEN;
		this.RISK_VIOLENCE = RISK_VIOLENCE;
		this.PARTNER_ASSISTANCE_STATUS = PARTNER_ASSISTANCE_STATUS;
		this.PARTNER_TEST_DATE_TEXT = PARTNER_TEST_DATE_TEXT;
		this.PARTNER_STATUS = PARTNER_STATUS;
		this.HDISP_DT = HDISP_DT == null ? null : Date.valueOf(HDISP_DT);
		this.CREATE_UID = CREATE_UID;
		this.CREATE_DATE = CREATE_DATE == null ? null : Date.valueOf(CREATE_DATE);
		this.INFORMED_DATE = INFORMED_DATE == null ? null : Date.valueOf(INFORMED_DATE);
		this.HIGH_PRIORITY = HIGH_PRIORITY;
		this.ELICITED_BY_PNAP = ELICITED_BY_PNAP;
		this.ELICITED_BY_ACT = ELICITED_BY_ACT;
		this.raw_address = raw_address;
	}
	
    
	
	

}
