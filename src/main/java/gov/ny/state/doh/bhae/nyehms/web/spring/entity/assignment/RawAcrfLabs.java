package gov.ny.state.doh.bhae.nyehms.web.spring.entity.assignment;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/** A lab test of an adult case report form (table RAW_ACRF_LABS); see {@link RawCrfLabs}. */
@Entity
@Table(name = "RAW_ACRF_LABS")
@AttributeOverride(name = "testCode", column = @Column(name = "ACRF_TEST_CODE"))
@AttributeOverride(name = "resultCode", column = @Column(name = "ACRF_RESULT_CODE"))
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RawAcrfLabs extends RawCrfLabs {
}
