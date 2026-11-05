package gov.ny.state.doh.bhae.nyehms.web.spring.repository.common;

import java.util.ArrayList;
import java.util.List;

import gov.ny.state.doh.bhae.nyehms.web.spring.record.LoincTestTypeRow;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

/** The implementation of {@link LabLoincQueries} (found by Spring Data by its name). */
public class LabLoincQueriesImpl implements LabLoincQueries {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<LoincTestTypeRow> findWithTestType(String loincCode, String description, String testType, String unit, boolean noUnit) {
		StringBuilder jpql = new StringBuilder("""
				select new gov.ny.state.doh.bhae.nyehms.web.spring.record.LoincTestTypeRow(ll, clt)
				from CodedLabTestType clt, LabLoinc ll where clt.testTypeCd = ll.testTypeCd""");
		List<Object> parameters = new ArrayList<>();
		condition(jpql, parameters, " and ll.loincCd like ?", loincCode);
		condition(jpql, parameters, " and clt.description like ?", description);
		condition(jpql, parameters, " and clt.testType = ?", testType);
		condition(jpql, parameters, " and clt.unitOfResult = ?", unit);
		if (noUnit) {
			jpql.append(" and clt.unitOfResult is null");
		}
		TypedQuery<LoincTestTypeRow> query = entityManager.createQuery(jpql.toString(), LoincTestTypeRow.class);
		for (int i = 0; i < parameters.size(); i++) {
			query.setParameter(i + 1, parameters.get(i));
		}
		return query.getResultList();
	}

	/** Adds a condition with the next parameter when the value is given. */
	private static void condition(StringBuilder jpql, List<Object> parameters, String condition, String value) {
		if (value != null) {
			parameters.add(value);
			jpql.append(condition).append(parameters.size());
		}
	}
}
