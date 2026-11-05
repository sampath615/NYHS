package gov.ny.state.doh.bhae.nyehms.web.spring.dao.standardization.facility;

import java.util.Map;

import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements.CallParam;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.Row;

/** Parameters of the PKG_STANDARDIZATION procedure calls of the facility DAOs. */
final class FacilityCalls {

	private FacilityCalls() {
	}

	/**
	 * An IN parameter from the caller's map, bound as MyBatis bound it: with the value's own type, or with the
	 * declared JDBC type's (BIGINT -> Long, VARCHAR -> String) when it is null or missing.
	 */
	static CallParam in(Map<Object, Object> map, String key, Class<?> nullType) {
		Object value = map.get(key);
		return NativeStatements.in(value, value == null ? nullType : value.getClass());
	}

	/** An OUT cursor whose rows are returned as read (the caller groups them). */
	static CallParam rows() {
		return NativeStatements.cursor((Row row) -> row);
	}
}
