package gov.ny.state.doh.bhae.nyehms.web.spring.repository.document;

import java.sql.Date;
import java.util.List;

import org.hibernate.query.NativeQuery;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.NycPRFPartnerVO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

/**
 * Native-query implementation of {@link NycprfPartnerQueries}.
 * <p>
 * The SQL is unchanged from the MyBatis mapper it replaces (NycPRFPartnerDAO.xml). Every column is
 * read with an explicit type, so values are converted exactly as MyBatis did: text and numbers with
 * {@code ResultSet.getString}, the three dates with {@code getDate} (date only, {@link java.sql.Date}).
 * Rows are not loaded as entities, so they never go through the persistence context.
 * <p>
 * One change to the SQL: the mapper selected {@code std.PARTNER_TEST_DATE_TEXT} twice. Hibernate rejects
 * duplicate column names in a native query, and selecting a column twice does not change the result
 * (also not with DISTINCT), so the second copy was dropped.
 */
class NycprfPartnerQueriesImpl implements NycprfPartnerQueries {

	private static final String PARTNERS_OF_DOCUMENT = """
			   SELECT DISTINCT std.PATIENT_ID ,
			       std.NYCPRF_DOCUMENT_ID  ,
			       std.FNAME,
			       std.MNAME,
			       std.LNAME,
			       std.ALT_NAME ,
			       std.ADDRESS as STD_ADDRESS,
			       std.CITY,
			       std.STATE,
			       std.ZIP,
			      std.PLUS4,
			       std.HOME_PHONE,
			       std.CELL_PHONE,
			       std.PAGER,
			       std.EMAIL,
			       std.DOB_TEXT,
			       std.AGE,
			       std.CURRENT_GENDER ,
			       std.ETHNICITY,
			       std.RACE_WHITE,
			       std.RACE_BLACK,
			       std.RACE_ASIAN,
			       std.RACE_INDIAN,
			       std.RACE_PACIFIC,
			       std.RACE_UNKNOWN,
			       std.RACE_OTHER,
			       std.RACE_OTHER_TEXT,
			       std.COMPLEXION_PARTNER ,
			       std.HAIR_PARTNER ,
			       std.HEIGHT,
			       std.WEIGHT,
			       std.PLACE_OF_EMPLOYMENT,
			       std.WORK_HOURS,
			       std.WORK_PHONE,
			       std.MARITAL_CODE ,
			       std.PREGNANT,
			       std.SPOUSE,
			       std.DVSCREEN ,
			       std.RISK_VIOLENCE ,
			       std.PARTNER_ASSISTANCE_STATUS,
			       std.PARTNER_TEST_DATE_TEXT ,
			       std.PARTNER_STATUS,
			       std.HDISP_DT HDISP_DT  ,
			       std.CREATE_UID ,
			       std.CREATE_DATE,
			       std.INFORMED_DATE,
			       std.HIGH_PRIORITY,
			       std.ELICITED_BY_PNAP,
			       std.ELICITED_BY_ACT,
			       raw1.PRTADDRESS  as raw_address
			  FROM RAW_NYCPRF_PARTNER    RAW1 , STD_NYCPRF_PARTNER   std
			  WHERE Raw1.PATIENTID   = std.PATIENT_ID  
			AND  RAW1.NYCPRF_DOCUMENT_ID = :docId
			""";

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<NycPRFPartnerVO> findPartnersOfDocument(int nycprfDocumentId) {
		@SuppressWarnings("unchecked")
		List<Object[]> rows = entityManager.createNativeQuery(PARTNERS_OF_DOCUMENT)
				.unwrap(NativeQuery.class)
				.addScalar("PATIENT_ID", String.class)
				.addScalar("NYCPRF_DOCUMENT_ID", String.class)
				.addScalar("FNAME", String.class)
				.addScalar("MNAME", String.class)
				.addScalar("LNAME", String.class)
				.addScalar("ALT_NAME", String.class)
				.addScalar("STD_ADDRESS", String.class)
				.addScalar("CITY", String.class)
				.addScalar("STATE", String.class)
				.addScalar("ZIP", String.class)
				.addScalar("PLUS4", String.class)
				.addScalar("HOME_PHONE", String.class)
				.addScalar("CELL_PHONE", String.class)
				.addScalar("PAGER", String.class)
				.addScalar("EMAIL", String.class)
				.addScalar("DOB_TEXT", String.class)
				.addScalar("AGE", String.class)
				.addScalar("CURRENT_GENDER", String.class)
				.addScalar("ETHNICITY", String.class)
				.addScalar("RACE_WHITE", String.class)
				.addScalar("RACE_BLACK", String.class)
				.addScalar("RACE_ASIAN", String.class)
				.addScalar("RACE_INDIAN", String.class)
				.addScalar("RACE_PACIFIC", String.class)
				.addScalar("RACE_UNKNOWN", String.class)
				.addScalar("RACE_OTHER", String.class)
				.addScalar("RACE_OTHER_TEXT", String.class)
				.addScalar("COMPLEXION_PARTNER", String.class)
				.addScalar("HAIR_PARTNER", String.class)
				.addScalar("HEIGHT", String.class)
				.addScalar("WEIGHT", String.class)
				.addScalar("PLACE_OF_EMPLOYMENT", String.class)
				.addScalar("WORK_HOURS", String.class)
				.addScalar("WORK_PHONE", String.class)
				.addScalar("MARITAL_CODE", String.class)
				.addScalar("PREGNANT", String.class)
				.addScalar("SPOUSE", String.class)
				.addScalar("DVSCREEN", String.class)
				.addScalar("RISK_VIOLENCE", String.class)
				.addScalar("PARTNER_ASSISTANCE_STATUS", String.class)
				.addScalar("PARTNER_TEST_DATE_TEXT", String.class)
				.addScalar("PARTNER_STATUS", String.class)
				.addScalar("HDISP_DT", Date.class)
				.addScalar("CREATE_UID", String.class)
				.addScalar("CREATE_DATE", Date.class)
				.addScalar("INFORMED_DATE", Date.class)
				.addScalar("HIGH_PRIORITY", String.class)
				.addScalar("ELICITED_BY_PNAP", String.class)
				.addScalar("ELICITED_BY_ACT", String.class)
				.addScalar("RAW_ADDRESS", String.class)
				.setParameter("docId", nycprfDocumentId)
				.getResultList();
		return rows.stream().map(NycprfPartnerQueriesImpl::toPartner).toList();
	}

	private static NycPRFPartnerVO toPartner(Object[] row) {
		NycPRFPartnerVO vo = new NycPRFPartnerVO();
		vo.setPATIENT_ID((String) row[0]);
		vo.setNYCPRF_DOCUMENT_ID((String) row[1]);
		vo.setFNAME((String) row[2]);
		vo.setMNAME((String) row[3]);
		vo.setLNAME((String) row[4]);
		vo.setALT_NAME((String) row[5]);
		vo.setSTD_ADDRESS((String) row[6]);
		vo.setCITY((String) row[7]);
		vo.setSTATE((String) row[8]);
		vo.setZIP((String) row[9]);
		vo.setPLUS4((String) row[10]);
		vo.setHOME_PHONE((String) row[11]);
		vo.setCELL_PHONE((String) row[12]);
		vo.setPAGER((String) row[13]);
		vo.setEMAIL((String) row[14]);
		vo.setDOB_TEXT((String) row[15]);
		vo.setAGE((String) row[16]);
		vo.setCURRENT_GENDER((String) row[17]);
		vo.setETHNICITY((String) row[18]);
		vo.setRACE_WHITE((String) row[19]);
		vo.setRACE_BLACK((String) row[20]);
		vo.setRACE_ASIAN((String) row[21]);
		vo.setRACE_INDIAN((String) row[22]);
		vo.setRACE_PACIFIC((String) row[23]);
		vo.setRACE_UNKNOWN((String) row[24]);
		vo.setRACE_OTHER((String) row[25]);
		vo.setRACE_OTHER_TEXT((String) row[26]);
		vo.setCOMPLEXION_PARTNER((String) row[27]);
		vo.setHAIR_PARTNER((String) row[28]);
		vo.setHEIGHT((String) row[29]);
		vo.setWEIGHT((String) row[30]);
		vo.setPLACE_OF_EMPLOYMENT((String) row[31]);
		vo.setWORK_HOURS((String) row[32]);
		vo.setWORK_PHONE((String) row[33]);
		vo.setMARITAL_CODE((String) row[34]);
		vo.setPREGNANT((String) row[35]);
		vo.setSPOUSE((String) row[36]);
		vo.setDVSCREEN((String) row[37]);
		vo.setRISK_VIOLENCE((String) row[38]);
		vo.setPARTNER_ASSISTANCE_STATUS((String) row[39]);
		vo.setPARTNER_TEST_DATE_TEXT((String) row[40]);
		vo.setPARTNER_STATUS((String) row[41]);
		vo.setHDISP_DT((Date) row[42]);
		vo.setCREATE_UID((String) row[43]);
		vo.setCREATE_DATE((Date) row[44]);
		vo.setINFORMED_DATE((Date) row[45]);
		vo.setHIGH_PRIORITY((String) row[46]);
		vo.setELICITED_BY_PNAP((String) row[47]);
		vo.setELICITED_BY_ACT((String) row[48]);
		vo.setRaw_address((String) row[49]);
		return vo;
	}
}
