package gov.ny.state.doh.bhae.nyehms.web.spring.jpa;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Clob;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

import org.json.simple.JSONObject;

import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements.RowMapper;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements.TypedRowMapper;

/**
 * Row mappers with the rules of the former MyBatis results (default settings: auto-mapping, no camel-case mapping,
 * no setters called for nulls).
 * <ul>
 * <li>{@link #bean}: a {@code resultType} class — each column is set on the property of the same name, ignoring case
 * (a setter {@code setXyz}, or a field without setter), converted to the property's type as MyBatis' type handlers
 * read it; a null value is not set, a column without property is ignored, and a row that sets nothing is null.</li>
 * <li>{@link #map}, {@link #json}: {@code java.util.Map} / {@code JSONObject} — one entry per column label (the first
 * of a repeated label), null values left out, a row of nulls null.</li>
 * <li>{@link #scalar}: {@code String}, {@code Integer}, ... — the first column, converted.</li>
 * </ul>
 */
public final class Rows {

	private Rows() {
	}

	/** Rows as instances of a bean class (MyBatis auto-mapping). */
	public static <T> RowMapper<T> bean(Class<T> type) {
		BeanProperties properties = BeanProperties.of(type);
		return new TypedRowMapper<T>() {
			@Override
			public Class<?> typeOf(String label, int index) {
				BeanProperty property = properties.find(label);
				return property == null ? null : property.type();
			}

			@Override
			public T map(Row row) {
				return fillBean(properties, row);
			}
		};
	}

	private static <T> T fillBean(BeanProperties properties, Row row) {
		{
			T bean = null;
			for (int i = 0; i < row.size(); i++) {
				String label = row.labels().get(i);
				if (row.indexOf(label) != i) {
					continue;                                   // a repeated label: the first column was used
				}
				Object value = row.get(i);
				BeanProperty property = properties.find(label);
				if (value == null || property == null) {
					continue;
				}
				if (bean == null) {
					bean = properties.newInstance();
				}
				property.set(bean, convert(value, property.type()));
			}
			return bean;
		}
	}

	/** Rows as plain HashMaps (resultType {@code java.util.Map}). */
	public static RowMapper<Map<String, Object>> map() {
		return row -> fill(row, HashMap::new);
	}

	/** Rows as JSONObjects (resultType {@code org.json.simple.JSONObject}). */
	@SuppressWarnings("unchecked")
	public static RowMapper<JSONObject> json() {
		return row -> (JSONObject) fill(row, () -> (Map<String, Object>) (Map<?, ?>) new JSONObject());
	}

	/** The first column of each row, converted to a type (resultType {@code String}, {@code int}, ...). */
	public static <T> RowMapper<T> scalar(Class<T> type) {
		return new TypedRowMapper<T>() {
			@Override
			public Class<?> typeOf(String label, int index) {
				return index == 0 ? type : null;
			}

			@Override
			@SuppressWarnings("unchecked")
			public T map(Row row) {
				return (T) convert(row.get(0), type);
			}
		};
	}

	private static <M extends Map<String, Object>> M fill(Row row, Supplier<M> factory) {
		M map = factory.get();
		for (int i = 0; i < row.size(); i++) {
			String label = row.labels().get(i);
			if (row.indexOf(label) == i && row.get(i) != null) {
				map.put(label, row.get(i));
			}
		}
		return map.isEmpty() ? null : map;
	}

	/**
	 * A value converted to a property type the way MyBatis' type handler for that type read the column (getString,
	 * getInt, getTimestamp, ...).
	 *
	 * @param value a value typed as JDBC getObject types it; null stays null
	 */
	static Object convert(Object value, Class<?> type) {
		if (value == null) {
			return null;
		}
		if (value instanceof Clob clob) {
			value = text(clob);
		}
		if (type == char.class || type == Character.class) {
			// CharacterTypeHandler: the first character of getString (an empty text is null in Oracle)
			if (value instanceof Character character) {
				return character;
			}
			String text = value instanceof BigDecimal number ? number.toPlainString() : value.toString();
			return text.isEmpty() ? null : text.charAt(0);
		}
		if (type == Object.class) {
			return value;
		}
		if (type == String.class) {
			if (value instanceof BigDecimal number) {
				return number.toPlainString();
			}
			return value.toString();
		}
		if (type == int.class || type == Integer.class) {
			return number(value).intValue();
		}
		if (type == long.class || type == Long.class) {
			return number(value).longValue();
		}
		if (type == double.class || type == Double.class) {
			return number(value).doubleValue();
		}
		if (type == float.class || type == Float.class) {
			return number(value).floatValue();
		}
		if (type == short.class || type == Short.class) {
			return number(value).shortValue();
		}
		if (type == byte.class || type == Byte.class) {
			return number(value).byteValue();
		}
		if (type == BigDecimal.class) {
			return number(value);
		}
		if (type == BigInteger.class) {
			return number(value).toBigInteger();
		}
		if (type == boolean.class || type == Boolean.class) {
			if (value instanceof Boolean flag) {
				return flag;
			}
			if (value instanceof Number number) {
				return number.intValue() != 0;
			}
			String text = value.toString().trim();
			if (text.equals("1") || text.equalsIgnoreCase("true")) {
				return true;
			}
			if (text.equals("0") || text.equalsIgnoreCase("false")) {
				return false;
			}
			throw new IllegalArgumentException("Not a boolean: " + value);
		}
		if (type == java.sql.Date.class) {
			return java.sql.Date.valueOf(timestamp(value).toLocalDateTime().toLocalDate());   // getDate: the day only
		}
		if (type == Timestamp.class) {
			return timestamp(value);
		}
		if (type == java.util.Date.class) {
			return new java.util.Date(timestamp(value).getTime());                        // getTimestamp as a Date
		}
		if (type == LocalDate.class) {
			return timestamp(value).toLocalDateTime().toLocalDate();
		}
		if (type == LocalDateTime.class) {
			return timestamp(value).toLocalDateTime();
		}
		if (type.isInstance(value)) {
			return value;
		}
		throw new IllegalArgumentException("Cannot read " + value.getClass().getName() + " as " + type.getName());
	}

	private static BigDecimal number(Object value) {
		if (value instanceof BigDecimal number) {
			return number;
		}
		if (value instanceof Number number) {
			return new BigDecimal(number.toString());
		}
		if (value instanceof Boolean flag) {
			return flag ? BigDecimal.ONE : BigDecimal.ZERO;
		}
		return new BigDecimal(value.toString().trim());
	}

	private static Timestamp timestamp(Object value) {
		if (value instanceof Timestamp timestamp) {
			return timestamp;
		}
		if (value instanceof java.util.Date date) {
			return new Timestamp(date.getTime());
		}
		if (value instanceof LocalDateTime dateTime) {
			return Timestamp.valueOf(dateTime);
		}
		if (value instanceof LocalDate date) {
			return Timestamp.valueOf(date.atStartOfDay());
		}
		throw new IllegalArgumentException("Not a date: " + value);
	}

	private static String text(Clob clob) {
		try {
			return clob.getSubString(1, (int) clob.length());
		} catch (SQLException e) {
			throw new IllegalStateException(e);
		}
	}

	/** A settable property of a bean: its setter (or field) and type. */
	record BeanProperty(Method setter, Field field, Class<?> type) {

		void set(Object bean, Object value) {
			try {
				if (setter != null) {
					setter.invoke(bean, value);
				} else {
					field.set(bean, value);
				}
			} catch (IllegalAccessException | InvocationTargetException e) {
				throw new IllegalStateException("Cannot set " + (setter != null ? setter : field), e);
			}
		}
	}

	/** The settable properties of a bean class, by upper-case name (MyBatis' Reflector rules). */
	record BeanProperties(Class<?> type, Map<String, BeanProperty> properties) {

		private static final Map<Class<?>, BeanProperties> CACHE = new ConcurrentHashMap<>();

		static BeanProperties of(Class<?> type) {
			return CACHE.computeIfAbsent(type, BeanProperties::reflect);
		}

		private static BeanProperties reflect(Class<?> type) {
			Map<String, BeanProperty> properties = new LinkedHashMap<>();
			for (Class<?> c = type; c != null && c != Object.class; c = c.getSuperclass()) {
				for (Method method : c.getDeclaredMethods()) {
					String name = method.getName();
					if (name.length() > 3 && name.startsWith("set") && method.getParameterCount() == 1 && !Modifier.isStatic(method.getModifiers())) {
						method.setAccessible(true);
						properties.putIfAbsent(key(property(name)), new BeanProperty(method, null, method.getParameterTypes()[0]));
					}
				}
			}
			// fields without a setter are set directly
			for (Class<?> c = type; c != null && c != Object.class; c = c.getSuperclass()) {
				for (Field field : c.getDeclaredFields()) {
					int modifiers = field.getModifiers();
					if (!Modifier.isStatic(modifiers) && !Modifier.isFinal(modifiers) && !properties.containsKey(key(field.getName()))) {
						field.setAccessible(true);
						properties.put(key(field.getName()), new BeanProperty(null, field, field.getType()));
					}
				}
			}
			return new BeanProperties(type, properties);
		}

		/** setXyz -> xyz, setXYZ / setX_Y -> XYZ / X_Y (MyBatis PropertyNamer). */
		private static String property(String setterName) {
			String name = setterName.substring(3);
			if (name.length() == 1 || !Character.isUpperCase(name.charAt(1))) {
				name = name.substring(0, 1).toLowerCase(Locale.ENGLISH) + name.substring(1);
			}
			return name;
		}

		private static String key(String name) {
			return name.toUpperCase(Locale.ENGLISH);
		}

		BeanProperty find(String label) {
			return properties.get(key(label));
		}

		@SuppressWarnings("unchecked")
		<T> T newInstance() {
			try {
				var constructor = type.getDeclaredConstructor();
				constructor.setAccessible(true);
				return (T) constructor.newInstance();
			} catch (ReflectiveOperationException e) {
				throw new IllegalStateException("Cannot create " + type.getName(), e);
			}
		}
	}
}
