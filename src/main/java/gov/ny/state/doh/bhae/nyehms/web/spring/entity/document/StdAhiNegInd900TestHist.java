package gov.ny.state.doh.bhae.nyehms.web.spring.entity.document;

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
 * The negative / indeterminate HIV test history of an AHI form (table STD_AHI_NEG_IND_900_TEST_HIST).
 * <p>
 * <b>The table has no known key</b>: AHI_DOCUMENT_ID is declared as the id, but it repeats (one row per test), so
 * the rows are inserted one by one and detached, and read through projections only. All columns are text, as the
 * former statements bound and read them (LAST_NEG_IND_DATE is assumed to be a text column).
 */
@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "STD_AHI_NEG_IND_900_TEST_HIST")
public class StdAhiNegInd900TestHist {

	@Id
	@Column(name = "AHI_DOCUMENT_ID")
	private Long ahiDocumentId;

	@Column(name = "LAST_NEG_IND_DATE")
	private String lastNegIndDate;

	@Column(name = "NEG_IND_RESULT")
	private String negIndResult;

	@Column(name = "NEG_IND_TEST_TYPE_CODE")
	private String negIndTestTypeCode;

	@Column(name = "NEG_IND_TEST_TYPE_OTHER")
	private String negIndTestTypeOther;

	@Column(name = "NEG_IND_LAB_REPORT")
	private String negIndLabReport;

	@Column(name = "NEG_IND_LAST_TEST_PROV_NAME")
	private String negIndLastTestProvName;

	@Column(name = "NEG_IND_PROV_NAME_UNKNOWN")
	private String negIndProvNameUnknown;
}
