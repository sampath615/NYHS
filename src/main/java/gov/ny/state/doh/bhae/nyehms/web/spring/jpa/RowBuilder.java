package gov.ny.state.doh.bhae.nyehms.web.spring.jpa;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import jakarta.persistence.Column;

/**
 * Builds a {@link Row} from entity attributes (by column name) and other values, so a loaded entity can be turned into a
 * former MyBatis result with the {@link Rows} mappers: the labels are the column labels the former SELECT produced
 * ({@code "COLUMN"} or {@code "COLUMN LABEL"} for an alias), the values the entity's attribute values.
 * <p>
 * Example: {@code Rows.bean(XyzVO.class).map(new RowBuilder().columns(doc, StdXyzDoc.class, "DOCUMENT_ID", "DOB DOB_TEXT").put("AHI_FLAG", flag).build())}.
 */
public final class RowBuilder {

	private static final Map<Class<?>, Map<String, Field>> COLUMNS = new ConcurrentHashMap<>();

	private final List<String> labels = new ArrayList<>();
	private final List<Object> values = new ArrayList<>();

	/**
	 * Adds entity attributes by column name.
	 *
	 * @param entity the entity (null: every value is null)
	 * @param type the entity class
	 * @param columns {@code "COLUMN"} (labelled as the column) or {@code "COLUMN LABEL"}
	 */
	public RowBuilder columns(Object entity, Class<?> type, String... columns) {
		Map<String, Field> fields = COLUMNS.computeIfAbsent(type, RowBuilder::columnFields);
		for (String column : columns) {
			String[] parts = column.trim().split("\\s+");
			Field field = fields.get(parts[0].toUpperCase(Locale.ENGLISH));
			if (field == null) {
				throw new IllegalArgumentException("No column " + parts[0] + " in " + type.getName());
			}
			try {
				put(parts.length > 1 ? parts[1] : parts[0], entity == null ? null : field.get(entity));
			} catch (IllegalAccessException e) {
				throw new IllegalStateException(e);
			}
		}
		return this;
	}

	/** Adds a value under a label. */
	public RowBuilder put(String label, Object value) {
		labels.add(label);
		values.add(value);
		return this;
	}

	public Row build() {
		return new Row(new ArrayList<>(labels), new ArrayList<>(values));
	}

	private static Map<String, Field> columnFields(Class<?> type) {
		Map<String, Field> fields = new HashMap<>();
		for (Class<?> c = type; c != null && c != Object.class; c = c.getSuperclass()) {
			for (Field field : c.getDeclaredFields()) {
				Column column = field.getAnnotation(Column.class);
				if (column != null) {
					field.setAccessible(true);
					fields.putIfAbsent(column.name().toUpperCase(Locale.ENGLISH), field);
				}
			}
		}
		return fields;
	}
}
