package gov.ny.state.doh.bhae.nyehms.web.spring.record;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A result an ACRF test type can have, as the ICCR data entry screen loads it ({@code getICCRTestResult.json}).
 * The JSON keeps the former shape: {@code {"ACRF_RESULT_CODE": ..., "ACRF_RESULT_DESC": ...}}, without
 * the keys whose value is null (as the former MyBatis map left them out).
 *
 * @param code ACRF_RESULT_CODE
 * @param description ACRF_RESULT_DESC
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record AcrfTestResult(@JsonProperty("ACRF_RESULT_CODE") String code, @JsonProperty("ACRF_RESULT_DESC") String description) {
}
