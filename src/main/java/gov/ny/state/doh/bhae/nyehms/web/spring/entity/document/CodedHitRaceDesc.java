package gov.ny.state.doh.bhae.nyehms.web.spring.entity.document;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * A race value found in HIT payloads (table CODED_HIT_RACE_DESC) with the race and ethnicity the HIT race mapping
 * screen assigns to it. Read through projections, inserted by an HQL insert and changed by a bulk JPQL update
 * (HITDocumentDAO). <b>RACE_VALUE as the key is assumed.</b>
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "CODED_HIT_RACE_DESC")
public class CodedHitRaceDesc {

	@Id
	@Column(name = "RACE_VALUE")
	private String raceValue;

	@Column(name = "RACE")
	private String race;

	@Column(name = "ETHNICITY")
	private String ethnicity;

	@Column(name = "CREATE_DATE")
	private Timestamp createDate;

	@Column(name = "UPDATED_DATE")
	private Timestamp updatedDate;

	@Column(name = "UPDATED_BY")
	private String updatedBy;
}
