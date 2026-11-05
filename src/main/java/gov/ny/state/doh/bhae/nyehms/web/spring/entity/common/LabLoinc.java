package gov.ny.state.doh.bhae.nyehms.web.spring.entity.common;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * A LOINC code of the LOINC lookup with its test type (table LAB_LOINC); the columns LoincResultDomainMappingDAO reads
 * and writes (the table is also used by LabReportsDAO and SchedulerJobsDAO).
 * <p>
 * <b>LOINC_CD as the key is assumed</b> (set by the application; an insert of an existing code fails, as before).
 */
@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "LAB_LOINC")
public class LabLoinc {

	@Id
	@Column(name = "LOINC_CD")
	private String loincCd;

	@Column(name = "TEST_TYPE_CD")
	private String testTypeCd;
}
