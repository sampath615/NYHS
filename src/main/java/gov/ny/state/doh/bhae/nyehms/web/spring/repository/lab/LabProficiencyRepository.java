package gov.ny.state.doh.bhae.nyehms.web.spring.repository.lab;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.document.DocumentLocator;

/**
 * The lab proficiency filters: the filter view, the filter conditions, the labs, and the updates of a lab's filters
 * (replaces the raw JDBC in the former LabProficiencyVO, LabPFIVO, LabProficiencyConditionVO, AddLabProficiencyDataVO and
 * UpdateLabProficiencyDataVO, which concatenated the request's values into the SQL).
 * <p>
 * Native SQL, unchanged from those classes except that every value is a bound parameter: the writes use SYSDATE and the
 * reads the view's {@code NVL(OPERATOR, ' ')} ordering. The entity type is only the repository's anchor.
 */
public interface LabProficiencyRepository extends JpaRepository<DocumentLocator, Integer> {

	/** Every filter condition (rows: CLIA, PFI, LAB_NAME, FILTER_NUMBER, CONDITION_ID, FIELD_NAME, FILTER_TYPE, CONDITION, VALUE, OPERATOR, FILTER_FLAG_ACTIVE). */
	@Query(nativeQuery = true, value = """
			SELECT CLIA,PFI,LAB_NAME,FILTER_NUMBER,CONDITION_ID,FIELD_NAME, FILTER_TYPE, CONDITION,VALUE,NVL(OPERATOR,' ') OPERATOR, FILTER_FLAG_ACTIVE
			FROM NYEHMS_PROCESS_FILTER_VW
			ORDER BY PFI,FILTER_NUMBER,operator DESC
			""")
	List<Object[]> findAllFilterConditions();

	/** The conditions of a filter (rows: PFI, FILTER_NUMBER, FILTER_TYPE, CONDITION_ID, FIELD_NAME, CONDITION, VALUE, OPERATOR, FILTER_FLAG_ACTIVE). */
	@Query(nativeQuery = true, value = """
			SELECT pfi,FILTER_NUMBER, FILTER_TYPE, CONDITION_ID,FIELD_NAME,CONDITION,VALUE,NVL(OPERATOR,' ') OPERATOR, FILTER_FLAG_ACTIVE
			FROM NYEHMS_PROCESS_FILTER_VW
			where FILTER_NUMBER = :filterNumber
			ORDER by pfi,filter_number,operator DESC
			""")
	List<Object[]> findConditionsOfFilter(String filterNumber);

	/** The active condition kinds (CODED_PROCESS_CONDITION.CONDITION). */
	@Query(nativeQuery = true, value = "SELECT CONDITION FROM CODED_PROCESS_CONDITION WHERE ACTIVE_FLAG='Y'")
	List<String> findActiveConditions();

	/** The active labs (rows: CLIA, PFI, LAB_NAME), by name. */
	@Query(nativeQuery = true, value = "SELECT clia, pfi, NVL(lab_name,' ') lab_name FROM lab_physical_facility WHERE flag_active = 'Y' order by lab_name asc")
	List<Object[]> findActiveLabs();

	/** Activates or deactivates a lab's filter. */
	@Transactional
	@Modifying
	@Query(nativeQuery = true, value = """
			UPDATE LAB_PROCESS_PFI SET FLAG_ACTIVE = :flagActive, LAST_UPDATED_BY = :userId, LAST_UPDATED_DT = SYSDATE
			WHERE FILTER_NUMBER = :filterNumber AND PFI = :pfi
			""")
	int updateFilterActive(String flagActive, String userId, String filterNumber, String pfi);

	/** Adds a condition to a filter (CONDITION_ID is filled by the database). */
	@Transactional
	@Modifying
	@Query(nativeQuery = true, value = """
			INSERT INTO LAB_PROCESS_TEST_FILTERS (FILTER_NUMBER,FIELD_NAME,CONDITION,VALUE,OPERATOR,FLAG_ACTIVE,CREATED_BY,CREATED_DT, FILTER_TYPE)
			VALUES (:filterNumber, :fieldName, :condition, :value, :operator, 'Y', :userId, SYSDATE, :filterType)
			""")
	int insertCondition(String filterNumber, String fieldName, String condition, String value, String operator, String userId, String filterType);

	/** Changes a condition of a filter. */
	@Transactional
	@Modifying
	@Query(nativeQuery = true, value = """
			UPDATE LAB_PROCESS_TEST_FILTERS SET FIELD_NAME = :fieldName, FILTER_TYPE = :filterType, CONDITION = :condition, VALUE = :value,
			OPERATOR = :operator, LAST_UPDATED_BY = :userId, LAST_UPDATED_DT = SYSDATE
			WHERE FILTER_NUMBER = :filterNumber AND CONDITION_ID = :conditionId
			""")
	int updateCondition(String fieldName, String filterType, String condition, String value, String operator, String userId, String filterNumber,
			String conditionId);

	/** Deactivates a condition of a filter. */
	@Transactional
	@Modifying
	@Query(nativeQuery = true, value = """
			UPDATE LAB_PROCESS_TEST_FILTERS SET FLAG_ACTIVE = 'N', FILTER_TYPE = :filterType, LAST_UPDATED_BY = :userId, LAST_UPDATED_DT = SYSDATE
			WHERE FILTER_NUMBER = :filterNumber AND CONDITION_ID = :conditionId
			""")
	int deactivateCondition(String filterType, String userId, String filterNumber, String conditionId);

	/** Sets the filter type of a condition. */
	@Transactional
	@Modifying
	@Query(nativeQuery = true, value = """
			UPDATE LAB_PROCESS_TEST_FILTERS SET FILTER_TYPE = :filterType, LAST_UPDATED_BY = :userId, LAST_UPDATED_DT = SYSDATE
			WHERE FILTER_NUMBER = :filterNumber AND CONDITION_ID = :conditionId
			""")
	int updateConditionFilterType(String filterType, String userId, String filterNumber, String conditionId);
}
