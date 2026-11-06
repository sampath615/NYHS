package gov.ny.state.doh.bhae.nyehms.web.spring.entity.scheduler;

import java.sql.Timestamp;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

/**
 * A gender code with its end date (table CODED_GENDER), read by the eCLRS loaders.
 * <p>
 * Read-only, through JPQL projections only. <b>GENDER_CODE as the key is assumed</b> (a code that repeats would not
 * matter: no entity is loaded).
 */
@Entity
@Immutable
@Getter
@Table(name = "CODED_GENDER")
public class CodedGender {

	@Id
	@Column(name = "GENDER_CODE")
	private String genderCode;

	@Column(name = "END_DATE")
	private Timestamp endDate;
}
