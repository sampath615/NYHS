package gov.ny.state.doh.bhae.nyehms.web.spring.entity.assignment;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

/**
 * The case report form columns the reabstraction compares, per raw table (table REAB_CRF_COMPARE_COLS).
 * <p>
 * Read-only and read through projections only, so a key that repeats does not matter. <b>COLUMN_NAME as the key is
 * assumed</b> (a column name may repeat over tables).
 */
@Entity
@Immutable
@Getter
@Table(name = "REAB_CRF_COMPARE_COLS")
public class ReabCrfCompareCols {

	@Id
	@Column(name = "COLUMN_NAME")
	private String columnName;

	@Column(name = "TABLE_NAME")
	private String tableName;
}
