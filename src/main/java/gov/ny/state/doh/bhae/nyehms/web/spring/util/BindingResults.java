package gov.ny.state.doh.bhae.nyehms.web.spring.util;

import java.util.HashMap;

import org.springframework.validation.BindingResult;
import org.springframework.validation.MapBindingResult;

/**
 * Factory methods for {@link BindingResult} instances that are not bound to a
 * request.
 */
public final class BindingResults {

	private BindingResults() {
	}

	/**
	 * Creates a new, empty {@link BindingResult} for calling service methods whose
	 * signature requires one when the caller has no model object to bind. Each
	 * call returns an independent instance (target: an empty map, object name
	 * "Sample").
	 *
	 * @return a new, empty binding result
	 */
	public static BindingResult newDummy() {
		return new MapBindingResult(new HashMap<Object, Object>(), "Sample");
	}
}
