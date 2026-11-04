package gov.ny.state.doh.bhae.nyehms.web.spring.platform.web;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Locale;

/**
 * Date-times as the pages exchange them: the bootstrap date-time picker posts "10/22/2026 01:05:00 PM"
 * (its format "MM/dd/yyyy HH:mm:ss PP", where HH is the 12-hour clock), and the jqGrid date formatter
 * reads the same text ('m/d/Y h:i:s A').
 */
public final class PickerDateTime {

    /** For {@code @JsonFormat(pattern = PickerDateTime.PATTERN, locale = "en_US")}; same as Oracle 'MM/DD/YYYY HH:MI:SS AM'. */
    public static final String PATTERN = "MM/dd/yyyy hh:mm:ss a";

    /** Parsing accepts 1 or 2 digit month/day/hour and am/pm in any case. */
    private static final DateTimeFormatter PARSER = new DateTimeFormatterBuilder()
            .parseCaseInsensitive()
            .appendPattern("M/d/yyyy h:mm:ss a")
            .toFormatter(Locale.US);

    private PickerDateTime() {
    }

    /** @throws java.time.format.DateTimeParseException when the text is missing or not a date-time */
    public static LocalDateTime parse(String text) {
        if (text == null) {
            throw new java.time.format.DateTimeParseException("date-time is missing", "", 0);
        }
        return LocalDateTime.parse(text.trim(), PARSER);
    }

    /** Blank (the grid sends "" or a single space for an empty cell) means no date. */
    public static LocalDateTime parseOptional(String text) {
        return (text == null || text.isBlank()) ? null : parse(text);
    }
}
