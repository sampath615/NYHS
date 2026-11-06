package gov.ny.state.doh.bhae.nyehms.web.spring.entity.sierra;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

/**
 * A drug of the Sierra (HIVdb) algorithm (table RAS3_DRUG): its id and abbreviation, read by the Sierra job
 * (SierraResponseDAO.getDrugCodedList). Read-only. <b>ID as the key is assumed</b>.
 */
@Entity
@Immutable
@Getter
@Table(name = "RAS3_DRUG")
public class Ras3Drug {

	@Id
	@Column(name = "ID")
	private Integer id;

	@Column(name = "ABBREV")
	private String abbrev;
}
