package gov.ny.state.doh.bhae.nyehms.web.spring.entity.common;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

/**
 * An eCLRS result to reassign (table LAB_ECLRS_REASSIGN): the source key (an OBX or OBR key) of a result, the OBX/OBR
 * key it belongs to, HIV or STD, and when the scheduler processed it; the columns SchedulerJobsDAO reads and writes (the
 * table is also used by EcrlsDataDAO).
 * <p>
 * Read through JPQL projections and changed by bulk JPQL updates only. <b>SRC_ID as the key is assumed</b> (a key that
 * repeats would not matter: no entity is loaded).
 */
@Entity
@Getter
@Table(name = "LAB_ECLRS_REASSIGN")
public class LabEclrsReassign {

	@Id
	@Column(name = "SRC_ID")
	private Long srcId;

	@Column(name = "OBX_OBR")
	private String obxObr;

	@Column(name = "HIV_STD")
	private String hivStd;

	@Column(name = "PROCESS_DATE")
	private Timestamp processDate;
}
