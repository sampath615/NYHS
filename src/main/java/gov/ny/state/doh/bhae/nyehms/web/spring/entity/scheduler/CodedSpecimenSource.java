package gov.ny.state.doh.bhae.nyehms.web.spring.entity.scheduler;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

/**
 * A specimen source with its specimen type (table CODED_SPECIMEN_SOURCE), read by the eCLRS loaders.
 * <p>
 * Read-only, through JPQL projections only. <b>SPECIMEN_SOURCE_NAME as the key is assumed</b> (a name that repeats
 * would not matter: no entity is loaded). SPECIMENTYPE is read as text, as the former statement read it.
 */
@Entity
@Immutable
@Getter
@Table(name = "CODED_SPECIMEN_SOURCE")
public class CodedSpecimenSource {

	@Id
	@Column(name = "SPECIMEN_SOURCE_NAME")
	private String specimenSourceName;

	@Column(name = "SPECIMENTYPE")
	private String specimentype;
}
