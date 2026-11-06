package gov.ny.state.doh.bhae.nyehms.web.spring.entity.assignment;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/** A CD4 test of a pediatric case report form (table RAW_PCRF_CD4); see {@link RawCrfCd4}. */
@Entity
@Table(name = "RAW_PCRF_CD4")
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RawPcrfCd4 extends RawCrfCd4 {
}
