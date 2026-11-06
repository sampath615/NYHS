package gov.ny.state.doh.bhae.nyehms.web.spring.entity.assignment;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/** A lab test of a pediatric case report form (table RAW_PCRF_LABS); see {@link RawCrfLabs}. */
@Entity
@Table(name = "RAW_PCRF_LABS")
@AttributeOverride(name = "testCode", column = @Column(name = "PCRF_TEST_CODE"))
@AttributeOverride(name = "resultCode", column = @Column(name = "PCRF_RESULT_CODE"))
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RawPcrfLabs extends RawCrfLabs {
}
