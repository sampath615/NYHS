package gov.ny.state.doh.bhae.nyehms.web.spring.entity.common;

import java.sql.Timestamp;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

/**
 * A unit of measurement with its eHARS code and end date (table CODED_UNITS); the columns the eCLRS loaders read (the
 * table is also used by CommonDocLabsDAO, which may add its columns here).
 * <p>
 * Read-only, through JPQL projections only. <b>UNITS_CODE as the key is assumed</b> (a code that repeats would not
 * matter: no entity is loaded).
 */
@Entity
@Immutable
@Getter
@Table(name = "CODED_UNITS")
public class CodedUnits {

	@Id
	@Column(name = "UNITS_CODE")
	private String unitsCode;

	@Column(name = "EHARS_UNITS_CODE")
	private String eharsUnitsCode;

	@Column(name = "END_DATE")
	private Timestamp endDate;
}
