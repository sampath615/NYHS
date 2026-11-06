package gov.ny.state.doh.bhae.nyehms.web.spring.entity.lab;

import java.io.Serializable;

/**
 * Key of {@link LaftClepAuditAccessionData} (assumed, see that class).
 */
public record LaftClepAuditAccessionDataId(Long surveyNumber, Long serialNo) implements Serializable {
}
