package gov.ny.state.doh.bhae.nyehms.web.spring.entity.person;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

/**
 * A person (table PERSON): only the active flag the person screen checks. Read in a JPQL projection by
 * {@code PersonDAO.checkStatenoIsActive}; the flag is cleared by a bulk JPQL update when a person's last document is
 * deleted ({@code CommonDocLabsDAO.checkAndInactiveStateno}), hence not immutable. <b>STATENO as the key is
 * assumed</b>.
 */
@Entity
@Getter
@Table(name = "PERSON")
public class Person {

	@Id
	@Column(name = "STATENO")
	private String stateno;

	@Column(name = "FLAG_ACTIVE")
	private String flagActive;
}
