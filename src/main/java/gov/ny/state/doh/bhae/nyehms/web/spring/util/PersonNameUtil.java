package gov.ny.state.doh.bhae.nyehms.web.spring.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Helpers for parsing person names received in incoming documents (CCD, HL7).
 */
public final class PersonNameUtil {

	/*
	 * Name suffixes recognised as a separate word of a name
	 */
	public static final List<String> NAME_SUFFIXES = Collections.unmodifiableList(
			Arrays.asList("JR", "SR", "III", "II", "MD", "MS", "MA", "MRS", "MR", "SIR"));

	private PersonNameUtil() {
	}

	/**
	 * Splits a known suffix off a name.
	 * <p>
	 * The first whitespace-separated word of the name that is in
	 * {@link #NAME_SUFFIXES} is taken as the suffix, and every occurrence of it is
	 * removed from the name.
	 *
	 * @param name the name, usually already upper-cased and trimmed
	 * @return <code>{nameWithoutSuffix, suffix}</code>, or <code>null</code> when
	 *         the name is empty or has no known suffix
	 */
	public static String[] splitSuffix(String name) {
		if (CommonUtil.isNullOrEmpty(name)) {
			return null;
		}
		return Arrays.stream(name.split("\\s+"))
				.filter(NAME_SUFFIXES::contains)
				.findFirst()
				.map(suffix -> new String[] { name.replace(suffix, "").trim(), suffix })
				.orElse(null);
	}
}
