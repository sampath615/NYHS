package gov.ny.state.doh.bhae.nyehms.web.spring.service.document.partnerService;


import gov.ny.state.doh.bhae.nyehms.web.spring.dao.document.PartnerServicesDocumentDAO;
import gov.ny.state.doh.bhae.nyehms.web.spring.repository.document.LegacyPsPersonPartnerRepository;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.partnerService.PsAssignmentView;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.partnerService.PsPartnerView;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonExceptionHandler;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

/**
 * This class will execute the query and return the JSON object this will be
 * used for Historical partner search data. There are two views from which this
 * data will be populated PERSON_PARTNER_RISK_VW and PS_INVEST_DETAILS_VW
 *
 * @author vxp06
 */
@RequiredArgsConstructor
@Service
public class PartnerService {

	private final LegacyPsPersonPartnerRepository legacyPsPersonPartnerRepository;

	@PersistenceContext
	private EntityManager entityManager;

	private static final String PARTNER_RISK_QUERY = "select * FROM    persons_partner pp LEFT OUTER JOIN ps_partner_risks ppr ON pp.partnrid = ppr.partner_id where PARTNRID = ? AND assignment_id = ?";
	
	
	private final PartnerServicesDocumentDAO partnerServicesDocumentDAO;
	
	
	private final CommonExceptionHandler commonExceptionHandler;
	
	private static final Logger log = LoggerFactory.getLogger(PartnerService.class);

	
	/**
     * This will return JSON object for Person investigation details view
     * LEGACY_PS_ASSIGNMENT_VW
     *
     * @param documentId
     * @return
     */
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
     * The partner and risk details of a partner of an assignment (persons_partner with its ps_partner_risks).
     * <p>
     * The query reads every column of both tables ({@code select *}), whose exact split between the two tables is not
     * known here, so it runs as SQL on the JPA session's connection; its values are bound, no longer part of the SQL.
     * An SQL error is logged and gives null, as before.
     *
     * @return the partner; the last row if there are several; null if there is none
     */
    @Transactional(readOnly = true)
    public PsPartnerView getPartnerRiskData(String partnerId, String assignment_id) {
        if (partnerId == null || partnerId.isEmpty()) {
            throw new IllegalArgumentException("partnerId should not be null");
        }
        return entityManager.unwrap(Session.class).doReturningWork(connection -> {
            try (PreparedStatement statement = connection.prepareStatement(PARTNER_RISK_QUERY)) {
                statement.setString(1, partnerId);
                statement.setString(2, assignment_id);
                try (ResultSet rs = statement.executeQuery()) {
                    PsPartnerView obj = null;
                    while (rs.next()) {
                        obj = partnerView(rs);
                    }
                    return obj;
                }
            } catch (SQLException ex) {
                log.error("SQL Exception occured", ex);
                return null;
            }
        });
    }

    private static PsPartnerView partnerView(ResultSet rs) throws SQLException {

        return new PsPartnerView(rs.getString("STATENO"),
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

    /**
     * This method will return list of all the partners associated with given
     * assignment id
     *
     * @param assignmentId
     * @return
     */
    public List<Integer> getPartnerList(int assignmentId) {
        try {
            return legacyPsPersonPartnerRepository.findPartnerIdsOfAssignment(assignmentId);
        } catch (DataAccessException ex) {
            log.error("SQL Exception occured", ex);
            return new ArrayList<>();
        }
    }
    
    public List<Map<String, String>> getCodedSexualBehaviors(){
    	
    	return partnerServicesDocumentDAO.getCodedSexualBehaviorsList();
    }
}
