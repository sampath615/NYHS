package gov.ny.state.doh.bhae.nyehms.web.spring.entity.common;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.Getter;

/**
 * A person of the NYC eHARS (table EHARS_PERSON_NYC), read-only: the NYC city numbers matched against NYEHMS
 * persons. The columns the former ADAP statements used.
 * <p>
 * <b>NYEHMS_STATENO as the key is assumed</b>; the table is read through counts and exists queries only, so a key
 * that repeats does not matter.
 */
@Entity
@Immutable
@Getter
@Table(name = "EHARS_PERSON_NYC")
public class EharsPersonNyc {

	@Id
	@Column(name = "NYEHMS_STATENO")
	private String nyehmsStateno;

	@Column(name = "CITYNO")
	private String cityno;
}
