package gov.ny.state.doh.bhae.nyehms.web.spring.platform.web;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Result of a save/update call, in the JSON shape the pages already read:
 * <pre>{ "TRANSACTION_RESULT": "TRANSACTION_SUCCESS", "TRANSACTION_MESSAGE": "Successfully created the message" }</pre>
 * Replaces the {@code Map} filled through {@code BindingResult} + {@code inspectDBTransacionResult}.
 */
public record ActionResult(
        @JsonProperty("TRANSACTION_RESULT") String result,
        @JsonProperty("TRANSACTION_MESSAGE") String message) {

    public static ActionResult success(String message) {
        return new ActionResult("TRANSACTION_SUCCESS", message);
    }
}
