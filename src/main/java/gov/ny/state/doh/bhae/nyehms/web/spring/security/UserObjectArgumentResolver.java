package gov.ny.state.doh.bhae.nyehms.web.spring.security;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * Resolves a controller method parameter of type {@link UserObject} from the
 * HTTP session attribute "UserObj", so controllers can declare
 * <code>UserObject userObject</code> as a parameter instead of repeating
 * <code>request.getSession().getAttribute("UserObj")</code> in every handler.
 * <p>
 * Behaves exactly like the code it replaces: the session is created if it does
 * not exist yet, and <code>null</code> is passed when the session has no
 * "UserObj" attribute (handlers that need a fallback keep their own
 * <code>if(userObject==null)</code> check).
 */
public class UserObjectArgumentResolver implements HandlerMethodArgumentResolver {

	/*
	 * Session attribute under which the logged in user is stored
	 */
	public static final String SESSION_ATTRIBUTE = "UserObj";

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return UserObject.class.equals(parameter.getParameterType());
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
		return request.getSession().getAttribute(SESSION_ATTRIBUTE);
	}
}
