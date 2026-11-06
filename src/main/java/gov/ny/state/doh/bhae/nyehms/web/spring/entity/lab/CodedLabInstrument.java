package gov.ny.state.doh.bhae.nyehms.web.spring.entity.lab;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.Getter;

/**
 * The lab instruments of the LOINC screen (table CODED_LAB_INSTRUMENT).
 * <p>
 * Read-only. <b>INSTRUMENT_CD as the key is assumed.</b>
 */
@Entity
@Immutable
@Getter
@Table(name = "CODED_LAB_INSTRUMENT")
public class CodedLabInstrument {

	@Id
	@Column(name = "INSTRUMENT_CD")
	private String instrumentCd;

	@Column(name = "INSTRUMENT_DESC")
	private String instrumentDesc;

	@Column(name = "SORT_ORDER")
	private Long sortOrder;
}
