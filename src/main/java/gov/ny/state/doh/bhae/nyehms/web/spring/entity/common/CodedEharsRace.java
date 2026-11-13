package gov.ny.state.doh.bhae.nyehms.web.spring.entity.common;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * The eHARS race codes (table CODED_EHARS_RACE), read-only. <b>EHARS_RACE_CODE as the key is assumed.</b>
 */
@Entity
@Immutable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "CODED_EHARS_RACE")
public class CodedEharsRace {

	@Id
	@Column(name = "EHARS_RACE_CODE")
	private String eharsRaceCode;

	@Column(name = "EHARS_RACE_DESC")
	private String eharsRaceDesc;

	/** 'Y' for the races of the expanded race list (CommonDocLabsDAO). */
	@Column(name = "EXPANDED_RACE")
	private String expandedRace;
}
