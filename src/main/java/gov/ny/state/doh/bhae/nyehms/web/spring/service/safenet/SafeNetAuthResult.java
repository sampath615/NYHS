package gov.ny.state.doh.bhae.nyehms.web.spring.service.safenet;

import lombok.Getter;

/**
 * Outcome of a crypto card (SafeNet MobilePass authentication attempt)
 */

@Getter
public enum SafeNetAuthResult {

    SUCCESS(""),

    /** Server rejected the PIN + passcode. */
    FAILED("Incorrect cryptocard number. Please enter your PIN followed by a new passcode."),

    /** Server asked for a challenge / PIN change / re-authentication, which NYEHMS does not support. */
    ACTION_REQUIRED("Your crypto card requires additional action (new PIN or challenge). Please contact the NYEHMS help desk."),

    /** Blank user id or passcode. */
    INVALID_INPUT("Please enter your PIN followed by the passcode."),

    /** SDK not initialized, both servers down, or the server returned no usable response. */
    UNAVAILABLE("Crypto card verification is temporarily unavailable. Please try again later or contact the NYEHMS help desk.");



    private final String userMessage;

    SafeNetAuthResult(String userMessage) {
        this.userMessage = userMessage;
    }

    public boolean isSuccess() {
        return this == SUCCESS;
    }

}
