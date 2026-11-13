package gov.ny.state.doh.bhae.nyehms.web.spring.jpa;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Reads a property path the way MyBatis read {@code #{a.b.c}} from its parameter: each segment a map key or a bean
 * getter ({@code getX()} / {@code isX()}, matched ignoring case, as MyBatis' reflector named properties); a null on the
 * way gives null.
 */
public final class BeanPaths {

	private BeanPaths() {
	}

	private static final Map<String, Method> GETTERS = new ConcurrentHashMap<>();

	/**
	 * The value at a path.
	 *
	 * @param root a map or a bean
	 * @param path the property path, e.g. {@code name.firstName}
	 * @throws IllegalArgumentException if a bean on the path has no getter for a segment
	 */
	public static Object get(Object root, String path) {
		Object current = root;
		for (String segment : path.split("\\.")) {
			if (current == null) {
				return null;
			}
			if (current instanceof Map<?, ?> map) {
				current = map.get(segment);
				continue;
			}
			current = invoke(getter(current.getClass(), segment), current);
		}
		return current;
	}

	private static Method getter(Class<?> type, String property) {
		return GETTERS.computeIfAbsent(type.getName() + "#" + property, key -> {
			String wanted = property.toUpperCase(Locale.ENGLISH);
			for (Method method : type.getMethods()) {
				String name = method.getName();
				if (method.getParameterCount() != 0 || method.getReturnType() == void.class) {
					continue;
				}
				String candidate = name.startsWith("get") ? name.substring(3) : name.startsWith("is") ? name.substring(2) : null;
				if (candidate != null && candidate.toUpperCase(Locale.ENGLISH).equals(wanted)) {
					return method;
				}
			}
			throw new IllegalArgumentException("No getter for " + property + " in " + type.getName());
		});
	}

	private static Object invoke(Method getter, Object bean) {
		try {
			return getter.invoke(bean);
		} catch (IllegalAccessException | InvocationTargetException e) {
			throw new IllegalStateException("Cannot read " + getter, e);
		}
	}
}
