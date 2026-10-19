package gov.ny.state.doh.bhae.nyehms.web.spring.entity.document;

import java.io.Serializable;

/**
 * Key of {@link CodedAcrfTestResult} (assumed, see that class).
 */
public record CodedAcrfTestResultId(String acrfTestCode, String acrfResultCode) implements Serializable {
}
