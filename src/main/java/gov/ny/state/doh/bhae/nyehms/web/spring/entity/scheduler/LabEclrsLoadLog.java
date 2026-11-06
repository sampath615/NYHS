package gov.ny.state.doh.bhae.nyehms.web.spring.entity.scheduler;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

/**
 * A run of the eCLRS HIV / STD loaders (table LAB_ECLRS_LOAD_LOG): the lowest and highest OBX key loaded, HIV or STD,
 * the number of records and of errors, the latest PID update date and who created the row when.
 * <p>
 * Inserted by a JPQL insert and changed by bulk JPQL updates only (its "last run" read stays native). <b>OBX_MAX as
 * the key is assumed</b> (the updates find a run by OBX_MAX).
 */
@Entity
@Getter
@Table(name = "LAB_ECLRS_LOAD_LOG")
public class LabEclrsLoadLog {

	@Id
	@Column(name = "OBX_MAX")
	private Long obxMax;

	@Column(name = "OBX_MIN")
	private Long obxMin;

	@Column(name = "HIV_STD")
	private String hivStd;

	@Column(name = "TOTAL_NO_RECORDS")
	private Long totalNoRecords;

	@Column(name = "NO_OF_ERRORS")
	private Long noOfErrors;

	@Column(name = "LAST_PIDUPDATEDATE")
	private Timestamp lastPidupdatedate;

	@Column(name = "CREATE_ID")
	private String createId;

	@Column(name = "CREATE_DATE")
	private Timestamp createDate;
}
