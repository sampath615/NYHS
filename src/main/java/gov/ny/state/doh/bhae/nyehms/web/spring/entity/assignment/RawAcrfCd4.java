package gov.ny.state.doh.bhae.nyehms.web.spring.entity.assignment;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/** A CD4 test of an adult case report form (table RAW_ACRF_CD4); see {@link RawCrfCd4}. */
@Entity
@Table(name = "RAW_ACRF_CD4")
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RawAcrfCd4 extends RawCrfCd4 {
}
