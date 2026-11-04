package gov.ny.state.doh.bhae.nyehms.web.spring.jpa;

import java.util.List;
import java.util.Locale;

/**
 * One result row of a native query: its column labels (as the database names them) and values (typed as JDBC
 * getObject types them).
 *
 * @param labels the column labels, in select order (a label may occur twice)
 * @param values the values, in the same order
 */
public record Row(List<String> labels, List<Object> values) {

	/** The number of columns. */
	public int size() {
		return labels.size();
	}

	/** The value of a column by position (0-based). */
	public Object get(int index) {
		return values.get(index);
	}

	/**
	 * The value of a column by label, ignoring case; of two columns with the same label the first, as JDBC and
	 * MyBatis read it.
	 *
	 * @throws IllegalArgumentException if no column has the label
	 */
	public Object get(String label) {
		int index = indexOf(label);
		if (index < 0) {
			throw new IllegalArgumentException("No column " + label + " in " + labels);
		}
		return values.get(index);
	}

	/** The position of the first column with this label (ignoring case), or -1. */
	public int indexOf(String label) {
		String wanted = label.toUpperCase(Locale.ENGLISH);
		for (int i = 0; i < labels.size(); i++) {
			if (labels.get(i).toUpperCase(Locale.ENGLISH).equals(wanted)) {
				return i;
			}
		}
		return -1;
	}
}
