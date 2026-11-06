package gov.ny.state.doh.bhae.nyehms.web.spring.entity.common;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

/**
 * A parameter of the schedulers (table LAB_SCHEDULER_PARAMETERS): name and text value (e.g. the last run date of a
 * scheduler as YYYYMMDD, the users allowed to run the schedulers, SQL the scheduler runs); the columns SchedulerJobsDAO
 * reads and writes (the table is also used by CommonDAO).
 * <p>
 * Read through JPQL projections and changed by bulk JPQL updates only. <b>PARAMTER_NAME as the key is assumed</b> (the
 * statements read and update a parameter by its name; a name that repeats would not matter: no entity is loaded).
 */
@Entity
@Getter
@Table(name = "LAB_SCHEDULER_PARAMETERS")
public class LabSchedulerParameters {

	@Id
	@Column(name = "PARAMTER_NAME")
	private String paramterName;

	@Column(name = "PARAMTER_VALUE")
	private String paramterValue;
}
