package gov.ny.state.doh.bhae.nyehms.web.spring.entity.common;

import java.sql.Timestamp;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

/**
 * The eHARS persons by NYEHMS state number (table NYEHMS_EHARS_STATENO_MAP): the name, date of birth (text) and status
 * columns the adult case report form copies, the mapped eHARS state number and the HIV/AIDS diagnosis date (the CDC
 * RIDR search and the CDC case definition check of DocumentDAO).
 * <p>
 * Read-only, used in JPQL projections only. A state number can have several rows. <b>NYEHMS_STATENO is declared as
 * the key only because an entity needs one; it is not unique.</b>
 */
@Entity
@Immutable
@Getter
@Table(name = "NYEHMS_EHARS_STATENO_MAP")
public class NyehmsEharsStatenoMap {

	@Id
	@Column(name = "NYEHMS_STATENO")
	private String nyehmsStateno;

	@Column(name = "FIRST_NAME")
	private String firstName;

	@Column(name = "MIDDLE_NAME")
	private String middleName;

	@Column(name = "LAST_NAME")
	private String lastName;

	@Column(name = "DOB")
	private String dob;

	@Column(name = "STATUS_FLAG")
	private String statusFlag;

	@Column(name = "EHARS_STATENO")
	private String eharsStateno;

	@Column(name = "HIV_AIDS_DX_DT")
	private Timestamp hivAidsDxDt;
}
