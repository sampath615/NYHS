package gov.ny.state.doh.bhae.nyehms.web.spring.entity.assignment;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

/**
 * The differences between the field staff's and the reabstracted case report form of an assignment, per tab (table
 * CO_ACRF_REAB_COL), as the reabstraction compare writes them and the difference summary reads them.
 * <p>
 * The table has no known key and an assignment has many rows (possibly equal ones), so it is written by a JPQL insert
 * statement and read through projections only, never loaded or saved as an entity. <b>ASSIGNMENT_ID as the key is
 * assumed</b> (it is not one).
 */
@Entity
@Getter
@Table(name = "CO_ACRF_REAB_COL")
public class CoAcrfReabCol {

	@Id
	@Column(name = "ASSIGNMENT_ID")
	private Long assignmentId;

	@Column(name = "DIFFERENT_COLUMN_NAMES")
	private String differentColumnNames;

	@Column(name = "TAB_NAME")
	private String tabName;

	@Column(name = "ARV_USE_TYPE")
	private String arvUseType;

	/** The source row id (LAB_SRC_ID / CD4_SRC_ID) of the field staff's form. */
	@Column(name = "SRC_ID_MAIN")
	private BigDecimal srcIdMain;

	/** The source row id of the reabstracted form. */
	@Column(name = "SRC_ID_SEC")
	private BigDecimal srcIdSec;

	@Column(name = "SAME_VALUES")
	private String sameValues;
}
