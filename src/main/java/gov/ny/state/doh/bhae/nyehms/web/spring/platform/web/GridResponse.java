package gov.ny.state.doh.bhae.nyehms.web.spring.platform.web;

import java.util.List;

/**
 * Response body for the jqGrid tables, same JSON as the old {@code GridObject}/{@code GetGridObject}:
 * <pre>{ "currentPage": 1, "totalPages": 1, "recordsPerPage": 5, "totalRecords": 3, "records": 3, "rows": [...] }</pre>
 * The grids read {@code rows}, {@code currentPage} and {@code totalPages} (jsonReader root/page/total).
 * <p>
 * A new object per response. The old {@code GetGridObject} is a singleton that reuses one shared
 * {@code GridObject}, so two users loading a grid at the same moment could get each other's rows.
 */
public record GridResponse<T>(
        int currentPage,
        int totalPages,
        int recordsPerPage,
        int totalRecords,
        int records,
        List<T> rows) {

    private static final int RECORDS_PER_PAGE = 5;

    /** All rows on one response, page numbers computed exactly like {@code GetGridObject.GetGrid}. */
    public static <T> GridResponse<T> of(List<T> rows) {
        int total = rows.size();
        int totalPages = (total / RECORDS_PER_PAGE > 1) ? total / RECORDS_PER_PAGE : 1;
        return new GridResponse<>(1, totalPages, RECORDS_PER_PAGE, total, total, rows);
    }
}
