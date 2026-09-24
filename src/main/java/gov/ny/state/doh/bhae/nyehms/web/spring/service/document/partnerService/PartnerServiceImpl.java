package gov.ny.state.doh.bhae.nyehms.web.spring.service.document.partnerService;


import gov.ny.state.doh.bhae.nyehms.web.spring.dao.document.CommonDocLabsDAO;
import gov.ny.state.doh.bhae.nyehms.web.spring.dao.document.PartnerServicesDocumentDAO;
import gov.ny.state.doh.bhae.nyehms.web.spring.dao.jdbc.ConnectionDAO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.partnerService.PsAssignmentView;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.partnerService.PsPartnerView;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonExceptionHandler;

//import gov.ny.state.doh.bhae.nyehms.web.spring.dao.jdbc.impl.JdbcConnectionDAO;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

/**
 * This class will execute the query and return the JSON object this will be
 * used for Historical partner search data. There are two views from which this
 * data will be populated PERSON_PARTNER_RISK_VW and PS_INVEST_DETAILS_VW
 *
 * @author vxp06
 */
@Service
public class PartnerServiceImpl implements PartnerService {

	@Autowired
	private ConnectionDAO connectionDAO ;
	
	
	@Autowired
	private PartnerServicesDocumentDAO partnerServicesDocumentDAO ;
	
	
	@Autowired
    private CommonExceptionHandler commonExceptionHandler;
	
	private static Logger log = LoggerFactory.getLogger(PartnerServiceImpl.class);

	
	/**
     * This will return JSON object for Person investigation details view
     * LEGACY_PS_ASSIGNMENT_VW
     *
     * @param documentId
     * @return
     */
    @Override
    public PsAssignmentView getInvestDetailsData(long documentId, UserObject userObject, BindingResult result) {
    	
    	
    	PsAssignmentView psAssignmentView = null;
         boolean rollbackFlag = false;
         log.debug(" fetching for docId : {}", documentId);
         try {
        	 psAssignmentView = partnerServicesDocumentDAO.getPartnerServicesDocData(documentId);
        	 
        	 Integer legacyAssignmentId = psAssignmentView.getLEGACY_ASSIGNMENT_ID();
        	 if(psAssignmentView != null && ( legacyAssignmentId != null && legacyAssignmentId != 0)){
        		 psAssignmentView.setPartnerList(getPartnerList(legacyAssignmentId));
         	 }
        	
         } catch (DataAccessException e) {
             rollbackFlag = true;
             log.error( e.getMessage());
             commonExceptionHandler.handleException(e, userObject, userObject.getSessionId());
         } catch (Exception e) {
             rollbackFlag = true;
             log.error( e.getMessage());
             commonExceptionHandler.handleException(e, userObject, userObject.getSessionId());
         } finally {
             if (rollbackFlag) {
                 commonExceptionHandler.populateBindObjTrsnFail(result);
             }
         }
        
        return psAssignmentView;
        
    }

    /**
     * This will return JSON object for LEGACY_PS_PERSON_PARTNER_VW
     *
     * @param partnerId
     * @param assignment_id
     * @return
     */
    @Override
    public PsPartnerView getPartnerRiskData(String partnerId, String assignment_id) {

        // check for null or empty arguments
        if (partnerId == null || partnerId.isEmpty()) {
            throw new IllegalArgumentException("partnerId should not be null");
        }

        // get the connection object
        Connection conn = connectionDAO.getConnectionObj();

        Statement statement = null;
        PsPartnerView obj = null;
        ResultSet rs = null;

        String query = "select * FROM    persons_partner pp LEFT OUTER JOIN ps_partner_risks ppr ON pp.partnrid = ppr.partner_id where PARTNRID=" + partnerId + " AND assignment_id =" + assignment_id;
        
//        SELECT   STATENO, PARTNRID,  WORK_TYPE, ASSIGNMENT_ID, GENRL_CD, RRT_CD, TESTLOC_CD, P_RISK_CD, RACE_CD, ETHNICCD,FNAME, MNAME, LNAME, ADDRESS, CITY,"
//                + "     STATE, ZIPCODE, PLUS4, TRACT_CENSUS, TRACT_SUFFIX_CENSUS,TELEPHONE, SPOUSE,"
//                + "    GENDER, DVSCREEN,  RISK_VIOLENCE, AGE,  NOTIF_DT, HDISP_DT, F_EXP_DT, L_EXP_DT, EXP_FREQ,"
//                + "     HDISP_UID, REFERRED_BY, PREGNANT, NUMBER_OF_WEEKS_PREGNANT,DISCL_CD, DISCL_DT, CASEWORKER_UID_DISCLOSURE_INTE, STATUS_PROVIDER,"
//                + "    STATUSDT_PROVIDER,    EXAM_DT_PARTNER, TEST_TYPE_PARTNER, RESULT_PARTNER, EXAM_PROVIDER_PARTNER, HEIGHT_PARTNER, BUILD_PARTNER, HAIR_PARTNER, COMPLEXION_PARTNER,"
//                + "     PLACE_OF_EMPLOYMENT_PARTNER, HDISP_CD,PARTNER_CO_FIPS, POSTESTC, REFPCD,REFLABCD, STATUS_CD_PARTNER_ASSIGNMENT, MARITALCD, INVEST_AGEN,"
//                + "     REFPART, REFPOSTLAB, OOJ_NO, OOJ_AR, REFOOJ, REFOOJCD, DUE_DATE, PART_LAT, PART_LONG, UPDATE_UID, UPDATE_DT, CREATEID,"
//                + "     CREATEDT, HIGH_PRIORITY, FLAG_CD, HEIGHT, RACE_INDIAN, RACE_BLACK, RACE_ASIAN, RACE_PACIFIC, RACE_WHITE, RACE_MULTIPLE,RACE_OTHER, RACE_UNKNOWN,"
//                + "     ALIAS_LNAME,   ALIAS_FNAME,     ALIAS_MNAME,  EMAIL_ADDRESS, CELL_PHONE, PAGER, DOB_TEXT, HIV_STATUS_PARTNER_CD, TEST_DT_PARTNER, WEIGHT, WORK_PHONE, WORK_HOURS,"
//                + "     PARTNER_ELICITED_BY_PNAP,   PARTNER_ELICITED_BY_ACT, RACE_OTHER_TEXT, SUFFIX, AKA, MAIDEN, COUNTRY_CD, BIRTH_SEX, ADD_GENDER, TYPE_REFERRAL,"
//                + "     PARTNER_TYPE,     NOTIFY,     NOTIFY_PLAN,     NOTIFY_METHOD,     PREV_HIV_TEST,     SELF_RPT_RES,     DATE_LAST_HIV_TEST,     REF_TO_TESTING,     REF_DATE,"
//                + "     TESTED,     POSTESTC_NEW,     OTHER_IDENTIFIERS,     REF2,     ATTEND_FIRST_APPT,     WEB_ADDRESS_SCREEN,     RACE_ASIAN_SUB,     RACE_PACIFIC_SUB,     FIELD_TESTING,"
//                + "     INTERNET_OUTCOME,     TEST_RESULTS,     DATE_COLLECTED,     SWM,     SWF,     SEX_W_TRANS,     SHARED_DRUG_EQUIP,     IDU,     COMMENTS,     RISK_OTHER from LEGACY_PS_PERSON_PARTNER_VW WHERE PARTNRID=" + partnerId + " AND assignment_id =" + assignment_id;

        try {

            statement = conn.createStatement();
            rs = statement.executeQuery(query);

            while (rs.next()) {

                obj = new PsPartnerView(rs.getString("STATENO"),
                        rs.getInt("PARTNRID"),
                        rs.getString("WORK_TYPE"),
                        rs.getInt("ASSIGNMENT_ID"),
                        rs.getString("GENRL_CD"),
                        rs.getString("RRT_CD"),
                        rs.getString("TESTLOC_CD"),
                        rs.getString("P_RISK_CD"),
                        rs.getString("RACE_CD"),
                        rs.getString("ETHNICCD"),
                        rs.getString("FNAME"),
                        rs.getString("MNAME"),
                        rs.getString("LNAME"),
                        rs.getString("ADDRESS"),
                        rs.getString("CITY"),
                        rs.getString("STATE"),
                        rs.getString("ZIPCODE"),
                        rs.getString("PLUS4"),
                        rs.getString("TRACT_CENSUS"),
                        rs.getString("TRACT_SUFFIX_CENSUS"),
                        rs.getString("TELEPHONE"),
                        rs.getString("SPOUSE"),
                        rs.getString("GENDER"),
                        rs.getString("DVSCREEN"),
                        rs.getString("RISK_VIOLENCE"),
                        rs.getInt("AGE"),
                        rs.getDate("NOTIF_DT"),
                        rs.getDate("HDISP_DT"),
                        rs.getString("F_EXP_DT"),
                        rs.getString("L_EXP_DT"),
                        rs.getString("EXP_FREQ"),
                        rs.getString("HDISP_UID"),
                        rs.getString("REFERRED_BY"),
                        rs.getString("PREGNANT"),
                        rs.getInt("NUMBER_OF_WEEKS_PREGNANT"),
                        rs.getString("DISCL_CD"),
                        rs.getDate("DISCL_DT"),
                        rs.getString("CASEWORKER_UID_DISCLOSURE_INTE"),
                        rs.getString("STATUS_PROVIDER"),
                        rs.getString("STATUSDT_PROVIDER"),
                        rs.getString("EXAM_DT_PARTNER"),
                        rs.getString("TEST_TYPE_PARTNER"),
                        rs.getString("RESULT_PARTNER"),
                        rs.getString("EXAM_PROVIDER_PARTNER"),
                        rs.getInt("HEIGHT_PARTNER"),
                        rs.getString("BUILD_PARTNER"),
                        rs.getString("HAIR_PARTNER"),
                        rs.getString("COMPLEXION_PARTNER"),
                        rs.getString("PLACE_OF_EMPLOYMENT_PARTNER"),
                        rs.getString("HDISP_CD"),
                        rs.getString("PARTNER_CO_FIPS"),
                        rs.getString("POSTESTC"),
                        rs.getString("REFPCD"),
                        rs.getString("REFLABCD"),
                        rs.getString("STATUS_CD_PARTNER_ASSIGNMENT"),
                        rs.getString("MARITALCD"),
                        rs.getString("INVEST_AGEN"),
                        rs.getString("REFPART"),
                        rs.getString("REFPOSTLAB"),
                        rs.getString("OOJ_NO"),
                        rs.getString("OOJ_AR"),
                        rs.getString("REFOOJ"),
                        rs.getString("REFOOJCD"),
                        rs.getString("DUE_DATE"),
                        rs.getString("PART_LAT"),
                        rs.getString("PART_LONG"),
                        rs.getString("UPDATE_UID"),
                        rs.getDate("UPDATE_DT"),
                        rs.getString("CREATEID"),
                        rs.getDate("CREATEDT"),
                        rs.getInt("HIGH_PRIORITY"),
                        rs.getInt("FLAG_CD"),
                        rs.getString("HEIGHT"),
                        rs.getInt("RACE_INDIAN"),
                        rs.getInt("RACE_BLACK"),
                        rs.getInt("RACE_ASIAN"),
                        rs.getInt("RACE_PACIFIC"),
                        rs.getInt("RACE_WHITE"),
                        rs.getInt("RACE_MULTIPLE"),
                        rs.getInt("RACE_OTHER"),
                        rs.getInt("RACE_UNKNOWN"),
                        rs.getString("ALIAS_LNAME"),
                        rs.getString("ALIAS_FNAME"),
                        rs.getString("ALIAS_MNAME"),
                        rs.getString("EMAIL_ADDRESS"),
                        rs.getString("CELL_PHONE"),
                        rs.getString("PAGER"),
                        rs.getString("DOB_TEXT"),
                        rs.getString("HIV_STATUS_PARTNER_CD"),
                        rs.getString("TEST_DT_PARTNER"),
                        rs.getString("WEIGHT"),
                        rs.getString("WORK_PHONE"),
                        rs.getString("WORK_HOURS"),
                        rs.getInt("PARTNER_ELICITED_BY_PNAP"),
                        rs.getInt("PARTNER_ELICITED_BY_ACT"),
                        rs.getString("RACE_OTHER_TEXT"),
                        rs.getString("SUFFIX"),
                        rs.getString("AKA"),
                        rs.getString("MAIDEN"),
                        rs.getString("COUNTRY_CD"),
                        rs.getString("BIRTH_SEX"),
                        rs.getString("ADD_GENDER"),
                        rs.getString("TYPE_REFERRAL"),
                        rs.getString("PARTNER_TYPE"),
                        rs.getString("NOTIFY"),
                        rs.getString("NOTIFY_PLAN"),
                        rs.getString("NOTIFY_METHOD"),
                        rs.getString("PREV_HIV_TEST"),
                        rs.getString("SELF_RPT_RES"),
                        rs.getDate("DATE_LAST_HIV_TEST"),
                        rs.getString("REF_TO_TESTING"),
                        rs.getDate("REF_DATE"),
                        rs.getString("TESTED"),
                        rs.getString("POSTESTC_NEW"),
                        rs.getString("OTHER_IDENTIFIERS"),
                        rs.getString("REF2"),
                        rs.getString("ATTEND_FIRST_APPT"),
                        rs.getString("WEB_ADDRESS_SCREEN"),
                        rs.getString("RACE_ASIAN_SUB"),
                        rs.getString("RACE_PACIFIC_SUB"),
                        rs.getString("FIELD_TESTING"),
                        rs.getString("INTERNET_OUTCOME"),
                        rs.getString("TEST_RESULTS"),
                        rs.getDate("DATE_COLLECTED"),
                        rs.getString("SWM"),
                        rs.getString("SWF"),
                        rs.getString("SEX_W_TRANS"),
                        rs.getString("SHARED_DRUG_EQUIP"),
                        rs.getString("IDU"),
                        rs.getString("COMMENTS"),
                        rs.getString("RISK_OTHER"));
            }

        } catch (SQLException ex) {
        	log.error("SQL Exception occured",ex);
        }
        finally{
        	try {
                if (conn != null) {
                	conn.close();
                }
                if (statement != null) {
                	statement.close();
                }
                if(rs != null){
                	rs.close();
                }
            } catch (SQLException sqlException) {
                  log.error("Exception while updating LOINC details of LOINC Code {}",sqlException.getMessage());
            }
        }
        return obj;
    }

    /**
     * This method will return list of all the partners associated with given
     * assignment id
     *
     * @param assignmentId
     * @return
     */
    public List<Integer> getPartnerList(int assignmentId) {

        // get the connection object
        Connection conn = connectionDAO.getConnectionObj();

        Statement statement = null;
        ResultSet rs = null;
        ArrayList<Integer> partnerList = new ArrayList<Integer>();
       // String partnerListQuery = "SELECT partnrid FROM NYEHMSLOCAL_OWNER.LEGACY_PS_PERSON_PARTNER_VW where assignment_id =" + assignmentId;
        String partnerListQuery = "SELECT partnrid FROM LEGACY_PS_PERSON_PARTNER_VW where assignment_id =" + assignmentId;


        try {
            statement = conn.createStatement();
            rs = statement.executeQuery(partnerListQuery);
            while (rs.next()) {
                partnerList.add(rs.getInt("partnrid"));
            }

        } catch (SQLException ex) {
        	log.error("SQL Exception occured",ex);
        }finally{
        	try {
                if (conn != null) {
                	conn.close();
                }
                if (statement != null) {
                	statement.close();
                }
                if(rs != null){
                	rs.close();
                }
            } catch (SQLException sqlException) {
                  log.error("Exception while updating LOINC details of LOINC Code {}",sqlException.getMessage());
            }
        }

        return partnerList;
    }
    
    @Override
    public List<Map<String, String>> getCodedSexualBehaviors(){
    	
    	return partnerServicesDocumentDAO.getCodedSexualBehaviorsList();
    }
}
