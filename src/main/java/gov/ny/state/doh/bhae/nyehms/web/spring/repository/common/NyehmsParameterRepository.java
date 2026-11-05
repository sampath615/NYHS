package gov.ny.state.doh.bhae.nyehms.web.spring.repository.common;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.common.NyehmsParameter;

/** The application parameters (table NYEHMS_PARAMETER), read as projections. */
public interface NyehmsParameterRepository extends JpaRepository<NyehmsParameter, String> {

	/** A parameter value. */
	record ParamValue(String paramValue) {
	}

	/** All values of a parameter. */
	List<ParamValue> findByParamName(String paramName);

	/** The values of a parameter in effect at {@code now} (started, not ended). */
	List<ParamValue> findByParamNameAndEffectiveStartDateLessThanEqualAndEffectiveEndDateIsNullOrParamNameAndEffectiveStartDateLessThanEqualAndEffectiveEndDateAfter(
			String paramName, Timestamp now, String paramName2, Timestamp now2, Timestamp now3);
}
