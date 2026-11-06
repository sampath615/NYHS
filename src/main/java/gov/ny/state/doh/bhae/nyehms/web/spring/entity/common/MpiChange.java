package gov.ny.state.doh.bhae.nyehms.web.spring.entity.common;

import java.io.Serializable;
import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.Getter;

/**
 * The MPI changes found by the HIE checks (table MPI_CHANGE): a current MPI and the old MPI it replaces; only the
 * columns the HIE death check writes.
 * <p>
 * Written by an HQL insert ... select only. <b>The key (CURRENT_MPI, OLD_MPI) is assumed</b> (the insert never adds a
 * pair that exists). The MPIs are text.
 */
@Entity
@Getter
@Table(name = "MPI_CHANGE")
@IdClass(MpiChange.Key.class)
public class MpiChange {

	/** The assumed key. */
	public record Key(String currentMpi, String oldMpi) implements Serializable {
	}

	@Id
	@Column(name = "CURRENT_MPI")
	private String currentMpi;

	@Id
	@Column(name = "OLD_MPI")
	private String oldMpi;

	@Column(name = "DATE_RECEIVED")
	private Timestamp dateReceived;
}
