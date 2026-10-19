package gov.ny.state.doh.bhae.nyehms.web.spring.record;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * A result an ACRF test type can have, as the ICCR data entry screen loads it ({@code getICCRTestResult.json}).
 * The JSON is {@code {"code": ..., "description": ...}} (read by iccrDeDoc.js), without the keys whose
 * value is null (as the former MyBatis map left them out).
 *
 * @param code ACRF_RESULT_CODE
 * @param description ACRF_RESULT_DESC
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record AcrfTestResult(String code, String description) {
}
