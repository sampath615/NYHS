package gov.ny.state.doh.bhae.nyehms.web.spring.entity.document;

import org.hibernate.annotations.ColumnTransformer;

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
 * A concurrent STI (sexually transmitted infection) condition of an AHI form (table STD_AHI_CI_STI_CONDITION).
 * <p>
 * <b>The table has no known key</b>: AHI_DOCUMENT_ID is declared as the id, but it repeats (one row per condition),
 * so the rows are inserted one by one and detached, and read through projections only. The diagnosis date is held
 * as the text the screen shows (read with TO_CHAR 'MM/DD/YYYY', written with FN_TO_DATE and the same mask, as before).
 */
@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "STD_AHI_CI_STI_CONDITION")
public class StdAhiCiStiCondition {

	@Id
	@Column(name = "AHI_DOCUMENT_ID")
	private Long ahiDocumentId;

	@Column(name = "CI_STI_CONDITION_CODE")
	private String ciStiConditionCode;

	@Column(name = "CI_STI_CONDITION_DX_DATE")
	@ColumnTransformer(read = "TO_CHAR(CI_STI_CONDITION_DX_DATE,'MM/DD/YYYY')", write = "FN_TO_DATE(?,'MM/DD/YYYY')")
	private String ciStiConditionDxDate;
}
