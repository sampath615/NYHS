package gov.ny.state.doh.bhae.nyehms.web.spring.record;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.common.CodedLabTestType;
import gov.ny.state.doh.bhae.nyehms.web.spring.entity.common.LabLoinc;

/** A LOINC code of the lookup with its test type (LAB_LOINC joined with CODED_LAB_TEST_TYPE). */
public record LoincTestTypeRow(LabLoinc loinc, CodedLabTestType testType) {
}
