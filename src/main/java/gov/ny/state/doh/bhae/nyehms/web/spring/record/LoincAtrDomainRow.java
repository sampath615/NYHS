package gov.ny.state.doh.bhae.nyehms.web.spring.record;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.common.AccptdTstResultDomain;
import gov.ny.state.doh.bhae.nyehms.web.spring.entity.common.CodedEharsStdResults;
import gov.ny.state.doh.bhae.nyehms.web.spring.entity.common.LoincAccptdTstResultDomain;

/**
 * An acceptable test result associated with a LOINC code, with its eHARS standard result
 * (LOINC_ACCPTD_TST_RESULT_DOMAIN joined with ACCPTD_TST_RESULT_DOMAIN and CODED_EHARS_STD_RESULTS).
 */
public record LoincAtrDomainRow(LoincAccptdTstResultDomain association, AccptdTstResultDomain domain, CodedEharsStdResults standardResult) {
}
